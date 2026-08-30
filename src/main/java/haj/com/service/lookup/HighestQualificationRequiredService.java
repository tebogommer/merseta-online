package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.HighestQualificationRequiredDAO;
import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.framework.AbstractService;

public class HighestQualificationRequiredService extends AbstractService {
	/** The dao. */
	private HighestQualificationRequiredDAO dao = new HighestQualificationRequiredDAO();

	/**
	 * Get all HighestQualificationRequired
 	 * @author TechFinium 
 	 * @see   HighestQualificationRequired
 	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
	 * @throws Exception the exception
 	 */
	public List<HighestQualificationRequired> allHighestQualificationRequired() throws Exception {
	  	return dao.allHighestQualificationRequired();
	}


	/**
	 * Create or update HighestQualificationRequired.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HighestQualificationRequired
	 */
	public void create(HighestQualificationRequired entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HighestQualificationRequired.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HighestQualificationRequired
	 */
	public void update(HighestQualificationRequired entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HighestQualificationRequired.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HighestQualificationRequired
	 */
	public void delete(HighestQualificationRequired entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HighestQualificationRequired}
	 * @throws Exception the exception
	 * @see    HighestQualificationRequired
	 */
	public HighestQualificationRequired findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HighestQualificationRequired by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
	 * @throws Exception the exception
	 * @see    HighestQualificationRequired
	 */
	public List<HighestQualificationRequired> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load HighestQualificationRequired
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HighestQualificationRequired}
	 * @throws Exception the exception
	 */
	public List<HighestQualificationRequired> allHighestQualificationRequired(int first, int pageSize) throws Exception {
		return dao.allHighestQualificationRequired(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of HighestQualificationRequired for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the HighestQualificationRequired
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HighestQualificationRequired.class);
	}
	
    /**
     * Lazy load HighestQualificationRequired with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 HighestQualificationRequired class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HighestQualificationRequired} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<HighestQualificationRequired> allHighestQualificationRequired(Class<HighestQualificationRequired> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HighestQualificationRequired>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of HighestQualificationRequired for lazy load with filters
     * @author TechFinium 
     * @param entity HighestQualificationRequired class
     * @param filters the filters
     * @return Number of rows in the HighestQualificationRequired entity
     * @throws Exception the exception     
     */	
	public int count(Class<HighestQualificationRequired> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
