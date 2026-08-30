/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.BlankDAO;
import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class SETMISValidationService extends AbstractService {
	
	/* Dao Level */
	private BlankDAO dao = new BlankDAO();
	
	/**
	 *  Lists 
	 */
	// list containing reserved words. Refer to method populateReservedWords()
	private List<String> reservedwords = new ArrayList<String>();
	// Refer to method populateRepeatList()
	private List<String> repeatList = new ArrayList<>();
	
	/**
	 *  Various VARS used to validation 
	 */
	private String pattern = "ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._-";
	private String regex = "[^A-Z0-9@#&+\\(\\)\\s/\\\\:\\._\\-]";
	private String regexNumbers = "[^0-9()/-]";
	private String regexLatitude = "[^0-9-]";
	private String regexLatitudeSeconds = "[^0-9.]";
	private String regexEmail = "[^ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@]";
	private Pattern r = Pattern.compile(regex);
	private Pattern r2 = Pattern.compile(regexNumbers);
	private Pattern r3 = Pattern.compile(regexEmail);
	private Pattern r4 = Pattern.compile(regexLatitude);
	private Pattern r5 = Pattern.compile(regexLatitudeSeconds);
	private Matcher m;
	private Date addressDefaultDate;

	public boolean validateFirstname(String firstname) {
		return firstname != null && !firstname.isEmpty() && firstname.length() < 5;
	}

	public boolean validateIDNumber(Long idNUmber) {
		return idNUmber == null;
	}

	public boolean validateCompanyName(String companyName) {
		return companyName != null;
	}

	public boolean validateAddressLine1(String addressLine1) {
		return addressLine1 != null;
	}
	
	/* Generic Validation Checks and population methods for checks */
	private void populateReservedWords() {
		reservedwords = new ArrayList<String>();
		reservedwords.add("UNKNOWN");
		reservedwords.add("AS ABOVE");
		reservedwords.add("SOOS BO");
		reservedwords.add("DELETE");
		reservedwords.add("N/A");
		reservedwords.add("NA");
		reservedwords.add("U");
		reservedwords.add("NONE");
		reservedwords.add("GEEN");
		reservedwords.add("0");
		reservedwords.add("TEST");
		reservedwords.add("ONTBREEK");
		reservedwords.add("NIL");
		reservedwords.add("NILL");
		reservedwords.add("-");
		reservedwords.add("ZZZ");
		reservedwords.add("XXX");
		reservedwords.add("ADDRES");
	}
	
	private void populateAddressDefaultDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			addressDefaultDate = sdf.parse("12/31/2018");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void populateRepeatList(){
		repeatList = new ArrayList<>();
		repeatList.add("1111111111111");
		repeatList.add("2222222222222");
		repeatList.add("3333333333333");
		repeatList.add("4444444444444");
		repeatList.add("5555555555555");
		repeatList.add("6666666666666");
		repeatList.add("7777777777777");
		repeatList.add("8888888888888");
		repeatList.add("9999999999999");
	}
	
	/**
	 * @param field
	 * @return boolean true if passed, false if failed
	 */
	public boolean stringValidiationCheckIfNotNullOrNotEmpty(String field){
		if (field == null || field.contentEquals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean dateValidiationCheckIfNotNull(Date field){
		if (field == null) {
			return false;
		} else {
			return true;
		}
	}
	
	// string starts with blank: true for it does not, false for it does (false is a fail)
	public boolean stringValidiationCheckIfDoesNotStartWithEmptySpace(String field){
		if (field != null) {
			if (field.startsWith(" ")) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	// refer to patterns in private VARS, returns true if passed (all charters in pattern), false for failed (charters contained not in pattern )
	public boolean stringValidiationCharatersInPattern(String field, Pattern pattern){
		if (field != null) {
			m = pattern.matcher(field);
			if (m.find()) {
				// Charter not in pattern
				return false;
			} else {
				return true;
			}
		} else {
			return false; 
		}
	}
	
	/*
	 *  if field not in reserved words will return true for pass, false for fail contained in reserved words
	 *  If field null will auto fail
	 */
	public boolean stringValidiationDoesNotContainReservedWords(String field){
		if (field != null) {
			// populate Reserved Words
			populateReservedWords();
			if (reservedwords.contains(field.toUpperCase())) {
				return false;
			}else {
				return true;
			}
		} else {
			return false; 
		}
	}
	
	/*
	 *  if field not in reserved words will return true for pass, false for fail contained in reserved words
	 *  If field null will auto fail
	 */
	public boolean stringValidiationDoesNotContainRepeatList(String field){
		if (field != null) {
			// populate Reserved Words
			populateRepeatList();
			if (repeatList.contains(field.toUpperCase())) {
				return false;
			}else {
				return true;
			}
		} else {
			return false; 
		}
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
	public boolean addressValidiationDegreeNullBeforeDatePassed(Date date, String field){
		if (field != null) {
			return true;
		} else {
			// field null at this point
			if (date == null) {
				return false;
			} else {
				populateAddressDefaultDate();
				if (date.after(addressDefaultDate)) {
					return false;
				} else {
					return true;
				}
			}
		}
	}
	
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
	
	/*
	 * Employer Approval End Date field can not Blank or null if Employer Status ID is 2 (Active)
	 * if (file200Bean.getEmployerApprovalStatusEndDate() == null && Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 2) {
	 */
	
	/*
	 * Employer Approval End Date field must Blank or null if Employer Status ID is 1 (Legacy) or 3 (Inactive)
	 * if (file200Bean.getEmployerApprovalStatusEndDate() != null && Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 1 || Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 3) {
	 */
	
	/*
	 * Province Code may not be 'X' if Country Code is 'ZA'
	 * if (file200Bean.getCountryCode() == "ZA" && file200Bean.getProvinceCode() == "X") {
	 */
	
	/*
	 * Province Code must be 'X' if Country Code is not 'ZA'
	 * if (file200Bean.getCountryCode() != "ZA" && file200Bean.getProvinceCode() != "X") {
	 */
	
	/*
	 * Person Alternate Id contains a value, Alternate Id Type Id may not = '533'
	 * if (File400Bean.getPersonAlternateId() != null && File400Bean.getAlternativeIdType() == "533") {
	 */
	
	/*
	 * Person Alternate Id contains no value, Alternate Id Type Id must = '533'
	 * if (File400Bean.getPersonAlternateId() == null && File400Bean.getAlternativeIdType() != "533") {
	 */
}