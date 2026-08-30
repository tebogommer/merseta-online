/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.exceptions;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ValueRequiredException.
 */
public class ValidationErrorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The errors. */
	private List<String> errors;

	/**
	 * Instantiates a new value required exception.
	 */
	public ValidationErrorException() {
	}

	/**
	 * Instantiates a new value required exception.
	 *
	 * @param message
	 *            the message
	 */
	public ValidationErrorException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new validation error exception.
	 *
	 * @param message the message
	 * @param errors the errors
	 */
	public ValidationErrorException(String message, List<String> errors) {
		super(message);
		this.errors = errors;
	}

	/**
	 * Instantiates a new validation error exception.
	 *
	 * @param errors the errors
	 */
	public ValidationErrorException(List<String> errors) {
		this.errors = errors;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
