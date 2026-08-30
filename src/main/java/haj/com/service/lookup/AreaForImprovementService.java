package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.AreaForImprovementDAO;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AreaForImprovementService extends AbstractService {
	/** The dao. */
	private AreaForImprovementDAO dao = new AreaForImprovementDAO();

	/**
	 * Get all AreaForImprovement
 	 * @author TechFinium 
 	 * @see   AreaForImprovement
 	 * @return a list of {@link haj.com.entity.AreaForImprovement}
	 * @throws Exception the exception
 	 */
	public List<AreaForImprovement> allAreaForImprovement() throws Exception {
	  	return dao.allAreaForImprovement();
	}


	/**
	 * Create or update AreaForImprovement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AreaForImprovement
	 */
	public void create(AreaForImprovement entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AreaForImprovement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AreaForImprovement
	 */
	public void update(AreaForImprovement entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AreaForImprovement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AreaForImprovement
	 */
	public void delete(AreaForImprovement entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AreaForImprovement}
	 * @throws Exception the exception
	 * @see    AreaForImprovement
	 */
	public AreaForImprovement findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AreaForImprovement by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AreaForImprovement}
	 * @throws Exception the exception
	 * @see    AreaForImprovement
	 */
	public List<AreaForImprovement> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AreaForImprovement
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AreaForImprovement}
	 * @throws Exception the exception
	 */
	public List<AreaForImprovement> allAreaForImprovement(int first, int pageSize) throws Exception {
		return dao.allAreaForImprovement(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AreaForImprovement for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AreaForImprovement
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AreaForImprovement.class);
	}
	
    /**
     * Lazy load AreaForImprovement with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AreaForImprovement class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AreaForImprovement} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> allAreaForImprovement(Class<AreaForImprovement> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AreaForImprovement>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AreaForImprovement for lazy load with filters
     * @author TechFinium 
     * @param entity AreaForImprovement class
     * @param filters the filters
     * @return Number of rows in the AreaForImprovement entity
     * @throws Exception the exception     
     */	
	public int count(Class<AreaForImprovement> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
