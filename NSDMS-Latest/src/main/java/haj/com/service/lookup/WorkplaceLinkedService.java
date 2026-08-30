package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.WorkplaceLinkedDAO;
import haj.com.entity.lookup.WorkplaceLinked;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkplaceLinkedService.
 */
public class WorkplaceLinkedService extends AbstractService {
	/** The dao. */
	private WorkplaceLinkedDAO dao = new WorkplaceLinkedDAO();

	/**
	 * Get all WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception the exception
	 * @see   WorkplaceLinked
	 */
	public List<WorkplaceLinked> allWorkplaceLinked() throws Exception {
	  	return dao.allWorkplaceLinked();
	}


	/**
	 * Create or update WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceLinked
	 */
	public void create(WorkplaceLinked entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceLinked
	 */
	public void update(WorkplaceLinked entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceLinked
	 */
	public void delete(WorkplaceLinked entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception the exception
	 * @see    WorkplaceLinked
	 */
	public WorkplaceLinked findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceLinked by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception the exception
	 * @see    WorkplaceLinked
	 */
	public List<WorkplaceLinked> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceLinked.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceLinked}
	 * @throws Exception the exception
	 */
	public List<WorkplaceLinked> allWorkplaceLinked(int first, int pageSize) throws Exception {
		return dao.allWorkplaceLinked(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceLinked for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WorkplaceLinked
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceLinked.class);
	}
	
    /**
     * Lazy load WorkplaceLinked with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 WorkplaceLinked class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceLinked} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceLinked> allWorkplaceLinked(Class<WorkplaceLinked> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceLinked>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceLinked for lazy load with filters.
     *
     * @author TechFinium
     * @param entity WorkplaceLinked class
     * @param filters the filters
     * @return Number of rows in the WorkplaceLinked entity
     * @throws Exception the exception
     */	
	public int count(Class<WorkplaceLinked> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
