package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.lookup.JobTitle;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.TrainingProviderMonitoringService;
import haj.com.service.lookup.JobTitleService;
import haj.com.utils.GenericUtility;


@ManagedBean(name = "monitoringAuditScheduleUI")
@ViewScoped
public class MonitoringAuditScheduleUI extends AbstractUI {
	
	/* Entity */
	private JobTitle jobTitle = null;
	
	/* Service Levels */
	private TrainingProviderMonitoringService trainingProviderMonitoringService = new TrainingProviderMonitoringService();
	
	/* Booleans */
	private boolean qaManager = true;
	
	/* Lazy Data Models */
	private LazyDataModel<TrainingProviderMonitoring> dataModel;
	private LazyDataModel<TrainingProviderMonitoring> dataModelPassed;
	private LazyDataModel<TrainingProviderMonitoring> dataModelUpComing;

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		populateIfQaManager();
		trainingProviderMonitoringInfo();
	}
	
	private void populateIfQaManager() throws Exception{
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
	
	public void trainingProviderMonitoringInfo() {
		dataModel = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retorno = new ArrayList<TrainingProviderMonitoring>();
			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (qaManager) {
						retorno = trainingProviderMonitoringService.allAudits(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(trainingProviderMonitoringService.countAllAudits(filters));
					} else {
						filters.put("userId", getSessionUI().getActiveUser().getId());
						retorno = trainingProviderMonitoringService.allAuditsByQa(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(trainingProviderMonitoringService.countAllAuditsByQa(filters));
					}
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
		
		dataModelPassed = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retornoPassed = new ArrayList<TrainingProviderMonitoring>();
			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (qaManager) {
						retornoPassed = trainingProviderMonitoringService.allAuditsBeforeAfterDate(first, pageSize, sortField, sortOrder, filters, true, GenericUtility.getStartOfDay(getNow()));
						dataModelPassed.setRowCount(trainingProviderMonitoringService.countAllAuditsBeforeAfterDate(filters,true));
					} else {
						filters.put("userId", getSessionUI().getActiveUser().getId());
						retornoPassed = trainingProviderMonitoringService.allAuditsBeforeAfterDateByUser(first, pageSize, sortField, sortOrder, filters, true, GenericUtility.getStartOfDay(getNow()));
						dataModelPassed.setRowCount(trainingProviderMonitoringService.countAllAuditsBeforeAfterDateByUser(filters, true));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retornoPassed;
			}
			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}
			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retornoPassed) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
		
		dataModelUpComing = new LazyDataModel<TrainingProviderMonitoring>() {
			private static final long serialVersionUID = 1L;
			private List<TrainingProviderMonitoring> retornoUpcoming = new ArrayList<TrainingProviderMonitoring>();
			@Override
			public List<TrainingProviderMonitoring> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (qaManager) {
						retornoUpcoming = trainingProviderMonitoringService.allAuditsBeforeAfterDate(first, pageSize, sortField, sortOrder, filters, false, GenericUtility.getStartOfDay(getNow()));
						dataModelUpComing.setRowCount(trainingProviderMonitoringService.countAllAuditsBeforeAfterDate(filters,false));
					} else {
						filters.put("userId", getSessionUI().getActiveUser().getId());
						retornoUpcoming = trainingProviderMonitoringService.allAuditsBeforeAfterDateByUser(first, pageSize, sortField, sortOrder, filters, false, GenericUtility.getStartOfDay(getNow()));
						dataModelUpComing.setRowCount(trainingProviderMonitoringService.countAllAuditsBeforeAfterDateByUser(filters, false));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retornoUpcoming;
			}
			@Override
			public Object getRowKey(TrainingProviderMonitoring obj) {
				return obj.getId();
			}
			@Override
			public TrainingProviderMonitoring getRowData(String rowKey) {
				for (TrainingProviderMonitoring obj : retornoUpcoming) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	

	/* getters and setters */
	public boolean isQaManager() {
		return qaManager;
	}

	public void setQaManager(boolean qaManager) {
		this.qaManager = qaManager;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderMonitoring> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModelPassed() {
		return dataModelPassed;
	}

	public void setDataModelPassed(LazyDataModel<TrainingProviderMonitoring> dataModelPassed) {
		this.dataModelPassed = dataModelPassed;
	}

	public LazyDataModel<TrainingProviderMonitoring> getDataModelUpComing() {
		return dataModelUpComing;
	}

	public void setDataModelUpComing(LazyDataModel<TrainingProviderMonitoring> dataModelUpComing) {
		this.dataModelUpComing = dataModelUpComing;
	}

	
}
