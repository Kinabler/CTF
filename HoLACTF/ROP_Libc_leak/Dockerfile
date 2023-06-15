FROM ubuntu:22.04


RUN apt-get update && \
    apt-get install -y socat && \
    apt-get clean

RUN useradd -md /home/suy suy && \
    echo "suy:suyvl" | chpasswd

WORKDIR /challenge
COPY ./source .
COPY ./flag.txt .

RUN chmod 755 /challenge
RUN chmod a+x ./source


USER suy

CMD socat -T 300 -d -d TCP-LISTEN:20003,reuseaddr,fork EXEC:"./source",pty,raw,echo=0