package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AqpDAO;
import haj.com.entity.lookup.Aqp;
import haj.com.framework.AbstractService;

public class AqpService extends AbstractService {
	/** The dao. */
	private AqpDAO dao = new AqpDAO();

	/**
	 * Get all Aqp
 	 * @author TechFinium 
 	 * @see   Aqp
 	 * @return a list of {@link haj.com.entity.Aqp}
	 * @throws Exception the exception
 	 */
	public List<Aqp> allAqp() throws Exception {
	  	return dao.allAqp();
	}


	/**
	 * Create or update Aqp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Aqp
	 */
	public void create(Aqp entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Aqp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Aqp
	 */
	public void update(Aqp entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Aqp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Aqp
	 */
	public void delete(Aqp entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Aqp}
	 * @throws Exception the exception
	 * @see    Aqp
	 */
	public Aqp findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Aqp by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Aqp}
	 * @throws Exception the exception
	 * @see    Aqp
	 */
	public List<Aqp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Aqp
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Aqp}
	 * @throws Exception the exception
	 */
	public List<Aqp> allAqp(int first, int pageSize) throws Exception {
		return dao.allAqp(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Aqp for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Aqp
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Aqp.class);
	}
	
    /**
     * Lazy load Aqp with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Aqp class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Aqp} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Aqp> allAqp(Class<Aqp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Aqp>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Aqp for lazy load with filters
     * @author TechFinium 
     * @param entity Aqp class
     * @param filters the filters
     * @return Number of rows in the Aqp entity
     * @throws Exception the exception     
     */	
	public int count(Class<Aqp> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
