package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.MailingListTypeEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "mailing_list")
public class MailingList extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of AbetBand. */
	@Column(name = "description", length = 500)
	private String description;
	
	/** The email. */
	@Column(name = "email_address", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String emailAddress;

	@Enumerated
	@Column(name = "mailing_list_type_enum")
	private MailingListTypeEnum mailingListTypeEnum;
	
	/**
	 * Instantiates a new abet band.
	 */
	public MailingList() {
		super();
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MailingList other = (MailingList) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public MailingListTypeEnum getMailingListTypeEnum() {
		return mailingListTypeEnum;
	}

	public void setMailingListTypeEnum(MailingListTypeEnum mailingListTypeEnum) {
		this.mailingListTypeEnum = mailingListTypeEnum;
	}

}
