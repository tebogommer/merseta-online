package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SummativeAssessmentReportUnitStandardsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SummativeAssessmentReportUnitStandards
 	 * @author TechFinium 
 	 * @see    SummativeAssessmentReportUnitStandards
 	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> allSummativeAssessmentReportUnitStandards() throws Exception {
		return (List<SummativeAssessmentReportUnitStandards>)super.getList("select o from SummativeAssessmentReportUnitStandards o");
	}

	/**
	 * Get all SummativeAssessmentReportUnitStandards between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SummativeAssessmentReportUnitStandards
 	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> allSummativeAssessmentReportUnitStandards(Long from, int noRows) throws Exception {
	 	String hql = "select o from SummativeAssessmentReportUnitStandards o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SummativeAssessmentReportUnitStandards>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SummativeAssessmentReportUnitStandards
 	 * @return a {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
 	 * @throws Exception global exception
 	 */
	public SummativeAssessmentReportUnitStandards findByKey(Long id) throws Exception {
	 	String hql = "select o from SummativeAssessmentReportUnitStandards o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SummativeAssessmentReportUnitStandards)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> findBySummativeAssessmentReport(Long summativeAssessmentReportId) throws Exception {
	 	String hql = "select o from SummativeAssessmentReportUnitStandards o where o.summativeAssessmentReport.id = :summativeAssessmentReportId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("summativeAssessmentReportId", summativeAssessmentReportId);
		return (List<SummativeAssessmentReportUnitStandards>)super.getList(hql, parameters);
	}

	/**
	 * Find SummativeAssessmentReportUnitStandards by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SummativeAssessmentReportUnitStandards
  	 * @return a list of {@link haj.com.entity.SummativeAssessmentReportUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SummativeAssessmentReportUnitStandards> findByName(String description) throws Exception {
	 	String hql = "select o from SummativeAssessmentReportUnitStandards o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SummativeAssessmentReportUnitStandards>)super.getList(hql, parameters);
	}

	public Long countUnitStandards(Long unitStandardsID, Long summativeAssessmentReportID) throws Exception{
		String hql = "select count(o) from  SummativeAssessmentReportUnitStandards o where o.unitStandards.id = :unitStandardsID and o.summativeAssessmentReport.id = :summativeAssessmentReportID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("unitStandardsID", unitStandardsID);
	    parameters.put("summativeAssessmentReportID", summativeAssessmentReportID);
		return (long) ((Long) getUniqueResult(hql, parameters)).intValue();	
	}
}

