package haj.com.ui;

import java.util.ArrayList;
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

import haj.com.entity.Doc;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumetFileTypeEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "generatedCertificateUI")
@ViewScoped
public class GeneratedCertificateUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private LazyDataModel<ScheduledEvent> dataModelScheduledEvent;
	private LazyDataModel<TrainingProviderVerfication> dataModelList;
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	List<SummativeAssessmentReportUnitStandards>summativeAssessmentReportUnitStandardsList = null;
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private Doc doc;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();
	
	boolean provided = true;
	boolean selectedall = false;
	
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	private ScheduledEvent scheduledEvent;
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

	private void runInit() throws Exception {

			prepareNew();
			trainingProviderVerficationScheduledEventInfo();
			//trainingproviderverficationGroup();
			//trainingproviderverficationInfo();
		
	}
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("createUserID", getSessionUI().getActiveUser().getId());
					filters.put("status", ApprovalEnum.Approved);
					filters.put("verificationStatus", ApprovalEnum.NA);
					retorno = service.allTrainingProviderVerficationStatus(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countTrainingProviderVerficationStatus(TrainingProviderVerfication.class, filters));
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
	
	private void trainingProviderVerficationScheduledEventInfo() {
		dataModelScheduledEvent = new LazyDataModel<ScheduledEvent>() {

			private static final long serialVersionUID = 1L;
			private List<ScheduledEvent> retorno = new ArrayList<ScheduledEvent>();

			@Override
			public List<ScheduledEvent> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(getSessionUI().isTrainingProvider()) {
						//filters.put("status", ApprovalEnum.Approved);
						filters.put("cetificateGenerated", true);
						retorno = service.sortAndFilterScheduledEvent(first, pageSize, sortField, sortOrder, filters);
						dataModelScheduledEvent.setRowCount(service.countScheduledEvent(filters));
					}else if(getSessionUI().isEmployee()) {
						//filters.put("status", ApprovalEnum.Approved);
						filters.put("cetificateGenerated", true);
						retorno = service.sortAndFilterScheduledEvent(first, pageSize, sortField, sortOrder, filters);
						dataModelScheduledEvent.setRowCount(service.countScheduledEvent( filters));
					}										
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ScheduledEvent obj) {
				return obj.getId();
			}

			@Override
			public ScheduledEvent getRowData(String rowKey) {
				for (ScheduledEvent obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
		
	}

	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(this.trainingproviderverfication.getId(), TrainingProviderVerfication.class.getName(), ConfigDocProcessEnum.ProviderVerification);			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}		
	}
	
	public List<Users> findAssersors() {
		CompanyUsersService lookupsService = new CompanyUsersService();
		List<Users> l = null;
		try {
			//l = lookupsService.findCompanyAssessorUsers(trainingproviderverfication.getTrainingProvider().getId());
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<Users> findModerators() {
		CompanyUsersService lookupsService = new CompanyUsersService();
		List<Users> l = null;
		try {
			//l = lookupsService.findCompanyModeratorsUsers(trainingproviderverfication.getTrainingProvider().getId());
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public void trainingproviderverficationListInfo() {
		try {
			trainingproviderverficationList=service.findByScheduledEvent(scheduledEvent);
		} catch (Exception e) {			
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateUnitStandards() {
		try {
			
			summativeAssessmentReportService.updateUnitStandards(assessmentReport);
			this.trainingproviderverfication.setAssessmentDone(false);
			service.create(this.trainingproviderverfication);
			/*checkFieldsprovided();
			if(provided) {
				summativeAssessmentReportService.updateUnitStandards(assessmentReport);
				this.trainingproviderverfication.setAssessmentDone(true);
				service.create(this.trainingproviderverfication);
			}else {
				addErrorMessage("Please provide required information");
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateAndZipDocuments() {
		try {	
			if(trainingproviderverficationList.size()==0) {
				throw new Exception("Please add atleast one certificate to be dowloaded");
			}
			service.generateAndZipDocuments(trainingproviderverficationList, getSessionUI().getActiveUser());
			for(TrainingProviderVerfication tp:trainingproviderverficationList) {
				if(!tp.getCreatedFinalApproval()) {
					service.createTrainingProviderVerficationTask(tp, getSessionUI().getActiveUser());
				}
			}
			prepareNew();	
			addInfoMessage("Document Selection Downloaded");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadSelectedDocuments() {
		try {			
			List<Doc> list = new ArrayList<>();	
			for(TrainingProviderVerfication tpv :trainingproviderverficationList) {
				list.add(DocService.instance().findDocByClassKey(TrainingProviderVerfication.class.getName(), tpv.getId()));
			}
			if(list != null && list.size()>0) {
				Faces.sendFile(DocService.instance().downloadZippedFiles(list), GenericUtility.fileTimestapName("Certficate", DocumetFileTypeEnum.ZIP.getFriendlyName()), true);
				addInfoMessage("Documents downloaded");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateUnitStandardsList() {
		try {			
			//summativeAssessmentReportService.updateUnitStandardsList(summativeAssessmentReportUnitStandardsList);
			service.create(this.trainingproviderverfication);		
		} catch (Exception e) {
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
	
	public void prepVisitDate() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
			//dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.CertificatesCollection);
			runClientSideExecute("PF('dlgReviewDate').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateVisitDate() {
		try {
			if (dateChangeReasonSelectionList.size() != 0) {
				//service.addNewlVisitDate(trainingproviderverfication, getSessionUI().getActiveUser(), null, false, dateChangeReasonSelectionList, trainingproviderverfication.getCollectionDate(), ConfigDocProcessEnum.CertificatesCollection);
				dateChangeReasonSelectionList = null;
				dateChangeReasonAvalibleSelectionList = null;
				//prepareEvaluationDate();
				addInfoMessage("Date Updated");
				runClientSideExecute("PF('dlgReviewDate').hide()");
				
			} else {
				addWarningMessage("Provide A Minium Of One Reason For Date Change Before Proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void summativeassessmentreportInfo() {
		summativeassessmentreportDataModel();	
		try {
			calculateTotal() ;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void summativeassessmentreportDataModel() {

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


	public void prepareNew() {
		trainingproviderverfication = new TrainingProviderVerfication();
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
	
	public void createWorkflow() {
		try {
			service.createWorkflow(trainingproviderverfication, getSessionUI().getActiveUser());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
			if(this.trainingproviderverfication.getAssessmentDone() != null &&  this.trainingproviderverfication.getAssessmentDone() == true) {
				service.completeWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
				ajaxRedirectToDashboard();
			}else {
				addErrorMessage("Please complete assessment before proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowToCathseta() {
		try {
			
			//service.completeWorkflowToCathsseta(trainingproviderverfication, getSessionUI().getActiveUser());
			//trainingproviderverficationGroup();
			trainingproviderverfication = null;
			ajaxRedirectToDashboard();
			
			/*if(this.trainingproviderverfication.getAssessmentDone() != null &&  this.trainingproviderverfication.getAssessmentDone() == true) {
				service.completeWorkflowToCathsseta(trainingproviderverfication, getSessionUI().getActiveUser());
				trainingproviderverficationInfo();
				trainingproviderverfication = null;
				ajaxRedirectToDashboard();				
			}else {
				addErrorMessage("Please complete assessment before proceeding");
			}*/
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clear() {
		try {
			this.trainingproviderverfication = new TrainingProviderVerfication();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalapproveWorkflow() {
		try {			
			//service.finalapproveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser());
			//trainingproviderverficationGroup();
			trainingproviderverfication = null;
			ajaxRedirectToDashboard();			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveTrainingProviderVerfication() {
		try {	
			//service.finalApproveTrainingProviderVerfication(trainingproviderverfication, getSessionUI().getActiveUser(),trainingproviderverficationList, getSessionUI().getTask());			
			//trainingproviderverficationGroup();
			trainingproviderverfication = null;
			ajaxRedirectToDashboard();		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveAllTrainingProviderVerfication() {
		try {	
			//service.finalApproveAllTrainingProviderVerfication(trainingproviderverficationList, getSessionUI().getActiveUser());		
			//trainingproviderverficationGroup();
			trainingproviderverfication = null;
			ajaxRedirectToDashboard();		
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflowFinal() {
		try {
			if(trainingproviderverfication.getCollectionDate() == null) {
				throw new Exception("Please Select Collection Date");
			}
			service.completeWorkflowFinal(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			//service.approveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			//service.rejectTrainingProviderVerficationWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			//service.finalApproveWorkflowDate(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask(), trainingproviderverficationList);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			//l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.CertificatesCollection);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
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
	
	public int calculateTotal() throws Exception {
		int total = 0;
		SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
		SummativeAssessmentReport summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		List<SummativeAssessmentReportUnitStandards>list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		
		for(SummativeAssessmentReportUnitStandards sr : list) {
			total = total+Integer.parseInt(sr.getUnitStandards().getUnitstdnumberofcredits());
		}
				
		return total;
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

	public boolean isSelectedall() {
		return selectedall;
	}

	public void setSelectedall(boolean selectedall) {
		this.selectedall = selectedall;
	}

	public List<SummativeAssessmentReportUnitStandards> getSummativeAssessmentReportUnitStandardsList() {
		return summativeAssessmentReportUnitStandardsList;
	}

	public void setSummativeAssessmentReportUnitStandardsList(List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandardsList) {
		this.summativeAssessmentReportUnitStandardsList = summativeAssessmentReportUnitStandardsList;
	}

	public List<TrainingProviderVerfication> getTrainingproviderverficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingproviderverficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
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

	public LazyDataModel<TrainingProviderVerfication> getDataModelList() {
		return dataModelList;
	}

	public void setDataModelList(LazyDataModel<TrainingProviderVerfication> dataModelList) {
		this.dataModelList = dataModelList;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public LazyDataModel<ScheduledEvent> getDataModelScheduledEvent() {
		return dataModelScheduledEvent;
	}

	public void setDataModelScheduledEvent(LazyDataModel<ScheduledEvent> dataModelScheduledEvent) {
		this.dataModelScheduledEvent = dataModelScheduledEvent;
	}
}
