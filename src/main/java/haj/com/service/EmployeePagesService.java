package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.EmployeePagesDAO;
import haj.com.entity.EmployeePages;
import haj.com.framework.AbstractService;

public class EmployeePagesService extends AbstractService {
	/** The dao. */
	private EmployeePagesDAO dao = new EmployeePagesDAO();

	/**
	 * Get all EmployeePages
 	 * @author TechFinium 
 	 * @see   EmployeePages
 	 * @return a list of {@link haj.com.entity.EmployeePages}
	 * @throws Exception the exception
 	 */
	public List<EmployeePages> allEmployeePages() throws Exception {
	  	return dao.allEmployeePages();
	}


	/**
	 * Create or update EmployeePages.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EmployeePages
	 */
	public void create(EmployeePages entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EmployeePages.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmployeePages
	 */
	public void update(EmployeePages entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EmployeePages.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmployeePages
	 */
	public void delete(EmployeePages entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmployeePages}
	 * @throws Exception the exception
	 * @see    EmployeePages
	 */
	public EmployeePages findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EmployeePages by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EmployeePages}
	 * @throws Exception the exception
	 * @see    EmployeePages
	 */
	public List<EmployeePages> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EmployeePages
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EmployeePages}
	 * @throws Exception the exception
	 */
	public List<EmployeePages> allEmployeePages(int first, int pageSize) throws Exception {
		return dao.allEmployeePages(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EmployeePages for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the EmployeePages
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EmployeePages.class);
	}
	
    /**
     * Lazy load EmployeePages with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 EmployeePages class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EmployeePages} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EmployeePages> allEmployeePages(Class<EmployeePages> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EmployeePages>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EmployeePages for lazy load with filters
     * @author TechFinium 
     * @param entity EmployeePages class
     * @param filters the filters
     * @return Number of rows in the EmployeePages entity
     * @throws Exception the exception     
     */	
	public int count(Class<EmployeePages> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
