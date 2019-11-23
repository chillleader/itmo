#include <stdio.h>
#include <sys/msg.h>

#include "lib/model.h"

int main() {
    struct message msg;
    int msg_id;

    msg_id = msgget(MEMORY_KEY, IPC_CREAT | 0666);
    if(msg_id < 0) {
        perror("msgget");
        return 1;
    }

    if(msgrcv(msg_id, &msg, sizeof(message), 0, 0 < 0) < 0) {
        perror("msgrcv");
        return 1;
    }

    application_stats stats = msg.stats;
    printf("pid: %d; uid: %d; gid: %d;\n"
           "average load { 1 min: %.2lf; 5 min: %.2lf; 15 min: %.2lf }\n"
           "time running: %lds\n"
           "========================\n",
           stats.pid, stats.uid, stats.gid,
           stats.load.avg_1min, stats.load.avg_5min, stats.load.avg_15min,
           stats.time_running
    );
    fflush(stdout);

    return 0;
}