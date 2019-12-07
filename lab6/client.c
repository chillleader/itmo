#include <errno.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/socket.h>

#define PORT 2282
#define BUFFER_SIZE 1024

int main(int argc, char **argv) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s {hostname} {directories}...\n", argv[0]);
        return EXIT_FAILURE;
    }

    int socket_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_fd < 0) {
        perror("socket");
        return EXIT_FAILURE;
    }

    struct hostent *srv_address = gethostbyname(argv[1]);
    if (!srv_address) {
        perror("gethostbyname");
        return EXIT_FAILURE;
    }

    struct sockaddr_in socket;
    memset(&socket, 0, sizeof(socket));

    socket.sin_family = AF_INET;
    memcpy((char *) srv_address->h_addr_list[0],
           (char *) &socket.sin_addr.s_addr,
           srv_address->h_length);
    socket.sin_port = htons(PORT);

    if (connect(socket_fd, (struct sockaddr *) &socket, sizeof(struct sockaddr)) < 0) {
        perror("connect");
        return EXIT_FAILURE;
    }

    ssize_t buf_len;
    char buf[BUFFER_SIZE];
    write(socket_fd, argv[2], strlen(argv[2]));
    for (int i = 3; i < argc; i++) {
        write(socket_fd, "\n", 1);
        write(socket_fd, argv[i], strlen(argv[i]));
    }
    write(socket_fd, "\0", 1);

    int iter = 0;
    while ((buf_len = read(socket_fd, buf, BUFFER_SIZE)) > 0) {
        write(STDOUT_FILENO, buf, buf_len);
        ++iter;
    }
    if (buf_len < 0) {
        perror("read");
    }
    free(buf);

    return EXIT_SUCCESS;
}