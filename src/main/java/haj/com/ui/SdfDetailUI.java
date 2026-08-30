package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Municipality;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.OrganisationType;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SICCode;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyService;
import haj.com.service.JasperService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.ChamberService;
import haj.com.service.lookup.OrganisationTypeService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SICCodeService;
import haj.com.utils.ListUtils;

/**
 * The Class SdfDetailUI.
 */
@ManagedBean(name = "sdfDetailUI")
@ViewScoped
public class SdfDetailUI extends AbstractUI {

	/** The service. */
	private SDFCompanyService service = new SDFCompanyService();
	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
	private String additionalInformation;

	/** The details. */
	private List<SDFCompany> details;

	/** The sdf. */
	private Users sdf;

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The companysdf. */
	private SDFCompany companysdf = null;

	/** The copy address. */
	private Boolean copyAddress;

	/** The chamber service. */
	private ChamberService chamberService = new ChamberService();

	/** The chambers. */
	private List<Chamber> chambers;

	/** the Organisation Type */
	private List<OrganisationType> organisationTypeList;

	/** the Organisation Type Service */
	private OrganisationTypeService organisationTypeService = new OrganisationTypeService();

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The sic code service. */
	private SICCodeService sicCodeService = new SICCodeService();

	/** The sic codes. */
	private List<SICCode> sicCodes;

	/** The exceptions. */
	private String exceptions;

	/** The doc. */
	private Doc doc;

	/** The js. */
	private JasperService js = new JasperService();

	/** The doc upload user. */
	private Boolean docUploadUser;

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

	/** The maximum size all for tax number */
	private Long MAX_TAX_NUMBER = HAJConstants.MAX_TAX_NUMBER;

	/** The maximum size all for vax number */
	private Long MAX_VAX_NUMBER = HAJConstants.MAX_VAX_NUMBER;

	/** The maximum size all for fax number */
	private Long MAX_FAX_NUMBER = HAJConstants.MAX_FAX_NUMBER;

	/** The maximum size all for fax number */
	private Long MAX_NUMBER_OF_EMPLOYEES_SIZE = HAJConstants.MAX_NUMBER_OF_EMPLOYEES_SIZE;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;

	/** The Constant company levy number format. */
	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** The Constant company vat number format. */
	private String companyVatNumberFormat = HAJConstants.companyVatNumberFormat;

	/** The Constant allow only number format. */
	private String allowOnlyNumber = HAJConstants.allowOnlyNumber;

	/** The Constant telephone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The Constant cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	/** The Constant FAX number format. */
	public String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

	private RejectReasons rejectReason;

	private List<RejectReasons> selectedRejectReason;

	private List<RejectReasonsChild> rejectReasonsChild;

	private Company replacementCompany;
	
