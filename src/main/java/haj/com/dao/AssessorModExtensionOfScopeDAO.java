package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.AssessorModExtensionOfScope;

public class AssessorModExtensionOfScopeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AssessorModExtensionOfScope
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModExtensionOfScope> allAssessorModExtensionOfScope() throws Exception {
		return (List<AssessorModExtensionOfScope>)super.getList("select o from AssessorModExtensionOfScope o");
	}

	/**
	 * Get all AssessorModExtensionOfScope between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AssessorModExtensionOfScope
 	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModExtensionOfScope> allAssessorModExtensionOfScope(Long from, int noRows) throws Exception {
	 	String hql = "select o from AssessorModExtensionOfScope o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AssessorModExtensionOfScope>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AssessorModExtensionOfScope
 	 * @return a {@link haj.com.entity.AssessorModExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	public AssessorModExtensionOfScope findByKey(Long id) throws Exception {
	 	String hql = "select o from AssessorModExtensionOfScope o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AssessorModExtensionOfScope)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AssessorModExtensionOfScope by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AssessorModExtensionOfScope
  	 * @return a list of {@link haj.com.entity.AssessorModExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModExtensionOfScope> findByName(String description) throws Exception {
	 	String hql = "select o from AssessorModExtensionOfScope o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AssessorModExtensionOfScope>)super.getList(hql, parameters);
	}

	public List<AssessorModExtensionOfScope> findByAssessorModeratorApplication(Long id) {
		String hql = "select o from AssessorModExtensionOfScope o where o.assessorModeratorApplication.id = :id order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<AssessorModExtensionOfScope>)super.getList(hql, parameters);
	}
}

