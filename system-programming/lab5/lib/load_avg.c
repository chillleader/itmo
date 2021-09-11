#include <stdio.h>
#include "load_avg.h"
#include <memory.h>
#include <stdlib.h>

bool is_open = false;
FILE *load_file;

bool open_load_avg_file() {
    load_file = fopen("/proc/loadavg", "r");
    is_open = (load_file != NULL);
    return is_open;
}

void close_load_avg_file() {
    if (is_open) {
        fclose(load_file);
    }
}

void reset_load_avg_file() {
    if (is_open) {
        rewind(load_file);
    }
}

bool get_load_avg(system_load *ptr) {
    if (!is_open) {
        return false;
    }

    fscanf(load_file, "%lf %lf %lf", &ptr->avg_1min, &ptr->avg_5min, &ptr->avg_15min);
    reset_load_avg_file();
    return true;
}