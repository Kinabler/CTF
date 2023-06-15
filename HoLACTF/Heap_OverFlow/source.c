#include<stdio.h>
#include<stdlib.h>

struct library{
    char name_of_book[64];
}library;

struct call_book{
    void (*call_book)();
    char __pad[64 - sizeof(unsigned long)];
}call;
__init(){
    setvbuf(stdin, NULL, _IONBF, 0);
    setvbuf(stdout, NULL, _IONBF, 0);
    setvbuf(stderr, NULL, _IONBF, 0);
}
__C(){
    puts("\n\nTo study C programming from zero to hero, you can follow these steps:\nStart by learning the basics of programming concepts such as variables, data types, control structures (if-else statements, loops), functions, and arrays.\nLearn about pointers, memory management, and dynamic memory allocation in C.\nMaster the concepts of file handling, input/output operations, and error handling in C.\nFamiliarize yourself with software development tools such as compilers, debuggers, and code editors.\nWork on projects that involve solving real-world problems using C programming language. This will help you to gain practical experience and improve your skills.\nStudy advanced topics such as data structures, algorithms, and multi-threading to become a proficient C programmer.\nKeep practicing and refining your knowledge through online courses, tutorials, coding challenges, and coding exercises.\nBy following these steps, you can gradually progress from being a beginner to a confident and skilled C programmer. \nGudbai!!!\n\n");
}
__Cpp(){
    puts("\n\nTo study C++ programming from zero to hero, you can follow these steps:\nStart by learning the basics of programming concepts such as variables, data types, control structures (if-else statements, loops), functions, and arrays.\nLearn about pointers, memory management, and dynamic memory allocation in C.\nMaster the concepts of file handling, input/output operations, and error handling in C.\nFamiliarize yourself with software development tools such as compilers, debuggers, and code editors.\nWork on projects that involve solving real-world problems using C programming language. This will help you to gain practical experience and improve your skills.\nStudy advanced topics such as data structures, algorithms, and multi-threading to become a proficient C programmer.\nKeep practicing and refining your knowledge through online courses, tutorials, coding challenges, and coding exercises.\nBy following these steps, you can gradually progress from being a beginner to a confident and skilled C programmer.\nGudbai!!!\n\n");    
}
__Java(){
    puts("\n\nTo study Java programming from zero to hero, you can follow these steps:\nStart by learning the basics of programming concepts such as variables, data types, control structures (if-else statements, loops), functions, and arrays.\nLearn about pointers, memory management, and dynamic memory allocation in C.\nMaster the concepts of file handling, input/output operations, and error handling in C.\nFamiliarize yourself with software development tools such as compilers, debuggers, and code editors.\nWork on projects that involve solving real-world problems using C programming language. This will help you to gain practical experience and improve your skills.\nStudy advanced topics such as data structures, algorithms, and multi-threading to become a proficient C programmer.\nKeep practicing and refining your knowledge through online courses, tutorials, coding challenges, and coding exercises.\nBy following these steps, you can gradually progress from being a beginner to a confident and skilled C programmer.\nGudbai!!!\n\n");    
}
__Error(){
    puts("I cannot find your book in my library\n");
}
void win(){
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
void process(){
    // __init();
    struct library *libc;
    struct call_book *call;
    char book4[32];
    int pos;
    libc = malloc(sizeof(struct library));
    call = malloc(sizeof(struct call_book));
    printf("[*]   Welcome to EHC's last challenge   [*]\n");
    printf("My name is kinabler, who is library manager in the challenge 3\n");
    printf("Can you help me find the hacker's book\n");
    
    printf("Now, EHC's library has 3 books: \n");
    printf("[1]  C from zero to hero     [1]\n");
    printf("[2]  C++ from zero to hero   [2]\n");
    printf("[3]  Java from zero to hero  [3]\n");
    printf("[4]  Find other books        [4]\n");
    printf("What book do you want to read?\nEnter your choose: ");
    scanf("%d", &pos);
    switch(pos){
        case 1:
            printf("The book's name is C from zero to hero\n");
            printf("The positive of this book is: %d\n", pos);
            call->call_book = __C;
            break;
        case 2:
            printf("The book's name is C++ from zero to hero\n");
            printf("The positive of this book is: %d", pos);
            call->call_book = __Cpp;
            break;
        case 3:
            printf("The book's name is Java from zero to hero\n");
            printf("The positive of this book is: %d", pos);
            call->call_book = __Java;
            break;
        case 4:
            char c = getchar();
            printf("Enter you book, that you want to read: \n");
            gets(book4);
            call->call_book = __Error;
            strcpy(libc->name_of_book, book4);
            break;
    }
    fflush(stdout);
    call->call_book();
    free(libc);
    free(call);
}

int main(){
    while(1){
        process();
    }
    puts("GUT BAI!!!");
    return 0;
}