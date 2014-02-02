/*	
**  project1.cpp
**  Justin Smith
**  Project 1
**  CMIS 315.6380
**  02.01.14
*/

#include <iostream> // cin, cout, endl

using std::cin;
using std::cout;
using std::endl; 


// FineCalculator class definition
class FineCalculator
{
public:
	FineCalculator(int);
	int getFine(int, int, int) const;

private:
	int _courtFees;
};

// Begin FineCalculator class implementation

FineCalculator::FineCalculator(int courtFees) { _courtFees = courtFees; }

int FineCalculator::getFine(int zone, int speedLimit, int actualSpeed) const
{
	switch(zone)
	{
		case 1: // Regular zone speeding
			return (actualSpeed - speedLimit) * 5 + _courtFees;

		case 2: // Work zone speeding
			return (actualSpeed - speedLimit) * 6 + _courtFees;

		case 3: // Residential zone speeding
			return (actualSpeed - speedLimit) * 7 + 200 + _courtFees;

		default: // Project specification does not require a check on user input
				 // so it is possible for a user of this class to reach this.
			return 0;
	}
}

// End FineCalculator class implentation


// Main program, user interaction.
int main()
{
	int userInput; // Holds user interaction values
	int zone, speedLimit, actualSpeed;

	cout << "Enter court fees: ";
	cin >> userInput;

	FineCalculator fc(userInput); // Instantiate a calculator for this session

	do
	{
		cout << "Enter the type of speeding offense "
			 << "(1 for Regular, 2 for Work Zone, 3 for Residential): ";
		cin >> zone;

		cout << "Enter the speed limit: ";
		cin >> speedLimit;

		cout << "Enter the actual speed: ";
		cin >> actualSpeed;

		cout << "The total fine is: $" << fc.getFine(zone, speedLimit, actualSpeed)
			 << endl;

		cout << "Enter 1 to produce another ticket or 0 to exit: ";
		cin >> userInput;	 
		
	} while (userInput != 0);

	cout << "Program finished" << endl;
}