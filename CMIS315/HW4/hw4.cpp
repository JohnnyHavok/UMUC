/*  
**  hw4.cpp
**  Justin Smith
**  Homework 4 
**  CMIS 315.6380
**  02.22.14
*/

#include <iostream>
#include "RationalNumber.h"

using namespace std;

int main()
{
  RationalNumber *RNArray = new RationalNumber[8];

  cout << "Generate Test Cases" << endl;
  try
  {
    // Generate some test cases
    RNArray[0] = RationalNumber(0,10);
    RNArray[1] = RationalNumber(12,100);
    RNArray[2] = RationalNumber(16,12);
    RNArray[3] = RationalNumber(1,2);
    RNArray[4] = RationalNumber(-3,5);
    RNArray[5] = RationalNumber(-1,-2);
    RNArray[6] = RationalNumber(3,-16);
    RNArray[7] = RationalNumber(6,10);
    
    RationalNumber ErrorCase(1,0); // Cause divide by zero exception to be thrown.
  }
  catch(const char *exception)
  {
    cout << exception << " Skipping..." << endl;
  }


  for(int i = 0; i < 8; ++i)
    cout << "RNArray[" << i << "]: " << RNArray[i] << endl;

  cout << "\nComparisons, all should output 1 for true" << endl;

  cout << RNArray[3] << " == " << RNArray[5] << ": " 
       << (RNArray[3] == RNArray[5]) << endl;

  cout << RNArray[0] << " != " << RNArray[6] << ": "
       << (RNArray[0] != RNArray[6]) << endl;

  cout << RNArray[1] << " < " << RNArray[2] << ": "
       << (RNArray[1] < RNArray[2]) << endl;

  cout << RNArray[5] << " > " << RNArray[4] << ": "
       << (RNArray[5] > RNArray[4]) << endl;

  cout << "\nOperations" << endl;

  cout << RNArray[6] << " + " << RNArray[1] << ": "
       << (RNArray[6] + RNArray[1]) << endl;

  cout << RNArray[0] << " - " << RNArray[3] << ": "
       << (RNArray[0] - RNArray[3]) << endl;
  
  cout << RNArray[5] << " * " << RNArray[4] << ": "
       << (RNArray[5] * RNArray[4]) << endl;
  
  cout << RNArray[6] << " / " << RNArray[1] << ": "
       << (RNArray[6] / RNArray[1]) << endl;


  cout << "\nSpecial Cases Resulting in Zero" << endl;

  cout << RNArray[0] << " * " << RNArray[3] << ": "
       << (RNArray[0] * RNArray[3]) << endl;

  cout << RNArray[4] << " + " << RNArray[7] << ": "
       << (RNArray[4] + RNArray[7]) << endl;

  cout << RNArray[3] << " - " << RNArray[5] << ": "
       << (RNArray[3] - RNArray[5]) << endl;


  cout << "\nChained Operations" << endl;
  cout << "(" << RNArray[6] << " * " << RNArray[4] << ") / " << RNArray[5] << " - "
       << RNArray[0] << " + " << RNArray[2] << " : "
       << (RNArray[6] * RNArray[4] / RNArray[5] - RNArray[0] + RNArray[2]) << endl;

  delete [] RNArray;
}
