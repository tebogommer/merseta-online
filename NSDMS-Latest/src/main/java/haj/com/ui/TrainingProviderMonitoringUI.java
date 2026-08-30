package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
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
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TrainingProviderFilterEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderMonitoringService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "trainingprovidermonitoringUI")
@ViewScoped
public class TrainingProviderMonitoringUI extends AbstractUI {
	
	/* Entity Levels */
	private TrainingProviderMonitoring trainingprovidermonitoring = null;
	private AuditorMonitorReview auditorMonitorReviewUpdate = null;
	private Region region = null;
	private AuditorMonitorReview auditorMonitorReview;
	private JobTitle jobTitle = null;
	private Company selectedCompany;
	private Users sdpUser;
	private Doc doc;

	/* Service Levels */
	private TrainingProviderMonitoringService service = new TrainingProviderMonitoringService();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private RegionService regionService = new RegionService();
	
	/* Array Lists */
	private List<TrainingProviderMonitoring> trainingprovidermonitoringList = null;
	private List<TrainingProviderMonitoring> trainingprovidermonitoringfilteredList = null;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectionReasons = new ArrayList<>();
	
	/* Lazy data models */
	private LazyDataModel<TrainingProviderMonitoring> dataModel;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModel;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModelListDataModel;
	private LazyDataModel<Company> companies;
	private LazyDataModel<Company> companiesDataModel;
	
