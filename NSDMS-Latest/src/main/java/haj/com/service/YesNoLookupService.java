package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.YesNoLookupDAO;
import haj.com.entity.YesNoLookup;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class YesNoLookupService.
 */
public class YesNoLookupService extends AbstractService {
	/** The dao. */
	private YesNoLookupDAO dao = new YesNoLookupDAO();
	private static YesNoLookupService yesNoLookupService;
	
	public static synchronized YesNoLookupService instance() {
		if (yesNoLookupService == null) {
			yesNoLookupService = new YesNoLookupService();
		}
		return yesNoLookupService;
	}

	/**
	 * Get all YesNoLookup.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception the exception
	 * @see   YesNoLookup
	 */
	public List<YesNoLookup> allYesNoLookup() throws Exception {
	  	return dao.allYesNoLookup();
	}


	/**
	 * Create or update YesNoLookup.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     YesNoLookup
	 */
	public void create(YesNoLookup entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  YesNoLookup.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    YesNoLookup
	 */
	public void update(YesNoLookup entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  YesNoLookup.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    YesNoLookup
	 */
	public void delete(YesNoLookup entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.YesNoLookup}
	 * @throws Exception the exception
	 * @see    YesNoLookup
	 */
	public YesNoLookup findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find YesNoLookup by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception the exception
	 * @see    YesNoLookup
	 */
	public List<YesNoLookup> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load YesNoLookup.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.YesNoLookup}
	 * @throws Exception the exception
	 */
	public List<YesNoLookup> allYesNoLookup(int first, int pageSize) throws Exception {
		return dao.allYesNoLookup(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of YesNoLookup for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the YesNoLookup
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(YesNoLookup.class);
	}
	
    /**
     * Lazy load YesNoLookup with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 YesNoLookup class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.YesNoLookup} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<YesNoLookup> allYesNoLookup(Class<YesNoLookup> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<YesNoLookup>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of YesNoLookup for lazy load with filters.
     *
     * @author TechFinium
     * @param entity YesNoLookup class
     * @param filters the filters
     * @return Number of rows in the YesNoLookup entity
     * @throws Exception the exception
     */	
	public int count(Class<YesNoLookup> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
