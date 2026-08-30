package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.DisabilityStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.DisabilityStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabilityStatusUI.
 */
@ManagedBean(name = "disabilitystatusUI")
@ViewScoped
public class DisabilityStatusUI extends AbstractUI {

	/** The service. */
	private DisabilityStatusService service = new DisabilityStatusService();
	
	/** The disabilitystatus list. */
	private List<DisabilityStatus> disabilitystatusList = null;
	
	/** The disabilitystatusfiltered list. */
	private List<DisabilityStatus> disabilitystatusfilteredList = null;
	
	/** The disabilitystatus. */
	private DisabilityStatus disabilitystatus = null;
	
	/** The data model. */
	private LazyDataModel<DisabilityStatus> dataModel;

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
	 * Initialize method to get all DisabilityStatus and prepare a for a create
	 * of a new DisabilityStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see DisabilityStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		disabilitystatusInfo();
	}

	/**
	 * Get all DisabilityStatus for data table.
	 *
	 * @author TechFinium
	 * @see DisabilityStatus
	 */
	public void disabilitystatusInfo() {

		dataModel = new LazyDataModel<DisabilityStatus>() {

			private static final long serialVersionUID = 1L;
			private List<DisabilityStatus> retorno = new ArrayList<DisabilityStatus>();

			@Override
			public List<DisabilityStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allDisabilityStatus(DisabilityStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(DisabilityStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DisabilityStatus obj) {
				return obj.getId();
			}

			@Override
			public DisabilityStatus getRowData(String rowKey) {
				for (DisabilityStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert DisabilityStatus into database.
	 *
	 * @author TechFinium
	 * @see DisabilityStatus
	 */
	public void disabilitystatusInsert() {
		try {
			service.create(this.disabilitystatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			disabilitystatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DisabilityStatus in database.
	 *
	 * @author TechFinium
	 * @see DisabilityStatus
	 */
	public void disabilitystatusUpdate() {
		try {
			service.update(this.disabilitystatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			disabilitystatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DisabilityStatus from database.
	 *
	 * @author TechFinium
	 * @see DisabilityStatus
	 */
	public void disabilitystatusDelete() {
		try {
			service.delete(this.disabilitystatus);
			prepareNew();
			disabilitystatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DisabilityStatus.
	 *
	 * @author TechFinium
	 * @see DisabilityStatus
	 */
	public void prepareNew() {
		disabilitystatus = new DisabilityStatus();
	}

	/*
	 * public List<SelectItem> getDisabilityStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * disabilitystatusInfo(); for (DisabilityStatus ug : disabilitystatusList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DisabilityStatus> complete(String desc) {
		List<DisabilityStatus> l = null;
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
	 * Gets the disability status list.
	 *
	 * @return the disability status list
	 */
	public List<DisabilityStatus> getDisabilityStatusList() {
		return disabilitystatusList;
	}

	/**
	 * Sets the disability status list.
	 *
	 * @param disabilitystatusList the new disability status list
	 */
	public void setDisabilityStatusList(List<DisabilityStatus> disabilitystatusList) {
		this.disabilitystatusList = disabilitystatusList;
	}

	/**
	 * Gets the disabilitystatus.
	 *
	 * @return the disabilitystatus
	 */
	public DisabilityStatus getDisabilitystatus() {
		return disabilitystatus;
	}

	/**
	 * Sets the disabilitystatus.
	 *
	 * @param disabilitystatus the new disabilitystatus
	 */
	public void setDisabilitystatus(DisabilityStatus disabilitystatus) {
		this.disabilitystatus = disabilitystatus;
	}

	/**
	 * Gets the disability statusfiltered list.
	 *
	 * @return the disability statusfiltered list
	 */
	public List<DisabilityStatus> getDisabilityStatusfilteredList() {
		return disabilitystatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param disabilitystatusfilteredList            the new disabilitystatusfilteredList list
	 * @see DisabilityStatus
	 */
	public void setDisabilityStatusfilteredList(List<DisabilityStatus> disabilitystatusfilteredList) {
		this.disabilitystatusfilteredList = disabilitystatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<DisabilityStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<DisabilityStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
