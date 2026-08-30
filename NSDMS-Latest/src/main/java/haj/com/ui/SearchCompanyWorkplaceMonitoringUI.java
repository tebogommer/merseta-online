package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Signoff;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.ActiveContractsService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.LearnersService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SignoffService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "searchCompanyWorkplaceMonitoringUI")
@ViewScoped
public class SearchCompanyWorkplaceMonitoringUI extends AbstractUI {

	/* Entity Level */
	private Company 					selectedCompany						= null;
	private ActiveContracts 			selectedActiveContracts 			= null;
	private ProjectImplementationPlan 	selectedProjectImplementationPlan 	= null;
	private CompanyLearners 			selectedCompanyLearners				= null;
	
	/* Service Level */
	private CompanyService 						companyService 						= new CompanyService();
	private LearnersService 					learnersService 					= new LearnersService();
	private SignoffService 						signoffService						= new SignoffService();
	private InterventionTypeService 			interventionTypeService				= new InterventionTypeService();
	private CompanyLearnersService 				companyLearnersService 				= new CompanyLearnersService();
	private ActiveContractsService 				activeContractsService 				= new ActiveContractsService();
	private ActiveContractDetailService 		activeContractDetailService 		= new ActiveContractDetailService();
	private ProjectImplementationPlanService 	projectImplementationPlanService 	= new ProjectImplementationPlanService();
	private HostingCompanyEmployeesService		hostingCompanyEmployeesService		= new HostingCompanyEmployeesService();
	private WorkplaceMonitoringSiteVisitService workplaceMonitoringSiteVisitService = new WorkplaceMonitoringSiteVisitService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> 							companyListDataModel;
	private LazyDataModel<ActiveContracts> 					allActiveContractsDataModel;
	private LazyDataModel<ProjectImplementationPlan> 		allActiveContractProjectImplementationPlanDataModel;
	private LazyDataModel<ActiveContractDetail> 			allActiveContractPaymentsDataModel;
	private LazyDataModel<ActiveContractDetail> 			allProjectImplementationPlanPaymentsDataModel;
	private LazyDataModel<ActiveContractDetail> 			allCompanyLearnerPaymentsDataModel;
	private LazyDataModel<CompanyLearners> 					allLearnersDataModel;
	private LazyDataModel<CompanyLearners> 					allLearnershipLearnersDataModel;
	private LazyDataModel<CompanyLearners> 					allApprenticeCbmtLearnersDataModel;
	private LazyDataModel<CompanyLearners> 					allMotorTbLearnersDataModel;
	private LazyDataModel<CompanyLearners> 					allMetalTbLearnersDataModel;
	private LazyDataModel<WorkplaceMonitoringSiteVisit> 	allWorkplaceMonitoringSiteVisitByCompanyDataModel;
	
	/* Array Lists */
	private List<Signoff> 					signOffMoaList	 				= null;
	private List<ProjectImplementationPlan> projectImplementationPlanList 	= null;
	private List<InterventionType>			allLearnershipInterventions 	= null;
	private List<InterventionType>			allApprenticeshipInterventions 	= null;
	private List<InterventionType>			allOtherInterventions 			= null;
	
