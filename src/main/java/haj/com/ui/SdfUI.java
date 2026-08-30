package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.RsaCitizenTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyService;
import haj.com.service.SDFCompanyService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.UsersService;
import haj.com.service.lookup.NationalityService;

// TODO: Auto-generated Javadoc
/**
 * The Class SdfUI.
 */
@ManagedBean(name = "sdfUI")
@ViewScoped
public class SdfUI extends AbstractUI {

	/** The service. */
	private UsersService service = new UsersService();

	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	private Boolean addSdfToTrainingCommitte;

	/** The company list. */
	private List<Company> companyList = null;

	/** The company. */
	private Company company;

	/** The form user. */
	private Users formUser;

	/** The passport number. */
	private boolean passportNumber = false;

	/** The company service. */
	private CompanyService companyService = new CompanyService(getResourceBundle());
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private HostingCompanyEmployeesService companyEmployeesService = new HostingCompanyEmployeesService();

	/** The doc. */
	private Doc doc;

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	private Long maxFileSize = HAJConstants.MAX_FILE_SIZE;

	/** maximum size of first name */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** maximum size of last name */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** maximum size of email */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** Telephone number format */
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** Cell number format */
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** maximum size of fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	private boolean startFromNew;

	private SDFCompany previousCompany;

	private UsersService usersService = new UsersService();

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
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	private void runInit() throws Exception {
		startFromNew = super.getParameter("startFromNew", true) != null;
		if (getSessionUI().getActiveUser() != null) {
			this.formUser = getSessionUI().getActiveUser();
		}
		previousCompany = (SDFCompany) super.getParameter("previousCompany", true);
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
		companyInfo();
	}

	/**
	 * Get all Company for data table.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void companyInfo() throws Exception {

	}

	
	private String validiationErrors = "";
	/**
	 * Insert SDF into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void createSDF() {
		// storeClientInfo();
		try {
			validiationErrors = "";
			if (formUser.getRsaIDNumber() != null && !formUser.getRsaIDNumber().isEmpty()
					&& formUser.getRsaCitizenTypeEnum() != null
					&& formUser.getRsaCitizenTypeEnum() == RsaCitizenTypeEnum.RsaCitizen) {
				formUser.setNationality(new NationalityService().findByKey(HAJConstants.SOUTH_AFRICAN_NATIONALITY));
			}
			// Validations required USER#1, COMPANY#1
			companyService.createCompanyAndSendTask(companyList, formUser, true, ConfigDocProcessEnum.SDF,
					hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("your.registration.is.being.processed"));
			if (formUser.getId() != null)
				storeClientInfo(formUser);
			companyInfo();
		} catch (javax.validation.ConstraintViolationException e) {
			validiationErrors = extractValidationErrorsReturnString(e);
			addErrorMessage("Validiation Exception, please refer to the error messages.");
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createCompany() throws Exception {
		try {
			
			if (company != null) {
//				companyService.validateRegistartionNumberByCompany(company, company.getCompanyRegistrationNumber());
			}
			if (company.getPayeSDLNumber() != null && !company.getPayeSDLNumber().trim().isEmpty()) {
				companyService.validateLevyNumberByCompany(company, company.getPayeSDLNumber());
			}
			
			if (Boolean.TRUE.equals(companyErrors(company) == null)) {
//				companyValidiation(company);
				storeClientInfo();
				isDocUploaded(company);
				if (!startFromNew) {
					companyService.createCompanyAndSendTask(1, company, getSessionUI().getActiveUser(), true,
							ConfigDocProcessEnum.SDF);
				} else {
					getSessionUI().getActiveUser().setDocs(this.formUser.getDocs());
					companyService.preUserRegisterChecks(getSessionUI().getActiveUser());
					companyList = new ArrayList<>();
					companyList.add(company);
					companyService.createCompanyAndSendTask(companyList, getSessionUI().getActiveUser(), true,
							ConfigDocProcessEnum.SDF, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA),
							null, null);
				}
				if (this.previousCompany != null) {
					Company c = this.previousCompany.getCompany();
					c.setCompanyStatus(CompanyStatusEnum.PendingReplacement);
					company.setPreviousCompany(c);
					companyService.update(c);
					companyService.update(company);
				}
				prepareNew();
				addInfoMessage("Please go to your dashboard to continue capturing company information");
				companyInfo();

			} else {
				addErrorMessage("Please correct Company Information where applicable");
			}
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void companyValidiation(Company company) throws Exception {
		if (company != null && company.getId() == null) {
			companyService.regNumberValidiation(company);
		}
	}

	/**
	 * Update SDF in database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void sdfUpdate() {
		try {
			service.update(this.formUser);
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
	 * Delete SDF from database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void sdfDelete() {
		try {
			service.delete(this.formUser);
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
	 * Create new instance of SDF.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void prepareNew() {
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
		formUser.setAcceptPopi(true);

		if (startFromNew) {
			try {
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null, this.formUser,
						CompanyUserTypeEnum.User);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.companyList = new ArrayList<>();
		prepareNewCompany();
	}

	/**
	 * Prepare new company.
	 */
	public void prepareNewCompany() {
		this.company = new Company();
		this.company.setDoneSearch(false);
		this.company.setTempUpdate(false);
		this.company.setCompanyType(CompanyTypeEnum.SDF);
	}

