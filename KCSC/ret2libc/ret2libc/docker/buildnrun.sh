#!/bin/sh

sudo apt install -y docker.io
sudo docker build . -t ret2libc && sudo docker run --rm -p 10001:10001 -it ret2libc