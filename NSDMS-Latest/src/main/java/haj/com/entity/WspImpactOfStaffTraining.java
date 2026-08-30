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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp Skills Gap.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_impact_of_staff_training")
public class WspImpactOfStaffTraining implements IDataEntity, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** row description of Wsp Skills Gap. */
	@Column(name = "row_description", length = 500)
	private String rowDescription;

	/** The manager selection */
	@Column(name = "toa_large_extent")
	private Boolean toaLargeExtent;
	/** The manager selection */
	@Column(name = "toa_limited_extent")
	private Boolean toaLimitedExtent;
	/** The manager selection */
	@Column(name = "nuetral")
	private Boolean nuetral;
	/** The manager selection */
	@Column(name = "not_really")
	private Boolean notReally;
	/** The manager selection */
	@Column(name = "not_at_all")
	private Boolean notAtAll;

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
		WspImpactOfStaffTraining other = (WspImpactOfStaffTraining) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

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

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public String getRowDescription() {
		return rowDescription;
	}

	public void setRowDescription(String rowDescription) {
		this.rowDescription = rowDescription;
	}

	public Boolean getToaLargeExtent() {
		return toaLargeExtent;
	}

	public void setToaLargeExtent(Boolean toaLargeExtent) {
		this.toaLargeExtent = toaLargeExtent;
	}

	public Boolean getToaLimitedExtent() {
		return toaLimitedExtent;
	}

	public void setToaLimitedExtent(Boolean toaLimitedExtent) {
		this.toaLimitedExtent = toaLimitedExtent;
	}

	public Boolean getNuetral() {
		return nuetral;
	}

	public void setNuetral(Boolean nuetral) {
		this.nuetral = nuetral;
	}

	public Boolean getNotReally() {
		return notReally;
	}

	public void setNotReally(Boolean notReally) {
		this.notReally = notReally;
	}

	public Boolean getNotAtAll() {
		return notAtAll;
	}

	public void setNotAtAll(Boolean notAtAll) {
		this.notAtAll = notAtAll;
	}
	
	public boolean getCheckAlreadyAnswered() {
		return (toaLargeExtent != null && toaLargeExtent) || (toaLimitedExtent != null && toaLimitedExtent) || (nuetral != null && nuetral) || (notReally != null && notReally) || (notAtAll != null && notAtAll);

	}
}
