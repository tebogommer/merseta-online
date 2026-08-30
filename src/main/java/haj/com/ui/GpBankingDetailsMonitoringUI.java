package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.GpBankAccountAlterAudit;
import haj.com.entity.Tasks;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.GpBankAccountAlterAuditService;
import haj.com.service.TasksService;


@ManagedBean(name = "gpBankingDetailsMonitoringUI")
@ViewScoped
public class GpBankingDetailsMonitoringUI extends AbstractUI {

	/* Entity Level */
	private Vendor 			vendor;
	private Company 		selectedCompany 				= null;
	private BankingDetails 	bankingDetails 					= null;
	private BankingDetails 	selectedBankingDetails 			= null;
	private BankingDetails 	selectedBankingDetailsTaskView 	= null;
	private GpBankAccountAlterAudit	gpBankAccountAlterAudit = null;
	
	/* Service Level */
	private TasksService					tasksService					= new TasksService();
	private CompanyService 					companyService 					= new CompanyService();
	private BankingDetailsService 			bankingDetailsService 			= new BankingDetailsService();
	private GpBankAccountAlterAuditService	gpBankAccountAlterAuditService	= new GpBankAccountAlterAuditService();
	
	/* Lazy Data Models */
	private LazyDataModel<Tasks> 					dataModelTasks;
	private LazyDataModel<Company> 					dataModelComapny;
	private LazyDataModel<GpBankAccountAlterAudit> 	gpBankAccountAlterAuditDataModel;
	
	/* Array lists */
	private List<BankingDetails> companyApprovedBankingDetails;
	private List<BankingDetails> bankingDetailsList;
	private List<BankingDetails> allBankingDetailsList;
	
	/* Boolean */
	private boolean hasAccess = false;
	
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
		// check if user has access
		validiateAccess();
		if (hasAccess) {
			populateCompanies();
			gpBankAccountAlterAuditDataModelInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}
	
