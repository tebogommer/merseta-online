package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RegionDAO;
import haj.com.entity.lookup.Region;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class RegionService.
 */
public class RegionService extends AbstractService {
	/** The dao. */
	private RegionDAO dao = new RegionDAO();

	/**
	 * Get all Region.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception the exception
	 * @see   Region
	 */
	public List<Region> allRegion() throws Exception {
	  	return dao.allRegion();
	}


	/**
	 * Create or update Region.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Region
	 */
	public void create(Region entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Region.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Region
	 */
	public void update(Region entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Region.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Region
	 */
	public void delete(Region entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Region}
	 * @throws Exception the exception
	 * @see    Region
	 */
	public Region findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Region by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception the exception
	 * @see    Region
	 */
	public List<Region> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Region.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Region}
	 * @throws Exception the exception
	 */
	public List<Region> allRegion(int first, int pageSize) throws Exception {
		return dao.allRegion(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Region for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Region
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Region.class);
	}
	
    /**
     * Lazy load Region with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Region class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Region} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Region> allRegion(Class<Region> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Region>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Region for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Region class
     * @param filters the filters
     * @return Number of rows in the Region entity
     * @throws Exception the exception
     */	
	public int count(Class<Region> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
