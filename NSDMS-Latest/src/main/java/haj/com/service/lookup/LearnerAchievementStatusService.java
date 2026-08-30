package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LearnerAchievementStatusDAO;
import haj.com.entity.lookup.LearnerAchievementStatus;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerAchievementStatusService.
 */
public class LearnerAchievementStatusService extends AbstractService {
	/** The dao. */
	private LearnerAchievementStatusDAO dao = new LearnerAchievementStatusDAO();

	/**
	 * Get all LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception the exception
	 * @see   LearnerAchievementStatus
	 */
	public List<LearnerAchievementStatus> allLearnerAchievementStatus() throws Exception {
	  	return dao.allLearnerAchievementStatus();
	}


	/**
	 * Create or update LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnerAchievementStatus
	 */
	public void create(LearnerAchievementStatus entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerAchievementStatus
	 */
	public void update(LearnerAchievementStatus entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnerAchievementStatus.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerAchievementStatus
	 */
	public void delete(LearnerAchievementStatus entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception the exception
	 * @see    LearnerAchievementStatus
	 */
	public LearnerAchievementStatus findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnerAchievementStatus by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception the exception
	 * @see    LearnerAchievementStatus
	 */
	public List<LearnerAchievementStatus> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnerAchievementStatus.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerAchievementStatus}
	 * @throws Exception the exception
	 */
	public List<LearnerAchievementStatus> allLearnerAchievementStatus(int first, int pageSize) throws Exception {
		return dao.allLearnerAchievementStatus(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnerAchievementStatus for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the LearnerAchievementStatus
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnerAchievementStatus.class);
	}
	
    /**
     * Lazy load LearnerAchievementStatus with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 LearnerAchievementStatus class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnerAchievementStatus} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnerAchievementStatus> allLearnerAchievementStatus(Class<LearnerAchievementStatus> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnerAchievementStatus>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnerAchievementStatus for lazy load with filters.
     *
     * @author TechFinium
     * @param entity LearnerAchievementStatus class
     * @param filters the filters
     * @return Number of rows in the LearnerAchievementStatus entity
     * @throws Exception the exception
     */	
	public int count(Class<LearnerAchievementStatus> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
