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

import haj.com.entity.Doc;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.GpGrantBatchEntryService;
import haj.com.service.GpGrantBatchListService;
import haj.com.service.TasksService;
import haj.com.service.TransactionsCompanyValidiationService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "gpgrantbatchlistUI")
@ViewScoped
public class GpGrantBatchListUI extends AbstractUI {

	/* Entity */
	private GpGrantBatchList gpgrantbatchlist = null;
	private GpGrantBatchEntry gpGrantBatchEntry = null;
	private Doc doc = null;
	
	/* Service Levels */
	private GpGrantBatchListService service = new GpGrantBatchListService();
	private GpGrantBatchEntryService gpGrantBatchEntryService = new GpGrantBatchEntryService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	private TransactionsCompanyValidiationService transactionsCompanyValidiationService = new TransactionsCompanyValidiationService();
	
	/* Array Lists */
	private List<GpGrantBatchList> gpgrantbatchlistList = null;
	private List<GpGrantBatchList> gpgrantbatchlistfilteredList = null;
	private List<RejectReasons> rejectionReasonsAssigned = null; 
	private List<RejectReasons> selectedRejectReason = null;
	
	/* Lazy Data Model Lists */
	private LazyDataModel<GpGrantBatchList> dataModel;
	private LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntry;
	private LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntryToBeRemoved;
	private LazyDataModel<TransactionsCompanyValidiation> dataModelTransactionsCompanyValidiationPassedValidiation;
	private LazyDataModel<TransactionsCompanyValidiation> dataModelTransactionsCompanyValidiationFailedValidiation;
	
	/* Booleans */
	private Boolean canEditWorkflow = false;
	private Boolean canUploadWorkflow = false;
	private Boolean canFinalApproveWorkflow = false;
	private boolean finalApproveVisible = false;
	
	private boolean displayAdditionalRejectionReasonDisplay = false;
	private String additionalRejectionReasonDisplay;  
	private String additionalRejectionReasonEntry;  
	private Integer entriesPassesValidiation = 0;
	private Integer entriesFailedValidiation = 0;
	
