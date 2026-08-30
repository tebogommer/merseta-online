package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProjectTypeDAO;
import haj.com.entity.lookup.ProjectType;
import haj.com.framework.AbstractService;

public class ProjectTypeService extends AbstractService {
	/** The dao. */
	private ProjectTypeDAO dao = new ProjectTypeDAO();

	/**
	 * Get all ProjectType
 	 * @author TechFinium 
 	 * @see   ProjectType
 	 * @return a list of {@link haj.com.entity.ProjectType}
	 * @throws Exception the exception
 	 */
	public List<ProjectType> allProjectTtype() throws Exception {
	  	return dao.allProjectType();
	}


	/**
	 * Create or update ProjectType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProjectType
	 */
	public void create(ProjectType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProjectType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProjectType
	 */
	public void update(ProjectType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProjectType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProjectType
	 */
	public void delete(ProjectType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProjectType}
	 * @throws Exception the exception
	 * @see    ProjectType
	 */
	public ProjectType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProjectType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProjectType}
	 * @throws Exception the exception
	 * @see    ProjectType
	 */
	public List<ProjectType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ProjectType
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProjectType}
	 * @throws Exception the exception
	 */
	public List<ProjectType> allProjectType(int first, int pageSize) throws Exception {
		return dao.allProjectType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProjectType for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ProjectType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProjectType.class);
	}
	
    /**
     * Lazy load ProjectType with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ProjectType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProjectType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProjectType> allProjectType(Class<ProjectType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProjectType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProjectType for lazy load with filters
     * @author TechFinium 
     * @param entity ProjectType class
     * @param filters the filters
     * @return Number of rows in the ProjectType entity
     * @throws Exception the exception     
     */	
	public int count(Class<ProjectType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
