package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.TradeTestResult;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TradeTestResultDAO.
 */
public class TradeTestResultDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TradeTestResult.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception global exception
	 * @see    TradeTestResult
	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestResult> allTradeTestResult() throws Exception {
		return (List<TradeTestResult>)super.getList("select o from TradeTestResult o");
	}

	/**
	 * Get all TradeTestResult between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception global exception
	 * @see    TradeTestResult
	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestResult> allTradeTestResult(Long from, int noRows) throws Exception {
	 	String hql = "select o from TradeTestResult o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TradeTestResult>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeTestResult}
	 * @throws Exception global exception
	 * @see    TradeTestResult
	 */
	public TradeTestResult findByKey(Long id) throws Exception {
	 	String hql = "select o from TradeTestResult o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TradeTestResult)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TradeTestResult by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.TradeTestResult}
	 * @throws Exception global exception
	 * @see    TradeTestResult
	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestResult> findByName(String description) throws Exception {
	 	String hql = "select o from TradeTestResult o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TradeTestResult>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param tradeTestResult the trade test result
	 * @return a {@link haj.com.entity.TradeTestResult}
	 * @throws Exception global exception
	 * @see    TradeTestResult
	 */
	public TradeTestResult findUniqueCode(TradeTestResult tradeTestResult) throws Exception {
	 	String hql = "select o from TradeTestResult o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (tradeTestResult.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", tradeTestResult.getId());
	 	}
	   
	    parameters.put("code", tradeTestResult.getCode());
		return (TradeTestResult)super.getUniqueResult(hql, parameters);
	}
}

