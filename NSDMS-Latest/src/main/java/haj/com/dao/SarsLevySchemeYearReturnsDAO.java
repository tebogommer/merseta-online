package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SarsLevySchemeYearReturns;

public class SarsLevySchemeYearReturnsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsLevySchemeYearReturns
 	 * @author TechFinium 
 	 * @see    SarsLevySchemeYearReturns
 	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevySchemeYearReturns> allSarsLevySchemeYearReturns() throws Exception {
		return (List<SarsLevySchemeYearReturns>)super.getList("select o from SarsLevySchemeYearReturns o");
	}

	/**
	 * Get all SarsLevySchemeYearReturns between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SarsLevySchemeYearReturns
 	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevySchemeYearReturns> allSarsLevySchemeYearReturns(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsLevySchemeYearReturns o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsLevySchemeYearReturns>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SarsLevySchemeYearReturns
 	 * @return a {@link haj.com.entity.SarsLevySchemeYearReturns}
 	 * @throws Exception global exception
 	 */
	public SarsLevySchemeYearReturns findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsLevySchemeYearReturns o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLevySchemeYearReturns)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SarsLevySchemeYearReturns by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SarsLevySchemeYearReturns
  	 * @return a list of {@link haj.com.entity.SarsLevySchemeYearReturns}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevySchemeYearReturns> findByName(String description) throws Exception {
	 	String hql = "select o from SarsLevySchemeYearReturns o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsLevySchemeYearReturns>)super.getList(hql, parameters);
	}
	
	public Integer countByYear(Integer year) throws Exception {
	 	String hql = "select count(o) from SarsLevySchemeYearReturns o where o.forSchemeYear = :year " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByYearExcludingId(Integer year, Long id) throws Exception {
	 	String hql = "select count(o) from SarsLevySchemeYearReturns o where o.forSchemeYear = :year and o.id <> :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("id", id);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

