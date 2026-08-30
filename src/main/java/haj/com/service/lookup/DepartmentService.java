package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DepartmentDAO;
import haj.com.entity.lookup.Department;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DepartmentService extends AbstractService {
	/** The dao. */
	private DepartmentDAO dao = new DepartmentDAO();

	/**
	 * Get all Department
 	 * @author TechFinium 
 	 * @see   Department
 	 * @return a list of {@link haj.com.entity.Department}
	 * @throws Exception the exception
 	 */
	public List<Department> allDepartment() throws Exception {
	  	return dao.allDepartment();
	}


	/**
	 * Create or update Department.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Department
	 */
	public void create(Department entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Department.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Department
	 */
	public void update(Department entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Department.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Department
	 */
	public void delete(Department entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Department}
	 * @throws Exception the exception
	 * @see    Department
	 */
	public Department findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Department by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Department}
	 * @throws Exception the exception
	 * @see    Department
	 */
	public List<Department> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Department
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Department}
	 * @throws Exception the exception
	 */
	public List<Department> allDepartment(int first, int pageSize) throws Exception {
		return dao.allDepartment(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Department for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Department
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Department.class);
	}
	
    /**
     * Lazy load Department with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Department class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Department} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Department> allDepartment(Class<Department> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Department>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Department for lazy load with filters
     * @author TechFinium 
     * @param entity Department class
     * @param filters the filters
     * @return Number of rows in the Department entity
     * @throws Exception the exception     
     */	
	public int count(Class<Department> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
