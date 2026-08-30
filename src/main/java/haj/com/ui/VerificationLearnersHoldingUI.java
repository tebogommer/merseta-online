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

import haj.com.constants.HAJConstants;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.ModerationEnum;
import haj.com.entity.enums.NewOrLegacyEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.ScheduledEventService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionService;

@ManagedBean(name = "verificationLearnersHoldingUI")
@ViewScoped
public class VerificationLearnersHoldingUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private ScheduledEventService scheduledEventService = new ScheduledEventService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private LazyDataModel<Company> tpDataModel;
	private LazyDataModel<ScheduledEvent> dataModelScheduledEvent; 
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards;
	private SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
	private Doc doc;
	boolean provided = true;
	boolean show = false;
	boolean showlearners = false;
	private boolean qualityassuror = false;
	boolean signoff = false;
	private Company selectedCompany;
	private boolean moderate = false;
	private ScheduledEvent scheduledEvent;
	boolean assessor ;
	private NewOrLegacyEnum newOrLegacyEnum;
	
	private String idnumber;
	private AssessorModeratorApplication assessorModeratorApplication;

	private JobTitle jobTitle;
	private Region region;
	private RegionService regionService = new RegionService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService(); 
	private AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
	private LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplication;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany;	
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelLearner;
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private AuditorMonitorReview auditorMonitorReview;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		populateJobTitleAndRegion() ;
		//providerInfo() ;
	}
	
	private void populateJobTitleAndRegion() throws Exception {
		qualityassuror = false;
		if (!getSessionUI().isAdmin()) {
			if (getSessionUI().isEmployee()) {
				HostingCompanyEmployees hce = hostingCompanyEmployeesService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
				if (hce.getJobTitle() != null && hce.getJobTitle().getId() != null) {
					// populate job title
					jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
					if (jobTitle.getRegion() != null && jobTitle.getRegion().getId() != null) {
						// populate region
						region = regionService.findByKey(jobTitle.getRegion().getId());
					}
					
					if (jobTitle != null && jobTitle.getRoles() !=null && jobTitle.getRoles().getId() != null && jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) {
						qualityassuror = true;
					} 
					/*if (jobTitle.getId().equals(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID)) {
						coordinator = true;
					} */
				}
			}
		}
		
		if(qualityassuror) {
			providerInfo();
			checkIfCanCompleteTask();
		}
	}
	
	public void providerInfo() {
		dataModelScheduledEvent = new LazyDataModel<ScheduledEvent>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<ScheduledEvent> retorno = new  ArrayList<ScheduledEvent>();		   
		   @Override 
		   public List<ScheduledEvent> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("regionId", region.getId());
					filters.put("status", ApprovalEnum.PendingInverstigation);
					retorno = (List<ScheduledEvent>) service.allScheduledEvent(first, pageSize, sortField, sortOrder, filters);
					dataModelScheduledEvent.setRowCount(service.countScheduledEvent(ScheduledEvent.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				} 
			    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(ScheduledEvent obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public ScheduledEvent getRowData(String rowKey) {
		        for(ScheduledEvent obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}
	
	public void populateVerificationInformation() {
		try {
			verificationInfo();
			populateCompanyModerationData();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private void populateCompanyModerationData() throws Exception {
		auditorMonitorReviewDataModelCompany = auditorMonitorReviewService.findByTargetKeyAndClass(scheduledEvent.getClass().getName(), scheduledEvent.getId());
		if(auditorMonitorReviewDataModelCompany.size()==0) {
			auditorMonitorReviewService.prepNewReview(scheduledEvent.getClass().getName(), scheduledEvent.getId(), ConfigDocProcessEnum.ProviderVerificationModeration);
			runInit();
		}
	}
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("scheduledEventID", scheduledEvent.getId());
					retorno = service.allProviderVerficationScheduledEvent(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countProviderVerficationScheduledEvent(TrainingProviderVerfication.class, filters));
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
		  showlearners=true;
	}

	public void trainingproviderverficationInfo() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();
			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					//filters.put("status", ApprovalEnum.PendingApproval);
					//filters.put("createUser", getSessionUI().getActiveUser());
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

	public void updateUnitStandards() {
		try {
			checkFieldsprovided();
			if(provided) {
				summativeAssessmentReportService.updateUnitStandards(assessmentReport);
				this.trainingproviderverfication.setAssessmentDone(true);
				service.create(this.trainingproviderverfication);
			}else {
				addErrorMessage("Please provide required information");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveUnitStandards() {
		try {
			if(summativeAssessmentReportUnitStandards.getAssesmentDate() != null && summativeAssessmentReportUnitStandards.getModerationDate() != null){
				if(summativeAssessmentReportUnitStandards.getAssesmentDate().after(summativeAssessmentReportUnitStandards.getModerationDate())) {
					throw new Exception("Moderation date cannot be after assessment date");
				}
				if(summativeAssessmentReportUnitStandards.getCompetenceEnum() != null){
					if(summativeAssessmentReportUnitStandards.getAssessorApplication() == null) {
						throw new Exception("Please add an assessor");
					}
					if(summativeAssessmentReportUnitStandards.getModeratorApplication()== null) {
						throw new Exception("Please add a moderator");
					}					
					summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				}else {
					addErrorMessage("Please provide competence");
				}
				
			}else {
				addErrorMessage("Please provide assesment/moderation date");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}		
	}
	
	public void saveAuditorMonitorReview() {
		try {
			if(auditorMonitorReview.getEvidenceRequired() == null) {
				throw new Exception("Please select YES/NO");
			}
			if(auditorMonitorReview.getEvidenceRequired() != null && auditorMonitorReview.getEvidenceRequired() == YesNoEnum.No && auditorMonitorReview.getComment()==null) {
				throw new Exception("Please add a comment");
			}
			AuditorMonitorReviewService auditorMonitorReviewService= new AuditorMonitorReviewService();
			auditorMonitorReviewService.update(auditorMonitorReview);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}
	public void downloadReport() {
		try {
			summativeAssessmentReportService.downloadReport(assessmentReport, getSessionUI().getActiveUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	public void setVisitDate() {
		try {
			service.setFirstVisitDate(trainingproviderverfication, getSessionUI().getActiveUser());
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareReviewDate() {
		scheduledEvent = new ScheduledEvent();
	}
	
	public void requestModeration() {		
		try {
			List<TrainingProviderVerfication>list =service.findByCompanyAndUserAndStatus(selectedCompany, getSessionUI().getActiveUser(), ApprovalEnum.NA);
			preCheckSummativeAssessmentReport();
			service.createWorkflowToQualityAssuror(selectedCompany, getSessionUI().getActiveUser(), null, list, true, scheduledEvent);
			providerInfo();
			verificationInfo();
			show=false;
			showlearners=false;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void preCheckSummativeAssessmentReport() {
		try {
			service.checkCredits(trainingproviderverfication);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateInformation() {
		try {
			summativeassessmentreportInfo();
			populateLearnerModerationData();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
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
		show=true;
	}

	public void trainingproviderverficationInsert() {
		try {
			service.create(this.trainingproviderverfication);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void trainingproviderverficationUpdate() {
		try {
			service.update(this.trainingproviderverfication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingproviderverficationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
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
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		trainingproviderverfication = new TrainingProviderVerfication();
	}
	
	public void viewCompanyLearnerData() {
		
	}
	
	public List<TrainingProviderVerfication> complete(String desc) {
		List<TrainingProviderVerfication> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowForModeration() {
		try {
			service.completeWorkflowForModeration(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowToQualityAssuror() {
		try {
			service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
			/*if(this.trainingproviderverfication.getAssessmentDone() != null &&  this.trainingproviderverfication.getAssessmentDone() == true) {
				service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
				ajaxRedirectToDashboard();
			}else {
				addErrorMessage("Please complete assessment before proceeding");
			}*/
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowFinal() {
		try {
			service.completeWorkflowFinal(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			//(Company company, Users user, Tasks tasks, ScheduledEvent scheduledEvent) 
			//service.approveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createAdministratorTask() {
		try {
			if(scheduledEvent.getModerationEnum() == null) {
				throw new Exception("Please select the moderation outcome");
			}
			List<AuditorMonitorReview> auditorMonitorReviewsList = auditorMonitorReviewService.findByTargetKeyAndClass(scheduledEvent.getClass().getName(), scheduledEvent.getId());
			boolean checkfields = checkFieldsprovided(auditorMonitorReviewsList);
			if(!checkfields) {
				throw new Exception("Please make sure company moderation is completed");
			}else {
				
			}
			
			/*List<TrainingProviderVerfication>list = service.findByScheduledEvent(scheduledEvent);
			
			for(TrainingProviderVerfication trainingProviderVerfication:list) {
				if(trainingProviderVerfication.getForModeration() != null && trainingProviderVerfication.getForModeration()) {
					List<AuditorMonitorReview>auditorMonitorReviews = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), trainingProviderVerfication.getId());
					boolean check = checkFieldsprovided(auditorMonitorReviews);
					if(!check) {
						throw new Exception("Please make sure all learner moderation has been completed");
					}
				}
			}*/
			
			if(scheduledEvent.getModerationEnum() == ModerationEnum.Upheld) {
				service.createAdministratorTask(scheduledEvent, getSessionUI().getActiveUser());
			}else {
				service.createRejectApplication(scheduledEvent, getSessionUI().getActiveUser());				
			}
			ajaxRedirectToDashboard();
			
			/*if(checkfields) {
				if(scheduledEvent.getModerationEnum() == ModerationEnum.Upheld) {
					service.createAdministratorTask(scheduledEvent, getSessionUI().getActiveUser());
				}else {
					service.createRejectApplication(scheduledEvent, getSessionUI().getActiveUser());				
				}
				ajaxRedirectToDashboard();
			}else {
				throw new Exception("Please complete moderation report");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void saveInformation() {
		try {
			if(scheduledEvent.getModerationEnum() == null) {
				throw new Exception("Please select the moderation outcome");
			}
			scheduledEventService.update(scheduledEvent);
			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
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
				doc.setTargetKey(trainingproviderverfication.getId());
				doc.setTargetClass(TrainingProviderVerfication.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<AssessorModeratorApplication> completeAssessorModeratorApplication(String desc) {
		AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
		List<AssessorModeratorApplication> l = null;
		try {
			l = assessorModeratorApplicationService.findByCerticateNumber(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void prepareDataModelAssesor() {
		try {
			assessor = true;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			assessorModeratorApplicationInfo();			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareDataModelModerator() {
		try {
			assessor = false;
			newOrLegacyEnum = NewOrLegacyEnum.NewApplication;
			assessorModeratorApplicationInfo();			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void assessorModeratorApplicationInfo() {
		dataModelAssessorModeratorApplication = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					//retorno = assessorModeratorApplicationService.allAssessorModeratorApplication(AssessorModeratorApplication.class, first, pageSize, sortField, sortOrder, filters);
					//dataModelAssessorModeratorApplication.setRowCount(assessorModeratorApplicationService.count(AssessorModeratorApplication.class, filters));
					
					filters.put("unitStandardId", summativeAssessmentReportUnitStandards.getUnitStandards().getId());
					retorno = assessorModeratorApplicationService.sortAndFilterUserUnitStandard(first,pageSize,sortField,sortOrder,filters);
					dataModelAssessorModeratorApplication.setRowCount(assessorModeratorApplicationService.countUserUnitStandard(AssessorModeratorApplication.class,filters));
					
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void addAssessor() {
		try {
			if(assessor) {
				summativeAssessmentReportUnitStandards.setLegacyAssessorAccreditation(null);
				summativeAssessmentReportUnitStandards.setAssessorApplication(assessorModeratorApplication);
				summativeAssessmentReportUnitStandards.setAssessorUser(assessorModeratorApplication.getUser());
				summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				addInfoMessage("Assessor Added");
			}else {
				summativeAssessmentReportUnitStandards.setLegacyModeratorAccreditation(null);
				summativeAssessmentReportUnitStandards.setModeratorApplication(assessorModeratorApplication);
				summativeAssessmentReportUnitStandards.setModeratorUser(assessorModeratorApplication.getUser());
				summativeAssessmentReportUnitStandardsService.update(summativeAssessmentReportUnitStandards);
				addInfoMessage("Moderator Added");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepNewDoc() {
		try {
			doc = new Doc();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void storeAssessmentFile(FileUploadEvent event) {		
		try {			
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(this.summativeAssessmentReportUnitStandards.getId());
				doc.setTargetClass(SummativeAssessmentReportUnitStandards.class.getName());
				if (doc.getId() == null) {
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				}
			}
			summativeassessmentreportInfo();
			super.runClientSideExecute("PF('dlgUpload1').hide()");			
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
		boolean isProvided = true;
		if(auditorMonitorReviews!= null) {
			for(AuditorMonitorReview sr : auditorMonitorReviews) {
				if(sr.getEvidenceRequired() == null) {
					isProvided = false;
					break;
				}
				
				/*if(sr.getEvidenceRequired() == null || sr.getComment()== null) {
					isProvided = false;
					break;
				}*/
			}
		}		
		return isProvided;
	}
	
	private void checkIfCanCompleteTask() throws ParseException {
		if(scheduledEvent !=null && scheduledEvent .getId()!=null) {
			SimpleDateFormat simpleDateFormat = HAJConstants.sdfDDMMYYYY2;
			String fromDate = simpleDateFormat.format(scheduledEvent.getFromDateTime());
			String now = simpleDateFormat.format(getNow());
			
			Date dateFrom =  simpleDateFormat.parse(fromDate);
			Date dateNow =  simpleDateFormat.parse(now);
			
			if(dateNow.after(dateFrom) || dateFrom.equals(dateNow)) {
				moderate = true;			
			}else {
				moderate = false;
			}
			
		}else {
			moderate = false;
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

	public List<TrainingProviderVerfication> getTrainingproviderverficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingproviderverficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
	}

	public List<TrainingProviderVerfication> getTrainingproviderverficationfilteredList() {
		return trainingproviderverficationfilteredList;
	}

	public void setTrainingproviderverficationfilteredList(List<TrainingProviderVerfication> trainingproviderverficationfilteredList) {
		this.trainingproviderverficationfilteredList = trainingproviderverficationfilteredList;
	}

	public SummativeAssessmentReportUnitStandards getSummativeAssessmentReportUnitStandards() {
		return summativeAssessmentReportUnitStandards;
	}

	public void setSummativeAssessmentReportUnitStandards(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards) {
		this.summativeAssessmentReportUnitStandards = summativeAssessmentReportUnitStandards;
	}

	public boolean isProvided() {
		return provided;
	}

	public void setProvided(boolean provided) {
		this.provided = provided;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public LazyDataModel<Company> getTpDataModel() {
		return tpDataModel;
	}

	public void setTpDataModel(LazyDataModel<Company> tpDataModel) {
		this.tpDataModel = tpDataModel;
	}

	public boolean isShowlearners() {
		return showlearners;
	}

	public void setShowlearners(boolean showlearners) {
		this.showlearners = showlearners;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public NewOrLegacyEnum getNewOrLegacyEnum() {
		return newOrLegacyEnum;
	}

	public void setNewOrLegacyEnum(NewOrLegacyEnum newOrLegacyEnum) {
		this.newOrLegacyEnum = newOrLegacyEnum;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public LazyDataModel<AssessorModeratorApplication> getDataModelAssessorModeratorApplication() {
		return dataModelAssessorModeratorApplication;
	}

	public void setDataModelAssessorModeratorApplication(LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplication) {
		this.dataModelAssessorModeratorApplication = dataModelAssessorModeratorApplication;
	}

	public LazyDataModel<ScheduledEvent> getDataModelScheduledEvent() {
		return dataModelScheduledEvent;
	}

	public void setDataModelScheduledEvent(LazyDataModel<ScheduledEvent> dataModelScheduledEvent) {
		this.dataModelScheduledEvent = dataModelScheduledEvent;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelCompany() {
		return auditorMonitorReviewDataModelCompany;
	}

	public void setAuditorMonitorReviewDataModelCompany(List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany) {
		this.auditorMonitorReviewDataModelCompany = auditorMonitorReviewDataModelCompany;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelLearner() {
		return auditorMonitorReviewDataModelLearner;
	}

	public void setAuditorMonitorReviewDataModelLearner(List<AuditorMonitorReview> auditorMonitorReviewDataModelLearner) {
		this.auditorMonitorReviewDataModelLearner = auditorMonitorReviewDataModelLearner;
	}

	public AuditorMonitorReview getAuditorMonitorReview() {
		return auditorMonitorReview;
	}

	public void setAuditorMonitorReview(AuditorMonitorReview auditorMonitorReview) {
		this.auditorMonitorReview = auditorMonitorReview;
	}

	public boolean isSignoff() {
		return signoff;
	}

	public void setSignoff(boolean signoff) {
		this.signoff = signoff;
	}

	public boolean isModerate() {
		return moderate;
	}

	public void setModerate(boolean moderate) {
		this.moderate = moderate;
	}

}
