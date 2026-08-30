package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.CitizenResidentStatusDAO;
import haj.com.entity.lookup.CitizenResidentStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CitizenResidentStatusService.
 */
public class CitizenResidentStatusService extends AbstractService {
	/** The dao. */
	private CitizenResidentStatusDAO dao = new CitizenResidentStatusDAO();

	/**
	 * Get all CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception the exception
	 * @see   CitizenResidentStatus
	 */
	public List<CitizenResidentStatus> allCitizenResidentStatus() throws Exception {
	  	return dao.allCitizenResidentStatus();
	}


	/**
	 * Create or update CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CitizenResidentStatus
	 */
	public void create(CitizenResidentStatus entity) throws Exception  {
	   if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique.");
		if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
		} else
			 this.dao.update(entity);
	}

	/**
	 * Update  CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CitizenResidentStatus
	 */
	public void update(CitizenResidentStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CitizenResidentStatus
	 */
	public void delete(CitizenResidentStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception the exception
	 * @see    CitizenResidentStatus
	 */
	public CitizenResidentStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CitizenResidentStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception the exception
	 * @see    CitizenResidentStatus
	 */
	public List<CitizenResidentStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CitizenResidentStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CitizenResidentStatus}
	 * @throws Exception the exception
	 */
	public List<CitizenResidentStatus> allCitizenResidentStatus(int first, int pageSize) throws Exception {
		return dao.allCitizenResidentStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CitizenResidentStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CitizenResidentStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CitizenResidentStatus.class);
	}
	
    /**
     * Lazy load CitizenResidentStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 CitizenResidentStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CitizenResidentStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CitizenResidentStatus> allCitizenResidentStatus(Class<CitizenResidentStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CitizenResidentStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CitizenResidentStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity CitizenResidentStatus class
     * @param filters the filters
     * @return Number of rows in the CitizenResidentStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<CitizenResidentStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
