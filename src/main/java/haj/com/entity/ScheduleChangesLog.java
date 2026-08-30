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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * ScheduleChangesLog.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "schedule_changes_log")
public class ScheduleChangesLog implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of ScheduleChangesLog. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The company if applicable */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "for_user_id", nullable = true)
	private Users forUser;

	/** The target class */
	@Column(name = "target_class")
	private String targetClass;

	/** The target key */
	@Column(name = "target_key")
	private Long targetKey;

	@Column(name = "information", columnDefinition = "LONGTEXT")
	private String information;
	
	@Column(name = "manual_intervention")
	private Boolean manualIntervention;

	@Transient
	private DgVerification dgVerification;

	@Transient
	private Wsp wsp;

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
		ScheduleChangesLog other = (ScheduleChangesLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public ScheduleChangesLog() {
		super();
	}

	public ScheduleChangesLog(String targetClass, Long targetKey, String information, Boolean manualIntervention) {
		super();
		this.targetClass = targetClass;
		this.targetKey = targetKey;
		this.information = information;
		this.manualIntervention = manualIntervention;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Boolean getManualIntervention() {
		return manualIntervention;
	}

	public void setManualIntervention(Boolean manualIntervention) {
		this.manualIntervention = manualIntervention;
	}

	public Users getForUser() {
		return forUser;
	}

	public void setForUser(Users forUser) {
		this.forUser = forUser;
	}
}
