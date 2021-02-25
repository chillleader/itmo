#include "calculator.h"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Bepis team");
MODULE_DESCRIPTION("Lab 1 Kernel Module");

char *buffer;
//module_param(buffer,char,0660);

size_t buffer_ptr = 0;

char plus = '+';
char minus = '-';
char multiply = '*';
char divide = '/';

void calc_init()
{
    buffer = (char*) kmalloc(BUFFSIZE * sizeof(char), GFP_KERNEL);
    if (buffer)
    {
        buffer_ptr = 0;
        printk(KERN_INFO "Driver: Successfully allocated result buffer of size %d\n", BUFFSIZE);
        return;
    }
    printk(KERN_ERR "Driver: Failed to allocate result buffer %d\n", BUFFSIZE);
}

void store(char* result, size_t len)
{
    if (len > BUFFSIZE - buffer_ptr)
    {
        printk(KERN_INFO "Driver: Result buffer is full, dumping it to kernel log to avoid overflow\n");
        printk(KERN_INFO "%s\n", buffer);

        kfree(buffer);
        calc_init();
    }

    buffer_ptr += (sprintf(buffer + buffer_ptr,"%s\n", result));
}

void parse(const char *string, int len)
{
    int first = 0, second = 0;
    int operator_index = 0;
    int result = 0;

    int i = 0;
    while(!(string[i] == plus 
        || string[i] == minus 
        || string[i] == multiply 
        || string[i] == divide))
    {
        first = first*10 + (string[i]-48);
        i++;
    }

    operator_index = i;
    i++;
    
    for (; i < len-1; ++i) 
    {
        second = second*10 + (string[i]-48);
    }
    

    if (string[operator_index] == plus)
    {
        result = first + second;
    }
    else if (string[operator_index] == minus)
    {
     result = first - second;
    }
    else if (string[operator_index] == multiply)
    {
       result = first * second;
    }
    else if (string[operator_index] == divide){
        result = first / second;
    }

    char str[20];
    sprintf(str, "%d", result);
    store(str, strlen(str));
}
