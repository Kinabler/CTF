#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
	setbuf(stdout, NULL);
	srand(time(0));
	for(int i = 0; i < 10; i++)
	{
		printf("%d\n", rand());
	}

}