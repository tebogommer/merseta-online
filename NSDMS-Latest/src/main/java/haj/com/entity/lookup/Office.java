package haj.com.entity.lookup;

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
import org.hibernate.validator.constraints.Email;

import haj.com.entity.Address;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * AbetBand.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "office")
public class Office extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Office. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of Office. */
	@Column(name = "description", length = 500)
	private String description;
	
	/** 
	 * The fax number of Company. 
	 * Field length = 20
	 * This field may  be left blank 
	 * Field may not start with a space.
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "fax_number", length = 20, nullable = true)
//	@Size(min = 0, max = 20, message = "Fax number length can't be more than 20 characters")
	private String faxNumber;
	
	/** 
	 * The contact number of Office. 
	 * Field length = 20
	 * This field may  be left blank 
	 * Field may not start with a space.
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "contact_number", length = 20, nullable = true)
//	@Size(min = 0, max = 20, message = "Fax number length can't be more than 20 characters")
	private String contactNumber;
	
	/** 
	 * The email of the office. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space.
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 */
	@Column(name = "email", nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	@Column(name = "contact_person", length = 500)
	private String contactPerson;
	
	@Column(name = "contact_person_email", length = 500)
	@Email(message = "Please enter a valid Email Address")
	private String contactPersonEmail;
	
	/** 
	 * The residential address of Office. 
	 * The residential address of Office can be null.
	 */
	@Fetch(FetchMode.JOIN)
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "physical_address_id", nullable = true)
	private Address physicalAddress;

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
		Office other = (Office) obj;
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
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Address getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(Address physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	
}