package haj.com.ui;

import java.util.ArrayList;
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
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.PercentageEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionService;

@ManagedBean(name = "learnerVerificationHoldingUI")
@ViewScoped
public class LearnerVerificationHoldingUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private LazyDataModel<Company> tpDataModel; 
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards;
	private SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
	private Doc doc;
	boolean provided = true;
	boolean show = false;
	boolean showlearners = false;
	private Company selectedCompany;
	private ScheduledEvent scheduledEvent;
	private boolean qualityassuror = false;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private JobTitle jobTitle;
	private Region region;
	private RegionService regionService = new RegionService();
	private PercentageEnum percentageEnum;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelLearner;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany;
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
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
		}
	}
	
	public void providerInfo() {
		tpDataModel = new LazyDataModel<Company>() { 		 
			   private static final long serialVersionUID = 1L; 
			   private List<Company> retorno = new  ArrayList<Company>();		   
			   @Override 
			   public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
					try {
						filters.put("regionId", region.getId());
						filters.put("status", ApprovalEnum.PreApproved);
						retorno = (List<Company>) service.allCompanyVerification(first, pageSize, sortField, sortOrder, filters);
						tpDataModel.setRowCount(service.countCompanyVerification(Company.class, filters));
					} catch (Exception e) {
						logger.fatal(e);
					} 
				    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Company obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Company getRowData(String rowKey) {
			        for(Company obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
	}
	
	private void populateCompanyModerationData() {
		try {			
			auditorMonitorReviewDataModelCompany = auditorMonitorReviewService.findByTargetKeyAndClass(scheduledEvent.getClass().getName(), scheduledEvent.getId());	
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("trainingProviderID", selectedCompany.getId());
					filters.put("status", ApprovalEnum.PreApproved);
					retorno = service.allProviderVerfication(first, pageSize, sortField, sortOrder, filters);
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
	
	public void downloadReport() {
		try {
			summativeAssessmentReportService.downloadReport(assessmentReport, getSessionUI().getActiveUser());
			/*
			 * checkFieldsprovided(); if(provided) {
			 * summativeAssessmentReportService.downloadReport(assessmentReport,
			 * getSessionUI().getActiveUser()); }else {
			 * addErrorMessage("Please provide required information"); }
			 */
			
		} catch (Exception e) {
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
		//scheduledEvent.setC
	}
	
	public void requestModeration() {		
		try {
			if(percentageEnum != null) {
				List<TrainingProviderVerfication>list =service.findByCompanyAndStatus(selectedCompany, ApprovalEnum.PreApproved);
				preCheckSummativeAssessmentReport();
				service.completeWorkflowToQualityAssuror(selectedCompany, getSessionUI().getActiveUser(), null, list, percentageEnum);
				providerInfo();
			}else {
				addErrorMessage("Please select percentage");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void preCheckSummativeAssessmentReport() {
		
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

	public PercentageEnum getPercentageEnum() {
		return percentageEnum;
	}

	public void setPercentageEnum(PercentageEnum percentageEnum) {
		this.percentageEnum = percentageEnum;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelCompany() {
		return auditorMonitorReviewDataModelCompany;
	}

	public void setAuditorMonitorReviewDataModelCompany(List<AuditorMonitorReview> auditorMonitorReviewDataModelCompany) {
		this.auditorMonitorReviewDataModelCompany = auditorMonitorReviewDataModelCompany;
	}
}
