package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.LookupMenuBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LookupMenuService;

// TODO: Auto-generated Javadoc
/**
 * The Class LookupMenuUI.
 */
@ManagedBean(name = "lookupMenuUI")
@ViewScoped
public class LookupMenuUI extends AbstractUI {

	/** The service. */
	private LookupMenuService service = new LookupMenuService();
	
	/** The menus. */
	private List<LookupMenuBean> menus;

	
    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
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
		info();
	}

	/**
	 * Info.
	 */
	public void info() {
		try {
			menus = 	service.prepMenu();
			
		} catch (Exception e) {
			logger.fatal(e);
		}
	}


	/**
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public List<LookupMenuBean> getMenus() {
		return menus;
	}


	/**
	 * Sets the menus.
	 *
	 * @param menus the new menus
	 */
	public void setMenus(List<LookupMenuBean> menus) {
		this.menus = menus;
	}
	
}
