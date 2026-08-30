package haj.com.bean;

import java.io.Serializable;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class AmountBean.
 */
public class AmountBean implements Serializable {
	
	
	/** The from locale. */
	private Locale fromLocale;
	
	/** The to locale. */
	private Locale toLocale;
	
	/** The amount. */
	private Double amount;
	
	/** The base amount. */
	private Double baseAmount;
    
    /** The exhange rate. */
    private Double exhangeRate;
    
    
	/**
	 * Instantiates a new amount bean.
	 */
	public AmountBean() {
		super();
	}

	/**
	 * Instantiates a new amount bean.
	 *
	 * @param fromLocale the from locale
	 * @param amount the amount
	 */
	public AmountBean(Locale fromLocale, Double amount) {
		super();
		this.fromLocale = fromLocale;
		this.amount = amount;
	}



	/**
	 * Instantiates a new amount bean.
	 *
	 * @param fromLocale the from locale
	 * @param toLocale the to locale
	 * @param amount the amount
	 */
	public AmountBean(Locale fromLocale, Locale toLocale, Double amount) {
		super();
		this.fromLocale = fromLocale;
		this.toLocale = toLocale;
		this.amount = amount;
	}



	/**
	 * Gets the from locale.
	 *
	 * @return the from locale
	 */
	public Locale getFromLocale() {
		return fromLocale;
	}
	
	/**
	 * Sets the from locale.
	 *
	 * @param fromLocale the new from locale
	 */
	public void setFromLocale(Locale fromLocale) {
		this.fromLocale = fromLocale;
	}
	
	/**
	 * Gets the to locale.
	 *
	 * @return the to locale
	 */
	public Locale getToLocale() {
		return toLocale;
	}
	
	/**
	 * Sets the to locale.
	 *
	 * @param toLocale the new to locale
	 */
	public void setToLocale(Locale toLocale) {
		this.toLocale = toLocale;
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
	 * Gets the base amount.
	 *
	 * @return the base amount
	 */
	public Double getBaseAmount() {
		return baseAmount;
	}
	
	/**
	 * Sets the base amount.
	 *
	 * @param baseAmount the new base amount
	 */
	public void setBaseAmount(Double baseAmount) {
		this.baseAmount = baseAmount;
	}

	/**
	 * Gets the exhange rate.
	 *
	 * @return the exhange rate
	 */
	public Double getExhangeRate() {
		return exhangeRate;
	}
	
	/**
	 * Sets the exhange rate.
	 *
	 * @param exhangeRate the new exhange rate
	 */
	public void setExhangeRate(Double exhangeRate) {
		this.exhangeRate = exhangeRate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AmountBean [fromLocale=" + fromLocale + ", toLocale=" + toLocale + ", amount=" + amount
				+ ", baseAmount=" + baseAmount + ", exhangeRate=" + exhangeRate + "]";
	}


}
