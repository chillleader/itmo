#include <errno.h>
#include <stdio.h>
#include <fcntl.h>
#include <dirent.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>

#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/msg.h>
#include <sys/types.h>
#include <sys/socket.h>

#include <netinet/in.h>
#include <stdbool.h>

#include "server_utils.h"
#include "threading.h"

bool is_working = true;

int current_id = -1;
int socket_fd;
int shm_id;
int msg_id;

void signal_handler(int signo) {
    fprintf(stderr, "\rInterrupted.\n");
    is_working = false;

    close(socket_fd);
    shmctl(shm_id, IPC_RMID, NULL);
    msgctl(msg_id, IPC_RMID, NULL);

    _exit(EXIT_SUCCESS);
}

void worker() {
    int client_fd;

    struct sockaddr_in client;
    socklen_t client_length = sizeof(client);

    proc_msg msg;
    msg.uid = current_id + 1;

    for (;;) {
        if (pool->pool[current_id].status == PROC_ZOMBIE) {
            msg.status = PROC_NONE;
            msgsnd(msg_id, &msg, sizeof(msg), 0);
            break;
        }

        client_fd = accept(socket_fd, (struct sockaddr *) &client, &client_length);
        if (client_fd < 0) {
            if (errno == EAGAIN) {
                continue;
            }
            perror("accept");
        }

        msg.status = PROC_BUSY;
        msgsnd(msg_id, &msg, sizeof(msg), 0);

        ssize_t buf_len;
        char buf[BUFFER_SIZE];
        while ((buf_len = read(client_fd, buf, BUFFER_SIZE)) > 0) {
            if (buf_len < 0) {
                if (errno == EAGAIN) {
                    continue;
                }

                perror("read");
                goto finalize;
            }
            if (buf_len == 0) {
                goto finalize;
            }
        }

        char *dir = strtok(buf, "\n");
        while (dir != NULL) {
            ls(client_fd, dir);
            dir = strtok(NULL, "\n");
        }
        free(dir);
        free(buf);

        finalize:
        close(client_fd);
        msg.status = PROC_FREE;
        msgsnd(msg_id, &msg, sizeof(msg), 0);
    }

    _exit(EXIT_SUCCESS);
}

int main(int argc, char **argv) {
    socket_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_fd < 0) {
        perror("socket");
        return EXIT_FAILURE;
    }

    struct sockaddr_in srv_socket;
    srv_socket.sin_family = AF_INET;
    srv_socket.sin_addr.s_addr = INADDR_ANY;
    srv_socket.sin_port = htons(PORT);

    if (bind(socket_fd, (struct sockaddr *) &srv_socket, sizeof(srv_socket)) < 0) {
        perror("bind");
        return EXIT_FAILURE;
    }

    int flags = fcntl(socket_fd, F_GETFL, 0);
    fcntl(socket_fd, F_SETFL, flags | O_NONBLOCK);

    listen(socket_fd, QUEUE_LENGTH);

    msg_id = msgget(IPC_PRIVATE, IPC_CREAT | 0600);
    if (msg_id < 0) {
        perror("msgget");
        return EXIT_FAILURE;
    }

    shm_id = shmget(IPC_PRIVATE, sizeof(proc_pool), IPC_CREAT | 0600);
    if (shm_id < 0) {
        perror("shmget");
        return EXIT_FAILURE;
    }

    pool = (proc_pool *) shmat(shm_id, NULL, 0);
    if (!pool) {
        perror("shmat");
        return EXIT_FAILURE;
    }
    memset(pool, 0, sizeof(proc_pool));

    struct sigaction action;
    memset(&action, 0, sizeof(struct sigaction));
    action.sa_handler = &signal_handler;
    if (sigaction(SIGINT, &action, NULL) || sigaction(SIGTERM, &action, NULL)) {
        perror("sigaction");
        return EXIT_FAILURE;
    }

    fprintf(stderr, "Server initialized\n");

    for (int i = 0; i < LEAST_AVAILABLE; i++) {
        pool_add_worker(&current_id, worker);
    }

    while (is_working) {
        proc_msg msg;
        msgrcv(msg_id, &msg, sizeof(proc_msg), 0, 0);
        msg.uid--;

        switch (msg.status) {
            case PROC_FREE:
                pool->pool[msg.uid].status = msg.status;
                pool->available++;
                fprintf(stderr, "[%3ld] is free, pool size: %d, available: %d\n", msg.uid, pool->size, pool->available);
                break;
            case PROC_BUSY:
                pool->pool[msg.uid].status = msg.status;
                pool->available--;
                fprintf(stderr, "[%3ld] is busy, pool size: %d, available: %d\n", msg.uid, pool->size, pool->available);
                break;
            case PROC_NONE:
                pool->pool[msg.uid].status = msg.status;
                pool->size--;
                fprintf(stderr, "[%3ld] is dead, pool size: %d, available: %d\n", msg.uid, pool->size, pool->available);
                break;
            default:
                break;
        }

        if (pool->available < LEAST_AVAILABLE) {
            pool_add_worker(&current_id, worker);
        }

        if (pool->available > MOST_AVAILABLE) {
            for (int i = 0; i < POOL_MAX_SIZE; i++) {
                if (pool->pool[i].status == PROC_FREE) {
                    pool->pool[i].status = PROC_ZOMBIE;
                    pool->available--;

                    fprintf(stderr, "[%3d] scheduled for death\n", i);
                }

                if (pool->available == MOST_AVAILABLE) {
                    break;
                }
            }
        }
    }

    return EXIT_SUCCESS;
}