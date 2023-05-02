// gcc -o bai1 -fstack-protector -pie bai1.c

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main()
{
    char buf[32];
    char flag[200];
    FILE *fp;
    fp = fopen("flag.txt", "r");
    if (fp == NULL)
    {
        exit(0);
    }
    fscanf(fp, "%s", flag);
    puts("Say something:");
    read(0, buf, 32);
    printf("\nYou said %s", buf);
    return 0;
}