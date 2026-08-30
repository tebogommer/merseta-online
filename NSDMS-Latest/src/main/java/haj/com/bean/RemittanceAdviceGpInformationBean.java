
package haj.com.bean;

/**
 * The Class RemittanceAdviceGpInformationBean. 
 * Used for report: REMITTANCE ADVICE finance reporting 
 * Section for report: GP information
 */
public class RemittanceAdviceGpInformationBean {

	private String entityOnGp;
	private String name;
	private String accountNumber;
	private String status;
	private String holdReason;
	
	public RemittanceAdviceGpInformationBean() {
		super();
	}
	
	public RemittanceAdviceGpInformationBean(String entityOnGp, String name, String accountNumber, String status,
			String holdReason) {
		super();
		this.entityOnGp = entityOnGp;
		this.name = name;
		this.accountNumber = accountNumber;
		this.status = status;
		this.holdReason = holdReason;
	}

	public String getEntityOnGp() {
		return entityOnGp;
	}

	public void setEntityOnGp(String entityOnGp) {
		this.entityOnGp = entityOnGp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHoldReason() {
		return holdReason;
	}

	public void setHoldReason(String holdReason) {
		this.holdReason = holdReason;
	}
	
	

}