/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
class SubExpression: public Expression {
	public:
		SubExpression(Expression* left, Expression* right);
		static Expression* parse(strstream& in);
	protected: 
		Expression* left;
		Expression* right;
};