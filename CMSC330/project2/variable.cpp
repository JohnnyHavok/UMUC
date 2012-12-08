/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <strstream>
#include <vector>
using namespace std;

#include "expression.h"
#include "operand.h"
#include "variable.h"
#include "symboltable.h"

extern SymbolTable symbolTable;

double Variable::evaluate() {
    return symbolTable.lookUp(name);
}