	public void gpBankAccountAlterAuditDataModelInfo() {
		gpBankAccountAlterAuditDataModel = new LazyDataModel<GpBankAccountAlterAudit>() {
			private static final long serialVersionUID = 1L;
			private List<GpBankAccountAlterAudit> retorno = new ArrayList<>();
			@Override
			public List<GpBankAccountAlterAudit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = gpBankAccountAlterAuditService.allGpBankAccountAlterAudit(GpBankAccountAlterAudit.class, first, pageSize, sortField, sortOrder, filters);
					gpBankAccountAlterAuditDataModel.setRowCount(gpBankAccountAlterAuditService.count(GpBankAccountAlterAudit.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(GpBankAccountAlterAudit obj) {
				return obj.getId();
			}
			@Override
			public GpBankAccountAlterAudit getRowData(String rowKey) {
				for (GpBankAccountAlterAudit obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	private void validiateAccess() {
		hasAccess = false;
		if (getSessionUI().isAdmin()) {
			hasAccess = true;
		}else if (getSessionUI() != null && getSessionUI().getActiveUser() != null && getSessionUI().getActiveUser().getGpBankingDetails() != null && getSessionUI().getActiveUser().getGpBankingDetails()) {
			hasAccess = true;
		}
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompanySetaCompanies(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModelComapny.setRowCount(companyService.countAllCompanySetaCompaniesn(filters));
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
			bankingDetailsList = bankingDetailsService.findByCompanyLatestApproved(selectedCompany);
			allBankingDetailsList = bankingDetailsService.findByCompanyLatest(selectedCompany);
			populateVendorFromGP();
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateVendorFromGP() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			companyApprovedBankingDetails = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(selectedCompany) > 1) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
				companyApprovedBankingDetails.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompany) > 0) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
//			this.vendor = bankingDetailsService.getGPDetailsForLNumberAndCreateIfApp(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
//			if (this.vendor == null) {
//				this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addCompanyToGP(){
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			companyApprovedBankingDetails = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(selectedCompany) > 1) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
				companyApprovedBankingDetails.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompany) > 0) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumberAndCreateIfApp(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
			if (this.vendor == null) {
				this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
			}
			bankingDetailsList = bankingDetailsService.findByCompanyLatestApproved(selectedCompany);
			addInfoMessage("Action Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDetailsOnGP(){
		try {
			if (selectedBankingDetails != null) {
				bankingDetailsService.updateBankningDetailsOnGP(bankingDetails, getSessionUI().getActiveUser(), vendor);
				selectCompany();
				addInfoMessage("Action Complete");
			} else {
				addErrorMessage("Unable to locate selected Banking Details, contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepSettingOnHold() {
		try {
			gpBankAccountAlterAudit = new GpBankAccountAlterAudit();
			gpBankAccountAlterAudit.setSetToHold(true);
			gpBankAccountAlterAudit.setCompany(selectedCompany);
			gpBankAccountAlterAudit.setUsers(getSessionUI().getActiveUser());
			runClientSideExecute("PF('gpHoldDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// not in use right now
	public void prepRemovingOnHold() {
		try {
			gpBankAccountAlterAudit = new GpBankAccountAlterAudit();
			gpBankAccountAlterAudit.setSetToHold(false);
			gpBankAccountAlterAudit.setCompany(selectedCompany);
			gpBankAccountAlterAudit.setUsers(getSessionUI().getActiveUser());
			runClientSideExecute("PF('gpHoldDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateDetailsOnGPHoldStatusTrue(){
		try {
			bankingDetailsService.updateBankningDetailsOnGPHoldStatusWithReason(getSessionUI().getActiveUser(), vendor, gpBankAccountAlterAudit.getSetToHold(), gpBankAccountAlterAudit.getReason());
			gpBankAccountAlterAuditService.create(gpBankAccountAlterAudit);
			if (gpBankAccountAlterAudit != null && gpBankAccountAlterAudit.getSetToHold() != null && gpBankAccountAlterAudit.getSetToHold()) {
				gpBankAccountAlterAuditService.sendNotificationForOnHold(gpBankAccountAlterAudit, gpBankAccountAlterAudit.getCompany(), getSessionUI().getActiveUser());	
			}
			gpBankAccountAlterAudit = null;
			selectCompany();
			gpBankAccountAlterAuditDataModelInfo();
			runClientSideExecute("PF('gpHoldDlg').hide()");
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void viewTasksAssigned(){
		try {
			if (selectedBankingDetailsTaskView != null && selectedBankingDetailsTaskView.getId() != null) {
				dataModelTasksInfo();
				runClientSideExecute("PF('taskViewDlg').show()");
			} else {
				addErrorMessage("Unable to locate banking details selected. Contact Support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelTasksInfo() {
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.DESCENDING;
					}
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, selectedBankingDetailsTaskView.getId(), selectedBankingDetailsTaskView.getClass().getName());
					dataModelTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
				} catch (Exception e) {
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


	/* getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<BankingDetails> getCompanyApprovedBankingDetails() {
		return companyApprovedBankingDetails;
	}

	public void setCompanyApprovedBankingDetails(List<BankingDetails> companyApprovedBankingDetails) {
		this.companyApprovedBankingDetails = companyApprovedBankingDetails;
	}

	public BankingDetails getSelectedBankingDetails() {
		return selectedBankingDetails;
	}

	public void setSelectedBankingDetails(BankingDetails selectedBankingDetails) {
		this.selectedBankingDetails = selectedBankingDetails;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}

	public List<BankingDetails> getAllBankingDetailsList() {
		return allBankingDetailsList;
	}

	public void setAllBankingDetailsList(List<BankingDetails> allBankingDetailsList) {
		this.allBankingDetailsList = allBankingDetailsList;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public BankingDetails getSelectedBankingDetailsTaskView() {
		return selectedBankingDetailsTaskView;
	}

	public void setSelectedBankingDetailsTaskView(BankingDetails selectedBankingDetailsTaskView) {
		this.selectedBankingDetailsTaskView = selectedBankingDetailsTaskView;
	}

	public LazyDataModel<GpBankAccountAlterAudit> getGpBankAccountAlterAuditDataModel() {
		return gpBankAccountAlterAuditDataModel;
	}

	public void setGpBankAccountAlterAuditDataModel(
			LazyDataModel<GpBankAccountAlterAudit> gpBankAccountAlterAuditDataModel) {
		this.gpBankAccountAlterAuditDataModel = gpBankAccountAlterAuditDataModel;
	}

	public GpBankAccountAlterAudit getGpBankAccountAlterAudit() {
		return gpBankAccountAlterAudit;
	}

	public void setGpBankAccountAlterAudit(GpBankAccountAlterAudit gpBankAccountAlterAudit) {
		this.gpBankAccountAlterAudit = gpBankAccountAlterAudit;
	}
	
}