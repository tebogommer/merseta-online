package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProviderStatusDAO;
import haj.com.entity.lookup.ProviderStatus;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderStatusService.
 */
public class ProviderStatusService extends AbstractService {
	/** The dao. */
	private ProviderStatusDAO dao = new ProviderStatusDAO();
	
	private static ProviderStatusService providerStatusService;
	public static synchronized ProviderStatusService instance() {
		if (providerStatusService == null) {
			providerStatusService = new ProviderStatusService();
		}
		return providerStatusService;
	}
	

	/**
	 * Get all ProviderStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception the exception
	 * @see   ProviderStatus
	 */
	public List<ProviderStatus> allProviderStatus() throws Exception {
	  	return dao.allProviderStatus();
	}


	/**
	 * Create or update ProviderStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderStatus
	 */
	public void create(ProviderStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderStatus
	 */
	public void update(ProviderStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderStatus
	 */
	public void delete(ProviderStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderStatus}
	 * @throws Exception the exception
	 * @see    ProviderStatus
	 */
	public ProviderStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception the exception
	 * @see    ProviderStatus
	 */
	public List<ProviderStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public ProviderStatus findByCode(String code) throws Exception {
		return dao.findByCode(code);
	}
	
	/**
	 * Lazy load ProviderStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderStatus}
	 * @throws Exception the exception
	 */
	public List<ProviderStatus> allProviderStatus(int first, int pageSize) throws Exception {
		return dao.allProviderStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProviderStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderStatus.class);
	}
	
    /**
     * Lazy load ProviderStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ProviderStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderStatus> allProviderStatus(Class<ProviderStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProviderStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProviderStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ProviderStatus class
     * @param filters the filters
     * @return Number of rows in the ProviderStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<ProviderStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
