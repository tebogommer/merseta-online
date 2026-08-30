package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.NsdpQuarterReporting;

public class NsdpQuarterReportingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NsdpQuarterReporting
 	 * @author TechFinium 
 	 * @see    NsdpQuarterReporting
 	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> allNsdpQuarterReporting() throws Exception {
		return (List<NsdpQuarterReporting>)super.getList("select o from NsdpQuarterReporting o");
	}

	/**
	 * Get all NsdpQuarterReporting between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NsdpQuarterReporting
 	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> allNsdpQuarterReporting(Long from, int noRows) throws Exception {
	 	String hql = "select o from NsdpQuarterReporting o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NsdpQuarterReporting>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NsdpQuarterReporting
 	 * @return a {@link haj.com.entity.NsdpQuarterReporting}
 	 * @throws Exception global exception
 	 */
	public NsdpQuarterReporting findByKey(Long id) throws Exception {
	 	String hql = "select o from NsdpQuarterReporting o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NsdpQuarterReporting)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NsdpQuarterReporting by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NsdpQuarterReporting
  	 * @return a list of {@link haj.com.entity.NsdpQuarterReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> findByName(String description) throws Exception {
	 	String hql = "select o from NsdpQuarterReporting o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NsdpQuarterReporting>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> findByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
	 	String hql = "select o from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nsdpReportConfigId", nsdpReportConfigId);
		return (List<NsdpQuarterReporting>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpQuarterReporting> findByNsdpReportConfigIdOrderedByQuarter(Long nsdpReportConfigId) throws Exception {
	 	String hql = "select o from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId order by o.finYearQuarters" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nsdpReportConfigId", nsdpReportConfigId);
		return (List<NsdpQuarterReporting>)super.getList(hql, parameters);
	}
	
	public int countByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
	 	String hql = "select count(o) from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nsdpReportConfigId", nsdpReportConfigId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer sumTotalAchivedByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
	 	String hql = "select sum(o.quarterAchivedAmount) from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId and o.quarterAchivedAmount is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nsdpReportConfigId", nsdpReportConfigId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer sumTargetsByNsdpReportConfigId(Long nsdpReportConfigId) throws Exception {
	 	String hql = "select sum(o.quarterTargetAmount) from NsdpQuarterReporting o where o.nsdpReportConfig.id = :nsdpReportConfigId and o.quarterTargetAmount is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nsdpReportConfigId", nsdpReportConfigId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

