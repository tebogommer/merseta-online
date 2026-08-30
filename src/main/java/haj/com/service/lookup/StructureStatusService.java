package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.StructureStatusDAO;
import haj.com.entity.lookup.StructureStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class StructureStatusService.
 */
public class StructureStatusService extends AbstractService {
	/** The dao. */
	private StructureStatusDAO dao = new StructureStatusDAO();

	/**
	 * Get all StructureStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception the exception
	 * @see   StructureStatus
	 */
	public List<StructureStatus> allStructureStatus() throws Exception {
	  	return dao.allStructureStatus();
	}


	/**
	 * Create or update StructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     StructureStatus
	 */
	public void create(StructureStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  StructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StructureStatus
	 */
	public void update(StructureStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  StructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    StructureStatus
	 */
	public void delete(StructureStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StructureStatus}
	 * @throws Exception the exception
	 * @see    StructureStatus
	 */
	public StructureStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find StructureStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception the exception
	 * @see    StructureStatus
	 */
	public List<StructureStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load StructureStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.StructureStatus}
	 * @throws Exception the exception
	 */
	public List<StructureStatus> allStructureStatus(int first, int pageSize) throws Exception {
		return dao.allStructureStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of StructureStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the StructureStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(StructureStatus.class);
	}
	
    /**
     * Lazy load StructureStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 StructureStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.StructureStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<StructureStatus> allStructureStatus(Class<StructureStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<StructureStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of StructureStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity StructureStatus class
     * @param filters the filters
     * @return Number of rows in the StructureStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<StructureStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
