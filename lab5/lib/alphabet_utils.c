#include <stdio.h>

void simple_print_alphabet(char *alphabet, size_t alphabet_size) {
    for (size_t i = 0; i < alphabet_size; i++) {
        printf("%c", alphabet[i]);
    }
    printf("\n");
}

void simple_reverse_alphabet(char *alphabet, size_t alphabet_size) {
    for (size_t i = 0; i < alphabet_size / 2; i++) {
        char t = alphabet[i];
        alphabet[i] = alphabet[alphabet_size - i - 1];
        alphabet[alphabet_size - i - 1] = t;
    }
}

void simple_switch_case(char *alphabet, size_t alphabet_size) {
    for (size_t i = 0; i < alphabet_size; i++) {
        alphabet[i] += (alphabet[i] - 'A') < alphabet_size ? 32 : -32;
    }
}

size_t simple_count_uppercase(char *alphabet, size_t alphabet_size) {
    size_t k = 0;
    for (size_t i = 0; i < alphabet_size; i++) {
        if ('A' <= alphabet[i] && alphabet[i] <= 'Z') {
            k++;
        }
    }
    return k;
}