#ifndef THREADING_H
#define THREADING_H

#include <sys/param.h>

#define POOL_MIN_SIZE 64
#define POOL_MAX_SIZE 128

#define LEAST_AVAILABLE 2
#define MOST_AVAILABLE 4

typedef enum {
    PROC_NONE = 0,
    PROC_BUSY,
    PROC_FREE,
    PROC_ZOMBIE
} proc_status_t;

typedef struct {
    long uid;
    proc_status_t status;
} proc_msg;

typedef struct {
    pid_t pid;
    proc_status_t status;
} proc_state_info;

typedef struct {
    proc_state_info pool[POOL_MAX_SIZE];
    int size;
    int available;
} proc_pool;

proc_pool *pool;

void pool_add_worker(int *current_id, void (*worker)(void));

#endif //THREADING_H
