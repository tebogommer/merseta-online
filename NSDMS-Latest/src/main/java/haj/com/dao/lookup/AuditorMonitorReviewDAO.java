package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AreaForImprovement;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AuditorMonitorReviewDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AuditorMonitorReview
	 * 
	 * @author TechFinium
	 * @see AuditorMonitorReview
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReview() throws Exception {
		return (List<AuditorMonitorReview>) super.getList("select o from AuditorMonitorReview o where o.trainingProviderMonitoring is null");
	}

	/**
	 * Get all AuditorMonitorReview between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see AuditorMonitorReview
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> allAuditorMonitorReview(Long from, int noRows) throws Exception {
		String hql = "select o from AuditorMonitorReview o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<AuditorMonitorReview>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see AuditorMonitorReview
	 * @return a {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             global exception
	 */
	public AuditorMonitorReview findByKey(Long id) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (AuditorMonitorReview) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AuditorMonitorReview by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see AuditorMonitorReview
	 * @return a list of {@link haj.com.entity.AuditorMonitorReview}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByName(String description) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByMonitor(TrainingProviderMonitoring trainingProviderMonitoring) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.trainingProviderMonitoring.id = :trainingProviderMonitoringID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("trainingProviderMonitoringID", trainingProviderMonitoring.getId());
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AreaForImprovement> findAreaForImprovement(String targetClass, Long targetKey) throws Exception {
		String hql = "select distinct(o.areaForImprovement) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequired = :evidenceRequired and o.areaForImprovement is not null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("evidenceRequired", YesNoEnum.Yes);
		return (List<AreaForImprovement>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByTargetKeyAndClass(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey order by section asc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByTargetKeyAndClass(String targetClass, Long targetKey, Integer section) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.section = :section order by section asc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("section", section);
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByforProcess(ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.forProcess = :forProcess and o.targetClass is null and o.trainingProviderMonitoring is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forProcess", forProcess);
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByforProcessTP(ConfigDocProcessEnum forProcess) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.forProcess = :forProcess and o.targetClass is null and o.trainingProviderMonitoring is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forProcess", forProcess);
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditorMonitorReview> findByforProcessAndTPApplicationType(ConfigDocProcessEnum forProcess,AccreditationApplicationTypeEnum appType) throws Exception {
		String hql = "select o from AuditorMonitorReview o where o.forProcess = :forProcess and o.tpAccreditationApplicationType = :appType and o.targetClass is null and o.trainingProviderMonitoring is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("forProcess", forProcess);
		parameters.put("appType", appType);
		return (List<AuditorMonitorReview>) super.getList(hql, parameters);
	}

	public int countByTargetKeyAndClassWhereEvidanceIsRequired(String targetClass, Long targetKey) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequired = :evidenceRequired";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("evidenceRequired", YesNoEnum.Yes);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
	public Integer countByTargetClassKeyWhereEvidanceAvalaibleNotPorvided(String targetClass, Long targetKey) throws Exception{
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequired is null";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(String targetClass, Long targetKey) throws Exception{
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequired is null and (o.isNotRelevant is null or o.isNotRelevant = false)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTargetClassKeyWhereOutcomeCommentNotProvided(String targetClass, Long targetKey) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and (o.evidenceRequiredEvaluatorOutcome is null or o.comment is null or o.comment = '')";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTargetClassKeyWhereOutcomeIsTypeAndNoDocProvided(String targetClass, Long targetKey, YesNoEnum evidenceRequiredEvaluatorOutcome) throws Exception{
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequiredEvaluatorOutcome = :evidenceRequiredEvaluatorOutcome and o.id not in (select x.targetKey from Doc x where x.targetClass = :targetClassDoc and x.targetKey is not null)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("targetClassDoc", AuditorMonitorReview.class.getName());
		parameters.put("evidenceRequiredEvaluatorOutcome", evidenceRequiredEvaluatorOutcome);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTargetClassKeyWhereOutcomeIsType(String targetClass, Long targetKey, YesNoEnum evidenceRequiredEvaluatorOutcome) throws Exception {
		String hql = "select count(o) from AuditorMonitorReview o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.evidenceRequiredEvaluatorOutcome = :evidenceRequiredEvaluatorOutcome ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("evidenceRequiredEvaluatorOutcome", evidenceRequiredEvaluatorOutcome);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
}