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
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

/**
 * AdministrationOfAQP.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "administration_of_apq")
@AuditTable(value = "administration_of_apq_hist")
@Audited
public class AdministrationOfAQP implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AdministrationOfAQP. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_id", nullable = true)
	private Users assessor;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moderator_id", nullable = true)
	private Users moderator;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users contactPerson;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_center_id", nullable = true)
	private Company assessmentCenter;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eisa_date", length = 19)
	private Date eisaDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	private Date endTime;

	@Enumerated
	@Column(name = "recieved_required_training")
	private YesNoEnum recievedRequiredTraining;

	@Enumerated
	@Column(name = "in_possession_final_eisa")
	private YesNoEnum inPossessionFinalEISA;

	@Enumerated
	@Column(name = "exemplar_eisa_has_been_published")
	private YesNoEnum exemplarEISAHasBeenPublished;

	@Enumerated
	@Column(name = "entry_requirements_met")
	private YesNoEnum entryRequirementsMet;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Transient
	private List<Doc> docs;

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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AdministrationOfAQP other = (AdministrationOfAQP) obj;
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

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Date getEisaDate() {
		return eisaDate;
	}

	public void setEisaDate(Date eisaDate) {
		this.eisaDate = eisaDate;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public YesNoEnum getRecievedRequiredTraining() {
		return recievedRequiredTraining;
	}

	public void setRecievedRequiredTraining(YesNoEnum recievedRequiredTraining) {
		this.recievedRequiredTraining = recievedRequiredTraining;
	}

	public YesNoEnum getInPossessionFinalEISA() {
		return inPossessionFinalEISA;
	}

	public void setInPossessionFinalEISA(YesNoEnum inPossessionFinalEISA) {
		this.inPossessionFinalEISA = inPossessionFinalEISA;
	}

	public YesNoEnum getExemplarEISAHasBeenPublished() {
		return exemplarEISAHasBeenPublished;
	}

	public void setExemplarEISAHasBeenPublished(YesNoEnum exemplarEISAHasBeenPublished) {
		this.exemplarEISAHasBeenPublished = exemplarEISAHasBeenPublished;
	}

	public YesNoEnum getEntryRequirementsMet() {
		return entryRequirementsMet;
	}

	public void setEntryRequirementsMet(YesNoEnum entryRequirementsMet) {
		this.entryRequirementsMet = entryRequirementsMet;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Users getAssessor() {
		return assessor;
	}

	public void setAssessor(Users assessor) {
		this.assessor = assessor;
	}

	public Users getModerator() {
		return moderator;
	}

	public void setModerator(Users moderator) {
		this.moderator = moderator;
	}

	public Users getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(Users contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Company getAssessmentCenter() {
		return assessmentCenter;
	}

	public void setAssessmentCenter(Company assessmentCenter) {
		this.assessmentCenter = assessmentCenter;
	}

}
