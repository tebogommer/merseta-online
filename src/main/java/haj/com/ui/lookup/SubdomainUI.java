package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Subdomain;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SubdomainService;

// TODO: Auto-generated Javadoc
/**
 * The Class SubdomainUI.
 */
@ManagedBean(name = "subdomainUI")
@ViewScoped
public class SubdomainUI extends AbstractUI {

	/** The service. */
	private SubdomainService service = new SubdomainService();
	
	/** The subdomain list. */
	private List<Subdomain> subdomainList = null;
	
	/** The subdomainfiltered list. */
	private List<Subdomain> subdomainfilteredList = null;
	
	/** The subdomain. */
	private Subdomain subdomain = null;
	
	/** The data model. */
	private LazyDataModel<Subdomain> dataModel;

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
	 * Initialize method to get all Subdomain and prepare a for a create of a
	 * new Subdomain.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Subdomain
	 */
	private void runInit() throws Exception {
		prepareNew();
		subdomainInfo();
	}

	/**
	 * Get all Subdomain for data table.
	 *
	 * @author TechFinium
	 * @see Subdomain
	 */
	public void subdomainInfo() {

		dataModel = new LazyDataModel<Subdomain>() {

			private static final long serialVersionUID = 1L;
			private List<Subdomain> retorno = new ArrayList<Subdomain>();

			@Override
			public List<Subdomain> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allSubdomain(Subdomain.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Subdomain.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Subdomain obj) {
				return obj.getId();
			}

			@Override
			public Subdomain getRowData(String rowKey) {
				for (Subdomain obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Subdomain into database.
	 *
	 * @author TechFinium
	 * @see Subdomain
	 */
	public void subdomainInsert() {
		try {
			service.create(this.subdomain);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			subdomainInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Subdomain in database.
	 *
	 * @author TechFinium
	 * @see Subdomain
	 */
	public void subdomainUpdate() {
		try {
			service.update(this.subdomain);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			subdomainInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Subdomain from database.
	 *
	 * @author TechFinium
	 * @see Subdomain
	 */
	public void subdomainDelete() {
		try {
			service.delete(this.subdomain);
			prepareNew();
			subdomainInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Subdomain.
	 *
	 * @author TechFinium
	 * @see Subdomain
	 */
	public void prepareNew() {
		subdomain = new Subdomain();
	}

	/*
	 * public List<SelectItem> getSubdomainDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * subdomainInfo(); for (Subdomain ug : subdomainList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Subdomain> complete(String desc) {
		List<Subdomain> l = null;
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
	 * Gets the subdomain list.
	 *
	 * @return the subdomain list
	 */
	public List<Subdomain> getSubdomainList() {
		return subdomainList;
	}

	/**
	 * Sets the subdomain list.
	 *
	 * @param subdomainList the new subdomain list
	 */
	public void setSubdomainList(List<Subdomain> subdomainList) {
		this.subdomainList = subdomainList;
	}

	/**
	 * Gets the subdomain.
	 *
	 * @return the subdomain
	 */
	public Subdomain getSubdomain() {
		return subdomain;
	}

	/**
	 * Sets the subdomain.
	 *
	 * @param subdomain the new subdomain
	 */
	public void setSubdomain(Subdomain subdomain) {
		this.subdomain = subdomain;
	}

	/**
	 * Gets the subdomainfiltered list.
	 *
	 * @return the subdomainfiltered list
	 */
	public List<Subdomain> getSubdomainfilteredList() {
		return subdomainfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param subdomainfilteredList            the new subdomainfilteredList list
	 * @see Subdomain
	 */
	public void setSubdomainfilteredList(List<Subdomain> subdomainfilteredList) {
		this.subdomainfilteredList = subdomainfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Subdomain> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Subdomain> dataModel) {
		this.dataModel = dataModel;
	}

}
