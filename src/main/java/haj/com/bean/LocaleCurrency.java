package haj.com.bean;

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class LocaleCurrency.
 */
public class LocaleCurrency implements Serializable {

	/** The locale. */
	private Locale locale;
	
	/** The currency. */
	private Currency currency;
	
	
	
	/**
	 * Instantiates a new locale currency.
	 *
	 * @param locale the locale
	 */
	public LocaleCurrency(Locale locale) {
		super();
		this.locale = locale;
	}
	
	/**
	 * Instantiates a new locale currency.
	 */
	public LocaleCurrency() {
		super();
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
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public Currency getCurrency() {
		if (locale!=null) {
			currency = Currency.getInstance(locale);
		}
		return currency;
	}
}
