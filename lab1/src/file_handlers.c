#include "file_handlers.h"
#include "calculator.h"

#define BUFSIZE  100

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Bepis team");
MODULE_DESCRIPTION("Lab 1 Kernel Module");

struct proc_dir_entry *ent;

struct cdev c_dev; 

struct file_operations mychdev_fops =
	{
		.owner = THIS_MODULE,
		.open = dev_open,
		.release = dev_close,
		.read = dev_read,
		.write = dev_write
	};

struct file_operations proc_fops =
	{
		.owner = THIS_MODULE,
		.read = proc_read
	};

ssize_t proc_read(struct file *f, char __user *ubuf, size_t count, loff_t *ppos)
{
  char buf[BUFSIZE];
  int len=0;
  printk(KERN_INFO "Read from proc\n");
  if(*ppos > 0 || count < BUFSIZE)
  	return 0;
  len += sprintf(buf,"%s",buffer);
  if(copy_to_user(ubuf,buf,len))
  	return -EFAULT;
  *ppos = len;
  return len;
}

ssize_t proc_write(struct file *f, const char __user *buf,  size_t len, loff_t *off)
{
  printk(KERN_INFO "Write proc\n");
  return len;
}

int dev_open(struct inode *i, struct file *f)
{
  printk(KERN_INFO "Driver: open()\n");
  return 0;
}

int dev_close(struct inode *i, struct file *f)
{
  printk(KERN_INFO "Driver: close()\n");
  return 0;
}

ssize_t dev_read(struct file *f, char __user *buf, size_t len, loff_t *off)
{
  printk(KERN_INFO "%s\n", buffer);
  return 0;
}

ssize_t dev_write(struct file *f, const char __user *buf,  size_t len, loff_t *off)
{
  printk(KERN_INFO "Driver: write()\n");
  parse(buf, len);
  return len;
}

void create_proc_file(char* filename)
{
  if (ent) proc_remove(ent);
  ent = proc_create(filename, 0660, NULL, &proc_fops);
  printk(KERN_INFO "Driver: created proc file with name %s\n", filename);
}