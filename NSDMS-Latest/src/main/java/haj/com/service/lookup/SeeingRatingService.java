package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SeeingRatingDAO;
import haj.com.entity.lookup.SeeingRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SeeingRatingService.
 */
public class SeeingRatingService extends AbstractService {
	/** The dao. */
	private SeeingRatingDAO dao = new SeeingRatingDAO();

	/**
	 * Get all SeeingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception the exception
	 * @see   SeeingRating
	 */
	public List<SeeingRating> allSeeingRating() throws Exception {
	  	return dao.allSeeingRating();
	}


	/**
	 * Create or update SeeingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SeeingRating
	 */
	public void create(SeeingRating entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SeeingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SeeingRating
	 */
	public void update(SeeingRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SeeingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SeeingRating
	 */
	public void delete(SeeingRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SeeingRating}
	 * @throws Exception the exception
	 * @see    SeeingRating
	 */
	public SeeingRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SeeingRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception the exception
	 * @see    SeeingRating
	 */
	public List<SeeingRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SeeingRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SeeingRating}
	 * @throws Exception the exception
	 */
	public List<SeeingRating> allSeeingRating(int first, int pageSize) throws Exception {
		return dao.allSeeingRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SeeingRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SeeingRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SeeingRating.class);
	}
	
    /**
     * Lazy load SeeingRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SeeingRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SeeingRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SeeingRating> allSeeingRating(Class<SeeingRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SeeingRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SeeingRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SeeingRating class
     * @param filters the filters
     * @return Number of rows in the SeeingRating entity
     * @throws Exception the exception
     */	
	public int count(Class<SeeingRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
