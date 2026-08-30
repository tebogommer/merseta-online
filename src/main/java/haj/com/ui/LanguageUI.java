package haj.com.ui;


import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import haj.com.bean.LocaleCurrency;
import haj.com.constants.HAJConstants;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
 
// TODO: Auto-generated Javadoc
/**
 * The Class LanguageUI.
 */
@ManagedBean(name="languageUI")
@SessionScoped
public class LanguageUI extends AbstractUI {

/** The Constant serialVersionUID. */
private static final long serialVersionUID = 1L;
	
	/** The locale code. */
	private String localeCode ;
	
	/** The locale code. */
	private String currentLang ;
	/** The amount. */
	private Double amount = 30.99;
	
	/** The diplay amount. */
	private Double diplayAmount;
	
	/** The locale. */
	private Locale locale;// = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	
	/** The currency. */
	private Currency currency ;
	
	/** The locale currency. */
	private List<LocaleCurrency> localeCurrency;
	
	/** The flag. */
	private String flag;

	
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
		flag = "flag-icon-gb";
	//	locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
		this.currentLang = "English";
		if (locale.getLanguage().equals(locale.ENGLISH.getLanguage())) {
			locale = Locale.UK;
		}
		
		localeCode = locale.getCountry();
		//doCurrency();
		//doLocaleCurrency();
	}
	

	
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public Locale getLocale() {
	        return locale;
	 }
	

	
	
	/**
	 * Gets the countries in map.
	 *
	 * @return the countries in map
	 */
	public Map<String, Object> getCountriesInMap() {
		return HAJConstants.countries;
	}

	
	/**
	 * Gets the locale code.
	 *
	 * @return the locale code
	 */
	public String getLocaleCode() {
		return localeCode;
	}


	/**
	 * Sets the locale code.
	 *
	 * @param localeCode the new locale code
	 */
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	/**
	 * Country locale code changed.
	 *
	 * @param e the e
	 */
	public void countryLocaleCodeChanged(ValueChangeEvent e){
		
		String newLocaleValue = e.getNewValue().toString();
		
		//loop a map to compare the locale code
        for (Map.Entry<String, Object> entry : HAJConstants.countries.entrySet()) {
        
        	if(entry.getValue().toString().equals(newLocaleValue)){
        		this.currentLang = entry.getKey();
        		FacesContext.getCurrentInstance()
        			.getViewRoot().setLocale((Locale)entry.getValue());
        		this.locale =(Locale)entry.getValue();
        		
        		if (HAJConstants.flags.get(this.locale) == null) {
        			flag = "flag-icon-gb";   
        		}
        		else {
        			this.flag = HAJConstants.flags.get(this.locale);
        		}
         		//doCurrency();
        		
        	}
        }

	}


	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}


	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency the new currency
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Gets the diplay amount.
	 *
	 * @return the diplay amount
	 */
	public Double getDiplayAmount() {
		return diplayAmount;
	}

	/**
	 * Sets the diplay amount.
	 *
	 * @param diplayAmount the new diplay amount
	 */
	public void setDiplayAmount(Double diplayAmount) {
		this.diplayAmount = diplayAmount;
	}

	/**
	 * Gets the locale currency.
	 *
	 * @return the locale currency
	 */
	public List<LocaleCurrency> getLocaleCurrency() {
		return localeCurrency;
	}

	/**
	 * Sets the locale currency.
	 *
	 * @param localeCurrency the new locale currency
	 */
	public void setLocaleCurrency(List<LocaleCurrency> localeCurrency) {
		this.localeCurrency = localeCurrency;
	}

	/**
	 * Gets the flag.
	 *
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * Sets the flag.
	 *
	 * @param flag the new flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCurrentLang() {
		return currentLang;
	}

	public void setCurrentLang(String currentLang) {
		this.currentLang = currentLang;
	}
	
	



}
