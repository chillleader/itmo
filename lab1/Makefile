obj-m += proc_example.o
proc_example-objs := ./src/proc_example.o ./src/file_handlers.o

all:
	make -C /lib/modules/`uname -r`/build M=$(PWD) modules
clean:
	make -C /lib/modules/`uname -r`/build M=$(PWD) clean

