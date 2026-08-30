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

import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersUI.
 */
@ManagedBean(name = "sdfUsersUI")
@ViewScoped
public class SDFUsersUI extends AbstractUI {

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
					retorno = service.allSDFUsers(Users.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countSDFUsers(Users.class, filters));
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


}
