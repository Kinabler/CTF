#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <stdlib.h>

void sigseg_handler()
{
	puts("Segmentation fault");
	exit(0);
}

void init()
{
	dup2(1, 2);
	setbuf(stdin, 0);
	setbuf(stdout, 0);
	setbuf(stderr, 0);
	signal(SIGSEGV, sigseg_handler);
}

int main()
{
	char buffer[0x20];

	init();

	memset(buffer, 0, sizeof(buffer));
	puts("-------------------");
	puts("| I'm unstoppable |");
	puts("-------------------");
	while (1)
	{
		if (fork() == 0)
		{
			printf("> ");
			read(0, buffer, 0x100);
			break;
		}
		else
			wait(NULL);
	}

	return 0;
}