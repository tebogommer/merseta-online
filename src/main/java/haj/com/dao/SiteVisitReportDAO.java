package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Company;
import haj.com.entity.SiteVisitReport;

public class SiteVisitReportDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SiteVisitReport
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> allSiteVisitReport() throws Exception {
		return (List<SiteVisitReport>) super.getList("select o from SiteVisitReport o");
	}

	/**
	 * Get all SiteVisitReport between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SiteVisitReport
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> allSiteVisitReport(Long from, int noRows) throws Exception {
		String hql = "select o from SiteVisitReport o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SiteVisitReport>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SiteVisitReport
	 * @return a {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             global exception
	 */
	public SiteVisitReport findByKey(Long id) throws Exception {
		String hql = "select o from SiteVisitReport o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SiteVisitReport) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SiteVisitReport by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SiteVisitReport
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> findByName(String description) throws Exception {
		String hql = "select o from SiteVisitReport o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SiteVisitReport>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> findByCompany(Company company) {
		String hql = "select o from SiteVisitReport o where o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		return (List<SiteVisitReport>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> locateSiteVisitReportBetweenDatesByVisitDate(Date fromDate, Date toDate, Long companyId){
		String hql = "select o from SiteVisitReport o where o.company.id = :companyId and date(o.visitDate) between date(:fromDate) and date(:toDate) order by o.visitDate desc" ;
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
	    parameters.put("fromDate", fromDate);
	    parameters.put("toDate", toDate);
	    return (List<SiteVisitReport>)super.getList(hql, parameters);
	}

}
