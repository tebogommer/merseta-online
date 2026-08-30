package haj.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TradeTestTaskResultDAO;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.TradeTestTaskResult;
import haj.com.framework.AbstractService;

public class TradeTestTaskResultService extends AbstractService {
	
	/** The dao. */
	private TradeTestTaskResultDAO dao = new TradeTestTaskResultDAO();

	/**
	 * Get all TradeTestTaskResult
 	 * @author TechFinium 
 	 * @see   TradeTestTaskResult
 	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
	 * @throws Exception the exception
 	 */
	public List<TradeTestTaskResult> allTradeTestTaskResult() throws Exception {
	  	return dao.allTradeTestTaskResult();
	}


	/**
	 * Create or update TradeTestTaskResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TradeTestTaskResult
	 */
	public void create(TradeTestTaskResult entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TradeTestTaskResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestTaskResult
	 */
	public void update(TradeTestTaskResult entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TradeTestTaskResult.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeTestTaskResult
	 */
	public void delete(TradeTestTaskResult entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeTestTaskResult}
	 * @throws Exception the exception
	 * @see    TradeTestTaskResult
	 */
	public TradeTestTaskResult findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TradeTestTaskResult by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
	 * @throws Exception the exception
	 * @see    TradeTestTaskResult
	 */
	public List<TradeTestTaskResult> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TradeTestTaskResult
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
	 * @throws Exception the exception
	 */
	public List<TradeTestTaskResult> allTradeTestTaskResult(int first, int pageSize) throws Exception {
		return dao.allTradeTestTaskResult(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TradeTestTaskResult for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TradeTestTaskResult
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TradeTestTaskResult.class);
	}
	
    /**
     * Lazy load TradeTestTaskResult with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TradeTestTaskResult class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TradeTestTaskResult} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> allTradeTestTaskResult(Class<TradeTestTaskResult> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TradeTestTaskResult>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TradeTestTaskResult for lazy load with filters
     * @author TechFinium 
     * @param entity TradeTestTaskResult class
     * @param filters the filters
     * @return Number of rows in the TradeTestTaskResult entity
     * @throws Exception the exception     
     */	
	public int count(Class<TradeTestTaskResult> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> allTradeTestTaskResultByTradeTestId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		String hql = "select o from TradeTestTaskResult o inner join CompanyLearnersTradeTest b on b.id = o.companyLearnersTradeTest.id where b.id = :tradeTestId ";
		filters.put("tradeTestId", companyLearnersTradeTest.getId());
		return (List<TradeTestTaskResult>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllTradeTestTaskResultByTradeTestId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TradeTestTaskResult o inner join CompanyLearnersTradeTest b on b.id = o.companyLearnersTradeTest.id where b.id = :tradeTestId ";
		return  dao.countWhere(filters, hql);
	}
	
	public int countAllTradeTestTaskResultByTradeTestId(CompanyLearnersTradeTest companylearnerstradetest) throws Exception {
		String hql = "select count(o) from TradeTestTaskResult o where o.companyLearnersTradeTest.id = :tradeTestId ";
		Map<String, Object> filters=new HashMap<String, Object>();
		filters.put("tradeTestId", companylearnerstradetest.getId());
		return  dao.countWhere(filters, hql);
	}
	
	public int countByTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		return dao.countByTradeTest(companyLearnersTradeTest.getId());
	}
	
	public List<TradeTestTaskResult> findByCompanyLearnersTradeTestId(CompanyLearnersTradeTest companylearnerstradetest) throws Exception {
		return dao.findByCompanyLearnersTradeTestId(companylearnerstradetest.getId());
	}

}