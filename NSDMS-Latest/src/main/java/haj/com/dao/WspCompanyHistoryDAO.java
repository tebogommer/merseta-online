package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WspCompanyHistory;

public class WspCompanyHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> allWspCompanyHistory() throws Exception {
		return (List<WspCompanyHistory>)super.getList("select o from WspCompanyHistory o");
	}

	/**
	 * Get all WspCompanyHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> allWspCompanyHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspCompanyHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspCompanyHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspCompanyHistory
 	 * @return a {@link haj.com.entity.WspCompanyHistory}
 	 * @throws Exception global exception
 	 */
	public WspCompanyHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspCompanyHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCompanyHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspCompanyHistory
  	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findByName(String description) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}
	
	public int countCompanyHistoryByWspId(Long wspId) throws Exception {
	 	String hql = "select count(o) from WspCompanyHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyHistoryByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select count(o) from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public WspCompanyHistory findCompanyHistoryByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (WspCompanyHistory)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findCompanyHistoryListByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey order by o.id desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findCompanyHistoryListByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.wspLinkId = :wspId order by o.id desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> findByTargetClassAndKeyAndMainLink(String targetClass, Long targetKey, Long wspCompanyMainHistoryId) throws Exception {
	 	String hql = "select o from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId order by o.id desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
	    parameters.put("wspCompanyMainHistoryId", wspCompanyMainHistoryId);
		return (List<WspCompanyHistory>)super.getList(hql, parameters);
	}

}