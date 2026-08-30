package haj.com.ui;

import javax.annotation.PostConstruct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.service.ActiveContractsService;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.entity.datamodel.ActiveContractsDatamodel;
import haj.com.entity.datamodel.ActiveContractsGGTaskStatusDatamodel;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;

import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ancientprogramming.fixedformat4j.format.ParseException;

import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "sdfProjectTerminationUI")
@ViewScoped
public class SdfProjectTerminationUI extends AbstractUI {

	private ActiveContractsService service = new ActiveContractsService();
	private List<ActiveContracts> activecontractsList = null;
	private List<ActiveContracts> activecontractsfilteredList = null;
	private ActiveContracts activecontracts = null;
	private LazyDataModel<ActiveContracts> dataModel;
	private LazyDataModel<ActiveContracts> taskStatusDataModel;
	private Doc doc;
	private ActiveContractDetail activeContractDetail;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectReasonsList = null;
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();

	private Date projectedRecruitmentMinDate;
	private Date projectedRecruitmentMaxDate;

	private Date requitmentInductionMaxDate;
	private Date requitmentInductionMinDate;

	private Date minProjectedRecruitmentDate;
	private Date maxProjectedRecruitmentDate;

	private Date minEstimatedCompletionDate;
	private Date maxEstimatedCompletionDate;

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

	/**
	 * Initialize method to get all ActiveContracts and prepare a for a create of a
	 * new ActiveContracts
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.activecontracts = service.findByKey(getSessionUI().getTask().getTargetKey());
			this.activecontracts.setStatus(ApprovalEnum.SuspendProject);
			service.prepareNewRegistrationProjectTermination(getSessionUI().getTask(), getSessionUI().getTask().getWorkflowProcess(), activecontracts, getSessionUI().getTask().getProcessRole());
			populateRejectReasonsAssigned();
		} else {
			prepareNew();
			activecontractsInfo();
			dgAllocationTaskStatusInfo();
		}
		prepareValidationDates();
	}

	private void populateRejectReasonsAssigned() throws Exception {
		if (this.activecontracts != null && this.activecontracts.getId() != null) {
			rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(this.activecontracts.getId(), this.activecontracts.getClass().getName());
		} else {
			rejectReasonsList = null;
		}
	}

	/**
	 * Get all ActiveContracts for data table
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsInfo() {
		dataModel = new ActiveContractsDatamodel();
	}

	/**
	 * Get all ActiveContracts for data table
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void dgAllocationTaskStatusInfo() {
		taskStatusDataModel = new ActiveContractsGGTaskStatusDatamodel();
	}

	/**
	 * Insert ActiveContracts into database
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsInsert() {
		try {
			service.create(this.activecontracts);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			activecontractsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareValidationDates() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		// Getting the current
		if (activecontracts != null && activecontracts.getDgAllocationParent() != null && activecontracts.getDgAllocationParent().getWsp() != null) {
			// Using Grant year
			year = activecontracts.getDgAllocationParent().getWsp().getFinYear() - 1;
		}
		// Getting the previous year
		projectedRecruitmentMinDate = parseDate("01-01-" + (year - 1));
		projectedRecruitmentMaxDate = parseDate("31-03-" + (year + 1));

		requitmentInductionMinDate = parseDate("01-01-" + (year));
		requitmentInductionMaxDate = parseDate("31-03-" + (year + 1));

		minProjectedRecruitmentDate = parseDate("01-01-" + (year));
		maxProjectedRecruitmentDate = parseDate("31-03-" + (year + 1));

		minEstimatedCompletionDate = parseDate("01-01-" + (year));
		maxEstimatedCompletionDate = parseDate("31-03-" + (year + 5));
		String yearPlus = String.valueOf(year + 1);
		projectedRegistrationDisplayDate = "Between 1 January " + year + " and 31 March " + yearPlus;
	}

	public Date parseDate(String date) {
		try {
			return new SimpleDateFormat("dd-MM-yyy").parse(date);
		} catch (Exception e) {

			addErrorMessage(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Update ActiveContracts in database
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsUpdate() {
		try {
			service.update(this.activecontracts);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			activecontractsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ActiveContracts from database
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsDelete() {
		try {
			service.delete(this.activecontracts);
			prepareNew();
			activecontractsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ActiveContracts
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void prepareNew() {
		activecontracts = new ActiveContracts();
	}

	/*
	 * public List<SelectItem> getActiveContractsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * activecontractsInfo(); for (ActiveContracts ug : activecontractsList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ActiveContracts> complete(String desc) {
		List<ActiveContracts> l = null;
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
			service.completeWorkflowProjectTerminationSDF(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflowProjectTermination(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select rejection reason(s)");
			}
			service.rejectWorkflowProjectTerminationSDF(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflowProjectTermination(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select rejection reason(s)");
			}

			service.finalRejectWorkflowProjectTermination(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflowAndTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select rejection reason(s)");
			}

			service.finalRejectWorkflowAndTaskProjectTermination(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
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
				doc.setTargetKey(activecontracts.getId());
				doc.setTargetClass(ActiveContracts.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFileDetail(FileUploadEvent event) {
		try {
			service.createNewDetailForProcessing(event.getFile(), getSessionUI().getActiveUser(), activeContractDetail);
			super.runClientSideExecute("PF('dlgRequestPayment').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.DG_PROJECT_TERMINATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasonsSeniorManager() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		Boolean booleanValue = true;
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcessSeniorManager(ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION, booleanValue);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void terminateProject() {

		// Kicks off project termination workflow by creating a project termination
		// task...

		try {
			service.submitProjectTermination(activecontracts, getSessionUI().getActiveUser());
			addInfoMessage(super.getEntryLanguage("project.termination.initiated"));
			prepareNew();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<ActiveContracts> getActiveContractsList() {
		return activecontractsList;
	}

	public void setActiveContractsList(List<ActiveContracts> activecontractsList) {
		this.activecontractsList = activecontractsList;
	}

	public ActiveContracts getActivecontracts() {
		return activecontracts;
	}

	public void setActivecontracts(ActiveContracts activecontracts) {
		this.activecontracts = activecontracts;
	}

	public List<ActiveContracts> getActiveContractsfilteredList() {
		return activecontractsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param activecontractsfilteredList
	 *            the new activecontractsfilteredList list
	 * @see ActiveContracts
	 */
	public void setActiveContractsfilteredList(List<ActiveContracts> activecontractsfilteredList) {
		this.activecontractsfilteredList = activecontractsfilteredList;
	}

