package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

/**
 * The Class UpdateAuditTrailChanges.
 */
@Entity
@Table(name = "update_audit_trail_changes")
public class UpdateAuditTrailChanges implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The audit trail. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_trail_id", nullable = true)
	private UpdateAuditTrail auditTrail;

	/** The field name. */
	@Column(name = "field_name", columnDefinition = "LONGTEXT")
	private String fieldName;

	/** The from value. */
	@Column(name = "from_value", columnDefinition = "LONGTEXT")
	private String fromValue;

	/** The to value. */
	@Column(name = "to_value", columnDefinition = "LONGTEXT")
	private String toValue;

	/**
	 * Instantiates a new update audit trail changes.
	 *
	 * @param auditTrail
	 *            the audit trail
	 * @param fieldName
	 *            the field name
	 * @param fromValue
	 *            the from value
	 * @param toValue
	 *            the to value
	 */
	public UpdateAuditTrailChanges(UpdateAuditTrail auditTrail, String fieldName, String fromValue, String toValue) {
		super();
		this.auditTrail = auditTrail;
		this.fieldName = fieldName;
		this.fromValue = fromValue;
		this.toValue = toValue;
	}

	/**
	 * Instantiates a new update audit trail changes.
	 */
	public UpdateAuditTrailChanges() {
		super();
		// TODO Auto-generated constructor stub
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateAuditTrailChanges other = (UpdateAuditTrailChanges) obj;
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the audit trail.
	 *
	 * @return the audit trail
	 */
	public UpdateAuditTrail getAuditTrail() {
		return auditTrail;
	}

	/**
	 * Sets the audit trail.
	 *
	 * @param auditTrail
	 *            the new audit trail
	 */
	public void setAuditTrail(UpdateAuditTrail auditTrail) {
		this.auditTrail = auditTrail;
	}

	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 *
	 * @param fieldName
	 *            the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the from value.
	 *
	 * @return the from value
	 */
	public String getFromValue() {
		return fromValue;
	}

	/**
	 * Sets the from value.
	 *
	 * @param fromValue
	 *            the new from value
	 */
	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}

	/**
	 * Gets the to value.
	 *
	 * @return the to value
	 */
	public String getToValue() {
		return toValue;
	}

	/**
	 * Sets the to value.
	 *
	 * @param toValue
	 *            the new to value
	 */
	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

}
