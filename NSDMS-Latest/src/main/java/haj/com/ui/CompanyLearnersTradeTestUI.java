package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.CollectDetail;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.entity.Doc;
import haj.com.entity.TradeTestTaskResult;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyTradeTestEmployerService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DetailsOfExperienceArplService;
import haj.com.service.DetailsOfTrainingArplService;
import haj.com.service.DocService;
import haj.com.service.ProviderApplicationTradeTestAssessorsLinkService;
import haj.com.service.TasksService;
import haj.com.service.TradeTestTaskResultService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DisabilityRatingService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "companylearnerstradetestUI")
@ViewScoped
public class CompanyLearnersTradeTestUI extends AbstractUI {

	/* Entity Levels */
	private CompanyLearnersTradeTest companylearnerstradetest = null;
	private DetailsOfExperienceArpl detailsOfExperienceArpl = null;
	private DetailsOfTrainingArpl detailsOfTrainingArpl = null;
	private TradeTestTaskResult tradeTestTaskResult = null;
	private Doc doc = null;
	private CompanyTradeTestEmployer companyTradeTestEmployer = null;
	private Company employerInformation = null;
	private Company providerInformation = null;
	private CompanyUsers selectedCompanyUser = null;
	private UsersLanguage selectedUserLanguage = null;
	private UsersDisability selectedUserDisability = null;
	private AssessorModeratorApplication selectedAssessorModeratorApplication = null;
	
	/* Service levels */
	private CompanyLearnersTradeTestService service = new CompanyLearnersTradeTestService();
	private DetailsOfExperienceArplService detailsOfExperienceArplService = new DetailsOfExperienceArplService();
	private DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
	private TradeTestTaskResultService tradeTestTaskResultService = new TradeTestTaskResultService(); 
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
	private UsersService usersService = new UsersService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
	private ProviderApplicationTradeTestAssessorsLinkService applicationTradeTestAssessorsLinkService = new ProviderApplicationTradeTestAssessorsLinkService();
	
	/* Lazy Data Models */
	private LazyDataModel<CompanyLearnersTradeTest> dataModel;
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	private LazyDataModel<AssessorModeratorApplication> assessorListDataModel;
	private LazyDataModel<AssessorModeratorApplication> moderatorListDataModel;

	/* Array Lists */
	private List<RejectReasons> rejectionReasonsFound = new ArrayList<>();
	private List<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason = new ArrayList<>();
	private List<CompanyLearnersTradeTest> companylearnerstradetestList = null;
	private List<CompanyLearnersTradeTest> companylearnerstradetestfilteredList = null;
	private List<Users> regionUserList;
	private List<CompanyUsers> companyUserSelectionList;
	private List<UsersLanguage> userLanguageList = new ArrayList<>();
	private List<UsersDisability> userDisabilityList = new ArrayList<>();

	/* Booleans */
	private boolean canEdit = false;
	private boolean canUpload = false;
	private boolean alterApplicationForm = false;
	
	private boolean alterDateOrginalDocumentSubmission = false;
	private boolean displayDateOrginalDocumentSubmission = false;
	
	private boolean displayTestInformation = false;
	private boolean alterTestInformation = false;
	private boolean viewTestResults = false;
	private boolean displayNambInfo = false;
	private boolean editNambInfo = false;
	private boolean displayCollection = false;
	private boolean editCollectionInfo = false;
	private boolean copyAddress;
	private boolean copyAddressEmployer;
	private Boolean check;
	private Boolean viewContactDetails;
	private Boolean searchEmp;
	private Boolean doneSearch;
	private Boolean employerSearch;
	private Boolean displayEmployerInfo;
	private Boolean accept = false;
	private Boolean downloadLpmFmDoc = true;
	private boolean alterUserLanguages;
	private boolean alterUserDisability;

	/* Vars */
	private Integer rejectionIndicator = 1;
	private String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;
	private Long motorCbmtId = HAJConstants.DESIGNATED_TRADE_MOTOR_CBMT_ID;
	
	private CollectDetail collectDetail;

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
	 * Initialize method to get all CompanyLearnersTradeTest and prepare a for a
	 * create of a new CompanyLearnersTradeTest
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestApplication) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			populateTradeTestInformationTradeTest();
			populateUserLanguageAndDisability();
			if (companylearnerstradetest.getCbmtQualification() != null && companylearnerstradetest.getCbmtQualification()) {
				downloadLpmFmDoc = false;
			}else {
				downloadLpmFmDoc = true;
			}
			
			determainUserActions();
			dertmainSectionTradeTestAlteration();
			dataModelDetailsOfExperienceArplInfo();
			dataModelDetailsOfTrainingArplInfo();
			dataModelTradeTestTaskResultInfo();
			populateRejectionReasons(ConfigDocProcessEnum.TradeTestApplication);
		} else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			
			populateTradeTestInformationArpl();
			populateUserLanguageAndDisability();
			populateRejectReasons(getSessionUI().getTask().getWorkflowProcess());
			
			dertmainSectionAlteration();
			dataModelDetailsOfExperienceArplInfo();
			dataModelDetailsOfTrainingArplInfo();
			dataModelTradeTestTaskResultInfo();
			determainUserActions();
			populateRejectionReasons(ConfigDocProcessEnum.ARPLTradeTestApplication);
		} else {
			prepareNew();
			companylearnerstradetestInfo();
		}
