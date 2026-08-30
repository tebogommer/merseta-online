package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.LearnerMentorRatioDAO;
import haj.com.entity.lookup.LearnerMentorRatio;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class LearnerMentorRatioService extends AbstractService {
	/** The dao. */
	private LearnerMentorRatioDAO dao = new LearnerMentorRatioDAO();

	/**
	 * Get all LearnerMentorRatio
 	 * @author TechFinium 
 	 * @see   LearnerMentorRatio
 	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
	 * @throws Exception the exception
 	 */
	public List<LearnerMentorRatio> allLearnerMentorRatio() throws Exception {
	  	return dao.allLearnerMentorRatio();
	}


	/**
	 * Create or update LearnerMentorRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnerMentorRatio
	 */
	public void create(LearnerMentorRatio entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnerMentorRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerMentorRatio
	 */
	public void update(LearnerMentorRatio entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnerMentorRatio.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerMentorRatio
	 */
	public void delete(LearnerMentorRatio entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerMentorRatio}
	 * @throws Exception the exception
	 * @see    LearnerMentorRatio
	 */
	public LearnerMentorRatio findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnerMentorRatio by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
	 * @throws Exception the exception
	 * @see    LearnerMentorRatio
	 */
	public List<LearnerMentorRatio> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnerMentorRatio
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerMentorRatio}
	 * @throws Exception the exception
	 */
	public List<LearnerMentorRatio> allLearnerMentorRatio(int first, int pageSize) throws Exception {
		return dao.allLearnerMentorRatio(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnerMentorRatio for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LearnerMentorRatio
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnerMentorRatio.class);
	}
	
    /**
     * Lazy load LearnerMentorRatio with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LearnerMentorRatio class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnerMentorRatio} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnerMentorRatio> allLearnerMentorRatio(Class<LearnerMentorRatio> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnerMentorRatio>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnerMentorRatio for lazy load with filters
     * @author TechFinium 
     * @param entity LearnerMentorRatio class
     * @param filters the filters
     * @return Number of rows in the LearnerMentorRatio entity
     * @throws Exception the exception     
     */	
	public int count(Class<LearnerMentorRatio> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