	/* Vars */
	private TrainingProviderFilterEnum trainingProviderFilterEnum = TrainingProviderFilterEnum.NO_FILTER;
	private boolean disableCauseDateBeforeScheduledDate = false;
	private Date minDate = null;
	private Boolean canInitiate = false;
	private Date newDate = null;
	private boolean qaManager = true;

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
	 * Initialize method to get all TrainingProviderMonitoring and prepare a for
	 * a create of a new TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		populateIfQaManager();
		populateEmployeeRegion();
		minDate = GenericUtility.getStartOfDay(getNow());
		prepareNew();
		companyInfo();
		companyInfo2();
	}

	private void populateIfQaManager() throws Exception {
		if (!getSessionUI().isAdmin()) {
			if (getSessionUI().isEmployee()) {
				HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
				HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
				if (hce.getJobTitle() != null && hce.getJobTitle().getId() != null) {
					jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
					if (!jobTitle.getId().equals(HAJConstants.Manager_Quality_Assurance_ID) && (jobTitle.getRoles() != null && jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) && jobTitle.getRegion() != null) {
						qaManager = false;
					}
				}
			}
		}
	}
	
	private void populateEmployeeRegion() throws Exception{
		if (getSessionUI().isEmployee()) {
			HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
			HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
			if (hce.getJobTitle() != null && hce.getJobTitle().getId() != null) {
				jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
				if (jobTitle.getRegion() != null && jobTitle.getRegion().getId() != null) {
					region = regionService.findByKey(jobTitle.getRegion().getId());
				}
			}
		}
	}

	public void auditormonitorreviewInfo() {
		try {
			auditorMonitorReviewDataModelList = auditorMonitorReviewService.findByMonitor(trainingprovidermonitoring);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void populateRejectionReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		try {
			rejectionReasons = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(trainingprovidermonitoring.getId(), trainingprovidermonitoring.getClass().getName(), ConfigDocProcessEnum.ProviderMonitoring);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void auditormonitorreviewInfoDataModel() {
		auditorMonitorReviewDataModelListDataModel = new LazyDataModel<AuditorMonitorReview>() {
			private static final long serialVersionUID = 1L;
			private List<AuditorMonitorReview> retorno = new ArrayList<AuditorMonitorReview>();
			@Override
			public List<AuditorMonitorReview> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("targetClass", trainingprovidermonitoring.getClass().getName());
					filters.put("targetKey", trainingprovidermonitoring.getId());
					retorno = auditorMonitorReviewService.allAuditorMonitorReviewByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters);
					auditorMonitorReviewDataModelListDataModel.setRowCount(auditorMonitorReviewService.countAllAuditorMonitorReviewByTargetClassAndKey(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(AuditorMonitorReview obj) {
				return obj.getId();
			}

			@Override
			public AuditorMonitorReview getRowData(String rowKey) {
				for (AuditorMonitorReview obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void updateAuditorMonitorReviewEntry() {
		try {
			auditorMonitorReviewService.update(auditorMonitorReviewUpdate);
			// auditormonitorreviewInfoDataModel();
			auditormonitorreviewInfo();
			addInfoMessage("Update Succesfull");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void companyInfo() {
		companies = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					filters.put("companyUserType", ConfigDocProcessEnum.TP);
					if (qaManager == true) {
						
					} else {
						
					}
					retorno = companyUsersService.allTrainingProviders(first, pageSize, sortField, sortOrder, filters);
					companies.setRowCount(companyUsersService.countTrainingProviders(filters));
				} catch (Exception e) {
					logger.fatal(e);
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

	public void companyInfo2() {
		companiesDataModel = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (trainingProviderFilterEnum == null) {
						filters.put("companyUserType", ConfigDocProcessEnum.TP);
					} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
						filters.put("companyUserType", ConfigDocProcessEnum.TP);
					}
					if (qaManager) {
						retorno = companyUsersService.allTrainingProvidersVersionTwo(first, pageSize, sortField, sortOrder, filters, trainingProviderFilterEnum);
						companiesDataModel.setRowCount(companyUsersService.countAllTrainingProvidersVersionTwo(filters, trainingProviderFilterEnum));
					} else {
						if (jobTitle != null) {
							retorno = companyUsersService.allTrainingProvidersByRegionVersionTwo(first, pageSize, sortField, sortOrder, filters, trainingProviderFilterEnum, jobTitle.getRegion());
							companiesDataModel.setRowCount(companyUsersService .countAllTrainingProvidersByRegionVersionTwo(filters, trainingProviderFilterEnum));
						} else {
							retorno = companyUsersService.allTrainingProvidersVersionTwo(first, pageSize, sortField, sortOrder, filters, trainingProviderFilterEnum);
							companiesDataModel.setRowCount(companyUsersService .countAllTrainingProvidersVersionTwo(filters, trainingProviderFilterEnum));
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
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

	public void selectCompanyAudit() {
		try {
			determanCanInitiateAudit();
			trainingprovidermonitoringInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void determanCanInitiateAudit() throws Exception {
		canInitiate = true;

		// first one if QA was not involved with SDP registration
		if (trainingProviderApplicationService.determainQaInvolvedWithReg(selectedCompany, getSessionUI().getActiveUser())) {
			canInitiate = false;
		}

		// open audits/monitoring for company
		int counter = service.countOpenMonitoringReport(selectedCompany, true);
		if (counter != 0) {
			canInitiate = false;
		}
	}

	/**
	 * Get all TrainingProviderMonitoring for data table
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 * 
	 *      FOR AUDITS
	 */
	public void trainingprovidermonitoringInfo() {
		dataModel = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();

			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("companyID", selectedCompany.getId());
					filters.put("auditValue", true);
					retorno = service.allAuditsForProvider(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countAllAuditsgForProvider(filters));
					// retorno = service.allMonitoringForProvider(first,
					// pageSize, sortField, sortOrder, filters);
					// dataModel.setRowCount(service.countMonitoringForProvider(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void selectCompanyMonitoring() {
		try {
			determanCanInitiateMonitoring();
			trainingprovidermonitoringInfoMonitoring();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void determanCanInitiateMonitoring() throws Exception {
		canInitiate = true;

		// first one if QA was not involved with SDP registration
		if (trainingProviderApplicationService.determainQaInvolvedWithReg(selectedCompany,
				getSessionUI().getActiveUser())) {
			canInitiate = false;
		}

		// open audits/monitoring for company
		int counter = service.countOpenMonitoringReport(selectedCompany, false);
		if (counter != 0) {
			canInitiate = false;
		}
	}

	/**
	 * Get all TrainingProviderMonitoring for data table
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring FOR MONITORING
	 * 
	 */
	public void trainingprovidermonitoringInfoMonitoring() {
		dataModel = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();

			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					filters.put("companyID", selectedCompany.getId());
					filters.put("auditValue", false);
					retorno = service.allAuditsForProvider(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countAllAuditsgForProvider(filters));
					// retorno = service.allMonitoringForProvider(first,
					// pageSize, sortField, sortOrder, filters);
					// dataModel.setRowCount(service.countMonitoringForProvider(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}

			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public boolean userIsQualityAssuror() {
		boolean userIsQualityAssuror = false;
		Roles qualityAssurorRole = null;
		try {
			qualityAssurorRole = RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
			if (getSessionUI().getRole().getDescription().equals(qualityAssurorRole.getDescription())) {
				userIsQualityAssuror = true;
			}
		} catch (Exception e) {
			logger.fatal(e);
			return userIsQualityAssuror;
		}
		return userIsQualityAssuror;
	}

	/**
	 * Insert TrainingProviderMonitoring into database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringInsert() {
		try {
			sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(selectedCompany);
			service.submitCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), sdpUser, null);
			addInfoMessage(super.getEntryLanguage("update.successful"));

			if (trainingprovidermonitoring.getAudit()) {
				trainingprovidermonitoringInfo();
				runClientSideExecute("PF('dlgNewProviderAudit').hide()");
			} else {
				trainingprovidermonitoringInfoMonitoring();
				runClientSideExecute("PF('dlgNewProviderAudit').hide()");
			}

			trainingprovidermonitoringInfo();
			// sdpUser =
			// trainingProviderApplicationService.findUsersByCompanyAndReturnUser(selectedCompany);
			// service.sendProvidorMonitoringAudit(trainingprovidermonitoring,
			// sdpUser);
			// GenericUtility.sendMail(sdpUser.getEmail(), "Provider Monitoring
			// Audit Date", "MerSETA will be conducting a new monitoring audit
			// on the " + trainingprovidermonitoring.getMonitoringDate(), null);
			prepareNew();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void trainingprovidermonitoringCancel() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			} else {
				boolean audit = false;
				if (trainingprovidermonitoring.getAudit()) {
					audit = true;
				}
				if (newDate == null) {
					if (audit) {
						throw new Exception("Provide New Date For Next Audit");
					} else {
						throw new Exception("Provide New Date For Next Monitoring");
					}
				}
				service.cancelTrainingProviderMonitoring(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingprovidermonitoring.getCompany());
				service.sendCancelNotification(sdpUser, trainingprovidermonitoring);
//				GenericUtility.sendMail(sdpUser.getEmail(), "Provider Monitoring Audit Date Cancelled", "MerSETA has cancelled a monitoring audit on the " + trainingprovidermonitoring.getMonitoringDate(), null);
				TrainingProviderMonitoring tpmNew = new TrainingProviderMonitoring();
				if (audit) {
					tpmNew.setCompany(selectedCompany);
					tpmNew.setUser(getSessionUI().getActiveUser());
					tpmNew.setStatus(ApprovalEnum.NA);
					tpmNew.setAudit(true);
					tpmNew.setMonitoringDate(newDate);
				} else {
					tpmNew.setCompany(selectedCompany);
					tpmNew.setUser(getSessionUI().getActiveUser());
					tpmNew.setStatus(ApprovalEnum.NA);
					tpmNew.setAudit(false);
					tpmNew.setMonitoringDate(newDate);
				}
				sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(selectedCompany);
				service.submitCompanyLearners(tpmNew, getSessionUI().getActiveUser(), sdpUser, getSessionUI().getTask());

				tpmNew = null;
				newDate = null;

				addInfoMessage(super.getEntryLanguage("update.successful"));
				if (trainingprovidermonitoring.getAudit()) {
					trainingprovidermonitoringInfo();
				} else {
					trainingprovidermonitoringInfoMonitoring();
				}
				runClientSideExecute("PF('rejectReason').hide()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * public void prepareNew() { minDate =
	 * GenericUtility.getStartOfDay(getNow()); trainingprovidermonitoring = new
	 * TrainingProviderMonitoring();
	 * trainingprovidermonitoring.setCompany(selectedCompany);
	 * trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
	 * trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
	 * trainingprovidermonitoring.setAudit(true); }
	 * 
	 * public void prepareNewMonitoring() { minDate =
	 * GenericUtility.getStartOfDay(getNow()); trainingprovidermonitoring = new
	 * TrainingProviderMonitoring();
	 * trainingprovidermonitoring.setCompany(selectedCompany);
	 * trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
	 * trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
	 * trainingprovidermonitoring.setAudit(false); }
	 */

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.ProviderMonitoring);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void completeCompanyLearners() {
		try {
			// TODO - Email to be sent ?
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload)
				service.completeCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), auditorMonitorReviewDataModelList);
			if (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Edit)
				service.completeCompanyLearnersNoDoc(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), auditorMonitorReviewDataModelList);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void approveCompanyLearners() {
		try {
			service.approveCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(),getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void rejectCompanyLearners() {
		try {
			if (selectedRejectReason != null && selectedRejectReason.size() == 0) {
				throw new Exception("Select one rejection reason before proceeding");
			}
			service.rejectCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalApproveCompanyLearners() {
		try {
			service.finalApproveCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask());
			sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingprovidermonitoring.getCompany());
			service.sendProvidorMonitoringApprovalReport(trainingprovidermonitoring, sdpUser);
			// GenericUtility.sendMail(sdpUser.getEmail(), "Provider Monitoring
			// Audit Approved", "MerSETA has conducted and approved a monitoring
			// audit on the " + trainingprovidermonitoring.getMonitoringDate(),
			// null);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalRejectCompanyLearners() {
		try {
			if (selectedRejectReason == null || selectedRejectReason.size() == 0) {
				throw new Exception("Select one rejection reason before proceeding");
			}
			service.finalRejectCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			if (doc != null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());

				if (doc.getId() != null)
					DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc = new Doc();
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				doc.setUsers(getSessionUI().getActiveUser());
				doc.setTargetClass(auditorMonitorReview.getClass().getName());
				doc.setTargetKey(auditorMonitorReview.getId());
				if (doc.getId() == null)
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(),
							getSessionUI().getActiveUser());
				this.auditormonitorreviewInfo();
			}
			doc = null;
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update TrainingProviderMonitoring in database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringUpdate() {
		try {
			service.update(this.trainingprovidermonitoring);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingprovidermonitoringInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete TrainingProviderMonitoring from database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringDelete() {
		try {
			service.delete(this.trainingprovidermonitoring);
			prepareNew();
			trainingprovidermonitoringInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void prepareNew() {
		minDate = GenericUtility.getStartOfDay(getNow());
		trainingprovidermonitoring = new TrainingProviderMonitoring();
		trainingprovidermonitoring.setCompany(selectedCompany);
		trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
		trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
		trainingprovidermonitoring.setAudit(true);
	}

	public void prepareNewMonitoring() {
		minDate = GenericUtility.getStartOfDay(getNow());
		trainingprovidermonitoring = new TrainingProviderMonitoring();
		trainingprovidermonitoring.setCompany(selectedCompany);
		trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
		trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
		trainingprovidermonitoring.setAudit(false);
	}

	/*
	 * public List<SelectItem> getTrainingProviderMonitoringDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * trainingprovidermonitoringInfo(); for (TrainingProviderMonitoring ug :
	 * trainingprovidermonitoringList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<TrainingProviderMonitoring> complete(String desc) {
		List<TrainingProviderMonitoring> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<TrainingProviderMonitoring> getTrainingProviderMonitoringList() {
		return trainingprovidermonitoringList;
	}

	public void setTrainingProviderMonitoringList(List<TrainingProviderMonitoring> trainingprovidermonitoringList) {
		this.trainingprovidermonitoringList = trainingprovidermonitoringList;
	}

	public TrainingProviderMonitoring getTrainingprovidermonitoring() {
		return trainingprovidermonitoring;
	}

	public void setTrainingprovidermonitoring(TrainingProviderMonitoring trainingprovidermonitoring) {
		this.trainingprovidermonitoring = trainingprovidermonitoring;
	}

	public List<TrainingProviderMonitoring> getTrainingProviderMonitoringfilteredList() {
		return trainingprovidermonitoringfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param trainingprovidermonitoringfilteredList
	 *            the new trainingprovidermonitoringfilteredList list
	 * @see TrainingProviderMonitoring
	 */
	public void setTrainingProviderMonitoringfilteredList(
			List<TrainingProviderMonitoring> trainingprovidermonitoringfilteredList) {
		this.trainingprovidermonitoringfilteredList = trainingprovidermonitoringfilteredList;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderMonitoring> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(LazyDataModel<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewDataModel() {
		return auditorMonitorReviewDataModel;
	}

	public void setAuditorMonitorReviewDataModel(LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModel) {
		this.auditorMonitorReviewDataModel = auditorMonitorReviewDataModel;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewDataModelList() {
		return auditorMonitorReviewDataModelList;
	}

	public void setAuditorMonitorReviewDataModelList(List<AuditorMonitorReview> auditorMonitorReviewDataModelList) {
		this.auditorMonitorReviewDataModelList = auditorMonitorReviewDataModelList;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public AuditorMonitorReview getAuditorMonitorReview() {
		return auditorMonitorReview;
	}

	public void setAuditorMonitorReview(AuditorMonitorReview auditorMonitorReview) {
		this.auditorMonitorReview = auditorMonitorReview;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public TrainingProviderFilterEnum getTrainingProviderFilterEnum() {
		return trainingProviderFilterEnum;
	}

	public void setTrainingProviderFilterEnum(TrainingProviderFilterEnum trainingProviderFilterEnum) {
		this.trainingProviderFilterEnum = trainingProviderFilterEnum;
	}

	public LazyDataModel<Company> getCompaniesDataModel() {
		return companiesDataModel;
	}

	public void setCompaniesDataModel(LazyDataModel<Company> companiesDataModel) {
		this.companiesDataModel = companiesDataModel;
	}

	public boolean isDisableCauseDateBeforeScheduledDate() {
		return disableCauseDateBeforeScheduledDate;
	}

	public void setDisableCauseDateBeforeScheduledDate(boolean disableCauseDateBeforeScheduledDate) {
		this.disableCauseDateBeforeScheduledDate = disableCauseDateBeforeScheduledDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Boolean getCanInitiate() {
		return canInitiate;
	}

	public void setCanInitiate(Boolean canInitiate) {
		this.canInitiate = canInitiate;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public boolean isQaManager() {
		return qaManager;
	}

	public void setQaManager(boolean qaManager) {
		this.qaManager = qaManager;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewDataModelListDataModel() {
		return auditorMonitorReviewDataModelListDataModel;
	}

	public void setAuditorMonitorReviewDataModelListDataModel(
			LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModelListDataModel) {
		this.auditorMonitorReviewDataModelListDataModel = auditorMonitorReviewDataModelListDataModel;
	}

	public AuditorMonitorReview getAuditorMonitorReviewUpdate() {
		return auditorMonitorReviewUpdate;
	}

	public void setAuditorMonitorReviewUpdate(AuditorMonitorReview auditorMonitorReviewUpdate) {
		this.auditorMonitorReviewUpdate = auditorMonitorReviewUpdate;
	}

	public List<RejectReasons> getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(List<RejectReasons> rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

}
