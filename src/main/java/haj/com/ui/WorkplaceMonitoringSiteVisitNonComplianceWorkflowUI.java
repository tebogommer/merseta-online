package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.RejectReasonsService;

/**
 * The Class WorkplaceMonitoringSiteVisitWorkflowUI.
 */
@ManagedBean(name = "workplaceMonitoringSiteVisitNonComplianceWorkflowUI")
@ViewScoped
public class WorkplaceMonitoringSiteVisitNonComplianceWorkflowUI extends AbstractUI {

	// entity levels
	private Doc 								doc 									= null;
	private Company 							company 								= null;
	private WorkplaceMonitoringSiteVisit 		workplaceMonitoringSiteVisit 			= null;
	private ConfigDocProcessEnum				workflowProcess							= null;
	private WorkplaceMonitoringMitigationPlan	workplaceMonitoringMitigationPlan		= null;
	
	/* Service levels */
	private CompanyService 								companyService 								= new CompanyService();
	private RejectReasonsService						rejectReasonsService						= new RejectReasonsService();
	private WorkplaceMonitoringSiteVisitService 		workplaceMonitoringSiteVisitService 		= new WorkplaceMonitoringSiteVisitService();
	private WorkplaceMonitoringMitigationPlanService	workplaceMonitoringMitigationPlanService	= new WorkplaceMonitoringMitigationPlanService();
	
	/* Lazy data models */
	private LazyDataModel<WorkplaceMonitoringMitigationPlan> workplaceMonitoringMitigationPlanDataModel;
	
	// Array Lists
	private List<RejectReasons> rejectReasonsList 			= null;
	private List<RejectReasons> selectedRejectReasonsList 	= null;
	
	/* Managed Properties */
	@ManagedProperty(value = "#{workplaceMonitoringInformationViewUI}")
	private WorkplaceMonitoringInformationViewUI workplaceMonitoringInformationViewUI;
	
	
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

	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.WorkplaceMonitoringNonComplianceApproval) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			
			workflowProcess = getSessionUI().getTask().getWorkflowProcess();
			workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisitService.findByKey(getSessionUI().getTask().getTargetKey());
			company = companyService.findByKey(workplaceMonitoringSiteVisit.getCompany().getId());
			companyService.resolveCompanyAddresses(company);
			getWorkplaceMonitoringInformationViewUI().init();
			getWorkplaceMonitoringInformationViewUI().setWorkplaceMonitoringSiteVisit(workplaceMonitoringSiteVisit);
			getWorkplaceMonitoringInformationViewUI().selectMonitoringToViewNoMessage();
			runClientSideExecute("mainForm");
			
			// Determines actions in Abstract UI
			determainUserActions();
			
			// populate rejection reasons
			populateRejectReasonsAssigned();
			
			// Mitigation plan
			workplaceMonitoringMitigationPlanDataModelInfo();
		} else {
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		}
	}
	
	private void populateRejectReasonsAssigned() throws Exception {
		if (this.workplaceMonitoringSiteVisit != null && this.workplaceMonitoringSiteVisit.getId() != null) {
			rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(this.workplaceMonitoringSiteVisit.getId(), this.workplaceMonitoringSiteVisit.getClass().getName());
		} else {
			rejectReasonsList = null;
		}
	}
	
	public void workplaceMonitoringMitigationPlanDataModelInfo() {
		workplaceMonitoringMitigationPlanDataModel = new LazyDataModel<WorkplaceMonitoringMitigationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringMitigationPlan> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringMitigationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringMitigationPlanService.allWorkplaceMonitoringMitigationPlanBySiteVisit(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringMitigationPlanDataModel.setRowCount(workplaceMonitoringMitigationPlanService.countAllWorkplaceMonitoringMitigationPlanBySiteVisit(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringMitigationPlan obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringMitigationPlan getRowData(String rowKey) {
				for (WorkplaceMonitoringMitigationPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepUpdateMitigationPlan(){
		try {
			runClientSideExecute("PF('mitigationPlanDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateMitigationPlan(){
		try {
			workplaceMonitoringMitigationPlanService.updatePlan(workplaceMonitoringMitigationPlan, getSessionUI().getActiveUser());
			runClientSideExecute("PF('mitigationPlanDlg').hide()");
			workplaceMonitoringMitigationPlanDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejection(){
		try {
			selectedRejectReasonsList = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectBackToHoldingArea() {
		try {
			if (selectedRejectReasonsList == null || selectedRejectReasonsList.isEmpty()) {
				throw new Exception("Provide atleast one reason before proceeding");
			}
			workplaceMonitoringSiteVisitService.rejectToHoldingAreaNonCompliance(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), rejectReasonsList, getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectNonCompliance() {
		try {
			if (selectedRejectReasonsList == null || selectedRejectReasonsList.isEmpty()) {
				throw new Exception("Provide atleast one reason before proceeding");
			}
			workplaceMonitoringSiteVisitService.finalRejectWorkplaceMonitoring(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), rejectReasonsList, getSessionUI().getTask(), Boolean.TRUE);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveNonComplaince(){
		try {
			workplaceMonitoringSiteVisitService.validiationBeforeFinalApproval(workplaceMonitoringSiteVisit);
			workplaceMonitoringSiteVisitService.finalApproveNonComplianceApproval(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
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
				doc.setTargetKey(workplaceMonitoringSiteVisit.getId());
				doc.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasonsForSelection() {
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(workflowProcess);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/* Getters and setters */
	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisit() {
		return workplaceMonitoringSiteVisit;
	}

	public void setWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit) {
		this.workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisit;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public List<RejectReasons> getSelectedRejectReasonsList() {
		return selectedRejectReasonsList;
	}

	public void setSelectedRejectReasonsList(List<RejectReasons> selectedRejectReasonsList) {
		this.selectedRejectReasonsList = selectedRejectReasonsList;
	}

	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}
	
	public LazyDataModel<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanDataModel() {
		return workplaceMonitoringMitigationPlanDataModel;
	}

	public void setWorkplaceMonitoringMitigationPlanDataModel(
			LazyDataModel<WorkplaceMonitoringMitigationPlan> workplaceMonitoringMitigationPlanDataModel) {
		this.workplaceMonitoringMitigationPlanDataModel = workplaceMonitoringMitigationPlanDataModel;
	}

	public WorkplaceMonitoringMitigationPlan getWorkplaceMonitoringMitigationPlan() {
		return workplaceMonitoringMitigationPlan;
	}

	public void setWorkplaceMonitoringMitigationPlan(WorkplaceMonitoringMitigationPlan workplaceMonitoringMitigationPlan) {
		this.workplaceMonitoringMitigationPlan = workplaceMonitoringMitigationPlan;
	}

	public WorkplaceMonitoringInformationViewUI getWorkplaceMonitoringInformationViewUI() {
		return workplaceMonitoringInformationViewUI;
	}

	public void setWorkplaceMonitoringInformationViewUI(
			WorkplaceMonitoringInformationViewUI workplaceMonitoringInformationViewUI) {
		this.workplaceMonitoringInformationViewUI = workplaceMonitoringInformationViewUI;
	}
}
