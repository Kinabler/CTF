FROM ubuntu:latest


RUN apt-get update && \
    apt-get install -y socat libc6 libc6-i386 && \
    apt-get clean

RUN useradd -md /home/suy suy && \
    echo "suy:suyvl" | chpasswd

WORKDIR /challenge
COPY ./bai3_patched .
COPY ./flag.txt .
COPY ./ld-linux-x86-64.so.2 .
COPY ./libc.so.6 .

RUN chmod 755 /challenge

RUN chmod a+x ./bai3_patched

USER suy

CMD socat -T 300 -d -d TCP-LISTEN:20003,reuseaddr,fork EXEC:"./bai3_patched",pty,raw,echo=0

