package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspSdfCompanyHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspSdfCompanyHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspSdfCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistory() throws Exception {
		return (List<WspSdfCompanyHistory>)super.getList("select o from WspSdfCompanyHistory o");
	}

	/**
	 * Get all WspSdfCompanyHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspSdfCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspSdfCompanyHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspSdfCompanyHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspSdfCompanyHistory
 	 * @return a {@link haj.com.entity.WspSdfCompanyHistory}
 	 * @throws Exception global exception
 	 */
	public WspSdfCompanyHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from WspSdfCompanyHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspSdfCompanyHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspSdfCompanyHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspSdfCompanyHistory
  	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> findByName(String description) throws Exception {
	 	String hql = "select o from WspSdfCompanyHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspSdfCompanyHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspSdfCompanyHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<WspSdfCompanyHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> findByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WspSdfCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspSdfCompanyHistory>)super.getList(hql, parameters);
	}
	
	
}