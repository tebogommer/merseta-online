package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ToolDAO;
import haj.com.entity.lookup.Tool;
import haj.com.framework.AbstractService;

public class ToolService extends AbstractService {
	/** The dao. */
	private ToolDAO dao = new ToolDAO();

	/**
	 * Get all Tool
 	 * @author TechFinium 
 	 * @see   Tool
 	 * @return a list of {@link haj.com.entity.Tool}
	 * @throws Exception the exception
 	 */
	public List<Tool> allTool() throws Exception {
	  	return dao.allTool();
	}


	/**
	 * Create or update Tool.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Tool
	 */
	public void create(Tool entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Tool.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Tool
	 */
	public void update(Tool entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Tool.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Tool
	 */
	public void delete(Tool entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Tool}
	 * @throws Exception the exception
	 * @see    Tool
	 */
	public Tool findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Tool by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Tool}
	 * @throws Exception the exception
	 * @see    Tool
	 */
	public List<Tool> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Tool
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Tool}
	 * @throws Exception the exception
	 */
	public List<Tool> allTool(int first, int pageSize) throws Exception {
		return dao.allTool(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Tool for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Tool
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Tool.class);
	}
	
    /**
     * Lazy load Tool with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Tool class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Tool} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Tool> allTool(Class<Tool> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Tool>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Tool for lazy load with filters
     * @author TechFinium 
     * @param entity Tool class
     * @param filters the filters
     * @return Number of rows in the Tool entity
     * @throws Exception the exception     
     */	
	public int count(Class<Tool> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
