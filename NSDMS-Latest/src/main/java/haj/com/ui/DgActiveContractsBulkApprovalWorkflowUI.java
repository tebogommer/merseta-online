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
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.BulkApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.DgContractingBulkEntryService;
import haj.com.service.DgContractingBulkItemsService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.RejectReasonMultipleSelectionService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "dgActiveContractsBulkApprovalWorkflowUI")
@ViewScoped
public class DgActiveContractsBulkApprovalWorkflowUI extends AbstractUI {

	/* Entity */
	private ActiveContracts activecontracts = null;
	private ActiveContractDetail activeContractDetail;
	private DgContractingBulkEntry dgContractingBulkEntry = null;
	private DgContractingBulkItems dgContractingBulkItems = null;
	private Doc doc;

	/* Service Levels */
	private ActiveContractsService service = new ActiveContractsService();
	private ProjectImplementationPlanService implementationPlanService = new ProjectImplementationPlanService();
	private SignoffService signoffService = new SignoffService();
	private DgContractingBulkEntryService dgContractingBulkEntryService = new DgContractingBulkEntryService();
	private DgContractingBulkItemsService dgContractingBulkItemsService = new DgContractingBulkItemsService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();

	/* Data Model Lists */
	private LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsDataModel;

	/* Array Lists */
	private List<ProjectImplementationPlan> projectimplementationplanList = null;
	private List<RejectReasons> selectedRejectReason = null;
	private List<RejectReasons> rejectReasonSelectionList = null;
	private List<Signoff> signOffMoaList = null;
	
	/* Vars */
	private Integer countofEntriesWithNoStatusAssigned = 0;
	private Integer countofEntriesToBeApproved = 0;
	private Integer countofEntriesToBeRejected = 0;
	private Integer countofEntriesToBeRejectedBackToSdf = 0;
	private Integer countofEntriesToBeRejectedBackToBulkAssignment = 0;
	private Integer countofEntriesWithdrawn = 0;
	private boolean canBulkApprove = false;
	private boolean provideRejectionReasons = false;
	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;
	
