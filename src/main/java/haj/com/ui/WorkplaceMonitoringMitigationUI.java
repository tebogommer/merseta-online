package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.service.WorkplaceMonitoringMitigationService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "workplacemonitoringmitigationUI")
@ViewScoped
public class WorkplaceMonitoringMitigationUI extends AbstractUI {

	private WorkplaceMonitoringMitigationService service = new WorkplaceMonitoringMitigationService();
	private List<WorkplaceMonitoringMitigation> workplacemonitoringmitigationList = null;
	private List<WorkplaceMonitoringMitigation> workplacemonitoringmitigationfilteredList = null;
	private WorkplaceMonitoringMitigation workplacemonitoringmitigation = null;
	private LazyDataModel<WorkplaceMonitoringMitigation> dataModel;
	private WorkplaceMonitoring workplacemonitoring = null;

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
	 * Initialize method to get all WorkplaceMonitoringMitigation and prepare a for
	 * a create of a new WorkplaceMonitoringMitigation
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringmitigationInfo();
	}

	/**
	 * Get all WorkplaceMonitoringMitigation for data table
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 */
	public void workplacemonitoringmitigationInfo() {

		dataModel = new LazyDataModel<WorkplaceMonitoringMitigation>() {

			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringMitigation> retorno = new ArrayList<WorkplaceMonitoringMitigation>();

			@Override
			public List<WorkplaceMonitoringMitigation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allWorkplaceMonitoringMitigation(WorkplaceMonitoringMitigation.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(WorkplaceMonitoringMitigation.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringMitigation obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringMitigation getRowData(String rowKey) {
				for (WorkplaceMonitoringMitigation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert WorkplaceMonitoringMitigation into database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 */
	public void workplacemonitoringmitigationInsert() {
		try {
			this.workplacemonitoringmitigation.setWorkplaceMonitoring(workplacemonitoring);
			service.create(this.workplacemonitoringmitigation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringmitigationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WorkplaceMonitoringMitigation in database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 */
	public void workplacemonitoringmitigationUpdate() {
		try {
			service.update(this.workplacemonitoringmitigation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringmitigationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkplaceMonitoringMitigation from database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 */
	public void workplacemonitoringmitigationDelete() {
		try {
			service.delete(this.workplacemonitoringmitigation);
			prepareNew();
			workplacemonitoringmitigationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WorkplaceMonitoringMitigation
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigation
	 */
	public void prepareNew() {
		workplacemonitoringmitigation = new WorkplaceMonitoringMitigation();
	}

	/*
	 * public List<SelectItem> getWorkplaceMonitoringMitigationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * workplacemonitoringmitigationInfo(); for (WorkplaceMonitoringMitigation ug :
	 * workplacemonitoringmitigationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkplaceMonitoringMitigation> complete(String desc) {
		List<WorkplaceMonitoringMitigation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WorkplaceMonitoringMitigation> getWorkplaceMonitoringMitigationList() {
		return workplacemonitoringmitigationList;
	}

	public void setWorkplaceMonitoringMitigationList(List<WorkplaceMonitoringMitigation> workplacemonitoringmitigationList) {
		this.workplacemonitoringmitigationList = workplacemonitoringmitigationList;
	}

	public WorkplaceMonitoringMitigation getWorkplacemonitoringmitigation() {
		return workplacemonitoringmitigation;
	}

	public void setWorkplacemonitoringmitigation(WorkplaceMonitoringMitigation workplacemonitoringmitigation) {
		this.workplacemonitoringmitigation = workplacemonitoringmitigation;
	}

	public List<WorkplaceMonitoringMitigation> getWorkplaceMonitoringMitigationfilteredList() {
		return workplacemonitoringmitigationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param workplacemonitoringmitigationfilteredList
	 *            the new workplacemonitoringmitigationfilteredList list
	 * @see WorkplaceMonitoringMitigation
	 */
	public void setWorkplaceMonitoringMitigationfilteredList(List<WorkplaceMonitoringMitigation> workplacemonitoringmitigationfilteredList) {
		this.workplacemonitoringmitigationfilteredList = workplacemonitoringmitigationfilteredList;
	}

	public LazyDataModel<WorkplaceMonitoringMitigation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringMitigation> dataModel) {
		this.dataModel = dataModel;
	}

	public WorkplaceMonitoring getWorkplacemonitoring() {
		return workplacemonitoring;
	}

	public void setWorkplacemonitoring(WorkplaceMonitoring workplacemonitoring) {
		this.workplacemonitoring = workplacemonitoring;
	}

}
