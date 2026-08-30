package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TrainingReportedAtrPtrDAO;
import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.framework.AbstractService;

public class TrainingReportedAtrPtrService extends AbstractService {
	/** The dao. */
	private TrainingReportedAtrPtrDAO dao = new TrainingReportedAtrPtrDAO();

	/**
	 * Get all TrainingReportedAtrPtr
 	 * @author TechFinium 
 	 * @see   TrainingReportedAtrPtr
 	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
	 * @throws Exception the exception
 	 */
	public List<TrainingReportedAtrPtr> allTrainingReportedAtrPtr() throws Exception {
	  	return dao.allTrainingReportedAtrPtr();
	}


	/**
	 * Create or update TrainingReportedAtrPtr.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingReportedAtrPtr
	 */
	public void create(TrainingReportedAtrPtr entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingReportedAtrPtr.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingReportedAtrPtr
	 */
	public void update(TrainingReportedAtrPtr entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingReportedAtrPtr.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingReportedAtrPtr
	 */
	public void delete(TrainingReportedAtrPtr entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingReportedAtrPtr}
	 * @throws Exception the exception
	 * @see    TrainingReportedAtrPtr
	 */
	public TrainingReportedAtrPtr findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingReportedAtrPtr by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
	 * @throws Exception the exception
	 * @see    TrainingReportedAtrPtr
	 */
	public List<TrainingReportedAtrPtr> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TrainingReportedAtrPtr
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingReportedAtrPtr}
	 * @throws Exception the exception
	 */
	public List<TrainingReportedAtrPtr> allTrainingReportedAtrPtr(int first, int pageSize) throws Exception {
		return dao.allTrainingReportedAtrPtr(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingReportedAtrPtr for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingReportedAtrPtr
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingReportedAtrPtr.class);
	}
	
    /**
     * Lazy load TrainingReportedAtrPtr with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingReportedAtrPtr class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingReportedAtrPtr} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingReportedAtrPtr> allTrainingReportedAtrPtr(Class<TrainingReportedAtrPtr> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingReportedAtrPtr>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingReportedAtrPtr for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingReportedAtrPtr class
     * @param filters the filters
     * @return Number of rows in the TrainingReportedAtrPtr entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingReportedAtrPtr> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
