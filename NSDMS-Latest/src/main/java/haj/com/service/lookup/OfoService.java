package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.OfoDAO;
import haj.com.entity.lookup.Ofo;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoService.
 */
public class OfoService extends AbstractService {
	/** The dao. */
	private OfoDAO dao = new OfoDAO();

	/**
	 * Get all Ofo.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception the exception
	 * @see   Ofo
	 */
	public List<Ofo> allOfo() throws Exception {
	  	return dao.allOfo();
	}


	/**
	 * Create or update Ofo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Ofo
	 */
	public void create(Ofo entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Ofo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Ofo
	 */
	public void update(Ofo entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Ofo.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Ofo
	 */
	public void delete(Ofo entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Ofo}
	 * @throws Exception the exception
	 * @see    Ofo
	 */
	public Ofo findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Ofo by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception the exception
	 * @see    Ofo
	 */
	public List<Ofo> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Ofo.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception the exception
	 */
	public List<Ofo> allOfo(int first, int pageSize) throws Exception {
		return dao.allOfo(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Ofo for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Ofo
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Ofo.class);
	}
	
    /**
     * Lazy load Ofo with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Ofo class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Ofo} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Ofo> allOfo(Class<Ofo> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Ofo>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Ofo for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Ofo class
     * @param filters the filters
     * @return Number of rows in the Ofo entity
     * @throws Exception the exception
     */	
	public int count(Class<Ofo> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
