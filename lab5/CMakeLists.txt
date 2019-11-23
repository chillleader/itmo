cmake_minimum_required(VERSION 3.15)
project(lab5 C)

set(CMAKE_C_STANDARD 99)

add_executable(task1_server task1_server.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)
add_executable(task1_client task1_client.c lib/model.h)

add_executable(task2_server task2_server.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)
add_executable(task2_client task2_client lib/model.h)

add_executable(task3_server task3_server.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)
add_executable(task3_client task3_client lib/model.h)

add_executable(task4 task4.c lib/alphabet_utils.c)
add_executable(task5 task5.c lib/alphabet_utils.c)
add_executable(task6 task6.c lib/alphabet_utils.c)
add_executable(task7 task7.c lib/alphabet_utils.c)

add_executable(task8_server task8_server.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)
add_executable(task8_client task8_client.c lib/model.h)

add_executable(task9 task9.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)
add_executable(task10 task10.c lib/model.h lib/load_avg.c lib/load_avg.h lib/stats.c lib/stats.h)

find_package (Threads)
target_link_libraries (task4 ${CMAKE_THREAD_LIBS_INIT})
target_link_libraries (task5 ${CMAKE_THREAD_LIBS_INIT})
target_link_libraries (task6 ${CMAKE_THREAD_LIBS_INIT})