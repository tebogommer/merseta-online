package haj.com.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Wsp;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeesService;
import haj.com.service.JasperService;

// TODO: Auto-generated Javadoc
/**
 * The Class JasperPracUI.
 */
@ManagedBean(name = "jasperPracUI")
@ViewScoped
public class JasperPracUI extends AbstractUI {

	/** The jasper service. */
	private JasperService jasperService = new JasperService();
	
	/** The params. */
	private Map<String, Object> params;

	/** The number of. */
	private Integer numberOf = 0;
	
	/** The employees service. */
	private EmployeesService employeesService = new EmployeesService();

	/** The wsp. */
	private Wsp wsp;
	
	/** The to age. */
	private Integer fromAge, toAge;
	
	/** The wsp id. */
	private Long wspId;
	
	/** The employed un employed enum. */
	private EmployedUnEmployedEnum employedUnEmployedEnum;
	
	/** The completed planned enum. */
	private CompletedPlannedEnum completedPlannedEnum;
	
	/** The pivot non pivot enum. */
	private PivotNonPivotEnum pivotNonPivotEnum;
	
	/** The equity. */
	private Equity equity;
	
	/** The gender. */
	private Gender gender;

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
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {

	}

	/**
	 * Do me birth age count.
	 */
	public void doMeBirthAgeCount() {
		try {
			this.numberOf = 0;
			this.numberOf = (employeesService.countEmployeesTrainingByAgeRangeForWsp(fromAge, toAge, wspId, employedUnEmployedEnum, completedPlannedEnum, pivotNonPivotEnum)).intValue();
			System.out.println(this.numberOf);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Do me gender equity count.
	 */
	public void doMeGenderEquityCount() {
		try {
			this.numberOf = 0;
			this.numberOf = (employeesService.countEmployeesTrainingGenderByEquityForWsp(equity.getId(), gender.getId(), wspId, employedUnEmployedEnum, completedPlannedEnum, pivotNonPivotEnum)).intValue();
			System.out.println(this.numberOf);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Jasper pdf.
	 */
	public void jasperPdf() {
		params = new HashMap<String, Object>();
		/*
		 * params.put("previousContract", true); String fname =
		 * "QuoteAMHydraulics.pdf"; try {
		 * jasperService.createReportFromDBtoServletOutputStream(
		 * "amhyrdaQuote.jasper", params, fname); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}

	/**
	 * Jasper excel.
	 */
	public void jasperExcel() {

	}

	/**
	 * Gets the number of.
	 *
	 * @return the number of
	 */
	public Integer getNumberOf() {
		return numberOf;
	}

	/**
	 * Sets the number of.
	 *
	 * @param numberOf the new number of
	 */
	public void setNumberOf(Integer numberOf) {
		this.numberOf = numberOf;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the from age.
	 *
	 * @return the from age
	 */
	public Integer getFromAge() {
		return fromAge;
	}

	/**
	 * Sets the from age.
	 *
	 * @param fromAge the new from age
	 */
	public void setFromAge(Integer fromAge) {
		this.fromAge = fromAge;
	}

	/**
	 * Gets the to age.
	 *
	 * @return the to age
	 */
	public Integer getToAge() {
		return toAge;
	}

	/**
	 * Sets the to age.
	 *
	 * @param toAge the new to age
	 */
	public void setToAge(Integer toAge) {
		this.toAge = toAge;
	}

	/**
	 * Gets the employed un employed enum.
	 *
	 * @return the employed un employed enum
	 */
	public EmployedUnEmployedEnum getEmployedUnEmployedEnum() {
		return employedUnEmployedEnum;
	}

	/**
	 * Sets the employed un employed enum.
	 *
	 * @param employedUnEmployedEnum the new employed un employed enum
	 */
	public void setEmployedUnEmployedEnum(EmployedUnEmployedEnum employedUnEmployedEnum) {
		this.employedUnEmployedEnum = employedUnEmployedEnum;
	}

	/**
	 * Gets the completed planned enum.
	 *
	 * @return the completed planned enum
	 */
	public CompletedPlannedEnum getCompletedPlannedEnum() {
		return completedPlannedEnum;
	}

	/**
	 * Sets the completed planned enum.
	 *
	 * @param completedPlannedEnum the new completed planned enum
	 */
	public void setCompletedPlannedEnum(CompletedPlannedEnum completedPlannedEnum) {
		this.completedPlannedEnum = completedPlannedEnum;
	}

	/**
	 * Gets the pivot non pivot enum.
	 *
	 * @return the pivot non pivot enum
	 */
	public PivotNonPivotEnum getPivotNonPivotEnum() {
		return pivotNonPivotEnum;
	}

	/**
	 * Sets the pivot non pivot enum.
	 *
	 * @param pivotNonPivotEnum the new pivot non pivot enum
	 */
	public void setPivotNonPivotEnum(PivotNonPivotEnum pivotNonPivotEnum) {
		this.pivotNonPivotEnum = pivotNonPivotEnum;
	}

	/**
	 * Gets the wsp id.
	 *
	 * @return the wsp id
	 */
	public Long getWspId() {
		return wspId;
	}

	/**
	 * Sets the wsp id.
	 *
	 * @param wspId the new wsp id
	 */
	public void setWspId(Long wspId) {
		this.wspId = wspId;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
