package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.Municipality;
import haj.com.entity.NonSetaCompanyUsers;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.SDPCompany;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.SitesSme;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.OrganisationType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorCompanySitesService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.MailDef;
import haj.com.service.ReviewCommitteeMeetingService;
import haj.com.service.SDPCompanyService;
import haj.com.service.SitesSmeService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderLearnershipService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.TrainingSiteService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.OrganisationTypeService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SICCodeService;
import haj.com.service.lookup.SaqaUsQualificationService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TpDetailUI.
 */
@ManagedBean(name = "tpDetailUI")
@ViewScoped
public class TpDetailUI extends AbstractUI {
	

	/** The Review Committee Meeting Service. */
	private ReviewCommitteeMeetingService service = new ReviewCommitteeMeetingService();

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company. */
	private Company company;

	private List<Company> companyList;

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The training provider user. */
	private Users trainingProviderUser;

	/** The company qualifications. */
	private List<CompanyQualifications> companyQualifications;

	/** The company qualifications service. */
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();

	/** The unit standards. */
	private List<CompanyUnitStandard> unitStandards;
	private CompanyUnitStandard selectCompanyUnitStandard;

	/** The company unit standard service. */
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();

	/** The chamber service. */
	private ChamberService chamberService = new ChamberService();

	/** The chambers. */
	private List<Chamber> chambers;

	/** The sic code service. */
	private SICCodeService sicCodeService = new SICCodeService();

	/** The sic codes. */
	private List<SICCode> sicCodes;

	/** The copy address. */
	private Boolean copyAddress;

	/** The update company bool. */
	private Boolean updateCompanyBool;

	/** The display user info. */
	private Boolean displayUserInfo;

	/** The doc upload user. */
	private Boolean docUploadUser;

	/** The company problem. */
	private String companyProblem;

	/** The exceptions. */
	private String exceptions;

	/** The doc. */
	private Doc doc;

	/** The qualification. */
	private Qualification qualification;

	/** The unit standard. */
	private UnitStandards unitStandard;

	private TrainingProviderApplication trainingProviderApplication;

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();

	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList= new ArrayList<>();
	/** the Organisation Type */
	private List<OrganisationType> organisationTypeList;

	/** the Organisation Type Service */
	private OrganisationTypeService organisationTypeService = new OrganisationTypeService();
	/** The maximum size all all last names */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The maximum size all for company trade name */
	private Long MAX_COMPANY_TRADE_NAME_SIZE = HAJConstants.MAX_COMPANY_TRADE_NAME_SIZE;

	/** The maximum size all for company name */
	private Long MAX_ADDRESS_LINE_SIZE = HAJConstants.MAX_ADDRESS_LINE_SIZE;

	/** The maximum size all for company name */
	private Long MAX_ADDRESS_CODE_SIZE = HAJConstants.MAX_ADDRESS_CODE_SIZE;

	/** The maximum size all for tax number */
	private Long MAX_TAX_NUMBER = HAJConstants.MAX_TAX_NUMBER;

	/** The maximum size all for vax number */
	private Long MAX_VAX_NUMBER = HAJConstants.MAX_VAX_NUMBER;

	/** The maximum size all for fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for fax number */
	private Long MAX_NUMBER_OF_EMPLOYEES_SIZE = HAJConstants.MAX_NUMBER_OF_EMPLOYEES_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant company levy number format. */
	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** The Constant company vat number format. */
	private String companyVatNumberFormat = HAJConstants.companyVatNumberFormat;

	/** The Constant allow only number format. */
	private String allowOnlyNumber = HAJConstants.allowOnlyNumber;

	/** The Constant telephone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The Constant cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	/** The Constant FAX number format. */
	public String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	private SitesSme sitesSme;
	private List<SitesSme> sitesSmes;
	private SitesSmeService sitesSmeService = new SitesSmeService();
	
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	/**Training Provider Contact Persons*/
	private List<CompanyUsers> contactPersonList;
	
	/**Training Provider Assessor/Facilitator*/
	private List<CompanyUsers> facilitatorAssessorList;
	
	/**Training Provider Contact Persons*/
	private List<NonSetaCompanyUsers> nonSetaContactPersonList;
	
	/**Training Provider Assessor/Facilitator*/
	private List<NonSetaCompanyUsers> nonSetaAssessorModList;
	
	/**Select TrainingProviderContactPerson*/
	private CompanyUsers selectedTPContsctPerson;

	/** The TP contact person. */
	private Users contactPerson;
	
	private boolean addAssFacilitator;
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	
	private List<RejectReasons> rejectReason=new ArrayList<>();
	
	private Date nextDate;
	
	private List<TrainingProviderSkillsProgramme> tpSkillsProgramList;
	
	private TrainingProviderSkillsProgramme selectSkillsProgram;
	
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
	
	private List<TrainingProviderSkillsSet> tpSkillsSetList;
	
	private TrainingProviderSkillsSetService trainingProviderSkillsSetSevice=new TrainingProviderSkillsSetService();
	
	/** The unit standards service. */
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	
	
	private boolean onAfterSiteVistDate = false;
	private boolean noDateProvided = false;
	private boolean finalRejection;
	
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	SDPReAccreditation sdpReAccreditation=new SDPReAccreditation();
	
	private CompanyQualifications selectedCompanyQualification;
	
	/** The qualification. */
	private Qualification learningProgrammeQualification;
	
	/** Training provider Skills Program */
	private SkillsProgram skillsProgram;
	
	private AuditorMonitorReview auditorMonitorReview;
	
	private SkillsSet skillsSet;
	private TrainingProviderSkillsSet tpSkillsSet;
	
	private Boolean enableEdit;
	
	/** The qualification list. */
	private List<Qualification> qualificationList=new ArrayList<>();
	
	/** The unit standards. */
	private List<UnitStandards> unitStandardList=new ArrayList<>();
	
	private ArrayList<SkillsProgram> skillsProgramList = new ArrayList<>();
	
	private ArrayList<SkillsSet> skillsSetList = new ArrayList<>();
	
	private SkillsProgramService skillsProgramService = new SkillsProgramService();
	
	private int resultsForSkillsProgramFound = 0;
	
	private List<SkillsProgram> skillsProgramByUnitStandardsList = new ArrayList<>();
	
	private Boolean qualUplaoaded;
	private Boolean usUplaoaded;
	private Boolean spUplaoaded;
	private Boolean ssUplaoaded;
	private Boolean doneQualification;
	private String docFor;
	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	private String docNode;
	private Boolean uploadCompanyDoc;
	private TrainingProviderLearnershipService trainingProviderLearnershipService=new TrainingProviderLearnershipService();
	
	/* Qualification Documents Parent */
	private TrainingProviderDocParent docParent;
	private List<TrainingProviderDocParent> docParentList = new ArrayList<>();
	private List<CompanyQualifications> companyQualificationsList = new ArrayList<>();
	private List<TrainingProviderSkillsProgramme> tpSkillsProgrammeList = new ArrayList<>();
	private List<TrainingProviderSkillsSet> trainingProviderSkillsSetList = new ArrayList<>();
	private List<CompanyUnitStandard> companyUnitStandardList = new ArrayList<>();
	private List<TrainingProviderLearnership> tpLearnership=new ArrayList<>();
	public Boolean showComplete;
	
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;
	
	private AuditorMonitorReview selectedAuditorMonitorReview = null;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList;
	
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private TrainingSite trainingSite;
	
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		if (super.getParameter("id", false) != null) {
			if((getSessionUI().getTask().getWorkflowProcess()==ConfigDocProcessEnum.NON_SETA_PROVIDERS)) {
				populateNonSetaTpApplicationData();
			}else if((getSessionUI().getTask().getWorkflowProcess()==ConfigDocProcessEnum.SDP_LEGACY_APPLICATION)) {
				populateLegacyTpApplicationData();
			}
			else{
				populateTpApplicationData();
			}
		}
		showComplete=false;
		chambers = chamberService.allChamber();
		sicCodes = sicCodeService.allSICCode();
		nextDate = GenericUtility.addDaysToDate(new Date(), 1);
	}
	
	public void populateNonSetaTpApplicationData() throws Exception {
		trainingProviderApplication=trainingProviderApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
		this.company = companyService.findByKeyAndResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(),getSessionUI().getTask().getWorkflowProcess());
		if(company.getDocs() ==null || company.getDocs().size()<1)
		{
			companyService.prepareNewRegistrationType(
					getSessionUI().getTask().getWorkflowProcess(), this.company, null,
					CompanyUserTypeEnum.Company);
		}
		this.companyList = new ArrayList<>();
		companyList.add(company);
		this.company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
		this.company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
		contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
		facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		//Training Provider User
		trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP, CompanyUserTypeEnum.User,trainingProviderApplication.getId(),trainingProviderApplication.getClass().getName());
		companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		tpSkillsProgramList = trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		tpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
		populateRejectReasons();
		checkEnableEdit();
		if(enableEdit !=null && enableEdit) {
			prepareQualificationDetails();
			checkIfDocsUploaded();
		}
		if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
			trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId()));
		}else {
			trainingSite = null;
		}
		sdpCompanyDataModelInfo();
		assessorModeratorCompanySitesDataModelInfo();
	}
	
	public void populateTpApplicationData() throws Exception {
		
		trainingProviderApplication=trainingProviderApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
		if (trainingProviderApplication != null) {
			if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
				TrainingSiteService.instance().resolveAddressInformatioAndRegion(trainingProviderApplication.getTrainingSite());
			}
		}
		this.company = companyService.findByKeyAndResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(),getSessionUI().getTask().getWorkflowProcess());
		this.companyList = new ArrayList<>();
		companyList.add(company);
		if(company.getDocs() ==null || company.getDocs().size()<1)
		{
			companyService.prepareNewRegistrationType(getSessionUI().getTask().getWorkflowProcess(), this.company, null,
					CompanyUserTypeEnum.Company);
		}
