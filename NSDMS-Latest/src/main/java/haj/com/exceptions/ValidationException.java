/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class ValueRequiredException.
 */
public class ValidationException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The params. */
	private Object[] params;

	/**
	 * Instantiates a new value required exception.
	 */
	public ValidationException() {
	}

	/**
	 * Instantiates a new value required exception.
	 *
	 * @param message
	 *            the message
	 */
	public ValidationException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new validation exception.
	 *
	 * @param message the message
	 * @param params the params
	 */
	public ValidationException(String message, Object...params) {
		super(message);
		this.params = params;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Object[] getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the new params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

}
