package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InterventionTypeDescriptionDAO;
import haj.com.entity.lookup.InterventionTypeDescription;
import haj.com.framework.AbstractService;

public class InterventionTypeDescriptionService extends AbstractService {
	/** The dao. */
	private InterventionTypeDescriptionDAO dao = new InterventionTypeDescriptionDAO();

	/**
	 * Get all InterventionTypeDescription
 	 * @author TechFinium 
 	 * @see   InterventionTypeDescription
 	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
	 * @throws Exception the exception
 	 */
	public List<InterventionTypeDescription> allInterventionTypeDescription() throws Exception {
	  	return dao.allInterventionTypeDescription();
	}


	/**
	 * Create or update InterventionTypeDescription.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     InterventionTypeDescription
	 */
	public void create(InterventionTypeDescription entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  InterventionTypeDescription.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionTypeDescription
	 */
	public void update(InterventionTypeDescription entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  InterventionTypeDescription.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionTypeDescription
	 */
	public void delete(InterventionTypeDescription entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionTypeDescription}
	 * @throws Exception the exception
	 * @see    InterventionTypeDescription
	 */
	public InterventionTypeDescription findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find InterventionTypeDescription by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
	 * @throws Exception the exception
	 * @see    InterventionTypeDescription
	 */
	public List<InterventionTypeDescription> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load InterventionTypeDescription
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.InterventionTypeDescription}
	 * @throws Exception the exception
	 */
	public List<InterventionTypeDescription> allInterventionTypeDescription(int first, int pageSize) throws Exception {
		return dao.allInterventionTypeDescription(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of InterventionTypeDescription for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the InterventionTypeDescription
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(InterventionTypeDescription.class);
	}
	
    /**
     * Lazy load InterventionTypeDescription with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 InterventionTypeDescription class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.InterventionTypeDescription} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<InterventionTypeDescription> allInterventionTypeDescription(Class<InterventionTypeDescription> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<InterventionTypeDescription>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of InterventionTypeDescription for lazy load with filters
     * @author TechFinium 
     * @param entity InterventionTypeDescription class
     * @param filters the filters
     * @return Number of rows in the InterventionTypeDescription entity
     * @throws Exception the exception     
     */	
	public int count(Class<InterventionTypeDescription> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
