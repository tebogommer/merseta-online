package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import haj.com.entity.Address;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.Municipality;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.UserLearnership;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserSkillsProgramme;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.AddressService;
import haj.com.service.AssessorModExtensionOfScopeService;
import haj.com.service.AssessorModReRegistrationService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.AssessorModeratorCompanyService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.MailDef;
import haj.com.service.RejectReasonMultipleSelectionService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.TasksService;
import haj.com.service.UserLearnershipService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserSkillsProgrammeService;
import haj.com.service.UserUnitStandardService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SICCodeService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "amDetailUI")
@ViewScoped
public class AmDetailUI extends AbstractUI {

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The company. */
	private Company company;

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The assessor moderator. */
	private Users assessorModerator;

	/** The assessor moderator application. */
	private AssessorModeratorApplication amApplication;

	/** The assessor moderator application Service. */
	private AssessorModeratorApplicationService amApplicationService = new AssessorModeratorApplicationService();

	/** The user qualifications. */
	private List<UserQualifications> userQualifications;
	
	/** The user qualification. */
	private UserQualifications userQualification;

	/** The user qualifications service. */
	private UserQualificationsService userQualificationsService = new UserQualificationsService();

	/** The user unit standards. */
	private List<UserUnitStandard> userUnitStandards;

	/** The user unit standard service. */
	private UserUnitStandardService userUnitStandardService = new UserUnitStandardService();

	/** The qualification. */
	private Qualification qualification;

	/** The unit standard. */
	private UnitStandards unitStandard;

	/** The assessor moderator company service. */
	private AssessorModeratorCompanyService assessorModeratorCompanyService = new AssessorModeratorCompanyService();

	/** The assessor moderator company. */
	private AssessorModeratorCompany assessorModeratorCompany;

	/** The assessor moderator Residential Address. */
	private Address AMResidentialAddress = new Address();

	/** The assessor moderator Postal Address. */
	private Address AMPostalAddress = new Address();

	/** The assessor moderator companies list. */
	private List<AssessorModeratorCompany> assessorModeratorCompaniesList;

	/** The chamber service. */
	private ChamberService chamberService = new ChamberService();

	/** The chambers. */
	private List<Chamber> chambers;

	/** The sic code service. */
	private SICCodeService sicCodeService = new SICCodeService();

	/** The sic codes. */
	private List<SICCode> sicCodes;

	/** The doc. */
	private Doc doc;

	/** The copy address. */
	private Boolean copyAddress;

	/** The copy assessor moderator address. */
	private Boolean copyAMAddress;

	/** The update company bool. */
    private Boolean updateCompanyBool;

	/** The display company info. */
	private Boolean displayCompanyInfo;

	/** The doc upload user. */
	private Boolean docUploadUser;

	/** The company problem. */
	private String companyProblem;

	/** The exceptions. */
	private String exceptions;

	/** The User Languages */
	private ArrayList<UsersLanguage> usersLanguageList = new ArrayList<>();

	/** The usersLanguageService */
	private UsersLanguageService usersLanguageService = new UsersLanguageService();

	/** The Reject Reasons Child Service */
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	
	/** The Reject Reasons Child Service */
	private ArrayList<RejectReasons> rejectReasonList;

	/** The Selected Reject Reasons */
	//private RejectReasonService rejectReasonsService = new RejectReasonsChildService();
	
	/** The Selected Reject Reasons */
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();

	/** The Additional Information */
	private String additionalInformation;
	
	/** The Additional Information */
	private String provideteAdditionalInfo;
	
	//private List<RejectReasonsChild> rejectReasonsChild;
	
	/**Boolean flag to allow user to update info*/
	private boolean enableEdit;
	
	/** The User Language */
	private UsersLanguage usersLanguage = new UsersLanguage();
	
	/**The UserUnitStandard*/
	private UserUnitStandard userUnitStandard=new UserUnitStandard();
	
	/** Boolean flag to check if home language is selected or not */
	private boolean homeLanguageSelected = false;
	
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	AssessorModExtensionOfScopeService assessorModExtensionOfScopeService=new AssessorModExtensionOfScopeService();
	
	private AssessorModExtensionOfScope assessorModExtensionOfScope;
	private AssessorModReRegistration assessorModReRegistration;
	private UserSkillsProgrammeService userSkillsProgrammeService=new UserSkillsProgrammeService();
	private UserLearnershipService userLearnershipService=new UserLearnershipService();
	private List<UserSkillsProgramme> userSkillsProgrammeList;
	private List<UserLearnership> userLearnershipList;


	/*
	 * Tab: Company Information, is rendered false due to the fact of adding
	 * multiple companies and not being able to access their physical addresses or
	 * postal information, only Employer/Provider information
	 */

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

	
	/**
	 * Check to enables user to edit information
	 * **/
	public void checkEnableEdit()
	{
		
		if(getSessionUI().getActiveUser().getId().equals(amApplication.getUser().getId()))
		{
			enableEdit=true;
			
		}
	}
	
