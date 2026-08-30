package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ToolCategoryDAO;
import haj.com.entity.lookup.ToolCategory;
import haj.com.framework.AbstractService;

public class ToolCategoryService extends AbstractService {
	/** The dao. */
	private ToolCategoryDAO dao = new ToolCategoryDAO();

	/**
	 * Get all ToolCategory
 	 * @author TechFinium 
 	 * @see   ToolCategory
 	 * @return a list of {@link haj.com.entity.ToolCategory}
	 * @throws Exception the exception
 	 */
	public List<ToolCategory> allToolCategory() throws Exception {
	  	return dao.allToolCategory();
	}


	/**
	 * Create or update ToolCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ToolCategory
	 */
	public void create(ToolCategory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ToolCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolCategory
	 */
	public void update(ToolCategory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ToolCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolCategory
	 */
	public void delete(ToolCategory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ToolCategory}
	 * @throws Exception the exception
	 * @see    ToolCategory
	 */
	public ToolCategory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ToolCategory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ToolCategory}
	 * @throws Exception the exception
	 * @see    ToolCategory
	 */
	public List<ToolCategory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ToolCategory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ToolCategory}
	 * @throws Exception the exception
	 */
	public List<ToolCategory> allToolCategory(int first, int pageSize) throws Exception {
		return dao.allToolCategory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ToolCategory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ToolCategory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ToolCategory.class);
	}
	
    /**
     * Lazy load ToolCategory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ToolCategory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ToolCategory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ToolCategory> allToolCategory(Class<ToolCategory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ToolCategory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ToolCategory for lazy load with filters
     * @author TechFinium 
     * @param entity ToolCategory class
     * @param filters the filters
     * @return Number of rows in the ToolCategory entity
     * @throws Exception the exception     
     */	
	public int count(Class<ToolCategory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
