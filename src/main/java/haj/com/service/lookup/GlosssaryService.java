package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.GlosssaryDAO;
import haj.com.entity.lookup.Glosssary;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class GlosssaryService.
 */
public class GlosssaryService extends AbstractService {
	/** The dao. */
	private GlosssaryDAO dao = new GlosssaryDAO();

	/**
	 * Get all Glosssary.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception the exception
	 * @see   Glosssary
	 */
	public List<Glosssary> allGlosssary() throws Exception {
	  	return dao.allGlosssary();
	}


	/**
	 * Create or update Glosssary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Glosssary
	 */
	public void create(Glosssary entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Glosssary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Glosssary
	 */
	public void update(Glosssary entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Glosssary.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Glosssary
	 */
	public void delete(Glosssary entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Glosssary}
	 * @throws Exception the exception
	 * @see    Glosssary
	 */
	public Glosssary findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Glosssary by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception the exception
	 * @see    Glosssary
	 */
	public List<Glosssary> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Glosssary.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Glosssary}
	 * @throws Exception the exception
	 */
	public List<Glosssary> allGlosssary(int first, int pageSize) throws Exception {
		return dao.allGlosssary(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Glosssary for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Glosssary
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Glosssary.class);
	}
	
    /**
     * Lazy load Glosssary with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Glosssary class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Glosssary} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Glosssary> allGlosssary(Class<Glosssary> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Glosssary>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Glosssary for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Glosssary class
     * @param filters the filters
     * @return Number of rows in the Glosssary entity
     * @throws Exception the exception
     */	
	public int count(Class<Glosssary> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
