package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Institution;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InstitutionService;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionUI.
 */
@ManagedBean(name = "institutionUI")
@ViewScoped
public class InstitutionUI extends AbstractUI {

	/** The service. */
	private InstitutionService service = new InstitutionService();
	
	/** The institution list. */
	private List<Institution> institutionList = null;
	
	/** The institutionfiltered list. */
	private List<Institution> institutionfilteredList = null;
	
	/** The institution. */
	private Institution institution = null;
	
	/** The data model. */
	private LazyDataModel<Institution> dataModel;

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
	 * Initialize method to get all Institution and prepare a for a create of a new
	 * Institution.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Institution
	 */
	private void runInit() throws Exception {
		prepareNew();
		institutionInfo();
	}

	/**
	 * Get all Institution for data table.
	 *
	 * @author TechFinium
	 * @see Institution
	 */
	public void institutionInfo() {

		dataModel = new LazyDataModel<Institution>() {

			private static final long serialVersionUID = 1L;
			private List<Institution> retorno = new ArrayList<Institution>();

			@Override
			public List<Institution> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allInstitution(Institution.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Institution.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Institution obj) {
				return obj.getId();
			}

			@Override
			public Institution getRowData(String rowKey) {
				for (Institution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Institution into database.
	 *
	 * @author TechFinium
	 * @see Institution
	 */
	public void institutionInsert() {
		try {
			service.create(this.institution);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			institutionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Institution in database.
	 *
	 * @author TechFinium
	 * @see Institution
	 */
	public void institutionUpdate() {
		try {
			service.update(this.institution);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			institutionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Institution from database.
	 *
	 * @author TechFinium
	 * @see Institution
	 */
	public void institutionDelete() {
		try {
			service.delete(this.institution);
			prepareNew();
			institutionInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted "));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Institution.
	 *
	 * @author TechFinium
	 * @see Institution
	 */
	public void prepareNew() {
		institution = new Institution();
	}

	/*
	 * public List<SelectItem> getInstitutionDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * institutionInfo(); for (Institution ug : institutionList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Institution> complete(String desc) {
		List<Institution> l = null;
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
	 * Gets the institution list.
	 *
	 * @return the institution list
	 */
	public List<Institution> getInstitutionList() {
		return institutionList;
	}

	/**
	 * Sets the institution list.
	 *
	 * @param institutionList the new institution list
	 */
	public void setInstitutionList(List<Institution> institutionList) {
		this.institutionList = institutionList;
	}

	/**
	 * Gets the institution.
	 *
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * Sets the institution.
	 *
	 * @param institution the new institution
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/**
	 * Gets the institutionfiltered list.
	 *
	 * @return the institutionfiltered list
	 */
	public List<Institution> getInstitutionfilteredList() {
		return institutionfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param institutionfilteredList            the new institutionfilteredList list
	 * @see Institution
	 */
	public void setInstitutionfilteredList(List<Institution> institutionfilteredList) {
		this.institutionfilteredList = institutionfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Institution> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Institution> dataModel) {
		this.dataModel = dataModel;
	}

}
