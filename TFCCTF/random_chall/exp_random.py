from pwn import *

elf = ELF("./random")

r = remote("challs.tfcctf.com", 32345)
payload = process("./mimic").read()
print(payload)
r.sendline(payload)
r.interactive()