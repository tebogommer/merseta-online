package haj.com.service;

import java.util.List;

import haj.com.dao.AllocationChangesReasonsDAO;
import haj.com.entity.AllocationChangesReasons;
import haj.com.entity.DgAllocationParent;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AllocationChangesReasonsService extends AbstractService {
	/** The dao. */
	private AllocationChangesReasonsDAO dao = new AllocationChangesReasonsDAO();

	/**
	 * Get all AllocationChangesReasons
 	 * @author TechFinium 
 	 * @see   AllocationChangesReasons
 	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
	 * @throws Exception the exception
 	 */
	public List<AllocationChangesReasons> allAllocationChangesReasons() throws Exception {
	  	return dao.allAllocationChangesReasons();
	}
	
	public List<AllocationChangesReasons> findDGAllocationChangesReasons(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findDGAllocationChangesReasons(dgAllocationParent);
	}


	/**
	 * Create or update AllocationChangesReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AllocationChangesReasons
	 */
	public void create(AllocationChangesReasons entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AllocationChangesReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AllocationChangesReasons
	 */
	public void update(AllocationChangesReasons entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AllocationChangesReasons.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AllocationChangesReasons
	 */
	public void delete(AllocationChangesReasons entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AllocationChangesReasons}
	 * @throws Exception the exception
	 * @see    AllocationChangesReasons
	 */
	public AllocationChangesReasons findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AllocationChangesReasons by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
	 * @throws Exception the exception
	 * @see    AllocationChangesReasons
	 */
	public List<AllocationChangesReasons> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AllocationChangesReasons
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AllocationChangesReasons}
	 * @throws Exception the exception
	 */
	public List<AllocationChangesReasons> allAllocationChangesReasons(int first, int pageSize) throws Exception {
		return dao.allAllocationChangesReasons(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AllocationChangesReasons for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AllocationChangesReasons
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AllocationChangesReasons.class);
	}
	
    /**
     * Lazy load AllocationChangesReasons with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AllocationChangesReasons class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AllocationChangesReasons} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AllocationChangesReasons> allAllocationChangesReasons(Class<AllocationChangesReasons> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AllocationChangesReasons>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AllocationChangesReasons for lazy load with filters
     * @author TechFinium 
     * @param entity AllocationChangesReasons class
     * @param filters the filters
     * @return Number of rows in the AllocationChangesReasons entity
     * @throws Exception the exception     
     */	
	public int count(Class<AllocationChangesReasons> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public AllocationChangesReasons findByDgAllocationParent(Long id) {
		return dao.findByDgAllocationParent(id);
	}
}
