/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <cctype>
#include <iostream>
#include <list>
#include <string>
#include <strstream>
using namespace std;

#include "expression.h"
#include "subexpression.h"
#include "operand.h"
#include "variable.h"
#include "literal.h"
#include "parse.h"

Expression* Operand::parse(strstream& in) {
    char paren;
    double value;

    in >> ws;
    if (isdigit(in.peek())) {
        in >> value;
        Expression* literal = new Literal(value);
        return literal;
    }
    if (in.peek() == '(') {
        in >> paren;
        return SubExpression::parse(in);
    } else
        return new Variable(parseName(in));
    return 0;
}