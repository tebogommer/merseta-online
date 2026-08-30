package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.NonSetaCompanyUsersDAO;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.NonSetaCompanyUsers;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;

public class NonSetaCompanyUsersService extends AbstractService {
	/** The dao. */
	private NonSetaCompanyUsersDAO dao = new NonSetaCompanyUsersDAO();

	/**
	 * Get all NonSetaCompanyUsers
 	 * @author TechFinium 
 	 * @see   NonSetaCompanyUsers
 	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
	 * @throws Exception the exception
 	 */
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsers() throws Exception {
	  	return dao.allNonSetaCompanyUsers();
	}


	/**
	 * Create or update NonSetaCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NonSetaCompanyUsers
	 */
	public void create(NonSetaCompanyUsers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NonSetaCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonSetaCompanyUsers
	 */
	public void update(NonSetaCompanyUsers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NonSetaCompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NonSetaCompanyUsers
	 */
	public void delete(NonSetaCompanyUsers entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NonSetaCompanyUsers}
	 * @throws Exception the exception
	 * @see    NonSetaCompanyUsers
	 */
	public NonSetaCompanyUsers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NonSetaCompanyUsers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
	 * @throws Exception the exception
	 * @see    NonSetaCompanyUsers
	 */
	public List<NonSetaCompanyUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NonSetaCompanyUsers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NonSetaCompanyUsers}
	 * @throws Exception the exception
	 */
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsers(int first, int pageSize) throws Exception {
		return dao.allNonSetaCompanyUsers(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NonSetaCompanyUsers for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NonSetaCompanyUsers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NonSetaCompanyUsers.class);
	}
	
    /**
     * Lazy load NonSetaCompanyUsers with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NonSetaCompanyUsers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NonSetaCompanyUsers} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsers(Class<NonSetaCompanyUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NonSetaCompanyUsers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NonSetaCompanyUsers for lazy load with filters
     * @author TechFinium 
     * @param entity NonSetaCompanyUsers class
     * @param filters the filters
     * @return Number of rows in the NonSetaCompanyUsers entity
     * @throws Exception the exception     
     */	
	public int count(Class<NonSetaCompanyUsers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<NonSetaCompanyUsers> findTPContact(Long id, ConfigDocProcessEnum configDocProcessEnum) {
		return dao.findTPContact(id,configDocProcessEnum);
	}

	public List<NonSetaCompanyUsers> findTPAssessorMod(Long id, ConfigDocProcessEnum configDocProcessEnum) {
		return dao.findTPAssessorMod(id,configDocProcessEnum);
	}

	/* Jonos Code Start */
	public Integer countTPContact(NonSetaCompany nonSetaCompany, ConfigDocProcessEnum configDocProcessEnum) {
		return dao.countTPContact(nonSetaCompany.getId(), configDocProcessEnum);
	}
	
	public Integer countTPAssessorMod(NonSetaCompany nonSetaCompany, ConfigDocProcessEnum configDocProcessEnum) {
		return dao.countTPAssessorMod(nonSetaCompany.getId(), configDocProcessEnum);
	}
	
	public Long findByUserTypeCount(Users users) throws Exception {
		return dao.findByUserTypeCount(users.getId());
	}
	
	public Long findByUserTypeAndNonSetaCompanyCount(Users users, NonSetaCompany nonSetaCompany) throws Exception {
		return dao.findByUserTypeAndNonSetaCompanyCount(users.getId(), nonSetaCompany.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<NonSetaCompanyUsers> allNonSetaCompanyUsersByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, NonSetaCompany nonSetaCompany) throws Exception {
		String hql = "select o from NonSetaCompanyUsers o where o.nonSetaCompany.id = :nonSetaCompanyId";
		filters.put("nonSetaCompanyId", nonSetaCompany.getId());
		return (List<NonSetaCompanyUsers>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllNonSetaCompanyUsersByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from NonSetaCompanyUsers o where o.nonSetaCompany.id = :nonSetaCompanyId";
		return dao.countWhere(filters, hql);
	}
	/* Jonos Code End */
}