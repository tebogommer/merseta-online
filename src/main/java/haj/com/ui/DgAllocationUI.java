package haj.com.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;

import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContracts;
import haj.com.entity.AllocationChangesReasons;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.datamodel.lookup.AllocationListDatamodel;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.AllocationChange;
import haj.com.entity.lookup.AllocationList;
import haj.com.entity.lookup.DGYear;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.AllocationChangesReasonsService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "dgallocationUI")
@ViewScoped
public class DgAllocationUI extends AbstractUI {

	private ActiveContractsService               activeContractsService            = new ActiveContractsService();
	private Wsp                                  wsp;
	private ProjectImplementationPlanService     projectImplementationPlanServicee = new ProjectImplementationPlanService();
	private LazyDataModel<WspSkillsRequirements> dataModel;
	private DgAllocationService                  dgAllocationService               = new DgAllocationService();
	private List<DgAllocation>                   allocations;
	private List<DgAllocation>                   allocationsGold;
	private List<DgAllocation>                   allocationsSilver;
	private List<DgAllocation>                   allocationsPlatinum;
	private DgAllocation                         allocation;
	private DgAllocationParentService            dgAllocationParentService         = new DgAllocationParentService();
	private DgAllocationParent                   dgAllocationParent;
	private LazyDataModel<AllocationList>        dataModelAllocationList;
	private AllocationChange                     allocationChange;
	private AllocationChangesReasonsService      allocationChangesReasonsService   = new AllocationChangesReasonsService();
	private List<AllocationChangesReasons>       reasons;
	private int                                  activeIndex;
	private Double                               total                             = 0.0;
	private Double                               totalAwardAmount                  = 0.0;
	private Double                               totalRecommendedAmount            = 0.0;
	private Double                               totalAwardAmountCofund            = 0.0;
	private Double                               totalAwardAmountDisabled          = 0.0;
	private Double                               totalRequestedAmount              = 0.0;
	private BigDecimal                           maxAlloccation                    = BigDecimal.ZERO;
	private BigDecimal                           totalAllocated                    = BigDecimal.ZERO;
	private BigDecimal                           totalRequested                    = BigDecimal.ZERO;
	private Doc                                  doc;
	private boolean                              requestChange;

	private CompanyUsersService companyUsersService        = new CompanyUsersService();
	private Users               userSelectionForMoaSignOff = null;
	private List<Users>         userSelectionList          = null;

