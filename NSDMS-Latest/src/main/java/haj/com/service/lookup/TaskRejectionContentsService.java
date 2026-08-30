package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TaskRejectionContentsDAO;
import haj.com.entity.lookup.TaskRejectionContents;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskRejectionContentsService.
 */
public class TaskRejectionContentsService extends AbstractService {
	/** The dao. */
	private TaskRejectionContentsDAO dao = new TaskRejectionContentsDAO();

	/**
	 * Get all TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception the exception
	 * @see   TaskRejectionContents
	 */
	public List<TaskRejectionContents> allTaskRejectionContents() throws Exception {
	  	return dao.allTaskRejectionContents();
	}


	/**
	 * Create or update TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TaskRejectionContents
	 */
	public void create(TaskRejectionContents entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TaskRejectionContents
	 */
	public void update(TaskRejectionContents entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TaskRejectionContents
	 */
	public void delete(TaskRejectionContents entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception the exception
	 * @see    TaskRejectionContents
	 */
	public TaskRejectionContents findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TaskRejectionContents by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception the exception
	 * @see    TaskRejectionContents
	 */
	public List<TaskRejectionContents> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TaskRejectionContents.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TaskRejectionContents}
	 * @throws Exception the exception
	 */
	public List<TaskRejectionContents> allTaskRejectionContents(int first, int pageSize) throws Exception {
		return dao.allTaskRejectionContents(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TaskRejectionContents for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TaskRejectionContents
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TaskRejectionContents.class);
	}
	
    /**
     * Lazy load TaskRejectionContents with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TaskRejectionContents class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TaskRejectionContents} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TaskRejectionContents> allTaskRejectionContents(Class<TaskRejectionContents> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TaskRejectionContents>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TaskRejectionContents for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TaskRejectionContents class
     * @param filters the filters
     * @return Number of rows in the TaskRejectionContents entity
     * @throws Exception the exception
     */	
	public int count(Class<TaskRejectionContents> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
