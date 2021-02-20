#ifndef CALCULATOR
#define CALCULATOR
#include <linux/module.h>
#include <linux/version.h>
#include <linux/kernel.h>
#include <linux/types.h>
#include <linux/kdev_t.h>
#include <linux/fs.h>
#include <linux/device.h>
#include <linux/cdev.h>
#include <linux/proc_fs.h>
#include <linux/uaccess.h>
#include <linux/moduleparam.h>
#include <linux/init.h>

#define BUFFSIZE 100

extern char buffer[];

extern int buffer_ptr;

void parse(char * string, int len);

#endif