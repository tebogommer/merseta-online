/**
 * 
 */
package haj.com.framework;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

// TODO: Auto-generated Javadoc
/**
 * AbstractLookup.
 *
 * @author TechFinium
 */
@MappedSuperclass
public abstract class AbstractLookup implements IDataEntity {

	/**
	 * Create Date of Object
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** SETMIS Code. */
	@Column(name = "setmis_code", length = 200, nullable = true)
	private String setmisCode;

	/** QCTO Code. */
	@Column(name = "qcto_code", length = 200, nullable = true)
	private String qctoCode;

	/** NLRD Code. */
	@Column(name = "nlrd_code", length = 200, nullable = true)
	private String nlrdCode;

	@Column(name = "sms_code", length = 200, nullable = true)
	private String smsCode;

	/** The code. */
	@Length(max = 20)
	@Column(name = "code", length = 20)
	private String code;

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
	 * Gets the setmis code.
	 *
	 * @return the setmisCode
	 */
	public String getSetmisCode() {
		return setmisCode;
	}

	/**
	 * Sets the setmis code.
	 *
	 * @param setmisCode
	 *            the setmisCode to set
	 */
	public void setSetmisCode(String setmisCode) {
		this.setmisCode = setmisCode;
	}

	/**
	 * Gets the qcto code.
	 *
	 * @return the qctoCode
	 */
	public String getQctoCode() {
		return qctoCode;
	}

	/**
	 * Sets the qcto code.
	 *
	 * @param qctoCode
	 *            the qctoCode to set
	 */
	public void setQctoCode(String qctoCode) {
		this.qctoCode = qctoCode;
	}

	/**
	 * Gets the nlrd code.
	 *
	 * @return the nlrdCode
	 */
	public String getNlrdCode() {
		return nlrdCode;
	}

	/**
	 * Sets the nlrd code.
	 *
	 * @param nlrdCode
	 *            the nlrdCode to set
	 */
	public void setNlrdCode(String nlrdCode) {
		this.nlrdCode = nlrdCode;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
