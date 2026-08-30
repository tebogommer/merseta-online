package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.UsersResponsibilitiesHistoryDAO;
import haj.com.entity.CompanyUsers;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.UsersResponsibilitiesHistory;
import haj.com.framework.AbstractService;

public class UsersResponsibilitiesHistoryService extends AbstractService {
	/** The dao. */
	private UsersResponsibilitiesHistoryDAO dao = new UsersResponsibilitiesHistoryDAO();
	private static UsersResponsibilitiesHistoryService responsibilitiesHistoryService = null;
	
	public static synchronized UsersResponsibilitiesHistoryService instance() {
		if (responsibilitiesHistoryService == null) {
			responsibilitiesHistoryService = new UsersResponsibilitiesHistoryService();
		}
		return responsibilitiesHistoryService;

	}
	/**
	 * Get all UsersResponsibilitiesHistory
 	 * @author TechFinium 
 	 * @see   UsersResponsibilitiesHistory
 	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception the exception
 	 */
	public List<UsersResponsibilitiesHistory> allUsersResponsibilitiesHistory() throws Exception {
	  	return dao.allUsersResponsibilitiesHistory();
	}


	/**
	 * Create or update UsersResponsibilitiesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersResponsibilitiesHistory
	 */
	public void create(UsersResponsibilitiesHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersResponsibilitiesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersResponsibilitiesHistory
	 */
	public void update(UsersResponsibilitiesHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersResponsibilitiesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersResponsibilitiesHistory
	 */
	public void delete(UsersResponsibilitiesHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception the exception
	 * @see    UsersResponsibilitiesHistory
	 */
	public UsersResponsibilitiesHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersResponsibilitiesHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception the exception
	 * @see    UsersResponsibilitiesHistory
	 */
	public List<UsersResponsibilitiesHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersResponsibilitiesHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersResponsibilitiesHistory}
	 * @throws Exception the exception
	 */
	public List<UsersResponsibilitiesHistory> allUsersResponsibilitiesHistory(int first, int pageSize) throws Exception {
		return dao.allUsersResponsibilitiesHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersResponsibilitiesHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UsersResponsibilitiesHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersResponsibilitiesHistory.class);
	}
	
    /**
     * Lazy load UsersResponsibilitiesHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UsersResponsibilitiesHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersResponsibilitiesHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilitiesHistory> allUsersResponsibilitiesHistory(Class<UsersResponsibilities> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UsersResponsibilitiesHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UsersResponsibilitiesHistory for lazy load with filters
     * @author TechFinium 
     * @param entity UsersResponsibilitiesHistory class
     * @param filters the filters
     * @return Number of rows in the UsersResponsibilitiesHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<UsersResponsibilitiesHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<UsersResponsibilitiesHistory> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
		return dao.findByCompanyUser(companyUsers);
	}
	
	public List<UsersResponsibilitiesHistory> findByCompanyUserID(Long id) throws Exception {
		return dao.findByCompanyUserID(id);
	}
	
	public void createHistory(List<UsersResponsibilities> usersResponsibilitiesList) throws Exception
	{
		for(UsersResponsibilities usersResponsibilities: usersResponsibilitiesList)
		{
			UsersResponsibilitiesHistory usersResponsibilitiesHistory=new UsersResponsibilitiesHistory();
			BeanUtils.copyProperties(usersResponsibilitiesHistory, usersResponsibilities);
			usersResponsibilitiesHistory.setId(null);
			create(usersResponsibilitiesHistory);
		}
	}
}
