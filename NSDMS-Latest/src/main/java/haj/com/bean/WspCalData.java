package haj.com.bean;

import java.math.BigDecimal;

import haj.com.framework.IDataEntity;

public class WspCalData implements IDataEntity {

	private String companyName;
	private BigDecimal percentage;
//	private String levyNumber;
//	private BigInteger totalCalcData;
//	private BigInteger totalTrainData;
	

	public WspCalData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
//
//	public String getLevyNumber() {
//		return levyNumber;
//	}
//
//	public void setLevyNumber(String levyNumber) {
//		this.levyNumber = levyNumber;
//	}
//
//	public BigInteger getTotalCalcData() {
//		return totalCalcData;
//	}
//
//	public void setTotalCalcData(BigInteger totalCalcData) {
//		this.totalCalcData = totalCalcData;
//	}
//
//	public BigInteger getTotalTrainData() {
//		return totalTrainData;
//	}
//
//	public void setTotalTrainData(BigInteger totalTrainData) {
//		this.totalTrainData = totalTrainData;
//	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	
}
