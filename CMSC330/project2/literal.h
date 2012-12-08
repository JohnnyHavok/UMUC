/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
class Literal: public Operand {
	public:
		Literal(double value) {
			this->value = value;
		}
		double evaluate() {
			return value;
		}
	private:
		double value;
};