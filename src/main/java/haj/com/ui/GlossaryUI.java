package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.lookup.Glosssary;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.GlosssaryService;

// TODO: Auto-generated Javadoc
/**
 * The Class GlossaryUI.
 */
@ManagedBean(name = "glossaryUI")
@ViewScoped
public class GlossaryUI extends AbstractUI {

	/** The service. */
	private GlosssaryService service = new  GlosssaryService();
	
	/** The glosssary. */
	private Glosssary glosssary;
	
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
		this.glosssary = service.findByKey(1l);
		if (this.glosssary==null) {
			  this.glosssary = new Glosssary();
		}
	 
	}

	/**
	 * Save.
	 */
	public void save() {
		
		try {
			service.create(glosssary);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}

	/**
	 * Gets the glosssary.
	 *
	 * @return the glosssary
	 */
	public Glosssary getGlosssary() {
		return glosssary;
	}

	/**
	 * Sets the glosssary.
	 *
	 * @param glosssary the new glosssary
	 */
	public void setGlosssary(Glosssary glosssary) {
		this.glosssary = glosssary;
	}
	

	
}
