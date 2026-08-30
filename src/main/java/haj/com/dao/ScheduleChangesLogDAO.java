package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ScheduleChangesLog;

public class ScheduleChangesLogDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ScheduleChangesLog
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleChangesLog> allScheduleChangesLog() throws Exception {
		return (List<ScheduleChangesLog>)super.getList("select o from ScheduleChangesLog o");
	}

	/**
	 * Get all ScheduleChangesLog between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ScheduleChangesLog
 	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleChangesLog> allScheduleChangesLog(Long from, int noRows) throws Exception {
	 	String hql = "select o from ScheduleChangesLog o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<ScheduleChangesLog>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ScheduleChangesLog
 	 * @return a {@link haj.com.entity.ScheduleChangesLog}
 	 * @throws Exception global exception
 	 */
	public ScheduleChangesLog findByKey(Long id) throws Exception {
	 	String hql = "select o from ScheduleChangesLog o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (ScheduleChangesLog)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countBytargetClassandTargetKey(String targetClass, Long targetKey) throws Exception {
	 	String hql = "select count(o) from ScheduleChangesLog o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass.trim());
	    parameters.put("targetKey", targetKey);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public ScheduleChangesLog findBytargetClassandTargetKey(String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from ScheduleChangesLog o where o.targetClass = :targetClass and o.targetKey = :targetKey " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass.trim());
	    parameters.put("targetKey", targetKey);
		return (ScheduleChangesLog)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ScheduleChangesLog> findBytargetClassandTargetKeyList(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from ScheduleChangesLog o where o.targetClass = :targetClass and o.targetKey = :targetKey order by o.id desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass.trim());
	    parameters.put("targetKey", targetKey);
		return (List<ScheduleChangesLog>)super.getList(hql, parameters);
	}
	
	/**
	 * Find ScheduleChangesLog by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ScheduleChangesLog
  	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleChangesLog> findByName(String description) throws Exception {
	 	String hql = "select o from ScheduleChangesLog o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ScheduleChangesLog>)super.getList(hql, parameters);
	}
}

