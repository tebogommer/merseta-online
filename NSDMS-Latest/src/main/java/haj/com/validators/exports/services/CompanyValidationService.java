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

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

/**
 * The Interface CSVAnnotation.
 */
public class CompanyValidationService extends AbstractService {

	private static CompanyValidationService companyValidationService;
	public static synchronized CompanyValidationService instance() {
		if (companyValidationService == null) {
			companyValidationService = new CompanyValidationService();
		}
		return companyValidationService;
	}
	
	public boolean companyNameValidation(String companyName) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (companyName.isEmpty()) {
				return false;
			}
			if (companyName.charAt(0) == ' ') {
				return false;
			}
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERNCOMPANYNAMETRADENAME, companyName.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(companyName.toUpperCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean companyTradingNameValidation(String tradingName) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (tradingName.isEmpty()) {
				return false;
			}
			if (tradingName.startsWith(" ")) {
				return false;
			}
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERNCOMPANYNAMETRADENAME, tradingName.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(tradingName.toUpperCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean contactNumberValidation(String contactNumber) {

		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON && !contactNumber.isEmpty()) {
			if (contactNumber.isEmpty()) {
				return false;
			}
			if (contactNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, contactNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(contactNumber)) {
				return false;
			}
		}
		return true;
	}

	public boolean faxNumberValidation(String faxNumber) {

		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (faxNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, faxNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(faxNumber)) {
				return false;
			}
		}

		return true;
	}

	public boolean emailValidation(String email) {

		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (email.isEmpty()) {
				return false;
			}

			if (email.startsWith(" ")) {
				return false;
			}
			if (!ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, email.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(email)) {
				return false;
			}
		}

