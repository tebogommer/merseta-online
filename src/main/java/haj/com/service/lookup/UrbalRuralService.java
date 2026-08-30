package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.UrbalRuralDAO;
import haj.com.entity.lookup.UrbalRural;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UrbalRuralService.
 */
public class UrbalRuralService extends AbstractService {
	/** The dao. */
	private UrbalRuralDAO dao = new UrbalRuralDAO();

	/**
	 * Get all UrbalRural.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception the exception
	 * @see   UrbalRural
	 */
	public List<UrbalRural> allUrbalRural() throws Exception {
	  	return dao.allUrbalRural();
	}


	/**
	 * Create or update UrbalRural.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UrbalRural
	 */
	public void create(UrbalRural entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UrbalRural.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UrbalRural
	 */
	public void update(UrbalRural entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UrbalRural.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UrbalRural
	 */
	public void delete(UrbalRural entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UrbalRural}
	 * @throws Exception the exception
	 * @see    UrbalRural
	 */
	public UrbalRural findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UrbalRural by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception the exception
	 * @see    UrbalRural
	 */
	public List<UrbalRural> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UrbalRural.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception the exception
	 */
	public List<UrbalRural> allUrbalRural(int first, int pageSize) throws Exception {
		return dao.allUrbalRural(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UrbalRural for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UrbalRural
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UrbalRural.class);
	}
	
    /**
     * Lazy load UrbalRural with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UrbalRural class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UrbalRural} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UrbalRural> allUrbalRural(Class<UrbalRural> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UrbalRural>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UrbalRural for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UrbalRural class
     * @param filters the filters
     * @return Number of rows in the UrbalRural entity
     * @throws Exception the exception
     */	
	public int count(Class<UrbalRural> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
