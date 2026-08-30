package haj.com.wsp.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.ATRReportSummayBean;
import haj.com.bean.DGApplicationSummaryBean;
import haj.com.bean.EmpEmploymentStatusBean;
import haj.com.bean.EmployeeEquityProfileBean;
import haj.com.bean.EmployeeProfileBean;
import haj.com.bean.PivotalTrainingReportBean;
import haj.com.bean.WSPReportSummayBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.ImpactOfStaffTraining;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Sites;
import haj.com.entity.Tasks;
import haj.com.entity.TempSignoff;
import haj.com.entity.TrainingComittee;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.entity.WspCompanyHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.entity.WspDispute;
import haj.com.entity.WspRejectionInformation;
import haj.com.entity.WspSdfCompanyHistory;
import haj.com.entity.WspSignoff;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.GrantOfoSelection;
import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Title;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DgVerificationService;
import haj.com.service.ExtensionRequestService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.ImpactOfStaffTrainingService;
import haj.com.service.JasperService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.OfoCodesService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyHistoryService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.UsersService;
import haj.com.service.WspChecklistService;
import haj.com.service.WspCompanyAddressHistoryService;
import haj.com.service.WspCompanyEmployeesHistoryService;
import haj.com.service.WspCompanyHistoryService;
import haj.com.service.WspCompanyMainHistoryService;
import haj.com.service.WspCompanyTrainingComitteeHistoryService;
import haj.com.service.WspDisputeService;
import haj.com.service.WspRejectionInformationService;
import haj.com.service.WspSdfCompanyHistoryService;
import haj.com.service.WspService;
import haj.com.service.WspServiceVer2;
import haj.com.service.WspSkillsRequirementsService;
import haj.com.service.lookup.GrantOfoSelectionService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.TitleService;
import haj.com.ui.SearchCompanyUI;
import haj.com.ui.SearchUserPassportOrIdUI;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class InitiatewspUI.
 */
@ManagedBean(name = "initiatewspUI")
@ViewScoped
public class InitiatewspUI extends AbstractUI {

	/** The service. */
	private SDFCompanyService service = new SDFCompanyService();

	/** Allocation service */
	private DgAllocationService allocationService = new DgAllocationService();

	/** The data model. */
	private LazyDataModel<SDFCompany> dataModel;

	/** The selected company. */
	private SDFCompany selectedCompany = null;

	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The wsp dispute service. */
	private WspDisputeService wspDisputeService = new WspDisputeService();

	/** The disputes. */
	private List<WspDispute> disputes;

	/** The dispute. */
	private WspDispute dispute;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company. */
	private Company company = null;

	private Company previousCompany = null;

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The sites service. */
	private SitesService sitesService = new SitesService();

	/** The linked companies. */
	private List<Company> linkedCompanies;

	/** The sites. */
	private List<Sites> sites;

	/** The training comittees. */
	private List<TrainingComittee> trainingComittees;

	/** The training comittee service. */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	/** The banking details. */
	private BankingDetails bankingDetails;

	/** The banking details service. */
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();

	/** The company users. */
	private List<CompanyUsers> companyUsers;

	/** The company user. */
	private CompanyUsers companyUser;

	/** The copy address. */
	private Boolean copyAddress;

	/** The impact of staff training. */
	private ImpactOfStaffTraining impactOfStaffTraining;

	/** The impact of staff training service. */
	private ImpactOfStaffTrainingService impactOfStaffTrainingService = new ImpactOfStaffTrainingService();

	/** The doc. */
	private Doc doc;

	/** The wsp service. */
	private WspService wspService = new WspService(getResourceBundle());

	private WspServiceVer2 wspServiceVer2 = new WspServiceVer2(getResourceBundle());
	
	/** The wsp list. */
	private List<Wsp> wspList;

	/** The Wsp list for Previous Transacting Company */
	private List<Wsp> previousCompanyWspList;

	/** The wsp. */
	private Wsp wsp;

	/** The wsp used for create. */
	private Wsp createWsp;

	/** The wsp checklist. */
	private WspChecklist wspChecklist;

	/** The wsp checklist service. */
	private WspChecklistService wspChecklistService = new WspChecklistService();

	/** The errors. */
	private String errors;

	private String signOffTitle;

	/** The dispute reason. */
	private String disputeReason;

	/** Update company Information */
	private boolean updateCompanyData;

	/** The Users Service */
	private UsersService usersService = new UsersService();

	/** The Region Service */
	private RegionService regionService = new RegionService();

	/** Update user Information */
	private boolean updateUserData;

	private boolean primarySDF;

	/** The User */
	private Users selectedUser;

	private ExtensionRequest selectedExtensionRequest = null;

	private Integer finYear = Integer.parseInt(GenericUtility.sdfYear.format(getSynchronizedDate()));

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** The previous financial year */
	private Integer previousFinYear;

	/** The next financial year */
	private Integer nextFinYear;

	private MandatoryGrantDetailService grantDetailService = new MandatoryGrantDetailService();

	private Date maxDate;

	private Date minDate;

	private boolean nonMersetaNonLevyPaying;

	private boolean belowThreshold;

	private boolean showDetails;

	private boolean showSubmitButton;

	private boolean extensionFound;

	private boolean viewWspExtensionRequests;

	private boolean viewWspTasks;

	private DgVerificationService dgVerificationService = new DgVerificationService();

	private RegionTown rt;

	private TempSignoff tempSignoff;

	private RejectReasons rejectReason;

	private List<RejectReasons> selectedRejectReason;

	private List<RejectReasonsChild> rejectReasonsChild;

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();

	private String additionalInformation;

	private Date closeOffDate;

//	private String termsAndConditionsLink = HAJConstants.DOC_SERVER + "DGD-PL-001 Grants Policy (Next Review Date 28 November 2021).pdf";
//	private String termsAndConditionsLink1 = HAJConstants.DOC_SERVER + "Grants Criteria.pdf";
	
	private String termsAndConditionsLink = HAJConstants.DOC_SERVER + HAJConstants.DOC_NAME_GRANTPOLICY;
	private String termsAndConditionsLink1 = HAJConstants.DOC_SERVER + HAJConstants.DOC_NAME_GRANTCRITERIA;
	
	private boolean canInitiate;
	private boolean rejectReasonFound;
	private boolean primarySdfForComapny;
	private ExtensionRequestService extensionRequestService = new ExtensionRequestService();
	private boolean canReopen;
	private int previousYear;
	private int currentYear;
	private int currentYearSmall;
	private int previousYearSmall;
	private boolean previousCompanyFound = false;

	private List<SDFCompanyHistory> sdfCompanyHistory;
	private boolean sdfHistory = false;

	private LazyDataModel<SDFCompany> usersDataModel;

	private WspTypeEnum wspTypeEnum;
	private String typeName;
	private GrantOfoSelection grantOfoSelection = null;
	private LazyDataModel<Tasks> dataModelTasks;
	private TasksService tasksService = null;

	/** Company History Information */

	/* Entity Level */
	private boolean historyFound = false;
	private WspCompanyMainHistory wspCompanyMainHistory = null;
	private WspCompanyHistory wspCompanyHistory = null;

	/* Data Models */
	private List<WspCompanyMainHistory> wspCompanyMainHistoryList;
	private LazyDataModel<WspCompanyAddressHistory> dataModelWspCompanyAddressHistory;
	private LazyDataModel<WspCompanyEmployeesHistory> dataModelWspCompanyEmployeesHistory;
	private LazyDataModel<WspCompanyHistory> dataModelWspCompanyHistory;
	private LazyDataModel<WspCompanyTrainingComitteeHistory> dataModelWspCompanyTrainingComittee;
	private LazyDataModel<WspSdfCompanyHistory> dataModelWspSdfCompanyHistory;

	private List<WspRejectionInformation> wspRejectionReasonsList = null;
	
	private WspTypeEnum wspTypeMan = null;

	public WspTypeEnum getWspTypeMan() {
		return wspTypeMan;
	}

	public void setWspTypeMan(WspTypeEnum wspTypeMan) {
		this.wspTypeMan = wspTypeMan;
	}

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
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		populateDefaults();
		checkTypeOfDataNeeded();
		findRemainingData();
		locateIfRejectReasons();
		identifyIfPrimarySdf();
		populateStringYears();
		sdfDetail();
		populateCompanyHistoryWsp();
		populateWspRejectionReasonInformation();
	}

	private void populateWspRejectionReasonInformation() throws Exception {
		if (wsp != null && wsp.getId() != null) {
			wspRejectionReasonsList = WspRejectionInformationService.instance().findByWspAndLastestEntry(wsp.getId(),
					Boolean.TRUE);
		}
	}

	private void populateStringYears() throws Exception {
		if (wsp != null && wsp.getFinYear() != null) {
			currentYear = wsp.getFinYear() - 1;
			previousYear = currentYear - 1;
			currentYearSmall = wsp.getFinYear() - 2000;
			previousYearSmall = wsp.getFinYear() - 2001;
			grantOfoSelection = GrantOfoSelectionService.instance().findByGrantYearListReturnFirst(wsp.getFinYear());
		}
	}

	private void findRemainingData() throws Exception {
		if (getSessionUI().getTask() != null)
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
		if (company != null)
			if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null)
				rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		maxDate = GenericUtility.getEndOfApril(getNow());
		if (company != null)
			nonMersetaNonLevyPaying = (company.getNonLevyPaying() != null && company.getNonLevyPaying()
					&& company.getSicCode() != null && "Unknown".equals(company.getSicCode().getChamber().getCode()));
		prepNewSignOff();
	}

	private void locateIfRejectReasons() throws Exception {
		if (wsp != null) {
			RejectReasonsService rejectService = new RejectReasonsService();
			long count = rejectService.countReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(),
					wsp.getClass().getName(), ConfigDocProcessEnum.WSP);
			if (count != 0) {
				rejectReasonFound = true;
			} else {
				rejectReasonFound = false;
			}
			rejectService = null;
		} else {
			rejectReasonFound = false;
		}
	}

	/**
	 * check if session user is the primary SDF for grant appeal
	 * 
	 * @throws Exception
	 */
	private void identifyIfPrimarySdf() throws Exception {
		if (!getSessionUI().isEmployee()) {
			if (wsp != null) {
				if (getSessionUI().getActiveUser().getId()
						.equals(sdfCompanyService.findPrimarySDF(wsp.getCompany()).getSdf().getId())) {
					primarySdfForComapny = true;
				} else {
					primarySdfForComapny = false;
				}
			} else {
				primarySdfForComapny = false;
			}
		} else {
			primarySdfForComapny = false;
		}
	}

	private void checkTypeOfDataNeeded() throws Exception, ParseException {
		if (super.getParameter("id", false) != null) {
			this.wsp = wspService.findByKey(getSessionUI().getTask().getTargetKey(), getSessionUI().getActiveUser());
			wspTypeEnum = wsp.getWspType();
			typeName = super.getEntryLanguage(wsp.getWspType().getRegistrationName());
			findGrantData();
			checkCloseOffDateVersionTwo();
			if (wsp.getWspStatusEnum() == WspStatusEnum.Draft) {
				if (getSessionUI().isExternalParty()) {
					selectedCompany = sdfCompanyService.findByCompanyAndUser(company, getSessionUI().getActiveUser());
					this.showDetails = selectedCompany.getSdfType().getId() != HAJConstants.EMP_ID
							&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_EMP_ID
							&& selectedCompany.getSdfType().getId() != HAJConstants.LAB_ID
							&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_LAB_ID;
					this.showSubmitButton = selectedCompany.getSdfType().getId() == HAJConstants.PRIMARY_ID;
				} else {
					this.showDetails = false;
					this.showSubmitButton = false;
				}
			}
			// if (wsp.getWspStatusEnum() == WspStatusEnum.PendingSignOff) {
			// closeOffDate =
			// GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("15-07-" +
			// (wsp.getFinYear() - 1)));
			// } else {
			// checkCloseOffDate();
			// }
		} else if (super.getParameter("idW", false) != null) {
			this.wsp = wspService.findByGuid(super.getParameter("idW", false).toString(), getSessionUI().getActiveUser());
			wspTypeEnum = wsp.getWspType();
			typeName = super.getEntryLanguage(wsp.getWspType().getRegistrationName());
			findGrantData();
			checkCloseOffDateVersionTwo();
			if (!getSessionUI().isExternalParty() && !getSessionUI().isAllowableInternalUser()) {
				this.wsp.setWspStatusEnum(WspStatusEnum.OpenedForReview);
			}
			if (getSessionUI().isExternalParty()) {
				selectedCompany = sdfCompanyService.findByCompanyAndUser(company, getSessionUI().getActiveUser());
				this.showDetails = selectedCompany.getSdfType().getId() != HAJConstants.EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.LAB_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_LAB_ID;
				this.showSubmitButton = selectedCompany.getSdfType().getId() == HAJConstants.PRIMARY_ID;
			} else if (getSessionUI().isAllowableInternalUser()) {
				this.showDetails = true;
				this.showSubmitButton = true;
			} else {
				this.showDetails = false;
				this.showSubmitButton = false;
			}
		} else {
			companysdfInfo();
		}
	}

	private void populateDefaults() {
		updateCompanyData = false;
		updateUserData = false;
		viewWspExtensionRequests = false;
		viewWspTasks = false;
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		getSessionUI().setActiveIndex(0);
	}

	@SuppressWarnings("unused")
	private void checkCloseOffDate() throws Exception {
		// List<ExtensionRequest> extensionRequests =
		// extensionRequestService.findByWSP(wsp);
		List<ExtensionRequest> extensionRequests = extensionRequestService.extensionRequestGranyedCheck(wsp.getId());

		// extension request
		if (extensionRequests.size() > 0 && extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
			Date endOfMay = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("31-05-" + (wsp.getFinYear() - 1)));
			if (extensionRequests.get(0).getNewSubmissionDate() != null) {
				extensionFound = true;
				closeOffDate = GenericUtility.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
			} else {
				extensionFound = false;
				closeOffDate = endOfMay;
			}

		}
		// no extension request but in pending sign off
		else if (wsp.getWspStatusEnum() == WspStatusEnum.PendingSignOff) {
			extensionFound = false;

			// identify new closure date
//			closeOffDate = wspService.determineSignOffClosureDate(wsp);

			// not longer applicable
//			closeOffDate = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("15-07-" + (wsp.getFinYear() - 1)));
		}
		// no extension request , not in pending sign off
		else {
			extensionFound = false;
			if (wsp.getWspType() == wspTypeEnum.Discretionary){
				closeOffDate = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("dg.submission.date") + (wsp.getFinYear() - 1)));
			}
			else if (wsp.getWspType() == wspTypeEnum.AdditionalFunding) {
				closeOffDate = GenericUtility.getEndOfDay(wsp.getDgYear().getEndDate());
			}
			else{
				closeOffDate = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("mg.submission.date") + (wsp.getFinYear() - 1)));
			}
		}
	}

	private void checkCloseOffDateVersionTwo() throws Exception {
		Date endOfMandatory =null;
		Date endOfDiscretionary = null;
		Date endOfManualSubmission = null;
		Date endOfDualGrantDate = null;

		List<ExtensionRequest> extensionRequests = extensionRequestService.extensionRequestGranyedCheck(wsp.getId());
		if(finYear != null) {
			try {
				endOfMandatory = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("mg.submission.date") + (wsp.getFinYear() - 1)));
			}catch(Exception e){
				logger.error("Failed to parse endDate for grant", e);
			}

			try {
				endOfDiscretionary = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("dg.submission.date") + (wsp.getFinYear() - 1)));
			}catch(Exception e){
				logger.error("Failed to parse endDate for grant", e);
			}

		}
		// added for scheme year 2021