		return true;
	}

	public boolean accreditationNumberValidation(String accreditationNumber) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON && !accreditationNumber.isEmpty()) {
			if (accreditationNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, accreditationNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(accreditationNumber)) {
				return false;
			}
		}
		return true;
	}

	public boolean siteNumberValidation(String companySiteNumber) {

		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (companySiteNumber.isEmpty()) {
				return false;
			}
	
			if (companySiteNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, companySiteNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(companySiteNumber)) {
				return false;
			}
		}

		return true;
	}

	public boolean companyWebsiteValidation(String companyWebsite) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON && !companyWebsite.isEmpty()) {
			if (companyWebsite.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, companyWebsite.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(companyWebsite)) {
				return false;
			}
		}

		return true;
	}
	
	public boolean sarsNumberValidation(String sarsNumber) {
		
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (sarsNumber.isEmpty()) {
				return false;
			}
			
			if (sarsNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, sarsNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(sarsNumber.toUpperCase())) {
				return false;
			}
		}
		
		return true;
	}

	public boolean companyRegValidation(String companyRegNumber) {

		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON) {
			if (companyRegNumber.isEmpty()) {
				return false;
			}
	
			if (companyRegNumber.startsWith(" ")) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, companyRegNumber)) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(companyRegNumber)) {
				return false;
			}
		}

		return true;
	}

	public boolean payeSDLNumberValidation(String payeSDLNumber) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON && !payeSDLNumber.isEmpty()) {
			if (payeSDLNumber.charAt(0) == ' ') {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, payeSDLNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(payeSDLNumber)) {
				return false;
			}
			if (!payeSDLNumber.startsWith("L") && !payeSDLNumber.startsWith("N")) {
				return false;
			}
			if (payeSDLNumber.length() != 10) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXLEVYNUMBER, payeSDLNumber.toUpperCase())) {
				return false;
			}
			if (payeSDLNumber.startsWith("L") && payeSDLNumber.trim().equals(ValidationConstants.LEVYNUMBERNOTEQUAL)) {
				return false;
			}
			if (payeSDLNumber.startsWith("L") && ( !ValidationConstants.LEVYNUMBERNUMBERONE.equals(String.valueOf(payeSDLNumber.charAt(4))) && !ValidationConstants.LEVYNUMBERNUMBERTWO.equals(String.valueOf(payeSDLNumber.charAt(4)))   ) ) {
				// LNNN*7*NNNNN 0R LNNN*8*NNNNN
				return false;
			}
		}
		return true;
	}

	public static boolean levyNumberValidation(String payeSDLNumber) {
		if (HAJConstants.COMPANY_SETMIS_VALIDATION_ON && !payeSDLNumber.isEmpty()) {
			if (payeSDLNumber.charAt(0) == ' ') {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, payeSDLNumber.toUpperCase())) {
				return false;
			}
			if (ValidationConstants.RESERVEDWORDS.contains(payeSDLNumber)) {
				return false;
			}
			
			  if (!payeSDLNumber.startsWith("L") && !payeSDLNumber.startsWith("N")) {
			  return false; }
			 //commenting below code on 16-march by vh
			
//			if (!payeSDLNumber.startsWith("L")) {
//				return false;
//			}
			
			if (payeSDLNumber.length() != 10) {
				return false;
			}
			if (ValidationConstants.matchPattern(ValidationConstants.REGEXLEVYNUMBER, payeSDLNumber.toUpperCase())) {
				return false;
			}
			if (payeSDLNumber.startsWith("L") && payeSDLNumber.trim().equals(ValidationConstants.LEVYNUMBERNOTEQUAL)) {
				return false;
			}
			if (payeSDLNumber.startsWith("L") && ( !ValidationConstants.LEVYNUMBERNUMBERONE.equals(String.valueOf(payeSDLNumber.charAt(4))) && !ValidationConstants.LEVYNUMBERNUMBERTWO.equals(String.valueOf(payeSDLNumber.charAt(4)))   ) ) {
				// LNNN*7*NNNNN 0R LNNN*8*NNNNN
				return false;
			}
		}
		return true;
	}
	
	/** testing Method */
	public static void main(String[] args) {
		try {
//			String payeSDLNumber = "N000100295";
//			Boolean passed = true;
//			if (payeSDLNumber.charAt(0) == ' ') {
//				passed = false;
//			}
//			if (ValidationConstants.matchPattern(ValidationConstants.REGEXNUMBERS, payeSDLNumber.toUpperCase())) {
//				passed = false;
//			}
//			if (ValidationConstants.RESERVEDWORDS.contains(payeSDLNumber)) {
//				passed = false;
//			}
//			if (!payeSDLNumber.startsWith("L") && !payeSDLNumber.startsWith("N")) {
//				passed = false;
//			}
//			if (payeSDLNumber.length() != 10) {
//				passed = false;
//			}
//			if (ValidationConstants.matchPattern(ValidationConstants.REGEXLEVYNUMBER, payeSDLNumber.toUpperCase())) {
//				passed = false;
//			}
//			if (payeSDLNumber.startsWith("L") && payeSDLNumber.trim().equals(ValidationConstants.LEVYNUMBERNOTEQUAL)) {
//				passed = false;
//			}
//			if (payeSDLNumber.startsWith("L") && ( !ValidationConstants.LEVYNUMBERNUMBERONE.equals(String.valueOf(payeSDLNumber.charAt(4))) && !ValidationConstants.LEVYNUMBERNUMBERTWO.equals(String.valueOf(payeSDLNumber.charAt(4)))   ) ) {
//				// LNNN*7*NNNNN 0R LNNN*8*NNNNN
//				passed = false;
//			}
//			System.out.println(passed);
		} catch (Exception e) {
		}
		
		try {
			List<IDataEntity> list = new ArrayList<>();
			list.add(new Company());
			for (IDataEntity iDataEntity : list) {
				if (iDataEntity instanceof Address) {
					System.out.println("Yes Address");
					Address new_name = (Address) iDataEntity;
				} else if (iDataEntity instanceof Company){
					System.out.println("Yes Comapny");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}