//		auditorMonitorReviewDataModelList = new ArrayList<>();
//		auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		auditorMonitorReviewEvidanceRequiredDataModelListInfo();
		if(trainingProviderApplication.getApprovalStatus()==ApprovalEnum.PendingResubmition)
		{
			getSessionUI().getTask().getProcessRole().setRolePermission(UserPermissionEnum.EditUpload);
		}
		contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
		facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		//Training Provider User
		trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(trainingProviderApplication.getUsers().getId(),getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User,trainingProviderApplication.getId(),trainingProviderApplication.getClass().getName());
		companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
		//organisationTypeList = organisationTypeService.allOrganisationType();
		populateRejectReasons();
		if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
			validateSiteVistData();
		}
		if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
			trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId()));
		}else {
			trainingSite = null;
		}
		sdpCompanyDataModelInfo();
		assessorModeratorCompanySitesDataModelInfo();
	}
	
	private void auditorMonitorReviewEvidanceRequiredDataModelListInfo() {
		auditorMonitorReviewEvidanceRequiredDataModelList = new LazyDataModel<AuditorMonitorReview>() {
			private static final long serialVersionUID = 1L;
			private List<AuditorMonitorReview> retorno = new ArrayList<AuditorMonitorReview>();
			@Override
			public List<AuditorMonitorReview> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortOrder == null) {
						sortOrder = SortOrder.ASCENDING;
						sortField = "o.section";
					}
					filters.put("targetClass", trainingProviderApplication.getClass().getName());
					filters.put("targetKey", trainingProviderApplication.getId());
					retorno = auditorMonitorReviewService.allAuditorMonitorReviewByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters);
					auditorMonitorReviewEvidanceRequiredDataModelList.setRowCount(auditorMonitorReviewService.countAllAuditorMonitorReviewByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AuditorMonitorReview obj) {
				return obj.getId();
			}

			@Override
			public AuditorMonitorReview getRowData(String rowKey) {
				for (AuditorMonitorReview obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepUpdateAuditorMonitorReview(){
		try {
			runClientSideExecute("PF('auditMonitorRevUpdDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeAuditorMonitorReviewView(){
		try {
			runClientSideExecute("PF('auditMonitorRevUpdDlg').hide()");
			selectedAuditorMonitorReview = null;
			auditorMonitorReviewEvidanceRequiredDataModelListInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateAuditorMonitorReview(){
		try {
			auditorMonitorReviewService.update(selectedAuditorMonitorReview);
			selectedAuditorMonitorReview = null;
			addInfoMessage("Action Complete");
			runClientSideExecute("PF('auditMonitorRevUpdDlg').hide()");
			auditorMonitorReviewEvidanceRequiredDataModelListInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	
	public void populateLegacyTpApplicationData() throws Exception {
		
		trainingProviderApplication=trainingProviderApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
		this.company = companyService.findByKeyAndResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(),getSessionUI().getTask().getWorkflowProcess());
		this.companyList = new ArrayList<>();
		companyList.add(company);
		if(company.getDocs() ==null || company.getDocs().size()<1)
		{
			companyService.prepareNewRegistrationType(getSessionUI().getTask().getWorkflowProcess(), this.company, null,CompanyUserTypeEnum.Company);
		}
		auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		
		if(trainingProviderApplication.getApprovalStatus()==ApprovalEnum.PendingResubmition)
		{
			getSessionUI().getTask().getProcessRole().setRolePermission(UserPermissionEnum.EditUpload);
		}
		contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
		facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		//Training Provider User
		trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(trainingProviderApplication.getUsers().getId(),getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User,trainingProviderApplication.getId(),trainingProviderApplication.getClass().getName());
		companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
		tpLearnership=trainingProviderLearnershipService.findByTrainingProvider(trainingProviderApplication.getId());
		populateRejectReasons();
		if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
			validateSiteVistData();
		}
		if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
			trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId()));
		}else {
			trainingSite = null;
		}
		sdpCompanyDataModelInfo();
		assessorModeratorCompanySitesDataModelInfo();
	}
	
	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();
			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null) {
						if (trainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getTrainingSite().getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSite(filters));
						}
					} else if(company != null && company.getId() != null) {
						retorno = sdpCompanyService.allSdpLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, company.getId());
						sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToHoldingCompany(filters));
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
			public List<AssessorModeratorCompanySites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null) {
						if (trainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getTrainingSite().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToTrainingSite(filters));
						}
					} else {
						retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, company.getId());
						assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToHoldingCompany(filters));
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
	
	public void prepareQualificationDetails() throws Exception
	{
		if(companyQualifications !=null && companyQualifications.size()>0)
		{
			for(CompanyQualifications cq:companyQualifications)
			{
				qualificationList.add(cq.getQualification());
			}
			companyQualificationsList.addAll(companyQualifications);
		}
		if(unitStandards !=null && unitStandards.size()>0)
		{
			for(CompanyUnitStandard cus:unitStandards)
			{
				unitStandardList.add(cus.getUnitStandard());
			}
			companyUnitStandardList.addAll(unitStandards);
		}
		if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0)
		{
			for(TrainingProviderSkillsProgramme tpsp:tpSkillsProgramList)
			{
				skillsProgramList.add(tpsp.getSkillsProgram());
			}
			tpSkillsProgrammeList.addAll(tpSkillsProgramList);
		}
		if(tpSkillsSetList !=null && tpSkillsSetList.size()>0)
		{
			for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
			{
				skillsSetList.add(tpss.getSkillsSet());
			}
			trainingProviderSkillsSetList.addAll(tpSkillsSetList);
		}
	}
	
	public void checkEnableEdit()
	{
		try {
			if(trainingProviderApplication !=null && trainingProviderApplication.getUsers() !=null && trainingProviderApplication.getUsers().equals(getSessionUI().getActiveUser())){
				enableEdit=true;
			}
			else{
				enableEdit=false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	private void validateSiteVistData() throws Exception{
		if (trainingProviderApplication.getSiteVisitDate() == null) {
			onAfterSiteVistDate = false;
			noDateProvided = true;
		} else {
			noDateProvided = false;
			if (GenericUtility.getStartOfDay(trainingProviderApplication.getSiteVisitDate()).equals(GenericUtility.getStartOfDay(getNow())) || GenericUtility.getStartOfDay(trainingProviderApplication.getSiteVisitDate()).before(GenericUtility.getStartOfDay(getNow()))) {
				onAfterSiteVistDate = true;
				trainingProviderApplication.setSiteVisitReportDate(getNow());
			} else {
				onAfterSiteVistDate = false;
			}
		}
	}
	
	public void setSiteVisitDate(){
		try {
			if (trainingProviderApplication.getSiteVisitDate() == null) {
				addWarningMessage("Provide Site Visit Date Before Proceeding");
			}else {
				//trainingProviderApplication.setQualityAssuranceUser(getSessionUI().getActiveUser());
				trainingProviderApplicationService.update(trainingProviderApplication);
				getSessionUI().getTask().setDueDate(trainingProviderApplication.getSiteVisitDate());
				TasksService.instance().update(getSessionUI().getTask());
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				validateSiteVistData();
				//send email notiofications
				sendSiteVisitiiEmailNotification();
				addInfoMessage("Site Visit Date Set");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void sendSiteVisitiiEmailNotification() throws Exception
	{
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus: unitStandards)
		{
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq: companyQualifications)
		{
			qualificationsList.add(cq.getQualification());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme tpsp:tpSkillsProgramList)
		{
			skillsProgramList.add(tpsp.getSkillsProgram());
		}
		
		List<SkillsSet> skillsSetList=new ArrayList<>();
		for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
		{
			skillsSetList.add(tpss.getSkillsSet());
		}
		trainingProviderApplicationService.sendSiteVisitEmailNotification(trainingProviderApplication, getSessionUI().getActiveUser(), unitStandardsList, qualificationsList, skillsProgramList, skillsSetList);
		
	}
	
	public void sendSiteVisitAmendedEmailNotification() throws Exception
	{
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus: unitStandards)
		{
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq: companyQualifications)
		{
			qualificationsList.add(cq.getQualification());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme tpsp:tpSkillsProgramList)
		{
			skillsProgramList.add(tpsp.getSkillsProgram());
		}
		
		List<SkillsSet> skillsSetList=new ArrayList<>();
		for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
		{
			skillsSetList.add(tpss.getSkillsSet());
		}
		trainingProviderApplicationService.sendSiteVisitAmendedEmailNotification(trainingProviderApplication, getSessionUI().getActiveUser(), unitStandardsList, qualificationsList, skillsProgramList, skillsSetList);
		
	}
	
	
	
	public void prepSiteVisitDateUpdate() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
			dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.TP);
			runClientSideExecute("PF('dlgReviewDate').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void siteVisitDateUpdate() {
		try {
			if (dateChangeReasonSelectionList.size() != 0) {
				trainingProviderApplicationService.update(trainingProviderApplication);
				trainingProviderApplicationService.createDateChangeReasons(trainingProviderApplication, getSessionUI().getActiveUser(), dateChangeReasonSelectionList, ConfigDocProcessEnum.TP);
				dateChangeReasonSelectionList = null;
				dateChangeReasonAvalibleSelectionList = null;
				getSessionUI().getTask().setDueDate(trainingProviderApplication.getSiteVisitDate());
				TasksService.instance().update(getSessionUI().getTask());
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				validateSiteVistData();
				addInfoMessage("Date Updated And Notified");
				runClientSideExecute("PF('dlgReviewDate').hide()");
				// add email notfiications
				sendSiteVisitAmendedEmailNotification();
			} else {
				addWarningMessage("Provide A Minium Of One Reason For Date Change Before Proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveQualificationSkillProg(CompanyQualifications cq)
	{
		try {
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0)
			{
				for(TrainingProviderSkillsProgramme tpsp: tpSkillsProgramList)
				{
					if(tpsp.getSkillsProgram() !=null && tpsp.getSkillsProgram().getQualification() !=null && cq.getQualification() !=null)
					{
						if(tpsp.getSkillsProgram().getQualification().equals(cq.getQualification()))
						{
							tpsp.setAccept(cq.getAccept());
						}
					}
				}
			}
			
			if(unitStandards !=null && unitStandards.size()>0)
			{
				for(CompanyUnitStandard cus: unitStandards)
				{
					if(cus.getForQualification()!=null)
					{
						if(cus.getForQualification().equals(cq.getQualification()))
						{
							cus.setAccept(cq.getAccept());
						}
					}
				}
			}
			
			if(tpSkillsSetList !=null && tpSkillsSetList.size()>0)
			{
				for(TrainingProviderSkillsSet tpSS: tpSkillsSetList)
				{
					if(tpSS.getSkillsSet().getQualification() !=null)
					{
						if(tpSS.getSkillsSet().getQualification().equals(cq.getQualification()))
						{
							tpSS.setAccept(cq.getAccept());
						}
					}
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
	public void prepareContactPerson() {
		try {
			contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
			facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {

			//rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(company.getId(), Company.class.getName(), ConfigDocProcessEnum.TP);			
			rejectReason = rs.findByTargetKeyClassAndProcessAndResolveRejectDate(trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}		
	}
	
	
	public void prepareAddAssFacilitator(boolean var)
	{
		addAssFacilitator=var;
	}
	
	public void removeContactPerson()
	{
		
		try {
			companyUsersService.delete(selectedTPContsctPerson);
			addWarningMessage("Contact person deleted");
			prepareContactPerson();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Removes the Skills Program from list.
	 */
	public void removeNonSetaSkillsProgramFromList() {
		try {
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0)
			{
				for(TrainingProviderSkillsProgramme tpsp:tpSkillsProgramList)
				{
					if(skillsProgram.equals(tpsp.getSkillsProgram()))
					{
						trainingProviderSkillsProgrammeService.delete(tpsp);
						tpSkillsProgramList = trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
						tpSkillsProgrammeList.clear();
						tpSkillsProgrammeList.addAll(tpSkillsProgramList);
					}
				}
			}
			skillsProgramList.remove(skillsProgram);
			resetDoneQualification();
			prepareNewSkillsProgram();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the qualification from list.
	 */
	public void removeNonSetaQualificationFromList() {
		try {
			qualificationList.remove(qualification);
			if(companyQualifications !=null && companyQualifications.size()>0)
			{
				for(CompanyQualifications cq:companyQualifications)
				{
					if(cq.getQualification().equals(qualification))
					{
						companyQualificationsService.delete(cq);
						companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
						companyQualificationsList.clear();
						companyQualificationsList.addAll(companyQualifications);
					}
				}
			}
			removeSKillsProgrammes(qualification);
			resetDoneQualification();
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeSKillsProgrammes(Qualification qualification) {
		List<SkillsProgram>tempList=new ArrayList<>();
		tempList.addAll(skillsProgramList);
		for(SkillsProgram sp:skillsProgramList)
		{
			if(sp.getQualification().equals(qualification))
			{
				tempList.remove(sp);
			}
		}
		skillsProgramList.clear();
		skillsProgramList=(ArrayList<SkillsProgram>) tempList;
	}
	
	/**
	 * Adds the Skills Program to list.
	 */
	public void addNonSetaSkillsSetToList() {
		try {
			if (skillsSet==null){
				throw new Exception("Please select Skills Set");
			}
			if (skillsSet != null && !skillsSetList.contains(skillsSet)) {
				if(ssUplaoaded !=null && ssUplaoaded)
				{
					if(trainingProviderSkillsSetList !=null && trainingProviderSkillsSetList.size()>0)
					{
						if(trainingProviderSkillsSetList.get(0).getTrainingProviderDocParent() !=null)
						{
							TrainingProviderSkillsSet tpSS = new TrainingProviderSkillsSet();
							tpSS.setSkillsSet(skillsSet);
							tpSS.setTrainingProviderDocParent(trainingProviderSkillsSetList.get(0).getTrainingProviderDocParent());
							trainingProviderSkillsSetList.add(tpSS);
						}
					}
					
				}
				this.skillsSetList.add(skillsSet);
				prepareNewSkillsSet();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("skills.set.already.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void storeSupportingDoc(FileUploadEvent event) {
		try {
			Doc doc = new Doc();
			docParent = new TrainingProviderDocParent();
			doc.setNote(docNode);
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			docParent.setDoc(doc);
			saveDocument();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void saveDocument() {
		if (docFor.equalsIgnoreCase("Qualifications")) {
			addQualificationBetch();
		} else if (docFor.equalsIgnoreCase("Unit Standards")) {
			addCmpanyUnitStandardBetch();
		} else if (docFor.equalsIgnoreCase("Skills Programme")) {
			addTPSkillsProgremeBetch();
		} else if (docFor.equalsIgnoreCase("Skills Set")) {
			addTPSkillsSetBetch();
		}
	}
	
	public void addQualificationBetch() {
		qualUplaoaded = true;
		companyQualificationsList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (Qualification qual : qualificationList) {
				CompanyQualifications companyQualification=null;
				for(CompanyQualifications cq:companyQualifications){
					if(cq.getQualification().equals(qual)){
						companyQualification=cq;
						break;
					}
				}
				if(companyQualification==null){
					companyQualification = new CompanyQualifications();
					companyQualification.setQualification(qual);
				}
				companyQualification.setTrainingProviderDocParent(docParent);
				companyQualificationsList.add(companyQualification);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addCmpanyUnitStandardBetch() {
		usUplaoaded = true;
		companyUnitStandardList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (UnitStandards us : unitStandardList) {
				CompanyUnitStandard compUS=null;
				for(CompanyUnitStandard cu:unitStandards){
					if(cu.getUnitStandard().equals(us)){
						compUS=cu;
					}
				}
				if(compUS==null){
					compUS = new CompanyUnitStandard();
					compUS.setUnitStandard(us);
				}
				compUS.setTrainingProviderDocParent(docParent);
				companyUnitStandardList.add(compUS);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addTPSkillsProgremeBetch() {
		spUplaoaded = true;
		tpSkillsProgrammeList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (SkillsProgram sp : skillsProgramList) {
				TrainingProviderSkillsProgramme stSP=null;
				for(TrainingProviderSkillsProgramme tpsp: tpSkillsProgramList)
				{
					if(tpsp.getSkillsProgram().equals(sp)){
						stSP=tpsp;
					}
				}
				if(stSP==null){
					stSP = new TrainingProviderSkillsProgramme();
					stSP.setSkillsProgram(sp);
				}
				stSP.setTrainingProviderDocParent(docParent);
				tpSkillsProgrammeList.add(stSP);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addTPSkillsSetBetch() {
		ssUplaoaded = true;
		trainingProviderSkillsSetList.clear();
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (SkillsSet ss : skillsSetList) {
				TrainingProviderSkillsSet tpSS=null;
				for(TrainingProviderSkillsSet tss:tpSkillsSetList){
					if(tss.getSkillsSet().equals(ss)){
						tpSS=tss;
					}
				}
				if(tpSS==null){
					tpSS = new TrainingProviderSkillsSet();
					tpSS.setSkillsSet(ss);
				}
				tpSS.setTrainingProviderDocParent(docParent);
				trainingProviderSkillsSetList.add(tpSS);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void doneQualificationDetails()
	{
		try {
			if (qualificationList.size() > 0 || unitStandardList.size() > 0 || skillsProgramList.size() > 0 || skillsSetList.size() > 0) {
				doneQualification=true;
				checkIfDocsUploaded();
			}
			else
			{
				throw new ValidationException("Please provide qualification details");
			}
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
	
	public void checkIfDocsUploaded()
	{

		if(companyQualifications !=null && companyQualifications.size()>0)
		{
			if(companyQualifications.get(0).getTrainingProviderDocParent() !=null)
			{
				qualUplaoaded=true;
			}
		}
		if(unitStandards !=null && unitStandards.size()>0)
		{
			if(unitStandards.get(0).getTrainingProviderDocParent() !=null)
			{
				usUplaoaded=true;
			}
		}
		if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0)
		{
			if(tpSkillsProgramList.get(0).getTrainingProviderDocParent() !=null)
			{
				spUplaoaded=true;
			}
		}
		
		if(tpSkillsSetList !=null && tpSkillsSetList.size()>0)
		{
			if(tpSkillsSetList.get(0).getTrainingProviderDocParent() !=null)
			{
				ssUplaoaded=true;
			}
		}
	}
	
	
	
	public void populateSkillsProgramByUnitStandardsList(){
		try {
			if (unitStandardList != null && unitStandardList.size() != 0) {
				resultsForSkillsProgramFound = skillsProgramService.countByUnitStandardIdAssigned(unitStandardList);
			} else {
				resultsForSkillsProgramFound = 0;
				trainingProviderApplication.setUseSkillProgrammeRoute(YesNoEnum.No);
				trainingProviderApplication.setSkillsProgram(null); 
			}
			if (trainingProviderApplication.getUseSkillProgrammeRoute() != null && trainingProviderApplication.getUseSkillProgrammeRoute() == YesNoEnum.Yes) {
				if (unitStandardList != null && unitStandardList.size() != 0) {
					skillsProgramByUnitStandardsList = skillsProgramService.findByUnitStandardIdAssigned(unitStandardList);
				} else {
					addErrorMessage("Select & add unit standards above to view skills programmes you can select");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Removes the Skills Program from list.
	 */
	public void removeNonSetaSkillsSetFromList() {
		try {
			if(tpSkillsSetList !=null && tpSkillsSetList.size()>0)
			{
				for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
				{
					if(skillsSet.equals(tpss.getSkillsSet()))
					{
						trainingProviderSkillsSetSevice.delete(tpss);
						tpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
						trainingProviderSkillsSetList.clear();
						trainingProviderSkillsSetList.addAll(tpSkillsSetList);
					}
				}
			}
			skillsSetList.remove(skillsSet);
			resetDoneQualification();
			prepareNewSkillsSet();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void prepareNewSkillsSet() {
		skillsSet = new SkillsSet();
	}
	
	/**
	 * Removes the unit from list.
	 */
	public void removeNonSetaUnitStandardsFromList() {
		try {
			if(unitStandards !=null && unitStandards.size()>0)
			{
				for(CompanyUnitStandard cu:unitStandards)
				{
					if(unitStandard.equals(cu.getUnitStandard()))
					{
						companyUnitStandardService.delete(cu);
						unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
						companyUnitStandardList.clear();
						companyUnitStandardList.addAll(unitStandards);
					}
				}
			}
			unitStandardList.remove(unitStandard);
			prepareNewQualification();
			resetDoneQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*Complete Unit Standards*/
	public List<UnitStandards> completeUnit(String desc) {
		List<UnitStandards> l = null;
		try {
			l = unitStandardsService.findByTitleAndBeforeLastEnrolmentDate(desc, getNow());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	/**
	 * Removes the Skills Program from list.
	 */
	public void removeSkillsProgramFromList() {
		try {
			trainingProviderSkillsProgrammeService.delete(selectSkillsProgram);
			tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
			addWarningMessage("Skills Program removed");
			selectSkillsProgram=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the Skills Program to list.
	 */
	public void addSkillsProgramToList() {
		try {
			addTpSkillsProgrammes(skillsProgram,true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the unit from list.
	 */
	public void removeUnitFromListmList() {
		try {
			companyUnitStandardService.delete(selectCompanyUnitStandard);
			unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			addWarningMessage("Unit Standard removed");
			selectCompanyUnitStandard=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the Skills Set from list.
	 */
	public void removeSkillsSetFromList() {
		try {
			trainingProviderSkillsSetSevice.delete(tpSkillsSet);
			tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
			addWarningMessage("Skills Set removed");
			tpSkillsSet=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Adds the unit to list.
	 */
	public void addUnitToList() {

		try {
			
			if(unitStandard==null)
			{
				throw new Exception("Please Select Unit Standard");
			}
			for(CompanyUnitStandard cus: unitStandards)
			{
				if(unitStandard.equals(cus.getUnitStandard()))
				{
					throw new Exception("Unit Standard already in the list");
				}
			}
			CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			companyUnitStandardService.create(companyUnitStandard);
			prepareNewQualification();
			unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			addInfoMessage("Unit Standard added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the unit to list.
	 */
	public void addSkillsSetToList() {

		try {
			
			if(skillsSet==null)
			{
				throw new Exception("Please Select Skills Set");
			}
			for(TrainingProviderSkillsSet tpss: tpSkillsSetList)
			{
				if(skillsSet.equals(tpss.getSkillsSet()))
				{
					throw new Exception("Skills Setalready in the list");
				}
			}
			TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, trainingProviderApplication);
			trainingProviderSkillsSetSevice.create(tpSkillsSet);
			prepareNewQualification();
			tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
			addInfoMessage("Skills Set added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void clearLearningProgrammeQualification()
	{
		learningProgrammeQualification=new Qualification();
	}
	
	/**
	 * Validate user from selecting US from that belong to selected
	 * qualification
	 */
	public void usQualificationValidation() {
		try {
			SaqaUsQualificationService saqaUsQualificationService = new SaqaUsQualificationService();
			for(CompanyQualifications qc:companyQualifications)
			{
				if ((saqaUsQualificationService.findByQualAndUS(qc.getQualification().getId(), unitStandard.getCode())).size() > 0) {
					unitStandard = null;
					throw new Exception("Unit Standard forms part of the qualification(" + qc.getQualification().getDescription()
							+ ") selected above");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	
	/**
	 * Removes the qualification from list.
	 */
	public void removeQualificationFromList() {
		try {
			companyQualificationsService.delete(selectedCompanyQualification);
			removeSKillsProgrammes(selectedCompanyQualification);
			companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			prepareNewQualification();
			addWarningMessage("Qualification removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeSKillsProgrammes(CompanyQualifications cp) throws Exception {
		
		for(TrainingProviderSkillsProgramme tpSp:tpSkillsProgramList)
		{
			if(tpSp.getSkillsProgram().getQualification().equals(cp.getQualification()))
			{
				trainingProviderSkillsProgrammeService.delete(tpSp);
			}
		}
	   tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
	   
	}
	
	
	
	
	/**
	 * Adds the qualification to list.
	 */
	public void addNonSetaQualificationToList() {
		try {
			if(qualification==null){
				throw new Exception("Please select qualification");
			}
			if (qualification != null && !qualificationList.contains(qualification)) {
				if(qualUplaoaded !=null && qualUplaoaded)
				{
					if(companyQualificationsList !=null && companyQualificationsList.size()>0)
					{
						if(companyQualificationsList.get(0).getTrainingProviderDocParent() !=null)
						{
							CompanyQualifications companyQualifications = new CompanyQualifications();
							companyQualifications.setQualification(qualification);
							companyQualifications.setTrainingProviderDocParent(companyQualificationsList.get(0).getTrainingProviderDocParent());
							companyQualificationsList.add(companyQualifications);
						}
					}
					
				}
				this.qualificationList.add(qualification);
				if(trainingProviderApplication !=null && 
				trainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL &&
				trainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider && 
				trainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.QCTOTradeTestCentre)
				{
					addNonSetaSKillsProgrammes();
				}
				prepareNewQualification();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("qualification.aready.in.the.list"));
			}
			trainingProviderApplication.setSkillsProgram(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void downloadQualSupportingDoc() {
		try {
			DocService docService=new DocService();
			Doc theDoc=companyQualificationsList.get(0).getTrainingProviderDocParent().getDoc();
			DocByte docByte = docService.findDocByteByKey(theDoc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(theDoc), true);
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	public void downloadUSSupportingDoc() {
		try {
			/*Doc theDoc=DocService.instance().findByKey(companyUnitStandardList.get(0).getTrainingProviderDocParent().getDoc().getId());
			Faces.sendFile(theDoc.getData(), theDoc.getOriginalFname(), true);*/
			
			DocService docService=new DocService();
			Doc theDoc=companyUnitStandardList.get(0).getTrainingProviderDocParent().getDoc();
			DocByte docByte = docService.findDocByteByKey(theDoc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(theDoc), true);
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadSkillsProgSupportingDoc() {
		try {
			/*Doc theDoc=DocService.instance().findByKey(tpSkillsProgrammeList.get(0).getTrainingProviderDocParent().getDoc().getId());
			Faces.sendFile(theDoc.getData(), theDoc.getOriginalFname(), true);*/
			
			DocService docService=new DocService();
			Doc theDoc=tpSkillsProgrammeList.get(0).getTrainingProviderDocParent().getDoc();
			DocByte docByte = docService.findDocByteByKey(theDoc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(theDoc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadSkillsSetSupportingDoc() {
		try {
			/*Doc theDoc=DocService.instance().findByKey(trainingProviderSkillsSetList.get(0).getTrainingProviderDocParent().getDoc().getId());
			Faces.sendFile(theDoc.getData(), theDoc.getOriginalFname(), true);*/
			
			DocService docService=new DocService();
			Doc theDoc=trainingProviderSkillsSetList.get(0).getTrainingProviderDocParent().getDoc();
			DocByte docByte = docService.findDocByteByKey(theDoc.getId());
			Faces.sendFile(docByte.getData(), GenericUtility.buidFileName(theDoc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Adds the unit to list.
	 */
	public void addNonSetaUnitStandardToList() {
		try {
			if (unitStandard==null){
				throw new Exception("Please select Unit Standard");
			}
			if (unitStandard != null && !unitStandardList.contains(unitStandard)) {
				if(usUplaoaded !=null && usUplaoaded)
				{
					if(companyUnitStandardList !=null && companyUnitStandardList.size()>0)
					{
						if(companyUnitStandardList.get(0).getTrainingProviderDocParent() !=null)
						{
							CompanyUnitStandard compUS = new CompanyUnitStandard();
							compUS.setUnitStandard(unitStandard);
							compUS.setTrainingProviderDocParent(companyUnitStandardList.get(0).getTrainingProviderDocParent() );
							companyUnitStandardList.add(compUS);
						}
					}
					
				}
				this.unitStandardList.add(unitStandard);
				prepareNewQualification();
				populateSkillsProgramByUnitStandardsList();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("us.aready.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the Skills Program to list.
	 */
	public void addNonSetaSkillsProgramToList() {
		try {
			if (skillsProgram==null){
				throw new Exception("Please select Skills Programme");
			}
			if (skillsProgram != null && !skillsProgramList.contains(skillsProgram)) {
				if(spUplaoaded !=null && spUplaoaded)
				{
					if(tpSkillsProgrammeList !=null && tpSkillsProgrammeList.size()>0)
					{
						if(tpSkillsProgrammeList.get(0).getTrainingProviderDocParent() !=null)
						{
							TrainingProviderSkillsProgramme stSP = new TrainingProviderSkillsProgramme();
							stSP.setSkillsProgram(skillsProgram);
							stSP.setTrainingProviderDocParent(tpSkillsProgrammeList.get(0).getTrainingProviderDocParent());
							tpSkillsProgrammeList.add(stSP);
						}
					}
					
				}
				this.skillsProgramList.add(skillsProgram);
				prepareNewSkillsProgram();
				resetDoneQualification();
			} else {
				addWarningMessage(super.getEntryLanguage("skills.program.already.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void prepareNewSkillsProgram() {
		skillsProgram = new SkillsProgram();
	}
	
	public void resetDoneQualification()
	{
		doneQualification=false;
		runClientSideUpdate("pgNonSetaQual");
	}
	
	public void addNonSetaSKillsProgrammes() {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualificationList);
				for(SkillsProgram sp:l)
				{
					if(!skillsProgramList.contains(sp))
					{
						sp.setCannotRemove(true);
						skillsProgramList.add(sp);
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
	 * Adds the qualification to list.
	 */
   public void addQualificationToList() {
	   try {
			 checkIfQualAdded();
			  CompanyQualifications companyQualification = new CompanyQualifications(company, qualification, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			  companyQualificationsService.create(companyQualification);
			  companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
			  tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
			  addInfoMessage("Qualification added");
			  if(trainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
			  {
				  addSKillsProgrammes();
			  }
			  prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	  
	  public void checkIfQualAdded() throws Exception
	  {
		  if(qualification !=null)
		  {
			  for(CompanyQualifications qc:companyQualifications)
			  {
				  if(qualification.equals(qc.getQualification()))
				  {
					  throw new Exception("Qualification already in the list");
				  }
			  }
		  }
		  else
		  {
			  throw new Exception("Please Select Qualification");
		  }
		  
	  }
	  
	  public void addSKillsProgrammes() {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			if (companyQualifications != null && companyQualifications.size() != 0) {
				List<Qualification> qualList=new ArrayList<>();
				for(CompanyQualifications qc:companyQualifications)
				{
					qualList.add(qc.getQualification());
				}
				l = unitStandardsService.findByQualificationList(qualList);
				for(SkillsProgram sp:l)
				{
					addTpSkillsProgrammes(sp,false);
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	  
	public void addTpSkillsProgrammes(SkillsProgram sp,boolean showMssg) throws Exception
	{
		boolean canAdd=true;
		for(TrainingProviderSkillsProgramme tpSp:tpSkillsProgramList)
		{
			if(tpSp.getSkillsProgram().equals(sp))
			{
				canAdd=false;
				break;
			}
		}
		
		if(canAdd)
		{
			TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme(sp, trainingProviderApplication);
			trainingProviderSkillsProgrammeService.create(tpSp);
			tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		}
		else
		{
			if(showMssg)
			{
				 throw new Exception("Skills Programme already in the list");
			}
			
		}
	}
	 
	public List<Qualification> completeLeaningProgrameQualification(String desc) {
		QualificationService qualificationService = new QualificationService();
		List<Qualification> l = null;
		try {
			if(learningProgrammeQualification !=null) {
				l = qualificationService.findLearningProgrammeByQual(desc,learningProgrammeQualification);
			}
			
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void checkIfNoDocumentIsRequired()
	{
		showComplete=true;
		for(AuditorMonitorReview rev:auditorMonitorReviewDataModelList)
		{
			if((rev.getIsNotRelevant()==null ||  rev.getIsNotRelevant()==false) && rev.getEvidenceRequiredEvaluatorOutcome()==null || rev.getEvidenceRequiredEvaluatorOutcome()==YesNoEnum.No ||
			rev.getComment()==null || rev.getComment().length()==0 || rev.getComment().isEmpty())
			{
				showComplete=false;
				break;
			}
		}
	}
	
  /**
	 * Prepare new qualification.
	 */
	public void prepareNewQualification() {
		this.qualification = new Qualification();
		this.unitStandard = new UnitStandards();
		this.skillsSet=null;
		
	}
	

	private void prepNewSME() {
		try {
			sitesSme = new SitesSme(company);
			sitesSmes = sitesSmeService.allSitesSmeByCompany(company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void smeInsert() {
		try {
			sitesSmeService.create(sitesSme);
			prepNewSME();
			addInfoMessage(super.getEntryLanguage("insert.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void smeDelete() {
		try {
			sitesSmeService.delete(sitesSme);
			prepNewSME();
			addInfoMessage(super.getEntryLanguage("delete.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				AddressService.instance().copyFromToAddress(company.getResidentialAddress(), company.getPostalAddress());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
			this.company = companyService.findByKeyResolveDoc(company.getId());
			companyService.prepareDocs(ConfigDocProcessEnum.TP, company, CompanyUserTypeEnum.Company);
			this.updateCompanyBool = false;
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prep for update.
	 */
	public void prepForUpdate() {
		if (company.getResidentialAddress() == null) {
			this.company.setResidentialAddress(new Address(company));
		}
		if (company.getPostalAddress() == null) {
			this.company.setPostalAddress(new Address(company));
		}
		this.updateCompanyBool = true;
		logger.info("");
	}
	
	
	public void storeEvidenceFile(FileUploadEvent event) {
		try {
			if (doc != null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());

				if (doc.getId() != null)
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc = new Doc();
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				doc.setTargetClass(auditorMonitorReview.getClass().getName());
				doc.setTargetKey(auditorMonitorReview.getId());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
							getSessionUI().getActiveUser());
				auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
				
			}
			doc = null;
			super.runClientSideExecute("PF('dlgUploadEvidence').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeEvidenceFileVersionTwo(FileUploadEvent event) {
		try {
			if (doc != null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());

				if (doc.getId() != null)
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				auditorMonitorReviewEvidanceRequiredDataModelListInfo();
			} else {
				doc = new Doc();
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				doc.setTargetClass(auditorMonitorReview.getClass().getName());
				doc.setTargetKey(auditorMonitorReview.getId());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
							getSessionUI().getActiveUser());
				auditorMonitorReviewEvidanceRequiredDataModelListInfo();
				
			}
			doc = null;
			super.runClientSideExecute("PF('dlgUploadEvidenceV2').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * complete task.
	 */
	@SuppressWarnings("unchecked")
	public void completeTask() {
		try {
			exceptions=null;
			companyProblem=null;
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
				checkIfNoDocumentIsRequired();
				if (!showComplete) {
					throw new Exception("Please Provide Evaluator Outcome / Comment For Missing Entries");
				}
			}
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				documentChecks(company);
//				validateSelfEvaluation(auditorMonitorReviewDataModelList);
				validateSelfEvaluationLazyLoad();
			} else {
	        	companyProblem = "";
	        }
			
			if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit)
			{
				validateSiteVisit(trainingProviderApplication);
//				validateSelfEvaluationComments(auditorMonitorReviewDataModelList);
				validateSelfEvaluationCommentsLazyLoad();
				checkIfQualificationIsApprove();
			}
			trainingProviderApplication.setSiteVisitDone(onAfterSiteVistDate);
			if (companyProblem==null||companyProblem.length() == 0) {
				companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsProgramList);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) tpLearnership);
				companyService.completeTPTask(super.getEntryLanguage("new.training.provider.for.approval"), trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPNewRequiresApproval, MailTagsEnum.CompanyName, company.getCompanyName()), auditorMonitorReviewDataModelList,trainingProviderApplication);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			else
			{
				exceptions=companyProblem;
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void checkIfQualificationIsApprove() throws Exception
	{
		boolean approve=false;
		if(companyQualifications !=null && companyQualifications.size()>0)
		{
			for(CompanyQualifications qual:companyQualifications)
			{
				if(qual.getAccept()!=null && qual.getAccept()==true)
				{
					approve=true;
				}
			}
		}
		
		if(!approve)
		{
			if(unitStandards !=null && unitStandards.size()>0)
			{
				for(CompanyUnitStandard qual:unitStandards)
				{
					if(qual.getAccept()!=null && qual.getAccept()==true)
					{
						approve=true;
					}
				}
			}
		}
		if(!approve)
		{
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0)
			{
				for(TrainingProviderSkillsProgramme qual:tpSkillsProgramList)
				{
					if(qual.getAccept()!=null && qual.getAccept()==true)
					{
						approve=true;
					}
				}
			}
			
		}
		
		if(!approve)
		{
			if(tpSkillsSetList !=null && tpSkillsSetList.size()>0)
			{
				for(TrainingProviderSkillsSet qual:tpSkillsSetList)
				{
					if(qual.getAccept()!=null && qual.getAccept()==true)
					{
						approve=true;
					}
				}
			}
			
		}
		
		if(!approve)
		{
			if(tpLearnership !=null && tpLearnership.size()>0)
			{
				for(TrainingProviderLearnership qual:tpLearnership)
				{
					if(qual.getAccept()!=null && qual.getAccept()==true)
					{
						approve=true;
					}
				}
			}
			
		}
		if(!approve)
		{
			throw new Exception("Please approve at least one qualificaiton, unit standard or skills programme");
		}
	}
	
	public void downloadETQTP004AccreditationSiteVisitReport() 
	{
		try {
			trainingProviderApplicationService.sendETQTP004AccreditationSiteVisitReport(trainingProviderApplication, false, null,null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Approve task.
	 */
	public void approveNonSETATPApplication() {
		try {
			checkIfQualificationIsApprove();
			companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsProgramList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
			//nonSetaCompanyService.approveNonSETATPApplication(getSessionUI().getTask(),trainingProviderApplication);
			trainingProviderApplicationService.approveNonTtApplicationAndSendCertificate(trainingProviderApplication, companyQualifications, unitStandards,(ArrayList<TrainingProviderSkillsProgramme>) tpSkillsProgramList,getSessionUI().getTask(),tpSkillsSetList);
			
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	/**
	 * Approve task.
	 */
	public void completeNonSETATPApplication() {
		try {
			validateSupportingDocs();
			trainingProviderApplicationService.completeNonSETATPApplication(trainingProviderApplication, getSessionUI().getTask(), getSessionUI().getActiveUser(), 
					companyUnitStandardList, companyQualificationsList, trainingProviderSkillsSetList, docParentList, tpSkillsProgrammeList);
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	/**Checks if supporting documents are uploaded*/
	public void validateSupportingDocs() throws Exception
	{
		if(qualificationList !=null && qualificationList.size()>0 && (qualUplaoaded ==null || !qualUplaoaded))
		{
			throw new Exception("Please upload supporting document for qualification(s)");
		}
		else if(unitStandardList !=null && unitStandardList.size()>0 && (usUplaoaded ==null ||  !usUplaoaded))
		{
			throw new Exception("Please upload supporting document for Unit Standard(s) ");
		}
		else if(skillsProgramList !=null && skillsProgramList.size()>0 &&  (spUplaoaded ==null || !spUplaoaded))
		{
			throw new Exception("Please upload supporting document for Skills Programme(s) ");
		}
		else if(skillsSetList !=null && skillsSetList.size()>0 && (ssUplaoaded ==null || !ssUplaoaded))
		{
			throw new Exception("Please upload  supporting document for Skills SetList(s)");
		}
	}
	
	/**Reject task*/
	public void rejectNonSETATPApplication() {
		try {
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				companyService.rejectNonSetaTask(getEntryLanguage("non.seta.training.provider.application.was.rejected"), this.trainingProviderApplication.getId(), this.trainingProviderApplication.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPRejected, MailTagsEnum.CompanyName, company.getCompanyName()),selectedRejectReason,trainingProviderApplication,finalRejection);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void updateSiteVisitDate(){
		try {
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	private void validateSelfEvaluationComments(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2)
		{
			if(audit !=null)
			{
				if(audit.getComment()==null || audit.getComment().isEmpty() ||audit.getComment().equals("") )
				{
					throw new Exception("Please provide self evaluation comments");
				}
				if(audit.getEvidenceRequiredEvaluatorOutcome()==null && audit.getIsNotRelevant() !=null && audit.getIsNotRelevant()==false)
				{
					throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
				}
			}
		}
	}
	
	private void validateSelfEvaluationCommentsLazyLoad() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeCommentNotProvided(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId()) > 0) {
			throw new Exception("Please provide on self evaluation: Evaluator Outcome: Evidence Available & Comments where missing");
		}
	}

	private void validateSelfEvaluation(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2)
		{
			if(audit.getEvidenceRequired()==null && audit.getIsNotRelevant() !=null &&  audit.getIsNotRelevant()==false)
			{
				throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
			}
			
			if(audit.getEvidenceRequiredEvaluatorOutcome() !=null && audit.getEvidenceRequiredEvaluatorOutcome()==YesNoEnum.No && (audit.getDocs()==null ||  audit.getDocs().size()<1))
			{
				throw new Exception("Please upload required  evidence");
			}
		}
		
	}
	
	private void validateSelfEvaluationLazyLoad() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId()) > 0) {
			throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
		}
		
		if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeIsTypeAndNoDocProvided(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId(), YesNoEnum.No ) > 0) {
			throw new Exception("Please upload required evidence where applicable.");
		}
	}

	private void validateSiteVisit(TrainingProviderApplication tpApplication) throws Exception {
		
		if(tpApplication.getSiteVisitDate()==null || tpApplication.getSiteVisitReportDate()==null)
		{
			throw new Exception("Please complete site visit details");
		}
	}

	private void documentChecks(Company entity) throws Exception {
		boolean error = false;
		String err="";
		if (company.getDocs() != null) {
			for (Doc doc : company.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error = true;
					err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>";
					companyProblem=err;
				}
			}
		}
		if (error) throw new Exception(err);
	}

	/**
	 * complete task.
	 */
	public void completeTask_OLD() {
		try {
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				companyProblem = companyService.doCheck(company);
				List<Company> companyList = new ArrayList<>();
				companyList.add(company);
				companyService.doChecksUserCompany(null, companyList, false, true);
				
			} else {
				companyProblem = "";
			}
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
				List<Company> companyList = new ArrayList<>();
				companyList.add(company);
				companyService.doChecksUserCompany(trainingProviderUser, companyList, true, true);
				//Ensuring that a minimum of two contact person details are captured 
				List<CompanyUsers> companyUserList=companyUsersService.findByCompany(company);
				if(companyUserList.size()<2){throw new Exception("Please ensure that a minimum of two contact person details are captured");}
			}
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload) {
				List<Company> companyList = new ArrayList<>();
				companyList.add(company);
				companyService.doChecksUserCompany(trainingProviderUser, companyList, true, false);
			}
			
			
			if (companyProblem.length() == 0) {

				companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);

				companyService.completeTask(super.getEntryLanguage("new.training.provider.for.approval"), company.getId(), company.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPNewRequiresApproval, MailTagsEnum.CompanyName, company.getCompanyName()), auditorMonitorReviewDataModelList);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			this.exceptions = e.getMessage();
			e.printStackTrace();
		}

	}

	/**
	 * approve task.
	 */
	public void approveTask() {
		try {
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				companyProblem = companyService.doCheck(company);
			} else {
				companyProblem = "";
			}
			if (companyProblem.length() == 0) {
				companyService.approveTask(getSessionUI().getTask(), getSessionUI().getActiveUser(), this.company,trainingProviderApplication,reviewCommitteeMeeting);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			this.exceptions = e.getMessage();
		}

	}
	
	public void approveTtApplicationAndSendCertificate()
	{
		try {
			
			trainingProviderApplicationService.approveTtApplicationAndSendCertificate(trainingProviderApplication, companyQualifications, unitStandards,(ArrayList<TrainingProviderSkillsProgramme>) tpSkillsProgramList,getSessionUI().getTask(),tpSkillsSetList);
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void approveLegacyApplication()
	{
		try {
			checkIfQualificationIsApprove();
			companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsProgramList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpLearnership);
			trainingProviderApplicationService.approveTtApplicationAndSendCertificateLegacyApproval(trainingProviderApplication, companyQualifications, unitStandards,(ArrayList<TrainingProviderSkillsProgramme>) tpSkillsProgramList,getSessionUI().getTask(),tpSkillsSetList);
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * reject task.
	 */
	@SuppressWarnings("unchecked")
	public void rejectTask() {
		try {
			
			if(trainingProviderApplication.getApprovalStatus()==ApprovalEnum.RejectedByEQTA){
				selectedRejectReason=(ArrayList<RejectReasons>) rejectReason;
			}
			if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit){
				if(auditorMonitorReviewDataModelList !=null && auditorMonitorReviewDataModelList.size()>0){
					validateSelfEvaluationComments(auditorMonitorReviewDataModelList);
					auditorMonitorReviewService.update((List<IDataEntity>) (List<?>) auditorMonitorReviewDataModelList);
				}
			}
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				companyService.rejectTPTask(getEntryLanguage("training.provider.form.was.rejected"), this.trainingProviderApplication.getId(), this.trainingProviderApplication.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPRejected, MailTagsEnum.CompanyName, company.getCompanyName()),selectedRejectReason,trainingProviderApplication);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	/**
	 * reject task.
	 */
	@SuppressWarnings("unchecked")
	public void finalRejectTask() {
		try {
			if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit){
				if(auditorMonitorReviewDataModelList !=null && auditorMonitorReviewDataModelList.size()>0){
					validateSelfEvaluationComments(auditorMonitorReviewDataModelList);
					auditorMonitorReviewService.update((List<IDataEntity>) (List<?>) auditorMonitorReviewDataModelList);
				}
			}
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				companyService.finalRejectTPTask(getEntryLanguage("training.provider.form.was.rejected"),  getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPRejected, MailTagsEnum.CompanyName, company.getCompanyName()),selectedRejectReason,trainingProviderApplication);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
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
			if (doc.getId() == null) {
				// new document
				if (!docUploadUser && (uploadCompanyDoc==null || uploadCompanyDoc==false)) {
					// if document is for company
					companyService.documentUpload(doc.getCompany(), getSessionUI().getActiveUser());
				} 
				else if (uploadCompanyDoc !=null && uploadCompanyDoc) 
				{
					companyService.uploadCompanyDocs(doc, company, trainingProviderUser, trainingProviderApplication);
				}
				else {
					// if document is for user
					doc.setCompany(null);
					doc.setForUsers(trainingProviderUser);
					usersService.documentUpload(trainingProviderUser, getSessionUI().getActiveUser());
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

	/**
	 * Adds the qualification.
	 */
	public void addQualification() {
		try {
			if (qualification != null) {
				CompanyQualifications cq = new CompanyQualifications(company, qualification);
				companyQualificationsService.create(cq);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.qualification = null;
				companyQualifications = companyQualificationsService.findByCompany(company);
			} else {
				addWarningMessage("Please select a qualification before adding.");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	/**
	 * Adds the unit standard.
	 */
	public void addUnitStandard() {
		try {
			if (unitStandard != null) {
				CompanyUnitStandard cu = new CompanyUnitStandard(company, unitStandard);
				companyUnitStandardService.create(cu);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.unitStandard = null;
				unitStandards = companyUnitStandardService.findByCompany(company);
			} else {
				addWarningMessage("Please select a unit standard before adding.");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}

	}
	
	/**
	 * Adds the qualification.
	 */
	public void addNewQualification() {
		try {
			if (qualification != null) {
				CompanyQualifications cq = new CompanyQualifications(company, qualification);
				companyQualificationsService.create(cq);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.qualification = null;
				companyQualifications = companyQualificationsService.findByCompany(company);
			} else {
				addWarningMessage("Please select a qualification before adding.");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	/**
	 * Adds the unit standard.
	 */
	public void addNewUnitStandard() {
		try {
			if (unitStandard != null) {
				CompanyUnitStandard cu = new CompanyUnitStandard(company, unitStandard);
				companyUnitStandardService.create(cu);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				this.unitStandard = null;
				unitStandards = companyUnitStandardService.findByCompany(company);
			} else {
				addWarningMessage("Please select a unit standard before adding.");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}

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
	
   public ArrayList<ReviewCommitteeMeeting> getTPReviewCommitteeMeetingList() {
    	ArrayList<ReviewCommitteeMeeting> list=new ArrayList<>();
    	if(trainingProviderApplication.getSiteVisitDate() !=null)
    	{
			try {
				//list = (ArrayList<ReviewCommitteeMeeting>) service.allActiveReviewCommitteeMeeting(trainingProviderApplication.getSiteVisitDate());
				list = (ArrayList<ReviewCommitteeMeeting>) service.allActiveReviewCommitteeMeeting();
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}
    	}
    	return list;
	}


	/**
	 * Clear company.
	 */
	public void clearCompany() {
		updateCompanyBool = false;
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
	 * Gets the company qualifications.
	 *
	 * @return the company qualifications
	 */
	public List<CompanyQualifications> getCompanyQualifications() {
		return companyQualifications;
	}

	/**
	 * Sets the company qualifications.
	 *
	 * @param companyQualifications
	 *            the new company qualifications
	 */
	public void setCompanyQualifications(List<CompanyQualifications> companyQualifications) {
		this.companyQualifications = companyQualifications;
	}

	/**
	 * Gets the unit standards.
	 *
	 * @return the unit standards
	 */
	public List<CompanyUnitStandard> getUnitStandards() {
		return unitStandards;
	}

	/**
	 * Sets the unit standards.
	 *
	 * @param unitStandards
	 *            the new unit standards
	 */
	public void setUnitStandards(List<CompanyUnitStandard> unitStandards) {
		this.unitStandards = unitStandards;
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
	 * Gets the display user info.
	 *
	 * @return the display user info
	 */
	public Boolean getDisplayUserInfo() {
		return displayUserInfo;
	}

	/**
	 * Sets the display user info.
	 *
	 * @param displayUserInfo
	 *            the new display user info
	 */
	public void setDisplayUserInfo(Boolean displayUserInfo) {
		this.displayUserInfo = displayUserInfo;
	}

	/**
	 * Gets the training provider user.
	 *
	 * @return the training provider user
	 */
	public Users getTrainingProviderUser() {
		return trainingProviderUser;
	}

	/**
	 * Sets the training provider user.
	 *
	 * @param trainingProviderUser
	 *            the new training provider user
	 */
	public void setTrainingProviderUser(Users trainingProviderUser) {
		this.trainingProviderUser = trainingProviderUser;
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

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	/**
	 * @return the auditorMonitorReviewDataModelList
	 */
	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelList() {
		return auditorMonitorReviewDataModelList;
	}

	/**
	 * @param auditorMonitorReviewDataModelList
	 *            the auditorMonitorReviewDataModelList to set
	 */
	public void setAuditorMonitorReviewDataModelList(List<AuditorMonitorReview> auditorMonitorReviewDataModelList) {
		this.auditorMonitorReviewDataModelList = auditorMonitorReviewDataModelList;
	}

	/**
	 * @return the organisationTypeList
	 */
	public List<OrganisationType> getOrganisationTypeList() {
		return organisationTypeList;
	}

	/**
	 * @param organisationTypeList
	 *            the organisationTypeList to set
	 */
	public void setOrganisationTypeList(List<OrganisationType> organisationTypeList) {
		this.organisationTypeList = organisationTypeList;
	}

	/**
	 * @return the mAX_EMAIL_SIZE
	 */
	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	/**
	 * @param mAX_EMAIL_SIZE
	 *            the mAX_EMAIL_SIZE to set
	 */
	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	/**
	 * @return the mAX_COMPANY_NAME_SIZE
	 */
	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	/**
	 * @param mAX_COMPANY_NAME_SIZE
	 *            the mAX_COMPANY_NAME_SIZE to set
	 */
	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	/**
	 * @return the mAX_COMPANY_TRADE_NAME_SIZE
	 */
	public Long getMAX_COMPANY_TRADE_NAME_SIZE() {
		return MAX_COMPANY_TRADE_NAME_SIZE;
	}

	/**
	 * @param mAX_COMPANY_TRADE_NAME_SIZE
	 *            the mAX_COMPANY_TRADE_NAME_SIZE to set
	 */
	public void setMAX_COMPANY_TRADE_NAME_SIZE(Long mAX_COMPANY_TRADE_NAME_SIZE) {
		MAX_COMPANY_TRADE_NAME_SIZE = mAX_COMPANY_TRADE_NAME_SIZE;
	}

	/**
	 * @return the mAX_ADDRESS_LINE_SIZE
	 */
	public Long getMAX_ADDRESS_LINE_SIZE() {
		return MAX_ADDRESS_LINE_SIZE;
	}

	/**
	 * @param mAX_ADDRESS_LINE_SIZE
	 *            the mAX_ADDRESS_LINE_SIZE to set
	 */
	public void setMAX_ADDRESS_LINE_SIZE(Long mAX_ADDRESS_LINE_SIZE) {
		MAX_ADDRESS_LINE_SIZE = mAX_ADDRESS_LINE_SIZE;
	}

	/**
	 * @return the mAX_ADDRESS_CODE_SIZE
	 */
	public Long getMAX_ADDRESS_CODE_SIZE() {
		return MAX_ADDRESS_CODE_SIZE;
	}

	/**
	 * @param mAX_ADDRESS_CODE_SIZE
	 *            the mAX_ADDRESS_CODE_SIZE to set
	 */
	public void setMAX_ADDRESS_CODE_SIZE(Long mAX_ADDRESS_CODE_SIZE) {
		MAX_ADDRESS_CODE_SIZE = mAX_ADDRESS_CODE_SIZE;
	}

	/**
	 * @return the mAX_TAX_NUMBER
	 */
	public Long getMAX_TAX_NUMBER() {
		return MAX_TAX_NUMBER;
	}

	/**
	 * @param mAX_TAX_NUMBER
	 *            the mAX_TAX_NUMBER to set
	 */
	public void setMAX_TAX_NUMBER(Long mAX_TAX_NUMBER) {
		MAX_TAX_NUMBER = mAX_TAX_NUMBER;
	}

	/**
	 * @return the mAX_VAX_NUMBER
	 */
	public Long getMAX_VAX_NUMBER() {
		return MAX_VAX_NUMBER;
	}

	/**
	 * @param mAX_VAX_NUMBER
	 *            the mAX_VAX_NUMBER to set
	 */
	public void setMAX_VAX_NUMBER(Long mAX_VAX_NUMBER) {
		MAX_VAX_NUMBER = mAX_VAX_NUMBER;
	}

	/**
	 * @return the mAX_FAX_NUMBER
	 */
	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	/**
	 * @param mAX_FAX_NUMBER
	 *            the mAX_FAX_NUMBER to set
	 */
	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
	}

	/**
	 * @return the mAX_NUMBER_OF_EMPLOYEES_SIZE
	 */
	public Long getMAX_NUMBER_OF_EMPLOYEES_SIZE() {
		return MAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	/**
	 * @param mAX_NUMBER_OF_EMPLOYEES_SIZE
	 *            the mAX_NUMBER_OF_EMPLOYEES_SIZE to set
	 */
	public void setMAX_NUMBER_OF_EMPLOYEES_SIZE(Long mAX_NUMBER_OF_EMPLOYEES_SIZE) {
		MAX_NUMBER_OF_EMPLOYEES_SIZE = mAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	/**
	 * @return the companyRegistrationNumberFormat
	 */
	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	/**
	 * @param companyRegistrationNumberFormat
	 *            the companyRegistrationNumberFormat to set
	 */
	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	/**
	 * @return the companyLevyNumberFormat
	 */
	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	/**
	 * @param companyLevyNumberFormat
	 *            the companyLevyNumberFormat to set
	 */
	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	/**
	 * @return the companyVatNumberFormat
	 */
	public String getCompanyVatNumberFormat() {
		return companyVatNumberFormat;
	}

	/**
	 * @param companyVatNumberFormat
	 *            the companyVatNumberFormat to set
	 */
	public void setCompanyVatNumberFormat(String companyVatNumberFormat) {
		this.companyVatNumberFormat = companyVatNumberFormat;
	}

	/**
	 * @return the allowOnlyNumber
	 */
	public String getAllowOnlyNumber() {
		return allowOnlyNumber;
	}

	/**
	 * @param allowOnlyNumber
	 *            the allowOnlyNumber to set
	 */
	public void setAllowOnlyNumber(String allowOnlyNumber) {
		this.allowOnlyNumber = allowOnlyNumber;
	}

	/**
	 * @return the tELPHONE_FORMAT
	 */
	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	/**
	 * @param tELPHONE_FORMAT
	 *            the tELPHONE_FORMAT to set
	 */
	public void setTELPHONE_FORMAT(String tELPHONE_FORMAT) {
		TELPHONE_FORMAT = tELPHONE_FORMAT;
	}

	/**
	 * @return the cELLPHONE_FORMAT
	 */
	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	/**
	 * @param cELLPHONE_FORMAT
	 *            the cELLPHONE_FORMAT to set
	 */
	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}

	/**
	 * @return the fAX_NUMBER_FORMAT
	 */
	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	/**
	 * @param fAX_NUMBER_FORMAT
	 *            the fAX_NUMBER_FORMAT to set
	 */
	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	/**
	 * @return the sitesSme
	 */
	public SitesSme getSitesSme() {
		return sitesSme;
	}

	/**
	 * @param sitesSme
	 *            the sitesSme to set
	 */
	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	/**
	 * @return the sitesSmes
	 */
	public List<SitesSme> getSitesSmes() {
		return sitesSmes;
	}

	/**
	 * @param sitesSmes
	 *            the sitesSmes to set
	 */
	public void setSitesSmes(List<SitesSme> sitesSmes) {
		this.sitesSmes = sitesSmes;
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
	
	/**Prepare contact Person, Assessor or Facilitator*/
	public void preparContactPersonUpdate()
	{
		contactPerson=selectedTPContsctPerson.getUser();
		contactPerson.setDesignation(selectedTPContsctPerson.getDesignation());
	}
	
	/**Clear contact Person*/
	public void clearContactPerson()
	{
		selectedTPContsctPerson=new CompanyUsers();
	}
	
	/**Update Contact Person*/
	public void updateContactPerson()
	{
		try {
			companyUsersService.updateContactPerson(contactPerson,selectedTPContsctPerson);
			addInfoMessage("Contact Person information updated");
			clearContactPerson();
			contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
			facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
			runClientSideExecute("PF('dlgContact').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * @return the contactPerson
	 */
	public Users getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(Users contactPerson) {
		this.contactPerson = contactPerson;
	}



	/**
	 * @return the contactPersonList
	 */
	public List<CompanyUsers> getContactPersonList() {
		return contactPersonList;
	}

	/**
	 * @param contactPersonList the contactPersonList to set
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
	 * @param facilitatorAssessorList the facilitatorAssessorList to set
	 */
	public void setFacilitatorAssessorList(List<CompanyUsers> facilitatorAssessorList) {
		this.facilitatorAssessorList = facilitatorAssessorList;
	}

	/**
	 * @return the selectedTPContsctPerson
	 */
	public CompanyUsers getSelectedTPContsctPerson() {
		return selectedTPContsctPerson;
	}

	/**
	 * @param selectedTPContsctPerson the selectedTPContsctPerson to set
	 */
	public void setSelectedTPContsctPerson(CompanyUsers selectedTPContsctPerson) {
		this.selectedTPContsctPerson = selectedTPContsctPerson;
	}

	/**
	 * @return the addAssFacilitator
	 */
	public boolean isAddAssFacilitator() {
		return addAssFacilitator;
	}

	/**
	 * @param addAssFacilitator the addAssFacilitator to set
	 */
	public void setAddAssFacilitator(boolean addAssFacilitator) {
		this.addAssFacilitator = addAssFacilitator;
	}

	/**
	 * @return the selectedRejectReason
	 */
	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	/**
	 * @param selectedRejectReason the selectedRejectReason to set
	 */
	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	/**
	 * @return the rejectReason
	 */
	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public TrainingProviderApplicationService getTrainingProviderApplicationService() {
		return trainingProviderApplicationService;
	}

	public void setTrainingProviderApplicationService(
			TrainingProviderApplicationService trainingProviderApplicationService) {
		this.trainingProviderApplicationService = trainingProviderApplicationService;
	}

	public List<TrainingProviderSkillsProgramme> getTpSkillsProgramList() {
		return tpSkillsProgramList;
	}

	public void setTpSkillsProgramList(List<TrainingProviderSkillsProgramme> tpSkillsProgramList) {
		this.tpSkillsProgramList = tpSkillsProgramList;
	}

	public List<TrainingProviderSkillsSet> getTpSkillsSetList() {
		return tpSkillsSetList;
	}

	public void setTpSkillsSetList(List<TrainingProviderSkillsSet> tpSkillsSetList) {
		this.tpSkillsSetList = tpSkillsSetList;
	}

	/*public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}*/

	public List<NonSetaCompanyUsers> getNonSetaContactPersonList() {
		return nonSetaContactPersonList;
	}

	public void setNonSetaContactPersonList(List<NonSetaCompanyUsers> nonSetaContactPersonList) {
		this.nonSetaContactPersonList = nonSetaContactPersonList;
	}

	public List<NonSetaCompanyUsers> getNonSetaAssessorModList() {
		return nonSetaAssessorModList;
	}

	public void setNonSetaAssessorModList(List<NonSetaCompanyUsers> nonSetaAssessorModList) {
		this.nonSetaAssessorModList = nonSetaAssessorModList;
	}

	public boolean isOnAfterSiteVistDate() {
		return onAfterSiteVistDate;
	}

	public void setOnAfterSiteVistDate(boolean onAfterSiteVistDate) {
		this.onAfterSiteVistDate = onAfterSiteVistDate;
	}

	public boolean isNoDateProvided() {
		return noDateProvided;
	}

	public void setNoDateProvided(boolean noDateProvided) {
		this.noDateProvided = noDateProvided;
	}

	public List<DateChangeReasons> getDateChangeReasonSelectionList() {
		return dateChangeReasonSelectionList;
	}

	public void setDateChangeReasonSelectionList(List<DateChangeReasons> dateChangeReasonSelectionList) {
		this.dateChangeReasonSelectionList = dateChangeReasonSelectionList;
	}

	public List<DateChangeReasons> getDateChangeReasonAvalibleSelectionList() {
		return dateChangeReasonAvalibleSelectionList;
	}

	public void setDateChangeReasonAvalibleSelectionList(List<DateChangeReasons> dateChangeReasonAvalibleSelectionList) {
		this.dateChangeReasonAvalibleSelectionList = dateChangeReasonAvalibleSelectionList;
	}

	/**
	 * @return the finalRejection
	 */
	public boolean isFinalRejection() {
		return finalRejection;
	}

	/**
	 * @param finalRejection the finalRejection to set
	 */
	public void setFinalRejection(boolean finalRejection) {
		this.finalRejection = finalRejection;
	}

	/**
	 * @return the selectedCompanyQualification
	 */
	public CompanyQualifications getSelectedCompanyQualification() {
		return selectedCompanyQualification;
	}

	/**
	 * @param selectedCompanyQualification the selectedCompanyQualification to set
	 */
	public void setSelectedCompanyQualification(CompanyQualifications selectedCompanyQualification) {
		this.selectedCompanyQualification = selectedCompanyQualification;
	}

	/**
	 * @return the selectCompanyUnitStandard
	 */
	public CompanyUnitStandard getSelectCompanyUnitStandard() {
		return selectCompanyUnitStandard;
	}

	/**
	 * @param selectCompanyUnitStandard the selectCompanyUnitStandard to set
	 */
	public void setSelectCompanyUnitStandard(CompanyUnitStandard selectCompanyUnitStandard) {
		this.selectCompanyUnitStandard = selectCompanyUnitStandard;
	}

	/**
	 * @return the skillsProgram
	 */
	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	/**
	 * @param skillsProgram the skillsProgram to set
	 */
	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	/**
	 * @return the selectSkillsProgram
	 */
	public TrainingProviderSkillsProgramme getSelectSkillsProgram() {
		return selectSkillsProgram;
	}

	/**
	 * @param selectSkillsProgram the selectSkillsProgram to set
	 */
	public void setSelectSkillsProgram(TrainingProviderSkillsProgramme selectSkillsProgram) {
		this.selectSkillsProgram = selectSkillsProgram;
	}

	public Qualification getLearningProgrammeQualification() {
		return learningProgrammeQualification;
	}

	public void setLearningProgrammeQualification(Qualification learningProgrammeQualification) {
		this.learningProgrammeQualification = learningProgrammeQualification;
	}

	public AuditorMonitorReview getAuditorMonitorReview() {
		return auditorMonitorReview;
	}

	public void setAuditorMonitorReview(AuditorMonitorReview auditorMonitorReview) {
		this.auditorMonitorReview = auditorMonitorReview;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	public TrainingProviderSkillsSet getTpSkillsSet() {
		return tpSkillsSet;
	}

	public void setTpSkillsSet(TrainingProviderSkillsSet tpSkillsSet) {
		this.tpSkillsSet = tpSkillsSet;
	}

	public Boolean getEnableEdit() {
		return enableEdit;
	}

	public void setEnableEdit(Boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<UnitStandards> getUnitStandardList() {
		return unitStandardList;
	}

	public void setUnitStandardList(List<UnitStandards> unitStandardList) {
		this.unitStandardList = unitStandardList;
	}

	public ArrayList<SkillsProgram> getSkillsProgramList() {
		return skillsProgramList;
	}

	public void setSkillsProgramList(ArrayList<SkillsProgram> skillsProgramList) {
		this.skillsProgramList = skillsProgramList;
	}

	public ArrayList<SkillsSet> getSkillsSetList() {
		return skillsSetList;
	}

	public void setSkillsSetList(ArrayList<SkillsSet> skillsSetList) {
		this.skillsSetList = skillsSetList;
	}

	public int getResultsForSkillsProgramFound() {
		return resultsForSkillsProgramFound;
	}

	public void setResultsForSkillsProgramFound(int resultsForSkillsProgramFound) {
		this.resultsForSkillsProgramFound = resultsForSkillsProgramFound;
	}

	public List<SkillsProgram> getSkillsProgramByUnitStandardsList() {
		return skillsProgramByUnitStandardsList;
	}

	public void setSkillsProgramByUnitStandardsList(List<SkillsProgram> skillsProgramByUnitStandardsList) {
		this.skillsProgramByUnitStandardsList = skillsProgramByUnitStandardsList;
	}

	public Boolean getQualUplaoaded() {
		return qualUplaoaded;
	}

	public void setQualUplaoaded(Boolean qualUplaoaded) {
		this.qualUplaoaded = qualUplaoaded;
	}

	public Boolean getUsUplaoaded() {
		return usUplaoaded;
	}

	public void setUsUplaoaded(Boolean usUplaoaded) {
		this.usUplaoaded = usUplaoaded;
	}

	public Boolean getSpUplaoaded() {
		return spUplaoaded;
	}

	public void setSpUplaoaded(Boolean spUplaoaded) {
		this.spUplaoaded = spUplaoaded;
	}

	public Boolean getSsUplaoaded() {
		return ssUplaoaded;
	}

	public void setSsUplaoaded(Boolean ssUplaoaded) {
		this.ssUplaoaded = ssUplaoaded;
	}

	public Boolean getDoneQualification() {
		return doneQualification;
	}

	public void setDoneQualification(Boolean doneQualification) {
		this.doneQualification = doneQualification;
	}

	public String getDocFor() {
		return docFor;
	}

	public void setDocFor(String docFor) {
		this.docFor = docFor;
	}

	public String getDocNode() {
		return docNode;
	}

	public void setDocNode(String docNode) {
		this.docNode = docNode;
	}

	public TrainingProviderDocParent getDocParent() {
		return docParent;
	}

	public void setDocParent(TrainingProviderDocParent docParent) {
		this.docParent = docParent;
	}

	public List<TrainingProviderSkillsProgramme> getTpSkillsProgrammeList() {
		return tpSkillsProgrammeList;
	}

	public void setTpSkillsProgrammeList(List<TrainingProviderSkillsProgramme> tpSkillsProgrammeList) {
		this.tpSkillsProgrammeList = tpSkillsProgrammeList;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public List<TrainingProviderSkillsSet> getTrainingProviderSkillsSetList() {
		return trainingProviderSkillsSetList;
	}

	public void setTrainingProviderSkillsSetList(List<TrainingProviderSkillsSet> trainingProviderSkillsSetList) {
		this.trainingProviderSkillsSetList = trainingProviderSkillsSetList;
	}

	public Boolean getUploadCompanyDoc() {
		return uploadCompanyDoc;
	}

	public void setUploadCompanyDoc(Boolean uploadCompanyDoc) {
		this.uploadCompanyDoc = uploadCompanyDoc;
	}

	public List<TrainingProviderLearnership> getTpLearnership() {
		return tpLearnership;
	}

	public void setTpLearnership(List<TrainingProviderLearnership> tpLearnership) {
		this.tpLearnership = tpLearnership;
	}

	public Boolean getShowComplete() {
		return showComplete;
	}

	public void setShowComplete(Boolean showComplete) {
		this.showComplete = showComplete;
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

	public AuditorMonitorReview getSelectedAuditorMonitorReview() {
		return selectedAuditorMonitorReview;
	}

	public void setSelectedAuditorMonitorReview(AuditorMonitorReview selectedAuditorMonitorReview) {
		this.selectedAuditorMonitorReview = selectedAuditorMonitorReview;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewEvidanceRequiredDataModelList() {
		return auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public void setAuditorMonitorReviewEvidanceRequiredDataModelList(
			LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList) {
		this.auditorMonitorReviewEvidanceRequiredDataModelList = auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}
	
	

}
