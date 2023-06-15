#include<stdio.h>
#include<math.h>
int money = 2;
void print_menu(){
    printf("1. Let's play poker.\n");
    printf("2. Rechange money.\n");
    printf("3. Withdraw all money.\n");
    printf("4. You don't want to play with me!\n > ");
}
void option1(){
    printf("The one who doesn't play is the winner!!!\n\n\n");
    return 0;
}
void option2(){
    unsigned int rechange;
    printf("Now, your money is %d$\n", money);
    printf("Enter the amount you want to rechange: \n > ");
    scanf("%d", &rechange);
    money += abs(rechange);
    printf("Now, your money after rechange is %d$ \n\n", money);
}
void option3(){
    char choose;
    printf("Now, your money is %d$\n", money);
    printf("Do you want to withdraw all? \(Y/N): ");
    scanf(" %c", &choose);
    switch(choose){
        case 'y':
        case 'Y':
            if(money == 0xdeadbeef){
                noor_hux();
            }
            else{
                money = 0;
                printf("Now, Your money is 0$ \n\n");
            }
            break;
        case 'N':
        case 'n':
            printf("\n\n");
            return 0;
        default:
            printf("Please, Input Y or N !!!\n\n");
    }
}
void noor_hux(){
    FILE *fp = fopen("flag.txt", "r");
    if (fp == NULL) {
        printf("Create flag, please\n\n");
        return 1;
    }
    char ch;
    while ((ch = fgetc(fp)) != EOF) {
        printf("%c", ch);
    }
    printf("\n\n");
    fclose(fp);
    exit(0);
}
int main(){
    int choose;
    printf("Welcome my EHC Poker Club. I will receive %d$ to play.\n", money);
    printf("Do you want to play a game of poker with me?\n");
    while(1){
        print_menu();
        scanf("%d", &choose);
        switch(choose){
        case 1:
            option1();
            break;
        case 2:
            option2();
            break;
        
        case 3:
            option3();
            break;
        
        case 4:
            exit(0);
        default:
            exit(0);
        }
    }
    return 0;
}