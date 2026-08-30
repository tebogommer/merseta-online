package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportSME;

public class SiteVisitReportSMEDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SiteVisitReportSME
 	 * @author TechFinium 
 	 * @see    SiteVisitReportSME
 	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportSME> allSiteVisitReportSME() throws Exception {
		return (List<SiteVisitReportSME>)super.getList("select o from SiteVisitReportSME o");
	}

	/**
	 * Get all SiteVisitReportSME between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SiteVisitReportSME
 	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportSME> allSiteVisitReportSME(Long from, int noRows) throws Exception {
	 	String hql = "select o from SiteVisitReportSME o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SiteVisitReportSME>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SiteVisitReportSME
 	 * @return a {@link haj.com.entity.SiteVisitReportSME}
 	 * @throws Exception global exception
 	 */
	public SiteVisitReportSME findByKey(Long id) throws Exception {
	 	String hql = "select o from SiteVisitReportSME o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SiteVisitReportSME)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SiteVisitReportSME by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SiteVisitReportSME
  	 * @return a list of {@link haj.com.entity.SiteVisitReportSME}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReportSME> findByName(String description) throws Exception {
	 	String hql = "select o from SiteVisitReportSME o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SiteVisitReportSME>)super.getList(hql, parameters);
	}

	public List<SiteVisitReportSME> findBySiteVisitReport(SiteVisitReport siteVisitReport) {
		String hql = "select o from SiteVisitReportSME o where o.siteVisitReport.id = :siteVisitReport" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("siteVisitReport", siteVisitReport.getId());
		return (List<SiteVisitReportSME>)super.getList(hql, parameters);
	}
}

