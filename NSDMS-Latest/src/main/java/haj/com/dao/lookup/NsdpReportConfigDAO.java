package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.NsdpReportConfig;

public class NsdpReportConfigDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NsdpReportConfig
 	 * @author TechFinium 
 	 * @see    NsdpReportConfig
 	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> allNsdpReportConfig() throws Exception {
		return (List<NsdpReportConfig>)super.getList("select o from NsdpReportConfig o");
	}

	/**
	 * Get all NsdpReportConfig between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NsdpReportConfig
 	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> allNsdpReportConfig(Long from, int noRows) throws Exception {
	 	String hql = "select o from NsdpReportConfig o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NsdpReportConfig>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NsdpReportConfig
 	 * @return a {@link haj.com.entity.NsdpReportConfig}
 	 * @throws Exception global exception
 	 */
	public NsdpReportConfig findByKey(Long id) throws Exception {
	 	String hql = "select o from NsdpReportConfig o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NsdpReportConfig)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NsdpReportConfig by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NsdpReportConfig
  	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> findByName(String description) throws Exception {
	 	String hql = "select o from NsdpReportConfig o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NsdpReportConfig>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> findByFinancialYearsId(Long financialYearsId) throws Exception {
	 	String hql = "select o from NsdpReportConfig o where o.financialYears.id = :financialYearsId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("financialYearsId", financialYearsId);
		return (List<NsdpReportConfig>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> findByFinancialYearsIdWithOrderBy(Long financialYearsId) throws Exception {
	 	String hql = "select o from NsdpReportConfig o where o.financialYears.id = :financialYearsId  order by o.orderNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("financialYearsId", financialYearsId);
		return (List<NsdpReportConfig>)super.getList(hql, parameters);
	}
}

