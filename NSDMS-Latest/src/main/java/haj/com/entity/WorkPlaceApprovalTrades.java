package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.framework.IDataEntity;

@Entity
@Table(name = "work_place_approval_trades")
@AuditTable(value = "work_place_approval_trades_hist")
@Audited
public class WorkPlaceApprovalTrades implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workPlace_approval_id", nullable = true)
	private WorkPlaceApproval workPlaceApproval;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	@Column(name = "number_of_learners")
	private Integer numberOfLearners;

	@Column(name = "number_of_mentors")
	private Integer numberOfMentors;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delete_user_id", nullable = true)
	private Users deleteUser;


	public WorkPlaceApprovalTrades() {
		super();
	}

	public WorkPlaceApprovalTrades(WorkPlaceApproval workPlaceApproval) {
		super();
		this.workPlaceApproval = workPlaceApproval;
	}

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
		WorkPlaceApprovalTrades other = (WorkPlaceApprovalTrades) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Integer getNumberOfLearners() {
		return numberOfLearners;
	}

	public void setNumberOfLearners(Integer numberOfLearners) {
		this.numberOfLearners = numberOfLearners;
	}

	public Integer getNumberOfMentors() {
		return numberOfMentors;
	}

	public void setNumberOfMentors(Integer numberOfMentors) {
		this.numberOfMentors = numberOfMentors;
	}

	public Users getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Users deleteUser) {
		this.deleteUser = deleteUser;
	}
}
