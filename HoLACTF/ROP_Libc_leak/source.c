#include<stdio.h>
#include <stdlib.h>
void printMenu(){
    puts("============================================\n");
    puts("\n[*] \t Welcome to EHC's library \t [*]\n\n");
    puts("I have a E-book. If you can read it, you can become a \"hac co\" :> \n");
    puts("\tBut I remember that I kept it very tight\n\n");
    puts("Now, my library only a few of book that you can read. :)\n");
    puts("What e-book do you want to read?\n");
    puts("1. Trang Giang - Nguyen Du. \n");
    puts("2. Luc Van Tien - Nguyen Dinh Chieu. \n");
    puts("3. Chien la cuoi cung - Xuan Quynh. \n");
    puts("4. Other\n");
}
void book(){
    int choose;
    printf("Your option: ");
    scanf("%d", &choose);
    switch(choose){
        case 1: 
            trang_giang();
            break;
        case 2:
            luc_van_tien();
            break;
        case 3:
            chiec_la_cuoi_cung();
            break;
        case 4:
            other();
            break;
        default:
            puts("Please input your choose again !!!");
    }
}
void trang_giang(){
    puts("Trang Giang nuoc non dep tuyet voi, \nSong ngan luu chay, nui xanh ngut ngan.\nDiem trang bao la canh quan huu tinh,\nTho ca nhan ai khong mo uoc duoc.\n\n");
}
void luc_van_tien(){
    puts("Find book in here: https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUJcmljayByb2xs\n");
}
void chiec_la_cuoi_cung(){
    puts("Find book in here: https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUJcmljayByb2xs\n");
}
void no_winner(){
    system("cat flag.txt > /dev/null");
}
void other(){
    char c;
    while ((c = getchar()) != '\n' && c != EOF) {}
    char book[0x28];
    printf("Enter, the book, that you want to read \n >");
    read(0, book, 0x100);
    puts("I can not find book: ");
    printf(book);
    puts("See you again.!!!\n\n");
    return 0;
}

int main(){
    setvbuf(stdin, NULL, _IONBF, 0);
    setvbuf(stdout, NULL, _IONBF, 0);
    setvbuf(stderr, NULL, _IONBF, 0);
    while(1){
        printMenu();
        book();
    }
    exit(0);
}