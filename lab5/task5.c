#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <pthread.h>
#include <unistd.h>

#include "lib/model.h"
#include "lib/alphabet_utils.h"

#define ALPHABET_SIZE 26
char alphabet[ALPHABET_SIZE] = "abcdefghijklmnopqrstuvwxyz";

#define THREADS_AMOUNT 2
pthread_t t[THREADS_AMOUNT];
key_t key = MEMORY_KEY;
int semid;

void print_alphabet(void) {
    int i;
    for (i = 0; i < ALPHABET_SIZE; i++) {
        printf("%c", alphabet[i]);
    }
    printf("\n");
}

void *reverse_alphabet(void *d) {
    struct sembuf lock = {0, -1, SEM_UNDO};
    struct sembuf unlock = {2, 1, SEM_UNDO};

    while (semop(semid, &lock, 1) != -1) {
        simple_reverse_alphabet(alphabet, ALPHABET_SIZE);
        semop(semid, &unlock, 1);
    }
    return NULL;
}

void *switch_case(void *d) {
    struct sembuf lock = {1, -1, SEM_UNDO};
    struct sembuf unlock = {2, 1, SEM_UNDO};
    while (semop(semid, &lock, 1) != -1) {
        simple_switch_case(alphabet, ALPHABET_SIZE);
        semop(semid, &unlock, 1);
    }
    return NULL;
}

int sem_init[] = {0, 0, 0};

int main() {
    struct sembuf buf = {0, 1, SEM_UNDO};
    struct sembuf sem_print = {2, -1, SEM_UNDO};

    key = MEMORY_KEY;
    semid = semget(key, 3, IPC_CREAT | 0600);
    semctl(semid, 0, GETALL, sem_init);

    pthread_create(&t[0], NULL, reverse_alphabet, NULL);
    pthread_create(&t[1], NULL, switch_case, NULL);
    while (1) {
        buf.sem_num = (buf.sem_num + 1) % THREADS_AMOUNT;
        semop(semid, &buf, 1);
        semop(semid, &sem_print, 1);
        print_alphabet();
        sleep(1);
    }
}
