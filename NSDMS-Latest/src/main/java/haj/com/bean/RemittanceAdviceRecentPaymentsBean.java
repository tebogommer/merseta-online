
package haj.com.bean;

import java.util.Date;

/**
 * The Class RemittanceAdviceRecentPaymentsBean. 
 * Used for report: REMITTANCE ADVICE finance reporting 
 * Section for report: Recent Payments
 */
public class RemittanceAdviceRecentPaymentsBean {

	private String type;
	private Date date;
	private String number;
	private String description;
	private String amountDescription;
	private Double amount;
	private Double appliedAmount;
	private Double balance;
	private Boolean displaySpacer;

	public RemittanceAdviceRecentPaymentsBean() {
		super();
	}

	public RemittanceAdviceRecentPaymentsBean(String type, Date date, String number, String description,
			String amountDescription, Double amount, Double appliedAmount, Double balance, Boolean displaySpacer) {
		super();
		this.type = type;
		this.date = date;
		this.number = number;
		this.description = description;
		this.amountDescription = amountDescription;
		this.amount = amount;
		this.appliedAmount = appliedAmount;
		this.balance = balance;
		this.displaySpacer = displaySpacer;
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

	public Boolean getDisplaySpacer() {
		return displaySpacer;
	}

	public void setDisplaySpacer(Boolean displaySpacer) {
		this.displaySpacer = displaySpacer;
	}

	public Double getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(Double appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

}
