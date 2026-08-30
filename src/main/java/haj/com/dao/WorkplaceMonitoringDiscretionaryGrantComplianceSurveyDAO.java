package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;

public class WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey() throws Exception {
		return (List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)super.getList("select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o");
	}

	/**
	 * Get all WorkplaceMonitoringDiscretionaryGrantComplianceSurvey between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringDiscretionaryGrantComplianceSurvey by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> findByNoTargetClassAndKey() throws Exception {
	 	String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass is null and o.targetKey is null" ;
		return (List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)super.getList(hql);
	}
	
	public Integer countByNoTargetClassAndKey() throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass is null and o.targetKey is null and o.interventionType.id = :interventionTypeId" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
}

