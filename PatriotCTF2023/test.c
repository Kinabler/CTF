#include<stdio.h>
int main(){
    // char flag[] = "pctf{simproc_r_optimal}";
    char test[] = "xk|nF{quxzwkgzgwx|quitH";
    int size = sizeof(flag);
    int i;
    char tmp, j;
    for(i = 0; i < 24; i++){
        for (j = 'A'; j <= '}'; j++) {
            tmp = j;
            tmp = (tmp + 65) - 122;
            if(tmp <= "@"){
                tmp += 61;
            }
            tmp = (tmp + 65) - 122;
            if(tmp <= "@"){
                tmp += 61;
            }
            if(tmp == test[i]){
                putchar(j);
                break;
            }
        }
    }
    putchar('\n');
    return 0;
}