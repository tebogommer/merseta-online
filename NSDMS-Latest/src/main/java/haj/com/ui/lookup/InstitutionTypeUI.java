package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InstitutionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InstitutionTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionTypeUI.
 */
@ManagedBean(name = "institutiontypeUI")
@ViewScoped
public class InstitutionTypeUI extends AbstractUI {

	/** The service. */
	private InstitutionTypeService service = new InstitutionTypeService();
	
	/** The Institution type list. */
	private List<InstitutionType> InstitutionTypeList = null;
	
	/** The Institution typefiltered list. */
	private List<InstitutionType> InstitutionTypefilteredList = null;
	
	/** The institution type. */
	private InstitutionType institutionType = null;
	
	/** The data model. */
	private LazyDataModel<InstitutionType> dataModel;

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
	 * Initialize method to get all InstitutionType and prepare a for a create of a new
	 * InstitutionType.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see InstitutionType
	 */
	private void runInit() throws Exception {
		prepareNew();
		InstitutionTypeInfo();
	}

	/**
	 * Get all InstitutionType for data table.
	 *
	 * @author TechFinium
	 * @see InstitutionType
	 */
	public void InstitutionTypeInfo() {

		dataModel = new LazyDataModel<InstitutionType>() {

			private static final long serialVersionUID = 1L;
			private List<InstitutionType> retorno = new ArrayList<InstitutionType>();

			@Override
			public List<InstitutionType> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allInstitutionType(InstitutionType.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(InstitutionType.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(InstitutionType obj) {
				return obj.getId();
			}

			@Override
			public InstitutionType getRowData(String rowKey) {
				for (InstitutionType obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert InstitutionType into database.
	 *
	 * @author TechFinium
	 * @see InstitutionType
	 */
	public void InstitutionTypeInsert() {
		try {
			service.create(this.institutionType);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			InstitutionTypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update InstitutionType in database.
	 *
	 * @author TechFinium
	 * @see InstitutionType
	 */
	public void InstitutionTypeUpdate() {
		try {
			service.update(this.institutionType);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			InstitutionTypeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete InstitutionType from database.
	 *
	 * @author TechFinium
	 * @see InstitutionType
	 */
	public void InstitutionTypeDelete() {
		try {
			service.delete(this.institutionType);
			prepareNew();
			InstitutionTypeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted "));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of InstitutionType.
	 *
	 * @author TechFinium
	 * @see InstitutionType
	 */
	public void prepareNew() {
		institutionType = new InstitutionType();
	}

	/*
	 * public List<SelectItem> getInstitutionTypeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * InstitutionTypeInfo(); for (InstitutionType ug : InstitutionTypeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<InstitutionType> complete(String desc) {
		List<InstitutionType> l = null;
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
	 * Gets the institution type list.
	 *
	 * @return the institution type list
	 */
	public List<InstitutionType> getInstitutionTypeList() {
		return InstitutionTypeList;
	}

	/**
	 * Sets the institution type list.
	 *
	 * @param InstitutionTypeList the new institution type list
	 */
	public void setInstitutionTypeList(List<InstitutionType> InstitutionTypeList) {
		this.InstitutionTypeList = InstitutionTypeList;
	}

	/**
	 * Gets the institution type.
	 *
	 * @return the institution type
	 */
	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	/**
	 * Sets the institution type.
	 *
	 * @param InstitutionType the new institution type
	 */
	public void setInstitutionType(InstitutionType InstitutionType) {
		this.institutionType = InstitutionType;
	}

	/**
	 * Gets the institution typefiltered list.
	 *
	 * @return the institution typefiltered list
	 */
	public List<InstitutionType> getInstitutionTypefilteredList() {
		return InstitutionTypefilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param InstitutionTypefilteredList            the new InstitutionTypefilteredList list
	 * @see InstitutionType
	 */
	public void setInstitutionTypefilteredList(List<InstitutionType> InstitutionTypefilteredList) {
		this.InstitutionTypefilteredList = InstitutionTypefilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<InstitutionType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<InstitutionType> dataModel) {
		this.dataModel = dataModel;
	}

}
