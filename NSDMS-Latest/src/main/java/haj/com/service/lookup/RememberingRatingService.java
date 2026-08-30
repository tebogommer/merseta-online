package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RememberingRatingDAO;
import haj.com.entity.lookup.RememberingRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class RememberingRatingService.
 */
public class RememberingRatingService extends AbstractService {
	/** The dao. */
	private RememberingRatingDAO dao = new RememberingRatingDAO();

	/**
	 * Get all RememberingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception the exception
	 * @see   RememberingRating
	 */
	public List<RememberingRating> allRememberingRating() throws Exception {
	  	return dao.allRememberingRating();
	}


	/**
	 * Create or update RememberingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     RememberingRating
	 */
	public void create(RememberingRating entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");

	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  RememberingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    RememberingRating
	 */
	public void update(RememberingRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  RememberingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    RememberingRating
	 */
	public void delete(RememberingRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.RememberingRating}
	 * @throws Exception the exception
	 * @see    RememberingRating
	 */
	public RememberingRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find RememberingRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception the exception
	 * @see    RememberingRating
	 */
	public List<RememberingRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load RememberingRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.RememberingRating}
	 * @throws Exception the exception
	 */
	public List<RememberingRating> allRememberingRating(int first, int pageSize) throws Exception {
		return dao.allRememberingRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of RememberingRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the RememberingRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(RememberingRating.class);
	}
	
    /**
     * Lazy load RememberingRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 RememberingRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.RememberingRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<RememberingRating> allRememberingRating(Class<RememberingRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<RememberingRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of RememberingRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity RememberingRating class
     * @param filters the filters
     * @return Number of rows in the RememberingRating entity
     * @throws Exception the exception
     */	
	public int count(Class<RememberingRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
