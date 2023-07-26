#!/bin/sh

gcc solution/ret2libc.c -o ret2libc
cp ret2libc docker/share