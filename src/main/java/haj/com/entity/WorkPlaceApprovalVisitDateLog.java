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

import haj.com.entity.lookup.DateChangeReasons;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "work_place_approval_visit_date_log")
public class WorkPlaceApprovalVisitDateLog implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_visit", length = 19)
	private Date dateOfVisit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workPlace_approval_id", nullable = true)
	private WorkPlaceApproval workPlaceApproval;
	
	/**
	 * If the entry was system generated or user
	 */
	@Column(name = "system_generated")
	private Boolean systemGenerated;
	
	/**
	 * The user who changed the visit date
	 * if null system generated
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	/**
	 * Additional Info If Required
	 */
	@Column(name = "additional_info", length = 100, nullable = true)
	private String additionalInfo;
	
	@Transient
	List<DateChangeReasons> dateChangeReasonsList;

	public WorkPlaceApprovalVisitDateLog() {
		super();
	}

	public WorkPlaceApprovalVisitDateLog(Date dateOfVisit, WorkPlaceApproval workPlaceApproval, Boolean systemGenerated,
			Users user, String additionalInfo) {
		super();
		this.dateOfVisit = dateOfVisit;
		this.workPlaceApproval = workPlaceApproval;
		this.systemGenerated = systemGenerated;
		if (systemGenerated) {
			this.user = user;
		} else {
			this.user = null;
		}
		if (additionalInfo == null || additionalInfo.isEmpty()) {
			this.additionalInfo = null;
		}else {
			this.additionalInfo = additionalInfo;
		}
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
		WorkPlaceApprovalVisitDateLog other = (WorkPlaceApprovalVisitDateLog) obj;
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





	public Date getDateOfVisit() {
		return dateOfVisit;
	}





	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}





	public Users getUser() {
		return user;
	}





	public void setUser(Users user) {
		this.user = user;
	}

	public Boolean getSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(Boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public List<DateChangeReasons> getDateChangeReasonsList() {
		return dateChangeReasonsList;
	}

	public void setDateChangeReasonsList(List<DateChangeReasons> dateChangeReasonsList) {
		this.dateChangeReasonsList = dateChangeReasonsList;
	}
	
}
