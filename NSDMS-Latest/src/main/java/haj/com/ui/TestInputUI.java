package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class TestUI.
 */
@ManagedBean(name = "testinputUI")
@ViewScoped
public class TestInputUI extends AbstractUI {
	
	private CompanyService companyService = new CompanyService();
	
	private Company valCompany = null;

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
			e.printStackTrace();
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepNew();
	}
	
	public void prepNew() {
		valCompany = new Company();
		valCompany.setCompanyName("Company Name 1");
		valCompany.setTradingName("Trading Name 1");           
		valCompany.setCompanyRegistrationNumber("1963/007352/07");           
		valCompany.setFaxNumber("0111234123");           
		valCompany.setTelNumber("0111234123");           
		valCompany.setEmail("email1@a.com");           
		valCompany.setPayeSDLNumber("N123456789");           
		valCompany.setLevyNumber("L123456789");           
		valCompany.setAccreditationNumber("98765");           
		valCompany.setCompanySiteNumber("43210");           
		valCompany.setCompanyWebsite("www.companywebsite1.com");           
	}
	
	
	public void testCompanyValidation() {
		try {
			companyService.create(valCompany);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Company getValCompany() {
		return valCompany;
	}

	public void setValCompany(Company valCompany) {
		this.valCompany = valCompany;
	}


}