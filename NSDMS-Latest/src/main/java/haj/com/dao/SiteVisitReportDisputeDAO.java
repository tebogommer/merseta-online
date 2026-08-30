package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportDispute;

public class SiteVisitReportDisputeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SiteVisitReportDispute
 	 * @author TechFinium 
 	 * @see    SiteVisitReportDispute
 	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportDispute> allSiteVisitReportDispute() throws Exception {
		return (List<SiteVisitReportDispute>)super.getList("select o from SiteVisitReportDispute o");
	}

	/**
	 * Get all SiteVisitReportDispute between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SiteVisitReportDispute
 	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportDispute> allSiteVisitReportDispute(Long from, int noRows) throws Exception {
	 	String hql = "select o from SiteVisitReportDispute o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SiteVisitReportDispute>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SiteVisitReportDispute
 	 * @return a {@link haj.com.entity.SiteVisitReportDispute}
 	 * @throws Exception global exception
 	 */
	public SiteVisitReportDispute findByKey(Long id) throws Exception {
	 	String hql = "select o from SiteVisitReportDispute o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SiteVisitReportDispute)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SiteVisitReportDispute by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SiteVisitReportDispute
  	 * @return a list of {@link haj.com.entity.SiteVisitReportDispute}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportDispute> findByName(String description) throws Exception {
	 	String hql = "select o from SiteVisitReportDispute o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SiteVisitReportDispute>)super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<SiteVisitReportDispute> findBySiteVisitReport(SiteVisitReport siteVisitReport) throws Exception {
	 	String hql = "select o from SiteVisitReportDispute o where o.siteVisitReport.id = :siteVisitReportID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("siteVisitReportID", siteVisitReport.getId());
		return (List<SiteVisitReportDispute>)super.getList(hql, parameters);
	}
}

