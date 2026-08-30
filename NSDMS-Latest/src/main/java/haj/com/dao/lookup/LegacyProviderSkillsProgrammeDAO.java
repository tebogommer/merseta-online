package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;

public class LegacyProviderSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderSkillsProgramme
 	 * @author TechFinium 
 	 * @see    LegacyProviderSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme() throws Exception {
		return (List<LegacyProviderSkillsProgramme>)super.getList("select o from LegacyProviderSkillsProgramme o");
	}

	/**
	 * Get all LegacyProviderSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderSkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderSkillsProgramme
 	 * @return a {@link haj.com.entity.LegacyProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderSkillsProgramme
  	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> findBySldNoAndSkillProgrameIsNotNull(String sldNo) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o where o.sldNo = :sldNo and o.skillsProgram is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sldNo", sldNo);
		return (List<LegacyProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> findByAccreditationNoAndSkillProgrameIsNotNull(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o where o.accreditationNo = :accreditationNo and o.skillsProgram is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> findByAccreditationNoAndSkillSetIsNotNull(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderSkillsProgramme o where o.accreditationNo = :accreditationNo and o.skillsSet is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderSkillsProgramme>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderSkillsProgramme o")).intValue();
	}
	
	
}

