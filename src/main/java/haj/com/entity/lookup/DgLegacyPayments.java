package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "dg_legacy_payments")
@AuditTable(value = "dg_legacy_payments_hist")
@Audited
public class DgLegacyPayments extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of DgLegacyPayments. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of DgLegacyPayments. */
	@Column(name = "description", length = 500)
	private String description;
	
	@CSVAnnotation(name = "Posting_Date", className = String.class)
	@Column(name = "posting_date")
	private String postingDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posting_date_converted", length = 19)
	private Date postingDateConverted;
	
	@CSVAnnotation(name = "Voucher_Number", className = String.class)
	@Column(name = "voucher_number")
	private String voucherNumber;
	
	@CSVAnnotation(name = "Scheme_Year", className = String.class)
	@Column(name = "scheme_year")
	private String schemeYear;
	
	@Column(name = "scheme_year_converted")
	private Integer schemeYearConverted;
	
	@CSVAnnotation(name = "Vendor_Id", className = String.class)
	@Column(name = "vendor_id")
	private String vendorId;
	
	@CSVAnnotation(name = "Vendor_Name", className = String.class)
	@Column(name = "vendor_name")
	private String vendorName;
	
	@CSVAnnotation(name = "Document_Type", className = String.class)
	@Column(name = "document_type")
	private String documentType;
	
	@CSVAnnotation(name = "Document_Date", className = String.class)
	@Column(name = "document_date")
	private String documentDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "document_date_converted", length = 19)
	private Date documentDateConverted;
	
	@CSVAnnotation(name = "Document_Number", className = String.class)
	@Column(name = "document_number")
	private String documentNumber;
	
	@CSVAnnotation(name = "Transaction_Description", className = String.class)
	@Column(name = "transaction_description")
	private String transactionDescription;
	
	@CSVAnnotation(name = "Document_Amount", className = String.class)
	@Column(name = "document_amount")
	private String documentAmount;
	
	@Column(name = "document_amount_converted")
	private Double documentAmountConverted;
	
	@CSVAnnotation(name = "Current_Trx_Amount", className = String.class)
	@Column(name = "current_trx_amount")
	private String currentTrxAmount;
	
	@Column(name = "current_trx_amount_converted")
	private Double currentTrxAmountConverted;
	
	@CSVAnnotation(name = "TRX_Source", className = String.class)
	@Column(name = "trx_source")
	private String trxSource;
	
	@CSVAnnotation(name = "Voided", className = String.class)
	@Column(name = "voided")
	private String voided;
	
	@CSVAnnotation(name = "Posted_Date", className = String.class)
	@Column(name = "posted_date")
	private String postedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "posted_date_converted", length = 19)
	private Date postedDateConverted;
	
	@CSVAnnotation(name = "Document_Status", className = String.class)
	@Column(name = "document_status")
	private String documentStatus;
	
	public DgLegacyPayments() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DgLegacyPayments other = (DgLegacyPayments) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public Date getPostingDateConverted() {
		return postingDateConverted;
	}

	public void setPostingDateConverted(Date postingDateConverted) {
		this.postingDateConverted = postingDateConverted;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(String schemeYear) {
		this.schemeYear = schemeYear;
	}

	public Integer getSchemeYearConverted() {
		return schemeYearConverted;
	}

	public void setSchemeYearConverted(Integer schemeYearConverted) {
		this.schemeYearConverted = schemeYearConverted;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public Date getDocumentDateConverted() {
		return documentDateConverted;
	}

	public void setDocumentDateConverted(Date documentDateConverted) {
		this.documentDateConverted = documentDateConverted;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getDocumentAmount() {
		return documentAmount;
	}

	public void setDocumentAmount(String documentAmount) {
		this.documentAmount = documentAmount;
	}

	public Double getDocumentAmountConverted() {
		return documentAmountConverted;
	}

	public void setDocumentAmountConverted(Double documentAmountConverted) {
		this.documentAmountConverted = documentAmountConverted;
	}

	public String getCurrentTrxAmount() {
		return currentTrxAmount;
	}

	public void setCurrentTrxAmount(String currentTrxAmount) {
		this.currentTrxAmount = currentTrxAmount;
	}

	public Double getCurrentTrxAmountConverted() {
		return currentTrxAmountConverted;
	}

	public void setCurrentTrxAmountConverted(Double currentTrxAmountConverted) {
		this.currentTrxAmountConverted = currentTrxAmountConverted;
	}

	public String getTrxSource() {
		return trxSource;
	}

	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}

	public String getVoided() {
		return voided;
	}

	public void setVoided(String voided) {
		this.voided = voided;
	}

	public Date getPostedDateConverted() {
		return postedDateConverted;
	}

	public void setPostedDateConverted(Date postedDateConverted) {
		this.postedDateConverted = postedDateConverted;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

}
