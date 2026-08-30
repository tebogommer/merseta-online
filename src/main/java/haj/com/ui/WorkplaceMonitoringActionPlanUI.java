package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringActionPlanService;

@ManagedBean(name = "workplacemonitoringactionplanUI")
@ViewScoped
public class WorkplaceMonitoringActionPlanUI extends AbstractUI {

	private WorkplaceMonitoringActionPlanService service = new WorkplaceMonitoringActionPlanService();
	private List<WorkplaceMonitoringActionPlan> workplacemonitoringactionplanList = null;
	private List<WorkplaceMonitoringActionPlan> workplacemonitoringactionplanfilteredList = null;
	private WorkplaceMonitoringActionPlan workplacemonitoringactionplan = null;
	private LazyDataModel<WorkplaceMonitoringActionPlan> dataModel;

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
	 * Initialize method to get all WorkplaceMonitoringActionPlan and prepare a
	 * for a create of a new WorkplaceMonitoringActionPlan
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringactionplanInfo();
	}

	/**
	 * Get all WorkplaceMonitoringActionPlan for data table
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void workplacemonitoringactionplanInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoringActionPlan>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringActionPlan> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringActionPlan> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allWorkplaceMonitoringActionPlanWhereNoTragetClassAssigned(first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(
							service.countAllWorkplaceMonitoringActionPlanWhereNoTragetClassAssigned(filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringActionPlan obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringActionPlan getRowData(String rowKey) {
				for (WorkplaceMonitoringActionPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert WorkplaceMonitoringActionPlan into database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void workplacemonitoringactionplanInsert() {
		try {
			service.createLookUpEntry(this.workplacemonitoringactionplan);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringactionplanInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WorkplaceMonitoringActionPlan in database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void workplacemonitoringactionplanUpdate() {
		try {
			service.update(this.workplacemonitoringactionplan);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringactionplanInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkplaceMonitoringActionPlan from database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void workplacemonitoringactionplanDelete() {
		try {
			service.delete(this.workplacemonitoringactionplan);
			prepareNew();
			workplacemonitoringactionplanInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WorkplaceMonitoringActionPlan
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void prepareNew() {
		workplacemonitoringactionplan = new WorkplaceMonitoringActionPlan();
	}

	/*
	 * public List<SelectItem> getWorkplaceMonitoringActionPlanDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * workplacemonitoringactionplanInfo(); for (WorkplaceMonitoringActionPlan
	 * ug : workplacemonitoringactionplanList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkplaceMonitoringActionPlan> complete(String desc) {
		List<WorkplaceMonitoringActionPlan> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WorkplaceMonitoringActionPlan> getWorkplaceMonitoringActionPlanList() {
		return workplacemonitoringactionplanList;
	}

	public void setWorkplaceMonitoringActionPlanList(
			List<WorkplaceMonitoringActionPlan> workplacemonitoringactionplanList) {
		this.workplacemonitoringactionplanList = workplacemonitoringactionplanList;
	}

	public WorkplaceMonitoringActionPlan getWorkplacemonitoringactionplan() {
		return workplacemonitoringactionplan;
	}

	public void setWorkplacemonitoringactionplan(WorkplaceMonitoringActionPlan workplacemonitoringactionplan) {
		this.workplacemonitoringactionplan = workplacemonitoringactionplan;
	}

	public List<WorkplaceMonitoringActionPlan> getWorkplaceMonitoringActionPlanfilteredList() {
		return workplacemonitoringactionplanfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param workplacemonitoringactionplanfilteredList
	 *            the new workplacemonitoringactionplanfilteredList list
	 * @see WorkplaceMonitoringActionPlan
	 */
	public void setWorkplaceMonitoringActionPlanfilteredList(
			List<WorkplaceMonitoringActionPlan> workplacemonitoringactionplanfilteredList) {
		this.workplacemonitoringactionplanfilteredList = workplacemonitoringactionplanfilteredList;
	}

	public LazyDataModel<WorkplaceMonitoringActionPlan> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringActionPlan> dataModel) {
		this.dataModel = dataModel;
	}

}
