package haj.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class TransactionsCompanyValidiationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TransactionsCompanyValidiation
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiation() throws Exception {
		return (List<TransactionsCompanyValidiation>)super.getList("select o from TransactionsCompanyValidiation o");
	}

	/**
	 * Get all TransactionsCompanyValidiation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    TransactionsCompanyValidiation
 	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiation(Long from, int noRows) throws Exception {
	 	String hql = "select o from TransactionsCompanyValidiation o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<TransactionsCompanyValidiation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    TransactionsCompanyValidiation
 	 * @return a {@link haj.com.entity.TransactionsCompanyValidiation}
 	 * @throws Exception global exception
 	 */
	public TransactionsCompanyValidiation findByKey(Long id) throws Exception {
	 	String hql = "select o from TransactionsCompanyValidiation o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (TransactionsCompanyValidiation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TransactionsCompanyValidiation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    TransactionsCompanyValidiation
  	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> findByName(String description) throws Exception {
	 	String hql = "select o from TransactionsCompanyValidiation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<TransactionsCompanyValidiation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from TransactionsCompanyValidiation o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<TransactionsCompanyValidiation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> findByTargetClassKeyAndErrorEntry(String targetClass, Long targetKey, Boolean errorentry) throws Exception {
	 	String hql = "select o from TransactionsCompanyValidiation o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.errorEntry = :errorEntry" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("errorEntry", errorentry);
		return (List<TransactionsCompanyValidiation>)super.getList(hql, parameters);
	}
	
	public Integer countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(String targetClass, Long targetKey, Boolean errorentry) throws Exception {
		String hql = "select count(o) from TransactionsCompanyValidiation o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.errorEntry = :errorEntry";
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("errorEntry", errorentry);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

