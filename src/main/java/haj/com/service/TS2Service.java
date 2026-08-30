package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.ReconSchemeYears;
import haj.com.dao.TS2DAO;
import haj.com.entity.Tempx;
import haj.com.entity.datatakeon.TS1;
import haj.com.entity.datatakeon.TS2;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TS2Service.
 */
public class TS2Service extends AbstractService {
	/** The dao. */
	private TS2DAO dao = new TS2DAO();

	/**
	 * Get all TS2.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception the exception
	 * @see   TS2
	 */
	public List<TS2> allTS2() throws Exception {
	  	return dao.allTS2();
	}


	/**
	 * Create or update TS2.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TS2
	 */
	public void create(TS2 entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TS2.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TS2
	 */
	public void update(TS2 entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TS2.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TS2
	 */
	public void delete(TS2 entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception the exception
	 * @see    TS2
	 */
	public TS2 findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TS2 by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception the exception
	 * @see    TS2
	 */
	public List<TS2> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TS2.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.datatakeon.TS2}
	 * @throws Exception the exception
	 */
	public List<TS2> allTS2(int first, int pageSize) throws Exception {
		return dao.allTS2(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TS2 for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TS2
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TS2.class);
	}
	
    /**
     * Lazy load TS2 with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TS2 class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.datatakeon.TS2} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TS2> allTS2(Class<TS2> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TS2>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TS2 for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TS2 class
     * @param filters the filters
     * @return Number of rows in the TS2 entity
     * @throws Exception the exception
     */	
	public int count(Class<TS2> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by TS 1.
	 *
	 * @param ts1 the ts 1
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<TS2> findByTS1(TS1 ts1) throws Exception { 
		return dao.findByTS1(ts1.getId());
	}
	
	/**
	 * All tempx.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Tempx> allTempx() throws Exception { 
		return dao.allTempx();
	}


	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public TS2DAO getDao() {
		return dao;
	}
	
	public List<ReconSchemeYears> listSchemeYears() throws Exception { 
		return dao.listSchemeYears();
	}
}
