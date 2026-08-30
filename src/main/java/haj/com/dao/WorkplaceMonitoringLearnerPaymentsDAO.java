package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkplaceMonitoringLearnerPaymentsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerPayments
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPayments() throws Exception {
		return (List<WorkplaceMonitoringLearnerPayments>)super.getList("select o from WorkplaceMonitoringLearnerPayments o");
	}

	/**
	 * Get all WorkplaceMonitoringLearnerPayments between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> allWorkplaceMonitoringLearnerPayments(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerPayments o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceMonitoringLearnerPayments>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringLearnerPayments findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerPayments o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringLearnerPayments)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringLearnerPayments by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringLearnerPayments
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerPayments}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerPayments o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringLearnerPayments>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerPayments> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerPayments o where o.targetClass = :targetClass  and o.targetKey = :targetKey  " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<WorkplaceMonitoringLearnerPayments>)super.getList(hql, parameters);
	}
}

