package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.IngterSetaTransferBean;
import haj.com.entity.HistoricalIntersetaTransfers;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class HistoricalIntersetaTransfersDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all HistoricalIntersetaTransfers
 	 * @author TechFinium
 	 * @see    HistoricalIntersetaTransfers
 	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HistoricalIntersetaTransfers> allHistoricalIntersetaTransfers() throws Exception {
		return (List<HistoricalIntersetaTransfers>)super.getList("select o from HistoricalIntersetaTransfers o");
	}

	/**
	 * Get all HistoricalIntersetaTransfers between from and noRows
 	 * @author TechFinium
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    HistoricalIntersetaTransfers
 	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HistoricalIntersetaTransfers> allHistoricalIntersetaTransfers(Long from, int noRows) throws Exception {
	 	String hql = "select o from HistoricalIntersetaTransfers o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<HistoricalIntersetaTransfers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium
 	 * @param id the id
 	 * @see    HistoricalIntersetaTransfers
 	 * @return a {@link haj.com.entity.HistoricalIntersetaTransfers}
 	 * @throws Exception global exception
 	 */
	public HistoricalIntersetaTransfers findByKey(Long id) throws Exception {
	 	String hql = "select o from HistoricalIntersetaTransfers o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (HistoricalIntersetaTransfers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find HistoricalIntersetaTransfers by description
 	 * @author TechFinium
 	 * @param description the description
 	 * @see    HistoricalIntersetaTransfers
  	 * @return a list of {@link haj.com.entity.HistoricalIntersetaTransfers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<HistoricalIntersetaTransfers> findByName(String description) throws Exception {
	 	String hql = "select o from HistoricalIntersetaTransfers o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<HistoricalIntersetaTransfers>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<IngterSetaTransferBean> summaryBySchemeYear() throws Exception {
	String hql ="select new haj.com.bean.IngterSetaTransferBean( o.mersetaSchemeYear, o.transactionType, sum(o.amount)) "+
			 "from HistoricalIntersetaTransfers o " +
			"group by o.mersetaSchemeYear, o.transactionType " +
			"order by o.mersetaSchemeYear, o.transactionType";
			return (List<IngterSetaTransferBean>)super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<IngterSetaTransferBean> breakDownBySchemeYearandTransactionType(Integer mersetaSchemeYear, String transactionType) throws Exception {
	String hql = "select  new haj.com.bean.IngterSetaTransferBean( o.mersetaSchemeYear, o.transactionType, o.grantType, sum(o.amount)) from HistoricalIntersetaTransfers o " +
			"where o.mersetaSchemeYear = :mersetaSchemeYear " +
			"and  o.transactionType = :transactionType " +
			"group by o.mersetaSchemeYear, o.transactionType,o.grantType " +
			"order by o.mersetaSchemeYear, o.transactionType,o.grantType";
		Map<String, Object> parameters = new Hashtable<String, Object>();
    		parameters.put("mersetaSchemeYear", mersetaSchemeYear);
    		parameters.put("transactionType", transactionType);
    		return (List<IngterSetaTransferBean>)super.getList(hql, parameters);
	}

	public Double totalMandatoryPerSchemeYearAndLNumber(Integer mersetaSchemeYear, String refNo)  throws Exception {
	String hql  ="select  sum(o.amount) from HistoricalIntersetaTransfers o " +
			"where o.mersetaSchemeYear = :mersetaSchemeYear " +
			"and  o.grantType = 'Mandatory' " +
			"and o.refNo = :refNo ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("mersetaSchemeYear", mersetaSchemeYear);
		parameters.put("refNo", refNo);
		return (Double)super.getUniqueResult(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<IngterSetaTransferBean> mandatoryBySchemeYear(Integer mersetaSchemeYear) throws Exception {
	String hql = "select  new haj.com.bean.IngterSetaTransferBean(o.transactionType , sum(o.amount)) from HistoricalIntersetaTransfers o " +
			"where o.mersetaSchemeYear = :mersetaSchemeYear " +
			"and  o.grantType = 'Mandatory' " +
			"group by  o.transactionType ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("mersetaSchemeYear", mersetaSchemeYear);
		return (List<IngterSetaTransferBean>)super.getList(hql, parameters);
	}
}

