package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EmployerApprovalStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EmployerApprovalStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployerApprovalStatusUI.
 */
@ManagedBean(name = "employerapprovalstatusUI")
@ViewScoped
public class EmployerApprovalStatusUI extends AbstractUI {

	/** The service. */
	private EmployerApprovalStatusService service = new EmployerApprovalStatusService();
	
	/** The employerapprovalstatus list. */
	private List<EmployerApprovalStatus> employerapprovalstatusList = null;
	
	/** The employerapprovalstatusfiltered list. */
	private List<EmployerApprovalStatus> employerapprovalstatusfilteredList = null;
	
	/** The employerapprovalstatus. */
	private EmployerApprovalStatus employerapprovalstatus = null;
	
	/** The data model. */
	private LazyDataModel<EmployerApprovalStatus> dataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all EmployerApprovalStatus and prepare a for a
	 * create of a new EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see EmployerApprovalStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		employerapprovalstatusInfo();
	}

	/**
	 * Get all EmployerApprovalStatus for data table.
	 *
	 * @author TechFinium
	 * @see EmployerApprovalStatus
	 */
	public void employerapprovalstatusInfo() {

		dataModel = new LazyDataModel<EmployerApprovalStatus>() {

			private static final long serialVersionUID = 1L;
			private List<EmployerApprovalStatus> retorno = new ArrayList<EmployerApprovalStatus>();

			@Override
			public List<EmployerApprovalStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEmployerApprovalStatus(EmployerApprovalStatus.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(EmployerApprovalStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EmployerApprovalStatus obj) {
				return obj.getId();
			}

			@Override
			public EmployerApprovalStatus getRowData(String rowKey) {
				for (EmployerApprovalStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert EmployerApprovalStatus into database.
	 *
	 * @author TechFinium
	 * @see EmployerApprovalStatus
	 */
	public void employerapprovalstatusInsert() {
		try {
			service.create(this.employerapprovalstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employerapprovalstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update EmployerApprovalStatus in database.
	 *
	 * @author TechFinium
	 * @see EmployerApprovalStatus
	 */
	public void employerapprovalstatusUpdate() {
		try {
			service.update(this.employerapprovalstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employerapprovalstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete EmployerApprovalStatus from database.
	 *
	 * @author TechFinium
	 * @see EmployerApprovalStatus
	 */
	public void employerapprovalstatusDelete() {
		try {
			service.delete(this.employerapprovalstatus);
			prepareNew();
			employerapprovalstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of EmployerApprovalStatus.
	 *
	 * @author TechFinium
	 * @see EmployerApprovalStatus
	 */
	public void prepareNew() {
		employerapprovalstatus = new EmployerApprovalStatus();
	}

	/*
	 * public List<SelectItem> getEmployerApprovalStatusDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * employerapprovalstatusInfo(); for (EmployerApprovalStatus ug :
	 * employerapprovalstatusList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EmployerApprovalStatus> complete(String desc) {
		List<EmployerApprovalStatus> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the employer approval status list.
	 *
	 * @return the employer approval status list
	 */
	public List<EmployerApprovalStatus> getEmployerApprovalStatusList() {
		return employerapprovalstatusList;
	}

	/**
	 * Sets the employer approval status list.
	 *
	 * @param employerapprovalstatusList the new employer approval status list
	 */
	public void setEmployerApprovalStatusList(List<EmployerApprovalStatus> employerapprovalstatusList) {
		this.employerapprovalstatusList = employerapprovalstatusList;
	}

	/**
	 * Gets the employerapprovalstatus.
	 *
	 * @return the employerapprovalstatus
	 */
	public EmployerApprovalStatus getEmployerapprovalstatus() {
		return employerapprovalstatus;
	}

	/**
	 * Sets the employerapprovalstatus.
	 *
	 * @param employerapprovalstatus the new employerapprovalstatus
	 */
	public void setEmployerapprovalstatus(EmployerApprovalStatus employerapprovalstatus) {
		this.employerapprovalstatus = employerapprovalstatus;
	}

	/**
	 * Gets the employer approval statusfiltered list.
	 *
	 * @return the employer approval statusfiltered list
	 */
	public List<EmployerApprovalStatus> getEmployerApprovalStatusfilteredList() {
		return employerapprovalstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param employerapprovalstatusfilteredList            the new employerapprovalstatusfilteredList list
	 * @see EmployerApprovalStatus
	 */
	public void setEmployerApprovalStatusfilteredList(List<EmployerApprovalStatus> employerapprovalstatusfilteredList) {
		this.employerapprovalstatusfilteredList = employerapprovalstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EmployerApprovalStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EmployerApprovalStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
