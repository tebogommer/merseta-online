package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;

public class WorkplaceMonitoringLearnerSurveyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurvey
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurvey() throws Exception {
		return (List<WorkplaceMonitoringLearnerSurvey>)super.getList("select o from WorkplaceMonitoringLearnerSurvey o");
	}

	/**
	 * Get all WorkplaceMonitoringLearnerSurvey between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurvey(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurvey o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WorkplaceMonitoringLearnerSurvey>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringLearnerSurvey
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringLearnerSurvey findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurvey o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringLearnerSurvey)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringLearnerSurvey by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringLearnerSurvey
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurvey> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerSurvey o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringLearnerSurvey>)super.getList(hql, parameters);
	}
	  
	public Integer countByTargetClassKeyAndCompanyLearner(String targetClass, Long targetKey, Long companyLearnersId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerSurvey o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.companyLearners.id = :companyLearnersId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("companyLearnersId", companyLearnersId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
}

