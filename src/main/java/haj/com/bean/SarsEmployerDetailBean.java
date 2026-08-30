
package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;

// TODO: Auto-generated Javadoc
/**
 * The Class Codes.
 */
@Generated("org.jsonschema2pojo")
public class SarsEmployerDetailBean {

	private BigInteger id; 
	private String companyRegistrationNumber;
	private Date createDate;
	private String refNo;
	private String registeredNameOfEntity;
	private String sicCode2;
	private String tradingName; 
	private String tradingStatus;
	private BigInteger sarsFiles;
	private int noEmployesAccordingToSARS;
	private String chamberPassed;
	
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}
	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRegisteredNameOfEntity() {
		return registeredNameOfEntity;
	}
	public void setRegisteredNameOfEntity(String registeredNameOfEntity) {
		this.registeredNameOfEntity = registeredNameOfEntity;
	}
	public String getSicCode2() {
		return sicCode2;
	}
	public void setSicCode2(String sicCode2) {
		this.sicCode2 = sicCode2;
	}
	public String getTradingName() {
		return tradingName;
	}
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}
	public String getTradingStatus() {
		return tradingStatus;
	}
	public void setTradingStatus(String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}
	public BigInteger getSarsFiles() {
		return sarsFiles;
	}
	public void setSarsFiles(BigInteger sarsFiles) {
		this.sarsFiles = sarsFiles;
	}
	public int getNoEmployesAccordingToSARS() {
		return noEmployesAccordingToSARS;
	}
	public void setNoEmployesAccordingToSARS(int noEmployesAccordingToSARS) {
		this.noEmployesAccordingToSARS = noEmployesAccordingToSARS;
	}
	public String getChamberPassed() {
		return chamberPassed;
	}
	public void setChamberPassed(String chamberPassed) {
		this.chamberPassed = chamberPassed;
	} 
}
