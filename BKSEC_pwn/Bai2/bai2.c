// gcc -o bai2 -no-pie -fno-stack-protector bai2.c

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

void PrintFlags()
{
    char flag[200];
    FILE *fp;
    fp = fopen("flag.txt", "r");
    if (fp == NULL)
    {
        exit(0);
    }
    if(fgets(flag, 28, fp) == NULL) exit(0);
    flag[27] = 0;
    puts(flag);
}

int main()
{
    char buf[32];
    puts("Say something:");
    int sz = 64;
    read(0, buf, sz);
    return 0;
}