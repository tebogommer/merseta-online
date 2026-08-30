package haj.com.dao;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UsersLanguageDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersLanguage
 	 * @author TechFinium 
 	 * @see    UsersLanguage
 	 * @return a list of {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> allUsersLanguage() throws Exception {
		return (List<UsersLanguage>)super.getList("select o from UsersLanguage o");
	}
	
	/**
	 * Get all UsersLanguage by user
 	 * @author TechFinium 
 	 *  * @param Users the users
 	 * @see    UsersLanguage
 	 * @return a list of {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByUser(Users users) throws Exception {
	 	String hql = "select o from UsersLanguage o left join fetch o.user u  where o.user.id = :usersId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("usersId", users.getId());
		return (List<UsersLanguage>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByUserId(Long userID) throws Exception {
	 	String hql = "select o from UsersLanguage o left join fetch o.user u  where o.user.id = :usersId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("usersId", userID);
		return (List<UsersLanguage>)super.getList(hql, parameters);
	}
	/**
	 * Get all UsersLanguage between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UsersLanguage
 	 * @return a list of {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> allUsersLanguage(Long from, int noRows) throws Exception {
	 	String hql = "select o from UsersLanguage o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UsersLanguage>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UsersLanguage
 	 * @return a {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	public UsersLanguage findByKey(Long id) throws Exception {
	 	String hql = "select o from UsersLanguage o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UsersLanguage)super.getUniqueResult(hql, parameters);
	}
	
	

	/**
	 * Find UsersLanguage by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UsersLanguage
  	 * @return a list of {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByName(String description) throws Exception {
	 	String hql = "select o from UsersLanguage o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UsersLanguage>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByTargetClassAndKey(Long targetKey, String targetClass) {
		String hql = "select o from UsersLanguage o where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<UsersLanguage>)super.getList(hql, parameters);
	}
	
	public int countByUserId(Long userID) throws Exception {
	 	String hql = "select count(o) from UsersLanguage o where o.user.id = :usersId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("usersId", userID);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

