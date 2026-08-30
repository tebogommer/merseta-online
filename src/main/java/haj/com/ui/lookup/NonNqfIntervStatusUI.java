package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NonNqfIntervStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NonNqfIntervStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class NonNqfIntervStatusUI.
 */
@ManagedBean(name = "nonnqfintervstatusUI")
@ViewScoped
public class NonNqfIntervStatusUI extends AbstractUI {

	/** The service. */
	private NonNqfIntervStatusService service = new NonNqfIntervStatusService();
	
	/** The nonnqfintervstatus list. */
	private List<NonNqfIntervStatus> nonnqfintervstatusList = null;
	
	/** The nonnqfintervstatusfiltered list. */
	private List<NonNqfIntervStatus> nonnqfintervstatusfilteredList = null;
	
	/** The nonnqfintervstatus. */
	private NonNqfIntervStatus nonnqfintervstatus = null;
	
	/** The data model. */
	private LazyDataModel<NonNqfIntervStatus> dataModel;

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
	 * Initialize method to get all NonNqfIntervStatus and prepare a for a
	 * create of a new NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see NonNqfIntervStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		nonnqfintervstatusInfo();
	}

	/**
	 * Get all NonNqfIntervStatus for data table.
	 *
	 * @author TechFinium
	 * @see NonNqfIntervStatus
	 */
	public void nonnqfintervstatusInfo() {

		dataModel = new LazyDataModel<NonNqfIntervStatus>() {

			private static final long serialVersionUID = 1L;
			private List<NonNqfIntervStatus> retorno = new ArrayList<NonNqfIntervStatus>();

			@Override
			public List<NonNqfIntervStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allNonNqfIntervStatus(NonNqfIntervStatus.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(NonNqfIntervStatus.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(NonNqfIntervStatus obj) {
				return obj.getId();
			}

			@Override
			public NonNqfIntervStatus getRowData(String rowKey) {
				for (NonNqfIntervStatus obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert NonNqfIntervStatus into database.
	 *
	 * @author TechFinium
	 * @see NonNqfIntervStatus
	 */
	public void nonnqfintervstatusInsert() {
		try {
			service.create(this.nonnqfintervstatus);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nonnqfintervstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update NonNqfIntervStatus in database.
	 *
	 * @author TechFinium
	 * @see NonNqfIntervStatus
	 */
	public void nonnqfintervstatusUpdate() {
		try {
			service.update(this.nonnqfintervstatus);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nonnqfintervstatusInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete NonNqfIntervStatus from database.
	 *
	 * @author TechFinium
	 * @see NonNqfIntervStatus
	 */
	public void nonnqfintervstatusDelete() {
		try {
			service.delete(this.nonnqfintervstatus);
			prepareNew();
			nonnqfintervstatusInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of NonNqfIntervStatus.
	 *
	 * @author TechFinium
	 * @see NonNqfIntervStatus
	 */
	public void prepareNew() {
		nonnqfintervstatus = new NonNqfIntervStatus();
	}

	/*
	 * public List<SelectItem> getNonNqfIntervStatusDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * nonnqfintervstatusInfo(); for (NonNqfIntervStatus ug :
	 * nonnqfintervstatusList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<NonNqfIntervStatus> complete(String desc) {
		List<NonNqfIntervStatus> l = null;
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
	 * Gets the non nqf interv status list.
	 *
	 * @return the non nqf interv status list
	 */
	public List<NonNqfIntervStatus> getNonNqfIntervStatusList() {
		return nonnqfintervstatusList;
	}

	/**
	 * Sets the non nqf interv status list.
	 *
	 * @param nonnqfintervstatusList the new non nqf interv status list
	 */
	public void setNonNqfIntervStatusList(List<NonNqfIntervStatus> nonnqfintervstatusList) {
		this.nonnqfintervstatusList = nonnqfintervstatusList;
	}

	/**
	 * Gets the nonnqfintervstatus.
	 *
	 * @return the nonnqfintervstatus
	 */
	public NonNqfIntervStatus getNonnqfintervstatus() {
		return nonnqfintervstatus;
	}

	/**
	 * Sets the nonnqfintervstatus.
	 *
	 * @param nonnqfintervstatus the new nonnqfintervstatus
	 */
	public void setNonnqfintervstatus(NonNqfIntervStatus nonnqfintervstatus) {
		this.nonnqfintervstatus = nonnqfintervstatus;
	}

	/**
	 * Gets the non nqf interv statusfiltered list.
	 *
	 * @return the non nqf interv statusfiltered list
	 */
	public List<NonNqfIntervStatus> getNonNqfIntervStatusfilteredList() {
		return nonnqfintervstatusfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param nonnqfintervstatusfilteredList            the new nonnqfintervstatusfilteredList list
	 * @see NonNqfIntervStatus
	 */
	public void setNonNqfIntervStatusfilteredList(List<NonNqfIntervStatus> nonnqfintervstatusfilteredList) {
		this.nonnqfintervstatusfilteredList = nonnqfintervstatusfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<NonNqfIntervStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<NonNqfIntervStatus> dataModel) {
		this.dataModel = dataModel;
	}

}
