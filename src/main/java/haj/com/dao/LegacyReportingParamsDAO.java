package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.LegacyReportingParams;

public class LegacyReportingParamsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyReportingParams
 	 * @author TechFinium 
 	 * @see    LegacyReportingParams
 	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReportingParams> allLegacyReportingParams() throws Exception {
		return (List<LegacyReportingParams>)super.getList("select o from LegacyReportingParams o");
	}

	/**
	 * Get all LegacyReportingParams between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyReportingParams
 	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReportingParams> allLegacyReportingParams(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyReportingParams o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyReportingParams>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyReportingParams
 	 * @return a {@link haj.com.entity.LegacyReportingParams}
 	 * @throws Exception global exception
 	 */
	public LegacyReportingParams findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyReportingParams o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyReportingParams)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyReportingParams by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyReportingParams
  	 * @return a list of {@link haj.com.entity.LegacyReportingParams}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyReportingParams> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyReportingParams o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyReportingParams>)super.getList(hql, parameters);
	}
}

