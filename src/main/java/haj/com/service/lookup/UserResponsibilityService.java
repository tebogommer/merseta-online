package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.UserResponsibilityDAO;
import haj.com.entity.CompanyUsers;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.AbstractService;

public class UserResponsibilityService extends AbstractService {
	/** The dao. */
	private UserResponsibilityDAO dao = new UserResponsibilityDAO();

	/**
	 * Get all UserResponsibility
 	 * @author TechFinium 
 	 * @see   UserResponsibility
 	 * @return a list of {@link haj.com.entity.UserResponsibility}
	 * @throws Exception the exception
 	 */
	public List<UserResponsibility> allUserResponsibility() throws Exception {
	  	return dao.allUserResponsibility();
	}


	/**
	 * Create or update UserResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserResponsibility
	 */
	public void create(UserResponsibility entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserResponsibility
	 */
	public void update(UserResponsibility entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserResponsibility
	 */
	public void delete(UserResponsibility entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserResponsibility}
	 * @throws Exception the exception
	 * @see    UserResponsibility
	 */
	public UserResponsibility findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserResponsibility by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserResponsibility}
	 * @throws Exception the exception
	 * @see    UserResponsibility
	 */
	public List<UserResponsibility> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserResponsibility
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserResponsibility}
	 * @throws Exception the exception
	 */
	public List<UserResponsibility> allUserResponsibility(int first, int pageSize) throws Exception {
		return dao.allUserResponsibility(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserResponsibility for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UserResponsibility
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserResponsibility.class);
	}
	
    /**
     * Lazy load UserResponsibility with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UserResponsibility class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserResponsibility} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> allUserResponsibility(Class<UserResponsibility> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserResponsibility>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
	/**
	 * Find UserResponsibility by companyUsers
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    UserResponsibility
  	 * @return a list of {@link haj.com.entity.UserResponsibility}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UserResponsibility> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
		return dao.findByCompanyUser(companyUsers);
	}
	
    /**
     * Get count of UserResponsibility for lazy load with filters
     * @author TechFinium 
     * @param entity UserResponsibility class
     * @param filters the filters
     * @return Number of rows in the UserResponsibility entity
     * @throws Exception the exception     
     */	
	public int count(Class<UserResponsibility> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
