package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProviderStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProviderStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderStatusUI.
 */
@ManagedBean(name = "providerstatusUI")
@ViewScoped
public class ProviderStatusUI extends AbstractUI {

	/** The service. */
	private ProviderStatusService service = new ProviderStatusService();
	
	/** The providerstatus list. */
	private List<ProviderStatus> providerstatusList = null;
	
	/** The providerstatusfiltered list. */
	private List<ProviderStatus> providerstatusfilteredList = null;
	
	/** The providerstatus. */
	private ProviderStatus providerstatus = null;
	
	/** The data model. */
	private LazyDataModel<ProviderStatus> dataModel;

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
	 * Initialize method to get all ProviderStatus and prepare a for a create of
	 * a new ProviderStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see ProviderStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		providerstatusInfo();
	}

	/**
	 * Get all ProviderStatus for data table.
	 *
	 * @author TechFinium
	 * @see ProviderStatus
	 */
	public void providerstatusInfo() {

		dataModel = new LazyDataModel<ProviderStatus>() {

			private static final long serialVersionUID = 1L;
			private List<ProviderStatus> retorno = new ArrayList<ProviderStatus>();

			@Override
			public List<ProviderStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allProviderStatus(ProviderStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(ProviderStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ProviderStatus obj) {
				return obj.getId();
			}

			@Override
			public ProviderStatus getRowData(String rowKey) {
				for (ProviderStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ProviderStatus into database.
	 *
	 * @author TechFinium
	 * @see ProviderStatus
	 */
	public void providerstatusInsert() {
		try {
			service.create(this.providerstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providerstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ProviderStatus in database.
	 *
	 * @author TechFinium
	 * @see ProviderStatus
	 */
	public void providerstatusUpdate() {
		try {
			service.update(this.providerstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providerstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProviderStatus from database.
	 *
	 * @author TechFinium
	 * @see ProviderStatus
	 */
	public void providerstatusDelete() {
		try {
			service.delete(this.providerstatus);
			prepareNew();
			providerstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProviderStatus.
	 *
	 * @author TechFinium
	 * @see ProviderStatus
	 */
	public void prepareNew() {
		providerstatus = new ProviderStatus();
	}

	/*
	 * public List<SelectItem> getProviderStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * providerstatusInfo(); for (ProviderStatus ug : providerstatusList) { //
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
	public List<ProviderStatus> complete(String desc) {
		List<ProviderStatus> l = null;
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
	 * Gets the provider status list.
	 *
	 * @return the provider status list
	 */
	public List<ProviderStatus> getProviderStatusList() {
		return providerstatusList;
	}

	/**
	 * Sets the provider status list.
	 *
	 * @param providerstatusList the new provider status list
	 */
	public void setProviderStatusList(List<ProviderStatus> providerstatusList) {
		this.providerstatusList = providerstatusList;
	}

	/**
	 * Gets the providerstatus.
	 *
	 * @return the providerstatus
	 */
	public ProviderStatus getProviderstatus() {
		return providerstatus;
	}

	/**
	 * Sets the providerstatus.
	 *
	 * @param providerstatus the new providerstatus
	 */
	public void setProviderstatus(ProviderStatus providerstatus) {
		this.providerstatus = providerstatus;
	}

	/**
	 * Gets the provider statusfiltered list.
	 *
	 * @return the provider statusfiltered list
	 */
	public List<ProviderStatus> getProviderStatusfilteredList() {
		return providerstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param providerstatusfilteredList            the new providerstatusfilteredList list
	 * @see ProviderStatus
	 */
	public void setProviderStatusfilteredList(List<ProviderStatus> providerstatusfilteredList) {
		this.providerstatusfilteredList = providerstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProviderStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ProviderStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
