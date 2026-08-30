/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import java.util.Date;

import haj.com.dao.BlankDAO;
import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class AssessorModeratorApplicaitonValidationService extends AbstractService {
	
	private BlankDAO dao = new BlankDAO();
	
	private static AssessorModeratorApplicaitonValidationService assessorModeratorApplicaitonValidationService;
	public static synchronized AssessorModeratorApplicaitonValidationService instance() {
		if (assessorModeratorApplicaitonValidationService == null) {
			assessorModeratorApplicaitonValidationService = new AssessorModeratorApplicaitonValidationService();
		}
		return assessorModeratorApplicaitonValidationService;
	}

	public boolean validateStartDate(Date startDate) {
		return false;
	}

	public boolean validateEndDate(Date endDate) {
		return false;
	}

	public boolean validateCertificateNumber(String certificateNumber) {
		if (certificateNumber != null && !certificateNumber.isEmpty()) {
			// This field may be left blank
			if (certificateNumber.length() == 0) {
				return true;
			}
			// Field may not start with a space.
			if (certificateNumber.startsWith(" ")) {
				return false;
			}
			// Size.
			if (certificateNumber.trim().length() > 20) {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._-
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN, certificateNumber.toUpperCase())) {
				return false;
			}
			// Upper case value in field may not contain characters
			if (ValidationConstants.containsInResevredWords(certificateNumber.toUpperCase(), ValidationConstants.populateReservedWordsContains())) {
				return false;
			}
			// Upper case value in field may not equal characters
			if (ValidationConstants.equalsInResevredWords(certificateNumber.toUpperCase(), ValidationConstants.populateReservedWordsEquals())) {
				return false;
			}
		}
		return true;
	}

}
