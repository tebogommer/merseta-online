package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ScheduledEvent;

public class ScheduledEventDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ScheduledEvent
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 * @return a list of {@link haj.com.entity.ScheduledEvent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> allScheduledEvent() throws Exception {
		return (List<ScheduledEvent>)super.getList("select o from ScheduledEvent o");
	}

	/**
	 * Get all ScheduledEvent between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ScheduledEvent
 	 * @return a list of {@link haj.com.entity.ScheduledEvent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> allScheduledEvent(Long from, int noRows) throws Exception {
	 	String hql = "select o from ScheduledEvent o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ScheduledEvent>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ScheduledEvent
 	 * @return a {@link haj.com.entity.ScheduledEvent}
 	 * @throws Exception global exception
 	 */
	public ScheduledEvent findByKey(Long id) throws Exception {
	 	String hql = "select o from ScheduledEvent o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ScheduledEvent)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ScheduledEvent by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ScheduledEvent
  	 * @return a list of {@link haj.com.entity.ScheduledEvent}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> findByName(String description) throws Exception {
	 	String hql = "select o from ScheduledEvent o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ScheduledEvent>)super.getList(hql, parameters);
	}

	public ScheduledEvent findByTargetKeyAndTargetClass(Long targetKey, String targetClass) throws Exception{
		String hql = "select o from ScheduledEvent o where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (ScheduledEvent)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> findByTargetKeyAndTargetClassList(Long targetKey, String targetClass) throws Exception{
		String hql = "select o from ScheduledEvent o where o.targetKey = :targetKey and o.targetClass = :targetClass" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetKey", targetKey);
	    parameters.put("targetClass", targetClass);
		return (List<ScheduledEvent>)super.getUniqueResult(hql, parameters);
	}
}

