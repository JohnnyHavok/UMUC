#include <stdio.h>
#define NUMBER_TO_FIND 4
void main()
{  
    int candidate=2, numberFound = 0, sumOfFactors, divisor;
    while (numberFound < NUMBER_TO_FIND)
    {
        sumOfFactors = 0;
        for (divisor = 1; divisor <= candidate / 2; divisor++)
            if (candidate % divisor == 0)
                sumOfFactors += divisor;    
            if (candidate == sumOfFactors)
            {
                printf("%d\n", candidate);
                numberFound++;
            }
        candidate++;
    }
}