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

import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.Tasks;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.ActiveContractsService;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.ProjectImplementationPlanLearnersService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "dgAllocationRegisterUI")
@ViewScoped
public class DgAllocationRegisterUI extends AbstractUI {

	/* Entity */
	private Doc doc = null;
	private Company selectedCompany = null;
	private ActiveContracts selectedActiveContract = null;
	private ActiveContracts withdrawActiveContract = null;
	private ActiveContractDetail selectedActiveContractDetail = null;
	private ProjectImplementationPlan selectedProjectImplementationPlan = null;
	private ProjectImplementationPlanLearners selectedProjectImplementationPlanLearners = null;

	/* Service Level */
	private CompanyService companyService = null;
	private ActiveContractsService activeContractsService = null;
	private ActiveContractDetailService activeContractDetailService = null;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = null;
	private ProjectImplementationPlanService projectImplementationPlanService = null;
	private ProjectImplementationPlanLearnersService projectImplementationPlanLearnersService = null;
	private TasksService tasksService = null;

	/* Data Models */
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<ActiveContracts> activeContractsDataModel;
	private LazyDataModel<ActiveContractDetail> activeContractDetailDataModel;
	private LazyDataModel<ProjectImplementationPlan> projectImplementationPlanDataModel;
	private LazyDataModel<ProjectImplementationPlanLearners> projectImplementationPlanLearnersTasks;
	private LazyDataModel<Tasks> tasksDataModel;

	/* Vars */
	private Boolean crmCloUser = false;
	private StringBuilder tasksTargetClass;
	private Long tasksTargetKey = null;
	
	/* Enums */
	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;

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