	/* Vars */
	private Double runningTotalForPayments = 0.0;

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
	 * Initialize method to get all GpGrantBatchList and prepare a for a create
	 * of a new GpGrantBatchList
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.MG_DG_TRANSACTIONS || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_TRANSACTIONS)) {
			
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
			
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			gpgrantbatchlist = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewDocsGpGrantBatchList(gpgrantbatchlist, getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.Company);
			calculatePermissions();
			populateLists();
			populateRunningTotals();
			selectedRejectReason = new ArrayList<>();
			rejectionReasonsAssigned = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(gpgrantbatchlist.getId(), gpgrantbatchlist.getClass().getName(), getSessionUI().getTask().getWorkflowProcess());
			if (gpgrantbatchlist.getAdditionalRejectionReason() != null && !gpgrantbatchlist.getAdditionalRejectionReason().isEmpty()) {
				displayAdditionalRejectionReasonDisplay = true;
				additionalRejectionReasonDisplay = gpgrantbatchlist.getAdditionalRejectionReason();
			}
			additionalRejectionReasonEntry = "";
			populateCompanyValidationResults();
			if (Boolean.TRUE.equals(canFinalApproveWorkflow)) {
				determineFinalApproveVisible();
			}
		} else {
			ajaxRedirectToDashboard();
		}
	}

	private void calculatePermissions() throws Exception{
		if (getSessionUI().getTask().getProcessRole() != null && getSessionUI().getTask().getProcessRole().getRolePermission() != null) {
			canEditWorkflow = TasksService.instance().canEditBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canUploadWorkflow = TasksService.instance().canUploadBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canFinalApproveWorkflow = TasksService.instance().canFinalApproveTaskBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			
		} else {
			canEditWorkflow = true;
			canUploadWorkflow = true;
		}
	}
	
	private void populateRunningTotals() throws Exception{
		runningTotalForPayments = 0.0;
		if (gpgrantbatchlist != null && gpgrantbatchlist.getWspType() == WspTypeEnum.Mandatory) {
			runningTotalForPayments = gpGrantBatchEntryService.sumMandatoryLevyAllEntriesByBatchListToBeRemoved(gpgrantbatchlist.getId(), false);
		} else if (gpgrantbatchlist != null && gpgrantbatchlist.getWspType() == WspTypeEnum.Discretionary) {
			runningTotalForPayments = gpGrantBatchEntryService.runningTotalForPaymentsDG(gpgrantbatchlist.getId(), false);
		}
	}
	
	private void determineFinalApproveVisible() throws Exception{
		finalApproveVisible = true;
		// Test if validation has run on batch level
		if (gpgrantbatchlist.getValidiationRun() == null || Boolean.FALSE.equals(gpgrantbatchlist.getValidiationRun())) {
			finalApproveVisible = false;
		} else {
			companyValidiationCounts();
			
			// check if any failed entries
			if (entriesFailedValidiation > 0) {
				finalApproveVisible = false;
			}
			
			// Validation underway
			if (Boolean.TRUE.equals(gpgrantbatchlist.getValidiationUnderway())) {
				finalApproveVisible = false;
			}
		}
	}
	
	private void populateCompanyValidationResults() throws Exception{
		companyValidiationCounts();
		dataModelTransactionsCompanyValidiationPassedValidiationInfo();
		dataModelTransactionsCompanyValidiationFailedValidiationInfo();
	}
	
	private void companyValidiationCounts() throws Exception{
		if (gpgrantbatchlist != null && gpgrantbatchlist.getId() != null) {
			entriesPassesValidiation = transactionsCompanyValidiationService.countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(gpgrantbatchlist.getClass().getName(), gpgrantbatchlist.getId(), false);
			entriesFailedValidiation = transactionsCompanyValidiationService.countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(gpgrantbatchlist.getClass().getName(), gpgrantbatchlist.getId(), true);
		} else {
			entriesPassesValidiation = 0;
			entriesFailedValidiation = 0;
		}
	}
	
	private void dataModelTransactionsCompanyValidiationPassedValidiationInfo() {
		dataModelTransactionsCompanyValidiationPassedValidiation = new LazyDataModel<TransactionsCompanyValidiation>() {
			private static final long serialVersionUID = 1L;
			private List<TransactionsCompanyValidiation> retorno = new ArrayList<>();
			@Override
			public List<TransactionsCompanyValidiation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
				
					retorno = transactionsCompanyValidiationService.allTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist.getClass().getName(), gpgrantbatchlist.getId(), false);
					dataModelTransactionsCompanyValidiationPassedValidiation.setRowCount(transactionsCompanyValidiationService.countAllTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(filters));
					
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TransactionsCompanyValidiation obj) {
				return obj.getId();
			}
			@Override
			public TransactionsCompanyValidiation getRowData(String rowKey) {
				for (TransactionsCompanyValidiation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void dataModelTransactionsCompanyValidiationFailedValidiationInfo() {
		dataModelTransactionsCompanyValidiationFailedValidiation = new LazyDataModel<TransactionsCompanyValidiation>() {
			private static final long serialVersionUID = 1L;
			private List<TransactionsCompanyValidiation> retorno = new ArrayList<>();
			@Override
			public List<TransactionsCompanyValidiation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = transactionsCompanyValidiationService.allTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist.getClass().getName(), gpgrantbatchlist.getId(), true);
					dataModelTransactionsCompanyValidiationFailedValidiation.setRowCount(transactionsCompanyValidiationService.countAllTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TransactionsCompanyValidiation obj) {
				return obj.getId();
			}
			@Override
			public TransactionsCompanyValidiation getRowData(String rowKey) {
				for (TransactionsCompanyValidiation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	

	public void populateLists() {
		dataModelGpGrantBatchEntryInfo();
		dataModelGpGrantBatchEntryToBeRemovedInfo();
	}
	
	private void dataModelGpGrantBatchEntryInfo() {
		 dataModelGpGrantBatchEntry = new LazyDataModel<GpGrantBatchEntry>() {
			private static final long serialVersionUID = 1L;
			private List<GpGrantBatchEntry> retorno = new ArrayList<>();
			@Override
			public List<GpGrantBatchEntry> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (gpgrantbatchlist.getWspType() == WspTypeEnum.Mandatory) {
						retorno = gpGrantBatchEntryService.gpGrantBatchEntryByBatchListToBeRemovedValueMandatory(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist, false);
						dataModelGpGrantBatchEntry.setRowCount(gpGrantBatchEntryService.countGpGrantBatchEntryByBatchListToBeRemovedValueMandatory(filters));
					} else {
						retorno = gpGrantBatchEntryService.gpGrantBatchEntryByBatchListToBeRemovedValue(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist, false);
						dataModelGpGrantBatchEntry.setRowCount(gpGrantBatchEntryService.countGpGrantBatchEntryByBatchListToBeRemovedValue(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(GpGrantBatchEntry obj) {
				return obj.getId();
			}
			@Override
			public GpGrantBatchEntry getRowData(String rowKey) {
				for (GpGrantBatchEntry obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void dataModelGpGrantBatchEntryToBeRemovedInfo() {
		dataModelGpGrantBatchEntryToBeRemoved = new LazyDataModel<GpGrantBatchEntry>() {
			private static final long serialVersionUID = 1L;
			private List<GpGrantBatchEntry> retorno = new ArrayList<>();
			@Override
			public List<GpGrantBatchEntry> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (gpgrantbatchlist.getWspType() == WspTypeEnum.Mandatory) {
						retorno = gpGrantBatchEntryService.gpGrantBatchEntryByBatchListToBeRemovedValueMandatory(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist, true);
						dataModelGpGrantBatchEntryToBeRemoved.setRowCount(gpGrantBatchEntryService.countGpGrantBatchEntryByBatchListToBeRemovedValueMandatory(filters));
					} else {
						retorno = gpGrantBatchEntryService.gpGrantBatchEntryByBatchListToBeRemovedValue(first, pageSize, sortField, sortOrder, filters, gpgrantbatchlist, true);
						dataModelGpGrantBatchEntryToBeRemoved.setRowCount(gpGrantBatchEntryService.countGpGrantBatchEntryByBatchListToBeRemovedValue(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(GpGrantBatchEntry obj) {
				return obj.getId();
			}
			@Override
			public GpGrantBatchEntry getRowData(String rowKey) {
				for (GpGrantBatchEntry obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void updateEntry(){
		try {
			if (Boolean.TRUE.equals(gpGrantBatchEntry.getToBeRemoved())) {
				gpGrantBatchEntry.setToBeRemoved(false);
			} else {
				gpGrantBatchEntry.setToBeRemoved(true);
			}
			gpGrantBatchEntry.setUserActioned(getSessionUI().getActiveUser());
			gpGrantBatchEntry.setUserActionedDate(getNow());
			gpGrantBatchEntryService.update(gpGrantBatchEntry);
			populateLists();
			populateRunningTotals();
			addInfoMessage("Entry Updated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejection(){
		try {
			additionalRejectionReasonEntry = "";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
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
			doc.setTargetClass(gpgrantbatchlist.getClass().getName());
			doc.setTargetKey(gpgrantbatchlist.getId());
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
	
	public void completeTask(){
		try {
			service.completeTask(gpgrantbatchlist, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectTask(){
		try {
			if (selectedRejectReason.isEmpty()) {
				addWarningMessage("Provide Atleast One Rejection Reason");
			} else {
				service.rejectTask(gpgrantbatchlist, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, additionalRejectionReasonEntry);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveTask(){
		try {
			service.approveTask(gpgrantbatchlist, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveGpTask(){
		try {
			service.finalApproveTask(gpgrantbatchlist, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectTask(){
		try {
			if (selectedRejectReason.isEmpty()) {
				addWarningMessage("Provide Atleast One Rejection Reason");
			} else {
				service.finalRejectTask(gpgrantbatchlist, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason, additionalRejectionReasonEntry);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiationCheck() {
		try {
			service.runValidiation(getSessionUI().getActiveUser(), gpgrantbatchlist);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage() , e);
		}
	}

	/**
	 * Insert GpGrantBatchList into database
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 */
	public void gpgrantbatchlistInsert() {
		try {
			service.create(this.gpgrantbatchlist);
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
	 * Update GpGrantBatchList in database
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 */
	public void gpgrantbatchlistUpdate() {
		try {
			service.update(this.gpgrantbatchlist);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete GpGrantBatchList from database
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 */
	public void gpgrantbatchlistDelete() {
		try {
			service.delete(this.gpgrantbatchlist);
			prepareNew();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of GpGrantBatchList
	 * 
	 * @author TechFinium
	 * @see GpGrantBatchList
	 */
	public void prepareNew() {
		gpgrantbatchlist = new GpGrantBatchList();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<GpGrantBatchList> complete(String desc) {
		List<GpGrantBatchList> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<GpGrantBatchList> getGpGrantBatchListList() {
		return gpgrantbatchlistList;
	}

	public void setGpGrantBatchListList(List<GpGrantBatchList> gpgrantbatchlistList) {
		this.gpgrantbatchlistList = gpgrantbatchlistList;
	}

	public GpGrantBatchList getGpgrantbatchlist() {
		return gpgrantbatchlist;
	}

	public void setGpgrantbatchlist(GpGrantBatchList gpgrantbatchlist) {
		this.gpgrantbatchlist = gpgrantbatchlist;
	}

	public List<GpGrantBatchList> getGpGrantBatchListfilteredList() {
		return gpgrantbatchlistfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param gpgrantbatchlistfilteredList
	 *            the new gpgrantbatchlistfilteredList list
	 * @see GpGrantBatchList
	 */
	public void setGpGrantBatchListfilteredList(List<GpGrantBatchList> gpgrantbatchlistfilteredList) {
		this.gpgrantbatchlistfilteredList = gpgrantbatchlistfilteredList;
	}

	public LazyDataModel<GpGrantBatchList> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GpGrantBatchList> dataModel) {
		this.dataModel = dataModel;
	}

	public GpGrantBatchEntry getGpGrantBatchEntry() {
		return gpGrantBatchEntry;
	}

	public void setGpGrantBatchEntry(GpGrantBatchEntry gpGrantBatchEntry) {
		this.gpGrantBatchEntry = gpGrantBatchEntry;
	}

	public LazyDataModel<GpGrantBatchEntry> getDataModelGpGrantBatchEntry() {
		return dataModelGpGrantBatchEntry;
	}

	public void setDataModelGpGrantBatchEntry(LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntry) {
		this.dataModelGpGrantBatchEntry = dataModelGpGrantBatchEntry;
	}

	public LazyDataModel<GpGrantBatchEntry> getDataModelGpGrantBatchEntryToBeRemoved() {
		return dataModelGpGrantBatchEntryToBeRemoved;
	}

	public void setDataModelGpGrantBatchEntryToBeRemoved(
			LazyDataModel<GpGrantBatchEntry> dataModelGpGrantBatchEntryToBeRemoved) {
		this.dataModelGpGrantBatchEntryToBeRemoved = dataModelGpGrantBatchEntryToBeRemoved;
	}

	

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public void setRejectionReasonsAssigned(List<RejectReasons> rejectionReasonsAssigned) {
		this.rejectionReasonsAssigned = rejectionReasonsAssigned;
	}

	public List<RejectReasons> getRejectionReasonsAssigned() {
		return rejectionReasonsAssigned;
	}

	public boolean isDisplayAdditionalRejectionReasonDisplay() {
		return displayAdditionalRejectionReasonDisplay;
	}

	public void setDisplayAdditionalRejectionReasonDisplay(boolean displayAdditionalRejectionReasonDisplay) {
		this.displayAdditionalRejectionReasonDisplay = displayAdditionalRejectionReasonDisplay;
	}

	public String getAdditionalRejectionReasonDisplay() {
		return additionalRejectionReasonDisplay;
	}

	public void setAdditionalRejectionReasonDisplay(String additionalRejectionReasonDisplay) {
		this.additionalRejectionReasonDisplay = additionalRejectionReasonDisplay;
	}

	public String getAdditionalRejectionReasonEntry() {
		return additionalRejectionReasonEntry;
	}

	public void setAdditionalRejectionReasonEntry(String additionalRejectionReasonEntry) {
		this.additionalRejectionReasonEntry = additionalRejectionReasonEntry;
	}

	public Double getRunningTotalForPayments() {
		return runningTotalForPayments;
	}

	public void setRunningTotalForPayments(Double runningTotalForPayments) {
		this.runningTotalForPayments = runningTotalForPayments;
	}

	public boolean isFinalApproveVisible() {
		return finalApproveVisible;
	}

	public void setFinalApproveVisible(boolean finalApproveVisible) {
		this.finalApproveVisible = finalApproveVisible;
	}

	public Integer getEntriesPassesValidiation() {
		return entriesPassesValidiation;
	}

	public void setEntriesPassesValidiation(Integer entriesPassesValidiation) {
		this.entriesPassesValidiation = entriesPassesValidiation;
	}

	public Integer getEntriesFailedValidiation() {
		return entriesFailedValidiation;
	}

	public void setEntriesFailedValidiation(Integer entriesFailedValidiation) {
		this.entriesFailedValidiation = entriesFailedValidiation;
	}

	public LazyDataModel<TransactionsCompanyValidiation> getDataModelTransactionsCompanyValidiationPassedValidiation() {
		return dataModelTransactionsCompanyValidiationPassedValidiation;
	}

	public void setDataModelTransactionsCompanyValidiationPassedValidiation(
			LazyDataModel<TransactionsCompanyValidiation> dataModelTransactionsCompanyValidiationPassedValidiation) {
		this.dataModelTransactionsCompanyValidiationPassedValidiation = dataModelTransactionsCompanyValidiationPassedValidiation;
	}

	public LazyDataModel<TransactionsCompanyValidiation> getDataModelTransactionsCompanyValidiationFailedValidiation() {
		return dataModelTransactionsCompanyValidiationFailedValidiation;
	}

	public void setDataModelTransactionsCompanyValidiationFailedValidiation(
			LazyDataModel<TransactionsCompanyValidiation> dataModelTransactionsCompanyValidiationFailedValidiation) {
		this.dataModelTransactionsCompanyValidiationFailedValidiation = dataModelTransactionsCompanyValidiationFailedValidiation;
	}

	public Boolean getCanEditWorkflow() {
		return canEditWorkflow;
	}

	public void setCanEditWorkflow(Boolean canEditWorkflow) {
		this.canEditWorkflow = canEditWorkflow;
	}

	public Boolean getCanUploadWorkflow() {
		return canUploadWorkflow;
	}

	public void setCanUploadWorkflow(Boolean canUploadWorkflow) {
		this.canUploadWorkflow = canUploadWorkflow;
	}

	public Boolean getCanFinalApproveWorkflow() {
		return canFinalApproveWorkflow;
	}

	public void setCanFinalApproveWorkflow(Boolean canFinalApproveWorkflow) {
		this.canFinalApproveWorkflow = canFinalApproveWorkflow;
	}

}
