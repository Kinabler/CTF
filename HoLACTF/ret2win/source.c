#include<stdio.h>
#include<string.h>
#include<stdlib.h>
int success(){
    asm("mov $0x0, %ebp");
    puts("Well!! Next challenge >>>");
    return system("sh");
}

void main(){
    char buffer[32];
    printf("########################\n");
    printf("#                      #\n");
    printf("#       EHC bank       #\n");
    printf("#                      #\n");
    printf("************************\n\n");
    printf("Give me your name to login: \n");
    read(0,buffer,0x48);
    puts("Thank you!!");
    return 0;
}