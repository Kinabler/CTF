// gcc -o bai4 -fno-stack-protector bai4.c
// need ubuntu 22.04 libc

#include <stdio.h>
#include <unistd.h>

int main() {
  char password[0x30];
  long long *ptr = (long long)&password;
  ptr[0] = (long long)&puts;
  puts("Wellcome to BKSEC! What is the password?");
  scanf("%[^\n]s", password);
  printf("Oke, %s is not the correct password!\n", password);
  puts("You have one more change to input the password: ");
  scanf("%s", password);
  printf("Wow, %s is such a bad password!\n", password);
  puts("You think you can hack our system? Goodbye!");
  return 0;
}

__attribute__((constructor))
void setup(void) {
  setbuf(stdin, NULL);
  setbuf(stdout, NULL);
  alarm(180);
}
