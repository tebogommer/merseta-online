package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SitesSmeCompanyLearners;
import haj.com.entity.ExtractErrorList;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesSmeCompanyLearnersDAO.
 */
public class SitesSmeCompanyLearnersDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SitesSmeCompanyLearners.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception
	 *             global exception
	 * @see SitesSmeCompanyLearners
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSmeCompanyLearners> allSitesSmeCompanyLearners() throws Exception {
		return (List<SitesSmeCompanyLearners>) super.getList("select o from SitesSmeCompanyLearners o");
	}

	/**
	 * Get all SitesSmeCompanyLearners between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception
	 *             global exception
	 * @see SitesSmeCompanyLearners
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSmeCompanyLearners> allSitesSmeCompanyLearners(Long from, int noRows) throws Exception {
		String hql = "select o from SitesSmeCompanyLearners o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SitesSmeCompanyLearners>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception
	 *             global exception
	 * @see SitesSmeCompanyLearners
	 */
	public SitesSmeCompanyLearners findByKey(Long id) throws Exception {
		String hql = "select o from SitesSmeCompanyLearners o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SitesSmeCompanyLearners) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SitesSmeCompanyLearners by reasonLearnerNotAvalaible.
	 *
	 * @author TechFinium
	 * @param reasonLearnerNotAvalaible
	 *            the reasonLearnerNotAvalaible
	 * @return a list of {@link haj.com.entity.SitesSmeCompanyLearners}
	 * @throws Exception
	 *             global exception
	 * @see SitesSmeCompanyLearners
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSmeCompanyLearners> findByName(String reasonLearnerNotAvalaible) throws Exception {
		String hql = "select o from SitesSmeCompanyLearners o where o.reasonLearnerNotAvalaible like  :reasonLearnerNotAvalaible order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("reasonLearnerNotAvalaible", "" + reasonLearnerNotAvalaible.trim() + "%");
		return (List<SitesSmeCompanyLearners>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExtractErrorList> findExportErrors() throws Exception {
		String hql = "select o from ExtractErrorList o";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<ExtractErrorList>) super.getList(hql, parameters);
	}
}
