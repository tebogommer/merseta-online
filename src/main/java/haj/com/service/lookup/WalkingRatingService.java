package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.WalkingRatingDAO;
import haj.com.entity.lookup.WalkingRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class WalkingRatingService.
 */
public class WalkingRatingService extends AbstractService {
	/** The dao. */
	private WalkingRatingDAO dao = new WalkingRatingDAO();

	/**
	 * Get all WalkingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception the exception
	 * @see   WalkingRating
	 */
	public List<WalkingRating> allWalkingRating() throws Exception {
	  	return dao.allWalkingRating();
	}


	/**
	 * Create or update WalkingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WalkingRating
	 */
	public void create(WalkingRating entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WalkingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WalkingRating
	 */
	public void update(WalkingRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WalkingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WalkingRating
	 */
	public void delete(WalkingRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WalkingRating}
	 * @throws Exception the exception
	 * @see    WalkingRating
	 */
	public WalkingRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WalkingRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception the exception
	 * @see    WalkingRating
	 */
	public List<WalkingRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WalkingRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WalkingRating}
	 * @throws Exception the exception
	 */
	public List<WalkingRating> allWalkingRating(int first, int pageSize) throws Exception {
		return dao.allWalkingRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WalkingRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WalkingRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WalkingRating.class);
	}
	
    /**
     * Lazy load WalkingRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 WalkingRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WalkingRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WalkingRating> allWalkingRating(Class<WalkingRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WalkingRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WalkingRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity WalkingRating class
     * @param filters the filters
     * @return Number of rows in the WalkingRating entity
     * @throws Exception the exception
     */	
	public int count(Class<WalkingRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
