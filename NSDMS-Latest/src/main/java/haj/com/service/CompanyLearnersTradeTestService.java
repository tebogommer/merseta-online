package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.ModularTrainingBean;
import haj.com.bean.WorkplaceBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersTradeTestDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.CollectDetail;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.NambDecisionHistory;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Province;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TradeTestTaskResult;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.ArplDocRequirements;
import haj.com.entity.enums.CollectionEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.DesignatedTradeLevelItems;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.Town;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DesignatedTradeLevelItemsService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyApprenticeshipTradeTestService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.QualificationToolKitService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.TownService;
import haj.com.utils.GenericUtility;
import haj.com.validators.exports.services.CompanyLearnerValidationService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CompanyLearnersTradeTestService extends AbstractService {
	
	/** The dao. */
	private CompanyLearnersTradeTestDAO dao = new CompanyLearnersTradeTestDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private UsersService usersService = null;
	CompanyUsersService companyUsersService = new CompanyUsersService();
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private NambDecisionHistoryService decisionHistoryService = new NambDecisionHistoryService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private CompanyService companyService;
	private EtqaService etqaService = new EtqaService();
	private ProvinceService provinceService = new ProvinceService();
	
	private TrainingProviderApplicationService trainingProviderApplicationService;
	private AssessorModeratorApplicationService assessorModeratorApplicationService;

	/** Instance of service level */
	private static CompanyLearnersTradeTestService companyLearnersTradeTestService;
	public static synchronized CompanyLearnersTradeTestService instance() {
		if (companyLearnersTradeTestService == null) {
			companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
		}
		return companyLearnersTradeTestService;
	}
	
	/**
	 * Populates additional information against CompanyLearnersTradeTest
	 * refer to method: populateAdditionalInformation
	 * @param companyLearnersTradeTestList
	 * @return companyLearnersTradeTestList
	 * @throws Exception
	 */
	public List<CompanyLearnersTradeTest> populateAdditionalInformationList(List<CompanyLearnersTradeTest> companyLearnersTradeTestList) throws Exception{
		for (CompanyLearnersTradeTest companyLearnersTradeTest : companyLearnersTradeTestList) {
			populateAdditionalInformation(companyLearnersTradeTest);
		}
		return companyLearnersTradeTestList;
	}
	
	public void populateUserDisabilityList(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		List<UsersDisability> usersDisability = usersDisabilityService.findByTargetClassAndKey(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName());
		if (usersDisability.size() != 0) {
			companyLearnersTradeTest.setUsersDisabilityList(usersDisability);
		} else {
			companyLearnersTradeTest.setUsersDisabilityList(new ArrayList<>());
		}
	}
	
	/**
	 * Populates additional information against CompanyLearnersTradeTest
	 * @param companyLearnersTradeTest
	 * @return companyLearnersTradeTest
	 * @throws Exception
	 */
	private CompanyLearnersTradeTest populateAdditionalInformation(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception{
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
			companyLearnersTradeTest.setCompanyLearners(companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId()));
		}
		if (companyLearnersTradeTest.getId() != null) {
			populateUserDisabilityList(companyLearnersTradeTest);
		}
		if (companyLearnersTradeTest.getLearner() != null && companyLearnersTradeTest.getLearner().getId() != null) {
			if (companyLearnersTradeTest.getLearner().getResidentialAddress() != null && companyLearnersTradeTest.getLearner().getResidentialAddress().getId() != null) {
				companyLearnersTradeTest.getLearner().setResidentialAddress(AddressService.instance().findByKey(companyLearnersTradeTest.getLearner().getResidentialAddress().getId()));
			} else {
				companyLearnersTradeTest.getLearner().setResidentialAddress(new Address());
			}
			if (companyLearnersTradeTest.getLearner().getPostalAddress() != null && companyLearnersTradeTest.getLearner().getPostalAddress().getId() != null) {
				companyLearnersTradeTest.getLearner().setPostalAddress(AddressService.instance().findByKey(companyLearnersTradeTest.getLearner().getPostalAddress().getId()));
			} else {
				companyLearnersTradeTest.getLearner().setPostalAddress(new Address());
			}
			if (usersService == null) {
				usersService = new UsersService();
			}
			usersService.identifyFieldAlteration(companyLearnersTradeTest.getLearner());
		}
		if (companyLearnersTradeTest.getDesignatedTradeLevel() != null) {
			companyLearnersTradeTest.setDesignatedTradeLevel(DesignatedTradeLevelService.instance().findByKey(companyLearnersTradeTest.getDesignatedTradeLevel().getId()));
		}
		if (companyLearnersTradeTest.getCompanyEmployer() != null && companyLearnersTradeTest.getCompanyEmployer().getId() != null) {
			populateCompanyAddress(companyLearnersTradeTest.getCompanyEmployer());
		}
		if (companyLearnersTradeTest.getCompanyProvider() != null && companyLearnersTradeTest.getCompanyProvider().getId() != null) {
			populateCompanyAddress(companyLearnersTradeTest.getCompanyProvider());
		}
		if (companyLearnersTradeTest.getPreferredTrainingCenter() != null && companyLearnersTradeTest.getPreferredTrainingCenter().getId() != null) {
			populateCompanyAddress(companyLearnersTradeTest.getPreferredTrainingCenter());
		}
		if (companyLearnersTradeTest.getPreviousTrainingCenter() != null && companyLearnersTradeTest.getPreviousTrainingCenter().getId() != null) {
			populateCompanyAddress(companyLearnersTradeTest.getPreviousTrainingCenter());
		}
		if (companyLearnersTradeTest.getClientServiceAdminUser() != null && companyLearnersTradeTest.getClientServiceAdminUser().getId() != null) {
			if (usersService == null) {
				usersService = new UsersService();
			}
			companyLearnersTradeTest.setClientServiceAdminUser(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		}
		if (companyLearnersTradeTest.getAssessorEtqa() != null && companyLearnersTradeTest.getAssessorEtqa().getId() != null) {
			companyLearnersTradeTest.setAssessorEtqa(etqaService.findByKey(companyLearnersTradeTest.getAssessorEtqa().getId()));
		}
		if (companyLearnersTradeTest.getModeratorEtqa() != null && companyLearnersTradeTest.getModeratorEtqa().getId() != null) {
			companyLearnersTradeTest.setModeratorEtqa(etqaService.findByKey(companyLearnersTradeTest.getModeratorEtqa().getId()));
		}
		if (companyLearnersTradeTest.getTrainingProviderApplication() != null && companyLearnersTradeTest.getTrainingProviderApplication().getId() != null) {
			if (trainingProviderApplicationService == null) {
				trainingProviderApplicationService = new TrainingProviderApplicationService();
			}
			companyLearnersTradeTest.setTrainingProviderApplication(trainingProviderApplicationService.findByKey(companyLearnersTradeTest.getTrainingProviderApplication().getId()));
		}
		
		if (companyLearnersTradeTest.getAssessorApplication() != null && companyLearnersTradeTest.getAssessorApplication().getId() != null) {
			if (assessorModeratorApplicationService == null) {
				assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			}
			companyLearnersTradeTest.setAssessorApplication(assessorModeratorApplicationService.findByKey(companyLearnersTradeTest.getAssessorApplication().getId()));
		}
		
		if (companyLearnersTradeTest.getModeratorApplication() != null && companyLearnersTradeTest.getModeratorApplication().getId() != null) {
			if (assessorModeratorApplicationService == null) {
				assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			}
			companyLearnersTradeTest.setModeratorApplication(assessorModeratorApplicationService.findByKey(companyLearnersTradeTest.getModeratorApplication().getId()));
		}
		return companyLearnersTradeTest;
	}
	
	private void populateCompanyAddress(Company company) throws Exception{
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
		} 
		if (company.getPostalAddress() != null && company.getPostalAddress().getId() != null) {
			company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
		}
	}
	
	public List<CompanyLearnersTradeTest> findByTradeTypeCompanyLearnerQualificationAndStatus(TradeTestTypeEnum tradeTestType, CompanyLearners companyLearners, Qualification qualification, ApprovalEnum status) throws Exception {
		return dao.findByTradeTypeCompanyLearnerQualificationAndStatus(tradeTestType, companyLearners.getId(), qualification.getId(), status);
	}
	
	public List<CompanyLearnersTradeTest> findByTradeTypeLearnerQualification(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId) throws Exception {
		return dao.findByTradeTypeLearnerQualification(tradeTestType, learnersId, qualificationId);
	}
	
	public Integer countByTypeLearnerIdQualificationAndStatus(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId, ApprovalEnum status) throws Exception {
		return dao.countByTypeLearnerIdQualificationAndStatus(tradeTestType, learnersId, qualificationId, status);
	}

	/**
	 * Get all CompanyLearnersTradeTest
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTest() throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTradeTest());
	}

	/**
	 * Create or update CompanyLearnersTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTradeTest
	 */
	public void create(CompanyLearnersTradeTest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}
	

	/**
	 * Update CompanyLearnersTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTradeTest
	 */
	public void update(CompanyLearnersTradeTest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersTradeTest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTradeTest
	 */
	public void delete(CompanyLearnersTradeTest entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTradeTest
	 */
	public CompanyLearnersTradeTest findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	public CompanyLearnersTradeTest populateDocsByTargetClassAndKey(CompanyLearnersTradeTest entity) throws Exception{ 
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		return entity;
	}
	
	public void populateDocsByProcessForArplNambApproval(CompanyLearnersTradeTest entity) throws Exception{
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ARPLTradeTestNambApproval, CompanyUserTypeEnum.User);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
	}
	
	/*
	 * 
	 * On reg:
	 * 1) If new learner ( no user id yet ) do not run 
	 * 2) If existing user run all three (boolean true)
	 * 
	 * On company learner level:
	 * 1) Run checks but boolean must be false 
	 * 
	 */
	public void validateUserAllowedArpl(Qualification qualification, Users learner, boolean checkCompanyLearners) throws Exception{
		// check not underway with an ARPL
		if (countOpenByArplByUser(learner, TradeTestTypeEnum.ARPL) != 0) {
			throw new Exception("Learner cueently underway with an ARPL. Please complete open ARPL with learner before proceeding.");
		}
		// Validation check against ARPL - Qualification not already obtained by learner
		if (countCompanyLearnersTradeTestByTypeStatusQualificationAndLearnerId(TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, qualification.getId(), learner.getId()) != 0) {
			throw new Exception("Learner has already obtained qualification under ARPL. Please select a different qualification to proceed.");
		}
		if (checkCompanyLearners) {
			// Validation if learner and qualification not underway or obtained 
			if (companyLearnersService.countCompanyLearnersByLearnerIdQualificationAndNotLearnerStatus(learner.getId(), qualification.getId(), LearnerStatusEnum.Terminated) != 0) {
				throw new Exception("Learner is currently underway / obtained qualification through the NSDMS learner module. Please select a different qualification to proceed.");
			}
		}
	}
	
	public void validiateEmployerProviderAllowedArpl(Qualification qualification, Users learner, CompanyLearners companyLearners) throws Exception{
		// check not underway with an ARPL
		if (countOpenByArplByUser(learner, TradeTestTypeEnum.ARPL) != 0) {
			throw new Exception("Learner cueently underway with an ARPL. Please complete open ARPL with learner before proceeding.");
		}
		// Validation check against ARPL - Qualification not already obtained by learner
		if (countCompanyLearnersTradeTestByTypeStatusQualificationAndLearnerId(TradeTestTypeEnum.ARPL, ApprovalEnum.QualificationObtained, qualification.getId(), learner.getId()) != 0) {
			throw new Exception("Learner has already obtained qualification under ARPL. Please select a different qualification to proceed.");
		}
		if (companyLearners == null || companyLearners.getId() == null) {
			// Validation if learner and qualification not underway or obtained 
			if (companyLearnersService.countCompanyLearnersByLearnerIdQualificationAndNotLearnerStatus(learner.getId(), qualification.getId(), LearnerStatusEnum.Terminated) != 0) {
				throw new Exception("Learner is currently underway / obtained qualification through the NSDMS learner module. Please select a different qualification to proceed.");
			}
		}
	}
	
	public CompanyLearnersTradeTest calculateArplAttempts(CompanyLearnersTradeTest companyLearnersTradeTest, Qualification qualification, Users learner) throws Exception{
		int previousFailedArplEntries = countCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum.ARPL, ApprovalEnum.Completed, qualification.getId(), learner.getId(), CompetenceEnum.NotYetCompetent);
		if (previousFailedArplEntries != 0) {
			companyLearnersTradeTest.setAttemptNumber(previousFailedArplEntries);
			List<CompanyLearnersTradeTest> attemptsList = findCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum.ARPL, ApprovalEnum.Completed, qualification.getId(), learner.getId(), CompetenceEnum.NotYetCompetent);
			CompanyLearnersTradeTest lastAttempt = attemptsList.get(0);
			companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
			companyLearnersTradeTest.setPreviousAttemptDate((lastAttempt.getDateOfTest() != null ? lastAttempt.getDateOfTest() : null));
			companyLearnersTradeTest.setPreviousTrainingCenter((lastAttempt.getPreferredTrainingCenter() != null ? lastAttempt.getPreferredTrainingCenter() : null));
		} else {
			companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.No);
			companyLearnersTradeTest.setAttemptNumber(null);
		}
		return companyLearnersTradeTest;
	}
	
	public CompanyLearnersTradeTest calculateTradeTestAttempts(CompanyLearnersTradeTest companyLearnersTradeTest, Qualification qualification, Users learner, boolean cmbtQualification) throws Exception{
		if (cmbtQualification) {
			
			
			
		} else {
			int previousFailedTradeTestEntries = countCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum.TRADE_TEST, ApprovalEnum.NotCompetent, qualification.getId(), learner.getId(), CompetenceEnum.NotYetCompetent);
			if (previousFailedTradeTestEntries != 0) {
				companyLearnersTradeTest.setAttemptNumber(previousFailedTradeTestEntries);
				List<CompanyLearnersTradeTest> attemptsList = findCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum.TRADE_TEST, ApprovalEnum.NotCompetent, qualification.getId(), learner.getId(), CompetenceEnum.NotYetCompetent);
				CompanyLearnersTradeTest lastAttempt = attemptsList.get(0);
				companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
				companyLearnersTradeTest.setPreviousAttemptDate((lastAttempt.getDateOfTest() != null ? lastAttempt.getDateOfTest() : null));
				companyLearnersTradeTest.setPreviousTrainingCenter((lastAttempt.getPreferredTrainingCenter() != null ? lastAttempt.getPreferredTrainingCenter() : null));
			} else {
				companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.No);
				companyLearnersTradeTest.setAttemptNumber(null);
			}
		}
		return companyLearnersTradeTest;
	}
	
	public int countCompanyLearnersTradeTestByTypeStatusQualificationAndLearnerId(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeStatusQualificationAndLearnerId(tradeTestTypeEnum, approvalEnum, qualificationId, learnerId);
	}
	
	public int countCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId, CompetenceEnum competence) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(tradeTestTypeEnum, approvalEnum, qualificationId, learnerId, competence);
	}
	
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalEnum, Long qualificationId, Long learnerId, CompetenceEnum competence) throws Exception {
		return dao.findCompanyLearnersTradeTestByTypeStatusQualificationLearnerIdAndCompetence(tradeTestTypeEnum, approvalEnum, qualificationId, learnerId, competence);
	}
	
	public void prepareNewRegistration(CompanyLearnersTradeTest entity, boolean firstInProcess) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.TradeTestApplication));
			List<ConfigDoc> l = new ArrayList<>();
			if (firstInProcess) {
				l = configDocService.findByProcessNotUploadedForReg(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.TradeTestApplication, CompanyUserTypeEnum.User);
			} else {
				l = configDocService.findByProcessNotUploadedNotForReg(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.TradeTestApplication, CompanyUserTypeEnum.User);
			}
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.TradeTestApplication, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareDocumentByProcess(ConfigDocProcessEnum configDocProcess, CompanyLearnersTradeTest entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
			
			if(entity.getDocs() != null && entity.getDocs().size()>0) {
				for(Doc doc:entity.getDocs()) {
					if(doc != null && doc.getConfigDoc() !=null && doc.getConfigDoc().getProcessRoles() !=null && processRoles != null && doc.getConfigDoc().getProcessRoles().equals(processRoles)) {
						doc.setEnableReupload(true);
					}
				}
			}
			
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareDocuments(CompanyLearnersTradeTest entity, boolean firstInProcess,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcessEnum));
			List<ConfigDoc> l = new ArrayList<>();
			if (firstInProcess) {
				l = configDocService.findByProcessNotUploadedForReg(entity.getClass().getName(), entity.getId(),configDocProcessEnum, CompanyUserTypeEnum.User);
			} else {
				l = configDocService.findByProcessNotUploadedNotForReg(entity.getClass().getName(), entity.getId(), configDocProcessEnum, CompanyUserTypeEnum.User);
			}
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcessEnum, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Find CompanyLearnersTradeTest by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTradeTest
	 */
	public List<CompanyLearnersTradeTest> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load CompanyLearnersTradeTest
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTest(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTradeTest(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of CompanyLearnersTradeTest for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersTradeTest
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersTradeTest.class);
	}

	/**
	 * Lazy load CompanyLearnersTradeTest with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersTradeTest class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.CompanyLearnersTradeTest} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTest(Class<CompanyLearnersTradeTest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	/**
	 * Get count of CompanyLearnersTradeTest for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersTradeTest class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersTradeTest entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersTradeTest> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTestType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType";
		filters.put("tradeTestType", tradeTestTypeEnum);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompanyLearnersTradeTestType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTestByLearnerId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user, TradeTestTypeEnum tradeTestTypeEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :userId";
		filters.put("userId", user.getId());
		filters.put("tradeTestType", tradeTestTypeEnum);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompanyLearnersTradeTestByLearnerId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.learner.id = :userId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allLegacyCompanyLearnersTradeTest(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.legacysecttwentyeight is not null";
		filters.put("tradeTestType", tradeTestTypeEnum);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllLegacyCompanyLearnersTradeTest(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.legacysecttwentyeight is not null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTestByStatusAndType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum status) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.status = :status";
		filters.put("tradeTestType", tradeTestTypeEnum);
		filters.put("status", status);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompanyLearnersTradeTestByStatusAndType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.status = :status";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allCompanyLearnersTradeTestByWithTypeAndOnHold(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, Boolean onhold) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.onHold = :onhold and o.aprlProgress = :aprlProgressEnum";
		filters.put("tradeTestType", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);
		filters.put("onhold", onhold);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompanyLearnersTradeTestByWithTypeAndOnHold(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestType and o.onHold = :onhold and o.aprlProgress = :aprlProgressEnum";
		return dao.countWhere(filters, hql);
	}
	
	public int countOpenByArplByUser(Users user, TradeTestTypeEnum tradeTestType) throws Exception {
		return dao.countOpenByArplByUser(user.getId(), tradeTestType);
	}

	public void completeWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument() && doc.getConfigDoc().getReqImmediate() == tasks.getFirstInProcess()) {
					error += "Please ensure all documents are uplaoded";
					break;
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		
		this.dao.update(companyLearners);
		
		locateNextInProcessRegionUsers(user, tasks, companyLearners, false);
		
		//Sending acknowledge 
		if(tasks.getFirstInProcess())
		{
			/*
			 * Upon receipt of the Trade Test application and supporting documentation by
			 * Client Services (Region), a notification email will be sent to the Learner to
			 * acknowledge receipt.
			 */
			ARPLLearnerAcknowledge(companyLearners.getCompanyLearners().getUser());
		}
	}

	public void recommendWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Recommended);
		dao.update(companyLearners);
		locateNextInProcessRegionUsers(user, tasks, companyLearners, false);
//		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		ARPLLearnerEvaluationNotification(companyLearners.getCompanyLearners().getUser(), companyLearners);
	}

	public void approveWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(companyLearners);
		locateNextInProcessRegionUsers(user, tasks, companyLearners, false);
	}

	public void rejectWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks, List<RejectReasons> selectedRejectReason) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		if(tasks.getProcessRole().getRolePermission() ==UserPermissionEnum.FinalApproval)
		{
			//Sending unsuccessful application email notification.
			unsuccessfulApplicationNotification(companyLearners.getCompanyLearners().getUser());
		}
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), companyLearners.getClass().getName(), selectedRejectReason, user, tasks.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		
	}

	public void finalApproveWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.getCompanyLearners().setStatus(ApprovalEnum.Approved);
		companyLearners.getCompanyLearners().setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setSerialNumber(generateCompanyLearnerTradeTestSerialNumber());
		dao.update(companyLearners);
		dao.update(companyLearners.getCompanyLearners());
		locateNextInProcessRegionUsers(user, tasks, companyLearners, false);
