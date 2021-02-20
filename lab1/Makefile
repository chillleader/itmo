obj-m += proc_example.o
proc_example-objs := ./src/file_handlers.o ./src/calculator.o ./src/proc_example.o

all:
	make -C /lib/modules/`uname -r`/build M=$(PWD) modules
clean:
	make -C /lib/modules/`uname -r`/build M=$(PWD) clean

