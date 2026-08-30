package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.UrbalRural;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.UrbalRuralService;

// TODO: Auto-generated Javadoc
/**
 * The Class UrbalRuralUI.
 */
@ManagedBean(name = "urbalruralUI")
@ViewScoped
public class UrbalRuralUI extends AbstractUI {

	/** The service. */
	private UrbalRuralService service = new UrbalRuralService();
	
	/** The urbalrural list. */
	private List<UrbalRural> urbalruralList = null;
	
	/** The urbalruralfiltered list. */
	private List<UrbalRural> urbalruralfilteredList = null;
	
	/** The urbalrural. */
	private UrbalRural urbalrural = null;
	
	/** The data model. */
	private LazyDataModel<UrbalRural> dataModel;

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
	 * Initialize method to get all UrbalRural and prepare a for a create of a
	 * new UrbalRural.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see UrbalRural
	 */
	private void runInit() throws Exception {
		prepareNew();
		urbalruralInfo();
	}

	/**
	 * Get all UrbalRural for data table.
	 *
	 * @author TechFinium
	 * @see UrbalRural
	 */
	public void urbalruralInfo() {

		dataModel = new LazyDataModel<UrbalRural>() {

			private static final long serialVersionUID = 1L;
			private List<UrbalRural> retorno = new ArrayList<UrbalRural>();

			@Override
			public List<UrbalRural> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allUrbalRural(UrbalRural.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(UrbalRural.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(UrbalRural obj) {
				return obj.getId();
			}

			@Override
			public UrbalRural getRowData(String rowKey) {
				for (UrbalRural obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert UrbalRural into database.
	 *
	 * @author TechFinium
	 * @see UrbalRural
	 */
	public void urbalruralInsert() {
		try {
			service.create(this.urbalrural);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			urbalruralInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update UrbalRural in database.
	 *
	 * @author TechFinium
	 * @see UrbalRural
	 */
	public void urbalruralUpdate() {
		try {
			service.update(this.urbalrural);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			urbalruralInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete UrbalRural from database.
	 *
	 * @author TechFinium
	 * @see UrbalRural
	 */
	public void urbalruralDelete() {
		try {
			service.delete(this.urbalrural);
			prepareNew();
			urbalruralInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of UrbalRural.
	 *
	 * @author TechFinium
	 * @see UrbalRural
	 */
	public void prepareNew() {
		urbalrural = new UrbalRural();
	}

	/*
	 * public List<SelectItem> getUrbalRuralDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * urbalruralInfo(); for (UrbalRural ug : urbalruralList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<UrbalRural> complete(String desc) {
		List<UrbalRural> l = null;
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
	 * Gets the urbal rural list.
	 *
	 * @return the urbal rural list
	 */
	public List<UrbalRural> getUrbalRuralList() {
		return urbalruralList;
	}

	/**
	 * Sets the urbal rural list.
	 *
	 * @param urbalruralList the new urbal rural list
	 */
	public void setUrbalRuralList(List<UrbalRural> urbalruralList) {
		this.urbalruralList = urbalruralList;
	}

	/**
	 * Gets the urbalrural.
	 *
	 * @return the urbalrural
	 */
	public UrbalRural getUrbalrural() {
		return urbalrural;
	}

	/**
	 * Sets the urbalrural.
	 *
	 * @param urbalrural the new urbalrural
	 */
	public void setUrbalrural(UrbalRural urbalrural) {
		this.urbalrural = urbalrural;
	}

	/**
	 * Gets the urbal ruralfiltered list.
	 *
	 * @return the urbal ruralfiltered list
	 */
	public List<UrbalRural> getUrbalRuralfilteredList() {
		return urbalruralfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param urbalruralfilteredList            the new urbalruralfilteredList list
	 * @see UrbalRural
	 */
	public void setUrbalRuralfilteredList(List<UrbalRural> urbalruralfilteredList) {
		this.urbalruralfilteredList = urbalruralfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UrbalRural> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UrbalRural> dataModel) {
		this.dataModel = dataModel;
	}

}
