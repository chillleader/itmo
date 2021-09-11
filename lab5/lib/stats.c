#include <time.h>
#include <unistd.h>
#include "stats.h"
#include "load_avg.h"

time_t start_time;

void init_stats() {
    open_load_avg_file();
    start_time = time(NULL);
}

void deinit_stats() {
    close_load_avg_file();
}

void update_stats(application_stats *stats) {
    stats->pid = getpid();
    stats->uid = getuid();
    stats->gid = getgid();

    if (!get_load_avg(&stats->load)) {
        stats->load.avg_1min = stats->load.avg_5min = stats->load.avg_15min = -1.0;
    }

    update_time(stats);
}

void update_time(application_stats *stats) {
    stats->time_running = time(NULL) - start_time;
}