//		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		//Sending successful application email notification.
		successfulApplicationNotification(companyLearners.getCompanyLearners().getUser(), companyLearners);
	}

	public void finalRejectWorkflow(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		TasksService.instance().completeTask(tasks);
	}
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearnersTradeTest entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null && processRoles != null ) {
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void ARPLLearnerAcknowledge (Users user) throws Exception {
		
		String subject = "Artisan Recognition of Prior Learning".toUpperCase();
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that all Trade Test Supporting Documentation have been uploaded and submited "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public void ARPLLearnerEvaluationNotification(Users user, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		List<Users>users = new ArrayList<>();
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(companyLearnersTradeTest.getPreferredTrainingCenter().getId());
		
		users.add(employerContactPerson);
		
		Users employerContactPerson2 = companyUsersService.findCompanyContactPerson(companyLearnersTradeTest.getCompanyLearners().getId());
		users.add(employerContactPerson2);
		
		String subject = "Artisan Recognition of Prior Learning".toUpperCase();
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that your application has been submitted for evaluation"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public void unsuccessfulApplicationNotification(Users user) throws Exception {
		
		String subject = "Artisan Recognition of Prior Learning".toUpperCase();
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that your application is unsuccessful "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public void successfulApplicationNotification(Users user, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {		
		
		String subject = "Artisan Recognition of Prior Learning".toUpperCase();
		String title="";
		if(user.getTitle() !=null)
		{
			title=user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getFirstName()+" "+user.getLastName()+",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "We write to inform you that your application is successful "
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "<b>The merSETA Team</b>"
				+ "</p>";
		
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public String generateCompanyLearnerTradeTestSerialNumber() throws Exception{
		/*
		 * First registered learner: 17/1000000/19
		Second registered learner: 17/1000001/19
		 */
		String regNumber = "";
		String mersetaCode = "17/"; // merseta code
		int number = 1000000 + (dao.generateCompanyLearnerTradeTestSerialNumber() + 1); // number for company learner
		String year = "/18";  // year [build in year identifier for future proof]
		regNumber = mersetaCode + number + year;
		return regNumber;
	}
	
	public void locateNextInProcessRegionUsers(Users user, Tasks tasks, CompanyLearnersTradeTest companyLearners, boolean createIndividualTasks) throws Exception{
		List<Users> usersByRegion = new ArrayList<>();
		if (tasks.getProcessRole() != null && tasks.getProcessRole().getNextTaskRole() != null) {
			ProcessRolesService processRolesService = new ProcessRolesService();
			ProcessRoles pr = processRolesService.findByKey(tasks.getProcessRole().getId());
			if (pr != null && pr.getNextTaskRole() != null) {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				Address address = null;
				if (tasks.getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
					// if initiator is the learner as well
					if (companyLearners.getLearner().getId().equals(companyLearners.getCreateUser().getId())) {
						address = AddressService.instance().findByKey(companyLearners.getLearner().getResidentialAddress().getId());
					} else if (companyLearners.getCompanyEmployer() != null && companyLearners.getCompanyEmployer().getId() != null) {
						// Employer Address
						address = AddressService.instance().findByKey(companyLearners.getCompanyEmployer().getResidentialAddress().getId());
					}else {
						// fail safe: learner address
						address = AddressService.instance().findByKey(companyLearners.getLearner().getResidentialAddress().getId());
					}
				} else {
					if (companyLearners.getCompanyLearners() != null && companyLearners.getCompanyLearners().getEmployer() != null && companyLearners.getCompanyLearners().getEmployer().getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(companyLearners.getCompanyLearners().getEmployer().getResidentialAddress().getId());
					}
				}
				if (address != null) {
					usersByRegion = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(pr.getNextTaskRole().getId()));
				}	
			}
		}
		if (usersByRegion.size() == 0) {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, createIndividualTasks);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersByRegion, createIndividualTasks);
		}
	}
	
	public void requestARPLTradeTestApplication(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		CompanyLearners companyLearners = null;
		if (companyLearnersTradeTest.getCompanyLearners() != null) {
			companyLearners = companyLearnersTradeTest.getCompanyLearners();
			companyLearners.setStatus(ApprovalEnum.PendingApproval);
			companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTradeTest);
		}
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		if (companyLearnersTradeTest.getAttemptNumber() != null) {
			Integer num = companyLearnersTradeTest.getAttemptNumber();
			if (num >= 3) {
				throw new Exception("You have reached maximum number of attemts");
			}
			companyLearnersTradeTest.setAttemptNumber(num + 1);
		} else {
			companyLearnersTradeTest.setAttemptNumber(1);
		}
		if (companyLearners != null) {
			this.dao.update(companyLearners);
		}
		this.dao.create(companyLearnersTradeTest);
		
		List<Users> usersAssignedToList = new ArrayList<>();
		
		// create user is initiator
		usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
		String desc= "test---";
		TasksService.instance().findFirstInProcessAndCreateTask("", u, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.ARPLTradeTestApplication, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), usersAssignedToList);
		
		List<Users> emailRecivers = new ArrayList<>();
		emailRecivers.add(companyLearnersTradeTest.getLearner());
		if (!companyLearnersTradeTest.getLearner().getId().equals(companyLearnersTradeTest.getCreateUser().getId())) {
			emailRecivers.add(companyLearnersTradeTest.getCreateUser());
		}
		
		generateAndSendLPMFM009(companyLearnersTradeTest, companyLearnersTradeTest.getLearner(), emailRecivers);
	}
	
	public void requestARPLTradeTestApplicationForLegacy(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		CompanyLearners companyLearners = null;
		if (companyLearnersTradeTest.getCompanyLearners() != null) {
			companyLearners = companyLearnersTradeTest.getCompanyLearners();
			//companyLearners.setStatus(ApprovalEnum.PendingApproval);
			companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTradeTest);
		}
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaOne);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		if (companyLearnersTradeTest.getAttemptNumber() != null) {
			Integer num = companyLearnersTradeTest.getAttemptNumber();
			if (num >= 3) {
				throw new Exception("You have reached maximum number of attempts");
			}
			companyLearnersTradeTest.setAttemptNumber(num + 1);
		} else {
			
			/*LegacyApprenticeshipService legacyApprenticeshipService = new LegacyApprenticeshipService();
			LegacyApprenticeshipTradeTestService legacyApprenticeshipTradeTestService = new LegacyApprenticeshipTradeTestService();
			LegacyApprenticeship legacyApprenticeship = legacyApprenticeshipService.findByKey(companyLearnersTradeTest.getCompanyLearners().getLegacyId());
			List<LegacyApprenticeshipTradeTest>list = legacyApprenticeshipTradeTestService.findByLegacyApprenticeship(legacyApprenticeship);
			
			if(list.size() > 3) {
				throw new Exception("You have reached maximum number of attemts");
			}else {
				companyLearnersTradeTest.setAttemptNumber(list.size());
			}*/
		}
		if (companyLearners != null) {
			this.dao.update(companyLearners);
		}
		companyLearnersTradeTest.setLearnerAgreement(YesNoEnum.Yes);
		if(companyLearnersTradeTest.getId() != null) {
			this.dao.update(companyLearnersTradeTest);
		}else {
			this.dao.create(companyLearnersTradeTest);
		}
		
		
		List<Users> usersAssignedToList = new ArrayList<>();
		
		// create user is initiator
		usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
		String desc= "test---";
		TasksService.instance().findFirstInProcessAndCreateTask("", u, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.ARPLTradeTestApplicationLegacy, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), usersAssignedToList);
		
	}

	public void sendLPMFM008AndLPMFM009_Old(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		UsersService usersService = new UsersService();
		CompanyService companyService = new CompanyService();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest()==YesNoEnum.Yes) {
			attemptedTradeTest = true;
		}
		boolean flc = false;
		if (companyLearnersTradeTest.getFlc()==YesNoEnum.Yes) {
			flc = true;
		}
		/* Trade Level Test application: for Artisan Recognition of Prior Learning */
		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);
		params.put("ofo_codes", ofoCodes);
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-009-TradeTestApplicationForm.jasper", params);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);
		/* AMIC LEARNER/CANDIDATE APPLICATION FORM FOR IMMMEDIATE RECOGNITION TEST */
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		beanAttachment = new AttachmentBean();
		params.put("employer_contact_person", employerContactPerson);
		params.put("learner", learner);
		params.put("employer", employer);
		bites = JasperService.instance().convertJasperReportToByte("LPM-FM-008-AMICApplicationForImmediateRecognitionTest.jasper", params);
		fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Immediate_Recognition_Test_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		String subject = "TRADE TEST APPLICATION FORMS";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Trade Test Application forms" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), subject, mssg, attachmentBeans, null);
	}
	

	public void downloadLPMFM008(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		/*CompanyService companyService = new CompanyService();
		if(companyLearnersTradeTest.getEmployer()==null){throw new Exception("Please provide emploer's details");}
		Company employer = companyService.findByKey(companyLearnersTradeTest.getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		if(employerContactPerson==null){throw new Exception("Please provide emploer's contact details");}*/
		Company employer = new Company();
		Users employerContactPerson = new Users();
		prepareEmpoyerAndContactPerson(companyLearnersTradeTest, companyLearnersTradeTest.getEmployer(), employer, employerContactPerson);
		
		String trade="N/A";
		if(companyLearnersTradeTest.getQualification() !=null && companyLearnersTradeTest.getQualification().getDesignatedTrade() !=null)
		{
			trade=companyLearnersTradeTest.getQualification().getDesignatedTrade().getDescription();
		}
		UsersService usersService = new UsersService();
		Users learner = usersService.findByKey(u.getId());
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("employer_contact_person", employerContactPerson);
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("trade", trade);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Immediate_Recognition_Test_Form.pdf";
		JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-008-AMICApplicationForImmediateRecognitionTest.jasper", params,fileName);
		
	}
	
	public void downloadLPMFM009(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Company employer = new Company();
		Users employerContactPerson = new Users();
		prepareEmpoyerAndContactPerson(companyLearnersTradeTest, companyLearnersTradeTest.getEmployer(), employer, employerContactPerson);
		
		String trade="N/A";
		if(companyLearnersTradeTest.getQualification() !=null && companyLearnersTradeTest.getQualification().getId() !=null) {
			trade=companyLearnersTradeTest.getQualification().getDescription();
		}
		UsersService usersService = new UsersService();
		DetailsOfExperienceArplService detailsOfExperienceArplService=new DetailsOfExperienceArplService();
		DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
		List<DetailsOfExperienceArpl> detailsOfExperienceArplList=detailsOfExperienceArplService.findByCompanyLearnersTradeTest(companyLearnersTradeTest.getId());
		List<DetailsOfTrainingArpl> detailsOfTrainingArplList=detailsOfTrainingArplService.findByCompanyLearnersTradeTest(companyLearnersTradeTest.getId());
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		Users learner = usersService.findByKey(companyLearnersTradeTest.getLearner().getId());
		
		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest() == YesNoEnum.Yes) {
			attemptedTradeTest = true;
		}
		
		boolean flc = false;
		String flcComments = "";
		if (companyLearnersTradeTest.getFlcEnglish() != null && companyLearnersTradeTest.getFlcEnglish() == YesNoEnum.Yes) {
			flc = true;
			flcComments = "English";
		}
		if (companyLearnersTradeTest.getFlcMathsLit() != null && companyLearnersTradeTest.getFlcMathsLit() == YesNoEnum.Yes) {
			flc = true;
			if (flcComments.isEmpty()) {
				flcComments = "Mathematical Literacy";
			} else {
				flcComments += " And Mathematical Literacy";
			}
		}
		
		boolean boundByAgreement = false;
		boolean medicalDisorder = false;
		
//		if (companyLearnersTradeTest.getFlc()==YesNoEnum.Yes) {flc = true;}
		if (companyLearnersTradeTest.getLearnerAgreement()==YesNoEnum.Yes) {boundByAgreement = true;}
		if (companyLearnersTradeTest.getMedicalInfo()==YesNoEnum.Yes) {medicalDisorder = true;}
		
		/* Trade Level Test application: for Artisan Recognition of Prior Learning */
//		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);// need to change, add logic to change
		params.put("flcComments", flcComments);
		params.put("boundByAgreement", boundByAgreement);
		params.put("medicalDisorder", medicalDisorder);
		if (companyLearnersTradeTest.getOfoCode() != null) {
			params.put("ofo_codes", companyLearnersTradeTest.getOfoCode().getOfoCode());
			params.put("currentOccupation", companyLearnersTradeTest.getOfoCode().getDescription());
		}else {
			params.put("ofo_codes", "");
			params.put("currentOccupation", "");
		}
		//new code to be rectified by John
		if(companyLearnersTradeTest.getDateSubmissionOriginalDoc() != null) {
			params.put("date_generated", companyLearnersTradeTest.getDateSubmissionOriginalDoc());
		}
		
		if(learner.getResidentialAddress() != null) {
			Address residential = AddressService.instance().findByKey(learner.getResidentialAddress().getId());
			if(residential.getMunicipality() != null && residential.getMunicipality().getProvince() != null) {
				Province provice = provinceService.findByKey(residential.getMunicipality().getProvince().getId());
				residential.getMunicipality().setProvince(provice);
			}
			params.put("address", residential);
		}
		
		if(learner.getPostalAddress() != null) {
			Address postal = AddressService.instance().findByKey(learner.getPostalAddress().getId());
			if(postal.getMunicipality() != null && postal.getMunicipality().getProvince() != null) {
				Province provice = provinceService.findByKey(postal.getMunicipality().getProvince().getId());
				postal.getMunicipality().setProvince(provice);
			}
			params.put("postal_address", postal);
		}
		if(companyLearnersTradeTest.getCompanyLearners() != null) {
			String qualification = CompanyLearnersService.instance().getCompanyLearnerStringQualification(companyLearnersTradeTest.getCompanyLearners());
			params.put("qualification", qualification);
		}
		
		// #{companylearnerstradetestUI.companylearnerstradetest.disability}
		// #{companylearnerstradetestUI.companylearnerstradetest.disabilityInfo}
		if (companyLearnersTradeTest.getDisability() != null && companyLearnersTradeTest.getDisability() == YesNoEnum.Yes) {
			params.put("haveDisability", true);
//			params.put("natureOfDisability", (companyLearnersTradeTest.getDisabilityInfo() != null ? companyLearnersTradeTest.getDisabilityInfo().trim() : "N/A"));
			params.put("natureOfDisability", (companyLearnersTradeTest.getDisabilityStatus() != null ? companyLearnersTradeTest.getDisabilityStatus().getDescription().trim() : "N/A"));
		} else if (companyLearnersTradeTest.getDisability() != null && companyLearnersTradeTest.getDisability() == YesNoEnum.No) {
			params.put("haveDisability", false);
			params.put("natureOfDisability", "N/A");
		} else {
			params.put("haveDisability", false);
			params.put("natureOfDisability", "N/A");
		}
		
		params.put("DetailsOfExperienceDataSource", new JRBeanCollectionDataSource(detailsOfExperienceArplList));
		params.put("DetailsOfTrainingDataSource", new JRBeanCollectionDataSource(detailsOfTrainingArplList));
		params.put("trade", trade);
		
		// populate RSA / ID Passport number
		if (learner.getRsaIDNumber() != null && !learner.getRsaIDNumber().trim().isEmpty() && learner.getRsaIDNumber().trim().length() == 13) {
			int lengthOfIdNumber  = learner.getRsaIDNumber().trim().length();
			for (int i = 0; i < lengthOfIdNumber; i++) {
				params.put("ID_PP_" + i, String.valueOf(learner.getRsaIDNumber().charAt(i)).trim());
			}
		} else if (learner.getPassportNumber() != null && !learner.getPassportNumber().trim().isEmpty()) {
			int lengthOfPassportNumber  = learner.getPassportNumber().trim().length();
			for (int i = 0; i < lengthOfPassportNumber; i++) {
				params.put("ID_PP_" + i, String.valueOf(learner.getPassportNumber().charAt(i)).trim());
			}
		}
		
		// populate Gender information
		boolean male = ((learner.getGender() != null &&  learner.getGender().getId() != null && learner.getGender().getId().equals(HAJConstants.GENDER_MALE_ID)) ? Boolean.TRUE : Boolean.FALSE);
		boolean female = ((learner.getGender() != null &&  learner.getGender().getId() != null && learner.getGender().getId().equals(HAJConstants.GENDER_FEMALE_ID)) ? Boolean.TRUE : Boolean.FALSE);
		
		// populate Equity information
		boolean african = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_BLACK_AFRICAN_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean indian = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_INDIAN_ASIAN_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean colored = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_COLOURED_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean white = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_WHITE_ID) ? Boolean.TRUE : Boolean.FALSE));
		
		params.put("African_Male", (male && african));
		params.put("Indian_Male", (male && indian));
		params.put("Coloured_Male", (male && colored));
		params.put("White_Male", (male && white));
		params.put("African_Female", (female && african));
		params.put("Indian_Female", (female && indian));
		params.put("Coloured_Female", (female && colored));
		params.put("White_Female", (female && white));
		
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-009-TradeTestApplicationForm.jasper", params,fileName);
	}
	
	public void downloadLPMFM010(CompanyLearnersTradeTest companyLearnersTradeTest, Users u, List<TradeTestTaskResult> taskResultsList) throws Exception {
		Long learnerId = 0l;
		String employerName = "";
		String qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
		String serialNo = "";
		String contactNo = "";
		String region = "";
		String tradeTestAt = "";
		String testDate = "";
		String attempt = "";
		String cmbtLevel = "";
		boolean contractualLearner = false;
		boolean artisanRPL = true;
		boolean atrami = false;
		String finalAssessment = "";
		String assessor = "";
		String assessorDate = "";
		String moderator = "";
		String moderatorDate = "";
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			learnerId = companyLearnersTradeTest.getLearner().getId();
			if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
				employerName = "UNEMPLOYED";
			} else {
				if (companyLearnersTradeTest.getCompanyEmployer() != null) {
					employerName = companyLearnersTradeTest.getCompanyEmployer().getCompanyName().trim().toUpperCase();
				} else {
					employerName = companyLearnersTradeTest.getEmployer().getCompanyName().trim().toUpperCase();
				}
			}
			qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
			serialNo = companyLearnersTradeTest.getSerialNumber();
			contactNo = "";
			region = "";
			tradeTestAt = companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName().trim().toUpperCase();
			testDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTest()) + " - " + HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTestToDate());
			attempt = companyLearnersTradeTest.getAttemptNumber().toString();
			cmbtLevel = "";
			contractualLearner = false;
			artisanRPL = true;
			atrami = false;
			if (companyLearnersTradeTest.getCompetenceEnum() != null) {
				finalAssessment = companyLearnersTradeTest.getCompetenceEnum().getFriendlyName();
			} else {
				finalAssessment = "AWAITING RESULT";
			}
			assessor = "";
			assessorDate = "";
			moderator = "";
			moderatorDate = "";
		} else {
			learnerId = companyLearnersTradeTest.getLearner().getId();
			if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
				employerName = "UNEMPLOYED";
			} else {
				if (companyLearnersTradeTest.getCompanyEmployer() != null) {
					employerName = companyLearnersTradeTest.getCompanyEmployer().getCompanyName().trim().toUpperCase();
				} else {
					employerName = companyLearnersTradeTest.getEmployer().getCompanyName().trim().toUpperCase();
				}
			}
			qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
			serialNo = companyLearnersTradeTest.getSerialNumber();
			contactNo = "";
			region = "";
			tradeTestAt = companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName().trim().toUpperCase();
			testDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTest()) + " - " + HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTestToDate());
			attempt = companyLearnersTradeTest.getAttemptNumber().toString();
			cmbtLevel = "";
			contractualLearner = true;
			artisanRPL = false;
			atrami = false;
			if (companyLearnersTradeTest.getCompetenceEnum() != null) {
				finalAssessment = companyLearnersTradeTest.getCompetenceEnum().getFriendlyName();
			} else {
				finalAssessment = "AWAITING RESULT";
			}
			assessor = "";
			assessorDate = "";
			moderator = "";
			moderatorDate = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("learner_id", learnerId);
		params.put("employerName", employerName);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("trade", qualificationString);
		params.put("serialNo", serialNo);
		params.put("contactNo", contactNo);
		params.put("region", region);
		params.put("tradeTestAt", tradeTestAt);
		params.put("testDate", testDate);
		params.put("attempt", attempt);
		params.put("cmbtLevel", cmbtLevel);
		params.put("contractualLearner", contractualLearner);
		params.put("artisanRPL", artisanRPL);
		params.put("atrami", atrami);
		params.put("TaskDataSource",new JRBeanCollectionDataSource(taskResultsList));
		params.put("finalAssessment", finalAssessment);
		params.put("assessor", assessor);
		params.put("assessorDate", assessorDate);
		params.put("moderator", moderator);
		params.put("moderatorDate", moderatorDate);
		JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-010-TradeTestReportForm.jasper", params,"LPM-FM-010_Trade_Test_Report_Form.pdf");
	}
	
	public byte[] returnLPMFM010Byte(CompanyLearnersTradeTest companyLearnersTradeTest, List<TradeTestTaskResult> taskResultsList) throws Exception {
		Long learnerId = 0l;
		String employerName = "";
		String qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
		String serialNo = "";
		String contactNo = "";
		String region = "";
		String tradeTestAt = "";
		String testDate = "";
		String attempt = "";
		String cmbtLevel = "";
		boolean contractualLearner = false;
		boolean artisanRPL = true;
		boolean atrami = false;
		String finalAssessment = "";
		String assessor = "";
		String assessorDate = "";
		String moderator = "";
		String moderatorDate = "";
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			learnerId = companyLearnersTradeTest.getLearner().getId();
			if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
				employerName = "UNEMPLOYED";
			} else {
				if (companyLearnersTradeTest.getCompanyEmployer() != null) {
					employerName = companyLearnersTradeTest.getCompanyEmployer().getCompanyName().trim().toUpperCase();
				} else {
					employerName = companyLearnersTradeTest.getEmployer().getCompanyName().trim().toUpperCase();
				}
			}
			qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
			serialNo = companyLearnersTradeTest.getSerialNumber();
			contactNo = "";
			region = "";
			tradeTestAt = companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName().trim().toUpperCase();
			testDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTest()) + " - " + HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTestToDate());
			attempt = companyLearnersTradeTest.getAttemptNumber().toString();
			cmbtLevel = "";
			contractualLearner = false;
			artisanRPL = true;
			atrami = false;
			if (companyLearnersTradeTest.getCompetenceEnum() != null) {
				finalAssessment = companyLearnersTradeTest.getCompetenceEnum().getFriendlyName();
			} else {
				finalAssessment = "Awaiting Decision";
			}
			assessor = "";
			assessorDate = "";
			moderator = "";
			moderatorDate = "";
		} else {
			learnerId = companyLearnersTradeTest.getLearner().getId();
			if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
				employerName = "UNEMPLOYED";
			} else {
				if (companyLearnersTradeTest.getCompanyEmployer() != null) {
					employerName = companyLearnersTradeTest.getCompanyEmployer().getCompanyName().trim().toUpperCase();
				} else {
					employerName = companyLearnersTradeTest.getEmployer().getCompanyName().trim().toUpperCase();
				}
			}
			qualificationString = "(" + companyLearnersTradeTest.getQualification().getCode().toString() + ") " + companyLearnersTradeTest.getQualification().getDescription();
			serialNo = companyLearnersTradeTest.getSerialNumber();
			contactNo = "";
			region = "";
			tradeTestAt = companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName().trim().toUpperCase();
			testDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTest()) + " - " + HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTestToDate());
			attempt = companyLearnersTradeTest.getAttemptNumber().toString();
			cmbtLevel = "";
			contractualLearner = true;
			artisanRPL = false;
			atrami = false;
			if (companyLearnersTradeTest.getCompetenceEnum() != null) {
				finalAssessment = companyLearnersTradeTest.getCompetenceEnum().getFriendlyName();
			} else {
				finalAssessment = "AWAITING RESULT";
			}
			assessor = "";
			assessorDate = "";
			moderator = "";
			moderatorDate = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("learner_id", learnerId);
		params.put("employerName", employerName);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("trade", qualificationString);
		params.put("serialNo", serialNo);
		params.put("contactNo", contactNo);
		params.put("region", region);
		params.put("tradeTestAt", tradeTestAt);
		params.put("testDate", testDate);
		params.put("attempt", attempt);
		params.put("cmbtLevel", cmbtLevel);
		params.put("contractualLearner", contractualLearner);
		params.put("artisanRPL", artisanRPL);
		params.put("atrami", atrami);
		params.put("TaskDataSource",new JRBeanCollectionDataSource(taskResultsList));
		params.put("finalAssessment", finalAssessment);
		params.put("assessor", assessor);
		params.put("assessorDate", assessorDate);
		params.put("moderator", moderator);
		params.put("moderatorDate", moderatorDate);
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-010-TradeTestReportForm.jasper", params);
		return bites;
	}
	
	public void downloadTradeTestReportForm() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<TradeTestTaskResult> resultsList=new ArrayList<>();
		TradeTestTaskResult results=new TradeTestTaskResult();
		results.setTask("Task 1");
		results.setCompetence(CompetenceEnum.Competent);
		resultsList.add(results);
		
		results=new TradeTestTaskResult();
		results.setTask("Task 2");
		results.setCompetence(CompetenceEnum.Competent);
		resultsList.add(results);
		
		results=new TradeTestTaskResult();
		results.setTask("Task 3");
		results.setCompetence(CompetenceEnum.NotYetCompetent);
		resultsList.add(results);
		
		JasperService.addLogo(params);
		params.put("learner_id", 89L);
		params.put("company_id", 30276L);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("trade", "Trade");
		params.put("serialNo", "SE1036655");
		params.put("contactNo", "07292665658");
		params.put("region", "Gauteng Region");
		params.put("tradeTestAt", "Techfinium");
		params.put("testDate", "20/04/2019");
		params.put("attempt", "2nd");
		params.put("cmbtLevel", "1st");
		params.put("contractualLearner", false);
		params.put("artisanRPL", false);
		params.put("atrami", true);
		params.put("TaskDataSource",new JRBeanCollectionDataSource(resultsList));
		params.put("finalAssessment", "Competent");
		
		params.put("assessor", "Assessor Name And Surname");
		params.put("assessorDate", "20/04/2019");
		params.put("moderator", "Moderator Name And Surname");
		params.put("moderatorDate", "20/05/2019");
		
		JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-010-TradeTestReportForm.jasper", params,"Trade_Test_Report_Form.pdf");
		
	}
	
	/*
	 *  byte[] bites = JasperService.instance().convertJasperReportToByte("MONITOR_AND_AUDIT_REPORT.jasper", params);
		return bites;
	 */

	public void prepareEmpoyerAndContactPerson(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyTradeTestEmployer companyTradeTestEmployer, Company employer, Users employerContactPerson) throws Exception {
		
		if (companyTradeTestEmployer == null) {
			companyTradeTestEmployer = new CompanyTradeTestEmployer();
		}		
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getCompanyEmployer() != null) {
			companyTradeTestEmployer.setCompanyName(companyLearnersTradeTest.getCompanyEmployer().getCompanyName());
			companyTradeTestEmployer.setTradingName(companyLearnersTradeTest.getCompanyEmployer().getTradingName());
			companyTradeTestEmployer.setTelNumber(companyLearnersTradeTest.getCompanyEmployer().getTelNumber());
			companyTradeTestEmployer.setFaxNumber(companyLearnersTradeTest.getCompanyEmployer().getFaxNumber());
			companyTradeTestEmployer.setEmail(companyLearnersTradeTest.getCompanyEmployer().getEmail());
			companyTradeTestEmployer.setResidentialAddress(companyLearnersTradeTest.getCompanyEmployer().getResidentialAddress());
			companyTradeTestEmployer.setPostalAddress(companyLearnersTradeTest.getCompanyEmployer().getPostalAddress());
			companyTradeTestEmployer.setLevyNumber(companyLearnersTradeTest.getCompanyEmployer().getLevyNumber());
		}
		
		employer.setCompanyName(companyTradeTestEmployer.getCompanyName());
		employer.setCompanyGuid(companyTradeTestEmployer.getCompanyGuid());
		employer.setTradingName(companyTradeTestEmployer.getTradingName());
		employer.setResidentialAddress(companyTradeTestEmployer.getResidentialAddress());
		employer.setPostalAddress(companyTradeTestEmployer.getPostalAddress());
		employer.setTelNumber(companyTradeTestEmployer.getTelNumber());
		employer.setFaxNumber(companyTradeTestEmployer.getFaxNumber());
		employer.setEmail(companyTradeTestEmployer.getEmail());
	    
	    employerContactPerson.setFirstName(companyTradeTestEmployer.getUserFirstName());
	    employerContactPerson.setLastName(companyTradeTestEmployer.getUserLastName());
	    employerContactPerson.setMiddleName(companyTradeTestEmployer.getUserMiddleName());
	    employerContactPerson.setEmail(companyTradeTestEmployer.getEmail());
	    employerContactPerson.setTelNumber(companyTradeTestEmployer.getTelNumber());
	    employerContactPerson.setCellNumber(companyTradeTestEmployer.getUserCellNumber());
		
	}
	
	public void prepareNewRegistrationTradeTest(ConfigDocProcessEnum configDocProcess, CompanyLearnersTradeTest entity, ProcessRoles processRoles) throws Exception {
		if (entity.getTradeTestProgress() == TradeTestProgressEnum.WithMersetaOne || entity.getTradeTestProgress() == TradeTestProgressEnum.WithMersetaTwo ||  entity.getTradeTestProgress() == TradeTestProgressEnum.WithMersetaThree) {
			
			if (entity.getId() != null) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			}
			
		} else if (entity.getTradeTestProgress() == TradeTestProgressEnum.WithTestCenter && (entity.getTestCenterSubmitted() != null && entity.getTestCenterSubmitted())) {
			
			if (entity.getId() != null) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			}
			
		} else {
			if (entity.getId() != null && processRoles != null ) {
				if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
				else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
				List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						entity.getDocs().add(new Doc(a));
					});
				}
			} else {
				entity.setDocs(new ArrayList<>());
				List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						entity.getDocs().add(new Doc(a));
					});
				}
			}
		}
	}
	
	public void prepareNewRegistrationArpl(ConfigDocProcessEnum configDocProcess, CompanyLearnersTradeTest entity, ProcessRoles processRoles) throws Exception {
		// merseta staff
		if (entity.getAprlProgress() == AprlProgressEnum.WithMersetaOne || entity.getAprlProgress() == AprlProgressEnum.WithMersetaTwo ||  entity.getAprlProgress() == AprlProgressEnum.WithMersetaThree ||  entity.getAprlProgress() == AprlProgressEnum.WithMersetaFour ||  entity.getAprlProgress() == AprlProgressEnum.WithMersetaFive ||  entity.getAprlProgress() == AprlProgressEnum.WithMersetaSix) {
			
			if (entity.getId() != null) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			}
			
		} else if (entity.getAprlProgress() == AprlProgressEnum.WithInitiatorTwo) {
			
			if (entity.getId() != null) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			}
			
		} else if (entity.getAprlProgress() == AprlProgressEnum.WithTestCenterOne && (entity.getTestCenterSubmitted() != null && entity.getTestCenterSubmitted())) {
			
			if (entity.getId() != null) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			}
			
		} else {
			if (entity.getId() != null && processRoles != null ) {
				if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
				else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
				List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						entity.getDocs().add(new Doc(a));
					});
				}
			} else {
				entity.setDocs(new ArrayList<>());
				List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						entity.getDocs().add(new Doc(a));
					});
				}
			}
		}
	}
	
	/* Complete Task: WithInitiator Trade Test */
	public void completeWorkflowWithInitiatorOneTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		documentValidiation(companyLearnersTradeTest);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaOne);
		if(companyLearnersTradeTest.getStatus()==ApprovalEnum.Rejected) {
			companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		}
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationTradeTestApplicationEmail(1, companyLearnersTradeTest, null);
//		locateNextInProcessRegionUsers(user, task, companyLearnersTradeTest, false);
	}
	
	/* Reject Task: WithMersetaOne Trade Test */
	public void rejectWorkflowWithWithMersetaOneTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		if (companyLearnersTradeTest.getAdminUser() == null) {
			companyLearnersTradeTest.setAdminUser(user);
		}
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithInitiator);
		update(companyLearnersTradeTest);
		List<Users> usersList=new ArrayList<>();
		usersList.add(companyLearnersTradeTest.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, usersList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess());
	}
	
	/* Complete Task: MersetaOne Trade Test */
	public void completeWorkflowWithWithMersetaOneTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithTestCenter);
		companyLearnersTradeTest.setSerialNumber(generateCompanyLearnerTradeTestSerialNumber());
		if (companyLearnersTradeTest.getTestCenterSubmitted() == null) {
			companyLearnersTradeTest.setTestCenterSubmitted(false);
		}
		if (companyLearnersTradeTest.getTestDatesProvided() == null) {
			companyLearnersTradeTest.setTestDatesProvided(false);
		}
		
