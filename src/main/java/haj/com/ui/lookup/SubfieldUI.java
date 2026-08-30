package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Subfield;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SubfieldService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubfieldUI.
 */
@ManagedBean(name = "subfieldUI")
@ViewScoped
public class SubfieldUI extends AbstractUI {

	/** The service. */
	private SubfieldService service = new SubfieldService();
	
	/** The subfield list. */
	private List<Subfield> subfieldList = null;
	
	/** The subfieldfiltered list. */
	private List<Subfield> subfieldfilteredList = null;
	
	/** The subfield. */
	private Subfield subfield = null;
	
	/** The data model. */
	private LazyDataModel<Subfield> dataModel;

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
	 * Initialize method to get all Subfield and prepare a for a create of a new
	 * Subfield.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Subfield
	 */
	private void runInit() throws Exception {
		prepareNew();
		subfieldInfo();
	}

	/**
	 * Get all Subfield for data table.
	 *
	 * @author TechFinium
	 * @see Subfield
	 */
	public void subfieldInfo() {

		dataModel = new LazyDataModel<Subfield>() {

			private static final long serialVersionUID = 1L;
			private List<Subfield> retorno = new ArrayList<Subfield>();

			@Override
			public List<Subfield> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSubfield(Subfield.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Subfield.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Subfield obj) {
				return obj.getId();
			}

			@Override
			public Subfield getRowData(String rowKey) {
				for (Subfield obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Subfield into database.
	 *
	 * @author TechFinium
	 * @see Subfield
	 */
	public void subfieldInsert() {
		try {
			service.create(this.subfield);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			subfieldInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Subfield in database.
	 *
	 * @author TechFinium
	 * @see Subfield
	 */
	public void subfieldUpdate() {
		try {
			service.update(this.subfield);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			subfieldInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Subfield from database.
	 *
	 * @author TechFinium
	 * @see Subfield
	 */
	public void subfieldDelete() {
		try {
			service.delete(this.subfield);
			prepareNew();
			subfieldInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Subfield.
	 *
	 * @author TechFinium
	 * @see Subfield
	 */
	public void prepareNew() {
		subfield = new Subfield();
	}

	/*
	 * public List<SelectItem> getSubfieldDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * subfieldInfo(); for (Subfield ug : subfieldList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Subfield> complete(String desc) {
		List<Subfield> l = null;
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
	 * Gets the subfield list.
	 *
	 * @return the subfield list
	 */
	public List<Subfield> getSubfieldList() {
		return subfieldList;
	}

	/**
	 * Sets the subfield list.
	 *
	 * @param subfieldList the new subfield list
	 */
	public void setSubfieldList(List<Subfield> subfieldList) {
		this.subfieldList = subfieldList;
	}

	/**
	 * Gets the subfield.
	 *
	 * @return the subfield
	 */
	public Subfield getSubfield() {
		return subfield;
	}

	/**
	 * Sets the subfield.
	 *
	 * @param subfield the new subfield
	 */
	public void setSubfield(Subfield subfield) {
		this.subfield = subfield;
	}

	/**
	 * Gets the subfieldfiltered list.
	 *
	 * @return the subfieldfiltered list
	 */
	public List<Subfield> getSubfieldfilteredList() {
		return subfieldfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param subfieldfilteredList            the new subfieldfilteredList list
	 * @see Subfield
	 */
	public void setSubfieldfilteredList(List<Subfield> subfieldfilteredList) {
		this.subfieldfilteredList = subfieldfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Subfield> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Subfield> dataModel) {
		this.dataModel = dataModel;
	}

}
