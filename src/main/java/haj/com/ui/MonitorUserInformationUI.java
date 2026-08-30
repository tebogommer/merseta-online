package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersService;


@ManagedBean(name = "monitorUserInformationUI")
@ViewScoped
public class MonitorUserInformationUI extends AbstractUI {

	/* Entity Level */
	private Users selectedUser = null;
	
	/* Service Level */
	private UsersService usersService = new UsersService();
	
	/* Lazy Data Models */
	private LazyDataModel<Users> dataModelUsers;
	
	/* VARS */
	private Long userID = null;
	private boolean useUserID = false;
	
	
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
		getSessionUI().setValidationErrors("");
		populateUsers();
	}
	
	private void populateUsers() throws Exception{
		userInfo();
	}
	
	public void applyFilter(){
		selectedUser = null;
		useUserID = true;
		userInfo();
	}
	
	public void clearFilter(){
		selectedUser = null;
		useUserID = false;
		userInfo();
	}
	
	public void userInfo() {
		dataModelUsers = new LazyDataModel<Users>() {
			private static final long serialVersionUID = 1L;
			private List<Users> list = new ArrayList<>();
			@Override
			public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (useUserID && userID != null) {
						list = usersService.allUsersByID(first, pageSize, sortField, sortOrder, filters, userID);
						dataModelUsers.setRowCount(usersService.countAllUsersByID(filters));
					} else {
						list = usersService.allUsers(Users.class, first, pageSize, sortField, sortOrder, filters);
						dataModelUsers.setRowCount(usersService.count(Users.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(Users object) {
				return object.getId();
			}
			@Override
			public Users getRowData(String rowKey) {
				for (Users obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void deselectObject(){
		try {
			getSessionUI().setValidationErrors("");
			selectedUser = null;
			addInfoMessage("User De-selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectObject(){
		try {
			getSessionUI().setValidationErrors("");
			selectedUser = usersService.findByKey(selectedUser.getId());
			usersService.resolveUserAddresses(selectedUser);
			addInfoMessage("User Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} 
	}
	
	public void runValidiationChecks(){
		try {
			getSessionUI().setValidationErrors("");
			Users userForValidiation = usersService.findByKey(selectedUser.getId());
			usersService.resolveUserAddresses(userForValidiation);
			userForValidiation.setValidiationRanDate(getNow());
			usersService.updateUserBatchInformation(userForValidiation);
			userForValidiation = null;
			addInfoMessage("Check User No Validiation Errors on User and/or address level");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateInformation(){
		try {
			if (selectedUser == null && selectedUser.getId() == null) {
				throw new Exception("Unable to locate user information, contact support!");
			} else {
				usersService.updateUserBatchInformation(selectedUser);
			}
			getSessionUI().setValidationErrors("");
			selectedUser = null;
			addInfoMessage("Update Complete");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} 
	}

	/** getters and setters */
	public Users getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
	}

	public LazyDataModel<Users> getDataModelUsers() {
		return dataModelUsers;
	}

	public void setDataModelUsers(LazyDataModel<Users> dataModelUsers) {
		this.dataModelUsers = dataModelUsers;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public boolean isUseUserID() {
		return useUserID;
	}

	public void setUseUserID(boolean useUserID) {
		this.useUserID = useUserID;
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

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}
		
		
}