//		if(companyLearnersTradeTest.getStatus()==ApprovalEnum.Rejected){
//			companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
//		}
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
//		locateNextInProcessRegionUsers(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		/**System to send acknowledgement notification and must include notification 
		 * about making payment to Trade Test Centre & Readiness Date & serial number*/
//		sendTradeTestAcknolegmnet(companyLearnersTradeTest, companyLearnersTradeTest.getCompanyLearners().getUser());
	}
	
	/* Approve Task: WithTestTwo Trade Test  ADD EMAILS */
	public void approveApplicationWithWithMersetaTwoTradeTest(CompanyLearnersTradeTest companylearnerstradetest,Users activeUser, Tasks task)  throws Exception {
		// sets sign off user
		if (companylearnerstradetest.getCoordinatorUser() == null) {
			companylearnerstradetest.setCoordinatorUser(activeUser);
		}
		
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		
		Signoff signoff = new Signoff(activeUser, "MERSETA: Region Client Services Coordinator");
		signoff.setTargetClass(companylearnerstradetest.getClass().getName());
		signoff.setTargetKey(companylearnerstradetest.getId());
		signoff.setAccept(true);
		signoff.setLastestSignoff(true);
		signoff.setSignOffDate(getSynchronizedDate());
		createList.add(signoff);
		
		/*
		 * Identify if qualification is designed trade
		 */
		if (companylearnerstradetest.getDesignatedTradeLevel() == null) {
			// not a designated trade / normal qualification
			switch (companylearnerstradetest.getCompetenceEnum()) {
			case AbsentCancelled:
				// does not count as an attempt
				companylearnerstradetest.setTestCenterSubmitted(true);
				companylearnerstradetest.setStatus(ApprovalEnum.Completed);
				companylearnerstradetest.setApprovalDate(getSynchronizedDate());
				companylearnerstradetest.setApprovedUser(activeUser);
				updateList.add(companylearnerstradetest);
				if (companylearnerstradetest.getCompanyLearners() != null && companylearnerstradetest.getCompanyLearners().getId() != null) {
					CompanyLearners companyLearner = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
					companyLearner.setLearnerStatus(LearnerStatusEnum.Registered);
					updateList.add(companyLearner);
				}
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				TasksService.instance().completeTask(task);
				break;
			case Competent:
				// send to next step for ADMIN to assigned NAMB submission date
				companylearnerstradetest.setTestCenterSubmitted(true);
				companylearnerstradetest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaThree);
				updateList.add(companylearnerstradetest);
				if (companylearnerstradetest.getCompanyLearners() != null && companylearnerstradetest.getCompanyLearners().getId() != null) {
					CompanyLearners companyLearner = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
					companyLearner.setLearnerStatus(LearnerStatusEnum.Achieved);
					companyLearner.setDateLearnerCompleted(getSynchronizedDate());
					updateList.add(companyLearner);
				}
				if (!updateList.isEmpty()) {
					dao.updateBatch(updateList);
				}
				locateNextInProgressArplTask(activeUser, task, companylearnerstradetest, false);
				sendNotificationTradeTestApplicationEmail(5, companylearnerstradetest, null);
				
				break;
			case NotYetCompetent:
				/*
				 *  calculate attempts
				 */
				companylearnerstradetest.setTestCenterSubmitted(true);
				companylearnerstradetest.setStatus(ApprovalEnum.Completed);
				companylearnerstradetest.setApprovalDate(getSynchronizedDate());
				companylearnerstradetest.setApprovedUser(activeUser);
				updateList.add(companylearnerstradetest);
				if (companylearnerstradetest.getAttemptNumber() <= 3) {
					// kick off learner termination
					if (companylearnerstradetest.getCompanyLearners() != null &&  companylearnerstradetest.getCompanyLearners().getId() != null) {
						if (companyLearnersService == null) {
							companyLearnersService = new CompanyLearnersService();
						}
						CompanyLearners companyLearners = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearners.setStatus(ApprovalEnum.PendingApproval);
						companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTermination);
						

						updateList.add(companyLearners);
						CompanyLearnersTermination companyLearnersTermination = new CompanyLearnersTermination();
						companyLearnersTermination.setCompanyLearners(companyLearners);
						companyLearnersTermination.setTerminationTypeEnum(TerminationTypeEnum.TradeTestTermination);
						companyLearnersTermination.setStatus(ApprovalEnum.PendingApproval);
/*						
 						if(companyLearners.getCompany() != null && companyLearners.getCompany().getId() != null){
							companyLearnersTermination.setTrainingProviderApplication(companyLearners.getCompany());
						}
*/
						dao.create(companyLearnersTermination);
						TasksService.instance().findFirstInProcessAndCreateTask("", null, companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName(), true, ConfigDocProcessEnum.TRADE_TEST_FAIL_LEARNER_TERMINATION, null, null);
					}
				} else {
					// set to normal status
					if (companylearnerstradetest.getCompanyLearners() != null &&  companylearnerstradetest.getCompanyLearners().getId() != null) {
						if (companyLearnersService == null) {
							companyLearnersService = new CompanyLearnersService();
						}
						CompanyLearners companyLearners = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
						updateList.add(companyLearners);
					}
					// send notification
				}
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				TasksService.instance().completeTask(task);
				sendNotificationTradeTestApplicationEmail(6, companylearnerstradetest, null);
				break;
			default:
				throw new Exception("Unable to locate status, contact support!");
			}
			
			// creates sign off
			if (!createList.isEmpty()) {
				dao.createBatch(createList);
			}
			
			RejectReasonMultipleSelectionService.instance().clearEntries(companylearnerstradetest.getId(), companylearnerstradetest.getClass().getName(), task.getWorkflowProcess());
		} else {
			DesignatedTradeLevel dtl = DesignatedTradeLevelService.instance().findByKey(companylearnerstradetest.getDesignatedTradeLevel().getId());
			
			switch (companylearnerstradetest.getCompetenceEnum()) {
			case AbsentCancelled:
				// does not count as an attempt 
				companylearnerstradetest.setTestCenterSubmitted(true);
				companylearnerstradetest.setStatus(ApprovalEnum.Approved);
				companylearnerstradetest.setApprovalDate(getSynchronizedDate());
				companylearnerstradetest.setApprovedUser(activeUser);
				updateList.add(companylearnerstradetest);
				if (companylearnerstradetest.getCompanyLearners() != null && companylearnerstradetest.getCompanyLearners().getId() != null) {
					CompanyLearners companyLearner = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
					companyLearner.setLearnerStatus(LearnerStatusEnum.Registered);
					companyLearner.setStatus(ApprovalEnum.Approved);
					updateList.add(companyLearner);
				}
				if (!updateList.isEmpty()) {
					dao.updateBatch(updateList);
				}
				TasksService.instance().completeTask(task);
				break;
			case Competent:
				// if on the final level of Designated trade must follow normal workflow to get certificate
				if (Boolean.TRUE.equals(companylearnerstradetest.getFinalCbmtQualification())) {
					// send to next step for ADMIN to assigned NAMB submission date
					companylearnerstradetest.setTestCenterSubmitted(true);
					companylearnerstradetest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaThree);
					updateList.add(companylearnerstradetest);
					if (companylearnerstradetest.getCompanyLearners() != null && companylearnerstradetest.getCompanyLearners().getId() != null) {
						CompanyLearners companyLearner = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearner.setLearnerStatus(LearnerStatusEnum.Achieved);
						companyLearner.setDateLearnerCompleted(getSynchronizedDate());
						updateList.add(companyLearner);
					}
					if (!updateList.isEmpty()) {
						dao.updateBatch(updateList);
					}
					locateNextInProgressArplTask(activeUser, task, companylearnerstradetest, false);
					sendNotificationTradeTestApplicationEmail(5, companylearnerstradetest, null);
				} else {
					companylearnerstradetest.setTestCenterSubmitted(true);
					companylearnerstradetest.setStatus(ApprovalEnum.Completed);
					companylearnerstradetest.setApprovalDate(getSynchronizedDate());
					companylearnerstradetest.setApprovedUser(activeUser);
					if (companylearnerstradetest.getCompanyLearners() != null &&  companylearnerstradetest.getCompanyLearners().getId() != null) {
						if (companyLearnersService == null) {
							companyLearnersService = new CompanyLearnersService();
						}
						CompanyLearners companyLearners = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
						companyLearners.setStatus(ApprovalEnum.Approved);
						// Subject to change
						companyLearners.setLastPassedTestDate(getSynchronizedDate());
						updateList.add(companyLearners);
					}
					updateList.add(companylearnerstradetest);
					if (!updateList.isEmpty()) {
						dao.updateBatch(updateList);
					}
					TasksService.instance().completeTask(task);
				}
				
				break;
			case NotYetCompetent:
				companylearnerstradetest.setTestCenterSubmitted(true);
				companylearnerstradetest.setStatus(ApprovalEnum.Completed);
				companylearnerstradetest.setApprovalDate(getSynchronizedDate());
				companylearnerstradetest.setApprovedUser(activeUser);
				updateList.add(companylearnerstradetest);
				Integer maxAttempts = 3;
				if (dtl.getLegacyMaxAttemptsAmount() != null && dtl.getDateLegacyAttempts() != null) {
					if (companylearnerstradetest.getCompanyLearners() != null && companylearnerstradetest.getCompanyLearners().getCommencementDate() != null) {
						if (GenericUtility.getStartOfDay(companylearnerstradetest.getCompanyLearners().getCommencementDate()).equals(GenericUtility.getStartOfDay(dtl.getDateLegacyAttempts())) || GenericUtility.getStartOfDay(companylearnerstradetest.getCompanyLearners().getCommencementDate()).before(GenericUtility.getStartOfDay(dtl.getDateLegacyAttempts()))) {
							maxAttempts = dtl.getLegacyMaxAttemptsAmount();
						}
					} else {
						if (dtl.getMaxAttemptsAmount() != null) {
							maxAttempts = dtl.getMaxAttemptsAmount();
						} 
					}
				} else {
					if (dtl.getMaxAttemptsAmount() != null) {
						maxAttempts = dtl.getMaxAttemptsAmount();
					}
				}
				if (companylearnerstradetest.getAttemptNumber() >= maxAttempts && companylearnerstradetest.getFinalCbmtQualification() != null && companylearnerstradetest.getFinalCbmtQualification()) {
					// kick off learner termination
					if (companylearnerstradetest.getCompanyLearners() != null &&  companylearnerstradetest.getCompanyLearners().getId() != null) {
						if (companyLearnersService == null) {
							companyLearnersService = new CompanyLearnersService();
						}
						CompanyLearners companyLearners = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearners.setStatus(ApprovalEnum.PendingApproval);
						companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTermination);
						

						updateList.add(companyLearners);
						CompanyLearnersTermination companyLearnersTermination = new CompanyLearnersTermination();
						companyLearnersTermination.setCompanyLearners(companyLearners);
						companyLearnersTermination.setTerminationTypeEnum(TerminationTypeEnum.TradeTestTermination);
						companyLearnersTermination.setStatus(ApprovalEnum.PendingApproval);
						dao.create(companyLearnersTermination);
						TasksService.instance().findFirstInProcessAndCreateTask("", null, companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName(), true, ConfigDocProcessEnum.TRADE_TEST_FAIL_LEARNER_TERMINATION, null, null);
					}
				} else {
					// set to normal status
					if (companylearnerstradetest.getCompanyLearners() != null &&  companylearnerstradetest.getCompanyLearners().getId() != null) {
						if (companyLearnersService == null) {
							companyLearnersService = new CompanyLearnersService();
						}
						CompanyLearners companyLearners = companyLearnersService.findByKey(companylearnerstradetest.getCompanyLearners().getId());
						companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
						companyLearners.setStatus(ApprovalEnum.Approved);
						updateList.add(companyLearners);
					}
				}
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				TasksService.instance().completeTask(task);
				sendNotificationTradeTestApplicationEmail(6, companylearnerstradetest, null);
				break;
			default:
				throw new Exception("Unable to locate status, contact support!");
			}
			
			// creates sign off
			if (createList.size() != 0) {
				dao.createBatch(createList);
			}
			RejectReasonMultipleSelectionService.instance().clearEntries(companylearnerstradetest.getId(), companylearnerstradetest.getClass().getName(), task.getWorkflowProcess());
		}
	}
	
	public void completeCollectCertificates(CompanyLearnersTradeTest companylearnerstradetest,Users activeUser, Tasks task,CollectDetail collectDetail)  throws Exception{
		CollectDetailService collectDetailService =new CollectDetailService();
		collectDetail.setCompetenceEnum(companylearnerstradetest.getCompetenceEnum());
		collectDetailService.create(collectDetail);
		companylearnerstradetest.setCollectDetail(collectDetail);
		update(companylearnerstradetest);
		TasksService.instance().completeTask(task);
	}
	
	/**The learner status will remain 
	 * registered until the learner is 
	 * competent on the final level.  
	 * If Competent on the final level, 
	 * the status will change to 
	 * qualification obtained. 
	 * @throws Exception 
	 * NOT USED
	 * */
	public void checkCBMTLearnerIfCompetent(CompanyLearnersTradeTest companylearnerstradetest) throws Exception
	{
		if(companylearnerstradetest.getFinalCbmtQualification() ==null){
//			companyLearnersService.locateCorrectDesignatedTradeLevel(companylearnerstradetest, companylearnerstradetest.getCompanyLearners());
		}
		if(companylearnerstradetest.getFinalCbmtQualification()){
			
			companylearnerstradetest.getCompanyLearners().setLearnerStatus(LearnerStatusEnum.QualificationObtained);
			companyLearnersService.update(companylearnerstradetest.getCompanyLearners());
		}
		else{
			companylearnerstradetest.getCompanyLearners().setLearnerStatus(LearnerStatusEnum.Registered);
			companyLearnersService.update(companylearnerstradetest.getCompanyLearners());
		}
	}
	
	public boolean checkIfCBMT(CompanyLearnersTradeTest companylearnerstradetest)
	{
		boolean isCBMT=false;
		if(companylearnerstradetest.getCompanyLearners() !=null && 
		companylearnerstradetest.getCompanyLearners().getQualification() !=null && 
		companylearnerstradetest.getCompanyLearners().getQualification().getDesignatedTrade() !=null )
		{
			if(companylearnerstradetest.getCompanyLearners().getQualification().getDesignatedTrade().getId().equals(3L))//MOTOR CBMT
			{
				isCBMT=true;
			}
		}
		return isCBMT;
	}
	
	/* Complete Task: WithTestCenter Trade Test */
	public void completeWorkflowWithTestCenterTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		trainingProviderValidiation(companyLearnersTradeTest);
		documentValidiation(companyLearnersTradeTest);
//		TradeTestTaskResultService tradeTestTaskResultService=new TradeTestTaskResultService();
//		if(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(companyLearnersTradeTest)< 1){
//			throw new Exception("Please Provide Assessment Tasks Information");
//		}
		if (companyLearnersTradeTest.getTestCenterUser() == null) {
			companyLearnersTradeTest.setTestCenterUser(user);
		}
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaTwo);
		update(companyLearnersTradeTest);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		sendNotificationTradeTestApplicationEmail(4, companyLearnersTradeTest, null);
