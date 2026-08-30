package haj.com.service;

import java.util.List;

import haj.com.dao.StrategicPrioritiesDAO;
import haj.com.entity.StrategicPriorities;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class StrategicPrioritiesService extends AbstractService {
	/** The dao. */
	private StrategicPrioritiesDAO dao = new StrategicPrioritiesDAO();

	/**
	 * Get all StrategicPriorities
 	 * @author TechFinium 
 	 * @see   StrategicPriorities
 	 * @return a list of {@link haj.com.entity.StrategicPriorities}
	 * @throws Exception the exception
 	 */
	public List<StrategicPriorities> allStrategicPriorities() throws Exception {
	  	return dao.allStrategicPriorities();
	}


	/**
	 * Create or update StrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     StrategicPriorities
	 */
	public void create(StrategicPriorities entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  StrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StrategicPriorities
	 */
	public void update(StrategicPriorities entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  StrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StrategicPriorities
	 */
	public void delete(StrategicPriorities entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StrategicPriorities}
	 * @throws Exception the exception
	 * @see    StrategicPriorities
	 */
	public StrategicPriorities findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find StrategicPriorities by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.StrategicPriorities}
	 * @throws Exception the exception
	 * @see    StrategicPriorities
	 */
	public List<StrategicPriorities> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load StrategicPriorities
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.StrategicPriorities}
	 * @throws Exception the exception
	 */
	public List<StrategicPriorities> allStrategicPriorities(int first, int pageSize) throws Exception {
		return dao.allStrategicPriorities(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of StrategicPriorities for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the StrategicPriorities
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(StrategicPriorities.class);
	}
	
    /**
     * Lazy load StrategicPriorities with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 StrategicPriorities class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.StrategicPriorities} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<StrategicPriorities> allStrategicPriorities(Class<StrategicPriorities> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<StrategicPriorities>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of StrategicPriorities for lazy load with filters
     * @author TechFinium 
     * @param entity StrategicPriorities class
     * @param filters the filters
     * @return Number of rows in the StrategicPriorities entity
     * @throws Exception the exception     
     */	
	public int count(Class<StrategicPriorities> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
