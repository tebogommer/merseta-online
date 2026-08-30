package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyExperientialService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class LegacyCompanyLearnersService extends AbstractService {
	/** The dao. */
	private CompanyLearnersDAO dao = new CompanyLearnersDAO();
	private UsersService usersService = new UsersService();
	CompanyService companyService = new CompanyService();
	private ConfigDocService configDocService = new ConfigDocService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	
	private static LegacyCompanyLearnersService legacyCompanyLearnersService;
	public static synchronized LegacyCompanyLearnersService instance() {
		if (legacyCompanyLearnersService == null) {
			legacyCompanyLearnersService = new LegacyCompanyLearnersService();
		}
		return legacyCompanyLearnersService;
	}

	/**
	 * Create or update Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void createLearner(Users createUser, Users entity, Company company, Company trainingProvider, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
		if (company == null) {
			throw new Exception("Employer error on registration");
		}else {
			if (companyService == null) {
				companyService = new CompanyService();
			}
			company = companyService.resolveCompanyAddressesReturnCompany(company);
		}
		if (company != null && company.getId() == null) {
			throw new Exception("Employer error on registration");
		}
		if(trainingProvider == null) {
			throw new Exception("Company cannot be null");
		} else {
			if (companyService == null) {
				companyService = new CompanyService();
			}
			trainingProvider = companyService.resolveCompanyAddressesReturnCompany(trainingProvider);
		}
		if(trainingProvider.getId() == null ) {
			throw new Exception("Company cannot be null");
		}
		List<Users> users = new ArrayList<>();
		if(cl.getInterventionType().getId() != HAJConstants.APPRENTICESHIP_ID) {
			System.out.println("inside if to check not apprenetship");
			if(company != null && company.getResidentialAddress() != null) {
				users = CompanyLearnersService.instance().findRegionClinetServiceCoodinator(company);
				//users = CompanyLearnersService.instance().findRegionClinetServiceAdministrator(company);
			}else if(trainingProvider != null && trainingProvider.getResidentialAddress() != null){
				//users = CompanyLearnersService.instance().findRegionClinetServiceAdministrator(trainingProvider);
				users = CompanyLearnersService.instance().findRegionClinetServiceCoodinator(trainingProvider);
			}	
			if (users == null || users.size() == 0) {
				throw new Exception("No Region Client Service Administrator for the region");
			}
		}
		else
		{
			System.out.println("inside if to check apprenetship");
			users = CompanyLearnersService.instance().findRegionClinetServiceCoodinator(company);
		}

		cl.setEmployer(company);
		
		/*if(cl.getInterventionType().getWorkplaceApprovalRequired() != null &&  cl.getInterventionType().getWorkplaceApprovalRequired() && CompanyLearnersService.instance().getCompanyLearnerQualification(cl) !=null &&  CompanyLearnersService.instance().getCompanyLearnerQualification(cl).getWorkplaceApprovalRequired()) {
			CompanyLearnersService.instance().checkSmeQualificationMentor(cl, CompanyLearnersService.instance().getCompanyLearnerQualification(cl), company);
		}*/
		
		Users gaurdian = null;
		if (requireGaurdian) {
			gaurdian = entity.getLegalGaurdian();
		} else {
			gaurdian = null;
			entity.setLegalGaurdian(gaurdian);
		}

		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String error = "";
		String pwdLearner = "";
		cl.setCreateUser(createUser);
		cl.setMersetaCompany(true);
		
		if (entity.getId() == null && entity.getEmail() != null && !entity.getEmail().isEmpty()) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());								
		}else {
			if(entity.getId() == null) {
				entity.setEmail(usersService.generateUuidEmailAddress());
			}
		}
		
		if (entity.getLegalGaurdian() != null && entity.getLegalGaurdian().getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}

		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (cl.getDocs() != null) {
			for (Doc doc : cl.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				// doc.setForUsers(formUser);
				doc.setUsers(createUser);
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				docsDataEntities.add(doc);
				if (doc.getData() != null) {
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, createUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				} else {
					throw new Exception("Please upload required document ");
				}
			}
		}

		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}
		
		if (error.length() > 0) throw new ValidationException(error);

		if (gaurdian != null) {
			if (gaurdian.getId() == null) {
				AddressService.instance().saveAddresses(entity.getResidentialAddress(), gaurdian.getPostalAddress());
				String pwd = GenericUtility.genPassord();
				gaurdian.setPassword(LogonService.encryptPassword(pwd));
				gaurdian.setStatus(UsersStatusEnum.EmailNotConfrimed);
				gaurdian.setEmailGuid(UUID.randomUUID().toString());
				gaurdian.setRegistrationDate(new java.util.Date());
				gaurdian.setAdmin(false);
				gaurdian.setAcceptPopi(false);
				createBatch.add(gaurdian);
			} else {
				updateBatch.add(gaurdian);
			}

			entity.setLegalGaurdian(gaurdian);
		}
		entity.setOfoCodes(cl.getOfoCodes());
		entity.setQualification(cl.getHighestQualification());
		entity.setLastSchoolYear(cl.getLastSchoolYear());
		entity.setPreviousSchools(cl.getPreviousSchools());
		
		if (entity.getId() == null) {
			pwdLearner = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwdLearner));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			entity.setAdmin(false);
			entity.setAcceptPopi(false);			
			createBatch.add(entity);
		} else {
			updateBatch.add(entity);
		}

		if (usersLanguages != null && usersLanguages.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersLanguageService uls = new UsersLanguageService();
				List<UsersLanguage> list = (ArrayList<UsersLanguage>) uls.findByUser(entity);
				if (list != null && list.size() > 0) {
					List<IDataEntity> usersLanguagesList = new ArrayList<>();
					usersLanguagesList.addAll(list);
					dao.deleteBatch(usersLanguagesList);
				}
			}

			for (UsersLanguage ul : usersLanguages) {
				ul.setUser(entity);
				createBatch.add(ul);
			}
		}

		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
				List<UsersDisability> usersDisabilitylistRemove = (ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
				if (usersDisabilitylistRemove != null && usersDisabilitylistRemove.size() > 0) {
					List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
					usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
					dao.deleteBatch(usersDisabilitylistIDataEntity);
				}
			}

			for (UsersDisability ud : usersDisabilityList) {
				ud.setUser(entity);
				createBatch.add(ud);
			}
		}

		/*if (company.getId() == null) createBatch.add(company);
		else updateBatch.add(company);*/

		cl.setUser(entity);
		cl.setCompany(trainingProvider);

		if (submitWorkflow) cl.setStatus(ApprovalEnum.PendingApproval);
		if (cl.getId() == null) {
			createBatch.add(cl);
		}else if (cl.getId() != null) {
			updateBatch.add(cl);
		}

		AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
		//AddressService.instance().create(entity.getBirthAddress());
		//AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
		/*if (gaurdian != null && gaurdian.getId() == null) {
			// AddressService.instance().saveAddresses(entity.getResidentialAddress(),
			// gaurdian.getPostalAddress());
			AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
		}*/
		// if (createBatch.size() > 0) this.dao.createBatch(createBatch);
		// if (updateBatch.size() > 0) this.dao.updateBatch(updateBatch);
		this.dao.createAndUpdateBatch(createBatch, updateBatch);
		if(cl.getDocs() !=null) {
			for (Doc doc : cl.getDocs()) {
				if (doc.getConfigDoc().getReqImmediate()) {
					doc.setTargetKey(cl.getId());
					doc.setTargetClass(CompanyLearners.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
				}
			}
		}
		System.out.println("cl.getInterventionType().getId():"+cl.getInterventionType().getId());
		System.out.println("HAJConstants.APPRENTICESHIP_ID:"+HAJConstants.APPRENTICESHIP_ID);
		if(cl.getInterventionType().getId() == HAJConstants.APPRENTICESHIP_ID) {
			System.out.println("Inside Aprentship check");
			System.out.println("cl.getLearnerStatus():"+cl.getLearnerStatus());
			if (cl.getLearnerStatus().toString().equalsIgnoreCase("Application")) {
				System.out.println("creating task");
				TasksService.instance().findFirstInProcessAndCreateTask("", createUser, cl.getId(), cl.getClass().getName(), true, ConfigDocProcessEnum.LegacyLearnerRegistration, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), users);
			}
			System.out.println("after creating task");
			
			finalApproveWorkflow(cl, createUser, null);
		}else {
			TasksService.instance().findFirstInProcessAndCreateTask("", createUser, cl.getId(), cl.getClass().getName(), true, ConfigDocProcessEnum.LegacyLearnerRegistration, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), users);
	
			if (cl.getInterventionType().getForm().matches("002")) {
				sendWorkBasedLearningProgrammeAgreement(createUser, entity, company, trainingProvider, cl);
			} else if (cl.getInterventionType().getForm().matches("015")) {
				sendLPMFM015Email(createUser, entity, company, trainingProvider, cl);
			}
		}
		
		closeLegacyLearner(cl, createUser);		
	}
	
	
	private void closeLegacyLearner(CompanyLearners cl, Users u) throws Exception{
		cl = CompanyLearnersService.instance().findByKey(cl.getId());
		if (cl.getLegacyTargetClass().equals(LegacyApprenticeship.class.getName())) {	
			LegacyApprenticeship legacyapprenticeship = LegacyApprenticeshipService.instance().findByKey(cl.getLegacyId());	
			legacyapprenticeship.setCreatedCompanyLearner(true);
			LegacyApprenticeshipService.instance().update(legacyapprenticeship);
		}else if(cl.getLegacyTargetClass().equals(LegacyBursary.class.getName())) {
			LegacyBursary legacybursary = LegacyBursaryService.instance().findByKey(cl.getLegacyId());	
			legacybursary.setCreatedCompanyLearner(true);
			LegacyBursaryService.instance().update(legacybursary);
		}else if(cl.getLegacyTargetClass().equals(LegacyInternship.class.getName())){
			LegacyInternship legacyinternship = LegacyInternshipService.instance().findByKey(cl.getLegacyId());		
			legacyinternship.setCreatedCompanyLearner(true);		
			LegacyInternshipService.instance().update(legacyinternship);
		}else if(cl.getLegacyTargetClass().equals(LegacyTvet.class.getName())){
			LegacyTvet legacytvet = LegacyTvetService.instance().findByKey(cl.getLegacyId());	
			legacytvet.setCreatedCompanyLearner(true);			
			LegacyTvetService.instance().update(legacytvet);			
		}else if(cl.getLegacyTargetClass().equals(LegacyUnitStandard.class.getName())){
			LegacyUnitStandard legacyunitstandard = LegacyUnitStandardService.instance().findByKey(cl.getLegacyId());	
			legacyunitstandard.setCreatedCompanyLearner(true);		
			LegacyUnitStandardService.instance().update(legacyunitstandard);
		}else if(cl.getLegacyTargetClass().equals(LegacySECTTwentyEight.class.getName())){
			LegacySECTTwentyEight legacysecttwentyeight = LegacySECTTwentyEightService.instance().findByKey(cl.getLegacyId());	
			legacysecttwentyeight.setCreatedCompanyLearner(true);
			LegacySECTTwentyEightService.instance().update(legacysecttwentyeight);
		}else if(cl.getLegacyTargetClass().equals(LegacyLearnership.class.getName())){
			LegacyLearnership legacylearnership = LegacyLearnershipService.instance().findByKey(cl.getLegacyId());	
			legacylearnership.setCreatedCompanyLearner(true);			
			LegacyLearnershipService.instance().update(legacylearnership);
		}else if(cl.getLegacyTargetClass().equals(LegacySkillsProgramme.class.getName())){
			LegacySkillsProgramme legacyskillsprogramme = LegacySkillsProgrammeService.instance().findByKey(cl.getLegacyId());	
			legacyskillsprogramme.setCreatedCompanyLearner(true);	
			LegacySkillsProgrammeService.instance().update(legacyskillsprogramme);
		}else if(cl.getLegacyTargetClass().equals(LegacyExperiential.class.getName())){
			LegacyExperiential legacyExperiential = LegacyExperientialService.instance().findByKey(cl.getLegacyId());	
			legacyExperiential.setCreatedCompanyLearner(true);	
			LegacyExperientialService.instance().update(legacyExperiential);
		}
		
		if(cl.getInterventionType().getRequestVerificationOfAssesments()!=null && cl.getInterventionType().getRequestVerificationOfAssesments()) {			
			requestVerification(cl, u);
		}
	}

	public void requestVerification(CompanyLearners companyLearners, Users u) throws Exception {
		List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards = summativeAssessmentReportService.findByCompanylearnersLegacy(companyLearners);
		if(summativeAssessmentReportUnitStandards.size() > 0) {
			companyLearners.setLearnerStatus(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
			CompanyLearnersService.instance().update(companyLearners);
			
			TrainingProviderVerfication trainingProviderVerfication = new TrainingProviderVerfication();			
			if(companyLearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_PROGRAM_LIST.contains(companyLearners.getInterventionType().getId())) {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_SET_LIST.contains(companyLearners.getInterventionType().getId())) {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate);
			}
			HostingCompanyEmployeesService hce = new HostingCompanyEmployeesService();
			if(hce.findByUserHostCompanyB(u.getId())) {
				trainingProviderVerfication.setCreatedByMerseta(true);
			}else {
				trainingProviderVerfication.setCreatedByMerseta(false);
			}		
			
			trainingProviderVerfication.setCompanyLearners(companyLearners);
			trainingProviderVerfication.setStatus(ApprovalEnum.NA);
			trainingProviderVerfication.setCreateUser(u);
			this.dao.create(trainingProviderVerfication);
			
			SummativeAssessmentReportService  summativeAssessmentReportService = new SummativeAssessmentReportService();
			SummativeAssessmentReport summativeAssessmentReport = new SummativeAssessmentReport();
			summativeAssessmentReport.setTrainingProviderVerfication(trainingProviderVerfication);
			summativeAssessmentReport.setCompanyLearners(companyLearners);
			summativeAssessmentReport.setInterventionType(companyLearners.getInterventionType());
			summativeAssessmentReport.setSkillsProgram(companyLearners.getSkillsProgram());
			summativeAssessmentReport.setSkillsSet(companyLearners.getSkillsSet());
			summativeAssessmentReport.setUnitStandard(companyLearners.getUnitStandard());
			summativeAssessmentReport.setQualification(companyLearners.getQualification());
			
			if(summativeAssessmentReportUnitStandards != null && summativeAssessmentReportUnitStandards.size() > 0) {
				summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
				dao.create(summativeAssessmentReport);		
				summativeAssessmentReportService.createSummativeAssessmentReportUnitStandards(summativeAssessmentReport, summativeAssessmentReportUnitStandards);
			}
		}
	}
	/**
	 * Create or update Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public void createLearner(Users createUser, Users entity, CompanyLearners cl, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList,LegacySECTTwentyEight legacysecttwentyeight) throws Exception {
		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String error = "";
		String pwdLearner = "";
		cl.setCreateUser(createUser);
		cl.setMersetaCompany(true);
		
		if (entity.getId() == null && entity.getEmail() != null && !entity.getEmail().isEmpty()) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());								
		}else {
			if(entity.getId() == null) {
				entity.setEmail(usersService.generateUuidEmailAddress());
			}
		}
		
		if (entity.getLegalGaurdian() != null && entity.getLegalGaurdian().getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}

		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (cl.getDocs() != null) {
			for (Doc doc : cl.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				// doc.setForUsers(formUser);
				doc.setUsers(createUser);
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				docsDataEntities.add(doc);
				if (doc.getData() != null) {
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, createUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				} else {
					throw new Exception("Please upload required document ");
				}
			}
		}

		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}
		
		if (error.length() > 0) throw new ValidationException(error);
		
		entity.setOfoCodes(cl.getOfoCodes());
		entity.setQualification(cl.getHighestQualification());
		entity.setLastSchoolYear(cl.getLastSchoolYear());
		entity.setPreviousSchools(cl.getPreviousSchools());
		
		if (entity.getId() == null) {
			pwdLearner = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwdLearner));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			entity.setAdmin(false);
			entity.setAcceptPopi(false);			
			createBatch.add(entity);
		} else {
			updateBatch.add(entity);
		}

		if (usersLanguages != null && usersLanguages.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersLanguageService uls = new UsersLanguageService();
				List<UsersLanguage> list = (ArrayList<UsersLanguage>) uls.findByUser(entity);
				if (list != null && list.size() > 0) {
					List<IDataEntity> usersLanguagesList = new ArrayList<>();
					usersLanguagesList.addAll(list);
					dao.deleteBatch(usersLanguagesList);
				}
			}

			for (UsersLanguage ul : usersLanguages) {
				ul.setUser(entity);
				createBatch.add(ul);
			}
		}

		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
				List<UsersDisability> usersDisabilitylistRemove = (ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
				if (usersDisabilitylistRemove != null && usersDisabilitylistRemove.size() > 0) {
					List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
					usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
					dao.deleteBatch(usersDisabilitylistIDataEntity);
				}
			}

			for (UsersDisability ud : usersDisabilityList) {
				ud.setUser(entity);
				createBatch.add(ud);
			}
		}



		cl.setUser(entity);


		if (cl.getId() == null) createBatch.add(cl);
		else if (cl.getId() != null) updateBatch.add(cl);

		AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());


		this.dao.createAndUpdateBatch(createBatch, updateBatch);

		/*for (Doc doc : cl.getDocs()) {
			if (doc.getConfigDoc().getReqImmediate()) {
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
			}
		}*/
		CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
		CompanyLearnersTradeTest companyLearnersTradeTest = new CompanyLearnersTradeTest();
		companyLearnersTradeTest.setLegacysecttwentyeight(legacysecttwentyeight);
		companyLearnersTradeTest.setLearner(entity);
		companyLearnersTradeTest.setCreateUser(createUser);
		companyLearnersTradeTest.setQualification(cl.getQualification());	
		companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.ARPL);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
		companyLearnersTradeTest.setAlterEmployerInfo(true);
		companyLearnersTradeTest.setStatus(ApprovalEnum.NA);
		if(cl.getEmploymentStatus()!=null) {
			if(cl.getEmploymentStatus() == EmploymentStatusEnum.Employed) {
				companyLearnersTradeTest.setEmployedUnEmployed(EmployedUnEmployedEnum.Employed);
			}else {
				companyLearnersTradeTest.setEmployedUnEmployed(EmployedUnEmployedEnum.UnEmployed);
			}
		}else {
			companyLearnersTradeTest.setEmployedUnEmployed(EmployedUnEmployedEnum.UnEmployed);
		}		
		companyLearnersTradeTestService.create(companyLearnersTradeTest);
		
		closeLegacyLearner(cl, createUser);
	}
	
	public void completeCompanyLearners(CompanyLearners cl, Users user, Tasks tasks) throws Exception {
		List<Users> users = CompanyLearnersService.instance().findRegionClinetServiceAdministrator(cl.getCompany());

		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}
		
		cl.setStatus(ApprovalEnum.PendingApproval);
		dao.update(cl);

		CompanyLearnersService.instance().uploadDocuments(cl, tasks, user);
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, cl.getId(), cl.getClass().getName(), true, ConfigDocProcessEnum.LegacyLearnerRegistration, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), users);
		if (cl.getInterventionType().getForm().matches("002")) {
			sendWorkBasedLearningProgrammeAgreement(user, cl.getUser(), cl.getEmployer(), cl.getCompany(), cl);
		} else if (cl.getInterventionType().getForm().matches("015")) {
			sendLPMFM015Email(user, cl.getUser(), cl.getEmployer(), cl.getCompany(), cl);
		}	
	}
	
	public void rejectCompanyLearners(CompanyLearners entity, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = new ArrayList<>();
		users.add(entity.getCreateUser());

		if (users == null || users.size() == 0) {
			throw new Exception("No user found ");
		}

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LegacyLearnerRegistration);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		String emailMessage = "";
		String subject = "";
		String welcome = "The learner registration application for: #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#, #LEARNER_EMAIL#) for #COMPANY_NAME# (#ENTITY_ID#) has not been approved. Please review the application.";
		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(entity.getUser());
		
		welcome = welcome.replaceAll("#LEARNER_FIRST_NAME#", entity.getUser().getFirstName());
		welcome = welcome.replaceAll("#LEARNER_LAST_NAME#", entity.getUser().getLastName());
		welcome = welcome.replaceAll("#LEARNER_IDENTITY_NUMBER#", rsaIdPassport);
		welcome = welcome.replaceAll("#LEARNER_EMAIL#", entity.getUser().getEmail());
		welcome = welcome.replaceAll("#COMPANY_NAME#", entity.getEmployer().getCompanyName());
		welcome = welcome.replaceAll("#ENTITY_ID#", entity.getEmployer().getLevyNumber());
		TasksService.instance().completeTask(tasks);
		TasksService.instance().createTask(CompanyLearners.class.getName(), null, emailMessage, subject, welcome, user, entity.getId(), true, true, tasks, users, ConfigDocProcessEnum.LegacyLearnerRegistration, null);		
	}
	
	public void sendWorkBasedLearningProgrammeAgreement(Users createUser, Users learner, Company company, Company trainingProvider, CompanyLearners companylearners) throws Exception {
		// WORK_BASED_LEARNING_PROGRAMME_AGREEMENT	
		
		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companylearners.getUser());
		String subject = "LEARNER REGISTRATION APPLICATION";
		String welcome = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: (#QualificationCode#)#QualificationTitle#.</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + CompanyLearnersService.instance().getRegionString(trainingProvider) + "</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", createUser.getFirstName());
		welcome = welcome.replaceAll("#Surname#", createUser.getLastName());
		welcome = welcome.replaceAll("#learnerFirstName#", companylearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#learnerSurname#", companylearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		if (companylearners.getQualification() != null && companylearners.getQualification().getId() != null) {
			welcome = welcome.replaceAll("#QualificationCode#", companylearners.getQualification().getCodeString());
			welcome = welcome.replaceAll("#QualificationTitle#", companylearners.getQualification().getDescription());
		} else if (companylearners.getLearnership() != null && companylearners.getLearnership().getQualification() != null && companylearners.getLearnership().getId() != null) {
			welcome = welcome.replaceAll("#QualificationCode#", companylearners.getLearnership().getQualification().getCodeString());
			welcome = welcome.replaceAll("#QualificationTitle#", companylearners.getLearnership().getQualification().getDescription());
		}else {
			Qualification qual = CompanyLearnersService.instance().getCompanyLearnerQualification(companylearners);
			if(qual != null) {
				welcome = welcome.replaceAll("#QualificationCode#", qual.getCodeString());
				welcome = welcome.replaceAll("#QualificationTitle#", qual.getDescription());
			}
		}

		createUser.getFirstName();
		createUser.getLastName();
		GenericUtility.sendMail(createUser.getEmail(), subject, welcome, null);
	}
	
	public void sendLPMFM015Email(Users createUser, Users learner, Company company, Company trainingProvider, CompanyLearners companylearners) throws Exception {
		
		String qualificationTitle = CompanyLearnersService.instance().getCompanyLearnerStringQualification(companylearners);	

		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companylearners.getUser());

		String subject = "LEARNER REGISTRATION APPLICATION";

		String mssg = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: #QualificationTitle#.</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + CompanyLearnersService.instance().getRegionString(trainingProvider) + "</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

		mssg = mssg.replaceAll("#FirstName#", createUser.getFirstName());
		mssg = mssg.replaceAll("#Surname#", createUser.getLastName());
		mssg = mssg.replaceAll("#learnerFirstName#", companylearners.getUser().getFirstName());
		mssg = mssg.replaceAll("#learnerSurname#", companylearners.getUser().getLastName());
		mssg = mssg.replaceAll("#rsaIdPassport#", rsaIdPassport);
		mssg = mssg.replaceAll("#QualificationTitle#", qualificationTitle);
	
		GenericUtility.sendMail(createUser.getEmail(), subject, mssg, null);
	}
	
	public void sendRejectEmail(CompanyLearners companyLearners, List<RejectReasons> rejectReasons, Users u) throws Exception {
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if (company != null && company.getAccreditationNumber() != null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companyLearners.getUser());
		String QualificationString = "";
		Qualification qualification = CompanyLearnersService.instance().getCompanyLearnerQualification(companyLearners);
		QualificationString = "(" + qualification.getCodeString() + ") " + qualification.getDescription();
		String reasons = CompanyLearnersService.instance().convertToHTML(rejectReasons);

		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) " + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been not been registered accordingly for the following reason(s):</p>" + reasons
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the " + CompanyLearnersService.instance().getRegionString(company) + " Office</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours faithfully,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Client Services Administrator</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#QualificationString#", QualificationString);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(u.getEmail(), "merSETA LEARNER REGISTRATION APPLICATION", welcome, null);
	}
	
	public void finalRejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setLearnerStatus(LearnerStatusEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
		TasksService.instance().completeTask(tasks);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, tasks.getWorkflowProcess());
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}
	
	public void finalApproveWorkflow(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {

		companyLearners.setStatus(ApprovalEnum.Approved);
		if(companyLearners.getDateLearnerRegistered() == null) {
			companyLearners.setDateLearnerRegistered(getSynchronizedDate());
		}
		if(companyLearners.getRegistrationNumber() == null || companyLearners.getRegistrationNumber().matches("")) {
			companyLearners.setRegistrationNumber(CompanyLearnersService.instance().generateCompanyLearnerRegNumber(companyLearners));
		}		
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setSubmissionEnum(SubmissionEnum.Completed);
		companyLearners.setApprovalDate(getSynchronizedDate());
		/*if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			activeContractDetailService.addTranchePaymentDetail(companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
		}*/
		WorkplaceMonitoring workplaceMonitoring = new WorkplaceMonitoring();
		workplaceMonitoring.setCompany(companyLearners.getEmployer());
		workplaceMonitoring.setUsers(user);
		WorkplaceMonitoringService workplaceMonitoringService = new WorkplaceMonitoringService();
		workplaceMonitoringService.requesteWorkflow(workplaceMonitoring, user);
		dao.update(companyLearners);

		List<Users> list = new ArrayList<>();
		list.add(user);
		//list.add(companyLearners.getCreateUser());
		//list.add(companyLearners.getUser());

		sendForOfficeUseOnlyForm(companyLearners, user);
		if(tasks!=null) {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, list, false);
		}
		/*if(companyLearners.getInterventionType().getForm().matches("002")) {
			List<Users> userList = new ArrayList<>();
			userList.add(user);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			sendForOfficeUseOnlyForm(companyLearners, user);
		}else {
			TasksService.instance().completeTask(tasks);

			super.removeDuplicatesFromList(list);
			for (Users u : list) {
				sendApprovalEmail(companyLearners, u);
			}			
			// sendApprovalEmailToTheLearner(companyLearners);
			CompanyLearnersService.instance().createLearnerFileManagementWorkflow(companyLearners, user, null);
		}*/
	}
	
	public void sendRejectEmailToTheLearner(CompanyLearners companyLearners, List<RejectReasons> rejectReasons) throws Exception {
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if (company != null && company.getAccreditationNumber() != null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companyLearners.getUser());
		String QualificationString = CompanyLearnersService.instance().getCompanyLearnerStringQualification(companyLearners);
		String reasons = CompanyLearnersService.instance().convertToHTML(rejectReasons);
		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #LearnerFirstName# #LearnerSurname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly be advised that the learner registration application for #LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) for #QualificationString#. was not been approved for the following reason(s):</p>" + reasons + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the " + CompanyLearnersService.instance().getRegionString(company) + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#QualificationString#", QualificationString);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(companyLearners.getUser().getEmail(), "merSETA Client Services", welcome, null);
	}
	
	public void sendApprovalEmail(CompanyLearners companyLearners, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// Qualification qualification =
		// getCompanyLearnerQualification(companyLearners);
		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		// String regionDescription = getRegionString(companyLearners.getCompany());

		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companyLearners.getUser());

		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearners.getCreateUser().getFirstName() + " " + companyLearners.getCreateUser().getLastName() + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: "
				+ companyLearners.getRegistrationNumber() + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);

	}
	
	public void sendForOfficeUseOnlyForm(CompanyLearners companyLearners, Users user) throws Exception {

		String adminName = user.getFirstName() + " " + user.getLastName();
		String rsaIdPassport = CompanyLearnersService.instance().anIDNumber(companyLearners.getUser());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
		
		String designation = "N/A";
		if(hostingCompanyEmployees != null) {
			designation = hostingCompanyEmployees.getJobTitle().getDescription();
		}
		
		String conditionalPlacementDate = "";
		String registrationDateOfaggrement = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(companyLearners.getConditionalPlacementDate() != null) {
			conditionalPlacementDate = dateFormat.format(companyLearners.getConditionalPlacementDate());
		}
		
		if(companyLearners.getApprovalDate() != null) {
			registrationDateOfaggrement = dateFormat.format(companyLearners.getApprovalDate());
		}
		
		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: " + companyLearners.getRegistrationNumber() 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";
	
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "higher_education_and_training_logo");
		params.put("user", user);
		params.put("adminName", adminName);
		params.put("designation", designation);
		params.put("aggrementNumber", companyLearners.getRegistrationNumber());
		params.put("conditionalPlacementDate", conditionalPlacementDate);
		params.put("registrationDateofaggrement", registrationDateOfaggrement);
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_OFFICE_USE.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("ForOfficeUseOnly.pdf");
		attachmentBeans.add(beanAttachment);

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), subject, mssg, attachmentBeans, null);		
	}
	
	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
			if (l != null && l.size() > 4 && !entity.getInterventionType().getBusary()) {
				l.remove(l.size()-2);
				l.remove(l.size()-1);
			}
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}

			/*
			 * l = ConfigDocService.instance().findByProcessA(configDocProcess); if (l !=
			 * null && l.size() > 0) { l.forEach(a -> { entityDoc.getDocs().add(new Doc(a));
			 * }); }
			 */

		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 4 && !entity.getInterventionType().getBusary()) {
				l.remove(l.size()-2);
				l.remove(l.size()-1);
			}
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}

		/*if (configDocProcess == ConfigDocProcessEnum.LegacyLearnerRegistration && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);
				}
			}
		}*/

	}
	
	public void prepareNewRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entity, Tasks tasks) throws Exception {
		List<ConfigDoc> l = null;
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));				
			if(entity != null && entity.getInterventionType() != null && entity.getInterventionType().getBusary() != null && entity.getInterventionType().getBusary()) {
				l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), tasks.getProcessRole());
			}else {
				l = ConfigDocService.instance().findByProcessRolesNotUploadedNotBusary(entity.getClass().getName(), entity.getId(), tasks.getProcessRole());
			}			
			if (l != null && l.size() > 0) {				
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			if(entity != null && entity.getInterventionType() != null && entity.getInterventionType().getBusary()) {
				l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			}else {
				l = configDocService.findByProcessLearners(configDocProcess, CompanyUserTypeEnum.User);
			}
			
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearners entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}

		if (configDocProcess == ConfigDocProcessEnum.LegacyLearnerRegistration && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);

				}
			}
		}
	}
	
	public void requestLegacyVerificationLearner(LegacyLearnership legacylearnership, Users u, CompanyLearners companyLearners, List<LegacyApprenticeship> legacyapprenticeshipList) throws Exception {
		
		TrainingProviderVerfication trainingProviderVerfication = new TrainingProviderVerfication();		
		trainingProviderVerfication.setStatus(ApprovalEnum.NA);
		trainingProviderVerfication.setCreateUser(u);
		trainingProviderVerfication.setTrainingProvider(companyLearners.getCompany());
		trainingProviderVerfication.setCompanyLearners(companyLearners);
		this.dao.create(trainingProviderVerfication);
		
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
		this.dao.update(companyLearners);
		
		SummativeAssessmentReport summativeAssessmentReport = new SummativeAssessmentReport();
		summativeAssessmentReport.setTrainingProviderVerfication(trainingProviderVerfication);
		summativeAssessmentReport.setCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		summativeAssessmentReport.setInterventionType(trainingProviderVerfication.getCompanyLearners().getInterventionType());
		summativeAssessmentReport.setSkillsProgram(trainingProviderVerfication.getCompanyLearners().getSkillsProgram());
		summativeAssessmentReport.setSkillsSet(trainingProviderVerfication.getCompanyLearners().getSkillsSet());
		summativeAssessmentReport.setUnitStandard(trainingProviderVerfication.getCompanyLearners().getUnitStandard());
		summativeAssessmentReport.setQualification(trainingProviderVerfication.getCompanyLearners().getQualification());
		
		List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards = summativeAssessmentReportService.findByCompanylearners(trainingProviderVerfication.getCompanyLearners());
		//List<UnitStandards> unitStandards = unitStandardsService.resolveCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		
		/*if(unitStandards != null && unitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			dao.create(summativeAssessmentReport);
			summativeAssessmentReportService.create(summativeAssessmentReport, unitStandards);
		}/*else {
			throw new Exception("No UnitsStandard for qualification");
		}*/
		
		if(summativeAssessmentReportUnitStandards != null && summativeAssessmentReportUnitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			dao.create(summativeAssessmentReport);
		
			summativeAssessmentReportService.createSummativeAssessmentReportUnitStandards(summativeAssessmentReport, summativeAssessmentReportUnitStandards);
		}
		
		//TasksService.instance().findFirstInProcessAndCreateTask(u, trainingProviderVerfication.getId(), trainingProviderVerfication.getClass().getName(), ConfigDocProcessEnum.ProviderVerification, false);
	}
}
