package haj.com.service;

import java.util.Date;
import java.util.List;

import haj.com.dao.ScheduledEventUsersDAO;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ScheduledEventUsersService extends AbstractService {
	/** The dao. */
	private ScheduledEventUsersDAO dao = new ScheduledEventUsersDAO();

	/**
	 * Get all ScheduledEventUsers
 	 * @author TechFinium 
 	 * @see   ScheduledEventUsers
 	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
	 * @throws Exception the exception
 	 */
	public List<ScheduledEventUsers> allScheduledEventUsers() throws Exception {
	  	return dao.allScheduledEventUsers();
	}
	
	public List<ScheduledEvent> findEvents(Users users, Date start, Date end) throws Exception {
	  	return dao.findEvents(users,start,end);
	}


	/**
	 * Create or update ScheduledEventUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ScheduledEventUsers
	 */
	public void create(ScheduledEventUsers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ScheduledEventUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScheduledEventUsers
	 */
	public void update(ScheduledEventUsers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ScheduledEventUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScheduledEventUsers
	 */
	public void delete(ScheduledEventUsers entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ScheduledEventUsers}
	 * @throws Exception the exception
	 * @see    ScheduledEventUsers
	 */
	public ScheduledEventUsers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ScheduledEventUsers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
	 * @throws Exception the exception
	 * @see    ScheduledEventUsers
	 */
	public List<ScheduledEventUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ScheduledEventUsers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ScheduledEventUsers}
	 * @throws Exception the exception
	 */
	public List<ScheduledEventUsers> allScheduledEventUsers(int first, int pageSize) throws Exception {
		return dao.allScheduledEventUsers(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ScheduledEventUsers for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ScheduledEventUsers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ScheduledEventUsers.class);
	}
	
    /**
     * Lazy load ScheduledEventUsers with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ScheduledEventUsers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ScheduledEventUsers} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ScheduledEventUsers> allScheduledEventUsers(Class<ScheduledEventUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ScheduledEventUsers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ScheduledEventUsers for lazy load with filters
     * @author TechFinium 
     * @param entity ScheduledEventUsers class
     * @param filters the filters
     * @return Number of rows in the ScheduledEventUsers entity
     * @throws Exception the exception     
     */	
	public int count(Class<ScheduledEventUsers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
