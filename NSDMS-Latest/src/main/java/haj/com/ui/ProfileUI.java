package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.constants.HAJConstants;
import haj.com.entity.ChangeReason;
import haj.com.entity.Doc;
import haj.com.entity.Images;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.ChangeReasonService;
import haj.com.service.ImagesService;
import haj.com.service.LogonService;
import haj.com.service.UserChangeRequestService;
import haj.com.service.UsersService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileUI.
 */
@ManagedBean(name = "profileUI")
@ViewScoped
public class ProfileUI extends AbstractUI {

	/** The service. */
	private AddressService service = new AddressService();

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The user. */
	private Users user = null;
	private Users updateUserInfo = null;

	/** The new password. */
	private String password, newPassword;

	/** The image. */
	private Images image;

	/** The format Tell phone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The format cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The maximum size first name . */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;
	public Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;

	/** The maximum size last name . */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** The maximum size email . */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	public ChangeReason changeReason = new ChangeReason();

	private Doc doc = new Doc();

	UserChangeRequestService userChangeRequestService = new UserChangeRequestService();
	UserChangeRequest userChangeRequest;

	private List<ChangeReason> changeReasonsList = new ArrayList<>();

	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();

	private boolean enableProfileEdit;

	private String validiationExceptionUserInfo = "";
	private String validiationExceptionUserInfoWorkflow = "";

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
		usersInfo();
		profileEditCheck();
		if (super.getParameter("id", false) != null) {
			userChangeRequest = userChangeRequestService.findByKey(getSessionUI().getTask().getTargetKey());
			changeReasonsList = (List<ChangeReason>) ChangeReasonService.instance().findByTargetClassAndTargetKey(
					getSessionUI().getTask().getTargetClass(), getSessionUI().getTask().getTargetKey());
			if (changeReasonsList.size() > 0) {
				changeReason = changeReasonsList.get(0);
			}
		}
	}

	public void profileEditCheck() {
		try {
			if (user != null && user.getId() != null) {
				enableProfileEdit = true;
				List<UserChangeRequest> requestList = userChangeRequestService.findByUserAndStatus(user.getId(),
						ApprovalEnum.PendingApproval);
				if (requestList != null && requestList.size() > 0) {
					enableProfileEdit = false;
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.USER_DETAILS_CHANGE);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Users info.
	 */
	public void usersInfo() {
		try {
			if (getSessionUI().getUser() != null)
				this.user = usersService.findByKey(getSessionUI().getUser().getId());
			/*
			 * List<Address> l = service.byUserId(this.user.getUid()); for
			 * (Address a : l) { switch (a.getAddrType()) { case Residential: {
			 * this.resiAddr = a; break; } case Postal: { this.postalAddr= a;
			 * break; } default: break; }
			 * 
			 * if (resiAddr==null) { resiAddr = new Address();
			 * resiAddr.setAddrType(AddressTypeEnum.Residential); } else {
			 * 
			 * model.addOverlay(new Marker(new LatLng(resiAddr.getLatitude(),
			 * resiAddr.getLongitude()), "M1")); } if (postalAddr==null) {
			 * postalAddr = new Address();
			 * postalAddr.setAddrType(AddressTypeEnum.Postal); }
			 * 
			 * }
			 */
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Users update.
	 */
	public void usersUpdate() {
		try {
			// if (this.users.getCompany().getCompanyId()==-1L) throw new
			// Exception("Select
			// a company");
			usersService.isMailUsed(user.getEmail(), user);
			usersService.create(this.user);
			super.addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setUser(this.user);
			usersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Approves change request */
	public void approveUserChangesTask() {
		try {
			validiationExceptionUserInfoWorkflow = "";
			usersService.approveUserChangesTask(userChangeRequest,changeReason,getSessionUI().getTask());
			super.addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			super.ajaxRedirect(getSessionUI().getDashboard());
		 } catch (javax.validation.ConstraintViolationException e) {
			validiationExceptionUserInfoWorkflow = extractValidationErrorsReturnString(e);
			addErrorMessage("SETMIS Validiation Exceptions");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/* Reject Change request */
	public void rejectUserChangesTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			} else {
				usersService.rejectUserChangesTask(userChangeRequest, changeReason, getSessionUI().getTask(),
						selectedRejectReason, getSessionUI().getActiveUser());
				getSessionUI().setTask(null);
				super.ajaxRedirect(getSessionUI().getDashboard());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Creates Task.
	 */
	public void createTask() {
		try {
			if (changeReason.getDoc() == null) {
				throw new Exception("Upload ID document");
			}
			if (changeReason.getDescription().isEmpty() || changeReason.getDescription().equals("")) {
				throw new Exception("Provide change reason");
			}
			usersService.createTask(this.user, changeReason);
			super.addInfoMessage("Your request to change your information has been submitted for review");
			clearrequestChangeInfo();
			usersInfo();
			profileEditCheck();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepUpdateUserInfo() {
		try {
			changeReason = new ChangeReason();
			updateUserInfo = usersService.findByKey(user.getId());
			validiationExceptionUserInfo = "";
			runClientSideExecute("PF('dlgChangeReason').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createTaskToUpdateProfile() {
		try {
			validiationExceptionUserInfo = "";
			StringBuilder sb = usersService.validiateUserInformation(updateUserInfo);
			if (sb.toString().trim().isEmpty()) {
				usersService.validateEmailAddressByUser(updateUserInfo);
				if (changeReason.getDoc() == null) {
					throw new Exception("Upload ID document");
				}
				if (changeReason.getDescription().isEmpty() || changeReason.getDescription().equals("")) {
					throw new Exception("Provide change reason");
				}
				usersService.createTask(updateUserInfo, changeReason);
				super.addInfoMessage("Your request to change your information has been submitted for review");
				runClientSideExecute("PF('dlgChangeReason').hide()");
				clearrequestChangeInfo();
				usersInfo();
				profileEditCheck();
			} else {
				validiationExceptionUserInfo = sb.toString();
				addWarningMessage("Validiation exception. Please refer to the message and make alterations. ");
			}
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearrequestChangeInfo() {
		changeReason = new ChangeReason();
	}

	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Users chg own pwd update.
	 */
	public void usersChgOwnPwdUpdate() {
		try {

			GenericUtility.checkPassword(newPassword, getSessionUI().getActiveUser());

			new LogonService().changePassword(this.user.getEmail(), password, newPassword);

			super.addInfoMessage(super.getEntryLanguage("password.change"));

			log("User changed password", this.user.getEmail());
			usersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Save profile image.
	 *
	 * @param event
	 *            the event
	 */
	public void saveImage(FileUploadEvent event) {
		try {
			ImagesService.saveProfilePic(this.image, event.getFile(), this.user, true);
			usersInfo();
			getSessionUI().setUser(usersService.findByKey(user.getId()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the new password.
	 *
	 * @return the new password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Sets the new password.
	 *
	 * @param newPassword
	 *            the new new password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void updateProfile() {
		try {
			usersService.save(user);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
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

	public UserChangeRequest getUserChangeRequest() {
		return userChangeRequest;
	}

	public void setUserChangeRequest(UserChangeRequest userChangeRequest) {
		this.userChangeRequest = userChangeRequest;
	}

	public List<ChangeReason> getChangeReasonsList() {
		return changeReasonsList;
	}

	public void setChangeReasonsList(List<ChangeReason> changeReasonsList) {
		this.changeReasonsList = changeReasonsList;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public boolean isEnableProfileEdit() {
		return enableProfileEdit;
	}

	public void setEnableProfileEdit(boolean enableProfileEdit) {
		this.enableProfileEdit = enableProfileEdit;
	}

	public Users getUpdateUserInfo() {
		return updateUserInfo;
	}

	public void setUpdateUserInfo(Users updateUserInfo) {
		this.updateUserInfo = updateUserInfo;
	}

	public String getValidiationExceptionUserInfo() {
		return validiationExceptionUserInfo;
	}

	public void setValidiationExceptionUserInfo(String validiationExceptionUserInfo) {
		this.validiationExceptionUserInfo = validiationExceptionUserInfo;
	}

	public String getValidiationExceptionUserInfoWorkflow() {
		return validiationExceptionUserInfoWorkflow;
	}

	public void setValidiationExceptionUserInfoWorkflow(String validiationExceptionUserInfoWorkflow) {
		this.validiationExceptionUserInfoWorkflow = validiationExceptionUserInfoWorkflow;
	}
}
