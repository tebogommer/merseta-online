package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyTradeTestEmployerUI.
 */
@ManagedBean(name = "companyTradeTestEmployerUI")
@ViewScoped
public class CompanyTradeTestEmployerUI extends AbstractUI {

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
	 * Initialize method to get all CompanyTradeTestEmployer and prepare a for a create of a new CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CompanyTradeTestEmployer
	 */
	private void runInit() throws Exception {
	
	}

	
}
