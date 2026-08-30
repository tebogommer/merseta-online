
package haj.com.bean;

import java.util.Date;

/**
 * The Class RemittanceAdviceInfoBean. Used for report: REMITTANCE ADVICE
 * finance reporting Section for report: Outstanding Transactions
 */
public class RemittanceAdviceInfoBean {

	private String vendID;
	private String vendname;
	private String voucherNumber;
	private String docType;
	private Date docDate;
	private String docNumber;
	private String docDescription;
	private Double docFunctionalAmount;
	private Double dockBalance1;
	private String docHold1;
	private String docHoldMessage;
	private String docHoldReason1;
	
	public RemittanceAdviceInfoBean() {
		super();
	}
	
	public String getVendID() {
		return vendID;
	}
	public void setVendID(String vendID) {
		this.vendID = vendID;
	}
	public String getVendname() {
		return vendname;
	}
	public void setVendname(String vendname) {
		this.vendname = vendname;
	}
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
	public Double getDocFunctionalAmount() {
		return docFunctionalAmount;
	}
	public void setDocFunctionalAmount(Double docFunctionalAmount) {
		this.docFunctionalAmount = docFunctionalAmount;
	}
	public Double getDockBalance1() {
		return dockBalance1;
	}
	public void setDockBalance1(Double dockBalance1) {
		this.dockBalance1 = dockBalance1;
	}
	public String getDocHold1() {
		return docHold1;
	}
	public void setDocHold1(String docHold1) {
		this.docHold1 = docHold1;
	}
	public String getDocHoldReason1() {
		return docHoldReason1;
	}
	public void setDocHoldReason1(String docHoldReason1) {
		this.docHoldReason1 = docHoldReason1;
	}

	public String getDocHoldMessage() {
		return docHoldMessage;
	}

	public void setDocHoldMessage(String docHoldMessage) {
		this.docHoldMessage = docHoldMessage;
	}

}
