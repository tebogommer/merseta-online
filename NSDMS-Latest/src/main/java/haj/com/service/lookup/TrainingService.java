package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TrainingDAO;
import haj.com.entity.enums.PlannedImplementedEnum;
import haj.com.entity.lookup.Training;
import haj.com.framework.AbstractService;

public class TrainingService extends AbstractService {
	/** The dao. */
	private TrainingDAO dao = new TrainingDAO();

	/**
	 * Get all Training
 	 * @author TechFinium 
 	 * @see   Training
 	 * @return a list of {@link haj.com.entity.Training}
	 * @throws Exception the exception
 	 */
	public List<Training> allTraining() throws Exception {
	  	return dao.allTraining();
	}
	
	public List<Training> allTraining(PlannedImplementedEnum plannedImplemented) throws Exception {
		return dao.allTraining(plannedImplemented);
	}


	/**
	 * Create or update Training.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Training
	 */
	public void create(Training entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Training.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Training
	 */
	public void update(Training entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Training.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Training
	 */
	public void delete(Training entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Training}
	 * @throws Exception the exception
	 * @see    Training
	 */
	public Training findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Training by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Training}
	 * @throws Exception the exception
	 * @see    Training
	 */
	public List<Training> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Training
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Training}
	 * @throws Exception the exception
	 */
	public List<Training> allTraining(int first, int pageSize) throws Exception {
		return dao.allTraining(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Training for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Training
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Training.class);
	}
	
    /**
     * Lazy load Training with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Training class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Training} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Training> allTraining(Class<Training> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Training>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Training for lazy load with filters
     * @author TechFinium 
     * @param entity Training class
     * @param filters the filters
     * @return Number of rows in the Training entity
     * @throws Exception the exception     
     */	
	public int count(Class<Training> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
