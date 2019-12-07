#ifndef SERVER_UTILS_H
#define SERVER_UTILS_H

#include <sys/param.h>

#define PORT 2282
#define QUEUE_LENGTH 4
#define BUFFER_SIZE 1024
#define NAME_MAX 4096

ssize_t write_const_chars(int fd, const char *str);

void ls(int fd, char *path);

#endif // SERVER_UTILS_H
