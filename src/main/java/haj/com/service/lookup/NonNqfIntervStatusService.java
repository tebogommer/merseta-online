package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NonNqfIntervStatusDAO;
import haj.com.entity.lookup.NonNqfIntervStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class NonNqfIntervStatusService.
 */
public class NonNqfIntervStatusService extends AbstractService {
	/** The dao. */
	private NonNqfIntervStatusDAO dao = new NonNqfIntervStatusDAO();

	/**
	 * Get all NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception the exception
	 * @see   NonNqfIntervStatus
	 */
	public List<NonNqfIntervStatus> allNonNqfIntervStatus() throws Exception {
	  	return dao.allNonNqfIntervStatus();
	}


	/**
	 * Create or update NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NonNqfIntervStatus
	 */
	public void create(NonNqfIntervStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonNqfIntervStatus
	 */
	public void update(NonNqfIntervStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonNqfIntervStatus
	 */
	public void delete(NonNqfIntervStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception the exception
	 * @see    NonNqfIntervStatus
	 */
	public NonNqfIntervStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NonNqfIntervStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception the exception
	 * @see    NonNqfIntervStatus
	 */
	public List<NonNqfIntervStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NonNqfIntervStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NonNqfIntervStatus}
	 * @throws Exception the exception
	 */
	public List<NonNqfIntervStatus> allNonNqfIntervStatus(int first, int pageSize) throws Exception {
		return dao.allNonNqfIntervStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NonNqfIntervStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the NonNqfIntervStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NonNqfIntervStatus.class);
	}
	
    /**
     * Lazy load NonNqfIntervStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 NonNqfIntervStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NonNqfIntervStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NonNqfIntervStatus> allNonNqfIntervStatus(Class<NonNqfIntervStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NonNqfIntervStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NonNqfIntervStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity NonNqfIntervStatus class
     * @param filters the filters
     * @return Number of rows in the NonNqfIntervStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<NonNqfIntervStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