	/**
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		populateServiceLevels();
		identifyUserEmployee();
		companyDataModelInfo();
		clearTasksSearchParams();
	}

	private void populateServiceLevels() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		if (activeContractDetailService == null) {
			activeContractDetailService = new ActiveContractDetailService();
		}
		if (activeContractDetailService == null) {
			activeContractDetailService = new ActiveContractDetailService();
		}
		if (hostingCompanyEmployeesService == null) {
			hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		}
		if (projectImplementationPlanService == null) {
			projectImplementationPlanService = new ProjectImplementationPlanService();
		}
		if (projectImplementationPlanLearnersService == null) {
			projectImplementationPlanLearnersService = new ProjectImplementationPlanLearnersService();
		}
	}

	private void identifyUserEmployee() throws Exception {
		HostingCompanyEmployees hce = hostingCompanyEmployeesService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		if (hce == null) {
			super.redirectToDashboard();
		} else {
			crmCloUser = RegionTownService.instance().checkIfCrmCloUser(hce);
		}
	}

	public void companyDataModelInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						retorno = companyService.allCompanyByCloCrmNoResolveData(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						companyDataModel.setRowCount(companyService.countRegion(Company.class, filters));
					} else {
						retorno = companyService.allCompanysLevyNotNullNoResolveData(Company.class, first, pageSize, sortField, sortOrder, filters);
						companyDataModel.setRowCount(companyService.countAllCompanysLevyNotNull(Company.class, filters));
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

	private void clearAll() {
		selectedCompany = null;
		selectedActiveContract = null;
		withdrawActiveContract = null;
		selectedActiveContractDetail = null;
		selectedProjectImplementationPlan = null;
		selectedProjectImplementationPlanLearners = null;
		
	}

	private void clearCompanySelection() {
		doc = null;
		selectedActiveContract = null;
		withdrawActiveContract = null;
		selectedActiveContractDetail = null;
		selectedProjectImplementationPlan = null;
		selectedProjectImplementationPlanLearners = null;
	}
	
	private void clearWithdrawContractSelection() {
		selectedActiveContract = null;
		selectedActiveContractDetail = null;
		selectedProjectImplementationPlan = null;
		selectedProjectImplementationPlanLearners = null;
	}

	private void clearContractSelection() {
		selectedActiveContractDetail = null;
		selectedProjectImplementationPlan = null;
		selectedProjectImplementationPlanLearners = null;
	}

	public void closeCompanyView() {
		try {
			clearAll();
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void selectCompany() {
		try {
			clearCompanySelection();
			activeContractsDataModelInfo();
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void activeContractsDataModelInfo() {
		activeContractsDataModel = new LazyDataModel<ActiveContracts>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContracts> retorno = new ArrayList<ActiveContracts>();
			@Override
			public List<ActiveContracts> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedCompany != null && selectedCompany.getId() != null) {
						retorno = activeContractsService.allActiveContractsLinkedToCompanyId(first, pageSize, sortField, sortOrder, filters, selectedCompany.getId());
						activeContractsDataModel.setRowCount(activeContractsService.countAllActiveContractsLinkedToCompanyId(filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
	
	public void prepWithdrawOfContract(){
		try {
			clearWithdrawContractSelection();
			// check to see if they can initiate payment
			activeContractsService.checkIfCanWithDrawApplication(withdrawActiveContract);
			doc = new Doc();
			doc.setTargetKey(withdrawActiveContract.getId());
			doc.setTargetClass(ActiveContracts.class.getName());
			doc.setUsers(getSessionUI().getActiveUser());
			runClientSideExecute("PF('dlgSelectReasonWithdrawal').show()");
		} catch (Exception e) {
			withdrawActiveContract = null;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
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
	
	public void withdrawActiveContractsCompanyContact() {
		try {
			if (withdrawalAppealEnum == null) {
				addErrorMessage("Select A Reason Before Proceeding");
			} else {
				if (doc == null) {
					addErrorMessage("Upload Evidance Before Proceeding");
				} else {
					activeContractsService.withdrawActiveContracts(withdrawActiveContract, getSessionUI().getActiveUser(), withdrawalAppealEnum, null, doc);
					addInfoMessage("Action Complete");
					runClientSideExecute("PF('dlgSelectReasonWithdrawal').hide()");
					clearCompanySelection();
					activeContractsDataModelInfo();
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void clearTasksSearchParams() {
		tasksTargetClass = new StringBuilder();
		tasksTargetKey = null;
	}
	
	private void populateTaskService() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
	}

	public void viewActiveContractsTasks() {
		try {
			populateTaskService();
			clearTasksSearchParams();
			if (selectedActiveContract != null && selectedActiveContract.getId() != null) {
				tasksTargetClass.append(selectedActiveContract.getClass().getName());
				tasksTargetKey = selectedActiveContract.getId();
				selectedActiveContract = null;
				runClientSideExecute("PF('tasksDlg').show()");
				tasksDataModelInfo();
			} else {
				addWarningMessage("Unable to find link for tasks view. Contact Support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeTaskView(){
		try {
			clearTasksSearchParams();
			runClientSideExecute("PF('tasksDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void tasksDataModelInfo() {
		tasksDataModel = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.DESCENDING;
					}
					if (tasksTargetKey != null && tasksTargetClass != null && !tasksTargetClass.toString().isEmpty()) {
						retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, tasksTargetKey, tasksTargetClass.toString());
						tasksDataModel.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
					}
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

	/* Getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public ActiveContracts getSelectedActiveContract() {
		return selectedActiveContract;
	}

	public void setSelectedActiveContract(ActiveContracts selectedActiveContract) {
		this.selectedActiveContract = selectedActiveContract;
	}

	public ActiveContractDetail getSelectedActiveContractDetail() {
		return selectedActiveContractDetail;
	}

	public void setSelectedActiveContractDetail(ActiveContractDetail selectedActiveContractDetail) {
		this.selectedActiveContractDetail = selectedActiveContractDetail;
	}

	public ProjectImplementationPlan getSelectedProjectImplementationPlan() {
		return selectedProjectImplementationPlan;
	}

	public void setSelectedProjectImplementationPlan(ProjectImplementationPlan selectedProjectImplementationPlan) {
		this.selectedProjectImplementationPlan = selectedProjectImplementationPlan;
	}

	public ProjectImplementationPlanLearners getSelectedProjectImplementationPlanLearners() {
		return selectedProjectImplementationPlanLearners;
	}

	public void setSelectedProjectImplementationPlanLearners(
			ProjectImplementationPlanLearners selectedProjectImplementationPlanLearners) {
		this.selectedProjectImplementationPlanLearners = selectedProjectImplementationPlanLearners;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public LazyDataModel<ActiveContracts> getActiveContractsDataModel() {
		return activeContractsDataModel;
	}

	public void setActiveContractsDataModel(LazyDataModel<ActiveContracts> activeContractsDataModel) {
		this.activeContractsDataModel = activeContractsDataModel;
	}

	public LazyDataModel<ActiveContractDetail> getActiveContractDetailDataModel() {
		return activeContractDetailDataModel;
	}

	public void setActiveContractDetailDataModel(LazyDataModel<ActiveContractDetail> activeContractDetailDataModel) {
		this.activeContractDetailDataModel = activeContractDetailDataModel;
	}

	public LazyDataModel<ProjectImplementationPlan> getProjectImplementationPlanDataModel() {
		return projectImplementationPlanDataModel;
	}

	public void setProjectImplementationPlanDataModel(
			LazyDataModel<ProjectImplementationPlan> projectImplementationPlanDataModel) {
		this.projectImplementationPlanDataModel = projectImplementationPlanDataModel;
	}

	public LazyDataModel<ProjectImplementationPlanLearners> getProjectImplementationPlanLearnersTasks() {
		return projectImplementationPlanLearnersTasks;
	}

	public void setProjectImplementationPlanLearnersTasks(
			LazyDataModel<ProjectImplementationPlanLearners> projectImplementationPlanLearnersTasks) {
		this.projectImplementationPlanLearnersTasks = projectImplementationPlanLearnersTasks;
	}

	public LazyDataModel<Tasks> getTasksDataModel() {
		return tasksDataModel;
	}

	public void setTasksDataModel(LazyDataModel<Tasks> tasksDataModel) {
		this.tasksDataModel = tasksDataModel;
	}

	public ActiveContracts getWithdrawActiveContract() {
		return withdrawActiveContract;
	}

	public void setWithdrawActiveContract(ActiveContracts withdrawActiveContract) {
		this.withdrawActiveContract = withdrawActiveContract;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
