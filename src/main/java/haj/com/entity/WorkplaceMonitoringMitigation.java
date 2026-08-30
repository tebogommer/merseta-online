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

import haj.com.entity.enums.StatusEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "workplace_monitoring_mitigation")
public class WorkplaceMonitoringMitigation implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of SiteVisit. */
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
	@JoinColumn(name = "workplace_monitoring_id", nullable = true)
	private WorkplaceMonitoring workplaceMonitoring;

	@Column(name = "non_compliance_issue")
	private String nonComplianceIssue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_date", length = 19)
	private Date actionDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date", length = 19)
	private Date dueDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsible_id", nullable = true)
	private Users responsible;

	@Column(name = "status")
	private StatusEnum status;

	@Transient
	private List<Doc> docs;

	public WorkplaceMonitoringMitigation() {
		super();
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
		WorkplaceMonitoringMitigation other = (WorkplaceMonitoringMitigation) obj;
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

	public WorkplaceMonitoring getWorkplaceMonitoring() {
		return workplaceMonitoring;
	}

	public void setWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) {
		this.workplaceMonitoring = workplaceMonitoring;
	}

	public String getNonComplianceIssue() {
		return nonComplianceIssue;
	}

	public void setNonComplianceIssue(String nonComplianceIssue) {
		this.nonComplianceIssue = nonComplianceIssue;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Users getResponsible() {
		return responsible;
	}

	public void setResponsible(Users responsible) {
		this.responsible = responsible;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
}
