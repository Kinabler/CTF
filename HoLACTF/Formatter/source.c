#include<stdio.h>
#include<stdlib.h>

void toUpper(char *str) {
    int i = 0;
    while(str[i] != '\n' && str[i] != '\0') {
        if(str[i] >= 'a' && str[i] <= 'z') {
        str[i] -= 32;
        }
      i++;
    }
}

int main(){
    char str[32];
    while(1){
        printf("\n********************************\n");
        printf("@   Formatter go broooouuuu!!! @\n");
        printf("********************************\n");
        printf("Enter text to convert: \n");
        read(0, str, 32);
        str[31] = 0x0;
        if(strlen(str) == 30){
            printf("Your name so long\n");
            printf("Your name: ");
            printf(str);
            printf("\n");
        }else{
            toUpper(str);
            printf("\nYour name after converted: %s\n", str);
        }

    }
    return 0;
}


