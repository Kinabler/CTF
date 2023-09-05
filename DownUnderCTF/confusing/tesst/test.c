#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void init() {
    setvbuf(stdout, 0, 2, 0);
    setvbuf(stdin, 0, 2, 0);
}

int main() {
    char s[4];
    printf("Give me s: ");
    scanf("%d", &s);
    printf("Inputed strings: %s\n", s);
}
