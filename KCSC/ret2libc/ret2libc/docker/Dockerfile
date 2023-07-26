FROM ubuntu:22.04

RUN apt update && apt install -y socat && apt clean && useradd -m user
ADD share/ /home/user/

WORKDIR /home/user
RUN chmod +x * && chmod 444 flag.txt

USER user
EXPOSE 10001

CMD ["/home/user/run.sh"]