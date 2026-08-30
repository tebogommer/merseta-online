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

import haj.com.entity.Municipality;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MunicipalityService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityUI.
 */
@ManagedBean(name = "municipalityUI")
@ViewScoped
public class MunicipalityUI extends AbstractUI {

	/** The service. */
	private MunicipalityService service = new MunicipalityService();

	/** The municipality list. */
	private List<Municipality> municipalityList = null;

	/** The municipalityfiltered list. */
	private List<Municipality> municipalityfilteredList = null;

	/** The municipality. */
	private Municipality municipality = null;

	/** The data model. */
	private LazyDataModel<Municipality> dataModel;

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
	 * Initialize method to get all Municipality and prepare a for a create of a
	 * new Municipality.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Municipality
	 */
	private void runInit() throws Exception {
		prepareNew();
		municipalityInfo();
	}

	/**
	 * Get all Municipality for data table.
	 *
	 * @author TechFinium
	 * @see Municipality
	 */
	public void municipalityInfo() {

		dataModel = new LazyDataModel<Municipality>() {

			private static final long serialVersionUID = 1L;
			private List<Municipality> retorno = new ArrayList<Municipality>();

			@Override
			public List<Municipality> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allMunicipality(Municipality.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Municipality.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Municipality obj) {
				return obj.getId();
			}

			@Override
			public Municipality getRowData(String rowKey) {
				for (Municipality obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Municipality into database.
	 *
	 * @author TechFinium
	 * @see Municipality
	 */
	public void municipalityInsert() {
		try {
			service.create(this.municipality);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			municipalityInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Municipality from database.
	 *
	 * @author TechFinium
	 * @see Municipality
	 */
	public void municipalityDelete() {
		try {
			service.delete(this.municipality);
			prepareNew();
			municipalityInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the municipality list.
	 *
	 * @return the municipality list
	 */
	public List<Municipality> getMunicipalityList() {
		return municipalityList;
	}

	/**
	 * Sets the municipality list.
	 *
	 * @param municipalityList
	 *            the new municipality list
	 */
	public void setMunicipalityList(List<Municipality> municipalityList) {
		this.municipalityList = municipalityList;
	}

	/**
	 * Gets the municipality.
	 *
	 * @return the municipality
	 */
	public Municipality getMunicipality() {
		return municipality;
	}

	/**
	 * Sets the municipality.
	 *
	 * @param municipality
	 *            the new municipality
	 */
	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	/**
	 * Create new instance of Municipality .
	 *
	 * @author TechFinium
	 * @see Municipality
	 */
	public void prepareNew() {
		municipality = new Municipality();
	}

	/**
	 * Update Municipality in database.
	 *
	 * @author TechFinium
	 * @see Municipality
	 */
	public void municipalityUpdate() {
		try {
			service.update(this.municipality);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			municipalityInfo();
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
	 * @return the municipality DD
	 * @see Municipality
	 */
	public List<SelectItem> getMunicipalityDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		municipalityInfo();
		for (Municipality ug : municipalityList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the municipalityfiltered list.
	 *
	 * @return the municipalityfiltered list
	 */
	public List<Municipality> getMunicipalityfilteredList() {
		return municipalityfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param municipalityfilteredList
	 *            the new municipalityfiltered list
	 * @see Municipality
	 */
	public void setMunicipalityfilteredList(List<Municipality> municipalityfilteredList) {
		this.municipalityfilteredList = municipalityfilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Municipality> complete(String desc) {
		List<Municipality> l = null;
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
	public LazyDataModel<Municipality> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Municipality> dataModel) {
		this.dataModel = dataModel;
	}

}
