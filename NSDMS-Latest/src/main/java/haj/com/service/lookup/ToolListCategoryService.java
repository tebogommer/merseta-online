package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ToolListCategoryDAO;
import haj.com.entity.lookup.ToolListCategory;
import haj.com.framework.AbstractService;

public class ToolListCategoryService extends AbstractService {
	/** The dao. */
	private ToolListCategoryDAO dao = new ToolListCategoryDAO();

	/**
	 * Get all ToolListCategory
 	 * @author TechFinium 
 	 * @see   ToolListCategory
 	 * @return a list of {@link haj.com.entity.ToolListCategory}
	 * @throws Exception the exception
 	 */
	public List<ToolListCategory> allToolListCategory() throws Exception {
	  	return dao.allToolListCategory();
	}


	/**
	 * Create or update ToolListCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ToolListCategory
	 */
	public void create(ToolListCategory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ToolListCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolListCategory
	 */
	public void update(ToolListCategory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ToolListCategory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ToolListCategory
	 */
	public void delete(ToolListCategory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ToolListCategory}
	 * @throws Exception the exception
	 * @see    ToolListCategory
	 */
	public ToolListCategory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ToolListCategory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ToolListCategory}
	 * @throws Exception the exception
	 * @see    ToolListCategory
	 */
	public List<ToolListCategory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ToolListCategory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ToolListCategory}
	 * @throws Exception the exception
	 */
	public List<ToolListCategory> allToolListCategory(int first, int pageSize) throws Exception {
		return dao.allToolListCategory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ToolListCategory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ToolListCategory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ToolListCategory.class);
	}
	
    /**
     * Lazy load ToolListCategory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ToolListCategory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ToolListCategory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ToolListCategory> allToolListCategory(Class<ToolListCategory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ToolListCategory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ToolListCategory for lazy load with filters
     * @author TechFinium 
     * @param entity ToolListCategory class
     * @param filters the filters
     * @return Number of rows in the ToolListCategory entity
     * @throws Exception the exception     
     */	
	public int count(Class<ToolListCategory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