	private String setmisValidationErrors = "";

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
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask().getTargetClass().equals(SDFCompany.class.getName())) {
				this.details = new ArrayList<>();
				companysdf = service.findByKey(getSessionUI().getTask().getTargetKey());
				this.sdf = companysdf.getSdf();
				details.add(companysdf);
				details = service.resolveEverything(details);
				if (companysdf.getSdf().getApprovedDate() == null) {
					if (!this.details.isEmpty()) {
						this.sdf = this.details.get(0).getSdf();
						usersService.resolveDocs(this.sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
					} else {
						this.sdf = usersService.findByKey(getSessionUI().getTask().getTargetKey());
						usersService.resolveDocs(this.sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
					}
				}
				this.replacementCompany = companysdf.getCompany();
				companysdf = null;
			} else {
				this.details = service.bySDFPrimary(getSessionUI().getTask().getTargetKey());
				if (!this.details.isEmpty()) {
					this.sdf = this.details.get(0).getSdf();
					usersService.resolveDocs(this.sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
				} else {
					this.sdf = usersService.findByKey(getSessionUI().getTask().getTargetKey());
					usersService.resolveDocs(this.sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
				}
			}
			rejectReasonsChild = rejectReasonsService.findBySDF(sdf);
		}
		if (getSessionUI().getTask() != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
		}
		chambers = chamberService.allChamber();
		organisationTypeList = organisationTypeService.allOrganisationType();
		sicCodes = sicCodeService.allSICCode();
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SDF);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void clearViews() {
		this.companysdf = null;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public List<SDFCompany> getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details
	 *            the new details
	 */
	public void setDetails(List<SDFCompany> details) {
		this.details = details;
	}

	/**
	 * Gets the sdf.
	 *
	 * @return the sdf
	 */
	public Users getSdf() {
		return sdf;
	}

	/**
	 * Sets the sdf.
	 *
	 * @param sdf
	 *            the new sdf
	 */
	public void setSdf(Users sdf) {
		this.sdf = sdf;
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
	 * Resolve addresses.
	 */
	public void resolveAddresses() {

		if (this.companysdf.getCompany().getPostalAddress() != null && this.companysdf.getCompany().getPostalAddress().getSameAddress() != null) {
			this.copyAddress = this.companysdf.getCompany().getPostalAddress().getSameAddress();
		} else {
			this.copyAddress = false;
		}
	}

	/**
	 * Prep new linked company.
	 */
	public void prepNewLinkedCompany() {

	}

	/**
	 * Cpy addresses.
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				AddressService.instance().copyFromToAddress(companysdf.getCompany().getResidentialAddress(), companysdf.getCompany().getPostalAddress());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		companysdf.getCompany().getPostalAddress().setSameAddress(copyAddress);

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

	public void usersUpdate() {
		try {
			usersService.create(this.sdf);
			super.addInfoMessage(super.getEntryLanguage("update.successful"));
			this.sdf = usersService.resolveDocs(usersService.findByKey(sdf.getId()), ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
			if (this.getSessionUI().getUser().equals(sdf)) {
				getSessionUI().setUser(this.sdf);
			}
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(companysdf.getCompany().getPostalAddress());
		}
	}

	/**
	 * Update company.
	 */
	public void updateCompany() {
		try {
			// clear setmis validiations on submit
			clearValidiationErrors();
			cpyAddresses();
			service.save(companysdf.getCompany());
			service.create(companysdf);
			if (getSessionUI().getTask().getTargetClass().equals(SDFCompany.class.getName())) {
				this.details = new ArrayList<>();
				companysdf = service.findByKey(getSessionUI().getTask().getTargetKey());
				details.add(companysdf);
				details = service.resolveEverything(details);
			} else {
				// this.details = service.bySDFPrimary(getSessionUI().getTask().getTargetKey());
				this.details = service.bySDFPrimary(getSessionUI().getTask().getTargetKey());
			}
			clearCompany();
		} catch (javax.validation.ConstraintViolationException e) { 
//			extractValidationErrors(e);
			setmisValidationErrors = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clearValidiationErrors(){
		try {
			// clear setmis validiations on submit
			setmisValidationErrors = "";
			getSessionUI().setValidationErrors(null);
			runClientSideUpdate("validationErrorForm");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Find details.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findDetails() {
		try {
			if (getSessionUI().getTask().getTargetClass().equals(SDFCompany.class.getName())) {
				this.details = new ArrayList<>();
				companysdf = service.findByKey(getSessionUI().getTask().getTargetKey());
				details.add(companysdf);
				details = service.resolveEverything(details);
				companysdf = null;
			} else {
				// this.details = service.bySDFPrimary(getSessionUI().getTask().getTargetKey());
				this.details = service.bySDFPrimary(getSessionUI().getTask().getTargetKey());
			}
		} catch (Exception e) {

			logger.fatal(e.getMessage());
		}
	}

	/**
	 * Clear company.
	 */
	public void clearCompany() {
		this.companysdf = null;
		clearValidiationErrors();
	}

	/**
	 * Prep for update.
	 */
	public void prepForUpdate() {
		if (companysdf.getCompany().getResidentialAddress() == null) {
			this.companysdf.getCompany().setResidentialAddress(new Address(companysdf.getCompany()));
		}
		if (companysdf.getCompany().getPostalAddress() == null) {
			this.companysdf.getCompany().setPostalAddress(new Address(companysdf.getCompany()));
		}
		resolveAddresses();
	}

	/**
	 * Complete task.
	 */
	public void completeTask() {
		try {
			storeClientInfo();
			if (getSessionUI().getTask().getProcessRole() == null) {
				service.completeToFirst(sdf, getSessionUI().getTask());
			} else {
				service.completeTask(this.sdf, getSessionUI().getTask(), getSessionUI().getActiveUser(), details);
			}
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			this.exceptions = e.getMessage();
			logger.fatal(e.getMessage());
		}
	}

	/**
	 * Approve task.
	 */
	public void approveTask() {
		try {
			storeClientInfo();
			service.approveTask(this.sdf, getSessionUI().getTask(), getSessionUI().getActiveUser(), details);
			getSessionUI().setTask(null);

			super.ajaxRedirectToDashboard();

		} catch (Exception e) {

			logger.fatal(e.getMessage());
			e.printStackTrace();
			this.exceptions = e.getMessage();
		}

	}

	/**
	 * Reject task.
	 */
	public void rejectTask() {

		try {
			storeClientInfo();
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			service.rejectTask(this.sdf, getSessionUI().getTask(), getSessionUI().getActiveUser());

			if (getSessionUI().getTask().getTargetClass().equals(SDFCompany.class.getName())) {
				companysdf = service.findByKey(getSessionUI().getTask().getTargetKey());
				rejectReasonsService.createSDFReject(selectedRejectReason, companysdf, getSessionUI().getTask(), additionalInformation);
				companysdf = null;
			} else {
				rejectReasonsService.createSDFReject(selectedRejectReason, sdf, getSessionUI().getTask(), additionalInformation);
			}

			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {

			logger.fatal(e.getMessage());
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prep upload close company information.
	 */
	public void prepUploadCloseCompanyInformation() {
		docUploadUser = false;

		if (companysdf != null && companysdf.getCompany() != null) companysdf.getCompany().setExpanded(true);

		if (companysdf != null && companysdf.getCompany() != null && companysdf.getCompany().getLinkedCompany() != null) {

			List<SDFCompany> a = ListUtils.filterByField("company", companysdf.getCompany().getLinkedCompany(), details);
			for (SDFCompany sdfCompany : a) {
				sdfCompany.getCompany().setExpanded(true);
			}
		}
	}

	/**
	 * Prep upload close company information for user.
	 */
	public void prepUploadCloseCompanyInformationForUser() {
		docUploadUser = true;
	}

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				if (doc.getId() == null) {
					// new document
					if (!docUploadUser) {
						// if document is for company
						doc.setCompany(companysdf.getCompany());
						companyService.documentUpload(doc.getCompany(), getSessionUI().getActiveUser());
					} else {
						// if document is for user
						doc.setCompany(null);
						doc.setForUsers(sdf);
						usersService.documentUpload(sdf, getSessionUI().getActiveUser());
					}
				}
			} else {
				if (!docUploadUser) {
					// if document is for company
					companyService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				} else {
					// if document is for user
					usersService.uploadNewVersion(doc, getSessionUI().getActiveUser());
				}
			}
			if (docUploadUser) {
				usersService.resolveDocs(this.sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
				super.runClientSideUpdate("mainForm:sdfTabView");
			} else {
				this.details = service.resolveDocs(details);
				prepUploadCloseCompanyInformation();
				super.runClientSideUpdate("mainForm:sdfTabView:companyListTable");
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);

			logger.fatal(e.getMessage());
		}
	}

	/**
	 * Reject company.
	 */
	public void rejectCompany() {
		try {

			companysdf = null;
		} catch (Exception e) {

			logger.fatal(e.getMessage());
		}
	}

	/**
	 * Approve company.
	 */
	public void approveCompany() {
		try {
			companysdf.getCompany().setCompanyStatus(CompanyStatusEnum.Approved);
			companyService.update(companysdf.getCompany());
			companysdf = null;
		} catch (Exception e) {

			logger.fatal(e.getMessage());
		}
	}

	/**
	 * Download application form.
	 *
	 * @param companyID
	 *            the company ID
	 */
	public void downloadApplicationForm(Long companyID) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("company_id", companyID);

		try {
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("MerSETAReport.jasper", params, "Application_Form.pdf");
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		}
		// mailToUser(doc, user);
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

	/**
	 * Gets the exceptions.
	 *
	 * @return the exceptions
	 */
	public String getExceptions() {
		return exceptions;
	}

	/**
	 * Sets the exceptions.
	 *
	 * @param exceptions
	 *            the new exceptions
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
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

	public List<OrganisationType> getOrganisationTypeList() {
		return organisationTypeList;
	}

	public void setOrganisationTypeList(List<OrganisationType> organisationTypeList) {
		this.organisationTypeList = organisationTypeList;
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

	public Long getMAX_COMPANY_TRADE_NAME_SIZE() {
		return MAX_COMPANY_TRADE_NAME_SIZE;
	}

	public void setMAX_COMPANY_TRADE_NAME_SIZE(Long mAX_COMPANY_TRADE_NAME_SIZE) {
		MAX_COMPANY_TRADE_NAME_SIZE = mAX_COMPANY_TRADE_NAME_SIZE;
	}

	public Long getMAX_FAX_NUMBER() {
		return MAX_FAX_NUMBER;
	}

	public void setMAX_FAX_NUMBER(Long mAX_FAX_NUMBER) {
		MAX_FAX_NUMBER = mAX_FAX_NUMBER;
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

	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Company getReplacementCompany() {
		return replacementCompany;
	}

	public void setReplacementCompany(Company replacementCompany) {
		this.replacementCompany = replacementCompany;
	}

	public String getSetmisValidationErrors() {
		return setmisValidationErrors;
	}

	public void setSetmisValidationErrors(String setmisValidationErrors) {
		this.setmisValidationErrors = setmisValidationErrors;
	}

}
