package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService;

@ManagedBean(name = "workplacemonitoringdiscretionarygrantcompliancesurveyUI")
@ViewScoped
public class WorkplaceMonitoringDiscretionaryGrantComplianceSurveyUI extends AbstractUI {

	private WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService service = new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService();
	private List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplacemonitoringdiscretionarygrantcompliancesurveyList = null;
	private List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList = null;
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurvey workplacemonitoringdiscretionarygrantcompliancesurvey = null;
	private LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> dataModel;

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
	 * Initialize method to get all
	 * WorkplaceMonitoringDiscretionaryGrantComplianceSurvey and prepare a for a
	 * create of a new WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringdiscretionarygrantcompliancesurveyInfo();
	}

	/**
	 * Get all WorkplaceMonitoringDiscretionaryGrantComplianceSurvey for data
	 * table
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void workplacemonitoringdiscretionarygrantcompliancesurveyInfo() {
		dataModel = new LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>() {
			private static final long serialVersionUID = 1L;
			private List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> retorno = new ArrayList<>();

			@Override
			public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> load(int first, int pageSize,
					String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service
							.allWorkplaceMonitoringDiscretionaryGrantComplianceSurveyWhereNoTragetClassAssigned(first,
									pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(
							service.countAllWorkplaceMonitoringDiscretionaryGrantComplianceSurveyWhereNoTragetClassAssigned(
									filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey obj) {
				return obj.getId();
			}

			@Override
			public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey getRowData(String rowKey) {
				for (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert WorkplaceMonitoringDiscretionaryGrantComplianceSurvey into
	 * database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void workplacemonitoringdiscretionarygrantcompliancesurveyInsert() {
		try {
			service.createLookUpEntry(this.workplacemonitoringdiscretionarygrantcompliancesurvey);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringdiscretionarygrantcompliancesurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WorkplaceMonitoringDiscretionaryGrantComplianceSurvey in database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void workplacemonitoringdiscretionarygrantcompliancesurveyUpdate() {
		try {
			service.update(this.workplacemonitoringdiscretionarygrantcompliancesurvey);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			workplacemonitoringdiscretionarygrantcompliancesurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WorkplaceMonitoringDiscretionaryGrantComplianceSurvey from
	 * database
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void workplacemonitoringdiscretionarygrantcompliancesurveyDelete() {
		try {
			service.delete(this.workplacemonitoringdiscretionarygrantcompliancesurvey);
			prepareNew();
			workplacemonitoringdiscretionarygrantcompliancesurveyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of
	 * WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void prepareNew() {
		workplacemonitoringdiscretionarygrantcompliancesurvey = new WorkplaceMonitoringDiscretionaryGrantComplianceSurvey();
	}

	/*
	 * public List<SelectItem>
	 * getWorkplaceMonitoringDiscretionaryGrantComplianceSurveyDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * workplacemonitoringdiscretionarygrantcompliancesurveyInfo(); for
	 * (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey ug :
	 * workplacemonitoringdiscretionarygrantcompliancesurveyList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> complete(String desc) {
		List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> getWorkplaceMonitoringDiscretionaryGrantComplianceSurveyList() {
		return workplacemonitoringdiscretionarygrantcompliancesurveyList;
	}

	public void setWorkplaceMonitoringDiscretionaryGrantComplianceSurveyList(
			List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplacemonitoringdiscretionarygrantcompliancesurveyList) {
		this.workplacemonitoringdiscretionarygrantcompliancesurveyList = workplacemonitoringdiscretionarygrantcompliancesurveyList;
	}

	public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey getWorkplacemonitoringdiscretionarygrantcompliancesurvey() {
		return workplacemonitoringdiscretionarygrantcompliancesurvey;
	}

	public void setWorkplacemonitoringdiscretionarygrantcompliancesurvey(
			WorkplaceMonitoringDiscretionaryGrantComplianceSurvey workplacemonitoringdiscretionarygrantcompliancesurvey) {
		this.workplacemonitoringdiscretionarygrantcompliancesurvey = workplacemonitoringdiscretionarygrantcompliancesurvey;
	}

	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> getWorkplaceMonitoringDiscretionaryGrantComplianceSurveyfilteredList() {
		return workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList
	 *            the new
	 *            workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList
	 *            list
	 * @see WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void setWorkplaceMonitoringDiscretionaryGrantComplianceSurveyfilteredList(
			List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList) {
		this.workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList = workplacemonitoringdiscretionarygrantcompliancesurveyfilteredList;
	}

	public LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> dataModel) {
		this.dataModel = dataModel;
	}

}
