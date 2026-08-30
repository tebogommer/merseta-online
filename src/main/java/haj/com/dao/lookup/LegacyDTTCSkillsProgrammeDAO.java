package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyDTTCSkillsProgramme;

public class LegacyDTTCSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyDTTCSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyDTTCSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgramme() throws Exception {
		return (List<LegacyDTTCSkillsProgramme>)super.getList("select o from LegacyDTTCSkillsProgramme o");
	}

	/**
	 * Get all LegacyDTTCSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyDTTCSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyDTTCSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyDTTCSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyDTTCSkillsProgramme
 	 * @return a {@link haj.com.entity.LegacyDTTCSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public LegacyDTTCSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyDTTCSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyDTTCSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyDTTCSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyDTTCSkillsProgramme
  	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyDTTCSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyDTTCSkillsProgramme>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyDTTCSkillsProgramme o")).intValue();
	}
	
	public Integer countAllLegacyDTTCSkillsProgrammeNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyDTTCSkillsProgramme o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyDTTCSkillsProgramme o where o.processed = false";
		return (List<LegacyDTTCSkillsProgramme>) super.getList(hql, numberOfEntries);
	}
}

