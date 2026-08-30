package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProjectType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ProjectTypeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProjectType
 	 * @author TechFinium 
 	 * @see    ProjectType
 	 * @return a list of {@link haj.com.entity.ProjectType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectType> allProjectType() throws Exception {
		return (List<ProjectType>)super.getList("select o from ProjectType o");
	}

	/**
	 * Get all ProjectType between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ProjectType
 	 * @return a list of {@link haj.com.entity.ProjectType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectType> allProjectType(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProjectType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProjectType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ProjectType
 	 * @return a {@link haj.com.entity.ProjectType}
 	 * @throws Exception global exception
 	 */
	public ProjectType findByKey(Long id) throws Exception {
	 	String hql = "select o from ProjectType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProjectType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProjectType by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ProjectType
  	 * @return a list of {@link haj.com.entity.ProjectType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ProjectType> findByName(String description) throws Exception {
	 	String hql = "select o from ProjectType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProjectType>)super.getList(hql, parameters);
	}
}

