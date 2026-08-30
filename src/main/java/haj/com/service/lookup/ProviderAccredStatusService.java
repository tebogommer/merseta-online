package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProviderAccredStatusDAO;
import haj.com.entity.lookup.ProviderAccredStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccredStatusService.
 */
public class ProviderAccredStatusService extends AbstractService {
	/** The dao. */
	private ProviderAccredStatusDAO dao = new ProviderAccredStatusDAO();

	/**
	 * Get all ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception the exception
	 * @see   ProviderAccredStatus
	 */
	public List<ProviderAccredStatus> allProviderAccredStatus() throws Exception {
	  	return dao.allProviderAccredStatus();
	}


	/**
	 * Create or update ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderAccredStatus
	 */
	public void create(ProviderAccredStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderAccredStatus
	 */
	public void update(ProviderAccredStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderAccredStatus
	 */
	public void delete(ProviderAccredStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception the exception
	 * @see    ProviderAccredStatus
	 */
	public ProviderAccredStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderAccredStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception the exception
	 * @see    ProviderAccredStatus
	 */
	public List<ProviderAccredStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ProviderAccredStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderAccredStatus}
	 * @throws Exception the exception
	 */
	public List<ProviderAccredStatus> allProviderAccredStatus(int first, int pageSize) throws Exception {
		return dao.allProviderAccredStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderAccredStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProviderAccredStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderAccredStatus.class);
	}
	
    /**
     * Lazy load ProviderAccredStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ProviderAccredStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderAccredStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderAccredStatus> allProviderAccredStatus(Class<ProviderAccredStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProviderAccredStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProviderAccredStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ProviderAccredStatus class
     * @param filters the filters
     * @return Number of rows in the ProviderAccredStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<ProviderAccredStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
