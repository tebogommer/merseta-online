package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Nationality;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NationalityService;

// TODO: Auto-generated Javadoc
/**
 * The Class NationalityUI.
 */
@ManagedBean(name = "nationalityUI")
@ViewScoped
public class NationalityUI extends AbstractUI {

	/** The service. */
	private NationalityService service = new NationalityService();
	
	/** The nationality list. */
	private List<Nationality> nationalityList = null;
	
	/** The nationalityfiltered list. */
	private List<Nationality> nationalityfilteredList = null;
	
	/** The nationality. */
	private Nationality nationality = null;
	
	/** The data model. */
	private LazyDataModel<Nationality> dataModel;

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
	 * Initialize method to get all Nationality and prepare a for a create of a
	 * new Nationality.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Nationality
	 */
	private void runInit() throws Exception {
		prepareNew();
		nationalityInfo();
	}

	/**
	 * Get all Nationality for data table.
	 *
	 * @author TechFinium
	 * @see Nationality
	 */
	public void nationalityInfo() {

		dataModel = new LazyDataModel<Nationality>() {

			private static final long serialVersionUID = 1L;
			private List<Nationality> retorno = new ArrayList<Nationality>();

			@Override
			public List<Nationality> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allNationality(Nationality.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Nationality.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Nationality obj) {
				return obj.getId();
			}

			@Override
			public Nationality getRowData(String rowKey) {
				for (Nationality obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Nationality into database.
	 *
	 * @author TechFinium
	 * @see Nationality
	 */
	public void nationalityInsert() {
		try {
			service.create(this.nationality);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nationalityInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Nationality in database.
	 *
	 * @author TechFinium
	 * @see Nationality
	 */
	public void nationalityUpdate() {
		try {
			service.update(this.nationality);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nationalityInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Nationality from database.
	 *
	 * @author TechFinium
	 * @see Nationality
	 */
	public void nationalityDelete() {
		try {
			service.delete(this.nationality);
			prepareNew();
			nationalityInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Nationality.
	 *
	 * @author TechFinium
	 * @see Nationality
	 */
	public void prepareNew() {
		nationality = new Nationality();
	}

	/*
	 * public List<SelectItem> getNationalityDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * nationalityInfo(); for (Nationality ug : nationalityList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Nationality> complete(String desc) {
		List<Nationality> l = null;
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
	 * Gets the nationality list.
	 *
	 * @return the nationality list
	 */
	public List<Nationality> getNationalityList() {
		return nationalityList;
	}

	/**
	 * Sets the nationality list.
	 *
	 * @param nationalityList the new nationality list
	 */
	public void setNationalityList(List<Nationality> nationalityList) {
		this.nationalityList = nationalityList;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public Nationality getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality the new nationality
	 */
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the nationalityfiltered list.
	 *
	 * @return the nationalityfiltered list
	 */
	public List<Nationality> getNationalityfilteredList() {
		return nationalityfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param nationalityfilteredList            the new nationalityfilteredList list
	 * @see Nationality
	 */
	public void setNationalityfilteredList(List<Nationality> nationalityfilteredList) {
		this.nationalityfilteredList = nationalityfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Nationality> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Nationality> dataModel) {
		this.dataModel = dataModel;
	}

}