	/**
	 * Adds the company to list.
	 */
	public void addCompanyToList() {
		try {

//			companyService.validateRegistartionNumberByCompany(company, company.getCompanyRegistrationNumber());

			if (company.getPayeSDLNumber() != null && !company.getPayeSDLNumber().trim().isEmpty()) {
				companyService.validateLevyNumberByCompany(company, company.getPayeSDLNumber());
			}
			if (Boolean.TRUE.equals(companyErrors(company) == null)) {
				if (this.formUser.getId() != null && this.company.getId() != null) {
					companyService.checkIfAlreadyRegistered(this.formUser, this.company);
				}

				if (company != null && company.getId() == null) {
					// run company validiation here
//					companyService.regNumberValidiation(company);
					// check to ensure that levy number companies are set to
					// levy paying entities
					if (company.getLevyNumber() != null && company.getLevyNumber().trim().contains("N") && company.getNonLevyPaying() != null && !company.getNonLevyPaying()) {
						company.setNonLevyPaying(false);
					}
				}

				// check to ensure L is not just entered for company
				if (company != null && company.getPayeSDLNumber() != null && company.getPayeSDLNumber().trim().equals("L")) {
					company.setPayeSDLNumber(null);
				}

				List<Company> listTemp = companyList.stream().filter(cmp -> validateCompaniesNoID(cmp))
						.collect(Collectors.toList());
				if (listTemp == null || listTemp.size() == 0) {
					// Checking if user uploaded required documents
					isDocUploaded(company);
					this.companyList.add(company);
					prepareNewCompany();
				} else {
					addErrorMessage(getEntryLanguage("registration.in.list", company.getCompanyRegistrationNumber()));
				}
			} else {
				addErrorMessage("Please correct Company Information where applicable");
			}

		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
			prepareNewCompany();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Check if required documents for company are uploaded
	 */
	public void isDocUploaded(Company theCompany) throws Exception {
		String err = "";
		if (theCompany.getDocs() != null) {
			for (Doc doc : theCompany.getDocs()) {
				if(doc.getId() != null){
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {

						err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName()
								+ "</i><br/>";
						throw new Exception(err);
					}
				}
			}
		} else {
			err = "Please uploaded all required documents";
			throw new Exception(err);
		}

	}

	public void continueRegistration() {
		// Checking if user uploaded required document(s)
		try {
			if (Boolean.TRUE.equals(userErrors(formUser) == null)) {
				companyService.preUserRegisterChecks(formUser);
				service.checkEmailUsedEmailOnly(formUser.getEmail());
				doneUserBit();
			} else {
				formUser.setRegFieldsDone(false);
				addErrorMessage("Please correct User Information where applicable");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Validate companies no ID.
	 *
	 * @param cmp
	 *            the cmp
	 * @return true, if successful
	 */
	private boolean validateCompaniesNoID(Company cmp) {
		return cmp.getCompanyRegistrationNumber().equals(company.getCompanyRegistrationNumber());

	}

	/**
	 * Removes the company from list.
	 */
	public void removeCompanyFromList() {
		companyList = companyList.stream().filter(cmp -> !validateCompaniesNoID(cmp)).collect(Collectors.toList());
		prepareNewCompany();
	}

	/**
	 * Reset search.
	 */
	public void resetSearch() {
		this.company.setDoneSearch(true);
		this.company.setTempUpdate(true);
	}

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		doc.setCompany(company);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");

	}

	/**
	 * Store file user.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFileUser(FileUploadEvent event) {
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUploadUser').hide()");
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
		// try {
		// l = service.findByName(desc);
		// } catch (ValidationException e) {
		// addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		// } catch (Exception e) {
		// addErrorMessage(e.getMessage(), e);
		// }
		return l;
	}

	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public String userErrors(Users user) {
		if (user != null) {
			String errors = usersService.validiateUserInformation(user).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
	}

	public String companyErrors(Company company) {
		if (company != null) {
			String errors = companyService.validiateCompanyInformation(company).toString();
			if (Boolean.FALSE.equals(errors.isEmpty())) {
				return errors;
			}
		}
		return null;
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
	 * @param companyList
	 *            the new company list
	 */
	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
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
	 * @param formUser
	 *            the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Checks if is passport number.
	 *
	 * @return true, if is passport number
	 */
	public boolean isPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber
	 *            the new passport number
	 */
	public void setPassportNumber(boolean passportNumber) {
		this.passportNumber = passportNumber;
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
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				if (formUser.getId() != null) {
					if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) {
						formUser = null;
						throw new Exception("Employees cannot register companies or register as an SDF.");
					} else {
						companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null, this.formUser,
								CompanyUserTypeEnum.User);
					}

				} else {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null, this.formUser,
							CompanyUserTypeEnum.User);
				}
			} else if (object instanceof Company) {
				this.company = (Company) object;
				if (company.getId() == null && !company.getNonLevyPaying()) {
					addWarningMessage(getEntryLanguage("not.merseta.company"));
					this.company = null;
				} else {
					if (company.getCompanyStatus() != null
							&& company.getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
						addWarningMessage(getEntryLanguage("not.merseta.company"));
						this.company = null;
					} else if (sdfCompanyService.findByCompanyCountPrimary(company) == 0
							|| (company.getCompanyStatus() != null
									&& company.getCompanyStatus() == CompanyStatusEnum.Rejected)) {
						// version two
						companyService.prepareNewRegistrationTypeRegistartionCertificate(ConfigDocProcessEnum.SDF,
								this.company, null, CompanyUserTypeEnum.Company);
						// version one
						// companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF,
						// this.company, null, CompanyUserTypeEnum.Company);
					} else {
						addWarningMessage("Company already registered on the MIS");
						this.company = null;
					}
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onOrganisationTypeSelection() {
		try {
			companyService.prepareNewRegistrationTypeRegistartionCertificate(ConfigDocProcessEnum.SDF, this.company,
					null, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * On tab change.
	 *
	 * @param event
	 *            the event
	 */
	public void onTabChange(TabChangeEvent event) {

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
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc
	 *            the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Prep doc.
	 *
	 * @param index
	 *            the index
	 */
	public void prepDoc(int index) {
		this.doc = this.company.getDocs().get(index);
	}

	/**
	 * Done user bit.
	 */
	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
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

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public Long getMAX_COMPANY_NAME_SIZE() {
		return MAX_COMPANY_NAME_SIZE;
	}

	public void setMAX_COMPANY_NAME_SIZE(Long mAX_COMPANY_NAME_SIZE) {
		MAX_COMPANY_NAME_SIZE = mAX_COMPANY_NAME_SIZE;
	}

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	public Boolean getAddSdfToTrainingCommitte() {
		return addSdfToTrainingCommitte;
	}

	public void setAddSdfToTrainingCommitte(Boolean addSdfToTrainingCommitte) {
		this.addSdfToTrainingCommitte = addSdfToTrainingCommitte;
	}

	public SDFCompany getPreviousCompany() {
		return previousCompany;
	}

	public void setPreviousCompany(SDFCompany previousCompany) {
		this.previousCompany = previousCompany;
	}

	public String getValidiationErrors() {
		return validiationErrors;
	}

	public void setValidiationErrors(String validiationErrors) {
		this.validiationErrors = validiationErrors;
	}

}
