/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class ValidationConstants extends AbstractService {

	public static final String       PATTERN              			= "[A-Z0-9@\\s#&+()/\\\\:._-]+";
	public static final String       REGEX                			= "[^A-Z0-9@#&+\\(\\)\\s/\\\\:\\._\\-]";
	public static final String       REGEXNUMBERS         			= "[^0-9()/-]";
	public static final String       REGEXLATITUDE        			= "[^0-9-]";
	public static final String       REGEXLATITUDESECONDS 			= "[^0-9.]";
	public static final String       REGEXEMAIL           			= "[^A-Z0-9_.<>-@]";
	public static final String       REGEXSDLNUMBER       			= "[^LN0-9]";
	public static final String       REGEXLEVYNUMBER      			= "[^L0-9]";
	public static final String       LEVYNUMBERNOTEQUAL  			= "L000000000";
	public static final String       LEVYNUMBERNUMBERONE 			= "7";
	public static final String       LEVYNUMBERNUMBERTWO  		 	= "8";
																	// ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`',
																	// ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._,`'-
	public static final String       PATTERNCOMPANYNAMETRADENAME 	= "[A-Z0-9'@\\\\\\s#&\\+\\(\\)/:\\.\\_\\-\\,`]+";
	public static final String       PATTERNADDRESSCONTAINS			= "[A-Z0-9'\\\\\\s#&\\+\\(\\)/:\\.\\_\\-\\,`]+";
	public static final List<String> RESERVEDWORDS        			= populateReservedWords();
	public static final List<String> REPEATLIST           			= populateRepeatList();
	
	
	/* Jonathan's Updates to Validation Updates Start */
	
	// Contents / Value of PATTERN_ONE: ABCDEFGHIJKLMNOPQRTSUVWXYZ`'-
	public static final String			PATTERN_ONE						= "[A-Zs`'-]+";
	// Contents / Value of PATTERN_TWO: ABCDEFGHIJKLMNOPQRTSUVWXYZ`' - (Includes Space)
	public static final String			PATTERN_TWO						= "[A-Zs`' -]+";
	// Contents / Value of PATTERN_THREE: 0123456789
	public static final String       	PATTERN_THREE         			= "[0-9]+";
	// Contents / Value of PATTERN_THREE: ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`',
	public static final String       	PATTERN_FOUR        			= "[A-Z0-9'\\\\\\s#&\\+\\(\\)/:\\.\\_\\-\\,` -]+";
	// Contents / Value of PATTERN_FIVE: 4 consecutive numbers next to each other 
	public static final String       	PATTERN_FIVE        			= ".*\\d{4}.*";
	// Contents / Value of PATTERN_FIVE: 1234567890 ()/-
	public static final String       	PATTERN_SIX        			= "[0-9 \\\\s()/-]+";
	/* Jonathan's Updates to Validation Updates End */
	

	private static List<String> populateReservedWords() {
		List<String> reservedwords = new ArrayList<>();
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
		reservedwords.add("O");
		reservedwords.add("TEST");
		reservedwords.add("ONTBREEK");
		reservedwords.add("NIL");
		reservedwords.add("NILL");
		reservedwords.add("-");
		reservedwords.add("ZZZ");
		reservedwords.add("XXX");
		reservedwords.add("ADDRES");
		reservedwords.add("n/a");
		return reservedwords;
	}

	private static List<String> populateRepeatList() {
		List<String> repeatList = new ArrayList<>();
		repeatList.add("1111111111111");
		repeatList.add("2222222222222");
		repeatList.add("3333333333333");
		repeatList.add("4444444444444");
		repeatList.add("5555555555555");
		repeatList.add("6666666666666");
		repeatList.add("7777777777777");
		repeatList.add("8888888888888");
		repeatList.add("9999999999999");
		return repeatList;
	}

	public static boolean matchPattern(String regex, String field) {
		Pattern patern = Pattern.compile(regex);
		return patern.matcher(field.toUpperCase()).matches();
	}
	
	/**
	 * SETMIS Validations 2018 04 10
	 * Populates Reserved Words For SETMIS Validations contains: 
	 * 
	 * User Information
	 * - First name
	 * - Middle Name
	 * - Last Name
	 * 
	 * Address Information
	 * - Address Line 1
	 * - Address Line 2
	 * - Address Line 3
	 * 
	 * @return reservedWordsList
	 * 			The list of reserved words which will be compared to values
	 */
	public static List<String> populateReservedWordsContains() {
		List<String> reservedWordsList = new ArrayList<>();
		reservedWordsList.add("UNKNOWN");
		reservedWordsList.add("AS ABOVE");
		reservedWordsList.add("SOOS BO");
		reservedWordsList.add("DELETE");
		reservedWordsList.add("ONTBREEK");
		return reservedWordsList;
	}
	
	/**
	 * SETMIS Validations 2018 04 10
	 * Populates Reserved Words For SETMIS Validations contains: 
	 * 
	 * Address Information
	 * - Address Line 1
	 * - Address Line 2
	 * - Address Line 3
	 * 
	 * @return reservedWordsList
	 * 			The list of reserved words which will be compared to values
	 */
	public static List<String> populateReservedWordsContainsAddress() {
		List<String> reservedWordsList = new ArrayList<>();
		reservedWordsList.add("ZZZ");
		reservedWordsList.add("XXX");
		reservedWordsList.add("ADDRES");
		return reservedWordsList;
	}
	
	/**
	 * SETMIS Validations 2018 04 10
	 * Populates Reserved Words For SETMIS Validations equals: 
	 * 
	 * User Information
	 * - First name
	 * - Middle Name
	 * - Last Name
	 * 
	 * Address Information
	 * - Address Line 1
	 * - Address Line 2
	 * - Address Line 3
	 * 
	 * @return reservedWordsList
	 * 			The list of reserved words which will be compared to values
	 */
	public static List<String> populateReservedWordsEquals() {
		List<String> reservedWordsList = new ArrayList<>();
		reservedWordsList.add("N/A");
		reservedWordsList.add("0");
		reservedWordsList.add("TEST");
		reservedWordsList.add("NIL");
		reservedWordsList.add("-");
		reservedWordsList.add("–");
		reservedWordsList.add("NA");
		reservedWordsList.add("U");
		reservedWordsList.add("NONE");
		reservedWordsList.add("GEEN");
		return reservedWordsList;
	}
	
	/**
	 * Validation to see if value contains any reserved words for SETMIS compliance
	 * 
	 * @param value
	 * 			The value to compare reserved words
	 * @param reseveredWords 
	 * 			List of reserved words
	 * @return doesContain
	 * 			if value contains any reserved words will return true for fail
	 */
	public static boolean containsInResevredWords(String value, List<String> reservedWords){
		boolean doesContain = false;
		for (String reservedWord : reservedWords) {
			if (value.trim().toUpperCase().contains(reservedWord.toUpperCase())) {
				doesContain = true;
				break;
			}
		}
		return doesContain;
	}
	
	/**
	 * Validation to see if value equals any reserved words for SETMIS compliance
	 * 
	 * @param value
	 * 			The value to compare reserved words
	 * @param reseveredWords 
	 * 			List of reserved words
	 * @return doesEqual
	 * 			if value equals any reserved words will return true for fail
	 */
	public static boolean equalsInResevredWords(String value, List<String> reservedWords){
		boolean doesEqual = false;
		for (String reservedWord : reservedWords) {
			if (value.trim().equalsIgnoreCase(reservedWord.trim().toUpperCase())) {
				doesEqual = true;
				break;
			}
		}
		return doesEqual;
	}
	
	public static void main(String[] args) {
		String name = "00 01";
		boolean passed = true;
		if (name.startsWith(" ")) {
			passed = false;
		}
		if (ValidationConstants.matchPattern("(?<!\\d)\\d{4}(?!\\d)", name.toUpperCase())) {
			passed = false;
		}
		
		if (passed) {
			// removed print line of result
		}
	}

}