//		Date endOfJune = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("30-06-" + (wsp.getFinYear() - 1)));
//		Date endOfJuly = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("15-07-" + (wsp.getFinYear() - 1)));
		// JIRA - 530 - added for MG Application manual submission
//		Date endOfManualSubmission = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("01-12-" + (wsp.getFinYear() - 1)));

		try{
			endOfDualGrantDate = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("dual.closeoff.date") + (wsp.getFinYear() - 1)));
		}catch(Exception e){
			logger.error("Failed to parse close off date for dual grant", e);
		}

		try {
			endOfManualSubmission = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse(NSDMSConfiguration.getString("manual.submission.date") + (wsp.getFinYear())));
		}catch(Exception e){
			logger.error("Failed to parse manual submission date for grant", e);
		}

		// sign off rule
		if (wsp.getWspStatusEnum() == WspStatusEnum.PendingSignOff) {
			// check final submission date
			closeOffDate = null;
			if (extensionRequests.size() > 0 && extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
				// check extension request date
				extensionFound = true;
				if (extensionRequests.get(0).getNewSubmissionDate() != null) {
					closeOffDate = GenericUtility.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
				} else {
					if (wsp.getWspType() == wspTypeEnum.Discretionary) {
						closeOffDate = endOfDiscretionary;
					} else if (wsp.getWspType() == wspTypeEnum.AdditionalFunding) {
						closeOffDate = GenericUtility.getEndOfDay(wsp.getDgYear().getEndDate());
					} else if (wsp.getWspType() == wspTypeEnum.Mandatory) {
						closeOffDate = endOfMandatory;
					} else if (wsp.getManuallyAdded() != null && wsp.getManuallyAdded()){
							closeOffDate = endOfManualSubmission;
					} else if (wsp.getWspType() == wspTypeEnum.Both){
						closeOffDate =endOfDualGrantDate;
					}
				}
			} else {
				// default submission date
				extensionFound = false;
				if (wsp.getWspType() == wspTypeEnum.Discretionary) {
					closeOffDate = endOfDiscretionary;
				} else if (wsp.getWspType() == wspTypeEnum.AdditionalFunding) {
					closeOffDate = GenericUtility.getEndOfDay(wsp.getDgYear().getEndDate());
				} else if (wsp.getWspType() == wspTypeEnum.Mandatory) {
					closeOffDate = endOfMandatory;
				} else if (wsp.getManuallyAdded() != null && wsp.getManuallyAdded()){
						closeOffDate = endOfManualSubmission;
				}  else if (wsp.getWspType() == wspTypeEnum.Both){
					closeOffDate =endOfDualGrantDate;
				}
			}
		}

		// extension request check, latest approved by WSP
		else if (wsp.getWspStatusEnum() != WspStatusEnum.PendingSignOff && extensionRequests.size() > 0
				&& extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
			extensionFound = true;
			if (extensionRequests.get(0).getNewSubmissionDate() != null) {
				closeOffDate = GenericUtility.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
			} else {
				if (wsp.getWspType() == wspTypeEnum.Discretionary) {
					closeOffDate = endOfDiscretionary;
				} else if (wsp.getWspType() == wspTypeEnum.AdditionalFunding) {
					closeOffDate = GenericUtility.getEndOfDay(wsp.getDgYear().getEndDate());
				} else if (wsp.getWspType() == wspTypeEnum.Mandatory) {
					closeOffDate = endOfMandatory;
				} else if (wsp.getManuallyAdded() != null && wsp.getManuallyAdded()){
						closeOffDate = endOfManualSubmission;
				}  else if (wsp.getWspType() == wspTypeEnum.Both){
					closeOffDate =endOfDualGrantDate;
				}
			}
		} else {
			// normal
			extensionFound = false;
			if (wsp.getWspType() == wspTypeEnum.Discretionary) {
				closeOffDate = endOfDiscretionary;
			} else if (wsp.getWspType() == wspTypeEnum.AdditionalFunding) {
				closeOffDate = GenericUtility.getEndOfDay(wsp.getDgYear().getEndDate());
			} else if (wsp.getWspType() == wspTypeEnum.Mandatory) {
				closeOffDate = endOfMandatory;
			} else if (wsp.getManuallyAdded() != null && wsp.getManuallyAdded()){
					closeOffDate = endOfManualSubmission;
			}  else if (wsp.getWspType() == wspTypeEnum.Both){
				closeOffDate =endOfDualGrantDate;
			}
		}
	}

	private void findGrantData() throws Exception {
		company = this.wsp.getCompany();
		getCompanyData();
		getSessionUI().setWspSession(this.wsp);
		calculateFinYears();
		calcCosts();
	}

	/**
	 * Gets the prepare fin year.
	 *
	 * @return the prepare fin year
	 */
	public List<Integer> getPrepareFinYear() {
		List<Integer> l = new ArrayList<>();
		// if (getNow().before(GenericUtility.getEndOfApril(getNow()))) {
		Calendar now = Calendar.getInstance();

		try {
			boolean canCreateNewWsp1 = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(),
					(finYear + 1)) == 0) ? false : true);
			boolean canCreateNewWsp_2023 = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(),
					(finYear + 2)) == 0) ? false : true);
			boolean canCreateNewWsp_2024 = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(),
					(finYear + 3)) == 0) ? false : true);
			if (!canCreateNewWsp1) {
				l.add(now.get(Calendar.YEAR) + 1);
			}
			else if (!belowThreshold) {
				// l.add(now.get(Calendar.YEAR) + 1);
				if (!canCreateNewWsp1 && !canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 2);
					l.add(now.get(Calendar.YEAR) + 3);
				} else if (canCreateNewWsp1 && !canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 2);
					l.add(now.get(Calendar.YEAR) + 3);
				}else if (!canCreateNewWsp1 && canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 3);
				}else if (!canCreateNewWsp1 && !canCreateNewWsp_2023 && canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 2);
				}
				else if (canCreateNewWsp1 && canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 3);
				}
			} else {

				// 2022 not created
				if (!canCreateNewWsp1 && !canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 2);
					l.add(now.get(Calendar.YEAR) + 3);
				} else if (canCreateNewWsp1 && !canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 2);
					l.add(now.get(Calendar.YEAR) + 3);
				}else if (!canCreateNewWsp1 && canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 3);
				}else if (!canCreateNewWsp1 && !canCreateNewWsp_2023 && canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 1);
					l.add(now.get(Calendar.YEAR) + 2);
				}				
				else if (canCreateNewWsp1 && canCreateNewWsp_2023 && !canCreateNewWsp_2024) {
					l.add(now.get(Calendar.YEAR) + 3);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// }
		return l;
	}

	public boolean checkFinyear(Integer finYear1) {
		boolean falg = false;
		boolean falg2 = false;
		try {
//			falg = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(), (finYear + 2)) == 0) ? true : false);
//			falg2 = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(), (finYear + 3)) == 0) ? true : false);
			int finyr2 = finYear + 1;
			if (finYear1 <= finYear + 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkFinyear_vh(Integer finYear1) {
		boolean falg = false;
		boolean falg2 = false;
		try {

			int finyr2 = finYear + 1;
			if (finYear1 <= finYear + 1) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<Integer> getPrepareFinYear_vh() {
		List<Integer> l = new ArrayList<>();
		// if (getNow().before(GenericUtility.getEndOfApril(getNow()))) {
		Calendar now = Calendar.getInstance();
		l.add(now.get(Calendar.YEAR) + 1);
		// }
		return l;
	}

	private void calculateFinYears() throws Exception {
		if (wsp != null && wsp.getFinYear() != null) {
			previousFinYear = wsp.getFinYear() - 1;
			nextFinYear = wsp.getFinYear();
		} else {
			previousFinYear = 2016;
			nextFinYear = 2018;
		}
	}

	/**
	 * Gets the company data.
	 *
	 * @return the company data
	 * @throws Exception the exception
	 */
	private void getCompanyData() throws Exception {
		linkedCompanies = companyService.findLinkedCompany(company);
		sites = sitesService.findByCompany(company);
		companyUsers = companyUsersService.findByCompany(company);
		bankingDetails = bankingDetailsService.findByCompanyLates(company);
		trainingComittees = trainingComitteeService.findByCompany(company);
		wspChecklist = wspChecklistService.findByWSP(wsp);
		impactOfStaffTraining = impactOfStaffTrainingService.findByWSP(wsp);
		rejectReasonsChild = rejectReasonsService.findByWsp(wsp);
		disputes = wspDisputeService.findByWsp(wsp);

		if (getSessionUI().getTask() != null && getSessionUI().getTask().getProcessRole() != null)
			if (wsp.getWspStatusEnum() == WspStatusEnum.Pending) {
				if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.View
						|| getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Approve
						|| getSessionUI().getTask().getProcessRole()
								.getRolePermission() == UserPermissionEnum.FinalApproval) {
					populateWspChecklist();
				}
			} else if (wsp.getWspStatusEnum() == WspStatusEnum.Approved
					|| wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
				populateWspChecklist();
			}
		// old check
		// if ((wsp.getWspStatusEnum() == WspStatusEnum.Pending &&
		// getSessionUI().getTask().getProcessRole().getRolePermission() ==
		// UserPermissionEnum.View)
		// || (wsp.getWspStatusEnum() == WspStatusEnum.Pending &&
		// getSessionUI().getTask().getProcessRole().getRolePermission() ==
		// UserPermissionEnum.Approve)
		// || (wsp.getWspStatusEnum() == WspStatusEnum.Pending &&
		// getSessionUI().getTask().getProcessRole().getRolePermission() ==
		// UserPermissionEnum.FinalApproval)
		// || (wsp.getWspStatusEnum() == WspStatusEnum.Approved ||
		// wsp.getWspStatusEnum() == WspStatusEnum.Rejected)) {
		// try {
		// wspChecklistService.populateChecklist(wspChecklist, wsp,
		// bankingDetails);
		// } catch (Exception e) {
		// addErrorMessage(e.getMessage(), e);
		// e.printStackTrace();
		// }
		// }
	}

	/**
	 * Populates the wsp checklist
	 */
	public void populateWspChecklist() {
		try {
			wspChecklistService.populateChecklist(wspChecklist, wsp, bankingDetails);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Get all CompanySDF for data table.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void companysdfInfo() {

		dataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					if (getSessionUI().isExternalParty()) {
						retorno = service.allCompanyFromSDFWherePrimaryAndSecondaryAndLabAndEmp(SDFCompany.class, first,
								pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						dataModel.setRowCount(service.allCompanyFromSDFCountWherePrimaryAndSecondaryLabAndEmpAndAltEmp(
								filters, getSessionUI().getActiveUser()));
						// retorno =
						// service.allCompanyFromSDFWherePrimaryAndSecondary(SDFCompany.class,
						// first, pageSize, sortField, sortOrder, filters,
						// getSessionUI().getActiveUser());
						// dataModel.setRowCount(service.allCompanyFromSDFCountWherePrimaryAndSecondary(filters,
						// getSessionUI().getActiveUser()));
					} else if (getSessionUI().isEmployee() || getSessionUI().isAdmin()) {
						retorno = service.allCompanyFromSDFWherePrimary(SDFCompany.class, first, pageSize, sortField,
								sortOrder, filters);
						dataModel.setRowCount(service.allCompanyFromSDFCountWherePrimary(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public void fixSignOffs() {
		try {
			addInfoMessage(wspService.fixSignOffs(wsp, getSessionUI().getActiveUser()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void calcCosts() {
		try {
			grantDetailService.calcCosts(wsp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays and populates the the list of tasks assigned to the WSP
	 */
	public void viewTasksByWsp() {
		try {
			this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());
			viewWspTasks = true;
			dataModelTasksInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void dataModelTasksInfo() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters,
							wsp.getId(), wsp.getClass().getName());
					dataModelTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Tasks obj) {
				return obj.getId();
			}

			@Override
			public Tasks getRowData(String rowKey) {
				for (Tasks obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Displays and populates the the list of extension requests assigned to the WSP
	 */
	public void viewExtensionRequestsByWsp() {
		try {
			viewWspExtensionRequests = true;
			selectedExtensionRequest = null;
			// populate user object against extension request
			wsp.setExtensionRequests(
					ExtensionRequestService.instance().populateInformationList(wsp.getExtensionRequests()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Fetches the additional information for the extension request
	 */
	public void viewSelectedExtensionRequestsDoc() {
		try {
			selectedExtensionRequest = ExtensionRequestService.instance().findByKey(selectedExtensionRequest.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Closes the view of the extension request assigned to the WSP
	 */
	public void closeExtensionRequestsByWsp() {
		try {
			selectedExtensionRequest = null;
			viewWspExtensionRequests = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Resolve users.
	 *
	 * @throws Exception the exception
	 */
	public void resolveUsers() throws Exception {
		companyUsers = companyUsersService.findByCompany(selectedCompany.getCompany());
		prepUsers();
	}

	public void downloadReport() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("company_id", wsp.getId());
		try {
			JasperService.addLogo(params);
			JasperService.instance().createReportFromDBtoServletOutputStream("Sign_Off.jasper", params, "Sign_Off.pdf");
			wsp.setRequireSignOffUpload(true);
			wspService.update(wsp);
			checkRequiredDocs();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void checkRequiredDocs() {
		try {
			wspService.checkDocRequired(wspService.resolveDocs(wsp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clear users.
	 *
	 * @throws Exception the exception
	 */
	public void clearUsers() throws Exception {
		this.companyUsers = null;
	}

	/**
	 * Prep users.
	 *
	 * @throws Exception the exception
	 */
	public void prepUsers() throws Exception {
		this.companyUser = null;
	}

	/**
	 * Insert Users into database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersInsert() {
		try {
			// cpyAddresses();
			companyUsersService.createWithReg(this.companyUser, null);
			resolveUsers();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Cpy addresses.
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				AddressService.instance().copyFromToAddress(companyUser.getUser().getResidentialAddress(),
						companyUser.getUser().getPostalAddress());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		companyUser.getUser().getPostalAddress().setSameAddress(copyAddress);

	}

	/**
	 * START For WSP.
	 */
	public void prepareWspForCompany() {
		try {
			if (this.selectedCompany.getCompany() != null) {
				// Patrick changes
				Calendar now = Calendar.getInstance();
				this.finYear = Integer.valueOf(now.get(Calendar.YEAR) + 1);

				this.wsp = new Wsp();
				this.wspList = new ArrayList<>();
				// Patrick changes
				// this.wspList =
				// wspService.findByCompanyAndFinancialYear(this.selectedCompany.getCompany().getId(),
				// finYear);

				this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());
				this.wsp.setCompany(this.selectedCompany.getCompany());
				this.wsp.setCreateUsers(getSessionUI().getActiveUser());
				bankingDetails = bankingDetailsService.findByCompanyLates(this.selectedCompany.getCompany());

				primarySDF = selectedCompany.getSdfType().getId() != HAJConstants.EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.LAB_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_LAB_ID;

				// Patrick changes
				List<ExtensionRequest> extensionRequests = extensionRequestService
						.findByCompanyNoWSP(selectedCompany.getCompany());
				// List<ExtensionRequest> extensionRequests =
				// extensionRequestService.findByCompanyNoWSPWithFinYear(selectedCompany.getCompany(),
				// finYear);
				if (extensionRequests.size() > 0 && extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
					Date endOfMay = GenericUtility
							.getEndOfDay(GenericUtility.sdf6.parse("31-05-" + GenericUtility.sdfYYYY.format(getNow())));
					if (extensionRequests.get(0).getNewSubmissionDate() != null) {
						closeOffDate = GenericUtility.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
						// if (closeOffDate.after(endOfMay)) {
						// closeOffDate = endOfMay;
						// }
					} else {
						closeOffDate = endOfMay;
					}
					canInitiate = getNow().before(closeOffDate) && bankingDetails != null && primarySDF;
				} else {
					canInitiate = getNow().before(GenericUtility.getEndOfApril(getNow())) && bankingDetails != null
							&& primarySDF;
				}
				super.runClientSideUpdate(":wspInsForm");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private boolean canCreateNewWsp = false;

	public void prepareWspForCompanyVersionTwo() {
		try {
			if (this.selectedCompany.getCompany() != null) {

				previousCompanyFound = false;
				previousCompany = null;
				this.previousCompanyWspList = new ArrayList<>();

				this.wsp = new Wsp();
				this.wsp.setCompany(this.selectedCompany.getCompany());
				this.wsp.setCreateUsers(getSessionUI().getActiveUser());

				this.createWsp = new Wsp();
				this.createWsp.setCompany(this.selectedCompany.getCompany());
				this.createWsp.setNumberOfEmployees(this.selectedCompany.getCompany().getNumberOfEmployees());
				this.createWsp.setCreateUsers(getSessionUI().getActiveUser());

				this.wspList = new ArrayList<>();
				this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());

				if (this.selectedCompany.getCompany().getPreviousCompany() != null
						&& this.selectedCompany.getCompany().getPreviousCompany().getId() != null) {
					previousCompany = companyService
							.findByKey(this.selectedCompany.getCompany().getPreviousCompany().getId());
					if (previousCompany != null && previousCompany.getId() != null) {
						previousCompanyFound = true;
						previousCompanyWspList = wspService.findByCompany(previousCompany);
					}
				} else {
					previousCompanyWspList = null;
				}
				// validation if one can raise a wsp on the current year
				canCreateNewWsp = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(),
						(finYear + 1)) == 0) ? true : false);

				// locates company banking details
				bankingDetails = bankingDetailsService.findByCompanyLates(this.selectedCompany.getCompany());

				// identifies if primary SDF
				primarySDF = selectedCompany.getSdfType().getId() != HAJConstants.EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_EMP_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.LAB_ID
						&& selectedCompany.getSdfType().getId() != HAJConstants.ALT_LAB_ID;

				if (finYear != null && (finYear + 1) == 2021) {

					// validation check on dates
					if (getNow().before(GenericUtility.getEndOfJuly(getNow()))) {
						// check if current date before end of april
						canInitiate = bankingDetails != null && primarySDF;
					} else {
						// view extension request checks
						List<ExtensionRequest> extensionRequests = extensionRequestService
								.findByCompanyNoWSPAssigned(selectedCompany.getCompany());
						if (!extensionRequests.isEmpty()
								&& extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
							Date endOfMay = GenericUtility.getEndOfDay(
									GenericUtility.sdf6.parse("30-06-" + GenericUtility.sdfYYYY.format(getNow())));
							Date endOfJuly = GenericUtility.getEndOfDay(
									GenericUtility.sdf6.parse("31-07-" + GenericUtility.sdfYYYY.format(getNow())));
							if (extensionRequests.get(0).getNewSubmissionDate() != null) {
								closeOffDate = GenericUtility
										.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
							} else {
//								closeOffDate = endOfMay;
								closeOffDate = endOfJuly;
							}
							canInitiate = getNow().before(closeOffDate) && bankingDetails != null && primarySDF;
						} else {
							canInitiate = false;
						}
					}

				} else {

					// validation check on dates
					if (getNow().before(GenericUtility.getEndOfApril(getNow()))) {
						// check if current date before end of april
						canInitiate = bankingDetails != null && primarySDF;
					} else {
						// view extension request checks
						// List<ExtensionRequest> extensionRequests =
						// extensionRequestService.findByCompanyNoWSP(selectedCompany.getCompany());
						List<ExtensionRequest> extensionRequests = extensionRequestService
								.findByCompanyNoWSPAssigned(selectedCompany.getCompany());
						if (!extensionRequests.isEmpty()
								&& extensionRequests.get(0).getStatus() == ApprovalEnum.Approved) {
							Date endOfMay = GenericUtility.getEndOfDay(
									GenericUtility.sdf6.parse("31-05-" + GenericUtility.sdfYYYY.format(getNow())));
							if (extensionRequests.get(0).getNewSubmissionDate() != null) {
								closeOffDate = GenericUtility
										.getEndOfDay(extensionRequests.get(0).getNewSubmissionDate());
							} else {
								closeOffDate = endOfMay;
							}
							canInitiate = getNow().before(closeOffDate) && bankingDetails != null && primarySDF;
						} else {
							canInitiate = false;
						}
					}
				}
				super.runClientSideUpdate(":wspInsForm");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Close wsp lookup.
	 */
	public void closeWspLookup() {
		this.wsp = new Wsp();
		this.wspList = new ArrayList<>();
	}

	/**
	 * Go to wsp for company.
	 */
	public void goToWspForCompany() {
		try {
			if (wsp.getWspStatusEnum() == WspStatusEnum.Draft) {
				wspService.wspSdfCheck(this.wsp);
			}
			if (wsp.getWspType() == WspTypeEnum.AdditionalFunding) {
				super.redirect("/pages/externalparty/wsp/reviewapplicationdg.jsf?idW=" + this.wsp.getWspGuid());
			}
			else
			{
				super.redirect("/pages/externalparty/wsp/reviewapplication.jsf?idW=" + this.wsp.getWspGuid());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Prepare WSP.
	 */
	public void prepareWSP() {
		this.wsp = new Wsp();
	}

	/**
	 * Initiate WSP.
	 * 
	 * NEEDs amendments
	 */
	public void initiateWSP() {
		try {

			// check if before 1 Feb of year to generate
			if (HAJConstants.DEV_TEST_PROD.equals("P")) {
//				wspService.checkIfAfterFebForGrantInitiation(this.wsp.getFinYear(), getNow());
				this.checkIfAfterFebForGrantInitiation(this.wsp.getFinYear(), getNow());
			}

//			wspService.checkIfGpsAssignedToCompany(this.wsp);

			wspService.checkWspFinYearUnique(this.wsp);
			wspService.createWithSignOff(this.wsp);
			getSessionUI().setWspSession(this.wsp);
			if (!getNow().before(GenericUtility.getEndOfApril(getNow()))) {
				List<ExtensionRequest> extensionRequests = extensionRequestService
						.findByCompanyNoWSPAssigned(selectedCompany.getCompany());
//				List<ExtensionRequest> extensionRequests = extensionRequestService.findByCompanyNoWSP(selectedCompany.getCompany());

				if (extensionRequests.size() > 0) {
					extensionRequests.get(0).setWsp(wsp);
					extensionRequestService.create(extensionRequests.get(0));
				}
			}
			canCreateNewWsp = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(), (finYear + 1)) == 0)
					? true
					: false);
			this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());
			runClientSideExecute("PF('dialogRangeWsp').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void initiateWspVersionTwo() {
		try {
			// check if before 1 Feb of year to generate
			if (HAJConstants.DEV_TEST_PROD.equals("P")) {
//				wspService.checkIfAfterFebForGrantInitiation(this.createWsp.getFinYear(), getNow());
				this.checkIfAfterFebForGrantInitiation(this.wsp.getFinYear(), getNow());
			}
			if( this.createWsp.getFinYear() !=null && this.createWsp.getWspType() != null) {
//				wspService.checkIfGpsAssignedToCompany(this.createWsp);
				this.createWsp.setManuallyAdded(false);
				createWsp.setWspType(createWsp.getWspType());
				wspService.checkWspFinYearUnique(this.createWsp);
				wspService.createWithSignOff(this.createWsp);
	//			getSessionUI().setWspSession(this.createWsp);
				if (!getNow().before(GenericUtility.getEndOfApril(getNow()))) {
					List<ExtensionRequest> extensionRequests = extensionRequestService
							.findByCompanyNoWSPAssigned(selectedCompany.getCompany());
	//				List<ExtensionRequest> extensionRequests = extensionRequestService.findByCompanyNoWSP(selectedCompany.getCompany());
	
					if (extensionRequests.size() > 0) {
						extensionRequests.get(0).setWsp(createWsp);
						extensionRequestService.create(extensionRequests.get(0));
					}
				}
				canCreateNewWsp = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(), (finYear + 1)) == 0)
						? true
						: false);
				this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());
				runClientSideExecute("PF('dialogRangeWsp').hide()");
			}
			else {
				addErrorMessage("Financial Year and Grant Type are Mandatory");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initiate Manual WSP.
	 * 
	 * JIRA #530
	 */
	public void initiateManualWsp() {
		try {
			wspService.checkIfGpsAssignedToCompany(this.createWsp);
			this.createWsp.setWspType(this.wspTypeMan);
			this.createWsp.setManuallyAdded(true);
			this.createWsp.setFinYear(finYear);
			this.createWsp.setRequireSignOffUpload(true);
			wspService.checkWspFinYearUnique(this.createWsp);
			wspService.createManualWspWithManualSignOff(this.createWsp);
			if (!getNow().before(GenericUtility.getEndOfApril(getNow()))) {
				List<ExtensionRequest> extensionRequests = extensionRequestService
						.findByCompanyNoWSPAssigned(selectedCompany.getCompany());
				if (extensionRequests.size() > 0) {
					extensionRequests.get(0).setWsp(createWsp);
					extensionRequestService.create(extensionRequests.get(0));
				}
			}
			canCreateNewWsp = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(), (finYear + 1)) == 0)
					? true
					: false);
			this.wspList = wspService.findByCompany(this.selectedCompany.getCompany());
			runClientSideExecute("PF('dialogManSubDate').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Save WSP.
	 */
	public void saveWSP() {
		try {
		/*	if (getNow().before(closeOffDate)) {
				wspService.submitWSP(wsp, getSessionUI().getActiveUser(), true);
			}
			**/
			if ((getNow().before(closeOffDate)) && (wsp.getFinYear().equals(finYear+1))) {
				wspService.submitWSP(wsp, getSessionUI().getActiveUser(), true);
			} else if ((getNow().before(closeOffDate)) && (!wsp.getFinYear().equals(finYear+1))) {
				wspService.submitWSP(wsp, getSessionUI().getActiveUser(), false);
			} else {
				wspService.saveWSP(wsp, getSessionUI().getActiveUser(), true);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));

			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Complete signoff.
	 */
	public void completeSignoff() {
		try {
			if (getNow().before(closeOffDate) || this.getSessionUI().isAllowableInternalUser()) {
				completeSuccessfulSignOff();
			} else {
				wspService.saveSignOffVersionTwo(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
				// Validation if submission available last submission date 15 July of fin year -
				// REMOVED AS POLICY CHANGED
//				boolean submissionAvalible = wspService.determineSignOffSubmission(wsp, closeOffDate, getSessionUI().getActiveUser(), GenericUtility.sdf6.parse("15-07-" + (wsp.getFinYear() - 1)));
//				if (submissionAvalible) {
//					// submission available
//					completeSuccessfulSignOff();
//				} else {
//					// submission not available but must save sign offs
//					wspService.saveSignOffVersionTwo(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
//				}
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			getSessionUI().setActiveIndex(8);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Complete signoff manually.
	 */
	public void completeSignoffManually() {
		try {
			if (getNow().before(closeOffDate) || this.getSessionUI().isAllowableInternalUser()) {
				completeSuccessfulSignOffManually();
			} else {
				wspService.saveSignOffVersionTwo(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			getSessionUI().setActiveIndex(8);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Completes signoff manually
	 * 
	 * @throws Exception
	 */
	public void completeSuccessfulSignOffManually() throws Exception {
		wspService.signOffManually(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
		if (wsp.getWspType() == WspTypeEnum.Discretionary)
			wspService.sendAcknowledgementOfSubmissionEmail(getSessionUI().getActiveUser(), wsp.getCompany());
		else
			wspService.sendAcknowledgementOfSubmission(getSessionUI().getActiveUser(), wsp.getCompany());
	}

	/**
	 * Completes signoff
	 * 
	 * @throws Exception
	 */
	public void completeSuccessfulSignOff() throws Exception {
		wspService.signOff(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
		if (wsp.getWspType() == WspTypeEnum.Discretionary)
			wspService.sendAcknowledgementOfSubmissionEmail(getSessionUI().getActiveUser(), wsp.getCompany());
		else
			wspService.sendAcknowledgementOfSubmission(getSessionUI().getActiveUser(), wsp.getCompany());
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getAdminRejectionReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),
					ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getManagerRejectionReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),
					ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<RejectReasons> getDocumentRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.WSPDocumentUpload);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<OfoCodes> completeOfoCodesAtrByDescAndYear(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			if (grantOfoSelection == null || grantOfoSelection.getOfoAtrSelectionYear() == null) {
				l = ofoCodesService.findByNameAtrAndYear(desc, 2021);
			} else {
				l = ofoCodesService.findByNameAtrAndYear(desc, grantOfoSelection.getOfoAtrSelectionYear());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<OfoCodes> completeOfoCodesWspByDescAndYear(String desc) {
		OfoCodesService ofoCodesService = new OfoCodesService();
		List<OfoCodes> l = null;
		try {
			if (grantOfoSelection == null || grantOfoSelection.getOfoWspSelectionYear() == null) {
				l = ofoCodesService.findByNameAtrAndYear(desc, 2021);
			} else {
				l = ofoCodesService.findByNameAtrAndYear(desc, grantOfoSelection.getOfoWspSelectionYear());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	private int daysBetweenDays;

	/**
	 * Checks before grant can be reopen and sent back to the SDF
	 */
	public void checkBeforeGrantReopen() {
		try {
			if (getNow().after(closeOffDate)) {
				daysBetweenDays = GenericUtility.getDaysBetweenDatesExcludeWeekends(closeOffDate, getNow());
				canReopen = false;
			} else {
				daysBetweenDays = GenericUtility.getDaysBetweenDatesExcludeWeekends(getNow(), closeOffDate);
				canReopen = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Send back to SDF.
	 */
	public void sendBackToSDF() {
		try {
			validateSDF(wsp.getCompany());
			wspService.sendBackToSDF(wsp, getSessionUI().getActiveUser());
			rejectReasonsService.createWspReject(selectedRejectReason, wsp, getSessionUI().getTask(),
					additionalInformation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Send back to SDF to re-upload documents.
	 */
	public void sendBackToSDFDocumentRejection() {
		try {
			wspServiceVer2.sendBackToSDFDocumentRejection(wsp, getSessionUI().getActiveUser(), selectedRejectReason, additionalInformation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve.
	 */
	public void approve() {
		try {
			wspChecklistService.update(wspChecklist);
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.ManagerApproved);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject.
	 */
	public void reject() {
		try {
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.ManagerRejected);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve.
	 */
	public void approveSenior() {
		try {
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Approved);
			wspChecklistService.update(wspChecklist);
			dgVerificationService.generateForWSP(wsp, getSessionUI().getActiveUser());
			dgAllocationCheckForGrant(wsp);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject.
	 */
	public void rejectSenior() {
		try {
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Rejected);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * The Final Approve of the grant application
	 */
	public void finalApproveGrantApplication() {
		try {
			wspChecklistService.update(wspChecklist);
			validateSDF(wsp.getCompany());
			wspService.finalApproveRejectGrantApplication(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Approved);
			if (wsp.getSdfAppealedGrant() != null && wsp.getSdfAppealedGrant() == true) {
				wspService.sendReleventNotificationToUsers(wsp, 2, null);
			} else {
				wspService.sendReleventNotificationToUsers(wsp, 1, null);
			}
			dgVerificationService.generateForWSP(wsp, getSessionUI().getActiveUser());
			addInfoMessage(super.getEntryLanguage("update.successful"));

			if (wsp.getFinYear() != 2019) {
				dgAllocationCheckForGrant(wsp);
			}
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * This method will need to be removed for 2020 grant applications for it will
	 * no longer be required to do a check on this level
	 * 
	 * @param wspPassed
	 * @throws Exception
	 */
	private void dgAllocationCheckForGrant(Wsp wspPassed) throws Exception {
		DgAllocationService dgAllocationService = new DgAllocationService();
		dgAllocationService.checkForAllocation(wsp);
		dgAllocationService = null;
	}

	/**
	 * Sets the grant application to awaiting MANCO approval
	 */
	public void awaitingMancoApproval() {
		try {
			wspService.awaitingMancoApproval(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * The Final Reject of the grant application
	 */
	public void finalRejectGrantApplication() {
		try {
			wspChecklistService.update(wspChecklist);
			validateSDF(wsp.getCompany());
			// version one
//			boolean generateVerification = wspService.determainDgVerificationGenerationRejectedGrant(wsp);

			// version two
			boolean generateVerification = false;
			if (wsp.getFinYear() != null && wsp.getFinYear() > 2020) {
				generateVerification = true;
			} else {
				generateVerification = wspService.determainDgVerificationGenerationRejectedGrant(wsp);
			}

			wspService.finalApproveRejectGrantApplication(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Rejected);
			if (wsp.getGrantRejected() && wsp.getSdfAppealedGrant()) {
				wspService.sendReleventNotificationToUsers(wsp, 4, getAdminRejectionReasons());
			}
			if (generateVerification) {
				dgVerificationService.generateForRejectedWspSmallCompany(wsp);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Rejects the grant application to the SDF to appeal the application or accept
	 * the rejection
	 */
	public void rejectToSdfToAppeal() {
		try {
			validateSDF(wsp.getCompany());
			if (selectedRejectReason == null || selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Reason For Rejection Before Submission.");
			}
			wspChecklistService.update(wspChecklist);

			// Version one
//			wspService.rejectToSdfToAppeal(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);

			// Version Two
			wspServiceVer2.rejectToSdfToAppealVersionTwo(wsp, getSessionUI().getActiveUser(), getSessionUI().getRole(),
					getSessionUI().getTask(), selectedRejectReason, additionalInformation);

			wspService.sendReleventNotificationToUsers(wsp, 3, selectedRejectReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * When the SDF accepts the grant rejection
	 */
	public void sdfAcceptRejecion() {
		try {
			boolean generateVerification = wspService.determainDgVerificationGenerationRejectedGrant(wsp);
			wspService.finalRejectionGrant(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			if (generateVerification) {
				dgVerificationService.generateForRejectedWspSmallCompany(wsp);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * When the SDF appeals the grant rejection
	 */
	public void sdfAppealApplication() {
		try {
			wspService.appealGrantApplication(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Save status update.
	 */
	public void saveStatusUpdate() {
		try {
			wspChecklistService.update(wspChecklist);
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Pending);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Save status update.
	 */
	public void rejectToManager() {
		try {
			if (selectedRejectReason == null || selectedRejectReason.size() == 0) {
				throw new Exception("Provide Atleast One Reason For Rejection Before Submission.");
			}
			wspChecklistService.update(wspChecklist);
			wspService.saveStatusUpdate(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(),
					WspStatusEnum.Pending);
			wspServiceVer2.setRejectedReasons(wsp.getClass().getName(), wsp.getId(), getSessionUI().getActiveUser(),
					selectedRejectReason, ConfigDocProcessEnum.WSP, additionalInformation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Dispute WSP.
	 */
	public void disputeWSP() {
		try {
			wspService.disputeWSP(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask(), disputeReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			this.wsp = wspService.findByGuid(wsp.getWspGuid(), getSessionUI().getActiveUser());
			disputes = wspDisputeService.findByWsp(wsp);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void saveDispute() {
		try {
			wspDisputeService.create(dispute);
			disputes = wspDisputeService.findByWsp(wsp);
			this.dispute = null;
			checkRequiredDocs();
			getSessionUI().setActiveIndex(15);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Change dispute tab.
	 */
	public void changeDisputeTab() {
		getSessionUI().setActiveIndex(9);
	}

	public void completeDispute() {
		try {
			dispute.setResolved(true);
			wspDisputeService.submitSignOff(dispute, getSessionUI().getActiveUser(), getSessionUI().getTask());
			disputes = wspDisputeService.findByWsp(wsp);
			this.dispute = null;
			checkRequiredDocs();
			getSessionUI().setActiveIndex(15);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

		// saveDispute();
	}

	/**
	 * Go to tab.
	 *
	 * @param index the index
	 */
	public void goToTab(int index) {
		// this.activeIndex = index;
		getSessionUI().setActiveIndex(index);
		System.out.println(index);
	}

	/**
	 * Impactofstafftraining insert.
	 */
	public void impactofstafftrainingInsert() {
		try {
			impactOfStaffTraining.setWsp(wsp);
			impactOfStaffTrainingService.create(this.impactOfStaffTraining);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			impactOfStaffTraining = impactOfStaffTrainingService.findByWSP(wsp);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Sets the company edit information on wsp
	 */
	public void changeCompanyEdit() {
		try {
			if (updateCompanyData) {
				updateCompanyData = false;
			} else {
				updateCompanyData = true;
			}
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Cancel company information Update on WSP section
	 */
	public void cancelUpdateCompanyInformationOnWsp() {
		try {
			company = this.wsp.getCompany();
			getCompanyData();
			getSessionUI().setWspSession(this.wsp);
			changeCompanyEdit();
			runClientSideUpdate("infoForm");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update company information on WSP section
	 */
	public void updateCompanyInformationOnWsp() {
		try {
			companyService.save(company);
			company = this.wsp.getCompany();
			getCompanyData();
			getSessionUI().setWspSession(this.wsp);
			changeCompanyEdit();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * preps the update of a user
	 */
	public void prepUserInformationUpdate() {
		try {
			// System.out.println(selectedUser.getFirstName() + " " +
			// selectedUser.getLastName());
			updateUserData = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Updates the selected users information
	 */
	public void updateUsersInformation() {
		try {
			usersService.update(selectedUser);
			companyUsers = companyUsersService.findByCompany(company);
			updateUserData = false;
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Cancels the User being updated
	 */
	public void cancelUserUpdate() {
		try {
			updateUserData = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * update the wsp run in the back end
	 */
	public void updateWsp() {
		try {
			wspService.update(this.wsp);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * update the wsp atr section
	 */
	public void updateWspAtr() {
		try {
			wspService.update(this.wsp);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * update the wsp
	 */
	public void updateWspDeviatedWorkplaceSkillsPlan() {
		try {
			wspService.update(this.wsp);
			this.wsp = wspService.findByKey(wsp.getId(), getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download Grant Application information
	 *
	 * @author TechFinium
	 */
	public void downloadGrantApplication() {

		Map<String, Object> params = new HashMap<String, Object>();
		// JasperService js = new JasperService();
		try {
			// Loading wsp details
			this.wsp = wspService.findByGuid(wsp.getWspGuid(), getSessionUI().getActiveUser());
			company = this.wsp.getCompany();
			if (this.wsp.getNumberOfEmployees() != null) {
				company.setNumberOfEmployees(this.wsp.getNumberOfEmployees());
			}
			if (wsp.getSubmissionDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
				String grantSubmittedDate = sdf.format(wsp.getSubmissionDate());
				System.out.println("grantSubmittedDate:: "+grantSubmittedDate);
				wsp.setSubmittedDateForReport(grantSubmittedDate);
			}
			else {
				wsp.setSubmittedDateForReport("Not Yet Submitted");
			}

			/*
			 * getCompanyData(); Commented this method because it was loading lot of data
			 * that we are not using on the report
			 */
			linkedCompanies = companyService.findLinkedCompany(company);
			sites = sitesService.findByCompany(company);
			companyUsers = companyUsersService.findByCompany(company);
			trainingComittees = trainingComitteeService.findByCompany(company);

			if (company.getMajorityUnion() == null) {
				OrganisedLabourUnion majorityUnion = new OrganisedLabourUnion();
				majorityUnion.setDescription("N/A");
				company.setMajorityUnion(majorityUnion);
			}

			params.put("company", company);
			params.put("wsp_id", wsp.getId());
			params.put("wsp", wsp);
			// Getting last sign off date
			Date finalSignOffDate = new Date();
			for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
				finalSignOffDate = wspSignoff.getCreateDate();
			}
			params.put("finalSignOffDate", finalSignOffDate);
			// Adding logo an backround image
			JasperService.addLogo(params);
			// Adding Grant reports
			JasperService.addGrantSubreport(params);

			MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

			List<EmployeeProfileBean> employeeProfileBeansList = mandatoryGrantDetailService
					.allEmployeeProfileBean(wsp.getCompany());
			List<EmployeeEquityProfileBean> employeeEquityProfileBeanList = mandatoryGrantDetailService
					.allEmployeeEquityProfileBean(wsp.getCompany());
			List<EmpEmploymentStatusBean> empEmploymentStatusBeanList = mandatoryGrantDetailService
					.allEmpEmploymentStatusBean(wsp.getCompany());

			List<ATRReportSummayBean> atrReportSummayBeanList = mandatoryGrantDetailService.allATRReportSummayBean(wsp,
					WspReportEnum.ATR, HAJConstants.DISC_FUNDING_ID);
			List<WSPReportSummayBean> wspReportSummayBeanList = mandatoryGrantDetailService.allWSPReportSummayBean(wsp,
					WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);

			List<PivotalTrainingReportBean> pivotalTrainingTrained = mandatoryGrantDetailService
					.allPivotalTrainingReport(wsp, WspReportEnum.ATR, HAJConstants.YES_ID);
			List<PivotalTrainingReportBean> pivotalTrainingPlaned = mandatoryGrantDetailService
					.allPivotalTrainingReportWSP(wsp, WspReportEnum.WSP, HAJConstants.YES_ID);
			// ??
			List<DGApplicationSummaryBean> dgApplicationSummaryBean = mandatoryGrantDetailService
					.allDGApplicationSummaryBean(wsp, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);

			WspSkillsRequirementsService wspSkillsRequirementsService = new WspSkillsRequirementsService();
			List<WspSkillsRequirements> wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(wsp);

			params.put("EmployeeProfileDataSource", new JRBeanCollectionDataSource(employeeProfileBeansList));
			params.put("EmployeeEquityDataSource", new JRBeanCollectionDataSource(employeeEquityProfileBeanList));
			params.put("ATRDataSource", new JRBeanCollectionDataSource(atrReportSummayBeanList));

			for (int x = 0; x < companyUsers.size(); x++) {
				CompanyUsers comUsers = companyUsers.get(x);

				if (comUsers.getCompanyUserType() != null) {
					companyUsers.remove(x);
					String type = super.getEntryLanguage(comUsers.getCompanyUserType().getType());
					if (comUsers.getCompanyUserType() == ConfigDocProcessEnum.SDF) {
						SDFCompany sdfCompany = SDFCompanyService.instance().findByCompanyAndUser(company,
								comUsers.getUser());
						if (sdfCompany != null) {
							type = sdfCompany.getSdfType().getDescription();
						}

					}
					comUsers.setCompanyUserTypeDes(type);
					companyUsers.add(x, comUsers);
				}

			}

			params.put("CompanyUsersDataSource", new JRBeanCollectionDataSource(companyUsers));
			params.put("TrainingCommDataSource", new JRBeanCollectionDataSource(trainingComittees));
			params.put("LinkedCompDataSource", new JRBeanCollectionDataSource(linkedCompanies));
			params.put("CompSiteDataSource", new JRBeanCollectionDataSource(sites));
			params.put("WspSkillsRequirementsListDataSource",
					new JRBeanCollectionDataSource(wspSkillsRequirementsList));
			params.put("WspSignoffsDataSource", new JRBeanCollectionDataSource(wsp.getWspSignoffs()));
			params.put("EmpEmploymentStatusListDataSource",
					new JRBeanCollectionDataSource(empEmploymentStatusBeanList));
			params.put("wspReportSummayBeanListDataSource", new JRBeanCollectionDataSource(wspReportSummayBeanList));

			params.put("PivotalTrainingTrainedDataSource", new JRBeanCollectionDataSource(pivotalTrainingTrained));
			params.put("PivotalTrainingPlanedDataSource", new JRBeanCollectionDataSource(pivotalTrainingPlaned));
			params.put("DGApplicationSummaryBeanDataSource", new JRBeanCollectionDataSource(dgApplicationSummaryBean));

			JasperService.instance().createReportFromDBtoServletOutputStream("GRANT_APPLICATION.jasper", params,
					company.getLevyNumber() + "_Grant_Application.pdf");

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Validation check to ensure primary SDF assigned to company for rejection to
	 * appeal
	 */
	public void prepRejectionToSdfToAppeal() {
		try {
			validateSDF(wsp.getCompany());
			runClientSideExecute("PF('rejectReasonFinal').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Validation check for primary SDF assigned
	 * 
	 * @param company
	 * @throws Exception
	 */
	private void validateSDF(Company company) throws Exception {
		SDFCompany primarySDF = companyService.findPrimarySDF(company);
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + company.getLevyNumber());
		}
	}

	/**
	 * Populates company history against wsp
	 * 
	 * @throws Exception
	 */
	private void populateCompanyHistoryWsp() throws Exception {
		if (wsp != null && wsp.getId() != null) {
			wspCompanyMainHistoryList = WspCompanyMainHistoryService.instance()
					.findByTargetClassAndTargetKeyOrderByLatest(wsp.getId(), wsp.getClass().getName());
			if (wspCompanyMainHistoryList.size() != 0) {
				historyFound = true;
				wspCompanyMainHistory = WspCompanyMainHistoryService.instance()
						.findByKey(wspCompanyMainHistoryList.get(0).getId());
				dataModelWspCompanyAddressHistoryInfo();
				dataModelWspCompanyEmployeesHistoryInfo();
				dataModelWspCompanyHistoryInfo();
				dataModelWspCompanyTrainingComitteeInfo();
				dataModelWspSdfCompanyHistoryInfo();
			}
		}
	}

	public void dataModelWspCompanyAddressHistoryInfo() {
		dataModelWspCompanyAddressHistory = new LazyDataModel<WspCompanyAddressHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyAddressHistory> list = new ArrayList<>();

			@Override
			public List<WspCompanyAddressHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					list = WspCompanyAddressHistoryService.instance()
							.allWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField,
									sortOrder, filters, wspCompanyMainHistory.getTargetKey(),
									wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyAddressHistory.setRowCount(WspCompanyAddressHistoryService.instance()
							.countAllWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(WspCompanyAddressHistory object) {
				return object.getId();
			}

			@Override
			public WspCompanyAddressHistory getRowData(String rowKey) {
				for (WspCompanyAddressHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void dataModelWspCompanyEmployeesHistoryInfo() {
		dataModelWspCompanyEmployeesHistory = new LazyDataModel<WspCompanyEmployeesHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyEmployeesHistory> list = new ArrayList<>();

			@Override
			public List<WspCompanyEmployeesHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					list = WspCompanyEmployeesHistoryService.instance()
							.allWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField,
									sortOrder, filters, wspCompanyMainHistory.getTargetKey(),
									wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyEmployeesHistory.setRowCount(WspCompanyEmployeesHistoryService.instance()
							.countAllWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(WspCompanyEmployeesHistory object) {
				return object.getId();
			}

			@Override
			public WspCompanyEmployeesHistory getRowData(String rowKey) {
				for (WspCompanyEmployeesHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void dataModelWspCompanyHistoryInfo() {
		try {
			if (wspCompanyMainHistory != null && wspCompanyMainHistory.getId() != null) {
				wspCompanyHistory = WspCompanyHistoryService.instance().findByTargetClassAndKeyAndMainLinkReturnOne(
						wspCompanyMainHistory.getTargetKey(), wspCompanyMainHistory.getTargetClass(),
						wspCompanyMainHistory);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		dataModelWspCompanyHistory = new LazyDataModel<WspCompanyHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyHistory> list = new ArrayList<>();

			@Override
			public List<WspCompanyHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					list = WspCompanyHistoryService.instance().allWspCompanyHistoryByTargetClassTargetKeyandMainId(
							first, pageSize, sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(),
							wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyHistory.setRowCount(WspCompanyHistoryService.instance()
							.countAllWspCompanyHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(WspCompanyHistory object) {
				return object.getId();
			}

			@Override
			public WspCompanyHistory getRowData(String rowKey) {
				for (WspCompanyHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void dataModelWspCompanyTrainingComitteeInfo() {
		dataModelWspCompanyTrainingComittee = new LazyDataModel<WspCompanyTrainingComitteeHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspCompanyTrainingComitteeHistory> list = new ArrayList<>();

			@Override
			public List<WspCompanyTrainingComitteeHistory> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = WspCompanyTrainingComitteeHistoryService.instance()
							.allWspCompanyTrainingComitteeHistoryByTargetClassTargetKeyandMainId(first, pageSize,
									sortField, sortOrder, filters, wspCompanyMainHistory.getTargetKey(),
									wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspCompanyTrainingComittee.setRowCount(WspCompanyTrainingComitteeHistoryService.instance()
							.countAllWspCompanyTrainingComitteeHistoryHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(WspCompanyTrainingComitteeHistory object) {
				return object.getId();
			}

			@Override
			public WspCompanyTrainingComitteeHistory getRowData(String rowKey) {
				for (WspCompanyTrainingComitteeHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void dataModelWspSdfCompanyHistoryInfo() {
		dataModelWspSdfCompanyHistory = new LazyDataModel<WspSdfCompanyHistory>() {
			private static final long serialVersionUID = 1L;
			private List<WspSdfCompanyHistory> list = new ArrayList<>();

			@Override
			public List<WspSdfCompanyHistory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					list = WspSdfCompanyHistoryService.instance()
							.allWspSdfCompanyHistoryHistoryByTargetClassTargetKeyandMainId(first, pageSize, sortField,
									sortOrder, filters, wspCompanyMainHistory.getTargetKey(),
									wspCompanyMainHistory.getTargetClass(), wspCompanyMainHistory);
					dataModelWspSdfCompanyHistory.setRowCount(WspSdfCompanyHistoryService.instance()
							.countAllWspSdfCompanyHistoryHistoryHistoryByTargetClassTargetKeyandMainId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(WspSdfCompanyHistory object) {
				return object.getId();
			}

			@Override
			public WspSdfCompanyHistory getRowData(String rowKey) {
				for (WspSdfCompanyHistory obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/**
	 * END For WSP.
	 *
	 * @return the data model
	 */

	public LazyDataModel<SDFCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SDFCompany> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the search user passport or id UI.
	 *
	 * @return the search user passport or id UI
	 */
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	/**
	 * Sets the search user passport or id UI.
	 *
	 * @param searchUserPassportOrIdUI the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * Gets the search company UI.
	 *
	 * @return the search company UI
	 */
	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	/**
	 * Sets the search company UI.
	 *
	 * @param searchCompanyUI the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	/**
	 * Gets the selected company.
	 *
	 * @return the selected company
	 */
	public SDFCompany getSelectedCompany() {
		return selectedCompany;
	}

	/**
	 * Sets the selected company.
	 *
	 * @param selectedCompany the new selected company
	 */
	public void setSelectedCompany(SDFCompany selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	/**
	 * Gets the company users.
	 *
	 * @return the company users
	 */
	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	/**
	 * Sets the company users.
	 *
	 * @param companyUsers the new company users
	 */
	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	/**
	 * Gets the company user.
	 *
	 * @return the company user
	 */
	public CompanyUsers getCompanyUser() {
		return companyUser;
	}

	/**
	 * Sets the company user.
	 *
	 * @param companyUser the new company user
	 */
	public void setCompanyUser(CompanyUsers companyUser) {
		this.companyUser = companyUser;
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(companyUser.getUser().getPostalAddress());
		}
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object the object
	 */
	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				Users user = (Users) object;
				companyUser = null;
				if (user.getId() != null) {
					companyUser = this.companyUsersService.findByUserAndCompany(selectedCompany.getCompany().getId(),
							user.getId());
				}
				if (user.getPostalAddress() == null) {
					user.setPostalAddress(new Address());
				}

				if (user.getResidentialAddress() == null) {
					user.setResidentialAddress(new Address());
				}

				if (companyUser == null) {
					this.companyUser = new CompanyUsers(user, selectedCompany.getCompany());
				} else {
					companyUser.setUser(user);
				}

			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Store file.
	 *
	 * @param event the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				doc.setWsp(wsp);
				wspService.documentUpload(wsp, getSessionUI().getActiveUser());
			} else {
				// if document is for company
				wspService.uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the wsp type enum for manual submission.
	 *
	 * @return the wsp type enumManual
	 */
	public List<SelectItem> getWspTypeEnumManual() {
		List<SelectItem> wspTypeEnumManual = new ArrayList<SelectItem>();
		try {
			wspTypeEnumManual.add(new SelectItem(WspTypeEnum.Mandatory, super.getEntryLanguage(WspTypeEnum.Mandatory.getRegistrationName())));
			wspTypeEnumManual.add(new SelectItem(WspTypeEnum.Both, super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wspTypeEnumManual;
	}
	
	/**
	 * Gets the wsp type enum DD.
	 *
	 * @return the wsp type enum DD
	 */
	public List<SelectItem> getWspTypeEnumDD() {
		List<SelectItem> wspTypeList = new ArrayList<SelectItem>();
		wspTypeList.add(new SelectItem(WspTypeEnum.Mandatory,
				super.getEntryLanguage(WspTypeEnum.Mandatory.getRegistrationName())));
		
//		int finYR=2022;
//		if (selectedCompany.getCompany().getNonLevyPaying() != null
//				&& selectedCompany.getCompany().getNonLevyPaying()) {
//			wspTypeList.add(new SelectItem(WspTypeEnum.Discretionary,
//					super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
//		} else {
//			try {
//				boolean canCreateNewWsp1 = ((wspService.countByCompanyAndFinYear(selectedCompany.getCompany(),
//						(finYear + 1)) == 0) ? true : false);
//				if(createWsp.getFinYear()!=null) {
//					finYR=createWsp.getFinYear();
//					if (finYR == finYear+1) {
//						wspTypeList.add(new SelectItem(WspTypeEnum.Mandatory,
//								super.getEntryLanguage(WspTypeEnum.Mandatory.getRegistrationName())));
////						if (selectedCompany.getCompany().getNonLevyPaying() != null
////								&& !selectedCompany.getCompany().getNonLevyPaying()) {
////							l.add(new SelectItem(WspTypeEnum.Both,
////								super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
////						}
//					}
//					else if (!belowThreshold) {
//						if (finYR==finYear+1) {
//							wspTypeList.add(new SelectItem(WspTypeEnum.Both,
//									super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
//						} else {
//							wspTypeList.add(new SelectItem(WspTypeEnum.Discretionary,
//									super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
//						}
//					} else {
//						wspTypeList.add(new SelectItem(WspTypeEnum.Discretionary,
//								super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
//					}
//				}else {
//					if(finYR==finYear+1 && !belowThreshold){
//						wspTypeList.add(new SelectItem(WspTypeEnum.Both,
//								super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
//					}
////					else {
////						l.add(new SelectItem(WspTypeEnum.Discretionary,
////								super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
////					}
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//
////			if (belowThreshold)
////			{
////				System.out.println("belowThreshold value:"+belowThreshold);
//////				/*
//////				 * l.add(new SelectItem(WspTypeEnum.Discretionary,
//////				 * super.getEntryLanguage(WspTypeEnum.Discretionary.getRegistrationName())));
//////				 */
////				l.add(new SelectItem(WspTypeEnum.Both,
////						super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
////			}
////			else
////			{
////				System.out.println("last else");
////				
////				l.add(new SelectItem(WspTypeEnum.Both, super.getEntryLanguage(WspTypeEnum.Both.getRegistrationName())));
////			}
//		}
		return wspTypeList;
	}

	/**
	 * Download Project Implementation Plan.
	 */
	public void downloadPIM() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js = new JasperService();
		try {

			// ********************************************
			String primary_sdf_fullname = "";
			String clo_fullname = "";
			String primary_sdf_subtion_date = "";
			String crm_fullname = "";
			String clo_aaproved_date = "";
			String crm_approved_date = "";
			String guid = wsp.getCompany().getCompanyGuid();

			params.put("primary_sdf_fullname", primary_sdf_fullname);
			params.put("clo_fullname", clo_fullname);
			params.put("primary_sdf_subtion_date", primary_sdf_subtion_date);
			params.put("crm_fullname", crm_fullname);
			params.put("clo_aaproved_date", clo_aaproved_date);
			params.put("clo_aaproved_date", clo_aaproved_date);
			params.put("crm_approved_date", crm_approved_date);
			params.put("guid", guid);
			// ********************************************

			ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
			List<ProjectImplementationPlan> list = new ArrayList<>();
			list = projectImplementationPlanService.findByWsp(wsp);
			if (list == null || list.size() <= 0) {
				list = new ArrayList<>();
			}
			params.put("PIMDataSource1", new JRBeanCollectionDataSource(list));

			List<ProjectImplementationPlan> dummyList = new ArrayList<>();
			for (int x = 0; x < 10; x++) {
				ProjectImplementationPlan imp = new ProjectImplementationPlan();
				dummyList.add(imp);
			}
			params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("Project_Implementation_Plan.jasper", params,
					"Project_Implementation_Plan.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download application form.
	 */
	public void downloadApplicationForm() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String path = HAJConstants.APP_PATH;
			// Creating Discretionary grant year to be displayed on the cover
			// page
			String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYear())
					.substring(Math.max(String.valueOf(wsp.getFinYear()).length() - 2, 0));
			String year = wsp.getFinYear() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
			// Adding Discretionary_Grant_Agreeement_details params

			RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
			if (regionService == null) {
				regionService = new RegionService();
			}
			Region r = regionService.findByKey(rt.getRegion().getId());
			// ***************************Dg Allocation
			// Data*****************************
			ArrayList<DgAllocation> dgList = new ArrayList<>();
			DgAllocationService dgAllocationService = new DgAllocationService();
			DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
			DgAllocationParent dgAllocationParent = null;
			if (wsp.getId() != null) {
				dgAllocationParent = dgAllocationParentService.findByWSP(wsp.getId());
				if (dgAllocationParent != null) {
					dgList = (ArrayList<DgAllocation>) dgAllocationService
							.findByParentWhereAmountAwarded(dgAllocationParent);
					// dgList=(ArrayList<DgAllocation>)
					// dgAllocationService.findByParent(dgAllocationParent);
				}

			}
			params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
			params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));
			/*
			 * double totalAwardedAmnt=0.00; for(DgAllocation dga:dgList) {
			 * if(dga.getTotalAwardAmount() !=null) { totalAwardedAmnt
			 * +=dga.getTotalAwardAmount().longValueExact(); } }
			 * params.put("total_awarded_amnt",totalAwardedAmnt);
			 */
			params.put("total_awarded_amnt",
					dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());
			// ***************************Mandatory Grant
			// Data*****************************
			/*
			 * andatoryGrantService mandatoryGrantService=new MandatoryGrantService();
			 * ArrayList<MandatoryGrant> mgList =new ArrayList<>();
			 * mgList=(ArrayList<MandatoryGrant>)
			 * mandatoryGrantService.findByWSPForDgVerificationIgnoreImport(wsp) ;
			 * params.put("AnnexureDataSource", new JRBeanCollectionDataSource(mgList));
			 */
			// params.put("AnnexureDataSource", new
			// JRBeanCollectionDataSource(dgList));
			// *********************************CEO**************************************
			HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
			List<Users> ceoList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
			Users ceo = null;
			if (ceoList.size() > 0) {
				ceo = ceoList.get(0);
			}

			String merSETA_CEO = "";
			if (ceo != null) {
				if (ceo.getTitle() != null) {
					TitleService titleService = new TitleService();
					Title tile = titleService.findByKey(ceo.getTitle().getId());
					merSETA_CEO = tile.getDescription() + " " + ceo.getLastName() + " " + ceo.getFirstName();
				} else {
					merSETA_CEO = ceo.getLastName() + " " + ceo.getFirstName();
				}

			}
			// **************************************************************************

			params.put("wsp", wsp);
			params.put("company", wsp.getCompany());
			params.put("wsp_id", wsp.getId());
			params.put("wsp_report", WspReportEnum.WSP.ordinal());
			params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
			params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
			params.put("region", r.getDescription());
			params.put("year", year);
			params.put("merSETA_CEO", merSETA_CEO);
			params.put("terminationDate", "30 September " + (wsp.getFinYear() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYear());
			params.put("projectedEndDate", "31 Mar " + (wsp.getFinYear() + 1));
			JasperService.addLogo(params);
			JasperService.addDiscretionaryGrantSubReports(params);
			String fileName = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear
					+ ")-Discretionary_Grant_Agreement.pdf";
			// Adding books
			if (wsp.getFinYearNonNull() > 2021){
				params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
			}
			else {
				params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
			}
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
			JasperService.instance().createReportFromDBtoServletOutputStream("DG_Agreement_Book.jasper", params,
					fileName);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepNewSignOff() {
		this.tempSignoff = new TempSignoff();
	}

	public void createDisputeSignOff() {
		try {
			wspService.createDisputeSignOff(dispute, tempSignoff, signOffTitle);
			disputes = wspDisputeService.findByWsp(wsp);
			this.dispute = wspDisputeService.findByKey(dispute.getId());
			prepNewSignOff();
			super.runClientSideExecute("PF('dlgSearchUser').hide()");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sdfCompanyHistortInfo() {
		try {
			SDFCompanyHistoryService sdfCompanyHistoryService = new SDFCompanyHistoryService();
			sdfCompanyHistory = sdfCompanyHistoryService.allSDFCompanyHistoryWithresolve(company);
			sdfHistory = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void sdfDetail() {
		usersDataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSDFForCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters,
							company, getSessionUI().getActiveUser());
					usersDataModel.setRowCount(
							service.allSDFForCompanyCount(filters, company, getSessionUI().getActiveUser()).intValue());

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public void saveWSPBeforeSubmisisonWithSignoff() {
		try {
			if (getNow().before(closeOffDate)) {
				wspService.runValidiationCheckOnWsp(wsp, getSessionUI().getActiveUser());
				runClientSideExecute("PF('grantSubWithSignOffDlg').show()");
			} else {
				wspService.saveWSP(wsp, getSessionUI().getActiveUser(), true);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				super.ajaxRedirectToDashboard();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void saveWSPSignoff() {
		try {
			if (getNow().before(closeOffDate)) {
				if (getSessionUI().isAllowableInternalUser()){
					List<WspSignoff> updatedWspSignoffs = new ArrayList<WspSignoff>(); 
					for (WspSignoff wspSignoff : wsp.getWspSignoffs()) {
						wspSignoff.setAccept(true);
						wspSignoff.setSignOffDate(wspSignoff.getSignOffDate());
						updatedWspSignoffs.add(wspSignoff);
					}
					wsp.setWspSignoffs(updatedWspSignoffs);
					wsp.setManuallyAdded(true);
					wsp.setManuallySubmissionDate(wsp.getCreateDate());
					wspService.submitWSPWithSignOffManually(wsp, getSessionUI().getActiveUser(), true);
				} else {
					wspService.submitWSPWithSignOff(wsp, getSessionUI().getActiveUser(), true);
				}
				if (wsp.getWspType() == WspTypeEnum.Discretionary)
					wspService.sendAcknowledgementOfSubmissionEmail(getSessionUI().getActiveUser(), wsp.getCompany());
				else
					wspService.sendAcknowledgementOfSubmission(getSessionUI().getActiveUser(), wsp.getCompany());
			} else {
				wspService.saveWSP(wsp, getSessionUI().getActiveUser(), true);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (ValidationErrorException e) {
			this.errors = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the copy address.
	 *
	 * @return the copy address
	 */
	public Boolean getCopyAddress() {
		return copyAddress;
	}

	/**
	 * Sets the copy address.
	 *
	 * @param copyAddress the new copy address
	 */
	public void setCopyAddress(Boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	/**
	 * Gets the wsp list.
	 *
	 * @return the wsp list
	 */
	public List<Wsp> getWspList() {
		return wspList;
	}

	/**
	 * Sets the wsp list.
	 *
	 * @param wspList the new wsp list
	 */
	public void setWspList(List<Wsp> wspList) {
		this.wspList = wspList;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the linked companies.
	 *
	 * @return the linked companies
	 */
	public List<Company> getLinkedCompanies() {
		return linkedCompanies;
	}

	/**
	 * Sets the linked companies.
	 *
	 * @param linkedCompanies the new linked companies
	 */
	public void setLinkedCompanies(List<Company> linkedCompanies) {
		this.linkedCompanies = linkedCompanies;
	}

	/**
	 * Gets the sites.
	 *
	 * @return the sites
	 */
	public List<Sites> getSites() {
		return sites;
	}

	/**
	 * Sets the sites.
	 *
	 * @param sites the new sites
	 */
	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	/**
	 * Gets the banking details.
	 *
	 * @return the banking details
	 */
	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	/**
	 * Sets the banking details.
	 *
	 * @param bankingDetails the new banking details
	 */
	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	/**
	 * Gets the training comittees.
	 *
	 * @return the training comittees
	 */
	public List<TrainingComittee> getTrainingComittees() {
		return trainingComittees;
	}

	/**
	 * Sets the training comittees.
	 *
	 * @param trainingComittees the new training comittees
	 */
	public void setTrainingComittees(List<TrainingComittee> trainingComittees) {
		this.trainingComittees = trainingComittees;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Gets the impact of staff training.
	 *
	 * @return the impact of staff training
	 */
	public ImpactOfStaffTraining getImpactOfStaffTraining() {
		return impactOfStaffTraining;
	}

	/**
	 * Sets the impact of staff training.
	 *
	 * @param impactOfStaffTraining the new impact of staff training
	 */
	public void setImpactOfStaffTraining(ImpactOfStaffTraining impactOfStaffTraining) {
		this.impactOfStaffTraining = impactOfStaffTraining;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(String errors) {
		this.errors = errors;
	}

	/**
	 * Gets the wsp checklist.
	 *
	 * @return the wsp checklist
	 */
	public WspChecklist getWspChecklist() {
		return wspChecklist;
	}

	/**
	 * Sets the wsp checklist.
	 *
	 * @param wspChecklist the new wsp checklist
	 */
	public void setWspChecklist(WspChecklist wspChecklist) {
		this.wspChecklist = wspChecklist;
	}

	/**
	 * Gets the dispute reason.
	 *
	 * @return the dispute reason
	 */
	public String getDisputeReason() {
		return disputeReason;
	}

	/**
	 * Sets the dispute reason.
	 *
	 * @param disputeReason the new dispute reason
	 */
	public void setDisputeReason(String disputeReason) {
		this.disputeReason = disputeReason;
	}

	/**
	 * Gets the disputes.
	 *
	 * @return the disputes
	 */
	public List<WspDispute> getDisputes() {
		return disputes;
	}

	/**
	 * Sets the disputes.
	 *
	 * @param disputes the new disputes
	 */
	public void setDisputes(List<WspDispute> disputes) {
		this.disputes = disputes;
	}

	/**
	 * Gets the dispute.
	 *
	 * @return the dispute
	 */
	public WspDispute getDispute() {
		return dispute;
	}

	/**
	 * Sets the dispute.
	 *
	 * @param dispute the new dispute
	 */
	public void setDispute(WspDispute dispute) {
		this.dispute = dispute;
	}

	public boolean isUpdateCompanyData() {
		return updateCompanyData;
	}

	public void setUpdateCompanyData(boolean updateCompanyData) {
		this.updateCompanyData = updateCompanyData;
	}

	public boolean isUpdateUserData() {
		return updateUserData;
	}

	public void setUpdateUserData(boolean updateUserData) {
		this.updateUserData = updateUserData;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
	}

	public Integer getPreviousFinYear() {
		return previousFinYear;
	}

	public void setPreviousFinYear(Integer previousFinYear) {
		this.previousFinYear = previousFinYear;
	}

	public Integer getNextFinYear() {
		return nextFinYear;
	}

	public void setNextFinYear(Integer nextFinYear) {
		this.nextFinYear = nextFinYear;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public boolean isPrimarySDF() {
		return primarySDF;
	}

	public void setPrimarySDF(boolean primarySDF) {
		this.primarySDF = primarySDF;
	}

	public boolean isNonMersetaNonLevyPaying() {
		return nonMersetaNonLevyPaying;
	}

	public void setNonMersetaNonLevyPaying(boolean nonMersetaNonLevyPaying) {
		this.nonMersetaNonLevyPaying = nonMersetaNonLevyPaying;
	}

	public RegionTown getRt() {
		return rt;
	}

	public void setRt(RegionTown rt) {
		this.rt = rt;
	}

	public TempSignoff getTempSignoff() {
		return tempSignoff;
	}

	public void setTempSignoff(TempSignoff tempSignoff) {
		this.tempSignoff = tempSignoff;
	}

	public String getSignOffTitle() {
		return signOffTitle;
	}

	public void setSignOffTitle(String signOffTitle) {
		this.signOffTitle = signOffTitle;
	}

	public boolean isBelowThreshold() {
		return belowThreshold;
	}

	public void setBelowThreshold(boolean belowThreshold) {
		this.belowThreshold = belowThreshold;
	}

	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public boolean isShowDetails() {
		return showDetails;
	}

	public void setShowDetails(boolean showDetails) {
		this.showDetails = showDetails;
	}

	public boolean isShowSubmitButton() {
		return showSubmitButton;
	}

	public void setShowSubmitButton(boolean showSubmitButton) {
		this.showSubmitButton = showSubmitButton;
	}

	public boolean isCanInitiate() {
		return canInitiate;
	}

	public void setCanInitiate(boolean canInitiate) {
		this.canInitiate = canInitiate;
	}

	public Date getCloseOffDate() {
		return closeOffDate;
	}

	public void setCloseOffDate(Date closeOffDate) {
		this.closeOffDate = closeOffDate;
	}

	public boolean isRejectReasonFound() {
		return rejectReasonFound;
	}

	public void setRejectReasonFound(boolean rejectReasonFound) {
		this.rejectReasonFound = rejectReasonFound;
	}

	public boolean isPrimarySdfForComapny() {
		return primarySdfForComapny;
	}

	public void setPrimarySdfForComapny(boolean primarySdfForComapny) {
		this.primarySdfForComapny = primarySdfForComapny;
	}

	public boolean isCanReopen() {
		return canReopen;
	}

	public void setCanReopen(boolean canReopen) {
		this.canReopen = canReopen;
	}

	public int getDaysBetweenDays() {
		return daysBetweenDays;
	}

	public void setDaysBetweenDays(int daysBetweenDays) {
		this.daysBetweenDays = daysBetweenDays;
	}

	public boolean isExtensionFound() {
		return extensionFound;
	}

	public void setExtensionFound(boolean extensionFound) {
		this.extensionFound = extensionFound;
	}

	public boolean isViewWspExtensionRequests() {
		return viewWspExtensionRequests;
	}

	public void setViewWspExtensionRequests(boolean viewWspExtensionRequests) {
		this.viewWspExtensionRequests = viewWspExtensionRequests;
	}

	public ExtensionRequest getSelectedExtensionRequest() {
		return selectedExtensionRequest;
	}

	public void setSelectedExtensionRequest(ExtensionRequest selectedExtensionRequest) {
		this.selectedExtensionRequest = selectedExtensionRequest;
	}

	public String getTermsAndConditionsLink() {
		return termsAndConditionsLink;
	}

	public void setTermsAndConditionsLink(String termsAndConditionsLink) {
		this.termsAndConditionsLink = termsAndConditionsLink;
	}

	public String getTermsAndConditionsLink1() {
		return termsAndConditionsLink1;
	}

	public void setTermsAndConditionsLink1(String termsAndConditionsLink1) {
		this.termsAndConditionsLink1 = termsAndConditionsLink1;
	}

	public int getPreviousYear() {
		return previousYear;
	}

	public void setPreviousYear(int previousYear) {
		this.previousYear = previousYear;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	/**
	 * Get an instance of current date, no idea why this is synchronized but has
	 * been left so from original e-port code.
	 *
	 * @return the synchronized date
	 */
	protected synchronized Date getSynchronizedDate() {
		return new Date();
	}

	public boolean isCanCreateNewWsp() {
		return canCreateNewWsp;
	}

	public void setCanCreateNewWsp(boolean canCreateNewWsp) {
		this.canCreateNewWsp = canCreateNewWsp;
	}

	public int getCurrentYearSmall() {
		return currentYearSmall;
	}

	public void setCurrentYearSmall(int currentYearSmall) {
		this.currentYearSmall = currentYearSmall;
	}

	public int getPreviousYearSmall() {
		return previousYearSmall;
	}

	public void setPreviousYearSmall(int previousYearSmall) {
		this.previousYearSmall = previousYearSmall;
	}

	/**
	 * @return the sdfCompanyHistory
	 */
	public List<SDFCompanyHistory> getSdfCompanyHistory() {
		return sdfCompanyHistory;
	}

	/**
	 * @param sdfCompanyHistory the sdfCompanyHistory to set
	 */
	public void setSdfCompanyHistory(List<SDFCompanyHistory> sdfCompanyHistory) {
		this.sdfCompanyHistory = sdfCompanyHistory;
	}

	/**
	 * @return the sdfHistory
	 */
	public boolean isSdfHistory() {
		return sdfHistory;
	}

	/**
	 * @param sdfHistory the sdfHistory to set
	 */
	public void setSdfHistory(boolean sdfHistory) {
		this.sdfHistory = sdfHistory;
	}

	/**
	 * @return the usersDataModel
	 */
	public LazyDataModel<SDFCompany> getUsersDataModel() {
		return usersDataModel;
	}

	/**
	 * @param usersDataModel the usersDataModel to set
	 */
	public void setUsersDataModel(LazyDataModel<SDFCompany> usersDataModel) {
		this.usersDataModel = usersDataModel;
	}

	public WspTypeEnum getWspTypeEnum() {
		return wspTypeEnum;
	}

	public void setWspTypeEnum(WspTypeEnum wspTypeEnum) {
		this.wspTypeEnum = wspTypeEnum;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Wsp> getPreviousCompanyWspList() {
		return previousCompanyWspList;
	}

	public void setPreviousCompanyWspList(List<Wsp> previousCompanyWspList) {
		this.previousCompanyWspList = previousCompanyWspList;
	}

	public boolean isPreviousCompanyFound() {
		return previousCompanyFound;
	}

	public void setPreviousCompanyFound(boolean previousCompanyFound) {
		this.previousCompanyFound = previousCompanyFound;
	}

	public Company getPreviousCompany() {
		return previousCompany;
	}

	public void setPreviousCompany(Company previousCompany) {
		this.previousCompany = previousCompany;
	}

	public boolean isHistoryFound() {
		return historyFound;
	}

	public void setHistoryFound(boolean historyFound) {
		this.historyFound = historyFound;
	}

	public WspCompanyMainHistory getWspCompanyMainHistory() {
		return wspCompanyMainHistory;
	}

	public void setWspCompanyMainHistory(WspCompanyMainHistory wspCompanyMainHistory) {
		this.wspCompanyMainHistory = wspCompanyMainHistory;
	}

	public WspCompanyHistory getWspCompanyHistory() {
		return wspCompanyHistory;
	}

	public void setWspCompanyHistory(WspCompanyHistory wspCompanyHistory) {
		this.wspCompanyHistory = wspCompanyHistory;
	}

	public LazyDataModel<WspCompanyAddressHistory> getDataModelWspCompanyAddressHistory() {
		return dataModelWspCompanyAddressHistory;
	}

	public void setDataModelWspCompanyAddressHistory(
			LazyDataModel<WspCompanyAddressHistory> dataModelWspCompanyAddressHistory) {
		this.dataModelWspCompanyAddressHistory = dataModelWspCompanyAddressHistory;
	}

	public LazyDataModel<WspCompanyEmployeesHistory> getDataModelWspCompanyEmployeesHistory() {
		return dataModelWspCompanyEmployeesHistory;
	}

	public void setDataModelWspCompanyEmployeesHistory(
			LazyDataModel<WspCompanyEmployeesHistory> dataModelWspCompanyEmployeesHistory) {
		this.dataModelWspCompanyEmployeesHistory = dataModelWspCompanyEmployeesHistory;
	}

	public LazyDataModel<WspCompanyHistory> getDataModelWspCompanyHistory() {
		return dataModelWspCompanyHistory;
	}

	public void setDataModelWspCompanyHistory(LazyDataModel<WspCompanyHistory> dataModelWspCompanyHistory) {
		this.dataModelWspCompanyHistory = dataModelWspCompanyHistory;
	}

	public LazyDataModel<WspCompanyTrainingComitteeHistory> getDataModelWspCompanyTrainingComittee() {
		return dataModelWspCompanyTrainingComittee;
	}

	public void setDataModelWspCompanyTrainingComittee(
			LazyDataModel<WspCompanyTrainingComitteeHistory> dataModelWspCompanyTrainingComittee) {
		this.dataModelWspCompanyTrainingComittee = dataModelWspCompanyTrainingComittee;
	}

	public LazyDataModel<WspSdfCompanyHistory> getDataModelWspSdfCompanyHistory() {
		return dataModelWspSdfCompanyHistory;
	}

	public void setDataModelWspSdfCompanyHistory(LazyDataModel<WspSdfCompanyHistory> dataModelWspSdfCompanyHistory) {
		this.dataModelWspSdfCompanyHistory = dataModelWspSdfCompanyHistory;
	}

	public List<WspCompanyMainHistory> getWspCompanyMainHistoryList() {
		return wspCompanyMainHistoryList;
	}

	public void setWspCompanyMainHistoryList(List<WspCompanyMainHistory> wspCompanyMainHistoryList) {
		this.wspCompanyMainHistoryList = wspCompanyMainHistoryList;
	}

	public boolean isViewWspTasks() {
		return viewWspTasks;
	}

	public void setViewWspTasks(boolean viewWspTasks) {
		this.viewWspTasks = viewWspTasks;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public List<WspRejectionInformation> getWspRejectionReasonsList() {
		return wspRejectionReasonsList;
	}

	public void setWspRejectionReasonsList(List<WspRejectionInformation> wspRejectionReasonsList) {
		this.wspRejectionReasonsList = wspRejectionReasonsList;
	}

	public Wsp getCreateWsp() {
		return createWsp;
	}

	public void setCreateWsp(Wsp createWsp) {
		this.createWsp = createWsp;
	}

	private void checkIfAfterFebForGrantInitiation(Integer finYear, Date now) throws Exception{
		Date grantStartDate = GenericUtility.getStartOfDay(GenericUtility.sdf7.parse(NSDMSConfiguration.getString("grant.start.date") + (finYear - 1)));
		if (GenericUtility.getEndOfDay(now).before(grantStartDate)) {
			throw new Exception("You can only initaite a application for financial year " + finYear + " on: " + GenericUtility.sdf7.format(grantStartDate) );
		}
	}
}
