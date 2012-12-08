/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <map> // I don't understand why I include this here,
			   // if I do not I get errors. This took me a long
			   // time to figure out.  I know I am declaring a map
			   // here, but this is included before the map is made
			   // in symboltable.cpp.

class SymbolTable {
	public:
		SymbolTable() {}
		void insert(string variable, double value);
		double lookUp(string variable);
		void init(); // Added as part of the spec given in the conference area. 
	private:
		map<string, double> table; // Our container for variables and their values. 
};