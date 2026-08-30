package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersTypeDAO;
import haj.com.entity.UsersType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTypeService.
 */
public class UsersTypeService extends AbstractService {
	/** The dao. */
	private UsersTypeDAO dao = new UsersTypeDAO();

	/**
	 * Get all UsersType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception the exception
	 * @see   UsersType
	 */
	public List<UsersType> allUsersType() throws Exception {
	  	return dao.allUsersType();
	}


	/**
	 * Create or update UsersType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersType
	 */
	public void create(UsersType entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersType
	 */
	public void update(UsersType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersType
	 */
	public void delete(UsersType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersType}
	 * @throws Exception the exception
	 * @see    UsersType
	 */
	public UsersType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception the exception
	 * @see    UsersType
	 */
	public List<UsersType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersType}
	 * @throws Exception the exception
	 */
	public List<UsersType> allUsersType(int first, int pageSize) throws Exception {
		return dao.allUsersType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UsersType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersType.class);
	}
	
    /**
     * Lazy load UsersType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UsersType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersType> allUsersType(Class<UsersType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UsersType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UsersType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UsersType class
     * @param filters the filters
     * @return Number of rows in the UsersType entity
     * @throws Exception the exception
     */	
	public int count(Class<UsersType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
