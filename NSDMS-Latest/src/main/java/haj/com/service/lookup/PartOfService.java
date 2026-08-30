package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.PartOfDAO;
import haj.com.entity.lookup.PartOf;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class PartOfService.
 */
public class PartOfService extends AbstractService {
	/** The dao. */
	private PartOfDAO dao = new PartOfDAO();

	/**
	 * Get all PartOf.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception the exception
	 * @see   PartOf
	 */
	public List<PartOf> allPartOf() throws Exception {
	  	return dao.allPartOf();
	}


	/**
	 * Create or update PartOf.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PartOf
	 */
	public void create(PartOf entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PartOf.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PartOf
	 */
	public void update(PartOf entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PartOf.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PartOf
	 */
	public void delete(PartOf entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PartOf}
	 * @throws Exception the exception
	 * @see    PartOf
	 */
	public PartOf findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PartOf by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception the exception
	 * @see    PartOf
	 */
	public List<PartOf> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PartOf.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception the exception
	 */
	public List<PartOf> allPartOf(int first, int pageSize) throws Exception {
		return dao.allPartOf(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PartOf for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the PartOf
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PartOf.class);
	}
	
    /**
     * Lazy load PartOf with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 PartOf class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PartOf} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PartOf> allPartOf(Class<PartOf> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PartOf>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PartOf for lazy load with filters.
     *
     * @author TechFinium
     * @param entity PartOf class
     * @param filters the filters
     * @return Number of rows in the PartOf entity
     * @throws Exception the exception
     */	
	public int count(Class<PartOf> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
