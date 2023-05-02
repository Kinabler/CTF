
from pwn import *
check = "Combine the first"
r = remote("challs.actf.co", 31402)
for i in range(20):
    string = r.recv()

    print(string)
    first_three = string[31:34]
    last_three = string[-4:-1]
    result = first_three + last_three
    print(i, " : result: ", result)
    r.sendline(result)
r.interactive()