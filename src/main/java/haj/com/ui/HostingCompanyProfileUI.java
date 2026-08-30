package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.HostingCompany;
import haj.com.entity.Images;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.HostingCompanyService;
import haj.com.service.ImagesService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileUI.
 */
@ManagedBean(name = "hostingCompanyProfileUI")
@ViewScoped
public class HostingCompanyProfileUI extends AbstractUI {

	/** The image. */
	private Images image = new Images();
	
	/**  Service. */
	private AddressService addressService = new AddressService();
	
	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/** The hosting company. */
	private HostingCompany hostingCompany = new HostingCompany();
	
	/** The address. */
	private Address address = new Address();
	
	/** The colour themes. */
	private Map<String, String> colourThemes;

	/** The data model. */
	private LazyDataModel<HostingCompany> hostingCompanyDataModel;
	/** The companyfiltered list. */
	private List<HostingCompany> hostingCompanyfilteredList = null;
	
	/** The maximum size all all last names*/
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;
 	
 	/** The maximum size all for company name*/
	private  Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;
 	
 	/** The maximum size all for company name*/
	private  Long MAX_ADDRESS_LINE_SIZE = HAJConstants.MAX_ADDRESS_LINE_SIZE;
 	
 	/** The maximum size all for company name*/
	private  Long MAX_ADDRESS_CODE_SIZE = HAJConstants.MAX_ADDRESS_CODE_SIZE;
 	
 	/** The maximum size all for tax number*/
	private  Long MAX_TAX_NUMBER = HAJConstants.MAX_TAX_NUMBER;
 	
 	/** The maximum size all for vax number*/
	private  Long MAX_VAX_NUMBER = HAJConstants.MAX_VAX_NUMBER;
	
	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;
	
	/** The Constant company levy number format. */
	private  String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;
	
	/** The Constant company vat number format. */
	private  String companyVatNumberFormat = HAJConstants.companyVatNumberFormat;
	
