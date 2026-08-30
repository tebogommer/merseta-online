package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.AetProgrammeLevelDAO;
import haj.com.entity.lookup.AetProgrammeLevel;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AetProgrammeLevelService extends AbstractService {
	/** The dao. */
	private AetProgrammeLevelDAO dao = new AetProgrammeLevelDAO();

	/**
	 * Get all AetProgrammeLevel
 	 * @author TechFinium 
 	 * @see   AetProgrammeLevel
 	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
	 * @throws Exception the exception
 	 */
	public List<AetProgrammeLevel> allAetProgrammeLevel() throws Exception {
	  	return dao.allAetProgrammeLevel();
	}


	/**
	 * Create or update AetProgrammeLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AetProgrammeLevel
	 */
	public void create(AetProgrammeLevel entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AetProgrammeLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AetProgrammeLevel
	 */
	public void update(AetProgrammeLevel entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AetProgrammeLevel.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AetProgrammeLevel
	 */
	public void delete(AetProgrammeLevel entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AetProgrammeLevel}
	 * @throws Exception the exception
	 * @see    AetProgrammeLevel
	 */
	public AetProgrammeLevel findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AetProgrammeLevel by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
	 * @throws Exception the exception
	 * @see    AetProgrammeLevel
	 */
	public List<AetProgrammeLevel> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AetProgrammeLevel
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AetProgrammeLevel}
	 * @throws Exception the exception
	 */
	public List<AetProgrammeLevel> allAetProgrammeLevel(int first, int pageSize) throws Exception {
		return dao.allAetProgrammeLevel(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AetProgrammeLevel for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AetProgrammeLevel
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AetProgrammeLevel.class);
	}
	
    /**
     * Lazy load AetProgrammeLevel with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AetProgrammeLevel class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AetProgrammeLevel} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AetProgrammeLevel> allAetProgrammeLevel(Class<AetProgrammeLevel> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AetProgrammeLevel>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AetProgrammeLevel for lazy load with filters
     * @author TechFinium 
     * @param entity AetProgrammeLevel class
     * @param filters the filters
     * @return Number of rows in the AetProgrammeLevel entity
     * @throws Exception the exception     
     */	
	public int count(Class<AetProgrammeLevel> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
