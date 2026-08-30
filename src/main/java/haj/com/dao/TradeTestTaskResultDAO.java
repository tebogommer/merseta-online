package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.TradeTestTaskResult;

public class TradeTestTaskResultDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TradeTestTaskResult
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> allTradeTestTaskResult() throws Exception {
		return (List<TradeTestTaskResult>)super.getList("select o from TradeTestTaskResult o");
	}

	/**
	 * Get all TradeTestTaskResult between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TradeTestTaskResult
 	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> allTradeTestTaskResult(Long from, int noRows) throws Exception {
	 	String hql = "select o from TradeTestTaskResult o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TradeTestTaskResult>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TradeTestTaskResult
 	 * @return a {@link haj.com.entity.TradeTestTaskResult}
 	 * @throws Exception global exception
 	 */
	public TradeTestTaskResult findByKey(Long id) throws Exception {
	 	String hql = "select o from TradeTestTaskResult o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TradeTestTaskResult)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TradeTestTaskResult by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TradeTestTaskResult
  	 * @return a list of {@link haj.com.entity.TradeTestTaskResult}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> findByName(String description) throws Exception {
	 	String hql = "select o from TradeTestTaskResult o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TradeTestTaskResult>)super.getList(hql, parameters);
	}
	
	public int countByTradeTest(Long companyLearnersTradeTestId) throws Exception {
	 	String hql = "select count(o) from TradeTestTaskResult o where o.companyLearnersTradeTest.id = :tradeTestId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyLearnersTradeTestId", companyLearnersTradeTestId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<TradeTestTaskResult> findByCompanyLearnersTradeTestId(Long companylearnerstradetestId) throws Exception {
	 	String hql = "select o from TradeTestTaskResult o inner join CompanyLearnersTradeTest b on b.id = o.companyLearnersTradeTest.id where b.id = :companylearnerstradetestId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companylearnerstradetestId", companylearnerstradetestId);
		return (List<TradeTestTaskResult>)super.getList(hql, parameters);
	}
	
	// 
}

