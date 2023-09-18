from pwn import *
elf = context.binary = ELF("./super_secure_heap_patched")
libc = elf.libc
r = elf.process()

gdb.attach(r, '''
    b* 
''')

r.interactive()