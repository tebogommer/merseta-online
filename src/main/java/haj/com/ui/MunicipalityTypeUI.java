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

import haj.com.entity.MunicipalityType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MunicipalityTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityTypeUI.
 */
@ManagedBean(name = "municipalitytypeUI")
@ViewScoped
public class MunicipalityTypeUI extends AbstractUI {

	/** The service. */
	private MunicipalityTypeService service = new MunicipalityTypeService();

	/** The municipalitytype list. */
	private List<MunicipalityType> municipalitytypeList = null;

	/** The municipalitytypefiltered list. */
	private List<MunicipalityType> municipalitytypefilteredList = null;

	/** The municipalitytype. */
	private MunicipalityType municipalitytype = null;

	/** The data model. */
	private LazyDataModel<MunicipalityType> dataModel;

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
	 * Initialize method to get all MunicipalityType and prepare a for a create
	 * of a new MunicipalityType.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see MunicipalityType
	 */
	private void runInit() throws Exception {
		prepareNew();
		municipalitytypeInfo();
	}

	/**
	 * Get all MunicipalityType for data table.
	 *
	 * @author TechFinium
	 * @see MunicipalityType
	 */
	public void municipalitytypeInfo() {

		dataModel = new LazyDataModel<MunicipalityType>() {

			private static final long serialVersionUID = 1L;
			private List<MunicipalityType> retorno = new ArrayList<MunicipalityType>();

			@Override
			public List<MunicipalityType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allMunicipalityType(MunicipalityType.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(MunicipalityType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MunicipalityType obj) {
				return obj.getId();
			}

			@Override
			public MunicipalityType getRowData(String rowKey) {
				for (MunicipalityType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert MunicipalityType into database.
	 *
	 * @author TechFinium
	 * @see MunicipalityType
	 */
	public void municipalitytypeInsert() {
		try {
			service.create(this.municipalitytype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			municipalitytypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete MunicipalityType from database.
	 *
	 * @author TechFinium
	 * @see MunicipalityType
	 */
	public void municipalitytypeDelete() {
		try {
			service.delete(this.municipalitytype);
			prepareNew();
			municipalitytypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the municipality type list.
	 *
	 * @return the municipality type list
	 */
	public List<MunicipalityType> getMunicipalityTypeList() {
		return municipalitytypeList;
	}

	/**
	 * Sets the municipality type list.
	 *
	 * @param municipalitytypeList
	 *            the new municipality type list
	 */
	public void setMunicipalityTypeList(List<MunicipalityType> municipalitytypeList) {
		this.municipalitytypeList = municipalitytypeList;
	}

	/**
	 * Gets the municipalitytype.
	 *
	 * @return the municipalitytype
	 */
	public MunicipalityType getMunicipalitytype() {
		return municipalitytype;
	}

	/**
	 * Sets the municipalitytype.
	 *
	 * @param municipalitytype
	 *            the new municipalitytype
	 */
	public void setMunicipalitytype(MunicipalityType municipalitytype) {
		this.municipalitytype = municipalitytype;
	}

	/**
	 * Create new instance of MunicipalityType .
	 *
	 * @author TechFinium
	 * @see MunicipalityType
	 */
	public void prepareNew() {
		municipalitytype = new MunicipalityType();
	}

	/**
	 * Update MunicipalityType in database.
	 *
	 * @author TechFinium
	 * @see MunicipalityType
	 */
	public void municipalitytypeUpdate() {
		try {
			service.update(this.municipalitytype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			municipalitytypeInfo();
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
	 * @return the municipality type DD
	 * @see MunicipalityType
	 */
	public List<SelectItem> getMunicipalityTypeDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		municipalitytypeInfo();
		for (MunicipalityType ug : municipalitytypeList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the municipality typefiltered list.
	 *
	 * @return the municipality typefiltered list
	 */
	public List<MunicipalityType> getMunicipalityTypefilteredList() {
		return municipalitytypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param municipalitytypefilteredList
	 *            the new municipality typefiltered list
	 * @see MunicipalityType
	 */
	public void setMunicipalityTypefilteredList(List<MunicipalityType> municipalitytypefilteredList) {
		this.municipalitytypefilteredList = municipalitytypefilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<MunicipalityType> complete(String desc) {
		List<MunicipalityType> l = null;
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
	public LazyDataModel<MunicipalityType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<MunicipalityType> dataModel) {
		this.dataModel = dataModel;
	}

}
