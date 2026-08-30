
package haj.com.bean;

import java.util.Date;

/**
 * The Class RemittanceAdvicePaymentsReturnedBean. 
 * Used for report: REMITTANCE ADVICE finance reporting 
 * Section for report: Payments Returned by the Bank
 */
public class RemittanceAdvicePaymentsReturnedBean {

	private String type;
	private Date date;
	private String number;
	private Double amount;
	private String reason;

	public RemittanceAdvicePaymentsReturnedBean() {
		super();
	}

	public RemittanceAdvicePaymentsReturnedBean(String type, Date date, String number, Double amount, String reason) {
		super();
		this.type = type;
		this.date = date;
		this.number = number;
		this.amount = amount;
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
