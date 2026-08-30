package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SDFType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SDFTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFTypeUI.
 */
@ManagedBean(name = "sdftypeUI")
@ViewScoped
public class SDFTypeUI extends AbstractUI {

	/** The service. */
	private SDFTypeService service = new SDFTypeService();
	
	/** The sdftype list. */
	private List<SDFType> sdftypeList = null;
	
	/** The sdftypefiltered list. */
	private List<SDFType> sdftypefilteredList = null;
	
	/** The sdftype. */
	private SDFType sdftype = null;
	
	/** The data model. */
	private LazyDataModel<SDFType> dataModel;

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
	 * Initialize method to get all SDFType and prepare a for a create of a new
	 * SDFType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see SDFType
	 */
	private void runInit() throws Exception {
		prepareNew();
		sdftypeInfo();
	}

	/**
	 * Get all SDFType for data table.
	 *
	 * @author TechFinium
	 * @see SDFType
	 */
	public void sdftypeInfo() {

		dataModel = new LazyDataModel<SDFType>() {

			private static final long serialVersionUID = 1L;
			private List<SDFType> retorno = new ArrayList<SDFType>();

			@Override
			public List<SDFType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSDFType(SDFType.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SDFType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFType obj) {
				return obj.getId();
			}

			@Override
			public SDFType getRowData(String rowKey) {
				for (SDFType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert SDFType into database.
	 *
	 * @author TechFinium
	 * @see SDFType
	 */
	public void sdftypeInsert() {
		try {
			service.create(this.sdftype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sdftypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SDFType in database.
	 *
	 * @author TechFinium
	 * @see SDFType
	 */
	public void sdftypeUpdate() {
		try {
			service.update(this.sdftype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sdftypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SDFType from database.
	 *
	 * @author TechFinium
	 * @see SDFType
	 */
	public void sdftypeDelete() {
		try {
			service.delete(this.sdftype);
			prepareNew();
			sdftypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SDFType.
	 *
	 * @author TechFinium
	 * @see SDFType
	 */
	public void prepareNew() {
		sdftype = new SDFType();
	}

	/*
	 * public List<SelectItem> getSDFTypeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * sdftypeInfo(); for (SDFType ug : sdftypeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SDFType> complete(String desc) {
		List<SDFType> l = null;
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
	 * Gets the SDF type list.
	 *
	 * @return the SDF type list
	 */
	public List<SDFType> getSDFTypeList() {
		return sdftypeList;
	}

	/**
	 * Sets the SDF type list.
	 *
	 * @param sdftypeList the new SDF type list
	 */
	public void setSDFTypeList(List<SDFType> sdftypeList) {
		this.sdftypeList = sdftypeList;
	}

	/**
	 * Gets the sdftype.
	 *
	 * @return the sdftype
	 */
	public SDFType getSdftype() {
		return sdftype;
	}

	/**
	 * Sets the sdftype.
	 *
	 * @param sdftype the new sdftype
	 */
	public void setSdftype(SDFType sdftype) {
		this.sdftype = sdftype;
	}

	/**
	 * Gets the SDF typefiltered list.
	 *
	 * @return the SDF typefiltered list
	 */
	public List<SDFType> getSDFTypefilteredList() {
		return sdftypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sdftypefilteredList            the new sdftypefilteredList list
	 * @see SDFType
	 */
	public void setSDFTypefilteredList(List<SDFType> sdftypefilteredList) {
		this.sdftypefilteredList = sdftypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SDFType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SDFType> dataModel) {
		this.dataModel = dataModel;
	}

}
