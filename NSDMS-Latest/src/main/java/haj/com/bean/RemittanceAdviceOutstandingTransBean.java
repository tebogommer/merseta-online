
package haj.com.bean;

import java.util.Date;


/**
 * The Class RemittanceAdviceOutstandingTransBean.
 * Used for report: REMITTANCE ADVICE finance reporting
 * Section for report: Outstanding Transactions
 */
public class RemittanceAdviceOutstandingTransBean {

	private String type;
	private Date date;
	private String number;
	private String description;
	private Double amount;
	private Double balance;
	private Boolean statusHold;
	private Boolean statusActive;
	private String holdReason;

	public RemittanceAdviceOutstandingTransBean() {
		super();
	}
	
	public RemittanceAdviceOutstandingTransBean(String type, Date date, String number, String description,
			Double amount, Double balance, Boolean statusHold, Boolean statusActive, String holdReason) {
		super();
		this.type = type;
		this.date = date;
		this.number = number;
		this.description = description;
		this.amount = amount;
		this.balance = balance;
		this.statusHold = statusHold;
		this.statusActive = statusActive;
		this.holdReason = holdReason;
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
	public Boolean getStatusHold() {
		return statusHold;
	}
	public void setStatusHold(Boolean statusHold) {
		this.statusHold = statusHold;
	}
	public Boolean getStatusActive() {
		return statusActive;
	}
	public void setStatusActive(Boolean statusActive) {
		this.statusActive = statusActive;
	}
	public String getHoldReason() {
		return holdReason;
	}
	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}
	
	
}
