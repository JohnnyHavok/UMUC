/*	
**  hw3_part1.cpp
**  Justin Smith
**  Homework 3 Part 1
**  CMIS 315.6380
**  02.08.14
*/

#include <iostream> // cout, endl
#include <iomanip>  // setw
#include <cstdlib>  // srand, rand
#include <ctime>    // time

using namespace std;

int main()
{
	const int arraySize = 25; // Array size for 4x6-side and 6x4-side

	int frequency_4x6[arraySize] = {};
	int frequency_6x4[arraySize] = {};

	srand(time(0)); // Seed random number generator

	// Roll our dice four and six times and add up the results 
	// and increment the corresponding cell. 
	for(int roll = 1; roll <= 6000000; ++roll)
	{
		++frequency_4x6[ (1 + rand() % 6) 
			           + (1 + rand() % 6) 
			           + (1 + rand() % 6) 
			           + (1 + rand() % 6) ];

		++frequency_6x4[ (1 + rand() % 4) 
					   + (1 + rand() % 4)
					   + (1 + rand() % 4)
					   + (1 + rand() % 4)
					   + (1 + rand() % 4)
					   + (1 + rand() % 4) ];
	}

	cout << "Results for 4x 6-sided dice rolls:" << endl;
	cout << "Total" << setw(13) << "Frequency" << endl;

	for(int i = 4; i < arraySize; ++i)
		cout << setw(5) << i << setw(13) << frequency_4x6[i] << endl;

	cout << endl;

	cout << "Results for 6x 4-sided dice rolls:" << endl;
	cout << "Total" << setw(13) << "Frequency" << endl;

	for(int i = 6; i < arraySize; ++i)
		cout << setw(5) << i << setw(13) << frequency_6x4[i] << endl;
}

/**
The results from running the above code was not inline with my expectations.
Based on the example in the book and an understanding of basic probability,
when rolling a single 6-sided dice we have an equal chance of rolling any
number.  So when running the above code I expected an equal (+/- a few pts)
chance of rolling a total between 4-24.  Instead the results were a bell 
curve where the highest precentile was the total 14.  For the 6x4 configuration
I recieved a similar bell curve centered around 15.

Results for 4x 6-sided dice rolls:
Total    Frequency
    4         4739
    5        18601
    6        46097
    7        92794
    8       162263
    9       259198
   10       370995
   11       481555
   12       577826
   13       648844
   14       676064
   15       647663
   16       579189
   17       481486
   18       369656
   19       259446
   20       161899
   21        92208
   22        46285
   23        18526
   24         4666

Results for 6x 4-sided dice rolls:
Total    Frequency
    6         1434
    7         8900
    8        31088
    9        81932
   10       176242
   11       316869
   12       491991
   13       669198
   14       798009
   15       849680
   16       799738
   17       668169
   18       491841
   19       316286
   20       175421
   21        81819
   22        30877
   23         9028
   24         1478
 
**/