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
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
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
import haj.com.service.CompanyService;
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

@ManagedBean(name = "trainingProviderMonitoringAuditViewUI")
@ViewScoped
public class TrainingProviderMonitoringAuditViewUI extends AbstractUI {
	
	/* Entity Levels */
	private TrainingProviderMonitoring trainingprovidermonitoring = null;
	private TrainingProviderApplication trainingProviderApplication = null;
	private AuditorMonitorReview auditorMonitorReviewUpdate = null;
	private Region region = null;
	private AuditorMonitorReview auditorMonitorReview;
	private JobTitle jobTitle = null;
	private Company selectedCompany;
	private Users sdpUser;
	private Users managerSelectedUser;

	/* Service Levels */
	private TrainingProviderMonitoringService service = new TrainingProviderMonitoringService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private RegionService regionService = new RegionService();
	private CompanyService companyService = new CompanyService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	
	/* Array Lists */
	private List<TrainingProviderMonitoring> trainingprovidermonitoringList = null;
	private List<TrainingProviderMonitoring> trainingprovidermonitoringfilteredList = null;
	private List<AuditorMonitorReview> auditorMonitorReviewDataModelList;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectionReasons = new ArrayList<>();
	
	/* Array Lists */
	private List<Users> avalaibleQaUsers = null;
	
	/* Lazy data models */
	private LazyDataModel<TrainingProviderMonitoring> dataModel;
	private LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModel;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewDataModelListDataModel;
	private LazyDataModel<Company> companies;
	private LazyDataModel<Company> companiesDataModel;
	
	/* Vars */
	private TrainingProviderFilterEnum trainingProviderFilterEnum = TrainingProviderFilterEnum.NO_FILTER;
	private boolean disableCauseDateBeforeScheduledDate = false;
	
	private Date minDate = null;
	private Date newDate = null;
	
	private Boolean canInitiate = false;
	private Boolean managerInitiate = false;
	private boolean qaManager = true;
	private boolean qaUserByRegion = false;
	
