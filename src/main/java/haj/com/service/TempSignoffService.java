package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TempSignoffDAO;
import haj.com.entity.TempSignoff;
import haj.com.framework.AbstractService;

public class TempSignoffService extends AbstractService {
	
	/** The dao. */
	private TempSignoffDAO dao = new TempSignoffDAO();
	
	private static TempSignoffService tempSignoffService;
	public static synchronized TempSignoffService instance() {
		if (tempSignoffService == null) {
			tempSignoffService = new TempSignoffService();
		}
		return tempSignoffService;
	}

	/**
	 * Get all TempSignoff
 	 * @author TechFinium 
 	 * @see   TempSignoff
 	 * @return a list of {@link haj.com.entity.TempSignoff}
	 * @throws Exception the exception
 	 */
	public List<TempSignoff> allTempSignoff() throws Exception {
	  	return dao.allTempSignoff();
	}


	/**
	 * Create or update TempSignoff.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TempSignoff
	 */
	public void create(TempSignoff entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TempSignoff.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TempSignoff
	 */
	public void update(TempSignoff entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TempSignoff.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TempSignoff
	 */
	public void delete(TempSignoff entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TempSignoff}
	 * @throws Exception the exception
	 * @see    TempSignoff
	 */
	public TempSignoff findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TempSignoff by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TempSignoff}
	 * @throws Exception the exception
	 * @see    TempSignoff
	 */
	public List<TempSignoff> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TempSignoff
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TempSignoff}
	 * @throws Exception the exception
	 */
	public List<TempSignoff> allTempSignoff(int first, int pageSize) throws Exception {
		return dao.allTempSignoff(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TempSignoff for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TempSignoff
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TempSignoff.class);
	}
	
    /**
     * Lazy load TempSignoff with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TempSignoff class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TempSignoff} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TempSignoff> allTempSignoff(Class<TempSignoff> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TempSignoff>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TempSignoff for lazy load with filters
     * @author TechFinium 
     * @param entity TempSignoff class
     * @param filters the filters
     * @return Number of rows in the TempSignoff entity
     * @throws Exception the exception     
     */	
	public int count(Class<TempSignoff> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
