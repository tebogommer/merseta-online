package  haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
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
import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Signoff;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractTerminationRequestService;
import haj.com.service.ActiveContractsService;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "activeContractTerminationRequestWorkflowUI")
@ViewScoped
public class ActiveContractTerminationRequestWorkflowUI extends AbstractUI {

	/* Entity */
	private ActiveContractTerminationRequest activeContractTerminationRequest = null;
	private ActiveContracts activeContracts = null;
	private Doc doc;
	
	/* Service */
	private ActiveContractTerminationRequestService activeContractTerminationRequestService = new ActiveContractTerminationRequestService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private CompanyService companyService = new CompanyService();
	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private SignoffService signoffService = new SignoffService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	
	/* Data Model Lists */
	private LazyDataModel<ProjectImplementationPlan> projectImplementationPlanDataModel;
	
	/* Array Lists */
	private List<RejectReasons> taskRejectionReasonsList = new ArrayList<RejectReasons>();
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<>();
	private List<Signoff> signOffUserList = null;
	
	/* Enums */
	private ConfigDocProcessEnum configDocProcessEnum = null;
	
	/* Vars */
	private String projectedRegistrationDisplayDate;

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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT_TERMINATION_REQUEST) {
			
			// safe guard to ensure task is not already completed by user
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			
			configDocProcessEnum = getSessionUI().getTask().getWorkflowProcess();
			activeContractTerminationRequest = activeContractTerminationRequestService.findByKey(getSessionUI().getTask().getTargetKey());
			activeContractTerminationRequestService.populateDocsByConfigDocProcessAndCompanyUserTypeEnum(activeContractTerminationRequest, configDocProcessEnum, CompanyUserTypeEnum.Company);

			// populate active contract information
			if (activeContractTerminationRequest.getActiveContracts() != null && activeContractTerminationRequest.getActiveContracts().getId() != null) {
				activeContracts = activeContractsService.findByKey(activeContractTerminationRequest.getActiveContracts().getId());
				populateActiveContractSignOffUsers();
				projectImplementationPlanDataModelInfo();
				
				// populate display for year information
				int year = Calendar.getInstance().get(Calendar.YEAR);
				String yearPlus = String.valueOf(year + 1);
				if (activeContracts != null && activeContracts.getProjectedRegistrationDateStart() != null && activeContracts.getProjectedRegistrationDateEnd()  != null) {
					projectedRegistrationDisplayDate = "Between " + HAJConstants.sdfDDMMMMYYYY.format(activeContracts.getProjectedRegistrationDateStart()) + " and " + HAJConstants.sdfDDMMMMYYYY.format(activeContracts.getProjectedRegistrationDateEnd());
				} else {
					projectedRegistrationDisplayDate = "Between 1 January " + year + " and 31 March " + yearPlus;
				}
				
				// Populate Company Address
				if (activeContracts != null && activeContracts.getDgAllocationParent() != null && activeContracts.getDgAllocationParent().getWsp() != null && activeContracts.getDgAllocationParent().getWsp().getCompany() != null) {
					companyService.resolveCompanyAddresses(activeContracts.getDgAllocationParent().getWsp().getCompany());
				}
				
			}
			
			// task actions
			determainUserActions();
			
			// populate reject reasons if any found
			populateRejectionReasons(getSessionUI().getTask().getWorkflowProcess());
		} else {
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		}
	}
	
	private void populateRejectionReasons(ConfigDocProcessEnum configDocEnum) throws Exception{
		taskRejectionReasonsList = new ArrayList<RejectReasons>();
		taskRejectionReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), configDocEnum);
	}
	
	private void populateActiveContractSignOffUsers() throws Exception{
		if (activeContracts != null && activeContracts.getId() != null) {
			signOffUserList = signoffService.findByTargetKeyAndClass(activeContracts.getId(), activeContracts.getClass().getName());
		} else {
			signOffUserList = null;
		}
	}
	
	public void projectImplementationPlanDataModelInfo() {
		projectImplementationPlanDataModel = new LazyDataModel<ProjectImplementationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<ProjectImplementationPlan> retorno = new ArrayList<ProjectImplementationPlan>();
			@Override
			public List<ProjectImplementationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = projectImplementationPlanService.allProjectImplementationPlanWhereTotalaountIsGreaterThanZero(first, pageSize, sortField, sortOrder, filters, activeContracts.getDgAllocationParent().getWsp().getId());
					projectImplementationPlanDataModel.setRowCount(projectImplementationPlanService.countAllProjectImplementationPlanWhereTotalaountIsGreaterThanZero(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ProjectImplementationPlan obj) {
				return obj.getId();
			}
			@Override
			public ProjectImplementationPlan getRowData(String rowKey) {
				for (ProjectImplementationPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public List<RejectReasons> getRejectionReasonsForSelection() {
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(configDocProcessEnum);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setTargetClass(activeContractTerminationRequest.getClass().getName());
			doc.setTargetKey(activeContractTerminationRequest.getId());
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void runValidiationChecks() throws Exception {
		if (activeContractTerminationRequest != null && activeContractTerminationRequest.getId() != null) {
			activeContractTerminationRequestService.validationsOfEditAndUpload(activeContractTerminationRequest, isCanEditAbstract(), isCanUploadAbstract());
		}
	}
	
	public void prepRejection(){
		try {
			selectedRejectionReasons = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeTask(){
		try {
			runValidiationChecks();
			activeContractTerminationRequestService.completeTask(activeContractTerminationRequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// download active contract document
	public void downloadMoaVersionTwo(){
		try {
			activeContractsService.downloadMoaVersionTwo(activeContracts, true);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectTask(){
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Provide Atleast One Rejection Reason Before Proceeding");
			} else {
				activeContractTerminationRequestService.rejectTask(activeContractTerminationRequest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveTask(){
		try {
			runValidiationChecks();
			activeContractTerminationRequestService.approveTask(activeContractTerminationRequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveTask(){
		try {
			runValidiationChecks();
			activeContractTerminationRequestService.finalApproveTask(activeContractTerminationRequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectTask(){
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Provide Atleast One Rejection Reason Before Proceeding");
			} else {
				activeContractTerminationRequestService.finalRejectTask(activeContractTerminationRequest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Getters and Setters */
	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public List<RejectReasons> getTaskRejectionReasonsList() {
		return taskRejectionReasonsList;
	}

	public void setTaskRejectionReasonsList(List<RejectReasons> taskRejectionReasonsList) {
		this.taskRejectionReasonsList = taskRejectionReasonsList;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public ActiveContractTerminationRequest getActiveContractTerminationRequest() {
		return activeContractTerminationRequest;
	}

	public void setActiveContractTerminationRequest(ActiveContractTerminationRequest activeContractTerminationRequest) {
		this.activeContractTerminationRequest = activeContractTerminationRequest;
	}

	public SignoffService getSignoffService() {
		return signoffService;
	}

	public void setSignoffService(SignoffService signoffService) {
		this.signoffService = signoffService;
	}

	public String getProjectedRegistrationDisplayDate() {
		return projectedRegistrationDisplayDate;
	}

	public void setProjectedRegistrationDisplayDate(String projectedRegistrationDisplayDate) {
		this.projectedRegistrationDisplayDate = projectedRegistrationDisplayDate;
	}

	public List<Signoff> getSignOffUserList() {
		return signOffUserList;
	}

	public void setSignOffUserList(List<Signoff> signOffUserList) {
		this.signOffUserList = signOffUserList;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	public LazyDataModel<ProjectImplementationPlan> getProjectImplementationPlanDataModel() {
		return projectImplementationPlanDataModel;
	}

	public void setProjectImplementationPlanDataModel(
			LazyDataModel<ProjectImplementationPlan> projectImplementationPlanDataModel) {
		this.projectImplementationPlanDataModel = projectImplementationPlanDataModel;
	}

}