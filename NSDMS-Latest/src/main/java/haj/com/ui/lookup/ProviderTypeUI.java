package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProviderType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProviderTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderTypeUI.
 */
@ManagedBean(name = "providertypeUI")
@ViewScoped
public class ProviderTypeUI extends AbstractUI {

	/** The service. */
	private ProviderTypeService service = new ProviderTypeService();
	
	/** The providertype list. */
	private List<ProviderType> providertypeList = null;
	
	/** The providertypefiltered list. */
	private List<ProviderType> providertypefilteredList = null;
	
	/** The providertype. */
	private ProviderType providertype = null;
	
	/** The data model. */
	private LazyDataModel<ProviderType> dataModel;

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
	 * Initialize method to get all ProviderType and prepare a for a create of a
	 * new ProviderType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see ProviderType
	 */
	private void runInit() throws Exception {
		prepareNew();
		providertypeInfo();
	}

	/**
	 * Get all ProviderType for data table.
	 *
	 * @author TechFinium
	 * @see ProviderType
	 */
	public void providertypeInfo() {

		dataModel = new LazyDataModel<ProviderType>() {

			private static final long serialVersionUID = 1L;
			private List<ProviderType> retorno = new ArrayList<ProviderType>();

			@Override
			public List<ProviderType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allProviderType(ProviderType.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(ProviderType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ProviderType obj) {
				return obj.getId();
			}

			@Override
			public ProviderType getRowData(String rowKey) {
				for (ProviderType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ProviderType into database.
	 *
	 * @author TechFinium
	 * @see ProviderType
	 */
	public void providertypeInsert() {
		try {
			service.create(this.providertype);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providertypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ProviderType in database.
	 *
	 * @author TechFinium
	 * @see ProviderType
	 */
	public void providertypeUpdate() {
		try {
			service.update(this.providertype);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providertypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProviderType from database.
	 *
	 * @author TechFinium
	 * @see ProviderType
	 */
	public void providertypeDelete() {
		try {
			service.delete(this.providertype);
			prepareNew();
			providertypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProviderType.
	 *
	 * @author TechFinium
	 * @see ProviderType
	 */
	public void prepareNew() {
		providertype = new ProviderType();
	}

	/*
	 * public List<SelectItem> getProviderTypeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * providertypeInfo(); for (ProviderType ug : providertypeList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ProviderType> complete(String desc) {
		List<ProviderType> l = null;
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
	 * Gets the provider type list.
	 *
	 * @return the provider type list
	 */
	public List<ProviderType> getProviderTypeList() {
		return providertypeList;
	}

	/**
	 * Sets the provider type list.
	 *
	 * @param providertypeList the new provider type list
	 */
	public void setProviderTypeList(List<ProviderType> providertypeList) {
		this.providertypeList = providertypeList;
	}

	/**
	 * Gets the providertype.
	 *
	 * @return the providertype
	 */
	public ProviderType getProvidertype() {
		return providertype;
	}

	/**
	 * Sets the providertype.
	 *
	 * @param providertype the new providertype
	 */
	public void setProvidertype(ProviderType providertype) {
		this.providertype = providertype;
	}

	/**
	 * Gets the provider typefiltered list.
	 *
	 * @return the provider typefiltered list
	 */
	public List<ProviderType> getProviderTypefilteredList() {
		return providertypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param providertypefilteredList            the new providertypefilteredList list
	 * @see ProviderType
	 */
	public void setProviderTypefilteredList(List<ProviderType> providertypefilteredList) {
		this.providertypefilteredList = providertypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProviderType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ProviderType> dataModel) {
		this.dataModel = dataModel;
	}

}
