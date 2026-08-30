package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.DisabilityRatingDAO;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class DisabilityRatingService extends AbstractService {
	/** The dao. */
	private DisabilityRatingDAO dao = new DisabilityRatingDAO();

	/**
	 * Get all DisabilityRating
 	 * @author TechFinium 
 	 * @see   DisabilityRating
 	 * @return a list of {@link haj.com.entity.DisabilityRating}
	 * @throws Exception the exception
 	 */
	public List<DisabilityRating> allDisabilityRating() throws Exception {
	  	return dao.allDisabilityRating();
	}


	/**
	 * Create or update DisabilityRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DisabilityRating
	 */
	public void create(DisabilityRating entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DisabilityRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DisabilityRating
	 */
	public void update(DisabilityRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DisabilityRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DisabilityRating
	 */
	public void delete(DisabilityRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DisabilityRating}
	 * @throws Exception the exception
	 * @see    DisabilityRating
	 */
	public DisabilityRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DisabilityRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DisabilityRating}
	 * @throws Exception the exception
	 * @see    DisabilityRating
	 */
	public List<DisabilityRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<DisabilityRating> findByDisability(Long id) throws Exception {
		return dao.findByDisability(id);
	}
	
	/**
	 * Lazy load DisabilityRating
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DisabilityRating}
	 * @throws Exception the exception
	 */
	public List<DisabilityRating> allDisabilityRating(int first, int pageSize) throws Exception {
		return dao.allDisabilityRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DisabilityRating for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DisabilityRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DisabilityRating.class);
	}
	
    /**
     * Lazy load DisabilityRating with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DisabilityRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DisabilityRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DisabilityRating> allDisabilityRating(Class<DisabilityRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DisabilityRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DisabilityRating for lazy load with filters
     * @author TechFinium 
     * @param entity DisabilityRating class
     * @param filters the filters
     * @return Number of rows in the DisabilityRating entity
     * @throws Exception the exception     
     */	
	public int count(Class<DisabilityRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
