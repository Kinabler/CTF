#include <stdio.h>

void setbufs();

int main() {
    setbufs();
    char name[40];
    printf("Enter your name: ");
    gets(name);
    printf("Your name is: %s", name);
    return 0;
}

void setbufs() {
    setbuf(stdin, NULL);
    setbuf(stdout, NULL);
    setbuf(stderr, NULL);
}

void win(){
    system("/bin/sh");
}