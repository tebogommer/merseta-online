package haj.com.bean;

import java.io.Serializable;

/**
 * The Class CompanyStatusVsSarsBean.
 * Used for counts in native sql quiries
 */
public class CompanyStatusVsSarsBean implements Serializable {

	
	private String entityId;
	private String companyName;
	private String tradingName;
	private String nsdmsStatus;
	private String sarsTradingStatus;
	
	public CompanyStatusVsSarsBean() {
		super();	
	}

	/* Getters and setters */
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getNsdmsStatus() {
		return nsdmsStatus;
	}

	public void setNsdmsStatus(String nsdmsStatus) {
		this.nsdmsStatus = nsdmsStatus;
	}

	public String getSarsTradingStatus() {
		return sarsTradingStatus;
	}

	public void setSarsTradingStatus(String sarsTradingStatus) {
		this.sarsTradingStatus = sarsTradingStatus;
	}
	
	
}