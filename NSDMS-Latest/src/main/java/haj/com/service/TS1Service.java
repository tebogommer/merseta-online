package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TS1DAO;
import haj.com.entity.datatakeon.TS1;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TS1Service.
 */
public class TS1Service extends AbstractService {
	/** The dao. */
	private TS1DAO dao = new TS1DAO();

	/**
	 * Get all TS1.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception the exception
	 * @see   TS1
	 */
	public List<TS1> allTS1() throws Exception {
	  	return dao.allTS1();
	}


	/**
	 * Create or update TS1.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TS1
	 */
	public void create(TS1 entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TS1.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TS1
	 */
	public void update(TS1 entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TS1.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TS1
	 */
	public void delete(TS1 entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception the exception
	 * @see    TS1
	 */
	public TS1 findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TS1 by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception the exception
	 * @see    TS1
	 */
	public List<TS1> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TS1.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.datatakeon.TS1}
	 * @throws Exception the exception
	 */
	public List<TS1> allTS1(int first, int pageSize) throws Exception {
		return dao.allTS1(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TS1 for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TS1
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TS1.class);
	}
	
    /**
     * Lazy load TS1 with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TS1 class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.datatakeon.TS1} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TS1> allTS1(Class<TS1> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TS1>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TS1 for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TS1 class
     * @param filters the filters
     * @return Number of rows in the TS1 entity
     * @throws Exception the exception
     */	
	public int count(Class<TS1> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
