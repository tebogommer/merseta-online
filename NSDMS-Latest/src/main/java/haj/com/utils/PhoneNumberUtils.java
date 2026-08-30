package haj.com.utils;



import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.MatchType;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

// TODO: Auto-generated Javadoc
/**
 * The Class PhoneNumberUtils.
 */
public class PhoneNumberUtils implements Serializable {

	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(PhoneNumberUtils.class);

	
	/**
	 * Takes a String containing a phone number and corrects the format of the number using the countryCode.
	 *
	 * @param phoneNumber the phone number
	 * @param countryCode the country code
	 * @return the phone number
	 * @throws Exception the exception
	 */
	public static PhoneNumber formatPhoneNumber(String phoneNumber, String countryCode) throws Exception{
		
		PhoneNumber swissNumberProto = null;

		if (countryCode ==null || countryCode.length()==0) countryCode = "ZA";

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			swissNumberProto = phoneUtil.parse(phoneNumber, countryCode);			

		} catch (Exception e) {
			logger.fatal(e.getMessage());	
		}
		return swissNumberProto;
	}
	
	/**
	 * Converts PhoneNumber to a String.
	 *
	 * @param phoneNumber the phone number
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String toString(PhoneNumber phoneNumber) throws Exception{
		String convertedNumber = "";
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		
		try {			
			convertedNumber = phoneUtil.format(phoneNumber,  PhoneNumberFormat.E164);

		} catch (Exception e) {
			logger.fatal(e.getMessage());	
		}
		return convertedNumber;
	}

	/**
	 * 	
	 *
	 * @param phoneNumber the phone number
	 * @param countryCode default to ZA
	 * @return converted
	 * @throws Exception the exception
	 */
	public static String convertNumberToInternalFormat(String phoneNumber,String countryCode) throws Exception {	
		
		
		return toString(formatPhoneNumber(phoneNumber, countryCode));
		
	
	}
	
	
	/**
	 * Verifies that the Phone Number supplied is actually a mobile number.
	 *
	 * @param phoneNumber the phone number
	 * @param countryCode the country code
	 * @return True if the number supplied is a mobile number
	 * @throws Exception the exception
	 */
	public static boolean isMobileNumber(String phoneNumber, String countryCode) throws Exception{
		
		return isMobileNumber(formatPhoneNumber(phoneNumber, countryCode));
	}
	
	/**
	 * Verifies that the Phone Number supplied is actually a mobile number.
	 *
	 * @param phoneNumber the phone number
	 * @return True if the number supplied is a mobile number
	 * @throws Exception the exception
	 */
	public static boolean isMobileNumber(PhoneNumber phoneNumber) throws Exception{
		boolean valid = false;
		PhoneNumberType phoneNumberType = null;

		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {

			phoneNumberType = phoneUtil.getNumberType(phoneNumber);	

			if(!isShortCode(phoneNumber)){
				//Make sure the phone number supplied is a mobile number
				if(phoneNumberType == PhoneNumberType.MOBILE || phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE)	{
					valid = true;
				}
			}

		} catch (Exception e) {
			logger.fatal(e.getMessage());	
		}
		
		return valid;
	}
	
	/**
	 * Full validation of a phone number for a region using length and prefix information.
	 *
	 * @param phoneNumber the phone number
	 * @return true, if is valid number
	 * @throws Exception the exception
	 */
	public static boolean isValidNumber(PhoneNumber phoneNumber) throws Exception{
		boolean valid = false;
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();		
		
		//Dont let short codes through!!!!
		if(!isShortCode(phoneNumber)){			
			valid = phoneUtil.isValidNumber(phoneNumber);	
		}
		
		return valid;
	}
	
	/**
	 *  Full validation of a phone number for a region using length and prefix information.
	 *
	 * @param phoneNumber the phone number
	 * @param countryCode the country code
	 * @return true, if is valid number
	 * @throws Exception the exception
	 */
	public static boolean isValidNumber(String phoneNumber, String countryCode) throws Exception{
		
		return isValidNumber(formatPhoneNumber(phoneNumber, countryCode));
	}
	
	/**
	 * checks if a number is a short code.
	 *
	 * @param phoneNumber the phone number
	 * @return true, if is short code
	 */
	public static boolean isShortCode(PhoneNumber phoneNumber){
		boolean valid = false;	
		//Short codes are normally 6 digits or less
		if(phoneNumber.getNationalNumber() <= 999999){			
			valid = true;	
		}

		return valid;
	}
	
	/**
	 * checks if a number is a short code.
	 *
	 * @param phoneNumber the phone number
	 * @param countryCode the country code
	 * @return true, if is short code
	 * @throws Exception the exception
	 */
	public static boolean isShortCode(String phoneNumber, String countryCode)  throws Exception{

		return isShortCode(formatPhoneNumber(phoneNumber, countryCode));
	}
	
	
	/**
	 *  Check if the two numbers supplied are an EXACT match.
	 *
	 * @param phoneNumber1 the phone number 1
	 * @param phoneNumber2 the phone number 2
	 * @return TRUE if numbers are an exact match.
	 */
	public static boolean isNumberMatch(String phoneNumber1, String phoneNumber2){
		boolean valid = false;
		MatchType matchType = null;
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();	
		matchType = phoneUtil.isNumberMatch(phoneNumber1, phoneNumber2);
		
		if(matchType == MatchType.EXACT_MATCH){
			valid = true;
		}

		return valid;
	}
	
	/**
	 * Method to check that no other active user has the same mobile number.
	 *
	 * @param phoneNumber the phone number
	 * @throws Exception the exception
	 */
