package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersUI.
 */
@ManagedBean(name = "usersUI")
@ViewScoped
public class UsersUI extends AbstractUI {

	/** The service. */
	private UsersService service = new UsersService();

	/** The users list. */
	private List<Users> usersList = null;

	/** The usersfiltered list. */
	private List<Users> usersfilteredList = null;

	/** The users. */
	private Users users = null;

	/** The data model. */
	private LazyDataModel<Users> dataModel;
	
	/** The format Tell phone format. */
	public  String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	
	/** The format cell phone format. */
	public  String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	
	/** The maximum size first name . */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** The maximum size last name . */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** The maximum size email . */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;
	
	/**maximum size of RSA ID number*/
	private Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
	
	/**maximum size of passport number*/
	private Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;


	/** The Constant allow only number format. */
	public String passportNumberFormat = HAJConstants.passportNumberFormat;
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
	 * Initialize method to get all Users and prepare a for a create of a new
	 * Users.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	private void runInit() throws Exception {
		prepareNew();
		usersInfo();
	}

	/**
	 * Get all Users for data table.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersInfo() {

		dataModel = new LazyDataModel<Users>() {

			private static final long serialVersionUID = 1L;
			private List<Users> retorno = new ArrayList<Users>();

			@Override
			public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allUsers(Users.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Users.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Users obj) {
				return obj.getId();
			}

			@Override
			public Users getRowData(String rowKey) {
				for (Users obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Users into database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersInsert() {
		try {
			service.create(this.users);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			usersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Users from database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersDelete() {
		try {
			service.delete(this.users);
			prepareNew();
			usersInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the users list.
	 *
	 * @return the users list
	 */
	public List<Users> getUsersList() {
		return usersList;
	}

	/**
	 * Sets the users list.
	 *
	 * @param usersList
	 *            the new users list
	 */
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Users getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users
	 *            the new users
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * Create new instance of Users .
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void prepareNew() {
		users = new Users();
	}

	/**
	 * Update Users in database.
	 *
	 * @author TechFinium
	 * @see Users
	 */
	public void usersUpdate() {
		try {
			service.update(this.users);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			usersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare select one menu data.
	 *
	 * @author TechFinium
	 * @return the users DD
	 * @see Users
	 */
	public List<SelectItem> getUsersDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		usersInfo();
		for (Users ug : usersList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}

	/**
	 * Gets the usersfiltered list.
	 *
	 * @return the usersfiltered list
	 */
	public List<Users> getUsersfilteredList() {
		return usersfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param usersfilteredList
	 *            the new usersfiltered list
	 * @see Users
	 */
	public void setUsersfilteredList(List<Users> usersfilteredList) {
		this.usersfilteredList = usersfilteredList;
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Users> complete(String desc) {
		List<Users> l = null;
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
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Users> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Users> dataModel) {
		this.dataModel = dataModel;
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

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}
	
}
