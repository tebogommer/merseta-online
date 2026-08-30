package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationSites;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.Sites;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgVerificationService;
import haj.com.service.DgVerificationSitesService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

//TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "discretionarygrantverificationUI")
@ViewScoped
public class DiscretionaryGrantVerificationUI extends AbstractUI {

	private MandatoryGrant mandatoryGrant;
	private MandatoryGrantDetail mandatoryGrantDetail;
	private WspReportEnum wspReport;
	private Company company;
	private Wsp wsp;
	private DgVerification dgVerification;
	private Sites site;
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private MandatoryGrantDetailService mandatoryGrantService = new MandatoryGrantDetailService();
	private MandatoryGrantService grantService = new MandatoryGrantService();
	private CompanyService companyService = new CompanyService();
	private Company selectedCompany = null;
	private DgVerificationService dgVerificationService = new DgVerificationService();
	private List<MandatoryGrantDetail> mandatoryGrants;
	private LazyDataModel<MandatoryGrant> dataModel;
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<Wsp> wspDataModel;
	private LazyDataModel<DgVerification> lazyDataModel;
	private List<Wsp> wspList;
	private WspService wspService = new WspService();
	private List<Sites> sites;
	private SitesService sitesService = new SitesService();
	private SignoffService signoffService = new SignoffService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private DgVerificationSitesService dgVerificationSitesService = new DgVerificationSitesService();
	private DgVerificationSites dgVerificationSites;
	private List<DgVerificationSites> dgVerificationSitesList;
	private Doc doc;
	private MandatoryGrantRecommendation mandatoryGrantRecommendation;
	private MandatoryGrantRecommendationService grantRecommendationService = new MandatoryGrantRecommendationService();
	private WorkPlaceApproval workPlaceApproval;
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	private boolean disableAll;
	private Boolean disableSignOffButton;
	private Boolean disableEdit;
	private Integer displayCheckInt = 0;
	private Boolean crmCloUser = false;
	private RejectReasons rejectReason;
	private List<RejectReasons> selectedRejectReason;
	private List<RejectReasonsChild> rejectReasonsChild;
	private boolean viewLastestFinYear = false;
	private Integer finYear;
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	private boolean nonCreditBearing;
	private Boolean errorEncountered = false;
	private String errors = "";

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
		wspReport = WspReportEnum.WSP;
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_VERIFICATION && super.getParameter("id", false) != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			dgVerification = dgVerificationService.findByKey(getSessionUI().getTask().getTargetKey());
			this.wsp = dgVerification.getWsp();
			selcteWsp();
		} else {
			finYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
			identifyUserLevel();
			companyInfo();
//			wspInfo();
		}
	}

	/**
	 * Identifys if the user is either:
	 * false - general user
	 * true - Clo or CRM
	 * @throws Exception
	 */
	private void identifyUserLevel() throws Exception{
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		// no client users should be able to view DG Verifications
		if (hce == null) {
			super.redirectToDashboard();
		} else {
			crmCloUser = RegionTownService.instance().checkIfCrmCloUser(hce);
		}
	}
	
	public void selectDeselectFinYearFilter(){
		try {
			companyInfo();
			selectedCompany = null;
			dataModel = null;
			if (viewLastestFinYear) {
				addInfoMessage("Filter Applied");
			} else {
				addInfoMessage("Filter Removed");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/**
	 * Get all SiteVisitReport for data table
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 */
	public void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						if (viewLastestFinYear) {
							retorno = companyService.allCompanyByCloCrmByDgFinYear(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser(),finYear);
							companyDataModel.setRowCount(companyService.countRegionDgFinYear(Company.class, filters));
						} else {
							filters.remove("FinYear");
							retorno = companyService.allCompanyByCloCrm(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
							companyDataModel.setRowCount(companyService.countRegion(Company.class, filters));
						}
					} else {
						if (viewLastestFinYear) {
							retorno = companyService.allCompanyByWspYearForDg(Company.class, first, pageSize, sortField, sortOrder, filters, finYear);
							companyDataModel.setRowCount(companyService.countAllCompanyByWspYearForDg(Company.class, filters));
						} else {
							filters.remove("FinYear");
							retorno = companyService.allCompanysLevyNotNull(Company.class, first, pageSize, sortField, sortOrder, filters);
							companyDataModel.setRowCount(companyService.countAllCompanysLevyNotNull(Company.class, filters));
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
	}
	
	public void locateDgVerificationByCompany(){
		try {
			dataModel = null;
			wspInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void wspInfo() throws Exception {
		lazyDataModel = new LazyDataModel<DgVerification>() {

			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();

			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgVerificationService.allDgVerificationByCompany(first, pageSize, sortField, sortOrder, filters, selectedCompany);
					lazyDataModel.setRowCount(dgVerificationService.countAllDgVerificationByCompany(filters));
//					if (crmCloUser) {
//						retorno = dgVerificationService.allDgVerificationByCloCrm(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
//						lazyDataModel.setRowCount(dgVerificationService.countRegion(filters));
//					} else {
//						retorno = dgVerificationService.allDgVerification(DgVerification.class, first, pageSize, sortField, sortOrder, filters);
//						lazyDataModel.setRowCount(dgVerificationService.count(DgVerification.class, filters));
//					}

				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DgVerification obj) {
				return obj.getId();
			}

			@Override
			public DgVerification getRowData(String rowKey) {
				for (DgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	public void prepVerificationSite() {
		try {
			dgVerificationSites = new DgVerificationSites(dgVerification, site);
			dgVerificationSitesList = dgVerificationSitesService.findDgAndSite(dgVerification, site);
			mandatoryGrants = mandatoryGrantService.findByWSPHosting(wsp, wspReport, HAJConstants.DISC_FUNDING_ID);
			mandatoryGrants.forEach(mg -> {
				try {
					mg.setGrantRecommendations(grantRecommendationService.findByMG(mandatoryGrant));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * This is new developement based on the site visit check
	 * 
	 * Info:
	 * 0 - Can Continue with verification 
	 * 1 - No Site Visit Found in the last 6 months
	 * 2 - Site visit found however not approved
	 */
	private void dgVerificationSiteVisitCheck() throws Exception{
		if (dgVerification.getStatus() == null) {
			displayCheckInt = 0;
//			displayCheckInt = SiteVisitReportService.instance().checkSixMonthRequirements(dgVerification,company);
//			displayCheckInt = siteVisitService.checkSixMonthRequirement(dgVerification);
		} else {
			displayCheckInt = 0;
		}
	}

	public void prepWorkPlaceApproval() {
		if (mandatoryGrant.getWorkPlaceApproval() != null) this.workPlaceApproval = mandatoryGrant.getWorkPlaceApproval();
		else this.workPlaceApproval = new WorkPlaceApproval(mandatoryGrant);
	}

	public void saveVerificationSite() {
		try {
			dgVerificationSitesService.create(dgVerificationSites);
			prepVerificationSite();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void selcteWsp(SelectEvent event) {
		try {
			this.wsp = dgVerification.getWsp();
			disableAll = dgVerification.getStatus() != null;
			dgVerification.setSignOffs(signoffService.findByDGVerifivcation(dgVerification));
			dgVerification.getSignOffs().forEach(sign -> {
				if (disableSignOffButton == null || !disableSignOffButton) {
					disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
				}
			});
			dgVerificationService.prepareNewDocs(dgVerification);
			pivitolInfoWsp();
			dgVerificationSitesList = dgVerificationSitesService.findDgVerification(dgVerification);
			selectedRejectReason = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
			e.printStackTrace();
		}
	}

	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selcteWsp() {
		try {
			this.wsp = dgVerification.getWsp();
			disableAll = dgVerification.getStatus() != null;
			// check if clo doing DG verification
			if (disableAll == false) {
				RegionTown rt = RegionTownService.instance().findByTown(this.wsp.getCompany().getResidentialAddress().getTown());
//				System.out.println("Session User ID: "+ getSessionUI().getUser().getId());
//				System.out.println("Session Active User ID: "+ getSessionUI().getActiveUser().getId());
//				System.out.println("Region Town CLO User ID: "+ rt.getClo().getUsers().getId());
				if (!getSessionUI().getUser().getId().equals(rt.getClo().getUsers().getId())) {
					disableAll = true;
				}
				rt = null;
			}
			dgVerification.setSignOffs(signoffService.findByDGVerifivcation(dgVerification));
			dgVerification.getSignOffs().forEach(sign -> {
				if (disableSignOffButton == null || !disableSignOffButton) {
					disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
				}
			});
			dgVerificationService.prepareNewDocs(dgVerification);
			pivitolInfoWsp();
			dgVerificationSitesList = dgVerificationSitesService.findDgVerification(dgVerification);
			dgVerificationService.populateCloCrmRejectionReasons(dgVerification);
			// locateWspDgVerification();
			dgVerificationSiteVisitCheck();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setDgVerification(dgVerification);
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

	public List<RejectReasons> getCloRejectionReasons(){
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
			try {
				l= rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(dgVerification.getId(), DgVerification.class.getName());
			} catch (Exception e) {
				addErrorMessage(e.getMessage(),e);
			}
		return l;
	}
	
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
//			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.DG_VERIFICATION);
			l = rejectReasonsService.findByProcessAndSoftDelete(ConfigDocProcessEnum.DG_VERIFICATION, false);
//			for(RejectReasons rejectReason : l) {
//				if(rejectReason.getDescription().equals(RejectReasonsEnum.AdditionalInformationSupplied.getFriendlyName())){
//					if(!getSessionUI().getRole().equals(UserRoleEnum.ClientRelationsManager.getFriendlyName())){
//						l.remove(l);
//					}
//				}
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getRejectReasonsCLO() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
//			l = rejectReasonsService.findByProcessAndBooleanSelection(ConfigDocProcessEnum.DG_VERIFICATION, false);
			l = rejectReasonsService.findByProcessAndBooleanSelectionAndSoftDelete(ConfigDocProcessEnum.DG_VERIFICATION, false, false);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<InterventionType> getInterventionType() {
		InterventionTypeService interventionTypeService = new InterventionTypeService();
		List<InterventionType> aList = null;
		try {
			aList = interventionTypeService.allInterventionType();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return aList;
	}

	public void completeTask() {
		try {
			dgVerificationService.completeTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveTask() {
		try {
			dgVerificationService.approveTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void crmApproveTaskNotification() {
		try {
			errors = dgVerificationService.validiationOnMandatoryGrantSubmission(dgVerification);
			if (errors.equals("")) {
				errorEncountered = false;
				dgVerificationService.crmApproveTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
				super.ajaxRedirectToDashboard();
			} else {
				errorEncountered = true;
				addErrorMessage("Validation Errors, please check under Validation Errors to action");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sdfAppealApplicationTaskNotification() {
		try {
			dgVerificationService.sdfAppealApplicationTaskNotification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), false);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveTask() {
		try {
			dgVerificationService.finalApproveTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * SDF accepts the verification,
	 * then checks if allocation generation can commence.
	 */
	public void acceptVerification() {
		try {
			dgVerificationService.acceptVerification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void acceptVerificationRejection() {
		try {
			dgVerificationService.acceptVerificationRejection(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void appealVerificationRejection() {
		try {
			dgVerificationService.acceptVerification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void declineVerification() {
		try {
			dgVerificationService.declineVerification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void declineAllVerification() {
		try {
			dgVerificationService.declineAllVerification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * @return boolean
	 */
	public boolean enableIfCurrentUserIsCLO() {
		
		boolean enable = false;
		try {
//			if(getSessionUI().getRole().getDescription().trim().equals(UserRoleEnum.ClientRelationsManager.getFriendlyName())){
//				enable = true;
//			}else {
//				enable = false;
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return enable;
	}
	
	public boolean enableIfCurrentUserNotSDF() {
		
		boolean enable = false;
		try {
			
			List<Company> sdfCompanyList = sdfCompanyService.findByUser(getSessionUI().getActiveUser());
			if(sdfCompanyList != null && sdfCompanyList.size() > 0) {
				enable = false;
			}else {
				enable = true;
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return enable;
	}
	
	public void withdrawnVerification() {
		try {
			dgVerificationService.withdrawnVerification(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void crmRejectApplicationNotifySDF() {
		try {
			dgVerificationService.crmRejectApplicationNotifySDF(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectReason, getSessionUI().getRole());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void rejectTask() {
		try {
			dgVerificationService.rejectTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectTask() {
		try {
			dgVerificationService.finalRejectTask(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void checkFinalReponseProvided() throws Exception{
		if (dgVerification.getFinalResponse() == null || dgVerification.getFinalResponse() == "") {
			throw new Exception("Provide Final Response Before Proceeding");
		}
	}
	
	public void finalApproveApplication() {
		try {
			checkFinalReponseProvided();
			dgVerificationService.finalApproveApplication(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), true);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectApplication() {
		try {
			checkFinalReponseProvided();
			dgVerificationService.finalRejectApplication(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), false);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * By wsp selection
	 */
	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(wsp.getCompany().getId());
		sites = sitesService.findByCompany(company);
		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = grantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, wsp);
					retorno.forEach(mg -> {
						try {
							mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
							// mg.setWorkPlaceApproval(workPlaceApprovalService.findByDiscretionaryGrant(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					dataModel.setRowCount(grantService.count(MandatoryGrant.class, filters, wsp));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}

			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	public void signOffVerification() {
		try {
			if (dgVerification.getDateCaptured() == null) {
				dgVerification.setDateCaptured(new Date());
			}
//			dgVerificationService.saveSignOff(dgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			// locateWspMgVerification();
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateMandatoryGrantRecommendation() {
		try {
			mandatoryGrantRecommendation = new MandatoryGrantRecommendation(mandatoryGrant, getSessionUI().getActiveUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveRecommendation() {
		try {
			boolean check = checkOnRecommendations();
			if (check) {
				cloActionUpdate();
				grantRecommendationService.clearUpdateAdditionalEntriesBeforeSaving(mandatoryGrant, mandatoryGrantRecommendation, skillsSet, skillsProgram, shortCreditBearing, nonCreditBearing);
				grantRecommendationService.create(mandatoryGrantRecommendation);
				generateMandatoryGrantRecommendation();
				addInfoMessage("Update Succesful");
			}
			runClientSideExecute("PF('dlgRecommend').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Check if necessary fields provided
	 * @throws Exception
	 */
	private Boolean checkOnRecommendations() throws Exception {
		Boolean hasData = true;
		if (mandatoryGrantRecommendation.getQualification() != null || mandatoryGrantRecommendation.getQuantity() != null || mandatoryGrantRecommendation.getInterventionType() != null) {
			if (mandatoryGrantRecommendation.getRejectReasons() == null) {
				throw new Exception("Provide a reason for rejection.");
			}
		}
		
		if (mandatoryGrantRecommendation.getQualification() == null && mandatoryGrantRecommendation.getQuantity() == null && mandatoryGrantRecommendation.getInterventionType() == null) {
			hasData = false;
			mandatoryGrantRecommendation.setRejectReasons(null);
			useGrantDataForRecommendation();
		}
		return hasData;
	}
	
	private void cloActionUpdate() throws Exception{
		if (dgVerification != null && (dgVerification.getCloAlteration() == null || dgVerification.getCloAlteration() == false)) {
			dgVerification.setCloAlteration(true);
			dgVerificationService.update(dgVerification);
		}
	}

	public void useGrantDataForRecommendation() {
		try {
			cloActionUpdate();
			grantRecommendationService.clearUpdateAdditionalEntriesBeforeSaving(mandatoryGrant, mandatoryGrantRecommendation, skillsSet, skillsProgram, shortCreditBearing, nonCreditBearing);
//			mandatoryGrantRecommendation.setQualification(mandatoryGrant.getQualification());
//			mandatoryGrantRecommendation.setQuantity(mandatoryGrant.getAmount());
			grantRecommendationService.create(mandatoryGrantRecommendation);
			generateMandatoryGrantRecommendation();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create / Update DG Verification submit button and relocate DG Verification by
	 * WSP
	 */
	public void updateDgVerification() {
		try {
			updateDg();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void updateDgVerificationWithSignOff() {
		try {
			errors = dgVerificationService.validiationOnMandatoryGrantSubmission(dgVerification);
			if (errors.equals("")) {
				errorEncountered = false;
				dgVerification.setCloRecommendation(CloRecommendationEnum.Approval);
				updateDgWithSignOff();
				// locateWspDgVerification();
				addInfoMessage("Update Succesful");
				disableAll = dgVerification.getStatus() != null;
			} else {
				errorEncountered = true;
				addErrorMessage("Validation Errors, please check under Validation Errors to action");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDgVerificationWithSignOffRecommendedReject() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Provide A Reason For Rejection");
			}
			dgVerification.setCloRecommendation(CloRecommendationEnum.Rejection);
			updateDgWithSignOff();
			// locateWspDgVerification();
			addInfoMessage("Update Succesful");
			disableAll = dgVerification.getStatus() != null;
			runClientSideExecute("PF('rejectReasonCrm').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void updateApproval() throws Exception {
		if (workPlaceApproval.getDateCaptured() == null) {
			workPlaceApproval.setDateCaptured(new Date());
		}
		workPlaceApprovalService.create(workPlaceApproval);
	}

	/**
	 * Create / Update DG Verification
	 * 
	 * @throws Exception
	 */
	private void updateDgWithSignOff() throws Exception {
		if (dgVerification.getDateCaptured() == null) {
			dgVerification.setDateCaptured(new Date());
		}
		dgVerificationService.createWithSignOffSubmission(dgVerification, getSessionUI().getUser(), getSessionUI().getTask(), false, selectedRejectReason, getSessionUI().getRole());
//		dgVerificationService.createWithSignOff(dgVerification, getSessionUI().getUser(), getSessionUI().getTask());
	}

	private void updateDg() throws Exception {
		if (dgVerification.getDateCaptured() == null) {
			dgVerification.setDateCaptured(new Date());
		}
		dgVerificationService.create(dgVerification);
	}

	/**
	 * By company selection
	 */
	public void pivitolInfo() {

		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mandatoryGrantService.findSummarizedData(MandatoryGrantDetail.class, first, pageSize, sortField, sortOrder, filters, wsp, wspReport, HAJConstants.DISC_FUNDING_ID);
					dataModel.setRowCount(mandatoryGrantService.count(MandatoryGrantDetail.class, filters, wspReport, company, HAJConstants.DISC_FUNDING_ID));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}

			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void generateEmployeesEmployed() {
		try {
			mandatoryGrantService.create(mandatoryGrantDetail);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrantDetail);

			pivitolInfo();
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applySaqaData(SelectEvent event) {
		try {
			mandatoryGrant.setNqfLevels(mandatoryGrant.getQualification().getNqflevel());
			mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
			mandatoryGrant.setEtqa(etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyInterventionData(SelectEvent event) {
		try {
			mandatoryGrant.setPivotNonPivot(mandatoryGrant.getInterventionType().getPivotNonPivot());
			if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.YES_ID));
			} else {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepUpdateOfRecommendation(){
		try {
			applyInterventionDataSelection();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void applyInterventionDataSelection() {
		try {
			if (mandatoryGrantRecommendation != null && mandatoryGrantRecommendation.getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(mandatoryGrantRecommendation.getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
				} else if (SKILLS_SET_LIST.contains(mandatoryGrantRecommendation.getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
				} else if (mandatoryGrantRecommendation.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = true;
					this.nonCreditBearing = false;
				} else if (mandatoryGrantRecommendation.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = true;
				} else {
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					this.nonCreditBearing = false;
				}
			} else {
				this.skillsProgram = false;
				this.skillsSet = false;
				this.shortCreditBearing = false;
				this.nonCreditBearing = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveValidiationCheck(){
		try {
			if (dgVerification != null) {
				errors = dgVerificationService.validiationOnMandatoryGrantSubmission(dgVerification);
				if (errors.equals("")) {
					errorEncountered = false;
					runClientSideExecute("PF('finalApproveDlg').show()");
				} else {
					errorEncountered = true;
					addErrorMessage("Validation Errors, please check under Validation Errors to action");
				}
			}else {
				addErrorMessage("Unable to locate verification, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiationCheck(){
		try {
			if (dgVerification != null) {
				errors = dgVerificationService.validiationOnMandatoryGrantSubmission(dgVerification);
				if (errors.equals("")) {
					errorEncountered = false;
					addInfoMessage("No Validation Errors Encountered");
				} else {
					errorEncountered = true;
					addErrorMessage("Validation Errors, please check under Validation Errors to action");
				}
			}else {
				addErrorMessage("Unable to locate verification, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	

	/* Getters and setters */
	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public WspReportEnum getWspReport() {
		return wspReport;
	}

	public void setWspReport(WspReportEnum wspReport) {
		this.wspReport = wspReport;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public List<MandatoryGrantDetail> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrantDetail> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

	public LazyDataModel<MandatoryGrant> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrant> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public LazyDataModel<Wsp> getWspDataModel() {
		return wspDataModel;
	}

	public void setWspDataModel(LazyDataModel<Wsp> wspDataModel) {
		this.wspDataModel = wspDataModel;
	}

	public LazyDataModel<DgVerification> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<DgVerification> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public List<Wsp> getWspList() {
		return wspList;
	}

	public void setWspList(List<Wsp> wspList) {
		this.wspList = wspList;
	}

	public List<Sites> getSites() {
		return sites;
	}

	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	public DgVerificationSites getDgVerificationSites() {
		return dgVerificationSites;
	}

	public void setDgVerificationSites(DgVerificationSites dgVerificationSites) {
		this.dgVerificationSites = dgVerificationSites;
	}

	public List<DgVerificationSites> getDgVerificationSitesList() {
		return dgVerificationSitesList;
	}

	public void setDgVerificationSitesList(List<DgVerificationSites> dgVerificationSitesList) {
		this.dgVerificationSitesList = dgVerificationSitesList;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public MandatoryGrantRecommendation getMandatoryGrantRecommendation() {
		return mandatoryGrantRecommendation;
	}

	public void setMandatoryGrantRecommendation(MandatoryGrantRecommendation mandatoryGrantRecommendation) {
		this.mandatoryGrantRecommendation = mandatoryGrantRecommendation;
	}

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public Boolean getDisableSignOffButton() {
		return disableSignOffButton;
	}

	public void setDisableSignOffButton(Boolean disableSignOffButton) {
		this.disableSignOffButton = disableSignOffButton;
	}

	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
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

	public Integer getDisplayCheckInt() {
		return displayCheckInt;
	}

	public void setDisplayCheckInt(Integer displayCheckInt) {
		this.displayCheckInt = displayCheckInt;
	}
	
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public boolean isViewLastestFinYear() {
		return viewLastestFinYear;
	}

	public void setViewLastestFinYear(boolean viewLastestFinYear) {
		this.viewLastestFinYear = viewLastestFinYear;
	}

	public boolean isSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public boolean isShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	public Boolean getErrorEncountered() {
		return errorEncountered;
	}

	public void setErrorEncountered(Boolean errorEncountered) {
		this.errorEncountered = errorEncountered;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public boolean isNonCreditBearing() {
		return nonCreditBearing;
	}

	public void setNonCreditBearing(boolean nonCreditBearing) {
		this.nonCreditBearing = nonCreditBearing;
	}



}
