package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.DesignationStructureStatusDAO;
import haj.com.entity.lookup.DesignationStructureStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignationStructureStatusService.
 */
public class DesignationStructureStatusService extends AbstractService {
	/** The dao. */
	private DesignationStructureStatusDAO dao = new DesignationStructureStatusDAO();

	/**
	 * Get all DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception the exception
	 * @see   DesignationStructureStatus
	 */
	public List<DesignationStructureStatus> allDesignationStructureStatus() throws Exception {
	  	return dao.allDesignationStructureStatus();
	}


	/**
	 * Create or update DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DesignationStructureStatus
	 */
	public void create(DesignationStructureStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Update  DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignationStructureStatus
	 */
	public void update(DesignationStructureStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DesignationStructureStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DesignationStructureStatus
	 */
	public void delete(DesignationStructureStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception the exception
	 * @see    DesignationStructureStatus
	 */
	public DesignationStructureStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DesignationStructureStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception the exception
	 * @see    DesignationStructureStatus
	 */
	public List<DesignationStructureStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DesignationStructureStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DesignationStructureStatus}
	 * @throws Exception the exception
	 */
	public List<DesignationStructureStatus> allDesignationStructureStatus(int first, int pageSize) throws Exception {
		return dao.allDesignationStructureStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DesignationStructureStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the DesignationStructureStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DesignationStructureStatus.class);
	}
	
    /**
     * Lazy load DesignationStructureStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 DesignationStructureStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DesignationStructureStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DesignationStructureStatus> allDesignationStructureStatus(Class<DesignationStructureStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DesignationStructureStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DesignationStructureStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity DesignationStructureStatus class
     * @param filters the filters
     * @return Number of rows in the DesignationStructureStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<DesignationStructureStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
