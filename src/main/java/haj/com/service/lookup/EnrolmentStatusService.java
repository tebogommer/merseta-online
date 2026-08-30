package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EnrolmentStatusDAO;
import haj.com.entity.lookup.EnrolmentStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusService.
 */
public class EnrolmentStatusService extends AbstractService {
	/** The dao. */
	private EnrolmentStatusDAO dao = new EnrolmentStatusDAO();

	/**
	 * Get all EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception the exception
	 * @see   EnrolmentStatus
	 */
	public List<EnrolmentStatus> allEnrolmentStatus() throws Exception {
	  	return dao.allEnrolmentStatus();
	}


	/**
	 * Create or update EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EnrolmentStatus
	 */
	public void create(EnrolmentStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentStatus
	 */
	public void update(EnrolmentStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EnrolmentStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentStatus
	 */
	public void delete(EnrolmentStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception the exception
	 * @see    EnrolmentStatus
	 */
	public EnrolmentStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EnrolmentStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception the exception
	 * @see    EnrolmentStatus
	 */
	public List<EnrolmentStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EnrolmentStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EnrolmentStatus}
	 * @throws Exception the exception
	 */
	public List<EnrolmentStatus> allEnrolmentStatus(int first, int pageSize) throws Exception {
		return dao.allEnrolmentStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EnrolmentStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EnrolmentStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EnrolmentStatus.class);
	}
	
    /**
     * Lazy load EnrolmentStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EnrolmentStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EnrolmentStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatus> allEnrolmentStatus(Class<EnrolmentStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EnrolmentStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EnrolmentStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EnrolmentStatus class
     * @param filters the filters
     * @return Number of rows in the EnrolmentStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<EnrolmentStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<EnrolmentStatus> allEnrolmentStatusWSP() throws Exception {
		return dao.allEnrolmentStatusWSP();
	}
	
	public List<EnrolmentStatus> allEnrolmentStatusATR() throws Exception {
		return dao.allEnrolmentStatusATR();
	}
}
