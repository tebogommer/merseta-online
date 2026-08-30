package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.PopiActStatusDAO;
import haj.com.entity.lookup.PopiActStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class PopiActStatusService.
 */
public class PopiActStatusService extends AbstractService {
	/** The dao. */
	private PopiActStatusDAO dao = new PopiActStatusDAO();

	/**
	 * Get all PopiActStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception the exception
	 * @see   PopiActStatus
	 */
	public List<PopiActStatus> allPopiActStatus() throws Exception {
	  	return dao.allPopiActStatus();
	}


	/**
	 * Create or update PopiActStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PopiActStatus
	 */
	public void create(PopiActStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PopiActStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PopiActStatus
	 */
	public void update(PopiActStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PopiActStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PopiActStatus
	 */
	public void delete(PopiActStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PopiActStatus}
	 * @throws Exception the exception
	 * @see    PopiActStatus
	 */
	public PopiActStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PopiActStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception the exception
	 * @see    PopiActStatus
	 */
	public List<PopiActStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PopiActStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PopiActStatus}
	 * @throws Exception the exception
	 */
	public List<PopiActStatus> allPopiActStatus(int first, int pageSize) throws Exception {
		return dao.allPopiActStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PopiActStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the PopiActStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PopiActStatus.class);
	}
	
    /**
     * Lazy load PopiActStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 PopiActStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PopiActStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PopiActStatus> allPopiActStatus(Class<PopiActStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PopiActStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PopiActStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity PopiActStatus class
     * @param filters the filters
     * @return Number of rows in the PopiActStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<PopiActStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
