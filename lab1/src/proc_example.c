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
#include "file_handlers.h"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Author");
MODULE_DESCRIPTION("The first kernel module");

static dev_t first;
static struct class *cl;

static struct file_operations mychdev_fops =
{
  .owner = THIS_MODULE,
  .open = dev_open,
  .release = dev_close,
  .read = dev_read,
  .write = dev_write
};

static struct file_operations proc_fops =
{
	.owner = THIS_MODULE,
	.read = proc_read
};
 
static int __init ch_drv_init(void)
{
    printk(KERN_INFO "Hello!\n");
    if (alloc_chrdev_region(&first, 0, 1, "ch_dev") < 0)
	  {
		return -1;
	  }
    if ((cl = class_create(THIS_MODULE, "chardrv")) == NULL)
	  {
		unregister_chrdev_region(first, 1);
		return -1;
	  }
    if (device_create(cl, NULL, first, NULL, "mychdev") == NULL)
	  {
		class_destroy(cl);
		unregister_chrdev_region(first, 1);
		return -1;
	  }
    cdev_init(&c_dev, &mychdev_fops);
	ent=proc_create("var2",0660,NULL,&proc_fops);

    if (cdev_add(&c_dev, first, 1) == -1)
	  {
		device_destroy(cl, first);
		class_destroy(cl);
		unregister_chrdev_region(first, 1);
		return -1;
	  }
    return 0;
}
 
static void __exit ch_drv_exit(void)
{
    cdev_del(&c_dev);
	proc_remove(ent);
    device_destroy(cl, first);
    class_destroy(cl);
    unregister_chrdev_region(first, 1);
    printk(KERN_INFO "Bye!!!\n");
}
 
module_init(ch_drv_init);
module_exit(ch_drv_exit);

