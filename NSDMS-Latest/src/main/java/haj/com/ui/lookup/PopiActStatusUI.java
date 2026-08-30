package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PopiActStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PopiActStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class PopiActStatusUI.
 */
@ManagedBean(name = "popiactstatusUI")
@ViewScoped
public class PopiActStatusUI extends AbstractUI {

	/** The service. */
	private PopiActStatusService service = new PopiActStatusService();
	
	/** The popiactstatus list. */
	private List<PopiActStatus> popiactstatusList = null;
	
	/** The popiactstatusfiltered list. */
	private List<PopiActStatus> popiactstatusfilteredList = null;
	
	/** The popiactstatus. */
	private PopiActStatus popiactstatus = null;
	
	/** The data model. */
	private LazyDataModel<PopiActStatus> dataModel;

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
	 * Initialize method to get all PopiActStatus and prepare a for a create of
	 * a new PopiActStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see PopiActStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		popiactstatusInfo();
	}

	/**
	 * Get all PopiActStatus for data table.
	 *
	 * @author TechFinium
	 * @see PopiActStatus
	 */
	public void popiactstatusInfo() {

		dataModel = new LazyDataModel<PopiActStatus>() {

			private static final long serialVersionUID = 1L;
			private List<PopiActStatus> retorno = new ArrayList<PopiActStatus>();

			@Override
			public List<PopiActStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allPopiActStatus(PopiActStatus.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(PopiActStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(PopiActStatus obj) {
				return obj.getId();
			}

			@Override
			public PopiActStatus getRowData(String rowKey) {
				for (PopiActStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert PopiActStatus into database.
	 *
	 * @author TechFinium
	 * @see PopiActStatus
	 */
	public void popiactstatusInsert() {
		try {
			service.create(this.popiactstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			popiactstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update PopiActStatus in database.
	 *
	 * @author TechFinium
	 * @see PopiActStatus
	 */
	public void popiactstatusUpdate() {
		try {
			service.update(this.popiactstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			popiactstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete PopiActStatus from database.
	 *
	 * @author TechFinium
	 * @see PopiActStatus
	 */
	public void popiactstatusDelete() {
		try {
			service.delete(this.popiactstatus);
			prepareNew();
			popiactstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of PopiActStatus.
	 *
	 * @author TechFinium
	 * @see PopiActStatus
	 */
	public void prepareNew() {
		popiactstatus = new PopiActStatus();
	}

	/*
	 * public List<SelectItem> getPopiActStatusDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * popiactstatusInfo(); for (PopiActStatus ug : popiactstatusList) { //
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
	public List<PopiActStatus> complete(String desc) {
		List<PopiActStatus> l = null;
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
	 * Gets the popi act status list.
	 *
	 * @return the popi act status list
	 */
	public List<PopiActStatus> getPopiActStatusList() {
		return popiactstatusList;
	}

	/**
	 * Sets the popi act status list.
	 *
	 * @param popiactstatusList the new popi act status list
	 */
	public void setPopiActStatusList(List<PopiActStatus> popiactstatusList) {
		this.popiactstatusList = popiactstatusList;
	}

	/**
	 * Gets the popiactstatus.
	 *
	 * @return the popiactstatus
	 */
	public PopiActStatus getPopiactstatus() {
		return popiactstatus;
	}

	/**
	 * Sets the popiactstatus.
	 *
	 * @param popiactstatus the new popiactstatus
	 */
	public void setPopiactstatus(PopiActStatus popiactstatus) {
		this.popiactstatus = popiactstatus;
	}

	/**
	 * Gets the popi act statusfiltered list.
	 *
	 * @return the popi act statusfiltered list
	 */
	public List<PopiActStatus> getPopiActStatusfilteredList() {
		return popiactstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param popiactstatusfilteredList            the new popiactstatusfilteredList list
	 * @see PopiActStatus
	 */
	public void setPopiActStatusfilteredList(List<PopiActStatus> popiactstatusfilteredList) {
		this.popiactstatusfilteredList = popiactstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<PopiActStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<PopiActStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
