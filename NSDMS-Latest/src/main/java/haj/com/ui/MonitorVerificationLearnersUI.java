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
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.NewOrLegacyEnum;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.SummativeAssessmentReportUnitStandardsService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "monitorVerificationLearnersUI")
@ViewScoped
public class MonitorVerificationLearnersUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private List<TrainingProviderVerfication> trainingproviderverficationList = null;
	private List<TrainingProviderVerfication> trainingproviderverficationfilteredList = null;
	private TrainingProviderVerfication trainingproviderverfication = null;
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private List<SummativeAssessmentReportUnitStandards> listSummativeAssessmentReportUnitStandards;
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
	boolean assessor ;
	private NewOrLegacyEnum newOrLegacyEnum;
	
	private String idnumber;
	private AssessorModeratorApplication assessorModeratorApplication;
	private LegacyAssessorAccreditation legacyAssessorAccreditation;
	private LegacyModeratorAccreditation legacyModeratorAccreditation;
	
	private AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
	private LegacyAssessorAccreditationService legacyAssessorAccreditationService = new LegacyAssessorAccreditationService();
	private LegacyModeratorAccreditationService legacyModeratorAccreditationService = new LegacyModeratorAccreditationService();

	private LazyDataModel<AssessorModeratorApplication> dataModelAssessorModeratorApplication;
	private LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditation; 
	private LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditation;

	private int count = 0;
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
		count=service.countAllResults();
		trainingproviderverficationInfo() ;
	}

	public void trainingproviderverficationInfo() {
		dataModel = new LazyDataModel<TrainingProviderVerfication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderVerfication> retorno = new ArrayList<TrainingProviderVerfication>();
			@Override
			public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allTrainingProviderVerfication1(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countTrainingProviderVerfication1(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
	
	@SuppressWarnings("unchecked")
	public void resolveErrors() {
		try {
			trainingproviderverficationList = service.findAllTrainingProviderVerficationWithErrors();
			//List<IDataEntity> dataEntities = new ArrayList<>();
			for(TrainingProviderVerfication trainingProviderMonitoring: trainingproviderverficationList) {
				if(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
				}else if(SKILLS_PROGRAM_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
					trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
				}else if(SKILLS_SET_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
					trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
				}else {
					trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate);
				}
				//dataEntities.add(trainingProviderMonitoring);
				service.update(trainingProviderMonitoring);
			}
			//service.update((List<IDataEntity>) (List<?>) trainingproviderverficationList);
			runInit();
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
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void prepareNew() {
		trainingproviderverfication = new TrainingProviderVerfication();
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
		
	public void completeWorkflowToQualityAssuror() {
		try {
			if(service.checkCredits(trainingproviderverfication)) {				
				service.closeTrainingProviderVerficationTasks(this.trainingproviderverfication);
				service.createWorkflowToQualityAssuror(trainingproviderverfication, getSessionUI().getActiveUser());
				addInfoMessage(super.getEntryLanguage("update.successful"));
				trainingproviderverficationInfo();
			}else {
				throw new Exception("Minimum credits not met");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createTasksToHoldingRoom() {
		try {
			try {
				this.trainingproviderverfication.setStatus(ApprovalEnum.NA);
				service.update(this.trainingproviderverfication);
				service.closeTrainingProviderVerficationTasks(this.trainingproviderverfication);
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

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(trainingproviderverfication, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
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
			e.printStackTrace();
		}
	}

		
	private void precheckLegacyAssMod() throws Exception {
		if(summativeAssessmentReportUnitStandards.getLegacyAssessorAccreditation()!= null && summativeAssessmentReportUnitStandards.getLegacyModeratorAccreditation() != null) {
			if(summativeAssessmentReportUnitStandards.getLegacyAssessorAccreditation().getId() == summativeAssessmentReportUnitStandards.getLegacyModeratorAccreditation() .getId()) {
				throw new Exception("Assessor and Moderator must not be the same");
			}
		}
	}
	
	public List<UnitStandards> completeSummativeAssessmentReportUnitStandards(String desc){
		List<UnitStandards>  listSummativeAssessmentReportUnitStandards = null;
		try {
			if(assessmentReport != null && assessmentReport.getCompanyLearners() != null) {
				CompanyLearners cl = CompanyLearnersService.instance().findByKey(assessmentReport.getCompanyLearners().getId());
				listSummativeAssessmentReportUnitStandards =  summativeAssessmentReportService.findUnitStandardsByCompanylearners(cl);			
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}	
		return listSummativeAssessmentReportUnitStandards;
	}
	
	public void prepSummativeAssessmentReportUnitStandards() {
		try {
			if(assessmentReport != null) {
				summativeAssessmentReportUnitStandards = new SummativeAssessmentReportUnitStandards();
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(assessmentReport);
				if(assessmentReport.getCompanyLearners() != null) {
					CompanyLearners cl = CompanyLearnersService.instance().findByKey(assessmentReport.getCompanyLearners().getId());
					summativeAssessmentReportUnitStandards.setCompanyLearners(cl);
					listSummativeAssessmentReportUnitStandards = summativeAssessmentReportService.findByCompanylearners(cl);
				}

			}else {
				throw new Exception("Error");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	public void summativeassessmentreportUpdate() {
		try {
			if(assessmentReport != null && assessmentReport.getCompanyLearners() != null) {
				CompanyLearners cl = CompanyLearnersService.instance().findByKey(assessmentReport.getCompanyLearners().getId());
				listSummativeAssessmentReportUnitStandards = summativeAssessmentReportService.findByCompanylearners(cl,assessmentReport);
				if(listSummativeAssessmentReportUnitStandards.size() > 0) {
					for(SummativeAssessmentReportUnitStandards sumAssReportUS:listSummativeAssessmentReportUnitStandards) {
						summativeAssessmentReportUnitStandardsService.create(sumAssReportUS);
					}
					summativeassessmentreportInfo();
					addInfoMessage(super.getEntryLanguage("update.successful"));
				}else {
					throw new Exception("Unit standards have already been added");
				}			
			}else {
				throw new Exception("Error");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	public void linkUnitStandard() {
		try {			
			preCheckUnitStandards();
			if(summativeAssessmentReportUnitStandards.getUnitStandards() != null) {
				summativeAssessmentReportUnitStandards.setSummativeAssessmentReport(assessmentReport);
				summativeAssessmentReportUnitStandardsService.create(summativeAssessmentReportUnitStandards);	
				addInfoMessage(super.getEntryLanguage("update.successful"));
			}else {
				throw new Exception("Error");
			}			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}
	
	private void preCheckUnitStandards() throws Exception {
		if(summativeAssessmentReportUnitStandardsService.countUnitStandards(summativeAssessmentReportUnitStandards.getUnitStandards(), assessmentReport) > 0) {
			throw new Exception("Unit Standard already added");
		}
	}
	
	public void summativeAssessmentReportUnitStandardsDelete() {
		try {
			summativeAssessmentReportUnitStandardsService.delete(summativeAssessmentReportUnitStandards);
			summativeassessmentreportInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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

	public LazyDataModel<LegacyModeratorAccreditation> getDataModelLegacyModeratorAccreditation() {
		return dataModelLegacyModeratorAccreditation;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getDataModelLegacyAssessorAccreditation() {
		return dataModelLegacyAssessorAccreditation;
	}

	public void setDataModelLegacyModeratorAccreditation(LazyDataModel<LegacyModeratorAccreditation> dataModelLegacyModeratorAccreditation) {
		this.dataModelLegacyModeratorAccreditation = dataModelLegacyModeratorAccreditation;
	}

	public void setDataModelLegacyAssessorAccreditation(LazyDataModel<LegacyAssessorAccreditation> dataModelLegacyAssessorAccreditation) {
		this.dataModelLegacyAssessorAccreditation = dataModelLegacyAssessorAccreditation;
	}

	public LegacyAssessorAccreditation getLegacyAssessorAccreditation() {
		return legacyAssessorAccreditation;
	}

	public LegacyModeratorAccreditation getLegacyModeratorAccreditation() {
		return legacyModeratorAccreditation;
	}

	public void setLegacyAssessorAccreditation(LegacyAssessorAccreditation legacyAssessorAccreditation) {
		this.legacyAssessorAccreditation = legacyAssessorAccreditation;
	}

	public void setLegacyModeratorAccreditation(LegacyModeratorAccreditation legacyModeratorAccreditation) {
		this.legacyModeratorAccreditation = legacyModeratorAccreditation;
	}

	public boolean isAssessor() {
		return assessor;
	}

	public void setAssessor(boolean assessor) {
		this.assessor = assessor;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
