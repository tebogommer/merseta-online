package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;

public class LegacyEmployerWA2WorkplaceDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyEmployerWA2Workplace
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2Workplace
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2Workplace() throws Exception {
		return (List<LegacyEmployerWA2Workplace>)super.getList("select o from LegacyEmployerWA2Workplace o");
	}

	/**
	 * Get all LegacyEmployerWA2Workplace between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyEmployerWA2Workplace
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2Workplace(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Workplace o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyEmployerWA2Workplace>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyEmployerWA2Workplace
 	 * @return a {@link haj.com.entity.LegacyEmployerWA2Workplace}
 	 * @throws Exception global exception
 	 */
	public LegacyEmployerWA2Workplace findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Workplace o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyEmployerWA2Workplace)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyEmployerWA2Workplace by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyEmployerWA2Workplace
  	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Workplace> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2Workplace o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyEmployerWA2Workplace>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyEmployerWA2Workplace o")).intValue();
	}
	
	public Integer countAllLegacyEmployerWA2WorkplaceNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyEmployerWA2Workplace o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2WorkplaceNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyEmployerWA2Workplace o where o.processed = false";
		return (List<LegacyEmployerWA2Workplace>) super.getList(hql,  numberOfEntries);
	}
	
}

