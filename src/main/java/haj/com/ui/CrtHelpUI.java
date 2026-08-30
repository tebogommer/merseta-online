package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.OnScreenHelpService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "crtHelpUI")
@RequestScoped
public class CrtHelpUI extends AbstractUI {

    private boolean showHelp;
    private String help;
    
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
		showHelp = false;
		help = null;
		help = OnScreenHelpService.instance().findHelp(getUri());
		if (help!=null) showHelp =true;
	}

	public boolean isShowHelp() {
		return showHelp;
	}

	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	
}
