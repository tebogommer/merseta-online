package  haj.com.ui;

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

import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.SDPCompany;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
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
import haj.com.service.DocumentTrackerService;
import haj.com.service.MailDef;
import haj.com.service.ReviewCommitteeMeetingService;
import haj.com.service.SDPCompanyService;
import haj.com.service.SDPExtensionOfScopeService;
import haj.com.service.SDPReAccreditationService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.TrainingSiteService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SaqaUsQualificationService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "sdpreaccreditationUI")
@ViewScoped
public class SDPReAccreditationUI extends AbstractUI {

	private SDPExtensionOfScopeService extensionOfScopeService = new SDPExtensionOfScopeService();
	private SDPReAccreditationService service = new SDPReAccreditationService();
	private List<SDPReAccreditation> sdpreaccreditationList = null;
	private List<SDPReAccreditation> sdpreaccreditationfilteredList = null;
	private SDPReAccreditation sdpreaccreditation = null;
	private LazyDataModel<SDPReAccreditation> dataModel; 
	//private Company selectedCompany;
	/** The exceptions. */
	private String exceptions;
	/** The Reject Reasons*/
	private List<RejectReasons> rejectReason=new ArrayList<>();
	/** The Training Provider Application*/
	private TrainingProviderApplication trainingProviderApplication;
	/** The company. */
	private Company company;
	/** The company List. */
	private List<Company> companyList;
	/** The update company bool. */
	private Boolean updateCompanyBool;
	/** The doc upload user. */
	private Boolean docUploadUser;
	/** The doc. */
	private Doc doc;
	/** The training provider user. */
	private Users trainingProviderUser;
	/** The company qualifications. */
	private List<CompanyQualifications> companyQualifications;
	/** The unit standards. */
	private List<CompanyUnitStandard> unitStandards;
	/** The Training Provider Skills Programme*/
	private List<TrainingProviderSkillsProgramme> tpSkillsProgramList;
	/** The Training Provider Skills Set*/
	private List<TrainingProviderSkillsSet> tpSkillsSetList;
	/** The Self Evaluation*/
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList= new ArrayList<>();
	/** Flag to check if date is after site visit*/
	private boolean onAfterSiteVistDate = false;
	/** Flag to check if user can add Assessor*/
	private boolean addAssFacilitator;
	/** The Review Committee Meeting*/
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	/** The Selected Reject Reason*/
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	/** The Date Change Reasons*/
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	/** The Date Change Reason Available Selection List*/
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	/**The Training Provider Application Service*/
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	/** Flag to check if date is provided*/
	private boolean noDateProvided = false;
	/** The company service. */
	private CompanyService companyService = new CompanyService();
	/** The users service. */
	private UsersService usersService = new UsersService();
	/** The Review Committee Meeting Service. */
	private ReviewCommitteeMeetingService rcmService = new ReviewCommitteeMeetingService();
	/** The company problem. */
	private String companyProblem;
	/** The company qualifications service. */
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	/** The SDP Re-Accreditation*/
	private SDPReAccreditation sdpReAccreditation=new SDPReAccreditation();
	/** The SDP Re-Accreditation Service*/
	private SDPReAccreditationService sdpReAccreditationService = new SDPReAccreditationService();
	/** The AuditorMonitorReviewService*/
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	/** Training Provider Contact Persons*/
	private List<CompanyUsers> contactPersonList;
	/** Training Provider Assessor/Facilitator*/
	private List<CompanyUsers> facilitatorAssessorList;
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	/** The company unit standard service. */
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
	/** The Training Provider Skills Programme Service*/
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
	/** The Training Provider Skills Set Service*/
	private TrainingProviderSkillsSetService trainingProviderSkillsSetSevice=new TrainingProviderSkillsSetService();
	/** The Next Date*/
	private Date nextDate;
	/**The Training Provider Application*/
	public static TrainingProviderApplication selectTrainingProviderApplication;
	/**Select TrainingProviderContactPerson*/
	private CompanyUsers selectedTPContsctPerson;
	/** The TP contact person. */
	private Users contactPerson;
	/**The Selected Company Qualifications*/
	private CompanyQualifications selectedCompanyQualification;
	/**The Selected Company Unit Standard*/
	private CompanyUnitStandard selectCompanyUnitStandard;
	/**The select Skills Program*/
	private TrainingProviderSkillsProgramme selectSkillsProgram;
	/**The Selected Skills Set*/
	private TrainingProviderSkillsSet tpSkillsSet;
	/** The qualification. */
	private Qualification qualification;
	/** The unit standard. */
	private UnitStandards unitStandard;
	/**The skills Set*/
	private SkillsSet skillsSet;
	/** Training provider Skills Program */
	private SkillsProgram skillsProgram;
	
	private List<TrainingProviderDocParent> docParentList = new ArrayList<>();
	private TrainingProviderDocParent docParent;
	private Boolean qualUplaoaded;
	private Boolean usUplaoaded;
	private Boolean spUplaoaded;
	private Boolean ssUplaoaded;
	private Boolean doneQualification;
	private String docNode;
	private String docFor;
	
