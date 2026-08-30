package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Users;
import haj.com.framework.AbstractUI;
import haj.com.service.RegisterService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmEmail.
 */
@ManagedBean(name = "confirmEmailUI")
@ViewScoped
public class ConfirmEmail extends AbstractUI {

	/** The service. */
	private UsersService service = new UsersService();
	
	/** The registerservice. */
	private RegisterService registerservice = new RegisterService();
	
	/** The uuid. */
	private String uuid;
	
	/** The valid link. */
	private boolean validLink;
	
	/** The link expired. */
	private boolean linkExpired;
	
	/** The users. */
	private Users users;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		validLink = false;
		if (super.getParameter("uuid", false) != null) {
			this.uuid = (String) super.getParameter("uuid", false);
			this.validLink = true;
			Users u = service.findUserByEmailGUID(this.uuid.trim());
			if (u == null) {
				this.validLink = false;
			} else {
				if (u.getConfirmExpireDate() != null && u.getConfirmExpireDate().before(getNow())) {
					this.linkExpired = true;			
					this.users = u;
				} else {
					registerservice.confirmEmail(u);
					this.linkExpired = false;
				}
			}
		}
	}
	
	/**
	 * Re register.
	 */
	public void reRegister() {
		try {
			registerservice.reRegister(this.users);
			super.ajaxRedirect("/login.jsf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Checks if is valid link.
	 *
	 * @return true, if is valid link
	 */
	public boolean isValidLink() {
		return validLink;
	}

	/**
	 * Sets the valid link.
	 *
	 * @param validLink the new valid link
	 */
	public void setValidLink(boolean validLink) {
		this.validLink = validLink;
	}

	/**
	 * Checks if is link expired.
	 *
	 * @return true, if is link expired
	 */
	public boolean isLinkExpired() {
		return linkExpired;
	}

	/**
	 * Sets the link expired.
	 *
	 * @param linkExpired the new link expired
	 */
	public void setLinkExpired(boolean linkExpired) {
		this.linkExpired = linkExpired;
	}

}
