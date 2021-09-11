#ifndef LAB5_MODEL_H
#define LAB5_MODEL_H

#include <sys/param.h>
#include "load_avg.h"

#define MEMORY_KEY 70
#define TMP_FILE "/tmp/s244701.tmp"

typedef struct application_stats {
    pid_t pid;
    uid_t uid;
    gid_t gid;
    time_t time_running;
    system_load load;
} application_stats;


typedef struct message {
    long type;
    struct application_stats stats;
} message;

#endif
