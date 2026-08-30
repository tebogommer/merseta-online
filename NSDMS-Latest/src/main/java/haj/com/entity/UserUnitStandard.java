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

import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity links a user that is a training provider type
 * to a unit standard 
 * 
 * CompanyUnitStandard.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "user_unit_standard")
@AuditTable(value = "user_unit_standard_hist")
@Audited
public class UserUnitStandard implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of CompanyUnitStandard. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The user linked to Unit Standard. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	/** The Unit Standard linked to the user. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id")
	private UnitStandards unitStandard;
	
	/** Note. */
	@Column(name="note", columnDefinition="LONGTEXT")
    private String note;
	
	/** Soft Deleted  Defaulted to false If true should not be displayed and / or referenced. */
	@Column(name="soft_delete")
    private Boolean softDelete;
	
	/**The AssessorModeratorApplication**/
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="for_assessor_moderator_application_id")
	private AssessorModeratorApplication forAssessorModeratorApplication;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_qualification_id")
	private Qualification forQualification;
	
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	@Column(name = "accept")
	private Boolean accept;
	
	/**
	 * Default Constructor.
	 */
	public UserUnitStandard() {
		super();
	}

	/**
	 * Constructor using user and unit standard fields.
	 *
	 * @param user the user
	 * @param unitStandard the unit standard
	 */
	public UserUnitStandard(Users user, UnitStandards unitStandard) {
		super();
		this.user = user;
		this.unitStandard = unitStandard;
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
		UserUnitStandard other = (UserUnitStandard) obj;
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
	 * Gets the unit standard.
	 *
	 * @return unitStandard
	 */
	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	/**
	 * Sets the unit standard.
	 *
	 * @param unitStandard the new unit standard
	 */
	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
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

	public Qualification getForQualification() {
		return forQualification;
	}

	public void setForQualification(Qualification forQualification) {
		this.forQualification = forQualification;
	}

}
