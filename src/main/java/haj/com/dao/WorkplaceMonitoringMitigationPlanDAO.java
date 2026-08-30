package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.enums.OpenClosedEnum;
import haj.com.entity.enums.YesNoEnum;

public class WorkplaceMonitoringMitigationPlanDAO extends AbstractDAO  {
	
	private String softDeleteHql = " o.softDeleted = false ";

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringMitigationPlan
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlan() throws Exception {
		return (List<WorkplaceMonitoringMitigationPlan>)super.getList("select o from WorkplaceMonitoringMitigationPlan o");
	}

	/**
	 * Get all WorkplaceMonitoringMitigationPlan between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlan(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<WorkplaceMonitoringMitigationPlan>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringMitigationPlan findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringMitigationPlan)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringMitigationPlan by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringMitigationPlan
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringMitigationPlan>)super.getList(hql, parameters);
	}
	
	public WorkplaceMonitoringMitigationPlan findByGrantComplianceSurvey(Long grantComplianceSurveyId) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.discretionaryGrantComplianceLinkSurvey.id = :grantComplianceSurveyId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("grantComplianceSurveyId", grantComplianceSurveyId);
		return (WorkplaceMonitoringMitigationPlan)super.getUniqueResult(hql, parameters);
	}
	
	public WorkplaceMonitoringMitigationPlan findByLearnerSurveyAnswer(Long learnerSurveyAnswerId) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.learnerSurveyAnswerLink.id = :learnerSurveyAnswerId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("learnerSurveyAnswerId", learnerSurveyAnswerId);
		return (WorkplaceMonitoringMitigationPlan)super.getUniqueResult(hql, parameters);
	}
	
	public Integer countMitigationPlanByInformationProvidedAndSiteVisitId(Long workplaceMonitoringSiteVisitId, Boolean infoProvided) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId and o.allInfoProvided = :infoProvided " + softDeleteHql ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("infoProvided", infoProvided);
	    parameters.put("workplaceMonitoringSiteVisitId", workplaceMonitoringSiteVisitId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countNonCompliantAndOpenMitigationPlanBySiteVisitId(Long workplaceMonitoringSiteVisitId, YesNoEnum yesNoValue, OpenClosedEnum openCloseEnum) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId and o.nonComplianceIssue = :yesNoValue and o.openClosedEnum = :openCloseEnum and " + softDeleteHql ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("yesNoValue", yesNoValue);
	    parameters.put("openCloseEnum", openCloseEnum);
	    parameters.put("workplaceMonitoringSiteVisitId", workplaceMonitoringSiteVisitId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countOpenMitigationPlanBySiteVisitId(Long workplaceMonitoringSiteVisitId, OpenClosedEnum openCloseEnum) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId and o.openClosedEnum = :openCloseEnum and " + softDeleteHql ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("openCloseEnum", openCloseEnum);
	    parameters.put("workplaceMonitoringSiteVisitId", workplaceMonitoringSiteVisitId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> findByWorkplaceMonitoringSiteVisitId(Long workplaceMonitoringSiteVisitId) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("workplaceMonitoringSiteVisitId", workplaceMonitoringSiteVisitId);
		return (List<WorkplaceMonitoringMitigationPlan>)super.getList(hql, parameters);
	}
}