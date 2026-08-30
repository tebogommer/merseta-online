package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.AssessorModeratorCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class AssessorModeratorCompanyDAO.
 */
public class AssessorModeratorCompanyDAO extends AbstractDAO  {
	
	/** The Constant leftJoinsCompanyUser. */
	private static final String leftJoinsCompanyUser = " " + "left join fetch o.company comp left join fetch o.assessorModerator am" + " ";


	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AssessorModeratorCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception global exception
	 * @see    AssessorModeratorCompany
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> allAssessorModeratorCompany() throws Exception {
		return (List<AssessorModeratorCompany>)super.getList("select o from AssessorModeratorCompany o");
	}

	/**
	 * Get all AssessorModeratorCompany between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception global exception
	 * @see    AssessorModeratorCompany
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> allAssessorModeratorCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from AssessorModeratorCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AssessorModeratorCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception global exception
	 * @see    AssessorModeratorCompany
	 */
	public AssessorModeratorCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from AssessorModeratorCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AssessorModeratorCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AssessorModeratorCompany by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompany}
	 * @throws Exception global exception
	 * @see    AssessorModeratorCompany
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> findByName(String description) throws Exception {
	 	String hql = "select o from AssessorModeratorCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModeratorCompany>)super.getList(hql, parameters);
	}
	
	/**
	 * Finds AssessorModeratorCompany by User ID.
	 *
	 * @param userId the user id
	 * @return List<AssessorModeratorCompany>
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> findByAssessorModerator(Long userId) throws Exception {
	 	String hql = "select o from AssessorModeratorCompany o " + leftJoinsCompanyUser
	 			+ "where o.assessorModerator.id = :userId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<AssessorModeratorCompany>)super.getList(hql, parameters);
	}
	
	/**
	 * Finds AssessorModeratorCompany by User ID.
	 *
	 * @param userId the user id
	 * @return List<AssessorModeratorCompany>
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompany> findByAssessorModeratorAndForAMApplication(Long userId,Long amAppID) throws Exception {
	 	String hql = "select o from AssessorModeratorCompany o " + leftJoinsCompanyUser
	 			+ "where o.assessorModerator.id = :userId and o.forAssessorModeratorApplication.id =:amAppID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
	    parameters.put("amAppID", amAppID);
		return (List<AssessorModeratorCompany>)super.getList(hql, parameters);
	}
}

