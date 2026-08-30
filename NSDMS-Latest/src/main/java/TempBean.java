


import java.math.BigDecimal;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;


public class TempBean  implements IDataEntity {


     /** The id. */
	 @CSVAnnotation(length = 20)
     private Long longValue;

     /** The posting date. */
	 @CSVAnnotation(name = "dateValue", className = Date.class,length = 8,datePattern="yyyy.MM.dd")
     private Date dateValue;

     /** The vendor name. */
	 @CSVAnnotation(name = "stringValue", className = String.class, length=15)
     private String stringValue;

     /** The vendor name. */
	 @CSVAnnotation(name = "doubleAmount", className = Double.class, length=15,numericPattern="%.2f")
     private Double doubleAmount;


     /** The vendor name. */
	 @CSVAnnotation(name = "amount", className = Integer.class, length=22)
     private Integer integerValue;

     /** The vendor name. */
	 @CSVAnnotation(name = "bigDecimalValue", className = BigDecimal.class, length=8 )
     private BigDecimal bigDecimalValue;




	public TempBean(Long longValue, Date dateValue, String stringValue, Double doubleAmount, Integer integerValue, BigDecimal bigDecimalValue) {
		super();
		this.longValue = longValue;
		this.dateValue = dateValue;
		this.stringValue = stringValue;
		this.doubleAmount = doubleAmount;
		this.integerValue = integerValue;
		this.bigDecimalValue = bigDecimalValue;
	}

	public TempBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}



	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Double getDoubleAmount() {
		return doubleAmount;
	}

	public void setDoubleAmount(Double doubleAmount) {
		this.doubleAmount = doubleAmount;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public BigDecimal getBigDecimalValue() {
		return bigDecimalValue;
	}

	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		this.bigDecimalValue = bigDecimalValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}


}


