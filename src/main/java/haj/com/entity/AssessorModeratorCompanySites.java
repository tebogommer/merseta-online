package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.framework.IDataEntity;

/**
 * AssessorModeratorCompanySites.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "assessor_moderator_company_sites")
@AuditTable(value = "assessor_moderator_company_sites_hist")
@Audited
public class AssessorModeratorCompanySites implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AssessorModeratorCompanySites. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_moderator_id", nullable = true)
	private Users assessorModerator;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_site_id", nullable = true)
	private TrainingSite trainingSite;
	
	@Enumerated
	@Column(name = "assessor_mod_type")
	private AssessorModType assessorModType;
	
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_moderator_application_id", nullable = true)
	private AssessorModeratorApplication assessorModeratorApplication;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user", nullable = true)
	private Users lastActionUser;
	
	@Transient
	private Boolean canAlter;
	
	/*
	 * Index:
	 * CREATE INDEX assessormoderatorsites_user_company_site ON assessor_moderator_company_sites (assessor_moderator_id, company_id, training_site_id);
	 * CREATE INDEX assessormoderatorsites_user_company ON assessor_moderator_company_sites (assessor_moderator_id, company_id);
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
		AssessorModeratorCompanySites other = (AssessorModeratorCompanySites) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new assessor moderator company.
	 */
	public AssessorModeratorCompanySites() {
		super();
	}

	/**
	 * Instantiates a new assessor moderator company.
	 *
	 * @param company the company
	 * @param assessorModerator the assessor moderator
	 */
	public AssessorModeratorCompanySites(Company company, Users assessorModerator) {
		super();
		this.company = company;
		this.assessorModerator = assessorModerator;
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
	 * @param id            the id to set
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
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the company of assessorModerator.
	 *
	 * @return the company of assessorModerator
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company            the new company of assessorModerator
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 *  
	 * @return assessorModerator
	 */
	public Users getAssessorModerator() {
		return assessorModerator;
	}

	/**
	 * Sets the assessor moderator.
	 *
	 * @param assessorModerator the new assessor moderator
	 */
	public void setAssessorModerator(Users assessorModerator) {
		this.assessorModerator = assessorModerator;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public AssessorModType getAssessorModType() {
		return assessorModType;
	}

	public void setAssessorModType(AssessorModType assessorModType) {
		this.assessorModType = assessorModType;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public Boolean getCanAlter() {
		return canAlter;
	}

	public void setCanAlter(Boolean canAlter) {
		this.canAlter = canAlter;
	}


}
