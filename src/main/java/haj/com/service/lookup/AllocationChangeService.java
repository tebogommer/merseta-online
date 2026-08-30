package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.AllocationChangeDAO;
import haj.com.entity.enums.AllocationChangeTypeEnum;
import haj.com.entity.lookup.AllocationChange;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AllocationChangeService extends AbstractService {
	/** The dao. */
	private AllocationChangeDAO dao = new AllocationChangeDAO();

	/**
	 * Get all AllocationChange
 	 * @author TechFinium 
 	 * @see   AllocationChange
 	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception the exception
 	 */
	public List<AllocationChange> allAllocationChange() throws Exception {
	  	return dao.allAllocationChange();
	}
	
	public List<AllocationChange> allAllocationChangeByType(AllocationChangeTypeEnum allocationChangeTypeEnum) throws Exception {
		return dao.allAllocationChangeByType(allocationChangeTypeEnum);
	}


	/**
	 * Create or update AllocationChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AllocationChange
	 */
	public void create(AllocationChange entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AllocationChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AllocationChange
	 */
	public void update(AllocationChange entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AllocationChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AllocationChange
	 */
	public void delete(AllocationChange entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AllocationChange}
	 * @throws Exception the exception
	 * @see    AllocationChange
	 */
	public AllocationChange findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AllocationChange by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception the exception
	 * @see    AllocationChange
	 */
	public List<AllocationChange> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AllocationChange
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AllocationChange}
	 * @throws Exception the exception
	 */
	public List<AllocationChange> allAllocationChange(int first, int pageSize) throws Exception {
		return dao.allAllocationChange(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AllocationChange for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AllocationChange
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AllocationChange.class);
	}
	
    /**
     * Lazy load AllocationChange with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AllocationChange class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AllocationChange} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AllocationChange> allAllocationChange(Class<AllocationChange> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AllocationChange>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AllocationChange for lazy load with filters
     * @author TechFinium 
     * @param entity AllocationChange class
     * @param filters the filters
     * @return Number of rows in the AllocationChange entity
     * @throws Exception the exception     
     */	
	public int count(Class<AllocationChange> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
