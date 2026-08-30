package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.LeviesPaidBean;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "levySummaryReportUI")
@ViewScoped
public class LevySummaryReportUI extends AbstractUI {

	private SarsFilesService service = new SarsFilesService();
	private Integer schemeYear;
	private LeviesPaidBean leviesPaidBean;
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

	public void summaryPerSchemeYear() {
		try {
			leviesPaidBean = service.summaryBySchemeYear(schemeYear);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	public LeviesPaidBean getLeviesPaidBean() {
		return leviesPaidBean;
	}

	public void setLeviesPaidBean(LeviesPaidBean leviesPaidBean) {
		this.leviesPaidBean = leviesPaidBean;
	}
	
}
