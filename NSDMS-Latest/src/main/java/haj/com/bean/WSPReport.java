package haj.com.bean;

import java.math.BigInteger;

import haj.com.framework.IDataEntity;

public class WSPReport implements IDataEntity {

	private BigInteger reportNumber;
	private String wspType;
	private BigInteger smallCount;
	private BigInteger midCount;
	private BigInteger largeCount;
	private BigInteger total;

	public WSPReport() {
		super();
	}
	
	/*
	 * Used for a specific report: haj.com.service.DashBoardService.generateReportGrantStatusReportByCompanySizes(Integer)
	 * @param wspType
	 */
	public WSPReport(String wspType) {
		super();
		this.wspType = wspType;
		this.smallCount = BigInteger.valueOf(0l);
		this.midCount = BigInteger.valueOf(0l);
		this.largeCount = BigInteger.valueOf(0l);
		this.total = BigInteger.valueOf(0l);
	}

	public String getWspType() {
		return wspType;
	}
	
	public void setWspType(String wspType) {
		this.wspType = wspType;
	}

	public BigInteger getSmallCount() {
		return smallCount;
	}

	public void setSmallCount(BigInteger smallCount) {
		this.smallCount = smallCount;
	}

	public BigInteger getMidCount() {
		return midCount;
	}

	public void setMidCount(BigInteger midCount) {
		this.midCount = midCount;
	}

	public BigInteger getLargeCount() {
		return largeCount;
	}

	public void setLargeCount(BigInteger largeCount) {
		this.largeCount = largeCount;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public BigInteger getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(BigInteger reportNumber) {
		this.reportNumber = reportNumber;
	}


}
