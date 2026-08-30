package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.UserLearnership;
import haj.com.entity.UserSkillsProgramme;

public class UserLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserLearnership
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 * @return a list of {@link haj.com.entity.UserLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserLearnership> allUserLearnership() throws Exception {
		return (List<UserLearnership>)super.getList("select o from UserLearnership o");
	}
	
	@SuppressWarnings("unchecked")
	public List<UserLearnership> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	 	String hql = "select o from UserLearnership o where o.user.id = :userId and o.forAssessorModeratorApplication.id =:amApID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("amApID", amApID);
		return (List<UserLearnership>)super.getList(hql, parameters);
	} 

	/**
	 * Get all UserLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserLearnership
 	 * @return a list of {@link haj.com.entity.UserLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserLearnership> allUserLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserLearnership
 	 * @return a {@link haj.com.entity.UserLearnership}
 	 * @throws Exception global exception
 	 */
	public UserLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from UserLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserLearnership
  	 * @return a list of {@link haj.com.entity.UserLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from UserLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserLearnership>)super.getList(hql, parameters);
	}
}

