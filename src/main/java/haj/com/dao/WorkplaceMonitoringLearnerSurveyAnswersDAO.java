package haj.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkplaceMonitoringLearnerSurveyAnswersDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurveyAnswers
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswers() throws Exception {
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)super.getList("select o from WorkplaceMonitoringLearnerSurveyAnswers o");
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurveyAnswers between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswers(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o " ;
	    Map<String, Object> parameters =  new HashMap<>();
	    
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringLearnerSurveyAnswers findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.id = :id " ;
	    Map<String, Object> parameters =  new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringLearnerSurveyAnswers)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringLearnerSurveyAnswers by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.description like :description order by o.desc " ;
	    Map<String, Object> parameters =  new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByNoTargetClassAndKeyByIntervetionType(Long interventionTypeId) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass is null and o.targetKey is null and o.interventionType.id = :interventionTypeId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("interventionTypeId", interventionTypeId);
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)super.getList(hql, parameters);
	}
	
	public Integer countByNoTargetClassAndKeyByIntervetionType(Long interventionTypeId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass is null and o.targetKey is null and o.interventionType.id = :interventionTypeId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("interventionTypeId", interventionTypeId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass = :targetClass and o.targetKey = :targetKey" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)super.getList(hql, parameters);
	}
	
}