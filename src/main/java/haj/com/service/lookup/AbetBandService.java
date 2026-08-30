package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AbetBandDAO;
import haj.com.entity.lookup.AbetBand;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbetBandService.
 */
public class AbetBandService extends AbstractService {
	/** The dao. */
	private AbetBandDAO dao = new AbetBandDAO();

	/**
	 * Get all AbetBand.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception the exception
	 * @see   AbetBand
	 */
	public List<AbetBand> allAbetBand() throws Exception {
	  	return dao.allAbetBand();
	}


	/**
	 * Create or update AbetBand.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AbetBand
	 */
	public void create(AbetBand entity) throws Exception  {
	if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AbetBand.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AbetBand
	 */
	public void update(AbetBand entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AbetBand.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AbetBand
	 */
	public void delete(AbetBand entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AbetBand}
	 * @throws Exception the exception
	 * @see    AbetBand
	 */
	public AbetBand findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AbetBand by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception the exception
	 * @see    AbetBand
	 */
	public List<AbetBand> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AbetBand.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AbetBand}
	 * @throws Exception the exception
	 */
	public List<AbetBand> allAbetBand(int first, int pageSize) throws Exception {
		return dao.allAbetBand(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AbetBand for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the AbetBand
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AbetBand.class);
	}
	
    /**
     * Lazy load AbetBand with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 AbetBand class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AbetBand} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AbetBand> allAbetBand(Class<AbetBand> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AbetBand>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AbetBand for lazy load with filters.
     *
     * @author TechFinium
     * @param entity AbetBand class
     * @param filters the filters
     * @return Number of rows in the AbetBand entity
     * @throws Exception the exception
     */	
	public int count(Class<AbetBand> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