//		searchEmp = true;
//		companyTradeTestEmployer = new CompanyTradeTestEmployer();
//		companyTradeTestEmployer.setResidentialAddress(new Address());
//		companyTradeTestEmployer.setPostalAddress(new Address());
	}

	/**
	 * @throws Exception
	 */
	public void populateTradeTestInformationTradeTest() throws Exception {
		this.companylearnerstradetest = service.findByKey(getSessionUI().getTask().getTargetKey());
		service.prepareNewRegistrationTradeTest(getSessionUI().getTask().getWorkflowProcess(), companylearnerstradetest, getSessionUI().getTask().getProcessRole());
		companylearnerstradetest = service.checkDocRequired(companylearnerstradetest, getSessionUI().getTask());
		if ((companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) || (companylearnerstradetest.getCompanyEmployer() != null)) {
			displayEmployerInfo = true;
		} else {
			displayEmployerInfo = false;
		}
		
		if (companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) {
			companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(companylearnerstradetest.getEmployer().getId());
		} else {
			companyTradeTestEmployer = new CompanyTradeTestEmployer();
			clearCompanyTradeTestEmployer();
			if (companylearnerstradetest.getCompanyEmployer() != null) {
				companyTradeTestEmployer.setCompanyName(companylearnerstradetest.getCompanyEmployer().getCompanyName());
				companyTradeTestEmployer.setTradingName(companylearnerstradetest.getCompanyEmployer().getTradingName());
				companyTradeTestEmployer.setTelNumber(companylearnerstradetest.getCompanyEmployer().getTelNumber());
				companyTradeTestEmployer.setFaxNumber(companylearnerstradetest.getCompanyEmployer().getFaxNumber());
				companyTradeTestEmployer.setEmail(companylearnerstradetest.getCompanyEmployer().getEmail());
				companyTradeTestEmployer.setResidentialAddress(companylearnerstradetest.getCompanyEmployer().getResidentialAddress());
				companyTradeTestEmployer.setPostalAddress(companylearnerstradetest.getCompanyEmployer().getPostalAddress());
				companyTradeTestEmployer.setLevyNumber(companylearnerstradetest.getCompanyEmployer().getLevyNumber());
			}
		}
		if (companylearnerstradetest.getCompanyLearners() != null) {
			// provider information
			if (companylearnerstradetest.getCompanyLearners().getCompany() != null && companylearnerstradetest.getCompanyLearners().getCompany().getId() != null) {
				providerInformation = companyService.findByKey(companylearnerstradetest.getCompanyLearners().getCompany().getId());
			}
		}
	}
	
	public void populateTradeTestInformationArpl() throws Exception {
		this.companylearnerstradetest = service.findByKey(getSessionUI().getTask().getTargetKey());
		service.prepareNewRegistrationArpl(getSessionUI().getTask().getWorkflowProcess(), companylearnerstradetest, getSessionUI().getTask().getProcessRole());
		companylearnerstradetest = service.checkDocRequired(companylearnerstradetest, getSessionUI().getTask());
		if ((companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) || (companylearnerstradetest.getCompanyEmployer() != null)) {
			displayEmployerInfo = true;
		} else {
			displayEmployerInfo = false;
		}
		
		if (companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) {
			companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(companylearnerstradetest.getEmployer().getId());
		} else {
			companyTradeTestEmployer = new CompanyTradeTestEmployer();
			clearCompanyTradeTestEmployer();
		}
	}

	private void populateUserLanguageAndDisability() throws Exception {
		alterUserDisability = false;
		alterUserLanguages = false;
		userDisabilityList = new ArrayList<>();
		userLanguageList = new ArrayList<>();
		if (companylearnerstradetest != null && companylearnerstradetest.getLearner() != null && companylearnerstradetest.getLearner().getId() != null) {
			if (usersLanguageService.countByUserId(companylearnerstradetest.getLearner().getId()) == 0) {
				alterUserLanguages = true;
				selectedUserLanguage = new UsersLanguage();
			} else {
				userLanguageList = usersLanguageService.findByUser(companylearnerstradetest.getLearner());
			}
			if (companylearnerstradetest.getLearner().getDisability() != null) {
				if (companylearnerstradetest.getLearner().getDisability() == YesNoEnum.Yes) {
					if (usersDisabilityService.countByKeyUser(companylearnerstradetest.getLearner().getId()) == 0) {
						alterUserDisability = true;
						selectedUserDisability = new UsersDisability();
					} else {
						userDisabilityList = usersDisabilityService.findByKeyUser(companylearnerstradetest.getLearner());
					}
				}
			} else {
				alterUserDisability = true;
				selectedUserDisability = new UsersDisability();
			}	
		}
	}
	
	private void failSafeUserLanguageAndDisability() {
		alterUserDisability = false;
		alterUserLanguages = false;
		try {
			if (companylearnerstradetest != null && companylearnerstradetest.getLearner() != null && companylearnerstradetest.getLearner().getId() != null) {
				if (usersLanguageService.countByUserId(companylearnerstradetest.getLearner().getId()) == 0) {
					alterUserLanguages = true;
					selectedUserLanguage = new UsersLanguage();
				} else {
					userLanguageList = usersLanguageService.findByUser(companylearnerstradetest.getLearner());
				}
				if (companylearnerstradetest.getLearner().getDisability() != null) {
					if (companylearnerstradetest.getLearner().getDisability() == YesNoEnum.Yes) {
						if (usersDisabilityService.countByKeyUser(companylearnerstradetest.getLearner().getId()) == 0) {
							alterUserDisability = true;
							selectedUserDisability = new UsersDisability();
						} else {
							userDisabilityList = usersDisabilityService.findByKeyUser(companylearnerstradetest.getLearner());
						}
					}
				} else {
					alterUserDisability = true;
					selectedUserDisability = new UsersDisability();
				}	
			}
		} catch (Exception e) {
			alterUserDisability = false;
			alterUserLanguages = false;
			userDisabilityList.clear();
			userLanguageList.clear();
		}
		
	}

	private void populateRejectionReasons(ConfigDocProcessEnum configDocEnum) throws Exception{
		RejectReasonsService rs = new RejectReasonsService();
		rejectionReasonsFound = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnerstradetest.getId(), companylearnerstradetest.getClass().getName(), configDocEnum);
	}

	/**
	 * @throws Exception
	 */
	public void determainUserActions() throws Exception {
		canEdit = TasksService.instance().canEditBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
		canUpload = TasksService.instance().canUploadBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
	}

	private void dertmainSectionTradeTestAlteration() throws Exception {
		switch (companylearnerstradetest.getTradeTestProgress()) {
		case WithInitiator:
			alterApplicationForm = true;
			break;
		// with admin
		case WithMersetaOne:
			break;
		// with test center
		case WithTestCenter:
			displayTestInformation = true;
			alterTestInformation = true;
			determainTestAlteration();
			break;
		// with Coordinator
		case WithMersetaTwo:
			viewTestResults = true;
			displayTestInformation = true;
			break;
		// with admin
		case WithMersetaThree:
			viewTestResults = true;
			displayTestInformation = true;
			editNambInfo = true;
			displayNambInfo = true;
			break;
		default:
			break;
		}
//		if (companylearnerstradetest.getTradeTestProgress() == TradeTestProgressEnum.WithInitiator) {
//			alterApplicationForm = true;
//		}
//		if (companylearnerstradetest.getTradeTestProgress() == TradeTestProgressEnum.WithTestCenter) {
//			displayTestInformation = true;
//			alterTestInformation = true;
//		}
//		if (companylearnerstradetest.getTradeTestProgress() == TradeTestProgressEnum.WithMersetaTwo || companylearnerstradetest.getTradeTestProgress() == TradeTestProgressEnum.WithMersetaThree) {
//			displayTestInformation = true;
//		}
	}

	private void dertmainSectionAlteration() throws Exception {
		switch (companylearnerstradetest.getAprlProgress()) {
		case WithInitiatorOne:
			alterApplicationForm = true;
			break;
		case WithMersetaOne:
			break;
		case WithInitiatorTwo:
			alterDateOrginalDocumentSubmission = true;
			displayDateOrginalDocumentSubmission = true;
			break;
		case WithMersetaTwo:
			displayDateOrginalDocumentSubmission = true;
			break;
		case WithMersetaThree:	
			displayDateOrginalDocumentSubmission = true;
			break;
		case WithMersetaFour:
			displayDateOrginalDocumentSubmission = true;
			break;
		case WithTestCenterOne:
			displayTestInformation = true;
			alterTestInformation = true;
			determainTestAlteration();
			break;
		case WithMersetaFive:
			viewTestResults = true;
			alterDateOrginalDocumentSubmission = true;
			displayTestInformation = true;
			break;
		case WithMersetaSix:
			viewTestResults = true;
			alterDateOrginalDocumentSubmission = true;
			displayTestInformation = true;
			editNambInfo = true;
			displayNambInfo = true;
			break;
		default:
			break;
		}
		
		/* old version
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithInitiatorTwo || companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithMersetaTwo) {
			alterDateOrginalDocumentSubmission = true;
			displayDateOrginalDocumentSubmission = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithTestCenterOne) {
			displayTestInformation = true;
			alterTestInformation = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithMersetaFive || companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithMersetaSix) {
			displayTestInformation = true;
			displayDateOrginalDocumentSubmission = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithMersetaFour || companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithInitiatorTwo) {
			displayNambInfo = true;
			displayDateOrginalDocumentSubmission = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithMersetaSix) {
			editNambInfo = true;
			displayDateOrginalDocumentSubmission = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithInitiatorOne) {
			alterApplicationForm = true;
		}
		if (companylearnerstradetest.getAprlProgress() == AprlProgressEnum.WithInitiatorTwo) {
			displayCollection = true;
			if (companylearnerstradetest.getStatus() != ApprovalEnum.Approved) {
				editCollectionInfo = true;
			}
		}
		*/
	}

	private void determainTestAlteration() throws Exception {
		viewTestResults = false;
		if (companylearnerstradetest != null && companylearnerstradetest.getTestDatesProvided() != null && companylearnerstradetest.getTestDatesProvided()) {
			Date today = GenericUtility.getStartOfDay(new Date());
			if (companylearnerstradetest.getDateOfTest() != null && (today.equals(GenericUtility.getStartOfDay(companylearnerstradetest.getDateOfTest())) || today.after(GenericUtility.getStartOfDay(companylearnerstradetest.getDateOfTest())) )) {
				viewTestResults = true;
			}
		}
	}

	public void populateRejectReasons(ConfigDocProcessEnum configDocProcessEnum) {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companylearnerstradetest.getId(), companylearnerstradetest.getClass().getName(), configDocProcessEnum);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/*
	 * public List<RejectReasons> getRejectReasons(ConfigDocProcessEnum
	 * configDocProcessEnum) { RejectReasonsService rejectReasonsService = new
	 * RejectReasonsService(); List<RejectReasons> l = null; try { l =
	 * rejectReasonsService.findByProcess(configDocProcessEnum); } catch (Exception
	 * e) { addErrorMessage(e.getMessage(), e); } return l; }
	 */

	/**
	 * Get all CompanyLearnersTradeTest for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 */
	public void companylearnerstradetestInfo() {
		dataModel = new LazyDataModel<CompanyLearnersTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTradeTest> retorno = new ArrayList<CompanyLearnersTradeTest>();

			@Override
			public List<CompanyLearnersTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allCompanyLearnersTradeTest(CompanyLearnersTradeTest.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyLearnersTradeTest.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersTradeTest obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersTradeTest getRowData(String rowKey) {
				for (CompanyLearnersTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert CompanyLearnersTradeTest into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 */
	public void companylearnerstradetestInsert() {
		try {
			service.create(this.companylearnerstradetest);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerstradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearnersTradeTest in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 */
	public void companylearnerstradetestUpdate() {
		try {
			service.update(this.companylearnerstradetest);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnerstradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearnersTradeTest from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 */
	public void companylearnerstradetestDelete() {
		try {
			service.delete(this.companylearnerstradetest);
			prepareNew();
			companylearnerstradetestInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearnersTradeTest
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTradeTest
	 */
	public void prepareNew() {
		companylearnerstradetest = new CompanyLearnersTradeTest();
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(companylearnerstradetest.getId());
				doc.setTargetClass(CompanyLearnersTradeTest.class.getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<CompanyLearnersTradeTest> complete(String desc) {
		List<CompanyLearnersTradeTest> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void prepAssessorSelectionList(){
		try {
			companyUserSelectionList = companyUsersService.findTPAssessorModByModTypeList(companylearnerstradetest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP, AssessorModType.getAssessorValues());
			if (companyUserSelectionList.isEmpty()) {
				throw new Exception("No Assessors Assigned To Test Centre. Please Assign Assessors Before Proceeding.");
			} else {
				runClientSideExecute("PF('dlgAssessorAssign').hide()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepModeratorSelectionList(){
		try {
			companyUserSelectionList = companyUsersService.findTPAssessorModByModTypeList(companylearnerstradetest.getPreferredTrainingCenter().getId(), ConfigDocProcessEnum.TP, AssessorModType.getModeratorValues());
			if (companyUserSelectionList.isEmpty()) {
				throw new Exception("No Moderators Assigned To Test Centre. Please Assign Moderators Before Proceeding.");
			}else {
				runClientSideExecute("PF('dlgModeratorAssign').hide()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectCompanyUserAssessor() 
	{
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// assessorModeratorApplicationList selectedCompanyUser selectedAssessorModeratorApplication assessorModeratorApplicationService

	public void completeWorkflow() {
		try {
			service.completeWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recommendWorkflow() {
		try {
			service.recommendWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			} else {
				service.rejectWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				ajaxRedirectToDashboard();
			}
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	 * Disability Start
	 */
	public void prepNewDisabilityOnSelection(){
		try {
			if (companylearnerstradetest.getLearner().getDisability() != null && companylearnerstradetest.getLearner().getDisability() == YesNoEnum.Yes) {
				selectedUserDisability = new UsersDisability();
				userDisabilityList.clear();
			} else {
				selectedUserDisability = null;
				userDisabilityList.clear();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public List<DisabilityRating> getSelectItemsUsersDisabilityRatingNew() {
		List<DisabilityRating> l = null;
		try {
			if (this.selectedUserDisability.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.selectedUserDisability.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void addDisabilityToList(){
		try {
			StringBuilder validiationError = new StringBuilder();
			
			if (selectedUserDisability.getDisabilityStatus() == null) {
				validiationError.append("Provide Disability. ");
			}
			
			if (selectedUserDisability.getDisabilityRating() == null) {
				validiationError.append("Provide Disability Rating. ");
			}
			
			if (validiationError.toString().isEmpty()) {
				boolean disabilityInList = false;
				for (UsersDisability inlist : userDisabilityList) {
					if (inlist.getDisabilityStatus().getId().equals(selectedUserDisability.getDisabilityStatus().getId())) {
						disabilityInList = true;
						break;
					}
				}
				if (!disabilityInList) {
					userDisabilityList.add(selectedUserDisability);
					selectedUserDisability = new UsersDisability();
					addInfoMessage("Action Complete");
				} else {
					addErrorMessage("Disability has already been added. Please select a different disability.");
				}
			} else {
				addErrorMessage("Unable to submit disability. Please attened to the following before subimtting: "+validiationError.toString());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeDisability() {
		try {
			userDisabilityList.remove(selectedUserDisability);
			if (userDisabilityList.isEmpty()) {
				userDisabilityList.clear();
			}
			selectedUserDisability = new UsersDisability();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	 * Language Start
	 */
	public void addUserLanguageToList(){
		try {
			StringBuilder validiationError = new StringBuilder();
			
			if (selectedUserLanguage.getLanguage() == null) {
				validiationError.append("Provide language. ");
			}
			
			if (selectedUserLanguage.getSpeark() == null) {
				validiationError.append("Provide if the learner can speak the language. ");
			}
			
			if (selectedUserLanguage.getRead() == null) {
				validiationError.append("Provide if the learner can read the language. ");
			}
			
			if (selectedUserLanguage.getWrite() == null) {
				validiationError.append("Provide if the learner can write the language. ");
			}
			
			if (selectedUserLanguage.getHomeLanguage() == null) {
				validiationError.append("Provide if it's a home language for the learner. ");
			}
			
			if (validiationError.toString().isEmpty()) {
				boolean languageInList = false;
				for (UsersLanguage inlist : userLanguageList) {
					if (inlist.getLanguage().getId().equals(selectedUserLanguage.getLanguage().getId())) {
						languageInList = true;
						break;
					}
				}
				if (!languageInList) {
					
					boolean homeLanguageError = false;
					if (selectedUserLanguage.getHomeLanguage()) {
						for (UsersLanguage entry : userLanguageList) {
							if (entry.getHomeLanguage()) {
								homeLanguageError = true;
								break;
							}
						}
					}
					
					if (homeLanguageError) {
						addErrorMessage("SETMIS Validiation Error: A home language has already been added. You can only have one home language assigned to the learner.");
					} else {
						userLanguageList.add(selectedUserLanguage);
						selectedUserLanguage = new UsersLanguage();
						addInfoMessage("Action Complete");
					}
				} else {
					addErrorMessage("Language has already been added. Please select a different language.");
				}
			} else {
				addErrorMessage("Unable to submit learner language. Please attened to the following before subimtting: "+validiationError.toString());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeUserLanguage() {
		try {
			userLanguageList.remove(selectedUserLanguage);
			if (userLanguageList.isEmpty()) {
				userLanguageList.clear();
			}
			selectedUserLanguage = new UsersLanguage();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	 * Assign Assessor Start
	 */
	public void clearAssignedAssessor(){
		try {
			companylearnerstradetest.setAssessorApplication(null);
			companyLearnersTradeTestService.update(companylearnerstradetest);	
			if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestApplication) {
				populateTradeTestInformationTradeTest();
			} else if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
				populateTradeTestInformationArpl();
			}
			addWarningMessage("Assessor Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepAssignNewAssessor(){
		try {
			// assignAssessorForm
			assessorListDataModelInfo();
			runClientSideExecute("PF('dlgAssignAssessor').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void assessorListDataModelInfo() {
		assessorListDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					
					if (applicationTradeTestAssessorsLinkService == null) {
						applicationTradeTestAssessorsLinkService = new ProviderApplicationTradeTestAssessorsLinkService();
					}
					retorno = applicationTradeTestAssessorsLinkService.findAssessorModeratorTtcByQualificationApprovedAndProviderApplication(first, pageSize, sortField, sortOrder, filters, 
							companylearnerstradetest.getTrainingProviderApplication().getId(), AssessorModeratorAppTypeEnum.getAssessorValuesTTC(), companylearnerstradetest.getQualification().getId());
					assessorListDataModel.setRowCount(applicationTradeTestAssessorsLinkService.countAssessorModeratorTTCByQualificationApprovedAndProviderApplication(filters, AssessorModeratorAppTypeEnum.getAssessorValuesTTC()));
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void assesignNewAssessor(){
		try {
			companyLearnersTradeTestService.assignNewAssessorModeratorToTradeTest(companylearnerstradetest, selectedAssessorModeratorApplication, true);
			selectedAssessorModeratorApplication = null;
			if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestApplication) {
				populateTradeTestInformationTradeTest();
			} else if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
				populateTradeTestInformationArpl();
			}
			runClientSideExecute("PF('dlgAssignAssessor').hide()");
			addInfoMessage("Assessor Assigned");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	/*
	 * Assign Moderator Start
	 */
	public void clearAssignedModerator(){
		try {
			companylearnerstradetest.setModeratorApplication(null);
			companyLearnersTradeTestService.update(companylearnerstradetest);
			if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestApplication) {
				populateTradeTestInformationTradeTest();
			} else if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
				populateTradeTestInformationArpl();
			}
			addWarningMessage("Moderator Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepAssignNewModerator(){
		try {
			// assignModeratorForm
			selectedAssessorModeratorApplication = null;
			moderatorListDataModelInfo();
			runClientSideExecute("PF('dlgAssignModerator').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void moderatorListDataModelInfo() {
		moderatorListDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (applicationTradeTestAssessorsLinkService == null) {
						applicationTradeTestAssessorsLinkService = new ProviderApplicationTradeTestAssessorsLinkService();
					}
					retorno = applicationTradeTestAssessorsLinkService.findAssessorModeratorTtcByQualificationApprovedAndProviderApplication(first, pageSize, sortField, sortOrder, filters, 
							companylearnerstradetest.getTrainingProviderApplication().getId(), AssessorModeratorAppTypeEnum.getModeratorValuesTTC(), companylearnerstradetest.getQualification().getId());
					moderatorListDataModel.setRowCount(applicationTradeTestAssessorsLinkService.countAssessorModeratorTTCByQualificationApprovedAndProviderApplication(filters, AssessorModeratorAppTypeEnum.getModeratorValuesTTC()));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void assesignNewModerator(){
		try {
			companyLearnersTradeTestService.assignNewAssessorModeratorToTradeTest(companylearnerstradetest, selectedAssessorModeratorApplication, false);
			selectedAssessorModeratorApplication = null;
			if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.TradeTestApplication) {
				populateTradeTestInformationTradeTest();
			} else if (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ARPLTradeTestApplication) {
				populateTradeTestInformationArpl();
			}
			runClientSideExecute("PF('dlgAssignModerator').hide()");
			addInfoMessage("Moderator Assigned");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	

	/*
	 * 
	 * ARPL START
	 * 
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(companylearnerstradetest.getLearner().getPostalAddress());
		}
	}
	
	public void clearPostalEmployer() {
		if (!copyAddressEmployer) {
			AddressService.instance().clearAddress(companyTradeTestEmployer.getPostalAddress());
		}
	}

	public void dataModelDetailsOfExperienceArplInfo() {
		dataModelDetailsOfExperienceArpl = new LazyDataModel<DetailsOfExperienceArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfExperienceArpl> retorno = new ArrayList<DetailsOfExperienceArpl>();

			@Override
			public List<DetailsOfExperienceArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfExperienceArplService.allDetailsOfExperienceArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, companylearnerstradetest);
					dataModelDetailsOfExperienceArpl.setRowCount(detailsOfExperienceArplService.countAllDetailsOfExperienceArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DetailsOfExperienceArpl obj) {
				return obj.getId();
			}

			@Override
			public DetailsOfExperienceArpl getRowData(String rowKey) {
				for (DetailsOfExperienceArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void dataModelDetailsOfTrainingArplInfo() {
		dataModelDetailsOfTrainingArpl = new LazyDataModel<DetailsOfTrainingArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfTrainingArpl> retorno = new ArrayList<DetailsOfTrainingArpl>();

			@Override
			public List<DetailsOfTrainingArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfTrainingArplService.allDetailsOfTrainingArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, companylearnerstradetest);
					dataModelDetailsOfTrainingArpl.setRowCount(detailsOfTrainingArplService.countAllDetailsOfTrainingArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DetailsOfTrainingArpl obj) {
				return obj.getId();
			}

			@Override
			public DetailsOfTrainingArpl getRowData(String rowKey) {
				for (DetailsOfTrainingArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void dataModelTradeTestTaskResultInfo() {
		dataModelTradeTestTaskResult = new LazyDataModel<TradeTestTaskResult>() {
			private static final long serialVersionUID = 1L;
			private List<TradeTestTaskResult> retorno = new ArrayList<TradeTestTaskResult>();

			@Override
			public List<TradeTestTaskResult> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tradeTestTaskResultService.allTradeTestTaskResultByTradeTestId(first, pageSize, sortField, sortOrder, filters, companylearnerstradetest);
					dataModelTradeTestTaskResult.setRowCount(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TradeTestTaskResult obj) {
				return obj.getId();
			}

			@Override
			public TradeTestTaskResult getRowData(String rowKey) {
				for (TradeTestTaskResult obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void updatelearnerInformation(){
		try {
			companyLearnersTradeTestService.updateLearnerInformation(companylearnerstradetest.getLearner(), copyAddress);
			companylearnerstradetest.setLearner(usersService.findByKey(companylearnerstradetest.getLearner().getId()));
			usersService.resolveUserAddresses(companylearnerstradetest.getLearner());
			usersService.identifyFieldAlteration(companylearnerstradetest.getLearner());
			addInfoMessage("Update Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearDisabilityRating() {
		try {
			companylearnerstradetest.setDisabilityRating(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public List<DisabilityRating> getSelectItemsUsersDisabilityRating() {
		DisabilityRatingService disabilityRatingService = new DisabilityRatingService();
		List<DisabilityRating> l = null;
		try {
			if (this.companylearnerstradetest.getDisabilityStatus() != null) {
				l = disabilityRatingService.findByDisability(this.companylearnerstradetest.getDisabilityStatus().getId());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void prepNewTradeTestTaskResult() {
		try {
			tradeTestTaskResult = new TradeTestTaskResult();
			tradeTestTaskResult.setCompanyLearnersTradeTest(companylearnerstradetest);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepNewDetailsOfExperienceArpl() {
		try {
			detailsOfExperienceArpl = new DetailsOfExperienceArpl();
			detailsOfExperienceArpl.setCompanyLearnersTradeTest(companylearnerstradetest);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepNewDetailsOfTrainingArpl() {
		try {
			detailsOfTrainingArpl = new DetailsOfTrainingArpl();
			detailsOfTrainingArpl.setCompanyLearnersTradeTest(companylearnerstradetest);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createNewTradeTestTaskResult() {
		try {
			tradeTestTaskResultService.create(tradeTestTaskResult);
			addInfoMessage("Entry Added");
			dataModelTradeTestTaskResultInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cancelNewTradeTestTaskResult() {
		try {
			tradeTestTaskResult = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createNewDetailsOfExperienceArpl() {
		try {
			detailsOfExperienceArplService.create(detailsOfExperienceArpl);
			detailsOfExperienceArpl = null;
			addInfoMessage("Entry Added");
			runClientSideExecute("PF('dlgNewExp').hide()");
			dataModelDetailsOfExperienceArplInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cancelNewDetailsOfExperienceArpl() {
		try {
			detailsOfExperienceArpl = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createNewDetailsOfTrainingArpl() {
		try {
			detailsOfTrainingArplService.create(detailsOfTrainingArpl);
			detailsOfTrainingArpl = null;
			addInfoMessage("Entry Added");
			dataModelDetailsOfTrainingArplInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cancelNewDetailsOfTrainingArpl() {
		try {
			detailsOfTrainingArpl = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteDetailsOfExperienceArpl() {
		try {
			detailsOfExperienceArplService.delete(detailsOfExperienceArpl);
			addWarningMessage("Entry Removed");
			dataModelDetailsOfExperienceArplInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteDetailsOfTrainingArpl() {
		try {
			detailsOfTrainingArplService.delete(detailsOfTrainingArpl);
			addInfoMessage("Entry Removed");
			dataModelDetailsOfTrainingArplInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteTradeTestTaskResult() {
		try {
			tradeTestTaskResultService.delete(tradeTestTaskResult);
			addInfoMessage("Entry Removed");
			dataModelTradeTestTaskResultInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Complete Task: WithInitiator */
	public void completeWorkflowWithInitiatorOne() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				
				
				// language check
				userLanguageValidiations();
				System.out.println("RUN userLanguageValidiations");
				
				// disability check
				userDisabilityValidiations();
				System.out.println("RUN userDisabilityValidiations");
				
				service.createUpdateArpl(companylearnerstradetest, companyTradeTestEmployer, copyAddress, copyAddressEmployer);
				System.out.println("RUN createUpdateArpl");
				regionCheck();
				if (!check) {
					service.completeWorkflowWithInitiatorOne(companylearnerstradetest, companyTradeTestEmployer, getSessionUI().getActiveUser(), getSessionUI().getTask(), copyAddress, copyAddressEmployer,  alterUserLanguages, userLanguageList, alterUserDisability, userDisabilityList);
					getSessionUI().setTask(null);
					ajaxRedirectToDashboard();
				} else {
					viewContactDetails = false;
					super.runClientSideExecute("PF('NoRegionDlg').show()");
				}
			} else {
				service.createUpdateArpl(companylearnerstradetest, companyTradeTestEmployer, copyAddress, copyAddressEmployer);
				service.completeWorkflowWithInitiatorOneTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (ValidationException e) {
			failSafeUserLanguageAndDisability();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			failSafeUserLanguageAndDisability();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void userLanguageValidiations() throws Exception {
		if (alterUserLanguages) {
			StringBuilder errors = new StringBuilder();
			if (userLanguageList.isEmpty()) {
				errors.append("Provide a minium of one language for the learner before proceeding.");
			} else {
				boolean containsHomeLanguage = false;
				for (UsersLanguage lang : userLanguageList) {
					if (lang.getHomeLanguage() != null && lang.getHomeLanguage()) {
						containsHomeLanguage = true;
						break;
					}
				}
				if (!containsHomeLanguage) {
					errors.append("Provide a minium of one home language for the learner before proceeding.");
				}
			}
			if (!errors.toString().isEmpty()) {
				throw new Exception("Learner Language Error: " + errors.toString());
			}
		}
	}
	
	private void userDisabilityValidiations() throws Exception{
		if (alterUserDisability) {
			StringBuilder errors = new StringBuilder();
			if (companylearnerstradetest.getLearner().getDisability() != null) {
				if (companylearnerstradetest.getLearner().getDisability() == YesNoEnum.Yes && userDisabilityList.isEmpty()) {
					errors.append("Provide a minium of one disbility for the learner before proceeding. Indicated on learner they have a disability. ");
				}
			} else {
				errors.append("Provide if the learner has a disablity. ");
			}
			if (!errors.toString().isEmpty()) {
				throw new Exception("Learner Disability Error: " + errors.toString());
			}
		}
	}

	/* Prep Reject Task: WithMersetaOne */
	public void prepRejectionWithWithMersetaOne() {
		rejectionIndicator = 1;
	}

	/* Reject Task: WithMersetaOne */
	public void rejectWorkflowWithWithMersetaOne() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				if (companylearnerstradetest.getClientServiceAdminUser() == null) {
					companylearnerstradetest.setClientServiceAdminUser(getSessionUI().getActiveUser());
				}
				service.rejectWorkflowWithWithMersetaOne(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			} else {
				service.rejectWorkflowWithWithMersetaOneTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Complete Task: WithMersetaOne */
	public void completeWorkflowWithWithMersetaOne() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				if (companylearnerstradetest.getClientServiceAdminUser() == null) {
					companylearnerstradetest.setClientServiceAdminUser(getSessionUI().getActiveUser());
				}
				service.completeWorkflowWithWithMersetaOne(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
				super.runClientSideExecute("uploadDone()");
			} else {
				service.completeWorkflowWithWithMersetaOneTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
				super.runClientSideExecute("uploadDone()");
			}
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void regionCheck() {
		try {
			check = true;
			regionUserList = new ArrayList<>();
			if (getSessionUI().getTask().getProcessRole().getNextTaskRole() != null) {
				// if initiator is the learner as well
				if (companylearnerstradetest.getLearner().getId().equals(companylearnerstradetest.getCreateUser().getId())) {
					regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
				} else if (companylearnerstradetest.getCompanyEmployer() != null && companylearnerstradetest.getCompanyEmployer().getId() != null) {
					// Employer Address
					regionUserList = companyLearnersTradeTestService.findRegionJobTitle(companylearnerstradetest.getCompanyEmployer(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
				} else {
					// fail safe: learner address
					regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
				}
				if (!regionUserList.isEmpty()) {
					check = false;
				} else {
					check = true;
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void regionCheckQualityAssuror()  throws Exception{
		if (companylearnerstradetest.getQualityAssurorUser() == null) {
			regionUserList = new ArrayList<>();
			// if initiator is the learner as well
			if (companylearnerstradetest.getLearner().getId().equals(companylearnerstradetest.getCreateUser().getId())) {
				regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			} else if (companylearnerstradetest.getCompanyEmployer() != null && companylearnerstradetest.getCompanyEmployer().getId() != null) {
				// Employer Address
				regionUserList = companyLearnersTradeTestService.findRegionJobTitle(companylearnerstradetest.getCompanyEmployer(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			} else {
				// fail safe: learner address
				regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			}
			if (regionUserList.size() == 0) {
				throw new Exception("Unable to locate Quality Assuror For Region, contact support!");
			}
		}
	}
	
	private void regionCheckCoordinator()  throws Exception{
		if (companylearnerstradetest.getCoordinatorUser() == null) {
			regionUserList = new ArrayList<>();
			// if initiator is the learner as well
			if (companylearnerstradetest.getLearner().getId().equals(companylearnerstradetest.getCreateUser().getId())) {
				regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			} else if (companylearnerstradetest.getCompanyEmployer() != null && companylearnerstradetest.getCompanyEmployer().getId() != null) {
				// Employer Address
				regionUserList = companyLearnersTradeTestService.findRegionJobTitle(companylearnerstradetest.getCompanyEmployer(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			} else {
				// fail safe: learner address
				regionUserList = companyLearnersTradeTestService.findRegionJobTitleByAddress(companylearnerstradetest.getLearner().getResidentialAddress(), getSessionUI().getTask().getProcessRole().getNextTaskRole().getId());
			}
			if (regionUserList.size() == 0) {
				throw new Exception("Unable to locate Coordinator For Region, contact support!");
			}
		}
	}
	
	/* Complete Task: WithInitiatorTwo */
	public void completeWithInitiatorTwo() {
		try {
			service.completeTaskWithInitiatorTwo(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Prep Reject Task: WithMersetaTwo */
	public void prepRejectionWithWithMersetaTwo() {
		rejectionIndicator = 2;
	}

	/* Reject Task: WithMersetaTwo */
	public void rejectApplicationWithWithMersetaTwo() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				service.rejectApplicationWithWithMersetaTwo(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			} else {
				service.rejectApplicationWithWithMersetaTwoTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Approve Task: WithMersetaTwo */
	public void approveApplicationWithWithMersetaTwo() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				service.approveApplicationWithWithMersetaTwo(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			} 
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/* Approve Task: WithMersetaTwo Trade Test */
	public void completeTradeTestActionWithSignoff() {
		try {
			if (accept == null || !accept) {
				throw new Exception("Sign Off Before Proceeding");
			}
			service.approveApplicationWithWithMersetaTwoTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void completeCollectCertificates() {
		try {
			service.completeCollectCertificates(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(),collectDetail);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void setTestDates() {
		try {
			service.setTestDates(companylearnerstradetest, getSessionUI().getActiveUser());
			determainTestAlteration();
			addInfoMessage("Test Dates Set, Learner Has Been Notified");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setTestDatesTradeTest() {
		try {
			service.setTestDates(companylearnerstradetest, getSessionUI().getActiveUser());
			determainTestAlteration();
			addInfoMessage("Test Dates Set, Learner Has Been Notified");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void amendTestDates() {
		try {
			service.amendTestDates(companylearnerstradetest, getSessionUI().getActiveUser());
			determainTestAlteration();
			addInfoMessage("Test Dates Amended");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void amendTestDatesTradeTest() {
		try {
			service.amendTestDates(companylearnerstradetest, getSessionUI().getActiveUser());
			determainTestAlteration();
			addInfoMessage("Test Dates Amended");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Complete Task: WithTestCenter */
	public void completeWorkflowWithTestCenter() {
		try {
			if (companylearnerstradetest.getTradeTestType() == TradeTestTypeEnum.ARPL) {
				regionCheckCoordinator();
				service.completeWorkflowWithTestCenter(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
				super.runClientSideExecute("uploadDone()");
			} else {
				service.completeWorkflowWithTestCenterTradeTest(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
				super.runClientSideExecute("uploadDone()");
			}
		} catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Prep Reject Task: WithMersetaThree */
	public void prepRejectionWithWithMersetaThree() {
		rejectionIndicator = 3;
	}

	/* Reject Task: WithMersetaThree */
	public void rejectWorkflowWithMersetaThree() {
		try {
			service.rejectWorkflowWithMersetaThree(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Complete Task: WithMersetaThree */
	public void completeWorkflowWithMersetaThree() {
		try {
			regionCheckQualityAssuror();
			service.completeWorkflowWithMersetaThree(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Prep Reject Task: WithMersetaFour */
	public void prepRejectionWithMersetaFour() {
		rejectionIndicator = 4;
	}

	/* Prep Allication Reject Task: WithMersetaFour */
	public void prepApplicationRejectionWithMersetaFour() {
		rejectionIndicator = 5;
	}
	
	/* Complete Task: WithMersetaFour */
	public void completeWorkflowWithMersetaFour() {
		try {
			if (companylearnerstradetest.getQualityAssurorUser() == null) {
				companylearnerstradetest.setQualityAssurorUser(getSessionUI().getActiveUser());
			}
			service.completeWorkflowWithMersetaFour(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/* Reject Task: WithMersetaFour */
	public void rejectWorkflowWithMersetaFour() {
		try {
			if (companylearnerstradetest.getQualityAssurorUser() == null) {
				companylearnerstradetest.setQualityAssurorUser(getSessionUI().getActiveUser());
			}
			service.rejectWorkflowWithMersetaFour(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Application Reject Task: WithMersetaFour */
	public void rejectApplicationWorkflowWithMersetaFour() {
		try {
			if (companylearnerstradetest.getQualityAssurorUser() == null) {
				companylearnerstradetest.setQualityAssurorUser(getSessionUI().getActiveUser());
			}
			service.rejectApplicationWithWithMersetaFour(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Complete Task: WithInitiatorTwo */
	public void completeWorkflowWithWithInitiatorTwo() {
		try {
			service.completeWorkflowWithWithInitiatorTwo(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Complete Task: WithMersetaFive */
	public void completeWorkflowWithMersetaFive() {
		try {
			if (accept == null || !accept) {
				throw new Exception("Sign Off Before Proceeding");
			}
			if (companylearnerstradetest.getCoordinatorUser() == null) {
				companylearnerstradetest.setCoordinatorUser(getSessionUI().getActiveUser());
			}
			switch (companylearnerstradetest.getCompetenceEnum()) {
			case Competent:
				service.completeWorkflowWithMersetaFiveCompetent(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				break;
			case NotYetCompetent:
				service.completeWorkflowWithMersetaFiveNotCompetent(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				break;
			case AbsentCancelled:
				service.completeWorkflowWithMersetaFiveAbsent(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
				break;
			default:
				break;
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Prep Allocation Reject Task: WithMersetaFive */
	public void prepApplicationRejectionWithMersetaFive() {
		rejectionIndicator = 6;
	}
	
	/* Reject Task: WithMersetaFive */
	public void rejectWorkflowWithMersetaFive() {
		try {
			if (companylearnerstradetest.getCoordinatorUser() == null) {
				companylearnerstradetest.setCoordinatorUser(getSessionUI().getActiveUser());
			}
			service.rejectWorkflowWithMersetaFive(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/* Complete Task: WithMersetaSix */
	public void completeWorkflowWithMersetaSix() {
		try {
			if (companylearnerstradetest.getAdminUser() == null) {
				companylearnerstradetest.setAdminUser(getSessionUI().getActiveUser());
			}
			service.completeWorkflowWithWithMersetaSix(companylearnerstradetest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadLPMFM008() {
		try {
			companylearnerstradetest.setEmployer(companyTradeTestEmployer);
			service.downloadLPMFM008(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadLPMFM009() {
		try {
			companylearnerstradetest.setEmployer(companyTradeTestEmployer);
			service.downloadLPMFM009(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadLPMFM010() {
		try {
			companylearnerstradetest.setEmployer(companyTradeTestEmployer);
			service.downloadLPMFM010(companylearnerstradetest, getSessionUI().getActiveUser(), tradeTestTaskResultService.findByCompanyLearnersTradeTestId(companylearnerstradetest));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadLPMFM007(){
		try {
			service.downloadLPMFM007(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadCbmtGeneric(){
		try {
			service.downloadCompanyLearnersTradeTestDesignatedTrade(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadCompanyLearnersTradeTest() throws Exception {
		try {
			service.downloadCompanyLearnersTradeTest(companylearnerstradetest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void contactSupport() {
		try {
			companyLearnersTradeTestService.contactSupport(companylearnerstradetest, getSessionUI().getActiveUser());
			addInfoMessage("An Email has been sent to Support");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepEmployedUser(){
		try {
			if (companylearnerstradetest.getEmployerOnNsdms() == YesNoEnum.Yes) {
				employerSearch = true;
				displayEmployerInfo = false;
				if (companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) {
					companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(companylearnerstradetest.getEmployer().getId());
				} else {
					companyTradeTestEmployer = new CompanyTradeTestEmployer();
				}
			} else {
				employerSearch = false;
				displayEmployerInfo = true;
				companylearnerstradetest.setCompanyEmployer(null);
				if (companylearnerstradetest.getEmployer() != null && companylearnerstradetest.getEmployer().getId() != null) {
					companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(companylearnerstradetest.getEmployer().getId());
				} else {
					companyTradeTestEmployer = new CompanyTradeTestEmployer();
				}
			}
			clearCompanyTradeTestEmployer();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void clearCompanyTradeTestEmployer() throws Exception{
		companyTradeTestEmployer.setCompanyName(null);
		companyTradeTestEmployer.setTradingName(null);
		companyTradeTestEmployer.setTelNumber(null);
		companyTradeTestEmployer.setFaxNumber(null);
		companyTradeTestEmployer.setEmail(null);
		companyTradeTestEmployer.setResidentialAddress(new Address());
		companyTradeTestEmployer.setRegisteredAddress(new Address());
		companyTradeTestEmployer.setPostalAddress(new Address());
		companyTradeTestEmployer.setLevyNumber(null);
		companyTradeTestEmployer.setUserFirstName(null);
		companyTradeTestEmployer.setUserMiddleName(null);
		companyTradeTestEmployer.setUserLastName(null);
		companyTradeTestEmployer.setUserEmail(null);
		companyTradeTestEmployer.setUserTelNumber(null);
		companyTradeTestEmployer.setUserCellNumber(null);
	}

	public void searchEmployer() {
		try {
			companyTradeTestEmployer.setCompany(companylearnerstradetest.getCompanyEmployer());
			displayEmployerInfo = true;
			companyTradeTestEmployer.setCompanyName(companyTradeTestEmployer.getCompany().getCompanyName());
			companyTradeTestEmployer.setTradingName(companyTradeTestEmployer.getCompany().getTradingName());
			companyTradeTestEmployer.setTelNumber(companyTradeTestEmployer.getCompany().getTelNumber());
			companyTradeTestEmployer.setFaxNumber(companyTradeTestEmployer.getCompany().getFaxNumber());
			companyTradeTestEmployer.setEmail(companyTradeTestEmployer.getCompany().getEmail());
			companyTradeTestEmployer.setResidentialAddress(companyTradeTestEmployer.getCompany().getResidentialAddress());
			companyTradeTestEmployer.setPostalAddress(companyTradeTestEmployer.getCompany().getPostalAddress());
			companyTradeTestEmployer.setLevyNumber(companyTradeTestEmployer.getLevyNumber());
//			doneSearch = true;
//			runClientSideUpdate("empInfoPanel");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepNewCompanyTradeTestEmployer() {
		try {
			companyTradeTestEmployer = new CompanyTradeTestEmployer();
			companyTradeTestEmployer.setRegisteredAddress(new Address());
			companyTradeTestEmployer.setPostalAddress(new Address());
			runClientSideUpdate("empInfoPanel");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * ARPL END
	 * 
	 */

	/* getters and setters */
	public List<CompanyLearnersTradeTest> getCompanyLearnersTradeTestList() {
		return companylearnerstradetestList;
	}

	public void setCompanyLearnersTradeTestList(List<CompanyLearnersTradeTest> companylearnerstradetestList) {
		this.companylearnerstradetestList = companylearnerstradetestList;
	}

	public CompanyLearnersTradeTest getCompanylearnerstradetest() {
		return companylearnerstradetest;
	}

	public void setCompanylearnerstradetest(CompanyLearnersTradeTest companylearnerstradetest) {
		this.companylearnerstradetest = companylearnerstradetest;
	}

	public List<CompanyLearnersTradeTest> getCompanyLearnersTradeTestfilteredList() {
		return companylearnerstradetestfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnerstradetestfilteredList the new
	 *                                             companylearnerstradetestfilteredList
	 *                                             list
	 * @see CompanyLearnersTradeTest
	 */
	public void setCompanyLearnersTradeTestfilteredList(List<CompanyLearnersTradeTest> companylearnerstradetestfilteredList) {
		this.companylearnerstradetestfilteredList = companylearnerstradetestfilteredList;
	}

	public LazyDataModel<CompanyLearnersTradeTest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersTradeTest> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<CompanyLearnersTradeTest> getCompanylearnerstradetestfilteredList() {
		return companylearnerstradetestfilteredList;
	}

	public void setCompanylearnerstradetestfilteredList(List<CompanyLearnersTradeTest> companylearnerstradetestfilteredList) {
		this.companylearnerstradetestfilteredList = companylearnerstradetestfilteredList;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReasons(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean isCanUpload() {
		return canUpload;
	}

	public void setCanUpload(boolean canUpload) {
		this.canUpload = canUpload;
	}

	public int getRejectionIndicator() {
		return rejectionIndicator;
	}

	public void setRejectionIndicator(int rejectionIndicator) {
		this.rejectionIndicator = rejectionIndicator;
	}

	public void setRejectionIndicator(Integer rejectionIndicator) {
		this.rejectionIndicator = rejectionIndicator;
	}

	public boolean isAlterApplicationForm() {
		return alterApplicationForm;
	}

	public void setAlterApplicationForm(boolean alterApplicationForm) {
		this.alterApplicationForm = alterApplicationForm;
	}

	public DetailsOfExperienceArpl getDetailsOfExperienceArpl() {
		return detailsOfExperienceArpl;
	}

	public void setDetailsOfExperienceArpl(DetailsOfExperienceArpl detailsOfExperienceArpl) {
		this.detailsOfExperienceArpl = detailsOfExperienceArpl;
	}

	public DetailsOfTrainingArpl getDetailsOfTrainingArpl() {
		return detailsOfTrainingArpl;
	}

	public void setDetailsOfTrainingArpl(DetailsOfTrainingArpl detailsOfTrainingArpl) {
		this.detailsOfTrainingArpl = detailsOfTrainingArpl;
	}

	public LazyDataModel<DetailsOfExperienceArpl> getDataModelDetailsOfExperienceArpl() {
		return dataModelDetailsOfExperienceArpl;
	}

	public void setDataModelDetailsOfExperienceArpl(LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl) {
		this.dataModelDetailsOfExperienceArpl = dataModelDetailsOfExperienceArpl;
	}

	public LazyDataModel<DetailsOfTrainingArpl> getDataModelDetailsOfTrainingArpl() {
		return dataModelDetailsOfTrainingArpl;
	}

	public void setDataModelDetailsOfTrainingArpl(LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl) {
		this.dataModelDetailsOfTrainingArpl = dataModelDetailsOfTrainingArpl;
	}

	public boolean isAlterTestInformation() {
		return alterTestInformation;
	}

	public void setAlterTestInformation(boolean alterTestInformation) {
		this.alterTestInformation = alterTestInformation;
	}

	public TradeTestTaskResult getTradeTestTaskResult() {
		return tradeTestTaskResult;
	}

	public void setTradeTestTaskResult(TradeTestTaskResult tradeTestTaskResult) {
		this.tradeTestTaskResult = tradeTestTaskResult;
	}

	public LazyDataModel<TradeTestTaskResult> getDataModelTradeTestTaskResult() {
		return dataModelTradeTestTaskResult;
	}

	public void setDataModelTradeTestTaskResult(LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult) {
		this.dataModelTradeTestTaskResult = dataModelTradeTestTaskResult;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public boolean isDisplayTestInformation() {
		return displayTestInformation;
	}

	public void setDisplayTestInformation(boolean displayTestInformation) {
		this.displayTestInformation = displayTestInformation;
	}

	public boolean isEditNambInfo() {
		return editNambInfo;
	}

	public void setEditNambInfo(boolean editNambInfo) {
		this.editNambInfo = editNambInfo;
	}

	public boolean isDisplayNambInfo() {
		return displayNambInfo;
	}

	public void setDisplayNambInfo(boolean displayNambInfo) {
		this.displayNambInfo = displayNambInfo;
	}

	public boolean isDisplayCollection() {
		return displayCollection;
	}

	public void setDisplayCollection(boolean displayCollection) {
		this.displayCollection = displayCollection;
	}

	public boolean isEditCollectionInfo() {
		return editCollectionInfo;
	}

	public void setEditCollectionInfo(boolean editCollectionInfo) {
		this.editCollectionInfo = editCollectionInfo;
	}

	public List<CompanyLearnersTradeTest> getCompanylearnerstradetestList() {
		return companylearnerstradetestList;
	}

	public void setCompanylearnerstradetestList(List<CompanyLearnersTradeTest> companylearnerstradetestList) {
		this.companylearnerstradetestList = companylearnerstradetestList;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Boolean getViewContactDetails() {
		return viewContactDetails;
	}

	public void setViewContactDetails(Boolean viewContactDetails) {
		this.viewContactDetails = viewContactDetails;
	}

	public List<Users> getRegionUserList() {
		return regionUserList;
	}

	public void setRegionUserList(List<Users> regionUserList) {
		this.regionUserList = regionUserList;
	}

	public CompanyTradeTestEmployer getCompanyTradeTestEmployer() {
		return companyTradeTestEmployer;
	}

	public void setCompanyTradeTestEmployer(CompanyTradeTestEmployer companyTradeTestEmployer) {
		this.companyTradeTestEmployer = companyTradeTestEmployer;
	}

	public Boolean getSearchEmp() {
		return searchEmp;
	}

	public void setSearchEmp(Boolean searchEmp) {
		this.searchEmp = searchEmp;
	}

	public Boolean getDoneSearch() {
		return doneSearch;
	}

	public void setDoneSearch(Boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public void setTELPHONE_FORMAT(String tELPHONE_FORMAT) {
		TELPHONE_FORMAT = tELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public CollectDetail getCollectDetail() {
		return collectDetail;
	}

	public void setCollectDetail(CollectDetail collectDetail) {
		this.collectDetail = collectDetail;
	}

	public boolean isCopyAddressEmployer() {
		return copyAddressEmployer;
	}

	public void setCopyAddressEmployer(boolean copyAddressEmployer) {
		this.copyAddressEmployer = copyAddressEmployer;
	}

	public Boolean getEmployerSearch() {
		return employerSearch;
	}

	public void setEmployerSearch(Boolean employerSearch) {
		this.employerSearch = employerSearch;
	}

	public Boolean getDisplayEmployerInfo() {
		return displayEmployerInfo;
	}

	public void setDisplayEmployerInfo(Boolean displayEmployerInfo) {
		this.displayEmployerInfo = displayEmployerInfo;
	}

	public List<RejectReasons> getRejectionReasonsFound() {
		return rejectionReasonsFound;
	}

	public void setRejectionReasonsFound(List<RejectReasons> rejectionReasonsFound) {
		this.rejectionReasonsFound = rejectionReasonsFound;
	}

	public boolean isAlterDateOrginalDocumentSubmission() {
		return alterDateOrginalDocumentSubmission;
	}

	public void setAlterDateOrginalDocumentSubmission(boolean alterDateOrginalDocumentSubmission) {
		this.alterDateOrginalDocumentSubmission = alterDateOrginalDocumentSubmission;
	}

	public boolean isDisplayDateOrginalDocumentSubmission() {
		return displayDateOrginalDocumentSubmission;
	}

	public void setDisplayDateOrginalDocumentSubmission(boolean displayDateOrginalDocumentSubmission) {
		this.displayDateOrginalDocumentSubmission = displayDateOrginalDocumentSubmission;
	}

	public boolean isViewTestResults() {
		return viewTestResults;
	}

	public void setViewTestResults(boolean viewTestResults) {
		this.viewTestResults = viewTestResults;
	}

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public Company getProviderInformation() {
		return providerInformation;
	}

	public void setProviderInformation(Company providerInformation) {
		this.providerInformation = providerInformation;
	}

	public Company getEmployerInformation() {
		return employerInformation;
	}

	public void setEmployerInformation(Company employerInformation) {
		this.employerInformation = employerInformation;
	}

	public Boolean getDownloadLpmFmDoc() {
		return downloadLpmFmDoc;
	}

	public void setDownloadLpmFmDoc(Boolean downloadLpmFmDoc) {
		this.downloadLpmFmDoc = downloadLpmFmDoc;
	}

	public List<CompanyUsers> getCompanyUserSelectionList() {
		return companyUserSelectionList;
	}

	public void setCompanyUserSelectionList(List<CompanyUsers> companyUserSelectionList) {
		this.companyUserSelectionList = companyUserSelectionList;
	}

	public CompanyUsers getSelectedCompanyUser() {
		return selectedCompanyUser;
	}

	public void setSelectedCompanyUser(CompanyUsers selectedCompanyUser) {
		this.selectedCompanyUser = selectedCompanyUser;
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

	public Long getMotorCbmtId() {
		return motorCbmtId;
	}

	public void setMotorCbmtId(Long motorCbmtId) {
		this.motorCbmtId = motorCbmtId;
	}

	public UsersLanguage getSelectedUserLanguage() {
		return selectedUserLanguage;
	}

	public void setSelectedUserLanguage(UsersLanguage selectedUserLanguage) {
		this.selectedUserLanguage = selectedUserLanguage;
	}

	public UsersDisability getSelectedUserDisability() {
		return selectedUserDisability;
	}

	public void setSelectedUserDisability(UsersDisability selectedUserDisability) {
		this.selectedUserDisability = selectedUserDisability;
	}

	public List<UsersLanguage> getUserLanguageList() {
		return userLanguageList;
	}

	public void setUserLanguageList(List<UsersLanguage> userLanguageList) {
		this.userLanguageList = userLanguageList;
	}

	public List<UsersDisability> getUserDisabilityList() {
		return userDisabilityList;
	}

	public void setUserDisabilityList(List<UsersDisability> userDisabilityList) {
		this.userDisabilityList = userDisabilityList;
	}

	public boolean isAlterUserLanguages() {
		return alterUserLanguages;
	}

	public void setAlterUserLanguages(boolean alterUserLanguages) {
		this.alterUserLanguages = alterUserLanguages;
	}

	public boolean isAlterUserDisability() {
		return alterUserDisability;
	}

	public void setAlterUserDisability(boolean alterUserDisability) {
		this.alterUserDisability = alterUserDisability;
	}

	public AssessorModeratorApplication getSelectedAssessorModeratorApplication() {
		return selectedAssessorModeratorApplication;
	}

	public void setSelectedAssessorModeratorApplication(AssessorModeratorApplication selectedAssessorModeratorApplication) {
		this.selectedAssessorModeratorApplication = selectedAssessorModeratorApplication;
	}

	public LazyDataModel<AssessorModeratorApplication> getAssessorListDataModel() {
		return assessorListDataModel;
	}

	public void setAssessorListDataModel(LazyDataModel<AssessorModeratorApplication> assessorListDataModel) {
		this.assessorListDataModel = assessorListDataModel;
	}

	public LazyDataModel<AssessorModeratorApplication> getModeratorListDataModel() {
		return moderatorListDataModel;
	}

	public void setModeratorListDataModel(LazyDataModel<AssessorModeratorApplication> moderatorListDataModel) {
		this.moderatorListDataModel = moderatorListDataModel;
	}

}
