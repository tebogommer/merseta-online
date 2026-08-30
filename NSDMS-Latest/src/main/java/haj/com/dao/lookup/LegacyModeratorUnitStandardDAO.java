package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;

public class LegacyModeratorUnitStandardDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyModeratorUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyModeratorUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandard() throws Exception {
		return (List<LegacyModeratorUnitStandard>)super.getList("select o from LegacyModeratorUnitStandard o");
	}

	/**
	 * Get all LegacyModeratorUnitStandard between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyModeratorUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyModeratorUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyModeratorUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> findByModeratorRegNo(String moderatorRegNo) throws Exception {
	 	String hql = "select o from LegacyModeratorUnitStandard o where o.moderatorRegNo = :moderatorRegNo and o.unitStandard is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("moderatorRegNo", moderatorRegNo.trim());
		return (List<LegacyModeratorUnitStandard>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyModeratorUnitStandard
 	 * @return a {@link haj.com.entity.LegacyModeratorUnitStandard}
 	 * @throws Exception global exception
 	 */
	public LegacyModeratorUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyModeratorUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyModeratorUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyModeratorUnitStandard by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyModeratorUnitStandard
  	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyModeratorUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyModeratorUnitStandard>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyModeratorUnitStandardNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyModeratorUnitStandard o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandardNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyModeratorUnitStandard o where o.processed = false";
		return (List<LegacyModeratorUnitStandard>) super.getList(hql,  numberOfEntries);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyModeratorUnitStandard o")).intValue();
	}
}

