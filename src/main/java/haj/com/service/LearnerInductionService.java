package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.LearnerInductionDAO;
import haj.com.entity.LearnerInduction;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.framework.AbstractService;

public class LearnerInductionService extends AbstractService {
	/** The dao. */
	private LearnerInductionDAO dao = new LearnerInductionDAO();

	/**
	 * Get all LearnerInduction
 	 * @author TechFinium 
 	 * @see   LearnerInduction
 	 * @return a list of {@link haj.com.entity.LearnerInduction}
	 * @throws Exception the exception
 	 */
	public List<LearnerInduction> allLearnerInduction() throws Exception {
	  	return dao.allLearnerInduction();
	}


	/**
	 * Create or update LearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnerInduction
	 */
	public void create(LearnerInduction entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerInduction
	 */
	public void update(LearnerInduction entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerInduction
	 */
	public void delete(LearnerInduction entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerInduction}
	 * @throws Exception the exception
	 * @see    LearnerInduction
	 */
	public LearnerInduction findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnerInduction by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnerInduction}
	 * @throws Exception the exception
	 * @see    LearnerInduction
	 */
	public List<LearnerInduction> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnerInduction
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerInduction}
	 * @throws Exception the exception
	 */
	public List<LearnerInduction> allLearnerInduction(int first, int pageSize) throws Exception {
		return dao.allLearnerInduction(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnerInduction for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LearnerInduction
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnerInduction.class);
	}
	
    /**
     * Lazy load LearnerInduction with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LearnerInduction class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnerInduction} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnerInduction> allLearnerInduction(Class<LearnerInduction> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnerInduction>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnerInduction for lazy load with filters
     * @author TechFinium 
     * @param entity LearnerInduction class
     * @param filters the filters
     * @return Number of rows in the LearnerInduction entity
     * @throws Exception the exception     
     */	
	public int count(Class<LearnerInduction> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<LearnerInduction> findByWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) throws Exception {
		return dao.findByWorkplaceMonitoring(workplaceMonitoring);
	}
}
