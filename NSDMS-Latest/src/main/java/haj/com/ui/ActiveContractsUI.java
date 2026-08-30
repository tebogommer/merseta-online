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
import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Signoff;
import haj.com.entity.Users;
import haj.com.service.ActiveContractsService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgAllocationService;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.entity.datamodel.ActiveContractsDatamodel;
import haj.com.entity.datamodel.ActiveContractsGGTaskStatusDatamodel;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.RejectReasons;

import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ancientprogramming.fixedformat4j.format.ParseException;

import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "activecontractsUI")
@ViewScoped
public class ActiveContractsUI extends AbstractUI {

	private ActiveContractsService           service                       = new ActiveContractsService();
	private List<ActiveContracts>            activecontractsList           = null;
	private List<ActiveContracts>            activecontractsfilteredList   = null;
	private ActiveContracts                  activecontracts               = null;
	private LazyDataModel<ActiveContracts>   dataModel;
	private LazyDataModel<ActiveContracts>   taskStatusDataModel;
	private Doc                              doc;
	private ActiveContractDetail             activeContractDetail;
	private List<ProjectImplementationPlan>  projectimplementationplanList = null;
	private ProjectImplementationPlanService implementationPlanService     = new ProjectImplementationPlanService();
	private ArrayList<RejectReasons>         selectedRejectReason          = new ArrayList<>();
	private List<RejectReasons>              rejectReasonsList             = null;
	private RejectReasonsService             rejectReasonsService          = new RejectReasonsService();

	private Date projectedRecruitmentMinDate;
	private Date projectedRecruitmentMaxDate;

	private Date requitmentInductionMaxDate;
	private Date requitmentInductionMinDate;

	private Date minProjectedRecruitmentDate;
	private Date maxProjectedRecruitmentDate;

	private Date minEstimatedCompletionDate;
	private Date maxEstimatedCompletionDate;

	private String projectedRegistrationDisplayDate;

	/* Sign off functionality Start */

	/* Entity levels */
	private Users   userSelectionForMoaSignOff = null;
	private Users   currentSignOffUser         = null;
	private Signoff signOffSelected            = null;

	/* Service levels */
	private SignoffService      signoffService      = new SignoffService();
	private UsersService        usersService        = new UsersService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private DgAllocationService dgAllocationService;

	/* Arrays Lists */
	private List<Signoff> signOffMoaList    = null;
	private List<Users>   userSelectionList = null;

	private Integer password       = null;
	private boolean displaySignOff = false;

	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;

