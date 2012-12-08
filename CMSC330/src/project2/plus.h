/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
class Plus: public SubExpression {
	public:
		Plus(Expression* left, Expression* right): SubExpression(left, right) {}

		double evaluate() {
		   return left->evaluate() + right->evaluate();
		}
};