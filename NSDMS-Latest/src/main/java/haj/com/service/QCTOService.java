package haj.com.service;

import java.util.List;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.QCTO01Bean;
import haj.com.dataextract.bean.QCTO02Bean;
import haj.com.dataextract.bean.QCTO03Bean;
import haj.com.entity.Company;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class companyService.
 */
public class QCTOService extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	/**
	 * QCTO FILES
	 */

	public List<QCTO01Bean> extractQCTO01Bean(Company company) throws Exception {
		return dao.extractQCTO01Bean(company);
	}

	public List<QCTO02Bean> extractQCTO02Bean(Company company) throws Exception {
		return dao.extractQCTO02Bean(company);
	}

	public List<QCTO03Bean> extractQCTO03Bean(Company company) throws Exception {
		return dao.extractQCTO03Bean(company);
	}
}
