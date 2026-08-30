package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "allWorkplaceMonitoringNonComplianceUI")
@ViewScoped
public class AllWorkplaceMonitoringNonComplianceUI extends AbstractUI {

	/* Entity Level */
	private WorkplaceMonitoringSiteVisit 		workplaceMonitoringSiteVisit 			= null;
	private WorkplaceMonitoringSiteVisit 		workplaceMonitoringSiteVisitToUpdate 	= null;
	private WorkplaceMonitoringMitigationPlan	workplaceMonitoringMitigationPlan		= null;
	
	/* Service Level */
	private CompanyService								companyService								= new CompanyService();
	private WorkplaceMonitoringSiteVisitService 		workplaceMonitoringSiteVisitService 		= new WorkplaceMonitoringSiteVisitService();
	private WorkplaceMonitoringMitigationPlanService	workplaceMonitoringMitigationPlanService	= new WorkplaceMonitoringMitigationPlanService();
	
	/* Lazy Data Models */
	private LazyDataModel<WorkplaceMonitoringSiteVisit> 		allWorkplaceMonitoringSiteVisitDataModel;
	private LazyDataModel<WorkplaceMonitoringSiteVisit> 		allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel;
	private LazyDataModel<WorkplaceMonitoringMitigationPlan>	workplaceMonitoringMitigationPlanDataModel;
	
