package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PartOf;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PartOfService;

// TODO: Auto-generated Javadoc
/**
 * The Class PartOfUI.
 */
@ManagedBean(name = "partofUI")
@ViewScoped
public class PartOfUI extends AbstractUI {

	/** The service. */
	private PartOfService service = new PartOfService();
	
	/** The partof list. */
	private List<PartOf> partofList = null;
	
	/** The partoffiltered list. */
	private List<PartOf> partoffilteredList = null;
	
	/** The partof. */
	private PartOf partof = null;
	
	/** The data model. */
	private LazyDataModel<PartOf> dataModel;

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
	 * Initialize method to get all PartOf and prepare a for a create of a new
	 * PartOf.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see PartOf
	 */
	private void runInit() throws Exception {
		prepareNew();
		partofInfo();
	}

	/**
	 * Get all PartOf for data table.
	 *
	 * @author TechFinium
	 * @see PartOf
	 */
	public void partofInfo() {

		dataModel = new LazyDataModel<PartOf>() {

			private static final long serialVersionUID = 1L;
			private List<PartOf> retorno = new ArrayList<PartOf>();

			@Override
			public List<PartOf> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allPartOf(PartOf.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(PartOf.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(PartOf obj) {
				return obj.getId();
			}

			@Override
			public PartOf getRowData(String rowKey) {
				for (PartOf obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert PartOf into database.
	 *
	 * @author TechFinium
	 * @see PartOf
	 */
	public void partofInsert() {
		try {
			service.create(this.partof);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			partofInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update PartOf in database.
	 *
	 * @author TechFinium
	 * @see PartOf
	 */
	public void partofUpdate() {
		try {
			service.update(this.partof);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			partofInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete PartOf from database.
	 *
	 * @author TechFinium
	 * @see PartOf
	 */
	public void partofDelete() {
		try {
			service.delete(this.partof);
			prepareNew();
			partofInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of PartOf.
	 *
	 * @author TechFinium
	 * @see PartOf
	 */
	public void prepareNew() {
		partof = new PartOf();
	}

	/*
	 * public List<SelectItem> getPartOfDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * partofInfo(); for (PartOf ug : partofList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<PartOf> complete(String desc) {
		List<PartOf> l = null;
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
	 * Gets the part of list.
	 *
	 * @return the part of list
	 */
	public List<PartOf> getPartOfList() {
		return partofList;
	}

	/**
	 * Sets the part of list.
	 *
	 * @param partofList the new part of list
	 */
	public void setPartOfList(List<PartOf> partofList) {
		this.partofList = partofList;
	}

	/**
	 * Gets the partof.
	 *
	 * @return the partof
	 */
	public PartOf getPartof() {
		return partof;
	}

	/**
	 * Sets the partof.
	 *
	 * @param partof the new partof
	 */
	public void setPartof(PartOf partof) {
		this.partof = partof;
	}

	/**
	 * Gets the part offiltered list.
	 *
	 * @return the part offiltered list
	 */
	public List<PartOf> getPartOffilteredList() {
		return partoffilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param partoffilteredList            the new partoffilteredList list
	 * @see PartOf
	 */
	public void setPartOffilteredList(List<PartOf> partoffilteredList) {
		this.partoffilteredList = partoffilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<PartOf> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<PartOf> dataModel) {
		this.dataModel = dataModel;
	}

}
