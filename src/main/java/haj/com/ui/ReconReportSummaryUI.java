package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Pattern;

import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class ReconReportSummaryUI.
 */
@ManagedBean(name = "reconReportSummaryUI")
@ViewScoped
public class ReconReportSummaryUI extends AbstractUI {

	/** The service. */
	private 	 SarsFilesService service = new SarsFilesService();
	
	/** The levies. */
	private List<SarsLevyDetails> levies; 
	
	/** The by SDL. */
	private boolean bySDL;	
	
	private SarsLevyDetails sarsLevyDetails;
	
	/** The levy number. */
	@Pattern(regexp = "L\\d\\d\\d7\\d\\d\\d\\d\\d",message="Invalid Levy number")
	private String levyNumber;
	
	private SarsEmployerDetail sarsEmployerDetail;
	
    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			bySDL=true;
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
		if (super.getParameter("summary", false) != null) {
		   bySDL = false;
 	     //  levies = service.reconLevySummary();
		   this.sarsLevyDetails = new SarsLevyDetails();
 	       
		}
	}

	/**
	 * Gets the levies.
	 *
	 * @return the levies
	 */
	public List<SarsLevyDetails> getLevies() {
		return levies;
	}

	/**
	 * Sets the levies.
	 *
	 * @param levies the new levies
	 */
	public void setLevies(List<SarsLevyDetails> levies) {
		this.levies = levies;
	}

	/**
	 * Checks if is by SDL.
	 *
	 * @return true, if is by SDL
	 */
	public boolean isBySDL() {
		return bySDL;
	}

	/**
	 * Sets the by SDL.
	 *
	 * @param bySDL the new by SDL
	 */
	public void setBySDL(boolean bySDL) {
		this.bySDL = bySDL;
	}

	/**
	 * Gets the levy number.
	 *
	 * @return the levy number
	 */
	public String getLevyNumber() {
		return levyNumber;
	}

	/**
	 * Sets the levy number.
	 *
	 * @param levyNumber the new levy number
	 */
	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	
	/**
	 * By levy number.
	 */
	public void byLevyNumber() {
		try {
			 this.sarsEmployerDetail = service.findBySDL(levyNumber);
			 levies = service.reconLevySummary(levyNumber);
			 if (levies.size()==0) addErrorMessage("No data for levy number: "+this.levyNumber);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void contribSummary() {
		try {
			 service.processEmployerTotals(sarsLevyDetails);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void empDetail() {
		try {
		//	 service.processEmployerTotals(sarsLevyDetails);
		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}

	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}
	
}
