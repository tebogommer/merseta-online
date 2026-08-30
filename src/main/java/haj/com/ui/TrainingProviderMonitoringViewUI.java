package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderMonitoringService;
import haj.com.service.TrainingSiteService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "trainingProviderMonitoringViewUI")
@ViewScoped
public class TrainingProviderMonitoringViewUI extends AbstractUI {

	/* Entity Levels */
	private TrainingProviderMonitoring trainingprovidermonitoring = null;
	private TrainingProviderApplication trainingProviderApplication = null;
	private Region region = null;
	private JobTitle jobTitle = null;
	private Company selectedCompany;
	private Users sdpUser;
	private Users managerSelectedUser;

	/* Service Levels */
	private TrainingProviderMonitoringService service = new TrainingProviderMonitoringService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private RegionService regionService = new RegionService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	/* Array Lists */
	private List<Users> avalaibleQaUsers = null;
	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();
	private List<RejectReasons> rejectionReasons = new ArrayList<>();

	/* Lazy data models */
	private LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel;
	private LazyDataModel<TrainingProviderMonitoring> dataModel;
	private LazyDataModel<Company> companiesDataModel;

	/* Vars */
	private boolean disableCauseDateBeforeScheduledDate = false;
	private Date minDate = null;
	private Boolean canInitiate = false;
	private Boolean managerInitiate = false;
	private Date newDate = null;

