package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import haj.com.entity.enums.QmrGenderEnum;
import haj.com.framework.AbstractLookup;

/**
 * The Class Gender.
 */
@Entity
@Table(name = "gender")
public class Gender extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The gender name. */
	@Column(name = "gender_name", length = 10)
	private String genderName;

	/** The gender code. */
	@Column(name = "gender_code", length = 1)
	private String genderCode;

	@Column(name = "qmr_gender", length = 10)
	private QmrGenderEnum qmrGender;

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
		Gender other = (Gender) obj;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractLookup#getCreateDate()
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractLookup#setCreateDate(java.util.Date)
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the gender name.
	 *
	 * @return the gender name
	 */
	public String getGenderName() {
		return genderName;
	}

	/**
	 * Sets the gender name.
	 *
	 * @param genderName
	 *            the new gender name
	 */
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	/**
	 * Gets the gender code.
	 *
	 * @return the gender code
	 */
	public String getGenderCode() {
		return genderCode;
	}

	/**
	 * Sets the gender code.
	 *
	 * @param genderCode
	 *            the new gender code
	 */
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public QmrGenderEnum getQmrGender() {
		return qmrGender;
	}

	public void setQmrGender(QmrGenderEnum qmrGender) {
		this.qmrGender = qmrGender;
	}

}
