#include "file_handlers.h"
#define BUFSIZE  100

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Bepis team");
MODULE_DESCRIPTION("Lab 1 Kernel Module");

static int irq=20;
module_param(irq,int,0660);
 
static int mode=1;
module_param(mode,int,0660);

ssize_t proc_read(struct file *f, char __user *ubuf, size_t count, loff_t *ppos)
{
  char buf[BUFSIZE];
  int len=0;
  printk(KERN_INFO "Read from proc\n");
  if(*ppos > 0 || count < BUFSIZE)
  	return 0;
  len += sprintf(buf,"irq = %d\n",irq);
  len += sprintf(buf + len,"mode = %d\n",mode);
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
  printk(KERN_INFO "Driver: read()\n");
  return 0;
}

ssize_t dev_write(struct file *f, const char __user *buf,  size_t len, loff_t *off)
{
  printk(KERN_INFO "Driver: write()\n");
  return len;
}