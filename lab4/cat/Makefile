CC = gcc
CFLAGS = -std=c99 -Wall -Wextra -pedantic -g
#CFLAGS		=-std=c99 -g

TARGET = cat_michael

SRC = cat.c
OBJ = $(SRC:%.c=%.o)

$(TARGET): $(OBJ)
	$(CC) -o $(TARGET) $(OBJ)

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

.PHONY: clean
clean:
	rm -f $(OBJ) $(TARGET)
