package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.lookup.Country;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.lookup.CountryService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "sheetControllerUI")
@ViewScoped
public class SheetControllerUI extends AbstractUI {

	private CountryService service = new CountryService();
	private List<Country> countries;

	private MandatoryGrantDetailService mandatoryGrantService = new MandatoryGrantDetailService();
	private List<MandatoryGrantDetail> details;

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
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		countries = service.allCountry();
		details = mandatoryGrantService.allMandatoryGrantDetail();
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<MandatoryGrantDetail> getDetails() {
		return details;
	}

	public void setDetails(List<MandatoryGrantDetail> details) {
		this.details = details;
	}

}
