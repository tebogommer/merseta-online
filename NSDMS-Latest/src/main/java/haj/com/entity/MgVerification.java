package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * MgVerification.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "mg_verification")
public class MgVerification implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of MgVerification. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_captured", length = 19)
	private Date dateCaptured;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "review_date", length = 19)
	private Date reviewDate;
	
	@Column(name = "date_for_visit_provided")
	private Boolean dateForVisitProvided;
	
	@Column(name = "submited_for_verification")
	private Boolean submitedForVerification;
	
	/** The CLO it generated for */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "clo_generated_for_id", nullable = true)
	private Users cloGeneratedFor;
	
	/** The CRM user Assigned */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "crm_user_assigned_id", nullable = true)
	private Users crmUserAssigned;

	/** The Wsp */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@Column(name = "training_needs_identified", columnDefinition = "BIT default false")
	private Boolean trainingNeedsIdentified;

	@Column(name = "has_training_commenced", columnDefinition = "BIT default false")
	private Boolean hasTrainingCommenced;

	@Column(name = "proof_of_training_verified", columnDefinition = "BIT default false")
	private Boolean proofOfTrainingVerified;

	/** Note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reset_date", length = 19)
	private Date resetDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	@Column(name = "final_fin")
	private Long finalFin;
	
	@Column(name = "batch_number")
	private Long batchNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_submitted", nullable = true)
	private YesNoLookup wspSubmitted;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "atr_submitted", nullable = true)
	private YesNoLookup atrSubmitted;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "organised_labour_union", nullable = true)
	private YesNoLookup organisedLabourUnion;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_comittee_minutes", nullable = true)
	private YesNoLookup trainingComitteeMinutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "employer_attend_meetings", nullable = true)
	private YesNoLookup employerAttendMeetings;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "labour_attend_meetings", nullable = true)
	private YesNoLookup labourAttendMeetings;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "mg_signed_off", nullable = true)
	private YesNoLookup mgSignedOff;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "complete_in_year", nullable = true)
	private YesNoLookup completeInYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_commenced", nullable = true)
	private YesNoLookup trainingCommenced;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "training_completed_as_previous", nullable = true)
	private YesNoLookup trainingCompletedAsPrevious;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "proof_training_verified", nullable = true)
	private YesNoLookup proofTrainingVerified;

	@Column(name = "company_rep_sign_off", columnDefinition = "BIT default false")
	private Boolean companyRepSignOff;

	@Column(name = "sdf_sign_off", columnDefinition = "BIT default false")
	private Boolean sdfSignOff;

	@Column(name = "merseta_rep_sign_off", columnDefinition = "BIT default false")
	private Boolean mersetaRepSignOff;

	@Column(name = "crm_sign_off", columnDefinition = "BIT default false")
	private Boolean crmSignOff;

	@Column(name = "status")
	private ApprovalEnum status;
	
	@Column(name = "clo_comment", columnDefinition = "LONGTEXT")
	private String cloComment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "primary_user_sign_off_id", nullable = true)
	private Users primaryUserSignOff;

	@Transient
	private List<Signoff> signOffs;
	
	@Transient 
	private Users cloUser;
	
	@Transient 
	private Users crmUser;
	
	@Transient 
	private Users sdfUser;
	
	@Transient RegionTown regionTown;
	
	@Transient
	private String rejectionReasons;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private String finYearDisplay;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MgVerification other = (MgVerification) obj;
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
	 * @param id
	 *            the id to set
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
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Date getDateCaptured() {
		return dateCaptured;
	}

	public void setDateCaptured(Date dateCaptured) {
		this.dateCaptured = dateCaptured;
	}

	public Boolean getTrainingNeedsIdentified() {
		return trainingNeedsIdentified;
	}

	public void setTrainingNeedsIdentified(Boolean trainingNeedsIdentified) {
		this.trainingNeedsIdentified = trainingNeedsIdentified;
	}

	public Boolean getHasTrainingCommenced() {
		return hasTrainingCommenced;
	}

	public void setHasTrainingCommenced(Boolean hasTrainingCommenced) {
		this.hasTrainingCommenced = hasTrainingCommenced;
	}

	public Boolean getProofOfTrainingVerified() {
		return proofOfTrainingVerified;
	}

	public void setProofOfTrainingVerified(Boolean proofOfTrainingVerified) {
		this.proofOfTrainingVerified = proofOfTrainingVerified;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public Long getFinalFin() {
		return finalFin;
	}

	public void setFinalFin(Long finalFin) {
		this.finalFin = finalFin;
	}

	public YesNoLookup getWspSubmitted() {
		return wspSubmitted;
	}

	public void setWspSubmitted(YesNoLookup wspSubmitted) {
		this.wspSubmitted = wspSubmitted;
	}

	public YesNoLookup getAtrSubmitted() {
		return atrSubmitted;
	}

	public void setAtrSubmitted(YesNoLookup atrSubmitted) {
		this.atrSubmitted = atrSubmitted;
	}

	public YesNoLookup getOrganisedLabourUnion() {
		return organisedLabourUnion;
	}

	public void setOrganisedLabourUnion(YesNoLookup organisedLabourUnion) {
		this.organisedLabourUnion = organisedLabourUnion;
	}

	public YesNoLookup getTrainingComitteeMinutes() {
		return trainingComitteeMinutes;
	}

	public void setTrainingComitteeMinutes(YesNoLookup trainingComitteeMinutes) {
		this.trainingComitteeMinutes = trainingComitteeMinutes;
	}

	public YesNoLookup getEmployerAttendMeetings() {
		return employerAttendMeetings;
	}

	public void setEmployerAttendMeetings(YesNoLookup employerAttendMeetings) {
		this.employerAttendMeetings = employerAttendMeetings;
	}

	public YesNoLookup getLabourAttendMeetings() {
		return labourAttendMeetings;
	}

	public void setLabourAttendMeetings(YesNoLookup labourAttendMeetings) {
		this.labourAttendMeetings = labourAttendMeetings;
	}

	public YesNoLookup getMgSignedOff() {
		return mgSignedOff;
	}

	public void setMgSignedOff(YesNoLookup mgSignedOff) {
		this.mgSignedOff = mgSignedOff;
	}

	public YesNoLookup getCompleteInYear() {
		return completeInYear;
	}

	public void setCompleteInYear(YesNoLookup completeInYear) {
		this.completeInYear = completeInYear;
	}

	public YesNoLookup getTrainingCommenced() {
		return trainingCommenced;
	}

	public void setTrainingCommenced(YesNoLookup trainingCommenced) {
		this.trainingCommenced = trainingCommenced;
	}

	public YesNoLookup getTrainingCompletedAsPrevious() {
		return trainingCompletedAsPrevious;
	}

	public void setTrainingCompletedAsPrevious(YesNoLookup trainingCompletedAsPrevious) {
		this.trainingCompletedAsPrevious = trainingCompletedAsPrevious;
	}

	public YesNoLookup getProofTrainingVerified() {
		return proofTrainingVerified;
	}

	public void setProofTrainingVerified(YesNoLookup proofTrainingVerified) {
		this.proofTrainingVerified = proofTrainingVerified;
	}

	public Boolean getCompanyRepSignOff() {
		return companyRepSignOff;
	}

	public void setCompanyRepSignOff(Boolean companyRepSignOff) {
		this.companyRepSignOff = companyRepSignOff;
	}

	public Boolean getSdfSignOff() {
		return sdfSignOff;
	}

	public void setSdfSignOff(Boolean sdfSignOff) {
		this.sdfSignOff = sdfSignOff;
	}

	public Boolean getMersetaRepSignOff() {
		return mersetaRepSignOff;
	}

	public void setMersetaRepSignOff(Boolean mersetaRepSignOff) {
		this.mersetaRepSignOff = mersetaRepSignOff;
	}

	public Boolean getCrmSignOff() {
		return crmSignOff;
	}

	public void setCrmSignOff(Boolean crmSignOff) {
		this.crmSignOff = crmSignOff;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public RegionTown getRegionTown() {
		return regionTown;
	}

	public void setRegionTown(RegionTown regionTown) {
		this.regionTown = regionTown;
	}

	public Users getCloUser() {
		return cloUser;
	}

	public void setCloUser(Users cloUser) {
		this.cloUser = cloUser;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Boolean getDateForVisitProvided() {
		return dateForVisitProvided;
	}

	public void setDateForVisitProvided(Boolean dateForVisitProvided) {
		this.dateForVisitProvided = dateForVisitProvided;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Boolean getSubmitedForVerification() {
		return submitedForVerification;
	}

	public void setSubmitedForVerification(Boolean submitedForVerification) {
		this.submitedForVerification = submitedForVerification;
	}

	public Users getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(Users crmUser) {
		this.crmUser = crmUser;
	}

	public Users getSdfUser() {
		return sdfUser;
	}

	public void setSdfUser(Users sdfUser) {
		this.sdfUser = sdfUser;
	}

	public Users getCloGeneratedFor() {
		return cloGeneratedFor;
	}

	public void setCloGeneratedFor(Users cloGeneratedFor) {
		this.cloGeneratedFor = cloGeneratedFor;
	}

	public String getCloComment() {
		return cloComment;
	}

	public void setCloComment(String cloComment) {
		this.cloComment = cloComment;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public Users getPrimaryUserSignOff() {
		return primaryUserSignOff;
	}

	public void setPrimaryUserSignOff(Users primaryUserSignOff) {
		this.primaryUserSignOff = primaryUserSignOff;
	}

	public Long getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Long batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getFinYearDisplay() {
		return finYearDisplay;
	}

	public void setFinYearDisplay(String finYearDisplay) {
		this.finYearDisplay = finYearDisplay;
	}

	public Users getCrmUserAssigned() {
		return crmUserAssigned;
	}

	public void setCrmUserAssigned(Users crmUserAssigned) {
		this.crmUserAssigned = crmUserAssigned;
	}	
}
