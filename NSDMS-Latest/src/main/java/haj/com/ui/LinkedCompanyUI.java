package haj.com.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Blank;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.Municipality;
import haj.com.entity.SDFCompany;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.SICCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyService;
import haj.com.service.MailDef;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.SICCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class LinkedCompanyUI.
 */
@ManagedBean(name = "linkedcompanyUI")
@ViewScoped
public class LinkedCompanyUI extends AbstractUI {

	/** The sdf company. */
	private SDFCompany sdfCompany;

	/** The sdf company service. */
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The company. */
	private Company company;

	/** The residential address. */
	private Address residentialAddress;

	/** The postal address. */
	private Address postalAddress;

	/** The sdf type service. */
	private SDFTypeService sdfTypeService = new SDFTypeService();

	/** The chambers. */
	private List<Chamber> chambers;

	/** The chamber service. */
	private ChamberService chamberService = new ChamberService();

	/** The sic code service. */
	private SICCodeService sicCodeService = new SICCodeService();

	/** The sic codes. */
	private List<SICCode> sicCodes;

	/** The address service. */
	private AddressService addressService = new AddressService();

	/** The copy address. */
	private Boolean copyAddress;

	/** The service. */
	private CompanyService service = new CompanyService();

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** The maximum size all all last names */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The maximum size all for company trade name */
	private Long MAX_COMPANY_TRADE_NAME_SIZE = HAJConstants.MAX_COMPANY_TRADE_NAME_SIZE;

	/** The maximum size all for company name */
	private Long MAX_ADDRESS_LINE_SIZE = HAJConstants.MAX_ADDRESS_LINE_SIZE;

	/** The maximum size all for company name */
	private Long MAX_ADDRESS_CODE_SIZE = HAJConstants.MAX_ADDRESS_CODE_SIZE;

