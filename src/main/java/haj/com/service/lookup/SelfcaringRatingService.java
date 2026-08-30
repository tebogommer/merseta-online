package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SelfcaringRatingDAO;
import haj.com.entity.lookup.SelfcaringRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SelfcaringRatingService.
 */
public class SelfcaringRatingService extends AbstractService {
	/** The dao. */
	private SelfcaringRatingDAO dao = new SelfcaringRatingDAO();

	/**
	 * Get all SelfcaringRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception the exception
	 * @see   SelfcaringRating
	 */
	public List<SelfcaringRating> allSelfcaringRating() throws Exception {
	  	return dao.allSelfcaringRating();
	}


	/**
	 * Create or update SelfcaringRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SelfcaringRating
	 */
	public void create(SelfcaringRating entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SelfcaringRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SelfcaringRating
	 */
	public void update(SelfcaringRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SelfcaringRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SelfcaringRating
	 */
	public void delete(SelfcaringRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception the exception
	 * @see    SelfcaringRating
	 */
	public SelfcaringRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SelfcaringRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception the exception
	 * @see    SelfcaringRating
	 */
	public List<SelfcaringRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SelfcaringRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SelfcaringRating}
	 * @throws Exception the exception
	 */
	public List<SelfcaringRating> allSelfcaringRating(int first, int pageSize) throws Exception {
		return dao.allSelfcaringRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SelfcaringRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SelfcaringRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SelfcaringRating.class);
	}
	
    /**
     * Lazy load SelfcaringRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SelfcaringRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SelfcaringRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SelfcaringRating> allSelfcaringRating(Class<SelfcaringRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SelfcaringRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SelfcaringRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SelfcaringRating class
     * @param filters the filters
     * @return Number of rows in the SelfcaringRating entity
     * @throws Exception the exception
     */	
	public int count(Class<SelfcaringRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
