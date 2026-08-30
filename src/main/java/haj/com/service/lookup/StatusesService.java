package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.StatusesDAO;
import haj.com.entity.lookup.Statuses;
import haj.com.framework.AbstractService;

public class StatusesService extends AbstractService {
	/** The dao. */
	private StatusesDAO dao = new StatusesDAO();

	/**
	 * Get all Statuses
 	 * @author TechFinium 
 	 * @see   Statuses
 	 * @return a list of {@link haj.com.entity.Statuses}
	 * @throws Exception the exception
 	 */
	public List<Statuses> allStatuses() throws Exception {
	  	return dao.allStatuses();
	}


	/**
	 * Create or update Statuses.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Statuses
	 */
	public void create(Statuses entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Statuses.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Statuses
	 */
	public void update(Statuses entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Statuses.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Statuses
	 */
	public void delete(Statuses entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Statuses}
	 * @throws Exception the exception
	 * @see    Statuses
	 */
	public Statuses findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Statuses by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Statuses}
	 * @throws Exception the exception
	 * @see    Statuses
	 */
	public List<Statuses> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Statuses
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Statuses}
	 * @throws Exception the exception
	 */
	public List<Statuses> allStatuses(int first, int pageSize) throws Exception {
		return dao.allStatuses(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Statuses for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Statuses
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Statuses.class);
	}
	
    /**
     * Lazy load Statuses with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Statuses class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Statuses} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Statuses> allStatuses(Class<Statuses> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Statuses>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Statuses for lazy load with filters
     * @author TechFinium 
     * @param entity Statuses class
     * @param filters the filters
     * @return Number of rows in the Statuses entity
     * @throws Exception the exception     
     */	
	public int count(Class<Statuses> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
