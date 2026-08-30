package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DisabilityStatusDAO;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabilityStatusService.
 */
public class DisabilityStatusService extends AbstractService {
	/** The dao. */
	private DisabilityStatusDAO dao = new DisabilityStatusDAO();

	/**
	 * Get all DisabilityStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception the exception
	 * @see   DisabilityStatus
	 */
	public List<DisabilityStatus> allDisabilityStatus() throws Exception {
	  	return dao.allDisabilityStatus();
	}


	/**
	 * Create or update DisabilityStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DisabilityStatus
	 */
	public void create(DisabilityStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DisabilityStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DisabilityStatus
	 */
	public void update(DisabilityStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DisabilityStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DisabilityStatus
	 */
	public void delete(DisabilityStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception the exception
	 * @see    DisabilityStatus
	 */
	public DisabilityStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DisabilityStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception the exception
	 * @see    DisabilityStatus
	 */
	public List<DisabilityStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<DisabilityStatus> findByNameCanSelect(String description) throws Exception {
		return dao.findByNameCanSelect(description);
	}
	
	/**
	 * Find DisabilityStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception the exception
	 * @see    DisabilityStatus
	 */
	public List<DisabilityStatus> completeDisabilityStatusExcludeNone(String desc, Long id) throws Exception {
		return dao.completeDisabilityStatusExcludeNone(desc, id);
	}
	
	public List<DisabilityStatus> completeDisabilityCanSelect(String desc, Boolean canSelect) throws Exception {
		return dao.completeDisabilityCanSelect(desc, canSelect);
	}	
	
	/**
	 * Lazy load DisabilityStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DisabilityStatus}
	 * @throws Exception the exception
	 */
	public List<DisabilityStatus> allDisabilityStatus(int first, int pageSize) throws Exception {
		return dao.allDisabilityStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DisabilityStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the DisabilityStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DisabilityStatus.class);
	}
	
    /**
     * Lazy load DisabilityStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 DisabilityStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DisabilityStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DisabilityStatus> allDisabilityStatus(Class<DisabilityStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DisabilityStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DisabilityStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity DisabilityStatus class
     * @param filters the filters
     * @return Number of rows in the DisabilityStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<DisabilityStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the disability status
	 * @throws Exception the exception
	 */
	public DisabilityStatus findByCode(String code) throws Exception {
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
