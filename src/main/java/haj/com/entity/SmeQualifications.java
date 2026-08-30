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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

/**
 * SmeQualifications.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sme_qualifications")
@AuditTable(value = "sme_qualifications_hist")
@Audited
public class SmeQualifications implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of SmeQualifications. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	

	/** The Qualification */
//	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	/** The SME linked to the Qualification */
//	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sites_sme_id", nullable = true)
	private SitesSme sitesSme;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approve_user_id", nullable = true)
	private Users approveUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;
	
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Column(name = "acepted", columnDefinition = "BIT default false")
	private Boolean acepted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sme_qualifications_parent_id", nullable = true)
	private SmeQualifications smeQualificationsParent;
	
	@Transient
	private boolean selected;
	
	@Transient
	private Integer learnerAssignedCount;
	
	@Transient
	private List<Doc> docs;

	@Transient
	private String reason;
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
		SmeQualifications other = (SmeQualifications) obj;
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

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public Integer getLearnerAssignedCount() {
		return learnerAssignedCount;
	}

	public void setLearnerAssignedCount(Integer learnerAssignedCount) {
		this.learnerAssignedCount = learnerAssignedCount;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public boolean isAcepted() {
		return acepted;
	}

	public void setAcepted(boolean acepted) {
		this.acepted = acepted;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public SmeQualifications getSmeQualificationsParent() {
		return smeQualificationsParent;
	}

	public void setSmeQualificationsParent(SmeQualifications smeQualificationsParent) {
		this.smeQualificationsParent = smeQualificationsParent;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
