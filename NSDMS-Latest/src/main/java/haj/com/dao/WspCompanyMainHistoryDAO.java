package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WspCompanyMainHistory;

public class WspCompanyMainHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCompanyMainHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> allWspCompanyMainHistory() throws Exception {
		return (List<WspCompanyMainHistory>)super.getList("select o from WspCompanyMainHistory o");
	}

	/**
	 * Get all WspCompanyMainHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspCompanyMainHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> allWspCompanyMainHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspCompanyMainHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspCompanyMainHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspCompanyMainHistory
 	 * @return a {@link haj.com.entity.WspCompanyMainHistory}
 	 * @throws Exception global exception
 	 */
	public WspCompanyMainHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from WspCompanyMainHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspCompanyMainHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCompanyMainHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspCompanyMainHistory
  	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> findByName(String description) throws Exception {
	 	String hql = "select o from WspCompanyMainHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspCompanyMainHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> findByTargetClassAndTargetKeyOrderByLatest(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select o from WspCompanyMainHistory o where o.targetKey = :targetKey and o.targetClass = :targetClass order by o.id desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspCompanyMainHistory>)super.getList(hql, parameters);
	}
	
	public WspCompanyMainHistory findByTargetClassAndTargetKeyReturnLastest(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select o from WspCompanyMainHistory o where o.targetKey = :targetKey and o.targetClass = :targetClass order by o.id desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (WspCompanyMainHistory)super.getList(hql, parameters, 1);
	}
}

