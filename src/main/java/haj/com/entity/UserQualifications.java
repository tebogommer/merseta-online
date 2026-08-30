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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationType;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity links a user that is a training provider type
 * to a qualification 
 * 
 * CompanyQualifications.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "user_qualifications")
@AuditTable(value = "user_qualifications_hist")
@Audited
public class UserQualifications implements IDataEntity {	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of CompanyQualifications. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The user linked to Qualification. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	/** The Qualification linked to the User. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;
	
	/** Additional Note. */
	@Column(name="note", columnDefinition="LONGTEXT")
    private String note;
	
	
	/** The qualification type. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_type_id", nullable = true)
	private QualificationType qualificationType;
	
	/** Soft Deleted  Defaulted to false If true should not be displayed and / or referenced. */
	@Column(name="soft_delete")
    private Boolean softDelete;
	
	/**The AssessorModeratorApplication**/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="for_assessor_moderator_application_id")
	private AssessorModeratorApplication forAssessorModeratorApplication;
	
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	@Column(name = "accept")
	private Boolean accept;

	@Column(name = "qualification_updated",columnDefinition="BIT default 0")
	private Boolean qualificationUpdated;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id", nullable = true)
	private Users updateUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	private Date updateDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expirty_date", length = 19, nullable = true)
	private Date expirtyDate;
	
	/**
	 * Default Constructor.
	 */
	public UserQualifications() {
		super();
	}
		
	/**
	 * Constructor using user and qualification fields.
	 *
	 * @param user the user
	 * @param qualification the qualification
	 */
	public UserQualifications(Users user, Qualification qualification) {
		super();
		this.user = user;
		this.qualification = qualification;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
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
		UserQualifications other = (UserQualifications) obj;
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

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the soft delete.
	 *
	 * @return softDelete
	 */
	public Boolean getSoftDelete() {
		return softDelete;
	}

	/**
	 * Sets the soft delete.
	 *
	 * @param softDelete the new soft delete
	 */
	public void setSoftDelete(Boolean softDelete) {
		this.softDelete = softDelete;
	}

	/**
	 * Gets the user.
	 *
	 * @return user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the qualification type.
	 *
	 * @return the qualification type
	 */
	public QualificationType getQualificationType() {
		return qualificationType;
	}

	/**
	 * Sets the qualification type.
	 *
	 * @param qualificationType the new qualification type
	 */
	public void setQualificationType(QualificationType qualificationType) {
		this.qualificationType = qualificationType;
	}

	public AssessorModeratorApplication getForAssessorModeratorApplication() {
		return forAssessorModeratorApplication;
	}

	public void setForAssessorModeratorApplication(AssessorModeratorApplication forAssessorModeratorApplication) {
		this.forAssessorModeratorApplication = forAssessorModeratorApplication;
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

	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public Boolean getQualificationUpdated() {
		return qualificationUpdated;
	}

	public void setQualificationUpdated(Boolean qualificationUpdated) {
		this.qualificationUpdated = qualificationUpdated;
	}

	public Users getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Users updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getExpirtyDate() {
		return expirtyDate;
	}

	public void setExpirtyDate(Date expirtyDate) {
		this.expirtyDate = expirtyDate;
	}

}
