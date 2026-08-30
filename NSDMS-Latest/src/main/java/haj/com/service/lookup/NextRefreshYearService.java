package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NextRefreshYearDAO;
import haj.com.entity.lookup.NextRefreshYear;
import haj.com.framework.AbstractService;

public class NextRefreshYearService extends AbstractService {
	/** The dao. */
	private NextRefreshYearDAO dao = new NextRefreshYearDAO();

	/**
	 * Get all NextRefreshYear
 	 * @author TechFinium 
 	 * @see   NextRefreshYear
 	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception the exception
 	 */
	public List<NextRefreshYear> allNextRefreshYear() throws Exception {
	  	return dao.allNextRefreshYear();
	}


	/**
	 * Create or update NextRefreshYear.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NextRefreshYear
	 */
	public void create(NextRefreshYear entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NextRefreshYear.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NextRefreshYear
	 */
	public void update(NextRefreshYear entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NextRefreshYear.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NextRefreshYear
	 */
	public void delete(NextRefreshYear entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception the exception
	 * @see    NextRefreshYear
	 */
	public NextRefreshYear findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NextRefreshYear by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception the exception
	 * @see    NextRefreshYear
	 */
	public List<NextRefreshYear> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NextRefreshYear
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NextRefreshYear}
	 * @throws Exception the exception
	 */
	public List<NextRefreshYear> allNextRefreshYear(int first, int pageSize) throws Exception {
		return dao.allNextRefreshYear(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NextRefreshYear for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NextRefreshYear
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NextRefreshYear.class);
	}
	
    /**
     * Lazy load NextRefreshYear with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NextRefreshYear class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NextRefreshYear} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NextRefreshYear> allNextRefreshYear(Class<NextRefreshYear> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NextRefreshYear>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of NextRefreshYear for lazy load with filters
     * @author TechFinium 
     * @param entity NextRefreshYear class
     * @param filters the filters
     * @return Number of rows in the NextRefreshYear entity
     * @throws Exception the exception     
     */	
	public int count(Class<NextRefreshYear> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<NextRefreshYear> findLast() throws Exception {
		return dao.findLast();
	}
}
