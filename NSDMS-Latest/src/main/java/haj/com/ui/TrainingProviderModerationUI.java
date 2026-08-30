package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Doc;
import haj.com.entity.QdfCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.ScheduledEventService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "trainingprovidermoderationUI")
@ViewScoped
public class TrainingProviderModerationUI extends AbstractUI {
	private ScheduledEventService scheduledEventService = new ScheduledEventService();
	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private AuditorMonitorReview auditorMonitorReview;
	private Doc doc;
	private Doc newdoc;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();
	boolean provided = true;	
	private ScheduledEvent scheduledEvent;
	private Date reviewDate;
	private boolean viewLearnerData = false;
	private boolean action = false;
	/** The company. */
	private Company taskCompany;
	/** The company. */
	private Company company;

	/** The company service. */
	private CompanyService companyService = new CompanyService();
	private CompanyLearners companylearners;
	private CompanyLearnersTransfer companyLearnersTransfer;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	//private TrainingProviderVerfication trainingProviderVerficationParent;
	boolean show = false;
	boolean signoff = false;
	private String meetingMessage;
	
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelLearner;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany;	
	private SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards;
	private SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
	
	private QualificationTypeSelectionEnum qualificationTypeSelectionEnum;
	private boolean disablefields = true;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ProviderVerificationModeration) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			scheduledEvent = scheduledEventService.findByKey(getSessionUI().getTask().getTargetKey());	
			this.taskCompany = scheduledEvent.getCompany()	;		
			companyService.resolveContactPerson(taskCompany);
			trainingProviderVerficationInformation();		
			disablefields = false;
			if(this.scheduledEvent != null && this.scheduledEvent.getId() != null && this.scheduledEvent.getFromDateTime() != null){
				checkModerationDate(this.scheduledEvent.getFromDateTime());
			}		
			populateCompanyModerationData();
			checkIfMustEdit();
			if(this.scheduledEvent != null && this.scheduledEvent.getStatus() == ApprovalEnum.Rejected) {				
				populateRejectReasons();
			}
			if(getSessionUI().getTask().getFirstInProcess()) {
				show = true;
			}else {
				signoff = true;
			}
			doc = new Doc();
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.ProviderVerificationModeration, scheduledEvent, scheduledEvent,getSessionUI().getTask().getProcessRole());
		}else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ProviderVerificationModerationApproval) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			scheduledEvent = scheduledEventService.findByKey(getSessionUI().getTask().getTargetKey());	
			this.taskCompany = scheduledEvent.getCompany()	;		
			companyService.resolveContactPerson(taskCompany);
			trainingProviderVerficationInformation();		
			
			if(this.scheduledEvent != null && this.scheduledEvent.getId() != null){
				checkModerationDate(this.scheduledEvent.getFromDateTime());
			}		
			populateCompanyModerationData();
			checkIfMustEdit();
			if(this.scheduledEvent != null && this.scheduledEvent.getStatus() == ApprovalEnum.Rejected) {
				populateRejectReasons();
			}
			if(getSessionUI().getTask().getFirstInProcess()) {
				show = true;
			}else {
				signoff = true;
			}
			doc = new Doc();
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.ProviderVerificationModerationApproval, scheduledEvent, scheduledEvent, getSessionUI().getTask().getProcessRole());
		}else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ProviderVerification) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.taskCompany  = companyService.findByKey(getSessionUI().getTask().getTargetKey());
			companyService.resolveContactPerson(taskCompany);
			verificationInfo();
			scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(this.taskCompany.getId(), TrainingProviderVerfication.class.getName());
			if (scheduledEvent == null) {
				this.scheduledEvent = new ScheduledEvent();
			}else if(this.scheduledEvent != null && this.scheduledEvent.getId() != null){
				checkModerationDate(this.scheduledEvent.getFromDateTime());
			}
			if(getSessionUI().getTask().getFirstInProcess() == null) {
				populateRejectReasons();
			}
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.ProviderVerification, scheduledEvent, scheduledEvent, getSessionUI().getTask().getProcessRole());
		}else {
			ajaxRedirectToDashboard();
		}
	}
	
	private void checkIfMustEdit() {
		if(getSessionUI().getTask() !=null && getSessionUI().getTask().getFirstInProcess()) {
			if(this.scheduledEvent != null && this.scheduledEvent.getId() != null && this.scheduledEvent.getFromDateTime() != null){
				if(checkModerationDate(this.scheduledEvent.getFromDateTime())) {
					show = true;
				}else {
					show = false;
				}
			}
		}else {
			show = false;
		}
	}

	private void populateCompanyModerationData() {
		try {			
			auditorMonitorReviewDataModelCompany = auditorMonitorReviewService.findByTargetKeyAndClass(scheduledEvent.getClass().getName(), scheduledEvent.getId());	
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}

	private void populateLearnerModerationData() {
		try {
			auditorMonitorReviewDataModelLearner = auditorMonitorReviewService.findByTargetKeyAndClass(trainingproviderverfication.getClass().getName(), trainingproviderverfication.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private void validateSelfEvaluation(List<AuditorMonitorReview> list) throws Exception {
		for(AuditorMonitorReview audit: list){
			if(audit.getEvidenceRequired()==null && audit.getIsNotRelevant()==false){
				throw new Exception("Please complete self evaluation details by specifying if evidence is required or not");
			}
		}		
	}
	
	private void validateSelfEvaluationComments(List<AuditorMonitorReview> list) throws Exception {
		for(AuditorMonitorReview audit: list){
			if(audit.getComment()==null || audit.getComment().isEmpty() ||audit.getComment().equals("") ){
				throw new Exception("Please provide self evaluation comments");
			}
		}
	}
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("trainingProviderID", taskCompany.getId());
					filters.put("status", ApprovalEnum.PendingApproval);
					retorno = service.allProviderVerfication(first, pageSize, sortField, sortOrder, filters);
					/*for(TrainingProviderVerfication tpv :retorno) {
						tpv.setAuditorMonitorReviews(auditorMonitorReviewService.findByTargetKeyAndClass(tpv.getClass().getName(), tpv.getId()));
					}*/
					dataModel.setRowCount(service.countProviderVerfication(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(TrainingProviderVerfication obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public TrainingProviderVerfication getRowData(String rowKey) {
		        for(TrainingProviderVerfication obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}
	public void trainingProviderVerficationInfo() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();

			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(getSessionUI().getTask().getFirstInProcess() && scheduledEvent.getStatus() != ApprovalEnum.Rejected) {
						filters.put("status", ApprovalEnum.PendingApproval);
						filters.put("trainingProviderID", taskCompany.getId());
						filters.put("scheduledEventID", scheduledEvent.getId());
						retorno = service.sortAndFilterSearchScheduledEvent(first, pageSize, sortField, sortOrder, filters);
						for(TrainingProviderVerfication trainingProviderVerfication: retorno) {
							trainingProviderVerfication.setCompanyLearners(CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId()));
						}
						dataModel.setRowCount(service.countSearchScheduledEvent(filters));
					}else {
						//filters.put("status", ApprovalEnum.Recommended);
						filters.put("trainingProviderVerficationParentID", scheduledEvent.getId());
						retorno = service.sortAndFilterSearchParent(first, pageSize, sortField, sortOrder, filters);
						for(TrainingProviderVerfication trainingProviderVerfication: retorno) {
							trainingProviderVerfication.setCompanyLearners(CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId()));
						}
						dataModel.setRowCount(service.countSearchParent(filters));
					}
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderVerfication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderVerfication getRowData(String rowKey) {
				for (TrainingProviderVerfication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void trainingProviderVerficationInformation() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();

			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {	
					if(getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess()!= null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.ProviderVerificationModerationApproval) {
						filters.put("status", ApprovalEnum.PendingFinalApproval);
					}else {
						filters.put("status", ApprovalEnum.PendingApproval);
					}
					
					filters.put("trainingProviderID", taskCompany.getId());
					filters.put("scheduledEventID", scheduledEvent.getId());
					retorno = service.sortAndFilterSearchScheduledEvent(first, pageSize, sortField, sortOrder, filters);
					for(TrainingProviderVerfication trainingProviderVerfication: retorno) {
						trainingProviderVerfication.setCompanyLearners(CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId()));
					}
					dataModel.setRowCount(service.countSearchScheduledEvent(filters));					
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderVerfication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderVerfication getRowData(String rowKey) {
				for (TrainingProviderVerfication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void saveUnitStandards() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssesmentDate() != null){
				if(summativeAssessmentReportUnitStandards.getCompetenceEnum() != null){
					summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				}else {
					addErrorMessage("Please provide competence");
				}
			}else {
				addErrorMessage("Please provide assesment date");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(this.taskCompany.getId(), TrainingProviderVerfication.class.getName(), ConfigDocProcessEnum.ProviderVerificationModeration);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	public void viewCompanyLearnerData() {
		try {
			companylearners = trainingproviderverfication.getCompanyLearners();
			companyLearnersService.resolveAllData(companylearners);
			companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearners);
			this.companylearners = companyLearnersService.resolveEverything(companylearners, ConfigDocProcessEnum.Learner);
			this.companylearners.setDocs(DocService.instance().searchByTargetKeyAndClass(this.companylearners.getClass().getName(), this.companylearners.getId(), ConfigDocProcessEnum.Learner));
			this.company = companyService.findByKey(companylearners.getCompany().getId());
			populateLearnerModerationData();
			summativeassessmentreportInfo();
			this.viewLearnerData = true;
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void trainingproviderverficationInfo() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();

			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allTrainingProviderVerfication(TrainingProviderVerfication.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderVerfication obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderVerfication getRowData(String rowKey) {
				for (TrainingProviderVerfication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}



	private void checkFieldsprovided() {	
		if(assessmentReport!= null) {
			for(SummativeAssessmentReportUnitStandards sr : assessmentReport.getUnitStandards()) {
				if(sr.getCompetenceEnum() == null || sr.getAssesmentDate() == null) {
					provided = false;
					break;
				}else {
					provided = true;
				}
			}
		}
	}
	
	private boolean checkFieldsprovided(List<AuditorMonitorReview> auditorMonitorReviews) {	
		boolean isProvided = false;
		if(auditorMonitorReviews!= null) {
			for(AuditorMonitorReview sr : auditorMonitorReviews) {
				if(sr.getEvidenceRequired() == null || sr.getComment()== null) {
					isProvided = false;
					break;
				}else {
					isProvided = true;
				}
			}
		}		
		return isProvided;
	}
	
	public void setVisitDate() {
		try {
			service.setFirstVisitDate(trainingproviderverfication, getSessionUI().getActiveUser());
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void setReviewDateForApproval() {
		try {
			scheduledEventService.update(scheduledEvent);
			//service.setFirstVisitDate(this.trainingProviderVerficationParent, getSessionUI().getActiveUser(), scheduledEvent, taskCompany);
			//scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(trainingProviderVerficationParent.getId(), TrainingProviderVerfication.class.getName());
			checkIfMustEdit();
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void updateReviewDateForApproval() {
		try {
			scheduledEventService.update(scheduledEvent);
			//service.updateReviewDateForApproval(this.trainingProviderVerficationParent, getSessionUI().getActiveUser(), scheduledEvent, taskCompany);
			//scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(trainingProviderVerficationParent.getId(), TrainingProviderVerfication.class.getName());
			checkIfMustEdit();
			addInfoMessage("Date Updated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void summativeassessmentreportInfo() {

		dataModelSummativeAssessmentReport = new LazyDataModel<SummativeAssessmentReport>() {
			private static final long serialVersionUID = 1L;
			private List<SummativeAssessmentReport> retorno = new ArrayList<SummativeAssessmentReport>();

			@Override
			public List<SummativeAssessmentReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					filters.put("trainingProviderVerficationID", trainingproviderverfication.getId());
					retorno = summativeAssessmentReportService.allSummativeAssessmentReportForVerification(first, pageSize, sortField, sortOrder, filters);
					dataModelSummativeAssessmentReport.setRowCount(summativeAssessmentReportService.countForVerification(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SummativeAssessmentReport obj) {
				return obj.getId();
			}

			@Override
			public SummativeAssessmentReport getRowData(String rowKey) {
				for (SummativeAssessmentReport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void trainingproviderverficationInsert() {
		try {
			service.create(this.trainingproviderverfication);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void trainingproviderverficationUpdate() {
		try {
			service.update(this.trainingproviderverfication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void trainingproviderverficationDelete() {
		try {
			service.delete(this.trainingproviderverfication);
			prepareNew();
			trainingproviderverficationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void prepareNew() {
		trainingproviderverfication = new TrainingProviderVerfication();
	}

	public List<TrainingProviderVerfication> complete(String desc) {
		List<TrainingProviderVerfication> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			e.printStackTrace();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}
	
	
	public void completeWorkflowDataModel() {
		try {
			if(!signoff) {
				throw new Exception("Please signoff before you proceed");
			}
			//if(checkModerationDate(this.scheduledEvent.getFromDateTime())) {
				//if(this.trainingProviderVerficationParent.getCompanyModerationDone() != null && this.trainingProviderVerficationParent.getCompanyModerationDone() == true) {
					service.completeWorkflowDataModel(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
					ajaxRedirectToDashboard();
				/*}else {
					addErrorMessage("Please complete moderation assessment first");
				}*/
			/*} else {
				addErrorMessage("Wait for scheduled date");
			}*/
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createQualityAssurerTask() {
		try {			
			service.createQualityAssurerTask(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private boolean checkModerationDate(Date modDate) {
		boolean isAfter = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String moddate = sdf.format(modDate);
		String now = sdf.format(getNow());
		
		try {
			Date moderationdate = sdf.parse(moddate);
			Date nowdate = sdf.parse(now);
			
	        if (moderationdate.equals(nowdate) ) {
	        	isAfter = true;
	        	action = true;
	        }
	        
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
		return isAfter;
	}
	
	public void completeWorkflow() {
		try {
			service.completeWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void completeCollectionWorkflow() {
		try {
			service.completeCollectionWorkflow(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createWorkflowToQualityAssuror() {
		try {
			List<TrainingProviderVerfication>list =service.findByUserAndStatus(getSessionUI().getActiveUser(), ApprovalEnum.PendingApproval);
			service.createWorkflowToQualityAssuror(this.taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), list, false, scheduledEvent);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	public void completeWorkflowForModeration() {
		try {
			service.completeWorkflowForModeration(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void completeWorkflowToQualityAssuror() {
		try {
			service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void completeWorkflowFinal() {
		try {
			service.completeWorkflowFinal(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void approveWorkflow() {
		try {
			if(scheduledEvent != null && scheduledEvent.getId() != null && scheduledEvent.getFromDateTime() != null) {
				service.approveWorkflow(this.taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), this.scheduledEvent);
				ajaxRedirectToDashboard();
			}else {
				addErrorMessage("Please add review date before you proceed");
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void reviewApproveCompanyLearners() {
		try {
			service.approveWorkflow(this.taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), this.scheduledEvent);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadReport() {
		try {
			checkFieldsprovided();
			if(provided) {
				summativeAssessmentReportService.downloadReport(assessmentReport, getSessionUI().getActiveUser());
			}else {
				addErrorMessage("Please provide required information");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalApproveModeration() {
		try {
			service.finalApproveModeration(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void approveModerationWorkflow() {
		try {
			
			service.approveModerationWorkflow(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createLearnersForModerate() {
		try {	
			if(scheduledEvent.getPercentageEnum() == null) {
				throw new Exception("Please provide percentage");
			}
			service.createLearnersForModerateNew(scheduledEvent);
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.rejectModetationWorkflow(this.taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void upholdWorkflow() {
		try {
			service.upholdWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void finalApprove() {
		try {
			//service.finalApprove(scheduledEvent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.ProviderVerification);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}
	
	public void createUpdateCompanyModerationReport() {
		try {
			if(checkFieldsprovided(auditorMonitorReviewDataModelCompany)) {
				service.createUpdateCompanyModerationReport(auditorMonitorReviewDataModelCompany, scheduledEvent, getSessionUI().getActiveUser());
			}else {
				addErrorMessage("Please provide required information");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createUpdateLearnerModerationReport() {
		try {
			if(checkFieldsprovided(auditorMonitorReviewDataModelLearner)) {
				service.createUpdateLearnerModerationReport(auditorMonitorReviewDataModelLearner, trainingproviderverfication, getSessionUI().getActiveUser());
			}else {
				addErrorMessage("Please provide required information");
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadCompanyModerationReport() {
		try {
			service.downloadCompanyModerationReport(scheduledEvent, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadLearnerModerationReport() {
		try {
			service.downloadLearnerModerationReport(auditorMonitorReviewDataModelLearner, trainingproviderverfication, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void prepNewDoc() {
		try {
			doc = new Doc();
			newdoc = new Doc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void storeNewFile(FileUploadEvent event) {		
		try {
			if(scheduledEvent == null || scheduledEvent.getId() == null) {
				throw new Exception("There is no meeting scheduled for this application");
			}
			newdoc.setData(event.getFile().getContents());
			newdoc.setOriginalFname(event.getFile().getFileName());
			newdoc.setExtension(FilenameUtils.getExtension(newdoc.getOriginalFname()));
			if (newdoc.getId() != null) {
				DocService.instance().uploadNewVersion(newdoc, getSessionUI().getActiveUser());
			} else {
				newdoc.setTargetKey(this.trainingproviderverfication.getCompanyLearners().getId());
				newdoc.setTargetClass(TrainingProviderVerfication.class.getName());
				if (newdoc.getId() == null) {
					DocService.instance().save(newdoc, newdoc.getData(), newdoc.getOriginalFname(), getSessionUI().getActiveUser());
				}
			}	
			super.runClientSideExecute("PF('dlgUpload1').hide()");			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<TrainingProviderVerfication> getTrainingProviderVerficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingProviderVerficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
	}

	public TrainingProviderVerfication getTrainingproviderverfication() {
		return trainingproviderverfication;
	}

	public void setTrainingproviderverfication(TrainingProviderVerfication trainingproviderverfication) {
		this.trainingproviderverfication = trainingproviderverfication;
	}

	public List<TrainingProviderVerfication> getTrainingProviderVerficationfilteredList() {
		return trainingproviderverficationfilteredList;
	}

	public void setTrainingProviderVerficationfilteredList(List<TrainingProviderVerfication> trainingproviderverficationfilteredList) {
		this.trainingproviderverficationfilteredList = trainingproviderverficationfilteredList;
	}

	public LazyDataModel<TrainingProviderVerfication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderVerfication> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<SummativeAssessmentReport> getDataModelSummativeAssessmentReport() {
		return dataModelSummativeAssessmentReport;
	}

	public void setDataModelSummativeAssessmentReport(LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport) {
		this.dataModelSummativeAssessmentReport = dataModelSummativeAssessmentReport;
	}

	public SummativeAssessmentReport getAssessmentReport() {
		return assessmentReport;
	}

	public void setAssessmentReport(SummativeAssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
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

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}

	public Company getTaskCompany() {
		return taskCompany;
	}

	public void setTaskCompany(Company taskCompany) {
		this.taskCompany = taskCompany;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}

	public boolean isViewLearnerData() {
		return viewLearnerData;
	}

	public void setViewLearnerData(boolean viewLearnerData) {
		this.viewLearnerData = viewLearnerData;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getMeetingMessage() {
		return meetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		this.meetingMessage = meetingMessage;
	}

	/*public TrainingProviderVerfication getTrainingProviderVerficationParent() {
		return trainingProviderVerficationParent;
	}

	public void setTrainingProviderVerficationParent(TrainingProviderVerfication trainingProviderVerficationParent) {
		this.trainingProviderVerficationParent = trainingProviderVerficationParent;
	}*/

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelLearner() {
		return auditorMonitorReviewDataModelLearner;
	}

	public void setAuditorMonitorReviewDataModelLearner(List<AuditorMonitorReview> auditorMonitorReviewDataModelLearner) {
		this.auditorMonitorReviewDataModelLearner = auditorMonitorReviewDataModelLearner;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelCompany() {
		return auditorMonitorReviewDataModelCompany;
	}

	public void setAuditorMonitorReviewDataModelCompany(List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany) {
		this.auditorMonitorReviewDataModelCompany = auditorMonitorReviewDataModelCompany;
	}

	public Doc getNewdoc() {
		return newdoc;
	}

	public void setNewdoc(Doc newdoc) {
		this.newdoc = newdoc;
	}

	public AuditorMonitorReview getAuditorMonitorReview() {
		return auditorMonitorReview;
	}

	public void setAuditorMonitorReview(AuditorMonitorReview auditorMonitorReview) {
		this.auditorMonitorReview = auditorMonitorReview;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public SummativeAssessmentReportUnitStandards getSummativeAssessmentReportUnitStandards() {
		return summativeAssessmentReportUnitStandards;
	}

	public void setSummativeAssessmentReportUnitStandards(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards) {
		this.summativeAssessmentReportUnitStandards = summativeAssessmentReportUnitStandards;
	}

	public boolean isSignoff() {
		return signoff;
	}

	public void setSignoff(boolean signoff) {
		this.signoff = signoff;
	}

	public boolean isDisablefields() {
		return disablefields;
	}

	public void setDisablefields(boolean disablefields) {
		this.disablefields = disablefields;
	}
}
