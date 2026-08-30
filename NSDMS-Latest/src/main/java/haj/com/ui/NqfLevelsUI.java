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

import haj.com.entity.lookup.NqfLevels;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NqfLevelsService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfLevelsUI.
 */
@ManagedBean(name = "nqflevelsUI")
@ViewScoped
public class NqfLevelsUI extends AbstractUI {

	/** The service. */
	private NqfLevelsService service = new NqfLevelsService();

	/** The nqflevels list. */
	private List<NqfLevels> nqflevelsList = null;

	/** The nqflevelsfiltered list. */
	private List<NqfLevels> nqflevelsfilteredList = null;

	/** The nqflevels. */
	private NqfLevels nqflevels = null;

	/** The data model. */
	private LazyDataModel<NqfLevels> dataModel;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all NqfLevels and prepare a for a create of a
	 * new NqfLevels.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	private void runInit() throws Exception {
		prepareNew();
		nqflevelsInfo();
	}

	/**
	 * Get all NqfLevels for data table.
	 *
	 * @author TechFinium
	 * @see NqfLevels
	 */
	public void nqflevelsInfo() {

		dataModel = new LazyDataModel<NqfLevels>() {

			private static final long serialVersionUID = 1L;
			private List<NqfLevels> retorno = new ArrayList<NqfLevels>();

			@Override
			public List<NqfLevels> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allNqfLevels(NqfLevels.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(NqfLevels.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(NqfLevels obj) {
				return obj.getId();
			}

			@Override
			public NqfLevels getRowData(String rowKey) {
				for (NqfLevels obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert NqfLevels into database.
	 *
	 * @author TechFinium
	 * @see NqfLevels
	 */
	public void nqflevelsInsert() {
		try {
			service.create(this.nqflevels);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nqflevelsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete NqfLevels from database.
	 *
	 * @author TechFinium
	 * @see NqfLevels
	 */
	public void nqflevelsDelete() {
		try {
			service.delete(this.nqflevels);
			prepareNew();
			nqflevelsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the nqf levels list.
	 *
	 * @return the nqf levels list
	 */
	public List<NqfLevels> getNqfLevelsList() {
		return nqflevelsList;
	}

	/**
	 * Sets the nqf levels list.
	 *
	 * @param nqflevelsList
	 *            the new nqf levels list
	 */
	public void setNqfLevelsList(List<NqfLevels> nqflevelsList) {
		this.nqflevelsList = nqflevelsList;
	}

	/**
	 * Gets the nqflevels.
	 *
	 * @return the nqflevels
	 */
	public NqfLevels getNqflevels() {
		return nqflevels;
	}

	/**
	 * Sets the nqflevels.
	 *
	 * @param nqflevels
	 *            the new nqflevels
	 */
	public void setNqflevels(NqfLevels nqflevels) {
		this.nqflevels = nqflevels;
	}

	/**
	 * Create new instance of NqfLevels .
	 *
	 * @author TechFinium
	 * @see NqfLevels
	 */
	public void prepareNew() {
		nqflevels = new NqfLevels();
	}

	/**
	 * Update NqfLevels in database.
	 *
	 * @author TechFinium
	 * @see NqfLevels
	 */
	public void nqflevelsUpdate() {
		try {
			service.update(this.nqflevels);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nqflevelsInfo();
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
	 * @return the nqf levels DD
	 * @see NqfLevels
	 */
	public List<SelectItem> getNqfLevelsDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		nqflevelsInfo();
		for (NqfLevels ug : nqflevelsList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the nqf levelsfiltered list.
	 *
	 * @return the nqf levelsfiltered list
	 */
	public List<NqfLevels> getNqfLevelsfilteredList() {
		return nqflevelsfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param nqflevelsfilteredList
	 *            the new nqf levelsfiltered list
	 * @see NqfLevels
	 */
	public void setNqfLevelsfilteredList(List<NqfLevels> nqflevelsfilteredList) {
		this.nqflevelsfilteredList = nqflevelsfilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<NqfLevels> complete(String desc) {
		List<NqfLevels> l = null;
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
	public LazyDataModel<NqfLevels> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<NqfLevels> dataModel) {
		this.dataModel = dataModel;
	}

}
