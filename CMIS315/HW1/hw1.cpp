/*	
**  hw1.cpp
**  Justin Smith
**  Homework 1
**  CMIS 315.6380
**  01.18.14
*/

#include <iostream>

using std::cin;
using std::cout;
using std::endl;

int main()
{
	int input, sum;
	float average;

	// Loop five times asking for numbers
	for(int i = 1; i <= 5; i++)
	{
		cout << "Please enter number " << i << ": ";
		cin >> input;
		sum += input;
	}

	// Output results of the average of the five numbers entered
	cout << "The average of the five numbers is: " << (sum / 5.0) << endl;
}