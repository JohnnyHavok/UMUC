/**
 * Author: Justin Smith
 * Course: CMSC 350
 * Project: 1
 * Date: Nov 3, 2012 8:33:14 PM
 */
package cmsc350.project1;

/**
 * @author jsmith
 *
 */
public interface AlgebraicSymbology {
	/**
	 * @return Algebraic Precedence Order Value
	 */
	int getPrecedence();
	
	/**
	 * @return Standard human recognizable symbology for given non-number algebraic token 
	 */
	String toString();
}
