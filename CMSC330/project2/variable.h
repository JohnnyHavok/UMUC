/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
class Variable: public Operand {
	public:
		Variable(string name) {
			this->name = name;
		}
		double Variable::evaluate();
	private:
		string name;
};