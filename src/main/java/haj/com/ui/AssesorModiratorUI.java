package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.BeanUtils;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.DocumentTrackerService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserUnitStandardService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyAssessorLearnershipService;
import haj.com.service.lookup.LegacyAssessorQualificationService;
import haj.com.service.lookup.LegacyAssessorSkillsProgrammeService;
import haj.com.service.lookup.LegacyAssessorUnitStandardService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;
import haj.com.service.lookup.LegacyModeratorLearnershipService;
import haj.com.service.lookup.LegacyModeratorQualificationService;
import haj.com.service.lookup.LegacyModeratorSkillsProgrammeService;
import haj.com.service.lookup.LegacyModeratorUnitStandardService;
import haj.com.service.lookup.QualificationService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class AssesorModiratorUI.
 */
@ManagedBean(name = "assesorModiratorUI")
@ViewScoped
public class AssesorModiratorUI extends AbstractUI {

	/** The service. */
	private UsersService service = new UsersService();

	/** The company list. */
	private List<Company> companyList = null;

	/** The company. */
	private Company company;

	/** The form user. */
	private Users formUser;

	/** The passport number. */
	private boolean passportNumber = false;

	/** The company service. */
	private CompanyService companyService = new CompanyService(getResourceBundle());

	/** The qualification service. */
	private QualificationService qualificationService = new QualificationService();

	/** The unit standards service. */
	private UnitStandardsService unitStandardsService = new UnitStandardsService();

	/** The doc. */
	private Doc doc;

	/** The qualification list. */
	private List<Qualification> qualificationList;
	
	/** The assessor application  qualification list. */
	private List<Qualification> assAppQualificationList;


	/** The qualification. */
	private Qualification qualification;
	
	/** The doubleAppQualification. */
	private Qualification assAppQualification; 
	
	/** The modAppQualification. */
	private Qualification modAppQualification;
	
	/** The Moderator application  qualification list. */
	private List<Qualification> modAppQualificationList;

	/** The company doc check. */
	private Boolean companyDocCheck;

	/** The user doc check. */
	private Boolean userDocCheck;

	private Boolean continueReg;

	/** The unit standards. */
	private List<UnitStandards> unitStandards;
	
	/** The unit standards. */
	private List<UnitStandards> assAppUnitStandards;
	
	/** The unit standards. */
	private List<UnitStandards> modAppUnitStandards;

	/** The unit standard. */
	private UnitStandards unitStandard;
	
	/** The unit standard. */
	private UnitStandards assAppUnitStandard;
	
	/** The unit standard. */
	private UnitStandards modAppUnitStandard;

	/** The Assessor Moderator Application. */
	private AssessorModeratorApplication amApplication = new AssessorModeratorApplication();

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
    private SearchCompanyUI searchCompanyUI;

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	/** maximum size of first name */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** maximum size of last name */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** maximum size of email */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** Telephone number format */
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** Cell number format */
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** maximum size of fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	/** The User Language */
	private UsersLanguage usersLanguage = new UsersLanguage();

	/** The User Disability */
	private UsersDisability     usersDisability               = new UsersDisability();

	/** The User Language List */
	private ArrayList<UsersLanguage> UsersLanguageList = new ArrayList<>();
	
	/** The User Language List */
	private List<UsersDisability>    usersDisabilityList  = new ArrayList<>();

	/** Boolean flag to check if home language is selected or not */
	private boolean homeLanguageSelected = false;

	private Address employerPhysicalAddress = new Address();

	private Address employerPostalAddress = new Address();
	
	private boolean searchTrainingProvider;
	
	private UsersLanguageService usersLanguageService=new UsersLanguageService();
	
	private UsersDisabilityService       usersDisabilityService       = new UsersDisabilityService();
	
	/**Company Postal Address*/
	//private Address compPostalAddress=new Address();
	/**Company Residential Address*/
	//private Address compResidentialAddress=new Address();
	
	/**User Postal Address*/
	private Address userPostalAddress=new Address();
	/**User Residential Address*/
	private Address userResidentialAddress=new Address();
	
	/**The AssessorModeratorApplicationService*/
	private AssessorModeratorApplicationService amService = new AssessorModeratorApplicationService();
	
	private boolean copyAddress;
	
	private ArrayList<AssessorModeratorApplication> userApplication=new ArrayList<>();
	
	private UserQualificationsService userQualificationsService=new UserQualificationsService();
	
	private UserUnitStandardService userUnitStandardService=new UserUnitStandardService();
	
	private boolean showUserInfo;
	
	private AssessorModeratorApplication extensionOfScopeAmApplication;
	
	private UsersService usersService = new UsersService();
	private AddressService addressService = new AddressService();
	
	private Boolean codeOfConductAccepted;
	
	/** The user qualifications. */
	private List<UserQualifications> userQualifications;
	
	/** The user unit standards. */
	private List<UserUnitStandard> userUnitStandards;
	
	/** The assessor moderator. */
	private Users assessorModerator;
	
	public static Boolean extensionOfScope;
	
	private AssessorModReRegistration assessorModReRegistration;
	private AssessorModExtensionOfScope assessorModExtensionOfScope;
	
	/**Legacy data*/
	private List<LegacyAssessorQualification> legacyAssessorQualificationList=new ArrayList<>();
	private List<LegacyAssessorSkillsProgramme> legacyAssessorSkillsProgrammeList=new ArrayList<>();
	private List<LegacyAssessorLearnership> legacyAssessorLearnershipList=new ArrayList<>();
	private List<LegacyAssessorUnitStandard> legacyAssessorUnitStandardList=new ArrayList<>();
	
	private LegacyAssessorUnitStandardService legacyAssessorUnitStandardService=new LegacyAssessorUnitStandardService();
	private LegacyAssessorQualificationService legacyAssessorQualificationService=new LegacyAssessorQualificationService();
	private LegacyAssessorSkillsProgrammeService legacyAssessorSkillsProgrammeService=new LegacyAssessorSkillsProgrammeService();
	private LegacyAssessorLearnershipService legacyAssessorLearnershipService=new LegacyAssessorLearnershipService();
	
	private List<LegacyModeratorQualification> legacyModeratorQualificationList=new ArrayList<>();
	private List<LegacyModeratorSkillsProgramme> legacyModeratorSkillsProgrammeList=new ArrayList<>();
	private List<LegacyModeratorLearnership> legacyModeratorLearnershipList=new ArrayList<>();
	private List<LegacyModeratorUnitStandard> legacyModeratorUnitStandardList=new ArrayList<>();
	
	private LegacyModeratorUnitStandardService legacyModeratorUnitStandardService=new LegacyModeratorUnitStandardService();
	private LegacyModeratorQualificationService legacyModeratorQualificationService=new LegacyModeratorQualificationService();
	private LegacyModeratorSkillsProgrammeService legacyModeratorSkillsProgrammeService=new LegacyModeratorSkillsProgrammeService();
	private LegacyModeratorLearnershipService legacyModeratorLearnershipService=new LegacyModeratorLearnershipService();
	
	private Boolean showSubmit;
	
