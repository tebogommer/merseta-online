package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspCompanyAddressHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspCompanyAddressHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCompanyAddressHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistory() throws Exception {
		return (List<WspCompanyAddressHistory>)super.getList("select o from WspCompanyAddressHistory o");
	}

	/**
	 * Get all WspCompanyAddressHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspCompanyAddressHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspCompanyAddressHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspCompanyAddressHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspCompanyAddressHistory
 	 * @return a {@link haj.com.entity.WspCompanyAddressHistory}
 	 * @throws Exception global exception
 	 */
	public WspCompanyAddressHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from WspCompanyAddressHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspCompanyAddressHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCompanyAddressHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspCompanyAddressHistory
  	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> findByName(String description) throws Exception {
	 	String hql = "select o from WspCompanyAddressHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspCompanyAddressHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspCompanyAddressHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<WspCompanyAddressHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> findByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WspCompanyAddressHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspCompanyAddressHistory>)super.getList(hql, parameters);
	}
	
}