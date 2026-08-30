package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TrainingDeliveryMethodDAO;
import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.framework.AbstractService;

public class TrainingDeliveryMethodService extends AbstractService {
	/** The dao. */
	private TrainingDeliveryMethodDAO dao = new TrainingDeliveryMethodDAO();

	/**
	 * Get all TrainingDeliveryMethod
 	 * @author TechFinium 
 	 * @see   TrainingDeliveryMethod
 	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
	 * @throws Exception the exception
 	 */
	public List<TrainingDeliveryMethod> allTrainingDeliveryMethod() throws Exception {
	  	return dao.allTrainingDeliveryMethod();
	}


	/**
	 * Create or update TrainingDeliveryMethod.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingDeliveryMethod
	 */
	public void create(TrainingDeliveryMethod entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingDeliveryMethod.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingDeliveryMethod
	 */
	public void update(TrainingDeliveryMethod entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingDeliveryMethod.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingDeliveryMethod
	 */
	public void delete(TrainingDeliveryMethod entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingDeliveryMethod}
	 * @throws Exception the exception
	 * @see    TrainingDeliveryMethod
	 */
	public TrainingDeliveryMethod findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingDeliveryMethod by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
	 * @throws Exception the exception
	 * @see    TrainingDeliveryMethod
	 */
	public List<TrainingDeliveryMethod> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingDeliveryMethod
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingDeliveryMethod}
	 * @throws Exception the exception
	 */
	public List<TrainingDeliveryMethod> allTrainingDeliveryMethod(int first, int pageSize) throws Exception {
		return dao.allTrainingDeliveryMethod(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingDeliveryMethod for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingDeliveryMethod
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingDeliveryMethod.class);
	}
	
    /**
     * Lazy load TrainingDeliveryMethod with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingDeliveryMethod class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingDeliveryMethod} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingDeliveryMethod> allTrainingDeliveryMethod(Class<TrainingDeliveryMethod> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingDeliveryMethod>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingDeliveryMethod for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingDeliveryMethod class
     * @param filters the filters
     * @return Number of rows in the TrainingDeliveryMethod entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingDeliveryMethod> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