	private boolean displayResults = false;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
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
		minDate = GenericUtility.getStartOfDay(getNow());
		populateIfQaManagerJobTitleAndRegion();
		companyInfo();
		trainingProviderApplicationDataModelInfo();
		displayResults = false;
	}

	private void populateIfQaManagerJobTitleAndRegion() throws Exception {
		qaManager = false;
		if (!getSessionUI().isAdmin()) {
			if (getSessionUI().isEmployee()) {
				HostingCompanyEmployees hce = hostingCompanyEmployeesService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
				if (hce.getJobTitle() != null && hce.getJobTitle().getId() != null) {
					// populate job title
					jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
					if (jobTitle.getRegion() != null && jobTitle.getRegion().getId() != null) {
						// populate region
						region = regionService.findByKey(jobTitle.getRegion().getId());
					}
					if (jobTitle.getId().equals(HAJConstants.Manager_Quality_Assurance_ID) ) {
						// populate qa manager
						qaManager = true;
					} else if (jobTitle.getRoles() != null && jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)){
						qaUserByRegion = true;
					}
				}
			}
		}
	}
	
	public void runFilter(){
		try {
			displayResults = false;
			trainingProviderApplicationDataModelInfo();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Get all TrainingProviderApplication based on business rules for provider audits and employee level
	 * 
	 * @author TechFinium
	 * @see TrainingProviderApplication
	 */
	public void trainingProviderApplicationDataModelInfo() {
		trainingProviderApplicationDataModel = new LazyDataModel<TrainingProviderApplication>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderApplication> retorno = new ArrayList<TrainingProviderApplication>();
			@Override
			public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList = new ArrayList<>();
					accreditationApplicationTypeEnumList.add(AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
					if (qaManager) {
						// QA MANAGER VIEW ALL REGIONS
						retorno = trainingProviderApplicationService.providerAuditTrainingProviderApplications(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved, trainingProviderFilterEnum);
						trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countProviderAuditTrainingProviderApplications(filters, accreditationApplicationTypeEnumList, trainingProviderFilterEnum));
					} else {
						if (region != null) {
							retorno = trainingProviderApplicationService.providerAuditTrainingProviderApplicationsByRegion(first, pageSize, sortField, sortOrder, filters, region.getId(), accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved, trainingProviderFilterEnum);
							trainingProviderApplicationDataModel.setRowCount( trainingProviderApplicationService.countProviderAuditTrainingProviderApplicationsByRegion(filters, accreditationApplicationTypeEnumList, trainingProviderFilterEnum));
						} else {
							retorno = trainingProviderApplicationService.providerAuditTrainingProviderApplications(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved,trainingProviderFilterEnum);
							trainingProviderApplicationDataModel.setRowCount( trainingProviderApplicationService.countProviderAuditTrainingProviderApplications(filters, accreditationApplicationTypeEnumList, trainingProviderFilterEnum));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(TrainingProviderApplication obj) {
				return obj.getId();
			}
			@Override
			public TrainingProviderApplication getRowData(String rowKey) {
				for (TrainingProviderApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};
	}

	public void companyInfo() {
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
	
	public void selectTrainingProviderApplicationAudit() {
		try {
			displayResults = true;
			selectedCompany = companyService.findByKey(trainingProviderApplication.getCompany().getId());
			determinIfQaLinkedToCompanyByRegion();
			determanCanInitiateAudit();
			trainingproviderAuditInfo();
		} catch (Exception e) {
			e.printStackTrace();
			displayResults = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void determinIfQaLinkedToCompanyByRegion() throws Exception {
		if (jobTitle != null) {
			if (jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) {
				// QA first one if QA was not involved with SDP registration
				if (!trainingProviderApplicationService.determainQaInvolvedWithReg(selectedCompany, getSessionUI().getActiveUser())) {
					// QA assigned to the region
					selectedCompany = companyService.populateReportInfo(selectedCompany);
					if (selectedCompany.getRegionTown() != null && selectedCompany.getRegionTown().getRegion() != null && selectedCompany.getRegionTown().getRegion().getId() != null) {
						if (region != null && region.getId().equals(selectedCompany.getRegionTown().getRegion().getId())) {
							qaUserByRegion = true;
						}
					}
				}
			}
		}
	}

	private void determanCanInitiateAudit() throws Exception {
		canInitiate = false;
		managerInitiate = false;
		// validate if open
		int counter = service.countOpenMonitoringReport(selectedCompany, true);
		if (counter == 0) {
			if (jobTitle != null) {
				if (jobTitle.getId().equals(HAJConstants.Manager_Quality_Assurance_ID)) {
					// manager
					managerInitiate = true;
				} else if (jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) {
					if (trainingProviderFilterEnum != TrainingProviderFilterEnum.NO_FILTER) {
						// QA first one if QA was not involved with SDP registration
						if (!trainingProviderApplicationService.determainQaInvolvedWithReg(selectedCompany, getSessionUI().getActiveUser())) {
							// QA assigned to the region
							selectedCompany = companyService.populateReportInfo(selectedCompany);
							if (selectedCompany.getRegionTown() != null && selectedCompany.getRegionTown().getRegion() != null && selectedCompany.getRegionTown().getRegion().getId() != null) {
								if (region != null && region.getId().equals(selectedCompany.getRegionTown().getRegion().getId())) {
									canInitiate = true;
								}
							}
						}
					}
				}
			}
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
	public void trainingproviderAuditInfo() {
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
			e.printStackTrace();
			logger.fatal(e);
			return userIsQualityAssuror;
		}
		return userIsQualityAssuror;
	}
	
	/**
	 * Create new instance of TrainingProviderMonitoring
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void prepareNew() {
		try {
			minDate = GenericUtility.getStartOfDay(getNow());
			trainingprovidermonitoring = new TrainingProviderMonitoring();
			trainingprovidermonitoring.setCompany(selectedCompany);
			trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
			trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
			trainingprovidermonitoring.setAudit(true);	
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void prepareNewAuditManager() {
		try {
			minDate = GenericUtility.getStartOfDay(getNow());
			trainingprovidermonitoring = new TrainingProviderMonitoring();
			trainingprovidermonitoring.setCompany(selectedCompany);
			trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
			trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
			trainingprovidermonitoring.setAudit(true);
			populateAvalaibleQaUsers();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void populateAvalaibleQaUsers() throws Exception {
		avalaibleQaUsers = hostingCompanyEmployeesService.findByAllActiveUsersByRole(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
	}
	
	/**
	 * Insert TrainingProviderMonitoring into database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringInsert() {
		try {
//			sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(selectedCompany);
			sdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(selectedCompany.getId(), true);
			if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
				trainingprovidermonitoring.setTrainingProviderApplication(trainingProviderApplication);
			}
			service.submitCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), sdpUser, null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runClientSideExecute("PF('dlgNewProviderAudit').hide()");
			runClientSideExecute("PF('dlgNewProviderAuditManager').hide()");
			trainingproviderAuditInfo();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepCancelation(){
		try {
			populateAvalaibleQaUsers();
			managerSelectedUser = null;
			selectedRejectReason = new ArrayList<>();
			runClientSideExecute("PF('rejectReason').show()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.AuditMonitoring);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void trainingprovidermonitoringCancel() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			} else {
				if (newDate == null) {
					throw new Exception("Provide New Date For Next Audit");
				}
				service.cancelTrainingProviderMonitoring(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
//				sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingprovidermonitoring.getCompany());
				sdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(trainingprovidermonitoring.getCompany().getId(), true);
				service.sendCancelNotification(sdpUser, trainingprovidermonitoring);
				TrainingProviderMonitoring tpmNew = new TrainingProviderMonitoring();
				tpmNew.setCompany(selectedCompany);
				tpmNew.setUser(getSessionUI().getActiveUser());
				tpmNew.setStatus(ApprovalEnum.NA);
				tpmNew.setAudit(true);
				tpmNew.setMonitoringDate(newDate);
				if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
					tpmNew.setTrainingProviderApplication(trainingProviderApplication);
				}
				if (managerSelectedUser != null) {
					tpmNew.setInitiatingQA(managerSelectedUser);
				}
//				sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(selectedCompany);
				sdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(selectedCompany.getId(), true);
				service.submitCompanyLearners(tpmNew, getSessionUI().getActiveUser(), sdpUser, getSessionUI().getTask());
				tpmNew = null;
				newDate = null;
				addInfoMessage(super.getEntryLanguage("update.successful"));
				trainingproviderAuditInfo();
				runClientSideExecute("PF('rejectReason').hide()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/* Getters and setters */
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

	public boolean isQaUserByRegion() {
		return qaUserByRegion;
	}

	public void setQaUserByRegion(boolean qaUserByRegion) {
		this.qaUserByRegion = qaUserByRegion;
	}

	public Boolean getManagerInitiate() {
		return managerInitiate;
	}

	public void setManagerInitiate(Boolean managerInitiate) {
		this.managerInitiate = managerInitiate;
	}

	public List<Users> getAvalaibleQaUsers() {
		return avalaibleQaUsers;
	}

	public void setAvalaibleQaUsers(List<Users> avalaibleQaUsers) {
		this.avalaibleQaUsers = avalaibleQaUsers;
	}

	public Users getManagerSelectedUser() {
		return managerSelectedUser;
	}

	public void setManagerSelectedUser(Users managerSelectedUser) {
		this.managerSelectedUser = managerSelectedUser;
	}

	public LazyDataModel<TrainingProviderApplication> getTrainingProviderApplicationDataModel() {
		return trainingProviderApplicationDataModel;
	}

	public void setTrainingProviderApplicationDataModel(
			LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel) {
		this.trainingProviderApplicationDataModel = trainingProviderApplicationDataModel;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public boolean isDisplayResults() {
		return displayResults;
	}

	public void setDisplayResults(boolean displayResults) {
		this.displayResults = displayResults;
	}

}
