from pwn import *
import os

#os.system("gcc -o bai4 -fno-stack-protector bai4.c")

p = gdb.debug("./bai4")

def sendline(line):
    p.sendline(line)
    return
p.recvline()
sendline(b'')
p.recvuntil(b'Oke, ')
print(hex(u64(p.recvuntil(b' ', True).strip().ljust(8, b'\0'))))
#p.interactive()