	private TrainingSite trainingSite = null;
	
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();
	private AuditorMonitorReview selectedAuditorMonitorReview = null;

	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList;
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;

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
	 * Initialize method to get all SDPReAccreditation and prepare a for a create of a new SDPReAccreditation
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sdpreaccreditationInfo();
		if (super.getParameter("id", false) != null) {
		    if((getSessionUI().getTask().getWorkflowProcess()==ConfigDocProcessEnum.SDP_RE_SUBMISSION)) {
				populateResubimissionApplicationData();
			} else {
				populateTpApplicationData();
			}
		}
		nextDate = GenericUtility.addDaysToDate(new Date(), 1);
	}

    
    public void populateTpApplicationData() throws Exception {
    	
    	 sdpReAccreditation=sdpReAccreditationService.findByKey(getSessionUI().getTask().getTargetKey());
    	 trainingProviderApplication = trainingProviderApplicationService.findByKey(sdpReAccreditation.getTrainingProviderApplication().getId());
    	 if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
    		 trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId()));
		 } else {
			trainingSite = null;
		 }
//		 this.trainingProviderApplication=sdpReAccreditation.getTrainingProviderApplication();
//		 this.company = companyService.findByKey(trainingProviderApplication.getCompany().getId());
//		 this.company = companyService.findByKeyAndResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(), ConfigDocProcessEnum.TP);
    	 this.company = companyService.findByKeyAndOnlyResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(), ConfigDocProcessEnum.TP);
			
		 this.companyList = new ArrayList<>();
		 companyList.add(company);
//		 auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(sdpReAccreditation.getClass().getName(), sdpReAccreditation.getId());
		 auditorMonitorReviewEvidanceRequiredDataModelListInfo();
		
		 sdpCompanyDataModelInfo();
//		contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
		 assessorModeratorCompanySitesDataModelInfo();
