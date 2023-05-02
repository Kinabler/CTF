FROM ubuntu:latest


RUN apt-get update && \
    apt-get install -y socat && \
    apt-get clean

RUN useradd -md /home/suy suy && \
    echo "suy:suyvl" | chpasswd

WORKDIR /challenge
COPY ./bai1 .
COPY ./flag.txt .

RUN chmod 755 /challenge
RUN chmod a+x ./bai1


USER suy

CMD socat -T 300 -d -d TCP-LISTEN:20001,reuseaddr,fork EXEC:"./bai1",pty,raw,echo=0

