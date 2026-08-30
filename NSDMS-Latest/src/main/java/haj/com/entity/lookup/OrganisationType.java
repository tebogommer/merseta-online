package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.entity.enums.PublicPrivateEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * OrganisationType.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "organisation_type")
public class OrganisationType extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of OrganisationType. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of OrganisationType. */
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "provide_registration_certificate_on_reg", columnDefinition = "BIT default false")
	private Boolean provideRegistrationCertificateOnReg;
	
	@Column(name = "work_place_approval_required", columnDefinition = "BIT default false")
	private Boolean workplaceApprovalRequired;
	
	@Enumerated
	@Column(name = "public_private")
	private PublicPrivateEnum publicPrivate;

	/**
	 * Instantiates a new bank.
	 */
	public OrganisationType() {
		super();
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
		OrganisationType other = (OrganisationType) obj;
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
	 * @param id            the id to set
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
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getProvideRegistrationCertificateOnReg() {
		return provideRegistrationCertificateOnReg;
	}

	public void setProvideRegistrationCertificateOnReg(Boolean provideRegistrationCertificateOnReg) {
		this.provideRegistrationCertificateOnReg = provideRegistrationCertificateOnReg;
	}

	public Boolean getWorkplaceApprovalRequired() {
		return workplaceApprovalRequired;
	}

	public void setWorkplaceApprovalRequired(Boolean workplaceApprovalRequired) {
		this.workplaceApprovalRequired = workplaceApprovalRequired;
	}

	public PublicPrivateEnum getPublicPrivate() {
		return publicPrivate;
	}

	public void setPublicPrivate(PublicPrivateEnum publicPrivate) {
		this.publicPrivate = publicPrivate;
	}
}
