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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * AssessorModeratorCompany.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "assessor_moderator_company")
public class AssessorModeratorCompany implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AssessorModeratorCompany. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** company of Assessor Moderator. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The Assessor Moderator. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_moderator_id", nullable = true)
	private Users assessorModerator;
	
	/** The AssessorModeratorApplication. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_assessor_moderator_application_id", nullable = true)
	private AssessorModeratorApplication forAssessorModeratorApplication;
	
	

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
		AssessorModeratorCompany other = (AssessorModeratorCompany) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new assessor moderator company.
	 */
	public AssessorModeratorCompany() {
		super();
	}

	/**
	 * Instantiates a new assessor moderator company.
	 *
	 * @param company the company
	 * @param assessorModerator the assessor moderator
	 */
	public AssessorModeratorCompany(Company company, Users assessorModerator) {
		super();
		this.company = company;
		this.assessorModerator = assessorModerator;
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
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the company of assessorModerator.
	 *
	 * @return the company of assessorModerator
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company            the new company of assessorModerator
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 *  
	 * @return assessorModerator
	 */
	public Users getAssessorModerator() {
		return assessorModerator;
	}

	/**
	 * Sets the assessor moderator.
	 *
	 * @param assessorModerator the new assessor moderator
	 */
	public void setAssessorModerator(Users assessorModerator) {
		this.assessorModerator = assessorModerator;
	}

	public AssessorModeratorApplication getForAssessorModeratorApplication() {
		return forAssessorModeratorApplication;
	}

	public void setForAssessorModeratorApplication(AssessorModeratorApplication forAssessorModeratorApplication) {
		this.forAssessorModeratorApplication = forAssessorModeratorApplication;
	}


}
