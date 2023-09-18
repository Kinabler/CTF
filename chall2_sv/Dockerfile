FROM ubuntu:22.04


RUN apt-get update && \
    apt-get install -y socat && \
    apt-get clean

RUN useradd -md /home/suy suy && \
    echo "suy:suyvl" | chpasswd

WORKDIR /challenge
COPY ./chall2 .
COPY ./flag.txt .

RUN chmod 755 /challenge
RUN chmod a+x ./chall2


USER suy

CMD socat -T 300 -d -d TCP-LISTEN:2002,reuseaddr,fork EXEC:"./chall2",pty,raw,echo=0