package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationSites;
import haj.com.entity.Sites;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class DgVerificationSitesDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgVerificationSites
	 * 
	 * @author TechFinium
	 * @see DgVerificationSites
	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> allDgVerificationSites() throws Exception {
		return (List<DgVerificationSites>) super.getList("select o from DgVerificationSites o");
	}

	/**
	 * Get all DgVerificationSites between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see DgVerificationSites
	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> allDgVerificationSites(Long from, int noRows) throws Exception {
		String hql = "select o from DgVerificationSites o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<DgVerificationSites>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see DgVerificationSites
	 * @return a {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception
	 *             global exception
	 */
	public DgVerificationSites findByKey(Long id) throws Exception {
		String hql = "select o from DgVerificationSites o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (DgVerificationSites) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgVerificationSites by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see DgVerificationSites
	 * @return a list of {@link haj.com.entity.DgVerificationSites}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> findByName(String description) throws Exception {
		String hql = "select o from DgVerificationSites o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<DgVerificationSites>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> findDgAndSite(DgVerification dgVerification, Sites sites) throws Exception {
		String hql = "select o from DgVerificationSites o left join fetch o.qualification q where o.dgVerification.id = :dgVerificationId and o.sites.id = :sitesId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sitesId", sites.getId());
		parameters.put("dgVerificationId", dgVerification.getId());
		return (List<DgVerificationSites>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerificationSites> findDgVerification(DgVerification dgVerification) throws Exception {
		String hql = "select o from DgVerificationSites o left join fetch o.qualification q where o.dgVerification.id = :dgVerificationId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("dgVerificationId", dgVerification.getId());
		return (List<DgVerificationSites>) super.getList(hql, parameters);
	}
}
