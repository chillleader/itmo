#include <stdio.h>
#include <sys/msg.h>
#include <sys/mman.h>
#include <fcntl.h>

#include "lib/model.h"

int main() {
    int fd;
    application_stats *stats;

    fd = open(TMP_FILE, O_RDWR);
    if (fd < 0) {
        perror(TMP_FILE);
        return -1;
    }

    stats = (application_stats *) mmap(NULL, sizeof(application_stats), PROT_READ, MAP_SHARED, fd, 0);
    if (stats == MAP_FAILED) {
        perror("mmap");
        return -2;
    }

    printf("pid: %d; uid: %d; gid: %d;\n"
           "average load { 1 min: %.2lf; 5 min: %.2lf; 15 min: %.2lf }\n"
           "time running: %lds\n"
           "========================\n",
           stats->pid, stats->uid, stats->gid,
           stats->load.avg_1min, stats->load.avg_5min, stats->load.avg_15min,
           stats->time_running
    );;
    fflush(stdout);

    munmap(stats, sizeof(application_stats));

    return 0;
}