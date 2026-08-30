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
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.SDPCompany;
import haj.com.entity.SDPExtensionOfScope;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.Tasks;
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
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SdpType;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.AssessorModeratorCompanySitesService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
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
import haj.com.service.UnitStandardsService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SaqaUsQualificationService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.utils.GenericUtility;



@ManagedBean(name = "sdpextensionofscopeUI")
@ViewScoped
public class SDPExtensionOfScopeUI extends AbstractUI {

	private SDPReAccreditationService sdpReAccreditationService = new SDPReAccreditationService();
	private SDPExtensionOfScopeService service = new SDPExtensionOfScopeService();
	private List<SDPExtensionOfScope> sdpextensionofscopeList = null;
	private List<SDPExtensionOfScope> sdpextensionofscopefilteredList = null;
	private SDPExtensionOfScope sdpextensionofscope = null;
	private LazyDataModel<SDPExtensionOfScope> dataModel; 
	private LazyDataModel<SDPExtensionOfScope> pendingAppDataModel; 
	/** The Review Committee Meeting Service. */
	private ReviewCommitteeMeetingService reviewCommitteeMeetingService = new ReviewCommitteeMeetingService();
	
	/** The unit standards service. */
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	
	private SkillsProgramService skillsProgramService=new SkillsProgramService();
	
	/** The qualification. */
	private Qualification qualification;
	
	/** The qualification list. */
	private List<Qualification> qualificationList;
	
	/** The unit standard. */
	private UnitStandards unitStandard;
	
	/** The unit standards. */
	private List<UnitStandards> unitStandards;
	
	/** The unit standards. */
	private List<CompanyUnitStandard> approvedCompanyUnitStandards;
	
	/**Training provider Skills Program*/
	private SkillsProgram skillsProgram;
	
	private ArrayList<SkillsProgram> skillsProgramList=new ArrayList<>();
	
	//private Company selectedCompany;
	
	/** The company. */
	private Company company;
	
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	
	/** The company service. */
	private CompanyService companyService = new CompanyService();
	
	/**The Training Provider Application*/
	private TrainingProviderApplication trainingProviderApplication;
	
	/**The Training Provider Application*/
	public static TrainingProviderApplication selectTrainingProviderApplication;
	
	/**The Company List*/
	private List<Company> companyList;
	
	/**Training Provider Contact Persons*/
	private List<CompanyUsers> contactPersonList;
	
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	/**Training Provider Assessor/Facilitator*/
	private List<CompanyUsers> facilitatorAssessorList;
	
	/** The users service. */
	private UsersService usersService = new UsersService();
	
	/** The training provider user. */
	private Users trainingProviderUser;
	
	/** The company qualifications. */
	private List<CompanyQualifications> companyQualifications;
	
	/** The company qualifications. */
	private List<CompanyQualifications> approvedCompanyQualifications;
	
	/** The company qualifications service. */
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();

	/** The company unit standard service. */
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
	
	/** The unit standards. */
	private List<CompanyUnitStandard> companyUnitStandards;
	
	private List<TrainingProviderSkillsProgramme> tpSkillsProgramList;
	
	private List<TrainingProviderSkillsProgramme> approvedTpSkillsProgramList;
	
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
	
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList;
	
	private List<RejectReasons> applicationRejectReasons=new ArrayList<>();
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	
	private ArrayList<TrainingProviderSkillsSet> tpSkillsSetList=new ArrayList<>();
	
	private List<TrainingProviderSkillsSet> approvedTpSkillsSetList;
	
    private ArrayList<SkillsSet> skillsSetList=new ArrayList<>();
	private SkillsSet skillsSet;
	
	/** The exceptions. */
	private String exceptions;
	
	/** The update company bool. */
	private Boolean updateCompanyBool;

	/** The doc upload user. */
	private Boolean docUploadUser;
	
	/** The doc. */
	private Doc doc;
	
	
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	/** The new qualification list. */
	private List<CompanyQualifications> newQualificationList=new ArrayList<>();;
	private CompanyQualifications selectedCompanyQualification = null;
	
	
	/** The new unit standards. */
	private List<CompanyUnitStandard> newUnitStandards=new ArrayList<>();;

	
	/** The new unit Skills Program. */
	private ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramList=new ArrayList<>();
	
	private TrainingProviderSkillsSetService trainingProviderSkillsSetSevice=new TrainingProviderSkillsSetService();
	
	private TrainingProviderSkillsSetService trainingProviderSkillsSetService=new TrainingProviderSkillsSetService();
	
	/** The Next Date*/
	private Date nextDate;
	
	private boolean noDateProvided = false;
	
	/** Flag to check if date is after site visit*/
	private boolean onAfterSiteVistDate = false;
	
	/** The Date Change Reasons*/
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	
	/** The Date Change Reason Available Selection List*/
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	
	/** The qualification. */
	private Qualification learningProgrammeQualification;
	
	private AuditorMonitorReview auditorMonitorReview;
	
	private Boolean nonSetaExOfScope;
	
	private CompanyQualifications companyQualification;
	private TrainingProviderSkillsProgramme tpSkillsProgramme;
	private CompanyUnitStandard companyUnitStandard;
	private TrainingProviderSkillsSet tpSkillsSet;
	
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
	
	private SdpType sdpType = null;
	
	private SDPCompany sdpCompanyLink = null;
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private LazyDataModel<SDPCompany> sdpCompanyDataModel;
	
	private AssessorModeratorCompanySitesService assessorModeratorCompanySitesService = new AssessorModeratorCompanySitesService();
	private LazyDataModel<AssessorModeratorCompanySites> assessorModeratorCompanySitesDataModel;
	