	/* VARS */
	private Boolean cloCanAction	= false;
	private Boolean crmCloUser 		= false;
	private Boolean canActionWpm 	= false;
	private Date minDate			= null;
	private Date newDate			= null;
	
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
		identifyUserLevel();
		populateDates();
		allWorkplaceMonitoringSiteVisitDataModelInfo();
		allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModelInfo();
	}
	
	private void populateDates() throws Exception {
		minDate = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(getNow(), 1));
		newDate = minDate;
	}

	private void identifyUserLevel() throws Exception{
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		// no client users should be able to view DG Verifications
		if (hce == null) {
			crmCloUser = false;
		} else {
			crmCloUser = RegionTownService.instance().checkIfCrmCloUser(hce);
		}
		if (getSessionUI().getRole() != null && getSessionUI().getRole().getInitiateWorkplaceMonitoring() != null) {
			canActionWpm = getSessionUI().getRole().getInitiateWorkplaceMonitoring();
		} else if (crmCloUser) {
			canActionWpm = true;
		}
	}
	
	public void allWorkplaceMonitoringSiteVisitDataModelInfo() {
		allWorkplaceMonitoringSiteVisitDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByCloCrmUserOpenWhereNonComplianceIdentified(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(), ApprovalEnum.PendingComplianceIssues);
						allWorkplaceMonitoringSiteVisitDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByCloCrmUserOpenWhereNonComplianceIdentified(filters));
					} else {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByOpenWhereNonComplianceIdentified(first, pageSize, sortField, sortOrder, filters, ApprovalEnum.PendingComplianceIssues);
						allWorkplaceMonitoringSiteVisitDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByOpenWhereNonComplianceIdentified(filters));
					}
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
	
	public void allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModelInfo() {
		allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByCloCrmUserWhereNonComplianceIdentified(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(), Boolean.TRUE);
						allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByCloCrmUserWhereNonComplianceIdentified(filters));
					} else {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByNonComplianceIdentified(first, pageSize, sortField, sortOrder, filters, Boolean.TRUE);
						allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByNonComplianceIdentified(filters));
					}
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
	
	public void prepUpdateToNonComplianceIssues(){
		try {
			workplaceMonitoringMitigationPlanDataModelInfo();
			// check CLO permissions
			cloCanAction = false;
			Users cloByRegionUser = null;
			if (workplaceMonitoringSiteVisit != null && workplaceMonitoringSiteVisit.getCompany() != null && workplaceMonitoringSiteVisit.getCompany().getId() != null) {
				Company company = companyService.resolveCompanyAddressesReturnCompany(companyService.findByKeyNoResolveData(workplaceMonitoringSiteVisit.getCompany().getId()));
				RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
				if (rt.getClo() != null && rt.getClo().getUsers() != null && rt.getClo().getUsers().getId() != null) {
					cloByRegionUser = rt.getClo().getUsers();
				}
				company = null;
				rt = null;
			}
			if(crmCloUser) {
				if (workplaceMonitoringSiteVisit != null && workplaceMonitoringSiteVisit.getCloUser() != null && workplaceMonitoringSiteVisit.getCloUser().getId().equals(getSessionUI().getActiveUser().getId())) {
					cloCanAction = true;
				} else if (cloByRegionUser != null && cloByRegionUser.getId() != null && cloByRegionUser.getId().equals(getSessionUI().getActiveUser().getId())) {
					cloCanAction = true;
				}
			}
			cloByRegionUser = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void workplaceMonitoringMitigationPlanDataModelInfo() {
		workplaceMonitoringMitigationPlanDataModel = new LazyDataModel<WorkplaceMonitoringMitigationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringMitigationPlan> retorno = new ArrayList<>();
			@Override
			public List<WorkplaceMonitoringMitigationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringMitigationPlanService.allWorkplaceMonitoringMitigationPlanBySiteVisit(first, pageSize, sortField, sortOrder, filters, workplaceMonitoringSiteVisit.getId());
					workplaceMonitoringMitigationPlanDataModel.setRowCount(workplaceMonitoringMitigationPlanService.countAllWorkplaceMonitoringMitigationPlanBySiteVisit(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkplaceMonitoringMitigationPlan obj) {
				return obj.getId();
			}
			@Override
			public WorkplaceMonitoringMitigationPlan getRowData(String rowKey) {
				for (WorkplaceMonitoringMitigationPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void closeViewUpdateToNonComplianceIssues(){
		try {
			workplaceMonitoringSiteVisit = null;
			addInfoMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateMitigationPlan(){
		try {
			runClientSideExecute("PF('mitigationPlanDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateMitigationPlan(){
		try {
			workplaceMonitoringMitigationPlanService.updatePlan(workplaceMonitoringMitigationPlan, getSessionUI().getActiveUser());
			runClientSideExecute("PF('mitigationPlanDlg').hide()");
			workplaceMonitoringMitigationPlanDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void submitNonComplianceForApproval(){
		try {
			workplaceMonitoringSiteVisitService.submitNonComplianceForApproval(workplaceMonitoringSiteVisit, getSessionUI().getActiveUser());
			workplaceMonitoringSiteVisit = null;
			allWorkplaceMonitoringSiteVisitDataModelInfo();
			allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Getters and Setters */
	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisit() {
		return workplaceMonitoringSiteVisit;
	}

	public void setWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit) {
		this.workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisit;
	}

	public Boolean getCrmCloUser() {
		return crmCloUser;
	}

	public void setCrmCloUser(Boolean crmCloUser) {
		this.crmCloUser = crmCloUser;
	}

	public Boolean getCanActionWpm() {
		return canActionWpm;
	}

	public void setCanActionWpm(Boolean canActionWpm) {
		this.canActionWpm = canActionWpm;
	}

	public LazyDataModel<WorkplaceMonitoringSiteVisit> getAllWorkplaceMonitoringSiteVisitDataModel() {
		return allWorkplaceMonitoringSiteVisitDataModel;
	}

	public void setAllWorkplaceMonitoringSiteVisitDataModel(LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitDataModel) {
		this.allWorkplaceMonitoringSiteVisitDataModel = allWorkplaceMonitoringSiteVisitDataModel;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisitToUpdate() {
		return workplaceMonitoringSiteVisitToUpdate;
	}

	public void setWorkplaceMonitoringSiteVisitToUpdate(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisitToUpdate) {
		this.workplaceMonitoringSiteVisitToUpdate = workplaceMonitoringSiteVisitToUpdate;
	}

	public LazyDataModel<WorkplaceMonitoringSiteVisit> getAllWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel() {
		return allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel;
	}

	public void setAllWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel(
			LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel) {
		this.allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel = allWorkplaceMonitoringSiteVisitNonComplianceCompletedDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanDataModel() {
		return workplaceMonitoringMitigationPlanDataModel;
	}

	public void setWorkplaceMonitoringMitigationPlanDataModel(
			LazyDataModel<WorkplaceMonitoringMitigationPlan> workplaceMonitoringMitigationPlanDataModel) {
		this.workplaceMonitoringMitigationPlanDataModel = workplaceMonitoringMitigationPlanDataModel;
	}

	public WorkplaceMonitoringMitigationPlan getWorkplaceMonitoringMitigationPlan() {
		return workplaceMonitoringMitigationPlan;
	}

	public void setWorkplaceMonitoringMitigationPlan(WorkplaceMonitoringMitigationPlan workplaceMonitoringMitigationPlan) {
		this.workplaceMonitoringMitigationPlan = workplaceMonitoringMitigationPlan;
	}

	public Boolean getCloCanAction() {
		return cloCanAction;
	}

	public void setCloCanAction(Boolean cloCanAction) {
		this.cloCanAction = cloCanAction;
	}
		
}