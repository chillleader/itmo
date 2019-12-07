#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>

#include "lib/model.h"
#include "lib/stats.h"

application_stats stats;

void sighandler(int signo) {
    switch (signo) {
        case SIGHUP:
            printf("%li\n", (long) stats.pid);
            break;
        case SIGINT:
            printf("%li\n", (long) stats.uid);
            break;
        case SIGTERM:
            printf("%li\n", (long) stats.gid);
            break;
        case SIGUSR1:
            printf("%li\n", (long) stats.time_running);
            break;
        case SIGUSR2:
            printf("%.2lf %.2lf %.2lf\n", stats.load.avg_1min, stats.load.avg_5min, stats.load.avg_15min);
            break;
        case SIGKILL:
            deinit_stats();
            _exit(0);
    }
}

int main(void) {
    struct sigaction *act;

    init_stats();
    update_stats(&stats);

    act = calloc(1, sizeof(struct sigaction));
    act->sa_handler = sighandler;
    sigaction(SIGHUP, act, NULL);
    sigaction(SIGINT, act, NULL);
    sigaction(SIGTERM, act, NULL);
    sigaction(SIGUSR1, act, NULL);
    sigaction(SIGUSR2, act, NULL);
    sigaction(SIGKILL, act, NULL);

    while (1) {
        update_time(&stats);
        sleep(1);
    }
}
