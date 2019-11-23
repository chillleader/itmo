CC=gcc
CFLAGS=-Wall -Werror -c -Wextra -std=c99

all: part1 part2 part3

part1: task1 task2 task3

part2: task4 task5 task6 task7

part3: task8 task9 task10

task1: task1_server task1_client

task1_server: task1_server.o lib/stats.o lib/load_avg.o
	$(CC) $^ -o $@

task1_client: task1_client.o
	$(CC) $^ -o $@

task2: task2_server task2_client

task2_server: task2_server.o lib/stats.o lib/load_avg.o
	$(CC) $^ -o $@

task2_client: task2_client.o
	$(CC) $^ -o $@

task3: task3_server task3_client

task3_server: task3_server.o lib/stats.o lib/load_avg.o
	$(CC) $^ -o $@

task3_client: task3_client.o
	$(CC) $^ -o $@

task4: task4.o lib/alphabet_utils.o
	$(CC) $^ -lrt -lpthread -o $@

task5: task5.o lib/alphabet_utils.o
	$(CC) $^ -lpthread -o $@

task6: task6.o lib/alphabet_utils.o
	$(CC) $^ -lpthread -o $@

task7: task7.o lib/alphabet_utils.o
	$(CC) $^ -lpthread -o $@

task8: task8_server task8_client

task8_server: task8_server.o lib/stats.o lib/load_avg.o
	$(CC) $^ -lsocket -lnsl -o $@

task8_client: task8_client.o
	$(CC) $^ -lnsl -lsocket -o $@

task9: task9_server

task9_server: task9_server.o lib/stats.o lib/load_avg.o
	$(CC) $^ -o $@

task10: task10.o
	$(CC) $^ -o $@

%.o: %.c
	$(CC) $(CFLAGS) -c -lthread -D_XOPEN_SOURCE -o $@ $<

.PHONY: clean
clean:
	rm -f *.o task{1..10}{_client,_server,''}
	rm -f lib/*.o