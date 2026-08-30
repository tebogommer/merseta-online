/*
 *	Programmer: wesley
 *	Date: 20 Jul 2017
 *	Project: WesleyUtilities
 *	Package: com.wesley.utils.csv
 *	Using JRE: 1.8.0_73
*/
package haj.com.validators.exports.services;

import haj.com.dao.BlankDAO;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

/**
 * The Interface CSVAnnotation.
 */
public class NLRDValidationService extends AbstractService {
	private BlankDAO dao = new BlankDAO();

	public boolean validateIDNumber(Long idNUmber) {
		return idNUmber == null;
	}

	public boolean validateCompanyName(String idNUmber) {
		return idNUmber != null;
	}

	public boolean validateAddressLine1(String idNUmber) {
		return idNUmber != null;
	}
}
