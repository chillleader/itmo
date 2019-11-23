#include <stdio.h>
#include <unistd.h>
#include <sys/un.h>
#include <sys/socket.h>
#include <fcntl.h>          /* fcntl */

#include "lib/model.h"
#include "lib/stats.h"

application_stats stats;

int main(void) {
    struct sockaddr_un addr;
    int fd, cl, flags;

    init_stats();
    update_stats(&stats);

    unlink(TMP_FILE);
    if ((fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
        perror("socket");
        return -1;
    }

    /* setup socket */
    memset(&addr, 0, sizeof(addr));
    addr.sun_family = AF_UNIX;
    strcpy(addr.sun_path, TMP_FILE);

    /* bind socket */
    if (bind(fd, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
        perror("bind");
        return -2;
    }

    /* set non-blocking flag */
    flags = fcntl(fd, F_GETFL, 0);
    fcntl(fd, F_SETFL, flags | O_NONBLOCK);

    /* set listener */
    if (listen(fd, 5) == -1) {
        perror("listen");
        return -4;
    }

    while (1) {
        update_time(&stats);

        if ((cl = accept(fd, NULL, NULL)) != -1) {
            write(cl, &stats, sizeof(stats));
        }
        sleep(1);
    }

    deinit_stats();
}
