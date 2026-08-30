package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.TradeTestResultDAO;
import haj.com.entity.lookup.TradeTestResult;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TradeTestResultService.
 */
public class TradeTestResultService extends AbstractService {
	/** The dao. */
	private TradeTestResultDAO dao = new TradeTestResultDAO();

	/**
	 * Get all TradeTestResult.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception the exception
	 * @see   TradeTestResult
	 */
	public List<TradeTestResult> allTradeTestResult() throws Exception {
	  	return dao.allTradeTestResult();
	}


	/**
	 * Create or update TradeTestResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TradeTestResult
	 */
	public void create(TradeTestResult entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");

	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TradeTestResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestResult
	 */
	public void update(TradeTestResult entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TradeTestResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestResult
	 */
	public void delete(TradeTestResult entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeTestResult}
	 * @throws Exception the exception
	 * @see    TradeTestResult
	 */
	public TradeTestResult findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TradeTestResult by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception the exception
	 * @see    TradeTestResult
	 */
	public List<TradeTestResult> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TradeTestResult.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception the exception
	 */
	public List<TradeTestResult> allTradeTestResult(int first, int pageSize) throws Exception {
		return dao.allTradeTestResult(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TradeTestResult for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TradeTestResult
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TradeTestResult.class);
	}
	
    /**
     * Lazy load TradeTestResult with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TradeTestResult class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TradeTestResult} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TradeTestResult> allTradeTestResult(Class<TradeTestResult> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TradeTestResult>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TradeTestResult for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TradeTestResult class
     * @param filters the filters
     * @return Number of rows in the TradeTestResult entity
     * @throws Exception the exception
     */	
	public int count(Class<TradeTestResult> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