	/**
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		if(getSessionUI().getTask() !=null)
		{
			if(getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.AM || getSessionUI().getTask().getWorkflowProcess()==ConfigDocProcessEnum.AM_ETQA_APPROVAL)
			{
				if (super.getParameter("id", false) != null) {
					amApplication = amApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
					this.assessorModerator = usersService.findByKey(amApplication.getUser().getId(), ConfigDocProcessEnum.AM, CompanyUserTypeEnum.User);
					resolveDocForAMApplication();
					usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(assessorModerator);
					assessorModeratorCompaniesList = assessorModeratorCompanyService.findByAssessorModeratorAndForAMApplication(assessorModerator.getId(), amApplication.getId());
					userQualifications = userQualificationsService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
					List<Company> companyList = companyUsersService.findByUser(assessorModerator);
					companyService.resolveDocsCompanyList(companyList);
					rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(amApplication.getId(), AssessorModeratorApplication.class.getName(), ConfigDocProcessEnum.AM);
					checkEnableEdit();
				}
				if (this.company != null && this.company.getId() != null) {
					displayCompanyInfo = true;
				} else {
					displayCompanyInfo = false;
				}
				//chambers = chamberService.allChamber();
				//sicCodes = sicCodeService.allSICCode();
				checkHomeLanguageSelect();
			}
			else if(getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE || getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE_ETQA_APPROVAL)
			{
			    assessorModExtensionOfScope=assessorModExtensionOfScopeService.findByKey(getSessionUI().getTask().getTargetKey());
				amApplication = amApplicationService.findByKey(assessorModExtensionOfScope.getAssessorModeratorApplication().getId());
				//this.assessorModerator = usersService.findByKey(amApplication.getUser().getId(), ConfigDocProcessEnum.AM, CompanyUserTypeEnum.User);
				//resolveDocForAMApplication();
				this.assessorModerator = usersService.findByKeyAndResolveDocTargetKeyAndClass(amApplication.getUser().getId(), ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE ,  CompanyUserTypeEnum.User, assessorModExtensionOfScope.getId(), assessorModExtensionOfScope.getClass().getName());
				usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(assessorModerator);
				rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(assessorModExtensionOfScope.getId(), AssessorModExtensionOfScope.class.getName(), ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE);
				userQualifications = userQualificationsService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
				userUnitStandards = userUnitStandardService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
				checkEnableEdit();
			}
			else if(getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.AM_RE_REGISTRATION || getSessionUI().getTask().getWorkflowProcess()==ConfigDocProcessEnum.AM_RE_REGISTRATION_ETQA_APPROVAL)
			{
				if (super.getParameter("id", false) != null) {
					AssessorModReRegistrationService assessorModReRegistrationService=new AssessorModReRegistrationService();
					assessorModReRegistration=assessorModReRegistrationService.findByKey(getSessionUI().getTask().getTargetKey());
					amApplication = amApplicationService.findByKey(assessorModReRegistration.getAssessorModeratorApplication().getId());
					amApplication.setAssessorModReRegistration(assessorModReRegistration);
					this.assessorModerator = usersService.findByKey(amApplication.getUser().getId(), ConfigDocProcessEnum.AM_RE_REGISTRATION, CompanyUserTypeEnum.User);
					resolveDocForAMReRegApplication();
					usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(assessorModerator);
					userQualifications = userQualificationsService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
					userSkillsProgrammeList = userSkillsProgrammeService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					userLearnershipList = userLearnershipService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(assessorModReRegistration.getId(), AssessorModReRegistration.class.getName(), ConfigDocProcessEnum.AM_RE_REGISTRATION);
					checkEnableEdit();
				}
				if (this.company != null && this.company.getId() != null) {
					displayCompanyInfo = true;
				} else {
					displayCompanyInfo = false;
				}
				//chambers = chamberService.allChamber();
				//sicCodes = sicCodeService.allSICCode();
				checkHomeLanguageSelect();
			}
			else if(getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.LEGACY_AM_APPLICATION )
			{
				if (super.getParameter("id", false) != null) {
					amApplication = amApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
					this.assessorModerator = usersService.findByKey(amApplication.getUser().getId(), ConfigDocProcessEnum.LEGACY_AM_APPLICATION, CompanyUserTypeEnum.User);
					resolveDocForAMApplication();
					usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(assessorModerator);
					userQualifications = userQualificationsService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
					userSkillsProgrammeList = userSkillsProgrammeService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					userLearnershipList = userLearnershipService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
					List<Company> companyList = companyUsersService.findByUser(assessorModerator);
					companyService.resolveDocsCompanyList(companyList);
					rejectReasonList=(ArrayList<RejectReasons>) rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(amApplication.getId(), AssessorModeratorApplication.class.getName(), ConfigDocProcessEnum.LEGACY_AM_APPLICATION);
					checkEnableEdit();
				}
				if (this.company != null && this.company.getId() != null) {
					displayCompanyInfo = true;
				} else {
					displayCompanyInfo = false;
				}
				checkHomeLanguageSelect();
			}
			if(rejectReasonList !=null && rejectReasonList.size()>0)
			{
				if(rejectReasonList.get(0).getAdditionalInformation() !=null)
				{
					provideteAdditionalInfo=rejectReasonList.get(0).getAdditionalInformation();
				}
				
			}
		}
		
	}
	
	public String getExtensionOfScopeTitle()
	{
		String title="Moderator Extension Of Scope";
		if(amApplication.getApplicationType()==AssessorModeratorAppTypeEnum.NewAssessorRegistration || 
		amApplication.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorExtensionOfScope   ||
		amApplication.getApplicationType()==AssessorModeratorAppTypeEnum.AssessorReRegistration     ){
			 title="Assessor Extension Of Scope";	
		}
		return title;
	}
	
	public void reparETQARejectReason()
	{
		selectedRejectReason=rejectReasonList;
	}
	
	@SuppressWarnings("unchecked")
	public void approveExtensionOfScopeTask() {
		try {
			if(reviewCommitteeMeeting!=null){assessorModExtensionOfScope.setReviewCommitteeMeeting(reviewCommitteeMeeting);}
			amApplicationService.update((List<IDataEntity>) (List<?>) userQualifications);
			amApplicationService.update((List<IDataEntity>) (List<?>) userUnitStandards);
			amApplicationService.update((List<IDataEntity>) (List<?>) userSkillsProgrammeList);
			amApplicationService.update((List<IDataEntity>) (List<?>) userLearnershipList);
			amApplicationService.approveExtensionOfScopeTask(assessorModExtensionOfScope,amApplication,getSessionUI().getTask(),assessorModerator);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void completeExtensionOfScopeTask() {
		try {
			
			amApplicationService.approveExtensionOfScopeTask(assessorModExtensionOfScope,amApplication,getSessionUI().getTask(),assessorModerator);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void extensionOfScopefinalApprove()
	{
		try {
			amApplicationService.approveExtensionOfScope(assessorModExtensionOfScope,userQualifications,userUnitStandards,amApplication,getSessionUI().getTask(),getSessionUI().getActiveUser());
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
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
			if(doc.getTargetKey()!=null && doc.getTargetKey().equals(amApplication.getId()) && doc.getTargetClass().equals(amApplication.getClass().getName()))
			{
				docsForCurrentApplication.add(doc);
			}
			
		}
		assessorModerator.setDocs(docsForCurrentApplication);
	}
	
	/**
	 * Removes user documents that are 
	 * not related to the current Application
	 * */
	public void resolveDocForAMReRegApplication()
	{
		ArrayList<Doc> docsForCurrentApplication=new ArrayList<>();
		for(Doc doc:assessorModerator.getDocs())
		{
			if(doc.getTargetKey()!=null && doc.getTargetKey().equals(assessorModReRegistration.getId()) && doc.getTargetClass().equals(assessorModReRegistration.getClass().getName()))
			{
				docsForCurrentApplication.add(doc);
			}
			
		}
		assessorModerator.setDocs(docsForCurrentApplication);
	}

	
	public void saveUserInfo()
	{
		try {
			usersService.create(assessorModerator);
			Address residentialAddreass=assessorModerator.getResidentialAddress();
			Address postalAddress=assessorModerator.getPostalAddress();
			if(residentialAddreass.getSameAddress() != null && residentialAddreass.getSameAddress() )
			{
				AddressService.instance().copyFromToAddress(residentialAddreass,postalAddress);
			}
			AddressService.instance().update(residentialAddreass);
			AddressService.instance().update(postalAddress);
			amApplicationService.create(amApplication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		
		
	}
	
	public void redirectToSchedule() {
		
		super.ajaxRedirect("/pages/etqaReviewCommittee/reviewCommitteeDateScheduling.jsf");
	}
	
	
	public void resubmitAMApplication()
	{
		try {
			amApplicationService.resubmitAMApplication(assessorModerator, amApplication, getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("your.regsitration.is.being.processed"));
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Gets the copy address.
	 *
	 * @return the copy address
	 */
	public Boolean getCopyAddress() {
		return copyAddress;
	}

	/**
	 * Sets the copy address.
	 *
	 * @param copyAddress
	 *            the new copy address
	 */
	public void setCopyAddress(Boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	/**
	 * Resolve addresses.
	 */
	public void resolveAddresses() {
		if (this.company.getPostalAddress() != null && this.company.getPostalAddress().getSameAddress() != null) {
			this.copyAddress = this.company.getPostalAddress().getSameAddress();
		} else {
			this.copyAddress = false;
		}
	}

	/**
	 * Cpy addresses.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void cpyAddresses() throws Exception {
		if (copyAddress) {
			AddressService.instance().copyFromToAddress(company.getResidentialAddress(), company.getPostalAddress());
		}
		company.getPostalAddress().setSameAddress(copyAddress);
	}

	/**
	 * Complete municipality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Municipality> completeMunicipality(String desc) {
		List<Municipality> l = null;
		try {
			l = AddressService.instance().findMunicipalitiesAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(company.getPostalAddress());
		}
	}

	/**
	 * Update company.
	 */
	public void updateCompany() {
		try {
			cpyAddresses();
			companyService.save(company);
			this.updateCompanyBool = false;
			assessorModeratorCompaniesList = assessorModeratorCompanyService.findByAssessorModerator(assessorModerator);
			List<Company> companyList = companyUsersService.findByUser(assessorModerator);
			companyService.resolveDocsCompanyList(companyList);
			addInfoMessage(super.getEntryLanguage("submit.complete"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Cancel company update.
	 */
	public void cancelCompanyUpdate() {
		try {
			this.updateCompanyBool = false;
			super.runClientSideUpdate("companyInsForm");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prep for update.
	 */
	public void prepForUpdate11() {
		try {
			company = companyService.findByKey(assessorModeratorCompany.getCompany().getId());

			if (company.getResidentialAddress() == null) {
				this.company.setResidentialAddress(new Address(company));
			}
			if (company.getPostalAddress() == null) {
				this.company.setPostalAddress(new Address(company));
			}
			this.updateCompanyBool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Complete task.
	 */
	public void completeTask() {
		try {
			this.exceptions = null;
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				assessorModeratorCompanyService.doChecksUserCompany(null, assessorModeratorCompaniesList, false, true);
			}
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
				assessorModeratorCompanyService.doChecksUserCompany(assessorModerator, assessorModeratorCompaniesList, true, true);
			}
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload) {
				assessorModeratorCompanyService.doChecksUserCompany(assessorModerator, assessorModeratorCompaniesList, true, false);
			}
		} catch (Exception e) {
			this.exceptions = e.getMessage();
		}
		if (exceptions == null || exceptions.isEmpty()) {
			try {
				if (getSessionUI().getTask().getProcessRole() == null) {
					companyService.completeToFirst(getSessionUI().getActiveUser(), getSessionUI().getTask(), amApplication);
				} else {
					companyService.completeTask("New Assessor and Moderator for approval", amApplication.getId(), AssessorModeratorApplication.class.getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.AMForApproval, MailTagsEnum.FirstName, assessorModerator.getFirstName(), MailTagsEnum.LastName, assessorModerator.getLastName()), null);
				}
				getSessionUI().setTask(null);
				super.ajaxRedirectToDashboard();
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
		}
	}

	/**
	 * approve task.
	 */
	public void approveTask() {
		try {
			companyService.approveAMTask(assessorModerator, getSessionUI().getTask(), getSessionUI().getActiveUser(), amApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Senior manager final approve task.
	 */
	@SuppressWarnings("unchecked")
	public void seniorManagerFinalapproveTask() {
		

		try {
			if(reviewCommitteeMeeting !=null){
				amApplication.setReviewCommitteeMeeting(reviewCommitteeMeeting);
			}
			amApplicationService.update((List<IDataEntity>) (List<?>) userQualifications);
			amApplicationService.update((List<IDataEntity>) (List<?>) userUnitStandards);
			amApplicationService.update((List<IDataEntity>) (List<?>) userSkillsProgrammeList);
			amApplicationService.update((List<IDataEntity>) (List<?>) userLearnershipList);
			amApplicationService.seniorManagerFinalapproveTask(assessorModerator, getSessionUI().getTask(), getSessionUI().getActiveUser(), assessorModeratorCompaniesList, amApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void checkIfQualificationIsApprove() 
	{
		try {
			boolean approve=false;
			if(userQualifications !=null && userQualifications.size()>0){
				for(UserQualifications qual:userQualifications){
					if(qual.getAccept()!=null && qual.getAccept()==true){
						approve=true;
					}
				}
			}
			
			if(!approve){
				if(userUnitStandards !=null && userUnitStandards.size()>0){
					for(UserUnitStandard qual:userUnitStandards){
						if(qual.getAccept()!=null && qual.getAccept()==true){
							approve=true;
						}
					}
				}
			}
			if(!approve){
				if(userSkillsProgrammeList !=null && userSkillsProgrammeList.size()>0){
					for(UserSkillsProgramme qual:userSkillsProgrammeList){
						if(qual.getAccept()!=null && qual.getAccept()==true){
							approve=true;
						}
					}
				}
				
			}
			if(!approve){
				if(userLearnershipList !=null && userLearnershipList.size()>0){
					for(UserLearnership qual:userLearnershipList){
						if(qual.getAccept()!=null && qual.getAccept()==true){
							approve=true;
						}
					}
				}
				
			}
			if(!approve){
				throw new Exception("Please approve at least one qualificaiton, unit standard, Learnership or skills programme");
			}
			else{
				super.runClientSideExecute("PF('dateSchedule').show()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public boolean checkIfLegacyQualificationIsApprove() throws Exception  {
		boolean approve=false;
		if(userQualifications !=null && userQualifications.size()>0){
			for(UserQualifications qual:userQualifications){
				if(qual.getAccept()!=null && qual.getAccept()==true){
					approve=true;
				}
			}
		}
		
		if(!approve){
			if(userUnitStandards !=null && userUnitStandards.size()>0){
				for(UserUnitStandard qual:userUnitStandards){
					if(qual.getAccept()!=null && qual.getAccept()==true){
						approve=true;
					}
				}
			}
		}
		if(!approve){
			if(userSkillsProgrammeList !=null && userSkillsProgrammeList.size()>0){
				for(UserSkillsProgramme qual:userSkillsProgrammeList){
					if(qual.getAccept()!=null && qual.getAccept()==true){
						approve=true;
					}
				}
			}
			
		}
		if(!approve){
			if(userLearnershipList !=null && userLearnershipList.size()>0){
				for(UserLearnership qual:userLearnershipList){
					if(qual.getAccept()!=null && qual.getAccept()==true){
						approve=true;
					}
				}
			}
			
		}
		return approve;
	}
	/**
	 * approve task.
	 */
	@SuppressWarnings("unchecked")
	public void finalApproveTask()
	{
		
		try {
			if(!checkIfLegacyQualificationIsApprove()){
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please approve at least one qualificaiton, unit standard, Learnership or skills programme");				
			}
			if(getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LEGACY_AM_APPLICATION){
				amApplicationService.update((List<IDataEntity>) (List<?>) userQualifications);
				amApplicationService.update((List<IDataEntity>) (List<?>) userUnitStandards);
				amApplicationService.update((List<IDataEntity>) (List<?>) userSkillsProgrammeList);
				amApplicationService.update((List<IDataEntity>) (List<?>) userLearnershipList);
			}
			amApplicationService.finalApproveTask( amApplication, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public ArrayList<AssessorModeratorCompany> loadUserCompany(AssessorModeratorApplication am)
	{
		
		ArrayList<AssessorModeratorCompany> list=new  ArrayList<AssessorModeratorCompany>();
		try {
			
			list=(ArrayList<AssessorModeratorCompany>)  assessorModeratorCompanyService.findByAssessorModeratorAndForAMApplication(am.getUser().getId(), am.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * Reject task.
	 */
	public void rejectAMTask() {

		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			
			amApplicationService.rejectAMTask(assessorModerator, getSessionUI().getTask(),  getSessionUI().getActiveUser(), amApplication, selectedRejectReason, additionalInformation);
			
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Reject task.
	 */
	public void rejectExtentionOfScope() {

		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			amApplicationService.rejectExtentionOfScope(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE, assessorModExtensionOfScope, getSessionUI().getActiveUser(), selectedRejectReason, getSessionUI().getTask());
			
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Reject task.
	 */
	public void finalRrejectExtentionOfScope() {

		try {
			
			if(assessorModExtensionOfScope.getStatus()==ApprovalEnum.RejectedByEQTA){selectedRejectReason=rejectReasonList;}
			
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			amApplicationService.finalRrejectExtentionOfScope(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE, assessorModExtensionOfScope, getSessionUI().getActiveUser(), selectedRejectReason, getSessionUI().getTask());
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Reject task.
	 * If not approved by ETQA Review Committee, application
	 * Senior Manager to reject application and must go back
	 * to applicant to address issues identified
	 */
	public void finalRejecTask() {
		
		try {
			//storeClientInfo();
			/*if(rejectReasonList !=null && rejectReasonList.size()>0){
				selectedRejectReason=rejectReasonList;
			}
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}*/
			
			amApplicationService.finalRejecTask(assessorModerator, getSessionUI().getTask(), getSessionUI().getActiveUser(), assessorModeratorCompaniesList, amApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			e.printStackTrace();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	
	public void finalRejecApplication() {
		
		try {
			if(rejectReasonList !=null && rejectReasonList.size()>0){
				selectedRejectReason=rejectReasonList;
			}
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			
			amApplicationService.finalRejecApplication(assessorModerator, getSessionUI().getTask(), getSessionUI().getActiveUser(), assessorModeratorCompaniesList, amApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			e.printStackTrace();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
		
	}

	/**
	 * reject task.
	 */
	public void rejectTask() {
		try {
			companyService.rejectTask("Assessor Moderator Task was rejected please review", assessorModerator.getId(), assessorModerator.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.AMRejectedReview, MailTagsEnum.FirstName, assessorModerator.getFirstName(), MailTagsEnum.LastName, assessorModerator.getLastName()));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Adds Qualification to user.
	 */
	public void addQualification() {
		try {
			for(UserQualifications us: userQualifications)
			{
				if(us.getQualification().equals(qualification))
				{
					throw new Exception("Qualification already exist in the list");
				}
			}
			amApplicationService.validateQualificationAccreditatation(qualification, amApplication.getUser(), amApplication.getApplicationType());
			if (qualification != null) {
				UserQualifications cq = new UserQualifications(assessorModerator, qualification);
				cq.setForAssessorModeratorApplication(amApplication);
				userQualificationsService.create(cq);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.qualification = null;
				userQualifications = userQualificationsService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
			} else {
				addWarningMessage(super.getEntryLanguage("select.qulaification.before.adding"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds Qualification to user.
	 */
	public void addExtentionOfScopeQualification() {
		try {
			for(UserQualifications us: userQualifications)
			{
				if(us.getQualification().equals(qualification))
				{
					throw new Exception("Qualification already exist in the list");
				}
			}
			amApplicationService.validateQualificationAccreditatation(qualification, amApplication.getUser(), amApplication.getApplicationType());
			if (qualification != null) {
				UserQualifications userQualification = new UserQualifications(assessorModExtensionOfScope.getCreateUser(), qualification);
				userQualification.setTargetClass(AssessorModExtensionOfScope.class.getName());
				userQualification.setTargetKey(assessorModExtensionOfScope.getId());
				userQualification.setQualification(qualification);
				userQualificationsService.create(userQualification);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.qualification = null;
				userQualifications = userQualificationsService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
			} else {
				addWarningMessage(super.getEntryLanguage("select.qulaification.before.adding"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeQualification()
	{
		try {
			
			userQualificationsService.delete(userQualification);
			userQualifications = userQualificationsService.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
		
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void acceptCodeOfConduct() {

		try {
			amApplication.setCodeOfConductAcceptDate(new Date());
			if(amApplication.getId() !=null)
			{
				AssessorModeratorApplicationService amAppService = new AssessorModeratorApplicationService();
				amAppService.update(amApplication);
				amApplication = amAppService.findByKey(amApplication.getId());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Adds Unit Standards to user.
	 */
	public void addUnitStandard() {
		try {
			
			for(UserUnitStandard uus :userUnitStandards)
			{
				if(uus.getUnitStandard().equals(unitStandard))
				{
					throw new Exception("Unit Standard already exist in the list");
				}
			}
			amApplicationService.validateUniStandardAccreditatation(unitStandard, amApplication.getUser(), amApplication.getApplicationType());
			if (unitStandard != null) {
				UserUnitStandard cu = new UserUnitStandard(assessorModerator, unitStandard);
				cu.setForAssessorModeratorApplication(amApplication);
				userUnitStandardService.createAUserUnitStandard(cu);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.unitStandard = null;
				userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
			} else {
				addWarningMessage(super.getEntryLanguage("select.unitStandard.before.adding"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}

	}
	
	/**
	 * Adds Unit Standards to user.
	 */
	public void addExtensionOfScopeUnitStandard() {
		try {
			
			for(UserUnitStandard uus :userUnitStandards)
			{
				if(uus.getUnitStandard().equals(unitStandard))
				{
					throw new Exception("Unit Standard already exist in the list");
				}
			}
			amApplicationService.validateUniStandardAccreditatation(unitStandard, amApplication.getUser(), amApplication.getApplicationType());
			if (unitStandard != null) {
				UserUnitStandard userUnitStandard = new UserUnitStandard();
				userUnitStandard.setTargetClass(AssessorModExtensionOfScope.class.getName());
				userUnitStandard.setTargetKey(assessorModExtensionOfScope.getId());
				userUnitStandard.setUnitStandard(unitStandard);
				userUnitStandardService.createAUserUnitStandard(userUnitStandard);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.unitStandard = null;
				userUnitStandards = userUnitStandardService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
			} else {
				addWarningMessage(super.getEntryLanguage("select.unitStandard.before.adding"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}

	}
	
	public void removeUserUnitStandard()
	{
		try {
			
			userUnitStandardService.delete(userUnitStandard);
			userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}

	/**
	 * Prep upload close company information.
	 */
	public void prepUploadCloseCompanyInformation() {
		if (updateCompanyBool != null && updateCompanyBool == true) {
			this.updateCompanyBool = false;
			runClientSideUpdate("companyInsForm");
		}
		docUploadUser = false;
	}

	/**
	 * Prep upload close company information for user.
	 */
	public void prepUploadCloseCompanyInformationForUser() {
		if (updateCompanyBool != null && updateCompanyBool == true) {
			this.updateCompanyBool = false;
			runClientSideUpdate("companyInsForm");
		}
		docUploadUser = true;
	}

	/**
	 * Saves the document Uploaded.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				// new document
				if (!docUploadUser) {
					// if document is for company
					doc.setCompany(assessorModeratorCompany.getCompany());
					companyService.documentUpload(doc.getCompany(), getSessionUI().getActiveUser());
				} else {
					// if document is for user
					doc.setCompany(null);
					doc.setForUsers(assessorModerator);
					usersService.documentUpload(assessorModerator, getSessionUI().getActiveUser());
				}
			} else {
				if (!docUploadUser) {
					// if document is for company
					companyService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				} else {
					// if document is for user
					usersService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				}
			}
			addInfoMessage(super.getEntryLanguage("upload.successful"));
			this.updateCompanyBool = true;
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	public void clearAMPostal() {
		if (!copyAMAddress) {
			AddressService.instance().clearAddress(AMPostalAddress);
		}
	}

	/**
	 * Final Approve
	 */
	public void finalApproveRegistration() {
		try {
			amApplicationService.finalApproveAM(amApplication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful."));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * reject
	 */
	public void rejectAMRegistration() {
		try {
			amApplicationService.rejectAMRegistration(amApplication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	

	/**
	 * Final reject 4. If Manager: Quality Assurance does not approve, notification
	 * to be sent to Applicant of unsuccessful application
	 */
	public void finalRejectAMregistration_Old() {
		try {
			
			amApplicationService.finalRejectAmRegistration(amApplication, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason,additionalInformation);
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeAMRegistration() {
		try {
			if (getSessionUI().getTask().getProcessRole() == null) {
				amApplicationService.completeToFirst(amApplication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			} else {
				amApplicationService.completeToFirst(amApplication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onTabChange(TabChangeEvent event) {
		try {
			compnayInfoRendererFalse();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private Boolean compnayInfoRendererFalse() {
		this.updateCompanyBool = false;
		return updateCompanyBool;
	}
	
	public void approveQualificationSkillProg(UserQualifications uq)
	{
		try {
			if(userSkillsProgrammeList !=null && userSkillsProgrammeList.size()>0)
			{
				for(UserSkillsProgramme sp: userSkillsProgrammeList)
				{
					if(sp.getSkillsProgram() !=null && sp.getSkillsProgram().getQualification() !=null && uq.getQualification() !=null)
					{
						if(sp.getSkillsProgram().getQualification().equals(uq.getQualification()))
						{
							sp.setAccept(uq.getAccept());
						}
					}
				}
			}
			
			if(userUnitStandards !=null && userUnitStandards.size()>0)
			{
				for(UserUnitStandard us: userUnitStandards)
				{
					if(us.getForQualification()!=null)
					{
						if(us.getForQualification().equals(uq.getQualification()))
						{
							us.setAccept(uq.getAccept());
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
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
	 * Gets the chambers.
	 *
	 * @return the chambers
	 */
	public List<Chamber> getChambers() {
		return chambers;
	}

	/**
	 * Sets the chambers.
	 *
	 * @param chambers
	 *            the new chambers
	 */
	public void setChambers(List<Chamber> chambers) {
		this.chambers = chambers;
	}

	/**
	 * Gets the update company bool.
	 *
	 * @return the update company bool
	 */
	public Boolean getUpdateCompanyBool() {
		return updateCompanyBool;
	}

	/**
	 * Sets the update company bool.
	 *
	 * @param updateCompanyBool
	 *            the new update company bool
	 */
	public void setUpdateCompanyBool(Boolean updateCompanyBool) {
		this.updateCompanyBool = updateCompanyBool;
	}

	/**
	 * Gets the assessor moderator.
	 *
	 * @return the assessor moderator
	 */
	public Users getAssessorModerator() {
		return assessorModerator;
	}

	/**
	 * Sets the assessor moderator.
	 *
	 * @param assessorModerator
	 *            the new assessor moderator
	 */
	public void setAssessorModerator(Users assessorModerator) {
		this.assessorModerator = assessorModerator;
	}
	
	public void prepareLanguageUpdate() {
		usersLanguageList.remove(usersLanguage);
		if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
			homeLanguageSelected = false;
		}
	}
	
	public void removeLanguageFromList() {
		usersLanguageList.remove(usersLanguage);
		//Deleting the language from table
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
		if(usersLanguageList.size()==0) {
			homeLanguageSelected = false;
		}
		usersLanguage = new UsersLanguage();
	}
	
	public void addLanguage() {
		try {

			languagePreCheck();
			//usersLanguageList.add(usersLanguage);
			/*if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
				homeLanguageSelected = true;
			}*/
			usersLanguage.setUser(assessorModerator);
			usersLanguageService.create(usersLanguage);
			usersLanguage = new UsersLanguage();
			usersLanguageList = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(assessorModerator);

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	public void checkHomeLanguageSelect()
	{
		for(UsersLanguage ul:usersLanguageList)
		{
			if (ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
				homeLanguageSelected = true;
				break;
			}
			
		}
	}

	public void languagePreCheck() throws Exception {
		// Checking if language is not added already
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

	/**
	 * Gets the user qualifications.
	 *
	 * @return the user qualifications
	 */
	public List<UserQualifications> getUserQualifications() {
		return userQualifications;
	}

	/**
	 * Sets the user qualifications.
	 *
	 * @param userQualifications
	 *            the new user qualifications
	 */
	public void setUserQualifications(List<UserQualifications> userQualifications) {
		this.userQualifications = userQualifications;
	}

	/**
	 * Gets the user unit standards.
	 *
	 * @return the user unit standards
	 */
	public List<UserUnitStandard> getUserUnitStandards() {
		return userUnitStandards;
	}

	/**
	 * Sets the user unit standards.
	 *
	 * @param userUnitStandards
	 *            the new user unit standards
	 */
	public void setUserUnitStandards(List<UserUnitStandard> userUnitStandards) {
		this.userUnitStandards = userUnitStandards;
	}

	/**
	 * Gets the display company info.
	 *
	 * @return the display company info
	 */
	public Boolean getDisplayCompanyInfo() {
		return displayCompanyInfo;
	}

	/**
	 * Sets the display company info.
	 *
	 * @param displayCompanyInfo
	 *            the new display company info
	 */
	public void setDisplayCompanyInfo(Boolean displayCompanyInfo) {
		this.displayCompanyInfo = displayCompanyInfo;
	}

	/**
	 * Gets the sic codes.
	 *
	 * @return the sic codes
	 */
	public List<SICCode> getSicCodes() {
		return sicCodes;
	}

	/**
	 * Sets the sic codes.
	 *
	 * @param sicCodes
	 *            the new sic codes
	 */
	public void setSicCodes(List<SICCode> sicCodes) {
		this.sicCodes = sicCodes;
	}

	/**
	 * Gets the assessor moderator companies list.
	 *
	 * @return the assessor moderator companies list
	 */
	public List<AssessorModeratorCompany> getAssessorModeratorCompaniesList() {
		return assessorModeratorCompaniesList;
	}

	/**
	 * Sets the assessor moderator companies list.
	 *
	 * @param assessorModeratorCompaniesList
	 *            the new assessor moderator companies list
	 */
	public void setAssessorModeratorCompaniesList(List<AssessorModeratorCompany> assessorModeratorCompaniesList) {
		this.assessorModeratorCompaniesList = assessorModeratorCompaniesList;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.AM);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
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
	 * Gets the assessor moderator company.
	 *
	 * @return the assessor moderator company
	 */
	public AssessorModeratorCompany getAssessorModeratorCompany() {
		return assessorModeratorCompany;
	}

	/**
	 * Sets the assessor moderator company.
	 *
	 * @param assessorModeratorCompany
	 *            the new assessor moderator company
	 */
	public void setAssessorModeratorCompany(AssessorModeratorCompany assessorModeratorCompany) {
		this.assessorModeratorCompany = assessorModeratorCompany;
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
	 * Gets the company problem.
	 *
	 * @return the company problem
	 */
	public String getCompanyProblem() {
		return companyProblem;
	}

	/**
	 * Sets the company problem.
	 *
	 * @param companyProblem
	 *            the new company problem
	 */
	public void setCompanyProblem(String companyProblem) {
		this.companyProblem = companyProblem;
	}

	/**
	 * Gets the exceptions.
	 *
	 * @return the exceptions
	 */
	public String getExceptions() {
		return exceptions;
	}

	/**
	 * Sets the exceptions.
	 *
	 * @param exceptions
	 *            the new exceptions
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	public Address getAMResidentialAddress() {
		return AMResidentialAddress;
	}

	public void setAMResidentialAddress(Address aMResidentialAddress) {
		AMResidentialAddress = aMResidentialAddress;
	}

	public Address getAMPostalAddress() {
		return AMPostalAddress;
	}

	public void setAMPostalAddress(Address aMPostalAddress) {
		AMPostalAddress = aMPostalAddress;
	}

	public Boolean getCopyAMAddress() {
		return copyAMAddress;
	}

	public void setCopyAMAddress(Boolean copyAMAddress) {
		this.copyAMAddress = copyAMAddress;
	}

	public AssessorModeratorApplication getAmApplication() {
		return amApplication;
	}

	public void setAmApplication(AssessorModeratorApplication amApplication) {
		this.amApplication = amApplication;
	}

	public ArrayList<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public void setUsersLanguageList(ArrayList<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/*public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}*/

	public boolean isEnableEdit() {
		return enableEdit;
	}

	public void setEnableEdit(boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}


	public UserQualifications getUserQualification() {
		return userQualification;
	}


	public void setUserQualification(UserQualifications userQualification) {
		this.userQualification = userQualification;
	}


	public UserUnitStandard getUserUnitStandard() {
		return userUnitStandard;
	}


	public void setUserUnitStandard(UserUnitStandard userUnitStandard) {
		this.userUnitStandard = userUnitStandard;
	}


	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}


	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}


	public ArrayList<RejectReasons> getRejectReasonList() {
		return rejectReasonList;
	}


	public void setRejectReasonList(ArrayList<RejectReasons> rejectReasonList) {
		this.rejectReasonList = rejectReasonList;
	}


	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}


	/**
	 * @param reviewCommitteeMeeting the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}


	public AssessorModExtensionOfScope getAssessorModExtensionOfScope() {
		return assessorModExtensionOfScope;
	}


	public void setAssessorModExtensionOfScope(AssessorModExtensionOfScope assessorModExtensionOfScope) {
		this.assessorModExtensionOfScope = assessorModExtensionOfScope;
	}


	public String getProvideteAdditionalInfo() {
		return provideteAdditionalInfo;
	}


	public void setProvideteAdditionalInfo(String provideteAdditionalInfo) {
		this.provideteAdditionalInfo = provideteAdditionalInfo;
	}


	public AssessorModReRegistration getAssessorModReRegistration() {
		return assessorModReRegistration;
	}


	public void setAssessorModReRegistration(AssessorModReRegistration assessorModReRegistration) {
		this.assessorModReRegistration = assessorModReRegistration;
	}


	public List<UserSkillsProgramme> getUserSkillsProgrammeList() {
		return userSkillsProgrammeList;
	}


	public void setUserSkillsProgrammeList(List<UserSkillsProgramme> userSkillsProgrammeList) {
		this.userSkillsProgrammeList = userSkillsProgrammeList;
	}


	public List<UserLearnership> getUserLearnershipList() {
		return userLearnershipList;
	}


	public void setUserLearnershipList(List<UserLearnership> userLearnershipList) {
		this.userLearnershipList = userLearnershipList;
	}


	

}
