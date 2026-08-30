package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EnrolmentStatusReasonDAO;
import haj.com.entity.lookup.EnrolmentStatusReason;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EnrolmentStatusReasonService.
 */
public class EnrolmentStatusReasonService extends AbstractService {
	/** The dao. */
	private EnrolmentStatusReasonDAO dao = new EnrolmentStatusReasonDAO();

	/**
	 * Get all EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception the exception
	 * @see   EnrolmentStatusReason
	 */
	public List<EnrolmentStatusReason> allEnrolmentStatusReason() throws Exception {
	  	return dao.allEnrolmentStatusReason();
	}


	/**
	 * Create or update EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EnrolmentStatusReason
	 */
	public void create(EnrolmentStatusReason entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentStatusReason
	 */
	public void update(EnrolmentStatusReason entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EnrolmentStatusReason.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EnrolmentStatusReason
	 */
	public void delete(EnrolmentStatusReason entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception the exception
	 * @see    EnrolmentStatusReason
	 */
	public EnrolmentStatusReason findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EnrolmentStatusReason by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception the exception
	 * @see    EnrolmentStatusReason
	 */
	public List<EnrolmentStatusReason> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EnrolmentStatusReason.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EnrolmentStatusReason}
	 * @throws Exception the exception
	 */
	public List<EnrolmentStatusReason> allEnrolmentStatusReason(int first, int pageSize) throws Exception {
		return dao.allEnrolmentStatusReason(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EnrolmentStatusReason for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EnrolmentStatusReason
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EnrolmentStatusReason.class);
	}
	
    /**
     * Lazy load EnrolmentStatusReason with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 EnrolmentStatusReason class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EnrolmentStatusReason} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EnrolmentStatusReason> allEnrolmentStatusReason(Class<EnrolmentStatusReason> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EnrolmentStatusReason>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EnrolmentStatusReason for lazy load with filters.
     *
     * @author TechFinium
     * @param entity EnrolmentStatusReason class
     * @param filters the filters
     * @return Number of rows in the EnrolmentStatusReason entity
     * @throws Exception the exception
     */	
	public int count(Class<EnrolmentStatusReason> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
