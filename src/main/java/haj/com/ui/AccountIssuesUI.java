package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyService;
import haj.com.service.MailDef;
import haj.com.service.RegisterService;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingProviderUI.
 */
@ManagedBean(name = "accountissuesUI")
@ViewScoped
public class AccountIssuesUI extends AbstractUI {

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	private Company company;

	private Users user;

	private Users formUser;

	private SDFCompany companysdf;

	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	private CompanyService companyService = new CompanyService(getResourceBundle());
	
	private RegisterService registerService = new RegisterService();

	private UsersService service = new UsersService();

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

	private ChangeReason changeReason = new ChangeReason();
	private Doc doc;

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
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				if (formUser != null) {
					this.user = (Users) object;
					if (user.getId() == null) {
						addWarningMessage("User is not registered on the NSDMS");
						this.user = null;
					} else {
						companysdf = sdfCompanyService.findByCompanyAndUser(company, user);
						if (companysdf == null) {
							addWarningMessage("User is currently not an SDF for the selected company.");
							this.user = null;							
						}
					}
				} else {
					this.formUser = (Users) object;
				}
			} else if (object instanceof Company) {
				this.company = (Company) object;
				if (this.company.getId() == null) {
					addWarningMessage(getEntryLanguage("not.merseta.company"));
					this.company = null;
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createDeleteSDFTask() {
		try {

			if (formUser.getId() == null) {
				registerService.register(formUser, true);
			}
			this.companysdf.setApprovalStatus(ApprovalEnum.PendingApproval);
			String desc = "A request to delete SDF has been made, please approve";
			sdfCompanyService.update(this.companysdf);
			// Creating change reason
			ChangeReasonService.instance().createChangeReason(this.companysdf.getId(), this.companysdf.getClass().getName(), changeReason);
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, this.companysdf.getId(), this.companysdf.getClass().getName(), true, ConfigDocProcessEnum.DELETE_SDF, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			changeReason = new ChangeReason();
			addInfoMessage("Request has been sent for approval");
			
			this.company = null;
			this.user = null;
			this.formUser = null;
			clearChangeReason();
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void prepChangeDoc() {
		clearChangeReason();
		doc = new Doc();
		doc.setChangeReason(changeReason);
	}

	private void clearChangeReason() {
		changeReason = new ChangeReason();
	}

	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(formUser);
			changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void continueRegistration() {
		// Checking if user uploaded required document(s)
		try {
			companyService.preUserRegisterChecks(formUser);
			service.checkEmailUsedEmailOnly(formUser.getEmail());
			doneUserBit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Users getFormUser() {
		return formUser;
	}

	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	public SDFCompany getCompanysdf() {
		return companysdf;
	}

	public void setCompanysdf(SDFCompany companysdf) {
		this.companysdf = companysdf;
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

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public ChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(ChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
