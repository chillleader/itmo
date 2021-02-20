obj-m += char_dev.o
char_dev-objs := ./src/file_handlers.o ./src/calculator.o ./src/char_dev.o

all:
	make -C /lib/modules/`uname -r`/build M=$(PWD) modules
clean:
	make -C /lib/modules/`uname -r`/build M=$(PWD) clean