//		//Scheduling test date (OLD METHOD)
//		if(task.getProcessRole().getRoleOrder()==3) {	
////			if(companyLearnersTradeTest.getStatus()==ApprovalEnum.Rejected){companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);}
//			update(companyLearnersTradeTest);
//			List<Users>userList=new ArrayList<>();
//			userList.add(user);
//			TasksService.instance().findNextInProcessAndCreateTask(user, task, userList, false);
//			RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
//			/**When date and serial number received, LPMTP007 
//			 * Level Test Date Notification to be 
//			 * issued the centre, workplace and learner.*/
//			sendLPMTP007(companyLearnersTradeTest);
//		} else{
//			TradeTestTaskResultService tradeTestTaskResultService=new TradeTestTaskResultService();
//			if(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(companyLearnersTradeTest)< 1){
//				throw new Exception("Please Provide Assessment Tasks Information");
//			}
//			documentValidiation(companyLearnersTradeTest);
//			companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaTwo);
//			if(companyLearnersTradeTest.getStatus()==ApprovalEnum.Rejected){companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);}
//			update(companyLearnersTradeTest);
//			locateNextInProcessRegionUsers(user, task, companyLearnersTradeTest, false);
//			RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
//			/**Once uploaded, an email must be sent to the 
//			 * workplace and learner that the results have been 
//			 * uploaded for verification and that they can expect 
//			 * the official results within 5 working days. */
//			sendTradeTestResultsNofication(companyLearnersTradeTest);
//		}
	}
	
	/* Complete Task: WithInitiator ARPL */
	public void completeWorkflowWithInitiatorOne(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyTradeTestEmployer employer, Users user, Tasks task, boolean copyAddress, boolean copyAddressEmployer, 
			boolean alterUserLanguages, List<UsersLanguage> userLanguageList, boolean alterUserDisability, List<UsersDisability> userDisabilityList) throws Exception {
		CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
		entryValidiation(companyLearnersTradeTest);
		documentValidiation(companyLearnersTradeTest);
		
		updateLearnerInformation(companyLearnersTradeTest.getLearner(), copyAddress);
		
		if (alterUserLanguages) {
			UsersLanguageService.instance().createLinkToUser(userLanguageList, companyLearnersTradeTest.getLearner(), user);
		}
		if (alterUserDisability) {
			UsersDisabilityService.instance().createLinkToUser(userDisabilityList, companyLearnersTradeTest.getLearner(), user);
		}
		
		if (companyLearnersTradeTest.getEmploymentStatus() != EmploymentStatusEnum.UnEmployed) {
			companyTradeTestEmployerService.createUpdateEmployerWithAddress(employer, copyAddressEmployer);
			companyLearnersTradeTest.setEmployer(employer);
		} else {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		
		if (companyLearnersTradeTest.getLearner() != null && companyLearnersTradeTest.getLearner().getId() != null && companyLearnersTradeTest.getLearner().getDisability() != null) {
			if (companyLearnersTradeTest.getLearner().getDisability() == YesNoEnum.Yes) {
				companyLearnersTradeTest.setDisability(YesNoEnum.Yes);
				List<UsersDisability> udList = UsersDisabilityService.instance().findByKeyUser(companyLearnersTradeTest.getLearner());
				if (!udList.isEmpty()) {
					UsersDisability firstOne = udList.get(0);
					if (firstOne != null) {
						if (firstOne.getDisabilityStatus() != null && firstOne.getDisabilityStatus().getId() != null) {
							companyLearnersTradeTest.setDisabilityStatus(firstOne.getDisabilityStatus());
						}
						if (firstOne.getDisabilityRating() != null && firstOne.getDisabilityRating().getId() != null) {
							companyLearnersTradeTest.setDisabilityRating(firstOne.getDisabilityRating());
						}
					} else {
						companyLearnersTradeTest.setDisabilityStatus(null);
						companyLearnersTradeTest.setDisabilityRating(null);
					}
					
				}
			} else {
				companyLearnersTradeTest.setDisability(YesNoEnum.No);
				companyLearnersTradeTest.setDisabilityStatus(null);
				companyLearnersTradeTest.setDisabilityRating(null);
			}
		} else {
			companyLearnersTradeTest.setDisability(YesNoEnum.No);
			companyLearnersTradeTest.setDisabilityStatus(null);
			companyLearnersTradeTest.setDisabilityRating(null);
		}
		
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaOne);
		update(companyLearnersTradeTest);
		
		
		
		
		
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(1, companyLearnersTradeTest, null);
	}
	
	public void createUpdateArpl(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyTradeTestEmployer employer, boolean copyAddress, boolean copyAddressEmployer) throws Exception{
		updateLearnerInformation(companyLearnersTradeTest.getLearner(), copyAddress);
		if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		
		if (companyLearnersTradeTest.getEmploymentStatus() != EmploymentStatusEnum.UnEmployed) {
			CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
			companyTradeTestEmployerService.createUpdateEmployerWithAddress(employer, copyAddressEmployer);
			companyLearnersTradeTest.setEmployer(employer);
		}
		// update disability info
		update(companyLearnersTradeTest);
	}
	
	public void createUpdateLegacyArpl(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyTradeTestEmployer employer, boolean copyAddress, boolean copyAddressEmployer) throws Exception{
		updateLearnerInformation(companyLearnersTradeTest.getLearner(), copyAddress);
		if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		
		if (companyLearnersTradeTest.getEmploymentStatus() != EmploymentStatusEnum.UnEmployed) {
			CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
			companyTradeTestEmployerService.createUpdateEmployerWithAddress(employer, copyAddressEmployer);
			companyLearnersTradeTest.setEmployer(employer);
		}
		// update disability info
		update(companyLearnersTradeTest);
	}
	
	private void entryValidiation(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception{
		DetailsOfExperienceArplService service = new DetailsOfExperienceArplService();
		DetailsOfTrainingArplService serviceTwo = new DetailsOfTrainingArplService();
		
		if (serviceTwo.countByTradeTest(companyLearnersTradeTest) == 0) {
			throw new Exception("Provide Atleast One Entry For: Details Of Training");
		}
		if (service.countByTradeTest(companyLearnersTradeTest) == 0) {
			throw new Exception("Provide Atleast One Entry For: Details Of Experience");
		}
	}

	public void updateLearnerInformation(Users learner, boolean copyAddress) throws Exception{
		if (copyAddress) {
			AddressService.instance().copyFromToAddress(learner.getResidentialAddress(), learner.getPostalAddress());
			AddressService.instance().saveAddresses(learner.getResidentialAddress(), learner.getPostalAddress());
		} else {
			AddressService.instance().saveAddresses(learner.getResidentialAddress(), learner.getPostalAddress());
		}
		UsersService usersService = new UsersService();
		usersService.update(learner);
	}

	/* Complete Task: MersetaOne */
	public void completeWorkflowWithWithMersetaOne(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorTwo);
		companyLearnersTradeTest.setDateSubmissionOriginalDoc(getSynchronizedDate());
		if (companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		companyLearnersTradeTest.setDueDateSubmissionOriginalDoc(GenericUtility.addDaysToDate(getSynchronizedDate(), 30));
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(2, companyLearnersTradeTest, null);
	}
	
	/* Reject Task: WithMersetaOne */
	public void rejectWorkflowWithWithMersetaOne(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
		update(companyLearnersTradeTest);
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			List<Users> usersAssignedToList = new ArrayList<>();
			usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, usersAssignedToList);
		}
		if (rejectReasonsList.size() != 0) {
			List<IDataEntity> createList = new ArrayList<>();
			createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess()));
			dao.createBatch(createList);
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), tasks.getWorkflowProcess());
		}
	}
	
	/* Complete Task With Initiator Two */
	public void completeTaskWithInitiatorTwo(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaTwo);
		if (companyLearnersTradeTest.getOnHold() == null) {
			companyLearnersTradeTest.setOnHold(false);
		}
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
	}
	
	/* Approve Task: WithMersetaTwo ARPL */
	public void approveApplicationWithWithMersetaTwo(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
//		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaThree);
		// add holding area 
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		companyLearnersTradeTest.setOnHold(true);
		update(companyLearnersTradeTest);
		sendNotificationArplApplicationEmail(17, companyLearnersTradeTest, null);
//		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		TasksService.instance().completeTask(task);
	}
	
	/* Send to MERSETA Three to approve original copies of docs */
	public void reopenFromHoldingAreaWithMerSETAThree(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		// Region Client Services Administrator
		List<Users> usersAssignedToList = new ArrayList<>();
		if (companyLearnersTradeTest.getClientServiceAdminUser() != null) {
			usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		}
		if (usersAssignedToList.size() == 0) {
			throw new Exception("Unable to locate assigned Region Client Services Administrator. Contact support!");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaThree);
		companyLearnersTradeTest.setOnHold(false);
		update(companyLearnersTradeTest);
		TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(4, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.ARPLTradeTestApplication, null, "", true, usersAssignedToList);
	}
	
	/* Reject Task: WithMersetaTwo ARPL */
	public void rejectApplicationWithWithMersetaTwo(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task, List<RejectReasons> rejectReasonsList) throws Exception{
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorTwo);
		update(companyLearnersTradeTest);
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			List<Users> usersAssignedToList = new ArrayList<>();
			usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, usersAssignedToList);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		}
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, task.getWorkflowProcess());
	}
	
	/* Reject Task: WithMersetaTwo TRADE TEST*/
	public void rejectApplicationWithWithMersetaTwoTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task, List<RejectReasons> rejectReasonsList) throws Exception{
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		if (companyLearnersTradeTest.getCoordinatorUser() == null) {
			companyLearnersTradeTest.setCoordinatorUser(user);
		}
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithTestCenter);
		update(companyLearnersTradeTest);
		if (companyLearnersTradeTest.getTestCenterUser() != null) {
			if (usersService == null) {
				usersService = new UsersService();
			}
			List<Users> usersAssignedToList = new ArrayList<>();
			usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getTestCenterUser().getId()));
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, usersAssignedToList);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		}
		
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, task.getWorkflowProcess());
	}
	
	public void setTestDates(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception{
		companyLearnersTradeTest.setTestDatesProvided(true);
		update(companyLearnersTradeTest);
		sendNotificationArplApplicationEmail(5, companyLearnersTradeTest, null);
	}
	
	public void amendTestDates(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception{
		update(companyLearnersTradeTest);
		sendNotificationArplApplicationEmail(14, companyLearnersTradeTest, null);
	}
	
	/* Complete Task: WithTestCenter */
	public void completeWorkflowWithTestCenter(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		trainingProviderValidiation(companyLearnersTradeTest);
		documentValidiation(companyLearnersTradeTest);
		boolean sendFirstTimeNotification = false;
		if (companyLearnersTradeTest.getTestCenterUser() == null) {
			companyLearnersTradeTest.setTestCenterUser(user);
			sendFirstTimeNotification = true;
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaFive);
		
		// quick fix should enhance in the future
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		if (sendFirstTimeNotification) {
			sendNotificationArplApplicationEmail(8, companyLearnersTradeTest, null);
		}
	}
	
	private void trainingProviderValidiation(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception{
		if (!companyLearnersTradeTest.getTestDatesProvided()) {
			throw new Exception("Provide Test Dates Before Proceeding");
		}
		if (companyLearnersTradeTest.getCompetenceEnum() == null) {
			throw new Exception("Provide If Learner is Competent or Not Yet Competent Before Proceeding");
		}
		switch (companyLearnersTradeTest.getCompetenceEnum()) {
		case Competent:
			if (companyLearnersTradeTest.getAssessorApplication() == null || companyLearnersTradeTest.getAssessorApplication().getId() == null) {
				throw new Exception("Provide an Assessor Before Proceeding");	
			}
			break;
		case NotYetCompetent:
			if (companyLearnersTradeTest.getAssessorApplication() == null || companyLearnersTradeTest.getAssessorApplication().getId() == null) {
				throw new Exception("Provide an Assessor Before Proceeding");	
			}
			break;
		default:
			break;
		}
	}

	/* Reject Task: WithMersetaThree */
	public void rejectWorkflowWithMersetaThree(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		if (usersService == null) {
			usersService = new UsersService();
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaTwo);
		update(companyLearnersTradeTest);
		List<Users> userReciverList = new ArrayList<>();
		// reject back to Region Client Services Administrator
		userReciverList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userReciverList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess());
	}
	
	/* Complete Task: WithMersetaThree */
	public void completeWorkflowWithMersetaThree(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaFour);
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
	}
	
	/* Reject Task: WithMersetaFour */
	public void rejectWorkflowWithMersetaFour(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaThree);
		update(companyLearnersTradeTest);
		List<Users> userReciverList = new ArrayList<>();
		userReciverList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userReciverList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess());
	}
	
	/* Reject Application Task: WithMersetaFour */
	public void rejectApplicationWithWithMersetaFour(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task, List<RejectReasons> rejectReasonsList) throws Exception{
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setStatus(ApprovalEnum.Rejected);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(user);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(3, companyLearnersTradeTest, rejectReasonsList);
	}
	
	/* Complete Task: WithMersetaFour */
	public void completeWorkflowWithMersetaFour(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		companyLearnersTradeTest.setTestCenterSubmitted(false);
		companyLearnersTradeTest.setDateSubmittedToTestCenter(getSynchronizedDate());
		companyLearnersTradeTest.setTestDatesProvided(false);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithTestCenterOne);
		companyLearnersTradeTest.setSerialNumber(generateCompanyLearnerTradeTestSerialNumber());
		update(companyLearnersTradeTest);
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(4, companyLearnersTradeTest, null);
	}
	
	/* Complete Task: WithInitiatorTwo */
	public void completeWorkflowWithWithInitiatorTwo(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		companyLearnersTradeTest.setStatus(ApprovalEnum.Approved);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
	}
	
	/* Complete Task: WithMersetaFive Competent */
	public void completeWorkflowWithMersetaFiveCompetent(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		boolean sendNotification = false;
		if (Boolean.TRUE.equals(companyLearnersTradeTest.getTestCenterSubmitted())) {
			sendNotification = false;
		} else {
			sendNotification = true;
		}
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaSix);
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		update(companyLearnersTradeTest);
		List<IDataEntity> list = new ArrayList<>();
		Signoff signoff = new Signoff(user, "MERSETA: Region Client Services Coordinator");
		signoff.setTargetClass(companyLearnersTradeTest.getClass().getName());
		signoff.setTargetKey(companyLearnersTradeTest.getId());
		signoff.setAccept(true);
		signoff.setLastestSignoff(true);
		signoff.setSignOffDate(getSynchronizedDate());
		list.add(signoff);
		if (list.size() != 0) {
			dao.createBatch(list);
		}
		// send notification 
		locateNextInProgressArplTask(user, task, companyLearnersTradeTest, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		if (sendNotification) {
			sendNotificationArplApplicationEmail(10, companyLearnersTradeTest, null);
		}
	}
	
	/* Complete Task: WithMersetaFive Not Competent */
	public void completeWorkflowWithMersetaFiveNotCompetent(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setStatus(ApprovalEnum.Completed);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(user);
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		update(companyLearnersTradeTest);
		
		// add company learners update
		
		List<IDataEntity> list = new ArrayList<>();
		Signoff signoff = new Signoff(user, "Region Client Services Coordinator");
		signoff.setTargetClass(companyLearnersTradeTest.getClass().getName());
		signoff.setTargetKey(companyLearnersTradeTest.getId());
		signoff.setAccept(true);
		signoff.setSignOffDate(getSynchronizedDate());
		list.add(signoff);
		if (list.size() != 0) {
			dao.createBatch(list);
		}
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(9, companyLearnersTradeTest, null);
	}
	
	/* Complete Task: WithMersetaFive Absent */
	public void completeWorkflowWithMersetaFiveAbsent(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setStatus(ApprovalEnum.Completed);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(user);
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		
		// add company learners update
		update(companyLearnersTradeTest);
		List<IDataEntity> list = new ArrayList<>();
		Signoff signoff = new Signoff(user, "Region Client Services Coordinator");
		signoff.setTargetClass(companyLearnersTradeTest.getClass().getName());
		signoff.setTargetKey(companyLearnersTradeTest.getId());
		signoff.setAccept(true);
		signoff.setSignOffDate(getSynchronizedDate());
		list.add(signoff);
		if (list.size() != 0) {
			dao.createBatch(list);
		}
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(18, companyLearnersTradeTest, null);
	}
	
	/* Reject Task: WithMersetaFive */
	public void rejectWorkflowWithMersetaFive(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithTestCenterOne);
		if (companyLearnersTradeTest.getTestCenterUser() != null) {
			List<Users> userReciverList = new ArrayList<>();
			userReciverList.add(usersService.findByKey(companyLearnersTradeTest.getTestCenterUser().getId()));
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userReciverList);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
		update(companyLearnersTradeTest);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess());
	}
	
	/* Complete Task: WithMersetaSix  */
	public void completeWorkflowWithWithMersetaSix(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		if (companyLearnersTradeTest.getEmploymentStatus() != null && companyLearnersTradeTest.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		companyLearnersTradeTest.setStatus(ApprovalEnum.AwaitingNAMB);
		companyLearnersTradeTest.setDateSetToAwaitingNamb(getSynchronizedDate());
		companyLearnersTradeTest.setNambDecision(null);
		
		updateList.add(companyLearnersTradeTest);
		
		// update company Learners info
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			CompanyLearners cl = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			cl.setStatus(ApprovalEnum.Completed);
			updateList.add(cl);
		}
		
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
	}
	
	/* Send task Back to Test Center ARPL */
	public void sendArplToTestCenter(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<RejectReasons> rejectReasonsList, List<Signoff> signoffList) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setDateSubmittedToTestCenter(getSynchronizedDate());
		companyLearnersTradeTest.setTestDatesProvided(true);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithTestCenterOne);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersTradeTest.setNambDecision(null);
		companyLearnersTradeTest.setNambSubmissionDate(null);
//		update(companyLearnersTradeTest);
		updateList.add(companyLearnersTradeTest);
		for (Signoff signoff : signoffList) {
			if (companyLearnersTradeTest.getCoordinatorUser() != null) {
				if (companyLearnersTradeTest.getCoordinatorUser().getId().equals(signoff.getUser().getId())) {
					signoff.setLastestSignoff(false);
					updateList.add(signoff);
				}
			}
		}
		if (updateList != null && updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		// lastestSignoff
		TasksService.instance().findByPositionAndCreateTaskWithUsers(6, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.ARPLTradeTestApplication, null, "", true, null);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, ConfigDocProcessEnum.ARPLTradeTestApplication);;
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Rejected, user, rejectReasonsList, "ARPL Trade Test Application Sent Back To Trade Test Center. Current Sign off added to previoulsy added sign offs");
	}
	
	public void sendTradeTestToTestCenter(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<RejectReasons> rejectReasonsList, List<Signoff> signoffList) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		List<IDataEntity> updateList = new ArrayList<>();
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setDateSubmittedToTestCenter(getSynchronizedDate());
		companyLearnersTradeTest.setTestDatesProvided(true);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithTestCenter);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersTradeTest.setNambDecision(null);
		companyLearnersTradeTest.setNambSubmissionDate(null);
