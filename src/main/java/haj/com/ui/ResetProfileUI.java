package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.RegisterService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileUI.
 */
@ManagedBean(name = "resetProfileUI")
@ViewScoped
public class ResetProfileUI extends AbstractUI {

	/** The users service. */
	private UsersService usersService = new UsersService();
	private RegisterService registerService = new RegisterService();

	/** The user. */
	private Users user = null;
	
	/** Validation Exception Display */
	private String setmisValidiationException = "";

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
	}

	/**
	 * Users info.
	 */
	public void usersInfo() {
		try {
			if (super.getParameter("id", false) != null) {
			  this.user = usersService.findByKey(getSessionUI().getTask().getTargetKey());
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
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



	public void updateProfile() {
		try {
			setmisValidiationException = "";
			usersService.update(user);
			registerService.reRegister(user);
			TasksService.instance().completeTask(getSessionUI().getTask());
			super.ajaxRedirect("/pages/dashboard.jsf");
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public String getSetmisValidiationException() {
		return setmisValidiationException;
	}

	public void setSetmisValidiationException(String setmisValidiationException) {
		this.setmisValidiationException = setmisValidiationException;
	}

	
}
