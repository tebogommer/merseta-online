package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TownDAO;
import haj.com.entity.lookup.Town;
import haj.com.framework.AbstractService;

public class TownService extends AbstractService {
	/** The dao. */
	private TownDAO dao = new TownDAO();

	/**
	 * Get all Town
 	 * @author TechFinium 
 	 * @see   Town
 	 * @return a list of {@link haj.com.entity.Town}
	 * @throws Exception the exception
 	 */
	public List<Town> allTown() throws Exception {
	  	return dao.allTown();
	}


	/**
	 * Create or update Town.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Town
	 */
	public void create(Town entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Town.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Town
	 */
	public void update(Town entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Town.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Town
	 */
	public void delete(Town entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Town}
	 * @throws Exception the exception
	 * @see    Town
	 */
	public Town findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Town by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Town}
	 * @throws Exception the exception
	 * @see    Town
	 */
	public List<Town> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Town
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Town}
	 * @throws Exception the exception
	 */
	public List<Town> allTown(int first, int pageSize) throws Exception {
		return dao.allTown(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Town for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Town
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Town.class);
	}
	
    /**
     * Lazy load Town with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Town class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Town} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Town> allTown(Class<Town> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Town>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Town for lazy load with filters
     * @author TechFinium 
     * @param entity Town class
     * @param filters the filters
     * @return Number of rows in the Town entity
     * @throws Exception the exception     
     */	
	public int count(Class<Town> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
