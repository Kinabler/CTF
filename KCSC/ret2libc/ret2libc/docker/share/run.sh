#!/bin/sh

cd /home/user
socat tcp-listen:10001,fork,reuseaddr exec:./ret2libc