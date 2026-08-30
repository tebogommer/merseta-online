package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.SiteVisit;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SiteVisitDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SiteVisit
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisit> allSiteVisit() throws Exception {
		return (List<SiteVisit>) super.getList("select o from SiteVisit o");
	}

	/**
	 * Get all SiteVisit between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SiteVisit
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisit> allSiteVisit(Long from, int noRows) throws Exception {
		String hql = "select o from SiteVisit o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SiteVisit>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SiteVisit
	 * @return a {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	public SiteVisit findByKey(Long id) throws Exception {
		String hql = "select o from SiteVisit o left join fetch o.company where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SiteVisit) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find List of object by company id order by visitDate
	 * 
	 * @author TechFinium
	 * @param companyId
	 *            the company id
	 * @see SiteVisit
	 * @return List {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisit> findByCompany(Long companyId) throws Exception {
		String hql = "select o from SiteVisit o left join fetch o.company where o.company.id = :companyId order by o.visitDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<SiteVisit>) super.getList(hql, parameters);
	}

	/**
	 * Find List of object by company id order by visitDate
	 * 
	 * @author TechFinium
	 * @param companyId
	 *            the company id
	 * @see SiteVisit
	 * @return List {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	public long findByCompanyCount(Long companyId) throws Exception {
		String hql = "select count(o) from SiteVisit o left join fetch o.company where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SiteVisit by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SiteVisit
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisit> findByName(String description) throws Exception {
		String hql = "select o from SiteVisit o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SiteVisit>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SiteVisit> allSiteVisit(Company company, Date date) throws Exception {
		String hql = "select o from SiteVisit o where o.company.id = :companyID and date(o.visitDate) > date(:lastDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("lastDate", date);
		return (List<SiteVisit>) super.getList(hql, parameters);
	}

	public long allSiteVisitCount(Company company, Date date) throws Exception {
		String hql = "select count(o) from SiteVisit o where o.company.id = :companyID and date(o.visitDate) > date(:lastDate)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		parameters.put("lastDate", date);
		return (long) super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SiteVisit> locateSiteVisitBetweenDatesByVisitDate(Date fromDate, Date toDate, Long companyId)
			throws Exception {
		String hql = "select o from SiteVisit o where o.company.id = :companyID and date(o.visitDate) between date(:fromDate) and date(:toDate) order by o.visitDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", companyId);
		parameters.put("fromDate", fromDate);
		parameters.put("toDate", toDate);
		return (List<SiteVisit>) super.getList(hql, parameters);
	}
}