	/** The Constant allow only number format. */
	public  String allowOnlyNumber = HAJConstants.allowOnlyNumber;

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
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepColourThemeUpdate();
		hostingCompanyInfo();
		prepareNewHostingCompany();
	}

	/**
	 * Prep colour theme update.
	 */
	public void prepColourThemeUpdate() {
		try {
			colourThemes = new HashMap<String, String>();
			colourThemes.put("Default-Theme", "rock");
			colourThemes.put("Blue-Grey", "blue-grey");
			colourThemes.put("Brown", "brown");
			colourThemes.put("Cyan", "cyan");
			colourThemes.put("Dark-Blue", "dark-blue");
			colourThemes.put("Dark-Green", "dark-green");
			colourThemes.put("Green", "green");
			colourThemes.put("Grey", "grey");
			colourThemes.put("Indigo", "indigo");
			colourThemes.put("Purple-Amber", "purple-amber");
			colourThemes.put("Purple-Cyan", "purple-cyan");
			colourThemes.put("Teal", "teal");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare address long lat.
	 */
	public void prepareAddressLongLat() {
		addressService.lookupLongitudeLatitude(this.address);
	}

	/**
	 * Prepare new hosting company.
	 */
	public void prepareNewHostingCompany() {
		this.hostingCompany = new HostingCompany();
		this.address = new Address();
	}

	/**
	 * Get all Company for data table.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void hostingCompanyInfo() {
		hostingCompanyDataModel = new LazyDataModel<HostingCompany>() {
			private static final long serialVersionUID = 1L;
			private List<HostingCompany> retorno = new ArrayList<HostingCompany>();

			@Override
			public List<HostingCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = hostingCompanyService.allHostingCompany(HostingCompany.class, first, pageSize, sortField,
							sortOrder, filters);
					hostingCompanyDataModel.setRowCount(hostingCompanyService.count(HostingCompany.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HostingCompany obj) {
				return obj.getId();
			}

			@Override
			public HostingCompany getRowData(String rowKey) {
				for (HostingCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Save profile image.
	 *
	 * @param event
	 *            the event
	 */
	public void saveImage(FileUploadEvent event) {
		try {
			if (this.hostingCompany.getId() != null) {
				ImagesService.saveHostingCompanyProfilePic(this.image, event.getFile(), this.hostingCompany, true);
				addInfoMessage(super.getEntryLanguage("profile.image.upload"));
			} else
			super.addWarningMessage(super.getEntryLanguage("no.hosting.company.for.upload"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update hosting company.
	 */
	public void updateHostingCompany() {
		try {
			hostingCompanyService.create(this.hostingCompany);
			this.address.setHostingCompany(this.hostingCompany);
			addressService.create(this.address);
			hostingCompanyInfo();
			addInfoMessage(super.getEntryLanguage("hosting.company.updated"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update hosting company find address.
	 */
	public void updateHostingCompanyFindAddress() {
		try {
			this.address = new Address();
			this.address = addressService.findByHostingCompanyId(this.hostingCompany.getId());
			if (this.address == null)
				this.address = new Address();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Images getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Images image) {
		this.image = image;
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the colour themes.
	 *
	 * @return the colour themes
	 */
	public Map<String, String> getColourThemes() {
		return colourThemes;
	}

	/**
	 * Sets the colour themes.
	 *
	 * @param colourThemes the colour themes
	 */
	public void setColourThemes(Map<String, String> colourThemes) {
		this.colourThemes = colourThemes;
	}

	/**
	 * Gets the hosting company data model.
	 *
	 * @return the hosting company data model
	 */
	public LazyDataModel<HostingCompany> getHostingCompanyDataModel() {
		return hostingCompanyDataModel;
	}

	/**
	 * Sets the hosting company data model.
	 *
	 * @param hostingCompanyDataModel the new hosting company data model
	 */
	public void setHostingCompanyDataModel(LazyDataModel<HostingCompany> hostingCompanyDataModel) {
		this.hostingCompanyDataModel = hostingCompanyDataModel;
	}

	/**
	 * Gets the hosting companyfiltered list.
	 *
	 * @return the hosting companyfiltered list
	 */
	public List<HostingCompany> getHostingCompanyfilteredList() {
		return hostingCompanyfilteredList;
	}

	/**
	 * Sets the hosting companyfiltered list.
	 *
	 * @param hostingCompanyfilteredList the new hosting companyfiltered list
	 */
	public void setHostingCompanyfilteredList(List<HostingCompany> hostingCompanyfilteredList) {
		this.hostingCompanyfilteredList = hostingCompanyfilteredList;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * View org chart.
	 */
	public void viewOrgChart() {
		try {
			getSessionUI().setHostingCompany(hostingCompany);
			super.ajaxRedirect("/pages/orgchart.jsf");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	public Long getMAX_TAX_NUMBER() {
		return MAX_TAX_NUMBER;
	}

	public void setMAX_TAX_NUMBER(Long mAX_TAX_NUMBER) {
		MAX_TAX_NUMBER = mAX_TAX_NUMBER;
	}

	public Long getMAX_VAX_NUMBER() {
		return MAX_VAX_NUMBER;
	}

	public void setMAX_VAX_NUMBER(Long mAX_VAX_NUMBER) {
		MAX_VAX_NUMBER = mAX_VAX_NUMBER;
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

	public String getCompanyVatNumberFormat() {
		return companyVatNumberFormat;
	}

	public void setCompanyVatNumberFormat(String companyVatNumberFormat) {
		this.companyVatNumberFormat = companyVatNumberFormat;
	}

	public String getAllowOnlyNumber() {
		return allowOnlyNumber;
	}

	public void setAllowOnlyNumber(String allowOnlyNumber) {
		this.allowOnlyNumber = allowOnlyNumber;
	}
	
}
