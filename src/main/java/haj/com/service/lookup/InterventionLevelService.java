package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.InterventionLevelDAO;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionLevelService.
 */
public class InterventionLevelService extends AbstractService {
	/** The dao. */
	private InterventionLevelDAO dao = new InterventionLevelDAO();

	/**
	 * Get all InterventionLevel.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception the exception
	 * @see   InterventionLevel
	 */
	public List<InterventionLevel> allInterventionLevel() throws Exception {
	  	return dao.allInterventionLevel();
	}


	/**
	 * Create or update InterventionLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     InterventionLevel
	 */
	public void create(InterventionLevel entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  InterventionLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionLevel
	 */
	public void update(InterventionLevel entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  InterventionLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    InterventionLevel
	 */
	public void delete(InterventionLevel entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionLevel}
	 * @throws Exception the exception
	 * @see    InterventionLevel
	 */
	public InterventionLevel findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find InterventionLevel by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception the exception
	 * @see    InterventionLevel
	 */
	public List<InterventionLevel> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load InterventionLevel.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception the exception
	 */
	public List<InterventionLevel> allInterventionLevel(int first, int pageSize) throws Exception {
		return dao.allInterventionLevel(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of InterventionLevel for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InterventionLevel
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(InterventionLevel.class);
	}
	
    /**
     * Lazy load InterventionLevel with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 InterventionLevel class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.InterventionLevel} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<InterventionLevel> allInterventionLevel(Class<InterventionLevel> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<InterventionLevel>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of InterventionLevel for lazy load with filters.
     *
     * @author TechFinium
     * @param entity InterventionLevel class
     * @param filters the filters
     * @return Number of rows in the InterventionLevel entity
     * @throws Exception the exception
     */	
	public int count(Class<InterventionLevel> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the intervention level
	 * @throws Exception the exception
	 */
	public InterventionLevel findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
