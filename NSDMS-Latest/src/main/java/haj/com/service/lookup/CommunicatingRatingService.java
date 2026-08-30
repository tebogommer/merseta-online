package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.CommunicatingRatingDAO;
import haj.com.entity.lookup.CommunicatingRating;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatingRatingService.
 */
public class CommunicatingRatingService extends AbstractService {
	/** The dao. */
	private CommunicatingRatingDAO dao = new CommunicatingRatingDAO();

	/**
	 * Get all CommunicatingRating.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception the exception
	 * @see   CommunicatingRating
	 */
	public List<CommunicatingRating> allCommunicatingRating() throws Exception {
	  	return dao.allCommunicatingRating();
	}


	/**
	 * Create or update CommunicatingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CommunicatingRating
	 */
	public void create(CommunicatingRating entity) throws Exception  {
		
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CommunicatingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CommunicatingRating
	 */
	public void update(CommunicatingRating entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CommunicatingRating.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CommunicatingRating
	 */
	public void delete(CommunicatingRating entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception the exception
	 * @see    CommunicatingRating
	 */
	public CommunicatingRating findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CommunicatingRating by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception the exception
	 * @see    CommunicatingRating
	 */
	public List<CommunicatingRating> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CommunicatingRating.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CommunicatingRating}
	 * @throws Exception the exception
	 */
	public List<CommunicatingRating> allCommunicatingRating(int first, int pageSize) throws Exception {
		return dao.allCommunicatingRating(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CommunicatingRating for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CommunicatingRating
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CommunicatingRating.class);
	}
	
    /**
     * Lazy load CommunicatingRating with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 CommunicatingRating class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CommunicatingRating} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CommunicatingRating> allCommunicatingRating(Class<CommunicatingRating> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CommunicatingRating>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CommunicatingRating for lazy load with filters.
     *
     * @author TechFinium
     * @param entity CommunicatingRating class
     * @param filters the filters
     * @return Number of rows in the CommunicatingRating entity
     * @throws Exception the exception
     */	
	public int count(Class<CommunicatingRating> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