/*	public static boolean matchMobileNumberInSystem(String phoneNumber, String countryCode) throws Exception{

		return matchMobileNumberInSystem(formatPhoneNumber(phoneNumber, countryCode));
	}
*/	
	/**
	 * Method to check that no other active user has the same mobile number.
	 * 
	 * @param mobileNumberToValidate
	 * @param CountryCode
	 * @return
	 * @throws Exception
	 */
/*	public static boolean matchMobileNumberInSystem(PhoneNumber phoneNumber) throws Exception{

		boolean cellAlreadyExists = false;
		UsersDAO userDAO = new UsersDAO();
		
		String mobileNumberToValidate = toString(phoneNumber);
		
		List<Users>users = (List) userDAO.getValues("select o from Users o where o.internalSearchCellNumber='" + mobileNumberToValidate + "' and o.status='" + EsignConstants.USER_STATUS_ACTIVE + "'");
		for (Users u : users) {
			if(isNumberMatch(u.getInternalSearchCellNumber(), mobileNumberToValidate)){
			//if (u.getInternalSearchCellNumber().equalsIgnoreCase(mobileNumberToValidate)) {
				cellAlreadyExists = true;
				break;
			}
		}

		return cellAlreadyExists;
	}
	*/
	/**
	 * 
	 * 
	 * @param mobileNumberToValidate
	 * @throws javax.faces.validator.ValidatorException
	 */
	private static void mobileNumberValidator(PhoneNumber phoneNumber) throws Exception{ 
		String message = "";
		
		try{
			if(isShortCode(phoneNumber)){ //Has the user entered in a shot code?
				message = "You've supplied a short code, we do not accept short codes as mobile number.";				
				
			}
			else if(!isValidNumber(phoneNumber)){ //Has the user entered in a valid number?
				message = "The mobile number supplied is not a valid number, please check that you enterd in the correct number.";				
				
			}
			else if(!isMobileNumber(phoneNumber)){ //Has the user entered in a mobile number?
				message = "Number supplied is not a valid mobile number, please check you have entered in a mobile number and not for example a fixed line number.";
								
			}	
		/*	else if((matchMobileNumberInSystem(phoneNumber))){ //Is the number already in the system?
				message = "Mobile number is already registered, please enter another number.";
				facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				
			}
			*/
		}
		catch(Exception e){ //Something went very wrong :(
			logger.error(e);
			message =	"We could not parse the number supplied! If the number is correct please report it to the helpdesk.";
		}	
		
		if(!message.isEmpty()){ //Did something fail validation?
				throw new Exception(message);
		}
	}
	
	
	/**
	 * Use this method on primefaces input text controls to validate the phone number supplied by the user.
	 *
	 * @param phoneNumber the phone number
	 * @throws ValidatorException the validator exception
	 */
	public static void primefacesMobileNumberValidator(PhoneNumber phoneNumber) throws javax.faces.validator.ValidatorException{ 
		
		
		try{
			mobileNumberValidator(phoneNumber);
		}catch(javax.faces.validator.ValidatorException ve){
			throw ve;
		}catch (Exception e) {
			logger.error(e);
		}
		

	}
	
	/**
	 * Use this method on primefaces input text controls to validate the phone number supplied by the user.
	 *
	 * @param mobileNumberToValidate the mobile number to validate
	 * @param CountryCode the country code
	 * @throws ValidatorException the validator exception
	 */
	public static void primefacesMobileNumberValidator(String mobileNumberToValidate, String CountryCode) throws javax.faces.validator.ValidatorException{ 
		FacesMessage message = null;
		
		try{
			primefacesMobileNumberValidator(formatPhoneNumber(mobileNumberToValidate, CountryCode));
		}
		catch(javax.faces.validator.ValidatorException ve){
			throw ve;
		}
		catch(Exception e){ //Something went very wrong :(
			logger.error(e);
			message =	new FacesMessage("We could not parse the number supplied! I the number is correct please report it to the helpdesk.");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			throw new javax.faces.validator.ValidatorException(message, null);
		}
		
	}

	
	/**
	 * Cell number validator.
	 *
	 * @param mobileNumberToValidate the mobile number to validate
	 * @param CountryCode the country code
	 * @throws Exception the exception
	 */
	public static void cellNumberValidator(String mobileNumberToValidate, String CountryCode) throws Exception {
		
		mobileNumberValidator(formatPhoneNumber(mobileNumberToValidate, CountryCode));
		

		
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Start");
			String cn = PhoneNumberUtils.convertNumberToInternalFormat("917-378-7631","US");
			System.out.println(cn.substring(2));
			System.out.println(PhoneNumberUtils.isNumberMatch(PhoneNumberUtils.convertNumberToInternalFormat("+19173787631","US"), PhoneNumberUtils.convertNumberToInternalFormat("+19173787631","US")));
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
