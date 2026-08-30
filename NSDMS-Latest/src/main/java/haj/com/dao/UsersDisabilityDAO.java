package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UsersDisability;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UsersDisabilityDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersDisability
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 * @return a list of {@link haj.com.entity.UsersDisability}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersDisability> allUsersDisability() throws Exception {
		return (List<UsersDisability>)super.getList("select o from UsersDisability o");
	}

	/**
	 * Get all UsersDisability between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UsersDisability
 	 * @return a list of {@link haj.com.entity.UsersDisability}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersDisability> allUsersDisability(Long from, int noRows) throws Exception {
	 	String hql = "select o from UsersDisability o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UsersDisability>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UsersDisability
 	 * @return a {@link haj.com.entity.UsersDisability}
 	 * @throws Exception global exception
 	 */
	public UsersDisability findByKey(Long id) throws Exception {
	 	String hql = "select o from UsersDisability o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UsersDisability)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersDisability by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UsersDisability
  	 * @return a list of {@link haj.com.entity.UsersDisability}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersDisability> findByName(String description) throws Exception {
	 	String hql = "select o from UsersDisability o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UsersDisability>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UsersDisability> findByKeyUser(Long userID) {
		String hql = "select o from UsersDisability o where o.user.id = :userID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", userID);
		return (List<UsersDisability>)super.getList(hql, parameters);
	}
	
	public int countByKeyUser(Long userID) throws Exception {
		String hql = "select count(o) from UsersDisability o where o.user.id = :userID " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("userID", userID);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<UsersDisability> findByTargetClassAndKey(Long targetKey, String targetClass) {
		String hql = "select o from UsersDisability o where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<UsersDisability>)super.getList(hql, parameters);
	}
}

