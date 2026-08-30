package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NqfDesigStatusDAO;
import haj.com.entity.lookup.NqfDesigStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfDesigStatusService.
 */
public class NqfDesigStatusService extends AbstractService {
	/** The dao. */
	private NqfDesigStatusDAO dao = new NqfDesigStatusDAO();

	/**
	 * Get all NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception the exception
	 * @see   NqfDesigStatus
	 */
	public List<NqfDesigStatus> allNqfDesigStatus() throws Exception {
	  	return dao.allNqfDesigStatus();
	}


	/**
	 * Create or update NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NqfDesigStatus
	 */
	public void create(NqfDesigStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NqfDesigStatus
	 */
	public void update(NqfDesigStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NqfDesigStatus
	 */
	public void delete(NqfDesigStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception the exception
	 * @see    NqfDesigStatus
	 */
	public NqfDesigStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NqfDesigStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception the exception
	 * @see    NqfDesigStatus
	 */
	public List<NqfDesigStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NqfDesigStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NqfDesigStatus}
	 * @throws Exception the exception
	 */
	public List<NqfDesigStatus> allNqfDesigStatus(int first, int pageSize) throws Exception {
		return dao.allNqfDesigStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NqfDesigStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the NqfDesigStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NqfDesigStatus.class);
	}
	
    /**
     * Lazy load NqfDesigStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 NqfDesigStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NqfDesigStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NqfDesigStatus> allNqfDesigStatus(Class<NqfDesigStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NqfDesigStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NqfDesigStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity NqfDesigStatus class
     * @param filters the filters
     * @return Number of rows in the NqfDesigStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<NqfDesigStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
