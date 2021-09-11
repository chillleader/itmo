#include <stdio.h>
#include <sys/shm.h>

#include "lib/model.h"

int main() {
    struct application_stats *stats;
    int mem_id;

    mem_id = shmget(MEMORY_KEY, sizeof(stats), 0);
    stats = (struct application_stats *) shmat(mem_id, NULL, 0);

    printf("pid: %d; uid: %d; gid: %d;\n"
           "average load { 1 min: %.2lf; 5 min: %.2lf; 15 min: %.2lf }\n"
           "time running: %lds\n"
           "========================\n",
           stats->pid, stats->uid, stats->gid,
           stats->load.avg_1min, stats->load.avg_5min, stats->load.avg_15min,
           stats->time_running
    );
    fflush(stdout);

    return 0;
}