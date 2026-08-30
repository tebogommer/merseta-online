package haj.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class CompanyLearnersTradeTestDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersTradeTest
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTest() throws Exception {
		return (List<CompanyLearnersTradeTest>) super.getList("select o from CompanyLearnersTradeTest o");
	}
	
	public int countForSerialNumber() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from CompanyLearnersTradeTest o where o.serialNumber <> null")).intValue();
	}

	/**
	 * Get all CompanyLearnersTradeTest between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see CompanyLearnersTradeTest
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTest(Long from, int noRows) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o ";
		Map<String, Object> parameters = new HashMap<>();
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see CompanyLearnersTradeTest
	 * @return a {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             global exception
	 */
	public CompanyLearnersTradeTest findByKey(Long id) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o left join fetch o.companyLearners cl left join fetch cl.company where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (CompanyLearnersTradeTest) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersTradeTest by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see CompanyLearnersTradeTest
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findByName(String description) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}

	public int generateCompanyLearnerTradeTestSerialNumber() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from CompanyLearnersTradeTest o where o.serialNumber <> null")).intValue();
	}
	
	public int countOpenByArplByUser(Long userId, TradeTestTypeEnum tradeTestType) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :userId and (o.status <> :approvedStatus and o.status <> :rejectedStatus and o.status <> :qualificationObtainedStatus)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId );
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("approvedStatus", ApprovalEnum.Approved);
		parameters.put("rejectedStatus", ApprovalEnum.Rejected);
		parameters.put("qualificationObtainedStatus", ApprovalEnum.QualificationObtained);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findByTradeTypeCompanyLearnerQualificationAndStatus(TradeTestTypeEnum tradeTestType, Long companyLearnersId, Long qualificationId, ApprovalEnum status) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.companyLearners.id = :companyLearnersId and o.qualification.id = :qualificationId order by o.id desc ";
		if(status !=null){
			parameters.put("status", status);
			 hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.companyLearners.id = :companyLearnersId and o.qualification.id = :qualificationId and o.status = :status order by o.id desc ";
		}
		
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("companyLearnersId", companyLearnersId);
		parameters.put("qualificationId", qualificationId);
