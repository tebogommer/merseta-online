package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.PrioritisationScaleDAO;
import haj.com.entity.lookup.PrioritisationScale;
import haj.com.framework.AbstractService;

public class PrioritisationScaleService extends AbstractService {
	/** The dao. */
	private PrioritisationScaleDAO dao = new PrioritisationScaleDAO();

	/**
	 * Get all PrioritisationScale
 	 * @author TechFinium 
 	 * @see   PrioritisationScale
 	 * @return a list of {@link haj.com.entity.PrioritisationScale}
	 * @throws Exception the exception
 	 */
	public List<PrioritisationScale> allPrioritisationScale() throws Exception {
	  	return dao.allPrioritisationScale();
	}


	/**
	 * Create or update PrioritisationScale.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PrioritisationScale
	 */
	public void create(PrioritisationScale entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PrioritisationScale.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PrioritisationScale
	 */
	public void update(PrioritisationScale entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PrioritisationScale.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PrioritisationScale
	 */
	public void delete(PrioritisationScale entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PrioritisationScale}
	 * @throws Exception the exception
	 * @see    PrioritisationScale
	 */
	public PrioritisationScale findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PrioritisationScale by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PrioritisationScale}
	 * @throws Exception the exception
	 * @see    PrioritisationScale
	 */
	public List<PrioritisationScale> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PrioritisationScale
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PrioritisationScale}
	 * @throws Exception the exception
	 */
	public List<PrioritisationScale> allPrioritisationScale(int first, int pageSize) throws Exception {
		return dao.allPrioritisationScale(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PrioritisationScale for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the PrioritisationScale
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PrioritisationScale.class);
	}
	
    /**
     * Lazy load PrioritisationScale with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 PrioritisationScale class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PrioritisationScale} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PrioritisationScale> allPrioritisationScale(Class<PrioritisationScale> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PrioritisationScale>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PrioritisationScale for lazy load with filters
     * @author TechFinium 
     * @param entity PrioritisationScale class
     * @param filters the filters
     * @return Number of rows in the PrioritisationScale entity
     * @throws Exception the exception     
     */	
	public int count(Class<PrioritisationScale> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
