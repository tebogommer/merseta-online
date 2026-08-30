package haj.com.bean;

import java.math.BigInteger;
import java.util.Date;

import haj.com.framework.IDataEntity;

public class MgTransactionsBean implements IDataEntity {

	private BigInteger sarsLevyFileId;
	private BigInteger count;
	private Date dateGenerated;
	private Boolean schemeYearFilterApplied;
	private Integer schemeYear;

	public MgTransactionsBean() {
		super();
	}

	public BigInteger getSarsLevyFileId() {
		return sarsLevyFileId;
	}

	public void setSarsLevyFileId(BigInteger sarsLevyFileId) {
		this.sarsLevyFileId = sarsLevyFileId;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public Boolean getSchemeYearFilterApplied() {
		return schemeYearFilterApplied;
	}

	public void setSchemeYearFilterApplied(Boolean schemeYearFilterApplied) {
		this.schemeYearFilterApplied = schemeYearFilterApplied;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}
	
}