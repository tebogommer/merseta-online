package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.GpDocumentType;
import haj.com.entity.lookup.Chamber;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;

/**
 * GpGrantBatchEntry.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "gp_grant_batch_entry")
@AuditTable(value = "gp_grant_batch_entry_hist")
@Audited
public class GpGrantBatchEntry implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of GpGrantBatchEntry. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** 
	 * Create Date of Object. 
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/* 
	 * Batch ID
	 * used on: Mandatory Grant Grant transactions 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "gp_grant_batch_list_id", nullable = false)
	private GpGrantBatchList gpGrantBatchList;
	
	/* 
	 * Voucher No : GP auto-number (Not sure if we need this)
	 * used on: Mandatory Grant Grant transactions 
	 */
	@Column(name = "voucher_no", length = 10)
	private String voucherNo;
	
	/* 
	 * Doc. Date
	 * Used on: Mandatory Grant Grant transactions  
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doc_date", length = 19)
	private Date docDate;
	
	@Column(name = "description")
	private String description;
	
	/* 
	 * Creditor ID: L-Number
	 * Used on: Mandatory Grant Grant transactions 
	 */
	@Column(name = "levy_number", length = 10)
	private String levyNumber;
	
	
	/* Name of employer from sars employer file */
	@Column(name = "employer_name")
	private String employerName;
	
	/* 
	 * Creditor ID: L-Number: [Scheme year][Fund A/B/C/D][List number] e.g. 2009A2291
	 * Used on: Mandatory Grant Grant transactions 
	 */
	@Column(name = "document_number", length = 30)
	private String documentNumber;
	
	/* 
	 * Purchases: Net amount from grant list
	 * Used on: Mandatory Grant Grant transactions 
	 * @see mandatoryLevy
	 * Feild may relate to it
	 */
	@Column(name = "purchases")
	private Double purchases;
	
	/* For grant year */
	@Column(name = "scheme_year")
	private Integer schemeYear;
	
	/* mandatoryLevy from Sars mandatoryLevy multiplied -1 */
	@Column(name = "mandatory_levy", columnDefinition = "DOUBLE")
	private Double mandatoryLevy;
	
	@Column(name = "mandatory_levy_rounded", columnDefinition = "DOUBLE")
	private Double mandatoryLevyRounded;
	
	/* discretionaryLevy from Sars discretionaryLevy multiplied -1 */
	@Column(name = "discretionary_levy", columnDefinition = "DOUBLE")
	private Double discretionaryLevy;
	
	@Column(name = "discretionary_levy_rounded", columnDefinition = "DOUBLE")
	private Double discretionaryLevyRounded;
	
	/* Total from Sars Total data multiplied -1 */
	@Column(name = "total")
	private Double total;
	
	/* Invoice / Return */
	@Enumerated
	@Column(name = "document_type_mandatory", length = 10)
	private GpDocumentType documentTypeMandatory;
	
	/* Invoice / Return */
	@Enumerated
	@Column(name = "document_type_discretionary", length = 10)
	private GpDocumentType documentTypeDiscretionary;
	
	/* mandatoryLevyFromSars / 0.8 */
	@Column(name = "full_levy_amount")
	private Double fullLevyAmount;
	
	/* totalFromSars / fullLevyAmount */
	@Column(name = "full_percentage_calculation")
	private Double fullPercentageCalculation;
	
	/* Indicator if sent to gp */
	@Column(name = "loaded_to_gp", columnDefinition = "BIT default false")
	private Boolean loadedToGp;
	
	/* Date for when sent to gp */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_loaded_to_gp", length = 19, nullable = true)
	private Date dateLoadedToGp;
	
	/* Indicator to be removed from being processed on final approval */
	@Column(name = "to_be_removed", columnDefinition = "BIT default false")
	private Boolean toBeRemoved;
	
	/* Copy of sars info start */
	@Column(name = "arrival_date_1_form_sars_files")
	private Date arrivalDateFromSars;
	
	@Column(name = "mandatory_levy_form_sars_files", columnDefinition = "DOUBLE")
	private BigDecimal mandatoryLevyFromSars;
	
	@Column(name = "discretionary_levy_from_sars_files", columnDefinition = "DOUBLE")
	private BigDecimal discretionaryLevyFromSars;
	
	@Column(name = "total_from_sars", columnDefinition = "DOUBLE")
	private BigDecimal totalFromSars;
	/* Copy of sars info end */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "user_actioned_id", nullable = true)
	private Users userActioned;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "user_actioned_date", length = 19, nullable = true)
	private Date userActionedDate;
	
	/* Link to active contract */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "active_contract_detail_id", nullable = true)
	private ActiveContractDetail activeContractDetail;
	
	/* Link to sars file */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_levy_details_id", nullable = true)
	private SarsLevyDetails sarsLevyDetailsId;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_employer_detail_linked_id", nullable = true)
	private SarsEmployerDetail sarsEmployerDetailLinked;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chamber_linked_id", nullable = true)
	private Chamber chamberLinked;
	
	@Column(name = "errorEntry")
	private Boolean errorEntry;
	
	@Column(name = "errorMessage", columnDefinition = "LONGTEXT")
	private String errorMessage;
	
	@Transient
	private SarsEmployerDetail employerDetail;
	
	@Transient
	private Boolean onSarsEmployerRecord;
	
	@Transient
	private String employerRecordsStatus;
	
	@Transient
	private Boolean onNsdmsDatabase;
	
	@Transient
	private String nsdmdStatus;
	
	public GpGrantBatchEntry() {
		super();
	}

	//select new  haj.com.entity.GpGrantBatchEntry(o.levyNumber, o.schemeYear,  sum(o.mandatoryLevy))
	public GpGrantBatchEntry( String levyNumber, Integer schemeYear, Double mandatoryLevy) {
		super();
		this.levyNumber = levyNumber;
		this.schemeYear = schemeYear;
		this.mandatoryLevy = mandatoryLevy;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GpGrantBatchEntry other = (GpGrantBatchEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public GpGrantBatchList getGpGrantBatchList() {
		return gpGrantBatchList;
	}

	public void setGpGrantBatchList(GpGrantBatchList gpGrantBatchList) {
		this.gpGrantBatchList = gpGrantBatchList;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getArrivalDateFromSars() {
		return arrivalDateFromSars;
	}

	public void setArrivalDateFromSars(Date arrivalDateFromSars) {
		this.arrivalDateFromSars = arrivalDateFromSars;
	}

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
	}

	public BigDecimal getMandatoryLevyFromSars() {
		return mandatoryLevyFromSars;
	}

	public void setMandatoryLevyFromSars(BigDecimal mandatoryLevyFromSars) {
		this.mandatoryLevyFromSars = mandatoryLevyFromSars;
	}

	public BigDecimal getDiscretionaryLevyFromSars() {
		return discretionaryLevyFromSars;
	}

	public void setDiscretionaryLevyFromSars(BigDecimal discretionaryLevyFromSars) {
		this.discretionaryLevyFromSars = discretionaryLevyFromSars;
	}

	public Boolean getToBeRemoved() {
		return toBeRemoved;
	}

	public void setToBeRemoved(Boolean toBeRemoved) {
		this.toBeRemoved = toBeRemoved;
	}

	public Double getPurchases() {
		return purchases;
	}

	public void setPurchases(Double purchases) {
		this.purchases = purchases;
	}

	public Boolean getLoadedToGp() {
		return loadedToGp;
	}

	public void setLoadedToGp(Boolean loadedToGp) {
		this.loadedToGp = loadedToGp;
	}

	public Double getMandatoryLevy() {
		return mandatoryLevy;
	}

	public void setMandatoryLevy(Double mandatoryLevy) {
		this.mandatoryLevy = mandatoryLevy;
	}

	public Double getDiscretionaryLevy() {
		return discretionaryLevy;
	}

	public void setDiscretionaryLevy(Double discretionaryLevy) {
		this.discretionaryLevy = discretionaryLevy;
	}

	public GpDocumentType getDocumentTypeMandatory() {
		return documentTypeMandatory;
	}

	public void setDocumentTypeMandatory(GpDocumentType documentTypeMandatory) {
		this.documentTypeMandatory = documentTypeMandatory;
	}

	public GpDocumentType getDocumentTypeDiscretionary() {
		return documentTypeDiscretionary;
	}

	public void setDocumentTypeDiscretionary(GpDocumentType documentTypeDiscretionary) {
		this.documentTypeDiscretionary = documentTypeDiscretionary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Users getUserActioned() {
		return userActioned;
	}

	public void setUserActioned(Users userActioned) {
		this.userActioned = userActioned;
	}

	public Date getUserActionedDate() {
		return userActionedDate;
	}

	public void setUserActionedDate(Date userActionedDate) {
		this.userActionedDate = userActionedDate;
	}

	public Date getDateLoadedToGp() {
		return dateLoadedToGp;
	}

	public void setDateLoadedToGp(Date dateLoadedToGp) {
		this.dateLoadedToGp = dateLoadedToGp;
	}

	public Double getFullPercentageCalculation() {
		return fullPercentageCalculation;
	}

	public void setFullPercentageCalculation(Double fullPercentageCalculation) {
		this.fullPercentageCalculation = fullPercentageCalculation;
	}

	public BigDecimal getTotalFromSars() {
		return totalFromSars;
	}

	public void setTotalFromSars(BigDecimal totalFromSars) {
		this.totalFromSars = totalFromSars;
	}

	public Double getFullLevyAmount() {
		return fullLevyAmount;
	}

	public void setFullLevyAmount(Double fullLevyAmount) {
		this.fullLevyAmount = fullLevyAmount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public SarsEmployerDetail getEmployerDetail() {
		return employerDetail;
	}

	public void setEmployerDetail(SarsEmployerDetail employerDetail) {
		this.employerDetail = employerDetail;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public ActiveContractDetail getActiveContractDetail() {
		return activeContractDetail;
	}

	public void setActiveContractDetail(ActiveContractDetail activeContractDetail) {
		this.activeContractDetail = activeContractDetail;
	}

	public SarsLevyDetails getSarsLevyDetailsId() {
		return sarsLevyDetailsId;
	}

	public void setSarsLevyDetailsId(SarsLevyDetails sarsLevyDetailsId) {
		this.sarsLevyDetailsId = sarsLevyDetailsId;
	}

	public Boolean getOnSarsEmployerRecord() {
		return onSarsEmployerRecord;
	}

	public void setOnSarsEmployerRecord(Boolean onSarsEmployerRecord) {
		this.onSarsEmployerRecord = onSarsEmployerRecord;
	}

	public String getEmployerRecordsStatus() {
		return employerRecordsStatus;
	}

	public void setEmployerRecordsStatus(String employerRecordsStatus) {
		this.employerRecordsStatus = employerRecordsStatus;
	}

	public Boolean getOnNsdmsDatabase() {
		return onNsdmsDatabase;
	}

	public void setOnNsdmsDatabase(Boolean onNsdmsDatabase) {
		this.onNsdmsDatabase = onNsdmsDatabase;
	}

	public String getNsdmdStatus() {
		return nsdmdStatus;
	}

	public void setNsdmdStatus(String nsdmdStatus) {
		this.nsdmdStatus = nsdmdStatus;
	}
	
	public String getGpDocumentNumer() {
		String newDocRefNumber = this.documentNumber + "/" +  this.id.toString();
		return newDocRefNumber;
	}

	public Boolean getErrorEntry() {
		return errorEntry;
	}

	public void setErrorEntry(Boolean errorEntry) {
		this.errorEntry = errorEntry;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Double getMandatoryLevyRounded() {
		return mandatoryLevyRounded;
	}

	public void setMandatoryLevyRounded(Double mandatoryLevyRounded) {
		this.mandatoryLevyRounded = mandatoryLevyRounded;
	}

	public Double getDiscretionaryLevyRounded() {
		return discretionaryLevyRounded;
	}

	public void setDiscretionaryLevyRounded(Double discretionaryLevyRounded) {
		this.discretionaryLevyRounded = discretionaryLevyRounded;
	}

	public Chamber getChamberLinked() {
		return chamberLinked;
	}

	public void setChamberLinked(Chamber chamberLinked) {
		this.chamberLinked = chamberLinked;
	}

	public SarsEmployerDetail getSarsEmployerDetailLinked() {
		return sarsEmployerDetailLinked;
	}

	public void setSarsEmployerDetailLinked(SarsEmployerDetail sarsEmployerDetailLinked) {
		this.sarsEmployerDetailLinked = sarsEmployerDetailLinked;
	}
}