#include <stdio.h>
#include <unistd.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>

#include "lib/stats.h"

int mem_id;
application_stats *stats;

void deinit() {
    shmctl(mem_id, IPC_RMID, NULL);
    free(stats);
}

void sighandler(int signo) {
    printf("SIG! %d", signo);
    fflush(stdout);

    deinit();

    exit(signo);
}

bool init() {
    init_stats();

    mem_id = shmget(MEMORY_KEY, sizeof(application_stats), IPC_CREAT | 0644);
    if (mem_id < 0) {
        perror("shmget");
        return false;
    }

    stats = (struct application_stats *) shmat(mem_id, NULL, 0);
    if (stats == NULL) {
        perror("shmat");
        return false;
    }

    struct sigaction *act = calloc(1, sizeof(struct sigaction));
    act->sa_handler = sighandler;
    sigaction(SIGINT, act, NULL);

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
