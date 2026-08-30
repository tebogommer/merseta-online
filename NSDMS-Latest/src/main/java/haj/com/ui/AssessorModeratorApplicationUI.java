package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.ApprovedApplicationBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModReRegistration;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressChangeService;
import haj.com.service.AssessorModExtensionOfScopeService;
import haj.com.service.AssessorModReRegistrationService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.AssessorModeratorCompanyService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.JasperService;
import haj.com.service.ReviewCommitteeMeetingAgendaService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserUnitStandardService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "assessorModeratorApplicationUI")
@ViewScoped
public class AssessorModeratorApplicationUI extends AbstractUI {

	private AssessorModeratorApplicationService service = new AssessorModeratorApplicationService();
	private AssessorModExtensionOfScopeService asessorModExtensionOfScopeService = new AssessorModExtensionOfScopeService();
	private List<AssessorModeratorApplication> assessorModeratorApplicationList = null;
	private List<AssessorModeratorApplication> assessorModeratorApplicationfilteredList = null;
	private AssessorModeratorApplication assessorModeratorApplication = null;
	private LazyDataModel<AssessorModeratorApplication> dataModel;
	private LazyDataModel<AssessorModeratorApplication> userAppDataModel;
	private LazyDataModel<AssessorModeratorApplication> userTTCAppDataModel;
	private LazyDataModel<AssessorModeratorApplication> allAMDataModel;
	private UserUnitStandardService userUnitStandardService = new UserUnitStandardService();
	UserQualificationsService userQualificationsService = new UserQualificationsService();
	private List<AssessorModeratorApplication> approveApplications = new ArrayList<>();
	private LazyDataModel<AssessorModExtensionOfScope> extOfScopedataModel;
	private LazyDataModel<Doc> docHistDataModel;

	/** Selected AssessorModeratorApplication */
	private AssessorModeratorApplication selectedAMApp = null;
	/** The Reject Reasons */
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();

	/** The assessor moderator application. */
	private AssessorModeratorApplication amApplication;

	/** The assessor moderator company service. */
	private AssessorModeratorCompanyService assessorModeratorCompanyService = new AssessorModeratorCompanyService();

	/** The usersLanguageService */
	UsersLanguageService usersLanguageService = new UsersLanguageService();

	private boolean showCommiteeActionButtons;

	/** The selected user languages */
	ArrayList<UsersLanguage> selectedUserLanguages = new ArrayList<>();

	/** The user qualifications. */
	private List<UserQualifications> userQualifications;

	/** The user unit standards. */
	private List<UserUnitStandard> userUnitStandards;

	/** The Doc Seversce */
	private DocService docService = new DocService();

	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingScheduleTypeService = new ReviewCommitteeMeetingAgendaService();
	private Boolean isAssOrMod;
	private Boolean isAssOrModTCC;

	private AssessorModExtensionOfScope assessorModExtensionOfScope;

	/** The Assessor Mod Reg Registration list */
	private List<AssessorModReRegistration> reRegList = new ArrayList<>();

	/** The Assessor Mod Extension Of Scope list */
	private List<AssessorModExtensionOfScope> exOfScopeList = new ArrayList<>();
	
	/** The users service. */
	private UsersService usersService = new UsersService();

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