//		update(companyLearnersTradeTest);
		updateList.add(companyLearnersTradeTest);
		for (Signoff signoff : signoffList) {
			if (companyLearnersTradeTest.getCoordinatorUser() != null) {
				if (companyLearnersTradeTest.getCoordinatorUser().getId().equals(signoff.getUser().getId())) {
					signoff.setLastestSignoff(false);
					updateList.add(signoff);
				}
			}
		}
		if (updateList != null && updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		// lastestSignoff
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getTestCenterUser() != null && companyLearnersTradeTest.getTestCenterUser().getId() != null) {
			if (usersService != null) {
				usersService = new UsersService();
			}
			userList.add(usersService.findByKey(companyLearnersTradeTest.getTestCenterUser().getId()));
		}
		
		if (userList.size() != 0) {
			TasksService.instance().findByPositionAndCreateTaskWithUsers(2, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.TradeTestApplication, null, "", true, userList);
		} else {
			TasksService.instance().findByPositionAndCreateTaskWithUsers(2, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.TradeTestApplication, null, "", true, null);
		}
		
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, ConfigDocProcessEnum.TradeTestApplication);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Rejected, user, rejectReasonsList, "Trade Test Application Sent Back To Trade Test Center. Current Sign off added to previoulsy added sign offs");
	}
	
	/* Sends back to Region Quality Assuror To Re-Upload Documents against ARPL trade test application. */
	public void sendToRegionQualityAssurorToReUpload(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
//		companyLearnersTradeTest.setNambDecision(null);
//		companyLearnersTradeTest.setNambSubmissionDate(null);
//		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaSix);
		update(companyLearnersTradeTest);
		
		// should be no open tasks
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
			userList.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
		}
		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.ARPLTradeTestDocumentUpload, null, userList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, ConfigDocProcessEnum.ARPLTradeTestDocumentUpload);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Rejected, user, rejectReasonsList, "ARPL Trade Test Application Sent Back Region Quality Assuror To Upload Required Documents.");
		// send notification to initiator to provide docs
	}
	
	/* Sends back to Admin To Re-Upload Documents against trade test application. */
	public void sendToAdminToReUpload(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersTradeTest.setNambDecision(null);
		companyLearnersTradeTest.setNambSubmissionDate(null);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaThree);
		update(companyLearnersTradeTest);
		
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getAdminUser() != null && companyLearnersTradeTest.getAdminUser().getId() != null) {
			userList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
		}
		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.TradeTestDocumentReUpload, null, userList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, ConfigDocProcessEnum.TradeTestDocumentReUpload);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Rejected, user, rejectReasonsList, "Trade Test Application Sent Back Administrator To Upload Required Documents.");
		// send notification to initiator to provide docs
	}
	
	/* Complete task for ARPL doc upload */
	public void completeDocUploadTask(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), ConfigDocProcessEnum.ARPLTradeTestDocumentUpload);
		TasksService.instance().completeTask(task);
		sendArplTradeTestToAdmin(companyLearnersTradeTest, user);
	}
	
	/* Complete task for trade Test doc upload */
	public void completeTradeTestDocUploadTask(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), ConfigDocProcessEnum.TradeTestDocumentReUpload);
		TasksService.instance().completeTask(task);
		sendTradeTestToAdmin(companyLearnersTradeTest, user);
	}
	
	/* Starts Work Flow: ARPLTradeTestNambRejection */
	public void sendToRegionQualityAssurorNambRejection(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}		
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
//		companyLearnersTradeTest.setNambDecision(null);
//		companyLearnersTradeTest.setNambSubmissionDate(null);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaSix);
		update(companyLearnersTradeTest);
		
		// should be no open tasks
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
			userList.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
		}
		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.ARPLTradeTestNambRejection, null, userList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, ConfigDocProcessEnum.ARPLTradeTestNambRejection);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Rejected, user, rejectReasonsList, "ARPL Trade Test Application Sent Back Region Quality Assuror To Upload Required Documents and submit to Manager for approval.");
		// send notification to initiator to provide docs
	}
	
	/* Submit Task to Quality Assurance Manager. Work Flow: ARPLTradeTestNambRejection  */
	public void completeTaskArplRejection(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		List<Users> managerQA = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		if ( managerQA== null || managerQA.size() == 0 ) {
			throw new Exception("Unable to locate Quality Assurance: Manager. Contact Support!");
		}
		TasksService.instance().findNextInProcessAndCreateTask(user, task, managerQA, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), ConfigDocProcessEnum.ARPLTradeTestNambRejection);
	}
	
	/* Rejects task back to region Quality Assurance. Work Flow: ARPLTradeTestNambRejection */
	public void rejectArplRejectionToRegionQualityAssurance(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task, List<RejectReasons> rejectionReasonsList) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		List<Users> usersList = new ArrayList<>();
		if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
			usersList.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
		}
		TasksService.instance().findPreviousInProcessAndCreateTask(user, task, usersList);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectionReasonsList, user, ConfigDocProcessEnum.ARPLTradeTestNambRejection);
	}
	
	/* Final Rejection AROK trade Test by QA Manager.  Work Flow: ARPLTradeTestNambRejection */
	public void finalRejectApplicationQaManager(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task, List<RejectReasons> rejectReasonsList) throws Exception{
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		// confirm competence
		companyLearnersTradeTest.setStatus(ApprovalEnum.Rejected);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(user);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), ConfigDocProcessEnum.ARPLTradeTestNambRejection);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, task.getWorkflowProcess());
		
		/* Notification Rejection */
		sendNotificationArplApplicationEmail(16, companyLearnersTradeTest, rejectReasonsList);
	}
	
	/* Complete task for ARPL Rejection upload */
	public void approveArplNambRejection(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception{
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), ConfigDocProcessEnum.ARPLTradeTestNambRejection);
		TasksService.instance().completeTask(task);
		sendArplTradeTestToAdmin(companyLearnersTradeTest, user);
	}
	
	/* Sends back to Admin User */
	public void sendArplTradeTestToAdmin(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setNambDecision(null);
		companyLearnersTradeTest.setNambSubmissionDate(null);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaSix);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getAdminUser() != null) {
			userList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
		}
		TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(8, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.ARPLTradeTestApplication, null, "", true, userList);
	}
	
	/* Sends back to Admin User */
	public void sendTradeTestToAdmin(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		companyLearnersTradeTest.setTestCenterSubmitted(true);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaThree);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTradeTest.getAdminUser() != null) {
			userList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
		}
		TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(4, "", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.TradeTestApplication, null, "", true, userList);
	}
	
	/* ARPL approved by NAMB */
	public void completeNambApproval(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<Doc> supportingDocs) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		
		// region client service admin
		List<Users> usersAssignedToList = new ArrayList<>();
		if (companyLearnersTradeTest.getClientServiceAdminUser() != null) {
			usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		}else {
			companyLearnersTradeTest.setClientServiceAdminUser(user);
			update(companyLearnersTradeTest);
			usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
		}
		
		if (usersAssignedToList.size() == 0) {			
			throw new Exception("Unable to locate Region Client Service Admin. Contact Support!");
		}
		
		// creates / add supporting documents 
		if (supportingDocs != null) {
			for (Doc doc : supportingDocs) {
				if (doc.getId() != null) {
					doc.setTargetClass(companyLearnersTradeTest.getClass().getName());
					doc.setTargetKey(companyLearnersTradeTest.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				}
			}
		}
		
		companyLearnersTradeTest.setStatus(ApprovalEnum.QualificationObtained);
		companyLearnersTradeTest.setApprovedUser(user);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setCertificateCollected(false);
		update(companyLearnersTradeTest);
		
		// update company learner information
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			CompanyLearners companyLearners = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
			if (companyLearners != null) {
				companyLearners.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
				companyLearners.setStatus(ApprovalEnum.Completed);
				companyLearners.setCertificateNumber(companyLearnersTradeTest.getCertificateNumber());
				companyLearners.setDateQualificationObtained(companyLearnersTradeTest.getDateCertified());
				companyLearnersService.update(companyLearners);
			}
		}
		
		// close tasks from previous work flow , clear rejection reasons from previous work flow
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), ConfigDocProcessEnum.ARPLTradeTestApplication);
		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.ARPLTradeTestNambApproval, null, usersAssignedToList);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Approved, user, null, "NAMB Approved, Certificate / Qualification Obtained");
		
		/* Notification Rejection */
		sendNotificationArplApplicationEmail(11, companyLearnersTradeTest, null);
	}
	
	/* Trade Test approved by NAMB */
	public void completeNambApprovalTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, List<Doc> supportingDocs) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		
		List<IDataEntity> updateList = new ArrayList<>();
		
		// region client service admin
		List<Users> usersAssignedToList = new ArrayList<>();
		if (companyLearnersTradeTest.getAdminUser() != null) {
			usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
		}
		if (usersAssignedToList.size() == 0) {
			throw new Exception("Unable to locate Administrator. Contact Support!");
		}
		
		// creates / add supporting documents 
		if (supportingDocs != null) {
			for (Doc doc : supportingDocs) {
				if (doc.getId() != null) {
					doc.setTargetClass(companyLearnersTradeTest.getClass().getName());
					doc.setTargetKey(companyLearnersTradeTest.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				}
			}
		}
		
		companyLearnersTradeTest.setStatus(ApprovalEnum.QualificationObtained);
		companyLearnersTradeTest.setApprovedUser(user);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setCertificateCollected(false);
//		update(companyLearnersTradeTest);
		updateList.add(companyLearnersTradeTest);
		
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			CompanyLearners companyLearners = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
			if (companyLearners != null) {
				companyLearners.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
				companyLearners.setStatus(ApprovalEnum.Completed);
				companyLearners.setCertificateNumber(companyLearnersTradeTest.getCertificateNumber());
				companyLearners.setDateQualificationObtained(companyLearnersTradeTest.getDateCertified());
				updateList.add(companyLearners);
			}
		}
		
		if (updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		
		// close tasks from previous work flow , clear rejection reasons from previous work flow
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), ConfigDocProcessEnum.TradeTestApplication);
		
		TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.TradeTestNambApproval, null, usersAssignedToList);
		createNambHistoryLink(companyLearnersTradeTest, ApprovalEnum.Approved, user, null, "NAMB Approved, Certificate / Qualification Obtained");
		
		sendNotificationTradeTestApplicationEmail(7, companyLearnersTradeTest, null);
	}
	
	/* Creates NAMB History */
	public void createNambHistoryLink(CompanyLearnersTradeTest companyLearnersTradeTest, ApprovalEnum approvalEnum,  Users user, List<RejectReasons> rejectReasonsList, String additionalInformation) throws Exception{
		NambDecisionHistory nambDecisionHistory = new NambDecisionHistory(user, approvalEnum, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), additionalInformation.trim());
		NambDecisionHistoryService.instance().createNewHistoryEntry(user, nambDecisionHistory, rejectReasonsList);
	}
	
	public void documentValidiation(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		if (companyLearnersTradeTest.getDocs() != null && !companyLearnersTradeTest.getDocs().isEmpty()) {
			String errors = "";
			for (Doc doc : companyLearnersTradeTest.getDocs()) {
				
				if ((companyLearnersTradeTest.getCollectionEnum() != null && companyLearnersTradeTest.getCollectionEnum() == CollectionEnum.PersonalCollection) && (doc.getConfigDoc().getArplDocRequirements() != null && doc.getConfigDoc().getArplDocRequirements() == ArplDocRequirements.CollectionAcknowledgment)) {
					doc.getConfigDoc().setRequiredDocument(true);
				} else if (doc.getConfigDoc().getArplDocRequirements() != null && doc.getConfigDoc().getArplDocRequirements() == ArplDocRequirements.CollectionAcknowledgment) {
					doc.getConfigDoc().setRequiredDocument(false);
				} else if (doc.getConfigDoc().getArplDocRequirements() != null && doc.getConfigDoc().getArplDocRequirements() == ArplDocRequirements.ToolKit) {
					// Tool Kit doc check
					if (companyLearnersTradeTest.getQualification() != null && companyLearnersTradeTest.getQualification().getId() != null) {
						if (QualificationToolKitService.instance().countByQualificationId(companyLearnersTradeTest.getQualification().getId()) > 0) {
							doc.getConfigDoc().setRequiredDocument(true);
						} else {
							doc.getConfigDoc().setRequiredDocument(false);
						}
					}else {
						doc.getConfigDoc().setRequiredDocument(false);
					}
					
				}
				
				if (doc.getId() == null) {
					if ((doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) && (doc.getData() == null || doc.getData().length == 0)) {
						if (errors != "") {
							errors += ", " + doc.getConfigDoc().getName();
						} else {
							errors = doc.getConfigDoc().getName();
						}	
					} else if (doc.isRequired() && (doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						if (errors != "") {
							errors += ", " + doc.getConfigDoc().getName();
						} else {
							errors = doc.getConfigDoc().getName();
						}	
					}
				}
			}
			if (errors != "") {
				throw new Exception("Provide the following required documents before proceeding: " + errors);
			}
		}	
	}
	
	public void uploadDocuments(CompanyLearnersTradeTest companyLearnersTradeTest, Users user) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		
		if (companyLearnersTradeTest.getDocs() != null && companyLearnersTradeTest.getDocs().size() != 0) {
			for (Doc doc : companyLearnersTradeTest.getDocs()) {
				if (doc.getId() != null) {
					// build in doc upload
				}
			}
		}
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
	}
	
	public void uploadDocuments(CompanyLearnersTradeTest entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && task.getProcessRole() != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity,
			// task.getProcessRole());
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
			}

			// check if data not empty and creates document
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(CompanyLearners.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(CompanyLearners.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
		}
	}
	
	/* ARPL Certificate Collection Start */
	public void completeTask(CompanyLearnersTradeTest companyLearnersTradeTest, Users sessionUser, Tasks task, boolean canUpload, boolean canEdit) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (canUpload) {
			documentValidiation(companyLearnersTradeTest);
		}
		List<Users> recivers = new ArrayList<>();
		if (task.getFirstInProcess()) {
			if (companyLearnersTradeTest.getCreateUser() != null && companyLearnersTradeTest.getCreateUser().getId() != null) {
				recivers.add(usersService.findByKey(companyLearnersTradeTest.getCreateUser().getId()));
			}
		} else {
			if (companyLearnersTradeTest.getClientServiceAdminUser() != null && companyLearnersTradeTest.getClientServiceAdminUser().getId() != null) {
				recivers.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
			}
		}
		if (recivers.size() == 0) {
			throw new Exception("Unable to locate next user, contact support!");
		}
		if (canEdit || (task.getFirstInProcess() != null && task.getFirstInProcess())) {
			update(companyLearnersTradeTest);
			if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
				if (companyLearnersService == null) {
					companyLearnersService = new CompanyLearnersService();
				}
				CompanyLearners companyLearners = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
				if (companyLearners != null) {
					companyLearners.setCertificateNumber(companyLearnersTradeTest.getCertificateNumber());
					companyLearners.setDateQualificationObtained(companyLearnersTradeTest.getDateCertified());
					companyLearnersService.update(companyLearners);
				}
			}
		}
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), true, task, null, recivers);
		// send notification
		if (canUpload) {
			// send notification
		} else {
			// send notification
		}
	}
	
	public void completeTaskTradeTestNambApproval(CompanyLearnersTradeTest companyLearnersTradeTest, Users sessionUser, Tasks task, boolean canUpload, boolean canEdit) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (canUpload) {
			documentValidiation(companyLearnersTradeTest);
		}
		List<Users> recivers = new ArrayList<>();
		if (task.getFirstInProcess()) {
			if (companyLearnersTradeTest.getCreateUser() != null && companyLearnersTradeTest.getCreateUser().getId() != null) {
				recivers.add(usersService.findByKey(companyLearnersTradeTest.getCreateUser().getId()));
			}
		} else {
			if (companyLearnersTradeTest.getAdminUser() != null && companyLearnersTradeTest.getAdminUser().getId() != null) {
				recivers.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
			}
		}
		if (recivers.size() == 0) {
			throw new Exception("Unable to locate next user, contact support!");
		}
		if (canEdit || (task.getFirstInProcess() != null && task.getFirstInProcess())) {
			update(companyLearnersTradeTest);
			if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
				if (companyLearnersService == null) {
					companyLearnersService = new CompanyLearnersService();
				}
				CompanyLearners companyLearners = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
				if (companyLearners != null) {
					companyLearners.setCertificateNumber(companyLearnersTradeTest.getCertificateNumber());
					companyLearners.setDateQualificationObtained(companyLearnersTradeTest.getDateCertified());
					companyLearnersService.update(companyLearners);
				}
			}
		}
		TasksService.instance().findNextInProcessAndCreateTask("", sessionUser, companyLearnersTradeTest.getId(), CompanyLearnersTradeTest.class.getName(), true, task, null, recivers);
		// send notification
		if (canUpload) {
			// send notification
		} else {
			// send notification
		}
	}
	
	public void finalApproveCertificateCollection(CompanyLearnersTradeTest companyLearnersTradeTest, Users sessionUser, Tasks task) throws Exception {
		documentValidiation(companyLearnersTradeTest);
		
		List<IDataEntity> updateList = new ArrayList<>();
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(sessionUser);
		companyLearnersTradeTest.setCertificateCollected(true);
//		update(companyLearnersTradeTest);
		updateList.add(companyLearnersTradeTest);
		
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getId() != null) {
			// update company learner information
			CompanyLearners companyLearners = companyLearnersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getId());
			companyLearners.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
			companyLearners.setStatus(ApprovalEnum.Completed);
			companyLearners.setCompletionDate(getSynchronizedDate());
			updateList.add(companyLearners);
		}
		
		// updates batch list
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		
		TasksService.instance().completeTask(task);
		TasksService.instance().completeTaskByTargetKey(companyLearnersTradeTest.getClass().getName(), companyLearnersTradeTest.getId());
	}
	
	public void finalRejectArplApplication(Users user, Tasks tasks, CompanyLearnersTradeTest companyLearnersTradeTest, List<RejectReasons> rejectReasonsList) throws Exception{
		companyLearnersTradeTest.setStatus(ApprovalEnum.Rejected);
		companyLearnersTradeTest.setApprovalDate(getSynchronizedDate());
		companyLearnersTradeTest.setApprovedUser(user);
		update(companyLearnersTradeTest);
		TasksService.instance().completeTask(tasks);
		if (rejectReasonsList.size() != 0) {
			List<IDataEntity> createList = new ArrayList<>();
			createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess()));
			dao.createBatch(createList);
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), tasks.getWorkflowProcess());
		}
		sendNotificationArplApplicationEmail(3, companyLearnersTradeTest, rejectReasonsList);
	}
	
	public void locateNextInProgressArplTask(Users user, Tasks tasks, CompanyLearnersTradeTest companyLearnersTradeTest, boolean createIndividualTasks) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			List<Users> usersAssignedToList = new ArrayList<>();
			switch (companyLearnersTradeTest.getAprlProgress()) {
			case WithInitiatorOne:
				usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				break;
			case WithMersetaOne:
				// Region Client Services Administrator
				if (companyLearnersTradeTest.getClientServiceAdminUser() == null) {
					locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				} else {
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				}
				break;
			case WithInitiatorTwo:
				usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				break;
			case WithMersetaTwo:
				// Region Client Services Administrator
				if (companyLearnersTradeTest.getClientServiceAdminUser() == null) {
					locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				} else {
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				}
				break;
			case WithMersetaThree:
				// Region Client Services Administrator
				if (companyLearnersTradeTest.getClientServiceAdminUser() == null) {
					locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				} else {
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				}
				break;
			case WithMersetaFour:
				// Region Quality Assuror
				if (companyLearnersTradeTest.getQualityAssurorUser() == null) {
					locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				} else {
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				}
				break;
			case WithTestCenterOne:
				locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				//sendNotificationArplApplicationEmail(2, companyLearnersTradeTest, null);
				break;
			case WithMersetaFive:
				// Client Services (Region) Coordinator
				if (companyLearnersTradeTest.getCoordinatorUser() == null) {
					locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
				} else {
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getCoordinatorUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
				}
				break;
			case WithMersetaSix:
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, createIndividualTasks);
				break;
			}
		} else {
			List<Users> usersAssignedToList = new ArrayList<>();
			if (companyLearnersTradeTest.getTradeTestProgress() != null) {
				switch (companyLearnersTradeTest.getTradeTestProgress()) {
				case WithInitiator:
					// With Create User
					usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getCreateUser().getId()));
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);					
					break;
				case WithMersetaOne:
					// MerSETA Admin
					if (companyLearnersTradeTest.getAdminUser() == null) {
						locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
						// add notification
					} else {
						usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
						TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
					}
					break;
				case WithTestCenter:
					// SDP
					if (companyLearnersTradeTest.getTestCenterUser() == null) {
						locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
					}else {
						usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getTestCenterUser().getId()));
						TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
					}
					break;
				case WithMersetaTwo:
					// with Coordinator
					if (companyLearnersTradeTest.getCoordinatorUser() == null) {
						locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
					}else {
						usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getCoordinatorUser().getId()));
						TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
					}
					break;
				case WithMersetaThree:
					// MerSETA Admin (Should never be null)
					if (companyLearnersTradeTest.getAdminUser() == null) {
						locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);
					} else {
						usersAssignedToList.add(usersService.findByKey(companyLearnersTradeTest.getAdminUser().getId()));
						TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersAssignedToList, createIndividualTasks);
					}
					break;
				}
			} else {
				// fall back if not ARPL task
				locateNextInProcessRegionUsers(user, tasks, companyLearnersTradeTest, createIndividualTasks);			
			}
		}
	}
	
	public void assignNewAssessorModeratorToTradeTest(CompanyLearnersTradeTest tradeTest, AssessorModeratorApplication app, boolean assessorAdd) throws Exception {
		if (assessorModeratorApplicationService == null) {
			assessorModeratorApplicationService = new AssessorModeratorApplicationService();
		}
		
		
		if (assessorAdd) {
			boolean failedValidiation = false;
			if (tradeTest.getModeratorApplication() != null && tradeTest.getModeratorApplication().getId() != null) {
				if (tradeTest.getModeratorApplication().getUser() != null && tradeTest.getModeratorApplication().getUser().getId() != null) {
					failedValidiation = tradeTest.getModeratorApplication().getUser().getId().equals(app.getUser().getId());
				}
			}
			
			if (failedValidiation) {
				throw new Exception("Assignment Error! You can not assign current user as they are assigned as the moderator. Please select a different user to assign as the assessor. ");
			} else {
				tradeTest.setAssessorApplication(app);
			}
			
		} else {
			boolean failedValidiation = false;
			if (tradeTest.getAssessorApplication() != null && tradeTest.getAssessorApplication().getId() != null) {
				if (tradeTest.getAssessorApplication().getUser() != null && tradeTest.getAssessorApplication().getUser().getId() != null) {
					failedValidiation = tradeTest.getAssessorApplication().getUser().getId().equals(app.getUser().getId());
				}
			}
			
			if (failedValidiation) {
				throw new Exception("Assignment Error! You can not assign current user as they are assigned as the assessor. Please select a different user to assign as the moderator. ");
			} else {
				tradeTest.setModeratorApplication(app);
			}
		}
		update(tradeTest);
	}
	
	/**
	 * 1 - Acknowledgement email 
	 * 
	 * @param notificationIdentifyer
	 * @param companyLearnersTradeTest
	 * @param rejectReasonsList
	 * @throws Exception
	 */
	private void sendNotificationTradeTestApplication(Integer notificationIdentifyer, CompanyLearnersTradeTest companyLearnersTradeTest, List<RejectReasons> rejectReasonsList) throws Exception{
		
		if (usersService == null) {
			usersService = new UsersService();
		}
		String subject = "";
		String message = "";
		List<Users> notificationRecivers = new ArrayList<>();
		switch (notificationIdentifyer) {
		case 1:
			subject = "ACKNOWLEDGEMENT OF TRADE TEST APPLICATION ";
		//	mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear "+sdpFullname+", </p>"
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #EMAIL_RECEIVER_FULL_NAME#, </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			for (Users users : notificationRecivers) {
				GenericUtility.sendMail(users.getEmail(), subject, message, null);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 1 - ACKNOWLEDGEMENT OF TRADE TEST APPLICATION 
	 * 2 - acknowledgement notification to learner
	 * 3 - 
	 * @param notificationIdentifyer
	 * @param companyLearnersTradeTest
	 * @param rejectReasonsList
	 * @throws Exception
	 */
	private void sendNotificationTradeTestEmail(Integer notificationIdentifyer, CompanyLearnersTradeTest companyLearnersTradeTest, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		boolean sendNotification = false;
		boolean withAttachment = false;
		String subject = "";
		String message = "";
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		switch (notificationIdentifyer) {
		case 1:
			sendNotification = true;
			subject = "ACKNOWLEDGEMENT OF TRADE TEST APPLICATION ";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The merSETA acknowledges the trade test application for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
			// send to learner / submission user
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			break;
		case 2:
			// May be related to 1
			sendNotification = true;
			subject = "ACKNOWLEDGEMENT OF TRADE TEST APPLICATION: Case 2";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Case 2 </p>";
			// send to learner / submission user
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			break;
		case 3:
			sendNotification = true;
			subject = "acknowledgement notification and must include notification about making payment to Trade Test Centre & Readiness Date & serial number ";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Case 3 </p>";
			// send to test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			break;
		case 4:
			// An email will be sent to the Learner and the Region Quality Assuror to notify of test date
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			// send to learner / submission user
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			// Region quality Assurer
			notificationRecivers.addAll(locateRegionQualityAssuror(companyLearnersTradeTest));
			break;
		case 5:
			// Reminder to be sent to trade test centre contact person if no test date has been allocated with 3 working days – send to trade test centre contact email 
			sendNotification = true;
			subject = "Case 5";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Case 5 </p>";
			// send to test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			break;
		case 6:
			// If not allocated within 7 working days, an escalation notification to be sent to the Trade Test Centre contact person (as registered on system), Manager: Quality Assurance for an urgent intervention.
			sendNotification = true;
			subject = "Case 6";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Case 6 </p>";
			// send to test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			// send to QA manager
			// Find JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID
			employeeList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID);
			for (Users employee : employeeList) {
				if (determainIfUserCanBeAdded(notificationRecivers, employee)) {
					notificationRecivers.add(employee);
				}
			}
			employeeList = null;
			break;
		case 7:
			// When date and serial number received, LPM-TP-007 Level Test Date Notification and email to be issued and sent to the Trade Test Centre, Workplace and if applicable, Provider and Learner
			sendNotification = true;
			subject = "NOTIFICATION OF TRADE TEST DATE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been set for #TRADE_TEST_DATE#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
			// send to learner / submission user
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			// send to test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			break;
		case 8:
			// Learner and workplace/provider (both learner and one of the other) to be sent reminder email of date when they are supposed to go to Trade Test Centre (Sandra to send details) – 3 days before
			sendNotification = true;
			subject = "REMINDER: NOTIFICATION OF TRADE TEST DATE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be reminded that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been set for #TRADE_TEST_DATE#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
			// send to learner / submission user
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			break;
		case 9:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 10:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 11:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 12:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 13:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 14:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 15:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		case 16:
			sendNotification = true;
			subject = "";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">  </p>";
			break;
		default:
			sendNotification = false;
			break;
		}	
		if (sendNotification) {
			message = replaceStringsArplMessage(message, companyLearnersTradeTest);
			notifyUsers(subject, message, notificationRecivers, withAttachment, attachmentBeans);
		}	
	}
	
	/*
	 * 1 - ACKNOWLEDGEMENT OF TRADE TEST APPLICATION
	 * 2 - REMINDER: NOTIFICATION OF TRADE TEST DATE
	 * 3 - NOTIFICATION OF TRADE TEST RESULTS UPLOAD BY TRADE TEST CENTRE
	 * 4 - NOTIFICATION OF TRADE TEST RESULTS
	 * 5 - NOTIFICATION OF TRADE TEST RESULTS - COMPETENT
	 * 6 - NOTIFICATION OF TRADE TEST RESULTS – NOT YET COMPETENT
	 * 7 - NOTIFICATION OF TRADE TEST CERTIFICATE
	 */
	private void sendNotificationTradeTestApplicationEmail(Integer notificationIdentifyer, CompanyLearnersTradeTest companyLearnersTradeTest, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		boolean sendNotification = false;
		boolean withAttachment = false;
		String subject = "";
		String message = "";
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		switch (notificationIdentifyer) {
			case 1:
				subject = "ACKNOWLEDGEMENT OF TRADE TEST APPLICATION";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The merSETA acknowledges the trade test application for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 2:
				subject = "REMINDER: NOTIFICATION OF TRADE TEST DATE";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be reminded that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been set for #TRADE_TEST_DATE#. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 3:
				subject = "NOTIFICATION OF TRADE TEST RESULTS UPLOAD BY TRADE TEST CENTRE";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# have been uploaded by the trade test centre. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 4:
				subject = "NOTIFICATION OF TRADE TEST RESULTS";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# are being reviewed and official results will be provided within five business days. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 5:
				subject = "NOTIFICATION OF TRADE TEST RESULTS - COMPETENT";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) successfully passed the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the results and other supporting documents will now be submitted to the National Artisan Moderation Body (NAMB) for the certification process and this may take longer than 30 business days. Please note that if additional information is required, we will advise accordingly. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 6:
				subject = "NOTIFICATION OF TRADE TEST RESULTS – NOT YET COMPETENT";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) was not successful in passing the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
			case 7:
				subject = "NOTIFICATION OF TRADE TEST CERTIFICATE";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that the certificate for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) who successfully passed the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been issued by the National Artisan Moderation Body (NAMB). </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration </p>";
				// Notify Learner
				notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
				sendNotification = true;
				break;
		default:
			sendNotification = false;
			break;
		}	
		if (sendNotification) {
			message = replaceStringsArplMessage(message, companyLearnersTradeTest);
			notifyUsers(subject, message, notificationRecivers, withAttachment, attachmentBeans);
		}
	}
		

	/**
	 * 1 - Application Submission notification
	 * 2 - Original Documentation submission
	 * 3 - Unsuccessful application notification 
	 * 4 - Application Successful
	 * 5 - Notification of trade test date
	 * 6 - Email Reminder: Trade test date 
	 * 7 - Notification Uploaded results
	 * 8 - Notification Uploaded results
	 * 9 - Notification results not yet COMPETENT
	 * 10 - Notification results COMPETENT 
	 * 11 - Notification certificate issued by NAMB
	 * 12 - Email Reminder: no test date has been allocated with 3 working days
	 * 13 - Email Reminder: no test date has been allocated with 7 working days
	 * 14 - Email Reminder: capture the results within 3 working days from the last day of the test
	 * 15 - Email Reminder: capture the results within 7 working days from the last day of the test
	 * 16 - NAMB rejection - Manager final rejects
	 * 17 - Should be 4: notification on when date approved by MerSETA to provide original documents 
	 * 18 - Notification results not Absent / cancelled
	 * @param notificationIdentifyer
	 * @param companyLearnersTradeTest
	 * @param rejectReasonsList
	 * @throws Exception
	 */
	private void sendNotificationArplApplicationEmail(Integer notificationIdentifyer, CompanyLearnersTradeTest companyLearnersTradeTest, List<RejectReasons> rejectReasonsList) throws Exception{
		if (usersService == null) {
			usersService = new UsersService();
		}
		boolean sendNotification = false;
		boolean withAttachment = false;
		String subject = "";
		String message = "";
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		switch (notificationIdentifyer) {
		
		case 1:
			// Application Submission notification
			subject = "ACKNOWLEDGEMENT OF ARTISAN RECOGNITION OF PRIOR LEARNING APPLICATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The merSETA acknowledges the trade test application for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// Notify Learner
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 2:
			// Original Documentation submission
			subject = "ARTISAN RECOGNITION OF PRIOR LEARNING APPLICATION DOCUMENTATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that you are required to submit original learner registration and supporting documentation for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# at the #REGIONAL_OFFICE_DESCRIPTION# office within 30 business days from the date of this email notification. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// Notify: applicant or workplace/provider 
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 3:
			// Unsuccessful application notification
			subject = "ARTISAN RECOGNITION OF PRIOR LEARNING APPLICATION OUTCOME";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the RPL application for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has not been approved. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// notification to be sent to the learner on the unsuccessful application
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 4:
			// Application Successful
			subject = "ARTISAN RECOGNITION OF PRIOR LEARNING APPLICATION OUTCOME";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the RPL application for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been approved and will now proceed to the trade test centre to allocate a trade test date.. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// Notify: notification to be sent to the learner 
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 5:
			// Notification of trade test date
			subject = "NOTIFICATION OF TRADE TEST DATE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been set for #TRADE_TEST_DATE#.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// An email will be sent to the Learner and the Region Quality Assuror to notify of test date.
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			sendNotification = true;
			break;
		case 6:
			// Email Reminder: Trade test date
			subject = "REMINDER: NOTIFICATION OF TRADE TEST DATE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be reminded that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been set for #TRADE_TEST_DATE#.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// Learner and workplace/provider (both learner and one of the other) to be sent reminder 
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 7:
			// Notification Uploaded results
			subject = "NOTIFICATION OF TRADE TEST RESULTS UPLOAD BY TRADE TEST CENTRE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# have been uploaded by the trade test centre.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			sendNotification = true;
			break;
		case 8:
			// Notification Uploaded results
			subject = "NOTIFICATION OF TRADE TEST RESULTS";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# are being reviewed and official results will be provided within five business days.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			// email must be sent to the workplace/provider and learner 
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 9:
			// Notification results not yet COMPETENT
			subject = "NOTIFICATION TRADE TEST RESULTS – NOT YET COMPETENT";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) was not successful in passing the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			sendNotification = true;
			break;
		case 10:
			// Notification results COMPETENT
			subject = "NOTIFICATION TRADE TEST RESULTS - COMPETENT";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) successfully passed the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the results and other supporting documents will now be submitted to the National Artisan Moderation Body (NAMB) for the certification process and this may take longer than 30 business days. Please note that if additional information is required, we will advise accordingly. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			sendNotification = true;
			break;
		case 11:
			// Notification certificate issued by NAMB
			subject = "NOTIFICATION OF TRADE TEST CERTIFICATE";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that the certificate for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) who successfully passed the trade test for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has been issued by the National Artisan Moderation Body (NAMB).</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";	
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			sendNotification = true;
			break;
		case 12:
			// Email Reminder: no test date has been allocated with 3 working days
			subject = "REMINDER 1: TRADE TEST DATE ALLOCATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be reminded that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has not been allocated. Kindly allocate the date.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";	
			// test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			sendNotification = true;
			break;
		case 13:
			// Email Reminder: no test date has been allocated with 7 working days
			subject = "REMINDER 2: TRADE TEST DATE ALLOCATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be reminded that the trade test date for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# has still not been allocated. Kindly assist to urgently allocate a date for the learner.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";	
			sendNotification = true;
			// test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			// QA user assigned
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			// Find JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID
			employeeList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID);
			for (Users employee : employeeList) {
				if (determainIfUserCanBeAdded(notificationRecivers, employee)) {
					notificationRecivers.add(employee);
				}
			}
			employeeList = null;
			break;
		case 14:
			// Email Reminder: capture the results within 3 working days from the last day of the test
			subject = "NOTIFICATION 1: REMINDER FOR TRADE TEST CENTRE TO UPLOAD TRADE TEST RESULTS FOR LEARNER";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# have not yet been uploaded/captured on the NSDMS by the trade test centre.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";	
			sendNotification = true;
			// test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			employeeList = null;
			break;
		case 15:
			// Email Reminder: capture the results within 7 working days from the last day of the test
			subject = "NOTIFICATION 2: REMINDER FOR TRADE TEST CENTRE TO UPLOAD TRADE TEST RESULTS FOR LEARNER";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the trade test results for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# have not yet been uploaded/captured on the NSDMS by the trade test centre.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			sendNotification = true;
			// test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			// QA user assigned
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			// Find JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID
			employeeList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID);
			for (Users employee : employeeList) {
				if (determainIfUserCanBeAdded(notificationRecivers, employee)) {
					notificationRecivers.add(employee);
				}
			}
			employeeList = null;
			break;
		case 16:
			// NAMB rejection - Manager final rejects
			subject = "REVIEW OF SUPPORTING DOCUMENTATION TO SUBMIT RE-EVALUATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that we have done further review of the documentation submitted to support the RPL re-evaluation for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#. Kindly be advised that the application is unsuccessful.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the merSETA Head Office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			sendNotification = true;
			// test center 
			notificationRecivers.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP));
			break;
		case 17:
			// Approval on Date for original document submission
			subject = "ARTISAN RECOGNITION OF PRIOR LEARNING APPLICATION DOCUMENTATION";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that you are required to submit original learner registration and supporting documentation for #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) for the qualification/programme: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION# at the #REGIONAL_OFFICE_DESCRIPTION# on #ORGINAL_DOC_SUBMISSION_DATE#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION#. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getClientServiceAdminUser() != null && companyLearnersTradeTest.getClientServiceAdminUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
			}
			sendNotification = true;
			break;
		case 18:
			// Notification results Absent
			subject = "NOTIFICATION TRADE TEST RESULTS – ABSENT / CANCELLED";
			message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME# </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> We write to advise you that #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#) cancelled/was absent for the trade test: (#QUALIFICATION_CODE#) #QUALIFICATION_DESCRIPTION#.</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Should you require any assistance or further information, kindly contact the #REGIONAL_OFFICE_DESCRIPTION# office. </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Client Services </p>";
			notificationRecivers.addAll(learnerNotificationUsers(companyLearnersTradeTest));
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			}
			sendNotification = true;
			break;
		default:
			sendNotification = false;
			break;
		}	
		if (sendNotification) {
			message = replaceStringsArplMessage(message, companyLearnersTradeTest);
			notifyUsers(subject, message, notificationRecivers, withAttachment, attachmentBeans);
		}
	}

	/**
	 * @param subject
	 * @param message
	 * @param notificationRecivers
	 * @param attachmentBeans 
	 * @param withAttachment 
	 */
	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment, List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			}else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), null);
			}
		}
	}
	
	/**
	 * Locates Employees By Job Title ID
	 * 
	 * @param jobTitleId
	 * @return List<Users>
	 */
	public List<Users> locateEmployeeUsersByJobTitle(Long jobTitleId) {
		try {
			List<Users> employeeList = hostingCompanyEmployeesService.findUserByJobTitle(jobTitleId);
			return employeeList;
		} catch (Exception e) {
			logger.fatal(e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Locates Region Quality Assuror
	 * 
	 * @param companyLearnersTradeTest
	 * @return notificationRecivers
	 */
	public List<Users> locateRegionQualityAssuror(CompanyLearnersTradeTest companyLearnersTradeTest){
		List<Users> notificationRecivers = new ArrayList<>();
		try {
			// QA user assigned
			if (companyLearnersTradeTest.getQualityAssurorUser() != null && companyLearnersTradeTest.getQualityAssurorUser().getId() != null) {
				notificationRecivers.add(usersService.findByKey(companyLearnersTradeTest.getQualityAssurorUser().getId()));
			} else {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				Address address = null;
				
				if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer().getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getResidentialAddress().getId());
				}
				
				if (address != null) 
					notificationRecivers.addAll(hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID)));		
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return notificationRecivers;
	}

	/**
	 * Locates Learner notifications
	 * @param companyLearnersTradeTest
	 * @param notificationRecivers
	 */
	public List<Users> learnerNotificationUsers(CompanyLearnersTradeTest companyLearnersTradeTest ) {
		List<Users> notificationRecivers = new ArrayList<>();
		if (companyLearnersTradeTest.getLearner().getId().equals(companyLearnersTradeTest.getCreateUser().getId())) {
			notificationRecivers.add(companyLearnersTradeTest.getLearner());
		} else {
			notificationRecivers.add(companyLearnersTradeTest.getLearner());
			notificationRecivers.add(companyLearnersTradeTest.getCreateUser());
		}
		return notificationRecivers;
	}
	
	private boolean determainIfUserCanBeAdded(List<Users> usersList, Users newUser){
		// prevents duplicate users being added to notification
		boolean addUser = true;
		for (Users users : usersList) {
			if (users.getId().equals(newUser.getId())){
				addUser = false;
				break;
			}
		}
		return addUser;
	}
	
	public String replaceStringsArplSubject(String subject, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception{
		return subject;
	}
	
	/*
	 * Tags:   
	 */
	public String replaceStringsArplMessage(String msg, CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		
		if (usersService == null) {
			usersService = new UsersService();
		}
		
		/*
		 *  populate learner information
		 *  Tags: #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# #LEARNER_IDENTITY_NUMBER#
		 */
		Users learner = null;
		if (companyLearnersTradeTest.getLearner() != null && companyLearnersTradeTest.getLearner().getId() != null) {
			learner = usersService.findByKey(companyLearnersTradeTest.getLearner().getId());
		}
		msg = msg.replace("#LEARNER_FIRST_NAME#", ( (learner != null && learner.getId() != null) ? learner.getFirstName().trim() : "#UNABLE TO LOCATE LEARNER FIRST NAME#" ) );
		msg = msg.replace("#LEARNER_LAST_NAME#", ( (learner != null && learner.getId() != null) ? learner.getLastName().trim() : "#UNABLE TO LOCATE LEARNER LAST NAME#" ) );
		msg = msg.replace("#LEARNER_IDENTITY_NUMBER#", ( (learner != null && learner.getId() != null) ? FixTaskDataService.instance().anIDNumber(learner) : "#UNABLE TO LOCATE LEARNER IDENTITY NUMBER#" ) );
		learner = null;
		
		/*
		 * populate qualification
		 * Tags: #QUALIFICATION_CODE# #QUALIFICATION_DESCRIPTION#
		 */
		Qualification qualification = null;
		if (companyLearnersTradeTest.getQualification() != null && companyLearnersTradeTest.getQualification().getId() != null) {
			qualification = QualificationService.instance().findByKey(companyLearnersTradeTest.getQualification().getId());
		}
		msg = msg.replace("#QUALIFICATION_CODE#", ( (qualification != null && qualification.getId() != null) ? qualification.getCodeString().trim() : "#UNABLE TO LOCATE QUALIFICATION CODE#" ) );
		msg = msg.replace("#QUALIFICATION_DESCRIPTION#", ( (qualification != null && qualification.getId() != null) ? qualification.getDescription().trim() : "#UNABLE TO LOCATE QUALIFICATION DESCRIPTION#" ) );
		qualification = null;
		
		/*
		 * Locate region
		 * Tags: #REGIONAL_OFFICE_DESCRIPTION#
		 */
		Address address = null;
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			// if initiator is the learner as well
			if (companyLearnersTradeTest.getLearner().getId().equals(companyLearnersTradeTest.getCreateUser().getId())) {
				address = AddressService.instance().findByKey(companyLearnersTradeTest.getLearner().getResidentialAddress().getId());
			} else if (companyLearnersTradeTest.getCompanyEmployer() != null && companyLearnersTradeTest.getCompanyEmployer().getId() != null) {
				// Employer Address
				address = AddressService.instance().findByKey(companyLearnersTradeTest.getCompanyEmployer().getResidentialAddress().getId());
			}else {
				// fail safe: learner address
				address = AddressService.instance().findByKey(companyLearnersTradeTest.getLearner().getResidentialAddress().getId());
			}
		} else {
			if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getResidentialAddress().getId());
			}
		}
		RegionTown rt = null;
		if (address != null && address.getId() != null && address.getTown() != null) {
			rt = RegionTownService.instance().findByTown(address.getTown());
		}
		msg = msg.replace("#REGIONAL_OFFICE_DESCRIPTION#", ( (rt != null && rt.getId() != null && rt.getRegion() != null) ?  rt.getRegion().getDescription().trim() : "#UNABLE TO LOCATE REGION#" ) );
		rt = null;
		address = null;
		
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getDateToProvideOriginalCopies() != null) {
			msg = msg.replace("#ORGINAL_DOC_SUBMISSION_DATE#", ( (companyLearnersTradeTest != null && companyLearnersTradeTest.getId() != null && companyLearnersTradeTest.getDateToProvideOriginalCopies() != null) ?  HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateToProvideOriginalCopies()) : "#UNABLE TO LOCATE SUBMISSION DATE#" ) );
		}
		
		/*
		 * Trade Test information
		 * Tags: #TRADE_TEST_DATE#
		 */
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getDateOfTest() != null) {
			msg = msg.replace("#TRADE_TEST_DATE#", ( (companyLearnersTradeTest != null && companyLearnersTradeTest.getId() != null && companyLearnersTradeTest.getDateOfTest() != null) ?  HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateOfTest()) : "#UNABLE TO TRADE TEST DATE#" ) );
		}
		
		return msg;
	}
	 
	
	public void sendTradeTestAcknolegmnet(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Address address=AddressService.instance().findByKey(companyLearnersTradeTest.getPreferredTrainingCenter().getResidentialAddress().getId());
		String strAddress=address.getAddressLine1()+", "+address.getAddressLine2()+", "+address.getAddressLine3()+", "+address.getTown().getDescription();
		String subject = "Trade Test Applications Acknowledgement ".toUpperCase();
		String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +u.getFirstName() + " " + u.getLastName() + " </p>" 
		
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "Kindly ensure to take note of the details of the trade test date as stipulated hereunder:" 
				+ "</p>" 
				
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "<b>Readiness Date:</b>"+HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getLearnerReadinessDate())+""
				+ "<b>Trade Test Centre:</b>"+companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName()+""
				+ "<b>Address/Venue:</b>+"+strAddress+""
				+ "<b>Serial Number:</b>"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours in Skills Development,"
				+ "<b>Readiness Date:</b>"+HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getLearnerReadinessDate())+"<br/>"
				+ "<b>Trade Test Centre:</b>"+companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName()+"<br/>"
				+ "<b>Address/Venue:</b>+"+strAddress+"<br/>"
				+ "<b>Serial Number:</b><br/>"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Client Service Administrator"
				+ "</p>";
		GenericUtility.sendMail(u, subject, msg, null);
	}
	
	public List<Users> findRegionQualityAssuror(Company company) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
		if (roles != null) {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		} else {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegion(company.getResidentialAddress().getTown());
		}
		return list;
	}
	
	public List<Users> findRegionQualityAssurorByProviderApplication(TrainingProviderApplication trainingProviderApplication) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		companyService= new CompanyService();
		RolesService rolesService = new RolesService();
		
		Company company = companyService.findByKey(trainingProviderApplication.getCompany().getId());
		companyService.resolveCompanyAddresses(company);
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
		if (roles != null) {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		} else {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegion(company.getResidentialAddress().getTown());
		}
		return list;
	}
	
	public List<Users> findRegionJobTitle(Company company, Long constants) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(constants);
		if (roles != null) {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		} else {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegion(company.getResidentialAddress().getTown());
		}
		return list;
	}
	
	public List<Users> findRegionJobTitleByAddress(Address address, Long constants) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(constants);
		if (roles != null) {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(address.getTown(), roles);
		} else {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegion(address.getTown());
		}
		return list;
	}
	
	public List<Users> findRegionJobTitleByTown(Town town, Long constants) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(constants);
		if (roles != null) {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(town, roles);
		} else {
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegion(town);
		}
		return list;
	}
	
	public List<CompanyLearnersTradeTest> companyLearnersTradeTestByLearnerId(Users user, TradeTestTypeEnum tradeTestTypeEnum) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.companyLearnersTradeTestByLearnerId(user, tradeTestTypeEnum));
	}
	
	public void contactSupport(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			u.setEmail(emailNotificiations);
			break;
		}
		Address address=AddressService.instance().findByKey(companyLearnersTradeTest.getPreferredTrainingCenter().getResidentialAddress().getId());
		RegionTown rt = RegionTownService.instance().findByTown(companyLearnersTradeTest.getPreferredTrainingCenter().getResidentialAddress().getTown());
		String strAddress=address.getAddressLine1()+", "+address.getAddressLine2()+", "+address.getAddressLine3()+", "+address.getTown().getDescription();
		String subject = "Trade Test Applications Support ".toUpperCase();
		String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +u.getFirstName() + " " + u.getLastName() + " </p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "<b>Note: There is no Quality Assuror assigned to the specified region.</b>" +"\n" 
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "<b>Trade Test Centre:</b>"+companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName()+"\n"
				+ "<b>Address/Venue:</b>+"+strAddress+"\n"
				+ "<b>Region:</b>" + rt.getDescription() +"\n"
				+ "<b>Serial Number:</b>" +"\n"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "<b>Learner Name:</b>"+companyLearnersTradeTest.getLearner().getFirstName() + " " +  companyLearnersTradeTest.getLearner().getLastName() +"\n"
				+ "<b>Celphone Number :</b>" + companyLearnersTradeTest.getLearner().getCellNumber() +"\n"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours in Skills Development,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Client Service Administrator"
				+ "</p>";
		GenericUtility.sendMail(u, subject, msg, null);

	}
	
	public void sendTradeTestResultsNofication(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
			Address address=AddressService.instance().findByKey(companyLearnersTradeTest.getPreferredTrainingCenter().getResidentialAddress().getId());
			String strAddress=address.getAddressLine1()+", "+address.getAddressLine2()+", "+address.getAddressLine3()+", "+address.getTown().getDescription();
			String subject = "Trade Test results".toUpperCase();
			List<Users> userList=new ArrayList<>();
			userList.add(companyLearnersTradeTest.getCreateUser());//SDP
			userList.add(companyLearnersTradeTest.getCompanyLearners().getUser());//Learner
			for(Users u:userList)
			{
				String title="";
				if(u.getTitle() !=null){title=u.getTitle().getDescription();}
				String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" " +u.getFirstName() + " " + u.getLastName() + " </p>" 
				
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
						+ "Be informed that the Trade Test Results have been uploaded for verification, "
						+ "you can expect the official results within 5 working days." 
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Yours in Skills Development,"
						+ "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Client Service Administrator"
						+ "</p>";
				GenericUtility.sendMail(u, subject, msg, null);
			}
	}
	public void sendLPMTP007(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		List<Users> userList=new ArrayList<>();
		userList.add(companyLearnersTradeTest.getCreateUser());//SDP
		userList.add(companyLearnersTradeTest.getCompanyLearners().getUser());//Learner
		Address address=AddressService.instance().findByKey(companyLearnersTradeTest.getPreferredTrainingCenter().getResidentialAddress().getId());
		String strAddress=address.getAddressLine1()+", "+address.getAddressLine2()+", "+address.getAddressLine3()+", "+address.getTown().getDescription();
		for(Users user:userList){
			String title="";
			if(user.getTitle() !=null){title=user.getTitle().getDescription();}
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			params.put("company_id", companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
			params.put("user_id",user.getId());
			params.put("companyLearnersTradeTest",companyLearnersTradeTest);
			params.put("address",strAddress);
			params.put("workshopNum","");//To be fixed
			byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-007-Level-Test-Date-Notification.jasper", params);
			
			String subject = "Level Test Date Notification".toUpperCase();
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" "+user.getTitle().getDescription()+" "+user.getFirstName()+" "+user.getLastName()+",</p>"
	
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please find the attached Trade Test Date Latter"
					+ "</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours sincerely,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "<b>Client Service Administrator</b>"
					+ "</p>";
	
			GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Level_Test_Date_Notification.pdf", "pdf", null);
		}
	}
	
	public void sendTradeTestNotYetCompetentNofication(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		String subject = "Trade Test results".toUpperCase();
		String leaner=companyLearnersTradeTest.getCompanyLearners().getUser().getFirstName()+" "+companyLearnersTradeTest.getCompanyLearners().getUser().getLastName();
		List<Users> userList=new ArrayList<>();
		userList.add(companyLearnersTradeTest.getCreateUser());//SDP
		userList.add(companyLearnersTradeTest.getCompanyLearners().getUser());//Learner
		List<Users> clientServicesList =findRegionClientServices(companyLearnersTradeTest.getCompanyLearners().getEmployer());
		if(clientServicesList !=null && clientServicesList.size()>0) {
			userList.addAll(clientServicesList);
		}
		for(Users u:userList)
		{
			String title="";
			if(u.getTitle() !=null){title=u.getTitle().getDescription();}
			String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" " +u.getFirstName() + " " + u.getLastName() + " </p>" 
			
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
					+ "Be informed that the learner("+leaner+") is not yet competent."
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours in Skills Development,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Client Service Administrator"
					+ "</p>";
			GenericUtility.sendMail(u, subject, msg, null);
		}
	}
	
	public void sendTradeTestCompetentNofication(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		String subject = "Trade Test results".toUpperCase();
		String leaner=companyLearnersTradeTest.getCompanyLearners().getUser().getFirstName()+" "+companyLearnersTradeTest.getCompanyLearners().getUser().getLastName();
		List<Users> userList=new ArrayList<>();
		userList.add(companyLearnersTradeTest.getCreateUser());//SDP
		userList.add(companyLearnersTradeTest.getCompanyLearners().getUser());//Learner
		List<Users> clientServicesList =findRegionClientServices(companyLearnersTradeTest.getCompanyLearners().getEmployer());
		if(clientServicesList !=null && clientServicesList.size()>0) {
			userList.addAll(clientServicesList);
		}
		for(Users u:userList)
		{
			String title="";
			if(u.getTitle() !=null){title=u.getTitle().getDescription();}
			String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+title+" " +u.getFirstName() + " " + u.getLastName() + " </p>" 
			
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
					+ "Be informed that the learner("+leaner+") is not yet competent."
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Yours in Skills Development,"
					+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Client Service Administrator"
					+ "</p>";
			GenericUtility.sendMail(u, subject, msg, null);
		}
	}
	
	public List<Users> findRegionClientServices(Company company) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public byte[] getDeclarationFormBytes(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception {
		    CompanyLearnersService companyLearnersService=new CompanyLearnersService();
			String trade="";
			String tradeLevel="";
			String minTime="";
			String maxTime="";
			/*companyLearnersService.locateCorrectDesignatedTradeLevel(companyLearnersTradeTest, companyLearnersTradeTest.getCompanyLearners());
			if(companyLearnersTradeTest.getCompanyLearners() !=null 
			&& companyLearnersTradeTest.getCompanyLearners().getQualification()!=null
			&& companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade() !=null){
				trade=companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade().getDescription();
			}
			if(companyLearnersTradeTest.getDesignatedTradeLevel() !=null){
				tradeLevel=companyLearnersTradeTest.getDesignatedTradeLevel().getDescription();
				minTime=companyLearnersTradeTest.getDesignatedTradeLevel().getMinweeks()+" WEEKS";
				maxTime=companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks()+" WEEKS";
			}*/
			
			Map<String, Object> params = new HashMap<String, Object>();
			List<ModularTrainingBean> modularTrainingList=new ArrayList<>();//To Do
			JasperService.addLogo(params);
			String documentTitle="Trade Test Declaration Form";
			String documentNumber="LPM-FM-021";
			params.put("companyLearnersTradeTest", companyLearnersTradeTest);
			params.put("document_title",documentTitle);
			params.put("document_number",documentNumber);
			params.put("trade",trade);
			params.put("trade_level",tradeLevel);
			params.put("min_time",minTime);
			params.put("max_time",maxTime);
			params.put("ModularTrainingBeanDataSource", new JRBeanCollectionDataSource(modularTrainingList));
			byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-021_Declaration.jasper", params);
			return bites;
	}
	
	@SuppressWarnings("unchecked")
	public CompanyLearnersTradeTest findLastByTradeTypeLearnerQualification(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId) throws Exception {
		return dao.findLastByTradeTypeLearnerQualification(tradeTestType, learnersId, qualificationId);
	}
	
	public CompanyLearnersTradeTest findLastByTradeTypeLearnerQualificationAndDesignatedTradeLevel(TradeTestTypeEnum tradeTestType, Long learnersId, Long qualificationId, Long designatedTradeLevelId) throws Exception {
		return dao.findLastByTradeTypeLearnerQualificationAndDesignatedTradeLevel(tradeTestType, learnersId, qualificationId, designatedTradeLevelId);
	}
	
	public Integer countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(TradeTestTypeEnum tradeTestType, CompanyLearners companyLearner, DesignatedTradeLevel designatedTradeLevel, CompetenceEnum competenceEnum, ApprovalEnum approvalEnum) throws Exception {
		return dao.countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(tradeTestType, companyLearner.getId(), designatedTradeLevel.getId(), competenceEnum, approvalEnum);
	}
	
	public void generateAndSendLPMFM009(CompanyLearnersTradeTest companyLearnersTradeTest, Users learner, List<Users> emailRecivers) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Company employer = new Company();
		Users employerContactPerson = new Users();
		prepareEmpoyerAndContactPerson(companyLearnersTradeTest, companyLearnersTradeTest.getEmployer(), employer, employerContactPerson);
		
		String trade="N/A";
		if(companyLearnersTradeTest.getQualification() !=null && companyLearnersTradeTest.getQualification().getId() !=null) {
			trade=companyLearnersTradeTest.getQualification().getDescription();
		}
		OfoCodes ofoCodes = new OfoCodes();
		UsersService usersService = new UsersService();
		DetailsOfExperienceArplService detailsOfExperienceArplService=new DetailsOfExperienceArplService();
		DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
		List<DetailsOfExperienceArpl> detailsOfExperienceArplList=detailsOfExperienceArplService.findByCompanyLearnersTradeTest(companyLearnersTradeTest.getId());
		List<DetailsOfTrainingArpl> detailsOfTrainingArplList=detailsOfTrainingArplService.findByCompanyLearnersTradeTest(companyLearnersTradeTest.getId());
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		learner = usersService.findByKey(learner.getId());
		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest() == YesNoEnum.Yes) {
			attemptedTradeTest = true;
		}
		
		boolean flc = false;
		String flcComments = "";
		if (companyLearnersTradeTest.getFlcEnglish() != null && companyLearnersTradeTest.getFlcEnglish() == YesNoEnum.Yes) {
			flc = true;
			flcComments = "English";
		}
		if (companyLearnersTradeTest.getFlcMathsLit() != null && companyLearnersTradeTest.getFlcMathsLit() == YesNoEnum.Yes) {
			flc = true;
			if (flcComments.isEmpty()) {
				flcComments = "Mathematical Literacy";
			} else {
				flcComments += " And Mathematical Literacy";
			}
		}
		
		boolean boundByAgreement = false;
		boolean medicalDisorder = false;
		
//		if (companyLearnersTradeTest.getFlc()==YesNoEnum.Yes) {flc = true;}
		if (companyLearnersTradeTest.getLearnerAgreement()==YesNoEnum.Yes) {boundByAgreement = true;}
		if (companyLearnersTradeTest.getMedicalInfo()==YesNoEnum.Yes) {medicalDisorder = true;}
		
		
//		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);// need to change, add logic to change
		params.put("flcComments", flcComments);
		params.put("boundByAgreement", boundByAgreement);
		params.put("medicalDisorder", medicalDisorder);
		if (companyLearnersTradeTest.getOfoCode() != null) {
			params.put("ofo_codes", companyLearnersTradeTest.getOfoCode().getOfoCode());
			params.put("currentOccupation", companyLearnersTradeTest.getOfoCode().getDescription());
		}else {
			params.put("ofo_codes", "");
			params.put("currentOccupation", "");
		}
		// #{companylearnerstradetestUI.companylearnerstradetest.disability}
		// #{companylearnerstradetestUI.companylearnerstradetest.disabilityInfo}
		if (companyLearnersTradeTest.getDisability() != null && companyLearnersTradeTest.getDisability() == YesNoEnum.Yes) {
			params.put("haveDisability", true);
			params.put("natureOfDisability", (companyLearnersTradeTest.getDisabilityInfo() != null ? companyLearnersTradeTest.getDisabilityInfo().trim() : "N/A"));
		} else if (companyLearnersTradeTest.getDisability() != null && companyLearnersTradeTest.getDisability() == YesNoEnum.No) {
			params.put("haveDisability", false);
			params.put("natureOfDisability", "N/A");
		} else {
			params.put("haveDisability", false);
			params.put("natureOfDisability", "N/A");
		}
		params.put("DetailsOfExperienceDataSource", new JRBeanCollectionDataSource(detailsOfExperienceArplList));
		params.put("DetailsOfTrainingDataSource", new JRBeanCollectionDataSource(detailsOfTrainingArplList));
		params.put("trade", trade);
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-009-TradeTestApplicationForm.jasper", params);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);
		
		for (Users user : emailRecivers) {
			String subject = "ARPL TRADE TEST APPLICATION FORMS";
			String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please find the the attched form:  " + "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";
			GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), subject, mssg, attachmentBeans, null);
		}
	}
	
	public CompanyLearnersTradeTest checkDocRequired(CompanyLearnersTradeTest companyLearnersTradeTest, Tasks task) throws Exception {
		if (companyLearnersTradeTest.getDocs() != null) {
			for (Doc doc : companyLearnersTradeTest.getDocs()) {
				if (doc.getId() == null) {	
					/*
					 *  check one business rules
					 *  1) User is South African citizen
					 *  2) Previous trade test center documents
					 */
					if (task != null && task.getFirstInProcess() != null && task.getFirstInProcess()) {
						// learner not a RSA citizen
						if (companyLearnersTradeTest.getLearner() != null && (companyLearnersTradeTest.getLearner().getRsaIDNumber() == null || companyLearnersTradeTest.getLearner().getRsaIDNumber().isEmpty())) {
							if (companyLearnersTradeTest.getLearner().getNationality() == null || !companyLearnersTradeTest.getLearner().getNationality().getId().equals(HAJConstants.SOUTH_AFRICAN_NATIONALITY)) {
								if ((doc.getConfigDoc().getArplDocRequirements() != null && doc.getConfigDoc().getArplDocRequirements() == ArplDocRequirements.PermitVISA)) {
									doc.setRequired(true);
								} else {
									doc.setRequired(false);
								}
							}	
						}
						// provide previous trade test center results
						if (companyLearnersTradeTest.getAttemptNumber() == null || companyLearnersTradeTest.getAttemptNumber() > 1) {
							if ((doc.getConfigDoc().getArplDocRequirements() != null && doc.getConfigDoc().getArplDocRequirements() == ArplDocRequirements.TradeTestResults)) {
								doc.setRequired(true);
							}
						}
					}
				}
			}
		}
		return companyLearnersTradeTest;
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, Boolean onhold) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeArplProgressAndOnHold(tradeTestTypeEnum, aprlProgressEnum, onhold);
	}
	
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, Boolean onhold) throws Exception {
		return dao.findCompanyLearnersTradeTestByTypeArplProgressAndOnHold(tradeTestTypeEnum, aprlProgressEnum, onhold);
	}
	
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		return dao.findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided);
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided, todayDate);
	}
	
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		return dao.findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided, todayDate);
	}
	
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided, todayDate);
	}
	
	public List<CompanyLearnersTradeTest> findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided, Date todayDate) throws Exception {
		return dao.findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided, todayDate);
	}
		
	
	public void downloadCompanyLearnersTradeTestDesignatedTrade(CompanyLearnersTradeTest companyLearnersTradeTest, Users sessionUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		companyLearnersTradeTest = dao.findByKey(companyLearnersTradeTest.getId());
		Users leaner = companyLearnersTradeTest.getLearner();
		Company company = companyLearnersTradeTest.getCompanyLearners().getCompany();
		
		List<ModularTrainingBean> list = new ArrayList<>();
		List<DesignatedTradeLevelItems> items = DesignatedTradeLevelItemsService.instance().findByDesignatedTradeLevel(companyLearnersTradeTest.getDesignatedTradeLevel().getId());
		if (items.isEmpty()) {
			for (int i = 0; i < 11; i++) {
				ModularTrainingBean mtb = new ModularTrainingBean("", "" , "" , "" , "", "");
				list.add(mtb);
			}
		} else {
			for (DesignatedTradeLevelItems designatedTradeLevelItems : items) {
				ModularTrainingBean mtb = new ModularTrainingBean(designatedTradeLevelItems.getDescription().trim(), designatedTradeLevelItems.getCode().trim() , "" , "" , "", "");
				list.add(mtb);
			}	
		}
			
		String aprenticeship = leaner.getFirstName().trim() + " " + leaner.getLastName().trim();
		String startDate = "This is start_date";
		String endDate = "This is a end_date";
		String minTime = "This is min_time";
		String maxTime = "This is a max_time";
		String documentTitle = "This is document_title";
		String documentNumber = "LPM-FM-021";
		
		if (companyLearnersTradeTest.getDateOfTest() != null) {
			startDate = HAJConstants.sdfDDMMYYYY2.format(GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateOfTest()));
		} else {
			startDate = "";
		}
		
		if (companyLearnersTradeTest.getDateOfTestToDate() != null) {
			endDate = HAJConstants.sdfDDMMYYYY2.format(GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateOfTestToDate()));
		} else {
			endDate = "";
		}
		
		if (companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentNumber() != null && !companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentNumber().isEmpty()) {
			documentNumber = companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentNumber().trim();
		} else {
			documentNumber = "";
		}
		
		if (companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentTitle() != null && !companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentTitle().isEmpty()) {
			documentTitle = companyLearnersTradeTest.getDesignatedTradeLevel().getDocumentTitle().trim();
		} else {
			documentTitle = "";
		}
		
		if (companyLearnersTradeTest.getDesignatedTradeLevel().getMinweeks() != null) {
			minTime = companyLearnersTradeTest.getDesignatedTradeLevel().getMinweeks().toString() + " Weeks";
		}else {
			minTime = "";
		}
		
		if (companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks() != null) {
			maxTime = companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks().toString() + " Weeks";
		}else {
			maxTime = "";
		}
			
		params.put("trade", companyLearnersTradeTest.getQualification().getDescription().trim());
		params.put("level", companyLearnersTradeTest.getDesignatedTradeLevel().getLevel().toString());
		params.put("aprenticeship", aprenticeship);
		params.put("leaner", leaner);
		params.put("company", company);
		params.put("contract_number", companyLearnersTradeTest.getSerialNumber().trim());
		
		params.put("start_date", startDate);
		params.put("end_date", endDate);
		params.put("min_time", minTime);
		params.put("max_time", maxTime);
		
		params.put("document_title", documentTitle);
		params.put("document_number", documentNumber);
		params.put("companyLearnersTradeTest", companyLearnersTradeTest);
		
		params.put("ModularTrainingBeanDataSource", new JRBeanCollectionDataSource(list));	
		JasperService js = new JasperService();
		String reportDownloadName = "TradeTestForm";
		if (companyLearnersTradeTest.getDesignatedTradeLevel().getReportDownloadName() != null && !companyLearnersTradeTest.getDesignatedTradeLevel().getReportDownloadName().isEmpty()) {
			reportDownloadName = companyLearnersTradeTest.getDesignatedTradeLevel().getReportDownloadName().trim();
		}
		js.createReportFromDBtoServletOutputStream("LPM-FM-021_Declaration.jasper", params, reportDownloadName + ".pdf");
	}
	
	public void downloadCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users sessionUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		companyLearnersTradeTest = dao.findByKey(companyLearnersTradeTest.getId());
		Users leaner = companyLearnersTradeTest.getLearner();
		Company company = companyLearnersTradeTest.getCompanyLearners().getCompany();
		List<ModularTrainingBean>list = new ArrayList<>();
		ModularTrainingBean mtb = new ModularTrainingBean("", "" , "" , "" , "", "");
		list.add(mtb);
		mtb = new ModularTrainingBean("", "" , "" , "" , "", "");
		list.add(mtb);
		String trade = "This is a trade description";
		String level = "This is a trade level";
		String aprenticeship = "This is aprenticeship";
		String contract_number = "This is a contract number";
		String start_date = "This is start_date";
		String end_date = "This is a end_date";
		String min_time = "This is min_time";
		String max_time = "This is a max_time";
		String document_title = "This is document_title";
		String document_number = "LPM-FM-021";
			
		params.put("trade", companyLearnersTradeTest.getQualification().getDescription().trim());
		params.put("level", companyLearnersTradeTest.getDesignatedTradeLevel().getLevel().toString());
		params.put("aprenticeship", aprenticeship);
		params.put("leaner", leaner);
		params.put("company", company);
		params.put("contract_number", companyLearnersTradeTest.getSerialNumber().trim());
		
		params.put("start_date", start_date);
		params.put("end_date", end_date);
		params.put("min_time", min_time);
		params.put("max_time", max_time);
		
		params.put("document_title", document_title);
		params.put("document_number", document_number);
		params.put("companyLearnersTradeTest", companyLearnersTradeTest);
		
		params.put("ModularTrainingBeanDataSource", new JRBeanCollectionDataSource(list));	
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-021_Declaration.jasper", params,"TradeTestForm.pdf");
	}
	
	public void downloadLPMFM007(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (usersService == null) {
			usersService = new UsersService();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String trade = "";
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getQualification() != null && companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade() != null) {
			trade = companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade().getDescription();
		}
		String currentOccupation = "N/A";
		String experience = "";
		String etqa = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		if (companyLearnersTradeTest.getCompanyLearners().getEmployer().getEtqa() != null) {
			etqa = companyLearnersTradeTest.getCompanyLearners().getEmployer().getEtqa().getDescription();
		}
		companyLearnersTradeTest.getCompanyLearners().getRegistrationNumber();

		if (companyLearnersTradeTest.getCompanyLearners().getUser().getEmploymentStatus() != null) {
			currentOccupation = companyLearnersTradeTest.getCompanyLearners().getUser().getEmploymentStatus().getFriendlyName();
		}

		List<WorkplaceBean> workplaces = new ArrayList<>();
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getCompany() != null) {
			Company company = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getCompany().getId());
			WorkplaceBean workplaceBean = new WorkplaceBean();
			workplaceBean.setName(company.getCompanyName());
			workplaceBean.setFrom(company.getCompanyName());
			workplaceBean.setTo(company.getCompanyName());
			workplaceBean.setDetailes(company.getCompanyName());

			workplaces.add(workplaceBean);
		}

		List<WorkplaceBean> providers = new ArrayList<>();
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer() != null) {
			Company company1 = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
			WorkplaceBean workplaceBean1 = new WorkplaceBean();
			workplaceBean1.setName(company1.getCompanyName());
			workplaceBean1.setFrom(company1.getCompanyName());
			workplaceBean1.setTo(company1.getCompanyName());
			workplaceBean1.setDetailes(company1.getCompanyName());

			providers.add(workplaceBean1);
		}

		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");

		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		if (employerContactPerson == null || employerContactPerson.getId() == null) {
			/* The inclusion of a contact person detail and email address is compulsory **/
			throw new Exception("No employer contact person found.");
		}

		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest().getFriendlyName().equalsIgnoreCase("Yes")) {
			attemptedTradeTest = true;
		}

		boolean flc = false;
		if (companyLearnersTradeTest.getFlc() != null && companyLearnersTradeTest.getFlc().getFriendlyName().equalsIgnoreCase("Yes")) {
			flc = true;
		}

		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();

		params.put("user", u);
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);
		params.put("ofo_codes", ofoCodes);
		params.put("currentOccupation", currentOccupation);
		params.put("experience", experience);
		params.put("workplacesReasonDataSource", new JRBeanCollectionDataSource(workplaces));
		params.put("providersDataSource", new JRBeanCollectionDataSource(providers));
		params.put("etqa", etqa);
		params.put("trade", trade);
		
		// populate RSA / ID Passport number
		if (learner.getRsaIDNumber() != null && !learner.getRsaIDNumber().trim().isEmpty() && learner.getRsaIDNumber().trim().length() == 13) {
			int lengthOfIdNumber  = learner.getRsaIDNumber().trim().length();
			for (int i = 0; i < lengthOfIdNumber; i++) {
				params.put("ID_PP_" + i, String.valueOf(learner.getRsaIDNumber().charAt(i)).trim());
			}
		} else if (learner.getPassportNumber() != null && !learner.getPassportNumber().trim().isEmpty()) {
			int lengthOfPassportNumber  = learner.getPassportNumber().trim().length();
			for (int i = 0; i < lengthOfPassportNumber; i++) {
				params.put("ID_PP_" + i, String.valueOf(learner.getPassportNumber().charAt(i)).trim());
			}
		}
		
		if(employer != null && employer.getLevyNumber() != null) {
			int lengthOfIdNumber  = employer.getLevyNumber().trim().length();
			for (int i = 0; i < lengthOfIdNumber; i++) {
				params.put("SarsLevyRegNo" + i, String.valueOf(employer.getLevyNumber().charAt(i)).trim());
			}
		}
		
		// populate Gender information
		boolean male = ((learner.getGender() != null &&  learner.getGender().getId() != null && learner.getGender().getId().equals(HAJConstants.GENDER_MALE_ID)) ? Boolean.TRUE : Boolean.FALSE);
		boolean female = ((learner.getGender() != null &&  learner.getGender().getId() != null && learner.getGender().getId().equals(HAJConstants.GENDER_FEMALE_ID)) ? Boolean.TRUE : Boolean.FALSE);
		
		// populate Equity information
		boolean african = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_BLACK_AFRICAN_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean indian = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_INDIAN_ASIAN_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean colored = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_COLOURED_ID) ? Boolean.TRUE : Boolean.FALSE));
		boolean white = ((learner.getEquity() != null && learner.getEquity().getId() != null && learner.getEquity().getId().equals(HAJConstants.EQUITY_WHITE_ID) ? Boolean.TRUE : Boolean.FALSE));
		
		params.put("African_Male", (male && african));
		params.put("Indian_Male", (male && indian));
		params.put("Coloured_Male", (male && colored));
		params.put("White_Male", (male && white));
		params.put("African_Female", (female && african));
		params.put("Indian_Female", (female && indian));
		params.put("Coloured_Female", (female && colored));
		params.put("White_Female", (female && white));
		

