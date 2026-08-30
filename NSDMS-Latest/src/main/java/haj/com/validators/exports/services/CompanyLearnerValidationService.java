/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import haj.com.framework.AbstractService;

/**
 * The Interface CSVAnnotation.
 */
public class CompanyLearnerValidationService extends AbstractService {

	private static CompanyLearnerValidationService companyLearnerValidationService;
	public static synchronized CompanyLearnerValidationService instance() {
		if (companyLearnerValidationService == null) {
			companyLearnerValidationService = new CompanyLearnerValidationService();
		}
		return companyLearnerValidationService;
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
			if (certificateNumber.trim().length() > 30) {
				return false;
			}
			// Upper case value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._-
			if (!ValidationConstants.matchPattern(ValidationConstants.PATTERN, certificateNumber.toUpperCase())) {
				return false;
			}
		}
		return true;
	}
	
}