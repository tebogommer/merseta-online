package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.OfoCodes;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.OfoCodesService;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoCodesUI.
 */
@ManagedBean(name = "ofocodesUI")
@ViewScoped
public class OfoCodesUI extends AbstractUI {

	/** The service. */
	private OfoCodesService service = new OfoCodesService();

	/** The ofocodes list. */
	private List<OfoCodes> ofocodesList = null;

	/** The ofocodesfiltered list. */
	private List<OfoCodes> ofocodesfilteredList = null;

	/** The ofocodes. */
	private OfoCodes ofocodes = null;

	/** The data model. */
	private LazyDataModel<OfoCodes> dataModel;
	
	private boolean specialization;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
			ofocodesList = service.allOfoCodes();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all OfoCodes and prepare a for a create of a new
	 * OfoCodes.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see OfoCodes
	 */
	private void runInit() throws Exception {
		prepareNew();
		ofocodesInfo();
	}

	/**
	 * Get all OfoCodes for data table.
	 *
	 * @author TechFinium
	 * @see OfoCodes
	 */
	public void ofocodesInfo() {

		dataModel = new LazyDataModel<OfoCodes>() {

			private static final long serialVersionUID = 1L;
			private List<OfoCodes> retorno = new ArrayList<OfoCodes>();

			@Override
			public List<OfoCodes> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allOfoCodes(OfoCodes.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(OfoCodes.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(OfoCodes obj) {
				return obj.getId();
			}

			@Override
			public OfoCodes getRowData(String rowKey) {
				for (OfoCodes obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert OfoCodes into database.
	 *
	 * @author TechFinium
	 * @see OfoCodes
	 */
	public void ofocodesInsert() {
		try {
			service.create(this.ofocodes);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			ofocodesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete OfoCodes from database.
	 *
	 * @author TechFinium
	 * @see OfoCodes
	 */
	public void ofocodesDelete() {
		try {
			service.delete(this.ofocodes);
			prepareNew();
			ofocodesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the ofo codes list.
	 *
	 * @return the ofo codes list
	 */
	public List<OfoCodes> getOfoCodesList() {
		return ofocodesList;
	}

	/**
	 * Sets the ofo codes list.
	 *
	 * @param ofocodesList
	 *            the new ofo codes list
	 */
	public void setOfoCodesList(List<OfoCodes> ofocodesList) {
		this.ofocodesList = ofocodesList;
	}

	/**
	 * Gets the ofocodes.
	 *
	 * @return the ofocodes
	 */
	public OfoCodes getOfocodes() {
		return ofocodes;
	}

	/**
	 * Sets the ofocodes.
	 *
	 * @param ofocodes
	 *            the new ofocodes
	 */
	public void setOfocodes(OfoCodes ofocodes) {
		this.ofocodes = ofocodes;
	}

	/**
	 * Create new instance of OfoCodes .
	 *
	 * @author TechFinium
	 * @see OfoCodes
	 */
	public void prepareNew() {
		ofocodes = new OfoCodes();
		ofocodes.setActive(true);
	}

	/**
	 * Update OfoCodes in database.
	 *
	 * @author TechFinium
	 * @see OfoCodes
	 */
	public void ofocodesUpdate() {
		try {
			service.update(this.ofocodes);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			ofocodesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare select one menu data.
	 *
	 * @author TechFinium
	 * @return the ofo codes DD
	 * @see OfoCodes
	 */
	public List<SelectItem> getOfoCodesDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		ofocodesInfo();
		for (OfoCodes ug : ofocodesList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the ofo codesfiltered list.
	 *
	 * @return the ofo codesfiltered list
	 */
	public List<OfoCodes> getOfoCodesfilteredList() {
		return ofocodesfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param ofocodesfilteredList
	 *            the new ofo codesfiltered list
	 * @see OfoCodes
	 */
	public void setOfoCodesfilteredList(List<OfoCodes> ofocodesfilteredList) {
		this.ofocodesfilteredList = ofocodesfilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<OfoCodes> complete(String desc) {
		List<OfoCodes> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<OfoCodes> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<OfoCodes> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isSpecialization() {
		return specialization;
	}

	public void setSpecialization(boolean specialization) {
		this.specialization = specialization;
	}

}
