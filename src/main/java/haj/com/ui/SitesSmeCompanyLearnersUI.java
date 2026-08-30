package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.LearnersMentorBean;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "sitesSmeCompanyLearnersUI")
@ViewScoped
public class SitesSmeCompanyLearnersUI extends AbstractUI {

	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private List<LearnersMentorBean> companyLearnersList;
	private Company company;

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
		if(getSessionUI().isEmployee() || getSessionUI().isAdmin()) {
			populateLearnersMentorBean();
		}		
	}

	public void populateCompanyLearnersMentorBean() throws Exception {
		companyLearnersList=companyLearnersService.generateLearnersMentorBean(company.getId());
	}
	
	private void populateLearnersMentorBean() throws Exception {
		companyLearnersList=companyLearnersService.generateLearnersMentorBean();
	}

	public List<LearnersMentorBean> getCompanyLearnersList() {
		return companyLearnersList;
	}

	public void setCompanyLearnersList(List<LearnersMentorBean> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}	
}
