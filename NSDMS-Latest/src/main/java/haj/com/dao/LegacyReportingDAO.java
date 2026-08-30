package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.LegacyReporting;
import haj.com.entity.LegacyReportingParams;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class LegacyReportingDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyReporting
 	 * @author TechFinium 
 	 * @see    LegacyReporting
 	 * @return a list of {@link haj.com.entity.LegacyReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReporting> allLegacyReporting() throws Exception {
		return (List<LegacyReporting>)super.getList("select o from LegacyReporting o");
	}

	/**
	 * Get all LegacyReporting between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyReporting
 	 * @return a list of {@link haj.com.entity.LegacyReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReporting> allLegacyReporting(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyReporting o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyReporting>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyReporting
 	 * @return a {@link haj.com.entity.LegacyReporting}
 	 * @throws Exception global exception
 	 */
	public LegacyReporting findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyReporting o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyReporting)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyReporting by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyReporting
  	 * @return a list of {@link haj.com.entity.LegacyReporting}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReporting> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyReporting o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyReporting>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyReporting> findByClassName(String className) throws Exception {
	 	String hql = "select o from LegacyReporting o where o.forClass = :forClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("forClass", className);
		return (List<LegacyReporting>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyReportingParams> findParams(LegacyReporting legacyReporting) throws Exception {
	 	String hql = "select o from LegacyReportingParams o where o.legacyReporting.id = :legacyReportingID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("legacyReportingID", legacyReporting.getId());
		return (List<LegacyReportingParams>)super.getList(hql, parameters);
	}
}

