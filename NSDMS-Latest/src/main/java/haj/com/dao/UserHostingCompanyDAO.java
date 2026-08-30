package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompany;
import haj.com.entity.UserHostingCompany;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserHostingCompanyDAO.
 */
public class UserHostingCompanyDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UserHostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<UserHostingCompany> allUserHostingCompany() throws Exception {
		return (List<UserHostingCompany>)super.getList("select o from UserHostingCompany o");
	}

	/**
	 * Get all UserHostingCompany between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<UserHostingCompany> allUserHostingCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from UserHostingCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UserHostingCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	public UserHostingCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from UserHostingCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UserHostingCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UserHostingCompany by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	@SuppressWarnings("unchecked")
	public List<UserHostingCompany> findByName(String description) throws Exception {
	 	String hql = "select o from UserHostingCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UserHostingCompany>)super.getList(hql, parameters);
	}
	
	/**
	 * Find count of UserHostingCompany by user and HostingCompany.
	 *
	 * @author TechFinium
	 * @param hostingCompany the hosting company
	 * @param user the user
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception global exception
	 * @see    UserHostingCompany
	 */
	public Long findCountByUserAndHoustingCompany(HostingCompany hostingCompany, Users user) throws Exception {
	 	String hql = "select count(o) from UserHostingCompany o where o.hostingCompany.id = :hostingCompanyID and o.user.id = :userID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyID", hostingCompany.getId());
	    parameters.put("userID", user.getId());
		return (Long)super.getUniqueResult(hql, parameters);
	}
}

