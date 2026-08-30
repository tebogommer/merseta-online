package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.MandatoryGrant;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "adminCommandsUI")
@ViewScoped
public class AdminCommandsUI extends AbstractUI {

	/** Service Levels */
	private MandatoryGrantService mandatoryGrantService;
	
	/** Vars */
	private static String accessPassword = "acc!23#";
	private static String actionPassword = "acT!23#";
	
	private String accessPasswordEntry = "";
	private String actionPasswordEntry = "";
	private boolean accessPasswordCorrect = false;
	private boolean actionPasswordCorrect = false;
	
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
	}
	
	public boolean validateAccessPassword(){
		return accessPassword.equals(accessPasswordEntry.trim());
	}
	
	public boolean validateActionPassword(){
		return actionPassword.equals(actionPasswordEntry.trim());
	}
	
	public void accessActions(){
		try {
			actionPasswordCorrect = false;
			if (validateAccessPassword()) {
				accessPasswordCorrect = true;
			}else {
				accessPasswordCorrect = false;
				addWarningMessage("Incorrect Password");
			}
		} catch (Exception e) {
			accessPasswordCorrect = false;
			actionPasswordCorrect = false;
			addErrorMessage(e.getMessage());
		}
	}
	
	/**
	 * JMB 18 10 2018
	 * 
	 * This methods functionality to fix the error on the mandatory grant summarization for the DG verification.
	 * 
	 * The error that happened is when the data was summarized for DG Verification. Where based on a non credit bearing intervention type was selected the user could select from a drop down
	 * the title for the intervention. However what happened is when it was summarized  it did not bring through the code of the drop down. 
	 * 
	 * This is a one time process run to fix all current data for wsp financial year 2019
	 */
	public void runFixToMandatoryGrantInterventionType(){
		try {
			if (mandatoryGrantService == null) {
				mandatoryGrantService = new MandatoryGrantService();
			}
			
			mandatoryGrantService = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Getters and setters */
	public String getAccessPasswordEntry() {
		return accessPasswordEntry;
	}

	public void setAccessPasswordEntry(String accessPasswordEntry) {
		this.accessPasswordEntry = accessPasswordEntry;
	}

	public String getActionPasswordEntry() {
		return actionPasswordEntry;
	}

	public void setActionPasswordEntry(String actionPasswordEntry) {
		this.actionPasswordEntry = actionPasswordEntry;
	}

	public boolean isAccessPasswordCorrect() {
		return accessPasswordCorrect;
	}

	public void setAccessPasswordCorrect(boolean accessPasswordCorrect) {
		this.accessPasswordCorrect = accessPasswordCorrect;
	}

	public boolean isActionPasswordCorrect() {
		return actionPasswordCorrect;
	}

	public void setActionPasswordCorrect(boolean actionPasswordCorrect) {
		this.actionPasswordCorrect = actionPasswordCorrect;
	}
	
}
