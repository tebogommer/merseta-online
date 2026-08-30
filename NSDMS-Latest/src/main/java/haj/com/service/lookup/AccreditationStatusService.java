package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.AccreditationStatusDAO;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AccreditationStatusService extends AbstractService {
	/** The dao. */
	private AccreditationStatusDAO dao = new AccreditationStatusDAO();

	/**
	 * Get all AccreditationStatus
 	 * @author TechFinium 
 	 * @see   AccreditationStatus
 	 * @return a list of {@link haj.com.entity.AccreditationStatus}
	 * @throws Exception the exception
 	 */
	public List<AccreditationStatus> allAccreditationStatus() throws Exception {
	  	return dao.allAccreditationStatus();
	}
	
	public List<AccreditationStatus> allNoLegacyAccreditationStatus() throws Exception {
	  	return dao.allNoLegacyAccreditationStatus();
	}


	/**
	 * Create or update AccreditationStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AccreditationStatus
	 */
	public void create(AccreditationStatus entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AccreditationStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AccreditationStatus
	 */
	public void update(AccreditationStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AccreditationStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AccreditationStatus
	 */
	public void delete(AccreditationStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AccreditationStatus}
	 * @throws Exception the exception
	 * @see    AccreditationStatus
	 */
	public AccreditationStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AccreditationStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AccreditationStatus}
	 * @throws Exception the exception
	 * @see    AccreditationStatus
	 */
	public List<AccreditationStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<AccreditationStatus> findByDescription(String desc) throws Exception {
		return dao.findByDescription(desc);
	}
	
	/**
	 * Lazy load AccreditationStatus
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AccreditationStatus}
	 * @throws Exception the exception
	 */
	public List<AccreditationStatus> allAccreditationStatus(int first, int pageSize) throws Exception {
		return dao.allAccreditationStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AccreditationStatus for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AccreditationStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AccreditationStatus.class);
	}
	
    /**
     * Lazy load AccreditationStatus with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AccreditationStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AccreditationStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> allAccreditationStatus(Class<AccreditationStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AccreditationStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AccreditationStatus for lazy load with filters
     * @author TechFinium 
     * @param entity AccreditationStatus class
     * @param filters the filters
     * @return Number of rows in the AccreditationStatus entity
     * @throws Exception the exception     
     */	
	public int count(Class<AccreditationStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
