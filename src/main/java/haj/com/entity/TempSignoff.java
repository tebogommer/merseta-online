package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.IdPassportEnum;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckID;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.UserValidationService;

@Entity
@Table(name = "temp_signoff")
public class TempSignoff implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;

	@Column(name = "middle_name", length = 100, nullable = true)
	private String middleName;

	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	@CheckID(message = "RSA ID number not valid")
	@Column(name = "id_number", length = 13, nullable = true)
	private String idNumber;
	
	/** The passport number. */
	@Column(name = "passport_number", length = 30, nullable = true)
	private String passportNumber;
	
	@Enumerated
	@Column(name = "id_passport_selection", length = 30, nullable = true)
	private IdPassportEnum idPassportSelection;

	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user", nullable = true)
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;

	@Column(name = "ref_to_sign_off_flat")
	private Long refToSignOffFlat;
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
		TempSignoff other = (TempSignoff) obj;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public Long getRefToSignOffFlat() {
		return refToSignOffFlat;
	}

	public void setRefToSignOffFlat(Long refToSignOffFlat) {
		this.refToSignOffFlat = refToSignOffFlat;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public IdPassportEnum getIdPassportSelection() {
		return idPassportSelection;
	}

	public void setIdPassportSelection(IdPassportEnum idPassportSelection) {
		this.idPassportSelection = idPassportSelection;
	}

}