	/** The maximum size all for fax number */
	private Long MAX_NUMBER_OF_EMPLOYEES_SIZE = HAJConstants.MAX_NUMBER_OF_EMPLOYEES_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant company levy number format. */
	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** The Constant telephone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The Constant cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		setObjects();
		chambers = chamberService.allChamber();
		sicCodes = sicCodeService.allSICCode();
	}

	/**
	 * Sets the objects.
	 */
	public void setObjects() {

		try {
			getSearchUserPassportOrIdUI().setObject(this);
			getSearchCompanyUI().setObject(this);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void changeObjects() {
		company.setDoneSearch(true);
	}

	/**
	 * Update company.
	 */
	public void updateCompany() {
		try {
			cpyAddresses();
			if (company.getLinkedCompany() == null) {
				company.setLinkedCompany(sdfCompany.getCompany());
			}
			service.save(company);

			if (sdfCompanyService.findByCompanyAndUser(company, sdfCompany.getSdf()) == null) {
				SDFCompany newsdComp = new SDFCompany(company, sdfCompany.getSdf());
				newsdComp.setSdfType(sdfCompany.getSdfType());
				sdfCompanyService.create(newsdComp);
			}

			addInfoMessage("Sucessfully updated");
			changeNull();
			sdfCompany = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void updateCompanyManagement() {
		try {
			cpyAddresses();
			if (company.getLinkedCompany() == null) {
				company.setLinkedCompany(sdfCompany.getCompany());
			}
			service.saveWithLinkedTask(getSessionUI().getActiveUser(), company);

			if (sdfCompanyService.findByCompanyAndUser(company, sdfCompany.getSdf()) == null) {
				SDFCompany newsdComp = new SDFCompany(company, sdfCompany.getSdf());
				newsdComp.setSdfType(sdfCompany.getSdfType());
				sdfCompanyService.create(newsdComp);
			}
			addInfoMessage("Sucessfully updated");
			changeNull();
			sdfCompany = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void delinkCompanyManagement() {
		try {
			// cpyAddresses();
			company.setLinkedCompany(null);
			service.rejectWithLinkedTask(getSessionUI().getActiveUser(), company);
			addInfoMessage("Sucessfully updated");
			changeNull();
			sdfCompany = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createDeleteCompTast() {
		try {
			this.company.setCompanyStatus(CompanyStatusEnum.Pending);

			String desc = "An attempt to delete Linked company has been made, please approve";
			service.update(this.company);
			// Creating change reason
			ChangeReasonService.instance().createChangeReason(this.company.getId(), this.company.getClass().getName(), CompanyInfoUI.changeReason);
			// Creating task
			TasksService.instance().findFirstInProcessAndCreateTask(desc, getSessionUI().getActiveUser(), this.company.getId(), this.company.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_LINKED_COMPANY, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			CompanyInfoUI.changeReason = new ChangeReason();
			addInfoMessage("Request has been sent for approval");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void changeNull() {
		company = null;
	}

	public void deleteLinkedCompany() {
		try {
			service.removeLink(company);
			addInfoMessage("Company Has Been Delinked");
			changeNull();
			sdfCompany = null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Company) {
				if (!((Company) object).equals(sdfCompany.getCompany())) {
					if (sdfCompanyService.findByCompanyCount(((Company) object)) == 0) {
						this.company = (Company) object;
						if (company.getId() == null && (company.getNonLevyPaying() == null || !company.getNonLevyPaying())) {
							addWarningMessage(getEntryLanguage("not.merseta.company"));
							this.company = null;				
						}else {				
							prepForUpdate();					
						}
					} else {
						addWarningMessage("Company already assigned to another SDF.");
					}
				} else {
					addWarningMessage("Cannot link a company to itself");
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearViews() {
		this.company = null;
		this.sdfCompany = null;
	}

	/**
	 * Prep for update.
	 */
	public void prepForUpdate() {
		if (company.getResidentialAddress() == null) {
			this.company.setResidentialAddress(new Address(company));
		}
		if (company.getPostalAddress() == null) {
			company.setPostalAddress(new Address(company));
		}
		if (sdfCompany == null || sdfCompany.getId() == null) {
			sdfCompany = new SDFCompany();
		}
		resolveAddresses();
	}

	/**
	 * Resolve addresses.
	 */
	public void resolveAddresses() {
		if (company.getPostalAddress() != null && company.getPostalAddress().getSameAddress() != null) {
			this.copyAddress = company.getPostalAddress().getSameAddress();
		} else {
			this.copyAddress = false;
		}
	}

	/**
	 * Complete municipality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Municipality> completeMunicipality(String desc) {
		List<Municipality> l = null;
		try {
			l = AddressService.instance().findMunicipalitiesAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(company.getPostalAddress());
		}
	}

	/**
	 * Cpy addresses.
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				addressService.copyFromToAddress(company.getResidentialAddress(), company.getPostalAddress());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		company.getPostalAddress().setSameAddress(copyAddress);
	}

	/**
	 * Gets the sdf company.
	 *
	 * @return the sdf company
	 */
	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	/**
	 * Sets the sdf company.
	 *
	 * @param sdfCompany
	 *            the new sdf company
	 */
	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
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
	 * @param searchUserPassportOrIdUI
	 *            the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * Gets the search company UI.
	 *
	 * @return the search company UI
	 */
	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	/**
	 * Sets the search company UI.
	 *
	 * @param searchCompanyUI
	 *            the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
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
	 * @param residentialAddress
	 *            the new residential address
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
	 * @param postalAddress
	 *            the new postal address
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
	 * @param copyAddress
	 *            the new copy address
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
	 * @param chambers
	 *            the new chambers
	 */
	public void setChambers(List<Chamber> chambers) {
		this.chambers = chambers;
	}

	/**
	 * Gets the sic codes.
	 *
	 * @return the sic codes
	 */
	public List<SICCode> getSicCodes() {
		return sicCodes;
	}

	/**
	 * Sets the sic codes.
	 *
	 * @param sicCodes
	 *            the new sic codes
	 */
	public void setSicCodes(List<SICCode> sicCodes) {
		this.sicCodes = sicCodes;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	public Long getMAX_COMPANY_TRADE_NAME_SIZE() {
		return MAX_COMPANY_TRADE_NAME_SIZE;
	}

	public void setMAX_COMPANY_TRADE_NAME_SIZE(Long mAX_COMPANY_TRADE_NAME_SIZE) {
		MAX_COMPANY_TRADE_NAME_SIZE = mAX_COMPANY_TRADE_NAME_SIZE;
	}

	public Long getMAX_ADDRESS_LINE_SIZE() {
		return MAX_ADDRESS_LINE_SIZE;
	}

	public void setMAX_ADDRESS_LINE_SIZE(Long mAX_ADDRESS_LINE_SIZE) {
		MAX_ADDRESS_LINE_SIZE = mAX_ADDRESS_LINE_SIZE;
	}

	public Long getMAX_ADDRESS_CODE_SIZE() {
		return MAX_ADDRESS_CODE_SIZE;
	}

	public void setMAX_ADDRESS_CODE_SIZE(Long mAX_ADDRESS_CODE_SIZE) {
		MAX_ADDRESS_CODE_SIZE = mAX_ADDRESS_CODE_SIZE;
	}

	public Long getMAX_NUMBER_OF_EMPLOYEES_SIZE() {
		return MAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	public void setMAX_NUMBER_OF_EMPLOYEES_SIZE(Long mAX_NUMBER_OF_EMPLOYEES_SIZE) {
		MAX_NUMBER_OF_EMPLOYEES_SIZE = mAX_NUMBER_OF_EMPLOYEES_SIZE;
	}

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public void setTELPHONE_FORMAT(String tELPHONE_FORMAT) {
		TELPHONE_FORMAT = tELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}
}
