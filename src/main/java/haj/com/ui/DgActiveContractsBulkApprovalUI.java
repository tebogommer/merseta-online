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

import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.DgContractingBulkEntry;
import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Signoff;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.DgContractingBulkEntryService;
import haj.com.service.DgContractingBulkItemsService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SignoffService;

@ManagedBean(name = "dgActiveContractsBulkApprovalUI")
@ViewScoped
public class DgActiveContractsBulkApprovalUI extends AbstractUI {

	/* Entity */
	private ActiveContracts activecontracts = null;
	private ActiveContractDetail activeContractDetail;
	private DgContractingBulkEntry dgContractingBulkEntryOpen = null;
	private DgContractingBulkEntry dgContractingBulkEntry = null;
	private DgContractingBulkItems dgContractingBulkItems = null;
	private Doc doc;

	/* Service Levels */
	private ActiveContractsService service = new ActiveContractsService();
	private ProjectImplementationPlanService implementationPlanService = new ProjectImplementationPlanService();
	private SignoffService signoffService = new SignoffService();
	private DgContractingBulkEntryService dgContractingBulkEntryService = new DgContractingBulkEntryService();
	private DgContractingBulkItemsService dgContractingBulkItemsService = new DgContractingBulkItemsService();

	/* Data Model Lists */
	private LazyDataModel<ActiveContracts> dataModel;
	private LazyDataModel<DgContractingBulkEntry> dgContractingBulkEntryDataModel;
	private LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsAwaitingProcessingDataModel;
	private LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsDataModel;

	/* Array Lists */
	private List<ProjectImplementationPlan> projectimplementationplanList = null;
	private List<Signoff> signOffMoaList = null;
	
