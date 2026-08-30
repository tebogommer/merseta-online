
package haj.com.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantBean.
 */
public class EmployeeReportBean {

	private String chamberName;
	private String companySize;
	private String companyName;
	private String province;
	private String region;
	private Integer grantYear;
	private BigDecimal numberOfCompanyActive;
	private BigDecimal numberOfCompanyInactive;
	private BigInteger levyPaying;
	private BigInteger notLevyPaying;
	private Integer grantType;

	public EmployeeReportBean() {
		super();
	}

	public EmployeeReportBean(String chamberName, String companySize, BigDecimal numberOfCompanyActive, BigDecimal numberOfCompanyInactive) {
		super();
		this.chamberName = chamberName;
		this.companySize = companySize;
		this.numberOfCompanyActive = numberOfCompanyActive;
		this.numberOfCompanyInactive = numberOfCompanyInactive;

	}
	
	public EmployeeReportBean(String chamberName, String companySize, BigInteger levyPaying, BigInteger notLevyPaying) {
		super();
		this.chamberName = chamberName;
		this.companySize = companySize;
		this.levyPaying = levyPaying;
		this.notLevyPaying = notLevyPaying;
		
	}
	
	public EmployeeReportBean(String companyName, Integer grantYear, Integer grantType, String province, String region) {
		super();
		this.companyName = companyName;
		this.grantYear = grantYear;
		this.grantType = grantType;
		this.province = province;
		this.region = region;
		
	}

	/** Getters and setters */

	public String getChamberName() {
		return chamberName;
	}

	public void setChamberName(String chamberName) {
		this.chamberName = chamberName;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public BigDecimal getNumberOfCompanyActive() {
		return numberOfCompanyActive;
	}

	public void setNumberOfCompanyActive(BigDecimal numberOfCompanyActive) {
		this.numberOfCompanyActive = numberOfCompanyActive;
	}

	public BigDecimal getNumberOfCompanyInactive() {
		return numberOfCompanyInactive;
	}

	public void setNumberOfCompanyInactive(BigDecimal numberOfCompanyInactive) {
		this.numberOfCompanyInactive = numberOfCompanyInactive;
	}

	public BigInteger getLevyPaying() {
		return levyPaying;
	}

	public void setLevyPaying(BigInteger levyPaying) {
		this.levyPaying = levyPaying;
	}

	public BigInteger getNotLevyPaying() {
		return notLevyPaying;
	}

	public void setNotLevyPaying(BigInteger notLevyPaying) {
		this.notLevyPaying = notLevyPaying;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}

	}