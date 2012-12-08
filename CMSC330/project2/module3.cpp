/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * File: module3.cpp
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <iostream>
#include <string>
#include <vector>
#include <stdexcept>
#include <fstream>
#include <strstream>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "symboltable.h"
#include "parse.h"

#define inputFileName	"input.txt"
#define maxLineSize		256

SymbolTable symbolTable;

void parseAssignments(strstream& in);

int main() {
	Expression* expression;
	char paren, comma, line[maxLineSize];
	
	// Open file for reading.
	ifstream input(inputFileName);

	while(true) {
		input.getline(line, maxLineSize); // Fetch next line until we hit EOF
		if(!input) // If no more lines are found get out of the while loop.
			break;
		strstream in(line, maxLineSize);

		cout << line << " "; // Display the current line we are working with
		in >> paren; // Get rid of a parenthesis

		symbolTable.init(); // Clears all data in our symbol table.  

		try { // Run the evaluation algorthim and catch/report any errors we detect. 
			expression = SubExpression::parse(in);
			in >> comma;

			if(comma != ';') // No variables in this one.
				if(comma == ',')  // Check to make sure we have a comma if we 
					parseAssignments(in);
				else
					throw exception("Error: Syntax Error"); // There was no comma or semi-colon.

			double result = expression->evaluate();
			cout << "Value= " << result << endl;
		} catch(exception& mesg) {
			cout << mesg.what() << endl;
		}
	}
	// Keep window open until you press a key.
	cout << endl << "Execution complete: Press any key to exit";
	cin.get();
    return 0;
}

void parseAssignments(strstream& in) {
    char assignop, delimiter;
    string variable;
    double value;
    do {
        variable = parseName(in);
        in >> ws >> assignop >> value >> delimiter;
		if(assignop == '=') // assignop must be an equals sign. 
			symbolTable.insert(variable, value);
		else
			throw exception("Error: Syntax Error");
    }
    while (delimiter == ',');
}
