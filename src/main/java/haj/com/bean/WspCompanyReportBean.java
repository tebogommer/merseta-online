package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * The Class WspCompanyReportBean.
 * Used for counts in native sql quiries
 */
public class WspCompanyReportBean implements Serializable {

	
	private String entityId;
	private String companyName;
	private String tradingName;
	
	private Integer grantYear;
	private String grantType;
	private String grantStatus;
	private String province;
	private String region;
	
	private BigInteger countSubmissions;
	
	// Added for JIRA #1330
	private Date grantSignOffDate;
	private String sdfType;
	
	public WspCompanyReportBean() {
		super();	
	}
	
	/*
	 * Report One Values
	 * - entityId
	 * - comapnyName
	 * - tradingName
	 * - grantYear
	 * - grantType
	 * - grantStatus
	 * - province
	 * - region
	 */
	
	/*
	 * Report Two Values:
	 * - entityId
	 * - comapnyName
	 * - tradingName
	 * - province
	 * - region
	 * - countSubmissions
	 */

	/* Getters and Setters */
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getGrantStatus() {
		return grantStatus;
	}

	public void setGrantStatus(String grantStatus) {
		this.grantStatus = grantStatus;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public BigInteger getCountSubmissions() {
		return countSubmissions;
	}

	public void setCountSubmissions(BigInteger countSubmissions) {
		this.countSubmissions = countSubmissions;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	// Added for JIRA #1330
	public Date getGrantSignOffDate() {
		return grantSignOffDate;
	}

	// Added for JIRA #1330
	public void setGrantSignOffDate(Date grantSignOffDate) {
		this.grantSignOffDate = grantSignOffDate;
	}
	
	// Added for JIRA #1330
	public String getSdfType() {
		return sdfType;
	}

	// Added for JIRA #1330
	public void setSdfType(String sdfType) {
		this.sdfType = sdfType;
	}

}