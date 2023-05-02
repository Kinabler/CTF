FROM ubuntu:latest


RUN apt-get update && \
    apt-get install -y socat && \
    apt-get clean

RUN useradd -md /home/suy suy && \
    echo "suy:suyvl" | chpasswd

WORKDIR /challenge
COPY ./bai2 .
COPY ./flag.txt .

RUN chmod +x /challenge

RUN chmod +x ./bai2

USER suy

CMD socat -T 300 -d -d TCP-LISTEN:20002,reuseaddr,fork EXEC:"./bai2",pty,raw,echo=0

