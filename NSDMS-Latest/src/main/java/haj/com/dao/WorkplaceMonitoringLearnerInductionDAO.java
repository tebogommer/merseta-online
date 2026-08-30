package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.EmployerQualificationBean;
import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class WorkplaceMonitoringLearnerInductionDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerInduction
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInduction() throws Exception {
		return (List<WorkplaceMonitoringLearnerInduction>)super.getList("select o from WorkplaceMonitoringLearnerInduction o");
	}

	/**
	 * Get all WorkplaceMonitoringLearnerInduction between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInduction(Long from, int noRows) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerInduction o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<WorkplaceMonitoringLearnerInduction>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WorkplaceMonitoringLearnerInduction
 	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
 	 * @throws Exception global exception
 	 */
	public WorkplaceMonitoringLearnerInduction findByKey(Long id) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerInduction o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (WorkplaceMonitoringLearnerInduction)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WorkplaceMonitoringLearnerInduction by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WorkplaceMonitoringLearnerInduction
  	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerInduction> findByName(String description) throws Exception {
	 	String hql = "select o from WorkplaceMonitoringLearnerInduction o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WorkplaceMonitoringLearnerInduction>)super.getList(hql, parameters);
	}
	
	public List<EmployerQualificationBean> allEmployerQualificationByEmployerIdAndLearnerStatus(Long employerId, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		String sql = "select   " + 
				"	targetKey  " + 
				"	, employerId  " + 
				"	, targetClass  " + 
				"from (  " + 
				"	select   " + 
				"		learnership_id as targetKey  " + 
				"		, employer_id as employerId  " + 
				"		, 'haj.com.entity.lookup.Learnership' as targetClass  " + 
				"	from   " + 
				"		company_learners   " + 
				"	where 	  " + 
				"		learnership_id is not null  " + 
				"		and qualification_id is null  " + 
				"		and non_credit_bearing_intervation_title_id is null  " + 
				"		and skills_set_id is null  " + 
				"		and skills_program_id is null  " + 
				"		and learner_status in (:learnerStatusEnumList)  " + 
				"	group by learnership_id, employerId  " + 
				"  " + 
				"	union all  " + 
				"  " + 
				"	select   " + 
				"		qualification_id as targetKey  " + 
				"		, employer_id as employerId  " + 
				"		, 'haj.com.entity.lookup.Qualification' as targetClass  " + 
				"	from   " + 
				"		company_learners   " + 
				"	where 	  " + 
				"		qualification_id is not null  " + 
				"		and learnership_id is null  " + 
				"		and non_credit_bearing_intervation_title_id is null  " + 
				"		and skills_set_id is null  " + 
				"		and skills_program_id is null  " + 
				"		and learner_status in (:learnerStatusEnumList)  " + 
				"	group by qualification_id, employerId  " + 
				"  " + 
				"	union all   " + 
				"  " + 
				"	select   " + 
				"		non_credit_bearing_intervation_title_id as targetKey  " + 
				"		, employer_id as employerId  " + 
				"		, 'haj.com.entity.lookup.NonCreditBearingIntervationTitle' as targetClass  " + 
				"	from   " + 
				"		company_learners   " + 
				"	where 	  " + 
				"		non_credit_bearing_intervation_title_id is not null  " + 
				"		and qualification_id is null  " + 
				"		and learnership_id is null  " + 
				"		and skills_set_id is null  " + 
				"		and skills_program_id is null  " + 
				"		and learner_status in (:learnerStatusEnumList)  " + 
				"	group by non_credit_bearing_intervation_title_id, employerId  " + 
				"  " + 
				"	union all  " + 
				"  " + 
				"	select   " + 
				"		skills_set_id as targetKey  " + 
				"		, employer_id as employerId  " + 
				"		, 'haj.com.entity.lookup.SkillsSet' as targetClass  " + 
				"	from   " + 
				"		company_learners   " + 
				"	where 	  " + 
				"		skills_set_id is not null  " + 
				"		and non_credit_bearing_intervation_title_id is null  " + 
				"		and qualification_id is null  " + 
				"		and learnership_id is null  " + 
				"		and skills_program_id is null  " + 
				"		and learner_status in (:learnerStatusEnumList)  " + 
				"	group by skills_set_id, employerId  " + 
				"  " + 
				"	union all  " + 
				"  " + 
				"	select   " + 
				"		skills_program_id as targetKey  " + 
				"		, employer_id as employerId  " + 
				"		, 'haj.com.entity.lookup.SkillsProgram' as targetClass  " + 
				"	from   " + 
				"		company_learners   " + 
				"	where 	  " + 
				"		skills_program_id is not null  " + 
				"		and	skills_set_id is null  " + 
				"		and non_credit_bearing_intervation_title_id is null  " + 
				"		and qualification_id is null  " + 
				"		and learnership_id is null  " + 
				"		and learner_status in (:learnerStatusEnumList)  " + 
				"	group by skills_program_id, employerId  " + 
				") base  " + 
				"where   " + 
				"	employerId = :employerIdPassed";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("employerIdPassed", employerId);
		Integer count = 1;
		String learnerStatusEnumListString = "";
		for (LearnerStatusEnum learnerStatusEnum : learnerStatusEnumList) {
			if (count == learnerStatusEnumList.size()) {
				learnerStatusEnumListString += learnerStatusEnum.ordinal();
			} else {
				learnerStatusEnumListString += learnerStatusEnum.ordinal() + ",";
			}
			count++;
		}
		parameters.put("learnerStatusEnumList", learnerStatusEnumListString);
		return (List<EmployerQualificationBean>)super.nativeSelectSqlList(sql, EmployerQualificationBean.class, parameters);
	}
	
	/* Checks for existing Items Start */
	
//	unit standard
	public Integer countByClassKeyUnitStandardId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.unitStandard.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	unit standard	
	public Integer countByClassKeyUnitStandardIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.unitStandard.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
//	Skills Set
	public Integer countByClassKeySkillsSetId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.skillsSet.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	Skills Set
	public Integer countByClassKeySkillsSetIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.skillsSet.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
//	qualification
	public Integer countByClassKeyQualificationId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.qualification.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	qualification
	public Integer countByClassKeyQualificationIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.qualification.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
//	skillsProgram
	public Integer countByClassKeySkillsProgramId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.skillsProgram.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	skillsProgram
	public Integer countByClassKeySkillsProgramIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.skillsProgram.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	

//	nonCreditBearingIntervationTitle
	public Integer countByClassKeyNonCreditBearingIntervationTitleId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.nonCreditBearingIntervationTitle.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	nonCreditBearingIntervationTitle
	public Integer countByClassKeyNonCreditBearingIntervationTitleIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.nonCreditBearingIntervationTitle.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}

//	learnership
	public Integer countByClassKeyLearnershipId(String targetClass, Long targetKey, Long objectId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.learnership.id = :objectId " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
//	learnership
	public Integer countByClassKeyLearnershipIdAndNotLearnerInductionId(String targetClass, Long targetKey, Long objectId, Long learnerInductionId) throws Exception {
	 	String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.learnership.id = :objectId and o.id <> :learnerInductionId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("targetClass", targetClass);
	    parameters.put("targetKey", targetKey);
	    parameters.put("objectId", objectId);
	    parameters.put("learnerInductionId", learnerInductionId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/* Checks for existing Items End */
	
}

