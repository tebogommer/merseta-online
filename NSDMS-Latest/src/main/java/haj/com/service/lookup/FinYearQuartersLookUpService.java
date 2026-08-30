package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.FinYearQuartersLookUpDAO;
import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class FinYearQuartersLookUpService extends AbstractService {
	
	/** The dao. */
	private FinYearQuartersLookUpDAO dao = new FinYearQuartersLookUpDAO();

	/**
	 * Get all FinYearQuartersLookUp
 	 * @author TechFinium 
 	 * @see   FinYearQuartersLookUp
 	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
	 * @throws Exception the exception
 	 */
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUp() throws Exception {
	  	return dao.allFinYearQuartersLookUp();
	}
	
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUpOrderedByQuarters() throws Exception {
		return dao.allFinYearQuartersLookUpOrderedByQuarters();
	}

	/**
	 * Create or update FinYearQuartersLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     FinYearQuartersLookUp
	 */
	public void create(FinYearQuartersLookUp entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  FinYearQuartersLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FinYearQuartersLookUp
	 */
	public void update(FinYearQuartersLookUp entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  FinYearQuartersLookUp.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    FinYearQuartersLookUp
	 */
	public void delete(FinYearQuartersLookUp entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.FinYearQuartersLookUp}
	 * @throws Exception the exception
	 * @see    FinYearQuartersLookUp
	 */
	public FinYearQuartersLookUp findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find FinYearQuartersLookUp by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
	 * @throws Exception the exception
	 * @see    FinYearQuartersLookUp
	 */
	public List<FinYearQuartersLookUp> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load FinYearQuartersLookUp
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.FinYearQuartersLookUp}
	 * @throws Exception the exception
	 */
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUp(int first, int pageSize) throws Exception {
		return dao.allFinYearQuartersLookUp(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of FinYearQuartersLookUp for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the FinYearQuartersLookUp
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(FinYearQuartersLookUp.class);
	}
	
    /**
     * Lazy load FinYearQuartersLookUp with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 FinYearQuartersLookUp class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.FinYearQuartersLookUp} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<FinYearQuartersLookUp> allFinYearQuartersLookUp(Class<FinYearQuartersLookUp> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<FinYearQuartersLookUp>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of FinYearQuartersLookUp for lazy load with filters
     * @author TechFinium 
     * @param entity FinYearQuartersLookUp class
     * @param filters the filters
     * @return Number of rows in the FinYearQuartersLookUp entity
     * @throws Exception the exception     
     */	
	public int count(Class<FinYearQuartersLookUp> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public FinYearQuartersLookUp findByQuarterAssigned(FinYearQuartersEnum quarter) throws Exception {
		return dao.findByQuarterAssigned(quarter);
	}
}
