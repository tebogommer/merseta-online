package haj.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspLocationsDAO;
import haj.com.entity.WspLocations;
import haj.com.framework.AbstractService;

public class WspLocationsService extends AbstractService {
	/** The dao. */
	private WspLocationsDAO dao = new WspLocationsDAO();

	/**
	 * Get all WspLocations
 	 * @author TechFinium 
 	 * @see   WspLocations
 	 * @return a list of {@link haj.com.entity.WspLocations}
	 * @throws Exception the exception
 	 */
	public List<WspLocations> allWspLocations() throws Exception {
	  	return dao.allWspLocations();
	}


	/**
	 * Create or update WspLocations.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspLocations
	 */
	public void create(WspLocations entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspLocations.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspLocations
	 */
	public void update(WspLocations entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspLocations.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspLocations
	 */
	public void delete(WspLocations entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspLocations}
	 * @throws Exception the exception
	 * @see    WspLocations
	 */
	public WspLocations findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspLocations by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspLocations}
	 * @throws Exception the exception
	 * @see    WspLocations
	 */
	public List<WspLocations> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspLocations
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspLocations}
	 * @throws Exception the exception
	 */
	public List<WspLocations> allWspLocations(int first, int pageSize) throws Exception {
		return dao.allWspLocations(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspLocations for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspLocations
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspLocations.class);
	}
	
    /**
     * Lazy load WspLocations with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspLocations class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspLocations} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspLocations> allWspLocations(Class<WspLocations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspLocations>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspLocations for lazy load with filters
     * @author TechFinium 
     * @param entity WspLocations class
     * @param filters the filters
     * @return Number of rows in the WspLocations entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspLocations> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	@SuppressWarnings("unchecked")
	public List<WspLocations> allWspLocations(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WspLocations o where o.wsp.id = :wspID";
		if (filters == null) filters = new HashMap<String, Object>();
		return (List<WspLocations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspLocations o where o.wsp.id = :wspID";
		if (filters == null) filters = new HashMap<String, Object>();
		return dao.countWhere(filters, hql);
	}
}
