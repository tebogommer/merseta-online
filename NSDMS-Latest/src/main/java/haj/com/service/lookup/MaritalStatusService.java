package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.MaritalStatusDAO;
import haj.com.entity.lookup.MaritalStatus;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class MaritalStatusService extends AbstractService {
	/** The dao. */
	private MaritalStatusDAO dao = new MaritalStatusDAO();

	/**
	 * Get all MaritalStatus
 	 * @author TechFinium 
 	 * @see   MaritalStatus
 	 * @return a list of {@link haj.com.entity.MaritalStatus}
	 * @throws Exception the exception
 	 */
	public List<MaritalStatus> allMaritalStatus() throws Exception {
	  	return dao.allMaritalStatus();
	}


	/**
	 * Create or update MaritalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     MaritalStatus
	 */
	public void create(MaritalStatus entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  MaritalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MaritalStatus
	 */
	public void update(MaritalStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MaritalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MaritalStatus
	 */
	public void delete(MaritalStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.MaritalStatus}
	 * @throws Exception the exception
	 * @see    MaritalStatus
	 */
	public MaritalStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find MaritalStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MaritalStatus}
	 * @throws Exception the exception
	 * @see    MaritalStatus
	 */
	public List<MaritalStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<MaritalStatus> findByNameAspopulated(String desc) throws Exception {
		return dao.findByNameAspopulated(desc);
	}
	
	
	/**
	 * Lazy load MaritalStatus
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MaritalStatus}
	 * @throws Exception the exception
	 */
	public List<MaritalStatus> allMaritalStatus(int first, int pageSize) throws Exception {
		return dao.allMaritalStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of MaritalStatus for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the MaritalStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MaritalStatus.class);
	}
	
    /**
     * Lazy load MaritalStatus with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 MaritalStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.MaritalStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MaritalStatus> allMaritalStatus(Class<MaritalStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<MaritalStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of MaritalStatus for lazy load with filters
     * @author TechFinium 
     * @param entity MaritalStatus class
     * @param filters the filters
     * @return Number of rows in the MaritalStatus entity
     * @throws Exception the exception     
     */	
	public int count(Class<MaritalStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
