
package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

import haj.com.entity.enums.GrantTypeEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
public class GrantListGenerationBean {

	/** lnumber */
	private String lNumber;
	
	/** Total */
	private BigInteger total ;
	
	private String tradingName;
	private GrantTypeEnum grandType;
	private Date monthFrom;
	private Date monthTo;
	private Integer  grantAmount;
	private String invoiceReturn;
	private Double grandPerecntage;
	private String levyStatus;	

	public String getlNumber() {
		return lNumber;
	}

	public void setlNumber(String lNumber) {
		this.lNumber = lNumber;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public GrantTypeEnum getGrandType() {
		return grandType;
	}

	public void setGrandType(GrantTypeEnum grandType) {
		this.grandType = grandType;
	}

	public Date getMonthFrom() {
		return monthFrom;
	}

	public void setMonthFrom(Date monthFrom) {
		this.monthFrom = monthFrom;
	}

	public Date getMonthTo() {
		return monthTo;
	}

	public void setMonthTo(Date monthTo) {
		this.monthTo = monthTo;
	}

	public Integer getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(Integer grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getInvoiceReturn() {
		return invoiceReturn;
	}

	public void setInvoiceReturn(String invoiceReturn) {
		this.invoiceReturn = invoiceReturn;
	}

	public Double getGrandPerecntage() {
		return grandPerecntage;
	}

	public void setGrandPerecntage(Double grandPerecntage) {
		this.grandPerecntage = grandPerecntage;
	}

	public String getLevyStatus() {
		return levyStatus;
	}

	public void setLevyStatus(String levyStatus) {
		this.levyStatus = levyStatus;
	}
	
	
}
