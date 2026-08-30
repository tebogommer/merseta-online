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

import haj.com.entity.YesNoLookup;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * EconomicStatus.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdf_type")
public class SDFType extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of EconomicStatus. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of EconomicStatus. */
	@Column(name = "description", length = 500)
	private String description;

	/** The sign off wsp. */
	@Column(name = "sign_off_wsp")
	private Boolean signOffWsp;

	/** The sign on recognition. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sign_on_recognition", nullable = true)
	private YesNoLookup signOnRecognition;
	
	/** The sign off wsp. */
	@Column(name = "register_learners")
	private Boolean registerLearners;
	
	@Column(name = "register_mentors")
	private Boolean registerMentors;

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
		SDFType other = (SDFType) obj;
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

	/**
	 * Gets the sign off wsp.
	 *
	 * @return the sign off wsp
	 */
	public Boolean getSignOffWsp() {
		return signOffWsp;
	}

	/**
	 * Sets the sign off wsp.
	 *
	 * @param signOffWsp the new sign off wsp
	 */
	public void setSignOffWsp(Boolean signOffWsp) {
		this.signOffWsp = signOffWsp;
	}

	/**
	 * Gets the sign on recognition.
	 *
	 * @return the sign on recognition
	 */
	public YesNoLookup getSignOnRecognition() {
		return signOnRecognition;
	}

	/**
	 * Sets the sign on recognition.
	 *
	 * @param signOnRecognition the new sign on recognition
	 */
	public void setSignOnRecognition(YesNoLookup signOnRecognition) {
		this.signOnRecognition = signOnRecognition;
	}

	public Boolean getRegisterLearners() {
		return registerLearners;
	}

	public void setRegisterLearners(Boolean registerLearners) {
		this.registerLearners = registerLearners;
	}

	public Boolean getRegisterMentors() {
		return registerMentors;
	}

	public void setRegisterMentors(Boolean registerMentors) {
		this.registerMentors = registerMentors;
	}
}
