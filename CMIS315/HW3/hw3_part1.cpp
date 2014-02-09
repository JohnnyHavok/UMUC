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
curve where the highest precentile was the total 14.

Results:

Total    Frequency
    4         4701
    5        18740
    6        46594
    7        92871
    8       161970
    9       258734
   10       370404
   11       481946
   12       579074
   13       648864
   14       677020
   15       647001
   16       577704
   17       480604
   18       370134
   19       259458
   20       162242
   21        92763
   22        45838
   23        18730
   24         4608
 
**/