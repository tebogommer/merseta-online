package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.JobTitle;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class JobTitleDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all JobTitle
 	 * @author TechFinium 
 	 * @see    JobTitle
 	 * @return a list of {@link haj.com.entity.JobTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JobTitle> allJobTitle() throws Exception {
		return (List<JobTitle>)super.getList("select o from JobTitle o");
	}

	/**
	 * Get all JobTitle between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    JobTitle
 	 * @return a list of {@link haj.com.entity.JobTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JobTitle> allJobTitle(Long from, int noRows) throws Exception {
	 	String hql = "select o from JobTitle o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<JobTitle>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    JobTitle
 	 * @return a {@link haj.com.entity.JobTitle}
 	 * @throws Exception global exception
 	 */
	public JobTitle findByKey(Long id) throws Exception {
	 	String hql = "select o from JobTitle o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (JobTitle)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find JobTitle by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    JobTitle
  	 * @return a list of {@link haj.com.entity.JobTitle}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<JobTitle> findByName(String description) throws Exception {
	 	String hql = "select o from JobTitle o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<JobTitle>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<JobTitle> findByRole(Long roleId) throws Exception {
	 	String hql = "select o from JobTitle o where o.roles.id = :roleId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("roleId", roleId);
		return (List<JobTitle>)super.getList(hql, parameters);
	}
}

