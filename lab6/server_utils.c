#include <errno.h>
#include <dirent.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>

#include "server_utils.h"

ssize_t write_const_chars(int fd, const char *const str) {
    return write(fd, str, sizeof(str));
}

void ls(int fd, char *path) {
    DIR *dir_ptr;
    dir_ptr = opendir(path);
    if (!dir_ptr) {
        write(fd, path, strlen(path));
        write_const_chars(fd, ":");

        char err_buf[256];
        if (strerror_r(errno, err_buf, 256) == 0) {
            write(fd, err_buf, strlen(err_buf));
            write(fd, (const void *) "\r\n", 2);
        } else {
            write_const_chars(fd, "unknown error\r\n");
        }

        return;
    }

    struct dirent *dire = (struct dirent *) malloc(sizeof(struct dirent) + NAME_MAX);
    if (!dire) {
        write_const_chars(fd, "Out of memory\r\n");
        return;
    }

    struct dirent *result;
    while (!(readdir_r(dir_ptr, dire, &result) == 0 && result == NULL)) {
        if (!result) {
            char err_buf[256];
            if (strerror_r(errno, err_buf, 256) == 0) {
                write(fd, err_buf, strlen(err_buf));
                write_const_chars(fd, "\r\n");
            } else {
                write_const_chars(fd, "unknown error\r\n");
            }
            continue;
        }

        size_t i = 0;
        while (dire->d_name[i] != '\0') {
            if (dire->d_name[i] == '\n' || dire->d_name[i] == '\r') {
                dire->d_name[i] = '?';
            }
            ++i;
        }

        write(fd, dire->d_name, i);
        write_const_chars(fd, "\r\n");
    }

    free(dire);
    closedir(dir_ptr);
}
