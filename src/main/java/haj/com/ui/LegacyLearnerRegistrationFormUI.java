package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Appointment;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Sites;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.UserQualifications;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.UsersTrainingProvider;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.entity.lookup.LegacyPerson;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.rest.idverification.IdVerificationRealTime;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.LegacyCompanyLearnersService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.FundingService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.LearnershipService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyExperientialService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacyPersonService;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM;
import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "legacyLearnerRegistrationFormUI")
@ViewScoped
public class LegacyLearnerRegistrationFormUI extends AbstractUI {

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;
	/** The user. */
	private Users user;
	private Users gaurdian;
	private boolean requireGaurdian = true;
	
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private LegacyLearnershipAssessmentService legacyLearnershipAssessmentService = new LegacyLearnershipAssessmentService();
	private List<LegacyApprenticeship> legacyapprenticeshipList = null;

	private List<LegacyLearnershipAssessment> legacyLearnershipAssessmentList = null;

	/** The company. */
	private Company company;

	/** The training provider. */
	private Company trainingProvider;

	/** The training provider. */
	private NonSetaCompany nonSetaCompany;

	private Company hostCompany;

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	/** The doc. */
	private Doc doc;

	/** Addresses. */
	private Address residentialAddress;

	/** The postal address. */
	private Address postalAddress;

	/** The birth address. */
	private Address birthAddress;
	
	/** The birth address. */
	private Address legalGaurdianPostalAddress;

	/** The birth address. */
	private Address legalGaurdianResidentialAddress;

	/** The copy address. */
	private boolean copyAddress;
	/** The copy address. */
	private boolean copyAddress1;

	/** The copy address. */
	private boolean copyAddress2;

	/** The qualification. */
	private Qualification qualification;

	/** The users qualification list. */
	private List<UserQualifications> usersQualificationList;

	private boolean homeLanguageSelected = false;
	private boolean signoff = false;
	private boolean showInfo = false;
	private boolean show = false;
	private boolean showTP = false;
	private boolean editDates = true;
	private DesignatedTradeType designatedTradeType;
	/** The User Language */
	private UsersLanguage usersLanguage = new UsersLanguage();
	private UsersDisability usersDisability = new UsersDisability();
	private TrainingProviderApplication trainingProviderApplication;

	/**
	 * This is for UsersTraingProvider Details After the correct training company is
	 * selected.
	 */
	private UsersTrainingProvider usersTrainingProvider;

	private List<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();
	/** The User Language List */
	private ArrayList<UsersLanguage> usersLanguageList = new ArrayList<>();
	private List<UsersDisability> usersDisabilityList = new ArrayList<>();

	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private LegacySECTTwentyEight legacysecttwentyeight;

	/** Screen Booleans. */
	private Boolean sectionAbool;// Learn Reg

	/** The section bbool. */
	private Boolean sectionBbool;// Company Info

	/** The section cbool. */
	private Boolean sectionCbool;// Trainers Info

	/** The section dbool. */
	private Boolean sectionDbool;// Trainers More Detail

	/** The section uploadbool. */
	private Boolean sectionUploadbool;// UploadDocs

	/** The Appointment. */
	private Appointment appointment = new Appointment();

	private InterventionType interventionType;
	private Qualification selectedQualification;
	private SkillsProgram selectedSkillsProgram;
	private SkillsSet selectedSkillsSet;
	private UnitStandards selectedUnitStandards;

	private CompanyLearners companylearners;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	private boolean nonCreditBearing;
	private boolean showQual;
	private boolean disableFields = false;
	private boolean arplWithNoEmployer = false;
	private List<Sites> sitesList = null;
	private boolean useCompanyAsSite = false;
	private Sites selectedSite = null;

	private Date today = GenericUtility.getStartOfDay(new Date());
	
	private InterventionTypeService interventionTypeService = new InterventionTypeService(); 
	private UsersService usersService = new UsersService();
	private CompanyService companyService = new CompanyService();
	private WorkPlaceApprovalService     workPlaceApprovalService     = new WorkPlaceApprovalService();
	
