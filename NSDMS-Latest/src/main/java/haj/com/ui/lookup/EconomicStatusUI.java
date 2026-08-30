package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.EconomicStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.EconomicStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class EconomicStatusUI.
 */
@ManagedBean(name = "economicstatusUI")
@ViewScoped
public class EconomicStatusUI extends AbstractUI {

	/** The service. */
	private EconomicStatusService service = new EconomicStatusService();
	
	/** The economicstatus list. */
	private List<EconomicStatus> economicstatusList = null;
	
	/** The economicstatusfiltered list. */
	private List<EconomicStatus> economicstatusfilteredList = null;
	
	/** The economicstatus. */
	private EconomicStatus economicstatus = null;
	
	/** The data model. */
	private LazyDataModel<EconomicStatus> dataModel;

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
	 * Initialize method to get all EconomicStatus and prepare a for a create of
	 * a new EconomicStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see EconomicStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		economicstatusInfo();
	}

	/**
	 * Get all EconomicStatus for data table.
	 *
	 * @author TechFinium
	 * @see EconomicStatus
	 */
	public void economicstatusInfo() {

		dataModel = new LazyDataModel<EconomicStatus>() {

			private static final long serialVersionUID = 1L;
			private List<EconomicStatus> retorno = new ArrayList<EconomicStatus>();

			@Override
			public List<EconomicStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allEconomicStatus(EconomicStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(EconomicStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EconomicStatus obj) {
				return obj.getId();
			}

			@Override
			public EconomicStatus getRowData(String rowKey) {
				for (EconomicStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert EconomicStatus into database.
	 *
	 * @author TechFinium
	 * @see EconomicStatus
	 */
	public void economicstatusInsert() {
		try {
			service.create(this.economicstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			economicstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update EconomicStatus in database.
	 *
	 * @author TechFinium
	 * @see EconomicStatus
	 */
	public void economicstatusUpdate() {
		try {
			service.update(this.economicstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			economicstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete EconomicStatus from database.
	 *
	 * @author TechFinium
	 * @see EconomicStatus
	 */
	public void economicstatusDelete() {
		try {
			service.delete(this.economicstatus);
			prepareNew();
			economicstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of EconomicStatus.
	 *
	 * @author TechFinium
	 * @see EconomicStatus
	 */
	public void prepareNew() {
		economicstatus = new EconomicStatus();
	}

	/*
	 * public List<SelectItem> getEconomicStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * economicstatusInfo(); for (EconomicStatus ug : economicstatusList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<EconomicStatus> complete(String desc) {
		List<EconomicStatus> l = null;
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
	 * Gets the economic status list.
	 *
	 * @return the economic status list
	 */
	public List<EconomicStatus> getEconomicStatusList() {
		return economicstatusList;
	}

	/**
	 * Sets the economic status list.
	 *
	 * @param economicstatusList the new economic status list
	 */
	public void setEconomicStatusList(List<EconomicStatus> economicstatusList) {
		this.economicstatusList = economicstatusList;
	}

	/**
	 * Gets the economicstatus.
	 *
	 * @return the economicstatus
	 */
	public EconomicStatus getEconomicstatus() {
		return economicstatus;
	}

	/**
	 * Sets the economicstatus.
	 *
	 * @param economicstatus the new economicstatus
	 */
	public void setEconomicstatus(EconomicStatus economicstatus) {
		this.economicstatus = economicstatus;
	}

	/**
	 * Gets the economic statusfiltered list.
	 *
	 * @return the economic statusfiltered list
	 */
	public List<EconomicStatus> getEconomicStatusfilteredList() {
		return economicstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param economicstatusfilteredList            the new economicstatusfilteredList list
	 * @see EconomicStatus
	 */
	public void setEconomicStatusfilteredList(List<EconomicStatus> economicstatusfilteredList) {
		this.economicstatusfilteredList = economicstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EconomicStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<EconomicStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