	/*
	 * Rejection Reasons Indicator
	 * 1 - Final Rejection
	 * 2 - Reject Bulk Assignment
	 * 3 - Reject To SDF
	 * 4 - Update to Rejection Reasons
	 */
	private int rejectReasonsIndicator = 0;

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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF && super.getParameter("id", false) != null) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.NotStarted || getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Underway || getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Overdue) {
					dgContractingBulkEntry = dgContractingBulkEntryService.findByKey(getSessionUI().getTask().getTargetKey());
					dgContractingBulkItemsDataModelInfo();
					rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
				} else {
					// fail safe
					getSessionUI().setTask(null);
					ajaxRedirectToDashboard();
				}
		} else {
			// false safe, page can only be accessed by task
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		}
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
	
	public void dgContractingBulkItemsDataModelInfo() {
		try {
//			countofEntriesWithNoStatusAssigned = dgContractingBulkItemsService.countEntriesWithoutStatus(dgContractingBulkEntry);
//			countofEntriesToBeApproved = dgContractingBulkItemsService.countEntriesByStatus(dgContractingBulkEntry, ApprovalEnum.Approved);
//			countofEntriesToBeRejected = dgContractingBulkItemsService.countEntriesByStatus(dgContractingBulkEntry, ApprovalEnum.Rejected);
			countofEntriesWithNoStatusAssigned = dgContractingBulkItemsService.countEntriesWithoutNewStatus(dgContractingBulkEntry);
			countofEntriesToBeApproved = dgContractingBulkItemsService.countEntriesByNewStatusAssigned(dgContractingBulkEntry, BulkApprovalEnum.FinalApproved);
			countofEntriesToBeRejected = dgContractingBulkItemsService.countEntriesByNewStatusAssigned(dgContractingBulkEntry, BulkApprovalEnum.FinalRejected);
			countofEntriesToBeRejectedBackToSdf = dgContractingBulkItemsService.countEntriesByNewStatusAssigned(dgContractingBulkEntry, BulkApprovalEnum.FinalToSdf);
			countofEntriesToBeRejectedBackToBulkAssignment = dgContractingBulkItemsService.countEntriesByNewStatusAssigned(dgContractingBulkEntry, BulkApprovalEnum.FinalToBulkAction);
			countofEntriesWithdrawn = dgContractingBulkItemsService.countEntriesByNewStatusAssigned(dgContractingBulkEntry, BulkApprovalEnum.Withdrawn);
			if (countofEntriesToBeRejected != 0 || countofEntriesToBeApproved != 0 || countofEntriesToBeRejectedBackToSdf != 0 || countofEntriesToBeRejectedBackToBulkAssignment != 0) {
				canBulkApprove = false;
			} else {
				canBulkApprove = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
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
	
	public void clearItemStatus(){
		try {
			dgContractingBulkItems.setStatusAssigned(null);
			dgContractingBulkItems.setBulkApproval(null);
			dgContractingBulkItems.setApprovalUser(null);
			dgContractingBulkItems.setApprovalDate(null);
			dgContractingBulkItemsService.update(dgContractingBulkItems);
			// clears any reasons assigned
			RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName());
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Item Status Cleared");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setItemToApproved(){
		try {
			dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Approved);
			dgContractingBulkItems.setBulkApproval(BulkApprovalEnum.FinalApproved);
			dgContractingBulkItems.setApprovalUser(null);
			dgContractingBulkItems.setApprovalDate(null);
			dgContractingBulkItemsService.update(dgContractingBulkItems);
			// clears any reasons assigned
			RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName());
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Item Set To Be: Final Approved");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepFinalRejection(){
		try {
			rejectReasonsIndicator = 1;
			selectedRejectReason = new ArrayList<>();
			rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
			runClientSideExecute("PF('rejectReasonsActionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setItemToFinalRejection(){
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
			}
			// creates rejection Reasons
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), selectedRejectReason, getSessionUI().getActiveUser(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Rejected);
			dgContractingBulkItems.setBulkApproval(BulkApprovalEnum.FinalRejected);
			dgContractingBulkItems.setApprovalUser(null);
			dgContractingBulkItems.setApprovalDate(null);
			dgContractingBulkItemsService.update(dgContractingBulkItems);
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Item Set To Be: Final Rejected");
			runClientSideExecute("PF('rejectReasonsActionDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejectBulkAssignment(){
		try {
			rejectReasonsIndicator = 2;
			selectedRejectReason = new ArrayList<>();
			rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
			runClientSideExecute("PF('rejectReasonsActionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setRejectBulkAssignment(){
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
			}
			// creates rejection Reasons
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), selectedRejectReason, getSessionUI().getActiveUser(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Rejected);
			dgContractingBulkItems.setBulkApproval(BulkApprovalEnum.FinalToBulkAction);
			dgContractingBulkItems.setApprovalUser(null);
			dgContractingBulkItems.setApprovalDate(null);
			dgContractingBulkItemsService.update(dgContractingBulkItems);
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Item Set To Be: Rejected To Bulk Assignment");
			runClientSideExecute("PF('rejectReasonsActionDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejectToSDF(){
		try {
			rejectReasonsIndicator = 3;
			selectedRejectReason = new ArrayList<>();
			rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
			runClientSideExecute("PF('rejectReasonsActionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setRejectToSDF(){
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
			}
			// creates rejection Reasons
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), selectedRejectReason, getSessionUI().getActiveUser(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Rejected);
			dgContractingBulkItems.setBulkApproval(BulkApprovalEnum.FinalToSdf);
			dgContractingBulkItems.setApprovalUser(null);
			dgContractingBulkItems.setApprovalDate(null);
			dgContractingBulkItemsService.update(dgContractingBulkItems);
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Item Set To Be: Rejected To SDF");
			runClientSideExecute("PF('rejectReasonsActionDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepRejectionReasonsUpdate(){
		try {
			rejectReasonsIndicator = 4;
			selectedRejectReason = new ArrayList<>();
			rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
			runClientSideExecute("PF('rejectReasonsActionDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateRejectionReasons(){
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
			}
			// creates rejection Reasons
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), selectedRejectReason, getSessionUI().getActiveUser(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			dgContractingBulkItems = null;
			dgContractingBulkItemsDataModelInfo();
			addInfoMessage("Rejection Reasons Updated");
			runClientSideExecute("PF('rejectReasonsActionDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void bulkApproveBatch(){
		try {
			dgContractingBulkEntryService.bulkApproveActiveContracts(dgContractingBulkEntry, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void bulkRejectBatch(){
		try {
			if (selectedRejectReason.size() == 0) {
				addErrorMessage("Provide Atleast One Rejection Reason Before Proceeding");
			} else {
				dgContractingBulkEntryService.bulkRejectActiveContracts(dgContractingBulkEntry, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Old Version */
	public void prepCompleteWorkflow(){
		try {
			
			provideRejectionReasons = false;
			
			// all items have a status assigned 
			if (countofEntriesWithNoStatusAssigned != 0) {
				throw new Exception("Provide A Status for entries not assigned one");
			}
			
			if (countofEntriesToBeRejected != 0) {
				selectedRejectReason = new ArrayList<>();
				rejectReasonSelectionList = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
				runClientSideExecute("PF('rejectReasonsTwoDlg').show()");
				provideRejectionReasons = true;
			} else {
				completeWorkflow();
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/* Old Version */
	public void completeWorkflow(){
		try {
			if (provideRejectionReasons) {
				if (selectedRejectReason.size() == 0) {
					throw new Exception("Provide Atleast One Rejection Reason Before Proceeding");
				}
			}
			dgContractingBulkEntryService.completeBulkActionByStatusesAssigned(dgContractingBulkEntry, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeBulkWorkflow(){
		try {
			// all items have a status assigned 
			if (countofEntriesWithNoStatusAssigned != 0) {
				throw new Exception("Provide A Status for all entries not assigned");
			}
			dgContractingBulkEntryService.completeBulkActionByStatusesAssignedVersionTwo(dgContractingBulkEntry, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
			if (dgContractingBulkItems == null ) {
				throw new Exception("Unable to locate bulk item, contact support!");
			} else if (dgContractingBulkItems.getActiveContracts() == null) {
				throw new Exception("Unable to locate contract against bulk item, contact support!");
			} else {
				doc = new Doc();
				doc.setTargetKey(dgContractingBulkItems.getActiveContracts().getId());
				doc.setTargetClass(ActiveContracts.class.getName());
				doc.setUsers(getSessionUI().getActiveUser());
				super.runClientSideExecute("PF('dlgSelectReasonWithdrawal').show()");
			}
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
			} else if (dgContractingBulkItems == null) {
				throw new Exception("Unable to locate bulk item entry, contact support!");
			} else {
				// DgContractingBulkItems dgContractingBulkItem, Users sessionUser,DiscretionalWithdrawalAppealEnum withdrawalAppealEnum, Doc doc
				dgContractingBulkEntryService.withdrawBulkActionItem(dgContractingBulkItems, getSessionUI().getActiveUser(), withdrawalAppealEnum, doc);
				dgContractingBulkItems = null;
				dgContractingBulkItemsDataModelInfo();
				addWarningMessage("DG Contract Withdrawn");
				super.runClientSideExecute("PF('dlgSelectReasonWithdrawal').hide()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void testApprovalEmail(){
		try {
			dgContractingBulkEntryService.testEmail(dgContractingBulkItems, getSessionUI().getActiveUser());
			addInfoMessage("Email Sent");
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

	public LazyDataModel<DgContractingBulkItems> getDgContractingBulkItemsDataModel() {
		return dgContractingBulkItemsDataModel;
	}

	public void setDgContractingBulkItemsDataModel(LazyDataModel<DgContractingBulkItems> dgContractingBulkItemsDataModel) {
		this.dgContractingBulkItemsDataModel = dgContractingBulkItemsDataModel;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasons> getRejectReasonSelectionList() {
		return rejectReasonSelectionList;
	}

	public void setRejectReasonSelectionList(List<RejectReasons> rejectReasonSelectionList) {
		this.rejectReasonSelectionList = rejectReasonSelectionList;
	}

	public Integer getCountofEntriesToBeApproved() {
		return countofEntriesToBeApproved;
	}

	public void setCountofEntriesToBeApproved(Integer countofEntriesToBeApproved) {
		this.countofEntriesToBeApproved = countofEntriesToBeApproved;
	}

	public Integer getCountofEntriesToBeRejected() {
		return countofEntriesToBeRejected;
	}

	public void setCountofEntriesToBeRejected(Integer countofEntriesToBeRejected) {
		this.countofEntriesToBeRejected = countofEntriesToBeRejected;
	}

	public Integer getCountofEntriesWithNoStatusAssigned() {
		return countofEntriesWithNoStatusAssigned;
	}

	public void setCountofEntriesWithNoStatusAssigned(Integer countofEntriesWithNoStatusAssigned) {
		this.countofEntriesWithNoStatusAssigned = countofEntriesWithNoStatusAssigned;
	}

	public boolean isCanBulkApprove() {
		return canBulkApprove;
	}

	public void setCanBulkApprove(boolean canBulkApprove) {
		this.canBulkApprove = canBulkApprove;
	}

	public boolean isProvideRejectionReasons() {
		return provideRejectionReasons;
	}

	public void setProvideRejectionReasons(boolean provideRejectionReasons) {
		this.provideRejectionReasons = provideRejectionReasons;
	}

	public Integer getCountofEntriesToBeRejectedBackToSdf() {
		return countofEntriesToBeRejectedBackToSdf;
	}

	public void setCountofEntriesToBeRejectedBackToSdf(Integer countofEntriesToBeRejectedBackToSdf) {
		this.countofEntriesToBeRejectedBackToSdf = countofEntriesToBeRejectedBackToSdf;
	}

	public Integer getCountofEntriesToBeRejectedBackToBulkAssignment() {
		return countofEntriesToBeRejectedBackToBulkAssignment;
	}

	public void setCountofEntriesToBeRejectedBackToBulkAssignment(Integer countofEntriesToBeRejectedBackToBulkAssignment) {
		this.countofEntriesToBeRejectedBackToBulkAssignment = countofEntriesToBeRejectedBackToBulkAssignment;
	}

	public Integer getCountofEntriesWithdrawn() {
		return countofEntriesWithdrawn;
	}

	public void setCountofEntriesWithdrawn(Integer countofEntriesWithdrawn) {
		this.countofEntriesWithdrawn = countofEntriesWithdrawn;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

	public int getRejectReasonsIndicator() {
		return rejectReasonsIndicator;
	}

	public void setRejectReasonsIndicator(int rejectReasonsIndicator) {
		this.rejectReasonsIndicator = rejectReasonsIndicator;
	}

}
