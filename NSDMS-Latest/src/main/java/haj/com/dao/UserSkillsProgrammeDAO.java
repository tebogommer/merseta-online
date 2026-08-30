package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserSkillsProgramme;

public class UserSkillsProgrammeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserSkillsProgramme
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserSkillsProgramme> allUserSkillsProgramme() throws Exception {
		return (List<UserSkillsProgramme>)super.getList("select o from UserSkillsProgramme o");
	}

	/**
	 * Get all UserSkillsProgramme between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserSkillsProgramme
 	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserSkillsProgramme> allUserSkillsProgramme(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserSkillsProgramme o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserSkillsProgramme>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	public List<UserSkillsProgramme> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	 	String hql = "select o from UserSkillsProgramme o where o.user.id = :userId and o.forAssessorModeratorApplication.id =:amApID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("amApID", amApID);
		return (List<UserSkillsProgramme>)super.getList(hql, parameters);
	} 

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserSkillsProgramme
 	 * @return a {@link haj.com.entity.UserSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	public UserSkillsProgramme findByKey(Long id) throws Exception {
	 	String hql = "select o from UserSkillsProgramme o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserSkillsProgramme)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserSkillsProgramme by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserSkillsProgramme
  	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserSkillsProgramme> findByName(String description) throws Exception {
	 	String hql = "select o from UserSkillsProgramme o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserSkillsProgramme>)super.getList(hql, parameters);
	}
}

