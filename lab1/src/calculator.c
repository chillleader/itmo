#include "calculator.h"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Bepis team");
MODULE_DESCRIPTION("Lab 1 Kernel Module");

char buffer[BUFFSIZE];
//module_param(buffer,char,0660);

int buffer_ptr = 0;

char plus = '+';
char minus = '-';
char multiply = '*';
char divide = '/';

void parse(char *string, int len)
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
    
    buffer_ptr += (sprintf(buffer + buffer_ptr,"%d\n", result));

    return result;
}
