package haj.com.service;

import java.util.List;

import haj.com.dao.TrainingProviderLearnershipDAO;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class TrainingProviderLearnershipService extends AbstractService {
	/** The dao. */
	private TrainingProviderLearnershipDAO dao = new TrainingProviderLearnershipDAO();

	/**
	 * Get all TrainingProviderLearnership
 	 * @author TechFinium 
 	 * @see   TrainingProviderLearnership
 	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
	 * @throws Exception the exception
 	 */
	public List<TrainingProviderLearnership> allTrainingProviderLearnership() throws Exception {
	  	return dao.allTrainingProviderLearnership();
	}


	/**
	 * Create or update TrainingProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TrainingProviderLearnership
	 */
	public void create(TrainingProviderLearnership entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TrainingProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderLearnership
	 */
	public void update(TrainingProviderLearnership entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TrainingProviderLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TrainingProviderLearnership
	 */
	public void delete(TrainingProviderLearnership entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TrainingProviderLearnership}
	 * @throws Exception the exception
	 * @see    TrainingProviderLearnership
	 */
	public TrainingProviderLearnership findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TrainingProviderLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
	 * @throws Exception the exception
	 * @see    TrainingProviderLearnership
	 */
	public List<TrainingProviderLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<TrainingProviderLearnership> findByTrainingProvider(Long id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByTrainingProvider(id);
	}
	
	/**
	 * Lazy load TrainingProviderLearnership
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderLearnership}
	 * @throws Exception the exception
	 */
	public List<TrainingProviderLearnership> allTrainingProviderLearnership(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderLearnership(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TrainingProviderLearnership for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TrainingProviderLearnership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TrainingProviderLearnership.class);
	}
	
    /**
     * Lazy load TrainingProviderLearnership with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TrainingProviderLearnership class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TrainingProviderLearnership} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> allTrainingProviderLearnership(Class<TrainingProviderLearnership> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TrainingProviderLearnership>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TrainingProviderLearnership for lazy load with filters
     * @author TechFinium 
     * @param entity TrainingProviderLearnership class
     * @param filters the filters
     * @return Number of rows in the TrainingProviderLearnership entity
     * @throws Exception the exception     
     */	
	public int count(Class<TrainingProviderLearnership> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> allTrainingProviderLearnershipByProviderApplicationId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId) throws Exception {
		String hql="select o from TrainingProviderLearnership o where o.trainingProviderApplication.id = :providerApplicationId  ";
		filters.put("providerApplicationId", providerApplicationId);
		return (List<TrainingProviderLearnership>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderLearnershipProviderApplicationId(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderLearnership o where o.trainingProviderApplication.id = :providerApplicationId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingProviderLearnership> allTrainingProviderLearnershipByProviderApplicationIdAndManuallyEntered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long providerApplicationId, Boolean manuallyAdded) throws Exception {
		String hql="select o from TrainingProviderLearnership o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		filters.put("providerApplicationId", providerApplicationId);
		filters.put("manuallyAdded", manuallyAdded);
		return (List<TrainingProviderLearnership>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTrainingProviderLearnershipByProviderApplicationIdAndManuallyEntered(Map<String, Object> filters) throws Exception {
		String hql="select count(o) from TrainingProviderLearnership o where o.trainingProviderApplication.id = :providerApplicationId and o.manuallyAdded = :manuallyAdded";
		return dao.countWhere(filters, hql);
	}
}
