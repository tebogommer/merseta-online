package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.validator.constraints.Email;

import haj.com.entity.Blank;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.RegisterService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "resendConfirmEmailUI")
@ViewScoped
public class ResendConfirmEmailUI extends AbstractUI {

	private RegisterService registerService = new RegisterService();
	private Users dbUser;
	@Email(message="Not a valid email address")
	private String email;
	
	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;
	
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
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
	  getSearchUserPassportOrIdUI().setObject(this);
	  this.initUser();
	}
	
	public void initUser() throws Exception {
		
		  this.dbUser = new Users();
		  this.dbUser.setDoneSearch(false);
		}
		
	
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.dbUser = (Users)object;
				registerService.resendChecks(this.dbUser);
			} 
			else {
				throw new Exception("Not a valid search");
			}
		} catch (Exception e) {
			 this.dbUser.setDoneSearch(false);
			 addErrorMessage(e.getMessage(), e);
			 runClientSideExecute("PF('resendDLG').hide()");
		}
	}

	
	public void validateResend() {
		try {
		    registerService.validateResend(dbUser,email);
		    addInfoMessage("Your confirmation has been mailed to you.");   
		    runClientSideExecute("PF('resendDLG').hide()");
		} catch (ValidationErrorException e) {
			 addInfoMessage(e.getMessage());
			 runClientSideExecute("PF('resendDLG').hide()");
		} catch (Exception e) {
			 addErrorMessage(e.getMessage(), e);
		}
	}
	
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	public Users getDbUser() {
		return dbUser;
	}

	public void setDbUser(Users dbUser) {
		this.dbUser = dbUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
