/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
class Divide: public SubExpression {
	public:
		Divide(Expression* left, Expression* right): SubExpression(left, right) {}
		double evaluate() {
			if(right->evaluate() != 0)
				return left->evaluate() / right->evaluate();
			else
				throw exception("Error: Attempted to divide by zero");
			return 0;
		}	
};