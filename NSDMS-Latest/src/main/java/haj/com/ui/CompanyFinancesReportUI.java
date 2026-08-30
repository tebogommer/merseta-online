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
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.Municipality;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.datamodel.CompanySdfDatamodel;
import haj.com.entity.datamodel.SdfCompanyDataModel;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.SDFType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.ChangeReasonService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.MailDef;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.lookup.SDFTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyUI.
 */
@ManagedBean(name = "companyFinancesReportUI")
@ViewScoped
public class CompanyFinancesReportUI extends AbstractUI {

	/** The service. */
	private SDFCompanyService service = new SDFCompanyService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	/** The training committee service */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	/** The companysdf list. */
	private List<SDFCompany> companysdfList = null;

	/** The companysdffiltered list. */
	private List<SDFCompany> companysdffilteredList = null;

	/** The companysdf. */
	private SDFCompany companysdf = new SDFCompany();

	/** The sdf type. */
	private SDFType sdfType;

	/** The company. */
	private Company company = null;

	/** The data model. */
	private LazyDataModel<SDFCompany> dataModel;

	/** The users data model. */
	private LazyDataModel<SDFCompany> usersDataModel;

	/** The address service. */
	private AddressService addressService = new AddressService();

	/** The copy address. */
	private Boolean copyAddress;

	/** The chambers. */
	private List<Chamber> chambers;

	/** The residential address. */
	private Address residentialAddress;

	/** The postal address. */
	private Address postalAddress;

	/** Boolean to add sdf to training committe */
	private Boolean addSdfToTrainingCommitte;

	/** The new SDF. */
	private Users newSDF;

	/** The sdf types. */
	private List<SDFType> sdfTypes;

	/** The sdf type service. */
	private SDFTypeService sdfTypeService = new SDFTypeService();

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** The maximum size all all first names */
	public Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** The maximum size all all last names */
	public Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** The maximum size all all last names */
	public Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** The maximum size all for fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The Constant telephone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The Constant cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	private boolean saveNewSDF = true;

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
	 * Initialize method to get all CompanySDF and prepare a for a create of a new
	 * CompanySDF.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	private void runInit() throws Exception {
		setObjects();
		companySdfInfo();
	}

	private void companySdfInfo() {
		dataModel = new SdfCompanyDataModel();
	}