//		String toEmail = u.getEmail();
//
//		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-007-TradeTest ApplicationForContractualLearnerUnderSDA.jasper", params);
//		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
//		AttachmentBean beanAttachment = new AttachmentBean();
//		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
//		beanAttachment.setExt("pdf");
//		beanAttachment.setFile(bites);
//		beanAttachment.setFilename(fileName);
//		attachmentBeans.add(beanAttachment);
		
		String reportName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-007-TradeTest ApplicationForContractualLearnerUnderSDA.jasper", params,reportName);
	}
	
	/**
	 * Email Reminder Service For ARPL
	 * 5 and current Day reminder for document submission
	 * @param today the date of today
	 * @throws Exception
	 */
	public void arplEmailReminderServiceDoucmnetSubmission(Date today) throws Exception{
		today = GenericUtility.getStartOfDay(getSynchronizedDate());
		if (usersService == null) {
			usersService = new UsersService();
		}
		/*
		 *  locate ARPL in holding area. Send notification: 5 days before, on day
		 *  Pending Approval
		 *  companyLearnersTradeTest.setOnHold(true); 
		 *  companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaTwo);
		 *  Type: ARPL
		 *  
		 *  Notification to be sent to: 
		 *  Region Client Services Administrator
		 *  Initiator & Learner 
		 */
		if (countCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, true) != 0) {
			List<CompanyLearnersTradeTest> listOnHold =  findCompanyLearnersTradeTestByTypeArplProgressAndOnHold(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithMersetaTwo, true);
			for (CompanyLearnersTradeTest companyLearnersTradeTest : listOnHold) {
				// on current day
				if (today.equals(GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateToProvideOriginalCopies()))) {					
					// populates docs
					companyLearnersTradeTest = populateDocsByTargetClassAndKey(companyLearnersTradeTest);
					String listOfOrginalsRequired = "<ul>";
					
					for (Doc doc : companyLearnersTradeTest.getDocs()) {
						if (doc.getConfigDoc().getOriginalRequired()) {
							listOfOrginalsRequired += "<li>" + doc.getConfigDoc().getName().trim()  + "</li>";
						}
					}
					listOfOrginalsRequired +="</ul>";
					
					// send email reminder
					List<Users> usersToNotify = new ArrayList<>();
					if (companyLearnersTradeTest.getLearner().getId().equals(companyLearnersTradeTest.getCreateUser().getId())) {
						// learner is initiator
						usersToNotify.add(usersService.findByKey(companyLearnersTradeTest.getLearner().getId()));
					} else {
						// learner
						usersToNotify.add(usersService.findByKey(companyLearnersTradeTest.getLearner().getId()));
						// initiator
						usersToNotify.add(usersService.findByKey(companyLearnersTradeTest.getCreateUser().getId()));
					}
					
					//  Region Client Services Administrator
					if (companyLearnersTradeTest.getClientServiceAdminUser() != null) {
						usersToNotify.add(usersService.findByKey(companyLearnersTradeTest.getClientServiceAdminUser().getId()));
					}
					
					// confirm wording with Sandra
					String subject = "ARPL ORIGINAL DOCUMENTS SUBMISSION REMINDER";
					String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#, </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Reminder that the orginal documents submission for the ARPL submission for: Learner:  Qualification: #QUALIFICATION_DESCRIPTION# (#QUALIFICATION_CODE#) are to be submitted today on the: #DATE_OF_DOC_SUBMISSION# to your regional office.</p>";
					if (companyLearnersTradeTest.getDocs().size() != 0) {
						body += "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The original documnets required are listed below: <br/>"
								+ listOfOrginalsRequired
								+ "<br/></p>";
					}
					body += "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please do not hesitate to contact the merSETA Regional Office for any further assistance or clarification.</p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, </p>"
							+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The MerSETA Team</p>";
					body = body.replace("#QUALIFICATION_DESCRIPTION#", ((companyLearnersTradeTest.getQualification() != null) ? companyLearnersTradeTest.getQualification().getDescription() : "N/A"));
					body = body.replace("#QUALIFICATION_CODE#", ((companyLearnersTradeTest.getQualification() != null) ? companyLearnersTradeTest.getQualification().getCode().toString() : "N/A"));
					body = body.replace("#DATE_OF_DOC_SUBMISSION#", ((companyLearnersTradeTest.getDateToProvideOriginalCopies() != null) ? HAJConstants.sdfDDMMYYYY2.format(companyLearnersTradeTest.getDateToProvideOriginalCopies()) : "N/A"));
					for (Users u : usersToNotify) {
				    	String fullName = "";
						if (u.getTitle() != null && u.getTitle().getDescription() != null) {
							fullName = u.getTitle().getDescription().trim() + " ";
						}
						fullName += u.getFirstName().trim() + " " +  u.getLastName().trim();
						GenericUtility.sendMail(u.getEmail(), subject, body.replace("#FULL_NAME#", fullName.trim()), null);
						break;
					}
					
				}
			}
			listOnHold = null;
		}
	}
	
	public void arplEmailReminderService(Date today) throws Exception{
		today = GenericUtility.getStartOfDay(getSynchronizedDate());
		if (usersService == null) {
			usersService = new UsersService();
		}

		/**
		 * 3. Trade Test Date Set Email Reminder
		 */
		if (countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, false) > 0) {
			List<CompanyLearnersTradeTest> testCentreAllocationDate = findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, false);
			for (CompanyLearnersTradeTest companyLearnersTradeTest : testCentreAllocationDate) {
				if (companyLearnersTradeTest.getDateSubmittedToTestCenter() != null) {
					Date todayRun = GenericUtility.getStartOfDay(today);
					int daycheck = GenericUtility.getDaysBetweenDatesExcludeWeekends(GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateSubmittedToTestCenter()), todayRun);
					switch (daycheck) {
					case 3:
						sendNotificationArplApplicationEmail(12, findByKey(companyLearnersTradeTest.getId()), null);
						break;
					case 7:
						sendNotificationArplApplicationEmail(13, findByKey(companyLearnersTradeTest.getId()), null);
						break;
					default:
						break;
					}
				}
			}
		}
		
		/**
		 * 4. 3 day reminder before test
		 */
		if (countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true, today) > 0) {
			List<CompanyLearnersTradeTest> arplTradeTests = findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedThreeDayReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true, today);
			for (CompanyLearnersTradeTest companyLearnersTradeTest : arplTradeTests) {
				Date todayRun = GenericUtility.getStartOfDay(today);
				int daycheck = GenericUtility.getDaysBetweenDatesExcludeWeekends(todayRun, GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateOfTest()));
				if (daycheck== 3) {
					sendNotificationArplApplicationEmail(6, findByKey(companyLearnersTradeTest.getId()), null);
				}
			}
		}
		
		/**
		 * 5. Trade Test Submission  
		 */
		if (countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true, today) > 0) {
			List<CompanyLearnersTradeTest> arplTradeTests = findCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAfterFromDateReminder(TradeTestTypeEnum.ARPL, AprlProgressEnum.WithTestCenterOne, false, true, today);
			for (CompanyLearnersTradeTest companyLearnersTradeTest : arplTradeTests) {	
				if (companyLearnersTradeTest.getDateOfTestToDate() != null && companyLearnersTradeTest.getDateOfTestToDate().before(today)) {
					Date todayRun = GenericUtility.getStartOfDay(today);
					int daycheck = GenericUtility.getDaysBetweenDatesExcludeWeekends(GenericUtility.getStartOfDay(companyLearnersTradeTest.getDateOfTestToDate()), todayRun);
					switch (daycheck) {
					case 3:
						sendNotificationArplApplicationEmail(14, findByKey(companyLearnersTradeTest.getId()), null);
						break;
					case 7:
						sendNotificationArplApplicationEmail(15, findByKey(companyLearnersTradeTest.getId()), null);
						break;
					default:
						break;
					}
				}
			}
		}	
	}
	
	public void testDateDifference(){
		try {
			Date today = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMYYYY2.parse("12/11/2019"));
			Date submissionToTradeTestCentre = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMYYYY2.parse("04/11/2019"));
			int daycheck = GenericUtility.getDaysBetweenDatesExcludeWeekends(submissionToTradeTestCentre, today);
			System.out.println("Result -------- "+ daycheck);
			
			// today: 04/11/2019, submissionToTradeTestCentre 04/11/2019, Result: 1
			// today: 06/11/2019, submissionToTradeTestCentre 04/11/2019, Result: 3
			// today: 08/11/2019, submissionToTradeTestCentre 04/11/2019, Result: 5
			// today: 12/11/2019, submissionToTradeTestCentre 04/11/2019, Result: 7
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
	
	/**
	 * ARPL Reporting START
	 */
	
	/*
	 *  Counts by Statuses
	 *  WithInitiatorOne,  WithMersetaOne, WithInitiatorTwo
	 */
	public int countArplTradeTestReportingByStatus(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum) throws Exception {
		return dao.countArplTradeTestReportingByStatus(tradeTestTypeEnum, aprlProgressEnum);
	}

	/*
	 * Lazy Load by Status
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countallArplByStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum";
		return dao.countWhere(filters, hql);
	}
	
	
	/*
	 * Counts byStatus and on hold value
	 * WithMersetaTwo - False, WithMersetaTwo - true
	 */
	public int countArplTradeTestReportingByStatusOnHold(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean onHold) throws Exception {
		return dao.countArplTradeTestReportingByStatusOnHold(tradeTestTypeEnum, aprlProgressEnum, onHold);
	}

	/*
	 * Lazy Load by Status and on hold value
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByStatusOnHold(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean onHold) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.onHold = :onHold";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);
		filters.put("onHold", onHold);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllArplByStatusOnHold(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.onHold = :onHold";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 *  Counts by Status and approval status
	 *  WithMersetaFour - Pending Approval (3), WithMersetaFour - Rejected (1)
	 */
	public int countArplTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		return dao.countArplTradeTestReportingByStatusAndApprovalStatus(tradeTestTypeEnum, aprlProgressEnum, approvalStatus);
	}
	
	/*
	 * Lazy Load by Status and approval status
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByStatusAndApprovalStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllArplByStatusAndApprovalStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 *  with test center not provided test date
	 */
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedReminder(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided);
	}
	
	/*
	 * Lazy Load  with test center not provided test date
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByTypeArplProgressTestDateProvidedReminder(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided ";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);	
		filters.put("testCenterSubmitted", testCenterSubmitted);	
		filters.put("testDateProvided", testDateProvided);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllArplByTypeArplProgressTestDateProvidedReminder(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided ";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 *  Count with trade test center awaiting results  
	 */
	public int countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAwaitingResults(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeArplProgressTestDateProvidedAwaitingResults(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted, testDateProvided);
	}
	
	/*
	 * Lazy Load with trade test center awaiting results
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByTypeArplProgressTestDateProvidedAwaitingResults(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided ";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);	
		filters.put("testCenterSubmitted", testCenterSubmitted);	// false
		filters.put("testDateProvided", testDateProvided); // true
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllArplByTypeArplProgressTestDateProvidedAwaitingResults(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts with trade test center after submission for alterations
	 */
	public int countArplWithTestCenterAfterSubmission(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted) throws Exception {
		return dao.countArplWithTestCenterAfterSubmission(tradeTestTypeEnum, aprlProgressEnum, testCenterSubmitted);
	}
	
	/*
	 * Lazy Load with trade test center after submission for alterations
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplWithTestCenterAfterSubmission(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, boolean testCenterSubmitted) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);	
		filters.put("testCenterSubmitted", testCenterSubmitted);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllArplWithTestCenterAfterSubmission(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.testCenterSubmitted = :testCenterSubmitted";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts by Status 
	 */
	public int countArplTradeTestReportingByApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus) throws Exception {
		return dao.countArplTradeTestReportingByApprovalStatus(tradeTestTypeEnum, approvalStatus);
	}
	
	/*
	 * Lazy Load by Status 
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByApprovalStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllArplByApprovalStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts by status and approval status and NAMB date not provided
	 */
	public int countArplTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		return dao.countArplTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(tradeTestTypeEnum, aprlProgressEnum, approvalStatus);
	}
	
	/*
	 * Lazy Load by status and approval status and NAMB date not provided
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByStatusAndApprovalStatusAndNambDateNotProvided(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, AprlProgressEnum aprlProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("aprlProgressEnum", aprlProgressEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllArplByStatusAndApprovalStatusAndNambDateNotProvided(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.aprlProgress = :aprlProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts  by approval status and certificate collected
	 */
	public int countArplTradeTestReportingByStatusAndCertificateCollected(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, Boolean certificateCollected) throws Exception {
		return dao.countArplTradeTestReportingByStatusAndCertificateCollected(tradeTestTypeEnum, approvalStatus, certificateCollected);
	}
	
	/*
	 * Lazy Load by approval status and certificate collected
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplByStatusAndCertificateCollected(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, Boolean certificateCollected) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.certificateCollected = :certificateCollected";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("certificateCollected", certificateCollected);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllArplByStatusAndCertificateCollected(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.certificateCollected = :certificateCollected";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts by status and collection type
	 */
	public int countArplTradeTestReportingByStatusAndCollectionType(TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, CollectionEnum collectionEnum) throws Exception {
		return dao.countArplTradeTestReportingByStatusAndCollectionType(tradeTestTypeEnum, approvalStatus, collectionEnum);
	}
	
	/*
	 * Lazy Load by Status and approval status
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allArplTradeTestReportingByStatusAndCollectionType(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, ApprovalEnum approvalStatus, CollectionEnum collectionEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.collectionEnum = :collectionEnum";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("collectionEnum", collectionEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllArplTradeTestReportingByStatusAndCollectionType(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.status = :approvalStatus and o.collectionEnum = :collectionEnum";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * ARPL Reporting END
	 */
	
	
	/**
	 * Trade Test Reporting: Start
	 */
	
	/*
	 * Counts Trade Tests by status
	 */
	public int countTradeTestReportingByStatus(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum) throws Exception {
		return dao.countTradeTestReportingByStatus(tradeTestTypeEnum, tradeTestProgressEnum);
	}
	
	/*
	 * Lazy Load by Status
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestByStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestByStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 *  with test center not provided test date
	 */
	public int countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedReminder(TradeTestTypeEnum tradeTestTypeEnum,  TradeTestProgressEnum tradeTestProgress, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedReminder(tradeTestTypeEnum, tradeTestProgress, testCenterSubmitted, testDateProvided);
	}
	
	/*
	 * Lazy Load with test center not provided test date
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestByStatusAndTestDateProvided(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum,  TradeTestProgressEnum tradeTestProgress, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgress and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgress", tradeTestProgress);	
		filters.put("testCenterSubmitted", testCenterSubmitted);	
		filters.put("testDateProvided", testDateProvided);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestByStatusAndTestDateProvided(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgress and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 *  Count with trade test center awaiting results  
	 */
	public int countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedAwaitingResults(TradeTestTypeEnum tradeTestTypeEnum,  TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		return dao.countCompanyLearnersTradeTestByTypeTradeTestProgressTestDateProvidedAwaitingResults(tradeTestTypeEnum, tradeTestProgressEnum, testCenterSubmitted, testDateProvided);
	}
	
	/*
	 * Lazy Load with trade test center awaiting results
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestByTypeProgressTestDateProvidedAwaitingResults(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted, boolean testDateProvided) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgressEnum", tradeTestProgressEnum);	
		filters.put("testCenterSubmitted", testCenterSubmitted);	// false
		filters.put("testDateProvided", testDateProvided); // true
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestByTypeProgressTestDateProvidedAwaitingResults(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted and o.testDatesProvided = :testDateProvided";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts with trade test center after submission for alterations
	 */
	public int countTradeTestsWithTestCenterAfterSubmission(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted) throws Exception {
		return dao.countTradeTestsWithTestCenterAfterSubmission(tradeTestTypeEnum, tradeTestProgressEnum, testCenterSubmitted);
	}

	/*
	 * Lazy Load with trade test center after submission for alterations
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestsWithTestCenterAfterSubmission(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, boolean testCenterSubmitted) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgressEnum", tradeTestProgressEnum);	
		filters.put("testCenterSubmitted", testCenterSubmitted);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestsWithTestCenterAfterSubmission(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.testCenterSubmitted = :testCenterSubmitted";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts by Status and approval status
	 */
	public int countTradeTestReportingByStatusAndApprovalStatus(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		return dao.countTradeTestReportingByStatusAndApprovalStatus(tradeTestTypeEnum, tradeTestProgressEnum, approvalStatus);
	}
	
	/*
	 * Lazy Load by Status and approval status
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestReportingByStatusAndApprovalStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestReportingByStatusAndApprovalStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * Counts by status and approval status and NAMB date not provided
	 */
	public int countTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		return dao.countTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(tradeTestTypeEnum, tradeTestProgressEnum, approvalStatus);
	}
	
	/*
	 * Lazy Load by status and approval status and NAMB date not provided
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTradeTest> allTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, TradeTestTypeEnum tradeTestTypeEnum, TradeTestProgressEnum tradeTestProgressEnum, ApprovalEnum approvalStatus) throws Exception {
		String hql = "select o from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null";
		filters.put("tradeTestTypeEnum", tradeTestTypeEnum);
		filters.put("tradeTestProgressEnum", tradeTestProgressEnum);
		filters.put("approvalStatus", approvalStatus);
		return populateAdditionalInformationList((List<CompanyLearnersTradeTest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	public int countAllTradeTestReportingByStatusAndApprovalStatusAndNambDateNotProvided(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnersTradeTest o where o.tradeTestType = :tradeTestTypeEnum and o.tradeTestProgress = :tradeTestProgressEnum and o.status = :approvalStatus and o.nambSubmissionDate is null";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * Trade Test Reporting: End
	 */
	
	/**
	 * Validations against company learners trade test
	 * @param tradeTest
	 * @return
	 */
	public StringBuilder validiateTradeTestInformation(CompanyLearnersTradeTest tradeTest) {
		StringBuilder errors = new StringBuilder();
		if (tradeTest.getCertificateNumber() != null && !tradeTest.getCertificateNumber().trim().isEmpty()) {
			if (!CompanyLearnerValidationService.instance().validateCertificateNumber(tradeTest.getCertificateNumber())) {
				errors.append("Validation Failed For SETMIS Certificate Number <ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li><li>Field may not be more than 30 characters</li></ul>");
				errors.append("<br/>");
			}
		}
		return errors;
	}
	
	//Legacy ARPL Trade test
	public void completeWorkflowTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks task) throws Exception {
		documentValidiation(companyLearnersTradeTest);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithMersetaOne);
		if(companyLearnersTradeTest.getStatus()==ApprovalEnum.Rejected) {
			companyLearnersTradeTest.setStatus(ApprovalEnum.PendingFinalApproval);
		}
		update(companyLearnersTradeTest);
		TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
	}
	
	/* Complete Task: WithInitiator ARPL */
	public void completeWorkflowWithInitiatorOneLegacy(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyTradeTestEmployer employer, Users user, Tasks task, boolean copyAddress, boolean copyAddressEmployer, 
			boolean alterUserLanguages, List<UsersLanguage> userLanguageList, boolean alterUserDisability, List<UsersDisability> userDisabilityList) throws Exception {
		CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
		entryValidiation(companyLearnersTradeTest);
		documentValidiation(companyLearnersTradeTest);
		
		uploadDocuments(companyLearnersTradeTest,task, user);
		
		updateLearnerInformation(companyLearnersTradeTest.getLearner(), copyAddress);
		
		if (alterUserLanguages) {
			UsersLanguageService.instance().createLinkToUser(userLanguageList, companyLearnersTradeTest.getLearner(), user);
		}
		if (alterUserDisability) {
			UsersDisabilityService.instance().createLinkToUser(userDisabilityList, companyLearnersTradeTest.getLearner(), user);
		}
		
		if (companyLearnersTradeTest.getEmploymentStatus() != EmploymentStatusEnum.UnEmployed) {
			companyTradeTestEmployerService.createUpdateEmployerWithAddress(employer, copyAddressEmployer);
			companyLearnersTradeTest.setEmployer(employer);
		} else {
			companyLearnersTradeTest.setCompanyEmployer(null);
			companyLearnersTradeTest.setEmployer(null);
		}
		
		if (companyLearnersTradeTest.getLearner() != null && companyLearnersTradeTest.getLearner().getId() != null && companyLearnersTradeTest.getLearner().getDisability() != null) {
			if (companyLearnersTradeTest.getLearner().getDisability() == YesNoEnum.Yes) {
				companyLearnersTradeTest.setDisability(YesNoEnum.Yes);
				List<UsersDisability> udList = UsersDisabilityService.instance().findByKeyUser(companyLearnersTradeTest.getLearner());
				if (!udList.isEmpty()) {
					UsersDisability firstOne = udList.get(0);
					if (firstOne != null) {
						if (firstOne.getDisabilityStatus() != null && firstOne.getDisabilityStatus().getId() != null) {
							companyLearnersTradeTest.setDisabilityStatus(firstOne.getDisabilityStatus());
						}
						if (firstOne.getDisabilityRating() != null && firstOne.getDisabilityRating().getId() != null) {
							companyLearnersTradeTest.setDisabilityRating(firstOne.getDisabilityRating());
						}
					} else {
						companyLearnersTradeTest.setDisabilityStatus(null);
						companyLearnersTradeTest.setDisabilityRating(null);
					}
					
				}
			} else {
				companyLearnersTradeTest.setDisability(YesNoEnum.No);
				companyLearnersTradeTest.setDisabilityStatus(null);
				companyLearnersTradeTest.setDisabilityRating(null);
			}
		} else {
			companyLearnersTradeTest.setDisability(YesNoEnum.No);
			companyLearnersTradeTest.setDisabilityStatus(null);
			companyLearnersTradeTest.setDisabilityRating(null);
		}
		
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithMersetaOne);
		update(companyLearnersTradeTest);
		
		
		
		
		
		TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), task.getWorkflowProcess());
		sendNotificationArplApplicationEmail(1, companyLearnersTradeTest, null);
	}
	
	/* Reject Task: WithMersetaOne */
	public void rejectWorkflowWithWithMersetaOneLegacy(CompanyLearnersTradeTest companyLearnersTradeTest, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		if (rejectReasonsList.size() == 0) {
			throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
		}
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
		update(companyLearnersTradeTest);
		if (companyLearnersTradeTest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
			List<Users> usersAssignedToList = new ArrayList<>();
			usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, usersAssignedToList);
		}else {
			List<Users> usersAssignedToList = new ArrayList<>();
			usersAssignedToList.add(companyLearnersTradeTest.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, usersAssignedToList);
		}
		if (rejectReasonsList.size() != 0) {
			List<IDataEntity> createList = new ArrayList<>();
			createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), rejectReasonsList, user, tasks.getWorkflowProcess()));
			dao.createBatch(createList);
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), tasks.getWorkflowProcess());
		}
	}
	
	public List<Users> findRegionAdministrator(Address address) throws Exception {
		RolesService rolesService=new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(HAJConstants.ADMIN_ROLE_ID);
		
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(address.getTown(), roles);
		return list;
	}
	
	public void finalApproveWorkflowLegacy(CompanyLearnersTradeTest companyLearners, Users user, Tasks tasks) throws Exception {
		List<Users> users = new ArrayList<>();
		if(companyLearners.getEmployer() != null) {
			users = findRegionAdministrator(companyLearners.getEmployer().getResidentialAddress());
		}else {
			users = findRegionAdministrator(companyLearners.getLearner().getResidentialAddress());
		}
		
		if(users.size() > 0) {
			companyLearners.setAdminUser(users.get(0));
		}else {
			companyLearners.setAdminUser(user);
		}
		
		companyLearners.setStatus(ApprovalEnum.AwaitingNAMB);
		companyLearners.setApprovalDate(getSynchronizedDate());
		//companyLearners.getCompanyLearners().setStatus(ApprovalEnum.AwaitingNAMB);
		//companyLearners.getCompanyLearners().setLearnerStatus(LearnerStatusEnum.Approved);
		companyLearners.setSerialNumber(generateCompanyLearnerTradeTestSerialNumber());
		if(companyLearners.getCompanyLearners() != null) {
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			cl.setStatus(ApprovalEnum.Approved);
			cl.setDateLearnerCompleted(getSynchronizedDate());
			cl.setDateQualificationObtained(getSynchronizedDate());
			CompanyLearnersService.instance().update(cl);
		}
		dao.update(companyLearners);
		//dao.update(companyLearners.getCompanyLearners());
		TasksService.instance().completeTask(tasks);
		if(companyLearners.getCompanyLearners() != null && companyLearners.getCompanyLearners().getCreateUser() != null) {
			successfulApplicationNotification(companyLearners.getCompanyLearners().getCreateUser(), companyLearners);
		}else if(companyLearners.getCreateUser() != null){
			successfulApplicationNotification(companyLearners.getCreateUser(), companyLearners);
		}
	}
	
	
	
	
	
}