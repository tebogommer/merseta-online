package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.NambDecisionHistory;

public class NambDecisionHistoryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NambDecisionHistory
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NambDecisionHistory> allNambDecisionHistory() throws Exception {
		return (List<NambDecisionHistory>)super.getList("select o from NambDecisionHistory o");
	}

	/**
	 * Get all NambDecisionHistory between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NambDecisionHistory
 	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NambDecisionHistory> allNambDecisionHistory(Long from, int noRows) throws Exception {
	 	String hql = "select o from NambDecisionHistory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NambDecisionHistory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NambDecisionHistory
 	 * @return a {@link haj.com.entity.NambDecisionHistory}
 	 * @throws Exception global exception
 	 */
	public NambDecisionHistory findByKey(Long id) throws Exception {
	 	String hql = "select o from NambDecisionHistory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NambDecisionHistory)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NambDecisionHistory by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NambDecisionHistory
  	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NambDecisionHistory> findByName(String description) throws Exception {
	 	String hql = "select o from NambDecisionHistory o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NambDecisionHistory>)super.getList(hql, parameters);
	}
	
	/**
	 * Counts entries by target class and target key on NambDecisionHistory
	 * @param targetKey
	 * @param targetClass
	 * @return int the count
	 * @throws Exception
	 */
	public NambDecisionHistory countByTargetClassAndKey(Long targetKey, String targetClass) throws Exception {
	 	String hql = "select count(o) from NambDecisionHistory o where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (NambDecisionHistory)super.getUniqueResult(hql, parameters);
	}
	
}