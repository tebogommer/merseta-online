package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RatioDAO;
import haj.com.entity.lookup.Ratio;
import haj.com.framework.AbstractService;

public class RatioService extends AbstractService {
	/** The dao. */
	private RatioDAO dao = new RatioDAO();

	/**
	 * Get all Ratio
 	 * @author TechFinium 
 	 * @see   Ratio
 	 * @return a list of {@link haj.com.entity.Ratio}
	 * @throws Exception the exception
 	 */
	public List<Ratio> allRatio() throws Exception {
	  	return dao.allRatio();
	}


	/**
	 * Create or update Ratio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Ratio
	 */
	public void create(Ratio entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Ratio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Ratio
	 */
	public void update(Ratio entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Ratio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Ratio
	 */
	public void delete(Ratio entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Ratio}
	 * @throws Exception the exception
	 * @see    Ratio
	 */
	public Ratio findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Ratio by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Ratio}
	 * @throws Exception the exception
	 * @see    Ratio
	 */
	public List<Ratio> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Ratio
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Ratio}
	 * @throws Exception the exception
	 */
	public List<Ratio> allRatio(int first, int pageSize) throws Exception {
		return dao.allRatio(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Ratio for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Ratio
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Ratio.class);
	}
	
    /**
     * Lazy load Ratio with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Ratio class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Ratio} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Ratio> allRatio(Class<Ratio> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Ratio>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Ratio for lazy load with filters
     * @author TechFinium 
     * @param entity Ratio class
     * @param filters the filters
     * @return Number of rows in the Ratio entity
     * @throws Exception the exception     
     */	
	public int count(Class<Ratio> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
