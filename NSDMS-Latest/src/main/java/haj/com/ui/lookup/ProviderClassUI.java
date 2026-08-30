package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProviderClass;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProviderClassService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderClassUI.
 */
@ManagedBean(name = "providerclassUI")
@ViewScoped
public class ProviderClassUI extends AbstractUI {

	/** The service. */
	private ProviderClassService service = new ProviderClassService();
	
	/** The providerclass list. */
	private List<ProviderClass> providerclassList = null;
	
	/** The providerclassfiltered list. */
	private List<ProviderClass> providerclassfilteredList = null;
	
	/** The providerclass. */
	private ProviderClass providerclass = null;
	
	/** The data model. */
	private LazyDataModel<ProviderClass> dataModel;

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
	 * Initialize method to get all ProviderClass and prepare a for a create of
	 * a new ProviderClass.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see ProviderClass
	 */
	private void runInit() throws Exception {
		prepareNew();
		providerclassInfo();
	}

	/**
	 * Get all ProviderClass for data table.
	 *
	 * @author TechFinium
	 * @see ProviderClass
	 */
	public void providerclassInfo() {

		dataModel = new LazyDataModel<ProviderClass>() {

			private static final long serialVersionUID = 1L;
			private List<ProviderClass> retorno = new ArrayList<ProviderClass>();

			@Override
			public List<ProviderClass> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allProviderClass(ProviderClass.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(ProviderClass.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(ProviderClass obj) {
				return obj.getId();
			}

			@Override
			public ProviderClass getRowData(String rowKey) {
				for (ProviderClass obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ProviderClass into database.
	 *
	 * @author TechFinium
	 * @see ProviderClass
	 */
	public void providerclassInsert() {
		try {
			service.create(this.providerclass);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providerclassInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ProviderClass in database.
	 *
	 * @author TechFinium
	 * @see ProviderClass
	 */
	public void providerclassUpdate() {
		try {
			service.update(this.providerclass);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			providerclassInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProviderClass from database.
	 *
	 * @author TechFinium
	 * @see ProviderClass
	 */
	public void providerclassDelete() {
		try {
			service.delete(this.providerclass);
			prepareNew();
			providerclassInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProviderClass.
	 *
	 * @author TechFinium
	 * @see ProviderClass
	 */
	public void prepareNew() {
		providerclass = new ProviderClass();
	}

	/*
	 * public List<SelectItem> getProviderClassDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * providerclassInfo(); for (ProviderClass ug : providerclassList) { //
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
	public List<ProviderClass> complete(String desc) {
		List<ProviderClass> l = null;
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
	 * Gets the provider class list.
	 *
	 * @return the provider class list
	 */
	public List<ProviderClass> getProviderClassList() {
		return providerclassList;
	}

	/**
	 * Sets the provider class list.
	 *
	 * @param providerclassList the new provider class list
	 */
	public void setProviderClassList(List<ProviderClass> providerclassList) {
		this.providerclassList = providerclassList;
	}

	/**
	 * Gets the providerclass.
	 *
	 * @return the providerclass
	 */
	public ProviderClass getProviderclass() {
		return providerclass;
	}

	/**
	 * Sets the providerclass.
	 *
	 * @param providerclass the new providerclass
	 */
	public void setProviderclass(ProviderClass providerclass) {
		this.providerclass = providerclass;
	}

	/**
	 * Gets the provider classfiltered list.
	 *
	 * @return the provider classfiltered list
	 */
	public List<ProviderClass> getProviderClassfilteredList() {
		return providerclassfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param providerclassfilteredList            the new providerclassfilteredList list
	 * @see ProviderClass
	 */
	public void setProviderClassfilteredList(List<ProviderClass> providerclassfilteredList) {
		this.providerclassfilteredList = providerclassfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProviderClass> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ProviderClass> dataModel) {
		this.dataModel = dataModel;
	}

}
