#include <stdio.h>
#include <sys/types.h>
#include <pthread.h>
#include <unistd.h>

#include "lib/alphabet_utils.h"

#define DELAY 1000*1000

#define ALPHABET_SIZE 26
char alphabet[ALPHABET_SIZE] = "abcdefghijklmnopqrstuvwxyz";

#define THREADS_AMOUNT 3
pthread_t t[THREADS_AMOUNT];
pthread_rwlock_t rwlock = PTHREAD_RWLOCK_INITIALIZER;

void *print_upcase_count(void *d) {
    while (1) {
        pthread_rwlock_wrlock(&rwlock);
        printf("%i\n", simple_count_uppercase(alphabet, ALPHABET_SIZE));
        pthread_rwlock_unlock(&rwlock);
        usleep(DELAY);
    }
}

void *reverse_alphabet(void *d) {
    while (1) {
        pthread_rwlock_rdlock(&rwlock);
        pthread_rwlock_wrlock(&rwlock);
        simple_reverse_alphabet(alphabet, ALPHABET_SIZE);
        pthread_rwlock_unlock(&rwlock);
        usleep(DELAY);
    }
}

void *switch_case(void *d) {
    while (1) {
        pthread_rwlock_rdlock(&rwlock);
        pthread_rwlock_wrlock(&rwlock);
        simple_switch_case(alphabet, ALPHABET_SIZE);
        pthread_rwlock_unlock(&rwlock);
        usleep(DELAY);
    }
}

int main(void) {
    pthread_create(&t[0], NULL, reverse_alphabet, NULL);
    pthread_create(&t[1], NULL, switch_case, NULL);
    pthread_create(&t[2], NULL, print_upcase_count, NULL);
    while (1) {
        pthread_rwlock_wrlock(&rwlock);
        simple_print_alphabet(alphabet, ALPHABET_SIZE);
        pthread_rwlock_unlock(&rwlock);
        usleep(DELAY);
    }
}
