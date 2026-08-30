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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "users_training_provider")
public class UsersTrainingProvider implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** 
	 * Create Date of Object. 
	 * Field length = 19
	 * Field required
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	@Size(min = 1, max = 19, message = "Date must be 19 characters")
	@NotEmpty(message ="Created date is required")
	private Date createDate;

	/** 
	 * The registration date.
	 * Field length = 19
	 * Field not required
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", length = 19)
	@Size(min = 1, max = 19, message = "Date must be 19 characters")
	private Date registrationDate;

	/** 
	 * The intervention start date. 
	 * Field length = 19
	 * Field not required
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "intervention_start_date", length = 19)
	@Size(min = 1, max = 19, message = "Date must be 19 characters")
	private Date interventionStartDate;

	/**  
	 * Update Date of Object. 
	 * Field length = 19
	 * Field not required
	 */
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	@Size(min = 1, max = 19, message = "Date must be 19 characters")
	private Date updateDate;

	/** 
	 * The user. 
	 * This field is required
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@NotEmpty(message ="User is required")
	private Users user;

	/** 
	 * The Company / Training Provider linked to qualification. 
	 * This field is required
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@NotEmpty(message ="Company is required")
	private Company company;

	/** The trainingDescription of TrainingProvider assigned to user. 
	  * This field is not required
	  * Length = 250
	  */
	@Column(name = "training_description", length = 250, nullable = true)
	@Size(min = 1, max = 250, message = "Training Description can't be more than 250 characters")
	private String trainingDescription;

	/** 
	 * The trainingDurationDescription of TrainingProvider assigned to user. 
	 * This field is not required
	 * Length = 250
	 */
	@Column(name = "training_duration_description", length = 250, nullable = true)
	@Size(min = 1, max = 250, message = "Training Duration Description can't be more than 250 characters")
	private String trainingDurationDescription;

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
		UsersTrainingProvider other = (UsersTrainingProvider) obj;
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
	 * Gets the update date.
	 *
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Sets the update date.
	 *
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the training description.
	 *
	 * @return the training description
	 */
	public String getTrainingDescription() {
		return trainingDescription;
	}

	/**
	 * Sets the training description.
	 *
	 * @param trainingDescription the new training description
	 */
	public void setTrainingDescription(String trainingDescription) {
		this.trainingDescription = trainingDescription;
	}

	/**
	 * Gets the training duration description.
	 *
	 * @return the training duration description
	 */
	public String getTrainingDurationDescription() {
		return trainingDurationDescription;
	}

	/**
	 * Sets the training duration description.
	 *
	 * @param trainingDurationDescription the new training duration description
	 */
	public void setTrainingDurationDescription(String trainingDurationDescription) {
		this.trainingDurationDescription = trainingDurationDescription;
	}

	/**
	 * Gets the registration date.
	 *
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets the registration date.
	 *
	 * @param registrationDate the new registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets the intervention start date.
	 *
	 * @return the intervention start date
	 */
	public Date getInterventionStartDate() {
		return interventionStartDate;
	}

	/**
	 * Sets the intervention start date.
	 *
	 * @param interventionStartDate the new intervention start date
	 */
	public void setInterventionStartDate(Date interventionStartDate) {
		this.interventionStartDate = interventionStartDate;
	}

}
