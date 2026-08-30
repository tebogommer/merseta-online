
package haj.com.bean;

import java.util.Date;

/**
 * The Class RemittanceAdviceRecentAdjustmentsBean. 
 * Used for report: REMITTANCE ADVICE finance reporting 
 * Section for report: Recent Adjustments
 */
public class RemittanceAdviceRecentAdjustmentsBean {

	private String type;
	private Date date;
	private String number;
	private String description;
	private String amountDescription;
	private Double amount;
	private Double balance;

	public RemittanceAdviceRecentAdjustmentsBean() {
		super();
	}

	public RemittanceAdviceRecentAdjustmentsBean(String type, Date date, String number, String description,
			String amountDescription, Double amount, Double balance) {
		super();
		this.type = type;
		this.date = date;
		this.number = number;
		this.description = description;
		this.amountDescription = amountDescription;
		this.amount = amount;
		this.balance = balance;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getAmountDescription() {
		return amountDescription;
	}

	public void setAmountDescription(String amountDescription) {
		this.amountDescription = amountDescription;
	}

}
