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

import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WorkplaceMonitoringSiteVisitService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "allWorkplaceMonitoringUI")
@ViewScoped
public class AllWorkplaceMonitoringUI extends AbstractUI {

	/* Entity Level */
	private WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit = null;
	private WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisitToInitiate = null;

	/* Service Level */
	private WorkplaceMonitoringSiteVisitService workplaceMonitoringSiteVisitService = new WorkplaceMonitoringSiteVisitService();

	/* Lazy Data Models */
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
		identifyUserLevel();
		populateDates();
		allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
		allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModelInfo();
	}

	private void populateDates() throws Exception {
		minDate = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(getNow(), 1));
		newDate = minDate;
	}

	private void identifyUserLevel() throws Exception {
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

	public void allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo() {
		allWorkplaceMonitoringSiteVisitDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitByCloCrmUser(first,
								pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId());
						allWorkplaceMonitoringSiteVisitDataModel.setRowCount(workplaceMonitoringSiteVisitService
								.countAllWorkplaceMonitoringSiteVisitByCloCrmUser(filters));
					} else {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisit(
								WorkplaceMonitoringSiteVisit.class, first, pageSize, sortField, sortOrder, filters);
						allWorkplaceMonitoringSiteVisitDataModel.setRowCount(
								workplaceMonitoringSiteVisitService.count(WorkplaceMonitoringSiteVisit.class, filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModelInfo() {
		allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel = new LazyDataModel<WorkplaceMonitoringSiteVisit>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringSiteVisit> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringSiteVisit> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (crmCloUser) {
						retorno = workplaceMonitoringSiteVisitService
								.allWorkplaceMonitoringSiteVisitAwaitingInitiationByCloCrmUser(first, pageSize,
										sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(),
										Boolean.TRUE);
						allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel
								.setRowCount(workplaceMonitoringSiteVisitService
										.countAllWorkplaceMonitoringSiteVisitAwaitingInitiationByCloCrmUser(filters));
					} else {
						retorno = workplaceMonitoringSiteVisitService.allWorkplaceMonitoringSiteVisitAwaitingInitiation(
								first, pageSize, sortField, sortOrder, filters, Boolean.TRUE);
						allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModel
								.setRowCount(workplaceMonitoringSiteVisitService
										.countAllWorkplaceMonitoringSiteVisitAwaitingInitiation(filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void updateMonitoringDate() {
		try {
			if (workplaceMonitoringSiteVisitToInitiate.getAwaitingInitiation()) {
				if (newDate.equals(workplaceMonitoringSiteVisitToInitiate.getMonitoringDate())) {
					addErrorMessage(
							"Date selected is the same as monitoring date assigned, please select a different date");
				} else {
					workplaceMonitoringSiteVisitService.initiateWorkplaceMonitoring(
							workplaceMonitoringSiteVisitToInitiate, getSessionUI().getActiveUser(), null, null);
					workplaceMonitoringSiteVisitToInitiate = null;
					allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModelInfo();
					allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
					addInfoMessage("Action complete");
				}
			} else {
				addErrorMessage(
						"Workplace Monitoring is already underway, you are unable to alter the monitoring date.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void initiateWorkplaceMonitroing() {
		try {
			if (workplaceMonitoringSiteVisitToInitiate.getAwaitingInitiation()) {
				workplaceMonitoringSiteVisitService.initiateWorkplaceMonitoring(workplaceMonitoringSiteVisitToInitiate,
						getSessionUI().getActiveUser(), null, null);
				allWorkplaceMonitoringSiteVisitAwaitingInitiationDataModelInfo();
				allWorkplaceMonitoringSiteVisitByCompanyDataModelInfo();
				workplaceMonitoringSiteVisitToInitiate = null;
				addInfoMessage("Action complete");
			} else {
				addErrorMessage("Workplace Monitoring is already underway, contact support on error recived.");
			}
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
}