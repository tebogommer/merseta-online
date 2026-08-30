package  haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.datamodel.CompanyCrmCloDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;

/**
 * The Class CompanyRegionReportingUI
 */
@ManagedBean(name = "companyRegionReportingUI")
@ViewScoped
public class CompanyRegionReportingUI extends AbstractUI {
	
	/* Service Levels */
	private CompanyService companyService = new CompanyService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> companies;
	
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
		populateCompanyDataModel();
	}
	
	private void populateCompanyDataModel() {
		companies = new CompanyCrmCloDatamodel(getSessionUI());
	}
	
	public void downloadReport(){
		try {
			companyService.downloadCompanyRegionReport(true, null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public LazyDataModel<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(LazyDataModel<Company> companies) {
		this.companies = companies;
	}

}
