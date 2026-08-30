package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.UserPermissions;

public class UserPermissionsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserPermissions
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 * @return a list of {@link haj.com.entity.UserPermissions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserPermissions> allUserPermissions() throws Exception {
		return (List<UserPermissions>)super.getList("select o from UserPermissions o");
	}

	/**
	 * Get all UserPermissions between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserPermissions
 	 * @return a list of {@link haj.com.entity.UserPermissions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserPermissions> allUserPermissions(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserPermissions o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserPermissions>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserPermissions
 	 * @return a {@link haj.com.entity.UserPermissions}
 	 * @throws Exception global exception
 	 */
	public UserPermissions findByKey(Long id) throws Exception {
	 	String hql = "select o from UserPermissions o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserPermissions)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserPermissions by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserPermissions
  	 * @return a list of {@link haj.com.entity.UserPermissions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserPermissions> findByName(String description) throws Exception {
	 	String hql = "select o from UserPermissions o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserPermissions>)super.getList(hql, parameters);
	}
	
	public UserPermissions findByUser(Long userId) throws Exception {
	 	String hql = "select o from UserPermissions o where o.user.id = :userId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (UserPermissions)super.getUniqueResult(hql, parameters);
	}
	
}