#include <stdio.h>
#include <sys/types.h>
#include <pthread.h>
#include <unistd.h>

#include "lib/alphabet_utils.h"

#define DELAY 1000*1000
int sem_init[] = {0, 0, 0};

#define ALPHABET_SIZE 26
char alphabet[ALPHABET_SIZE] = "abcdefghijklmnopqrstuvwxyz";

#define THREADS_AMOUNT 2
pthread_t t[THREADS_AMOUNT];
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

void print_alphabet(void) {
    int i;
    for (i = 0; i < ALPHABET_SIZE; i++) {
        printf("%c", alphabet[i]);
    }
    printf("\n");
}

void *reverse_alphabet(void *d) {
    while (1) {
        pthread_mutex_lock(&mutex);
        simple_reverse_alphabet(alphabet, ALPHABET_SIZE);
        pthread_mutex_unlock(&mutex);
        usleep(DELAY);
    }
}

void *switch_case(void *d) {
    while (1) {
        pthread_mutex_lock(&mutex);
        simple_switch_case(alphabet, ALPHABET_SIZE);
        pthread_mutex_unlock(&mutex);
        usleep(DELAY);
    }
}

int main(void) {
    pthread_create(&t[0], NULL, reverse_alphabet, NULL);
    pthread_create(&t[1], NULL, switch_case, NULL);

    while (1) {
        pthread_mutex_lock(&mutex);
        print_alphabet();
        pthread_mutex_unlock(&mutex);
        usleep(DELAY);
    }
}
