package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * The Class CounterBean.
 * Used for counts in native sql quiries
 */
public class DescriptionCounterBean implements Serializable {

	private String description;
	private Date generated;
	private BigInteger counter;
	
	private Date fromDate;
	private Date toDate;

	public DescriptionCounterBean() {
		super();
	}

	public DescriptionCounterBean(String description, BigInteger counter, Date generated) {
		super();
		this.description = description;
		this.counter = counter;
		this.generated = generated;
	}
	
	public DescriptionCounterBean(String description, BigInteger counter, Date generated, Date fromDate, Date toDate) {
		super();
		this.description = description;
		this.counter = counter;
		this.generated = generated;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	/* Getters and setters */
	public BigInteger getCounter() {
		return counter;
	}

	public void setCounter(BigInteger counter) {
		this.counter = counter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
}
