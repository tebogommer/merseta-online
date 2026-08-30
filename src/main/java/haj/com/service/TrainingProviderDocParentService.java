package haj.com.service;

import java.util.List;

import haj.com.dao.TrainingProviderDocParentDAO;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class TrainingProviderDocParentService extends AbstractService {
	/** The dao. */
	private TrainingProviderDocParentDAO dao = new TrainingProviderDocParentDAO();

	/**
	 * Get all TrainingProviderDocParent
 	 * @author TechFinium 
 	 * @see   TrainingProviderDocParent
 	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
	 * @throws Exception the exception
 	 */
	public List<TrainingProviderDocParent> allTrainingProviderDocParent() throws Exception {
	  	return dao.allTrainingProviderDocParent();
	}


	/**
	 * Create or update TrainingProviderDocParent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingProviderDocParent
	 */
	public void create(TrainingProviderDocParent entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingProviderDocParent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderDocParent
	 */
	public void update(TrainingProviderDocParent entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingProviderDocParent.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderDocParent
	 */
	public void delete(TrainingProviderDocParent entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingProviderDocParent}
	 * @throws Exception the exception
	 * @see    TrainingProviderDocParent
	 */
	public TrainingProviderDocParent findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingProviderDocParent by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
	 * @throws Exception the exception
	 * @see    TrainingProviderDocParent
	 */
	public List<TrainingProviderDocParent> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingProviderDocParent
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderDocParent}
	 * @throws Exception the exception
	 */
	public List<TrainingProviderDocParent> allTrainingProviderDocParent(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderDocParent(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingProviderDocParent for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingProviderDocParent
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingProviderDocParent.class);
	}
	
    /**
     * Lazy load TrainingProviderDocParent with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingProviderDocParent class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingProviderDocParent} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderDocParent> allTrainingProviderDocParent(Class<TrainingProviderDocParent> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingProviderDocParent>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingProviderDocParent for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingProviderDocParent class
     * @param filters the filters
     * @return Number of rows in the TrainingProviderDocParent entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingProviderDocParent> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
