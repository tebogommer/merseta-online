package haj.com.ui;

import java.util.List;
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
import haj.com.entity.Employees;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeesService;
import haj.com.service.RegisterService;
import haj.com.service.UsersService;
import haj.com.service.lookup.NationalityService;

// TODO: Auto-generated Javadoc
/**
 * The Class SdfUI.
 */
@ManagedBean(name = "selfServiceRegisterUI")
@ViewScoped
public class SelfServiceRegisterUI extends AbstractUI {


	/** The service. */
	private RegisterService registerService = new RegisterService();
	private UsersService usersService = new UsersService();
	private Boolean addSdfToTrainingCommitte;
	/** The form user. */
	private Users formUser;
	/** The passport number. */
	private boolean passportNumber = false;

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

	/** maximum size of first name */
	private Long MAX_MIDDLE_NAME_SIZE = HAJConstants.MAX_MIDDLE_NAME_SIZE;

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
	
	private String validiationExceptionsUser = "";

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
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
		companyInfo();
	}
	
	private void clearInformation() {
		validiationExceptionsUser = "";
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

	/**
	 * Insert SDF into database.
	 *
	 * @author TechFinium
	 * @see Company
	 */
	public void createSDF() {
		// storeClientInfo();
		try {
			clearInformation();
			validiationExceptionsUser = usersService.validiateUserInformation(formUser).toString();
			
			if (validiationExceptionsUser.trim().isEmpty()) {
				
			
			
			if (formUser.getRsaIDNumber() != null && !formUser.getRsaIDNumber().isEmpty()) {
				formUser.setNationality(new NationalityService().findByKey(HAJConstants.SOUTH_AFRICAN_NATIONALITY));
			}
			
			Users myUser = usersService.getUserByEmail(formUser.getEmail());
			if(usersService.getUserByEmail(formUser.getEmail())== null)
			{
				//check if user exist on employees table
				EmployeesService employeesService = new EmployeesService();
				Employees employee = employeesService.findEmployeeByIdOrPassport(this.formUser);
				
				if(employee != null) {
					if(checkIfUsersMatch(this.formUser, employee)) {
						// registers user
						if (formUser != null && formUser.getId() == null) {
							formUser = registerService.register(formUser, true);
						}
						addInfoMessage(super.getEntryLanguage("your.registration.is.being.processed"));
					}else {
						addErrorMessage("Provided User detailes does not match with the employee detailes");
					}
				}else {
					// registers user
					if (formUser != null && formUser.getId() == null) {
						formUser = registerService.register(formUser, true);
					}
					addInfoMessage(super.getEntryLanguage("your.registration.is.being.processed"));
				}
				
			}
			else
			{
				addErrorMessage(super.getEntryLanguage("user.already.email"));
			}
			
			//companyService.createCompanyAndSendTask(companyList, formUser, true, ConfigDocProcessEnum.SDF, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();
			} else {
				addErrorMessage("SETMIS Validiation Exception. Please refer to the information and make the corrections.");
			}
			
			/*if (formUser.getId() != null) storeClientInfo(formUser);
			companyInfo();*/
		}catch (javax.validation.ConstraintViolationException e) {
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

	
	private boolean checkIfUsersMatch(Users user, Employees employee) {
		boolean match = false;
		if(user.getFirstName().equalsIgnoreCase(employee.getFirstName())  && user.getLastName().equalsIgnoreCase(employee.getLastName())) {
			match = true;
		}
		return match;
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
		formUser.setDoneSearch(false);

		if (startFromNew) {
			try {
				//companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null, this.formUser, CompanyUserTypeEnum.User);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
				clearInformation();
				this.formUser = (Users) object;
				if (formUser.getId() != null) {
					addWarningMessage(getEntryLanguage("user.already.exists"));
					formUser = null;
				} else {
					//companyService.prepareNewRegistrationType(ConfigDocProcessEnum.SDF, null, this.formUser, CompanyUserTypeEnum.User);
				}
			} 

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

	public Long getMAX_MIDDLE_NAME_SIZE() {
		return MAX_MIDDLE_NAME_SIZE;
	}

	public void setMAX_MIDDLE_NAME_SIZE(Long mAX_MIDDLE_NAME_SIZE) {
		MAX_MIDDLE_NAME_SIZE = mAX_MIDDLE_NAME_SIZE;
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

	public String getValidiationExceptionsUser() {
		return validiationExceptionsUser;
	}

	public void setValidiationExceptionsUser(String validiationExceptionsUser) {
		this.validiationExceptionsUser = validiationExceptionsUser;
	}


}
