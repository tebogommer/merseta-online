package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.enums.ActionPlanValidiationTypeEnum;

public class WorkplaceMonitoringActionPlanDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringActionPlan
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringActionPlan
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlan() throws Exception {
		return (List<WorkplaceMonitoringActionPlan>)super.getList("select o from WorkplaceMonitoringActionPlan o");
	}

	/**
	 * Get all WorkplaceMonitoringActionPlan between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringActionPlan
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlan(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringActionPlan o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<WorkplaceMonitoringActionPlan>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringActionPlan
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringActionPlan}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringActionPlan findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringActionPlan o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringActionPlan)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringActionPlan by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringActionPlan
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringActionPlan o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringActionPlan>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> findByNoTargetClassAndKey() throws Exception {
	 	String hql = "select o from WorkplaceMonitoringActionPlan o where o.targetClass is null and o.targetKey is null" ;
		return (List<WorkplaceMonitoringActionPlan>)super.getList(hql);
	}
	
	public Integer countByActionPlanValidiationType(ActionPlanValidiationTypeEnum actionPlanValidiationType) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringActionPlan o where o.actionPlanValidiationTypeEnum = :actionPlanValidiationType" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("actionPlanValidiationType", actionPlanValidiationType);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByActionPlanValidiationTypeAndDoesNotEqualId(ActionPlanValidiationTypeEnum actionPlanValidiationType, Long workplaceMonitoringActionPlanId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringActionPlan o where o.actionPlanValidiationTypeEnum = :actionPlanValidiationType and o.id <> :workplaceMonitoringActionPlanId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("actionPlanValidiationType", actionPlanValidiationType);
	    parameters.put("workplaceMonitoringActionPlanId", workplaceMonitoringActionPlanId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
		
}