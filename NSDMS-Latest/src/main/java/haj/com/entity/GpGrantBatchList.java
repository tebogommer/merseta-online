package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "gp_grant_batch_list")
@AuditTable(value = "gp_grant_batch_list_hist")
@Audited
public class GpGrantBatchList implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of GpGrantBatchList. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/* User created batch */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_users_id", nullable = true)
	private Users createUsers;
	
	/* Mandatory or Discretionary Type */
	@Enumerated
	@Column(name = "wsp_type")
	private WspTypeEnum wspType;
	
	/* Batch ID : GRANTS [List Number] */
	@Column(name = "batch_number")
	private Integer batchNumber;
	
	/* Batch Description: Mandatory/Discretionary Grants from List [List Number] */
	@Column(name = "batch_description", length = 500)
	private String batchDescription;
	
	/* Status */
	@Enumerated
	@Column(name = "approval_enum", length = 19)
	private ApprovalEnum approvalEnum;
	
	/* From Date For Generated Criteria */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date_period", length = 19)
	private Date fromDatePeriod;

	/* To Date For Generated Criteria */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date_period", length = 19)
	private Date toDatePeriod;
	
	/* Sars from Date For Generated Criteria */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sars_from_date_period", length = 19)
	private Date sarsFromDatePeriod;
	
	/* Sars To Date For Generated Criteria */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sars_to_date_period", length = 19)
	private Date sarsToDatePeriod;
	
	/* Original Total Amount before entries were altered */
	@Column(name = "original_amount")
	private Double originalAmount;
	
	/* Final Approve User */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approve_user_id", nullable = true)
	private Users approveUser;
	
	/* Date Final Approved */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_final_approved", length = 19)
	private Date dateFinalApproved;
	
	@Column(name = "completed_gp_transation", nullable = true)
	private Boolean completedGpTransation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_completed_gp_transation", length = 19, nullable = true)
	private Date dateCompletedGpTransation;
	
	@Column(name = "additional_rejection_reason" , columnDefinition= "LONGTEXT")
	private String additionalRejectionReason;
	
	@Column(name = "validiation_run")
	private Boolean validiationRun;
	
	@Column(name = "validiation_underway")
	private Boolean validiationUnderway;
	
	@Column(name = "filtered_scheme_year")
	private Integer filteredSchemeYear;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_validiation_run", length = 19, nullable = true)
	private Date dateValidiationRun;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private Integer totalEntiresAssigned;
	
	@Transient
	private Integer totalActive;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GpGrantBatchList other = (GpGrantBatchList) obj;
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

	public Users getCreateUsers() {
		return createUsers;
	}

	public void setCreateUsers(Users createUsers) {
		this.createUsers = createUsers;
	}

	public Date getFromDatePeriod() {
		return fromDatePeriod;
	}

	public void setFromDatePeriod(Date fromDatePeriod) {
		this.fromDatePeriod = fromDatePeriod;
	}

	public Date getToDatePeriod() {
		return toDatePeriod;
	}

	public void setToDatePeriod(Date toDatePeriod) {
		this.toDatePeriod = toDatePeriod;
	}

	public Date getSarsFromDatePeriod() {
		return sarsFromDatePeriod;
	}

	public void setSarsFromDatePeriod(Date sarsFromDatePeriod) {
		this.sarsFromDatePeriod = sarsFromDatePeriod;
	}

	public Date getSarsToDatePeriod() {
		return sarsToDatePeriod;
	}

	public void setSarsToDatePeriod(Date sarsToDatePeriod) {
		this.sarsToDatePeriod = sarsToDatePeriod;
	}

	public Integer getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public WspTypeEnum getWspType() {
		return wspType;
	}

	public void setWspType(WspTypeEnum wspType) {
		this.wspType = wspType;
	}

	public Date getDateFinalApproved() {
		return dateFinalApproved;
	}

	public void setDateFinalApproved(Date dateFinalApproved) {
		this.dateFinalApproved = dateFinalApproved;
	}

	public String getBatchDescription() {
		return batchDescription;
	}

	public void setBatchDescription(String batchDescription) {
		this.batchDescription = batchDescription;
	}

	public Integer getTotalEntiresAssigned() {
		return totalEntiresAssigned;
	}

	public void setTotalEntiresAssigned(Integer totalEntiresAssigned) {
		this.totalEntiresAssigned = totalEntiresAssigned;
	}

	public Integer getTotalActive() {
		return totalActive;
	}

	public void setTotalActive(Integer totalActive) {
		this.totalActive = totalActive;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public Double getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(Double originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Boolean getCompletedGpTransation() {
		return completedGpTransation;
	}

	public void setCompletedGpTransation(Boolean completedGpTransation) {
		this.completedGpTransation = completedGpTransation;
	}

	public Date getDateCompletedGpTransation() {
		return dateCompletedGpTransation;
	}

	public void setDateCompletedGpTransation(Date dateCompletedGpTransation) {
		this.dateCompletedGpTransation = dateCompletedGpTransation;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public Boolean getValidiationRun() {
		return validiationRun;
	}

	public void setValidiationRun(Boolean validiationRun) {
		this.validiationRun = validiationRun;
	}

	public Date getDateValidiationRun() {
		return dateValidiationRun;
	}

	public void setDateValidiationRun(Date dateValidiationRun) {
		this.dateValidiationRun = dateValidiationRun;
	}

	public Boolean getValidiationUnderway() {
		return validiationUnderway;
	}

	public void setValidiationUnderway(Boolean validiationUnderway) {
		this.validiationUnderway = validiationUnderway;
	}

	public Integer getFilteredSchemeYear() {
		return filteredSchemeYear;
	}

	public void setFilteredSchemeYear(Integer filteredSchemeYear) {
		this.filteredSchemeYear = filteredSchemeYear;
	}

}