// gcc -o bai3 -fno-stack-protector bai3.c
// need ubuntu 22.04 libc

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main()
{
    char buf[32];
    int sz = 32;
    char buf2[32];
    printf("I will tell you a secret before you die: 0x%llx", (unsigned long long)&exit);
    printf("\nAny last word?\n");
    fflush(stdout);
    read(0, buf, 33);
    printf("OKE, ready for your death?\n");
    fflush(stdout);
    read(0, buf, sz);
    return 0;
}