	/* User types */
	private boolean qaManager = false;
	private boolean qaUserByRegion = false;

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
		minDate = GenericUtility.getStartOfDay(getNow());
		populateIfQaManagerJobTitleAndRegion();
		trainingProviderApplicationDataModelInfo();
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
					if (jobTitle.getId().equals(HAJConstants.Manager_Quality_Assurance_ID)) {
						// populate qa manager
						qaManager = true;
					} else if (jobTitle.getRoles() != null && jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) {
						qaUserByRegion = true;
					}
				}
			}
		}
	}

	/**
	 * Get all TrainingProviderApplication based on business rules for provider monitoring and employee level
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
					accreditationApplicationTypeEnumList.add(AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL);
					if (qaManager) {
						retorno = trainingProviderApplicationService.providerMonitoringTrainingProviderApplications(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
						trainingProviderApplicationDataModel.setRowCount(trainingProviderApplicationService.countProviderMonitoringTrainingProviderApplications(filters, accreditationApplicationTypeEnumList));
					} else {
						if (region != null) {
							retorno = trainingProviderApplicationService.providerMonitoringTrainingProviderApplicationsByRegion(first, pageSize, sortField, sortOrder, filters, region.getId(), accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
							trainingProviderApplicationDataModel.setRowCount( trainingProviderApplicationService.countProviderMonitoringTrainingProviderCompaniesByRegion(filters, accreditationApplicationTypeEnumList));
						} else {
							retorno = trainingProviderApplicationService.providerMonitoringTrainingProviderApplications(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
							trainingProviderApplicationDataModel.setRowCount( trainingProviderApplicationService.countProviderMonitoringTrainingProviderApplications(filters, accreditationApplicationTypeEnumList));
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
	
	public void selectTrainingProviderApplicationMonitoring() {
		try {
			selectedCompany = companyService.findByKey(trainingProviderApplication.getCompany().getId());
			determinIfQaLinkedToCompanyByRegion();
			determanCanInitiateMonitoring();
			trainingprovidermonitoringInfoMonitoring();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void companyInfo() {
		companiesDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList = new ArrayList<>();
					accreditationApplicationTypeEnumList.add(AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
					accreditationApplicationTypeEnumList.add(AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL);
					if (qaManager) {
						retorno = companyUsersService.providerMonitoringTrainingProviderCompanies(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
						companiesDataModel.setRowCount(companyUsersService.countProviderMonitoringTrainingProviderCompanies(filters, accreditationApplicationTypeEnumList));
					} else {
						if (region != null) {
							retorno = companyUsersService.providerMonitoringTrainingProviderCompaniesByRegion(first, pageSize, sortField, sortOrder, filters, region.getId(), accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
							companiesDataModel.setRowCount( companyUsersService.countProviderMonitoringTrainingProviderCompaniesByRegion( filters, accreditationApplicationTypeEnumList));
						} else {
							retorno = companyUsersService.providerMonitoringTrainingProviderCompanies(first, pageSize, sortField, sortOrder, filters, accreditationApplicationTypeEnumList, getNow(), ApprovalEnum.Approved);
							companiesDataModel.setRowCount( companyUsersService.countProviderMonitoringTrainingProviderCompanies(filters, accreditationApplicationTypeEnumList));
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

	public void selectCompanyMonitoring() {
		try {
			determinIfQaLinkedToCompanyByRegion();
			determanCanInitiateMonitoring();
			trainingprovidermonitoringInfoMonitoring();
		} catch (Exception e) {
			e.printStackTrace();
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
					if (trainingProviderApplication.getTrainingSite() == null) {
						if (selectedCompany.getRegionTown() != null && selectedCompany.getRegionTown().getRegion() != null && selectedCompany.getRegionTown().getRegion().getId() != null) {
							if (region != null && region.getId().equals(selectedCompany.getRegionTown().getRegion().getId())) {
								qaUserByRegion = true;
							}
						}
					} else {
						TrainingSiteService.instance().resolveTrainingSiteRegion(trainingProviderApplication.getTrainingSite());
						if (trainingProviderApplication.getTrainingSite().getRegionTown() != null && trainingProviderApplication.getTrainingSite().getRegionTown().getRegion() != null && trainingProviderApplication.getTrainingSite().getRegionTown().getRegion().getId() != null) {
							if (region != null && region.getId().equals(trainingProviderApplication.getTrainingSite().getRegionTown().getRegion().getId())) {
								qaUserByRegion = true;
							}
						}
					}
				}
			}
		}
	}

	private void determanCanInitiateMonitoring() throws Exception {
		canInitiate = false;
		managerInitiate = false;
		// validate if open
		int counter = service.countOpenMonitoringReport(selectedCompany, false);
		if (counter == 0) {
			if (jobTitle != null) {
				if (jobTitle.getId().equals(HAJConstants.Manager_Quality_Assurance_ID)) {
					// manager
					managerInitiate = true;
				} else if (jobTitle.getRoles().getId().equals(HAJConstants.QUALITY_ASSUROR_ROLE_ID)) {
					// QA first one if QA was not involved with SDP registration
					if (!trainingProviderApplicationService.determainQaInvolvedWithReg(selectedCompany, getSessionUI().getActiveUser())) {
						// QA assigned to the region
						selectedCompany = companyService.populateReportInfo(selectedCompany);
						if (trainingProviderApplication.getTrainingSite() == null) {
							if (selectedCompany.getRegionTown() != null && selectedCompany.getRegionTown().getRegion() != null && selectedCompany.getRegionTown().getRegion().getId() != null) {
								if (region != null && region.getId().equals(selectedCompany.getRegionTown().getRegion().getId())) {
									canInitiate = true;
								}
							}
						} else {
							TrainingSiteService.instance().resolveTrainingSiteRegion(trainingProviderApplication.getTrainingSite());
							if (trainingProviderApplication.getTrainingSite().getRegionTown() != null && trainingProviderApplication.getTrainingSite().getRegionTown().getRegion() != null && trainingProviderApplication.getTrainingSite().getRegionTown().getRegion().getId() != null) {
								if (region != null && region.getId().equals(trainingProviderApplication.getTrainingSite().getRegionTown().getRegion().getId())) {
									qaUserByRegion = true;
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
	 * @see TrainingProviderMonitoring FOR MONITORING
	 * 
	 */
	public void trainingprovidermonitoringInfoMonitoring() {
		dataModel = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();
			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					filters.put("companyID", selectedCompany.getId());
					filters.put("auditValue", false);
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

	public void prepareNewMonitoring() {
		try {
			minDate = GenericUtility.getStartOfDay(getNow());
			trainingprovidermonitoring = new TrainingProviderMonitoring();
			trainingprovidermonitoring.setCompany(selectedCompany);
			trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
			trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
			trainingprovidermonitoring.setAudit(false);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNewMonitoringManager() {
		try {
			minDate = GenericUtility.getStartOfDay(getNow());
			trainingprovidermonitoring = new TrainingProviderMonitoring();
			trainingprovidermonitoring.setCompany(selectedCompany);
			trainingprovidermonitoring.setUser(getSessionUI().getActiveUser());
			trainingprovidermonitoring.setStatus(ApprovalEnum.NA);
			trainingprovidermonitoring.setAudit(false);
			populateAvalaibleQaUsers();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void populateAvalaibleQaUsers() throws Exception {
		avalaibleQaUsers = hostingCompanyEmployeesService
				.findByAllActiveUsersByRole(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
	}

	/**
	 * Insert TrainingProviderMonitoring into database
	 * 
	 * @author TechFinium
	 * @see TrainingProviderMonitoring
	 */
	public void trainingprovidermonitoringInsert() {
		try {
			sdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(selectedCompany.getId(), true);
			if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
				trainingprovidermonitoring.setTrainingProviderApplication(trainingProviderApplication);
			}
			service.submitCompanyLearners(trainingprovidermonitoring, getSessionUI().getActiveUser(), sdpUser, null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			trainingprovidermonitoringInfoMonitoring();
			runClientSideExecute("PF('dlgNewProviderAudit').hide()");
			runClientSideExecute("PF('dlgNewProviderAuditManager').hide()");
			determanCanInitiateMonitoring();
			trainingprovidermonitoringInfoMonitoring();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepCancelation() {
		try {
			populateAvalaibleQaUsers();
			selectedRejectReason = new ArrayList<>();
			managerSelectedUser = null;
			runClientSideExecute("PF('rejectReason').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

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

	public void trainingprovidermonitoringCancel() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			} else {
				if (newDate == null) {
					throw new Exception("Provide New Date For Next Monitoring");
				}
				service.cancelTrainingProviderMonitoring(trainingprovidermonitoring, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				sdpUser = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(trainingprovidermonitoring.getCompany().getId(), true);
//				sdpUser = trainingProviderApplicationService.findUsersByCompanyAndReturnUser(trainingprovidermonitoring.getCompany());
				service.sendCancelNotification(sdpUser, trainingprovidermonitoring);
				TrainingProviderMonitoring tpmNew = new TrainingProviderMonitoring();
				tpmNew.setCompany(selectedCompany);
				tpmNew.setUser(getSessionUI().getActiveUser());
				tpmNew.setStatus(ApprovalEnum.NA);
				tpmNew.setAudit(false);
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
				determanCanInitiateMonitoring();
				trainingprovidermonitoringInfoMonitoring();
				runClientSideExecute("PF('rejectReason').hide()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public TrainingProviderMonitoring getTrainingprovidermonitoring() {
		return trainingprovidermonitoring;
	}

	public void setTrainingprovidermonitoring(TrainingProviderMonitoring trainingprovidermonitoring) {
		this.trainingprovidermonitoring = trainingprovidermonitoring;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderMonitoring> dataModel) {
		this.dataModel = dataModel;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
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

	public List<RejectReasons> getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(List<RejectReasons> rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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

	public boolean isQaUserByRegion() {
		return qaUserByRegion;
	}

	public void setQaUserByRegion(boolean qaUserByRegion) {
		this.qaUserByRegion = qaUserByRegion;
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

	public void setTrainingProviderApplicationDataModel(LazyDataModel<TrainingProviderApplication> trainingProviderApplicationDataModel) {
		this.trainingProviderApplicationDataModel = trainingProviderApplicationDataModel;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

}
