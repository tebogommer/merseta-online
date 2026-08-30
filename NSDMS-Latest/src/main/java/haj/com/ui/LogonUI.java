package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.axis.utils.StringUtils;
import org.hibernate.validator.constraints.Email;

import haj.com.constants.HAJConstants;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.ActiveUsersService;
import haj.com.service.CompanyUsersService;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyService;
import haj.com.service.LogonService;
import haj.com.service.NonSetaCompanyUsersService;
import haj.com.service.SDPCompanyService;
import haj.com.service.UsersRoleService;
import haj.com.service.UsersService;
import haj.com.service.WeatherService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class LogonUI.
 */
@ManagedBean(name = "logonUI")
@ViewScoped
public class LogonUI extends AbstractUI {

	private static final NSDMSLogger logger=NSDMSLogger.getLogger(LogonUI.class);
	/** The email. */
	@Email(message = "Please enter a valid Email Address")
	private String email;

	/** The email. */
	@Email(message = "Please enter a valid Email Address to impersonate")
	private String impersonatedEmail;

	/** The newpassword. */
	private String inputPassword, newpassword;

	/** The logon service. */
	private LogonService logonService = new LogonService();

	/** The users role service. */
	private UsersRoleService usersRoleService = new UsersRoleService();

	/** The hc employees service. */
	private HostingCompanyEmployeesService hcEmployeesService = new HostingCompanyEmployeesService();

	/** The hosting company departments employees service. */
	private HostingCompanyDepartmentsEmployeesService hostingCompanyDepartmentsEmployeesService = new HostingCompanyDepartmentsEmployeesService();

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private NonSetaCompanyUsersService nonSetaCompanyUsersService = new NonSetaCompanyUsersService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();

	/** The Users service */
	private UsersService usersService;
	private Users userUpdateInfo = null;

	private boolean accessRestricted = HAJConstants.RESTRICT_ACCESS;

	/** Set theme via GuestPreferences. */
	@ManagedProperty(value = "#{guestPreferences}")
	private GuestPreferences guestPreferences = new GuestPreferences();

	@ManagedProperty(value = "#{uploadDocUI}")
	private UploadDocUI uploadDocUI = new UploadDocUI();

	private boolean impersonateUser = false;

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
	
	/** The Constant allow FAX number format. */
	private String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	
	/** The maximum size all for vax number*/
 	public Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
 	
 	public Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
 	
 	public String passportNumberFormat = HAJConstants.passportNumberFormat;
 	
