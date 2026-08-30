package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.AssessorModReRegistration;

public class AssessorModReRegistrationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AssessorModReRegistration
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> allAssessorModReRegistration() throws Exception {
		return (List<AssessorModReRegistration>)super.getList("select o from AssessorModReRegistration o");
	}

	/**
	 * Get all AssessorModReRegistration between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AssessorModReRegistration
 	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> allAssessorModReRegistration(Long from, int noRows) throws Exception {
	 	String hql = "select o from AssessorModReRegistration o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AssessorModReRegistration>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModReRegistration
 	 * @return a {@link haj.com.entity.AssessorModReRegistration}
 	 * @throws Exception global exception
 	 */
	public AssessorModReRegistration findByKey(Long id) throws Exception {
	 	String hql = "select o from AssessorModReRegistration o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AssessorModReRegistration)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AssessorModReRegistration by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AssessorModReRegistration
  	 * @return a list of {@link haj.com.entity.AssessorModReRegistration}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> findByName(String description) throws Exception {
	 	String hql = "select o from AssessorModReRegistration o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModReRegistration>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModReRegistration> findByAssessorModeratorApplication(Long id) throws Exception {
	 	String hql = "select o from AssessorModReRegistration o where o.assessorModeratorApplication.id = :id order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<AssessorModReRegistration>)super.getList(hql, parameters);
	}
}