//		hql += " and o.status = :status ";
//		parameters.put("status", status);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> companyLearnersTradeTestByLearnerId(Users user, TradeTestTypeEnum tradeTestTypeEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :userId and o.status <> :status order by o.createDate desc" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", user.getId());
		parameters.put("tradeTestType", tradeTestTypeEnum);
		parameters.put("status", ApprovalEnum.Approved);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters, 1);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findByTradeTypeLearnerQualificationAndStatus(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId, ApprovalEnum status) throws Exception {
		List<CompanyLearnersTradeTest> l = new ArrayList<CompanyLearnersTradeTest>();
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :learnersId and o.qualification.id = :qualificationId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("learnersId", learnersId);
		parameters.put("qualificationId", qualificationId);
		l = (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
		if (l != null && l.size() > 0) return l;
		else return new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findByTradeTypeLearnerQualification(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId) throws Exception {
		List<CompanyLearnersTradeTest> l = new ArrayList<CompanyLearnersTradeTest>();
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :learnersId and o.qualification.id = :qualificationId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("learnersId", learnersId);
		parameters.put("qualificationId", qualificationId);
		l = (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
		if (l != null && l.size() > 0) return l;
		else return new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public CompanyLearnersTradeTest findLastByTradeTypeLearnerQualification(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId) throws Exception {
		List<CompanyLearnersTradeTest> l = new ArrayList<CompanyLearnersTradeTest>();
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :learnersId and o.qualification.id = :qualificationId order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("learnersId", learnersId);
		parameters.put("qualificationId", qualificationId);
		CompanyLearnersTradeTest companyLearnersTradeTest=null;
		l = (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
		if (l != null && l.size() > 0) {
			companyLearnersTradeTest=l.get(0);
		}
		return companyLearnersTradeTest;
	}
	
	@SuppressWarnings("unchecked")
	public CompanyLearnersTradeTest findLastByTradeTypeLearnerQualificationAndDesignatedTradeLevel(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId, Long designatedTradeLevelId) throws Exception {
		List<CompanyLearnersTradeTest> l = new ArrayList<CompanyLearnersTradeTest>();
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :learnersId and o.qualification.id = :qualificationId  and o.designatedTradeLevel.id = :designatedTradeLevelId order by o.createDate desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("learnersId", learnersId);
		parameters.put("qualificationId", qualificationId);
		parameters.put("designatedTradeLevelId", designatedTradeLevelId);
		CompanyLearnersTradeTest companyLearnersTradeTest=null;
		l = (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
		if (l != null && l.size() > 0) {
			companyLearnersTradeTest=l.get(0);
		}
		return companyLearnersTradeTest;
	}
	

	public Integer countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(TradeTestTypeEnum tradeTestType, Long companyLearnerId, Long designatedTradeLevelId, CompetenceEnum competenceEnum, ApprovalEnum approvalEnum) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.companyLearners.id = :companyLearnerId and o.designatedTradeLevel.id = :designatedTradeLevelId and o.competenceEnum = :competenceEnum and o.status = :approvalEnum";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("companyLearnerId", companyLearnerId);
		parameters.put("designatedTradeLevelId", designatedTradeLevelId);
		parameters.put("competenceEnum", competenceEnum);
		parameters.put("approvalEnum", approvalEnum);
		return  ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByTypeLearnerIdQualificationAndStatus(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId, ApprovalEnum status) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :learnersId and o.qualification.id = :qualificationId and o.status = :status ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestType", tradeTestType);
		parameters.put("learnersId", learnersId);
		parameters.put("qualificationId", qualificationId);
		parameters.put("status", status);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, Boolean onhold) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.onHold = :onhold and o.aprlProgress = :aprlProgressEnum" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		parameters.put("onhold", onhold);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, Boolean onhold) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.onHold = :onhold and o.aprlProgress = :aprlProgressEnum" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		parameters.put("onhold", onhold);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}
	
	// ApprovalEnum.QualificationObtained
	public int countCompanyLearnersTradeTestByTypeStatusQualificationAndLearnerId(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalEnum and o.qualification.id = :qualificationId and o.learner.id = :learnerId " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("approvalEnum", approvalEnum);
		parameters.put("qualificationId", qualificationId);
		parameters.put("learnerId", learnerId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId, CompetenceEnum competence) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalEnum and o.qualification.id = :qualificationId and o.learner.id = :learnerId and o.competenceEnum = :competence " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("approvalEnum", approvalEnum);
		parameters.put("qualificationId", qualificationId);
		parameters.put("learnerId", learnerId);
		parameters.put("competence", competence);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId, CompetenceEnum competence) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalEnum and o.qualification.id = :qualificationId and o.learner.id = :learnerId and o.competenceEnum = :competence order by o.id desc" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("approvalEnum", approvalEnum);
		parameters.put("qualificationId", qualificationId);
		parameters.put("learnerId", learnerId);
		parameters.put("competence", competence);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	
		parameters.put("testDateProvided", testDateProvided);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public int countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum,  TradeTestProgressEnum tradeTestProgress, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgress and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgress", tradeTestProgress);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	
		parameters.put("testDateProvided", testDateProvided);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	
		parameters.put("testDateProvided", testDateProvided);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided and DATE(o.dateOfTest) > DATE(:todayDate)" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		parameters.put("todayDate", todayDate);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided and DATE(o.dateOfTest) > DATE(:todayDate)" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		parameters.put("todayDate", todayDate);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided and DATE(o.dateOfTestToDate) < DATE(:todayDate)" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		parameters.put("todayDate", todayDate);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided and DATE(o.dateOfTest) > DATE(:todayDate)" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		parameters.put("todayDate", todayDate);
		return (List<CompanyLearnersTradeTest>) super.getList(hql, parameters);
	}
	
	/*
	 * ARPL Reporting Start
	 */
	// by status
	public int countArplTradeTestReportingByStatus(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and on hold
	public int countArplTradeTestReportingByStatusOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean onHold) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.onHold = :onHold" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		parameters.put("onHold", onHold);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and approval status
	public int countArplTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// awaiting trade test center to submit after test dates provided
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAwaitingResults(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// awaiting trade test center to submit after test dates provided
	public int countArplWithTestCenterAfterSubmission(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// true
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and approval status
	public int countArplTradeTestReportingByApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and approval status and NAMB date not provided
	public int countArplTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("aprlProgressEnum", aprlProgressEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by approval status and certificate collected
	public int countArplTradeTestReportingByStatusAndCertificateCollected(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, Boolean certificateCollected) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.certificateCollected = :certificateCollected " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("certificateCollected", certificateCollected);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by approval status and collection
	public int countArplTradeTestReportingByStatusAndCollectionType(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, CollectionEnum collectionEnum) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.collectionEnum = :collectionEnum " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("collectionEnum", collectionEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	/*
	 * ARPL Reporting End
	 */
	
	/*
	 * Trade Test Reporting Start
	 * private TradeTestProgressEnum tradeTestProgress;
	 */
	// by status
	public int countTradeTestReportingByStatus(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// awaiting trade test center to submit after test dates provided
	public int countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedAwaitingResults(TradeTestTypeEnum tradeTestTypeEnum,  TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgressEnum", tradeTestProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// false
		parameters.put("testDateProvided", testDateProvided); // true
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// awaiting trade test center to submit after test dates provided
	public int countTradeTestsWithTestCenterAfterSubmission(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgressEnum", tradeTestProgressEnum);	
		parameters.put("testCenterSubmitted", testCenterSubmitted);	// true
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and approval status
	public int countTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	// by status and approval status and NAMB date not provided
	public int countTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null" ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		parameters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		parameters.put("approvalStatus", approvalStatus);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	/*
	 * Trade Test Reporting End
	 */
	
}