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

static struct proc_dir_entry *ent;

static struct cdev c_dev; 


static int proc_open(struct inode *i, struct file *f);

static int proc_close(struct inode *i, struct file *f);

static ssize_t proc_read(struct file *f, char __user *buf, size_t len, loff_t *off);

static ssize_t proc_write(struct file *f, const char __user *buf,  size_t len, loff_t *off);

static int dev_open(struct inode *i, struct file *f);

static int dev_close(struct inode *i, struct file *f);

static ssize_t dev_read(struct file *f, char __user *buf, size_t len, loff_t *off);

static ssize_t dev_write(struct file *f, const char __user *buf,  size_t len, loff_t *off);

#endif