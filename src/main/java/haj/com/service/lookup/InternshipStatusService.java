package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InternshipStatusDAO;
import haj.com.entity.lookup.InternshipStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InternshipStatusService.
 */
public class InternshipStatusService extends AbstractService {
	/** The dao. */
	private InternshipStatusDAO dao = new InternshipStatusDAO();

	/**
	 * Get all InternshipStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception the exception
	 * @see   InternshipStatus
	 */
	public List<InternshipStatus> allInternshipStatus() throws Exception {
	  	return dao.allInternshipStatus();
	}


	/**
	 * Create or update InternshipStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     InternshipStatus
	 */
	public void create(InternshipStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  InternshipStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InternshipStatus
	 */
	public void update(InternshipStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  InternshipStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InternshipStatus
	 */
	public void delete(InternshipStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InternshipStatus}
	 * @throws Exception the exception
	 * @see    InternshipStatus
	 */
	public InternshipStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find InternshipStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception the exception
	 * @see    InternshipStatus
	 */
	public List<InternshipStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load InternshipStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.InternshipStatus}
	 * @throws Exception the exception
	 */
	public List<InternshipStatus> allInternshipStatus(int first, int pageSize) throws Exception {
		return dao.allInternshipStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of InternshipStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InternshipStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(InternshipStatus.class);
	}
	
    /**
     * Lazy load InternshipStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 InternshipStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.InternshipStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<InternshipStatus> allInternshipStatus(Class<InternshipStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<InternshipStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of InternshipStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity InternshipStatus class
     * @param filters the filters
     * @return Number of rows in the InternshipStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<InternshipStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
