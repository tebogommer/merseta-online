/*-------------------------------------------------------------------*
  Programmer: hendrik    		Date: 19 May 2017                    
  Project: Timesheet									         
 *-------------------------------------------------------------------*/
package haj.com.service;

import java.beans.PropertyDescriptor;
import java.net.URL;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import haj.com.bean.AmountBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CurrencyDAO;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.json.CurrencyBean;
import haj.com.json.Rates;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyService.
 */
public class CurrencyService extends AbstractService {
	
	  /** The Constant logger. */
  	protected static final Log logger = LogFactory.getLog(CurrencyService.class);
	
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	
	/** The dao. */
	private static  CurrencyDAO dao = new CurrencyDAO();
	
	/** The baserate. */
	private static String baserate = "GBP";
	
	/** The base locale. */
	public static Locale baseLocale = Locale.UK;
	
	/** The Constant url. */
	private static final String url = "http://api.fixer.io/latest?base=";
	
	/** The Constant basreRateUrl. */
	private static final String basreRateUrl = url+baserate;
	
	/**
	 * Gets the rates from base rate.
	 *
	 * @return the rates
	 * @throws Exception the exception
	 */
	public static Rates getRates() throws Exception {
		CurrencyBean obj = dao.findByBaseDate(baserate, HAJConstants.sdf2.format(new Date()));
		if (obj==null) {
			obj = mapper.readValue(new URL(basreRateUrl), CurrencyBean.class);
		}
		return  obj.getRates();
	} 

	/**
	 * Gets the all rates .
	 *
	 * @return the all rates
	 * @throws Exception the exception
	 */
	public static CurrencyBean getAllRates() throws Exception {
		return  mapper.readValue(new URL(basreRateUrl), CurrencyBean.class);
	}
	
	/**
	 * Create and Populate rates.
	 */
	public static void  populateRates() {
	    try {
				CurrencyBean cb = CurrencyService.getAllRates();
				cb.getRates().setCurrencyBean(cb);
				dao.create(cb);
			} catch (Exception e) {
				logger.fatal(e);
			}
	}
	
	/**
	 * Convert currency.
	 *
	 * @param amount the amount
	 * @param currency the currency
	 * @return the double
	 */
	public static Double convertCurrency(Double amount, Currency currency) {
		
		if (currency==null) return amount;
		try {
			if (currency.getCurrencyCode().equals(baserate)) {
				return amount;
			}
			else {
				Rates rates = CurrencyService.getRates();
//				Object rate = new PropertyDescriptor(currency.getCurrencyCode(), Rates.class).getReadMethod().invoke(rates);
//				if (rate instanceof Double) {
//					amount = amount.doubleValue() * ((Double)rate).doubleValue();
//				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		return amount;
	}
	

	
	/**
	 * Real time exchange rate from locale to base.
	 *
	 * @param fromLocale the from locale
	 * @return the double
	 * @throws Exception the exception
	 */
	public static Double exhangeRateFromToBase(Locale fromLocale) throws Exception { 
		if (fromLocale.equals(baseLocale)) return 1.0;
		return exhangeRateFromTo(fromLocale,baseLocale);
	}
	
	/**
	 * Real time exchange rate from to.
	 *
	 * @param fromLocale the from locale
	 * @param toLocale the to locale
	 * @return the double
	 * @throws Exception the exception
	 */
	public static Double exhangeRateFromTo(Locale fromLocale, Locale toLocale) throws Exception {
		Currency fromCurrency = Currency.getInstance(fromLocale);
		Currency toCurrency = Currency.getInstance(toLocale);
		if (fromCurrency==null || toCurrency==null) throw new ValidationException("not.convert.to.currency");
		
			if (fromCurrency.getCurrencyCode().equals(baserate) && toCurrency.getCurrencyCode().equals(baserate)) {
				return 1.0;
			}
			else {
				String turl = url + fromCurrency.getCurrencyCode() + "&symbols="+toCurrency.getCurrencyCode();
				CurrencyBean obj = mapper.readValue(new URL(turl), CurrencyBean.class);
				Rates rates = obj.getRates();
//				Object rate = new PropertyDescriptor(toCurrency.getCurrencyCode(), Rates.class).getReadMethod().invoke(rates);
//				if (rate instanceof Double) {
//					return ((Double)rate);
//				}
			}
	
		return 1.0;
	}
	
	
	/**
	 * Real time exchange rate Convert amount from to.
	 *
	 * @param fromLocale the from locale
	 * @param toLocale the to locale
	 * @param amount the amount
	 * @return the double
	 * @throws Exception the exception
	 */
	public static Double convertAmountFromTo(Locale fromLocale, Locale toLocale , Double amount) throws Exception {
		return amount * exhangeRateFromTo(fromLocale, toLocale);
	}
	
	/**
	 * Convert real time exchange rate.
	 *
	 * @param amountBean the amount bean
	 * @throws Exception the exception
	 */
	public static void convert(AmountBean amountBean) throws Exception {
		if (amountBean == null) throw new ValidationException("Instantiate.amount.bean");
		else if (amountBean.getFromLocale() == null)  throw new ValidationException("supply.locale.amount.bean");
		else if (amountBean.getAmount()==null)  throw new ValidationException("supply.amount.amount.bean");
		if  (amountBean.getToLocale() == null) {
			amountBean.setToLocale(Locale.UK);
		}
		
		if (amountBean.getFromLocale().equals(amountBean.getToLocale())) {
			amountBean.setExhangeRate(1.0);
			amountBean.setBaseAmount(amountBean.getAmount());
		}
		else if (amountBean.getFromLocale().equals(baseLocale)) {
			amountBean.setBaseAmount(amountBean.getAmount());
			amountBean.setExhangeRate(exhangeRateFromTo(amountBean.getFromLocale(), amountBean.getToLocale()));
			amountBean.setAmount(amountBean.getBaseAmount()*amountBean.getExhangeRate());
		}
		else {
			amountBean.setExhangeRate(exhangeRateFromTo(amountBean.getFromLocale(), amountBean.getToLocale()));
			amountBean.setBaseAmount(amountBean.getAmount()*amountBean.getExhangeRate());
		}

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			
			
			Locale fromLocale = Locale.UK;
			Locale toLocale = new Locale("en","ZA");
			Double value = 1.0;
			
			AmountBean amountBean =  new AmountBean(fromLocale,toLocale, value);
			CurrencyService.convert(amountBean);
			
			System.out.println(amountBean.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
       System.exit(0);
	}
		
}
