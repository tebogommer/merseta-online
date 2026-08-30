package haj.com.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompany;
import haj.com.entity.InterSetaTransfer;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyService;
import haj.com.service.InterSetaTransferService;
import haj.com.service.JasperService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "interSetaTransferUI")
@ViewScoped
public class InterSetaTransferUI extends AbstractUI {

	private CompanyService companyService = new CompanyService(getResourceBundle());
	private InterSetaTransfer interSetaTransfer;
	private InterSetaTransferService service = new InterSetaTransferService();
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private Users formUser;
	private Company company;
	private boolean companyOut = false;

	/** maximum file size */
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

	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	/** maximum size of fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for company name */
	private Long MAX_COMPANY_NAME_SIZE = HAJConstants.MAX_COMPANY_NAME_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;
	/** The doc. */
	private Doc doc;

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

	private void runInit() throws Exception {
		if (!(getSessionUI().getActiveUser() == null)) {
			getPassedCompanyDetailsSearch();
			getSessionUserSearch(getSessionUI().getActiveUser());
		} else {
			getSearchUserPassportOrIdUI().setObject(this);
			getSearchCompanyUI().setObject(this);
			prepareNew();
		}
	}

	private void getPassedCompanyDetailsSearch() throws Exception {
		interSetaTransfer = new InterSetaTransfer();
		if (super.getParameter("id", false) != null) {
			this.company = companyService.findByGUID((String) super.getParameter("id", false));
			if (company == null) {
				company = new Company();
				company.setExistingCompany(false);
			} else {
				company.setExistingCompany(true);
			}
			company.setNonLevyPaying(false);
			company.setDoneSearch(true);
			companyOut = true;

		} else {
			throw new Exception("Null company details");
		}
	}

	private void getSessionUserSearch(Users activeUser) throws Exception {
		if (activeUser.getId() != null) {
			formUser = service.sessionUserSearch(activeUser);
		}
	}

	private void prepareNew() {
		interSetaTransfer = new InterSetaTransfer();
		company = null;
		formUser = null;
	}

	public void saveTransfer() {
		try {
			companyService.createCompanyAndSendTask(interSetaTransfer, company, formUser, true,
					ConfigDocProcessEnum.INTER_SETA_TRANSFER,
					hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("your.registration.is.being.processed"));
			super.runClientSideUpdate("intersetaForm");
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	


	public void saveTransferOut() {
		try {
			//TODO- sort out this methode
			companyService.sendTaskOut(interSetaTransfer, company, formUser, true,ConfigDocProcessEnum.INTER_SETA_TRANSFER,
					hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), null, null);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("your.transfer.is.being.processed"));
			super.runClientSideUpdate("intersetaForm");
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
	}

	public void continueReg() {
		try {
			companyService.preUserRegisterChecks(formUser);
			doneUserBit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
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

	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.INTER_SETA_TRANSFER, null, this.formUser,
						CompanyUserTypeEnum.User);
			} else if (object instanceof Company) {
				this.company = (Company) object;
				if (company.getId() != null) {
					addWarningMessage(getEntryLanguage("company.exists"));
					this.company = null;
				} else {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.INTER_SETA_TRANSFER, this.company,
							null, CompanyUserTypeEnum.Company);
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		doc.setCompany(company);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");
	}

	public void downloadReport() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("company_id", interSetaTransfer.getId());
		try {
			JasperService.addLogo(params);
			JasperService.instance().createReportFromDBtoServletOutputStream(
					"Application_By_An_Employer_For_Inter_Seta_Transfer.jasper", params, "Sign_Off.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public InterSetaTransfer getInterSetaTransfer() {
		return interSetaTransfer;
	}

	public void setInterSetaTransfer(InterSetaTransfer interSetaTransfer) {
		this.interSetaTransfer = interSetaTransfer;
	}

	public Users getFormUser() {
		return formUser;
	}

	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
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

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
	}

	public boolean isCompanyOut() {
		return companyOut;
	}

	public void setCompanyOut(boolean companyOut) {
		this.companyOut = companyOut;
	}
}