	private Integer currentFinYearUsed = HAJConstants.DG_ALLOCATION_FOCUS_YEAR;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		selectedRejectReason = new ArrayList<>();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_ALLOCATION) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			dgAllocationParent = dgAllocationParentService.findByKey(getSessionUI().getTask().getTargetKey());
			if (getSessionUI().getTask().getProcessRole() != null) allocations = dgAllocationService.findBySuperParent(dgAllocationParent);
			else allocations = dgAllocationService.findByParent(dgAllocationParent);
			findTotals();
		} else {
			findTotals();
			onTabChange(null);
		}
	}

	public void findAllocationReasons() {
		try {
			reasons = allocationChangesReasonsService.findDGAllocationChangesReasons(dgAllocationParent);
			findRejectReasons();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void findTotals() throws Exception {
		DGYear dgYear = null;
		if (allocations != null && !allocations.isEmpty()) {
			for (DgAllocation dgAllocation : allocations) {
				dgYear = dgAllocation.getMandatoryGrant().getWsp().getDgYear();
				break;
			}
		}
		if (dgYear != null) {
			maxAlloccation = dgAllocationService.findMaxAllocationAmount(dgYear);
			totalAllocated = dgAllocationService.findTotalAllocated(dgYear, null);
			totalRequested = dgAllocationService.findTotalRequested(dgYear);
		} else {
			maxAlloccation = dgAllocationService.findMaxAllocationAmount(currentFinYearUsed);
			totalAllocated = dgAllocationService.findTotalAllocated(currentFinYearUsed, null);
			totalRequested = dgAllocationService.findTotalRequested(currentFinYearUsed);
		}
	}

	public void onTabChange(TabChangeEvent a) {
		try {
			Long finYear = 0l;
			getSessionUI().setTask(null);
			if (getNow().before(GenericUtility.getEndOfApril(getNow()))) {
				Calendar now = Calendar.getInstance();
				finYear = Long.valueOf(now.get(Calendar.YEAR) + 1);
			} else {
				Calendar now = Calendar.getInstance();
				finYear = Long.valueOf(now.get(Calendar.YEAR) + 1);
			}
			switch (activeIndex) {
				case 0:
					// dataModelAllocationList = new AllocationListDatamodel(true);
					dataModelAllocationList = new AllocationListDatamodel(true, currentFinYearUsed);
					break;
				case 1:
					findAllocations();
					super.runClientSideExecute("showSummary()");
					break;
				case 2:
					findAllocations();

					break;
				case 3:
					findAllocations();

					break;
				case 4:
					findAllocations();

					break;
				case 5:
					// wspSkillsRequirementsAll =
					// wspSkillsRequirementsService.findByYear(finYear.intValue());

					break;
				case 6:
					allocations = dgAllocationService.allDgAllocation();
					break;

				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findAllocations() throws Exception {
		allocations         = dgAllocationService.allDgAllocationNotStatus();
		allocationsGold     = new ArrayList<>();
		allocationsPlatinum = new ArrayList<>();
		allocationsSilver   = new ArrayList<>();
		for (DgAllocation dgAllocation : allocations) {
			switch (dgAllocation.getDgAllocationParent().getCompanyCategorization()) {
				case Gold:
					allocationsGold.add(dgAllocation);

					break;

				case Platinum:
					allocationsPlatinum.add(dgAllocation);

					break;

				case Silver:
					allocationsSilver.add(dgAllocation);
					break;

				default:
					break;
			}
		}
	}

	public void doAllocation() {
		try {
			dgAllocationService = new DgAllocationService();
			dgAllocationService.checkForAllocationWithException(wsp);
			allocations = dgAllocationService.allDgAllocationNotStatus();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		this.wsp = null;
	}

	public void onSummaryRow(Object filter) {
		Long id = (Long) filter;
		total                    = 0.0;
		totalAwardAmount         = 0.0;
		totalAwardAmountCofund   = 0.0;
		totalAwardAmountDisabled = 0.0;
		totalRecommendedAmount   = 0.0;
		totalRequestedAmount     = 0.0;

		if (allocations != null && !allocations.isEmpty()) {
			this.allocations.stream().filter(a -> a.getId() != null && (a.getDgAllocationParent().getId() == id || a.getMandatoryGrant().getWsp().getId() == id)).forEach(a -> {
				total                    += a.getTotalAwardAmount() == null ? 0.0 : a.getTotalAwardAmount().doubleValue();
				totalAwardAmount         += a.getAwardAmount() == null ? 0.0 : a.getAwardAmount().doubleValue();
				totalAwardAmountCofund   += a.getCoFundingAwardAmount() == null ? 0.0 : a.getCoFundingAwardAmount().doubleValue();
				totalRequestedAmount     += a.getRequestedAmount() == null ? 0.0 : a.getRequestedAmount().doubleValue();
				totalRecommendedAmount   += a.getRecommendedAmount() == null ? 0.0 : a.getRecommendedAmount().doubleValue();
				totalAwardAmountDisabled += a.getDisabledGrantAmount() == null ? 0.0 : a.getDisabledGrantAmount().doubleValue();
			});
		}

	}

	public void rejectAllocation() {
		try {
			if (getSessionUI().getTask() == null && selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			dgAllocationService.rejectAllocation(dgAllocationParent, getSessionUI().getActiveUser());
			if (selectedRejectReason.size() > 0) rejectReasonsService.createReject(selectedRejectReason, DgAllocationParent.class.getName(), dgAllocationParent.getId(), null, "");
			findNewAllocations();
			findTotals();
			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void findNewAllocations() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_ALLOCATION) {
			dgAllocationParent = dgAllocationParentService.findByKey(getSessionUI().getTask().getTargetKey());
			if (getSessionUI().getTask().getProcessRole() != null) allocations = dgAllocationService.findBySuperParent(dgAllocationParent);
			else allocations = dgAllocationService.findByParent(dgAllocationParent);
		} else {
			allocations = dgAllocationService.allDgAllocationNotStatus();
		}

	}

	public void saveChange() throws Exception {
		try {
			dgAllocationService.create(allocation);
			findNewAllocations();
			findTotals();
			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveAllocation() {
		try {
			dgAllocationService.approveAllocation(dgAllocationParent, getSessionUI().getActiveUser());
			findNewAllocations();
			findTotals();
			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveAllocationLineItem() throws Exception {
		try {
			dgAllocationService.approveAllocationLineItem(allocation, getSessionUI().getActiveUser());
			findNewAllocations();
			findTotals();
			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectAllocationLineItem() throws Exception {
		try {
			dgAllocationService.rejectAllocationLineItem(allocation, getSessionUI().getActiveUser());
			findNewAllocations();
			findTotals();
			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void requesteWorkflow() {
		try {
			findTotals();
			if (this.totalAllocated.compareTo(maxAlloccation) != 1) {
				dgAllocationService.requesteWorkflow(allocations, getSessionUI().getActiveUser());
				findNewAllocations();
			} else {
				addErrorMessage("Allcoated amount is higher than total allocation amount");
			}

			// ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepAcceptMoa() {
		try {
			userSelectionList          = companyUsersService.findDistinctUsersByCompany(dgAllocationParent.getWsp().getCompany());
			userSelectionForMoaSignOff = null;
			runClientSideExecute("PF('acceptMoaDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * This version now includes the sign off process for sign off
	 */
	public void completeWorkflowVersionTwo() {
		try {
			boolean sendEmailNotConfrimedNotification = false;
			if (userSelectionForMoaSignOff == null || userSelectionForMoaSignOff.getId() == null) {
				throw new Exception("Select User Required For Sign Off of MOA");
			}
			if (userSelectionForMoaSignOff.getStatus() != UsersStatusEnum.Active || userSelectionForMoaSignOff.getLastLogin() == null) {
				if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.InActive) {
					throw new Exception("Selected User Is In-Active, Please Select A Different User For Sign Off");
				} else if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.EmailNotConfrimed) {
					sendEmailNotConfrimedNotification = true;
					// throw new Exception("Selected User Has Not Confirmed Their Email Address, Please Select A Different User For Sign Off");
				} else {
					throw new Exception("Selected User Has Not Completed First Time Log In, Please Select A Different User For Sign Off");
				}
			}
			dgAllocationService.mailValidiations(dgAllocationParent);
			dgAllocationService.completeWorkflow(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), true);
			if (dgAllocationParent.getWsp() != null) {
				ActiveContracts ac = activeContractsService.requesteWorkflowVersionTwo(dgAllocationParent, getSessionUI().getActiveUser(), userSelectionForMoaSignOff);
				projectImplementationPlanServicee.generateImpementationPlan(dgAllocationParent.getWsp(), ac);
				dgAllocationService.sendNotificationMOA(dgAllocationParent, getSessionUI().getActiveUser(), true);
				if (sendEmailNotConfrimedNotification) {
					dgAllocationService.sendEmailNotConfirmNotification(dgAllocationParent, userSelectionForMoaSignOff);
				}
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			} else {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeWorkflow() {
		try {
			dgAllocationService.completeWorkflow(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), true);
			if (dgAllocationParent.getWsp() != null) {
				ActiveContracts ac = activeContractsService.requesteWorkflow(dgAllocationParent, getSessionUI().getActiveUser());
				projectImplementationPlanServicee.generateImpementationPlan(dgAllocationParent.getWsp(), ac);
				ajaxRedirectToDashboard();
			} else {
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// should not be used
	public void requestHigherallocation() {
		try {
			dgAllocationService.requestHigherallocation(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), allocationChange);
			if (dgAllocationParent.getWsp() != null) {
				ActiveContracts ac = activeContractsService.requesteWorkflow(dgAllocationParent, getSessionUI().getActiveUser());
				projectImplementationPlanServicee.generateImpementationPlan(dgAllocationParent.getWsp(), ac);
				ajaxRedirectToDashboard();
			} else {
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepAcceptMoaHigherAllocation() {
		try {
			userSelectionList          = companyUsersService.findDistinctUsersByCompany(dgAllocationParent.getWsp().getCompany());
			userSelectionForMoaSignOff = null;
			runClientSideExecute("PF('acceptMoaHiggerAllDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// latest version
	public void requestHigherallocationVersionTwo() {
		try {
			boolean sendEmailNotConfrimedNotification = false;
			if (userSelectionForMoaSignOff == null || userSelectionForMoaSignOff.getId() == null) {
				throw new Exception("Select User Required For Sign Off of MOA");
			}
			if (userSelectionForMoaSignOff.getStatus() != UsersStatusEnum.Active || userSelectionForMoaSignOff.getLastLogin() == null) {
				if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.InActive) {
					throw new Exception("Selected User Is In-Active, Please Select A Different User For Sign Off");
				} else if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.EmailNotConfrimed) {
					sendEmailNotConfrimedNotification = true;
					// throw new Exception("Selected User Has Not Confirmed Their Email Address, Please Select A Different User For Sign Off");
				} else {
					throw new Exception("Selected User Has Not Completed First Time Log In, Please Select A Different User For Sign Off");
				}
			}
			dgAllocationService.mailValidiations(dgAllocationParent);
			dgAllocationService.requestHigherallocationVersionTwo(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), allocationChange);
			if (dgAllocationParent.getWsp() != null) {
				ActiveContracts ac = activeContractsService.requesteWorkflowVersionTwo(dgAllocationParent, getSessionUI().getActiveUser(), userSelectionForMoaSignOff);
				projectImplementationPlanServicee.generateImpementationPlan(dgAllocationParent.getWsp(), ac);
				// active contract notification
				dgAllocationService.requestHigherallocationVersionTwoNotification(dgAllocationParent, getSessionUI().getActiveUser(), allocationChange, ac);
				if (sendEmailNotConfrimedNotification) {
					dgAllocationService.sendEmailNotConfirmNotification(dgAllocationParent, userSelectionForMoaSignOff);
				}
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			} else {
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void resetAllocation() {
		try {
			dgAllocationService.resetAllocation(dgAllocationParent, allocationChange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveChangeAllocation() {
		try {
			dgAllocationService.approveChangeAllocation(allocations, dgAllocationParent, allocationChange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void switchRequestChangeBool() {
		this.requestChange = !requestChange;
		if (!requestChange) {
			for (DgAllocation dgAllocation : allocations) {
				dgAllocation.setChangeAllocationLearners(null);
			}
		}
	}

	public void requestHigherAllocation() {
		try {
			dgAllocationService.requestHigherAllocation(dgAllocationParent, allocationChange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			dgAllocationService.approveWorkflow(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			if (getSessionUI().getTask() == null && selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			if (getSessionUI().getTask().getFirstInProcess() != null && getSessionUI().getTask().getFirstInProcess()) {
				dgAllocationService.resetAllocations(dgAllocationParent, allocations, getSessionUI().getActiveUser(), getSessionUI().getTask());
			} else {
				dgAllocationService.rejectWorkflow(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			}
			List<DgAllocationParent> parents = allocations.stream().map(al -> al.getDgAllocationParent()).distinct().collect(Collectors.toList());
			for (DgAllocationParent dgAllocation : parents) {
				rejectReasonsService.createReject(selectedRejectReason, DgAllocationParent.class.getName(), dgAllocation.getId(), null, "");
			}
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			dgAllocationService.finalApproveWorkflow(dgAllocationParent, allocations, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			dgAllocationService.finalRejectWorkflow(dgAllocationParent, allocations, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void changeAllocation() throws Exception {
		try {
			boolean changeMade = false;
			for (DgAllocation dgAllocation : allocations) {
				if (dgAllocation.getChangeAllocationLearners() != null) {
					changeMade = true;
					break;
				}
			}
			if (!changeMade) {
				throw new Exception("You have not changed any of the interventions");
			}
			dgAllocationParent.setAlreadyRequested(true);
			dgAllocationParent.setDontAllocate(true);
			dgAllocationService.changeAllocation(dgAllocationParent, allocationChange, getSessionUI().getActiveUser(), getSessionUI().getTask());
			for (DgAllocation dgAllocation : allocations) {
				dgAllocationService.create(dgAllocation);
			}
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void sendBackToSDFToChangeAlteredLearners() {
		try {
			dgAllocationParent.setAlreadyRequested(null);
			dgAllocationParent.setDontAllocate(null);
			dgAllocationService.sendBackToSDFToChangeAlteredLearners(dgAllocationParent, allocations, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectChange() throws Exception {
		try {
			dgAllocationService.rejectChange(dgAllocationParent, allocations, getSessionUI().getActiveUser(), getSessionUI().getTask());
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
				doc.setTargetKey(dgAllocationParent.getId());
				doc.setTargetClass(DgAllocationParent.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private RejectReasons rejectReason;

	private List<RejectReasons> selectedRejectReason;

	private List<RejectReasonsChild> rejectReasonsChild;

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();

	public void findRejectReasons() {
		try {
			this.rejectReasonsChild = rejectReasonsService.findByTargetClassAndKey(DgAllocationParent.class.getName(), dgAllocationParent.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons>  l                    = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.DG_ALLOCATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public LazyDataModel<WspSkillsRequirements> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspSkillsRequirements> dataModel) {
		this.dataModel = dataModel;
	}

	public List<DgAllocation> getAllocations() {
		return allocations;
	}

	public void setAllocations(List<DgAllocation> allocations) {
		this.allocations = allocations;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalAwardAmount() {
		return totalAwardAmount;
	}

	public void setTotalAwardAmount(Double totalAwardAmount) {
		this.totalAwardAmount = totalAwardAmount;
	}

	public Double getTotalAwardAmountCofund() {
		return totalAwardAmountCofund;
	}

	public void setTotalAwardAmountCofund(Double totalAwardAmountCofund) {
		this.totalAwardAmountCofund = totalAwardAmountCofund;
	}

	public Double getTotalRequestedAmount() {
		return totalRequestedAmount;
	}

	public void setTotalRequestedAmount(Double totalRequestedAmount) {
		this.totalRequestedAmount = totalRequestedAmount;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	/**
	 * @return the dgAllocationParent
	 */
	public DgAllocationParent getDgAllocationParent() {
		return dgAllocationParent;
	}

	/**
	 * @param dgAllocationParent
	 * the dgAllocationParent to set
	 */
	public void setDgAllocationParent(DgAllocationParent dgAllocationParent) {
		this.dgAllocationParent = dgAllocationParent;
	}

	/**
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * @param doc
	 * the doc to set
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * @return the totalRecommendedAmount
	 */
	public Double getTotalRecommendedAmount() {
		return totalRecommendedAmount;
	}

	/**
	 * @param totalRecommendedAmount
	 * the totalRecommendedAmount to set
	 */
	public void setTotalRecommendedAmount(Double totalRecommendedAmount) {
		this.totalRecommendedAmount = totalRecommendedAmount;
	}

	/**
	 * @return the totalAwardAmountDisabled
	 */
	public Double getTotalAwardAmountDisabled() {
		return totalAwardAmountDisabled;
	}

	/**
	 * @param totalAwardAmountDisabled
	 * the totalAwardAmountDisabled to set
	 */
	public void setTotalAwardAmountDisabled(Double totalAwardAmountDisabled) {
		this.totalAwardAmountDisabled = totalAwardAmountDisabled;
	}

	/**
	 * @return the allocation
	 */
	public DgAllocation getAllocation() {
		return allocation;
	}

	/**
	 * @param allocation
	 * the allocation to set
	 */
	public void setAllocation(DgAllocation allocation) {
		this.allocation = allocation;
	}

	/**
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * @param wsp
	 * the wsp to set
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * @return the allocationsGold
	 */
	public List<DgAllocation> getAllocationsGold() {
		return allocationsGold;
	}

	/**
	 * @param allocationsGold
	 * the allocationsGold to set
	 */
	public void setAllocationsGold(List<DgAllocation> allocationsGold) {
		this.allocationsGold = allocationsGold;
	}

	/**
	 * @return the allocationsSilver
	 */
	public List<DgAllocation> getAllocationsSilver() {
		return allocationsSilver;
	}

	/**
	 * @param allocationsSilver
	 * the allocationsSilver to set
	 */
	public void setAllocationsSilver(List<DgAllocation> allocationsSilver) {
		this.allocationsSilver = allocationsSilver;
	}

	/**
	 * @return the allocationsPlatinum
	 */
	public List<DgAllocation> getAllocationsPlatinum() {
		return allocationsPlatinum;
	}

	/**
	 * @param allocationsPlatinum
	 * the allocationsPlatinum to set
	 */
	public void setAllocationsPlatinum(List<DgAllocation> allocationsPlatinum) {
		this.allocationsPlatinum = allocationsPlatinum;
	}

	/**
	 * @return the maxAlloccation
	 */
	public BigDecimal getMaxAlloccation() {
		return maxAlloccation;
	}

	/**
	 * @param maxAlloccation
	 * the maxAlloccation to set
	 */
	public void setMaxAlloccation(BigDecimal maxAlloccation) {
		this.maxAlloccation = maxAlloccation;
	}

	/**
	 * @return the totalAllocated
	 */
	public BigDecimal getTotalAllocated() {
		return totalAllocated;
	}

	/**
	 * @param totalAllocated
	 * the totalAllocated to set
	 */
	public void setTotalAllocated(BigDecimal totalAllocated) {
		this.totalAllocated = totalAllocated;
	}

	/**
	 * @return the dataModelAllocationList
	 */
	public LazyDataModel<AllocationList> getDataModelAllocationList() {
		return dataModelAllocationList;
	}

	/**
	 * @param dataModelAllocationList
	 * the dataModelAllocationList to set
	 */
	public void setDataModelAllocationList(LazyDataModel<AllocationList> dataModelAllocationList) {
		this.dataModelAllocationList = dataModelAllocationList;
	}

	/**
	 * @return the totalRequested
	 */
	public BigDecimal getTotalRequested() {
		return totalRequested;
	}

	/**
	 * @param totalRequested
	 * the totalRequested to set
	 */
	public void setTotalRequested(BigDecimal totalRequested) {
		this.totalRequested = totalRequested;
	}

	/**
	 * @return the allocationChange
	 */
	public AllocationChange getAllocationChange() {
		return allocationChange;
	}

	/**
	 * @param allocationChange
	 * the allocationChange to set
	 */
	public void setAllocationChange(AllocationChange allocationChange) {
		this.allocationChange = allocationChange;
	}

	/**
	 * @return the reasons
	 */
	public List<AllocationChangesReasons> getReasons() {
		return reasons;
	}

	/**
	 * @param reasons
	 * the reasons to set
	 */
	public void setReasons(List<AllocationChangesReasons> reasons) {
		this.reasons = reasons;
	}

	/**
	 * @return the rejectReason
	 */
	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason
	 * the rejectReason to set
	 */
	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the selectedRejectReason
	 */
	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	/**
	 * @param selectedRejectReason
	 * the selectedRejectReason to set
	 */
	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	/**
	 * @return the rejectReasonsChild
	 */
	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	/**
	 * @param rejectReasonsChild
	 * the rejectReasonsChild to set
	 */
	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	/**
	 * @return the requestChange
	 */
	public boolean isRequestChange() {
		return requestChange;
	}

	/**
	 * @param requestChange
	 * the requestChange to set
	 */
	public void setRequestChange(boolean requestChange) {
		this.requestChange = requestChange;
	}

	public List<Users> getUserSelectionList() {
		return userSelectionList;
	}

	public void setUserSelectionList(List<Users> userSelectionList) {
		this.userSelectionList = userSelectionList;
	}

	public Users getUserSelectionForMoaSignOff() {
		return userSelectionForMoaSignOff;
	}

	public void setUserSelectionForMoaSignOff(Users userSelectionForMoaSignOff) {
		this.userSelectionForMoaSignOff = userSelectionForMoaSignOff;
	}

}