	/* Vars */
	private int 	index;
	private Boolean crmCloUser 			= false;
	private Boolean canCreateSiteVisit 	= false;
	private Date 	today 				= new Date();
	private Date 	siteVisitDate 		= new Date();
	
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
		companyListDataModelInfo();
		populateInterventionTypeLists();
		identifyUserLevel();
	}
	
	/**
	 * Identifys if the user is either:
	 * false - general user
	 * true - Clo or CRM
	 * @throws Exception
	 */
	private void identifyUserLevel() throws Exception{
		HostingCompanyEmployees hce = hostingCompanyEmployeesService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		crmCloUser = RegionTownService.instance().checkIfCrmCloUser(hce);
	}
	
	private void populateInterventionTypeLists() throws Exception {
		allLearnershipInterventions = interventionTypeService.findByInterventionType(InterventionTypeEnum.Learnership);
		allApprenticeshipInterventions = interventionTypeService.findByInterventionType(InterventionTypeEnum.Apprenticeship);
		allOtherInterventions = interventionTypeService.findByInterventionType(InterventionTypeEnum.Other);
	}

	public void companyListDataModelInfo() {
		companyListDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						companyList = companyService.allCompanyByCloCrm(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						companyListDataModel.setRowCount(companyService.countRegion(Company.class, filters));
					}else {
						if (sortField == null) {
							sortField = "companyStatus";
							sortOrder = SortOrder.DESCENDING;
						}
						companyList = companyService.allCompanySetaCompanies(Company.class, first, pageSize, sortField, sortOrder, filters);
						companyListDataModel.setRowCount(companyService.countSetaCompanies(Company.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void selectCompany(){
		try {
			clearInformationOnTabChange();
			companyService.resolveCompanyAddresses(selectedCompany);
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void deselectCompany(){
		try {
			selectedCompany = null;
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void onTabChange(TabChangeEvent event) {
		try {
			clearInformationOnTabChange();
			switch (event.getTab().getId()) {
			case "companyInfoTab":
				break;
			case "activeContractsTab":
				allActiveContractsDataModelInfo();
				break;
			case "allLearnersTab":
				allLearnersDataModelInfo();
				break;
			case "learnersLearnershipTab":
				allLearnershipLearnersDataModelInfo();
				break;
			case "learnersAppCbmtTab":
				allApprenticeCbmtLearnersDataModellInfo();
				break;
			case "learnersAppMotorTbTab":
				allMotorTbLearnersDataModelInfo();
				break;
			case "learnersAppMetalTbTab":
				allMetalTbLearnersDataModelInfo();
				break;
			case "workplaceMonitoringTab":
				checkIfCanCreateSiteVisit();
				allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearInformationOnTabChange(){
		try {
			clearSelectActiveContractInformation();
			clearCompanyLearnersInformation();
			canCreateSiteVisit = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void allActiveContractsDataModelInfo() {
		allActiveContractsDataModel = new LazyDataModel<ActiveContracts>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContracts> retorno = new ArrayList<>();
			@Override
			public List<ActiveContracts> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = activeContractsService.allActiveContractsByCompanyId(ActiveContracts.class, first, pageSize, sortField, sortOrder, filters, selectedCompany.getId());
					allActiveContractsDataModel.setRowCount(activeContractsService.countAllActiveContractsByCompanyId(ActiveContracts.class, filters));
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
	
	public void viewActiveContractInformation(){
		try {
			projectImplementationPlanList = new ArrayList<>();
			if (selectedActiveContracts.getDgAllocationParent() != null) projectImplementationPlanList = projectImplementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(selectedActiveContracts.getDgAllocationParent().getWsp());
			signOffMoaList = signoffService.findByTargetKeyAndClass(selectedActiveContracts.getId(), selectedActiveContracts.getClass().getName());
			allActiveContractPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void viewTranchPaymentsAssignedToActiveContract(){
		try {
			allActiveContractPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void allActiveContractPaymentsDataModelInfo() {
		allActiveContractPaymentsDataModel = new LazyDataModel<ActiveContractDetail>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContractDetail> retorno = new ArrayList<>();
			@Override
			public List<ActiveContractDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = activeContractDetailService.allActiveContractDetailByActiveContractId(first, pageSize, sortField, sortOrder, filters, selectedActiveContracts.getId());
					allActiveContractPaymentsDataModel.setRowCount(activeContractDetailService.countAllActiveContractDetailByActiveContractId(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ActiveContractDetail obj) {
				return obj.getId();
			}
			@Override
			public ActiveContractDetail getRowData(String rowKey) {
				for (ActiveContractDetail obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void viewTranchPaymnetsByProjectImplementationPlanSelected(){
		try {
			allProjectImplementationPlanPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void allProjectImplementationPlanPaymentsDataModelInfo() {
		allProjectImplementationPlanPaymentsDataModel = new LazyDataModel<ActiveContractDetail>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContractDetail> retorno = new ArrayList<>();
			@Override
			public List<ActiveContractDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = activeContractDetailService.allActiveContractDetailByProjectImplementationPlanId(first, pageSize, sortField, sortOrder, filters, selectedProjectImplementationPlan.getId());
					allProjectImplementationPlanPaymentsDataModel.setRowCount(activeContractDetailService.countAllActiveContractDetailByProjectImplementationPlanId(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ActiveContractDetail obj) {
				return obj.getId();
			}
			@Override
			public ActiveContractDetail getRowData(String rowKey) {
				for (ActiveContractDetail obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void deselectActiveContract(){
		try {
			clearSelectActiveContractInformation();
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void clearSelectActiveContractInformation(){
		try {
			selectedActiveContracts = null;
			allActiveContractProjectImplementationPlanDataModel = null;
			allProjectImplementationPlanPaymentsDataModel = null;
			allActiveContractPaymentsDataModel = null;
			projectImplementationPlanList = null;
			signOffMoaList = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// not used yet
	public void allActiveContractProjectImplementationPlanDataModelInfo() {
		allActiveContractProjectImplementationPlanDataModel = new LazyDataModel<ProjectImplementationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<ProjectImplementationPlan> rentroList = new ArrayList<>();
			@Override
			public List<ProjectImplementationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					rentroList = projectImplementationPlanService.allProjectImplementationPlanByWsp(first, pageSize, sortField, sortOrder, filters, selectedActiveContracts.getDgAllocationParent().getWsp().getId());
					allActiveContractProjectImplementationPlanDataModel.setRowCount(projectImplementationPlanService.countAllProjectImplementationPlanByWsp( filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return rentroList;
			}
			@Override
			public Object getRowKey(ProjectImplementationPlan object) {
				return object.getId();
			}
			@Override
			public ProjectImplementationPlan getRowData(String rowKey) {
				for (ProjectImplementationPlan obj : rentroList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void closeViewCompanyLearnersPayment(){
		try {
			clearCompanyLearnersInformation();
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearCompanyLearnersInformation(){
		allCompanyLearnerPaymentsDataModel = null;
	}
	
	public void viewCompanyLearnersPayments(){
		try {
			allCompanyLearnerPaymentsDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void allCompanyLearnerPaymentsDataModelInfo() {
		allCompanyLearnerPaymentsDataModel = new LazyDataModel<ActiveContractDetail>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContractDetail> retorno = new ArrayList<>();
			@Override
			public List<ActiveContractDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = activeContractDetailService.allActiveContractDetailByCompanyLearnerId(first, pageSize, sortField, sortOrder, filters, selectedCompanyLearners.getId());
					allCompanyLearnerPaymentsDataModel.setRowCount(activeContractDetailService.countAllActiveContractDetailByCompanyLearnerId(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ActiveContractDetail obj) {
				return obj.getId();
			}
			@Override
			public ActiveContractDetail getRowData(String rowKey) {
				for (ActiveContractDetail obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void allLearnersDataModelInfo() {
		allLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatus(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId());
					allLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatus(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void allLearnershipLearnersDataModelInfo() {
		allLearnershipLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeList(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId(), allLearnershipInterventions);
					allLearnershipLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeList(filters, allLearnershipInterventions) );
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void allApprenticeCbmtLearnersDataModellInfo() {
		allApprenticeCbmtLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId(), HAJConstants.INTERVENTION_TYPE_APPRENTICESIPS_ID, HAJConstants.DESIGNATED_TRADE_MOTOR_CBMT_ID);
					allApprenticeCbmtLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void allMotorTbLearnersDataModelInfo() {
		allMotorTbLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId(), HAJConstants.INTERVENTION_TYPE_APPRENTICESIPS_ID, HAJConstants.DESIGNATED_TRADE_MOTOR_TIME_BASED_ID);
					allMotorTbLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void allMetalTbLearnersDataModelInfo() {
		allMetalTbLearnersDataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<>();
			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = learnersService.allLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId(), HAJConstants.INTERVENTION_TYPE_APPRENTICESIPS_ID, HAJConstants.DESIGNATED_TRADE_METAL_AND_PLASTICS_ID);
					allMetalTbLearnersDataModel.setRowCount(learnersService.countAllLearnersAssignedToEmployerWithoutStatusAndByInterventionTypeAndDesignatedTrade(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void checkIfCanCreateSiteVisit(){
		try {
			canCreateSiteVisit = workplaceMonitoringSiteVisitService.canInititaeNewMonitoringSiteVisit(selectedCompany, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo() {
		allWorkplaceMonitoringSiteVisitByCompanyDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByCompanyId(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId());
					allWorkplaceMonitoringSiteVisitByCompanyDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByCompanyId(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringSiteVisit obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringSiteVisit getRowData(String rowKey) {
				for (WorkplaceMonitoringSiteVisit obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void prepCreateSiteVisit(){
		try {
			if (canCreateSiteVisit) {
				siteVisitDate = new Date();
				runClientSideExecute("PF('newMonitoringSiteVisitDlg').show()");
			} else {
				addWarningMessage("You do not have access to Initiate: Workplace Monitoring Site Visit.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void createSiteVisit(){
		try {
			boolean cancreate = true;
			
			if (siteVisitDate == null) {
				addErrorMessage("Provide date before proceeding.");
				cancreate = false;
			}
			
//			if (siteVisitDate.before(today)) {
//				addErrorMessage("Provide date after today.");
//				cancreate = false;
//			}
			
			if (cancreate) {
				workplaceMonitoringSiteVisitService.createWorkplaceMonitoringSiteVisit(selectedCompany, getSessionUI().getActiveUser(), siteVisitDate, null, null);
				allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
				checkIfCanCreateSiteVisit();
				runClientSideExecute("PF('newMonitoringSiteVisitDlg').hide()");
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/* getters and Setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<Company> getCompanyListDataModel() {
		return companyListDataModel;
	}

	public void setCompanyListDataModel(LazyDataModel<Company> companyListDataModel) {
		this.companyListDataModel = companyListDataModel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LazyDataModel<CompanyLearners> getAllLearnersDataModel() {
		return allLearnersDataModel;
	}

	public void setAllLearnersDataModel(LazyDataModel<CompanyLearners> allLearnersDataModel) {
		this.allLearnersDataModel = allLearnersDataModel;
	}

	public LazyDataModel<ActiveContracts> getAllActiveContractsDataModel() {
		return allActiveContractsDataModel;
	}

	public void setAllActiveContractsDataModel(LazyDataModel<ActiveContracts> allActiveContractsDataModel) {
		this.allActiveContractsDataModel = allActiveContractsDataModel;
	}

	public LazyDataModel<CompanyLearners> getAllLearnershipLearnersDataModel() {
		return allLearnershipLearnersDataModel;
	}

	public void setAllLearnershipLearnersDataModel(LazyDataModel<CompanyLearners> allLearnershipLearnersDataModel) {
		this.allLearnershipLearnersDataModel = allLearnershipLearnersDataModel;
	}

	public LazyDataModel<CompanyLearners> getAllApprenticeCbmtLearnersDataModel() {
		return allApprenticeCbmtLearnersDataModel;
	}

	public void setAllApprenticeCbmtLearnersDataModel(LazyDataModel<CompanyLearners> allApprenticeCbmtLearnersDataModel) {
		this.allApprenticeCbmtLearnersDataModel = allApprenticeCbmtLearnersDataModel;
	}

	public ActiveContracts getSelectedActiveContracts() {
		return selectedActiveContracts;
	}

	public void setSelectedActiveContracts(ActiveContracts selectedActiveContracts) {
		this.selectedActiveContracts = selectedActiveContracts;
	}

	public LazyDataModel<ProjectImplementationPlan> getAllActiveContractProjectImplementationPlanDataModel() {
		return allActiveContractProjectImplementationPlanDataModel;
	}

	public void setAllActiveContractProjectImplementationPlanDataModel(
			LazyDataModel<ProjectImplementationPlan> allActiveContractProjectImplementationPlanDataModel) {
		this.allActiveContractProjectImplementationPlanDataModel = allActiveContractProjectImplementationPlanDataModel;
	}

	public List<ProjectImplementationPlan> getProjectImplementationPlanList() {
		return projectImplementationPlanList;
	}

	public void setProjectImplementationPlanList(List<ProjectImplementationPlan> projectImplementationPlanList) {
		this.projectImplementationPlanList = projectImplementationPlanList;
	}

	public List<Signoff> getSignOffMoaList() {
		return signOffMoaList;
	}

	public void setSignOffMoaList(List<Signoff> signOffMoaList) {
		this.signOffMoaList = signOffMoaList;
	}

	public ProjectImplementationPlan getSelectedProjectImplementationPlan() {
		return selectedProjectImplementationPlan;
	}

	public void setSelectedProjectImplementationPlan(ProjectImplementationPlan selectedProjectImplementationPlan) {
		this.selectedProjectImplementationPlan = selectedProjectImplementationPlan;
	}

	public LazyDataModel<ActiveContractDetail> getAllActiveContractPaymentsDataModel() {
		return allActiveContractPaymentsDataModel;
	}

	public void setAllActiveContractPaymentsDataModel(
			LazyDataModel<ActiveContractDetail> allActiveContractPaymentsDataModel) {
		this.allActiveContractPaymentsDataModel = allActiveContractPaymentsDataModel;
	}

	public LazyDataModel<ActiveContractDetail> getAllProjectImplementationPlanPaymentsDataModel() {
		return allProjectImplementationPlanPaymentsDataModel;
	}

	public void setAllProjectImplementationPlanPaymentsDataModel(
			LazyDataModel<ActiveContractDetail> allProjectImplementationPlanPaymentsDataModel) {
		this.allProjectImplementationPlanPaymentsDataModel = allProjectImplementationPlanPaymentsDataModel;
	}

	public List<InterventionType> getAllApprenticeshipInterventions() {
		return allApprenticeshipInterventions;
	}

	public void setAllApprenticeshipInterventions(List<InterventionType> allApprenticeshipInterventions) {
		this.allApprenticeshipInterventions = allApprenticeshipInterventions;
	}

	public List<InterventionType> getAllOtherInterventions() {
		return allOtherInterventions;
	}

	public void setAllOtherInterventions(List<InterventionType> allOtherInterventions) {
		this.allOtherInterventions = allOtherInterventions;
	}

	public LazyDataModel<CompanyLearners> getAllMetalTbLearnersDataModel() {
		return allMetalTbLearnersDataModel;
	}

	public void setAllMetalTbLearnersDataModel(LazyDataModel<CompanyLearners> allMetalTbLearnersDataModel) {
		this.allMetalTbLearnersDataModel = allMetalTbLearnersDataModel;
	}

	public LazyDataModel<CompanyLearners> getAllMotorTbLearnersDataModel() {
		return allMotorTbLearnersDataModel;
	}

	public void setAllMotorTbLearnersDataModel(LazyDataModel<CompanyLearners> allMotorTbLearnersDataModel) {
		this.allMotorTbLearnersDataModel = allMotorTbLearnersDataModel;
	}

	public LazyDataModel<ActiveContractDetail> getAllCompanyLearnerPaymentsDataModel() {
		return allCompanyLearnerPaymentsDataModel;
	}

	public void setAllCompanyLearnerPaymentsDataModel(
			LazyDataModel<ActiveContractDetail> allCompanyLearnerPaymentsDataModel) {
		this.allCompanyLearnerPaymentsDataModel = allCompanyLearnerPaymentsDataModel;
	}

	public CompanyLearners getSelectedCompanyLearners() {
		return selectedCompanyLearners;
	}

	public void setSelectedCompanyLearners(CompanyLearners selectedCompanyLearners) {
		this.selectedCompanyLearners = selectedCompanyLearners;
	}

	public LazyDataModel<WorkplaceMonitoringSiteVisit> getAllWorkplaceMonitoringSiteVisitByCompanyDataModel() {
		return allWorkplaceMonitoringSiteVisitByCompanyDataModel;
	}

	public void setAllWorkplaceMonitoringSiteVisitByCompanyDataModel(
			LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitByCompanyDataModel) {
		this.allWorkplaceMonitoringSiteVisitByCompanyDataModel = allWorkplaceMonitoringSiteVisitByCompanyDataModel;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Date getSiteVisitDate() {
		return siteVisitDate;
	}

	public void setSiteVisitDate(Date siteVisitDate) {
		this.siteVisitDate = siteVisitDate;
	}

	public Boolean getCanCreateSiteVisit() {
		return canCreateSiteVisit;
	}

	public void setCanCreateSiteVisit(Boolean canCreateSiteVisit) {
		this.canCreateSiteVisit = canCreateSiteVisit;
	}
	
	
}