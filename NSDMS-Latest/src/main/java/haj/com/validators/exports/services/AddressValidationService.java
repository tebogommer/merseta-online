/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import java.util.Date;

import haj.com.constants.HAJConstants;
import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class AddressValidationService extends AbstractService {
	
	private static AddressValidationService addressValidationService;
	public static synchronized AddressValidationService instance() {
		if (addressValidationService == null) {
			addressValidationService = new AddressValidationService();
		}
		return addressValidationService;
	}
	
	public boolean validateAdressLine1(String addressLine1) {
		return addressLine1 != null;
	}
		
	public boolean validatingAdressLine1(String addressLine1) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON) {
			// This field may not be left blank
			if (addressLine1.trim().isEmpty()) {
				return false;
			}
			// Field may not start with a space.
			if (addressLine1.trim().charAt(0) == ' ') {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`', (Inculdes Space)
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_FOUR, addressLine1.trim().toUpperCase())) {
				return false;
			}
			// The field may not contain a value that contains only numbers
			if (ValidationConstants.matchPattern(ValidationConstants.PATTERN_THREE, addressLine1.trim().toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsContainsAddress())) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean validatingAdressLine2 (String addressLine2) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON) {
			// This field may not be left blank
			if (addressLine2.trim().isEmpty()) {
				return false;
			}
			// Field may not start with a space.
			if (addressLine2.charAt(0) == ' ') {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`', (Inculdes Space)
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_FOUR, addressLine2.trim().toUpperCase())) {
				return false;
			}
			// The field may not contain a value that contains only numbers
			if (ValidationConstants.matchPattern(ValidationConstants.PATTERN_THREE, addressLine2.trim().toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine2.trim().toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(addressLine2.trim().toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine2.trim().toUpperCase(), ValidationConstants.populateReservedWordsContainsAddress())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingAdressLine3 (String addressLine3) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !addressLine3.trim().isEmpty()) {
			// This field may not be left blank
			if (addressLine3.trim().isEmpty()) {
				return false;
			}
			// Field may not start with a space.
			if (addressLine3.charAt(0) == ' ') {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`', (Inculdes Space)
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_FOUR, addressLine3.trim().toUpperCase())) {
				return false;
			}
			// The field may not contain a value that contains only numbers
			if (ValidationConstants.matchPattern(ValidationConstants.PATTERN_THREE, addressLine3.trim().toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine3.trim().toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(addressLine3.trim().toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(addressLine3.trim().toUpperCase(), ValidationConstants.populateReservedWordsContainsAddress())) {
				return false;
			}
			// Field may not have 4 consecutive numbers embedded in it Business Rules (informational) 
			if (addressLine3.trim().toUpperCase().matches(ValidationConstants.PATTERN_FIVE)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingPostCode(String postcode) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON) {
			// field may only contain numbers
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_THREE, postcode.toUpperCase())) {
				return false;
			}
			// filed must equal 4 numbers
			if (postcode.length() != 4) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLatitudeDegrees(Double latitudeDegrees) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && latitudeDegrees != null) {
			// check REGEX if number
			if(latitudeDegrees > Double.valueOf(0.0)){
				return false;
			}
			if (latitudeDegrees >  Double.valueOf(-22.0) || latitudeDegrees <  Double.valueOf(-35.0)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLatitudeDegreesString(String latitudeDegrees) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !latitudeDegrees.isEmpty()) {
			if ((latitudeDegrees.startsWith(" "))) {
				return false;
			}
			// check REGEX if number
			if(Double.valueOf(latitudeDegrees) > Double.valueOf(0.0)){
				return false;
			}
			if (Double.valueOf(latitudeDegrees) >  Double.valueOf(-22.0) ||  Double.valueOf(latitudeDegrees) <  Double.valueOf(-35.0)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLatitudeMinutes(Double latitudeMinutes) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && latitudeMinutes != null) {	
			// Latitude Minutes may not be less than 00 and Greater than 59
			if(latitudeMinutes < Double.valueOf(0.0) || latitudeMinutes > Double.valueOf(59.0)){
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLatitudeMinutesString(String latitudeMinutes) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !latitudeMinutes.isEmpty()) {
			if ((latitudeMinutes.startsWith(" "))) {
				return false;
			}
			// check REGEX if numberasda
			
			// Latitude Minutes may not be less than 00 and Greater than 59
			if(Double.valueOf(latitudeMinutes) < Double.valueOf(0.0) || Double.valueOf(latitudeMinutes) > Double.valueOf(59.0)){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validatingLatitudeSeconds(Double latitudeSeconds) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && latitudeSeconds != null) {
			// Latitude Seconds may not be less than 00.000 and Greater than 59.999
			if(latitudeSeconds < Double.valueOf(0.0) || latitudeSeconds > Double.valueOf(59.99)){
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLatitudeSecondsString(String latitudeSeconds) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !latitudeSeconds.isEmpty()) {
			if ((latitudeSeconds.startsWith(" "))) {
				return false;
			}
			// check REGEX if number
			
			// Latitude Seconds may not be less than 00.000 and Greater than 59.999
			if(Double.valueOf(latitudeSeconds) < Double.valueOf(0.0) || Double.valueOf(latitudeSeconds) > Double.valueOf(59.99)){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validatingLongitudeDegrees(Double longitudeDegrees) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && longitudeDegrees != null) {
			// Value may not be greater than 33 and may not have a value less than 16
			if(longitudeDegrees < Double.valueOf(16.0) || longitudeDegrees > Double.valueOf(33.0)){
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLongitudeDegreesString(String longitudeDegrees) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !longitudeDegrees.isEmpty()) {
			if ((longitudeDegrees.startsWith(" "))) {
				return false;
			}
			// check regex numbers
			if (ValidationConstants.REGEXLATITUDE.contains(longitudeDegrees)){
				return false;
			}
			// Value may not be greater than 33 and may not have a value less than 16
			if(Double.valueOf(longitudeDegrees) < Double.valueOf(16.0) || Double.valueOf(longitudeDegrees) > Double.valueOf(33.0)){
				return false;
			}
		}
		return true;
	}
	
	public boolean validatingLongitudeMinutes(Double longitudeMinutes) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && longitudeMinutes != null) {
			// check regex numbers
			// Value must have a length of exactly 2 (leading zeros) and may not be greater than 59
			if(longitudeMinutes < Double.valueOf(0.0) || longitudeMinutes > Double.valueOf(59.0)){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validatingLongitudeMinutesString(String longitudeMinutes) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !longitudeMinutes.isEmpty()) {
			if ((longitudeMinutes.startsWith(" "))) {
				return false;
			}
			
			// check regex numbers
			// Value must have a length of exactly 2 (leading zeros) and may not be greater than 59
			if(Double.valueOf(longitudeMinutes) < Double.valueOf(0.0) || Double.valueOf(longitudeMinutes) > Double.valueOf(59.0)){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validatingLongitudeSeconds(Double longitudeSeconds) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && longitudeSeconds != null) {
			// Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999
			if(longitudeSeconds < Double.valueOf(00.0) || longitudeSeconds > Double.valueOf(59.9)){
				return false;
			} 
		}
		return true;
	}
	
	public boolean validatingLongitudeSecondsString(String longitudeSeconds) {
		if (HAJConstants.ADDRESS_SETMIS_VALIDATION_ON && !longitudeSeconds.isEmpty()) {
			if ((longitudeSeconds.startsWith(" "))) {
				return false;
			}
			// Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999
			if(Double.valueOf(longitudeSeconds) < Double.valueOf(00.0) || Double.valueOf(longitudeSeconds) > Double.valueOf(59.9)){
				return false;
			} 
		}
		return true;
	}
		
	
	
	/**
	 * Check to see if the field parameter is within the minimum and maximum amount of charters. If within the threshold will return a boolean: true for passed else will return a boolean false for a fail
	 * Note if field null will return a automatic fail
	 * @param field the field the size check will be done against
	 * @param maxNumberOfChatarers the max amount of charters the string must contain
	 * @param minNumberOfChatarers the min amount of charters the string must contain
	 * @return boolean true if passed, false if failed
	 */
	public boolean stringValidiationChatarerSizeCheckBetweenMinAndMaxAmount(String field, Integer maxNumberOfChatarers, Integer minNumberOfChatarers){
		if (field != null) {
			int sizeOfCurrentFiled = field.trim().length();
			if (sizeOfCurrentFiled > minNumberOfChatarers && sizeOfCurrentFiled < maxNumberOfChatarers) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	 *  return false if does not equal amount, true if equals amount.
	 *  If field null will auto fail
	 */
	public boolean stringValidiationChatarerSizeCheckEqualsAmount(String field, Integer amountOfCharters){
		if (field != null) {
			int sizeOfCurrentFiled = field.trim().length();
			if (sizeOfCurrentFiled == amountOfCharters) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	 * Check if start date before end date.
	 * if before will return true for passed
	 * if after will return false for failed
	 * 
	 * if either startDate or EndDate null will return false for fail
	 */
	public boolean dateValidiationStartDateBeforeEndDate(Date startDate, Date endDate){
		if (startDate == null || endDate == null) {
			if (startDate.before(endDate)) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	 *  Field between min and max amount will return true for passed
	 *  Else will return false for failed
	 *  
	 *  if Field null will return false for failed
	 *  if (Double.parseDouble(file100Bean.getLatitudeDegree()) > -22 && Double.parseDouble(file100Bean.getLatitudeDegree()) < -35) {
	 *  -35 as min amount, -22 as grater amount
	 *  
	 *  Latitude Minutes may not be less than 00 and Greater than 59
	 *  Latitude Seconds may not be less than 00.000 and Greater than 59.999
	 *  Longitude Degree may not be Greater than -22 and Less than -35
	 *  Longitude Minutes may not be less than 00 and Greater than 59
	 *  
	 */
	public boolean doubleValidiationLatitudeDegreeBetweenMinAndMaxAmount(Double field, Double minAmount, Double maxAmount){
		if (field != null) {
			if (field < maxAmount && field > minAmount) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	 * Check if field null before Var: addressDefaultDate
	 * 
	 * If field not null return true for passed
	 * if date passed is null will return false for failed 
	 */

	
	// return true if can parse in a to a int 
	public boolean integerNumberOrNot(String field) {
		try {
			Integer.parseInt(field);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	// return true if can parse in a to a double 
	public boolean doubleNumberOrNot(String field) {
		try {
			Double.parseDouble(field);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	/*
	 * Check if fields have the same characters
	 * 
	 * If field1 and field2 are null and have the same characters return true for passed
	 * If field1 and field2 are not null will return false for failed 
	 */
	public boolean stringValidiationSameStrings(String field, String field1){
		if(field != null && !field.trim().equalsIgnoreCase("") && field1 != null && !field1.trim().equalsIgnoreCase("")) {
			if(field.trim().equalsIgnoreCase(field1.trim())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		String addressLine1 = "8";
		boolean passed = true;
		// This field may not be left blank
		if (addressLine1.trim().isEmpty()) {
			passed = false;
		}
		// Field may not start with a space.
		if (addressLine1.trim().charAt(0) == ' ') {
			passed = false;
		}
		// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`', (Inculdes Space)
		if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN_FOUR, addressLine1.trim().toUpperCase())) {
			passed = false;
		}
		// The field may not contain a value that contains only numbers
		if (ValidationConstants.matchPattern(ValidationConstants.PATTERN_THREE, addressLine1.trim().toUpperCase())) {
			passed = false;
		}
		// Upper case value in field may not contain characters
		if (ValidationConstants.containsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
			passed = false;
		}
		// Upper case value in field may not equal characters
		if (ValidationConstants.equalsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
			passed = false;
		}
		// Upper case value in field may not contain characters
		if (ValidationConstants.containsInResevredWords(addressLine1.trim().toUpperCase(), ValidationConstants.populateReservedWordsContainsAddress())) {
			passed = false;
		}
		System.out.println(passed);
	}
	
}