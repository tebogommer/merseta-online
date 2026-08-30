package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyService;
import haj.com.service.lookup.ChamberService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUI.
 */
@ManagedBean(name = "companyUI")
@ViewScoped
public class CompanyUI extends AbstractUI {

	/** The service. */
	private CompanyService service = new CompanyService();
	
	/** The company list. */
	private List<Company> companyList = null;
	
	/** The companyfiltered list. */
	private List<Company> companyfilteredList = null;
	
	/** The company. */
	private Company company = null;
	
	/** The form user. */
	private Users formUser;
	
	/** The data model. */
	private LazyDataModel<Company> dataModel;
	
	/** The address service. */
	private AddressService addressService = new AddressService();
	
	/** The copy address. */
	private Boolean copyAddress;
	
	/** The residential address. */
	private Address residentialAddress;
	
	/** The postal address. */
	private Address postalAddress;
	
	/** The chamber service. */
	private ChamberService chamberService = new ChamberService();
	
	/** The chambers. */
	private List<Chamber> chambers;
	
	/** The add form user. */
	private Boolean addFormUser;
	
	/** The id passort select. */
	private IdPassportEnum idPassortSelect = null;
	
	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	
	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

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
	 * Initialize method to get all Company and prepare a for a create of a new
	 * Company.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		prepareNew();
		companyInfo();
		chambers = chamberService.allChamber();
		getSearchUserPassportOrIdUI().setObject(this);
	}

	/**
	 * Get all Company for data table.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void companyInfo() {

		dataModel = new LazyDataModel<Company>() {

			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {
						retorno = service.allCompany(Company.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						dataModel.setRowCount(service.allCompanyCount(getSessionUI().getActiveUser()));						
					}else {
						retorno = service.allCompany(Company.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(Company.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Company into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void companyInsert() {
		try {
			cpyAddresses();
			service.createCompanyAndSendTask(companyList, getSessionUI().getActiveUser(), true, ConfigDocProcessEnum.COMPANY, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
		
	/**
	 * Passport rsa id change.
	 */
	public void passportRsaIdChange(){
		addFormUser = false;
	}
	
	/**
	 * Validates if user selected either passport or RSA ID
	 * Checks on type selected.
	 */
	public void validateFormUser(){
		try {
			if (idPassortSelect == IdPassportEnum.Passport) {
				addFormUser = true;
			} else {
				if (formUser.getRsaIDNumber() != null) {
					if (formUser.getRsaIDNumber().length() == 13) {
						addFormUser = true;
					}else {
						addWarningMessage(super.getEntryLanguage("provide.valid.rsa.id.number"));
						addFormUser = false;
					}
				} else {
					addWarningMessage(super.getEntryLanguage("provide.rsa.id.number"));
				}
			}	
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object the object
	 */
	public void callBackMethod(Object object) {
		this.formUser = (Users) object;
		super.runClientSideUpdate("tpInsGrid");
		if (formUser.getId() == null) {
			addFormUser = true;
		}
		addInfoMessage(super.getEntryLanguage("validation.complete"));
	}
	
	/**
	 * Insert company training provider into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void companyTrainingProviderInsertNotLoggedIn() {
		try {
			company.setCompanyType(CompanyTypeEnum.TP);
			cpyAddresses();
			service.createCompanyAndSendTask(companyList, formUser, true, ConfigDocProcessEnum.TP, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null,null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Company in database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void companyUpdate() {
		try {
			service.update(this.company);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Company from database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void companyDelete() {
		try {
			service.delete(this.company);
			prepareNew();
			companyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Company.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void prepareNew() {
		company = new Company();
		company.setCompanyType(CompanyTypeEnum.COMPANY);
		residentialAddress = new Address();
		postalAddress = new Address();
		addFormUser = false;
		this.formUser = new Users();
		formUser.setAdmin(false);
		this.copyAddress = false;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Company> complete(String desc) {
		List<Company> l = null;
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
	 * Gets the company list.
	 *
	 * @return the company list
	 */
	public List<Company> getCompanyList() {
		return companyList;
	}

	/**
	 * Sets the company list.
	 *
	 * @param companyList the new company list
	 */
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
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
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the companyfiltered list.
	 *
	 * @return the companyfiltered list
	 */
	public List<Company> getCompanyfilteredList() {
		return companyfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companyfilteredList            the new companyfilteredList list
	 * @see Company
	 */
	public void setCompanyfilteredList(List<Company> companyfilteredList) {
		this.companyfilteredList = companyfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Cpy addresses.
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				addressService.copyFromToAddress(residentialAddress, postalAddress);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		postalAddress.setSameAddress(copyAddress);
		company.setResidentialAddress(residentialAddress);
		company.setPostalAddress(postalAddress);
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			addressService.clearAddress(postalAddress);
		}
	}

	/**
	 * Resolve addresses.
	 */
	public void resolveAddresses() {
		this.residentialAddress = this.company.getResidentialAddress();
		this.postalAddress = this.company.getPostalAddress();
		this.copyAddress = this.postalAddress.getSameAddress();
	}

	/**
	 * Gets the residential address.
	 *
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 *
	 * @param residentialAddress the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 *
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 *
	 * @param postalAddress the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the copy address.
	 *
	 * @return the copy address
	 */
	public Boolean getCopyAddress() {
		return copyAddress;
	}

	/**
	 * Sets the copy address.
	 *
	 * @param copyAddress the new copy address
	 */
	public void setCopyAddress(Boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	/**
	 * Gets the chambers.
	 *
	 * @return the chambers
	 */
	public List<Chamber> getChambers() {
		return chambers;
	}

	/**
	 * Sets the chambers.
	 *
	 * @param chambers the new chambers
	 */
	public void setChambers(List<Chamber> chambers) {
		this.chambers = chambers;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Gets the id passort select.
	 *
	 * @return the id passort select
	 */
	public IdPassportEnum getIdPassortSelect() {
		return idPassortSelect;
	}

	/**
	 * Sets the id passort select.
	 *
	 * @param idPassortSelect the new id passort select
	 */
	public void setIdPassortSelect(IdPassportEnum idPassortSelect) {
		this.idPassortSelect = idPassortSelect;
	}

	/**
	 * Gets the adds the form user.
	 *
	 * @return the adds the form user
	 */
	public Boolean getAddFormUser() {
		return addFormUser;
	}

	/**
	 * Sets the adds the form user.
	 *
	 * @param addFormUser the new adds the form user
	 */
	public void setAddFormUser(Boolean addFormUser) {
		this.addFormUser = addFormUser;
	}

	/**
	 * Gets the search user passport or id UI.
	 *
	 * @return the search user passport or id UI
	 */
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	/**
	 * Sets the search user passport or id UI.
	 *
	 * @param searchUserPassportOrIdUI the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

}
