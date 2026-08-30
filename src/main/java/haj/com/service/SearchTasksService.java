package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TaskUsersDAO;
import haj.com.dao.TasksDAO;
import haj.com.entity.Tasks;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksService.
 */
public class SearchTasksService extends AbstractService {
	/** The dao. */
	private TasksDAO dao = new TasksDAO();
	
	/**
	 * Get all Tasks.
	 * 
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception the exception
	 * @see   Tasks
	 */
	public List<Tasks> allTasks() throws Exception {
	  	return dao.allTasks();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksDesc(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Tasks o order by o.id desc ";
		return (List<Tasks>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public Integer countAllTasksDesc(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o order by o.id desc ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksDescByConfigDoc(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, ConfigDocProcessEnum configDoc) throws Exception {
		String hql = "select o from Tasks o where o.workflowProcess = :configDoc order by o.id desc ";
		filters.put("configDoc", configDoc);
		return (List<Tasks>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public Integer countAllTasksDescByConfigDoc(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.workflowProcess = :configDoc order by o.id desc ";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * Create or update Tasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Tasks
	 */
	public void create(Tasks entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Tasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Tasks
	 */
	public void update(Tasks entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Tasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Tasks
	 */
	public void delete(Tasks entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Tasks}
	 * @throws Exception the exception
	 * @see    Tasks
	 */
	public Tasks findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Tasks by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception the exception
	 * @see    Tasks
	 */
	public List<Tasks> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Tasks.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception the exception
	 */
	public List<Tasks> allTasks(int first, int pageSize) throws Exception {
		return dao.allTasks(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Tasks for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Tasks
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Tasks.class);
	}
	
    /**
     * Lazy load Tasks with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Tasks class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Tasks} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasks(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Tasks>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Tasks for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Tasks class
     * @param filters the filters
     * @return Number of rows in the Tasks entity
     * @throws Exception the exception
     */	
	public int count(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
