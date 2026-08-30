package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ScarcityReasonDAO;
import haj.com.entity.lookup.ScarcityReason;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScarcityReasonService.
 */
public class ScarcityReasonService extends AbstractService {
	/** The dao. */
	private ScarcityReasonDAO dao = new ScarcityReasonDAO();

	/**
	 * Get all ScarcityReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception the exception
	 * @see   ScarcityReason
	 */
	public List<ScarcityReason> allScarcityReason() throws Exception {
	  	return dao.allScarcityReason();
	}


	/**
	 * Create or update ScarcityReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ScarcityReason
	 */
	public void create(ScarcityReason entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ScarcityReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScarcityReason
	 */
	public void update(ScarcityReason entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ScarcityReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScarcityReason
	 */
	public void delete(ScarcityReason entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ScarcityReason}
	 * @throws Exception the exception
	 * @see    ScarcityReason
	 */
	public ScarcityReason findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ScarcityReason by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception the exception
	 * @see    ScarcityReason
	 */
	public List<ScarcityReason> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ScarcityReason.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ScarcityReason}
	 * @throws Exception the exception
	 */
	public List<ScarcityReason> allScarcityReason(int first, int pageSize) throws Exception {
		return dao.allScarcityReason(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ScarcityReason for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ScarcityReason
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ScarcityReason.class);
	}
	
    /**
     * Lazy load ScarcityReason with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ScarcityReason class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ScarcityReason} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ScarcityReason> allScarcityReason(Class<ScarcityReason> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ScarcityReason>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ScarcityReason for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ScarcityReason class
     * @param filters the filters
     * @return Number of rows in the ScarcityReason entity
     * @throws Exception the exception
     */	
	public int count(Class<ScarcityReason> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
