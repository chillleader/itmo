#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/mman.h>

#include "lib/stats.h"

int fd;
application_stats *stats;

void deinit() {
    close(fd);
    free(stats);
}

void sighandler(int signo) {
    printf("SIG! %d", signo);
    fflush(stdout);


    deinit();

    exit(signo);
}

bool init() {
    stats = calloc(1, sizeof(application_stats));

    init_stats();

    struct sigaction *act = calloc(1, sizeof(struct sigaction));
    act->sa_handler = sighandler;
    sigaction(SIGINT, act, NULL);

    unlink(TMP_FILE);

    fd = open(TMP_FILE, O_RDWR | O_CREAT, 0644);
    if (fd < 0) {
        perror(TMP_FILE);
        return -1;
    }

    stats = (application_stats *) mmap(NULL, sizeof(application_stats), PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (stats == MAP_FAILED) {
        perror("mmap");
        return false;
    }
    truncate(TMP_FILE, sizeof(application_stats));

    return true;
}

int main() {
    if (!init()) {
        return -1;
    }

    update_stats(stats);
    while (1) {
        update_time(stats);
        sleep(1);
    }
}
