#ifndef LAB5_LOAD_AVG_H
#define LAB5_LOAD_AVG_H

#include <stdbool.h>

typedef struct {
    double avg_1min;
    double avg_5min;
    double avg_15min;
} system_load;

bool open_load_avg_file();

void close_load_avg_file();

bool get_load_avg(system_load *ptr);

#endif
