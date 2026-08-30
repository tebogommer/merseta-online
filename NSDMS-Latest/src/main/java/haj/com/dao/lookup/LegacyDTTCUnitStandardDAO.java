package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyDTTCUnitStandard;

public class LegacyDTTCUnitStandardDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyDTTCUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyDTTCUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandard() throws Exception {
		return (List<LegacyDTTCUnitStandard>)super.getList("select o from LegacyDTTCUnitStandard o");
	}

	/**
	 * Get all LegacyDTTCUnitStandard between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyDTTCUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyDTTCUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyDTTCUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyDTTCUnitStandard
 	 * @return a {@link haj.com.entity.LegacyDTTCUnitStandard}
 	 * @throws Exception global exception
 	 */
	public LegacyDTTCUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyDTTCUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyDTTCUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyDTTCUnitStandard by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyDTTCUnitStandard
  	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyDTTCUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyDTTCUnitStandard>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyDTTCUnitStandard o")).intValue();
	}
	
	public Integer countAllLegacyDTTCUnitStandardNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyDTTCUnitStandard o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandardNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyDTTCUnitStandard o where o.processed = false";
		return (List<LegacyDTTCUnitStandard>) super.getList(hql, numberOfEntries);
	}
}

