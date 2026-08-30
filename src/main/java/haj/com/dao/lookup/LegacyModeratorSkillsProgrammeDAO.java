package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;

public class LegacyModeratorSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyModeratorSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyModeratorSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgramme() throws Exception {
		return (List<LegacyModeratorSkillsProgramme>)super.getList("select o from LegacyModeratorSkillsProgramme o");
	}

	/**
	 * Get all LegacyModeratorSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyModeratorSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyModeratorSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyModeratorSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> findByModeratorRegNo(String moderatorRegNo) throws Exception {
	 	String hql = "select o from LegacyModeratorSkillsProgramme o where o.moderatorRegNo = :moderatorRegNo and o.skillsProgram is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("moderatorRegNo", moderatorRegNo.trim());
		return (List<LegacyModeratorSkillsProgramme>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyModeratorSkillsProgramme
 	 * @return a {@link haj.com.entity.LegacyModeratorSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public LegacyModeratorSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyModeratorSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyModeratorSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyModeratorSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyModeratorSkillsProgramme
  	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyModeratorSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyModeratorSkillsProgramme>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyModeratorSkillsProgrammeNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyModeratorSkillsProgramme o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyModeratorSkillsProgramme o where o.processed = false";
		return (List<LegacyModeratorSkillsProgramme>) super.getList(hql,  numberOfEntries);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyModeratorSkillsProgramme o")).intValue();
	}
}

