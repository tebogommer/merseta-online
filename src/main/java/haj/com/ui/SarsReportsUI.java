package  haj.com.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyDetailsService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsReportsUI.
 */
@ManagedBean(name = "sarsReportsUI")
@ViewScoped
public class SarsReportsUI extends AbstractUI {


	/** The service. */
	private SarsFilesService service = new SarsFilesService();
	private SarsLevyDetailsService sarsLevyDetailsService = new SarsLevyDetailsService();

	/** The sars employer detail service. */
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();

	/** The levies. */
	private List<SarsLevyDetails> levies;

	/** The ref no. */
	private String refNo;

    /** The totals. */
    private SarsLevyDetails totals;


    private SarsLevyDetails summary;

    /** The sars employer detail. */
    private SarsEmployerDetail sarsEmployerDetail;

    /** The to date. */
    private Date fromDate,toDate;

    private Integer schemeYear;

    public String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;
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
		toDate = GenericUtility.getEndOfDay(new Date());
		this.fromDate = GenericUtility.getFirstDayOfYear(toDate);
	}




	/**
	 * Gets the ref no.
	 *
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 *
	 * @param refNo the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}


	/**
	 * By LSDL.
	 */
	public void byLSDL() {
		try {
			//levies = service.sdlSummary(refNo);
			levies = service.sdlSummaryByRefAndDate(refNo, fromDate, toDate);
			if (levies.isEmpty()) addInfoMessage("SDL number does not exist");
			else {
				this.sarsEmployerDetail = sarsEmployerDetailService.findSDL(refNo);
				doTotals();


		        super.runClientSideExecute("doChart()");
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Do totals.
	 */
	private void doTotals() {
		this.totals = new SarsLevyDetails();
		this.totals.setAdminLevy(BigDecimal.valueOf(0.0));
		this.totals.setDiscretionaryLevy(BigDecimal.valueOf(0.0));
		this.totals.setInterest(BigDecimal.valueOf(0.0));
		this.totals.setMandatoryLevy(BigDecimal.valueOf(0.0));
		this.totals.setPenalty(BigDecimal.valueOf(0.0));
		this.totals.setTotal(BigDecimal.valueOf(0.0));
		this.levies.forEach(a-> {
			totals.setAdminLevy(BigDecimal.valueOf(totals.getAdminLevy().doubleValue() + (a.getAdminLevy()==null?0.0:a.getAdminLevy().doubleValue())));
			totals.setDiscretionaryLevy(BigDecimal.valueOf(totals.getDiscretionaryLevy().doubleValue()  + (a.getDiscretionaryLevy()==null?0.0:a.getDiscretionaryLevy().doubleValue() )));
			totals.setInterest(BigDecimal.valueOf(totals.getInterest().doubleValue()  + (a.getInterest()==null?0.0:a.getInterest().doubleValue() )));
			totals.setMandatoryLevy(BigDecimal.valueOf(totals.getMandatoryLevy().doubleValue()  + (a.getMandatoryLevy()==null?0.0:a.getMandatoryLevy().doubleValue() )));
			totals.setPenalty(BigDecimal.valueOf(totals.getPenalty().doubleValue()  + (a.getPenalty()==null?0.0:a.getPenalty().doubleValue() )));
			totals.setTotal(BigDecimal.valueOf(totals.getTotal().doubleValue()  + (a.getTotal()==null?0.0:a.getTotal().doubleValue() )));

		});

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
	 * Gets the totals.
	 *
	 * @return the totals
	 */
	public SarsLevyDetails getTotals() {
		return totals;
	}

	/**
	 * Sets the totals.
	 *
	 * @param totals the new totals
	 */
	public void setTotals(SarsLevyDetails totals) {
		this.totals = totals;
	}

	/**
	 * Gets the sars employer detail.
	 *
	 * @return the sars employer detail
	 */
	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	/**
	 * Sets the sars employer detail.
	 *
	 * @param sarsEmployerDetail the new sars employer detail
	 */
	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}

	/**
	 * Gets the from date.
	 *
	 * @return the from date
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the from date.
	 *
	 * @param fromDate the new from date
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Gets the to date.
	 *
	 * @return the to date
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * Sets the to date.
	 *
	 * @param toDate the new to date
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}


	public void bySdlAndSchemeYear() {
		try {
			//levies = service.sdlSummary(refNo);
			levies = service.sdlSummaryByRefAndSchemeYearGroupByArrivalDate(refNo, schemeYear);
			if (levies.isEmpty() ||  levies.get(0).getMandatoryLevy()==null) {
				addInfoMessage("No data for this criterea");
				//levies.remove(0);
			}
			else {
				this.sarsEmployerDetail = sarsEmployerDetailService.findSDL(refNo);
				doTotals();


		        super.runClientSideExecute("doChart()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void byChamberAndSchemeYear() {
		try {
			//levies = service.sdlSummary(refNo);
			levies = sarsLevyDetailsService.leviesBySchemeYearByChamber(schemeYear);
			if (levies.isEmpty()) addInfoMessage("No data for this criterea");
			else {

				doTotals();


		        super.runClientSideExecute("doChart()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public SarsLevyDetails getSummary() {
		return summary;
	}

	public void setSummary(SarsLevyDetails summary) {
		this.summary = summary;
	}
}
