package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.SDFCompany;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.SDFCompanyService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "allWorkplaceMonitoringSdfViewUI")
@ViewScoped
public class AllWorkplaceMonitoringSdfViewUI extends AbstractUI {

	/* Entity Level */
	private Company 						selectedCompany 						= null;
	private WorkplaceMonitoringSiteVisit 	workplaceMonitoringSiteVisit 			= null;
	private WorkplaceMonitoringSiteVisit 	workplaceMonitoringSiteVisitToInitiate 	= null;
	
	/* Service Level */
	private CompanyService 						companyService 						= new CompanyService();
	private SDFCompanyService					sdfCompanyService					= new SDFCompanyService();
	private WorkplaceMonitoringSiteVisitService workplaceMonitoringSiteVisitService = new WorkplaceMonitoringSiteVisitService();
	
	/* Lazy Data Models */
	private LazyDataModel<SDFCompany> sdfCompanyDataModel;
	private LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitDataModel;
	private LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel;

	/* VARS */
	private Boolean crmCloUser = false;
	private Boolean canActionWpm = false;
	private Date minDate = null;
	private Date newDate = null;

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
		infoClearAll();
		sdfCompanyDataModelInfo();
	}
	
	public void sdfCompanyDataModelInfo() {
		sdfCompanyDataModel = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();
			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {
						retorno = sdfCompanyService.allCompanyFromSDFWherePrimary(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						sdfCompanyDataModel.setRowCount(sdfCompanyService.allCompanyFromSDFCountWherePrimary(filters, getSessionUI().getActiveUser()).intValue());
					} else {
						retorno = sdfCompanyService.allSDFCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						sdfCompanyDataModel.setRowCount(sdfCompanyService.count(SDFCompany.class, filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void infoClearAll(){
		selectedCompany = null;
		workplaceMonitoringSiteVisit = null;
	}
	
	public void selectCompany(){
		try {
			workplaceMonitoringSiteVisit = null;
			selectedCompany = companyService.resolveCompanyAddressesReturnCompany(companyService.findByKeyNoResolveData(selectedCompany.getId()));
			allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeCompanySelectedView(){
		try {
			infoClearAll();
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo() {
		allWorkplaceMonitoringSiteVisitDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByCompanyId( first, pageSize, sortField, sortOrder, filters, selectedCompany.getId());
					allWorkplaceMonitoringSiteVisitDataModel.setRowCount(workplaceMonitoringSiteVisitService.countAllWorkplaceMonitoringSiteVisitByCompanyId( filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
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

	public void setAllWorkplaceMonitoringSiteVisitDataModel(
			LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitDataModel) {
		this.allWorkplaceMonitoringSiteVisitDataModel = allWorkplaceMonitoringSiteVisitDataModel;
	}

	public LazyDataModel<WorkplaceMonitoringSiteVisit> getAllWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel() {
		return allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel;
	}

	public void setAllWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel(
			LazyDataModel<WorkplaceMonitoringSiteVisit> allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel) {
		this.allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel = allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel;
	}

	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisitToInitiate() {
		return workplaceMonitoringSiteVisitToInitiate;
	}

	public void setWorkplaceMonitoringSiteVisitToInitiate(
			WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisitToInitiate) {
		this.workplaceMonitoringSiteVisitToInitiate = workplaceMonitoringSiteVisitToInitiate;
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

	public LazyDataModel<SDFCompany> getSdfCompanyDataModel() {
		return sdfCompanyDataModel;
	}

	public void setSdfCompanyDataModel(LazyDataModel<SDFCompany> sdfCompanyDataModel) {
		this.sdfCompanyDataModel = sdfCompanyDataModel;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}
}