	private String documentBoxID;
	private String workplaceApprovalMsg;
	private String status;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}	

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		companylearners = new CompanyLearners();
		String rsaIDorPassport= "";
		//String employerSDL= "";
		//String providerSDL= "";
		String fromDate = "";
		String toDate = "";
		String regDate = "";
				
			// testing
			if (super.getParameter("id", false) != null) {
				if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LegacyLearnerRegistration) {
					getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
					this.companylearners = companyLearnersService.findByKey(getSessionUI().getTask().getTargetKey(), ConfigDocProcessEnum.LegacyLearnerRegistration);
					LegacyCompanyLearnersService.instance().prepareNewRegistration(ConfigDocProcessEnum.LegacyLearnerRegistration, companylearners);
					if (companylearners.getAppointment() != null) {
						appointment = companylearners.getAppointment();
					}
					if (companylearners.getStatus() == ApprovalEnum.Rejected) {
						populateRejectReasons();
					}
					if (companylearners.getHostCompany() != null && companylearners.getHostCompany().getId() != null) {
						this.hostCompany = companyService.findByKey(companylearners.getHostCompany().getId());
						prepareHostCompanyAfterSearch();
						companylearners.setHostCompany(hostCompany);
					}
					usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(companylearners.getUser());
					usersDisabilityList = usersDisabilityService.findByKeyUser(companylearners.getUser());
					populateExistingLearner();
					
					if(getSessionUI().getTask().getFirstInProcess() != null && getSessionUI().getTask().getFirstInProcess()) {
						disableFields = true;
					}
					editDates = false;
				}
			}else if (super.getParameter("apprenticeshipId", true) != null) {	
				LegacyApprenticeship legacyapprenticeship = LegacyApprenticeshipService.instance().findByKey((Long) super.getParameter("apprenticeshipId", true));
				companylearners.setLegacyId(legacyapprenticeship.getId());
				companylearners.setLegacyTargetClass(LegacyApprenticeship.class.getName());
				companylearners.setRegistrationNumber(legacyapprenticeship.getAgreementNumber());
				if(legacyapprenticeship.getFromDate() != null && !legacyapprenticeship.getFromDate().matches("")) {
					fromDate = legacyapprenticeship.getFromDate();
				}
				if(legacyapprenticeship.getToDate() != null && !legacyapprenticeship.getToDate().matches("")) {
					toDate = legacyapprenticeship.getToDate();
				}
				if(legacyapprenticeship.getIdNo() != null && !legacyapprenticeship.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyapprenticeship.getIdNo();
				}else if(legacyapprenticeship.getAlternateId() != null && !legacyapprenticeship.getAlternateId().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyapprenticeship.getAlternateId();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacyapprenticeship.getSdlNo()!= null ) {
					employerSDL = legacyapprenticeship.getSdlNo();
				}else {
					addErrorMessage("Learner is not linked to an employer, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not linked to an employer, please contact the merSETA Office");
				}				
				if(legacyapprenticeship.getSdlNo()!= null ) {
					providerSDL = legacyapprenticeship.getSdlNo();
				}else {
					providerSDL = employerSDL;
				}*/	
				if(legacyapprenticeship.getContractStatus() != null) {
					status = legacyapprenticeship.getContractStatus().toUpperCase();
				}
				
				company = companyService.findByKey(legacyapprenticeship.getEmployer().getId());
				trainingProvider = companyService.findByKey(legacyapprenticeship.getCompany().getId());
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				
				this.interventionType = interventionTypeService.findByKey(HAJConstants.APPRENTICESHIP_ID);
				if(legacyapprenticeship.getQualification() != null) {
					this.qualification = legacyapprenticeship.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					addErrorMessage("Qualification not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("Qualification not found");
				}
				
				if(legacyapprenticeship.getOfoCodes() != null) {
					this.companylearners.setOfoCodes(legacyapprenticeship.getOfoCodes());
				}
				if(legacyapprenticeship.getContractStatus().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacyapprenticeship.getContractStatus().equalsIgnoreCase("Registered")) {
					regDate = legacyapprenticeship.getApprenticeshipRegisterDate();
				}
				this.companylearners.setRegistrationNumber(legacyapprenticeship.getAgreementNumber());
				
			}else if(super.getParameter("bursaryId", true) != null){
				LegacyBursary legacybursary = LegacyBursaryService.instance().findByKey((Long) super.getParameter("bursaryId", true));	
				companylearners.setLegacyId(legacybursary.getId());
				companylearners.setLegacyTargetClass(LegacyBursary.class.getName());
				
				if(legacybursary.getStartDate() != null && !legacybursary.getStartDate().matches("")) {
					fromDate = legacybursary.getStartDate();
				}
				if(legacybursary.getEndDate() != null && !legacybursary.getEndDate().matches("")) {
					toDate = legacybursary.getEndDate();
				}
				//fromDate = legacybursary.getStartDate();
				//toDate = legacybursary.getEndDate();
				if(legacybursary.getIdNo() != null && !legacybursary.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacybursary.getIdNo();
				}else if(legacybursary.getIdTwo() != null && !legacybursary.getIdTwo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacybursary.getIdTwo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				if(legacybursary.getDescription() != null) {
					status = legacybursary.getDescription().toUpperCase();
				}
				
				/*if(legacybursary.getEmployerSdl()!= null ) {
					employerSDL = legacybursary.getEmployerSdl();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}
				
				if(legacybursary.getPartnershipSdl()!= null && legacybursary.getPartnershipSdl()!= "Null" && legacybursary.getPartnershipSdl()!= "") {
					providerSDL = legacybursary.getPartnershipSdl();
				}else {
					providerSDL = employerSDL;
				}*/
				company = companyService.findByKey(legacybursary.getEmployer().getId());
				trainingProvider = companyService.findByKey(legacybursary.getCompany().getId());
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				
				this.interventionType = interventionTypeService.findByKey(26l);
				
				if(legacybursary.getQualification() != null) {
					this.qualification = legacybursary.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					super.ajaxRedirectToDashboard();
					throw new Exception("Qualification not found");
				}
				
				if(legacybursary.getOfoCodes() != null) {
					this.companylearners.setOfoCodes(legacybursary.getOfoCodes());
				}
				if(legacybursary.getDescription().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacybursary.getDescription().equalsIgnoreCase("Registered")) {
					regDate = legacybursary.getRegistrationDate();
				}
				this.companylearners.setRegistrationNumber(null);
				//legacybursaryList = bursaryservice.findBySdlNumber(legacybursary.getEmployerSdl());
			}else if(super.getParameter("internshipId", true) != null){
				LegacyInternship legacyinternship = LegacyInternshipService.instance().findByKey((Long) super.getParameter("internshipId", true));	
				companylearners.setLegacyId(legacyinternship.getId());
				companylearners.setLegacyTargetClass(LegacyInternship.class.getName());	
				
				if(legacyinternship.getStartDate() != null && !legacyinternship.getStartDate().matches("")) {
					fromDate = legacyinternship.getStartDate();
				}
				if(legacyinternship.getEndDate() != null && !legacyinternship.getEndDate().matches("")) {
					toDate = legacyinternship.getEndDate();
				}
				if(legacyinternship.getIdNo() != null && !legacyinternship.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyinternship.getIdNo();
				}else if(legacyinternship.getIdTwo() != null && !legacyinternship.getIdTwo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyinternship.getIdTwo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacyinternship.getEmployerSdl()!= null ) {
					employerSDL = legacyinternship.getEmployerSdl();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}*/
				if(legacyinternship.getStatus() != null) {
					status = legacyinternship.getStatus().toUpperCase();
				}
				/*if(legacyinternship.getPartnershipSDL()!= null ) {
					providerSDL = legacyinternship.getPartnershipSDL();
				}else {
					providerSDL = employerSDL;
				}*/
				company = companyService.findByKey(legacyinternship.getEmployer().getId());
				trainingProvider = companyService.findByKey(legacyinternship.getCompany().getId());
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				
				this.interventionType = interventionTypeService.findByKey(26l);
				
				if(legacyinternship.getQualification() != null) {
					this.qualification = legacyinternship.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					addErrorMessage("Qualification not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("Qualification not found");
				}
				
				if(legacyinternship.getOfoCodes() != null) {
					this.companylearners.setOfoCodes(legacyinternship.getOfoCodes());
				}
				if(legacyinternship.getStatus().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacyinternship.getStatus().equalsIgnoreCase("Registered")) {
					regDate = legacyinternship.getRegistrationDate();
				}
				this.companylearners.setRegistrationNumber(null);
			}else if(super.getParameter("learnershipId", true) != null){
				LegacyLearnership legacylearnership = LegacyLearnershipService.instance().findByKey((Long) super.getParameter("learnershipId", true));	
				companylearners.setLegacyId(legacylearnership.getId());
				companylearners.setLegacyTargetClass(LegacyLearnership.class.getName());	
				companylearners.setRegistrationNumber(legacylearnership.getAgreementRefNo());
				if(legacylearnership.getAgreementStartDate() != null && !legacylearnership.getAgreementStartDate().matches("")) {
					fromDate = legacylearnership.getAgreementStartDate();
				}
				if(legacylearnership.getAgreementEndDate() != null && !legacylearnership.getAgreementEndDate().matches("")) {
					toDate = legacylearnership.getAgreementEndDate();
				}
				if(legacylearnership.getIdNo() != null && !legacylearnership.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacylearnership.getIdNo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacylearnership.getEmployerSdl()!= null ) {
					employerSDL = legacylearnership.getEmployerSdl();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}*/
				if(legacylearnership.getAgreementStatusDesc() != null) {
					status = legacylearnership.getAgreementStatusDesc().toUpperCase();
				}
				/*if(legacylearnership.getProviderSdl()!= null ) {
					providerSDL = legacylearnership.getProviderSdl();
				}else {
					providerSDL = employerSDL;
				}*/
				
				company = companyService.findByKey(legacylearnership.getEmployer().getId());
				trainingProviderApplication = trainingProviderApplicationService.findByKey(legacylearnership.getTrainingProviderApplication().getId());				
				trainingProvider = trainingProviderApplication.getCompany();				
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				this.companylearners.setTrainingProviderApplication(trainingProviderApplication);
				if (this.companylearners.getTrainingProviderApplication() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
					this.companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
				}
				/*if(legacylearnership.getAccreditationNumber() != null) {
					this.companylearners.setTrainingProviderApplication(trainingProviderApplicationService.findByCertificateNumber(legacylearnership.getAccreditationNumber()));
				}*/
				this.interventionType = interventionTypeService.findByKey(32l);
				
				if(legacylearnership.getLearnership() != null) {
					Learnership learnership = legacylearnership.getLearnership();
					this.qualification = learnership.getQualification() ;
					if(qualification == null) {
						addErrorMessage("Qualification not found");
						super.ajaxRedirectToDashboard();
						throw new Exception("Qualification not found");
					}
					this.companylearners.setLearnership(learnership);
				}else {
					addErrorMessage("Learnership not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learnership not found");
				}
				
				if(legacylearnership.getAgreementStatusDesc().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacylearnership.getAgreementStatusDesc().equalsIgnoreCase("Registered")) {
					regDate = legacylearnership.getRegistrationDate();
				}
				this.companylearners.setRegistrationNumber(legacylearnership.getAgreementRefNo());
				legacyLearnershipAssessmentList= legacyLearnershipAssessmentService.findByLegacyLegacyLearnership(legacylearnership);
						//findByEmployerSdl(legacylearnership.getEmployerSdl());
			}else if(super.getParameter("skillsprogrammeId", true) != null){
				LegacySkillsProgramme legacyskillsprogramme = LegacySkillsProgrammeService.instance().findByKey((Long) super.getParameter("skillsprogrammeId", true));
				companylearners.setLegacyId(legacyskillsprogramme.getId());
				companylearners.setLegacyTargetClass(LegacySkillsProgramme.class.getName());
				
				if(legacyskillsprogramme.getDtStartDate() != null && !legacyskillsprogramme.getDtStartDate().matches("")) {
					fromDate = legacyskillsprogramme.getDtStartDate();
				}
				if(legacyskillsprogramme.getDtEndDate() != null && !legacyskillsprogramme.getDtEndDate().matches("")) {
					toDate = legacyskillsprogramme.getDtEndDate();
				}
				if(legacyskillsprogramme.getIdNo() != null && !legacyskillsprogramme.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyskillsprogramme.getIdNo();
				}else if(legacyskillsprogramme.getAlternateId() != null && !legacyskillsprogramme.getAlternateId().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyskillsprogramme.getAlternateId();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacyskillsprogramme.getEmployerSDL()!= null ) {
					employerSDL = legacyskillsprogramme.getEmployerSDL();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}*/
				if(legacyskillsprogramme.getLearnerLPStatusDesc() != null) {
					status = legacyskillsprogramme.getLearnerLPStatusDesc();
				}
				/*if(legacyskillsprogramme.getProviderSDL()!= null ) {
					providerSDL = legacyskillsprogramme.getProviderSDL();
				}else {
					providerSDL = employerSDL;
				}*/
				company = companyService.findByKey(legacyskillsprogramme.getEmployer().getId());
				trainingProviderApplication = trainingProviderApplicationService.findByKey(legacyskillsprogramme.getTrainingProviderApplication().getId());				
				trainingProvider = trainingProviderApplication.getCompany();
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				this.companylearners.setTrainingProviderApplication(trainingProviderApplication);
				if (this.companylearners.getTrainingProviderApplication() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
					this.companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
				}
				if(legacyskillsprogramme.getSkillsProgram() != null) {
					this.interventionType = interventionTypeService.findByKey(SKILLS_PROGRAM);
				}else {
					this.interventionType = interventionTypeService.findByKey(SKILLS_SET);
				}
				
				if(legacyskillsprogramme.getSkillsProgram() != null) {
					SkillsProgram skillsProgram = legacyskillsprogramme.getSkillsProgram();
					this.qualification = legacyskillsprogramme.getSkillsProgram().getQualification() ;
					this.companylearners.setQualification(this.qualification);
					this.companylearners.setSkillsProgram(skillsProgram);
				}if(legacyskillsprogramme.getSkillsSet() != null) {
					SkillsSet skillsSet = legacyskillsprogramme.getSkillsSet();
					this.qualification = legacyskillsprogramme.getSkillsSet().getQualification() ;
					this.companylearners.setQualification(this.qualification);
					this.companylearners.setSkillsSet(skillsSet);
				}else {
					addErrorMessage("Skills Program/Set not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("Skills Program/Set not found");
				}
				if(legacyskillsprogramme.getLearnerLPStatusDesc().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacyskillsprogramme.getLearnerLPStatusDesc().equalsIgnoreCase("Registered")) {
					regDate = legacyskillsprogramme.getDtEffectiveDate();
				}
				this.companylearners.setRegistrationNumber(null);
				if(legacyskillsprogramme.getAccreditationNumber() != null) {
					this.companylearners.setTrainingProviderApplication(trainingProviderApplicationService.findByCertificateNumber(legacyskillsprogramme.getAccreditationNumber()));
					if (this.companylearners.getTrainingProviderApplication() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
						this.companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
					}
				}
			}else if(super.getParameter("legacytvet", true) != null){
				LegacyTvet legacytvet = LegacyTvetService.instance().findByKey((Long) super.getParameter("legacytvet", true));
				companylearners.setLegacyId(legacytvet.getId());
				companylearners.setLegacyTargetClass(LegacyTvet.class.getName());
				
				if(legacytvet.getStartDate() != null && !legacytvet.getStartDate().matches("")) {
					fromDate = legacytvet.getStartDate();
				}
				if(legacytvet.getEndDate() != null && !legacytvet.getEndDate().matches("")) {
					toDate = legacytvet.getEndDate();
				}
				if(legacytvet.getIdNo() != null && !legacytvet.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacytvet.getIdNo();
				}else if(legacytvet.getIdTwo() != null && !legacytvet.getIdTwo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacytvet.getIdTwo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacytvet.getEmployerSDL()!= null ) {
					employerSDL = legacytvet.getEmployerSDL();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}*/
				if(legacytvet.getStatus() != null) {
					status = legacytvet.getStatus().toUpperCase();
				}
				/*if(legacytvet.getPartnershipSDL()!= null ) {
					providerSDL = legacytvet.getPartnershipSDL();
				}else {
					providerSDL = employerSDL;
				}*/
				
				company = companyService.findByKey(legacytvet.getEmployer().getId());
				trainingProvider = companyService.findByKey(legacytvet.getCompany().getId());
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
						
				this.interventionType = interventionTypeService.findByKey(HAJConstants.tvet);
				
				if(legacytvet.getQualification() != null) {
					this.qualification = legacytvet.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					addErrorMessage("SkillsProgram not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("SkillsProgram not found");
				}
				
				if(legacytvet.getOfoCodes() != null) {
					this.companylearners.setOfoCodes(legacytvet.getOfoCodes());
				}
				
				if(legacytvet.getStatus().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacytvet.getStatus().equalsIgnoreCase("Registered")) {
					regDate = legacytvet.getRegistrationDate();
				}
				this.companylearners.setRegistrationNumber(null);
			}else if(super.getParameter("unitstandardId", true) != null){
				LegacyUnitStandard legacyunitstandard = LegacyUnitStandardService.instance().findByKey((Long) super.getParameter("unitstandardId", true));
				companylearners.setLegacyId(legacyunitstandard.getId());
				companylearners.setLegacyTargetClass(LegacyUnitStandard.class.getName());
				
				if(legacyunitstandard.getDtStartDate() != null && !legacyunitstandard.getDtStartDate().matches("")) {
					fromDate = legacyunitstandard.getDtStartDate();
				}
				if(legacyunitstandard.getDtEndDate() != null && !legacyunitstandard.getDtEndDate().matches("")) {
					toDate = legacyunitstandard.getDtEndDate();
				}
				if(legacyunitstandard.getIdNo() != null && !legacyunitstandard.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyunitstandard.getIdNo();
				}else if(legacyunitstandard.getAlternateId() != null && !legacyunitstandard.getAlternateId().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyunitstandard.getAlternateId();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				/*if(legacyunitstandard.getEmployerSDL()!= null ) {
					employerSDL = legacyunitstandard.getEmployerSDL();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}*/
				if(legacyunitstandard.getLearnerUSStatusDesc() != null) {
					status = legacyunitstandard.getLearnerUSStatusDesc().toUpperCase();
				}
				/*if(legacyunitstandard.getProviderSDL()!= null ) {
					providerSDL = legacyunitstandard.getProviderSDL();
				}else {
					providerSDL = employerSDL;
				}*/
				company = companyService.findByKey(legacyunitstandard.getEmployer().getId());
				trainingProviderApplication = trainingProviderApplicationService.findByKey(legacyunitstandard.getTrainingProviderApplication().getId());
				trainingProvider = trainingProviderApplication.getCompany();
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				this.companylearners.setTrainingProviderApplication(trainingProviderApplication);
				if (this.companylearners.getTrainingProviderApplication() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
					this.companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
				}
				
				this.interventionType = interventionTypeService.findByKey(HAJConstants.CREDIT_BEARING_SHORT_COURSE);
				if(legacyunitstandard.getUnitStandard() != null) {
					this.qualification = legacyunitstandard.getUnitStandard().getQualification() ;
					this.companylearners.setQualification(this.qualification);
					this.companylearners.setUnitStandard(legacyunitstandard.getUnitStandard());
				}else {
					addErrorMessage("Unit standard not found");
					super.ajaxRedirectToDashboard();
					throw new Exception("Unit standard not found");
				}
				if(legacyunitstandard.getLearnerUSStatusDesc().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacyunitstandard.getLearnerUSStatusDesc().equalsIgnoreCase("Registered")) {
					regDate = legacyunitstandard.getDtEffectiveDate();
				}
				this.companylearners.setRegistrationNumber(null);
				if(legacyunitstandard.getQualification() != null) {
					this.companylearners.setQualification(legacyunitstandard.getQualification());
				}
				if(legacyunitstandard.getAccreditationNumber() != null) {
					this.companylearners.setTrainingProviderApplication(trainingProviderApplicationService.findByCertificateNumber(legacyunitstandard.getAccreditationNumber()));
					if (this.companylearners.getTrainingProviderApplication() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite() != null && this.companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
						this.companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
					}
				}
			}else if(super.getParameter("secttwentyeightId", true) != null){
				legacysecttwentyeight = LegacySECTTwentyEightService.instance().findByKey((Long) super.getParameter("secttwentyeightId", true));
				companylearners.setLegacyId(legacysecttwentyeight.getId());
				companylearners.setLegacyTargetClass(LegacySECTTwentyEight.class.getName());
				
				if(legacysecttwentyeight.getApprenticeshipStatusEffectiveDate() != null && !legacysecttwentyeight.getApprenticeshipStatusEffectiveDate().matches("")) {
					fromDate = legacysecttwentyeight.getApprenticeshipStatusEffectiveDate();
				}
				if(legacysecttwentyeight.getApprenticeshipEndDate() != null && !legacysecttwentyeight.getApprenticeshipEndDate().matches("")) {
					toDate = legacysecttwentyeight.getApprenticeshipEndDate();
				}
				if(legacysecttwentyeight.getIdNo() != null && !legacysecttwentyeight.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacysecttwentyeight.getIdNo();
				}else if(legacysecttwentyeight.getAlternateIDNo() != null && !legacysecttwentyeight.getAlternateIDNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacysecttwentyeight.getAlternateIDNo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					//super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				
				if(legacysecttwentyeight.getDescription() != null) {
					status = legacysecttwentyeight.getDescription().toUpperCase();
				}
				
				if(legacysecttwentyeight.getEmployer() == null) {
					arplWithNoEmployer = true;
				}
				
				if(!arplWithNoEmployer) {
					company = companyService.findByKey(legacysecttwentyeight.getEmployer().getId());
					if(legacysecttwentyeight.getCompany() != null) {
						trainingProvider = companyService.findByKey(legacysecttwentyeight.getCompany().getId());
					}else {
						trainingProvider = company;
					}
					this.companylearners.setEmployer(company);
					this.companylearners.setCompany(trainingProvider);
					/*employerSDL = company.getLevyNumber();
					trainingProvider = companyService.findByKey(legacysecttwentyeight.getCompany().getId());
					providerSDL = trainingProvider.getLevyNumber();
					
					if(legacysecttwentyeight.getSdlNo()!= null || !legacysecttwentyeight.getSdlNo().matches("") || !legacysecttwentyeight.getSdlNo().isEmpty()) {
						employerSDL = legacysecttwentyeight.getSdlNo();
					}else {
						addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
						//super.ajaxRedirectToDashboard();
						throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
					}
					
					if(legacysecttwentyeight.getWasdl()!= null || !legacysecttwentyeight.getWasdl().matches("") || !legacysecttwentyeight.getWasdl().isEmpty()) {
						providerSDL = legacysecttwentyeight.getWasdl();
					}else {
						providerSDL = employerSDL;
					}*/
				}
				
				if(legacysecttwentyeight.getQualification()!= null) {
					this.qualification =legacysecttwentyeight.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					addErrorMessage("Qualification not found");
					//super.ajaxRedirectToDashboard();
					throw new Exception("Qualification not found");
				}
				if(legacysecttwentyeight.getDescription().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacysecttwentyeight.getDescription().equalsIgnoreCase("Registered")) {
					regDate = legacysecttwentyeight.getApprenticeshipStatusEffectiveDate();
				}
				this.interventionType = interventionTypeService.findByKey(HAJConstants.ARPL_ID);			
				this.companylearners.setRegistrationNumber(null);
				
			}else if(super.getParameter("legacyexperientialId", true) != null){
				LegacyExperiential legacyexperiential  = LegacyExperientialService.instance().findByKey((Long) super.getParameter("legacyexperientialId", true));
				companylearners.setLegacyId(legacyexperiential.getId());
				companylearners.setLegacyTargetClass(LegacyExperiential.class.getName());
				
				if(legacyexperiential.getApplicationDate() != null && !legacyexperiential.getApplicationDate().matches("")) {
					fromDate = legacyexperiential.getApplicationDate();
				}
				if(legacyexperiential.getCompletionDate() != null && !legacyexperiential.getCompletionDate().matches("")) {
					toDate = legacyexperiential.getCompletionDate();
				}
				if(legacyexperiential.getIdNo() != null && !legacyexperiential.getIdNo().equalsIgnoreCase("")) {
					rsaIDorPassport = legacyexperiential.getIdNo();
				}else {
					addErrorMessage("RSA/Passport error when registering learner, please contact the merSETA Office");
					//super.ajaxRedirectToDashboard();
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
				if(legacyexperiential.getStatus() != null) {
					status = legacyexperiential.getStatus().toUpperCase();
				}
				/*if(legacyexperiential.getEmployerSdl()!= null ) {
					employerSDL = legacyexperiential.getEmployerSdl();
				}else {
					addErrorMessage("Learner is not kinked to an employer , please contact the merSETA Office");
					//super.ajaxRedirectToDashboard();
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}
				
				if(legacyexperiential.getProvider()!= null ) {
					providerSDL = legacyexperiential.getEmployerSdl();
				}else {
					providerSDL = employerSDL;
				}*/
				company = companyService.findByKey(legacyexperiential.getEmployer().getId());
				trainingProvider = companyService.findByKey(legacyexperiential.getCompany().getId());
				this.companylearners.setEmployer(company);
				this.companylearners.setCompany(trainingProvider);
				
				if(legacyexperiential.getQualification()!= null) {
					this.qualification =legacyexperiential.getQualification() ;
					this.companylearners.setQualification(this.qualification);
				}else {
					addErrorMessage("Qualification not found");
					//super.ajaxRedirectToDashboard();
					throw new Exception("Qualification not found");
				}
				if(legacyexperiential.getStatus().equalsIgnoreCase("Application")) {
					editDates = false;
				}else if(legacyexperiential.getStatus().equalsIgnoreCase("Registered")) {
					regDate = legacyexperiential.getDateUpdated();
				}
				
				if(legacyexperiential.getPracticalTrainingOther().matches("P1")) {
					this.interventionType = interventionTypeService.findByKey(39L);
				}else if(legacyexperiential.getPracticalTrainingOther().matches("P2")) {
					this.interventionType = interventionTypeService.findByKey(40L);
				}else if(legacyexperiential.getPracticalTrainingOther().matches("P1 & P2")) {
					this.interventionType = interventionTypeService.findByKey(41L);
				}else {
					this.interventionType = interventionTypeService.findByKey(42L);
				}
							
				this.companylearners.setRegistrationNumber(null);
			}else {
				addInfoMessage("Error when registering learner, please contact the merSETA Office");
				super.ajaxRedirectToDashboard();
			}
			
			if (super.getParameter("id", false) == null) {	
				if(rsaIDorPassport != null && !rsaIDorPassport.isEmpty() && rsaIDorPassport != "") {
					this.user = usersService.findByRsaId(rsaIDorPassport);
					searchSetObject();
					if(this.user == null) {
						this.user = usersService.findByPassportNumber(rsaIDorPassport);
					}				
					if(this.user==null) {
						this.user = new Users();
						LegacyPerson legacyPerson = LegacyPersonService.instance().findByIdNo(rsaIDorPassport);
						if(legacyPerson!=null && legacyPerson.getId() != null) {
							LegacyPersonService.instance().populateLegacyUser(this.user,legacyPerson);
						}/*else {
							addInfoMessage("Learner is not registered on the Legacy Person file, please contact the merSETA Office");
							super.ajaxRedirectToDashboard();
						}*/
					}
					
					//New code to verify RSA ID Number
					if (this.user.getRsaIDNumber() != null) {	
						IdVerificationRealTime irt = usersService.idVerificationRealTime(user);		
						user.setValidationStatus(irt.getStatus());
						if(irt != null && irt.getRealTimeResults()!= null) {
							user.setFirstName(irt.getRealTimeResults().getFirstNames());
							user.setLastName(irt.getRealTimeResults().getSurName());
							user.setDeceasedStatus(irt.getRealTimeResults().getDeceasedStatus());
							if(!user.getValidationStatus().matches("Failure")) {
								if(user.getDeceasedStatus().matches("Alive")) {
									GenericUtility.calcIDData(user);
								}else {
									throw new Exception("User with this ID Number is deceased as per Department of Home Affairs records");
								}
							}						
						}
					}
					checkRequireGaurdian();
				}
				
				
				//populating employer and provider information
				if(!arplWithNoEmployer) {
					//this.company = companyService.findByLevyNumber(employerSDL);
					companyService.resolveCompanyAddresses(this.company);
					
					/*if(employerSDL != null && !employerSDL.isEmpty()&& !employerSDL.matches("") && employerSDL.length() > 0) {
						this.company = companyService.findByLevyNumber(employerSDL);
					}*/
					
					if (this.company != null && this.company.getId() != null) {
						companyService.resolveContactPerson(company);
						this.company.setDoneSearch(true);
					} else {
						this.company = new Company();
						this.company.setDoneSearch(false);			
					}
					
					//this.trainingProvider = companyService.findByLevyNumber(providerSDL);
					companyService.resolveCompanyAddresses(this.trainingProvider);
					/*if(providerSDL != null && providerSDL != "NULL" && providerSDL != "") {
						this.trainingProvider = companyService.findByLevyNumber(providerSDL);
					}else {
						if(employerSDL != null && !employerSDL.isEmpty()&& !employerSDL.matches("") && employerSDL.length() > 0) {
							this.trainingProvider = companyService.findByLevyNumber(employerSDL);
						}
					}*/
					
					if (this.trainingProvider != null && this.trainingProvider.getId() != null) {
						this.trainingProvider.setDoneSearch(true);
					} else {
						this.trainingProvider = this.company;
						this.trainingProvider.setDoneSearch(false);			
					}
				}
			
				//populating company learners  information	
				companylearners.setInterventionType(this.interventionType);
				companylearners.setUser(this.user);
				companylearners.setCreateUser(getSessionUI().getActiveUser());
				SimpleDateFormat sdf = HAJConstants.sdfYYYYMMDD;
				if(fromDate != null && !fromDate.matches("") && !fromDate.matches("NULL")) {
					Date commencementDate=sdf.parse(fromDate);
					companylearners.setCommencementDate(commencementDate);
				}
				if(toDate != null && !toDate.matches("") && !fromDate.matches("NULL") && !toDate.isEmpty()) {
					Date completionDate=sdf.parse(toDate);
					companylearners.setCompletionDate(completionDate);
				}
				if(regDate != null && !regDate.matches("") && !regDate.matches("NULL") && !regDate.isEmpty()) {
					Date registrationDate=sdf.parse(regDate);
					companylearners.setLegacyRegistrationDate(registrationDate);
					companylearners.setDateLearnerRegistered(registrationDate);
				}
							
				//Date commencementDate=new SimpleDateFormat("dd/MM/yyyy").parse(fromDate); 
				//Date completionDate=new SimpleDateFormat("dd/MM/yyyy").parse(toDate); 
				
				if(rsaIDorPassport != null && !rsaIDorPassport.isEmpty() && rsaIDorPassport != "") {
					prepaddress();
					applySaqaData();
					applyInterventionData();
					prepareUserAfterSearch();
				}				
			}
		
	}

	public void startDateFilterData() {
		try {
			if (companylearners.getInterventionLevel() != null) {
				calculateEndDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void calculateEndDate() {
		if (companylearners.getCommencementDate() != null && companylearners.getInterventionType().getDuration() != null) {
			this.companylearners.setCompletionDate(GenericUtility.addMonthsToDate(this.companylearners.getCommencementDate(), this.companylearners.getInterventionType().getDuration()));
		}
	}
	
	public void submitLearnerRegistration() {
		String error = "";
		try {
			System.out.println("Inside submitLearnerRegistration method");
			// Learner Validation
			error = validateLearner();
			if (error.length() > 0 || !error.matches("")) {
				addErrorMessage(error);
			} else {
				if (interventionType.getForm().matches("015")) {
					if (usersLanguageList.size() <= 0) {
						throw new Exception("Select atleast one language");
					}
				}
				startLearnerReg(true);
			}

		}catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void startLearnerReg(boolean sendWorkflow) throws Exception {
		System.out.println("startLearnerReg:");
		cpyAddresses();
		this.user.setResidentialAddress(this.residentialAddress);
		this.user.setPostalAddress(this.postalAddress);
		//this.user.setBirthAddress(this.birthAddress);
		if(companylearners.isContinuation_flag_check_status()==true) {
			companylearners.setContinuation_flag(1);
		}else {
			companylearners.setContinuation_flag(0);
		}
		System.out.println("companylearners.getContinuation_flag()---"+companylearners.getContinuation_flag());
		if (requireGaurdian) {
			this.user.getLegalGaurdian().setResidentialAddress(legalGaurdianResidentialAddress);
			this.user.getLegalGaurdian().setPostalAddress(legalGaurdianPostalAddress);
		}
		
		
		if(!arplWithNoEmployer) {
			if (company == null || company.getId() == null) {
				throw new Exception("Please provide your training company");
			}
			if (companylearners.getInterventionType() == null) {
				super.ajaxRedirect("/pages/tp/learners.jsf");
				throw new Exception("Please provide intervention type");
			}
		}
		
		if(this.interventionType != null && this.interventionType.getForSdpAccreditaion() != null && this.interventionType.getForSdpAccreditaion()) {
			//validateSdpAccreditation();
		}
		//checkSmeQualificationMentor();
		if(this.trainingProviderApplication != null) {
			companylearners.setTrainingProviderApplication(trainingProviderApplication);
			if (companylearners.getTrainingProviderApplication() != null && companylearners.getTrainingProviderApplication().getTrainingSite() != null && companylearners.getTrainingProviderApplication().getTrainingSite().getId() != null ) {
				companylearners.setTrainingSite(this.companylearners.getTrainingProviderApplication().getTrainingSite());
			}
		}
		
		if(usersLanguageList.size() > 0) {
			boolean homelang = false;
			for(UsersLanguage ul:usersLanguageList) {
				if(ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
					homelang = true;
					break;
				}
			}
			if(!homeLanguageSelected) {
				throw new Exception("Please select home language");
			}
			if(!homelang) {
				throw new Exception("Please select home language");
			}
		}else {
			throw new Exception("Please add atleast one language");
		}
		
		System.out.println("getSessionUI().isExternalParty():"+getSessionUI().isExternalParty());
		/*if (getSessionUI().isExternalParty()) {
			companylearners.setLearnerStatus(LearnerStatusEnum.Application);
		}*/
		
		if(arplWithNoEmployer) {
			LegacyCompanyLearnersService.instance().createLearner(getSessionUI().getActiveUser(), this.user,  companylearners, usersLanguageList, usersDisabilityList, legacysecttwentyeight);
		}else {
			if(hostCompany !=null) {
				companylearners.setHostCompany(hostCompany);
			}
			LegacyCompanyLearnersService.instance().createLearner(getSessionUI().getActiveUser(), this.user, company, trainingProvider, companylearners, sendWorkflow, requireGaurdian, usersLanguageList, usersDisabilityList);
		}
		
		super.setParameter("companyId", null, true);
		super.setParameter("employerId", null, true);
		addInfoMessage(super.getEntryLanguage("update.successful"));
		clearCurrentLearner();
		super.ajaxRedirectToDashboard();
	}
		
	public void prepareUserAfterSearch() throws Exception {
		if(!arplWithNoEmployer) {
			if(companylearners.getInterventionType().getId() != HAJConstants.APPRENTICESHIP_ID) {
				LegacyCompanyLearnersService.instance().prepareNewRegistrationDocs(ConfigDocProcessEnum.LegacyLearnerRegistration, companylearners, null);
			}
		}
		//LegacyCompanyLearnersService.instance().prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.LegacyLearnerRegistration, companylearners, companylearners, null);
		resolveAddresses();
		loadUserLanguage();
		loadUsersDisability();
	}
	
	public void completeCompanyLearners() {
		try {
			LegacyCompanyLearnersService.instance().completeCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void rejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			LegacyCompanyLearnersService.instance().rejectCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			if (signoff) {
				companylearners.setSignoff(signoff);
				LegacyCompanyLearnersService.instance().finalApproveWorkflow(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
				ajaxRedirectToDashboard();
			} else {
				addErrorMessage("Please signof ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			LegacyCompanyLearnersService.instance().finalRejectCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearners.getId(), CompanyLearners.class.getName(), getSessionUI().getTask().getWorkflowProcess());
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareHostCompanyAfterSearch() throws Exception {

		if (hostCompany != null && hostCompany.getId() != null) {
			List<Users> list = companyUsersService.findCompanyContactPersonList(hostCompany.getId());
			if (list != null && list.size() > 0) {
				this.hostCompany.setContactPerson(list.get(0));
			}
		}
	}
	
	private void populateExistingLearner() throws Exception {
		applyInterventionData();
		this.company = companylearners.getEmployer();
		this.user = companylearners.getUser();
		this.trainingProvider = companylearners.getCompany();		
		if (companylearners.getCompany() != null && companylearners.getCompany().getId() != null) {
			List<Users> list = companyUsersService.findCompanyContactPersonList(companylearners.getCompany().getId());
			if (list != null && list.size() > 0) {
				this.trainingProvider.setContactPerson(list.get(0));
			}

			if (trainingProviderApplicationService.findByCompany(companylearners.getCompany()) != null && trainingProviderApplicationService.findByCompany(companylearners.getCompany()).size() > 0) {
				trainingProvider.setTrainingProviderApplication(trainingProviderApplicationService.findByCompany(companylearners.getCompany()).get(0));
			}
		}

		if (companylearners.getEmployer() != null && companylearners.getEmployer().getId() != null) {
			if (trainingProviderApplicationService.findByCompany(companylearners.getEmployer()) != null && trainingProviderApplicationService.findByCompany(companylearners.getEmployer()).size() > 0) {
				company.setTrainingProviderApplication(trainingProviderApplicationService.findByCompany(companylearners.getEmployer()).get(0));
				companylearners.getEmployer().setTrainingProviderApplication(trainingProviderApplicationService.findByCompany(companylearners.getEmployer()).get(0));
			}
		}
		if (this.company != null) {
			if (this.company.getPostalAddress() != null && this.company.getPostalAddress().getId() != null) {
				this.company.setPostalAddress(AddressService.instance().findByKey(this.company.getPostalAddress().getId()));
			} else {
				this.company.setPostalAddress(new Address());
			}
		}
		if (this.trainingProvider != null) {
			if (this.trainingProvider.getPostalAddress() != null && this.trainingProvider.getPostalAddress().getId() != null) {
				this.trainingProvider.setPostalAddress(AddressService.instance().findByKey(this.trainingProvider.getPostalAddress().getId()));
			} else {
				this.trainingProvider.setPostalAddress(new Address());
			}
		}
		setsearchComplete();
	}
	
	private void setsearchComplete() throws Exception {
		user.setExistingUser(true);
		user.setRegFieldsDone(true);
		company.setExistingCompany(true);
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		Users companyContactPerson = new Users();
		if (company != null && company.getId() != null) {
			List<Users> list = companyUsersService.findCompanyContactPersonList(company.getId());
			if (list != null && list.size() > 0) {
				companyContactPerson = list.get(0);
				company.setContactPerson(companyContactPerson);
			}

		}
		if (this.user.getResidentialAddress() != null) {
			this.residentialAddress = this.user.getResidentialAddress();
		} else {
			this.residentialAddress = new Address();
		}
		if (this.user.getPostalAddress() != null) {
			this.postalAddress = this.user.getPostalAddress();
			if (this.postalAddress.getSameAddress() != null);
			this.copyAddress = this.postalAddress.getSameAddress();
		} else {
			this.postalAddress = new Address();
			this.copyAddress = false;
		}

		if (this.user.getBirthAddress() != null) {
			this.birthAddress = user.getBirthAddress();
			if (birthAddress.getSameAddress() != null) this.copyAddress1 = birthAddress.getSameAddress();
		} else {
			this.birthAddress = new Address();
			this.copyAddress1 = false;
		}
		checkRequireGaurdian();
	}

	public void applySaqaData() {
		try {
			if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
				companyLearnersService.applySkillsProgram(companylearners);
			} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
				companyLearnersService.applySkillsSet(companylearners);
			} else {
				companyLearnersService.applySaqaData(companylearners);
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void applyInterventionData() {
		try {
			if (companylearners.getInterventionType() != null) {

				if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
					companylearners.setSkillsSet(null);
					companylearners.setUnitStandard(null);
				} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
					companylearners.setSkillsProgram(null);
					companylearners.setUnitStandard(null);
				} else if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					companylearners.setSkillsProgram(null);
					companylearners.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.nonCreditBearing = false;
					this.shortCreditBearing = true;
				} else if (companylearners.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
					companylearners.setSkillsProgram(null);
					companylearners.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = true;
				} else {
					companylearners.setSkillsProgram(null);
					companylearners.setSkillsSet(null);
					companylearners.setUnitStandard(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
				}
				companyLearnersService.applyInterventionData(companylearners);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare company after search.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void prepareCompanyAfterSearch() throws Exception {
		if (company != null && company.getId() != null) {
			List<Users> list = companyUsersService.findCompanyContactPersonList(company.getId());
			if (list != null && list.size() > 0) {
				this.company.setContactPerson(list.get(0));
			}
		}

		if (this.company.getResidentialAddress() != null) {
			// this.residentialAddress = this.company.getResidentialAddress();
		} else {
			this.company.setResidentialAddress(new Address());
		}
		if (this.company.getPostalAddress() != null) {
			// this.postalAddress = this.company.getPostalAddress();
			// this.copyAddress = this.postalAddress.getSameAddress();
		} else {
			// this.company.setResidentialAddress(new Address());
			this.company.setPostalAddress(new Address());
			this.copyAddress = false;
		}
	}



	/**
	 * Address Copy, Clear, Find.
	 */
	public void cpyAddresses() {
		try {
			if (this.copyAddress) {
				AddressService.instance().copyFromToAddress(this.residentialAddress, this.postalAddress);
			}

			if (this.copyAddress1) {
				AddressService.instance().copyFromToAddress(this.residentialAddress, this.birthAddress);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		this.postalAddress.setSameAddress(this.copyAddress);
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!this.copyAddress) {
			AddressService.instance().clearAddress(this.postalAddress);
		}
	}

	public void clearPostal1() {
		if (!this.copyAddress1) {
			AddressService.instance().clearAddress(this.birthAddress);
		}
	}

	public void prepaddress(){
		this.residentialAddress = new Address();
		this.postalAddress = new Address();
		this.birthAddress = new Address();
	}

	/**
	 * Resolve addresses.
	 */
	public void resolveAddresses() {
		if (this.sectionAbool != null && this.sectionAbool && this.user != null && this.user.getId() != null) {// user
			if (this.user.getResidentialAddress() != null) {
				this.residentialAddress = this.user.getResidentialAddress();
			} else {
				this.residentialAddress = new Address();
			}
			if (this.user.getPostalAddress() != null) {
				this.postalAddress = this.user.getPostalAddress();
				if (postalAddress.getSameAddress() != null) this.copyAddress = this.postalAddress.getSameAddress();
			} else {
				this.postalAddress = new Address();
				this.copyAddress = false;
			}
			if (this.user.getBirthAddress() != null) {
				this.birthAddress = user.getBirthAddress();
				if (birthAddress.getSameAddress() != null) this.copyAddress1 = this.birthAddress.getSameAddress();
			} else {
				this.birthAddress = new Address();
				this.copyAddress1 = false;
			}

		} else if (this.sectionBbool != null && this.sectionBbool) {// Company assigned to User
			if (this.company.getResidentialAddress() != null) {
				this.residentialAddress = this.company.getResidentialAddress();
			} else {
				this.residentialAddress = new Address();
			}

			if (this.company.getPostalAddress() != null) {
				this.postalAddress = this.company.getPostalAddress();
				this.copyAddress = this.postalAddress.getSameAddress();
			} else {
				this.postalAddress = new Address();
				this.copyAddress = false;
			}
		} else if (this.sectionCbool != null && this.sectionCbool) {// Training provider
			if (this.trainingProvider.getResidentialAddress() != null) {
				this.residentialAddress = this.trainingProvider.getResidentialAddress();
			} else {
				this.residentialAddress = new Address();
			}
			if (this.trainingProvider.getPostalAddress() != null) {
				this.postalAddress = this.trainingProvider.getPostalAddress();
				this.copyAddress = this.postalAddress.getSameAddress();
			} else {
				this.postalAddress = new Address();
				this.copyAddress = false;
			}
		}else {
			
		}

	}

	public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if (this.user.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.user.getDisabilityStatus().getId());
			} else if (this.usersDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.usersDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DisabilityRating> getSelectItemsUsersDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if (this.usersDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.usersDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void clearDisabilityRating() {
		try {
			this.user.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void loadUserLanguage() {
		try {
			if (user.getId() != null) {
				usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(user);
				for (UsersLanguage ul : usersLanguageList) {
					if (ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void loadUsersDisability() {
		try {
			if (user != null && user.getId() != null) {
				usersDisabilityList = usersDisabilityService.findByKeyUser(this.user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void languagePreCheck() throws Exception {
		if(usersLanguage != null && usersLanguage.getLanguage() != null) {
			for (UsersLanguage ul : usersLanguageList) {
				if (ul != null && ul.getLanguage() != null && usersLanguage != null && usersLanguage.getId() != null && usersLanguage.getRead() != null  && ul.getLanguage().getId() == usersLanguage.getLanguage().getId()) {
					throw new Exception("Language already exist on your language list");
				}
			}
		}else {
			throw new Exception("Select a language");
		}
	}

	public void usersDisabilityPreCheck() throws Exception {
		for (UsersDisability ud : usersDisabilityList) {
			if (ud != null && ud.getDisabilityStatus() != null && usersDisability != null && ud.getDisabilityStatus().getId() == usersDisability.getDisabilityStatus().getId()) {
				throw new Exception("Disability already exist on your disability list");
			}
		}
	}

	public void addLanguage() {
		try {

			languagePreCheck();
			if (usersLanguage != null && usersLanguage.getRead() != null) {
				usersLanguageList.add(usersLanguage);
				if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
					// uncomment to allow user to select only 1 home language
					homeLanguageSelected = true;
				}
				usersLanguage = new UsersLanguage();
			} else {
				addErrorMessage("Select a language or speak, read and write");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void addUsersDisability() {
		try {
			usersDisabilityPreCheck();
			if (usersDisability != null && usersDisability.getDisabilityStatus() != null) {
				usersDisabilityList.add(usersDisability);
				usersDisability = new UsersDisability();
			} else {
				addErrorMessage("Select a disability and disability rating");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareLanguageUpdate() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			// uncomment to allow user to select only 1 home language
			homeLanguageSelected = false;
		}
	}

	public void prepareUsersDisabilityUpdate() {
		usersDisabilityList.remove(usersDisability);
	}

	public void removeLanguageFromList() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getId() != null) {
			try {
				usersLanguageService.delete(usersLanguage);
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		if(usersLanguageList.size()==0) {
			homeLanguageSelected = false;
		}
		usersLanguage = new UsersLanguage();
	}

	public void removeUsersDisabilityFromList() {
		usersDisabilityList.remove(usersDisability);
		if (usersDisability.getId() != null) {
			try {
				usersDisabilityService.delete(usersDisability);
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		usersDisability = new UsersDisability();
	}
	
	
	public void createWorkplaceApproval() {
		try {
			String        msg           = "";
			Qualification qualification = companyLearnersService.getCompanyLearnerQualification(companylearners);
			if (this.interventionType.getWorkplaceApprovalRequired()) {
				if (qualification != null && this.trainingProvider != null && this.trainingProvider.getId() != null && workPlaceApprovalService.checkForExistingRecords(this.trainingProvider, qualification, null, null) == 0) {
					// createWorkplaceApproval = true;
					companyLearnersService.createWorkplaceApproval(this.trainingProvider, qualification, getSessionUI().getActiveUser());
					msg = "Your application has been submitted";
				} else if (qualification != null && this.company != null && this.company.getId() != null && workPlaceApprovalService.checkForExistingRecords(this.company, qualification, null, null) == 0) {
					// createWorkplaceApproval = true;
					companyLearnersService.createWorkplaceApproval(this.company, qualification, getSessionUI().getActiveUser());
					msg = "Your application has been submitted";
				} else {
					msg = "Workplace Approval has already been submitted for this qualification";
				}
			}
			// this.user = null;
			showInfo = false;
			super.runClientSideExecute("PF('wpaCompanyForQualDialog').hide()");
			if (msg != "") {
				addErrorMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cancelWorkplaceApproval() {
		try {
			super.runClientSideExecute("PF('wpaCompanyForQualDialog').hide()");
			showInfo = false;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	@Override
	public void callBackMethod(Object object) {
		try {
			System.out.println("intervention type ---"+this.interventionType.getDescription());
			if(this.interventionType.getDescription().trim()!=null && this.interventionType.getDescription().contains("Bursar")) {
				companylearners.setContinuation_flag_check(true);
			}else {
				companylearners.setContinuation_flag_check(false);
			}
			System.out.println(companylearners.isContinuation_flag_check());
			if (object instanceof Users) {
				
				if (sitesList.size() != 0 && !useCompanyAsSite) {
					if (selectedSite == null) {
						throw new Exception("Select A Site Before Proceeding");
					}else {
						companylearners.setSite(selectedSite);
					}
				}
				
				if (user == null) {
					this.user = (Users) object;
					if(this.user != null && this.user.getId()!= null) {						
						
					}
				} else {
					user.setLegalGaurdian((Users) object);
				}
				
				if (this.companylearners == null) {
					this.companylearners = new CompanyLearners();
				}
				searchSetObject();
				prepareUserAfterSearch();
			} else if (object instanceof Company) {
				if (this.company != null && !this.company.isDoneSearch()) {
					this.company = (Company) object;
					if (company == null || company.getId() == null) {
						company = null;
						throw new Exception("The company does not exist");
					}
					if (this.company.getId() == null) {
						this.company.setCompanyStatus(CompanyStatusEnum.NonMersetaCompany);						
					}
				} else {
					this.hostCompany = (Company) object;
					if (hostCompany == null || hostCompany.getId() == null) {
						hostCompany = null;
						throw new Exception("The company does not exist");
					}
					if (this.hostCompany.getId() == null) {
						this.hostCompany.setCompanyStatus(CompanyStatusEnum.NonMersetaCompany);
					} else {
						prepareHostCompanyAfterSearch();
						companylearners.setHostCompany(hostCompany);
					}
					// searchSetObject();
					// prepareCompanyAfterSearch();
				}
			} else if (object instanceof Company && this.sectionCbool) {
				this.trainingProvider = (Company) object;
				searchSetObject();
				prepareCompanyAfterSearch();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Search set object.
	 */
	public void searchSetObject() {
		getSearchCompanyUI().setObject(this);
		getSearchUserPassportOrIdUI().setObject(this);
	}
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the residential address.
	 *
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 *
	 * @param residentialAddress
	 *            the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 *
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 *
	 * @param postalAddress
	 *            the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}


	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification
	 *            the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the users qualification list.
	 *
	 * @return the users qualification list
	 */
	public List<UserQualifications> getUsersQualificationList() {
		return usersQualificationList;
	}

	/**
	 * Sets the users qualification list.
	 *
	 * @param usersQualificationList
	 *            the new users qualification list
	 */
	public void setUsersQualificationList(List<UserQualifications> usersQualificationList) {
		this.usersQualificationList = usersQualificationList;
	}
	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the training provider.
	 *
	 * @return the training provider
	 */
	public Company getTrainingProvider() {
		return trainingProvider;
	}

	/**
	 * Sets the training provider.
	 *
	 * @param trainingProvider
	 *            the new training provider
	 */
	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	/**
	 * Gets the section abool.
	 *
	 * @return the section abool
	 */
	public Boolean getSectionAbool() {
		return sectionAbool;
	}

	/**
	 * Sets the section abool.
	 *
	 * @param sectionAbool
	 *            the new section abool
	 */
	public void setSectionAbool(Boolean sectionAbool) {
		this.sectionAbool = sectionAbool;
	}

	/**
	 * Gets the section bbool.
	 *
	 * @return the section bbool
	 */
	public Boolean getSectionBbool() {
		return sectionBbool;
	}

	/**
	 * Sets the section bbool.
	 *
	 * @param sectionBbool
	 *            the new section bbool
	 */
	public void setSectionBbool(Boolean sectionBbool) {
		this.sectionBbool = sectionBbool;
	}

	/**
	 * Gets the section cbool.
	 *
	 * @return the section cbool
	 */
	public Boolean getSectionCbool() {
		return sectionCbool;
	}

	/**
	 * Sets the section cbool.
	 *
	 * @param sectionCbool
	 *            the new section cbool
	 */
	public void setSectionCbool(Boolean sectionCbool) {
		this.sectionCbool = sectionCbool;
	}

	/**
	 * Gets the section dbool.
	 *
	 * @return the section dbool
	 */
	public Boolean getSectionDbool() {
		return sectionDbool;
	}

	/**
	 * Sets the section dbool.
	 *
	 * @param sectionDbool
	 *            the new section dbool
	 */
	public void setSectionDbool(Boolean sectionDbool) {
		this.sectionDbool = sectionDbool;
	}

	/**
	 * Gets the section uploadbool.
	 *
	 * @return the section uploadbool
	 */
	public Boolean getSectionUploadbool() {
		return sectionUploadbool;
	}

	/**
	 * Sets the section uploadbool.
	 *
	 * @param sectionUploadbool
	 *            the new section uploadbool
	 */
	public void setSectionUploadbool(Boolean sectionUploadbool) {
		this.sectionUploadbool = sectionUploadbool;
	}

	/**
	 * Gets the users training provider.
	 *
	 * @return the users training provider
	 */
	public UsersTrainingProvider getUsersTrainingProvider() {
		return usersTrainingProvider;
	}

	/**
	 * Sets the users training provider.
	 *
	 * @param usersTrainingProvider
	 *            the new users training provider
	 */
	public void setUsersTrainingProvider(UsersTrainingProvider usersTrainingProvider) {
		this.usersTrainingProvider = usersTrainingProvider;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc
	 *            the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public boolean isSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public boolean isShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	/**
	 * @return the appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}

	/**
	 * @param appointment
	 *            the appointment to set
	 */
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public boolean isSignoff() {
		return signoff;
	}

	public void setSignoff(boolean signoff) {
		this.signoff = signoff;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public String validateLearner() throws Exception {
		String error = "";
		if (this.user.getId() != null) {
			
			System.out.println("this.user.getRoles():"+this.user.getRoles());
			
			ArrayList<CompanyLearners> companyLearnersList = (ArrayList<CompanyLearners>) companyLearnersService.allLearnersByUser(this.user);
			if (companyLearnersList.size() > 0) {
				for (CompanyLearners cl : companyLearnersList) {
					if (cl.getLearnerStatus() != LearnerStatusEnum.Terminated && cl.getLearnerStatus() != LearnerStatusEnum.Withdrawn && cl.getLearnerStatus() != LearnerStatusEnum.Rejected) {
						if(companylearners.getInterventionType().getBusary() != null && companylearners.getInterventionType().getBusary()) {
							/*if(cl.getLearnerStatus() == LearnerStatusEnum.Registered && cl.getQualification() !=null &&  cl.getQualification().getId() == companylearners.getQualification().getId()) {
								error = "Please do a learner completion for previous application before attempting a new registration";
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application && cl.getQualification() !=null && cl.getQualification().getId() == companylearners.getQualification().getId()) {
								error = "Please do a learner completion for previous application before attempting a new registration";
							}*/
							
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered) {
								if(cl.getQualification() !=null &&  cl.getQualification().getId().equals(companylearners.getQualification().getId())) {
									error = "Please do a learner completion for previous application before attempting a new registration";
									break;
								}
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application) {
								if(cl.getQualification() !=null && cl.getQualification().getId().equals(companylearners.getQualification().getId())) {
									error = "Please do a learner completion for previous application before attempting a new registration";
									break;
								}
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Completed) {
								companylearners.setNewBursary(false);
							}
						}else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship && cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered) {
								error = "Learner cannot be registered for more than 1 apprenticeship at same time unless one is terminated";
								break;
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application ) {
								error = "Learner cannot be registered for more than 1 apprenticeship at same time unless one is terminated!!!";
								break;
							}
						}else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership &&  cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered) {
								error = "Learner cannot be registered for more than 1 learnership at same time unless one is terminated";
								break;
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application ) {
								error = "Learner cannot be registered for more than 1 learnership at same time unless one is terminated !!!";
								break;
							}
						} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship && cl.getInterventionType().getDescription().contains("TVET")) {
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered) {
								error = "Learner cannot be registered for more than 1 learnership/apprenticeship at same time unless one is terminated";
								break;
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application ) {
								error = "Learner cannot be registered for more than 1 learnership/apprenticeship at same time unless one is terminated";
								break;
							}
						} else if (cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership && companylearners.getInterventionType().getDescription().contains("TVET")) {
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered) {
								error = "Learner cannot be registered for more than 1 learnership/apprenticeship at same time unless one is terminated";
								break;
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application ) {
								error = "Learner cannot be registered for more than 1 learnership/apprenticeship at same time unless one is terminated";
								break;
							}
						}else if(SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
							if(cl.getSkillsProgram() != null &&  companylearners.getSkillsProgram() != null && cl.getSkillsProgram().getId().equals(companylearners.getSkillsProgram().getId() )) {	
									error = "Learner cannot be registered for the same skills programme at the same time";
									break;
							}
						}else if(SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
							if(cl.getSkillsSet() != null && companylearners.getSkillsSet() != null && cl.getSkillsSet().getId().equals(companylearners.getSkillsSet().getId())) {	
									error = "Learner cannot be registered for the same skills programme at the same time";
									break;
							}
						}else if(companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
							if(cl.getUnitStandard() != null && companylearners.getUnitStandard() != null && cl.getUnitStandard().getId().equals(companylearners.getUnitStandard().getId()) ) {	
								error = "Learner cannot be registered for the same unit standard at the same time";
								break;
							}
						}else if(companylearners.getQualification() != null && cl.getQualification() != null) {
							if(cl.getLearnerStatus() == LearnerStatusEnum.Registered && companylearners.getInterventionType().getId().equals(cl.getInterventionType().getId()) && cl.getQualification() != null && companylearners.getQualification() != null && cl.getQualification().getId().equals(companylearners.getQualification().getId())) {
								error = "Learner cannot be registered for the same qualification at the same time";
								break;
							}else if(cl.getLearnerStatus() == LearnerStatusEnum.Application && companylearners.getInterventionType().getId() == cl.getInterventionType().getId() && cl.getQualification() != null && companylearners.getQualification() != null && cl.getQualification().getId().equals(companylearners.getQualification().getId())) {
								error = "Learner cannot be registered for the same qualification at the same time";
								break;
							}
						}
					}
				}
			}
		}

		return error;
	}
	
	public void checkRequireGaurdian() throws Exception {
		if (user.getMaried() == null || user.getMaried() == YesNoEnum.No) {
			requireGaurdian = this.user.getDateOfBirth() != null && GenericUtility.getAge(this.user.getDateOfBirth()) < 18;
			if (requireGaurdian) {
				this.gaurdian = new Users();
				if (user.getLegalGaurdian() == null) {
					this.user.setLegalGaurdian(gaurdian);
				} else if (user.getLegalGaurdian() != null) {
					if (this.user.getLegalGaurdian().getResidentialAddress() != null && this.user.getLegalGaurdian().getResidentialAddress().getId() != null) {
						this.user.getLegalGaurdian().setResidentialAddress(AddressService.instance().findByKey(this.user.getLegalGaurdian().getResidentialAddress().getId()));
						this.legalGaurdianPostalAddress = this.user.getLegalGaurdian().getPostalAddress();
					} else {
						this.user.getLegalGaurdian().setResidentialAddress(new Address());
					}

					if (this.user.getLegalGaurdian().getPostalAddress() != null && this.user.getLegalGaurdian().getPostalAddress().getId() != null) {
						this.user.getLegalGaurdian().setPostalAddress(AddressService.instance().findByKey(this.user.getLegalGaurdian().getPostalAddress().getId()));
						this.legalGaurdianPostalAddress = this.user.getLegalGaurdian().getPostalAddress();
					} else {
						this.user.getLegalGaurdian().setPostalAddress(new Address());
					}

					if (this.user.getLegalGaurdian().getPostalAddress() != null && this.user.getLegalGaurdian().getPostalAddress().getId() != null) {
						this.legalGaurdianPostalAddress = this.user.getLegalGaurdian().getPostalAddress();
						this.copyAddress2 = this.legalGaurdianPostalAddress.getSameAddress() != null ? this.legalGaurdianPostalAddress.getSameAddress() : true;
					} else {
						this.legalGaurdianPostalAddress = new Address();
						this.copyAddress2 = false;
					}
				}
			}
		} else {
			requireGaurdian = false;
		}
	}
	
	public void resetRequidGuidian() {
		if (requireGaurdian) {
			if (user.getMaried() == YesNoEnum.Yes) {
				requireGaurdian = false;
				user.setLegalGaurdian(null);
			}
		}
	}
	
	public void clearPostal2() {
		if (!this.copyAddress2) {
			AddressService.instance().clearAddress(this.legalGaurdianPostalAddress);
		}
	}
	
	public List<ProjectImplementationPlan> getSelectedProjectImplementationPlan() {
		ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
		List<ProjectImplementationPlan> l = null;
		try {
			l = projectImplementationPlanService.findByCompanyAndInterventionType(company, interventionType);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Funding> getSelectedFunding() {
		FundingService fundingService = new FundingService();
		List<Funding> l = null;
		try {
			l = fundingService.findByExcludeMerSETAKey();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Learnership> getSelectedLearnershipAll() {
		LearnershipService learnershipPlanService = new LearnershipService();
		List<Learnership> l = null;
		try {
			l = learnershipPlanService.findAllQualification();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Clear current learner.
	 */
	public void clearCurrentLearner() {
		this.user = null;
		this.interventionType = new InterventionType();
		this.qualification = new Qualification();
		this.usersQualificationList = new ArrayList<UserQualifications>();
		if (this.company != null && this.company.getId() != null) {
			this.company.setDoneSearch(true);
		} else {
			this.company = new Company();
			this.company.setDoneSearch(false);			
		}		
		this.companylearners.setInterventionType(new InterventionType());
		this.companylearners.setQualification(new Qualification());
		this.companylearners.setDocs(new ArrayList<Doc>());
		this.companylearners = new CompanyLearners();
		showInfo = false;

		super.setParameter("bursaryId", null, true);
		super.setParameter("internshipId", null, true);
		super.setParameter("learnershipId", null, true);
		super.setParameter("skillsprogrammeId", null, true);
		super.setParameter("tvetId", null, true);
		super.setParameter("unitstandardId", null, true);
		super.setParameter("secttwentyeightId", null, true);		
		super.setParameter("apprenticeshipId", null, true);
		
		super.ajaxRedirectToDashboard();
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public Qualification getSelectedQualification() {
		return selectedQualification;
	}

	public void setSelectedQualification(Qualification selectedQualification) {
		this.selectedQualification = selectedQualification;
	}

	public DesignatedTradeType getDesignatedTradeType() {
		return designatedTradeType;
	}

	public void setDesignatedTradeType(DesignatedTradeType designatedTradeType) {
		this.designatedTradeType = designatedTradeType;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public boolean isCopyAddress1() {
		return copyAddress1;
	}

	public void setCopyAddress1(boolean copyAddress1) {
		this.copyAddress1 = copyAddress1;
	}

	public boolean isCopyAddress2() {
		return copyAddress2;
	}

	public void setCopyAddress2(boolean copyAddress2) {
		this.copyAddress2 = copyAddress2;
	}


	public boolean isShowQual() {
		return showQual;
	}

	public void setShowQual(boolean showQual) {
		this.showQual = showQual;
	}

	public boolean isNonCreditBearing() {
		return nonCreditBearing;
	}

	public void setNonCreditBearing(boolean nonCreditBearing) {
		this.nonCreditBearing = nonCreditBearing;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public ArrayList<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public void setUsersLanguageList(ArrayList<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}

	public SkillsProgram getSelectedSkillsProgram() {
		return selectedSkillsProgram;
	}

	public void setSelectedSkillsProgram(SkillsProgram selectedSkillsProgram) {
		this.selectedSkillsProgram = selectedSkillsProgram;
	}

	public SkillsSet getSelectedSkillsSet() {
		return selectedSkillsSet;
	}

	public void setSelectedSkillsSet(SkillsSet selectedSkillsSet) {
		this.selectedSkillsSet = selectedSkillsSet;
	}

	public UnitStandards getSelectedUnitStandards() {
		return selectedUnitStandards;
	}

	public void setSelectedUnitStandards(UnitStandards selectedUnitStandards) {
		this.selectedUnitStandards = selectedUnitStandards;
	}


	public UsersDisability getUsersDisability() {
		return usersDisability;
	}

	public void setUsersDisability(UsersDisability usersDisability) {
		this.usersDisability = usersDisability;
	}

	public List<UsersDisability> getUsersDisabilityList() {
		return usersDisabilityList;
	}

	public void setUsersDisabilityList(List<UsersDisability> usersDisabilityList) {
		this.usersDisabilityList = usersDisabilityList;
	}

	public Company getHostCompany() {
		return hostCompany;
	}

	public void setHostCompany(Company hostCompany) {
		this.hostCompany = hostCompany;
	}

	public List<Sites> getSitesList() {
		return sitesList;
	}

	public void setSitesList(List<Sites> sitesList) {
		this.sitesList = sitesList;
	}

	public boolean isUseCompanyAsSite() {
		return useCompanyAsSite;
	}

	public void setUseCompanyAsSite(boolean useCompanyAsSite) {
		this.useCompanyAsSite = useCompanyAsSite;
	}
 
	public Sites getSelectedSite() {
		return selectedSite;
	}

	public void setSelectedSite(Sites selectedSite) {
		this.selectedSite = selectedSite;
	}

	public boolean isShowTP() {
		return showTP;
	}

	public void setShowTP(boolean showTP) {
		this.showTP = showTP;
	}
	
	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public boolean isDisableFields() {
		return disableFields;
	}

	public void setDisableFields(boolean disableFields) {
		this.disableFields = disableFields;
	}

	public Users getGaurdian() {
		return gaurdian;
	}

	public void setGaurdian(Users gaurdian) {
		this.gaurdian = gaurdian;
	}

	public boolean isRequireGaurdian() {
		return requireGaurdian;
	}

	public void setRequireGaurdian(boolean requireGaurdian) {
		this.requireGaurdian = requireGaurdian;
	}

	public Address getLegalGaurdianPostalAddress() {
		return legalGaurdianPostalAddress;
	}

	public void setLegalGaurdianPostalAddress(Address legalGaurdianPostalAddress) {
		this.legalGaurdianPostalAddress = legalGaurdianPostalAddress;
	}

	public Address getLegalGaurdianResidentialAddress() {
		return legalGaurdianResidentialAddress;
	}

	public void setLegalGaurdianResidentialAddress(Address legalGaurdianResidentialAddress) {
		this.legalGaurdianResidentialAddress = legalGaurdianResidentialAddress;
	}

	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	public String getWorkplaceApprovalMsg() {
		return workplaceApprovalMsg;
	}

	public void setWorkplaceApprovalMsg(String workplaceApprovalMsg) {
		this.workplaceApprovalMsg = workplaceApprovalMsg;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public boolean isEditDates() {
		return editDates;
	}

	public void setEditDates(boolean editDates) {
		this.editDates = editDates;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<LegacyLearnershipAssessment> getLegacyLearnershipAssessmentList() {
		return legacyLearnershipAssessmentList;
	}

	public void setLegacyLearnershipAssessmentList(List<LegacyLearnershipAssessment> legacyLearnershipAssessmentList) {
		this.legacyLearnershipAssessmentList = legacyLearnershipAssessmentList;
	}

	public boolean isArplWithNoEmployer() {
		return arplWithNoEmployer;
	}

	public void setArplWithNoEmployer(boolean arplWithNoEmployer) {
		this.arplWithNoEmployer = arplWithNoEmployer;
	}
}
