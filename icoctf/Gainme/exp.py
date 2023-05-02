#!/usr/bin/env python3
from pwn import *
r = remote('143.198.219.171',5003 )

a = r.recvuntil(b'Level 0:')
print(a)
r.sendline(b'ICTF4')
a=r.recvuntil(b'Level 1:')
print(a)
r.sendline(b'dasDASQWgjtrkodsc')
a=r.recvuntil(b'Level 2:')
print(a)
r.sendline(p32(0xDEADBEEF))
a=r.recvuntil(b'Level 3:')
print(a)
r.sendline(b'1')
r.interactive()