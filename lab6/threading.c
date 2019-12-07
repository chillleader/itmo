#include <errno.h>
#include <stdio.h>
#include <fcntl.h>
#include <dirent.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>

#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <sys/socket.h>

#include <netinet/in.h>

#include "threading.h"

void pool_add_worker(int *current_id, void (*worker)(void)) {
    int new_idx = 0;
    for (; new_idx < POOL_MAX_SIZE && pool->pool[new_idx].status != PROC_NONE; new_idx++);

    if (new_idx == POOL_MAX_SIZE) {
        fprintf(stderr, "POOL_MAX reached, cannot fork()\n");
        return;
    }

    struct sigaction default_action;
    memset(&default_action, 0, sizeof(struct sigaction));

    int new_pid = fork();
    switch (new_pid) {
        case -1:
            perror("fork");
            return;
        case 0:
            *current_id = new_idx;
            sigaction(SIGINT, &default_action, NULL);
            worker();
            fprintf(stderr, "[%3d] WARNING: should have called _exit(...)\n", new_idx);
            _exit(EXIT_FAILURE);
        default:
            pool->pool[new_idx].pid = new_pid;
            pool->pool[new_idx].status = PROC_FREE;
            pool->size++;
            pool->available++;
            fprintf(stderr, "%d (id %d) was born, pool size: %d, available: %d\n",
                    new_pid, new_idx, pool->size, pool->available);
    }
}