	/**
	 * Sets the objects.
	 */
	public void setObjects() {

		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);

	}

	/**
	 * Insert CompanySDF into database.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void companysdfInsert() {
		try {
			service.create(this.companysdf);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanySDF in database.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void companysdfUpdate() {
		try {
			service.update(this.companysdf);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanySDF from database.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void companysdfDelete() {
		try {
			service.delete(this.companysdf);
			prepNewSDF();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createDeleteSDFTask() {
		try {
			this.companysdf.setApprovalStatus(ApprovalEnum.PendingApproval);
			String desc = "An request to delete SDF has been made, please approve";
			service.update(this.companysdf);
			// Creating change reason
			ChangeReasonService.instance().createChangeReason(this.companysdf.getId(), this.companysdf.getClass().getName(), CompanyInfoUI.changeReason);
			TasksService.instance().findFirstInProcessAndCreateTask(desc, getSessionUI().getActiveUser(), this.companysdf.getId(), this.companysdf.getClass().getName(), true, ConfigDocProcessEnum.DELETE_SDF, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			CompanyInfoUI.changeReason = new ChangeReason();

			addInfoMessage("Request has been sent for approval");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Create new instance of CompanySDF.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void prepareNew() {
		companysdf = new SDFCompany();
		this.postalAddress = new Address();
		this.residentialAddress = new Address();
	}

	/**
	 * Prep new SDF.
	 */
	public void prepNewSDF() {
		setObjects();
		addSdfToTrainingCommitte = false;
		newSDF = new Users();

		newSDF.setChgPwdNow(false);
		newSDF.setDoneSearch(false);
		newSDF.setExistingUser(false);
		newSDF.setRegFieldsDone(false);
		sdfType = null;

		usersDataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSDFForCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, companysdf.getCompany(), getSessionUI().getActiveUser());
					usersDataModel.setRowCount(service.allSDFForCompanyCount(filters, companysdf.getCompany(), getSessionUI().getActiveUser()).intValue());

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
		selectItemsSDFType();
	}

	/**
	 * Prep update.
	 */
	public void prepUpdate() {
		try {
			sdfTypes = sdfTypeService.allSDFTypeNotUsedAndNoReplacementSDF(companysdf.getCompany());
			if (sdfType != null) {
				sdfTypes.add(sdfType);
			}
			saveNewSDF = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SDFCompany> complete(String desc) {
		List<SDFCompany> l = null;
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
	 * Save SDF And Create Task.
	 */
	public void saveSDFAndCreatetask() {
		try {
			service.checkByCriteria(newSDF, companysdf.getCompany());
			service.createSecondarySDFAndCreateTask(this.companysdf.getCompany(), newSDF, sdfType, getSessionUI().getActiveUser());

			// check to add SDF to training committee
			if (addSdfToTrainingCommitte) {
				trainingComitteeService.copyUserAndCreateTask(newSDF, companysdf.getCompany(), getSessionUI().getActiveUser());
			}
			addInfoMessage("Sucessfully updated");
			addSdfToTrainingCommitte = false;
		}catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			System.out.println(getValidationErrors());
			addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		prepNewSDF();
	}
	
	/**
	 * Save SDF.
	 */
	public void saveSDF() {
		try {
			service.createSecondarySDF(this.companysdf.getCompany(), newSDF, sdfType, saveNewSDF);
			saveNewSDF = true;
			// check to add SDF to training committee
			if (addSdfToTrainingCommitte) {
				trainingComitteeService.copyUser(newSDF, companysdf.getCompany());
			}
			addInfoMessage("Sucessfully updated");
			addSdfToTrainingCommitte = false;
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		prepNewSDF();
	}

	public void updateSDF() {
		try {

			service.updateSDF(companysdf.getCompany(), newSDF, sdfType);
			addInfoMessage("Sucessfully updated");
			/*
			 * System.out.println("Comp ID:"+companysdf.getCompany().getId());
			 * System.out.println("newSDF ID:"+newSDF.getId());
			 * System.out.println("sdfType ID:"+sdfType.getId());
			 * System.out.println("---------------------------------------------------");
			 * //SDFCompany sdfCompany = new SDFCompany(this.companysdf.getCompany(),
			 * newSDF, sdfType);
			 * 
			 * SDFCompany
			 * sdfCompany=service.findByCompanyAndUser(this.companysdf.getCompany(),
			 * newSDF); sdfCompany.setSdf(newSDF); sdfCompany.setSdfType(sdfType);
			 */

			// service.update(sdfCompany);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the company SDF list.
	 *
	 * @return the company SDF list
	 */
	public List<SDFCompany> getCompanySDFList() {
		return companysdfList;
	}

	/**
	 * Sets the company SDF list.
	 *
	 * @param companysdfList
	 *            the new company SDF list
	 */
	public void setCompanySDFList(List<SDFCompany> companysdfList) {
		this.companysdfList = companysdfList;
	}

	/**
	 * Gets the companysdf.
	 *
	 * @return the companysdf
	 */
	public SDFCompany getCompanysdf() {
		return companysdf;
	}

	/**
	 * Sets the companysdf.
	 *
	 * @param companysdf
	 *            the new companysdf
	 */
	public void setCompanysdf(SDFCompany companysdf) {
		this.companysdf = companysdf;
	}

	/**
	 * Gets the company SD ffiltered list.
	 *
	 * @return the company SD ffiltered list
	 */
	public List<SDFCompany> getCompanySDFfilteredList() {
		return companysdffilteredList;
	}

	/**
	 * Resolve addresses.
	 */
	public void resolveAddresses() {
		this.residentialAddress = this.companysdf.getCompany().getResidentialAddress();
		this.postalAddress = this.companysdf.getCompany().getPostalAddress();
		if (this.postalAddress != null && this.postalAddress.getSameAddress() != null) {
			this.copyAddress = this.postalAddress.getSameAddress();
		} else {
			this.copyAddress = false;
		}
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
	 * Complete municipality.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Municipality> completeMunicipality(String desc) {
		List<Municipality> l = null;
		try {
			l = addressService.findMunicipalitiesAutocomplete(desc);
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
			addressService.clearAddress(postalAddress);
		}
	}

	/**
	 * Select items SDF type.
	 */
	public void selectItemsSDFType() {

		try {
			sdfTypes = sdfTypeService.allSDFTypeNotUsedAndNoReplacementSDF(companysdf.getCompany());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractUIInterface#callBackMethod(java.lang.Object)
	 */
	@Override
	public void callBackMethod(Object object) {
		selectItemsSDFType();
		if (object instanceof Users) {
			this.newSDF = (Users) object;
			try {
				if (newSDF.getId() != null) {
					if (hostingCompanyEmployeesService.findByUserCount(newSDF.getId(), HAJConstants.HOSTING_MERSETA) > 0) {
						addWarningMessage("merSETA employees cannot be an employee or labour SDF");
					} else if (service.findByCompanyAndUser(companysdf.getCompany(), newSDF) != null) {
						addWarningMessage("User already exists as an sdf for the company.");
						prepNewSDF();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param companysdffilteredList
	 *            the new companysdffilteredList list
	 * @see SDFCompany
	 */
	public void setCompanySDFfilteredList(List<SDFCompany> companysdffilteredList) {
		this.companysdffilteredList = companysdffilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SDFCompany> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<SDFCompany> dataModel) {
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

	/**
	 * Gets the companysdf list.
	 *
	 * @return the companysdf list
	 */
	public List<SDFCompany> getCompanysdfList() {
		return companysdfList;
	}

	/**
	 * Sets the companysdf list.
	 *
	 * @param companysdfList
	 *            the new companysdf list
	 */
	public void setCompanysdfList(List<SDFCompany> companysdfList) {
		this.companysdfList = companysdfList;
	}

	/**
	 * Gets the companysdffiltered list.
	 *
	 * @return the companysdffiltered list
	 */
	public List<SDFCompany> getCompanysdffilteredList() {
		return companysdffilteredList;
	}

	/**
	 * Sets the companysdffiltered list.
	 *
	 * @param companysdffilteredList
	 *            the new companysdffiltered list
	 */
	public void setCompanysdffilteredList(List<SDFCompany> companysdffilteredList) {
		this.companysdffilteredList = companysdffilteredList;
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
	 * Gets the new SDF.
	 *
	 * @return the new SDF
	 */
	public Users getNewSDF() {
		return newSDF;
	}

	/**
	 * Sets the new SDF.
	 *
	 * @param newSDF
	 *            the new new SDF
	 */
	public void setNewSDF(Users newSDF) {
		this.newSDF = newSDF;
	}

	/**
	 * Gets the users data model.
	 *
	 * @return the users data model
	 */
	public LazyDataModel<SDFCompany> getUsersDataModel() {
		return usersDataModel;
	}

	/**
	 * Sets the users data model.
	 *
	 * @param usersDataModel
	 *            the new users data model
	 */
	public void setUsersDataModel(LazyDataModel<SDFCompany> usersDataModel) {

		this.usersDataModel = usersDataModel;
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
	 * Gets the sdf types.
	 *
	 * @return the sdf types
	 */
	public List<SDFType> getSdfTypes() {
		return sdfTypes;
	}

	/**
	 * Sets the sdf types.
	 *
	 * @param sdfTypes
	 *            the new sdf types
	 */
	public void setSdfTypes(List<SDFType> sdfTypes) {
		this.sdfTypes = sdfTypes;
	}

	/**
	 * Gets the sdf type.
	 *
	 * @return the sdf type
	 */
	public SDFType getSdfType() {
		return sdfType;
	}

	/**
	 * Sets the sdf type.
	 *
	 * @param sdfType
	 *            the new sdf type
	 */
	public void setSdfType(SDFType sdfType) {
		this.sdfType = sdfType;
	}

	public Boolean getAddSdfToTrainingCommitte() {
		return addSdfToTrainingCommitte;
	}

	public void setAddSdfToTrainingCommitte(Boolean addSdfToTrainingCommitte) {
		this.addSdfToTrainingCommitte = addSdfToTrainingCommitte;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

	public Long getMAX_LAST_NAME_SIZE() {
		return MAX_LAST_NAME_SIZE;
	}

	public void setMAX_LAST_NAME_SIZE(Long mAX_LAST_NAME_SIZE) {
		MAX_LAST_NAME_SIZE = mAX_LAST_NAME_SIZE;
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
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

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

}
