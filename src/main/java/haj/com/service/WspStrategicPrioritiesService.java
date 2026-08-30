package haj.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspStrategicPrioritiesDAO;
import haj.com.entity.WspStrategicPriorities;
import haj.com.framework.AbstractService;

public class WspStrategicPrioritiesService extends AbstractService {
	/** The dao. */
	private WspStrategicPrioritiesDAO dao = new WspStrategicPrioritiesDAO();

	/**
	 * Get all WspStrategicPriorities
 	 * @author TechFinium 
 	 * @see   WspStrategicPriorities
 	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
	 * @throws Exception the exception
 	 */
	public List<WspStrategicPriorities> allWspStrategicPriorities() throws Exception {
	  	return dao.allWspStrategicPriorities();
	}


	/**
	 * Create or update WspStrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspStrategicPriorities
	 */
	public void create(WspStrategicPriorities entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspStrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspStrategicPriorities
	 */
	public void update(WspStrategicPriorities entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspStrategicPriorities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspStrategicPriorities
	 */
	public void delete(WspStrategicPriorities entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspStrategicPriorities}
	 * @throws Exception the exception
	 * @see    WspStrategicPriorities
	 */
	public WspStrategicPriorities findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspStrategicPriorities by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
	 * @throws Exception the exception
	 * @see    WspStrategicPriorities
	 */
	public List<WspStrategicPriorities> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspStrategicPriorities
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
	 * @throws Exception the exception
	 */
	public List<WspStrategicPriorities> allWspStrategicPriorities(int first, int pageSize) throws Exception {
		return dao.allWspStrategicPriorities(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspStrategicPriorities for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspStrategicPriorities
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspStrategicPriorities.class);
	}
	
    /**
     * Lazy load WspStrategicPriorities with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspStrategicPriorities class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspStrategicPriorities} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspStrategicPriorities> allWspStrategicPriorities(Class<WspStrategicPriorities> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspStrategicPriorities>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspStrategicPriorities for lazy load with filters
     * @author TechFinium 
     * @param entity WspStrategicPriorities class
     * @param filters the filters
     * @return Number of rows in the WspStrategicPriorities entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspStrategicPriorities> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspStrategicPriorities> allWspStrategicPriorities(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WspStrategicPriorities o where o.wsp.id = :wspID";
		return (List<WspStrategicPriorities>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspStrategicPriorities o where o.wsp.id = :wspID";
		return dao.countWhere(filters, hql);
	}
}