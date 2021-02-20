#ifndef FILE_HANDLERS
#define FILE_HANDLERS
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

extern struct proc_dir_entry *ent;

extern struct cdev c_dev; 


int proc_open(struct inode *i, struct file *f);

int proc_close(struct inode *i, struct file *f);

ssize_t proc_read(struct file *f, char __user *buf, size_t len, loff_t *off);

ssize_t proc_write(struct file *f, const char __user *buf,  size_t len, loff_t *off);

int dev_open(struct inode *i, struct file *f);

int dev_close(struct inode *i, struct file *f);

ssize_t dev_read(struct file *f, char __user *buf, size_t len, loff_t *off);

ssize_t dev_write(struct file *f, const char __user *buf,  size_t len, loff_t *off);

#endif