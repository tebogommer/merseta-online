package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.EquityDAO;
import haj.com.entity.lookup.Equity;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class EquityService.
 */
public class EquityService extends AbstractService {
	/** The dao. */
	private EquityDAO dao = new EquityDAO();

	/**
	 * Get all Equity.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception the exception
	 * @see   Equity
	 */
	public List<Equity> allEquity() throws Exception {
	  	return dao.allEquity();
	}


	/**
	 * Create or update Equity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Equity
	 */
	public void create(Equity entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Equity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Equity
	 */
	public void update(Equity entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Equity.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Equity
	 */
	public void delete(Equity entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Equity}
	 * @throws Exception the exception
	 * @see    Equity
	 */
	public Equity findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Equity by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception the exception
	 * @see    Equity
	 */
	public List<Equity> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<Equity> findByNameEcludeOtherAndUnkown(String description) throws Exception {
		return dao.findByNameEcludeOtherAndUnkown(description);
	}
	
	/**
	 * Lazy load Equity.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Equity}
	 * @throws Exception the exception
	 */
	public List<Equity> allEquity(int first, int pageSize) throws Exception {
		return dao.allEquity(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Equity for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Equity
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Equity.class);
	}
	
    /**
     * Lazy load Equity with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Equity class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Equity} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Equity> allEquity(Class<Equity> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Equity>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Equity for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Equity class
     * @param filters the filters
     * @return Number of rows in the Equity entity
     * @throws Exception the exception
     */	
	public int count(Class<Equity> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the equity
	 * @throws Exception the exception
	 */
	public Equity findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
	
	public Equity findByNameReturnOneResult(String description) throws Exception {
		List<Equity> results = findByName(description);
		if (results.isEmpty()) {
			return null;
		} else {
			return results.get(0);
		}
	}
}
