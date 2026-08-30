package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TaskUsersDAO;
import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskUsersService.
 */
public class TaskUsersService extends AbstractService {
	/** The dao. */
	private TaskUsersDAO dao = new TaskUsersDAO();

	/**
	 * Get all TaskUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public List<TaskUsers> allTaskUsers() throws Exception {
		return dao.allTaskUsers();
	}

	/**
	 * Create or update TaskUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public void create(TaskUsers entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update TaskUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public void update(TaskUsers entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TaskUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public void delete(TaskUsers entity) throws Exception {
		this.dao.delete(entity);
	}
	
	public void deleteBatch(List<TaskUsers> entity) throws Exception {
		this.dao.deleteBatch((List<IDataEntity>)(List<?>)entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TaskUsers}
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public TaskUsers findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find TaskUsers by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception
	 *             the exception
	 * @see TaskUsers
	 */
	public List<TaskUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TaskUsers.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TaskUsers}
	 * @throws Exception
	 *             the exception
	 */
	public List<TaskUsers> allTaskUsers(int first, int pageSize) throws Exception {
		return dao.allTaskUsers(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TaskUsers for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TaskUsers
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TaskUsers.class);
	}

	/**
	 * Lazy load TaskUsers with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            TaskUsers class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.TaskUsers} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TaskUsers> allTaskUsers(Class<TaskUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<TaskUsers>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of TaskUsers for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            TaskUsers class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TaskUsers entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TaskUsers> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<TaskUsers> findByTask(Tasks tasks) throws Exception {
		return dao.findByTask(tasks);
	}
}