	public LazyDataModel<ActiveContracts> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContracts> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * @param doc
	 *            the doc to set
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * @return the activeContractDetail
	 */
	public ActiveContractDetail getActiveContractDetail() {
		return activeContractDetail;
	}

	/**
	 * @param activeContractDetail
	 *            the activeContractDetail to set
	 */
	public void setActiveContractDetail(ActiveContractDetail activeContractDetail) {
		this.activeContractDetail = activeContractDetail;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Date getProjectedRecruitmentMinDate() {
		return projectedRecruitmentMinDate;
	}

	public void setProjectedRecruitmentMinDate(Date projectedRecruitmentMinDate) {
		this.projectedRecruitmentMinDate = projectedRecruitmentMinDate;
	}

	public Date getProjectedRecruitmentMaxDate() {
		return projectedRecruitmentMaxDate;
	}

	public void setProjectedRecruitmentMaxDate(Date projectedRecruitmentMaxDate) {
		this.projectedRecruitmentMaxDate = projectedRecruitmentMaxDate;
	}

	/**
	 * @return the requitmentInductionMaxDate
	 */
	public Date getRequitmentInductionMaxDate() {
		return requitmentInductionMaxDate;
	}

	/**
	 * @param requitmentInductionMaxDate
	 *            the requitmentInductionMaxDate to set
	 */
	public void setRequitmentInductionMaxDate(Date requitmentInductionMaxDate) {
		this.requitmentInductionMaxDate = requitmentInductionMaxDate;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public Date getRequitmentInductionMinDate() {
		return requitmentInductionMinDate;
	}

	public void setRequitmentInductionMinDate(Date requitmentInductionMinDate) {
		this.requitmentInductionMinDate = requitmentInductionMinDate;
	}

	public Date getMinProjectedRecruitmentDate() {
		return minProjectedRecruitmentDate;
	}

	public void setMinProjectedRecruitmentDate(Date minProjectedRecruitmentDate) {
		this.minProjectedRecruitmentDate = minProjectedRecruitmentDate;
	}

	public Date getMaxProjectedRecruitmentDate() {
		return maxProjectedRecruitmentDate;
	}

	public void setMaxProjectedRecruitmentDate(Date maxProjectedRecruitmentDate) {
		this.maxProjectedRecruitmentDate = maxProjectedRecruitmentDate;
	}

	public Date getMinEstimatedCompletionDate() {
		return minEstimatedCompletionDate;
	}

	public void setMinEstimatedCompletionDate(Date minEstimatedCompletionDate) {
		this.minEstimatedCompletionDate = minEstimatedCompletionDate;
	}

	public Date getMaxEstimatedCompletionDate() {
		return maxEstimatedCompletionDate;
	}

	public void setMaxEstimatedCompletionDate(Date maxEstimatedCompletionDate) {
		this.maxEstimatedCompletionDate = maxEstimatedCompletionDate;
	}

	/**
	 * @return the taskStatusDataModel
	 */
	public LazyDataModel<ActiveContracts> getTaskStatusDataModel() {
		return taskStatusDataModel;
	}

	/**
	 * @param taskStatusDataModel
	 *            the taskStatusDataModel to set
	 */
	public void setTaskStatusDataModel(LazyDataModel<ActiveContracts> taskStatusDataModel) {
		this.taskStatusDataModel = taskStatusDataModel;
	}

	/**
	 * @return the projectedRegistrationDisplayDate
	 */
	public String getProjectedRegistrationDisplayDate() {
		return projectedRegistrationDisplayDate;
	}

	/**
	 * @param projectedRegistrationDisplayDate
	 *            the projectedRegistrationDisplayDate to set
	 */
	public void setProjectedRegistrationDisplayDate(String projectedRegistrationDisplayDate) {
		this.projectedRegistrationDisplayDate = projectedRegistrationDisplayDate;
	}

}
