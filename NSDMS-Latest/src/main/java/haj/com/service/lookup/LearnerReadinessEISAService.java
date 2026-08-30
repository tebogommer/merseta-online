package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LearnerReadinessEISADAO;
import haj.com.entity.lookup.LearnerReadinessEISA;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerReadinessEISAService.
 */
public class LearnerReadinessEISAService extends AbstractService {
	/** The dao. */
	private LearnerReadinessEISADAO dao = new LearnerReadinessEISADAO();

	/**
	 * Get all LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception the exception
	 * @see   LearnerReadinessEISA
	 */
	public List<LearnerReadinessEISA> allLearnerReadinessEISA() throws Exception {
	  	return dao.allLearnerReadinessEISA();
	}


	/**
	 * Create or update LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnerReadinessEISA
	 */
	public void create(LearnerReadinessEISA entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerReadinessEISA
	 */
	public void update(LearnerReadinessEISA entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnerReadinessEISA.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnerReadinessEISA
	 */
	public void delete(LearnerReadinessEISA entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception the exception
	 * @see    LearnerReadinessEISA
	 */
	public LearnerReadinessEISA findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnerReadinessEISA by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception the exception
	 * @see    LearnerReadinessEISA
	 */
	public List<LearnerReadinessEISA> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnerReadinessEISA.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnerReadinessEISA}
	 * @throws Exception the exception
	 */
	public List<LearnerReadinessEISA> allLearnerReadinessEISA(int first, int pageSize) throws Exception {
		return dao.allLearnerReadinessEISA(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnerReadinessEISA for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the LearnerReadinessEISA
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnerReadinessEISA.class);
	}
	
    /**
     * Lazy load LearnerReadinessEISA with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 LearnerReadinessEISA class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnerReadinessEISA} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnerReadinessEISA> allLearnerReadinessEISA(Class<LearnerReadinessEISA> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnerReadinessEISA>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnerReadinessEISA for lazy load with filters.
     *
     * @author TechFinium
     * @param entity LearnerReadinessEISA class
     * @param filters the filters
     * @return Number of rows in the LearnerReadinessEISA entity
     * @throws Exception the exception
     */	
	public int count(Class<LearnerReadinessEISA> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
