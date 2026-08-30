package haj.com.ui;

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
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Doc;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.UserQualifications;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.UsersTrainingProvider;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.lookup.DesignatedTradeType;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationType;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.UsersTrainingProviderService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.validators.CheckID;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "learnerFinalApprovaFormUI")
@ViewScoped
public class LearnerFinalApprovaFormUI extends AbstractUI {
	
	/** The idpassport. */
	private IdPassportEnum idpassport = IdPassportEnum.Passport;
	
	/** The idnumber. */
	@CheckID(message="Not a valid RSA ID number")
	private String idnumber;
	
	/** The passport number. */
	private String passportNumber;

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

	/** The users service. */
	private UsersService usersService = new UsersService();
	private UsersLanguageService usersLanguageService=new UsersLanguageService();

	/** The company. */
	private Company company;

	/** The training provider. */
	private Company trainingProvider;

	/** The training provider. */
	private NonSetaCompany nonSetaCompany;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

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

	/** This is for the UsersQualification. */
	private QualificationType qualificationType;

	/** The users qualification. */
	private UserQualifications usersQualification;

	/** The qualification. */
	private Qualification qualification;

	/** The users qualification service. */
	private UserQualificationsService usersQualificationService = new UserQualificationsService();

	/** The users qualification list. */
	private List<UserQualifications> usersQualificationList;

	private boolean homeLanguageSelected = false;
	private boolean signoff = false;
	private boolean showInfo = false;
	private boolean show = false;
	private DesignatedTradeType designatedTradeType;
	/** The User Language */
	private UsersLanguage usersLanguage = new UsersLanguage();
	
	/**
	 * This is for UsersTraingProvider Details After the correct training company is
	 * selected.
	 */
	private UsersTrainingProvider usersTrainingProvider;

	private List<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();
	/** The User Language List */
	private ArrayList<UsersLanguage> usersLanguageList = new ArrayList<>();

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
	private CompanyLearnersTransfer companyLearnersTransfer;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	private boolean nonCreditBearing;
	private boolean showQual;

	private String regNumber;

	private ConfigDocProcessEnum configForRejectReasons;
	private boolean rejectReasonFound = false;
	
	public Date minDate;

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
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (super.getParameter("id", false) != null) {
			//configForRejectReasons = getSessionUI().getTask().getWorkflowProcess();
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.CompanyLearner) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				this.companylearners = companyLearnersService.findByKey(getSessionUI().getTask().getTargetKey(), ConfigDocProcessEnum.CompanyLearner);
				companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.CompanyLearner, companylearners);
				// companyLearnersService.prepareNewCompanyLearnersRegistration(getSessionUI().getTask().getWorkflowProcess(),
				// companylearners, getSessionUI().getTask().getProcessRole());
				if (companylearners.getAppointment() != null) {
					appointment = companylearners.getAppointment();
				}
				if (companylearners.getStatus() == ApprovalEnum.Rejected) {
					populateRejectReasons();
				}
				if(companylearners.getHostCompany() != null) {
					companylearners.setHostCompany(companyService.findByKey(companylearners.getHostCompany().getId()));
				}
				usersLanguageList=(ArrayList<UsersLanguage>) usersLanguageService.findByUser(companylearners.getUser());
				populateExistingLearner();
			} 
		} else {
			
		}
	}

	private void populateExistingLearner() throws Exception {

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
		if (this.company != null && this.company.getPostalAddress() != null && this.company.getPostalAddress().getId() != null) {
			this.company.setPostalAddress(AddressService.instance().findByKey(this.company.getPostalAddress().getId()));
		} else {
			this.company.setPostalAddress(new Address());
		}
		if (this.trainingProvider != null && this.trainingProvider.getPostalAddress() != null && this.trainingProvider.getPostalAddress().getId() != null) {
			this.trainingProvider.setPostalAddress(AddressService.instance().findByKey(this.trainingProvider.getPostalAddress().getId()));
		} else {
			this.trainingProvider.setPostalAddress(new Address());
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
			if (this.postalAddress.getSameAddress() != null) ;
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

	}


	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	// TODO Doc upload
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
			addErrorMessage(e.getMessage(), e);
		}
	}



	public List<RejectReasons> getRejectReasonsByProcess() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(configForRejectReasons);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}


	public void completeNewCompanyLearners() throws Exception {
		try {
			companyLearnersService.completeNewCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
			 getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveCompanyLearners() {
		try {
			companyLearnersService.approveCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), appointment);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			companyLearnersService.rejectCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, appointment);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			if (signoff) {
				companylearners.setSignoff(signoff);
				companyLearnersService.finalApproveCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
				ajaxRedirectToDashboard();
			} else {
				addErrorMessage("Please signof ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			companyLearnersService.finalRejectCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearners.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.CompanyLearner);
		} catch (Exception e) {

		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.CompanyLearner);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	
	public void loadUserLanguage()
	{
		try {
			if(user.getId() !=null)
			{
				usersLanguageList=(ArrayList<UsersLanguage>) usersLanguageService.findByUser(user);
				for(UsersLanguage ul:usersLanguageList)
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
	 * Gets the qualification type.
	 *
	 * @return the qualification type
	 */
	public QualificationType getQualificationType() {
		return qualificationType;
	}

	/**
	 * Sets the qualification type.
	 *
	 * @param qualificationType
	 *            the new qualification type
	 */
	public void setQualificationType(QualificationType qualificationType) {
		this.qualificationType = qualificationType;
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
	 * Gets the users qualification.
	 *
	 * @return the users qualification
	 */
	public UserQualifications getUsersQualification() {
		return usersQualification;
	}

	/**
	 * Sets the users qualification.
	 *
	 * @param usersQualification
	 *            the new users qualification
	 */
	public void setUsersQualification(UserQualifications usersQualification) {
		this.usersQualification = usersQualification;
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

	public CompanyLearnersTransfer getCompanyLearnersTransfer() {
		return companyLearnersTransfer;
	}

	public void setCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer) {
		this.companyLearnersTransfer = companyLearnersTransfer;
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

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
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
	/*
	 * public List<Qualification> completeQualification(String desc) {
	 * QualificationService qualificationService = new QualificationService();
	 * List<Qualification> l = null; try { //l =
	 * qualificationService.findByCompanyQualifications(this.trainingProvider,
	 * true); } catch (ValidationException e) {
	 * addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams())); } catch
	 * (Exception e) { addErrorMessage(e.getMessage(), e); } return l; }
	 */

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

	public boolean isRejectReasonFound() {
		return rejectReasonFound;
	}

	public void setRejectReasonFound(boolean rejectReasonFound) {
		this.rejectReasonFound = rejectReasonFound;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	public Address getLegalGaurdianResidentialAddress() {
		return legalGaurdianResidentialAddress;
	}

	public void setLegalGaurdianResidentialAddress(Address legalGaurdianResidentialAddress) {
		this.legalGaurdianResidentialAddress = legalGaurdianResidentialAddress;
	}

	public Address getLegalGaurdianPostalAddress() {
		return legalGaurdianPostalAddress;
	}

	public void setLegalGaurdianPostalAddress(Address legalGaurdianPostalAddress) {
		this.legalGaurdianPostalAddress = legalGaurdianPostalAddress;
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

	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
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

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
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
}
