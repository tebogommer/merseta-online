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

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * QualificationMultipleSelection.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qualification_multiple_selection")
@AuditTable(value = "qualification_multiple_selection_hist")
@Audited
public class QualificationMultipleSelection implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of RejectReasonMultipleSelection. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** target key of entity. */
	@Column(name = "target_key", nullable = false)
	private Long targetKey;

	/** target_class. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

	/** link to Qualification. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = false)
	private Qualification qualification;

	/** User who created */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	/** Description of RejectReasons. */
	@Column(name = "for_process", length = 500)
	private ConfigDocProcessEnum forProcess;
	

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
		QualificationMultipleSelection other = (QualificationMultipleSelection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public QualificationMultipleSelection() {
		super();
	}

	public QualificationMultipleSelection(Long targetKey, String targetClass, Qualification qualification,
			Users createUserPassed, ConfigDocProcessEnum configDocProcessEnum) {
		super();
		this.createDate = new Date();
		this.targetKey = targetKey;
		this.targetClass = targetClass;
		this.qualification = qualification;
		if (createUserPassed != null) {
			this.createUser = createUserPassed;
		}
		if (configDocProcessEnum != null) {
			this.forProcess = configDocProcessEnum;
		}
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

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public ConfigDocProcessEnum getForProcess() {
		return forProcess;
	}

	public void setForProcess(ConfigDocProcessEnum forProcess) {
		this.forProcess = forProcess;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	
}
