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

//TODO: function is broken, fix it
void parse(const char *string, int len)
{
    int i;
    int sign_index;
    int sign;
    int fist_operand = 0;
    int second_operand = 0;
    int result;
    for (i = 0; i < len; i++)
    {
        if (string[i] == plus || string[i] == minus || string[i] == multiply || string[i] == divide)
        {
            sign_index = i;
        }
        if (string[i] == plus)
        {
            sign = 0;
        }
        else if (string[i] == minus)
        {
            sign = 1;
        }
        else if (string[i] == multiply)
        {
            sign = 3;
        }
        else if (string[i] == divide)
        {
            sign = 4;
        }
    }
    for (i = 0; i < sign_index; i++)
    {
        fist_operand += 10 * i + (int)string[i];
    }
    for (i = sign_index + 1; i < len; i++)
    {
        second_operand += 10 * i + (int)string[i];
    }
    switch (sign)
    {
    case 0:
        result = fist_operand + second_operand;
        break;
    case 1:
        result = fist_operand - second_operand;
        break;
    case 2:
        result = fist_operand * second_operand;
        break;
    case 3:
        result = fist_operand / second_operand;
        break;
    }


    char str[20];
    sprintf(str, "%d", result);
    store(str, strlen(str));
}