	private AuditorMonitorReview selectedAuditorMonitorReview = null;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList;

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
	 * Initialize method to get all SDPExtensionOfScope and prepare a for a create of a new SDPExtensionOfScope
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nextDate = GenericUtility.addDaysToDate(new Date(), 1);
		if (super.getParameter("id", false) != null) {
			sdpextensionofscope=service.findByKey(getSessionUI().getTask().getTargetKey());
			trainingProviderApplication = sdpextensionofscope.getTrainingProviderApplication();
			this.company = companyService.findByKeyAndResolveDoc(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId(),trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId(), ConfigDocProcessEnum.TP);
		    this.companyList = new ArrayList<>();
			companyList.add(company);
			//TP Contact Persons
			sdpCompanyDataModelInfo();
//			contactPersonList=companyUsersService.findTPContact(company.getId(), ConfigDocProcessEnum.TP);
			assessorModeratorCompanySitesDataModelInfo();
//			facilitatorAssessorList=companyUsersService.findTPAssessorMod(company.getId(), ConfigDocProcessEnum.TP);
			//Training Provider User
			trainingProviderUser = usersService.findByKey(sdpextensionofscope.getUsers().getId());
			newQualificationList=companyQualificationsService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			newUnitStandards= companyUnitStandardService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			newSkillsProgramList=(ArrayList<TrainingProviderSkillsProgramme>) trainingProviderSkillsProgrammeService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			tpSkillsSetList=(ArrayList<TrainingProviderSkillsSet>) trainingProviderSkillsSetSevice.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			
			if (trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
				trainingSite = trainingSiteService.resolveAddressInformatioAndRegion(trainingSiteService.findByKey(trainingProviderApplication.getTrainingSite().getId()));
			}else {
				trainingSite = null;
			}
			
			if (trainingProviderApplication != null) {
				auditorMonitorReviewEvidanceRequiredDataModelListInfo();
//				auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
//				auditorMonitorReviewDataModelList = new ArrayList<>();
			}else {
				auditorMonitorReviewDataModelList = new ArrayList<>();
			}
			
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
				validateSiteVistData();
			}
			populateRejectReasons();
		}
		else
		{
			lazyLoadByStatusInfo();
		}
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
					filters.put("targetClass", sdpextensionofscope.getClass().getName());
					filters.put("targetKey", sdpextensionofscope.getId());
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
				auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByTargetKeyAndClass(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
				
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
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				auditorMonitorReviewEvidanceRequiredDataModelListInfo();
				
			}
			doc = null;
			super.runClientSideExecute("PF('dlgUploadEvidenceV2').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	private void validateSiteVistData() throws Exception{
		if (sdpextensionofscope.getSiteVisitDate() == null) {
			onAfterSiteVistDate = false;
			noDateProvided = true;
		} else {
			noDateProvided = false;
			if (GenericUtility.getStartOfDay(sdpextensionofscope.getSiteVisitDate()).equals(GenericUtility.getStartOfDay(getNow())) || GenericUtility.getStartOfDay(sdpextensionofscope.getSiteVisitDate()).before(GenericUtility.getStartOfDay(getNow()))) {
				onAfterSiteVistDate = true;
				sdpextensionofscope.setSiteVisitReportDate(getNow());
			} else {
				onAfterSiteVistDate = false;
			}
		}
	}
	
	public void setSiteVisitDate(){
		try {
			if (sdpextensionofscope.getSiteVisitDate() == null) {
				addWarningMessage("Provide Site Visit Date Before Proceeding");
			}else {
				//trainingProviderApplication.setQualityAssuranceUser(getSessionUI().getActiveUser());
				service.update(sdpextensionofscope);
				getSessionUI().getTask().setDueDate(sdpextensionofscope.getSiteVisitDate());
				TasksService.instance().update(getSessionUI().getTask());
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				
				validateSiteVistData();
				
				addInfoMessage("Site Visit Date Set");

				//send email notiofications
				sendSiteVisitiiEmailNotification();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendSiteVisitiiEmailNotification() throws Exception
	{
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus: newUnitStandards)
		{
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq: newQualificationList)
		{
			qualificationsList.add(cq.getQualification());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme tpsp:newSkillsProgramList)
		{
			skillsProgramList.add(tpsp.getSkillsProgram());
		}
		
		List<SkillsSet> skillsSetList=new ArrayList<>();
		for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
		{
			skillsSetList.add(tpss.getSkillsSet());
		}
		trainingProviderApplicationService.sendExtensionOfScopeSiteVisitEmail(sdpextensionofscope, getSessionUI().getActiveUser(), unitStandardsList, qualificationsList, skillsProgramList, skillsSetList);
		
	}
	
	public void sendSiteVisitiiEmailNotification11() throws Exception
	{
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus: newUnitStandards)
		{
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq: newQualificationList)
		{
			qualificationsList.add(cq.getQualification());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme tpsp:newSkillsProgramList)
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
	
	public void prepSiteVisitDateUpdate() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
			dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.TP);
			runClientSideExecute("PF('dlgReviewDate').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	
	/**
	 * Get all TrainingProviderApplication for data table
 	 * @author TechFinium 
 	 * @see    TrainingProviderApplication
 	 */
	public void lazyLoadByStatusInfo() {
	      pendingAppDataModel = new LazyDataModel<SDPExtensionOfScope>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<SDPExtensionOfScope> retorno = new  ArrayList<SDPExtensionOfScope>();
		   
		   @Override 
		   public List<SDPExtensionOfScope> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				filters.put("approvalStatus", ApprovalEnum.PendingCommitteeApproval);
				retorno = service.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters);
				//retorno = service.allTrainingProviderApplication(TrainingProviderApplication.class,first,pageSize,sortField,sortOrder,filters);
				pendingAppDataModel.setRowCount(service.count(SDPExtensionOfScope.class,filters));
			} catch (Exception e) {
				logger.fatal(e);
			} 
		    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(SDPExtensionOfScope obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public SDPExtensionOfScope getRowData(String rowKey) {
		        for(SDPExtensionOfScope obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}
	
	
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			applicationRejectReasons = rs.findByTargetKeyClassAndProcessAndResolveRejectDate(sdpextensionofscope.getId(), sdpextensionofscope.getClass().getName(), ConfigDocProcessEnum.SDPExtensionOfScope);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public List<CompanyUsers> loadContactPerson(Company comp)
	{
		List<CompanyUsers> contactPersonList=new ArrayList<>();
		try {
			contactPersonList=companyUsersService.findTPContact(comp.getId(), ConfigDocProcessEnum.TP);
			if(contactPersonList==null){contactPersonList=new ArrayList<>();}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return contactPersonList;
	}
	
	public List<CompanyUsers> loadAssessorFacilitator(Company comp)
	{
		List<CompanyUsers> facilitatorAssessorList=new ArrayList<>();
		try {
			facilitatorAssessorList=companyUsersService.findTPAssessorMod(comp.getId(), ConfigDocProcessEnum.TP);
			if(facilitatorAssessorList==null){facilitatorAssessorList=new ArrayList<>();}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return facilitatorAssessorList;
	}

	public void approveQualificationSkillProg(CompanyQualifications cq)
	{
		try {
			if(newSkillsProgramList !=null && newSkillsProgramList.size()>0)
			{
				for(TrainingProviderSkillsProgramme tpsp: newSkillsProgramList)
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
			
			if(newUnitStandards !=null && newUnitStandards.size()>0)
			{
				for(CompanyUnitStandard cus: newUnitStandards)
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
	
	public List<RejectReasons> getTpRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void populateSdpType() throws Exception{
		sdpType = null;
		sdpCompanyLink = null;
		// new sdp company link
		if (selectTrainingProviderApplication != null) {
			if (selectTrainingProviderApplication.getTrainingSite() != null && selectTrainingProviderApplication.getTrainingSite().getId() != null) {
				sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectTrainingProviderApplication.getCompany().getId(), selectTrainingProviderApplication.getTrainingSite().getId());
			} else {
				sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectTrainingProviderApplication.getCompany().getId());
			}
			if (sdpCompanyLink != null) {
				sdpType = sdpCompanyLink.getSdpType();
			}
		}
		if (sdpType == null) {
			if (selectTrainingProviderApplication != null && selectTrainingProviderApplication.getCompany() != null && selectTrainingProviderApplication.getId() != null) {
				sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(selectTrainingProviderApplication, getSessionUI().getActiveUser());
			}
		}
	}
	
	public void validiateCanEditInformation() throws Exception{
		if (sdpType == null || sdpType.getActionSdpInformation() == null || !sdpType.getActionSdpInformation()) {
			selectTrainingProviderApplication = null;
			sdpType = null;
			throw new Exception("You currently do not have access to action");
		}
	}
	
	
	public void prepareExtensionOfScope() {
		try {
			populateSdpType();
			validiateCanEditInformation();
			boolean passedValidiation = true;
			try {
				int openExtensions = service.countOpenSDPExtensionOfScopeByProviderApplicationId(selectTrainingProviderApplication.getId(), ApprovalEnum.getOpenStatusForSdpExtensionOfScope());
				if (openExtensions > 0) {
					selectTrainingProviderApplication = null;
					sdpType = null;
					passedValidiation = false;
					selectTrainingProviderApplication = null;
					throw new Exception("There is currently an open extension of scope. Please wait for it to be completed before submitting another extension of scope.");
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
			
			if (passedValidiation) {
				try {
					int openResubmissions = sdpReAccreditationService.countOpenSDPReAccreditationByProviderApplicationId(selectTrainingProviderApplication.getId(), ApprovalEnum.getOpenStatusForSdpReAccrediciation());
					if (openResubmissions > 0) {
						selectTrainingProviderApplication = null;
						sdpType = null;
						passedValidiation = false;
						selectTrainingProviderApplication = null;
						throw new Exception("There is currently an open re-submission. Please wait for it to be completed before submitting an extension of scope.");
					}
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
				}
				if (passedValidiation) {
					if(doc==null && nonSetaExOfScope !=null && nonSetaExOfScope==true){
						doc=new Doc();
					}
					clearQualificationDetails();
					prepparQualification();
				}
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void prepparQualification() throws Exception
	{
		approvedCompanyQualifications=companyQualificationsService.findByTargetClassAndTargetKeyAndAccept(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId(),true);
		approvedCompanyUnitStandards=companyUnitStandardService.findByTargetClassAndTargetKeyAndAccept(selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId(),true);
		approvedTpSkillsProgramList=trainingProviderSkillsProgrammeService.findByTrainingProviderAndAccept(selectTrainingProviderApplication.getId(),true);
		approvedTpSkillsSetList = trainingProviderSkillsSetSevice.findByTrainingProviderAndAccept(selectTrainingProviderApplication.getId(),true);
	}
	
	public void clearQualificationDetails()throws Exception
	{
		
		SDPReAccreditationUI.selectTrainingProviderApplication=null;
		learningProgrammeQualification=null;
		qualification=null;
		qualificationList=new ArrayList<>();
		unitStandards=new ArrayList<>();
		skillsProgramList=new ArrayList<>();
		skillsSetList=new ArrayList<>();
		if(nonSetaExOfScope !=null && nonSetaExOfScope==true){
			tpSkillsProgramList=new ArrayList<>();
			companyQualifications=new ArrayList<>();
			companyUnitStandards=new ArrayList<>();
			tpSkillsSetList=new ArrayList<>();
		}
		
	}
	
	public void clearExtensionOfScope()
	{
		try {
			clearQualificationDetails();
			selectTrainingProviderApplication=null;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
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
	
	
	/**
	 * Prepare new qualification.
	 */
	public void prepareNewQualification() {
		this.qualification = new Qualification();
		this.unitStandard = new UnitStandards();
	}
	
	public void clearLearningProgrammeQualification()
	{
		learningProgrammeQualification=new Qualification();
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
	
	/**
	 * Adds the qualification to list.
	 */
	public void addQualificationToList() {
		
		try {
			if (qualification != null && !qualificationList.contains(qualification)) {
				CompanyQualifications companyQualifications= companyQualificationsService.findByCompany(selectTrainingProviderApplication.getCompany(), qualification, true);
				if(companyQualifications !=null){throw new Exception("You are already accredited for this qualification");}
				this.qualificationList.add(qualification);
				/*if(selectTrainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
				{
					addSKillsProgrammes();
				}*/
				addSKillsProgrammes();
				addUnitStandards();
				addSKillsSet();
				addInfoMessage("Qualification added");
				prepareNewQualification();
			}
			else
			{
				addWarningMessage(super.getEntryLanguage("qualification.aready.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addSKillsProgrammesCompanyQualifications() {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		TrainingProviderSkillsProgrammeService tpSpService = new TrainingProviderSkillsProgrammeService();
		List<SkillsProgram> l = null;
		try {
			if (companyQualifications != null && !companyQualifications.isEmpty()) {
				l = unitStandardsService.findByCompanyQualificationList(companyQualifications);
				for(SkillsProgram sp:l) {
					boolean contains = false;
					List<TrainingProviderSkillsProgramme> tpSplist = tpSpService.findByTrainingProviderAndSkillsProgAndAccept(selectTrainingProviderApplication.getId(), sp.getId(), true);
					if(!tpSplist.isEmpty()) {
						contains = true;
					}
					tpSplist.clear();
					if (!contains) {
						for (TrainingProviderSkillsProgramme tpSkillsProgram : tpSkillsProgramList) {
							if (tpSkillsProgram.getSkillsProgram().getId().equals(sp.getId())) {
								contains = true;
								break;
							}
						}
					}
					if (!contains) {
						TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme();
						if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0 && tpSkillsProgramList.get(0).getTrainingProviderDocParent() !=null){
						    tpSp.setSkillsProgram(sp);
						    tpSp.setTrainingProviderDocParent(companyQualifications.get(0).getTrainingProviderDocParent());
						    tpSp.setCannotRemove(true);
						} else{
							tpSp.setCannotRemove(true);
							tpSp.setSkillsProgram(sp);
						}
						tpSkillsProgramList.add(tpSp);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addSKillsProgrammes() {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<SkillsProgram> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualificationList);
				for(SkillsProgram sp:l) {
					boolean contains = false;
					if(!skillsProgramList.contains(sp)) {
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
	
	public void addSKillsSetCompanyQualifications() {
		SkillsSetService skillsSetService = new SkillsSetService();
		TrainingProviderSkillsSetService providerSkillsSetService=new TrainingProviderSkillsSetService();
		List<SkillsSet> l = null;
		try {
			if (companyQualifications != null && companyQualifications.size() != 0) {
				l = skillsSetService.findByCompanyQualificationList(companyQualifications);
				for(SkillsSet ss:l) {
					boolean contains = false;
					
					int count= providerSkillsSetService.countfindByCompany(selectTrainingProviderApplication.getCompany(), ss, true);
					if (count>0) {
						contains = true;
					}
					
					if (!contains) {
						for (TrainingProviderSkillsSet tpSkillsProgram : tpSkillsSetList) {
							if (tpSkillsProgram.getSkillsSet().getId().equals(ss.getId())) {
								contains = true;
								break;
							}
						}
					}
					if (!contains) {
						TrainingProviderSkillsSet tpSkillsSet = null;
						if(tpSkillsSetList!=null && tpSkillsSetList.size()>0 && tpSkillsSetList.get(0).getTrainingProviderDocParent() !=null) {
						    tpSkillsSet = new TrainingProviderSkillsSet(ss, selectTrainingProviderApplication);
						    tpSkillsSet.setTrainingProviderDocParent(tpSkillsSetList.get(0).getTrainingProviderDocParent());
						    tpSkillsSet.setCannotRemove(true);
						} else{
						    tpSkillsSet = new TrainingProviderSkillsSet(ss, selectTrainingProviderApplication);
						    tpSkillsSet.setCannotRemove(true);
						}
						if (tpSkillsSet != null) {
							tpSkillsSetList.add(tpSkillsSet);
						}
						tpSkillsSet = null;
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addSKillsSet() {
		SkillsSetService skillsSetService = new SkillsSetService();
		List<SkillsSet> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = skillsSetService.findByQualificationList(qualificationList);
				for(SkillsSet ss:l)
				{
					if(!skillsSetList.contains(ss))
					{
						ss.setCannotRemove(true);
						skillsSetList.add(ss);
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUnitStandards() {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			if (qualificationList != null && qualificationList.size() != 0) {
				l = unitStandardsService.findByQualificationList(qualificationList);
				for(UnitStandards us:l)
				{
					if(!unitStandards.contains(us))
					{
						us.setCannotRemove(true);
						unitStandards.add(us);
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
	 * Removes the qualification from list.
	 */
	public void removeQualificationFromList() {
		try {
			qualificationList.remove(qualification);
			removeSKillsProgrammes(qualification);
			removeSKillsSet(qualification);
			removeUnitStandards(qualification);
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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

	public void removeSKillsSet(Qualification qualification) {
		List<SkillsSet> tempList = new ArrayList<>();
		tempList.addAll(skillsSetList);
		for (SkillsSet ss : skillsSetList) {
			if (ss.getQualification().equals(qualification)) {
				tempList.remove(ss);
			}
		}
		skillsSetList.clear();
		skillsSetList = (ArrayList<SkillsSet>) tempList;
	}

	public void removeUnitStandards(Qualification qualification) {
		List<UnitStandards> tempList = new ArrayList<>();
		tempList.addAll(unitStandards);
		for (UnitStandards us : unitStandards) {
			if (us.getQualification().equals(qualification)) {
				tempList.remove(us);
			}
		}
		unitStandards.clear();
		unitStandards = (ArrayList<UnitStandards>) tempList;
	}

	/**
	 * Insert SDPExtensionOfScope into database
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 */
	public void sdpextensionofscopeInsert() {
		try {
				 service.create(this.sdpextensionofscope);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SDPExtensionOfScope in database
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 */
    public void sdpextensionofscopeUpdate() {
		try {
				 service.update(this.sdpextensionofscope);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
    
    /**
	 * Adds the unit to list.
	 */
	public void addUnitToList() {

		try {
			if (unitStandard != null && !unitStandards.contains(unitStandard)) {
				int  count= companyUnitStandardService.countfindByCompany(selectTrainingProviderApplication.getCompany(), unitStandard, true);
				if(count>0){throw new Exception("You are already accredited for this Unit Standard");}
				this.unitStandards.add(unitStandard);
				prepareNewQualification();
			}
			else
			{
				addWarningMessage(super.getEntryLanguage("us.aready.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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
	 * Validate user from selecting US from that belong to selected qualification
	 */
	public void usQualificationValidation() {
		try {
			SaqaUsQualificationService saqaUsQualificationService = new SaqaUsQualificationService();
			for (Qualification qual : qualificationList) {
				if ((saqaUsQualificationService.findByQualAndUS(qual.getId(), unitStandard.getCode())).size() > 0) {
					unitStandard = null;
					throw new Exception("Unit Standard forms part of the qualification(" + qual.getDescription() + ") selected above");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Delete SDPExtensionOfScope from database
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 */
	public void sdpextensionofscopeDelete() {
		try {
			 service.delete(this.sdpextensionofscope);
			  prepareNew();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SDPExtensionOfScope 
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 */
	public void prepareNew() {
		sdpextensionofscope = new SDPExtensionOfScope();
		this.qualificationList = new ArrayList<>();
		this.unitStandards = new ArrayList<>();
		this.nonSetaExOfScope=false;
		selectTrainingProviderApplication=null;
	}
	
	public ArrayList<SkillsProgram> getSelectItemsSkillsProgram()
	{
		ArrayList<SkillsProgram> list=null;
		 try {
			 list =skillsProgramService.findByQualifications((ArrayList<Qualification>) qualificationList);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		 
		 if(list ==null || list.size()==0)
		 {
			 sdpextensionofscope.setUseSkillProgrammeRoute(YesNoEnum.No);
			 addWarningMessage("No Skills Programs available for qualification select");
		 }
		 return list;
	}
	
	/**
	 * Adds the Skills Program to list.
	 */
	public void addSkillsProgramToList() {
		try {
			if (skillsProgram != null && !skillsProgramList.contains(skillsProgram)) {
				TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
				int  count= trainingProviderSkillsProgrammeService.countfindByCompany(selectTrainingProviderApplication, skillsProgram, true);
				if(count>0){throw new Exception("You are already accredited for this Skills Programme");}
				
				this.skillsProgramList.add(skillsProgram);
				prepareNewSkillsProgram();
			}
			else
			{
				addWarningMessage(super.getEntryLanguage("skills.program.already.in.the.list"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Removes the Skills Program from list.
	 */
	public void removeSkillsProgramFromList() {
		skillsProgramList.remove(skillsProgram);
		prepareNewSkillsProgram();
	}
	
	public void prepareNewSkillsProgram()
	{
		skillsProgram=new SkillsProgram();
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
	
	 public ArrayList<ReviewCommitteeMeeting> getTPReviewCommitteeMeetingList() {
	    	ArrayList<ReviewCommitteeMeeting> list=new ArrayList<>();
		
		  if(sdpextensionofscope.getSiteVisitDate() !=null)
		  { 
			  try { 
				  list =(ArrayList<ReviewCommitteeMeeting>)reviewCommitteeMeetingService.allActiveReviewCommitteeMeeting(sdpextensionofscope.getSiteVisitDate()); 
			  } 
			  catch (Exception e) 
			  { 
				  addErrorMessage(e.getMessage(),e); e.printStackTrace(); 
			  } 
		  }
		 
	    	return list;
		}
	 
	 /**
		 * Adds the Skills Program to list.
		 */
		public void addSkillsSetToList() {
			if (skillsSet != null && !skillsSetList.contains(skillsSet)) {
				this.skillsSetList.add(skillsSet);
				prepareNewSkillsSet();
			}
			else
			{
				addWarningMessage(super.getEntryLanguage("skills.set.already.in.the.list"));
			}
		}

		/**
		 * Removes the Skills Program from list.
		 */
		public void removeSkillsSetFromList() {
			skillsSetList.remove(skillsSet);
			prepareNewSkillsSet();
		}
		
		public void prepareNewSkillsSet()
		{
			skillsSet=new SkillsSet();
		}
	
	/**********Action Button************/
	
	public void requestExtensionOfScope()
	{
		try {
			if (selectTrainingProviderApplication.getCodeOfConductAccepted() == null || !selectTrainingProviderApplication.getCodeOfConductAccepted()) {
				throw new Exception("Please accept Code Of Conduct");
			}
			sdpextensionofscope.setUsers(getSessionUI().getActiveUser());
			sdpextensionofscope.setTrainingProviderApplication(selectTrainingProviderApplication);
			service.requestExtensionOfScope(getSessionUI().getActiveUser(),sdpextensionofscope,qualificationList,unitStandards,skillsProgramList,skillsSetList);
			 addInfoMessage(super.getEntryLanguage("update.successful"));
			 clearRequestExtensionOfScope();
			 super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void clearRequestExtensionOfScope()
	{
		try {
			selectTrainingProviderApplication=null;
			sdpextensionofscope=new SDPExtensionOfScope();
			qualificationList.clear();
			unitStandards.clear();
			skillsProgramList.clear();
			
			tpSkillsProgramList=new ArrayList<>();
			companyQualifications=new ArrayList<>();
			companyUnitStandards=new ArrayList<>();
			tpSkillsSetList=new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * approve task.
	 */
	public void approveTask() {
		try {
			
			service.approveTask(getSessionUI().getTask(), getSessionUI().getActiveUser(),sdpextensionofscope,reviewCommitteeMeeting,company);
			super.ajaxRedirect(getSessionUI().getDashboard());
			getSessionUI().setTask(null);
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			this.exceptions = e.getMessage();
		}

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
				service.rejectTask(getEntryLanguage("training.provider.form.was.rejected"), this.sdpextensionofscope.getId(), this.sdpextensionofscope.getClass().getName(), getSessionUI().getTask(), getSessionUI().getActiveUser(), MailDef.instance(MailEnum.SDPExtensionOfScopeRejectted, MailTagsEnum.CompanyName, company.getCompanyName()),selectedRejectReason,sdpextensionofscope);
			    super.ajaxRedirect(getSessionUI().getDashboard());
				getSessionUI().setTask(null);
			}
			super.runClientSideExecute("uploadDone()");
			super.ajaxRedirect(getSessionUI().getDashboard());
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	/**
	 * Final Rejection
	 */
	public void finalRejection() throws Exception
	{
		try {
			
			if(sdpextensionofscope.getApprovalStatus()==ApprovalEnum.RejectedByEQTA){
				selectedRejectReason=(ArrayList<RejectReasons>) applicationRejectReasons;
			}
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");				
			}
			else{
				service.finalRejection(sdpextensionofscope,selectedRejectReason,getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("uploadDone()");
			super.ajaxRedirect(getSessionUI().getDashboard());
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void extensionOfScopeETQARejection() throws Exception
	{
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");				
			}
			else{
				service.extensionOfScopeETQARejection(sdpextensionofscope,getSessionUI().getActiveUser(),selectedRejectReason);
				lazyLoadByStatusInfo();
			}
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void nonMerSETAExtensionOfScopeRejection() throws Exception
	{
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");				
			}
			else{
				service.nonMerSETAExtensionOfScopeRejection(sdpextensionofscope, selectedRejectReason, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			super.ajaxRedirect(getSessionUI().getDashboard());
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * complete task.
	 */
	public void completeTask() {
		try {
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
//				validateSelfEvaluation(auditorMonitorReviewDataModelList);
				validateSelfEvaluationLazyLoaded();
			} else if(getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
				validateSiteVisit(sdpextensionofscope);
				validateSelfEvaluationCommentsLazyLoad();
//				validateSelfEvaluationComments(auditorMonitorReviewDataModelList);
			}
			sdpextensionofscope.setSiteVisitDone(onAfterSiteVistDate);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) newQualificationList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) newUnitStandards);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) newSkillsProgramList);
			companyQualificationsService.update((List<IDataEntity>) (List<?>) tpSkillsSetList);
			service.completeTask(getSessionUI().getTask(), getSessionUI().getActiveUser(), sdpextensionofscope, reviewCommitteeMeeting,company,auditorMonitorReviewDataModelList,trainingProviderApplication);
			super.ajaxRedirect(getSessionUI().getDashboard());
			getSessionUI().setTask(null);
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
		}

	}
	
	public void siteVisitDateUpdate() {
		try {
			if (dateChangeReasonSelectionList.size() != 0) {
				service.update(sdpextensionofscope);
				trainingProviderApplicationService.createDateChangeReasons(sdpextensionofscope, getSessionUI().getActiveUser(), dateChangeReasonSelectionList, ConfigDocProcessEnum.SDPExtensionOfScope);
				dateChangeReasonSelectionList = null;
				dateChangeReasonAvalibleSelectionList = null;
				getSessionUI().getTask().setDueDate(sdpextensionofscope.getSiteVisitDate());
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
	
	public void sendSiteVisitAmendedEmailNotification() throws Exception
	{
		List<UnitStandards> unitStandardsList=new ArrayList<>();
		for(CompanyUnitStandard cus: newUnitStandards)
		{
			unitStandardsList.add(cus.getUnitStandard());
		}
		
		List<Qualification> qualificationsList=new ArrayList<>();
		for(CompanyQualifications cq: newQualificationList)
		{
			qualificationsList.add(cq.getQualification());
		}
		
		List<SkillsProgram> skillsProgramList=new ArrayList<>();
		for(TrainingProviderSkillsProgramme tpsp:newSkillsProgramList)
		{
			skillsProgramList.add(tpsp.getSkillsProgram());
		}
		
		List<SkillsSet> skillsSetList=new ArrayList<>();
		for(TrainingProviderSkillsSet tpss:tpSkillsSetList)
		{
			skillsSetList.add(tpss.getSkillsSet());
		}
		trainingProviderApplicationService.sendExtensionOfScopeSiteVisitAmendedEmail(sdpextensionofscope, getSessionUI().getActiveUser(), unitStandardsList, qualificationsList, skillsProgramList, skillsSetList);
		
	}
	
	private void validateSelfEvaluationComments(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2) {
			if(audit.getComment()==null || audit.getComment().isEmpty() ||audit.getComment().equals("") ) {
				throw new Exception("Please provide self evaluation comments");
			}
		}
	}
	
	private void validateSelfEvaluationCommentsLazyLoad() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeCommentNotProvided(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId()) > 0) {
			throw new Exception("Please provide on self evaluation: Evaluator Outcome: Evidence Available & Comments where missing");
		}
	}
	
	private void validateSelfEvaluation(List<AuditorMonitorReview> auditorMonitorReviewDataModelList2) throws Exception {
		for(AuditorMonitorReview audit: auditorMonitorReviewDataModelList2) {
			if(audit.getEvidenceRequired()==null && audit.getIsNotRelevant()==false)
			{
				throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
			}
		}
	}
	
	private void validateSelfEvaluationLazyLoaded() throws Exception {
		if (auditorMonitorReviewService.countByTargetClassKeyWhereEvidanceAvalaibleNotPorvidedWithRelevent(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId()) > 0) {
			throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
		}
		
		if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeIsTypeAndNoDocProvided(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId(), YesNoEnum.No ) > 0) {
			throw new Exception("Please upload required evidence where applicable.");
		}
	}

	private void validateSiteVisit(SDPExtensionOfScope sdpextensionofscope) throws Exception {
		
		if(sdpextensionofscope.getSiteVisitDate()==null || sdpextensionofscope.getSiteVisitReportDate()==null)
		{
			throw new Exception("Please complete site visit details");
		}
	}
	
	public void approveTtApplicationAndSendCertificate()
	{
		try {
			
			service.approveTtApplicationAndSendCertificate(sdpextensionofscope,trainingProviderApplication, newQualificationList, newUnitStandards,newSkillsProgramList,getSessionUI().getTask(),tpSkillsSetList);
			super.ajaxRedirect(getSessionUI().getDashboard());
			getSessionUI().setTask(null);
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extensionOfScopeETQAFinalApproval()
	{
		try {
			service.extensionOfScopeETQAFinalApproval(sdpextensionofscope,trainingProviderApplication, newQualificationList, newUnitStandards,newSkillsProgramList,tpSkillsSetList,getSessionUI().getTask());
			super.ajaxRedirect(getSessionUI().getDashboard());
			getSessionUI().setTask(null);
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveNonMerSETAExtensionOfScope()
	{
		try {
			checkIfQualificationIsApprove();
			service.approveNonMerSETAExtensionOfScope(sdpextensionofscope, newQualificationList, newUnitStandards,newSkillsProgramList,tpSkillsSetList,getSessionUI().getActiveUser(),getSessionUI().getTask());
			super.ajaxRedirect(getSessionUI().getDashboard());
			getSessionUI().setTask(null);
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
		if(newQualificationList !=null && newQualificationList.size()>0)
		{
			for(CompanyQualifications qual:newQualificationList)
			{
				if(qual.getAccept()!=null && qual.getAccept()==true)
				{
					approve=true;
				}
			}
		}
		
		if(!approve)
		{
			if(newUnitStandards !=null && newUnitStandards.size()>0)
			{
				for(CompanyUnitStandard qual:newUnitStandards)
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
			if(newSkillsProgramList !=null && newSkillsProgramList.size()>0)
			{
				for(TrainingProviderSkillsProgramme qual:newSkillsProgramList)
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
			throw new Exception("Please approve at least one qualificaiton, unit standard or skills programme");
		}
	}
	
	
	
	public void approveApplicationAndSendCertificate()
	{
		try {
			newQualificationList=companyQualificationsService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			newUnitStandards= companyUnitStandardService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			newSkillsProgramList=(ArrayList<TrainingProviderSkillsProgramme>) trainingProviderSkillsProgrammeService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			tpSkillsSetList=(ArrayList<TrainingProviderSkillsSet>) trainingProviderSkillsSetSevice.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
			service.approveApplicationAndSendCertificate(sdpextensionofscope,sdpextensionofscope.getTrainingProviderApplication(), newQualificationList, newUnitStandards,newSkillsProgramList,tpSkillsSetList);
			lazyLoadByStatusInfo();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extensionOfScopeETQAApproval()
	{
		try {
			service.extensionOfScopeETQAApproval(sdpextensionofscope,getSessionUI().getActiveUser());
			lazyLoadByStatusInfo();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	/********************End Action Button***********************/
	
	  
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
	
	public ArrayList<Company> companyList(SDPExtensionOfScope sdpextensionofscope)
	{
		ArrayList<Company> companyList = new ArrayList<>();
		CompanyService companyService=new CompanyService();
		try {
			Company company = companyService.findByKeyResolveDoc(sdpextensionofscope.getTrainingProviderApplication().getCompany().getId());
			if(company !=null)
			{
				companyList.add(company);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return companyList;
	}
	public ArrayList<CompanyUnitStandard> companyUnitStandard(SDPExtensionOfScope sdpextensionofscope)
	{
		ArrayList<CompanyUnitStandard> l=new ArrayList<>();
		try {
			l= (ArrayList<CompanyUnitStandard>) companyUnitStandardService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public ArrayList<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammes(SDPExtensionOfScope sdpextensionofscope)
	{
		ArrayList<TrainingProviderSkillsProgramme> l=new ArrayList<>();
		try {
			
			l=(ArrayList<TrainingProviderSkillsProgramme>) trainingProviderSkillsProgrammeService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public ArrayList<TrainingProviderSkillsSet> trainingProviderSkillsSet(SDPExtensionOfScope sdpextensionofscope)
	{
		ArrayList<TrainingProviderSkillsSet> l=new ArrayList<>();
		try {
			
			l=(ArrayList<TrainingProviderSkillsSet>) trainingProviderSkillsSetService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public  ArrayList<CompanyQualifications> companyQualifications(SDPExtensionOfScope sdpextensionofscope)
	{
		 ArrayList<CompanyQualifications> l=new ArrayList<>();
		try {
			l= (ArrayList<CompanyQualifications>) companyQualificationsService.findByTargetClassAndTargetKey(sdpextensionofscope.getClass().getName(), sdpextensionofscope.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
		return l;
	}

	public void downloadSelfEvaluationForm()
	{
		
		try {
			
			trainingProviderApplicationService.downloadSelfEvaluationForm(null, sdpextensionofscope);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/************************************Non merSETA Extension Of Scope****************************************/
	
	public void requestNonMerSETAExtensionOfScope()
	{
		try {
			if (selectTrainingProviderApplication.getCodeOfConductAccepted() == null || !selectTrainingProviderApplication.getCodeOfConductAccepted()) {
				throw new Exception("Please accept Code Of Conduct");
			}
			sdpextensionofscope.setUsers(getSessionUI().getActiveUser());
			sdpextensionofscope.setTrainingProviderApplication(selectTrainingProviderApplication);
			service.requestNonMerSETAExtensionOfScope(getSessionUI().getActiveUser(), sdpextensionofscope, companyQualifications, companyUnitStandards, tpSkillsProgramList, tpSkillsSetList, docParentList);
			addInfoMessage("Your application is being processed");
			clearRequestExtensionOfScope();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void removeCompanyQualification() {
		try {
			companyQualifications.remove(companyQualification);
			removeSKillsProgrammesCompanyQual(companyQualification);
			removeSkillsSetCompanyQual(companyQualification);
			addWarningMessage("Qualification removed");
			resetDoneQualification();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSKillsProgrammesCompanyQual(CompanyQualifications companyQualifications) {		
		List<TrainingProviderSkillsProgramme>tempList=new ArrayList<>();
		tempList.addAll(tpSkillsProgramList);
		for(TrainingProviderSkillsProgramme sp:tpSkillsProgramList) {
			if(sp.getSkillsProgram().getQualification().getId().equals(companyQualifications.getQualification().getId())) {
				tempList.remove(sp);
			}
		}
		tpSkillsProgramList.clear();
		if (!tempList.isEmpty()) {
			tpSkillsProgramList.addAll(tempList);
		}
		tempList.clear();
	}
	
	public void removeTpSkillsProgram(){
		try {
			tpSkillsProgramList.remove(tpSkillsProgramme);
			addWarningMessage("Skills Programme removed");
			resetDoneQualification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeSkillsSetCompanyQual(CompanyQualifications companyQualifications) {		
		List<TrainingProviderSkillsSet>tempList=new ArrayList<>();
		tempList.addAll(tpSkillsSetList);
		for(TrainingProviderSkillsSet ss : tpSkillsSetList) {
			if(ss.getSkillsSet().getQualification().getId().equals(companyQualifications.getQualification().getId())) {
				tempList.remove(ss);
			}
		}
		tpSkillsSetList.clear();
		if (!tempList.isEmpty()) {
			tpSkillsSetList.addAll(tempList);
		}
		tempList.clear();
	}
	
	public void removeTpSkillsSet()
	{
		try {
			tpSkillsSetList.remove(tpSkillsSet);
			addWarningMessage("Skills Set removed");
			resetDoneQualification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeTpUnitStandards()
	{
		try {
			companyUnitStandards.remove(companyUnitStandard);
			addWarningMessage("Unit Standard removed");
			resetDoneQualification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Adds the Skills Programme to list.
	 */
	public void addNonSetaExtOfScopeSkillsPrg() {
		try {
			checkIfSkillsProgAdded();
			if(tpSkillsProgramList !=null && tpSkillsProgramList.size()>0 && tpSkillsProgramList.get(0).getTrainingProviderDocParent() !=null){
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme();
				tpSp.setSkillsProgram(skillsProgram);
				tpSp.setTrainingProviderDocParent(companyQualifications.get(0).getTrainingProviderDocParent());
				tpSp.setCannotRemove(false);
				tpSkillsProgramList.add(tpSp);
			} else{
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme();
				tpSp.setCannotRemove(false);
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
	
	/**
	 * Adds the qualification to list.
	 */
	public void addNonSetaExOfScopeQualification() {
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
			selectTrainingProviderApplication.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.QCTOTradeTestCentre)
			{
				addSKillsProgrammesCompanyQualifications();
				addSKillsSetCompanyQualifications();
				//TO DO: Auto populate SS and US
			}
			addInfoMessage("Qualification added");
			resetDoneQualification();
			prepareNewQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	

	/**
	 * Adds the unit to list.
	 */
	public void addNonSetaUnitStandardsToList() {

		try {
			checkIfUsAdded();
			if(companyUnitStandards!=null && companyUnitStandards.size()>0 && companyUnitStandards.get(0).getTrainingProviderDocParent() !=null)
			{
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
				companyUnitStandard.setCannotRemove(false);
				companyUnitStandard.setTrainingProviderDocParent(companyUnitStandards.get(0).getTrainingProviderDocParent());
				companyUnitStandards.add(companyUnitStandard);
			}
			else{
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, selectTrainingProviderApplication.getClass().getName(), selectTrainingProviderApplication.getId());
				companyUnitStandard.setCannotRemove(false);
				companyUnitStandards.add(companyUnitStandard);
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
	public void addNonSetaSkillsSetToList() {

		try {
			checkIfSkillsSetAdded();
			if(tpSkillsSetList!=null && tpSkillsSetList.size()>0 && tpSkillsSetList.get(0).getTrainingProviderDocParent() !=null) {
				TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, selectTrainingProviderApplication);
				tpSkillsSet.setTrainingProviderDocParent(tpSkillsSetList.get(0).getTrainingProviderDocParent());
				tpSkillsSet.setCannotRemove(false);
				tpSkillsSetList.add(tpSkillsSet);
			} else{
				TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, selectTrainingProviderApplication);
				tpSkillsSet.setCannotRemove(false);
				tpSkillsSetList.add(tpSkillsSet);
			}
			resetDoneQualification();
			prepareNewQualification();
			addInfoMessage("Skills Set added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void checkIfSkillsSetAdded() throws Exception
	{
		if(skillsSet==null){throw new Exception("Please Select Skills Set");}
		TrainingProviderSkillsSetService providerSkillsSetService=new TrainingProviderSkillsSetService();
		int count= providerSkillsSetService.countfindByCompany(selectTrainingProviderApplication.getCompany(), skillsSet, true);
		if(count>0){throw new Exception("You are already accredited for this Skills Set");}
		for(TrainingProviderSkillsSet tpss: tpSkillsSetList){
			if(skillsSet.equals(tpss.getSkillsSet())){
				throw new Exception("Skills Setalready in the list");
			}
		}
		
	}
	
	public void checkIfUsAdded() throws Exception
	{
		if(unitStandard==null){throw new Exception("Please Select Unit Standard");}
		CompanyUnitStandardService companyUnitStandardService=new CompanyUnitStandardService();
		int count=companyUnitStandardService.countfindByCompany(selectTrainingProviderApplication.getCompany(), unitStandard, true);
		if(count>0){throw new Exception("You are already accredited for this Unit Standard");}
		for(CompanyUnitStandard cus: companyUnitStandards){
			if(unitStandard.equals(cus.getUnitStandard())){
				throw new Exception("Unit Standard already in the list");
			}
		}
		
	}
	 public void checkIfQualAdded() throws Exception
	 {
		 
		  if(qualification !=null){
			  CompanyQualifications companyQualification= companyQualificationsService.findByCompany(selectTrainingProviderApplication.getCompany(), qualification, true);
			  if(companyQualification !=null){throw new Exception("You are already accredited for this qualification");}
			  for(CompanyQualifications qc:companyQualifications){
				  if(qualification.equals(qc.getQualification())){
					  throw new Exception("Qualification already in the list");
				  }
			  }
		  }
		  else{
			  throw new Exception("Please Select Qualification");
		  }
		  
	  }
	public void checkIfSkillsProgAdded() throws Exception
	{
		//Check if you are not accredeted for qual
		if(skillsProgram !=null)
		{
			TrainingProviderSkillsProgrammeService tpSpService=new TrainingProviderSkillsProgrammeService();
			List<TrainingProviderSkillsProgramme> tpSplist=tpSpService.findByTrainingProviderAndSkillsProgAndAccept(selectTrainingProviderApplication.getId(), skillsProgram.getId(), true);
			if(tpSplist !=null && tpSplist.size()>0){throw new Exception("You are already accredited for this Skills Programme");}
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
	
	public void resetDoneQualification()
	{
		doneQualification=false;
		runClientSideUpdate("pgResubmissionQualDetails");
	}
	public void doneQualificationDetails()
	{
		try {
			if (companyQualifications.size() > 0 || companyUnitStandards.size() > 0 || tpSkillsProgramList.size() > 0 || tpSkillsSetList.size() > 0) {
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
		if(companyUnitStandards !=null && companyUnitStandards.size()>0)
		{
			if(companyUnitStandards.get(0).getTrainingProviderDocParent() !=null)
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
	public void addCmpanyUnitStandardBetch() {
		usUplaoaded = true;
		try {
			if (docParent.getDoc() == null || docParent.getDoc().getData() == null) {
				throw new Exception("Upload suporting documents");
			}
			docParentList.add(docParent);
			for (CompanyUnitStandard compUS : companyUnitStandards) {
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
	
	public void addTPSkillsSetBetch() {
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
	
	public void addTPSkillsProgremeBetch() {
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
	/**********************************End Non merSETA Extension Of Scope**************************************/
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<SDPExtensionOfScope> complete(String desc) {
		List<SDPExtensionOfScope> l = null;
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
    
    public void prepUpdateAuditorMonitorReview() {
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
	
	public void autoAcceptsSkillsProgramSetByQualification(CompanyQualifications compQ){
		try {
			selectedCompanyQualification = compQ;
			// trainingProviderApplication.accreditationApplicationTypeEnum
			if (selectedCompanyQualification != null && selectedCompanyQualification.getQualification() != null && trainingProviderApplication != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() != null && trainingProviderApplication.getAccreditationApplicationTypeEnum() != AccreditationApplicationTypeEnum.QCTOTradeTestCentre) {
				// apply to skills programs
				if (newSkillsProgramList != null && !newSkillsProgramList.isEmpty()) {
					for (TrainingProviderSkillsProgramme sp : newSkillsProgramList) {
						if (sp.getSkillsProgram() != null && sp.getSkillsProgram().getQualification() != null && sp.getSkillsProgram().getQualification().getId() != null) {
							if (sp.getSkillsProgram().getQualification().getId().equals(selectedCompanyQualification.getQualification().getId())) {
								sp.setAccept(selectedCompanyQualification.getAccept());
							}
						}
					}
				}
				// apply to skills set
				if (tpSkillsSetList != null && !tpSkillsSetList.isEmpty()) {
					for (TrainingProviderSkillsSet ss : tpSkillsSetList) {
						if (ss.getSkillsSet() != null && ss.getSkillsSet().getQualification() != null && ss.getSkillsSet().getQualification().getId() != null) {
							if (ss.getSkillsSet().getQualification().getId().equals(selectedCompanyQualification.getQualification().getId())) {
								ss.setAccept(selectedCompanyQualification.getAccept());
							}
						}
					}
				}
			}
			selectedCompanyQualification = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
    
    public List<SDPExtensionOfScope> getSDPExtensionOfScopeList() {
		return sdpextensionofscopeList;
	}
	public void setSDPExtensionOfScopeList(List<SDPExtensionOfScope> sdpextensionofscopeList) {
		this.sdpextensionofscopeList = sdpextensionofscopeList;
	}
	public SDPExtensionOfScope getSdpextensionofscope() {
		return sdpextensionofscope;
	}
	public void setSdpextensionofscope(SDPExtensionOfScope sdpextensionofscope) {
		this.sdpextensionofscope = sdpextensionofscope;
	}

    public List<SDPExtensionOfScope> getSDPExtensionOfScopefilteredList() {
		return sdpextensionofscopefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sdpextensionofscopefilteredList the new sdpextensionofscopefilteredList list
 	 * @see    SDPExtensionOfScope
 	 */	
	public void setSDPExtensionOfScopefilteredList(List<SDPExtensionOfScope> sdpextensionofscopefilteredList) {
		this.sdpextensionofscopefilteredList = sdpextensionofscopefilteredList;
	}

	
	public LazyDataModel<SDPExtensionOfScope> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SDPExtensionOfScope> dataModel) {
		this.dataModel = dataModel;
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
	
	public ArrayList<SkillsProgram> getSkillsProgramList() {
		return skillsProgramList;
	}

	public void setSkillsProgramList(ArrayList<SkillsProgram> skillsProgramList) {
		this.skillsProgramList = skillsProgramList;
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

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}
	public List<SDPExtensionOfScope> getSdpextensionofscopeList() {
		return sdpextensionofscopeList;
	}

	public void setSdpextensionofscopeList(List<SDPExtensionOfScope> sdpextensionofscopeList) {
		this.sdpextensionofscopeList = sdpextensionofscopeList;
	}

	public List<SDPExtensionOfScope> getSdpextensionofscopefilteredList() {
		return sdpextensionofscopefilteredList;
	}

	public void setSdpextensionofscopefilteredList(List<SDPExtensionOfScope> sdpextensionofscopefilteredList) {
		this.sdpextensionofscopefilteredList = sdpextensionofscopefilteredList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public UnitStandardsService getUnitStandardsService() {
		return unitStandardsService;
	}

	public void setUnitStandardsService(UnitStandardsService unitStandardsService) {
		this.unitStandardsService = unitStandardsService;
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

	public List<CompanyUnitStandard> getCompanyUnitStandards() {
		return companyUnitStandards;
	}

	public void setCompanyUnitStandards(List<CompanyUnitStandard> companyUnitStandards) {
		this.companyUnitStandards = companyUnitStandards;
	}

	public List<TrainingProviderSkillsProgramme> getTpSkillsProgramList() {
		return tpSkillsProgramList;
	}

	public void setTpSkillsProgramList(List<TrainingProviderSkillsProgramme> tpSkillsProgramList) {
		this.tpSkillsProgramList = tpSkillsProgramList;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelList() {
		return auditorMonitorReviewDataModelList;
	}

	public void setAuditorMonitorReviewDataModelList(List<AuditorMonitorReview> auditorMonitorReviewDataModelList) {
		this.auditorMonitorReviewDataModelList = auditorMonitorReviewDataModelList;
	}


	public String getExceptions() {
		return exceptions;
	}

	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
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

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public List<CompanyQualifications> getNewQualificationList() {
		return newQualificationList;
	}

	public void setNewQualificationList(List<CompanyQualifications> newQualificationList) {
		this.newQualificationList = newQualificationList;
	}

	public List<CompanyUnitStandard> getNewUnitStandards() {
		return newUnitStandards;
	}

	public void setNewUnitStandards(List<CompanyUnitStandard> newUnitStandards) {
		this.newUnitStandards = newUnitStandards;
	}

	public ArrayList<TrainingProviderSkillsProgramme> getNewSkillsProgramList() {
		return newSkillsProgramList;
	}

	public void setNewSkillsProgramList(ArrayList<TrainingProviderSkillsProgramme> newSkillsProgramList) {
		this.newSkillsProgramList = newSkillsProgramList;
	}

	public LazyDataModel<SDPExtensionOfScope> getPendingAppDataModel() {
		return pendingAppDataModel;
	}

	public void setPendingAppDataModel(LazyDataModel<SDPExtensionOfScope> pendingAppDataModel) {
		this.pendingAppDataModel = pendingAppDataModel;
	}

	public ArrayList<TrainingProviderSkillsSet> getTpSkillsSetList() {
		return tpSkillsSetList;
	}

	public void setTpSkillsSetList(ArrayList<TrainingProviderSkillsSet> tpSkillsSetList) {
		this.tpSkillsSetList = tpSkillsSetList;
	}

	public ArrayList<SkillsSet> getSkillsSetList() {
		return skillsSetList;
	}

	public void setSkillsSetList(ArrayList<SkillsSet> skillsSetList) {
		this.skillsSetList = skillsSetList;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	
	public List<RejectReasons> getApplicationRejectReasons() {
		return applicationRejectReasons;
	}

	public void setApplicationRejectReasons(List<RejectReasons> applicationRejectReasons) {
		this.applicationRejectReasons = applicationRejectReasons;
	}

	public TrainingProviderApplication getSelectTrainingProviderApplication() {
		return selectTrainingProviderApplication;
	}

	public void setSelectTrainingProviderApplication(TrainingProviderApplication selectTrainingProviderApplication) {
		SDPExtensionOfScopeUI.selectTrainingProviderApplication = selectTrainingProviderApplication;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}

	public boolean isNoDateProvided() {
		return noDateProvided;
	}

	public void setNoDateProvided(boolean noDateProvided) {
		this.noDateProvided = noDateProvided;
	}

	public boolean isOnAfterSiteVistDate() {
		return onAfterSiteVistDate;
	}

	public void setOnAfterSiteVistDate(boolean onAfterSiteVistDate) {
		this.onAfterSiteVistDate = onAfterSiteVistDate;
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

	public List<CompanyQualifications> getApprovedCompanyQualifications() {
		return approvedCompanyQualifications;
	}

	public void setApprovedCompanyQualifications(List<CompanyQualifications> approvedCompanyQualifications) {
		this.approvedCompanyQualifications = approvedCompanyQualifications;
	}


	public List<TrainingProviderSkillsProgramme> getApprovedTpSkillsProgramList() {
		return approvedTpSkillsProgramList;
	}

	public void setApprovedTpSkillsProgramList(List<TrainingProviderSkillsProgramme> approvedTpSkillsProgramList) {
		this.approvedTpSkillsProgramList = approvedTpSkillsProgramList;
	}

	public List<TrainingProviderSkillsSet> getApprovedTpSkillsSetList() {
		return approvedTpSkillsSetList;
	}

	public void setApprovedTpSkillsSetList(List<TrainingProviderSkillsSet> approvedTpSkillsSetList) {
		this.approvedTpSkillsSetList = approvedTpSkillsSetList;
	}

	public List<CompanyUnitStandard> getApprovedCompanyUnitStandards() {
		return approvedCompanyUnitStandards;
	}

	public void setApprovedCompanyUnitStandards(List<CompanyUnitStandard> approvedCompanyUnitStandards) {
		this.approvedCompanyUnitStandards = approvedCompanyUnitStandards;
	}

	public Boolean getNonSetaExOfScope() {
		return nonSetaExOfScope;
	}

	public void setNonSetaExOfScope(Boolean nonSetaExOfScope) {
		this.nonSetaExOfScope = nonSetaExOfScope;
	}

	public List<TrainingProviderDocParent> getDocParentList() {
		return docParentList;
	}

	public void setDocParentList(List<TrainingProviderDocParent> docParentList) {
		this.docParentList = docParentList;
	}

	public TrainingProviderDocParent getDocParent() {
		return docParent;
	}

	public void setDocParent(TrainingProviderDocParent docParent) {
		this.docParent = docParent;
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

	public CompanyQualifications getCompanyQualification() {
		return companyQualification;
	}

	public void setCompanyQualification(CompanyQualifications companyQualification) {
		this.companyQualification = companyQualification;
	}

	public TrainingProviderSkillsProgramme getTpSkillsProgramme() {
		return tpSkillsProgramme;
	}

	public void setTpSkillsProgramme(TrainingProviderSkillsProgramme tpSkillsProgramme) {
		this.tpSkillsProgramme = tpSkillsProgramme;
	}

	public CompanyUnitStandard getCompanyUnitStandard() {
		return companyUnitStandard;
	}

	public void setCompanyUnitStandard(CompanyUnitStandard companyUnitStandard) {
		this.companyUnitStandard = companyUnitStandard;
	}

	public TrainingProviderSkillsSet getTpSkillsSet() {
		return tpSkillsSet;
	}

	public void setTpSkillsSet(TrainingProviderSkillsSet tpSkillsSet) {
		this.tpSkillsSet = tpSkillsSet;
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

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewEvidanceRequiredDataModelList() {
		return auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public void setAuditorMonitorReviewEvidanceRequiredDataModelList(
			LazyDataModel<AuditorMonitorReview> auditorMonitorReviewEvidanceRequiredDataModelList) {
		this.auditorMonitorReviewEvidanceRequiredDataModelList = auditorMonitorReviewEvidanceRequiredDataModelList;
	}

	public AuditorMonitorReview getSelectedAuditorMonitorReview() {
		return selectedAuditorMonitorReview;
	}

	public void setSelectedAuditorMonitorReview(AuditorMonitorReview selectedAuditorMonitorReview) {
		this.selectedAuditorMonitorReview = selectedAuditorMonitorReview;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public CompanyQualifications getSelectedCompanyQualification() {
		return selectedCompanyQualification;
	}

	public void setSelectedCompanyQualification(CompanyQualifications selectedCompanyQualification) {
		this.selectedCompanyQualification = selectedCompanyQualification;
	}

	
	
	
	
}
