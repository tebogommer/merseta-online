package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressHistoryService;
import haj.com.service.CompanyService;
import haj.com.service.SitesHistoryService;
import haj.com.service.SitesService;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesUI.
 */
@ManagedBean(name = "sitesUI")
@ViewScoped
public class SitesUI extends AbstractUI {

	/** The service. */
	private SitesService service = new SitesService();

	/** The sitesfiltered list. */
	private List<Sites> sitesfilteredList = null;

	/** The sites. */
	private Sites sites = null;

	/** The company. */
	private Company company;

	/** The data model. */
	private LazyDataModel<Sites> dataModel;

	/** The sitesfiltered list. */
	private List<Sites> companySiteList = new ArrayList<>();
	
	private String setmisValidationErrors = "";

	private Long totalSiteNumber;

	private LegacyOrganisationSites legacyOrganisationSites;
	private CompanyService companyService = new CompanyService();
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
	 * Initialize method to get all Sites and prepare a for a create of a new Sites.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	private void runInit() throws Exception {
		prepareNew();
		// sitesInfo();
	}

	/**
	 * Get all Sites for data table.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void sitesInfo() {

		dataModel = new LazyDataModel<Sites>() {

			private static final long serialVersionUID = 1L;
			private List<Sites> retorno = new ArrayList<Sites>();

			@Override
			public List<Sites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSites(Sites.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Sites.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Sites obj) {
				return obj.getId();
			}

			@Override
			public Sites getRowData(String rowKey) {
				for (Sites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Sites into database.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void sitesInsert() {
		try {
			if (sites.getCompany() == null) {
				sites.setCompany(company);
			}
			service.create(this.sites);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitesInfo();

			// runClientSideExecute("PF('dlgSites').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Insert Sites into database.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void createSiteAndCreateTask() {
		try {
			setmisValidationErrors = "";
			sites.setFormUser(getSessionUI().getActiveUser());
			sites.setSiteStatus(CompanyStatusEnum.PendingChangeApproval);
			if (sites.getCompany() == null) {
				sites.setCompany(company);
			}
			if(sites.getId() !=null) {
				// Update Site
				SitesHistoryService.instance().createHistory(sites.getId());
				AddressHistoryService.instance().createHistory(sites.getRegisteredAddress().getId());
				service.updateSiteAndCreateTask(sites, getSessionUI().getActiveUser());
			} else {
				//Create new Site
				service.createSiteAndCreateTask(this.sites,getSessionUI().getActiveUser());
			}
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runClientSideExecute("PF('dlgSites').hide()");
			runClientSideExecute("updateInfo()");
			sitesInfo();
			// runClientSideExecute("PF('dlgSites').hide()");
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidationErrors = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
			// extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateLegacyOrganisationSites() {
		try {
			if(legacyOrganisationSites != null) {
				sites = new Sites();
				sites.setRegisteredAddress(new Address());
				if (company == null) {
					company = companyService.findByLevyNumberNoResolve(legacyOrganisationSites.getLinkedSdl());				
				}
				if (company != null) {
					companySiteList = service.findByCompany(company);
					sites.setCompany(company);
					sites.setCompanyName(legacyOrganisationSites.getOrganisationNameLegal());
					sites.setLegacyOrganisationSites(legacyOrganisationSites);
					sites.setTelNumber(legacyOrganisationSites.getPhone());
					sites.setFaxNumber(legacyOrganisationSites.getFax());
					super.runClientSideExecute("PF('dlgSites').show()");
				}				
			}			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/**
	 * Update Sites in database.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void sitesUpdate() {
		try {
			setmisValidationErrors = "";
			service.update(this.sites);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Sites from database.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void sitesDelete() {
		try {
			service.delete(this.sites);
			prepareNew();
			sitesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			setmisValidationErrors = "";
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Sites.
	 *
	 * @author TechFinium
	 * @see Sites
	 */
	public void prepareNew() {

		try {
			setmisValidationErrors = "";
			sites = new Sites();
			sites.setRegisteredAddress(new Address());
			if (company != null) {
				companySiteList = service.findByCompany(company);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
		}
		calcTotal();
	}

	public void checkTotal() {
		calcTotal();
	}

	public void calcTotal() {
		if (company != null) {
			try {
				Long currentCount = service.findSumByCompany(company);
				if (currentCount != null && company.getNumberOfEmployees() != null && company.getNumberOfEmployees() > currentCount) {
					if (sites.getNumberOfEmployees() != null) {
						totalSiteNumber = (company.getNumberOfEmployees() - currentCount) + sites.getNumberOfEmployees();
					} else {
						totalSiteNumber = company.getNumberOfEmployees() - currentCount;
					}
				} else if (currentCount == null && company != null && company.getNumberOfEmployees() != null) {
					totalSiteNumber = company.getNumberOfEmployees().longValue();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Sites> complete(String desc) {
		List<Sites> l = null;
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
	 * Gets the sites.
	 *
	 * @return the sites
	 */
	public Sites getSites() {
		return sites;
	}

	/**
	 * Sets the sites.
	 *
	 * @param sites
	 *            the new sites
	 */
	public void setSites(Sites sites) {
		this.sites = sites;
	}

	/**
	 * Gets the sitesfiltered list.
	 *
	 * @return the sitesfiltered list
	 */
	public List<Sites> getSitesfilteredList() {
		return sitesfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sitesfilteredList
	 *            the new sitesfilteredList list
	 * @see Sites
	 */
	public void setSitesfilteredList(List<Sites> sitesfilteredList) {
		this.sitesfilteredList = sitesfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Sites> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Sites> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getTotalSiteNumber() {
		return totalSiteNumber;
	}

	public void setTotalSiteNumber(Long totalSiteNumber) {
		this.totalSiteNumber = totalSiteNumber;
	}

	public List<Sites> getCompanySiteList() {
		return companySiteList;
	}

	public void setCompanySiteList(List<Sites> companySiteList) {
		this.companySiteList = companySiteList;
	}

	public LegacyOrganisationSites getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(LegacyOrganisationSites legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public String getSetmisValidationErrors() {
		return setmisValidationErrors;
	}

	public void setSetmisValidationErrors(String setmisValidationErrors) {
		this.setmisValidationErrors = setmisValidationErrors;
	}

}