 	/* user information validation displays */
 	private boolean firstNameFailed = false;
 	private boolean middleNameFailed = false;
 	private boolean lastNameFailed = false;
 	private boolean workcontactNumberFailed = false;
 	private boolean emailFailed = false;
 	private boolean telNumberFailed = false;
 	private boolean cellNumberFailed = false;
 	private boolean faxNumberFailed = false;
 	private boolean passportNumberFailed = false;
 	private boolean rsaIdNumberFailed = false;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			getSessionUI().setExternalSessionId(super.getExternalSessionId());
			ActiveUsersService.instance().addSession(getSessionUI().getExternalSessionId());
			getSessionUI().setWsUrl(HAJConstants.WEBSOCKET_SERVER);
			if (getSessionUI().getUser() == null) {
				getSessionUI().setEmployee(false);
				getSessionUI().setAdmin(false);
				getSessionUI().setExternalParty(false);
				runInit();
			}
			try {
				this.impersonateUser = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("impersonateUser"));
			}catch(Exception e){
				logger.trace("Normal logon in progress, not impersonation");
			}
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
		if (getSessionUI().getHostingCompany() == null) {
			getSessionUI().setHostingCompany(hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA));
		}
	}

	/**
	 * Logon.
	 */
	public void logon() {
		Users u = null;
		try {
			validateIfSessionActive();
			userUpdateInfo = null;
			if (accessRestricted) {
				if (email.trim().toUpperCase().equals("ADMIN@A.COM") || email.trim().toUpperCase().equals("SANDRA@DAJOASSOCIATES.COM")) {
					u = logonService.logonByEmail(email, inputPassword);
					getSessionUI().setValidationErrors("");
					u.setUserPermissions(logonService.populateUserPermissions(u));
					getSessionUI().setUser(u);
					getSessionUI().setActiveUser(getSessionUI().getUser());
					getUploadDocUI().setActiveUser(u);
					ActiveUsersService.instance().addUser(getSessionUI().getExternalSessionId(), u);
					super.setParameter("uobj", getSessionUI().getUser(), true);
					resolveWeather();
					logonContinue();
				} else {
					addErrorMessage("Please note the NSDMS is currently down for scheduled maintenance.");
				}
			} else {
				u = logonService.logonByEmailWithoutUpdates(email, inputPassword);
				u.setUserPermissions(logonService.populateUserPermissions(u));
				getSessionUI().setValidationErrors("");
				// Validation of user information
				validiateUserInformation(u);

				// normal login
				boolean canImpersonate= NSDMSConfiguration.getBoolean(String.format("nsdms.admin.%s.can.impersonate",email),false);
				if(canImpersonate && impersonatedEmail != null){
					u = logonService.impersonateByEmailWithoutUpdates(email, inputPassword,impersonatedEmail);
				}else {
					u = logonService.logonByEmail(email, inputPassword);
				}
				u.setUserPermissions(logonService.populateUserPermissions(u));
				getSessionUI().setUser(u);
				getSessionUI().setActiveUser(getSessionUI().getUser());
				getUploadDocUI().setActiveUser(u);
				ActiveUsersService.instance().addUser(getSessionUI().getExternalSessionId(), u);
				super.setParameter("uobj", getSessionUI().getUser(), true);
				resolveWeather();
				logonContinue();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()), e);
		} catch (javax.validation.ConstraintViolationException e) {
			if (u != null) {
				extractValidationErrors(e);
				
				// if validation exception happens bring user information
				if (usersService == null) {
					usersService = new UsersService();
				}
				userUpdateInfo = u;
				
				populateSetMisFailedEntryDisplays();
				
				// update user info dialog
				super.runClientSideExecute("PF('dlgUserInfo').show()");
				super.runClientSideUpdate("userUpdateForm");
			}
		} catch (Exception e) {
			log("invalid logon for email", email);
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void validateIfSessionActive() throws Exception {
		if (getSessionUI().getActiveUser() != null || getSessionUI().getUser() != null) {
			throw new Exception("Session Already Active. Please log out of current session or fully close browser and attempt login again.");
		}
	}

	private void populateSetMisFailedEntryDisplays() {
		firstNameFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS First Name") : Boolean.FALSE);
		middleNameFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Middle Name") : Boolean.FALSE);
		lastNameFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Last Name") : Boolean.FALSE);
		passportNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Passport Number") : Boolean.FALSE);
		rsaIdNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS ID Number") : Boolean.FALSE);
		workcontactNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Work Number") : Boolean.FALSE);
		telNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Tel Number") : Boolean.FALSE);
		cellNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Cell Number") : Boolean.FALSE);
		faxNumberFailed = ( (getSessionUI().getValidationErrors() != null && !getSessionUI().getValidationErrors().isEmpty()) ? getSessionUI().getValidationErrors().contains("SETMIS Fax Number") : Boolean.FALSE);
	}

	private void validiateUserInformation(Users u) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		usersService.updateWithValidiationDate(u);
	}

	public void updateInformation() {
		if (usersService == null) {
			usersService = new UsersService();
		}
		try {
			usersService.updateAfterLoginValidiation(userUpdateInfo);
			
			logonService.updateUserInfoAfterLogin(userUpdateInfo);
			
			super.runClientSideExecute("PF('dlgUserInfo').hide()");
			
			getSessionUI().setUser(userUpdateInfo);
			getSessionUI().setActiveUser(getSessionUI().getUser());
			getUploadDocUI().setActiveUser(getSessionUI().getUser());
			resolveWeather();
			logonContinue();
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			addErrorMessage("Validiation Failed. Please refer to message on screen and alter information based on information.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Logon sales staff.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void logonContinue() throws Exception {
		if (getSessionUI().getActiveUser().getAcceptPopi() == null || !getSessionUI().getActiveUser().getAcceptPopi()) {
			super.runClientSideExecute("PF('dlgPopi').show()");
			super.runClientSideUpdate("usersPwdChgForm");
		} else if (getSessionUI().getActiveUser().isChgPwdNow()) {
			super.runClientSideExecute("PF('dlgPwdChg').show()");
			super.runClientSideUpdate("usersPwdChgForm");
		} else {
			completeLogon();
		}

	}

	/**
	 * Complete logon.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void completeLogon() throws Exception {
		storeClientInfo();
		getSessionUI().setllowableInternalUser(false);
		if (hcEmployeesService.findByUserReturnHostCompanyB(getSessionUI().getActiveUser().getId())) {
			getSessionUI().setEmployee(true);
			getSessionUI().setExternalParty(false);
			getSessionUI().setHostingCompany(hcEmployeesService
					.findByUserReturnHostCompany(getSessionUI().getActiveUser().getId()).getHostingCompany());
			if (getSessionUI().getHostingCompany().getTheme() != null) {
				// HS removed because is demo on the 16/10/2017 to merSETA
				// getGuestPreferences().setTheme(getSessionUI().getHostingCompany().getTheme());
			}
			// HS removed because is demo on the 16/10/2017 to merSETA
			// else
			// getGuestPreferences().setTheme("techfinium");

			if (getSessionUI().getActiveUser().getAdmin() != null && getSessionUI().getActiveUser().getAdmin()) {
				getSessionUI().setAdmin(true);
				getSessionUI().setDashboard("/admin/dashboard.jsf");
				// admin reporting item
				getSessionUI().setDashboardReporting("/admin/dashboard.jsf");
				super.ajaxRedirect("/admin/dashboard.jsf");
			} else if (getSessionUI().getActiveUser().getFinance() != null
					&& getSessionUI().getActiveUser().getFinance()) {
				HostingCompanyEmployees hce = hcEmployeesService
						.findByUserReturnHostCompany(getSessionUI().getActiveUser().getId());
				if (hce != null && hce.getJobTitle() != null) {
					getSessionUI().setRole(hce.getJobTitle().getRoles());
				}
				getSessionUI().setFinance(true);
				getSessionUI().setDashboard("/admin/sarsfiles.jsf");
				super.ajaxRedirect("/admin/sarsfiles.jsf");
			} else {
				// check for manager OLD
				List<Roles> roles = hostingCompanyDepartmentsEmployeesService
						.findAllRolessForManagers(getSessionUI().getHostingCompany(), getSessionUI().getActiveUser());
				if (roles.size() > 0) {
					getSessionUI().setRoles(roles);
				} else {
					getSessionUI().setRoles(usersRoleService.findUniqueRoles(getSessionUI().getActiveUser()));
				}

				HostingCompanyEmployees hce = hcEmployeesService
						.findByUserReturnHostCompany(getSessionUI().getActiveUser().getId());
				if (hce != null && hce.getJobTitle() != null) {
					getSessionUI().setRole(hce.getJobTitle().getRoles());
				}
				if (hce.getJobTitle().getId() == 87 ) {
					getSessionUI().setllowableInternalUser(true);
				}
				
				if (StringUtils.isEmpty(getSessionUI().getRole().getDashboard())) {
					getSessionUI().setDashboard("/pages/dashboard.jsf");
					getSessionUI().setDashboardReporting(null);
				} else {
					getSessionUI().setDashboard(getSessionUI().getRole().getDashboard());
					getSessionUI().setDashboardReporting(getSessionUI().getRole().getDashboardReporting());
				}
				super.ajaxRedirect(getSessionUI().getDashboard());
			}

		} else {
			// getGuestPreferences().setTheme("layout-dark-green");
			if (getSessionUI().getActiveUser().getAdmin() != null && getSessionUI().getActiveUser().getAdmin()) {
				getSessionUI().setAdmin(true);
				getSessionUI().setDashboard("/admin/dashboard.jsf");
				super.ajaxRedirect("/admin/dashboard.jsf");
			} else if (getSessionUI().getActiveUser().getFinance() != null
					&& getSessionUI().getActiveUser().getFinance()) {
				getSessionUI().setEmployee(true);
				getSessionUI().setFinance(true);
				getSessionUI().setDashboard("/admin/sarsfiles.jsf");
				super.ajaxRedirect("/admin/sarsfiles.jsf");
			} else {
				getSessionUI().setEmployee(false);
				getSessionUI().setExternalParty(true);
				if (companyUsersService.findUsersByCompanyInResponsibilityAndUser(getSessionUI().getActiveUser(),
						ConfigDocProcessEnum.Learner).size() >= 1) {
					getSessionUI().setContact(true);
				}
				getSessionUI().setSdf(companyUsersService.findByUserTypeCount(getSessionUI().getActiveUser(), ConfigDocProcessEnum.SDF) >= 1);
				getSessionUI().setMentorregistration(companyUsersService.findByUserTypeCountAndCanRegisterMentor( getSessionUI().getActiveUser(), ConfigDocProcessEnum.SDF, true) >= 1);
				// needs to be updated
				getSessionUI().setTrainingProvider(companyUsersService.findByUserTypeCount(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP) >= 1);
				if (!getSessionUI().isTrainingProvider()) {
					getSessionUI().setTrainingProvider(sdpCompanyService.countByUserId(getSessionUI().getActiveUser().getId()) >= 1);
				}
				getSessionUI().setAssMod(companyUsersService.findByUserTypeCount(getSessionUI().getActiveUser(), ConfigDocProcessEnum.AM) >= 1);
				getSessionUI().setNonSetaCompany(nonSetaCompanyUsersService.findByUserTypeCount(getSessionUI().getActiveUser()) >= 1);
				if (getSessionUI().isCheckLearner()) getSessionUI().setLearner(companyUsersService.findLearnerCount(getSessionUI().getActiveUser()) >= 1);
				getSessionUI().setDashboard("/pages/externalparty/dashboard.jsf");
				getSessionUI().setDashboardReporting(null);
				super.ajaxRedirect("/pages/externalparty/dashboard.jsf");
			}
		}
	}

	/**
	 * Logoff.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void logoff() throws Exception {
		logoffGeneric();
		super.removeParm("sessionUI", true);
		super.removeParm("uobj", true);
		super.removeParm("languageUI", true);
		log();
		super.ajaxRedirect("/login.jsf");
	}

	/**
	 * Logoff generic.
	 */
	private void logoffGeneric() {
		storeClientInfo();
		getSessionUI().setUser(null);
		getSessionUI().setActiveUser(null);
		getSessionUI().setHostingCompany(null);
		getSessionUI().setSelDoc(null);
		getSessionUI().setUploadHeading(null);
		getSessionUI().setTask(null);
		getSessionUI().setRole(null);
		getSessionUI().setRoles(null);
		getSessionUI().setDashboard(null);
		getSessionUI().setWeather(null);
		getSessionUI().setEmployee(false);
		getSessionUI().setAdmin(false);
		getSessionUI().setExternalParty(false);
		getSessionUI().setFinance(false);
		getSessionUI().setSdf(false);
		getSessionUI().setLearner(false);
		getSessionUI().setTrainingProvider(false);
		getSessionUI().setAssMod(false);
		getSessionUI().setExternalSessionId(null);
		getSessionUI().setWsUrl(null);
	}

	public void acceptPopiAct() {
		if (getSessionUI().getActiveUser() != null && getSessionUI().getActiveUser().getAcceptPopi() == null) {
			getSessionUI().getActiveUser().setAcceptPopi(false);
		}
		if (getSessionUI().getActiveUser() != null && !getSessionUI().getActiveUser().getAcceptPopi()) {
			if (!getSessionUI().isAcceptPopi()) {
				addErrorMessage("You are required to accept the POPI act should you wish to use the NSDMS");
			} else {
				super.runClientSideExecute("PF('dlgPopi').hide()");
				getSessionUI().getActiveUser().setAcceptPopi(true);
				getSessionUI().getActiveUser().setAcceptPopiDate(new java.util.Date());
				try {
					logonService.update(getSessionUI().getActiveUser());
					logonContinue();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else {
			if (getSessionUI().isAcceptPopi()) {
				super.runClientSideExecute("PF('dlgPopi').hide()");
				super.runClientSideExecute("PF('goToregistrationDLG').show()");
			} else {
				addErrorMessage("You are required to accept the POPI act should you wish to use the NSDMS");
			}
		}
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the input password.
	 *
	 * @return the input password
	 */
	public String getInputPassword() {
		return inputPassword;
	}

	/**
	 * Sets the input password.
	 *
	 * @param inputPassword
	 *            the new input password
	 */
	public void setInputPassword(String inputPassword) {
		this.inputPassword = inputPassword;
	}

	/**
	 * Gets the newpassword.
	 *
	 * @return the newpassword
	 */
	public String getNewpassword() {
		return newpassword;
	}

	/**
	 * Sets the newpassword.
	 *
	 * @param newpassword
	 *            the new newpassword
	 */
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	/**
	 * Change password.
	 */
	public void changePassword() {
		try {
			GenericUtility.checkPassword(inputPassword, getSessionUI().getActiveUser());
			logonService.changePassword(getSessionUI().getActiveUser(), inputPassword);
			super.addInfoMessage(super.getEntryLanguage("password.changed"));
			completeLogon();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * New password.
	 */
	public void newPassword() {
		try {
			logonService.notifyUserNewPasswordEmail(email);
			super.addInfoMessage(super.getEntryLanguage("a.new.password.has.been.mailed.to.you"));
			super.runClientSideExecute("PF('dlgPwd').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Resolve weather.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void resolveWeather() throws Exception {
		if (getSessionUI().getWeather() == null) {
			if (getSessionUI().getLatitude() != null && getSessionUI().getLongitude() != null) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// System.out.println("Before Weather");
//							getSessionUI().setWeather(WeatherService.instance().byLatLang(getSessionUI().getLatitude(),
//									getSessionUI().getLongitude()));
							// System.out.println("After Weather");

						} catch (Exception e) {
							logger.fatal(e);

						}
					}

				}).start();
			} else {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							// System.out.println("Before Weather JHB");
							getSessionUI().setWeather(WeatherService.instance().byCity("Johannesburg"));
							// System.out.println("After Weather JHB");
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

				}).start();

			}
		}
	}

	/**
	 * Gets the guest preferences.
	 *
	 * @return the guest preferences
	 */
	public GuestPreferences getGuestPreferences() {
		return guestPreferences;
	}

	/**
	 * Sets the guest preferences.
	 *
	 * @param guestPreferences
	 *            the new guest preferences
	 */
	public void setGuestPreferences(GuestPreferences guestPreferences) {
		this.guestPreferences = guestPreferences;
	}

	public void onIdle() {
		try {
			logoff();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// super.runClientSideExecute("startIdleMonitor()");
	}

	public void onActive() {
		super.runClientSideExecute("	PF('timeoutDialog').hide()");
	}

	public UploadDocUI getUploadDocUI() {
		return uploadDocUI;
	}

	public void setUploadDocUI(UploadDocUI uploadDocUI) {
		this.uploadDocUI = uploadDocUI;
	}

	public boolean isAccessRestricted() {
		return accessRestricted;
	}

	public void setAccessRestricted(boolean accessRestricted) {
		this.accessRestricted = accessRestricted;
	}

	public Users getUserUpdateInfo() {
		return userUpdateInfo;
	}

	public void setUserUpdateInfo(Users userUpdateInfo) {
		this.userUpdateInfo = userUpdateInfo;
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

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public void setFAX_NUMBER_FORMAT(String fAX_NUMBER_FORMAT) {
		FAX_NUMBER_FORMAT = fAX_NUMBER_FORMAT;
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

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public boolean isFirstNameFailed() {
		return firstNameFailed;
	}

	public void setFirstNameFailed(boolean firstNameFailed) {
		this.firstNameFailed = firstNameFailed;
	}

	public boolean isMiddleNameFailed() {
		return middleNameFailed;
	}

	public void setMiddleNameFailed(boolean middleNameFailed) {
		this.middleNameFailed = middleNameFailed;
	}

	public boolean isLastNameFailed() {
		return lastNameFailed;
	}

	public void setLastNameFailed(boolean lastNameFailed) {
		this.lastNameFailed = lastNameFailed;
	}

	public boolean isEmailFailed() {
		return emailFailed;
	}

	public void setEmailFailed(boolean emailFailed) {
		this.emailFailed = emailFailed;
	}

	public boolean isTelNumberFailed() {
		return telNumberFailed;
	}

	public void setTelNumberFailed(boolean telNumberFailed) {
		this.telNumberFailed = telNumberFailed;
	}

	public boolean isCellNumberFailed() {
		return cellNumberFailed;
	}

	public void setCellNumberFailed(boolean cellNumberFailed) {
		this.cellNumberFailed = cellNumberFailed;
	}

	public boolean isFaxNumberFailed() {
		return faxNumberFailed;
	}

	public void setFaxNumberFailed(boolean faxNumberFailed) {
		this.faxNumberFailed = faxNumberFailed;
	}

	public boolean isPassportNumberFailed() {
		return passportNumberFailed;
	}

	public void setPassportNumberFailed(boolean passportNumberFailed) {
		this.passportNumberFailed = passportNumberFailed;
	}

	public boolean isRsaIdNumberFailed() {
		return rsaIdNumberFailed;
	}

	public void setRsaIdNumberFailed(boolean rsaIdNumberFailed) {
		this.rsaIdNumberFailed = rsaIdNumberFailed;
	}

	public boolean isWorkcontactNumberFailed() {
		return workcontactNumberFailed;
	}

	public void setWorkcontactNumberFailed(boolean workcontactNumberFailed) {
		this.workcontactNumberFailed = workcontactNumberFailed;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public void setImpersonateUser(boolean impersonateUser){
		this.impersonateUser=impersonateUser;
	}

	public boolean getImpersonateUser(){
		return this.impersonateUser;
	}


	public void setImpersonatedEmail(String impersonatedEmail){
		this.impersonatedEmail = impersonatedEmail;
	}

	public String getImpersonatedEmail(){
		return this.impersonatedEmail;
	}
}
