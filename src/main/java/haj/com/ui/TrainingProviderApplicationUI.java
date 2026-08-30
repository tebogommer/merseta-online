package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.SDPCompany;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.SdpCompanyActionListEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SdpType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.AssessorModeratorCompanySitesService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.ReviewCommitteeMeetingService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.SdpCompanyActionsService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.TrainingSiteService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.AddressValidationService;

@ManagedBean(name = "trainingproviderapplicationUI")
@ViewScoped
public class TrainingProviderApplicationUI extends AbstractUI {

	private TrainingProviderApplicationService service = new TrainingProviderApplicationService();
	private SDFCompanyService sdfcompanyservice = new SDFCompanyService();
	private LegacyProviderAccreditationService legacyProviderAccreditationService = new LegacyProviderAccreditationService();
	private List<TrainingProviderApplication> trainingproviderapplicationList = null;
	private List<TrainingProviderApplication> trainingproviderapplicationfilteredList = null;
	private TrainingProviderApplication trainingproviderapplication = null;
	private LazyDataModel<TrainingProviderApplication> dataModel;
	private LazyDataModel<LegacyProviderAccreditation> legacyDataModel;
	private LazyDataModel<TrainingProviderApplication> pendingAppDataModel;
	private Users trainingProviderUser;

	private SdpType sdpType = null;
	/** The users service. */
	private UsersService usersService = new UsersService();
	
	private AddressValidationService addressValidationService=new AddressValidationService();
	
	/** The company unit standard service. */
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();

	/** The company qualifications service. */
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();

	/** The Reject Reasons */
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();

	private TrainingProviderApplication selectedTrainingProviderApplication = new TrainingProviderApplication();
	/** The company qualifications. */
	private List<CompanyQualifications> companyQualifications;

	/** The unit standards. */
	private List<CompanyUnitStandard> unitStandards;

	/** The Review Committee date */
	private ReviewCommitteeMeeting meetingSchedule = new ReviewCommitteeMeeting();

	/** The ReviewCommitteeMeetingService */
	private ReviewCommitteeMeetingService reviewCommitteeMeetingService = new ReviewCommitteeMeetingService();

	/** The Doc Seversce */
	private DocService docService = new DocService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	private List<TrainingProviderSkillsProgramme> tpSkillsProgramList;

	private List<TrainingProviderSkillsSet> tpSkillsSetList;

	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();

	private TrainingProviderSkillsSetService trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();

	/** The Review Committee Meeting */
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	/** The Review Committee Meeting Service. */
	private ReviewCommitteeMeetingService rcmService = new ReviewCommitteeMeetingService();

	List<Doc> docList;

	private LazyDataModel<Doc> docHistDataModel;

	/** Training Provider Contact Persons */
	private List<CompanyUsers> contactPersonList;

	/** Training Provider Assessor/Facilitator */
	private List<CompanyUsers> facilitatorAssessorList;

	private List<TrainingProviderApplication> companyTPApplicationList;

	private Doc doc;

	private Doc docChange;

	public static ChangeReason changeReason = new ChangeReason();
	private boolean copyAddress;

	private Boolean editCompanyDetails;

	private String redirectTo;

	private Users user;

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	private CompanyUsers selectedCompanyUser;

	private Boolean assessorModUpdate;

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	private Boolean canActionInformation = false;
	private Boolean newStructureSearch = false;
	private Boolean sdpUserSearch = false;
	private String setmisValidiationErrors = "";
	private String trainingSiteSetmisValidiations = "";
	
	
	private Users newUserAssigned = null;
	private SDPCompany sdpCompanyLink = null;
	private SDPCompany alterSdpCompany = null;
	private AssessorModeratorCompanySites assessorModeratorCompanySites = null;
	private AssessorModeratorCompanySites alterAssessorModeratorCompanySites = null;
	private AssessorModType assessorModType = null;

	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private TrainingSite trainingSite = null;
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();

	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;

	private SdpCompanyActions newAction = null;
	private Doc actionDoc = null;
	private List<Doc> actionDocList = new ArrayList<>();
	private SdpCompanyActionsService sdpCompanyActionsService = new SdpCompanyActionsService();
	