	private List<LegacyAssessorAccreditation> assesorLegacyList=new ArrayList<>();
	private List<LegacyModeratorAccreditation> moderatorLegacyList=new ArrayList<>();
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
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void requestExternsionOfScope()
	{
		try {

			checkExtensionOfScopeDocProvided();
			if(codeOfConductAccepted==null || codeOfConductAccepted==false)
			{
				throw new Exception("Please accept code of conduct");
			}
			amService.requestExternsionOfScope(assessorModExtensionOfScope,getSessionUI().getActiveUser(), true, ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE, unitStandards, qualificationList, extensionOfScopeAmApplication, UsersLanguageList,codeOfConductAccepted);
			addInfoMessage(super.getEntryLanguage("your.regsitration.is.being.processed"));
			extensionOfScopeAmApplication=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void prepareExtensionOfScopeDetails()
	{
		try {
			assessorModExtensionOfScope=new AssessorModExtensionOfScope();
			amService.prepareRegistrationDocs(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE, null, null,null,assessorModExtensionOfScope, CompanyUserTypeEnum.User);
			userQualifications = userQualificationsService.findByUserAMApplicationAndAccept(getSessionUI().getActiveUser().getId(), extensionOfScopeAmApplication.getId(),true);
			userUnitStandards = userUnitStandardService.findByUserAndAPApplicationAndAccept(getSessionUI().getActiveUser().getId(), extensionOfScopeAmApplication.getId(),true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Check user doc provided.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void checkExtensionOfScopeDocProvided() throws Exception {
		if (assessorModExtensionOfScope.getDocs() != null) {
			List<Doc> list=assessorModExtensionOfScope.getDocs();
			for(Doc doc:list )
			{
				if(doc.getId() != null){
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						{

							throw new Exception("Please upload "+doc.getConfigDoc().getName() );
						}
					}
				}
			}
			
		}

	}
	
	
	public void clearExternsionOfScope()
	{
		extensionOfScopeAmApplication=null;
	}
	
	public void clearAssessorModReRegistration()
	{
		assessorModReRegistration=null;
	}
	
	
	
	/**
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
		companyInfo();
		
	}
	
	public void loadUserDisability() {
		if(formUser.getId() !=null)
		{
			usersDisabilityList = usersDisabilityService.findByKeyUser(formUser);
		}
		
		if(usersDisabilityList == null) {
			usersDisabilityList = new ArrayList<>();
		}
	}
	
	public void loadUserLanguage()
	{
		try {
			if(formUser.getId() !=null)
			{
				UsersLanguageList=(ArrayList<UsersLanguage>) usersLanguageService.findByUser(formUser);
				for(UsersLanguage ul:UsersLanguageList)
				{
					if (ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
						// uncomment to allow user to select only 1 home language
						homeLanguageSelected = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void updateFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			DocumentTrackerService.create(getSessionUI().getActiveUser(), doc, DocumentTrackerEnum.Upload);
			addInfoMessage(super.getEntryLanguage("upload.successful"));
			super.runClientSideExecute("PF('dlgDocUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes user documents that are 
	 * not related to the current Application
	 * */
	public void resolveDocForAMApplication()
	{
		ArrayList<Doc> docsForCurrentApplication=new ArrayList<>();
		for(Doc doc:assessorModerator.getDocs())
		{
			if(doc.getTargetKey()!=null && doc.getTargetKey().equals(extensionOfScopeAmApplication.getId()))
			{
				docsForCurrentApplication.add(doc);
			}
			
		}
		assessorModerator.setDocs(docsForCurrentApplication);
	}

	/**
	 * Get all Company for data table.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void companyInfo() throws Exception {

	}
	
	public void sendReRegistrationRequest()
	{
		try {
			checkReRegDocProvided();
			if(codeOfConductAccepted ==null ||codeOfConductAccepted==false)
			{
				throw new Exception("Please accept code of conduct");
			}
			qualificationList = userQualificationsService.findQualificationByAMApplication(extensionOfScopeAmApplication.getId());
			unitStandards = userUnitStandardService.findUnitStandardByAMApplication( extensionOfScopeAmApplication.getId());
			amService.sendReRegistrationRequest(assessorModReRegistration,getSessionUI().getActiveUser(), true,ConfigDocProcessEnum.AM_RE_REGISTRATION, extensionOfScopeAmApplication,codeOfConductAccepted);
			runClientSideExecute("uploadDone()");
			addInfoMessage(super.getEntryLanguage("your.regsitration.is.being.processed"));
			assessorModReRegistration=null;
		} catch (Exception e) {
			runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void preparReRegistration()
	{
		 try {
			assessorModReRegistration=new AssessorModReRegistration();
			amService.prepareRegistrationDocs(ConfigDocProcessEnum.AM_RE_REGISTRATION, assessorModReRegistration, null,null,null, CompanyUserTypeEnum.User);
			userQualifications = userQualificationsService.findByUserAMApplication(getSessionUI().getActiveUser().getId(), extensionOfScopeAmApplication.getId());
			userUnitStandards = userUnitStandardService.findByUserAndAPApplication(getSessionUI().getActiveUser().getId(), extensionOfScopeAmApplication.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Check user doc provided.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void checkReRegDocProvided() throws Exception {
		if (assessorModReRegistration.getDocs() != null) {
			List<Doc> list=assessorModReRegistration.getDocs();
			for(Doc doc:list )
			{
				if(doc.getId() != null){
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						{

							throw new Exception("Please upload "+doc.getConfigDoc().getName() );
						}
					}
				}
			}
			
		}

	}
	/**
	 * Insert trainingprovider into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void createAsssesorModirator() {
		try {
			
			validateUserEmail();
			if(amApplication.getCodeOfConductAccepted() ==null || amApplication.getCodeOfConductAccepted()==false)
			{
				throw new Exception("Please accept code of conduct");
			}
			if(copyAddress)
			{
				AddressService.instance().copyFromToAddress(userResidentialAddress, userPostalAddress);
			}
			amApplication.setFinalRejected(false);
			formUser.setPostalAddress(userPostalAddress);
			formUser.setResidentialAddress(userResidentialAddress);
			amApplication.setAssessorModeratorType(AssessorModeratorTypeEnum.MerSETA_AM);
			if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorAndNewModerator) {
				List<Doc> tempDocs=formUser.getDocs();
				//Creating 2 applications at the same time
				AssessorModeratorApplication assessorApplication=copyAMApplication(amApplication);
				assessorApplication.setApplicationType(AssessorModeratorAppTypeEnum.NewAssessorRegistration);
				assessorApplication.setAssessorModeratorType(AssessorModeratorTypeEnum.MerSETA_AM);
				amService.createAMApplicationAndSendTask(formUser, true, ConfigDocProcessEnum.AM, assAppUnitStandards, assAppQualificationList, assessorApplication, UsersLanguageList);
				if(formUser.getDocs()==null){formUser.setDocs(tempDocs);}
				AssessorModeratorApplication moderatorApplication=copyAMApplication(amApplication);
				moderatorApplication.setApplicationType(AssessorModeratorAppTypeEnum.NewModeratorRegistration);
				amService.createAMApplicationAndSendTask(formUser, true, ConfigDocProcessEnum.AM, modAppUnitStandards, modAppQualificationList, moderatorApplication, UsersLanguageList);
				
			} else {
				if(amApplication.getLegacyAssessorAccreditation() !=null) {
					ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.LEGACY_AM_APPLICATION;
					if(amApplication.getLegacyStatus().equalsIgnoreCase("Expired")){
						configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
					}
					amService.createAssessorLegacyAMApplicationAndSendTask(formUser, true, configDocProcessEnum,amApplication, UsersLanguageList, legacyAssessorQualificationList, legacyAssessorUnitStandardList,legacyAssessorSkillsProgrammeList,legacyAssessorLearnershipList);
				} else if(amApplication.getLegacyModeratorAccreditation() !=null) {
					ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.LEGACY_AM_APPLICATION;
					if(amApplication.getLegacyStatus().equalsIgnoreCase("Expired")){
						configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
					}
					amService.createModeratorLegacyAMApplicationAndSendTask(formUser, true,configDocProcessEnum,amApplication, UsersLanguageList, legacyModeratorQualificationList, legacyModeratorSkillsProgrammeList,legacyModeratorLearnershipList,legacyModeratorUnitStandardList);
				} else {
					amService.createAMApplicationAndSendTask(formUser, true, ConfigDocProcessEnum.AM, unitStandards, qualificationList, amApplication, UsersLanguageList);
				}
			}

			prepareNew();
			addInfoMessage(super.getEntryLanguage("your.regsitration.is.being.processed"));
			companyInfo();
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			e.printStackTrace();
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void validateUserEmail() throws Exception
	{
	
		if(formUser.getId() !=null)
		{
			usersService.isMailUsed(formUser.getEmail(),formUser);
		}
		else{
			usersService.checkEmailUsedEmailOnly(formUser.getEmail());
		}
	}

	
	public AssessorModeratorApplication copyAMApplication(AssessorModeratorApplication amApplication)
	{
		AssessorModeratorApplication am=new AssessorModeratorApplication();
		BeanUtils.copyProperties(amApplication, am);
		return am;
	}
	
	public void checkAccreditationNum() 
	{
		try {
			validateAccreditationNum() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	public void validateAccreditationNum() throws Exception 
	{
	
		if(formUser.getId() !=null){
			amApplication=amService.findByAccreditation(amApplication.getCertificateNumber(), formUser.getId());
			if(amApplication==null || amApplication.getStatus() !=ApprovalEnum.Approved)
			{
				amApplication=new AssessorModeratorApplication();
				throw new Exception("This option is available to existing accredited Assessors or Moderators");
			}
			else
			{
				showUserInfo=true;
				//Loaging qualification sand Unit Statands
				qualificationList=userQualificationsService.findQualificationByAMApplication(amApplication.getId());
				unitStandards=userUnitStandardService.findUnitStandardByAMApplication(amApplication.getId());
				if(qualificationList ==null){qualificationList=new ArrayList<>();}
				if(unitStandards ==null){unitStandards=new ArrayList<>();}
			}
		}
		else
		{
			throw new Exception("This option is available to existing accredited Assessors or Moderators");
		}
	
	}
	
	
	public void checkShowUserInfo()
	{
		try {
			if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorRegistration){
				legacyAssessorRegValidatio();
			}
			if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewModeratorRegistration){
				legacyModeratorRegValidatio();
			}
			if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorAndNewModerator){
				legacyAssessorRegValidatio();
				legacyModeratorRegValidatio();
			}
			
			if(formUser.getId() !=null)
			{
				amService.validateAssessorModeratorApp(formUser, amApplication.getApplicationType());
			}
			if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorAndNewModerator || amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorRegistration ||amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewModeratorRegistration)
			{
				showUserInfo=true;
			}
			else
			{
				AssessorModeratorAppTypeEnum tempAppType=amApplication.getApplicationType() ;
				amApplication=new AssessorModeratorApplication();
				amApplication.setApplicationType(tempAppType);
				showUserInfo=false;
			}
		} catch (Exception e) {
			showUserInfo=false;
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Update trainingprovider in database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void userUpdate() {
		try {
			service.update(this.formUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Gets the select items titles.
	 *
	 * @return the select items titles
	 */
	public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if(formUser.getDisabilityStatus() !=null)
			{
				l = disabilityRatingService.findByDisability(formUser.getDisabilityStatus().getId());
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
		List<DisabilityRating>  l                       = null;
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
	
	public void usersDisabilityPreCheck() throws Exception {
		for (UsersDisability ud : usersDisabilityList) {
			if (ud != null && ud.getDisabilityStatus() != null && usersDisability != null && ud.getDisabilityStatus().getId() == usersDisability.getDisabilityStatus().getId()) {
				throw new Exception("Disability already exist on your disability list");
			}
		}
	}
	
	public void prepareUsersDisabilityUpdate() {
		usersDisabilityList.remove(usersDisability);
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
	
	public void clearDisabilityRating()
	{
		try {
			formUser.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public boolean showExtensionRequest(AssessorModeratorApplication am)
	{
		boolean show=false;
		if(am.getStatus() ==ApprovalEnum.Approved || am.getStatus() ==ApprovalEnum.DeRegistered)
		{
		    boolean hideOnProd = "P".equals(HAJConstants.DEV_TEST_PROD);
		    if(!hideOnProd){
		    	show=true;//JUST FOR TESTING,TO BE REMOVED
		    }
			
			if(am.getEndDate()!=null)
			{
				int months=GenericUtility.getMonthsBetweenDates(new Date(), am.getEndDate());
				if(months<=6)
				{
					show=true;
				}
			}
		}
		return show;
	}
	
	public boolean showExtensionOfScope(AssessorModeratorApplication am) {
		boolean show=true;
		if(am.getEndDate() !=null) {
			int months=GenericUtility.getMonthsBetweenDates(new Date(), am.getEndDate());
			if( months < 1 ) {
				show=false;
			}
		}
	    if(am.getStatus() != ApprovalEnum.Approved) {
			show=false;
		}
	    if(am.getStatus()==ApprovalEnum.Approved) {
	    	boolean hideOnProd = "P".equals(HAJConstants.DEV_TEST_PROD);
		    if (!hideOnProd) {
		    	show=true; 
		    }
		}
		return show;
	}

	/**
	 * Delete trainingprovider from database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void userDelete() {
		try {
			service.delete(this.formUser);
			prepareNew();
			companyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of trainingprovider.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void prepareNew() {
		this.formUser = null;
		this.company = null;
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
		this.companyList = new ArrayList<>();
		prepareNewQualification();
		this.qualificationList = new ArrayList<>();
		this.assAppQualificationList=new ArrayList<>();
	    this.modAppQualificationList=new ArrayList<>();
		this.unitStandards = new ArrayList<>();
		this.assAppUnitStandards = new ArrayList<>();
		this.modAppUnitStandards = new ArrayList<>();
		userResidentialAddress=new Address();
		userPostalAddress=new Address();
		UsersLanguageList.clear();
		showUserInfo=false;
		continueReg=false;
	}

	/**
	 * Prepare new qualification.
	 */
	public void prepareNewQualification() {
		this.qualification = new Qualification();
		this.unitStandard = new UnitStandards();
	}

	/**
	 * Adds the qualification to list.
	 */
	public void addQualificationToList() {
		try {
			//validateUserQual(qualification) ;
			if(extensionOfScopeAmApplication !=null && extensionOfScopeAmApplication.getId() !=null)
			{
				validateQualificationAccreditatation(qualification, extensionOfScopeAmApplication.getUser(),extensionOfScopeAmApplication.getApplicationType());
			}
			else
			{
				validateQualificationAccreditatation(qualification, formUser,amApplication.getApplicationType());
			}
			if (qualification != null && !qualificationList.contains(qualification)) {
				this.qualificationList.add(qualification);
				if(extensionOfScopeAmApplication !=null && extensionOfScopeAmApplication.getId() !=null){
					addUnitStandards(qualificationList,unitStandards,extensionOfScopeAmApplication.getApplicationType(),getSessionUI().getActiveUser());
				}
				else{
					addUnitStandards(qualificationList,unitStandards,amApplication.getApplicationType(),formUser);
				}
				prepareNewQualification();
			}
			else
			{
				addWarningMessage("Qualification already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	
	/*NOT IN USE*/
	/**
	 * Ensure that user is not accreditated 
	 * for the same Qualification during an 
	 * active accreditation period for the 
	 * same application type
	 * */
	public void validateUserQual(Qualification qual) throws Exception
	{
		if(formUser.getId() !=null)
		{
				userApplication=(ArrayList<AssessorModeratorApplication>) amService.findByUserForValidation(formUser);
				if(userApplication !=null && userApplication.size()>0)
				{
					for(AssessorModeratorApplication amApp :  userApplication)
					{
						if(amApp.getApplicationType()==amApplication.getApplicationType())
						{
							ArrayList<UserQualifications> userQuallList=(ArrayList<UserQualifications>) userQualificationsService.findByUserAMApplication(formUser.getId(), amApp.getId());
							if(userQuallList !=null && userQuallList.size()>0)
							{
								for(UserQualifications userQal:userQuallList )
								{
									if(userQal.getQualification().equals(qual))
									{
										throw new Exception("Failed to add qualification beacause there is an active application for the qualification selected");
									}
								}
							}
						}
					}
				}
		}
	}
	
	/*NOT IN USE*/
	/**
	 * Ensure that user is not accreditated 
	 * for the same Unit Standard during an 
	 * active accreditation period for the 
	 * same application type
	 * */
	public void validateUserUS(UnitStandards us) throws Exception
	{
		if(formUser.getId() !=null)
		{
			userApplication=(ArrayList<AssessorModeratorApplication>) amService.findByUserForValidation(formUser);
			if(userApplication !=null && userApplication.size()>0)
			{
				for(AssessorModeratorApplication amApp :  userApplication)
				{
					if(amApp.getApplicationType()==amApplication.getApplicationType())
					{
						ArrayList<UserUnitStandard> userUSList=(ArrayList<UserUnitStandard>) userUnitStandardService.findByUserAndAPApplication(formUser.getId(), amApp.getId());
						
						if(userUSList !=null && userUSList.size()>0)
						{
							for(UserUnitStandard userUser:userUSList )
							{
								if(userUser.getUnitStandard().equals(us))
								{
									throw new Exception("Failed to add Unit Standard  beacause there is an active application for the Unit Standard selected");
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Adds the qualification to list.
	 */
	public void addAssAPpQualificationToList() {
		try {
			if (assAppQualification != null && !assAppQualificationList.contains(assAppQualification)) {
				//validateUserQual(assAppQualification);
				validateQualificationAccreditatation(qualification, formUser,AssessorModeratorAppTypeEnum.NewAssessorRegistration);
				this.assAppQualificationList.add(assAppQualification);
				addUnitStandards(assAppQualificationList,assAppUnitStandards,AssessorModeratorAppTypeEnum.NewAssessorRegistration,formUser);
				prepareNewAssAppQualification();
			}
			else
			{
				addWarningMessage("Qualification already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void addUnitStandards(List<Qualification> qualList,List<UnitStandards> usList,AssessorModeratorAppTypeEnum amAppType,Users user) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			if (qualList != null && qualList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualList);
				for(UnitStandards us:l){
					//Check if user is not Accredited for Unit Standard
					List<AssessorModeratorApplication> usCheck=null;
					if(user.getId() !=null){
						usCheck=userUnitStandardService.findAMApplicationByUserUSAndAccept(user.getId(),us.getId(), amAppType, true);
					}
					if((usCheck==null || usCheck.size()<1) && !usList.contains(us)){
						us.setCannotRemove(true);
						usList.add(us);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Prepare new qualification.
	 */
	public void prepareNewAssAppQualification() {
		this.assAppQualification = new Qualification();
		this.assAppUnitStandard = new  UnitStandards();
	}
	
	/**
	 * Adds the qualification to list.
	 */
	public void addModAppQualificationToList() {
		try {
			if (modAppQualification != null && !modAppQualificationList.contains(modAppQualification)) {
				//validateUserQual(modAppQualification);
				validateQualificationAccreditatation(qualification, formUser,AssessorModeratorAppTypeEnum.NewModeratorRegistration);
				this.modAppQualificationList.add(modAppQualification);
				addUnitStandards(modAppQualificationList,modAppUnitStandards,AssessorModeratorAppTypeEnum.NewModeratorRegistration,formUser);
				prepareNewModAppQualification();
			}
			else
			{
				addWarningMessage("Qualification already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Prepare new qualification.
	 */
	public void prepareNewModAppQualification() {
		try {
			this.modAppQualification = new Qualification();
			this.modAppUnitStandard = new  UnitStandards();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Removes the qualification from list.
	 */
	public void removeQualificationFromList() {
		try {
			qualificationList.remove(qualification);
			removequalificationUs(unitStandards, qualification);
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the qualification from list.
	 */
	public void removeAssAppQualificationFromList() {
		try {
			assAppQualificationList.remove(assAppQualification);
			removequalificationUs(assAppUnitStandards, assAppQualification);
			prepareNewAssAppQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the qualification from list.
	 */
	public void removeModAppQualificationFromList() {
		try {
			modAppQualificationList.remove(modAppQualification);
			removequalificationUs(modAppUnitStandards, modAppQualification);
			prepareNewModAppQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void removequalificationUs(List<UnitStandards> usList,Qualification qual)
	{
		List<UnitStandards>  newList=new ArrayList<>();
		newList.addAll(usList);
		if(usList !=null && usList.size()>0 && qual !=null && qual.getId() !=null){
			for(UnitStandards us:usList){
				if(us.getQualification()!=null && us.getQualification().equals(qual)){
					newList.remove(us);
				}
			}
			usList.clear();
			usList=newList;
		}
	}
	
	
	

	/**
	 * Adds the unit to list.
	 */
	public void addAssAppUnitToList() {
		try {
			if (assAppUnitStandard != null && !assAppUnitStandards.contains(assAppUnitStandard)) {
				//validateUserUS(assAppUnitStandard);
				validateUniStandardAccreditatation(assAppUnitStandard, formUser,AssessorModeratorAppTypeEnum.NewAssessorRegistration);
				this.assAppUnitStandards.add(assAppUnitStandard);
				prepareNewAssAppQualification();
			}
			else
			{
				addWarningMessage("Unit Standard already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Adds the unit to list.
	 */
	public void addModAppUnitToList() {
		try {
			if (modAppUnitStandard != null && !modAppUnitStandards.contains(modAppUnitStandard)) {
				//validateUserUS(modAppUnitStandard);
				validateUniStandardAccreditatation(modAppUnitStandard, formUser,AssessorModeratorAppTypeEnum.NewModeratorRegistration);
				this.modAppUnitStandards.add(modAppUnitStandard);
				prepareNewModAppQualification();
			}
			else
			{
				addWarningMessage("Unit Standard already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Adds the unit to list.
	 */
	public void addUnitToList() {
		try {
			if (unitStandard != null && !unitStandards.contains(unitStandard)) {
				//validateUserUS(unitStandard);
				if(extensionOfScopeAmApplication !=null && extensionOfScopeAmApplication.getId() !=null)
				{
					validateUniStandardAccreditatation(unitStandard, extensionOfScopeAmApplication.getUser(),extensionOfScopeAmApplication.getApplicationType());
				}
				else
				{
					validateUniStandardAccreditatation(unitStandard, formUser,amApplication.getApplicationType());
				}
				this.unitStandards.add(unitStandard);
				prepareNewQualification();
			}
			else
			{
				addWarningMessage("Unit Standard already in the list");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Adds the company to list.
	 */
	public void addCompanyToList() {
		try {

			if (this.formUser.getId() != null && this.company.getId() != null) {
				companyService.checkIfAlreadyRegistered(this.formUser, this.company);
			}
			List<Company> listTemp = companyList.stream().filter(cmp -> validateCompaniesNoID(cmp)).collect(Collectors.toList());

			if (listTemp == null || listTemp.size() == 0) {
				//Set address if company is new
				/*if(company.getId()==null){
					company.setResidentialAddress(compResidentialAddress);
					if(copyAddress){
						compResidentialAddress.setSameAddress(copyAddress);
						BeanUtils.copyProperties(compResidentialAddress, compPostalAddress);
					}
					company.setPostalAddress(compPostalAddress);
				}*/
				/*company.setResidentialAddress(compResidentialAddress);
				if(copyAddress){
					compResidentialAddress.setSameAddress(copyAddress);
					BeanUtils.copyProperties(compResidentialAddress, compPostalAddress);
				}
				company.setPostalAddress(compPostalAddress);*/
				this.companyList.add(company);
				
				prepareNewCompany();
			} else {
				addErrorMessage(getEntryLanguage("registration.in.list", company.getCompanyRegistrationNumber()));
			}
			checkCompanyDocProvided();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
			prepareNewCompany();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addLanguage() {
		try {

			languagePreCheck();
			UsersLanguageList.add(usersLanguage);
			if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
				// uncomment to allow user to select only 1 home language
				homeLanguageSelected = true;
			}
			usersLanguage = new UsersLanguage();

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void languagePreCheck() throws Exception {
		// Checking if language is not added already
		if(usersLanguage != null && usersLanguage.getLanguage() != null) {
			for (UsersLanguage ul : UsersLanguageList) {
				if (ul != null && ul.getLanguage() != null && usersLanguage != null && usersLanguage.getId() != null && usersLanguage.getRead() != null  && ul.getLanguage().getId() == usersLanguage.getLanguage().getId()) {
					throw new Exception("Language already exist on your language list");
				}
			}
		}else {
			throw new Exception("Select a language");
		}
	}

	public void prepareLanguageUpdate() {
		UsersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			// uncomment to allow user to select only 1 home language
			homeLanguageSelected = false;
		}
	}

	public void removeLanguageFromList() {
		UsersLanguageList.remove(usersLanguage);
		if(usersLanguage.getId() !=null)
		{
			try {
				usersLanguageService.delete(usersLanguage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		if(UsersLanguageList.size()==0) {
			homeLanguageSelected = false;
		}
		usersLanguage = new UsersLanguage();
	}

	/**
	 * Check company doc provided.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void checkCompanyDocProvided() throws Exception {
		companyDocCheck = true;
		for (Company company : companyList) {
			if (company.getDocs() != null) {
				company.getDocs().forEach(doc -> {
					if(doc.getId() != null){
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							this.company.setRegDone(false);
							companyDocCheck = false;
							addErrorMessage("Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>");
						}
					}
				});
			}
		}

	}

	/**
	 * Check user doc provided.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void checkUserDocProvided() throws Exception {
		userDocCheck = true;
		if (formUser.getDocs() != null) {
			this.formUser.getDocs().forEach(doc -> {
				if(doc.getId() != null){
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						userDocCheck = false;
						addErrorMessage("Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + this.formUser.getFirstName() + "</i><br/>");
					}
				}
			});
		}

	}

	/**
	 * Removes the unit from list.
	 */
	public void removeUnitFromList() {
		unitStandards.remove(unitStandard);
		prepareNewQualification();
	}
	
	/**
	 * Removes the unit from list.
	 */
	public void removeAssAppUnitFromList() {
		assAppUnitStandards.remove(assAppUnitStandard);
		prepareNewAssAppQualification();
	}
	
	/**
	 * Removes the unit from list.
	 */
	public void removeModAppUnitFromList() {
		modAppUnitStandards.remove(modAppUnitStandard);
		prepareNewModAppQualification();
	}

	/**
	 * Gets the company list.
	 *
	 * @return the company list
	 */
	public List<Company> getCompanyList() {
		return companyList;
	}

	/**
	 * Sets the company list.
	 *
	 * @param companyList
	 *            the new company list
	 */
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser
	 *            the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Checks if is passport number.
	 *
	 * @return true, if is passport number
	 */
	public boolean isPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber
	 *            the new passport number
	 */
	public void setPassportNumber(boolean passportNumber) {
		this.passportNumber = passportNumber;
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
	 * Gets the search user passport or id UI.
	 *
	 * @return the search user passport or id UI
	 */
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	/**
	 * Sets the search user passport or id UI.
	 *
	 * @param searchUserPassportOrIdUI
	 *            the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * Reset search.
	 */
	public void resetSearch() {
		this.company.setDoneSearch(true);
		this.company.setTempUpdate(true);
	}

	/**
	 * Removes the company from list.
	 */
	public void removeCompanyFromList() {
		companyList = companyList.stream().filter(cmp -> !validateCompaniesNoID(cmp)).collect(Collectors.toList());
		prepareNewCompany();
	}

	/**
	 * Validate companies no ID.
	 *
	 * @param cmp
	 *            the cmp
	 * @return true, if successful
	 */
	private boolean validateCompaniesNoID(Company cmp) {
		return cmp.getCompanyRegistrationNumber().equals(company.getCompanyRegistrationNumber());

	}

	/**
	 * Prepare new company.
	 */
	public void prepareNewCompany() {
		this.company = new Company();
		company.setCompanyName("");
		company.setCompanyRegistrationNumber("");
		this.company.setDoneSearch(false);
		this.company.setTempUpdate(false);
		this.company.setCompanyType(CompanyTypeEnum.AM);
		searchTrainingProvider=false;
		//compPostalAddress=new Address();
		//compResidentialAddress=new Address();
		copyAddress=false;
	}

	/**
	 * Update company.
	 */
	public void updateCompany() {
		try {
			prepareNewCompany();
			checkCompanyDocProvided();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				formUser.setRecieveEmailTaskNotification(true);
				HostingCompanyEmployeesService companyEmployeesService = new HostingCompanyEmployeesService();
				if(formUser.getId() !=null)
				{
					if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0){
						formUser=null;
						throw new Exception("Employees cannot register as an Assessor/Moderator.");
					}
				}
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.AM, null, this.formUser, CompanyUserTypeEnum.User);
				amApplication = new AssessorModeratorApplication();
				loadUserLanguage();
				loadUserDisability();
				if(formUser.getResidentialAddress() !=null)
				{
					userResidentialAddress=formUser.getResidentialAddress();
				}
				if(formUser.getPostalAddress() !=null)
				{
					userPostalAddress=formUser.getPostalAddress();
				}
			} else if (object instanceof Company) {
				this.company = (Company) object;
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.AM, this.company, null, CompanyUserTypeEnum.Company);
				
			}
			else if (object instanceof LegacyAssessorAccreditation) {
				LegacyAssessorAccreditation legacyAssessorAccreditation=(LegacyAssessorAccreditation) object;
				if(legacyAssessorAccreditation !=null && legacyAssessorAccreditation.getUser() !=null)
				{
					this.formUser =legacyAssessorAccreditation.getUser();
					if(formUser.getId() !=null){
						amService.validateAssessorModeratorApp(formUser, AssessorModeratorAppTypeEnum.NewAssessorRegistration);
					}
					formUser.setRecieveEmailTaskNotification(true);
					ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.LEGACY_AM_APPLICATION;
					if(legacyAssessorAccreditation.getAssessorStatusDesc().equalsIgnoreCase("Expired")){
						configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
					}
					companyService.prepareNewRegistrationType(configDocProcessEnum, null, this.formUser, CompanyUserTypeEnum.User);
					amApplication = new AssessorModeratorApplication();
					amApplication.setLegacyAssessorAccreditation(legacyAssessorAccreditation);
					loadUserLanguage();
					if(formUser.getResidentialAddress() !=null)
					{
						userResidentialAddress=formUser.getResidentialAddress();
					}
					if(formUser.getPostalAddress() !=null)
					{
						userPostalAddress=formUser.getPostalAddress();
					}
					populateAssessorLegecyData(legacyAssessorAccreditation);
					showUserInfo=true;
				}
				else
				{
					throw new Exception("No Assessor legacy data found for the ID number provided");
				}
			}
			else if (object instanceof LegacyModeratorAccreditation) {
				LegacyModeratorAccreditation legacyModeratorAccreditation=(LegacyModeratorAccreditation) object;
				if(legacyModeratorAccreditation !=null && legacyModeratorAccreditation.getUser() !=null)
				{
					this.formUser =legacyModeratorAccreditation.getUser();
					if(formUser.getId() !=null){
						amService.validateAssessorModeratorApp(formUser, AssessorModeratorAppTypeEnum.NewModeratorRegistration);
					}
					formUser.setRecieveEmailTaskNotification(true);
					ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.LEGACY_AM_APPLICATION;
					if(legacyModeratorAccreditation.getModeratorStatusDesc().equalsIgnoreCase("Expired")){
						configDocProcessEnum=ConfigDocProcessEnum.AM_RE_REGISTRATION;
					}
					companyService.prepareNewRegistrationType(configDocProcessEnum, null, this.formUser, CompanyUserTypeEnum.User);
					amApplication = new AssessorModeratorApplication();
					amApplication.setLegacyModeratorAccreditation(legacyModeratorAccreditation);
					loadUserLanguage();
					if(formUser.getResidentialAddress() !=null)
					{
						userResidentialAddress=formUser.getResidentialAddress();
					}
					if(formUser.getPostalAddress() !=null)
					{
						userPostalAddress=formUser.getPostalAddress();
					}
					populateAssessorLegecyData(legacyModeratorAccreditation);
					showUserInfo=true;
					
				}
				else
				{
					throw new Exception("No Moderator legacy data found for the ID number provided");
				}
			}
			
			
		} catch (Exception e) {
			if (object instanceof LegacyModeratorAccreditation || object instanceof LegacyAssessorAccreditation){
				formUser=null;
			}
			formUser=null;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void legacyAssessorRegValidatio() throws Exception
	{
		if(formUser !=null){
			LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
			if(formUser.getRsaIDNumber() !=null || formUser.getPassportNumber() !=null){
				String searchID=formUser.getRsaIDNumber();
				if(searchID==null || searchID.isEmpty() || searchID.equals("")){
					searchID=formUser.getPassportNumber();
				}
				//assesorLegacyList=legacyAssessorAccreditationService.findByIdNoStatusAndProcessed(searchID, "Registered", false);
				assesorLegacyList=legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditation(searchID, false);
				if(assesorLegacyList !=null && assesorLegacyList.size()>0)
				{
					formUser=null;
					throw new Exception("There is historical accreditation details linked to this ID/Passport number. Please use the Legacy Assessor/Moderator Registration option to complete the system registration process");
				}
				
			}
		}
	}
	
	public void legacyModeratorRegValidatio() throws Exception
	{
		if(formUser !=null){
			LegacyModeratorAccreditationService legacyAssessorAccreditationService=new LegacyModeratorAccreditationService();
			if(formUser.getRsaIDNumber() !=null || formUser.getPassportNumber() !=null){
				String searchID=formUser.getRsaIDNumber();
				if(searchID==null || searchID.isEmpty() || searchID.equals("")){
					searchID=formUser.getPassportNumber();
				}
				//moderatorLegacyList=legacyAssessorAccreditationService.findByIdNoStatusAndProcessed(searchID, "Registered", false);
				moderatorLegacyList=legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditation(searchID, false);
				if(moderatorLegacyList !=null && moderatorLegacyList.size()>0){
					formUser=null;
					throw new Exception("There is historical accreditation details linked to this ID/Passport number. Please use the Legacy Assessor/Moderator Registration option to complete the system registration process");
				}
				
			}
		}
	}
	
	public void populateAssessorLegecyData(LegacyModeratorAccreditation legacyModeratorAccreditation) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		amApplication.setApplicationType(AssessorModeratorAppTypeEnum.NewModeratorRegistration);
		amApplication.setLegacyStatus(legacyModeratorAccreditation.getModeratorStatusDesc());
		if(legacyModeratorAccreditation.getModeratorRegStartDate() !=null && !legacyModeratorAccreditation.getModeratorRegStartDate().isEmpty() 
		&& !legacyModeratorAccreditation.getModeratorRegStartDate().equals("") && !legacyModeratorAccreditation.getModeratorRegStartDate().equals("NULL"))
		{
			amApplication.setStartDate(formatter.parse(legacyModeratorAccreditation.getModeratorRegStartDate()));
		}
		if(legacyModeratorAccreditation.getModeratorRegEndDate() !=null && !legacyModeratorAccreditation.getModeratorRegEndDate().isEmpty() 
		&& !legacyModeratorAccreditation.getModeratorRegEndDate().equals("") && !legacyModeratorAccreditation.getModeratorRegEndDate().equals("NULL"))
		{
			amApplication.setEndDate(formatter.parse(legacyModeratorAccreditation.getModeratorRegEndDate()));
		}
		if(legacyModeratorAccreditation.getModeratorRegNo() !=null && !legacyModeratorAccreditation.getModeratorRegNo().isEmpty() 
		&& !legacyModeratorAccreditation.getModeratorRegNo().equals("") && !legacyModeratorAccreditation.getModeratorRegNo().equals("NULL"))
		{
			amApplication.setCertificateNumber(legacyModeratorAccreditation.getModeratorRegNo());
		}
		if(legacyModeratorAccreditation.getModeratorStatusEffectiveDate() !=null && !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().isEmpty() 
		&& !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().equals("") && !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().equals("NULL"))
		{
			amApplication.setApprovedDate(formatter.parse(legacyModeratorAccreditation.getModeratorStatusEffectiveDate()));
		}
		
		if(legacyModeratorAccreditation.getDecisionNumber() !=null && !legacyModeratorAccreditation.getDecisionNumber().isEmpty() 
		&& !legacyModeratorAccreditation.getDecisionNumber().equals("") && !legacyModeratorAccreditation.getDecisionNumber().equals("NULL"))
		{
			amApplication.setDecisionNumber(legacyModeratorAccreditation.getDecisionNumber());
		}
		
		if(legacyModeratorAccreditation.getReviewCommitteeDate() !=null && !legacyModeratorAccreditation.getReviewCommitteeDate().isEmpty() 
		&& !legacyModeratorAccreditation.getReviewCommitteeDate().equals("") && !legacyModeratorAccreditation.getReviewCommitteeDate().equals("NULL"))
		{
			amApplication.setReviewCommitteeDate(formatter.parse(legacyModeratorAccreditation.getReviewCommitteeDate()));
		}
		if(legacyModeratorAccreditation.getModeratorStatusEffectiveDate() !=null && !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().isEmpty() 
		&& !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().equals("") && !legacyModeratorAccreditation.getModeratorStatusEffectiveDate().equals("NULL"))
		{
			amApplication.setAssessorStatusEffectiveDate(formatter.parse(legacyModeratorAccreditation.getModeratorStatusEffectiveDate()));
		}
	}
	
	public void populateAssessorLegecyData(LegacyAssessorAccreditation legacyAssessorAccreditation) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		amApplication.setApplicationType(AssessorModeratorAppTypeEnum.NewAssessorRegistration);
		amApplication.setLegacyStatus(legacyAssessorAccreditation.getAssessorStatusDesc());
		if(legacyAssessorAccreditation.getAssessorRegStartDate() !=null && !legacyAssessorAccreditation.getAssessorRegStartDate().isEmpty() 
		&& !legacyAssessorAccreditation.getAssessorRegStartDate().equals("") && !legacyAssessorAccreditation.getAssessorRegStartDate().equals("NULL"))
		{
			amApplication.setStartDate(formatter.parse(legacyAssessorAccreditation.getAssessorRegStartDate()));
		}
		if(legacyAssessorAccreditation.getAssessorRegEndDate() !=null && !legacyAssessorAccreditation.getAssessorRegEndDate().isEmpty() 
	    && !legacyAssessorAccreditation.getAssessorRegEndDate().equals("")  && !legacyAssessorAccreditation.getAssessorRegEndDate().equals("NULL"))
		{
			amApplication.setEndDate(formatter.parse(legacyAssessorAccreditation.getAssessorRegEndDate()));
		}
		if(legacyAssessorAccreditation.getAssessorRegNo() !=null && !legacyAssessorAccreditation.getAssessorRegNo().isEmpty() 
	    && !legacyAssessorAccreditation.getAssessorRegNo().equals("")  && !legacyAssessorAccreditation.getAssessorRegNo().equals("NULL"))
		{
			amApplication.setCertificateNumber(legacyAssessorAccreditation.getAssessorRegNo());
		}
		if(legacyAssessorAccreditation.getAssessorStatusEffectiveDate() !=null && !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().isEmpty() 
		&& !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().equals("") && !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().equals("NULL"))
		{
			amApplication.setApprovedDate(formatter.parse(legacyAssessorAccreditation.getAssessorStatusEffectiveDate()));
		}
		
		if(legacyAssessorAccreditation.getDecisionNumber() !=null && !legacyAssessorAccreditation.getDecisionNumber().isEmpty()
		&& !legacyAssessorAccreditation.getDecisionNumber().equals(""))
		{
			amApplication.setDecisionNumber(legacyAssessorAccreditation.getDecisionNumber());
		}
		
		if(legacyAssessorAccreditation.getReviewCommitteeDate() !=null && !legacyAssessorAccreditation.getReviewCommitteeDate().isEmpty() 
		&& !legacyAssessorAccreditation.getReviewCommitteeDate().equals("") && !legacyAssessorAccreditation.getReviewCommitteeDate().equals("NULL"))
		{
			amApplication.setReviewCommitteeDate(formatter.parse(legacyAssessorAccreditation.getReviewCommitteeDate()));
		}
		if(legacyAssessorAccreditation.getAssessorStatusEffectiveDate() !=null && !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().isEmpty() 
		&& !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().equals("") && !legacyAssessorAccreditation.getAssessorStatusEffectiveDate().equals("NULL"))
		{
			amApplication.setAssessorStatusEffectiveDate(formatter.parse(legacyAssessorAccreditation.getAssessorStatusEffectiveDate()));
		}
		
	}

	/**
	 * On tab change.
	 *
	 * @param event
	 *            the event
	 */
	public void onTabChange(TabChangeEvent event) {

	}

	/**
	 * Gets the search company UI.
	 *
	 * @return the search company UI
	 */
	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	/**
	 * Sets the search company UI.
	 *
	 * @param searchCompanyUI
	 *            the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
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

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		doc.setCompany(company);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");
	}

	/**
	 * Store file user.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFileUser(FileUploadEvent event) {
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUploadUser').hide()");
	}

	/**
	 * Prep doc.
	 *
	 * @param index
	 *            the index
	 */
	public void prepDoc(int index) {
		this.doc = this.company.getDocs().get(index);
	}

	/**
	 * Done user bit.
	 */
	public void doneUserBit() {
		try {
			usersService.validateEmailAddressByUser(formUser);
			if (Boolean.TRUE.equals(userErrors(formUser) == null) && addressErrors(userResidentialAddress) == null && postalAddressErrors(userPostalAddress) == null) {
				validateUserEmail();
				assAppQualificationList.clear();
				qualificationList.clear();
				modAppQualificationList.clear();
				assAppUnitStandards.clear();
				modAppUnitStandards.clear();
				if(amApplication.getLegacyAssessorAccreditation()==null && amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.ModeratorReRegistration || amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration || amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope || amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.ModeratorExtensionOfScope)
				{
					if(formUser.getId() !=null){
						validateAccreditationNum();
					}
					else{
						throw new Exception("This option is available to existing accredited Assessors or Moderators");
					}
				}
				
				if (UsersLanguageList.size() <= 0) {
					throw new Exception("Please add aleast 1 language");
				}
				checkUserDocProvided();
				if (userDocCheck) {
					this.formUser.setRegFieldsDone(true);
					this.continueReg = true;
				}
				
				if(amApplication.getLegacyAssessorAccreditation() != null){
					loadAssessorLegacyQualification();
				}
				else if(amApplication.getLegacyModeratorAccreditation()!= null){
					loadModeratorLegacyQualification();
				}
			} else {
				
				if (Boolean.TRUE.equals(userErrors(formUser) != null)){
					addErrorMessage("Please correct User Information where applicable");
				}
				if (Boolean.TRUE.equals(addressErrors(userResidentialAddress) != null)){
					addErrorMessage("Please correct Physical Address Information where applicable");
				}
				if (Boolean.TRUE.equals(postalAddressErrors(userPostalAddress) != null)){
					addErrorMessage("Please correct Postal Address Information where applicable");
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void loadModeratorLegacyQualification() throws Exception
	{
		legacyModeratorQualificationList=legacyModeratorQualificationService.findByModeratorRegNo(amApplication.getCertificateNumber());
		legacyModeratorUnitStandardList=legacyModeratorUnitStandardService.findByModeratorRegNo(amApplication.getCertificateNumber());
		legacyModeratorSkillsProgrammeList=legacyModeratorSkillsProgrammeService.findByModeratorRegNo(amApplication.getCertificateNumber());
		legacyModeratorLearnershipList=legacyModeratorLearnershipService.findByModeratorRegNo(amApplication.getCertificateNumber());
		if(legacyModeratorQualificationList.size()>0) {
			List<Qualification>qualList =new ArrayList<>();
			for(LegacyAssessorQualification legacyAssessorQualification: legacyAssessorQualificationList) {
				if(legacyAssessorQualification.getQualification() != null) {
					qualList.add(legacyAssessorQualification.getQualification());
				}
			}
			List<UnitStandards>usList = unitStandardsService.findByQualificationList(qualList);
			for(UnitStandards unitStandards: usList) {
				LegacyModeratorUnitStandard legacyModeratorUnitStandard = new LegacyModeratorUnitStandard();
				legacyModeratorUnitStandard.setUnitStandard(unitStandards);
				legacyModeratorUnitStandard.setUnitStandardExpired(false);
				legacyModeratorUnitStandard.setModeratorRegNo(amApplication.getCertificateNumber());
				legacyModeratorUnitStandardList.add(legacyModeratorUnitStandard);
			}
		}
		showSubmit=false;
		if(legacyModeratorQualificationList.size()>0 ||
		legacyModeratorUnitStandardList.size()>0 ||
		legacyModeratorSkillsProgrammeList.size()>0 ||
		legacyModeratorLearnershipList.size()>0)
		{
			Boolean atLeastOneQualToBeProcessed=false;
			for(LegacyModeratorQualification qual:legacyModeratorQualificationList){
				if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
					atLeastOneQualToBeProcessed=true;
					break;
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyModeratorUnitStandard qual:legacyModeratorUnitStandardList){
					if(qual.getUnitStandardExpired() !=null && qual.getUnitStandardExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyModeratorSkillsProgramme qual:legacyModeratorSkillsProgrammeList){
					if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyModeratorLearnership qual:legacyModeratorLearnershipList){
					if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				showSubmit=false;
				throw new Exception("Your application cannot be processed because all your qualifications have expired");
			}
			showSubmit=true;
		}
		else
		{
			showSubmit=false;
			throw new Exception("No Qualifications details found, please conatct support");
		}
	}
	
	public void loadAssessorLegacyQualification() throws Exception
	{
		legacyAssessorQualificationList=legacyAssessorQualificationService.findByAssessorRegNo(amApplication.getCertificateNumber());
		legacyAssessorUnitStandardList=legacyAssessorUnitStandardService.findByAssessorRegNo(amApplication.getCertificateNumber());
		legacyAssessorSkillsProgrammeList=legacyAssessorSkillsProgrammeService.findByAssessorRegNo(amApplication.getCertificateNumber());
		legacyAssessorLearnershipList=legacyAssessorLearnershipService.findByAssessorRegNo(amApplication.getCertificateNumber());
		if(legacyAssessorQualificationList.size()>0) {
			List<Qualification>qualList =new ArrayList<>();
			for(LegacyAssessorQualification legacyAssessorQualification: legacyAssessorQualificationList) {
				if(legacyAssessorQualification.getQualification() != null) {
					qualList.add(legacyAssessorQualification.getQualification());
				}
			}
			List<UnitStandards>usList = unitStandardsService.findByQualificationList(qualList);
			for(UnitStandards unitStandards: usList) {
				LegacyAssessorUnitStandard legacyAssessorUnitStandard = new LegacyAssessorUnitStandard();
				legacyAssessorUnitStandard.setUnitStandard(unitStandards);
				legacyAssessorUnitStandard.setUnitStandardExpired(false);
				legacyAssessorUnitStandard.setAssessorRegNo(amApplication.getCertificateNumber());
				legacyAssessorUnitStandardList.add(legacyAssessorUnitStandard);
			}
		}
		
		if(legacyAssessorQualificationList.size()>0 ||
		legacyAssessorUnitStandardList.size()>0 ||
		legacyAssessorSkillsProgrammeList.size()>0 ||
		legacyAssessorQualificationList.size()>0)
		{
			Boolean atLeastOneQualToBeProcessed=false;
			for(LegacyAssessorQualification qual:legacyAssessorQualificationList){
				if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
					atLeastOneQualToBeProcessed=true;
					break;
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyAssessorUnitStandard qual:legacyAssessorUnitStandardList){
					if(qual.getUnitStandardExpired() !=null && qual.getUnitStandardExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyAssessorSkillsProgramme qual:legacyAssessorSkillsProgrammeList){
					if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				for(LegacyAssessorLearnership qual:legacyAssessorLearnershipList){
					if(qual.getQualificationExpired() !=null && qual.getQualificationExpired()==false){
						atLeastOneQualToBeProcessed=true;
						break;
					}
				}
			}
			if(!atLeastOneQualToBeProcessed){
				showSubmit=false;
				throw new Exception("Your application cannot be processed because all your qualifications have expired");
			}
			showSubmit=true;
		}
		else
		{
			showSubmit=false;
			throw new Exception("No Qualifications details found, please conatct support");
		}
		
		
	}
	
	
	
	

	/**
	 * Populates enum list for AssessorModeratorAppTypeEnum.
	 *
	 * @return List<AssessorModeratorAppTypeEnum>
	 */
	public List<SelectItem> getAssessorModeratorAppTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (AssessorModeratorAppTypeEnum val : AssessorModeratorAppTypeEnum.values()) {
			l.add(new SelectItem(val, val.getRegistrationName()));
		}
		return l;
	}
	
	
	/**Ensure that you are not 
	 * accreditated for the 
	 * same Qualification 
	 * @throws Exception */
	public void validateQualificationAccreditatation(Qualification qual,Users u,AssessorModeratorAppTypeEnum appType) throws Exception
	{
		amService.validateQualificationAccreditatation(qual, u, appType);
		
	}
	
	
	/**Ensure that you are not 
	 * accreditated for the 
	 * same Unit Standards 
	 * @throws Exception */
	public void validateUniStandardAccreditatation(UnitStandards us,Users u,AssessorModeratorAppTypeEnum appType) throws Exception
	{
		amService.validateUniStandardAccreditatation(us, u, appType);
	}
	
	public String getQualificationTitle()
	{
		String title="";
		if(amApplication!=null)
		{
			if(amApplication.getApplicationType() !=null)
			{
				if(amApplication.getApplicationType() ==AssessorModeratorAppTypeEnum.NewAssessorRegistration)
				{
					title="Assessor";
				}
				else
				{
					title="Moderator";
				}
			}
		}
		return title;
	}

	/**
	 * Done company bit.
	 */
	public void doneCompanyBit() {
		this.company.setRegDone(true);

	}

	/**
	 * Complete qualification.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Qualification> completeQualification(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeQualificationDate(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocomplete(desc, getNow());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> completeMersetaQualification(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> findMersetaWPAQualificationAutocomplete(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaWPAQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Qualification> findMersetaWPAQualificationAutocompleteVersionTwo(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findMersetaWPAQualificationAutocompleteVersionTwo(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Qualification> findQualificationAutocompleteWPARequired(String desc) {
		List<Qualification> l = null;
		try {
			l = qualificationService.findQualificationAutocompleteWPARequired(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete unit.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<UnitStandards> completeUnit(String desc) {
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitle(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Complete unit.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<UnitStandards> completeMerSetaUnit(String desc) {
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findMerSETAUnitStandras(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(userPostalAddress);
		}
	}
	
	public String userErrors(Users user) {
		if (user != null) {
			String errors = usersService.validiateUserInformation(user).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}
	
	public String addressErrors(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return "Physical Address " + errors;
			}
		}
		return null;
	}
	
	public String postalAddressErrors(Address address) {
		if (address != null) {
			String errors = addressService.validiateAddressInformation(address).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return "Postal Address " + errors;
			}
		}
		return null;
	}
	

	/**
	 * Gets the qualification list.
	 *
	 * @return the qualification list
	 */
	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	/**
	 * Sets the qualification list.
	 *
	 * @param qualificationList
	 *            the new qualification list
	 */
	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
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
	 * Gets the unit standards.
	 *
	 * @return the unit standards
	 */
	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	/**
	 * Sets the unit standards.
	 *
	 * @param unitStandards
	 *            the new unit standards
	 */
	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	/**
	 * Gets the unit standard.
	 *
	 * @return the unit standard
	 */
	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	/**
	 * Sets the unit standard.
	 *
	 * @param unitStandard
	 *            the new unit standard
	 */
	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	/**
	 * Gets the company doc check.
	 *
	 * @return the company doc check
	 */
	public Boolean getCompanyDocCheck() {
		return companyDocCheck;
	}

	/**
	 * Sets the company doc check.
	 *
	 * @param companyDocCheck
	 *            the new company doc check
	 */
	public void setCompanyDocCheck(Boolean companyDocCheck) {
		this.companyDocCheck = companyDocCheck;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

	public Long getMAX_LAST_NAME_SIZE() {
		return MAX_LAST_NAME_SIZE;
	}

	public void setMAX_LAST_NAME_SIZE(Long mAX_LAST_NAME_SIZE) {
		MAX_LAST_NAME_SIZE = mAX_LAST_NAME_SIZE;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
	}

	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public AssessorModeratorApplication getAmApplication() {
		return amApplication;
	}

	public void setAmApplication(AssessorModeratorApplication amApplication) {
		this.amApplication = amApplication;
	}

	public Boolean getContinueReg() {
		return continueReg;
	}

	public void setContinueReg(Boolean continueReg) {
		this.continueReg = continueReg;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public ArrayList<UsersLanguage> getUsersLanguageList() {
		return UsersLanguageList;
	}

	public void setUsersLanguageList(ArrayList<UsersLanguage> usersLanguageList) {
		UsersLanguageList = usersLanguageList;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}

	public Address getEmployerPhysicalAddress() {
		return employerPhysicalAddress;
	}

	public void setEmployerPhysicalAddress(Address employerPhysicalAddress) {
		this.employerPhysicalAddress = employerPhysicalAddress;
	}

	public Address getEmployerPostalAddress() {
		return employerPostalAddress;
	}

	public void setEmployerPostalAddress(Address employerPostalAddress) {
		this.employerPostalAddress = employerPostalAddress;
	}

	public boolean isSearchTrainingProvider() {
		return searchTrainingProvider;
	}

	public void setSearchTrainingProvider(boolean searchTrainingProvider) {
		this.searchTrainingProvider = searchTrainingProvider;
	}

	/*public Address getCompPostalAddress() {
		return compPostalAddress;
	}

	public void setCompPostalAddress(Address compPostalAddress) {
		this.compPostalAddress = compPostalAddress;
	}

	public Address getCompResidentialAddress() {
		return compResidentialAddress;
	}

	public void setCompResidentialAddress(Address compResidentialAddress) {
		this.compResidentialAddress = compResidentialAddress;
	}*/

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public Address getUserPostalAddress() {
		return userPostalAddress;
	}

	public void setUserPostalAddress(Address userPostalAddress) {
		this.userPostalAddress = userPostalAddress;
	}

	public Address getUserResidentialAddress() {
		return userResidentialAddress;
	}

	public void setUserResidentialAddress(Address userResidentialAddress) {
		this.userResidentialAddress = userResidentialAddress;
	}

	public Qualification getAssAppQualification() {
		return assAppQualification;
	}

	public void setAssAppQualification(Qualification assAppQualification) {
		this.assAppQualification = assAppQualification;
	}

	public List<Qualification> getAssAppQualificationList() {
		return assAppQualificationList;
	}

	public void setAssAppQualificationList(List<Qualification> assAppQualificationList) {
		this.assAppQualificationList = assAppQualificationList;
	}

	public UnitStandards getAssAppUnitStandard() {
		return assAppUnitStandard;
	}

	public void setAssAppUnitStandard(UnitStandards assAppUnitStandard) {
		this.assAppUnitStandard = assAppUnitStandard;
	}

	public List<UnitStandards> getAssAppUnitStandards() {
		return assAppUnitStandards;
	}

	public void setAssAppUnitStandards(List<UnitStandards> assAppUnitStandards) {
		this.assAppUnitStandards = assAppUnitStandards;
	}

	public Qualification getModAppQualification() {
		return modAppQualification;
	}

	public void setModAppQualification(Qualification modAppQualification) {
		this.modAppQualification = modAppQualification;
	}

	public List<Qualification> getModAppQualificationList() {
		return modAppQualificationList;
	}

	public void setModAppQualificationList(List<Qualification> modAppQualificationList) {
		this.modAppQualificationList = modAppQualificationList;
	}

	public UnitStandards getModAppUnitStandard() {
		return modAppUnitStandard;
	}

	public void setModAppUnitStandard(UnitStandards modAppUnitStandard) {
		this.modAppUnitStandard = modAppUnitStandard;
	}

	public List<UnitStandards> getModAppUnitStandards() {
		return modAppUnitStandards;
	}

	public void setModAppUnitStandards(List<UnitStandards> modAppUnitStandards) {
		this.modAppUnitStandards = modAppUnitStandards;
	}

	/**
	 * @return the showUserInfo
	 */
	public boolean isShowUserInfo() {
		return showUserInfo;
	}

	/**
	 * @param showUserInfo the showUserInfo to set
	 */
	public void setShowUserInfo(boolean showUserInfo) {
		this.showUserInfo = showUserInfo;
	}

	public AssessorModeratorApplication getExtensionOfScopeAmApplication() {
		return extensionOfScopeAmApplication;
	}

	public void setExtensionOfScopeAmApplication(AssessorModeratorApplication extensionOfScopeAmApplication) {
		this.extensionOfScopeAmApplication = extensionOfScopeAmApplication;
	}

	/**
	 * @return the codeOfConductAccepted
	 */
	public Boolean getCodeOfConductAccepted() {
		return codeOfConductAccepted;
	}

	/**
	 * @param codeOfConductAccepted the codeOfConductAccepted to set
	 */
	public void setCodeOfConductAccepted(Boolean codeOfConductAccepted) {
		this.codeOfConductAccepted = codeOfConductAccepted;
	}

	public UserUnitStandardService getUserUnitStandardService() {
		return userUnitStandardService;
	}

	public void setUserUnitStandardService(UserUnitStandardService userUnitStandardService) {
		this.userUnitStandardService = userUnitStandardService;
	}

	public List<UserUnitStandard> getUserUnitStandards() {
		return userUnitStandards;
	}

	public void setUserUnitStandards(List<UserUnitStandard> userUnitStandards) {
		this.userUnitStandards = userUnitStandards;
	}

	public List<UserQualifications> getUserQualifications() {
		return userQualifications;
	}

	public void setUserQualifications(List<UserQualifications> userQualifications) {
		this.userQualifications = userQualifications;
	}

	public Users getAssessorModerator() {
		return assessorModerator;
	}

	public void setAssessorModerator(Users assessorModerator) {
		this.assessorModerator = assessorModerator;
	}

	public Boolean getExtensionOfScope() {
		return extensionOfScope;
	}

	public void setExtensionOfScope(Boolean extensionOfScope) {
		AssesorModiratorUI.extensionOfScope = extensionOfScope;
	}

	public AssessorModReRegistration getAssessorModReRegistration() {
		return assessorModReRegistration;
	}

	public void setAssessorModReRegistration(AssessorModReRegistration assessorModReRegistration) {
		this.assessorModReRegistration = assessorModReRegistration;
	}

	public AssessorModExtensionOfScope getAssessorModExtensionOfScope() {
		return assessorModExtensionOfScope;
	}

	public void setAssessorModExtensionOfScope(AssessorModExtensionOfScope assessorModExtensionOfScope) {
		this.assessorModExtensionOfScope = assessorModExtensionOfScope;
	}

	public List<LegacyAssessorSkillsProgramme> getLegacyAssessorSkillsProgrammeList() {
		return legacyAssessorSkillsProgrammeList;
	}

	public void setLegacyAssessorSkillsProgrammeList(
			List<LegacyAssessorSkillsProgramme> legacyAssessorSkillsProgrammeList) {
		this.legacyAssessorSkillsProgrammeList = legacyAssessorSkillsProgrammeList;
	}

	public List<LegacyAssessorLearnership> getLegacyAssessorLearnershipList() {
		return legacyAssessorLearnershipList;
	}

	public void setLegacyAssessorLearnershipList(List<LegacyAssessorLearnership> legacyAssessorLearnershipList) {
		this.legacyAssessorLearnershipList = legacyAssessorLearnershipList;
	}

	public List<LegacyAssessorUnitStandard> getLegacyAssessorUnitStandardList() {
		return legacyAssessorUnitStandardList;
	}

	public void setLegacyAssessorUnitStandardList(List<LegacyAssessorUnitStandard> legacyAssessorUnitStandardList) {
		this.legacyAssessorUnitStandardList = legacyAssessorUnitStandardList;
	}

	public List<LegacyAssessorQualification> getLegacyAssessorQualificationList() {
		return legacyAssessorQualificationList;
	}

	public void setLegacyAssessorQualificationList(List<LegacyAssessorQualification> legacyAssessorQualificationList) {
		this.legacyAssessorQualificationList = legacyAssessorQualificationList;
	}

	public Boolean getShowSubmit() {
		return showSubmit;
	}

	public void setShowSubmit(Boolean showSubmit) {
		this.showSubmit = showSubmit;
	}

	public List<LegacyModeratorLearnership> getLegacyModeratorLearnershipList() {
		return legacyModeratorLearnershipList;
	}

	public void setLegacyModeratorLearnershipList(List<LegacyModeratorLearnership> legacyModeratorLearnershipList) {
		this.legacyModeratorLearnershipList = legacyModeratorLearnershipList;
	}

	public List<LegacyModeratorQualification> getLegacyModeratorQualificationList() {
		return legacyModeratorQualificationList;
	}

	public void setLegacyModeratorQualificationList(List<LegacyModeratorQualification> legacyModeratorQualificationList) {
		this.legacyModeratorQualificationList = legacyModeratorQualificationList;
	}

	public List<LegacyModeratorSkillsProgramme> getLegacyModeratorSkillsProgrammeList() {
		return legacyModeratorSkillsProgrammeList;
	}

	public void setLegacyModeratorSkillsProgrammeList(
			List<LegacyModeratorSkillsProgramme> legacyModeratorSkillsProgrammeList) {
		this.legacyModeratorSkillsProgrammeList = legacyModeratorSkillsProgrammeList;
	}

	public List<LegacyModeratorUnitStandard> getLegacyModeratorUnitStandardList() {
		return legacyModeratorUnitStandardList;
	}

	public void setLegacyModeratorUnitStandardList(List<LegacyModeratorUnitStandard> legacyModeratorUnitStandardList) {
		this.legacyModeratorUnitStandardList = legacyModeratorUnitStandardList;
	}

	public List<LegacyAssessorAccreditation> getAssesorLegacyList() {
		return assesorLegacyList;
	}

	public void setAssesorLegacyList(List<LegacyAssessorAccreditation> assesorLegacyList) {
		this.assesorLegacyList = assesorLegacyList;
	}

	public List<LegacyModeratorAccreditation> getModeratorLegacyList() {
		return moderatorLegacyList;
	}

	public void setModeratorLegacyList(List<LegacyModeratorAccreditation> moderatorLegacyList) {
		this.moderatorLegacyList = moderatorLegacyList;
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

}
