package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WspCompanyEmployeesHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspCompanyEmployeesHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistory() throws Exception {
		return (List<WspCompanyEmployeesHistory>)super.getList("select o from WspCompanyEmployeesHistory o");
	}

	/**
	 * Get all WspCompanyEmployeesHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspCompanyEmployeesHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspCompanyEmployeesHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspCompanyEmployeesHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspCompanyEmployeesHistory
 	 * @return a {@link haj.com.entity.WspCompanyEmployeesHistory}
 	 * @throws Exception global exception
 	 */
	public WspCompanyEmployeesHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from WspCompanyEmployeesHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspCompanyEmployeesHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspCompanyEmployeesHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspCompanyEmployeesHistory
  	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> findByName(String description) throws Exception {
	 	String hql = "select o from WspCompanyEmployeesHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspCompanyEmployeesHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> findByWspId(Long wspId) throws Exception {
	 	String hql = "select o from WspCompanyEmployeesHistory o where o.wspLinkId = :wspId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("wspId", wspId);
		return (List<WspCompanyEmployeesHistory>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> findByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WspCompanyEmployeesHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<WspCompanyEmployeesHistory>)super.getList(hql, parameters);
	}
	
}