	/* Sign off functionality End */

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
	 * Initialize method to get all ActiveContracts and prepare a for a create of a new ActiveContracts
	 * @author TechFinium
	 * @see ActiveContracts
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SPECIAL_PROJECTS)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.activecontracts = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(getSessionUI().getTask(), getSessionUI().getTask().getWorkflowProcess(), activecontracts, getSessionUI().getTask().getProcessRole());
			if (activecontracts.getDgAllocationParent() != null) projectimplementationplanList = implementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(activecontracts.getDgAllocationParent().getWsp());
			populateRejectReasonsAssigned();
		} else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT_EC) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.activecontracts = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(getSessionUI().getTask(), getSessionUI().getTask().getWorkflowProcess(), activecontracts, getSessionUI().getTask().getProcessRole());
			if (activecontracts.getDgAllocationParent() != null) {
				projectimplementationplanList = implementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(activecontracts.getDgAllocationParent().getWsp());
			}
			populateRejectReasonsAssigned();
			// populate sign offs required
			populateSignOffs();
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
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsInfo() {
		dataModel = new ActiveContractsDatamodel();
	}

	/**
	 * Get all ActiveContracts for data table
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void dgAllocationTaskStatusInfo() {
		taskStatusDataModel = new ActiveContractsGGTaskStatusDatamodel();
	}

	private void populateSignOffs() throws Exception {
		signOffMoaList = signoffService.findByTargetKeyAndClass(activecontracts.getId(), activecontracts.getClass().getName());
	}

	/**
	 * Insert ActiveContracts into database
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void activecontractsInsert() {
		try {
			activecontracts.setAccrualraised(0.0);
			service.requesteWorkflow(this.activecontracts, getSessionUI().getActiveUser());
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
			year = activecontracts.getDgAllocationParent().getWsp().getFinYearNonNull() - 1;
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
		if (activecontracts != null && activecontracts.getProjectedRegistrationDateStart() != null && activecontracts.getProjectedRegistrationDateEnd() != null) {
			projectedRegistrationDisplayDate = "Between " + HAJConstants.sdfDDMMMMYYYY.format(activecontracts.getProjectedRegistrationDateStart()) + " and " + HAJConstants.sdfDDMMMMYYYY.format(activecontracts.getProjectedRegistrationDateEnd());
			minProjectedRecruitmentDate      = activecontracts.getProjectedRegistrationDateStart();
			maxProjectedRecruitmentDate      = activecontracts.getProjectedRegistrationDateEnd();
		} else {
			projectedRegistrationDisplayDate = "Between 1 January " + year + " and 31 March " + yearPlus;
		}
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
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void prepareNew() {
		activecontracts = new ActiveContracts();
	}

	/*
	 * public List<SelectItem> getActiveContractsDD() { List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------")); activecontractsInfo(); for (ActiveContracts ug : activecontractsList) { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 * @param desc
	 * the desc
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
			if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SPECIAL_PROJECTS)) {
				service.completeWorkflow(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), signOffMoaList);
				if (projectimplementationplanList != null && !projectimplementationplanList.isEmpty()) implementationPlanService.create(projectimplementationplanList);
			} else if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_CONTRACT_EC) {
				if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
					extensionTerminationUnderwayValidiation();
				}
				service.completeWorkflowES(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), signOffMoaList);
				if (projectimplementationplanList != null && !projectimplementationplanList.isEmpty()) implementationPlanService.create(projectimplementationplanList);
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			service.approveWorkflow(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void extensionTerminationUnderwayValidiation() throws Exception {
		if (activecontracts.getExtensionTerminationWorkflowActive() != null && activecontracts.getExtensionTerminationWorkflowActive()) {
			throw new Exception("Unable to proceed, please await notification on outcome");
		}
	}

	public void rejectWorkflow() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select rejection reason(s)");
			}
			service.rejectWorkflow(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			service.finalApproveWorkflow(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
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

			service.finalRejectWorkflow(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
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

			service.finalRejectWorkflowAndTask(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
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
		List<RejectReasons>  l                    = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.DG_CONTRACT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getRejectReasonsSeniorManager() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		Boolean              booleanValue         = true;
		List<RejectReasons>  l                    = null;
		try {
			l = rejectReasonsService.findByProcessSeniorManager(ConfigDocProcessEnum.DG_CONTRACT, booleanValue);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void terminateProject() {
		// Kicks off project termination workflow by creating a project termination task...
		try {
			// service.submitProjectTermination(activecontracts, getSessionUI().getActiveUser());
			service.submitProjectTerminationVersionTwo(activecontracts, getSessionUI().getActiveUser());
			addInfoMessage("Project terminiation for " + activecontracts.getRefnoAuto() + " requested");
			prepareNew();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void resendSignOffPin() {
		try {
			if (getSessionUI().getActiveUser().getId().equals(signOffSelected.getUser().getId())) {
				service.sendOneTimePinForSignOffEmailNotification(activecontracts, signOffSelected);
				populateSignOffs();
				addInfoMessage("A New OTP Has Been Generated And Sent");
			} else {
				addErrorMessage("You do not have the permission to re-send OTP");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepPinValidiation() {
		try {
			if (getSessionUI().getActiveUser().getId().equals(signOffSelected.getUser().getId())) {
				password = null;
				runClientSideExecute("PF('signOffDlg').show()");
				displaySignOff = false;
			} else {
				addErrorMessage("You do not have permission to sign off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void validiatePin() {
		try {
			if (service.validatePinExpiryDate(signOffSelected)) {
				if (service.validatePin(signOffSelected, password)) {
					displaySignOff = true;
				} else {
					displaySignOff = false;
					password       = null;
					addErrorMessage("Incorrect OTP Provided");
				}
			} else {
				displaySignOff = false;
				password       = null;
				addErrorMessage("OTP Expired, please resend a new OTP");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void signOff() {
		try {
			if (signOffSelected.getAccept()) {
				signOffSelected.setSignOffDate(getNow());
				signoffService.update(signOffSelected);
				addInfoMessage("Sign Off Complete");
				runClientSideExecute("PF('signOffDlg').hide()");
				displaySignOff = false;
				password       = null;
				populateSignOffs();
			} else {
				addErrorMessage("Please Accept Acknowledgement Before Signing Off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepChangeSignOffUser() {
		try {
			userSelectionList = companyUsersService.findDistinctUsersByCompany(activecontracts.getDgAllocationParent().getWsp().getCompany());
			if (signOffSelected.getUser() != null && signOffSelected.getUser().getId() != null) {
				currentSignOffUser = usersService.findByKey(signOffSelected.getUser().getId());
			}
			userSelectionForMoaSignOff = null;
			runClientSideExecute("PF('changeSignOffUserDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void changeSignOffUser() {
		try {
			if (userSelectionForMoaSignOff != null) {
				// validate sign off
				boolean sendEmailNotConfrimedNotification = false;
				if (userSelectionForMoaSignOff.getStatus() != UsersStatusEnum.Active || userSelectionForMoaSignOff.getLastLogin() == null) {
					if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.InActive) {
						throw new Exception("Selected User Is In-Active, Please Select A Different User For Sign Off");
					} else if (userSelectionForMoaSignOff.getStatus() == UsersStatusEnum.EmailNotConfrimed) {
						sendEmailNotConfrimedNotification = true;
					} else {
						throw new Exception("Selected User Has Not Completed First Time Log In, Please Select A Different User For Sign Off");
					}
				}

				signOffSelected.setAccept(false);
				signOffSelected.setSignOffDate(null);
				signOffSelected.setUser(userSelectionForMoaSignOff);
				signOffSelected.setDateSignOffUserChanged(getNow());
				signOffSelected.setExpiryDate(null);
				signOffSelected.setOneTimePassword(null);
				signOffSelected.setChangeUser(getSessionUI().getActiveUser());
				signoffService.update(signOffSelected);
				if (sendEmailNotConfrimedNotification) {
					if (dgAllocationService == null) {
						dgAllocationService = new DgAllocationService();
					}
					dgAllocationService.sendEmailNotConfirmNotification(activecontracts.getDgAllocationParent(), userSelectionForMoaSignOff);
				}
				addInfoMessage("Sign Off User Assigned");
				runClientSideExecute("PF('changeSignOffUserDlg').hide()");
			} else {
				addErrorMessage("Select A User For MOA Sign Off");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void goToCompanyManagment() {
		try {
			getSessionUI().setTask(null);
			ajaxRedirect("/pages/externalparty/companymanagement.jsf");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeSignOffTask() {
		try {
			boolean signedOff = false;
			for (Signoff signoff : signOffMoaList) {
				if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getAccept() != null && signoff.getAccept())) {
					signedOff = true;
				}
			}
			if (signedOff) {
				if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
					extensionTerminationUnderwayValidiation();
				}
				service.completeSignOff(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), signOffMoaList);
				if (projectimplementationplanList != null && !projectimplementationplanList.isEmpty()) implementationPlanService.create(projectimplementationplanList);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			} else {
				throw new Exception("Please Sign Off Before Submission");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * Sends back to the SDF user with clearing sign off user removed for re-assignment
	 */
	public void sendBackToSDF() {
		try {
			service.sendBackToSdf(activecontracts, getSessionUI().getActiveUser(), getSessionUI().getTask(), signOffMoaList);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepWithdrawOfApplication() {
		try {
			doc = new Doc();
			doc.setTargetKey(activecontracts.getId());
			doc.setTargetClass(ActiveContracts.class.getName());
			doc.setUsers(getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepExtensionRequest() {
		try {
			doc = new Doc();
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

	public void withdrawActiveContractsCompanyContact() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			if (withdrawalAppealEnum == null) {
				addErrorMessage("Select A Reason Before Proceeding");
			} else {
				if (doc == null) {
					addErrorMessage("Upload Evidance Before Proceeding");
				} else {
					service.withdrawActiveContracts(activecontracts, getSessionUI().getActiveUser(), withdrawalAppealEnum, getSessionUI().getTask(), doc);
					getSessionUI().setTask(null);
					ajaxRedirectToDashboard();
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void requestExtensionOnActiveContract() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			if (doc == null) {
				addErrorMessage("Upload Evidence Before Proceeding");
			} else {
				service.requestExtensionOfContract(activecontracts, getSessionUI().getActiveUser(), doc);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void requestTerminationOnActiveContract() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			if (doc == null) {
				addErrorMessage("Upload Evidence Before Proceeding");
			} else {
				service.requestTerminationOfContract(activecontracts, getSessionUI().getActiveUser(), doc);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void withdrawActiveContractsEmployee() {
		try {
			if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
				extensionTerminationUnderwayValidiation();
			}
			if (withdrawalAppealEnum == null) {
				addErrorMessage("Select A Reason Before Proceeding");
			} else {
				service.withdrawActiveContracts(activecontracts, getSessionUI().getActiveUser(), withdrawalAppealEnum, getSessionUI().getTask(), doc);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void downloadMoaVersionTwo() {
		try {
			service.downloadMoaVersionTwo(activecontracts, true);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
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
	 * @author TechFinium
	 * @param activecontractsfilteredList
	 * the new activecontractsfilteredList list
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
	 * the doc to set
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
	 * the activeContractDetail to set
	 */
	public void setActiveContractDetail(ActiveContractDetail activeContractDetail) {
		this.activeContractDetail = activeContractDetail;
	}

	/**
	 * @return the projectimplementationplanList
	 */
	public List<ProjectImplementationPlan> getProjectimplementationplanList() {
		return projectimplementationplanList;
	}

	/**
	 * @param projectimplementationplanList
	 * the projectimplementationplanList to set
	 */
	public void setProjectimplementationplanList(List<ProjectImplementationPlan> projectimplementationplanList) {
		this.projectimplementationplanList = projectimplementationplanList;
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
	 * the requitmentInductionMaxDate to set
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
	 * the taskStatusDataModel to set
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
	 * the projectedRegistrationDisplayDate to set
	 */
	public void setProjectedRegistrationDisplayDate(String projectedRegistrationDisplayDate) {
		this.projectedRegistrationDisplayDate = projectedRegistrationDisplayDate;
	}

	public List<Signoff> getSignOffMoaList() {
		return signOffMoaList;
	}

	public void setSignOffMoaList(List<Signoff> signOffMoaList) {
		this.signOffMoaList = signOffMoaList;
	}

	public Signoff getSignOffSelected() {
		return signOffSelected;
	}

	public void setSignOffSelected(Signoff signOffSelected) {
		this.signOffSelected = signOffSelected;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public boolean isDisplaySignOff() {
		return displaySignOff;
	}

	public void setDisplaySignOff(boolean displaySignOff) {
		this.displaySignOff = displaySignOff;
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

	public Users getCurrentSignOffUser() {
		return currentSignOffUser;
	}

	public void setCurrentSignOffUser(Users currentSignOffUser) {
		this.currentSignOffUser = currentSignOffUser;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

}
