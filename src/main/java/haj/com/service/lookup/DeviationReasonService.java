package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DeviationReasonDAO;
import haj.com.entity.lookup.DeviationReason;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviationReasonService.
 */
public class DeviationReasonService extends AbstractService {
	/** The dao. */
	private DeviationReasonDAO dao = new DeviationReasonDAO();

	/**
	 * Get all DeviationReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception the exception
	 * @see   DeviationReason
	 */
	public List<DeviationReason> allDeviationReason() throws Exception {
	  	return dao.allDeviationReason();
	}


	/**
	 * Create or update DeviationReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DeviationReason
	 */
	public void create(DeviationReason entity) throws Exception  {
	if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DeviationReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DeviationReason
	 */
	public void update(DeviationReason entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DeviationReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DeviationReason
	 */
	public void delete(DeviationReason entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DeviationReason}
	 * @throws Exception the exception
	 * @see    DeviationReason
	 */
	public DeviationReason findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DeviationReason by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception the exception
	 * @see    DeviationReason
	 */
	public List<DeviationReason> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DeviationReason.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DeviationReason}
	 * @throws Exception the exception
	 */
	public List<DeviationReason> allDeviationReason(int first, int pageSize) throws Exception {
		return dao.allDeviationReason(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DeviationReason for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the DeviationReason
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DeviationReason.class);
	}
	
    /**
     * Lazy load DeviationReason with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 DeviationReason class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DeviationReason} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DeviationReason> allDeviationReason(Class<DeviationReason> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DeviationReason>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DeviationReason for lazy load with filters.
     *
     * @author TechFinium
     * @param entity DeviationReason class
     * @param filters the filters
     * @return Number of rows in the DeviationReason entity
     * @throws Exception the exception
     */	
	public int count(Class<DeviationReason> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
