#include <stdio.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/msg.h>

#include "lib/stats.h"

int msg_id;
message *msg;

void deinit() {
    free(msg);
}

void sighandler(int signo) {
    printf("SIG! %d", signo);
    fflush(stdout);

    msgctl(msg_id, IPC_RMID, NULL);

    deinit();

    exit(signo);
}

bool init() {
    msg = calloc(1, sizeof(message));

    init_stats();

    struct sigaction *act = calloc(1, sizeof(struct sigaction));
    act->sa_handler = sighandler;
    sigaction(SIGINT, act, NULL);

    msg_id = msgget(MEMORY_KEY, IPC_CREAT | 0666);
    if(msg_id < 0) {
        perror("msgget");
        return 1;
    }

    return true;
}

int main() {
    if(!init()) {
        return -1;
    }

    update_stats(&msg->stats);
    while(1) {
        msg->type++;
        update_time(&msg->stats);
        if(msgsnd(msg_id, msg, sizeof(message), 0) < 0) {
            perror("msgsnd");
        }
        sleep(1);
    }
}
