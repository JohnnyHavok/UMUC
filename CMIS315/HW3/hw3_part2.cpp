/*	
**  hw3_part2.cpp
**  Justin Smith
**  Homework 3 Part 2
**  CMIS 315.6380
**  02.08.14
*/

/** Answer to ex. 8.14:
The code starts by creating two char arrays string1 and string2, which hold
80 char each.  The user inputs two strings (words) seperated by a white space.
The program then sends the starting address of the two strings to a function 
called mystery1.  mystery1's function prototype indicates that it will modify
string 1 in some way and not modify string 2.  The while() loop inside mystery1
iterates over string1, using the terminating character as a conditional test.  
If the current character does not equal '\0' then it increments the pointer 
address using pointer arithmetic, ++s1, which moves the pointer address to the 
next char in memory.  Finally, the function enters a for() loop where the 
values of s2 are copied into s1 so that s1 has the same string as s2 but
different memory addresses (deep copy) before exiting the function.
**/

/** Answer to ex. 8.15:
The code starts by creating an char array string1, which holds 80 char.  The
user inputs a string at the terminal.  Once cin finishes, the program sends 
the mystery2 function a pointer to the first memory location of the string.
It then calculates the size of the string by looping through the array using
a for() loop with the terminating character '\0' as the test and pointer
arithmetic ++s to advance the memory address through the entire array.  With
each sucessfull pass through the array the function updates the integer value
of x before returning it to the caller. 
**/

// Begin rewrite of function mystery2, replace pointers with array/index
// notation.

#include <iostream> // cout, cin, endl

using namespace std;

int mystery2(const char []); // prototype

int main() 
{
	char string1[80];

	cout << "Enter a string: ";
	cin >> string1;
	cout << mystery2(string1) << endl;
}

int mystery2(const char s[])
{
	int x; 

	for(x = 0; s[x] != '\0'; x++)
		;

	return x;
}
