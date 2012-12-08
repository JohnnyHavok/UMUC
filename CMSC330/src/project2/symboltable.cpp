/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <map>
#include <string>
#include <iostream>
using namespace std;

#include "symboltable.h"

void SymbolTable::insert(string variable, double value) {
	table[variable] = value; // Creates a new map entry, if variable name already exist it overwrites last value.
}

double SymbolTable::lookUp(string variable) {
	if(table.find(variable) == table.end()) // Search for the variable, find() returns a position, if thats the end then we didnt find it.
		throw exception("Error: Uninitialized variable");
	else
		return table[variable];
}

void SymbolTable::init() { 
	table.clear(); // Clears the map, removes all elements.
} 