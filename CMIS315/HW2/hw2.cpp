
/*	
**  hw2.cpp
**  Justin Smith
**  Homework 2
**  CMIS 315.6380
**  01.25.14
*/

#include <iostream>

using std::cin;
using std::cout;
using std::endl;

int main()
{
	int count = 0;
	int input = 0;
	int sum   = 0;
	
	long product = 1;

	float average = 0;

	cout << "Please enter the count: ";
	cin >> count;

	// Loop five times asking for numbers
	for(int i = 1; i <= count; i++)
	{
		cout << "Please enter number " << i << ": ";
		cin >> input;
		sum += input;
	}

	// Calculate average
	average = static_cast<float>(sum) / count;

	// Output results of the average and product of the numbers
	cout << "The average of the " << count << " numbers is: " << average << endl;
	cout << "The product of the " << count << " numbers is: " << product << endl;
}