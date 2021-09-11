#include <stdio.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>

#include "lib/alphabet_utils.h"

#define ALPHABET_LENGTH 26
char alphabet[ALPHABET_LENGTH] = "abcdefghijklmnopqrstuvwxyz";

#define THREADS_AMOUNT 3
pthread_t t[THREADS_AMOUNT];
sem_t sem[THREADS_AMOUNT];

void *reverse_alphabet(void *d) {
    while (sem_wait(&sem[0]) == 0) {
        simple_reverse_alphabet(alphabet, ALPHABET_LENGTH);
        sem_post(&sem[2]);
    }
    return NULL;
}

void *switch_case(void *d) {
    while (sem_wait(&sem[1]) == 0) {
        simple_switch_case(alphabet, ALPHABET_LENGTH);
        sem_post(&sem[2]);
    }
    return NULL;
}

int main(void) {
    int i;

    for (i = 0; i < THREADS_AMOUNT; i++) {
        if (sem_init(&sem[i], 0, 0) == -1) {
            perror("sem_init");
            return -1;
        }
    }

    pthread_create(&t[0], NULL, reverse_alphabet, NULL);
    pthread_create(&t[1], NULL, switch_case, NULL);

    while (1) {
        i = (i + 1) % (THREADS_AMOUNT - 1);

        sem_post(&sem[i]);
        sem_wait(&sem[2]);

        simple_print_alphabet(alphabet, ALPHABET_LENGTH);

        sleep(1);
    }
}
