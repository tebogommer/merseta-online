package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.TradeTestCentersHistoric;

public class TradeTestCentersHistoricDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TradeTestCentersHistoric
 	 * @author TechFinium 
 	 * @see    TradeTestCentersHistoric
 	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestCentersHistoric> allTradeTestCentersHistoric() throws Exception {
		return (List<TradeTestCentersHistoric>)super.getList("select o from TradeTestCentersHistoric o");
	}

	/**
	 * Get all TradeTestCentersHistoric between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TradeTestCentersHistoric
 	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestCentersHistoric> allTradeTestCentersHistoric(Long from, int noRows) throws Exception {
	 	String hql = "select o from TradeTestCentersHistoric o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<TradeTestCentersHistoric>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TradeTestCentersHistoric
 	 * @return a {@link haj.com.entity.TradeTestCentersHistoric}
 	 * @throws Exception global exception
 	 */
	public TradeTestCentersHistoric findByKey(Long id) throws Exception {
	 	String hql = "select o from TradeTestCentersHistoric o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (TradeTestCentersHistoric)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TradeTestCentersHistoric by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TradeTestCentersHistoric
  	 * @return a list of {@link haj.com.entity.TradeTestCentersHistoric}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TradeTestCentersHistoric> findByName(String description) throws Exception {
	 	String hql = "select o from TradeTestCentersHistoric o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TradeTestCentersHistoric>)super.getList(hql, parameters);
	}
}

