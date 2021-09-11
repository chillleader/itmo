#include <stdio.h>
#include <sys/un.h>
#include <sys/socket.h>
#include <unistd.h>
#include <string.h>

#include "lib/model.h"

int main(void) {
    application_stats stats;
    struct sockaddr_un addr;
    int fd;

    if ((fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
        perror("socket");
        return -1;
    }

    memset(&addr, 0, sizeof(addr));
    addr.sun_family = AF_UNIX;
    strcpy(addr.sun_path, TMP_FILE);

    if (connect(fd, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
        perror("connect");
        return -2;
    }

    if (read(fd, &stats, sizeof(stats)) != sizeof(stats)) {
        printf("Read error!\n");
        return -3;
    }

    printf("pid: %d; uid: %d; gid: %d;\n"
           "average load { 1 min: %.2lf; 5 min: %.2lf; 15 min: %.2lf }\n"
           "time running: %lds\n"
           "========================\n",
           stats.pid, stats.uid, stats.gid,
           stats.load.avg_1min, stats.load.avg_5min, stats.load.avg_15min,
           stats.time_running
    );
    close(fd);

    return 0;
}
