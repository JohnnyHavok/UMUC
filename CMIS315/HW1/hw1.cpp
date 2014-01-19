/*	
**  hw1.cpp
**  Justin Smith
**  Homework 1 - Rework
**  CMIS 315.6380
**  01.18.14
*/

#include <iostream>

using std::cin;
using std::cout;
using std::endl;

int main()
{
	int input = 0;
	int sum   = 0;
	
	long product = 0;

	float average = 0;

	// Loop five times asking for numbers
	for(int i = 1; i <= 5; i++)
	{
		cout << "Please enter number " << i << ": ";
		cin >> input;
		sum += input;

		if(i == 1) // First time through do not multiply by initial 0
			product = input;
		else 
			product *= input;
	}

	// Calculate average
	average = sum / 5.0;

	// Output results of the average and product of the five numbers
	cout << "The average of the five numbers is: " << average << endl;
	cout << "The product of the five numbers is: " << product << endl;

}