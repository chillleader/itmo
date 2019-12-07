CC=gcc
#CFLAGS=-std=c99 -D_XOPEN_SOURCE=600 -D_POSIX_C_SOURCE -Wall -Wextra -pedantic -g -lsocket -lnsl
CFLAGS=-std=c99 -D_XOPEN_SOURCE=600 -D_POSIX_C_SOURCE -Wall -c -Wextra

all: server client

server: server.o threading.o server_utils.o
	$(CC) $^ -o $@

client: client.o

%.o: %.c
	$(CC) $(CFLAGS) -c -lthread -D_XOPEN_SOURCE -o $@ $<

.PHONY: clean
clean:
	rm -f server.o threading.o server_utils.o client.o
	rm -f server client

