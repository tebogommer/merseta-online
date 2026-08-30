package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;

public class LegacyEmployerWA2SkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyEmployerWA2SkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyEmployerWA2SkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgramme() throws Exception {
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList("select o from LegacyEmployerWA2SkillsProgramme o");
	}

	/**
	 * Get all LegacyEmployerWA2SkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyEmployerWA2SkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2SkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyEmployerWA2SkillsProgramme
 	 * @return a {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public LegacyEmployerWA2SkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2SkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyEmployerWA2SkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyEmployerWA2SkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyEmployerWA2SkillsProgramme
  	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2SkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList(hql, parameters);
	}
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyEmployerWA2SkillsProgramme o")).intValue();
	}
	
	public Integer countAllLegacyEmployerWA2SkillsProgrammeNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyEmployerWA2SkillsProgramme o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyEmployerWA2SkillsProgramme o where o.processed = false";
		return (List<LegacyEmployerWA2SkillsProgramme>) super.getList(hql, numberOfEntries);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> findByLevy(String linkedSdl) throws Exception {
	 	String hql = "select o from LegacyEmployerWA2SkillsProgramme o where o.linkedSdl = :linkedSdl " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> findDistinctByLevy(String linkedSdl) throws Exception {
	 	String hql = "select distinct(o) from LegacyEmployerWA2SkillsProgramme o where o.linkedSdl = :linkedSdl " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> findDistinctByLevy(String linkedSdl, String sdlNo) throws Exception {
	 	String hql = "select distinct(o) from LegacyEmployerWA2SkillsProgramme o where o.linkedSdl = :linkedSdl and o.sdlNo = :sdlNo" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("linkedSdl", linkedSdl);
	    parameters.put("sdlNo", sdlNo);
		return (List<LegacyEmployerWA2SkillsProgramme>)super.getList(hql, parameters);
	}
}

