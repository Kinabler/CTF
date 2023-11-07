FROM ubuntu:22.04 as chroot

RUN /usr/sbin/useradd --no-create-home -u 1000 user
RUN apt update -y
RUN apt install socat -y

#if libc/ld provided they are hooked with patchelf and coppied as well
COPY source /home/user/
COPY flag.txt /home/user/

USER user

EXPOSE 1337

CMD socat TCP-LISTEN:1337,reuseaddr,fork EXEC:"/home/user/source"