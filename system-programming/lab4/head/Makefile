CC=cc -Wall -Wextra --std=gnu99 -pedantic -Werror

all: head

.PHONY: head
head: head.o
	$(CC) -o $@ head.o

head.o: head.c
	$(CC) -c -o $@ head.c