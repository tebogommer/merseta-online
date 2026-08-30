package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SarsLevyDetailCalculation;

public class SarsLevyDetailCalculationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SarsLevyDetailCalculation
 	 * @author TechFinium 
 	 * @see    SarsLevyDetailCalculation
 	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetailCalculation> allSarsLevyDetailCalculation() throws Exception {
		return (List<SarsLevyDetailCalculation>)super.getList("select o from SarsLevyDetailCalculation o");
	}

	/**
	 * Get all SarsLevyDetailCalculation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SarsLevyDetailCalculation
 	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetailCalculation> allSarsLevyDetailCalculation(Long from, int noRows) throws Exception {
	 	String hql = "select o from SarsLevyDetailCalculation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SarsLevyDetailCalculation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SarsLevyDetailCalculation
 	 * @return a {@link haj.com.entity.SarsLevyDetailCalculation}
 	 * @throws Exception global exception
 	 */
	public SarsLevyDetailCalculation findByKey(Long id) throws Exception {
	 	String hql = "select o from SarsLevyDetailCalculation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SarsLevyDetailCalculation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SarsLevyDetailCalculation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SarsLevyDetailCalculation
  	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetailCalculation> findByName(String description) throws Exception {
	 	String hql = "select o from SarsLevyDetailCalculation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SarsLevyDetailCalculation>)super.getList(hql, parameters);
	}
	
	public Integer countByYear(Integer year) throws Exception {
	 	String hql = "select count(o) from SarsLevyDetailCalculation o where o.forSchemeYear = :year " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByYearExcludingId(Integer year, Long id) throws Exception {
	 	String hql = "select count(o) from SarsLevyDetailCalculation o where o.forSchemeYear = :year and o.id <> :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("year", year);
	    parameters.put("id", id);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

