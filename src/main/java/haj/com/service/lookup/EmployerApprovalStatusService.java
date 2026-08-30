package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EmployerApprovalStatusDAO;
import haj.com.entity.lookup.EmployerApprovalStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployerApprovalStatusService.
 */
public class EmployerApprovalStatusService extends AbstractService {
	/** The dao. */
	private EmployerApprovalStatusDAO dao = new EmployerApprovalStatusDAO();

	/**
	 * Get all EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception the exception
	 * @see   EmployerApprovalStatus
	 */
	public List<EmployerApprovalStatus> allEmployerApprovalStatus() throws Exception {
	  	return dao.allEmployerApprovalStatus();
	}


	/**
	 * Create or update EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EmployerApprovalStatus
	 */
	public void create(EmployerApprovalStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmployerApprovalStatus
	 */
	public void update(EmployerApprovalStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EmployerApprovalStatus
	 */
	public void delete(EmployerApprovalStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception the exception
	 * @see    EmployerApprovalStatus
	 */
	public EmployerApprovalStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EmployerApprovalStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception the exception
	 * @see    EmployerApprovalStatus
	 */
	public List<EmployerApprovalStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EmployerApprovalStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EmployerApprovalStatus}
	 * @throws Exception the exception
	 */
	public List<EmployerApprovalStatus> allEmployerApprovalStatus(int first, int pageSize) throws Exception {
		return dao.allEmployerApprovalStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EmployerApprovalStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EmployerApprovalStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EmployerApprovalStatus.class);
	}
	
    /**
     * Lazy load EmployerApprovalStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EmployerApprovalStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EmployerApprovalStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EmployerApprovalStatus> allEmployerApprovalStatus(Class<EmployerApprovalStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EmployerApprovalStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EmployerApprovalStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EmployerApprovalStatus class
     * @param filters the filters
     * @return Number of rows in the EmployerApprovalStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<EmployerApprovalStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
