#include <iostream>
#include <string>
#include <stdexcept>
using namespace std;

Divide(Expression* left, Expression* right): SubExpression(left, right) {}

Evaluate() {
	if(right->evaluate() != 0)
		return left->evaluate() / right->evaluate();
	else
		throw exception();
}