	private String validiationExceptionsCompany = "";

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
	 * Initialize method to get all TrainingProviderApplication and prepare a
	 * for a create of a new TrainingProviderApplication
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareAdstractObject();
		prepareNew();
		trainingproviderapplicationInfo();
		lazyLoadByStatusInfo();
	}

	public void prepareAdstractObject() {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
	}

	public void populateSdpType() throws Exception {
		if (getSessionUI().isExternalParty()) {
			sdpType = null;
			sdpCompanyLink = null;
			// new sdp company link
			if (selectedTrainingProviderApplication != null) {
				if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
					sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProviderApplication.getCompany().getId(), selectedTrainingProviderApplication.getTrainingSite().getId());
				} else {
					sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProviderApplication.getCompany().getId());
				}
				if (sdpCompanyLink != null) {
					sdpType = sdpCompanyLink.getSdpType();
				}
			}
			// fail safe to use old version
			if (sdpType == null) {
				if (selectedTrainingProviderApplication != null && selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getId() != null) {
					sdpType = service.locateSdpTypeByApplicationAndSessionUser(selectedTrainingProviderApplication, getSessionUI().getActiveUser());
				}
			}
		}
	}

	public void validiateCanViewInformation() throws Exception {
		if (getSessionUI().isExternalParty()) {
			if (sdpType == null || sdpType.getViewSdpInformation() == null || !sdpType.getViewSdpInformation()) {
				selectedTrainingProviderApplication = null;
				throw new Exception("You currently do not have access to view the information");
			}
		}
	}

	/**
	 * Loads training provider application info
	 **/
	public void prepareSelectTPfor(Boolean editCompanyDetails) {
		try {
			populateSdpType();
			validiateCanViewInformation();

			TrainingProviderSkillsSetService trainingProviderSkillsSetSevice = new TrainingProviderSkillsSetService();
			SDPExtensionOfScopeUI.selectTrainingProviderApplication = null;
			SDPReAccreditationUI.selectTrainingProviderApplication = null;
			trainingProviderUser = usersService.findByKey(selectedTrainingProviderApplication.getUsers().getId(), ConfigDocProcessEnum.TP, CompanyUserTypeEnum.User);

			resolveDocForTPApplication();

			if (getSessionUI().isEmployee()) {
				canActionInformation = false;
			} else {
				if (selectedTrainingProviderApplication.getApprovalStatus() == ApprovalEnum.Approved || selectedTrainingProviderApplication.getApprovalStatus() == ApprovalEnum.DeAccredited) {
					canActionInformation = true;
				} else {
					canActionInformation = false;
				}
			}
			
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				trainingSite = trainingSiteService.findByKey(selectedTrainingProviderApplication.getTrainingSite().getId());
				trainingSiteService.resolveAddressInformatioAndRegion(trainingSite);
			} else {
				trainingSite = null;
			}

			companyQualifications = companyQualificationsService.findByTargetClassAndTargetKey(selectedTrainingProviderApplication.getClass().getName(), selectedTrainingProviderApplication.getId());
			unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(selectedTrainingProviderApplication.getClass().getName(), selectedTrainingProviderApplication.getId());
			meetingSchedule = selectedTrainingProviderApplication.getReviewCommitteeMeeting();
			tpSkillsProgramList = trainingProviderSkillsProgrammeService.findByTrainingProvider(selectedTrainingProviderApplication.getId());
			tpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProvider(selectedTrainingProviderApplication.getId());
			DocService docService = new DocService();
			docList = docService.searchByClassAndKeyConfigDocNotNull(selectedTrainingProviderApplication.getId(), selectedTrainingProviderApplication.getClass().getName());
			contactPersonList = companyUsersService.findTPContact(selectedTrainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			facilitatorAssessorList = companyUsersService.findTPAssessorMod(selectedTrainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);

			selectedTrainingProviderApplication.getCompany().setResidentialAddress(AddressService.instance().findByKey(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getId()));
			selectedTrainingProviderApplication.getCompany().setPostalAddress(AddressService.instance().findByKey(selectedTrainingProviderApplication.getCompany().getPostalAddress().getId()));
			companyTPApplicationList = service.findByCompany(selectedTrainingProviderApplication.getCompany());
			if (companyTPApplicationList != null && companyTPApplicationList.size() > 0) {
				companyTPApplicationList.remove(selectedTrainingProviderApplication);
			}
			this.editCompanyDetails = editCompanyDetails;
			prepareEditCompany();
			legacyprovideraccreditationInfo();

			// populate new SDP Structure
			sdpCompanyDataModelInfo();
			// populate new Ass/Mod Structure
			assessorModeratorCompanySitesDataModelInfo();
			// clears information for SDP alteration information
			clearSdfActionInfo();
			// clears info regarding SDP & ass/mod alteration
			clearSdpAssModAlteration();
			trainingSiteSetmisValidiations = "";
			setmisValidiationErrors = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateTrainingSiteInformation(){
		try {
			trainingSiteSetmisValidiations = "";
			trainingSiteSetmisValidiations = trainingSiteService.validiateSiteInformation(trainingSite).toString();
			if (!trainingSiteSetmisValidiations.trim().isEmpty()) {
				addErrorMessage("Validiation error. Please review the error message and make the following changes.");
			} else {
				trainingSiteService.createUpdateSite(trainingSite);
				trainingSite = trainingSiteService.findByKey(trainingSite.getId());
				trainingSiteService.resolveAddressInformatioAndRegion(trainingSite);
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();

			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (selectedTrainingProviderApplication != null && selectedTrainingProviderApplication.getTrainingSite() != null) {
						if (selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSiteSdpView(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getTrainingSite().getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSiteSdpView(filters));
						}
					} else if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
						retorno = sdpCompanyService.allSdpLinkedToHoldingCompanySdpView(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getId());
						sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToHoldingCompanySdpView(filters));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDPCompany obj) {
				return obj.getId();
			}

			@Override
			public SDPCompany getRowData(String rowKey) {
				for (SDPCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void assessorModeratorCompanySitesDataModelInfo() {
		assessorModeratorCompanySitesDataModel = new LazyDataModel<AssessorModeratorCompanySites>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorCompanySites> retorno = new ArrayList<>();

			@Override
			public List<AssessorModeratorCompanySites> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedTrainingProviderApplication != null && selectedTrainingProviderApplication.getTrainingSite() != null) {
						if (selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getTrainingSite().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToTrainingSite(filters));
						}
					} else {
						if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, selectedTrainingProviderApplication.getCompany().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToHoldingCompany(filters));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorCompanySites obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorCompanySites getRowData(String rowKey) {
				for (AssessorModeratorCompanySites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void prepareEditCompany() throws Exception {
		if (selectedTrainingProviderApplication.getApprovalStatus() != ApprovalEnum.Approved) {
			this.editCompanyDetails = false;
		}
	}

	public void clearSelectedTP() {
		try {
			selectedTrainingProviderApplication = null;
			sdpType = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void clearUser() {
		try {
			user = new Users();
			selectedCompanyUser = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void submitNewContactPerson() {
		try {
			if (assessorModUpdate == null || !assessorModUpdate) {
				addContactValidation();
				// Creating Task
				service.submitNewContactPerson(selectedTrainingProviderApplication, user,
						getSessionUI().getActiveUser(), TrainingProviderApplicationUI.changeReason);
				super.runClientSideExecute("PF('dlgContatPersonChange').hide()");
				clearUser();
				prepareContactPerson();
				addInfoMessage("Request submitted for review");

			} else {
				addAssessorModValidation();
				// Creating Task
				service.submitNewAssessorMod(selectedTrainingProviderApplication, user, getSessionUI().getActiveUser(),
						TrainingProviderApplicationUI.changeReason);
				super.runClientSideExecute("PF('dlgContatPersonChange').hide()");
				clearUser();
				prepareContactPerson();
				addInfoMessage("Request submitted for review");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void prepareContactPerson() {
		try {
			if (assessorModUpdate == null || !assessorModUpdate) {
				contactPersonList = companyUsersService.findTPContact(
						selectedTrainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			} else {
				facilitatorAssessorList = companyUsersService.findTPAssessorMod(
						selectedTrainingProviderApplication.getCompany().getId(), ConfigDocProcessEnum.TP);
			}
			super.runClientSideUpdate("contactPersonsTab");
			super.runClientSideUpdate("sdpInfoForm");
			super.runClientSideUpdate("sdpManagementForm");
			prepareEditCompany();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void submitContactPersonUpdate() {
		try {
			validateUserEmail();
			// Creating Task
			service.submitContactPersonUpdate(selectedTrainingProviderApplication, user, selectedCompanyUser,
					getSessionUI().getActiveUser(), TrainingProviderApplicationUI.changeReason);
			super.runClientSideExecute("PF('dlgContatPersonChange').hide()");
			clearUser();
			prepareContactPerson();
			addInfoMessage("Request submitted for review");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addContactValidation() throws Exception {
		if (user != null) {
			validateUserEmail();
			if (user.getId() != null) {
				if (contactPersonList != null && contactPersonList.size() > 0) {
					for (CompanyUsers cp : contactPersonList) {
						if (cp.getUser().equals(user)) {
							throw new Exception("Contact person already added");
						}
					}
				}
			}
		} else {
			throw new Exception("Please add contact person");
		}
	}

	public void addAssessorModValidation() throws Exception {
		if (user != null) {
			validateUserEmail();
			if (user.getId() != null) {
				if (facilitatorAssessorList != null && facilitatorAssessorList.size() > 0) {
					for (CompanyUsers cp : facilitatorAssessorList) {
						if (cp.getUser().equals(user) && cp.getAssessorModType() == user.getAssessorModType()) {
							throw new Exception(user.getAssessorModType() + " already added");
						}
					}
				}
			}

		} else {
			throw new Exception("Please add contact person");
		}
	}

	public void validateUserEmail() throws Exception {

		if (user.getId() != null) {
			usersService.isMailUsed(user.getEmail(), user);
		} else {
			usersService.checkEmailUsedEmailOnly(user.getEmail());
		}
	}

	public void resolveDocForTPApplication() {
		ArrayList<Doc> docsForCurrentApplication = new ArrayList<>();
		for (Doc doc : trainingProviderUser.getDocs()) {
			if (doc.getTargetKey() != null && doc.getTargetKey().equals(selectedTrainingProviderApplication.getId())) {
				docsForCurrentApplication.add(doc);
			}

		}
		trainingProviderUser.setDocs(docsForCurrentApplication);
	}

	public void createSeniorManagerApprovalTask() {
		try {
			service.createSeniorManagerApprovalTask(trainingproviderapplication, getSessionUI().getActiveUser());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}

	public List<Doc> loadUserDocuments(Users user) {
		List<Doc> list = new ArrayList<>();
		try {
			list = docService.searchByUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	public List<Doc> loadUserDocumentsByConfig(Users user, Long tpId) {
		List<Doc> list = new ArrayList<>();
		try {
			list = docService.searchByUserConfigtargetClassAndKey(user, ConfigDocProcessEnum.TP,
					TrainingProviderApplication.class.getName(), tpId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	public void updateReviewCommitteeDate() {
		try {
			service.updateReviewCommitteeDate(trainingproviderapplication, reviewCommitteeMeeting,
					getSessionUI().getActiveUser());
			addInfoMessage("Review Committee Date Updated");
			lazyLoadByStatusInfo();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dateSchedule').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public ArrayList<ReviewCommitteeMeeting> getTPReviewCommitteeMeetingList() {
		ArrayList<ReviewCommitteeMeeting> list = new ArrayList<>();
		if (trainingproviderapplication.getSiteVisitDate() != null) {
			try {
				list = (ArrayList<ReviewCommitteeMeeting>) rcmService
						.allActiveReviewCommitteeMeeting(trainingproviderapplication.getSiteVisitDate());
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return list;
	}

	public void downloadSelfEvaluationForm() {

		try {

			service.downloadSelfEvaluationForm(trainingproviderapplication, null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createSeniorManagerRejectionTask() {
		try {
			if (selectedRejectReason == null || selectedRejectReason.size() <= 0)
				throw new Exception("Please select a reject reason");

			service.createSeniorManagerRejectionTask(trainingproviderapplication, getSessionUI().getActiveUser(),
					selectedRejectReason);
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('rejectReason').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectionTaskForResubmition() {
		try {
			if (selectedRejectReason == null || selectedRejectReason.size() <= 0)
				throw new Exception("Please select a reject reason");
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.TP;
			if (trainingproviderapplication
					.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				configDocProcessEnum = ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL;
			}
			service.rejectionTaskForResubmition(trainingproviderapplication, getSessionUI().getActiveUser(),
					selectedRejectReason, getSessionUI().getActiveUser(), configDocProcessEnum);
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('rejectReasonForResumition').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<CompanyUsers> loadContactPerson(Company comp) {
		List<CompanyUsers> contactPersonList = new ArrayList<>();
		try {
			contactPersonList = companyUsersService.findTPContact(comp.getId(), ConfigDocProcessEnum.TP);
			if (contactPersonList == null) {
				contactPersonList = new ArrayList<>();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return contactPersonList;
	}

	public List<CompanyUsers> loadAssessorFacilitator(Company comp) {
		List<CompanyUsers> facilitatorAssessorList = new ArrayList<>();
		try {
			facilitatorAssessorList = companyUsersService.findTPAssessorMod(comp.getId(), ConfigDocProcessEnum.TP);
			if (facilitatorAssessorList == null) {
				facilitatorAssessorList = new ArrayList<>();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

		return facilitatorAssessorList;
	}

	public void documentHistoryInfo() {

		docHistDataModel = new LazyDataModel<Doc>() {

			private static final long serialVersionUID = 1L;
			private List<Doc> retorno = new ArrayList<Doc>();

			@Override
			public List<Doc> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("targetClass", selectedTrainingProviderApplication.getClass().getName());
					filters.put("targetKey", selectedTrainingProviderApplication.getId());
					retorno = docService.allDocAndResolve(first, pageSize, sortField, sortOrder, filters);
					docHistDataModel.setRowCount(docService.countWhere(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Doc obj) {
				return obj.getId();
			}

			@Override
			public Doc getRowData(String rowKey) {
				for (Doc obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all TrainingProviderApplication for data table
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void lazyLoadByStatusInfo() {
		pendingAppDataModel = new LazyDataModel<TrainingProviderApplication>() {

			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<TrainingProviderApplication>();

			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("approvalStatus", ApprovalEnum.PendingCommitteeApproval);
					retorno = service.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters);
					// retorno =
					// service.allTrainingProviderApplication(TrainingProviderApplication.class,first,pageSize,sortField,sortOrder,filters);
					pendingAppDataModel.setRowCount(service.count(TrainingProviderApplication.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public void legacyprovideraccreditationInfo() {

		legacyDataModel = new LazyDataModel<LegacyProviderAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderAccreditation> retorno = new ArrayList<LegacyProviderAccreditation>();

			@Override
			public List<LegacyProviderAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("linkedSdl", selectedTrainingProviderApplication.getCompany().getLevyNumber());
					retorno = legacyProviderAccreditationService.allLegacyProviderAccreditation(
							LegacyProviderAccreditation.class, first, pageSize, sortField, sortOrder, filters);
					legacyDataModel.setRowCount(
							legacyProviderAccreditationService.count(LegacyProviderAccreditation.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderAccreditation getRowData(String rowKey) {
				for (LegacyProviderAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public ArrayList<Company> companyList(TrainingProviderApplication tpApplication) {
		ArrayList<Company> companyList = new ArrayList<>();
		CompanyService companyService = new CompanyService();
		try {
			Company company = companyService.findByKeyResolveDoc(tpApplication.getCompany().getId());
			if (company != null) {
				companyList.add(company);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

		return companyList;
	}

	public ArrayList<CompanyUnitStandard> companyUnitStandard(TrainingProviderApplication tpApplication) {
		ArrayList<CompanyUnitStandard> l = new ArrayList<>();
		try {
			// l= (ArrayList<CompanyUnitStandard>)
			// companyUnitStandardService.findByCompany(tpApplication.getCompany());
			l = (ArrayList<CompanyUnitStandard>) companyUnitStandardService
					.findByTargetClassAndTargetKey(tpApplication.getClass().getName(), tpApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public ArrayList<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammes(TrainingProviderApplication tpApplication) {
		ArrayList<TrainingProviderSkillsProgramme> l = new ArrayList<>();
		try {

			l = (ArrayList<TrainingProviderSkillsProgramme>) trainingProviderSkillsProgrammeService.findByTrainingProvider(tpApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public ArrayList<TrainingProviderSkillsSet> trainingProviderSkillsSet(TrainingProviderApplication tpApplication) {
		ArrayList<TrainingProviderSkillsSet> l = new ArrayList<>();
		try {

			l = (ArrayList<TrainingProviderSkillsSet>) trainingProviderSkillsSetService.findByTrainingProvider(tpApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public ArrayList<CompanyQualifications> companyQualifications(TrainingProviderApplication tpApplication) {
		ArrayList<CompanyQualifications> l = new ArrayList<>();
		try {
			// l= (ArrayList<CompanyQualifications>)
			// companyQualificationsService.findByCompany(tpApplication.getCompany());
			l = (ArrayList<CompanyQualifications>) companyQualificationsService.findByTargetClassAndTargetKey(tpApplication.getClass().getName(), tpApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

		return l;
	}

	/**
	 * Get all TrainingProviderApplication for data table
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void trainingproviderapplicationInfo() {

		dataModel = new LazyDataModel<TrainingProviderApplication>() {

			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<TrainingProviderApplication>();

			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					// retorno =
					// service.allTrainingProviderApplication(TrainingProviderApplication.class,first,pageSize,sortField,sortOrder,filters);
					retorno = service.sortAndFilterWhereTPInfo(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countWhereTPInfo(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert TrainingProviderApplication into database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void trainingproviderapplicationInsert() {
		try {
			service.create(this.trainingproviderapplication);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderapplicationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update TrainingProviderApplication in database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void trainingproviderapplicationUpdate() {
		try {
			service.update(this.trainingproviderapplication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderapplicationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFileAndCreateTask(FileUploadEvent event) {
		try {
			docChange.setApprovalStatus(ApprovalEnum.PendingApproval);
			docChange.setData(event.getFile().getContents());
			docChange.setOriginalFname(event.getFile().getFileName());
			docChange.setExtension(FilenameUtils.getExtension(docChange.getOriginalFname()));
			DocService.instance().uploadNewVersionAndCreateSDPDocUpdateTask(docChange, getSessionUI().getActiveUser(), changeReason);
			selectedTrainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
			service.update(selectedTrainingProviderApplication);
			super.runClientSideExecute("PF('dlgUploadAndCreateTask').hide()");
			super.runClientSideUpdate("sdpManagementForm");
			prepareEditCompany();
			addInfoMessage("Request submitted for review");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Delete TrainingProviderApplication from database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void trainingproviderapplicationDelete() {
		try {
			service.delete(this.trainingproviderapplication);
			prepareNew();
			trainingproviderapplicationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of TrainingProviderApplication
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void prepareNew() {
		trainingproviderapplication = new TrainingProviderApplication();
	}

	/*
	 * public List<SelectItem> getTrainingProviderApplicationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * trainingproviderapplicationInfo(); for (TrainingProviderApplication ug :
	 * trainingproviderapplicationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<TrainingProviderApplication> complete(String desc) {
		List<TrainingProviderApplication> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Download Pro-forma Letter for Full Accreditation
	 */
	public void downloadETQTP017(TrainingProviderApplication tp) {
		try {
			service.downloadETQTP017(tp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Provider Certificate
	 */
	public void downloadProviderCertificate(TrainingProviderApplication tp) {
		try {
			service.downloadProviderCertificate(tp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Accreditation Approval Application Form
	 */
	public void downloadETQFM002A(TrainingProviderApplication tp) {
		try {
			service.downloadETQFM002A(tp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Statement Of Qualifications
	 */
	public void downloadETQTP011(TrainingProviderApplication tp) {
		try {
			service.downloadETQTP011(tp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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
				setmisValidiationErrors = "";
				if (newStructureSearch) {
					this.newUserAssigned = (Users) object;
					if (!sdpUserSearch) {
						if (newUserAssigned != null && newUserAssigned.getId() != null) {
							service.checkIfIsAssOrMod(newUserAssigned);
						} else {
							newUserAssigned = null;
							throw new Exception("The select user is not an accredited assessor/moderator");
						}
					}
					newUserAssigned.setRecieveEmailTaskNotification(true);
					newUserAssigned.setRegFieldsDone(false);
					if (newUserAssigned.getAcceptPopi() == null) {
						newUserAssigned.setAcceptPopi(true);
					}
					newUserAssigned.setDoneSearch(true);
					usersService.identifyFieldAlteration(newUserAssigned);
				} else {
					this.user = (Users) object;
					if (assessorModUpdate) {
						if (user != null && user.getId() != null) {
							service.checkIfIsAssOrMod(user);
						} else {
							user = null;
							throw new Exception("The select user is not an accredited assessor/moderator");
						}
					}	
					user.setRecieveEmailTaskNotification(true);
					user.setRegFieldsDone(false);
					if (user.getAcceptPopi() == null) {
						user.setAcceptPopi(true);
					}
					user.setDoneSearch(true);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<SelectItem> getAssessorModTypeDD() {
		List<SelectItem> l = null;
		try {
			l = new ArrayList<SelectItem>();
			AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			List<AssessorModeratorApplication> amApplicationList = assessorModeratorApplicationService
					.findByApprovedUserApplications(user);

			for (AssessorModeratorApplication amApp : amApplicationList) {
				if (amApp.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration
						|| amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope
						|| amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration) {
					AssessorModType val = AssessorModType.Assessor;
					l.add(new SelectItem(val, val.getFriendlyName()));
				} else {
					AssessorModType val = AssessorModType.Moderator;
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			if (l.size() == 1) {
				user.setAssessorModType((AssessorModType) l.get(0).getValue());
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Download Pro-forma Letter for Full Accreditation
	 */
	public void downloadETQTP017() {
		try {
			service.downloadETQTP017(selectedTrainingProviderApplication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Provider Certificate
	 */
	public void downloadProviderCertificate() {
		try {
			service.downloadProviderCertificate(selectedTrainingProviderApplication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Accreditation Approval Application Form
	 */
	public void downloadETQFM002A() {
		try {
			service.downloadETQFM002A(selectedTrainingProviderApplication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Statement Of Qualifications
	 */
	public void downloadETQTP011() {
		try {
			service.downloadETQTP011(selectedTrainingProviderApplication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Download Approval Report
	 */
	public void downloadETQTP009() {
		try {
			service.downloadETQTP009(selectedTrainingProviderApplication);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void updateCompayAndCreateTask(FileUploadEvent event) {
		try {
			selectedTrainingProviderApplication.getCompany().setCompanyStatus(CompanyStatusEnum.PendingChangeApproval);
			selectedTrainingProviderApplication.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			selectedTrainingProviderApplication.getCompany().getPostalAddress().setSameAddress(copyAddress);
			service.updateCompayAndCreateTask(selectedTrainingProviderApplication, getSessionUI().getActiveUser());
			storeChangeNewFile(event);
			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(selectedTrainingProviderApplication.getId(),
					selectedTrainingProviderApplication.getClass().getName(),
					TrainingProviderApplicationUI.changeReason);
			prepareEditCompany();
			super.runClientSideUpdate("sdpManagementForm");
			super.runClientSideUpdate("sdpInfoForm");
			addInfoMessage(super.getEntryLanguage("update.successful"));

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepChangeDoc() {
		clearChangeReason();
		doc = new Doc();
		doc.setChangeReason(changeReason);
	}

	public void clearChangeReason() {
		changeReason = new ChangeReason();
		changeReason.setDescription(null);
	}

	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			if (doc == null) {
				prepChangeDoc();
			}
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			changeReason.setDoc(doc);
			pageRedirection();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void pageRedirection() {
		try {
			if (redirectTo != null) {
				if (redirectTo.equals("dlgContatPersonChange")) {
					clearUser();
					super.runClientSideExecute("PF('dlgContatPersonChange').show()");
					super.runClientSideUpdate("sdpuserForm");
				} else if (redirectTo.equals("dlgContatPersonDelete")) {
					createRemoveSDPConatctPersonTask();
				} else if (redirectTo.equals("dlgContatPersonUpdate")) {
					super.runClientSideExecute("PF('dlgContatPersonChange').show()");
					super.runClientSideUpdate("sdpuserForm");
					super.runClientSideExecute("PF('dlgContatPersonChange').update()");
				} else if (redirectTo.equals("dlgDeleteAssesorMod")) {
					createRemoveSDPAssessorModTask();
				} else if (redirectTo.equals("dlgUploadAndCreateTask")) {
					super.runClientSideExecute("PF('dlgUploadAndCreateTask').show()");
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void prepareSdpContactUpdate() {
		try {
			user = selectedCompanyUser.getUser();
			user.setDoneSearch(true);
			user.setDesignation(selectedCompanyUser.getDesignation());
			if (selectedCompanyUser.getExistingUser() != null) {
				user.setExistingUser(selectedCompanyUser.getExistingUser());
			} else {
				user.setExistingUser(false);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void createRemoveSDPConatctPersonTask() {
		try {
			service.createRemoveSDPConatctPersonTask(selectedTrainingProviderApplication,
					getSessionUI().getActiveUser(), selectedCompanyUser, TrainingProviderApplicationUI.changeReason);
			addInfoMessage("Request submitted for review ");
			prepareContactPerson();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void createRemoveSDPAssessorModTask() {
		try {
			service.createRemoveSDPAssessorModTask(selectedTrainingProviderApplication, getSessionUI().getActiveUser(),
					selectedCompanyUser, TrainingProviderApplicationUI.changeReason);
			addInfoMessage("Request submitted for review ");
			prepareContactPerson();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public String getContactPersonTitle() {
		if (assessorModUpdate == null || !assessorModUpdate) {
			return "SDP Contact Person";
		} else {
			return "SDP Assessor/Moderator";
		}
	}
	
	private void clearSdpAssModAlteration(){
		newUserAssigned = null;
		alterSdpCompany = null;
		alterAssessorModeratorCompanySites = null;
		assessorModType = null;
	}
	
	private void clearSdfActionInfo() {
		newStructureSearch = false;
		sdpUserSearch = false;
		newUserAssigned = null;
		newAction = null;
		actionDoc = null;
		actionDocList.clear();
		setmisValidiationErrors = "";
	}
	
	public void prepUpdateDesignationSdp(){
		try {
			alterAssessorModeratorCompanySites = null;
			clearSdfActionInfo();
			newAction = new SdpCompanyActions();
			newAction.setSdpCompanyAction(SdpCompanyActionListEnum.UpdateDesignation);
			newAction.setTrainingProviderApplication(selectedTrainingProviderApplication);
			newAction.setCompany(alterSdpCompany.getCompany());
			newAction.setSdpCompanyFlatId(alterSdpCompany.getId());
			newAction.setCurrentDesignation(alterSdpCompany.getSdpType());
			newAction.setSdpUser(alterSdpCompany.getSdp());
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				newAction.setTrainingSite(selectedTrainingProviderApplication.getTrainingSite());
			}
			runClientSideExecute("PF('sdpUpdateDesignationDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewActionDoc(){
		actionDoc = new Doc();
	}
	
	public void prepActionUpload(){
		try {
			prepNewActionDoc();
			runClientSideExecute("PF('uploadSupportingDocDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeNewActionFile(FileUploadEvent event) {
		try {
			actionDoc.setData(event.getFile().getContents());
			actionDoc.setOriginalFname(event.getFile().getFileName());
			actionDoc.setExtension(FilenameUtils.getExtension(actionDoc.getOriginalFname()));
			if (actionDoc.getId() != null) {
				DocService.instance().uploadNewVersion(actionDoc, getSessionUI().getActiveUser());
			}
			// add support doc
			if (actionDocList == null) {
				actionDocList = new ArrayList<>();
			}
			actionDocList.add(actionDoc);
			runClientSideExecute("PF('uploadSupportingDocDlg').hide()");
			addInfoMessage("Documents Uploaded, Awaiting Submission");
			prepNewActionDoc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitUpdateDesignationSdp() {
		try {
			// min of 1 doc required
			if (actionDocList.isEmpty()) {
				throw new Exception("Upload supporting documents to proceed.");
			}
			// new designation assigned
			if (newAction.getNewDesignation() == null) {
				throw new Exception("Please assign new designation before proceeding.");
			}
			// check if have no selected the same designation
			if (newAction.getCurrentDesignation().getId().equals(newAction.getNewDesignation().getId())) {
				throw new Exception("Please assign a different designation before proceeding. Selected current designation.");
			}
			// designation not already assigned on company level
			int counterIfNewDesignationAssigned = 0;
			int counterInWorkflow = 0;
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByTrainingSiteId(selectedTrainingProviderApplication.getTrainingSite().getId(), newAction.getNewDesignation().getId());
				counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(newAction.getNewDesignation().getId(), selectedTrainingProviderApplication.getCompany().getId(), selectedTrainingProviderApplication.getTrainingSite().getId(), ApprovalEnum.getOpenStatusListForSdpCompanyActions());
			} else {
				counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByHoldingCompany(selectedTrainingProviderApplication.getCompany().getId(), newAction.getNewDesignation().getId());
				counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(newAction.getNewDesignation().getId(), selectedTrainingProviderApplication.getCompany().getId(), null, ApprovalEnum.getOpenStatusListForSdpCompanyActions());
			}
			if (counterIfNewDesignationAssigned > 0) {
				throw new Exception("Designation already assigned to another SDP contact, Please select a different designation.");
			}
			if (counterInWorkflow > 0) {
				throw new Exception("Designation already underway in approval process. Please select a different designation.");
			}
			// check if designation change not currently in a workflow
			sdpCompanyActionsService.initiateWorkflow(newAction, alterSdpCompany.getSdp(), alterSdpCompany, selectedTrainingProviderApplication, getSessionUI().getActiveUser(), actionDocList);
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			sdpCompanyDataModelInfo();
			addInfoMessage("Action complete.");
			runClientSideExecute("PF('sdpUpdateDesignationDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepDelinkSdpContactPerson(){
		try {
			// check if has three min contacts
			if (sdpCompanyService.validiationNumberOfContactsAssignedByProviderApplication(selectedTrainingProviderApplication) <= 2) {
				clearSdpAssModAlteration();
				clearSdfActionInfo();
				addErrorMessage("Validiation Error. Profile must always contain a mininum of 2 approved: SDP contacts persons (Excluding Primary SDP Contact and 2 Secondary SDP Contacts). Please assign more SDP contact persons or complete any open SDP contact persons tasks before attempting to remove.");
			} else {
				alterAssessorModeratorCompanySites = null;
				clearSdfActionInfo();
				newAction = new SdpCompanyActions();
				newAction.setSdpCompanyAction(SdpCompanyActionListEnum.RemoveSDP);
				newAction.setTrainingProviderApplication(selectedTrainingProviderApplication);
				newAction.setCompany(alterSdpCompany.getCompany());
				newAction.setSdpCompanyFlatId(alterSdpCompany.getId());
				newAction.setCurrentDesignation(alterSdpCompany.getSdpType());
				newAction.setSdpUser(alterSdpCompany.getSdp());
				if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
					newAction.setTrainingSite(selectedTrainingProviderApplication.getTrainingSite());
				}
				runClientSideExecute("PF('sdpRemoveSdpDlg').show()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitDelinkSdpContactPerson() {
		try {
			// min of 1 doc required
			if (actionDocList.isEmpty()) {
				throw new Exception("Upload supporting documents to proceed.");
			}
			sdpCompanyActionsService.initiateWorkflow(newAction, alterSdpCompany.getSdp(), alterSdpCompany, selectedTrainingProviderApplication, getSessionUI().getActiveUser(), actionDocList);
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			sdpCompanyDataModelInfo();
			addInfoMessage("Action complete.");
			runClientSideExecute("PF('sdpRemoveSdpDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewSdpContactPerson(){
		try {			
			alterAssessorModeratorCompanySites = null;
			clearSdfActionInfo();
			prepUserSearch();
			newStructureSearch = true;
			sdpUserSearch = true;
			newAction = new SdpCompanyActions();
			newAction.setSdpCompanyAction(SdpCompanyActionListEnum.NewSDP);
			newAction.setTrainingProviderApplication(selectedTrainingProviderApplication);
			newAction.setCompany(selectedTrainingProviderApplication.getCompany());
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				newAction.setTrainingSite(selectedTrainingProviderApplication.getTrainingSite());
			}
			runClientSideExecute("PF('sdpNewSdpDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUserSearch() {
		newUserAssigned = null;
		getSearchUserPassportOrIdUI().setObject(this);
	}
	
	public void cancelNewSdpContactPerson(){
		try {
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			runClientSideExecute("PF('sdpNewSdpDlg').hide()");
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitNewSdpContactPerson() {
		try {
			// min of 1 doc required
			if (actionDocList.isEmpty()) {
				throw new Exception("Upload supporting documents to proceed.");
			}
			// new designation assigned
			if (newAction.getNewDesignation() == null) {
				throw new Exception("Please assign new designation before proceeding.");
			}
			// check to see if user assigned
			if (newUserAssigned == null) {
				throw new Exception("Please provide new SDP contact user before proceeding.");
			}
			// designation not already assigned on company level
			int counterIfNewDesignationAssigned = 0;
			int counterInWorkflow = 0;
			int userAssignedToCompany = 0;
			int userInWorkflowForCompany = 0;
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByTrainingSiteId(selectedTrainingProviderApplication.getTrainingSite().getId(), newAction.getNewDesignation().getId());
				counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(newAction.getNewDesignation().getId(), selectedTrainingProviderApplication.getCompany().getId(), selectedTrainingProviderApplication.getTrainingSite().getId(), ApprovalEnum.getOpenStatusListForSdpCompanyActions());
				if (newUserAssigned.getId() != null) {
					userAssignedToCompany = sdpCompanyService.countUserAssignedByTrainingSiteId(selectedTrainingProviderApplication.getTrainingSite().getId(), newUserAssigned.getId());
					userInWorkflowForCompany = sdpCompanyActionsService.countByUserIdCompanyTrainingSiteAndOpenStatus(newUserAssigned.getId(), selectedTrainingProviderApplication.getCompany().getId(), selectedTrainingProviderApplication.getTrainingSite().getId(), ApprovalEnum.getOpenStatusListForSdpCompanyActions());
				}
			} else {
				counterIfNewDesignationAssigned = sdpCompanyService.countSdpTypeByHoldingCompany(selectedTrainingProviderApplication.getCompany().getId(), newAction.getNewDesignation().getId());
				counterInWorkflow = sdpCompanyActionsService.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(newAction.getNewDesignation().getId(), selectedTrainingProviderApplication.getCompany().getId(), null, ApprovalEnum.getOpenStatusListForSdpCompanyActions());
				if (newUserAssigned.getId() != null) {
					userAssignedToCompany = sdpCompanyService.countUserAssignedByHoldingCompany(selectedTrainingProviderApplication.getCompany().getId(), newUserAssigned.getId());
					userInWorkflowForCompany = sdpCompanyActionsService.countByUserIdCompanyTrainingSiteAndOpenStatus(newUserAssigned.getId(), selectedTrainingProviderApplication.getCompany().getId(), null, ApprovalEnum.getOpenStatusListForSdpCompanyActions());
				}
			}
			if (counterIfNewDesignationAssigned > 0) {
				throw new Exception("Designation already assigned to another SDP contact, Please select a different designation.");
			}
			if (counterInWorkflow > 0) {
				throw new Exception("Designation already underway in approval process. Please select a different designation.");
			}
			if (userAssignedToCompany > 0) {
				throw new Exception("SDP contact user is already assigned to the company. Please provide a different user.");
			}
			if (userInWorkflowForCompany > 0) {
				throw new Exception("SDP contact user is underway in an approval process for the company. Please provide a different user.");
			}
			// Validate user information
			setmisValidiationErrors = "";
			setmisValidiationErrors = usersService.validiateUserInformation(newUserAssigned).toString();
			if (!setmisValidiationErrors.trim().isEmpty()) {
				throw new Exception("SDP contact user information validiation error. Please review error message.");
			}
			if (newUserAssigned.getId() == null) {
				// check if email in use
				if (usersService.countUsersByEmail(newUserAssigned.getEmail().trim()) > 0) {
					throw new Exception("SDP contact user email address provided is already registered on the application. Provide a new email address or contact merSETA support.");
				}
			}
			sdpCompanyActionsService.initiateWorkflow(newAction, newUserAssigned, null, selectedTrainingProviderApplication, getSessionUI().getActiveUser(), actionDocList);
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			sdpCompanyDataModelInfo();
			addInfoMessage("Action complete.");
			runClientSideExecute("PF('sdpNewSdpDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeAssModLink(){
		try {
			clearSdfActionInfo();
			assessorModeratorCompanySitesService.removeLinkSendNotification(alterAssessorModeratorCompanySites, getSessionUI().getActiveUser());
			alterAssessorModeratorCompanySites = null;
			assessorModeratorCompanySitesDataModelInfo();
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewAssessorModeratorLink(){
		try {
			clearSdfActionInfo();
			prepUserSearch();
			newStructureSearch = true;
			sdpUserSearch = false;
			assessorModType = null;
			runClientSideExecute("PF('sdpAssModDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cancelNewAssessorModeratorLink(){
		try {
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			runClientSideExecute("PF('sdpAssModDlg').show()");
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<SelectItem> getAssessorModTypeDDVersionTwo() {
		List<SelectItem> l = null;
		try {
			l = new ArrayList<SelectItem>();
			AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			List<AssessorModeratorApplication> amApplicationList = assessorModeratorApplicationService.findByApprovedUserApplications(newUserAssigned);
			boolean addedAss = false;
			boolean addedMod = false;
			for (AssessorModeratorApplication amApp : amApplicationList) {
				if (amApp.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope || amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration) {
					if (!addedAss) {
						AssessorModType val = AssessorModType.Assessor;
						l.add(new SelectItem(val, val.getFriendlyName()));
						addedAss = true;
					}
				} else {
					if (!addedMod) {
						AssessorModType val = AssessorModType.Moderator;
						l.add(new SelectItem(val, val.getFriendlyName()));
						addedMod = true;
					}
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void checkIfIsAssOrModVersionTwo() {
		try {
			service.checkIfIsAssOrMod(newUserAssigned);
		} catch (Exception e) {
			assessorModType = null;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createNewAssessorModeratorLink(){
		try {
			// type assigned
			if (assessorModType == null) {
				throw new Exception("Provide a type before proceeding");
			}
			// currently not linked with type 
			if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
				if (assessorModeratorCompanySitesService.countByUserTrainingSiteAssModType(newUserAssigned.getId(), selectedTrainingProviderApplication.getTrainingSite().getId(), assessorModType) > 0) {
					throw new Exception("User already assigned to company with type. Please select a different user or type.");
				}
			}else {
				if (assessorModeratorCompanySitesService.countByUserHoldingCompanyAssModType(newUserAssigned.getId(), selectedTrainingProviderApplication.getCompany().getId(), assessorModType) > 0) {
					throw new Exception("User already assigned to company with type. Please select a different user or type.");
				}
			}
			// create link
			assessorModeratorCompanySitesService.createNewAssessorModeratorLink(selectedTrainingProviderApplication, getSessionUI().getActiveUser(), newUserAssigned, assessorModType);
			clearSdfActionInfo();
			clearSdpAssModAlteration();
			assessorModeratorCompanySitesDataModelInfo();
			addInfoMessage("Action complete.");
			runClientSideExecute("PF('sdpAssModDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public List<TrainingProviderApplication> getTrainingProviderApplicationList() {
		return trainingproviderapplicationList;
	}

	public void setTrainingProviderApplicationList(List<TrainingProviderApplication> trainingproviderapplicationList) {
		this.trainingproviderapplicationList = trainingproviderapplicationList;
	}

	public TrainingProviderApplication getTrainingproviderapplication() {
		return trainingproviderapplication;
	}

	public void setTrainingproviderapplication(TrainingProviderApplication trainingproviderapplication) {
		this.trainingproviderapplication = trainingproviderapplication;
	}

	public List<TrainingProviderApplication> getTrainingProviderApplicationfilteredList() {
		return trainingproviderapplicationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param trainingproviderapplicationfilteredList
	 *            the new trainingproviderapplicationfilteredList list
	 * @see TrainingProviderApplication
	 */
	public void setTrainingProviderApplicationfilteredList(
			List<TrainingProviderApplication> trainingproviderapplicationfilteredList) {
		this.trainingproviderapplicationfilteredList = trainingproviderapplicationfilteredList;
	}

	public LazyDataModel<TrainingProviderApplication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderApplication> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<TrainingProviderApplication> getPendingAppDataModel() {
		return pendingAppDataModel;
	}

	public void setPendingAppDataModel(LazyDataModel<TrainingProviderApplication> pendingAppDataModel) {
		this.pendingAppDataModel = pendingAppDataModel;
	}

	/**
	 * @return the selectedTrainingProviderApplication
	 */
	public TrainingProviderApplication getSelectedTrainingProviderApplication() {
		return selectedTrainingProviderApplication;
	}

	/**
	 * @param selectedTrainingProviderApplication
	 *            the selectedTrainingProviderApplication to set
	 */
	public void setSelectedTrainingProviderApplication(
			TrainingProviderApplication selectedTrainingProviderApplication) {
		this.selectedTrainingProviderApplication = selectedTrainingProviderApplication;
	}

	/**
	 * @return the trainingProviderUser
	 */
	public Users getTrainingProviderUser() {
		return trainingProviderUser;
	}

	/**
	 * @param trainingProviderUser
	 *            the trainingProviderUser to set
	 */
	public void setTrainingProviderUser(Users trainingProviderUser) {
		this.trainingProviderUser = trainingProviderUser;
	}

	/**
	 * @return the companyQualifications
	 */
	public List<CompanyQualifications> getCompanyQualifications() {
		return companyQualifications;
	}

	/**
	 * @param companyQualifications
	 *            the companyQualifications to set
	 */
	public void setCompanyQualifications(List<CompanyQualifications> companyQualifications) {
		this.companyQualifications = companyQualifications;
	}

	/**
	 * @return the unitStandards
	 */
	public List<CompanyUnitStandard> getUnitStandards() {
		return unitStandards;
	}

	/**
	 * @param unitStandards
	 *            the unitStandards to set
	 */
	public void setUnitStandards(List<CompanyUnitStandard> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	/**
	 * @return the meetingSchedule
	 */
	public ReviewCommitteeMeeting getMeetingSchedule() {
		return meetingSchedule;
	}

	/**
	 * @param meetingSchedule
	 *            the meetingSchedule to set
	 */
	public void setMeetingSchedule(ReviewCommitteeMeeting meetingSchedule) {
		this.meetingSchedule = meetingSchedule;
	}

	public List<TrainingProviderSkillsProgramme> getTpSkillsProgramList() {
		return tpSkillsProgramList;
	}

	public void setTpSkillsProgramList(List<TrainingProviderSkillsProgramme> tpSkillsProgramList) {
		this.tpSkillsProgramList = tpSkillsProgramList;
	}

	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	/**
	 * @param reviewCommitteeMeeting
	 *            the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	/**
	 * @return the docList
	 */
	public List<Doc> getDocList() {
		return docList;
	}

	/**
	 * @param docList
	 *            the docList to set
	 */
	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}

	/**
	 * @return the contactPersonList
	 */
	public List<CompanyUsers> getContactPersonList() {
		return contactPersonList;
	}

	/**
	 * @param contactPersonList
	 *            the contactPersonList to set
	 */
	public void setContactPersonList(List<CompanyUsers> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

	/**
	 * @return the facilitatorAssessorList
	 */
	public List<CompanyUsers> getFacilitatorAssessorList() {
		return facilitatorAssessorList;
	}

	/**
	 * @param facilitatorAssessorList
	 *            the facilitatorAssessorList to set
	 */
	public void setFacilitatorAssessorList(List<CompanyUsers> facilitatorAssessorList) {
		this.facilitatorAssessorList = facilitatorAssessorList;
	}

	/**
	 * @return the tpSkillsSetList
	 */
	public List<TrainingProviderSkillsSet> getTpSkillsSetList() {
		return tpSkillsSetList;
	}

	/**
	 * @param tpSkillsSetList
	 *            the tpSkillsSetList to set
	 */
	public void setTpSkillsSetList(List<TrainingProviderSkillsSet> tpSkillsSetList) {
		this.tpSkillsSetList = tpSkillsSetList;
	}

	public LazyDataModel<Doc> getDocHistDataModel() {
		return docHistDataModel;
	}

	public void setDocHistDataModel(LazyDataModel<Doc> docHistDataModel) {
		this.docHistDataModel = docHistDataModel;
	}

	public List<TrainingProviderApplication> getCompanyTPApplicationList() {
		return companyTPApplicationList;
	}

	public void setCompanyTPApplicationList(List<TrainingProviderApplication> companyTPApplicationList) {
		this.companyTPApplicationList = companyTPApplicationList;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Boolean getEditCompanyDetails() {
		return editCompanyDetails;
	}

	public void setEditCompanyDetails(Boolean editCompanyDetails) {
		this.editCompanyDetails = editCompanyDetails;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public CompanyUsers getSelectedCompanyUser() {
		return selectedCompanyUser;
	}

	public void setSelectedCompanyUser(CompanyUsers selectedCompanyUser) {
		this.selectedCompanyUser = selectedCompanyUser;
	}

	public Boolean getAssessorModUpdate() {
		return assessorModUpdate;
	}

	public void setAssessorModUpdate(Boolean assessorModUpdate) {
		this.assessorModUpdate = assessorModUpdate;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public Doc getDocChange() {
		return docChange;
	}

	public void setDocChange(Doc docChange) {
		this.docChange = docChange;
	}

	public LazyDataModel<LegacyProviderAccreditation> getLegacyDataModel() {
		return legacyDataModel;
	}

	public void setLegacyDataModel(LazyDataModel<LegacyProviderAccreditation> legacyDataModel) {
		this.legacyDataModel = legacyDataModel;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public LazyDataModel<SDPCompany> getSdpCompanyDataModel() {
		return sdpCompanyDataModel;
	}

	public void setSdpCompanyDataModel(LazyDataModel<SDPCompany> sdpCompanyDataModel) {
		this.sdpCompanyDataModel = sdpCompanyDataModel;
	}

	public LazyDataModel<AssessorModeratorCompanySites> getAssessorModeratorCompanySitesDataModel() {
		return assessorModeratorCompanySitesDataModel;
	}

	public void setAssessorModeratorCompanySitesDataModel(
			LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel) {
		this.assessorModeratorCompanySitesDataModel = assessorModeratorCompanySitesDataModel;
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}

	public SDPCompany getAlterSdpCompany() {
		return alterSdpCompany;
	}

	public void setAlterSdpCompany(SDPCompany alterSdpCompany) {
		this.alterSdpCompany = alterSdpCompany;
	}

	public AssessorModeratorCompanySites getAssessorModeratorCompanySites() {
		return assessorModeratorCompanySites;
	}

	public void setAssessorModeratorCompanySites(AssessorModeratorCompanySites assessorModeratorCompanySites) {
		this.assessorModeratorCompanySites = assessorModeratorCompanySites;
	}

	public Boolean getCanActionInformation() {
		return canActionInformation;
	}

	public void setCanActionInformation(Boolean canActionInformation) {
		this.canActionInformation = canActionInformation;
	}

	public SdpCompanyActions getNewAction() {
		return newAction;
	}

	public void setNewAction(SdpCompanyActions newAction) {
		this.newAction = newAction;
	}

	public AssessorModeratorCompanySites getAlterAssessorModeratorCompanySites() {
		return alterAssessorModeratorCompanySites;
	}

	public void setAlterAssessorModeratorCompanySites(AssessorModeratorCompanySites alterAssessorModeratorCompanySites) {
		this.alterAssessorModeratorCompanySites = alterAssessorModeratorCompanySites;
	}

	public Doc getActionDoc() {
		return actionDoc;
	}

	public void setActionDoc(Doc actionDoc) {
		this.actionDoc = actionDoc;
	}

	public List<Doc> getActionDocList() {
		return actionDocList;
	}

	public void setActionDocList(List<Doc> actionDocList) {
		this.actionDocList = actionDocList;
	}

	public Users getNewUserAssigned() {
		return newUserAssigned;
	}

	public void setNewUserAssigned(Users newUserAssigned) {
		this.newUserAssigned = newUserAssigned;
	}

	public Boolean getNewStructureSearch() {
		return newStructureSearch;
	}

	public void setNewStructureSearch(Boolean newStructureSearch) {
		this.newStructureSearch = newStructureSearch;
	}

	public Boolean getSdpUserSearch() {
		return sdpUserSearch;
	}

	public void setSdpUserSearch(Boolean sdpUserSearch) {
		this.sdpUserSearch = sdpUserSearch;
	}

	public String getSetmisValidiationErrors() {
		return setmisValidiationErrors;
	}

	public void setSetmisValidiationErrors(String setmisValidiationErrors) {
		this.setmisValidiationErrors = setmisValidiationErrors;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public String getTrainingSiteSetmisValidiations() {
		return trainingSiteSetmisValidiations;
	}

	public void setTrainingSiteSetmisValidiations(String trainingSiteSetmisValidiations) {
		this.trainingSiteSetmisValidiations = trainingSiteSetmisValidiations;
	}

	public AssessorModType getAssessorModType() {
		return assessorModType;
	}

	public void setAssessorModType(AssessorModType assessorModType) {
		this.assessorModType = assessorModType;
	}
	
	public void updateCompanyData_vh() {
		
		System.out.println("selectedTrainingProviderApplication.getCompany()---"+selectedTrainingProviderApplication.getCompany().getCompanyName());
		System.out.println("selectedTrainingProviderApplication.getCompany()---"+selectedTrainingProviderApplication.getCompany().getCompanyStatus());
		System.out.println("selectedTrainingProviderApplication.getCompany().getRegisteredAddress().getLatitudeDegrees():"+selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeDegrees());
		System.out.println("selectedTrainingProviderApplication.getCompany().getRegisteredAddress().getLatitudeMinutes():"+selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeMinutes());
		System.out.println("selectedTrainingProviderApplication.getCompany().getRegisteredAddress().getLatitudeSeconds():"+selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeSeconds());
		System.out.println("selectedTrainingProviderApplication.getCompany().getRegisteredAddress().getLongitudeDegrees():"+selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLongitudeDegrees());
		CompanyInfoUI companyInfoUI = new CompanyInfoUI();
		CompanyStatusEnum orignalStatus = null;
		boolean result=false;
		System.out.println("HAJConstants.ADDRESS_SETMIS_VALIDATION_ON:"+HAJConstants.ADDRESS_SETMIS_VALIDATION_ON);
		if(!addressValidationService.validatingLatitudeDegrees(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeDegrees())) {
			addErrorMessage("Validation Failed For SETMIS Latitude Degrees /Field may not start with a space.Uppercase value in field may only contain characters 1234567890-/Field must be a negative value, may not be greater than -22 and may not have a value less than -35");
			result=true;
		}
		if(!addressValidationService.validatingLatitudeMinutes(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeMinutes())) {
			addErrorMessage("Validation Failed For SETMIS Latitude Minutes.Uppercase value in field may only contain characters 1234567890 . Value must have a length of exactly 2 (leading zeros) and may not be greater than 59");
			result=true;
		}
		if(!addressValidationService.validatingLatitudeSeconds(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLatitudeSeconds())) {
			addErrorMessage("Validation Failed For SETMIS Latitude Seconds.Field may not start with a space.Uppercase value in field may only contain characters 1234567890.  Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999");
			result=true;
		}
		
		
		if(!addressValidationService.validatingLongitudeDegrees(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLongitudeDegrees())) {
			addErrorMessage("Validation Failed For SETMIS Longitude Degrees /Field may not start with a space.Uppercase value in field may only contain characters 1234567890-Field must not be a negative value, may not be greater than 33 and may not have a value less than 16");
			result=true;
		}
		if(!addressValidationService.validatingLongitudeMinutes(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLongitudeMinutes())) {
			addErrorMessage("Validation Failed For SETMIS Longitude Minutes.Uppercase value in field may only contain characters 1234567890 . Value must have a length of exactly 2 (leading zeros) and may not be greater than 59");
			result=true;
		}
		if(!addressValidationService.validatingLongitudeSeconds(selectedTrainingProviderApplication.getCompany().getResidentialAddress().getLongitudeSeconds())) {
			addErrorMessage("Validation Failed For SETMIS Longitude Seconds.Field may not start with a space.Uppercase value in field may only contain characters 1234567890.  Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999");
			result=true;
		}
		
		if (selectedTrainingProviderApplication.getCompany() != null && selectedTrainingProviderApplication.getCompany().getCompanyStatus() != null) {
			System.out.println("selectedTrainingProviderApplication.getCompany()---"+selectedTrainingProviderApplication.getCompany().getCompanyStatus());
			orignalStatus  = selectedTrainingProviderApplication.getCompany().getCompanyStatus();
			
		}
		try {
			if(result==false)
			{
			getSessionUI().setValidationErrors(null);
			runClientSideUpdate("validationErrorForm");
			selectedTrainingProviderApplication.getCompany().getResidentialAddress().setSameAddress(copyAddress);
			selectedTrainingProviderApplication.getCompany().getPostalAddress().setSameAddress(copyAddress);
			sdfcompanyservice.changeDetailsNoTask(selectedTrainingProviderApplication.getCompany(), getSessionUI().getActiveUser());
			System.out.println("changeDetailsNoTask out");
			companyInfoUI.companysdfInfo();
			validiationExceptionsCompany = "";
			addInfoMessage(super.getEntryLanguage("update.successful"));
			}
			
		} catch (javax.validation.ConstraintViolationException e) {
			// fall back for SETMIS validiation fail
			if (orignalStatus == null) {
				selectedTrainingProviderApplication.getCompany().setCompanyStatus(CompanyStatusEnum.Active);
			} else {
				selectedTrainingProviderApplication.getCompany().setCompanyStatus(orignalStatus);
			}
			selectedTrainingProviderApplication.getCompany().setLocked(false);
			validiationExceptionsCompany = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message by the company information.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
		
	
	}
	
	public void updateUserApplicationData() {
		System.out.println("updateUserApplicationData.getReviewCommitteeMeeting()--");
		if(trainingProviderUser!=null) {
			try {
				usersService.update(trainingProviderUser);
				addInfoMessage(super.getEntryLanguage("update.successful"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
	}
	
	public void updateETQAData_vh() {
		System.out.println("selectedTrainingProviderApplication.getReviewCommitteeMeeting()--");
		System.out.println("selectedTrainingProviderApplication.getReviewCommitteeMeeting()--"+selectedTrainingProviderApplication.getReviewCommitteeMeeting().getMeetingNumber());
		if(selectedTrainingProviderApplication.getReviewCommitteeMeeting()!=null) {
			try {
				reviewCommitteeMeetingService.update(selectedTrainingProviderApplication.getReviewCommitteeMeeting());
				addInfoMessage(super.getEntryLanguage("update.successful"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
	}
	
	public void clearDisabilityRating() {
		try {
			trainingProviderUser.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public List<DisabilityRating> getSelectItemsDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if (trainingProviderUser.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(trainingProviderUser.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}


	
}