	/* Vars */
	private Integer countOfEntriesAwaitingProcess = 0;
	private Integer countOfEntriesAvalaibleForProcessing = 0;
	private Integer countOfEntriesPreviouslyProcessed = 0;
	private boolean displaybatchEntries = false;
	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;

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
		locateOpenBulkEntry();
		// locate active contracts open.
		activecontractsInfo();
		// locates entries awaiting to be submitted for processing.
		dgContractingBulkItemsAwaitingProcessingDataModelInfo();
		// locates entries previously processed
		dgContractingBulkEntryDataModelInfo();
	}

	private void locateOpenBulkEntry() throws Exception{
		dgContractingBulkEntryOpen = dgContractingBulkEntryService.findOpenBatchEntry();
		if (dgContractingBulkEntryOpen == null) {
			dgContractingBulkEntryOpen = dgContractingBulkEntryService.createOpenEntry(dgContractingBulkEntryOpen, getSessionUI().getActiveUser());
		}
	}

	public void dgContractingBulkItemsAwaitingProcessingDataModelInfo() {
		try {
			countOfEntriesAwaitingProcess = dgContractingBulkItemsService.countEntriesAgainstDgContractingBulkEntry(dgContractingBulkEntryOpen);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		dgContractingBulkItemsAwaitingProcessingDataModel = new LazyDataModel<DgContractingBulkItems>() {
			private static final long serialVersionUID = 1L;
			private List<DgContractingBulkItems> retorno = new ArrayList<DgContractingBulkItems>();
			@Override
			public List<DgContractingBulkItems> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgContractingBulkItemsService.allDgContractingBulkItemsByBulkEntry(first, pageSize, sortField, sortOrder, filters, dgContractingBulkEntryOpen);
					dgContractingBulkItemsAwaitingProcessingDataModel.setRowCount(dgContractingBulkItemsService.countAllDgContractingBulkItemsByBulkEntry(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DgContractingBulkItems obj) {
				return obj.getId();
			}
			@Override
			public DgContractingBulkItems getRowData(String rowKey) {
				for (DgContractingBulkItems obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void activecontractsInfo() {
		try {
			countOfEntriesAvalaibleForProcessing = service.countDgContractsAwaitingProcessing();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		dataModel = new LazyDataModel<ActiveContracts>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContracts> retorno = new ArrayList<ActiveContracts>();
			@Override
			public List<ActiveContracts> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allActiveContractsAwaitingSignOff(ActiveContracts.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countAllActiveContractsAwaitingSignOff(ActiveContracts.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ActiveContracts obj) {
				return obj.getId();
			}
			@Override
			public ActiveContracts getRowData(String rowKey) {
				for (ActiveContracts obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dgContractingBulkEntryDataModelInfo() {
		try {
			countOfEntriesPreviouslyProcessed = dgContractingBulkEntryService.countBulkEntriesWithStatus();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		dgContractingBulkEntryDataModel = new LazyDataModel<DgContractingBulkEntry>() {
			private static final long serialVersionUID = 1L;
			private List<DgContractingBulkEntry> retorno = new ArrayList<DgContractingBulkEntry>();
			@Override
			public List<DgContractingBulkEntry> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgContractingBulkEntryService.allDgContractingBulkEntryWithStatusAssigned(first, pageSize, sortField, sortOrder, filters);
					dgContractingBulkEntryDataModel.setRowCount(dgContractingBulkEntryService.countAllDgContractingBulkEntryWithStatusAssigned(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DgContractingBulkEntry obj) {
				return obj.getId();
			}
			@Override
			public DgContractingBulkEntry getRowData(String rowKey) {
				for (DgContractingBulkEntry obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void selectContractToView() {
		try {
			populateActiveContractInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void populateActiveContractInfo() throws Exception {
		this.activecontracts = service.findByKey(activecontracts.getId());
//		service.prepareNewRegistration(getSessionUI().getTask(), getSessionUI().getTask().getWorkflowProcess(), activecontracts, getSessionUI().getTask().getProcessRole());
		if (activecontracts.getDgAllocationParent() != null) projectimplementationplanList = implementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(activecontracts.getDgAllocationParent().getWsp());
		populateSignOffs();
	}
	
	public void closeContractView() {
		try {
			activecontracts = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffs() throws Exception {
		signOffMoaList = signoffService.findByTargetKeyAndClass(activecontracts.getId(), activecontracts.getClass().getName());
	}
	
	public void approveActiveContract(){
		try {
			if (activecontracts == null || activecontracts.getId() == null) {
				populateActiveContractInfo();
				service.approveOneBatchEntryOfActiveContracts(activecontracts, getSessionUI().getActiveUser());
			} else {
				addErrorMessage("Unable to locate active contract selected, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectActiveContract(){
		try {
			if (activecontracts == null || activecontracts.getId() == null) {
				populateActiveContractInfo();			
			} else {
				addErrorMessage("Unable to locate active contract selected, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveActiveContractBatch(){
		try {
			if (activecontracts == null || activecontracts.getId() == null) {
				populateActiveContractInfo();
				service.approveOneBatchEntryOfActiveContracts(activecontracts, getSessionUI().getActiveUser());
			} else {
				addErrorMessage("Unable to locate active contract selected, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectActiveContractbatch(){
		try {
			if (activecontracts == null || activecontracts.getId() == null) {
				populateActiveContractInfo();			
			} else {
				addErrorMessage("Unable to locate active contract selected, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addToOpenBulkEntry(){
		try {
			if (dgContractingBulkEntryOpen == null) {
				locateOpenBulkEntry();
			}
			dgContractingBulkItemsService.linkActiveContractToBulkEntry(activecontracts, dgContractingBulkEntryOpen, getSessionUI().getActiveUser());
			dgContractingBulkItemsAwaitingProcessingDataModelInfo();
			activecontractsInfo();
			activecontracts = null;
			displaybatchEntries = false;
			addInfoMessage("DG Contract Linked To Batch Number: " + dgContractingBulkEntryOpen.getBatchNumber());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeFromBulkEntryList(){
		try {
			if (dgContractingBulkItems == null) {
				addErrorMessage("Unable to locate item to be removed, contact support!");
			} else {
				dgContractingBulkItemsService.deleteLink(dgContractingBulkItems);
				activecontracts = null;
				displaybatchEntries = false;
				dgContractingBulkItemsAwaitingProcessingDataModelInfo();
				activecontractsInfo();
				addInfoMessage("DG Contract Linked To Batch Number: " + dgContractingBulkEntryOpen.getBatchNumber() + " Removed.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitBatchForApproval() {
		try {
			dgContractingBulkEntryService.submitForApproval(dgContractingBulkEntryOpen, getSessionUI().getActiveUser());
			activecontracts = null;
			dgContractingBulkItems = null;
			dgContractingBulkEntryOpen = null;
			displaybatchEntries = false;
			locateOpenBulkEntry();
			dgContractingBulkItemsAwaitingProcessingDataModelInfo();
			activecontractsInfo();
			dgContractingBulkEntryDataModelInfo();
			addInfoMessage("Batch Submitted For Approval");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void viewBatchEntries(){
		try {
			displaybatchEntries = true; 
			dgContractingBulkItemsDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeBatchEntriesView(){
		try {
			displaybatchEntries = false;
			dgContractingBulkEntry = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dgContractingBulkItemsDataModelInfo() {
		dgContractingBulkItemsDataModel = new LazyDataModel<DgContractingBulkItems>() {
			private static final long serialVersionUID = 1L;
			private List<DgContractingBulkItems> retorno = new ArrayList<DgContractingBulkItems>();
			@Override
			public List<DgContractingBulkItems> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgContractingBulkItemsService.allDgContractingBulkItemsByBulkEntry(first, pageSize, sortField, sortOrder, filters, dgContractingBulkEntry);
					dgContractingBulkItemsDataModel.setRowCount(dgContractingBulkItemsService.countAllDgContractingBulkItemsByBulkEntry(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DgContractingBulkItems obj) {
				return obj.getId();
			}
			@Override
			public DgContractingBulkItems getRowData(String rowKey) {
				for (DgContractingBulkItems obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void downloadMoaVersionTwo(){
		try {
			service.downloadMoaVersionTwo(activecontracts, true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepWithdrawOfApplication(){
		try {
			doc = new Doc();
			doc.setTargetKey(activecontracts.getId());
			doc.setTargetClass(ActiveContracts.class.getName());
			doc.setUsers(getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeFileMemory(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			super.runClientSideExecute("PF('dlgUploadMemory').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void withdrawActiveContractsCompanyContact(){
		try {
			if (withdrawalAppealEnum == null) {
				addErrorMessage("Select A Reason Before Proceeding");
			} else {
				service.withdrawActiveContracts(activecontracts, getSessionUI().getActiveUser(), withdrawalAppealEnum, getSessionUI().getTask(), doc);
				activecontracts = null;
				activecontractsInfo();
				dgContractingBulkItemsAwaitingProcessingDataModelInfo();
				dgContractingBulkEntryDataModelInfo();
				addWarningMessage("DG Contract Withdrawn");
				super.runClientSideExecute("PF('dlgSelectReasonWithdrawal').hide()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public ActiveContracts getActivecontracts() {
		return activecontracts;
	}

	public void setActivecontracts(ActiveContracts activecontracts) {
		this.activecontracts = activecontracts;
	}

	public ActiveContractDetail getActiveContractDetail() {
		return activeContractDetail;
	}

	public void setActiveContractDetail(ActiveContractDetail activeContractDetail) {
		this.activeContractDetail = activeContractDetail;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public LazyDataModel<ActiveContracts> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContracts> dataModel) {
		this.dataModel = dataModel;
	}

	public List<ProjectImplementationPlan> getProjectimplementationplanList() {
		return projectimplementationplanList;
	}

	public void setProjectimplementationplanList(List<ProjectImplementationPlan> projectimplementationplanList) {
		this.projectimplementationplanList = projectimplementationplanList;
	}

	public List<Signoff> getSignOffMoaList() {
		return signOffMoaList;
	}

	public void setSignOffMoaList(List<Signoff> signOffMoaList) {
		this.signOffMoaList = signOffMoaList;
	}

	public DgContractingBulkEntry getDgContractingBulkEntry() {
		return dgContractingBulkEntry;
	}

	public void setDgContractingBulkEntry(DgContractingBulkEntry dgContractingBulkEntry) {
		this.dgContractingBulkEntry = dgContractingBulkEntry;
	}

	public DgContractingBulkItems getDgContractingBulkItems() {
		return dgContractingBulkItems;
	}

	public void setDgContractingBulkItems(DgContractingBulkItems dgContractingBulkItems) {
		this.dgContractingBulkItems = dgContractingBulkItems;
	}

	public DgContractingBulkEntry getDgContractingBulkEntryOpen() {
		return dgContractingBulkEntryOpen;
	}

	public void setDgContractingBulkEntryOpen(DgContractingBulkEntry dgContractingBulkEntryOpen) {
		this.dgContractingBulkEntryOpen = dgContractingBulkEntryOpen;
	}

	public LazyDataModel<DgContractingBulkItems> getDgContractingBulkItemsDataModel() {
		return dgContractingBulkItemsDataModel;
	}

	public void setDgContractingBulkItemsDataModel(LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsDataModel) {
		this.dgContractingBulkItemsDataModel = dgContractingBulkItemsDataModel;
	}

	public LazyDataModel<DgContractingBulkEntry> getDgContractingBulkEntryDataModel() {
		return dgContractingBulkEntryDataModel;
	}

	public void setDgContractingBulkEntryDataModel(LazyDataModel<DgContractingBulkEntry> dgContractingBulkEntryDataModel) {
		this.dgContractingBulkEntryDataModel = dgContractingBulkEntryDataModel;
	}

	public LazyDataModel<DgContractingBulkItems> getDgContractingBulkItemsAwaitingProcessingDataModel() {
		return dgContractingBulkItemsAwaitingProcessingDataModel;
	}

	public void setDgContractingBulkItemsAwaitingProcessingDataModel(
			LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsAwaitingProcessingDataModel) {
		this.dgContractingBulkItemsAwaitingProcessingDataModel = dgContractingBulkItemsAwaitingProcessingDataModel;
	}

	public Integer getCountOfEntriesAwaitingProcess() {
		return countOfEntriesAwaitingProcess;
	}

	public void setCountOfEntriesAwaitingProcess(Integer countOfEntriesAwaitingProcess) {
		this.countOfEntriesAwaitingProcess = countOfEntriesAwaitingProcess;
	}

	public Integer getCountOfEntriesAvalaibleForProcessing() {
		return countOfEntriesAvalaibleForProcessing;
	}

	public void setCountOfEntriesAvalaibleForProcessing(Integer countOfEntriesAvalaibleForProcessing) {
		this.countOfEntriesAvalaibleForProcessing = countOfEntriesAvalaibleForProcessing;
	}

	public Integer getCountOfEntriesPreviouslyProcessed() {
		return countOfEntriesPreviouslyProcessed;
	}

	public void setCountOfEntriesPreviouslyProcessed(Integer countOfEntriesPreviouslyProcessed) {
		this.countOfEntriesPreviouslyProcessed = countOfEntriesPreviouslyProcessed;
	}

	public boolean isDisplaybatchEntries() {
		return displaybatchEntries;
	}

	public void setDisplaybatchEntries(boolean displaybatchEntries) {
		this.displaybatchEntries = displaybatchEntries;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

}
