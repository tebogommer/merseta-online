/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import haj.com.constants.HAJConstants;
import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class UserValidationService extends AbstractService {

	public String pattern1      		= "[A-Z\\s`'-]+";
	public String pattern1NoSpaces      = "[A-Z\\s`'-]+";
	public String idPattern     		= "[0-9]+";
	public String AltIDPattern  		= "[0-9@_]+";
	public String numberPattern 		= "[0-9\\s()/-]+";
	public String altIDPattern  		= "[A-Z0-9@_]+";
	
	private static UserValidationService userValidationService;
	public static synchronized UserValidationService instance() {
		if (userValidationService == null) {
			userValidationService = new UserValidationService();
		}
		return userValidationService;
	}
	
	/**
	 * Validates Users First Name
	 * 
	 * if passes validations will return true
	 * 
	 * SETMIS validation requirements: 2018 04 10
	 * 
	 * This field may be left blank (not optional on MerSETA)
	 * Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided
	 * Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-
	 * 
	 * Upper case value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or –
	 * Field must be blank if POPI_Act_Status_ID has a value of 2
	 * Value must be provided if POPI_Act_Status_ID does not have a value of 2
	 * Upper case value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name (Can't Do It Here on this level, sperate process required)
	 * 
	 * @param fieldName
	 * @return boolean if passed validations
	 */
	public boolean validateFirstName(String fieldName) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && !fieldName.isEmpty()) {
			// This field may be left blank
			if (fieldName.length() == 0) {
				return true;
			}
			// Field may not start with a space.
			if (fieldName.startsWith(" ")) {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_ONE, fieldName.toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Validates Users Middle Name
	 * 
	 * if passes validations will return true
	 * 
	 * SETMIS validation requirements: 2018 04 10
	 * 
	 * This field may be left blank
	 * Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided
	 * Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' - (Includes Space)
	 * 
	 * Upper case value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or –
	 * Field must be blank if POPI_Act_Status_ID has a value of 2
	 * Upper case value in field should not contain characters NA or U or NONE or GEEN
	 * Person_Middle_Name should not have the same value as Person_Last_Name
	 * 
	 * @param fieldName
	 * @return boolean if passed validations
	 */
	public boolean validateMiddleName(String fieldName) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && !fieldName.isEmpty()) {
			// This field may be left blank
			if (fieldName.length() == 0) {
				return true;
			}
			// Field may not start with a space.
			if (fieldName.startsWith(" ")) {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' - (Includes Space)
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_TWO, fieldName.toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Validates Users Last Name
	 * 
	 * if passes validations will return true
	 * 
	 * SETMIS validation requirements: 2018 04 10
	 * 
	 * This field may be left blank
	 * Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided
	 * Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' - (Includes Space)
	 * 
	 * Upper case value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or –
	 * Field must be blank if POPI_Act_Status_ID has a value of 2
	 * Value must be provided if POPI_Act_Status_ID does not have a value of 2
	 * Upper case value in field should not contain characters NA or U or NONE or GEEN
	 * Person_Last_Name should not have the same value as Person_First_Name
	 * 
	 * @param fieldName
	 * @return boolean if passed validations
	 */
	public boolean validateLastName(String fieldName) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && !fieldName.isEmpty()) {
			// This field may be left blank
			if (fieldName.length() == 0) {
				return true;
			}
			// Field may not start with a space.
			if (fieldName.startsWith(" ")) {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' - (Includes Space)
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_TWO, fieldName.toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
		}
		return true;
	}

	public boolean validateID(String id) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && id != null && !id.trim().isEmpty()) {
			if (id != null && id.length() == 0) {
				return true;
			}
			if (id.startsWith(" ")) {
				return false;
			}
			if (!ValidationConstants.matchPattern(idPattern, id)) {
				return false;
			}
			if (id.length() < 13) {
				return false;
			}
			if (id.subSequence(6, 10).equals("0000") || id.subSequence(0, 4).equals("0000")) {
				return false;
			}
			if (ValidationConstants.REPEATLIST.contains(id)) {
				return false;
			}
		}
		return true;
	}

	public boolean validateNumber(String number) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON) {
			if (number.length() == 0) {
				return true;
			}
			if (number.startsWith(" ")) {

				return false;
			}
			if (!ValidationConstants.matchPattern(numberPattern, number)) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(number)) {

				return false;
			}
		}
		return true;
	}
	
	public boolean validateFaxNumber(String number) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && !number.trim().isEmpty()) {
			if (number.startsWith(" ")) {
				return false;
			}
			// 1234567890 ()/- 
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_SIX, number.toUpperCase().trim())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(number.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(number.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
		}
		return true;
	}

	public boolean validateAltID(String altId) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON && !altId.isEmpty()) {
			if (altId.length() == 0) {
				return true;
			}
			if (altId.startsWith(" ")) {
				return false;
			}
			if (!ValidationConstants.matchPattern(altIDPattern, altId)) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(altId)) {
				return false;
			}
		}
		return true;
	}
	
	/* Methods to be removed START */
	public boolean validateName(String fieldName) {
		if (HAJConstants.USERS_SETMIS_VALIDATION_ON) {
			if (fieldName.length() == 0) {
				return true;
			}
			if (fieldName.startsWith(" ")) {
				return false;
			}
			if (!ValidationConstants.matchPattern(pattern1, fieldName.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(fieldName)) {
				return false;
			}
		}
		return true;
	}
	/* Methods to be removed END */
	
	/**
	 * Testing method for validations 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fieldName = "Harry      ";
		String trimmed = fieldName.trim();
		System.out.println("Filed Name Legnth: " + fieldName.length());
		System.out.println("Trimmed Field Name Legnth: " + trimmed.length());
		
		boolean passed = true;
		// This field may be left blank
		if (fieldName.length() == 0) {
			passed = true;
		}
		// Field may not start with a space.
		if (fieldName.startsWith(" ")) {
			passed = false;
		}
		// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-
		if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_ONE, fieldName.toUpperCase())) {
			passed = false;
		}
		// Upper case value in field may not contain characters
		if (ValidationConstants.containsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
			passed = false;
		}
		// Upper case value in field may not equal characters
		if (ValidationConstants.equalsInResevredWords(fieldName.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
			passed = false;
		}
		System.out.println(passed);
	}
}