	public String reviewCommitteeMeetingScheduleMeetingNumber(AssessorModeratorApplication amApp) {
		String meetingNumber = "N/A";
		try {
			if (amApp.getReviewCommitteeMeeting() != null) {
				ReviewCommitteeMeetingAgenda meetingType = reviewCommitteeMeetingScheduleTypeService
						.findByMeetingAgendaAndReviewCommitteeMeeting(
								HAJConstants.ASSESSOR_MOD_MEETING_SCHEDULE_TYPE_ID,
								amApp.getReviewCommitteeMeeting().getId());

				if (meetingType != null) {
					if (meetingType.getDecisionNumber() != null) {
						meetingNumber = meetingType.getDecisionNumber();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return meetingNumber;
	}

	/**
	 * Initialize method to get all AssessorModeratorApplication and prepare a
	 * for a create of a new AssessorModeratorApplication
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		assessorModeratorApplicationInfo();
		allAssessorModeratorApplicationInfo();
		// approveApplications=service.findByStatus(ApprovalEnum.PendingCommitteeApproval);
		// approveApplications=service.allAssessorModeratorApplication();
		showCommiteeActionBtnCheck();
		checkIfIsAssOrMod();
		userApplicationData();
		userTTCAppDataModelInfo();
		extensionOfScopeApplicationInfo();
	}

	/** Load AM application data */
	public void prepareAMInfor() {
		try {
			AssessorModReRegistrationService assessorModReRegistrationService = new AssessorModReRegistrationService();
			AssessorModExtensionOfScopeService assessorModExtensionOfScopeService = new AssessorModExtensionOfScopeService();
			selectedUserLanguages = loadUserLanguages(selectedAMApp.getUser());
			userQualifications = userQualificationsService.findByUserAMApplication(selectedAMApp.getUser().getId(), selectedAMApp.getId());
			userUnitStandards = userUnitStandardService.findByUserAndAPApplication(selectedAMApp.getUser().getId(), selectedAMApp.getId());
			reRegList = assessorModReRegistrationService.findByAssessorModeratorApplication(selectedAMApp.getId());
			exOfScopeList = assessorModExtensionOfScopeService.findByAssessorModeratorApplication(selectedAMApp.getId());
			AssesorModiratorUI.extensionOfScope = null;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}

	public boolean getShowAddressUpdate() {
		boolean show = false;
		;
		try {
			if (selectedAMApp.getStatus() == ApprovalEnum.Approved && selectedAMApp != null
					&& selectedAMApp.getUser() != null && selectedAMApp.getUser().getResidentialAddress() != null
					&& selectedAMApp.getUser().getResidentialAddress().getId() != null
					&& selectedAMApp.getUser().getPostalAddress() != null
					&& selectedAMApp.getUser().getPostalAddress().getId() != null) {
				AddressChangeService service = new AddressChangeService();
				int count = service.countByForPostalAndForResidentialAddress(
						selectedAMApp.getUser().getResidentialAddress().getId(),
						selectedAMApp.getUser().getPostalAddress().getId(), ApprovalEnum.PendingApproval);
				if (count == 0) {
					show = true;
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

		return show;
	}

	/**
	 * Check if ETQA user have permission to approve or reject application
	 */
	public void showCommiteeActionBtnCheck() {

		try {

			showCommiteeActionButtons = false;
			HostingCompanyEmployeesService hostingCompanyService = new HostingCompanyEmployeesService();
			HostingCompanyEmployees hs = hostingCompanyService
					.findByUserReturnHostCompany(getSessionUI().getActiveUser().getId());
			if (hs != null) {
				if (hs.getJobTitle().getDescription().contains("Senior Manager")) {
					showCommiteeActionButtons = true;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearSelectedAMApp() {
		selectedAMApp = null;
	}

	public ArrayList<UsersLanguage> loadUserLanguages(Users user) {
		ArrayList<UsersLanguage> list = new ArrayList<UsersLanguage>();
		try {
			list = (ArrayList<UsersLanguage>) usersLanguageService.findByUser(user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

		return list;
	}

	public List<Doc> loadExtensionOfScopeDocuments(AssessorModExtensionOfScope assessorModExtensionOfScope) {
		UsersService usersService = new UsersService();
		List<Doc> list = new ArrayList<>();
		try {
			Users u = usersService.findByKeyAndResolveDocTargetKeyAndClass(
					assessorModExtensionOfScope.getCreateUser().getId(), ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE,
					CompanyUserTypeEnum.User, assessorModExtensionOfScope.getId(),
					assessorModExtensionOfScope.getClass().getName());
			list = u.getDocs();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}

	public List<Doc> loadUserDocuments(Users user, AssessorModeratorApplication am) {
		List<Doc> list = new ArrayList<>();
		try {
			List<Doc> allDoclist = docService.searchByUser(user);
			if (am.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration
					|| am.getApplicationType() == AssessorModeratorAppTypeEnum.ModeratorReRegistration) {
				AssessorModReRegistrationService assessorModReRegistrationService = new AssessorModReRegistrationService();
				List<AssessorModReRegistration> appList = assessorModReRegistrationService
						.findByAssessorModeratorApplication(am.getId());
				AssessorModReRegistration assessorModReRegistration = null;
				if (appList != null && appList.size() > 0) {
					assessorModReRegistration = appList.get(0);
					for (Doc doc : allDoclist) {
						if (doc.getTargetKey() != null && doc.getTargetKey().equals(assessorModReRegistration.getId())
								&& doc.getTargetClass().equals(assessorModReRegistration.getClass().getName())) {
							list.add(doc);
						}

					}
				}

			} else {
				for (Doc doc : allDoclist) {
					if (doc.getTargetKey() != null && doc.getTargetKey().equals(am.getId())
							&& doc.getTargetClass().equals(am.getClass().getName())) {
						list.add(doc);
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	public ArrayList<AssessorModeratorCompany> loadUserCompany(AssessorModeratorApplication am) {

		ArrayList<AssessorModeratorCompany> list = new ArrayList<AssessorModeratorCompany>();
		try {

			list = (ArrayList<AssessorModeratorCompany>) assessorModeratorCompanyService
					.findByAssessorModeratorAndForAMApplication(am.getUser().getId(), am.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	public void downloadList() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js = new JasperService();
		try {

			JasperService.addFormTemplateParams(params);
			params.put("footerDocTitle", "Assessor/Moderator Application".toUpperCase());
			params.put("footerDocNum", "");
			params.put("title", "Assessor/Moderator Application".toUpperCase());
			ArrayList<ApprovedApplicationBean> appList = new ArrayList<>();
			approveApplications = service.findByStatus(ApprovalEnum.PendingCommitteeApproval);
			for (AssessorModeratorApplication amApp : approveApplications) {
				String qualificationList = "";
				String unitStandardList = "";
				int count = 0;
				for (UserQualifications qual : userQualifications(amApp.getUser(), amApp)) {
					count++;
					qualificationList += count + ". " + qual.getQualification().getSaqaQualification() + "<br/><br/>";
				}
				count = 0;
				for (UserUnitStandard us : userUnitStandard(amApp.getUser(), amApp)) {
					count++;
					unitStandardList += count + ". (" + us.getUnitStandard().getCode() + ") "
							+ us.getUnitStandard().getTitle() + "<br/><br/>";
				}
				String id = amApp.getUser().getRsaIDNumber();
				if (id.isEmpty()) {
					id = amApp.getUser().getPassportNumber();
				}
				ApprovedApplicationBean bean = new ApprovedApplicationBean(id, amApp.getUser().getFirstName(),
						amApp.getUser().getLastName(), qualificationList, unitStandardList,
						amApp.getApplicationType().getDisplayName());
				appList.add(bean);

			}

			params.put("ApprovedApplicationBeanDataSource", new JRBeanCollectionDataSource(appList));
			js.createReportFromDBtoServletOutputStream("AMReport.jasper", params, "AssessorModationApplication.pdf");

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createSeniorManagerApprovalTask() {
		try {
			AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.AM_ETQA_APPROVAL;
			if (amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration
					|| amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.ModeratorReRegistration) {
				configDocProcessEnum = ConfigDocProcessEnum.AM_RE_REGISTRATION_ETQA_APPROVAL;
			}
			assessorModeratorApplicationService.createSeniorManagerApprovalTask(configDocProcessEnum, amApplication,
					getSessionUI().getActiveUser());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createETQAExtentionOfScopeTask() {
		try {

			service.createETQAExtentionOfScopeTask(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE_ETQA_APPROVAL,
					assessorModExtensionOfScope, getSessionUI().getActiveUser());
			extensionOfScopeApplicationInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createETQAExtentionOfScopeRejectTask() {
		try {
			if (selectedRejectReason.size() == 0)
				throw new Exception("Please select a reject reason");
			service.createETQAExtentionOfScopeRejectTask(ConfigDocProcessEnum.AM_EXTENSION_OF_SCOPE_ETQA_APPROVAL,
					assessorModExtensionOfScope, getSessionUI().getActiveUser(), selectedRejectReason);
			extensionOfScopeApplicationInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createSeniorManagerRejectionTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.AM_ETQA_APPROVAL;
			if (amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration
					|| amApplication.getApplicationType() == AssessorModeratorAppTypeEnum.ModeratorReRegistration) {
				configDocProcessEnum = ConfigDocProcessEnum.AM_RE_REGISTRATION_ETQA_APPROVAL;
			}
			service.createSeniorManagerRejectionTask(configDocProcessEnum, amApplication,
					getSessionUI().getActiveUser(), selectedRejectReason);
			// approveApplications=service.findByStatus(ApprovalEnum.PendingCommitteeApproval);
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('rejectReason').hide()");

		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void extensionOfScopeApplicationInfo() {

		extOfScopedataModel = new LazyDataModel<AssessorModExtensionOfScope>() {

			private static final long serialVersionUID = 1L;
			private List<AssessorModExtensionOfScope> retorno = new ArrayList<AssessorModExtensionOfScope>();

			@Override
			public List<AssessorModExtensionOfScope> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("status", ApprovalEnum.PendingCommitteeApproval);
					retorno = asessorModExtensionOfScopeService.sortAndFilterWhere(first, pageSize, sortField,
							sortOrder, filters);
					extOfScopedataModel.setRowCount(
							asessorModExtensionOfScopeService.count(AssessorModExtensionOfScope.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModExtensionOfScope obj) {
				return obj.getId();
			}

			@Override
			public AssessorModExtensionOfScope getRowData(String rowKey) {
				for (AssessorModExtensionOfScope obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public void documentHistoryInfo() {

		docHistDataModel = new LazyDataModel<Doc>() {

			private static final long serialVersionUID = 1L;
			private List<Doc> retorno = new ArrayList<Doc>();

			@Override
			public List<Doc> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("targetClass", selectedAMApp.getClass().getName());
					filters.put("targetKey", selectedAMApp.getId());
					retorno = docService.allDocAndResolve(first, pageSize, sortField, sortOrder, filters);
					docHistDataModel.setRowCount(docService.countWhere(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Doc obj) {
				return obj.getId();
			}

			@Override
			public Doc getRowData(String rowKey) {
				for (Doc obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public ArrayList<UserUnitStandard> userUnitStandard(Users assessorModerator,
			AssessorModeratorApplication amApplication) {
		ArrayList<UserUnitStandard> l = new ArrayList<>();
		try {
			l = (ArrayList<UserUnitStandard>) userUnitStandardService
					.findByUserAndAPApplication(assessorModerator.getId(), amApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public ArrayList<UserQualifications> userQualifications(Users assessorModerator,
			AssessorModeratorApplication amApplication) {
		ArrayList<UserQualifications> l = new ArrayList<>();
		try {
			l = (ArrayList<UserQualifications>) userQualificationsService
					.findByUserAMApplication(assessorModerator.getId(), amApplication.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

		return l;
	}

	public void checkIfIsAssOrMod() {
		try {
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("userId", getSessionUI().getActiveUser().getId());
			filters.put("assessorModeratorType", AssessorModeratorTypeEnum.MerSETA_AM);
			// version one
//			int count = service.countAllApplicationsByUserIdAndType(AssessorModeratorApplication.class, filters);
			// version two
			int count = service.countAllApplicationsByUserIdAndType(filters);
			if (count > 0) {
				isAssOrMod = true;
			}else {
				isAssOrMod = false;
			}
			
			filters = new HashMap<String, Object>();
			filters.put("userId", getSessionUI().getActiveUser().getId());
			filters.put("assessorModeratorType", AssessorModeratorTypeEnum.TTC_AM);
			count = service.countAllApplicationsByUserIdAndType(filters);
			if (count > 0) {
				isAssOrModTCC = true;
			} else {
				isAssOrModTCC = false;
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadAssessorModCertificate(AssessorModeratorApplication am) {
		try {
			service.downloadAssessorModCertificate(am);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadAssessorModCertificateLetter(AssessorModeratorApplication am) {
		try {
			service.downloadAssessorModCertificateLetter(am);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadStatementOfQualifications(AssessorModeratorApplication am) {
		try {
			service.downloadStatementOfQualifications(am);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadAssessorModCertificate() {
		try {
			service.downloadAssessorModCertificate(selectedAMApp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadAssessorModCertificateLetter() {
		try {
			service.downloadAssessorModCertificateLetter(selectedAMApp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void downloadStatementOfQualifications() {
		try {
			service.downloadStatementOfQualifications(selectedAMApp);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<Doc> loadDocs(AssessorModeratorApplication am) {
		List<Doc> list = null;
		try {
			UsersService usersService = new UsersService();
			Users u = usersService.findByKey(am.getUser().getId(), ConfigDocProcessEnum.AM, CompanyUserTypeEnum.User);
			list = u.getDocs();
			ArrayList<Doc> docsForCurrentApplication = new ArrayList<>();
			for (Doc doc : list) {
				if (doc.getTargetKey() != null && doc.getTargetKey().equals(am.getId())
						&& doc.getTargetClass().equals(am.getClass().getName())) {
					docsForCurrentApplication.add(doc);
				}

			}
			return docsForCurrentApplication;
		} catch (Exception e) {
			list = new ArrayList<>();
			e.printStackTrace();
			return list;
		}

	}

	public List<Doc> loadReRegistrationsDocs(AssessorModReRegistration reReg) {
		List<Doc> list = null;
		try {
			UsersService usersService = new UsersService();
			Users u = usersService.findByKey(reReg.getUser().getId(), ConfigDocProcessEnum.AM_RE_REGISTRATION,
					CompanyUserTypeEnum.User);
			list = u.getDocs();
			ArrayList<Doc> docsForCurrentApplication = new ArrayList<>();
			for (Doc doc : list) {
				if (doc.getTargetKey() != null && doc.getTargetKey().equals(reReg.getId())
						&& doc.getTargetClass().equals(reReg.getClass().getName())) {
					docsForCurrentApplication.add(doc);
				}

			}
			return docsForCurrentApplication;
		} catch (Exception e) {
			list = new ArrayList<>();
			e.printStackTrace();
			return list;
		}

	}
	
	public void userTTCAppDataModelInfo() {
		userTTCAppDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try { 
					retorno = service.allApplicationsByUserIdAndType(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(), AssessorModeratorTypeEnum.TTC_AM);
					userTTCAppDataModel.setRowCount(service.countAllApplicationsByUserIdAndType( filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all AssessorModeratorApplication for data table
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void userApplicationData() {
		userAppDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					// version one
//					filters.put("userId", getSessionUI().getActiveUser().getId());
//					retorno = service.sortAndFilterWhereUserApp(first, pageSize, sortField, sortOrder, filters);
//					userAppDataModel.setRowCount(service.countUserInfoWhere(AssessorModeratorApplication.class, filters));
					
					// version two 
					retorno = service.allApplicationsByUserIdAndType(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(), AssessorModeratorTypeEnum.MerSETA_AM);
					userAppDataModel.setRowCount(service.countAllApplicationsByUserIdAndType( filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all AssessorModeratorApplication for data table
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void assessorModeratorApplicationInfo() {

		dataModel = new LazyDataModel<AssessorModeratorApplication>() {

			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();

			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					filters.put("status", ApprovalEnum.PendingCommitteeApproval);
					retorno = service.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(AssessorModeratorApplication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all AssessorModeratorApplication for data table
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void allAssessorModeratorApplicationInfo() {
		allAMDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<AssessorModeratorApplication>();

			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					// version one
					// retorno = service.sortAndFilterWhereAMInfo(first,
					// pageSize, sortField, sortOrder, filters);
					// allAMDataModel.setRowCount(service.countWhereAMInfo(filters));

					retorno = service.allAssessorModeratorApplicationByType(first, pageSize, sortField, sortOrder, filters, AssessorModeratorTypeEnum.MerSETA_AM);
					allAMDataModel.setRowCount(service.countAllAssessorModeratorApplicationByType(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}

			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert AssessorModeratorApplication into database
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void assessorModeratorApplicationInsert() {
		try {
			service.create(this.assessorModeratorApplication);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			assessorModeratorApplicationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update AssessorModeratorApplication in database
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void assessorModeratorApplicationUpdate() {
		try {
			service.update(this.assessorModeratorApplication);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			assessorModeratorApplicationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete AssessorModeratorApplication from database
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void assessorModeratorApplicationDelete() {
		try {
			service.delete(this.assessorModeratorApplication);
			prepareNew();
			assessorModeratorApplicationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of AssessorModeratorApplication
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorApplication
	 */
	public void prepareNew() {
		assessorModeratorApplication = new AssessorModeratorApplication();
	}

	/*
	 * public List<SelectItem> getAssessorModeratorApplicationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * assessorModeratorApplicationInfo(); for (AssessorModeratorApplication ug
	 * : assessorModeratorApplicationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */
	public void downloadReport(){
		try {
			service.downloadReport(AssessorModeratorTypeEnum.MerSETA_AM);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<AssessorModeratorApplication> complete(String desc) {
		List<AssessorModeratorApplication> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<AssessorModeratorApplication> getAssessorModeratorApplicationList() {
		return assessorModeratorApplicationList;
	}

	public void setAssessorModeratorApplicationList(
			List<AssessorModeratorApplication> assessorModeratorApplicationList) {
		this.assessorModeratorApplicationList = assessorModeratorApplicationList;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public List<AssessorModeratorApplication> getAssessorModeratorApplicationfilteredList() {
		return assessorModeratorApplicationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param assessorModeratorApplicationfilteredList
	 *            the new assessorModeratorApplicationfilteredList list
	 * @see AssessorModeratorApplication
	 */
	public void setAssessorModeratorApplicationfilteredList(
			List<AssessorModeratorApplication> assessorModeratorApplicationfilteredList) {
		this.assessorModeratorApplicationfilteredList = assessorModeratorApplicationfilteredList;
	}

	public LazyDataModel<AssessorModeratorApplication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AssessorModeratorApplication> dataModel) {
		this.dataModel = dataModel;
	}

	public List<AssessorModeratorApplication> getApproveApplications() {
		return approveApplications;
	}

	public void setApproveApplications(List<AssessorModeratorApplication> approveApplications) {
		this.approveApplications = approveApplications;
	}

	public AssessorModeratorApplication getAmApplication() {
		return amApplication;
	}

	public void setAmApplication(AssessorModeratorApplication amApplication) {
		this.amApplication = amApplication;
	}

	public boolean isShowCommiteeActionButtons() {
		return showCommiteeActionButtons;
	}

	public void setShowCommiteeActionButtons(boolean showCommiteeActionButtons) {
		this.showCommiteeActionButtons = showCommiteeActionButtons;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	/**
	 * @return the allAMDataModel
	 */
	public LazyDataModel<AssessorModeratorApplication> getAllAMDataModel() {
		return allAMDataModel;
	}

	/**
	 * @param allAMDataModel
	 *            the allAMDataModel to set
	 */
	public void setAllAMDataModel(LazyDataModel<AssessorModeratorApplication> allAMDataModel) {
		this.allAMDataModel = allAMDataModel;
	}

	/**
	 * @return the selectedAMApp
	 */
	public AssessorModeratorApplication getSelectedAMApp() {
		return selectedAMApp;
	}

	/**
	 * @param selectedAMApp
	 *            the selectedAMApp to set
	 */
	public void setSelectedAMApp(AssessorModeratorApplication selectedAMApp) {
		this.selectedAMApp = selectedAMApp;
	}

	/**
	 * @return the selectedUserLanguages
	 */
	public ArrayList<UsersLanguage> getSelectedUserLanguages() {
		return selectedUserLanguages;
	}

	/**
	 * @param selectedUserLanguages
	 *            the selectedUserLanguages to set
	 */
	public void setSelectedUserLanguages(ArrayList<UsersLanguage> selectedUserLanguages) {
		this.selectedUserLanguages = selectedUserLanguages;
	}

	/**
	 * @return the userQualifications
	 */
	public List<UserQualifications> getUserQualifications() {
		return userQualifications;
	}

	/**
	 * @param userQualifications
	 *            the userQualifications to set
	 */
	public void setUserQualifications(List<UserQualifications> userQualifications) {
		this.userQualifications = userQualifications;
	}

	/**
	 * @return the userUnitStandards
	 */
	public List<UserUnitStandard> getUserUnitStandards() {
		return userUnitStandards;
	}

	/**
	 * @param userUnitStandards
	 *            the userUnitStandards to set
	 */
	public void setUserUnitStandards(List<UserUnitStandard> userUnitStandards) {
		this.userUnitStandards = userUnitStandards;
	}

	public LazyDataModel<AssessorModeratorApplication> getUserAppDataModel() {
		return userAppDataModel;
	}

	public void setUserAppDataModel(LazyDataModel<AssessorModeratorApplication> userAppDataModel) {
		this.userAppDataModel = userAppDataModel;
	}

	public Boolean getIsAssOrMod() {
		return isAssOrMod;
	}

	public void setIsAssOrMod(Boolean isAssOrMod) {
		this.isAssOrMod = isAssOrMod;
	}

	public AssessorModeratorCompanyService getAssessorModeratorCompanyService() {
		return assessorModeratorCompanyService;
	}

	public void setAssessorModeratorCompanyService(AssessorModeratorCompanyService assessorModeratorCompanyService) {
		this.assessorModeratorCompanyService = assessorModeratorCompanyService;
	}

	public AssessorModExtensionOfScope getAssessorModExtensionOfScope() {
		return assessorModExtensionOfScope;
	}

	public void setAssessorModExtensionOfScope(AssessorModExtensionOfScope assessorModExtensionOfScope) {
		this.assessorModExtensionOfScope = assessorModExtensionOfScope;
	}

	public LazyDataModel<AssessorModExtensionOfScope> getExtOfScopedataModel() {
		return extOfScopedataModel;
	}

	public void setExtOfScopedataModel(LazyDataModel<AssessorModExtensionOfScope> extOfScopedataModel) {
		this.extOfScopedataModel = extOfScopedataModel;
	}

	public LazyDataModel<Doc> getDocHistDataModel() {
		return docHistDataModel;
	}

	public void setDocHistDataModel(LazyDataModel<Doc> docHistDataModel) {
		this.docHistDataModel = docHistDataModel;
	}

	public List<AssessorModReRegistration> getReRegList() {
		return reRegList;
	}

	public void setReRegList(List<AssessorModReRegistration> reRegList) {
		this.reRegList = reRegList;
	}

	public List<AssessorModExtensionOfScope> getExOfScopeList() {
		return exOfScopeList;
	}

	public void setExOfScopeList(List<AssessorModExtensionOfScope> exOfScopeList) {
		this.exOfScopeList = exOfScopeList;
	}

	public LazyDataModel<AssessorModeratorApplication> getUserTTCAppDataModel() {
		return userTTCAppDataModel;
	}

	public void setUserTTCAppDataModel(LazyDataModel<AssessorModeratorApplication> userTTCAppDataModel) {
		this.userTTCAppDataModel = userTTCAppDataModel;
	}

	public Boolean getIsAssOrModTCC() {
		return isAssOrModTCC;
	}

	public void setIsAssOrModTCC(Boolean isAssOrModTCC) {
		this.isAssOrModTCC = isAssOrModTCC;
	}

	public void updateUserApplicationData_vh1() {
		System.out.println("inside updateUserApplicationData_vh1");
		if(selectedAMApp.getUser()!=null) {
			try {
				//System.out.println("selectedAMApp.getUser():"+selectedAMApp.getUser().getResidentialAddress().getAddressLine1());
//				usersService.update(selectedAMApp.getUser());
				service.update(selectedAMApp);
				addInfoMessage(super.getEntryLanguage("update.successful"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
	}
}