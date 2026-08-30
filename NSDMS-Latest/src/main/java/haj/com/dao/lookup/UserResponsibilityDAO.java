package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyUsers;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class UserResponsibilityDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserResponsibility
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 * @return a list of {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> allUserResponsibility() throws Exception {
		return (List<UserResponsibility>)super.getList("select o from UserResponsibility o");
	}

	/**
	 * Get all UserResponsibility between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    UserResponsibility
 	 * @return a list of {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> allUserResponsibility(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserResponsibility o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserResponsibility>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    UserResponsibility
 	 * @return a {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	public UserResponsibility findByKey(Long id) throws Exception {
	 	String hql = "select o from UserResponsibility o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserResponsibility)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserResponsibility by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserResponsibility
  	 * @return a list of {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> findByName(String description) throws Exception {
	 	String hql = "select o from UserResponsibility o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserResponsibility>)super.getList(hql, parameters);
	}
	
	/**
	 * Find UserResponsibility by companyUsers
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserResponsibility
  	 * @return a list of {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
	 	String hql = "select o from UserResponsibility o,UsersResponsibilities ur,CompanyUsers cu where ur.userResponsibility =o.id AND ur.companyUsers =cu.id AND cu.user =:theUser" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("theUser", companyUsers.getUser());
		return (List<UserResponsibility>)super.getList(hql, parameters);
	}
}

