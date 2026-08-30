package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.HearingRatingDAO;
import haj.com.entity.lookup.HearingRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HearingRatingService.
 */
public class HearingRatingService extends AbstractService {
	/** The dao. */
	private HearingRatingDAO dao = new HearingRatingDAO();

	/**
	 * Get all HearingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception the exception
	 * @see   HearingRating
	 */
	public List<HearingRating> allHearingRating() throws Exception {
	  	return dao.allHearingRating();
	}


	/**
	 * Create or update HearingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HearingRating
	 */
	public void create(HearingRating entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HearingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HearingRating
	 */
	public void update(HearingRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HearingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HearingRating
	 */
	public void delete(HearingRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HearingRating}
	 * @throws Exception the exception
	 * @see    HearingRating
	 */
	public HearingRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HearingRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception the exception
	 * @see    HearingRating
	 */
	public List<HearingRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load HearingRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HearingRating}
	 * @throws Exception the exception
	 */
	public List<HearingRating> allHearingRating(int first, int pageSize) throws Exception {
		return dao.allHearingRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of HearingRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HearingRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HearingRating.class);
	}
	
    /**
     * Lazy load HearingRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 HearingRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HearingRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<HearingRating> allHearingRating(Class<HearingRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HearingRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of HearingRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity HearingRating class
     * @param filters the filters
     * @return Number of rows in the HearingRating entity
     * @throws Exception the exception
     */	
	public int count(Class<HearingRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
