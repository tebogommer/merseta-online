package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ScopeOfResponsibilityDAO;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.framework.AbstractService;

public class ScopeOfResponsibilityService extends AbstractService {
	/** The dao. */
	private ScopeOfResponsibilityDAO dao = new ScopeOfResponsibilityDAO();

	private static ScopeOfResponsibilityService scopeOfResponsibilityService;
	public static synchronized ScopeOfResponsibilityService instance() {
		if (scopeOfResponsibilityService == null) {
			scopeOfResponsibilityService = new ScopeOfResponsibilityService();
		}
		return scopeOfResponsibilityService;
	}
	
	/**
	 * Get all ScopeOfResponsibility
 	 * @author TechFinium 
 	 * @see   ScopeOfResponsibility
 	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
	 * @throws Exception the exception
 	 */
	public List<ScopeOfResponsibility> allScopeOfResponsibility() throws Exception {
	  	return dao.allScopeOfResponsibility();
	}


	/**
	 * Create or update ScopeOfResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ScopeOfResponsibility
	 */
	public void create(ScopeOfResponsibility entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ScopeOfResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScopeOfResponsibility
	 */
	public void update(ScopeOfResponsibility entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ScopeOfResponsibility.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ScopeOfResponsibility
	 */
	public void delete(ScopeOfResponsibility entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ScopeOfResponsibility}
	 * @throws Exception the exception
	 * @see    ScopeOfResponsibility
	 */
	public ScopeOfResponsibility findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ScopeOfResponsibility by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
	 * @throws Exception the exception
	 * @see    ScopeOfResponsibility
	 */
	public List<ScopeOfResponsibility> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ScopeOfResponsibility
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ScopeOfResponsibility}
	 * @throws Exception the exception
	 */
	public List<ScopeOfResponsibility> allScopeOfResponsibility(int first, int pageSize) throws Exception {
		return dao.allScopeOfResponsibility(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ScopeOfResponsibility for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ScopeOfResponsibility
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ScopeOfResponsibility.class);
	}
	
    /**
     * Lazy load ScopeOfResponsibility with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ScopeOfResponsibility class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ScopeOfResponsibility} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ScopeOfResponsibility> allScopeOfResponsibility(Class<ScopeOfResponsibility> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ScopeOfResponsibility>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ScopeOfResponsibility for lazy load with filters
     * @author TechFinium 
     * @param entity ScopeOfResponsibility class
     * @param filters the filters
     * @return Number of rows in the ScopeOfResponsibility entity
     * @throws Exception the exception     
     */	
	public int count(Class<ScopeOfResponsibility> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
