package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.MailDef;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.UsersResponsibilitiesService;
import haj.com.service.UsersService;
import haj.com.service.lookup.CompanyUserPositionService;
import haj.com.service.lookup.UserResponsibilityService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersUI.
 */
@ManagedBean(name = "companyUsersUI")
@ViewScoped
public class CompanyUsersUI extends AbstractUI {

	/** The service. */
	private SDFCompanyService service = new SDFCompanyService();
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	private boolean addSdfToTrainingCommitte;

	/** The data model. */
	private LazyDataModel<SDFCompany> dataModel;

	/** The selected company. */
	private SDFCompany selectedCompany = new SDFCompany();

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company. */
	private Company company = null;

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The company users. */
	private List<CompanyUsers> companyUsers;

	/** The company user. */
	private CompanyUsers companyUser;

	/** The copy address. */
	private Boolean copyAddress;

	private List<UserResponsibility> selectedResponsibilities;

	private UsersService usersService = null;

	private String validiationErrors = "";

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** the HAJcontants validation */

	/** maximum length for first Name */

	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** maximum length for last name */

	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** maximum length for RSA ID number */

	private final Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;

	/** maximum length for passport number */

	private Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;

	/** maximum length for email address */

	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** telephone number format */

	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** cell phone number format */

	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The Constant allow FAX number format. */
	private final String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;

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
		setObjects();
		companysdfInfo();
	}

	/**
	 * Sets the objects.
	 */
	public void setObjects() {

		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);

	}

	public void clearViews() {
		this.companyUsers = null;
	}

	public List<SelectItem> getAssessorModTypeDD() {
		List<SelectItem> l = null;
		try {
			l = new ArrayList<SelectItem>();
			if (companyUser != null && companyUser.getUser() != null && companyUser.getUser().getId() != null) {

				AssessorModeratorApplicationService assessorModeratorApplicationService = new AssessorModeratorApplicationService();
				List<AssessorModeratorApplication> amApplicationList = assessorModeratorApplicationService
						.findByApprovedUserApplications(companyUser.getUser());

				for (AssessorModeratorApplication amApp : amApplicationList) {
					if (amApp.getApplicationType() == AssessorModeratorAppTypeEnum.NewAssessorRegistration
							|| amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorExtensionOfScope
							|| amApp.getApplicationType() == AssessorModeratorAppTypeEnum.AssessorReRegistration) {
						AssessorModType val = AssessorModType.Assessor;
						l.add(new SelectItem(val, val.getFriendlyName()));
					} else {
						AssessorModType val = AssessorModType.Moderator;
						l.add(new SelectItem(val, val.getFriendlyName()));
					}
				}
				if (l.size() == 1) {
					companyUser.setAssessorModType((AssessorModType) l.get(0).getValue());
				}
				if (l.size() < 1) {
					// companyUser=null;
					AssessorModType val = AssessorModType.Assessor;
					l.add(new SelectItem(val, val.getFriendlyName()));
					AssessorModType val2 = AssessorModType.Moderator;
					l.add(new SelectItem(val2, val2.getFriendlyName()));
					super.runClientSideUpdate("dlgAddContact");
					// throw new Exception("The selected user is not an assessor
					// or a moderator");
				}
			} else {
				// companyUser=null;
				AssessorModType val = AssessorModType.Assessor;
				l.add(new SelectItem(val, val.getFriendlyName()));
				AssessorModType val2 = AssessorModType.Moderator;
				l.add(new SelectItem(val2, val2.getFriendlyName()));
				super.runClientSideUpdate("dlgAddContact");
				// throw new Exception("The selected user is not an assessor or
				// a moderator");
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Get all CompanySDF for data table.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void companysdfInfo() {

		dataModel = new LazyDataModel<SDFCompany>() {

			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					if (getSessionUI().getActiveUser().getAdmin() == null
							|| !getSessionUI().getActiveUser().getAdmin()) {
						retorno = service.allCompanyFromSDF(SDFCompany.class, first, pageSize, sortField, sortOrder,
								filters, getSessionUI().getActiveUser());
						dataModel.setRowCount(
								service.allCompanyFromSDFCount(filters, getSessionUI().getActiveUser()).intValue());
					} else {
						retorno = service.allSDFCompany(SDFCompany.class, first, pageSize, sortField, sortOrder,
								filters);
						dataModel.setRowCount(service.count(SDFCompany.class, filters));
					}
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public List<CompanyUserPosition> getSelectItemsCompanyUserPosition() {
		CompanyUserPositionService companyUserPositionService = new CompanyUserPositionService();
		List<CompanyUserPosition> l = null;
		try {
			if (selectedCompany != null && selectedCompany.getId() != null)
				l = companyUserPositionService.allCompanyUserPositionNotUsed(selectedCompany.getCompany());
			else if (company != null && company.getId() != null)
				l = companyUserPositionService.allCompanyUserPositionNotUsed(company);
			else
				companyUserPositionService.allCompanyUserPosition();
			if (companyUser != null && companyUser.getPosition() != null) {
				if (l == null) {
					l = new ArrayList<>();
				}
				l.add(companyUserPositionService.findByKey(companyUser.getPosition().getId()));
			}
				
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Resolve users.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void resolveUsers() throws Exception {
		setObjects();
		if (selectedCompany != null && selectedCompany.getId() != null)
			companyUsers = companyUsersService.findByCompanyNotSDF(selectedCompany.getCompany());
		else
			if (company != null && company.getId() != null) {
				companyUsers = companyUsersService.findByCompanyNotSDF(company);
			}
		prepUsers();
	}

	public void prepForUpdate() {
		this.selectedResponsibilities = new ArrayList<>();
		for (UsersResponsibilities companyUsers2 : companyUser.getSelectedResponsibilities()) {
			this.selectedResponsibilities.add(companyUsers2.getUserResponsibility());
		}
	}

	/**
	 * Clear users.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void clearUsers() throws Exception {
		this.companyUsers = null;
	}

	/**
	 * Prep users.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void prepUsers() throws Exception {
		this.companyUser = null;
		this.selectedResponsibilities = new ArrayList<>();
	}

	/**
	 * Insert Users into database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersInsert() {
		try {
			/// validate user information
			if (usersService == null) {
				usersService = new UsersService();
			}
			validiationErrors = "";
			StringBuilder errors = usersService.validiateUserInformation(this.companyUser.getUser());
			if (errors.toString().trim().isEmpty()) {
				companyUsersService.createWithReg(this.companyUser, selectedResponsibilities);
				if (addSdfToTrainingCommitte) {
					trainingComitteeService.copyUser(companyUser.getUser(), companyUser.getCompany());
				}
				resolveUsers();
				addSdfToTrainingCommitte = false;
				addInfoMessage(super.getEntryLanguage("update.successful"));
			} else {
				validiationErrors = errors.toString();
				addErrorMessage("Validiation Error on user information. Please refer to message before proceeding. ");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (javax.validation.ConstraintViolationException e) {
			String setmisErrors = extractValidationErrorsReturnString(e);
			addErrorMessage("Validiation Exception: " + setmisErrors, e);
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void insertTPContact() {
		try {
			companyUsersService.createTPContact(this.companyUser);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			resolveUsers();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert Users into database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void createWithRegAndCreateTask() {
		try {
			if (usersService == null) {
				usersService = new UsersService();
			}
			validiationErrors = "";
			StringBuilder errors = usersService.validiateUserInformation(this.companyUser.getUser());
			if (errors.toString().trim().isEmpty()) {
				companyUser.setApprovalStatus(ApprovalEnum.PendingApproval);
				companyUsersService.createWithRegAndCreatetask(this.companyUser, selectedResponsibilities,getSessionUI().getActiveUser());
				if (addSdfToTrainingCommitte) {
					trainingComitteeService.copyUserAndCreateTask(companyUser.getUser(), companyUser.getCompany(),getSessionUI().getActiveUser());
				}
				resolveUsers();
				addSdfToTrainingCommitte = false;
				addInfoMessage(super.getEntryLanguage("update.successful"));
			} else {
				validiationErrors = errors.toString();
				addErrorMessage("Validiation Error on user information. Please refer to message before proceeding. ");
			}
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			System.out.println(getValidationErrors());
			addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete User.
	 *
	 * @author TechFinium
	 * @see CompanyUsers
	 */
	public void delteCompanyUser() {
		try {

			ArrayList<UsersResponsibilities> userResponsibilities = (ArrayList<UsersResponsibilities>) companyUser
					.getSelectedResponsibilities();
			UsersResponsibilitiesService usersResponsibilitiesService = new UsersResponsibilitiesService();

			for (UsersResponsibilities userResp : userResponsibilities) {
				usersResponsibilitiesService.delete(userResp);
			}
			companyUsersService.delete(this.companyUser);
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			resolveUsers();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<UserResponsibility> geUserResponsibilit() {
		UserResponsibilityService userResponsibilityService = new UserResponsibilityService();
		try {
			selectedResponsibilities = userResponsibilityService.findByCompanyUser(companyUser);

		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

		return selectedResponsibilities;
	}

	/**
	 * Cpy addresses.
	 */
	public void cpyAddresses() {
		try {
			if (copyAddress) {
				AddressService.instance().copyFromToAddress(companyUser.getUser().getResidentialAddress(),
						companyUser.getUser().getPostalAddress());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		companyUser.getUser().getPostalAddress().setSameAddress(copyAddress);

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
	 * Gets the selected company.
	 *
	 * @return the selected company
	 */
	public SDFCompany getSelectedCompany() {
		return selectedCompany;
	}

	/**
	 * Sets the selected company.
	 *
	 * @param selectedCompany
	 *            the new selected company
	 */
	public void setSelectedCompany(SDFCompany selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	/**
	 * Gets the company users.
	 *
	 * @return the company users
	 */
	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	/**
	 * Sets the company users.
	 *
	 * @param companyUsers
	 *            the new company users
	 */
	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	/**
	 * Gets the company user.
	 *
	 * @return the company user
	 */
	public CompanyUsers getCompanyUser() {
		return companyUser;
	}

	/**
	 * Sets the company user.
	 *
	 * @param companyUser
	 *            the new company user
	 */
	public void setCompanyUser(CompanyUsers companyUser) {
		this.companyUser = companyUser;
	}

	/**
	 * Clear postal.
	 */
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(companyUser.getUser().getPostalAddress());
		}
	}

	public void createDeleteCompanyUserTask() {
		try {
			this.companyUser.setApprovalStatus(ApprovalEnum.PendingApproval);
			String desc = "An attempt to delete contact person has been made, please approve";
			companyUsersService.update(this.companyUser);
			TasksService.instance().findFirstInProcessAndCreateTask(desc, getSessionUI().getActiveUser(),
					this.companyUser.getId(), this.companyUser.getClass().getName(), true,
					ConfigDocProcessEnum.DELETE_CONTACT_PERSON,
					MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(this.companyUser.getId(),
					this.companyUser.getClass().getName(), CompanyInfoUI.changeReason);

			new CompanyInfoUI().setChangeReason(new ChangeReason());
			addInfoMessage("Request has been sent for approval");
			super.runClientSideExecute("PF('dlgDeleContactPersonReason').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
			if (object instanceof Users) {
				Users user = (Users) object;
				companyUser = null;
				if (user.getId() != null) {
					if (selectedCompany != null && selectedCompany.getId() != null)
						companyUser = this.companyUsersService
								.findByUserAndCompany(selectedCompany.getCompany().getId(), user.getId());
					else
						companyUser = this.companyUsersService.findByUserAndCompany(company.getId(), user.getId());
				}
				// if (companyUser == null) {
				if (user.getPostalAddress() == null) {
					user.setPostalAddress(new Address());
				}

				if (user.getResidentialAddress() == null) {
					user.setResidentialAddress(new Address());
				}
				if (selectedCompany != null && selectedCompany.getId() != null)
					this.companyUser = new CompanyUsers(user, selectedCompany.getCompany());
				else
					this.companyUser = new CompanyUsers(user, company);

			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public List<UserResponsibility> getSelectedResponsibilities() {
		return selectedResponsibilities;
	}

	public void setSelectedResponsibilities(List<UserResponsibility> selectedResponsibilities) {
		this.selectedResponsibilities = selectedResponsibilities;
	}

	public boolean isAddSdfToTrainingCommitte() {
		return addSdfToTrainingCommitte;
	}

	public void setAddSdfToTrainingCommitte(boolean addSdfToTrainingCommitte) {
		this.addSdfToTrainingCommitte = addSdfToTrainingCommitte;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public String getValidiationErrors() {
		return validiationErrors;
	}

	public void setValidiationErrors(String validiationErrors) {
		this.validiationErrors = validiationErrors;
	}

}
