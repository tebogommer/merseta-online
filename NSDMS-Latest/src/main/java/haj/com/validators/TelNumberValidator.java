package haj.com.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// TODO: Auto-generated Javadoc
/**
 * The Class IDValidator.
 */
public class TelNumberValidator implements ConstraintValidator<CheckTelNumber, String> {

	/** The id mode. */
	private IDMode idMode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(CheckTelNumber constraintAnnotation) {
		this.idMode = constraintAnnotation.value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		if (object == null)
			return true;
		if ("".equals(object.toString()))
			return true;
		if (idMode == IDMode.IDCHECK)
			return checkCell(object.toString());
		else
			return false;
		// return object.equals(object.toLowerCase());
	}

	/**
	 * Check id.
	 *
	 * @param idVal
	 *            the id val
	 * @return true, if successful
	 */
	public boolean checkCell(String idVal) {
		return !"00".equals(idVal.substring(0, 2));
	}
}