//		facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		
		//Training Provider User
		trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(trainingProviderApplication.getUsers().getId(),getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User,trainingProviderApplication.getId(),trainingProviderApplication.getClass().getName());
		
		companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		
		unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		
		tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		
		tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
		populateRejectReasons();
		
		if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
			validateSiteVistData();
		}
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
					filters.put("targetClass", sdpReAccreditation.getClass().getName());
					filters.put("targetKey", sdpReAccreditation.getId());
					retorno = auditorMonitorReviewService.allAuditorMonitorReviewByTargetClassAndKeyNoDocResolve(first, pageSize, sortField, sortOrder, filters);
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
	
	public void sdpCompanyDataModelInfo() {
		sdpCompanyDataModel = new LazyDataModel<SDPCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDPCompany> retorno = new ArrayList<>();

			@Override
			public List<SDPCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null) {
						if (trainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = sdpCompanyService.allSdpLinkedToTrainingSiteSdpView(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getTrainingSite().getId());
							sdpCompanyDataModel.setRowCount(sdpCompanyService.countAllSdpLinkedToTrainingSiteSdpView(filters));
						}
					} else if (trainingProviderApplication.getCompany() != null && trainingProviderApplication.getCompany().getId() != null) {
						retorno = sdpCompanyService.allSdpLinkedToHoldingCompanySdpView(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getCompany().getId());
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
					if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null) {
						if (trainingProviderApplication.getTrainingSite().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToTrainingSite(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getTrainingSite().getId());
							assessorModeratorCompanySitesDataModel.setRowCount(assessorModeratorCompanySitesService.countAllAssessorModeratorLinkedToTrainingSite(filters));
						}
					} else {
						if (trainingProviderApplication.getCompany() != null && trainingProviderApplication.getCompany().getId() != null) {
							retorno = assessorModeratorCompanySitesService.allAssessorModeratorLinkedToHoldingCompany(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getCompany().getId());
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

    
	public void populateResubimissionApplicationData() throws Exception {
		sdpReAccreditation=sdpReAccreditationService.findByKey(getSessionUI().getTask().getTargetKey());
		this.trainingProviderApplication=sdpReAccreditation.getTrainingProviderApplication();
		//this.company = companyService.findByKey(trainingProviderApplication.getCompany().getId());
		this.company = companyService.findByKeyAndResolveDoc(trainingProviderApplication.getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(), ConfigDocProcessEnum.TP);
		this.company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
		this.company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
		
		//TP Contact Persons contactPersonList=trainingProviderContactPersonService.findByTP(trainingProviderApplication.getId());
		contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
		facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		
		//Training Provider User
		//trainingProviderUser = usersService.findByKey(trainingProviderApplication.getUsers().getId());
		trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(trainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP, CompanyUserTypeEnum.User,trainingProviderApplication.getId(),trainingProviderApplication.getClass().getName());
		
		companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		
		unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
		
		tpSkillsProgramList = trainingProviderSkillsProgrammeService.findByTrainingProvider(trainingProviderApplication.getId());
		
		tpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProvider(trainingProviderApplication.getId());
		
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
			} else {
				addWarningMessage("Provide A Minium Of One Reason For Date Change Before Proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
				//send email notiofications
				validateSiteVistData();
				addInfoMessage("Site Visit Date Set");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
	
	public void downloadETQTP004AccreditationSiteVisitReport() 
	{
		try {
			trainingProviderApplicationService.sendETQTP004AccreditationSiteVisitReport(trainingProviderApplication, false, null,null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public Doc getCompanyQualSupportingDoc()
	{
		Doc theDoc=null;
		try {
			if(companyQualifications !=null && companyQualifications.size()>0 && 
			companyQualifications.get(0).getDocList() !=null && companyQualifications.get(0).getDocList().size()>0)
			{
				theDoc=companyQualifications.get(0).getDocList().get(0);
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return theDoc;
	}
	
	public Doc getCompanyUSSupportingDoc()
	{
		Doc theDoc=null;
		try {
			if(unitStandards !=null && unitStandards.size()>0 && 
			unitStandards.get(0).getDocList() !=null && unitStandards.get(0).getDocList().size()>0)
			{
				theDoc=unitStandards.get(0).getDocList().get(0);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return theDoc;
	}
	
	public Doc getCompanySkillsProgSupportingDoc()
	{
		Doc theDoc=null;
		try {
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0 && 
					tpSkillsProgramList.get(0).getDocList() !=null && tpSkillsProgramList.get(0).getDocList().size()>0)
			{
				theDoc=tpSkillsProgramList.get(0).getDocList().get(0);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return theDoc;
	}
	
	public Doc getCompanySkillsSetSupportingDoc()
	{
		Doc theDoc=null;
		try {
			if(tpSkillsSetList !=null && tpSkillsSetList.size()>0 && 
					tpSkillsSetList.get(0).getDocList() !=null && tpSkillsSetList.get(0).getDocList().size()>0)
			{
				theDoc=tpSkillsSetList.get(0).getDocList().get(0);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return theDoc;
	}
	
	public void doneQualificationDetails()
	{
		try {
			if (companyQualifications.size() > 0 || unitStandards.size() > 0 || tpSkillsProgramList.size() > 0 || tpSkillsSetList.size() > 0) {
				doneQualification=true;
				checkIfDocsUploaded();
			}
			else{
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
	
	public void resetDoneQualification()
	{
		doneQualification=false;
		runClientSideUpdate("pgResubmissionQualDetails");
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
				if (!docUploadUser) {
					// if document is for company
					companyService.documentUpload(doc.getCompany(), getSessionUI().getActiveUser());
				} else {
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
	
	public void updateFile(FileUploadEvent event) {
		try {
			
			if(doc !=null && doc.getId() !=null){
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				DocumentTrackerService.create(getSessionUI().getActiveUser(), doc, DocumentTrackerEnum.Upload);
				addInfoMessage(super.getEntryLanguage("upload.successful"));
				super.runClientSideExecute("PF('dlgUploadSupportingDoc').hide()");
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
			addTPSkillsProgreme();
		} else if (docFor.equalsIgnoreCase("Skills Set")) {
			addTPSkillsSet();
		}
	}
	
	public void addCmpanyUnitStandardBetch() {
		usUplaoaded = true;
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (CompanyUnitStandard compUS : unitStandards) {
				compUS.setTrainingProviderDocParent(docParent);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addQualificationBetch() {
		qualUplaoaded = true;
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (CompanyQualifications companyQualifications : companyQualifications) {
				companyQualifications.setTrainingProviderDocParent(docParent);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addTPSkillsProgreme() {
		spUplaoaded = true;
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (TrainingProviderSkillsProgramme stSP : tpSkillsProgramList) {
				stSP.setTrainingProviderDocParent(docParent);
			}
			docParent = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void addTPSkillsSet() {
		ssUplaoaded = true;
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (TrainingProviderSkillsSet tpSS : tpSkillsSetList) {
				tpSS.setTrainingProviderDocParent(docParent);
			}
			docParent = null;
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
			if(selectedCompanyQualification !=null && selectedCompanyQualification.getId() !=null)
			{
				companyQualificationsService.delete(selectedCompanyQualification);
				companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
			}
			else
			{
				companyQualifications.remove(selectedCompanyQualification);
			}
			resetDoneQualification();
			removeSKillsProgrammes(selectedCompanyQualification);
			addWarningMessage("Qualification removed");
			selectedCompanyQualification=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the unit from list.
	 */
	public void removeUnitFromListmList() {
		try {
			if(selectCompanyUnitStandard !=null && selectCompanyUnitStandard.getId() !=null)
			{
				companyUnitStandardService.delete(selectCompanyUnitStandard);
				unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
			}
			else
			{
				unitStandards.remove(selectCompanyUnitStandard);
			}
			resetDoneQualification();
			addWarningMessage("Unit Standard removed");
			selectCompanyUnitStandard=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the Skills Program from list.
	 */
	public void removeSkillsProgramFromList() {
		try {
			if(selectSkillsProgram !=null && selectSkillsProgram.getId() !=null)
			{
				trainingProviderSkillsProgrammeService.delete(selectSkillsProgram);
				tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(selectTrainingProviderApplication.getId());
			}
			else
			{
				tpSkillsProgramList.remove(selectSkillsProgram);
			}
			resetDoneQualification();
			addWarningMessage("Skills Program removed");
			selectSkillsProgram=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the Skills Set from list.
	 */
	public void removeSkillsSetFromListmList() {
		try {
			if(tpSkillsSet !=null && tpSkillsSet.getId() !=null)
			{
				trainingProviderSkillsSetSevice.delete(tpSkillsSet);
				tpSkillsSetList=trainingProviderSkillsSetSevice.findByTrainingProvider(selectTrainingProviderApplication.getId());
			}
			else
			{
				tpSkillsSetList.remove(tpSkillsSet);
			}
			resetDoneQualification();
			addWarningMessage("Skills Set removed");
			tpSkillsSet=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the qualification to list.
	 */
	public void addQualificationToList() {
		try {
			checkIfQualAdded();
			if(companyQualifications !=null && companyQualifications.size()>0 && companyQualifications.get(0).getTrainingProviderDocParent() !=null)
			{
				CompanyQualifications companyQualification = new CompanyQualifications();
				companyQualification.setQualification(qualification);
				companyQualification.setTrainingProviderDocParent(companyQualifications.get(0).getTrainingProviderDocParent());
				companyQualifications.add(companyQualification);
			}
			else
			{
				CompanyQualifications companyQualification = new CompanyQualifications();
				companyQualification.setQualification(qualification);
				companyQualifications.add(companyQualification);
			}
			if(selectTrainingProviderApplication !=null && 
			selectTrainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL &&
			selectTrainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider && 
			selectTrainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.QCTOTradeTestCentre)
			{
				addSKillsProgrammes();
			}
			resetDoneQualification();
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the Skills Programme to list.
	 */
	public void addSkillsProgrammeToList() {
		try {
			checkIfSkillsProgAdded();
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0 && tpSkillsProgramList.get(0).getTrainingProviderDocParent() !=null){
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme();
				tpSp.setSkillsProgram(skillsProgram);
				tpSp.setTrainingProviderDocParent(companyQualifications.get(0).getTrainingProviderDocParent());
				tpSkillsProgramList.add(tpSp);
			}
			else{
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme();
				tpSp.setSkillsProgram(skillsProgram);
				tpSkillsProgramList.add(tpSp);
			}
			addInfoMessage("Skills Programme added");
			resetDoneQualification();
			skillsProgram=new SkillsProgram();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void checkIfSkillsProgAdded() throws Exception
	{
		if(skillsProgram !=null)
		{
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0){
				for(TrainingProviderSkillsProgramme tpSp:tpSkillsProgramList){
					if(tpSp.getSkillsProgram().equals(skillsProgram))
					{
						throw new Exception("Skills Programme already in the list");
					}
				}
			}
		}
		else
		{
			 throw new Exception("Please Select Skills Programme");
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
			TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme(sp, selectTrainingProviderApplication);
			tpSkillsProgramList.add(tpSp);
			//trainingProviderSkillsProgrammeService.create(tpSp);
			tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(selectTrainingProviderApplication.getId());
		}
		else
		{
			if(showMssg)
			{
				 throw new Exception("Skills Programme already in the list");
			}
			
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
	  
	/**
	 * Prepare new qualification.
	 */
	public void prepareNewQualification() {
		this.qualification = new Qualification();
		this.unitStandard = new UnitStandards();
	}
	
	public void removeSKillsProgrammes(CompanyQualifications cp) throws Exception {
		
		if(cp !=null && cp.getId() !=null)
		{
			for(TrainingProviderSkillsProgramme tpSp:tpSkillsProgramList)
			{
				if(tpSp.getSkillsProgram().getQualification().equals(cp.getQualification()))
				{
					trainingProviderSkillsProgrammeService.delete(tpSp);
				}
			}
		   tpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProvider(selectedCompanyQualification.getId());
		}
		else
		{
			for(TrainingProviderSkillsProgramme tpSp:tpSkillsProgramList)
			{
				if(tpSp.getSkillsProgram().getQualification().equals(cp.getQualification()))
				{
					tpSkillsProgramList.remove(tpSp);
				}
			}
		}
	   
	}
	
	/**
	 * Adds the unit to list.
	 */
	public void addUnitToList() {

		try {
			
			if(unitStandard==null){throw new Exception("Please Select Unit Standard");}
			for(CompanyUnitStandard cus: unitStandards){
				if(unitStandard.equals(cus.getUnitStandard())){
					throw new Exception("Unit Standard already in the list");
				}
			}
			
			if(unitStandards!=null && unitStandards.size()>0 && unitStandards.get(0).getTrainingProviderDocParent() !=null)
			{
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
				companyUnitStandard.setTrainingProviderDocParent(unitStandards.get(0).getTrainingProviderDocParent());
				unitStandards.add(companyUnitStandard);
			}
			else{
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
				unitStandards.add(companyUnitStandard);
			}
			resetDoneQualification();
			prepareNewQualification();
			addInfoMessage("Unit Standard added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds  Skills Set to list.
	 */
	public void addSkillsSetToList() {

		try {
			
			if(skillsSet==null){throw new Exception("Please Select Skills Set");}
			for(TrainingProviderSkillsSet tpss: tpSkillsSetList){
				if(skillsSet.equals(tpss.getSkillsSet())){
					throw new Exception("Skills Setalready in the list");
				}
			}
			
			if(tpSkillsSetList!=null && tpSkillsSetList.size()>0 && tpSkillsSetList.get(0).getTrainingProviderDocParent() !=null)
			{
				TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, selectTrainingProviderApplication);
				tpSkillsSet.setTrainingProviderDocParent(tpSkillsSetList.get(0).getTrainingProviderDocParent());
				tpSkillsSetList.add(tpSkillsSet);
			}
			else{
				TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, selectTrainingProviderApplication);
				tpSkillsSetList.add(tpSkillsSet);
			}
			//trainingProviderSkillsSetSevice.create(tpSkillsSet);
			resetDoneQualification();
			prepareNewQualification();
			addInfoMessage("Skills Set added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
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
	 * reject task.
	 */
	public void rejectTask() {
		try {
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				companyService.rejectTPReAccreditationTask(getEntryLanguage("training.provider.re.accreditation.was.rejected"), this.sdpReAccreditation.getId(), this.sdpReAccreditation.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, company.getCompanyName()),selectedRejectReason,trainingProviderApplication,sdpReAccreditation);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	public void rejectTaskWorkflow() {
		try {
			if (selectedRejectReason.size() == 0)  {
				throw new Exception("Please select a reject reason");				
			} else {
				companyService.rejectTaskWorkflow(getSessionUI().getTask(), selectedRejectReason, trainingProviderApplication , sdpReAccreditation, getSessionUI().getActiveUser());
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
    
    public ArrayList<ReviewCommitteeMeeting> getTPReviewCommitteeMeetingList() {
    	ArrayList<ReviewCommitteeMeeting> list=new ArrayList<>();
    	if(trainingProviderApplication.getSiteVisitDate() !=null)
    	{
			try {
				list = (ArrayList<ReviewCommitteeMeeting>) rcmService.allActiveReviewCommitteeMeeting(trainingProviderApplication.getSiteVisitDate());
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}
    	}
    	return list;
	}
    
    public void prepareAddAssFacilitator(boolean var)
	{
		addAssFacilitator=var;
	}
    
	public void approveTtApplicationAndSendCertificate()
	{
		try {
			
			trainingProviderApplicationService.approveTtApplicationAndSendCertificate(trainingProviderApplication, companyQualifications, unitStandards,(ArrayList<TrainingProviderSkillsProgramme>) tpSkillsProgramList,getSessionUI().getTask(),tpSkillsSetList,sdpReAccreditation);
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
    
    /**
	 * complete task.
	 */
	public void completeTask() {
		try {
			exceptions=null;
			companyProblem=null;
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
				documentChecks(company);
//				validateSelfEvaluation(auditorMonitorReviewDataModelList);
				validateSelfEvaluationLazyLoaded();
			} else {
	        	companyProblem = "";
	        }
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
				validateSiteVisit(trainingProviderApplication);
				validateSelfEvaluationCommentsLazyLoaded();
//				validateSelfEvaluationComments(auditorMonitorReviewDataModelList);
			}
			trainingProviderApplication.setSiteVisitDone(onAfterSiteVistDate);
			if (companyProblem==null||companyProblem.length() == 0) {
				companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsProgramList);
				companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
				companyService.completeTPTask(super.getEntryLanguage("training.provider.re.accreditation.for.approval"), sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.TPNewRequiresApproval, MailTagsEnum.CompanyName, company.getCompanyName()), auditorMonitorReviewDataModelList,trainingProviderApplication,sdpReAccreditation);
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
	
	/**
	 * Approve task.
	 */
	public void approveResubmissionApplication() {
		try {
			checkIfQualificationIsApprove();
			companyQualificationsService.update((List<IDataEntity>) (List<?>) companyQualifications);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) unitStandards);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsProgramList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
			sdpReAccreditationService.approveResubmission(sdpReAccreditation, trainingProviderApplication, companyQualifications, unitStandards, tpSkillsProgramList, getSessionUI().getTask(), tpSkillsSetList);
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	/**Reject task*/
	public void rejectResubmissionApplication() {
		try {
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				sdpReAccreditationService.rejectResubmission(sdpReAccreditation, getSessionUI().getTask(), getSessionUI().getActiveUser(), selectedRejectReason, trainingProviderApplication);
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
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
			throw new Exception("Please approve at least one qualificaiton, unit standard or skills programme");
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
	
	public void clearResubmission()
	{
		try {
			selectTrainingProviderApplication=null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private void validateSelfEvaluationComments(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2)
		{
			if(audit.getComment()==null || audit.getComment().isEmpty() ||audit.getComment().equals("") )
			{
				throw new Exception("Please provide self evaluation comments");
			}
		}
	}

	private void validateSelfEvaluation(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2) {
			if(audit.getEvidenceRequired()==null && audit.getIsNotRelevant()!=null && audit.getIsNotRelevant()==false)
			{
				throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
			}
		}
	}
	
	private void validateSelfEvaluationLazyLoaded() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(sdpReAccreditation.getClass().getName(), sdpReAccreditation.getId()) > 0) {
			throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
		}
	}
	
	private void validateSelfEvaluationCommentsLazyLoaded() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeCommentNotProvided(sdpReAccreditation.getClass().getName(), sdpReAccreditation.getId()) > 0) {
			throw new Exception("Please provide self evaluation: Evaluator Outcome: Evidence Available & Comments where missing");
		}
	}

	private void validateSiteVisit(TrainingProviderApplication tpApplication) throws Exception {
		
		if(tpApplication.getSiteVisitDate()==null || tpApplication.getSiteVisitReportDate()==null)
		{
			throw new Exception("Please complete site visit details");
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
				companyService.approveSDPReAccreditationTask(getSessionUI().getTask(), getSessionUI().getActiveUser(), this.company,trainingProviderApplication,reviewCommitteeMeeting,sdpReAccreditation);
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
	 * Prep upload close company information.
	 */
	public void prepUploadCloseCompanyInformation() {
		if (updateCompanyBool != null && updateCompanyBool == true) {
			this.updateCompanyBool = false;
			runClientSideUpdate("companyInsForm");
		}
		docUploadUser = false;
	}
    
    private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(company.getId(), Company.class.getName(), ConfigDocProcessEnum.TP);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}		
	}
    
    public void requestReAccreditation()
    {
    	try {
    		
    		boolean passedValidiation = true;
			try {
				int openResubmissions = sdpReAccreditationService.countOpenSDPReAccreditationByProviderApplicationId(selectTrainingProviderApplication.getId(), ApprovalEnum.getOpenStatusForSdpReAccrediciation());
				
				if (openResubmissions > 0) {
					passedValidiation = false;
					selectTrainingProviderApplication = null;
					throw new Exception("There is currently an open re-submission. Please wait for it to be completed before submitting another re-submission.");
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
    		
			if (passedValidiation) {
	    		if(selectTrainingProviderApplication.getCodeOfConductAccepted() ==null ||!selectTrainingProviderApplication.getCodeOfConductAccepted() )
				{
					throw new Exception("Please accept code of conduct");
				}
				service.requestReAccreditation(selectTrainingProviderApplication,getSessionUI().getActiveUser());
				addInfoMessage(super.getEntryLanguage("your.application.is.being.processed"));
			}
			selectTrainingProviderApplication = null;
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('codeOfConductDialog').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
    }
    
    public void closeResubmissionDlg(){
    	try {
    		selectTrainingProviderApplication = null;
    		super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('codeOfConductDialog').hide()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
    }
    
    public void requestResubmission()
    {
    	try {
    		if (selectTrainingProviderApplication.getCodeOfConductAccepted() == null || !selectTrainingProviderApplication.getCodeOfConductAccepted()) {
				throw new Exception("Please accept Code Of Conduct");
			}
    		validateSupportingDocs();
			service.requestResubmission(selectTrainingProviderApplication,getSessionUI().getActiveUser(),companyQualifications,unitStandards,tpSkillsProgramList,tpSkillsSetList,docParentList);
			addInfoMessage(super.getEntryLanguage("your.application.is.being.processed"));
			super.runClientSideExecute("uploadDone()");
			clearResubmission();
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
    }
    
    /**Checks if supporting documents are uploaded*/
	public void validateSupportingDocs() throws Exception
	{
		if(companyQualifications !=null && companyQualifications.size()>0 && (qualUplaoaded ==null || !qualUplaoaded))
		{
			throw new Exception("Please upload supporting document for qualification(s)");
		}
		else if(unitStandards !=null && unitStandards.size()>0 && (usUplaoaded ==null ||  !usUplaoaded))
		{
			throw new Exception("Please upload supporting document for Unit Standard(s) ");
		}
		else if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0 &&  (spUplaoaded ==null || !spUplaoaded))
		{
			throw new Exception("Please upload supporting document for Skills Programme(s) ");
		}
		else if(tpSkillsSetList !=null && tpSkillsSetList.size()>0 && (ssUplaoaded ==null || !ssUplaoaded))
		{
			throw new Exception("Please upload  supporting document for Skills SetList(s)");
		}
	}
    
    /**Prepare contact Person, Assessor or Facilitator*/
	public void preparContactPersonUpdate()
	{
		try {
			contactPerson=selectedTPContsctPerson.getUser();
			contactPerson.setDesignation(selectedTPContsctPerson.getDesignation());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void prepareContactPerson() {
		try {
			contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
			facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Get all SDPReAccreditation for data table
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 */
	public void sdpreaccreditationInfo() {
			//dataModel = new SDPReAccreditationDatamodel();
	}

	/**
	 * Insert SDPReAccreditation into database
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 */
	public void sdpreaccreditationInsert() {
		try {
				 service.create(this.sdpreaccreditation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpreaccreditationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SDPReAccreditation in database
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 */
    public void sdpreaccreditationUpdate() {
		try {
				 service.update(this.sdpreaccreditation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpreaccreditationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SDPReAccreditation from database
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 */
	public void sdpreaccreditationDelete() {
		try {
			 service.delete(this.sdpreaccreditation);
			  prepareNew();
			 sdpreaccreditationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareResubmissionData() {
		try {

			boolean passedValidiation = true;
			try {
				int openResubmissions = sdpReAccreditationService.countOpenSDPReAccreditationByProviderApplicationId(selectTrainingProviderApplication.getId(), ApprovalEnum.getOpenStatusForSdpReAccrediciation());
				
				if (openResubmissions > 0) {
					passedValidiation = false;
					selectTrainingProviderApplication = null;
					throw new Exception("There is currently an open re-submission. Please wait for it to be completed before submitting another re-submission.");
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
			
			SDPExtensionOfScopeUI.selectTrainingProviderApplication =null;
			if (passedValidiation) {
				try {
					int openExtensions = extensionOfScopeService.countOpenSDPExtensionOfScopeByProviderApplicationId(selectTrainingProviderApplication.getId(), ApprovalEnum.getOpenStatusForSdpExtensionOfScope());
					if (openExtensions > 0) {
						selectTrainingProviderApplication = null;
						passedValidiation = false;
						throw new Exception("There is currently an open extension of scope. Please wait for it to be completed before submitting a re-submission.");
					}
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
				}
				if (passedValidiation) {
					this.company = selectTrainingProviderApplication.getCompany();
					this.company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
					this.company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
					contactPersonList = companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
					facilitatorAssessorList = companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
					trainingProviderUser = usersService.findByKeyAndResolveDocTargetKeyAndClass(selectTrainingProviderApplication.getUsers().getId(),ConfigDocProcessEnum.TP, CompanyUserTypeEnum.User,selectTrainingProviderApplication.getId(),selectTrainingProviderApplication.getClass().getName());
					companyQualifications=companyQualificationsService.findByTargetClassAndTargetKey(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
					unitStandards = companyUnitStandardService.findByTargetClassAndTargetKey(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
					tpSkillsProgramList = trainingProviderSkillsProgrammeService.findByTrainingProvider(selectTrainingProviderApplication.getId());
					tpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProvider(selectTrainingProviderApplication.getId());
					checkIfDocsUploaded();
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void switchValuesTrainingAssessment() {
		try {
			if (selectTrainingProviderApplication.getTrainingAssessment()) {
				selectTrainingProviderApplication.setAssessmentOnly(false);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	
	public void switchValuesAssessmentOnly() {
		try {
			if (selectTrainingProviderApplication.getAssessmentOnly()) {
				selectTrainingProviderApplication.setTrainingAssessment(false);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SDPReAccreditation 
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 */
	public void prepareNew() {
		sdpreaccreditation = new SDPReAccreditation();
	}

/*
    public List<SelectItem> getSDPReAccreditationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sdpreaccreditationInfo();
    	for (SDPReAccreditation ug : sdpreaccreditationList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<SDPReAccreditation> complete(String desc) {
		List<SDPReAccreditation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<SDPReAccreditation> getSDPReAccreditationList() {
		return sdpreaccreditationList;
	}
	public void setSDPReAccreditationList(List<SDPReAccreditation> sdpreaccreditationList) {
		this.sdpreaccreditationList = sdpreaccreditationList;
	}
	public SDPReAccreditation getSdpreaccreditation() {
		return sdpreaccreditation;
	}
	public void setSdpreaccreditation(SDPReAccreditation sdpreaccreditation) {
		this.sdpreaccreditation = sdpreaccreditation;
	}

    public List<SDPReAccreditation> getSDPReAccreditationfilteredList() {
		return sdpreaccreditationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sdpreaccreditationfilteredList the new sdpreaccreditationfilteredList list
 	 * @see    SDPReAccreditation
 	 */	
	public void setSDPReAccreditationfilteredList(List<SDPReAccreditation> sdpreaccreditationfilteredList) {
		this.sdpreaccreditationfilteredList = sdpreaccreditationfilteredList;
	}

	
	public LazyDataModel<SDPReAccreditation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDPReAccreditation> dataModel) {
		this.dataModel = dataModel;
	}
	
	public void completeWorkflow() {
		try {
			service.completeWorkflow(sdpreaccreditation, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(sdpreaccreditation, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			service.rejectWorkflow(sdpreaccreditation, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(sdpreaccreditation, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(sdpreaccreditation, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}*/

	public String getExceptions() {
		return exceptions;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public Boolean getUpdateCompanyBool() {
		return updateCompanyBool;
	}

	public void setUpdateCompanyBool(Boolean updateCompanyBool) {
		this.updateCompanyBool = updateCompanyBool;
	}

	public Boolean getDocUploadUser() {
		return docUploadUser;
	}

	public void setDocUploadUser(Boolean docUploadUser) {
		this.docUploadUser = docUploadUser;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Users getTrainingProviderUser() {
		return trainingProviderUser;
	}

	public void setTrainingProviderUser(Users trainingProviderUser) {
		this.trainingProviderUser = trainingProviderUser;
	}

	public List<CompanyQualifications> getCompanyQualifications() {
		return companyQualifications;
	}

	public void setCompanyQualifications(List<CompanyQualifications> companyQualifications) {
		this.companyQualifications = companyQualifications;
	}

	public List<CompanyUnitStandard> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<CompanyUnitStandard> unitStandards) {
		this.unitStandards = unitStandards;
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

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelList() {
		return auditorMonitorReviewDataModelList;
	}

	public void setAuditorMonitorReviewDataModelList(List<AuditorMonitorReview> auditorMonitorReviewDataModelList) {
		this.auditorMonitorReviewDataModelList = auditorMonitorReviewDataModelList;
	}

	public boolean isOnAfterSiteVistDate() {
		return onAfterSiteVistDate;
	}

	public void setOnAfterSiteVistDate(boolean onAfterSiteVistDate) {
		this.onAfterSiteVistDate = onAfterSiteVistDate;
	}

	public boolean isAddAssFacilitator() {
		return addAssFacilitator;
	}

	public void setAddAssFacilitator(boolean addAssFacilitator) {
		this.addAssFacilitator = addAssFacilitator;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
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

	public boolean isNoDateProvided() {
		return noDateProvided;
	}

	public void setNoDateProvided(boolean noDateProvided) {
		this.noDateProvided = noDateProvided;
	}

	public List<CompanyUsers> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<CompanyUsers> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

	public List<CompanyUsers> getFacilitatorAssessorList() {
		return facilitatorAssessorList;
	}

	public void setFacilitatorAssessorList(List<CompanyUsers> facilitatorAssessorList) {
		this.facilitatorAssessorList = facilitatorAssessorList;
	}

	public SDPReAccreditation getSdpReAccreditation() {
		return sdpReAccreditation;
	}

	public void setSdpReAccreditation(SDPReAccreditation sdpReAccreditation) {
		this.sdpReAccreditation = sdpReAccreditation;
	}

	/**
	 * @return the nextDate
	 */
	public Date getNextDate() {
		return nextDate;
	}

	/**
	 * @param nextDate the nextDate to set
	 */
	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public TrainingProviderApplication getSelectTrainingProviderApplication() {
		return selectTrainingProviderApplication;
	}

	public void setSelectTrainingProviderApplication(TrainingProviderApplication selectTrainingProviderApplication) {
		SDPReAccreditationUI.selectTrainingProviderApplication = selectTrainingProviderApplication;
	}

	public CompanyUsers getSelectedTPContsctPerson() {
		return selectedTPContsctPerson;
	}

	public void setSelectedTPContsctPerson(CompanyUsers selectedTPContsctPerson) {
		this.selectedTPContsctPerson = selectedTPContsctPerson;
	}

	public Users getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Users contactPerson) {
		this.contactPerson = contactPerson;
	}

	public CompanyQualifications getSelectedCompanyQualification() {
		return selectedCompanyQualification;
	}

	public void setSelectedCompanyQualification(CompanyQualifications selectedCompanyQualification) {
		this.selectedCompanyQualification = selectedCompanyQualification;
	}

	public CompanyUnitStandard getSelectCompanyUnitStandard() {
		return selectCompanyUnitStandard;
	}

	public void setSelectCompanyUnitStandard(CompanyUnitStandard selectCompanyUnitStandard) {
		this.selectCompanyUnitStandard = selectCompanyUnitStandard;
	}

	public TrainingProviderSkillsProgramme getSelectSkillsProgram() {
		return selectSkillsProgram;
	}

	public void setSelectSkillsProgram(TrainingProviderSkillsProgramme selectSkillsProgram) {
		this.selectSkillsProgram = selectSkillsProgram;
	}

	public TrainingProviderSkillsSet getTpSkillsSet() {
		return tpSkillsSet;
	}

	public void setTpSkillsSet(TrainingProviderSkillsSet tpSkillsSet) {
		this.tpSkillsSet = tpSkillsSet;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
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

	public String getDocNode() {
		return docNode;
	}

	public void setDocNode(String docNode) {
		this.docNode = docNode;
	}

	public String getDocFor() {
		return docFor;
	}

	public void setDocFor(String docFor) {
		this.docFor = docFor;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public TrainingProviderDocParent getDocParent() {
		return docParent;
	}

	public void setDocParent(TrainingProviderDocParent docParent) {
		this.docParent = docParent;
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
	
}
