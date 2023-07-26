int run()
{
  int result; // eax
  unsigned int v1; // ebx
  _QWORD v2[20]; // [rsp+0h] [rbp-1C0h] BYREF
  char s[256]; // [rsp+A0h] [rbp-120h] BYREF
  int choose; // [rsp+1A0h] [rbp-20h]
  unsigned int j; // [rsp+1A4h] [rbp-1Ch]
  unsigned int i; // [rsp+1A8h] [rbp-18h]
  int v7; // [rsp+1ACh] [rbp-14h]
  char *v8; // [rsp+1B0h] [rbp-10h] BYREF

  v7 = 0;
  result = (unsigned int)memset(s, 0, sizeof(s));
  while ( !v7 )                                 // v7 == 0
  {
    menu();
    __isoc99_scanf("%d");
    remove_newline();
    result = choose;
    if ( choose == 3 )
    {
      v7 = 1;
    }
    else
    {
      if ( choose > 3 )
        goto LABEL_18;
      if ( choose == 1 )
      {
        if ( racer_num > 9 )
        {
          result = puts("Maximum 10 racers are allowed!");
        }
        else
        {
          v1 = racer_num;
          (&racers)[v1] = (char *)malloc(256uLL);
          printf("Name for new racer: ");
          __isoc99_scanf("%256s");
          remove_newline();
          result = ++racer_num;
        }
      }
      else if ( choose == 2 )
      {
        memset(s, 0, sizeof(s));
        for ( i = 0; i < racer_num; ++i )
        {
          v2[2 * i] = (&racers)[i];
          (&v8)[2 * i - 53] = s;
          pthread_create(&thread_id[i], 0LL, race, &v2[2 * i]);
        }
        for ( j = 0; j < racer_num; ++j )
          pthread_join(thread_id[j], 0LL);
        result = printf("Our winner: %s\n", s);
      }
      else
      {
LABEL_18:
        result = puts("Invalid choice!");
      }
    }
  }
  return result;
}