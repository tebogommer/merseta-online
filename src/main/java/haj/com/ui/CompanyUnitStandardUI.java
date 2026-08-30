package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUnitStandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUnitStandardUI.
 */
@ManagedBean(name = "companyunitstandardUI")
@ViewScoped
public class CompanyUnitStandardUI extends AbstractUI {

	/** The service. */
	private CompanyUnitStandardService service = new CompanyUnitStandardService();
	
	/** The companyunitstandard list. */
	private List<CompanyUnitStandard> companyunitstandardList = null;
	
	/** The companyunitstandardfiltered list. */
	private List<CompanyUnitStandard> companyunitstandardfilteredList = null;
	
	/** The companyunitstandard. */
	private CompanyUnitStandard companyunitstandard = null;
	
	/** The data model. */
	private LazyDataModel<CompanyUnitStandard> dataModel;

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
	 * Initialize method to get all CompanyUnitStandard and prepare a for a
	 * create of a new CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see CompanyUnitStandard
	 */
	private void runInit() throws Exception {
		prepareNew();
		companyunitstandardInfo();
	}

	/**
	 * Get all CompanyUnitStandard for data table.
	 *
	 * @author TechFinium
	 * @see CompanyUnitStandard
	 */
	public void companyunitstandardInfo() {

		dataModel = new LazyDataModel<CompanyUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyUnitStandard> retorno = new ArrayList<CompanyUnitStandard>();

			@Override
			public List<CompanyUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allCompanyUnitStandard(CompanyUnitStandard.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public CompanyUnitStandard getRowData(String rowKey) {
				for (CompanyUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert CompanyUnitStandard into database.
	 *
	 * @author TechFinium
	 * @see CompanyUnitStandard
	 */
	public void companyunitstandardInsert() {
		try {
			service.create(this.companyunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyUnitStandard in database.
	 *
	 * @author TechFinium
	 * @see CompanyUnitStandard
	 */
	public void companyunitstandardUpdate() {
		try {
			service.update(this.companyunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyUnitStandard from database.
	 *
	 * @author TechFinium
	 * @see CompanyUnitStandard
	 */
	public void companyunitstandardDelete() {
		try {
			service.delete(this.companyunitstandard);
			prepareNew();
			companyunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyUnitStandard.
	 *
	 * @author TechFinium
	 * @see CompanyUnitStandard
	 */
	public void prepareNew() {
		companyunitstandard = new CompanyUnitStandard();
	}

	/*
	 * public List<SelectItem> getCompanyUnitStandardDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * companyunitstandardInfo(); for (CompanyUnitStandard ug :
	 * companyunitstandardList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<CompanyUnitStandard> complete(String desc) {
		List<CompanyUnitStandard> l = null;
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
	 * Gets the company unit standard list.
	 *
	 * @return the company unit standard list
	 */
	public List<CompanyUnitStandard> getCompanyUnitStandardList() {
		return companyunitstandardList;
	}

	/**
	 * Sets the company unit standard list.
	 *
	 * @param companyunitstandardList the new company unit standard list
	 */
	public void setCompanyUnitStandardList(List<CompanyUnitStandard> companyunitstandardList) {
		this.companyunitstandardList = companyunitstandardList;
	}

	/**
	 * Gets the companyunitstandard.
	 *
	 * @return the companyunitstandard
	 */
	public CompanyUnitStandard getCompanyunitstandard() {
		return companyunitstandard;
	}

	/**
	 * Sets the companyunitstandard.
	 *
	 * @param companyunitstandard the new companyunitstandard
	 */
	public void setCompanyunitstandard(CompanyUnitStandard companyunitstandard) {
		this.companyunitstandard = companyunitstandard;
	}

	/**
	 * Gets the company unit standardfiltered list.
	 *
	 * @return the company unit standardfiltered list
	 */
	public List<CompanyUnitStandard> getCompanyUnitStandardfilteredList() {
		return companyunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companyunitstandardfilteredList            the new companyunitstandardfilteredList list
	 * @see CompanyUnitStandard
	 */
	public void setCompanyUnitStandardfilteredList(List<CompanyUnitStandard> companyunitstandardfilteredList) {
		this.companyunitstandardfilteredList = companyunitstandardfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CompanyUnitStandard> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CompanyUnitStandard> dataModel) {
		this.dataModel = dataModel;
	}

}
