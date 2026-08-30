package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.UserBrowserInformation;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UserBrowserInformationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserBrowserInformation
 	 * @author TechFinium 
 	 * @see    UserBrowserInformation
 	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserBrowserInformation> allUserBrowserInformation() throws Exception {
		return (List<UserBrowserInformation>)super.getList("select o from UserBrowserInformation o");
	}

	/**
	 * Get all UserBrowserInformation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserBrowserInformation
 	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserBrowserInformation> allUserBrowserInformation(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserBrowserInformation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserBrowserInformation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserBrowserInformation
 	 * @return a {@link haj.com.entity.UserBrowserInformation}
 	 * @throws Exception global exception
 	 */
	public UserBrowserInformation findByKey(Long id) throws Exception {
	 	String hql = "select o from UserBrowserInformation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserBrowserInformation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserBrowserInformation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserBrowserInformation
  	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserBrowserInformation> findByName(String description) throws Exception {
	 	String hql = "select o from UserBrowserInformation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserBrowserInformation>)super.getList(hql, parameters);
	}
}

