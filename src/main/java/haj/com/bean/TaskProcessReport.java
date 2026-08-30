package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.HostingCompanyProcess;

/**
 * The Class CounterBean.
 * Used for counts in native sql quiries
 */
public class TaskProcessReport implements Serializable {

	private Date generated;
	private BigInteger hostingCompanyProcessId;
	private HostingCompanyProcess hostingCompanyProcess;
	private String description;
	
	private BigInteger completedCounter;
	private BigInteger underwayCounter;
	private BigInteger assignedCounter;
	
	private BigInteger completedInSlaCounter;
	private BigInteger completedNotInSlaCounter;
	
	private Date fromDate;
	private Date toDate;
	
	public TaskProcessReport() {
		super();	
	}
	
	public TaskProcessReport(Date generated, String description) {
		super();
		this.generated = generated;
		this.description = description;
		this.completedCounter = BigInteger.valueOf(0);
		this.underwayCounter = BigInteger.valueOf(0);
		this.assignedCounter = BigInteger.valueOf(0);
		this.completedInSlaCounter = BigInteger.valueOf(0);
		this.completedNotInSlaCounter = BigInteger.valueOf(0);
	}

	/* getters and setters */
	public BigInteger getHostingCompanyProcessId() {
		return hostingCompanyProcessId;
	}
	public void setHostingCompanyProcessId(BigInteger hostingCompanyProcessId) {
		this.hostingCompanyProcessId = hostingCompanyProcessId;
	}
	public HostingCompanyProcess getHostingCompanyProcess() {
		return hostingCompanyProcess;
	}
	public void setHostingCompanyProcess(HostingCompanyProcess hostingCompanyProcess) {
		this.hostingCompanyProcess = hostingCompanyProcess;
	}
	public BigInteger getCompletedCounter() {
		return completedCounter;
	}
	public void setCompletedCounter(BigInteger completedCounter) {
		this.completedCounter = completedCounter;
	}
	public BigInteger getUnderwayCounter() {
		return underwayCounter;
	}
	public void setUnderwayCounter(BigInteger underwayCounter) {
		this.underwayCounter = underwayCounter;
	}
	public BigInteger getAssignedCounter() {
		return assignedCounter;
	}
	public void setAssignedCounter(BigInteger assignedCounter) {
		this.assignedCounter = assignedCounter;
	}
	public Date getGenerated() {
		return generated;
	}
	public void setGenerated(Date generated) {
		this.generated = generated;
	}

	public BigInteger getCompletedInSlaCounter() {
		return completedInSlaCounter;
	}

	public void setCompletedInSlaCounter(BigInteger completedInSlaCounter) {
		this.completedInSlaCounter = completedInSlaCounter;
	}

	public BigInteger getCompletedNotInSlaCounter() {
		return completedNotInSlaCounter;
	}

	public void setCompletedNotInSlaCounter(BigInteger completedNotInSlaCounter) {
		this.completedNotInSlaCounter = completedNotInSlaCounter;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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
