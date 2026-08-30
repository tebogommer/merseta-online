package  haj.com.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.CompanyService;
import haj.com.service.SarsEmployerDetailService;
import haj.com.service.SarsFilesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsReportsUI.
 */
@ManagedBean(name = "companySARSLevies")
@ViewScoped
public class CompanySARSLevies extends AbstractUI {

	
	/** The service. */
	private SarsFilesService service = new SarsFilesService();
	
	private CompanyService companyService = new CompanyService();
	/** The sars employer detail service. */
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();
	
	/** The levies. */
	private List<SarsLevyDetails> levies; 
	
	/** The ref no. */
	private String refNo;

    /** The totals. */
    private SarsLevyDetails totals;
    
    /** The sars employer detail. */
    private SarsEmployerDetail sarsEmployerDetail;
    
    /** The to date. */
    private Date fromDate,toDate;
    
	private Company company;
    
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
		this.fromDate = GenericUtility.getStartOfDay(SARSConstants.sdf.parse("200001"));
 		byCompany();
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
	
	private void byCompany() {
		try {	
		if (super.getParameter("id", false) != null) {
			company = companyService.findByGUID(super.getParameter("id", false).toString());
			if (this.company!=null && !StringUtils.isBlank(this.company.getLevyNumber())) {
				this.refNo = this.company.getLevyNumber();
			}
			byLSDL();
		}
		
		
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
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
		
		if (totals.getAdminLevy().doubleValue() < 0) totals.setAdminLevy(BigDecimal.valueOf( ( totals.getAdminLevy().doubleValue() *-1 )));
		if (totals.getDiscretionaryLevy().doubleValue() < 0) totals.setDiscretionaryLevy(BigDecimal.valueOf( ( totals.getDiscretionaryLevy().doubleValue() *-1 )));
		if (totals.getInterest().doubleValue() < 0) totals.setInterest(BigDecimal.valueOf( ( totals.getInterest().doubleValue() *-1 )));
		if (totals.getMandatoryLevy().doubleValue() < 0) totals.setMandatoryLevy(BigDecimal.valueOf( ( totals.getMandatoryLevy().doubleValue() *-1 )));
		if (totals.getPenalty().doubleValue() < 0) totals.setPenalty(BigDecimal.valueOf( ( totals.getPenalty().doubleValue() *-1 )));
		if (totals.getTotal().doubleValue() < 0) totals.setTotal(BigDecimal.valueOf( ( totals.getTotal().doubleValue() *-1 )));
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
