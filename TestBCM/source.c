#include <stdio.h>
#include <unistd.h>

int main(){
    dont_care_function();
    puts("Welcome to EHC offical !!!!");
    puts("I have a gift for you !!!");
    sleep(1);
    puts("Wait me for 5 seconds....");
    sleep(5);
    clear_monitor();
    vuln();
    return 0;
}

void dont_care_function(){
    setbuf(stdin, 0x0);
    setbuf(stdout, 0x0);
    setbuf(stderr, 0x0);
}

void clear_monitor(){
    //Clear all text in screen
    printf("\x1B[2J");
    return printf("\x1B[%d;%dH", 0LL, 0LL);

}

void vuln(){
    char name[0x30];
    int EHCcheck = 0x2003;
    //Set name string is 0;
    memset(name, 0, sizeof(name));
    puts("Enter your name: ");
    read(0x0, name, 0x40);
    //Set last of string to \x00 bytes
    name[sizeof(name)] = 0x0;
    if(EHCcheck == 0x31383031){
        puts("You have a card to pass EHC");
        puts("Take it in ticket file if you see this message!");
        return system("/bin/sh");
    }else{
        return puts("Thank you your information \n\t[*] My gift will you sent to you soon [*]");
    }
}