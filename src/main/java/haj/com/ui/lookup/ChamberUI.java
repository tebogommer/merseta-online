package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Chamber;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ChamberService;

// TODO: Auto-generated Javadoc
/**
 * The Class ChamberUI.
 */
@ManagedBean(name = "chamberUI")
@ViewScoped
public class ChamberUI extends AbstractUI {

	/** The service. */
	private ChamberService service = new ChamberService();
	
	/** The chamber list. */
	private List<Chamber> chamberList = null;
	
	/** The chamberfiltered list. */
	private List<Chamber> chamberfilteredList = null;
	
	/** The chamber. */
	private Chamber chamber = null;
	
	/** The data model. */
	private LazyDataModel<Chamber> dataModel;

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
	 * Initialize method to get all Chamber and prepare a for a create of a new
	 * Chamber.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Chamber
	 */
	private void runInit() throws Exception {
		prepareNew();
		chamberInfo();
	}

	/**
	 * Get all Chamber for data table.
	 *
	 * @author TechFinium
	 * @see Chamber
	 */
	public void chamberInfo() {

		dataModel = new LazyDataModel<Chamber>() {

			private static final long serialVersionUID = 1L;
			private List<Chamber> retorno = new ArrayList<Chamber>();

			@Override
			public List<Chamber> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allChamber(Chamber.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Chamber.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Chamber obj) {
				return obj.getId();
			}

			@Override
			public Chamber getRowData(String rowKey) {
				for (Chamber obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Chamber into database.
	 *
	 * @author TechFinium
	 * @see Chamber
	 */
	public void chamberInsert() {
		try {
			service.create(this.chamber);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			chamberInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Chamber in database.
	 *
	 * @author TechFinium
	 * @see Chamber
	 */
	public void chamberUpdate() {
		try {
			service.update(this.chamber);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			chamberInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Chamber from database.
	 *
	 * @author TechFinium
	 * @see Chamber
	 */
	public void chamberDelete() {
		try {
			service.delete(this.chamber);
			prepareNew();
			chamberInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Chamber.
	 *
	 * @author TechFinium
	 * @see Chamber
	 */
	public void prepareNew() {
		chamber = new Chamber();
	}

	/*
	 * public List<SelectItem> getChamberDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * chamberInfo(); for (Chamber ug : chamberList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Chamber> complete(String desc) {
		List<Chamber> l = null;
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
	 * Gets the chamber list.
	 *
	 * @return the chamber list
	 */
	public List<Chamber> getChamberList() {
		return chamberList;
	}

	/**
	 * Sets the chamber list.
	 *
	 * @param chamberList the new chamber list
	 */
	public void setChamberList(List<Chamber> chamberList) {
		this.chamberList = chamberList;
	}

	/**
	 * Gets the chamber.
	 *
	 * @return the chamber
	 */
	public Chamber getChamber() {
		return chamber;
	}

	/**
	 * Sets the chamber.
	 *
	 * @param chamber the new chamber
	 */
	public void setChamber(Chamber chamber) {
		this.chamber = chamber;
	}

	/**
	 * Gets the chamberfiltered list.
	 *
	 * @return the chamberfiltered list
	 */
	public List<Chamber> getChamberfilteredList() {
		return chamberfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param chamberfilteredList            the new chamberfilteredList list
	 * @see Chamber
	 */
	public void setChamberfilteredList(List<Chamber> chamberfilteredList) {
		this.chamberfilteredList = chamberfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Chamber> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Chamber> dataModel) {
		this.dataModel = dataModel;
	}

}
