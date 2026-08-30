package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SkillsGapTrackLookUpDAO;
import haj.com.entity.lookup.SkillsGapTrackLookUp;
import haj.com.framework.AbstractService;

public class SkillsGapTrackLookUpService extends AbstractService {
	/** The dao. */
	private SkillsGapTrackLookUpDAO dao = new SkillsGapTrackLookUpDAO();

	/**
	 * Get all SkillsGapTrackLookUp
 	 * @author TechFinium 
 	 * @see   SkillsGapTrackLookUp
 	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
	 * @throws Exception the exception
 	 */
	public List<SkillsGapTrackLookUp> allSkillsGapTrackLookUp() throws Exception {
	  	return dao.allSkillsGapTrackLookUp();
	}


	/**
	 * Create or update SkillsGapTrackLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsGapTrackLookUp
	 */
	public void create(SkillsGapTrackLookUp entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsGapTrackLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsGapTrackLookUp
	 */
	public void update(SkillsGapTrackLookUp entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsGapTrackLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsGapTrackLookUp
	 */
	public void delete(SkillsGapTrackLookUp entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsGapTrackLookUp}
	 * @throws Exception the exception
	 * @see    SkillsGapTrackLookUp
	 */
	public SkillsGapTrackLookUp findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsGapTrackLookUp by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
	 * @throws Exception the exception
	 * @see    SkillsGapTrackLookUp
	 */
	public List<SkillsGapTrackLookUp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsGapTrackLookUp
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsGapTrackLookUp}
	 * @throws Exception the exception
	 */
	public List<SkillsGapTrackLookUp> allSkillsGapTrackLookUp(int first, int pageSize) throws Exception {
		return dao.allSkillsGapTrackLookUp(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SkillsGapTrackLookUp for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsGapTrackLookUp
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsGapTrackLookUp.class);
	}
	
    /**
     * Lazy load SkillsGapTrackLookUp with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsGapTrackLookUp class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsGapTrackLookUp} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsGapTrackLookUp> allSkillsGapTrackLookUp(Class<SkillsGapTrackLookUp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsGapTrackLookUp>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsGapTrackLookUp for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsGapTrackLookUp class
     * @param filters the filters
     * @return Number of rows in the SkillsGapTrackLookUp entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsGapTrackLookUp> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
