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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.enums.CreatedByEnum;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.SETMISFieldValidation;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learner_users")
public class CompanyLearnerUsers implements IDataEntity {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "user", fieldValue = "NOT_NULL")
	private Users user;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "createUser", fieldValue = "NOT_NULL")
	private Users createUser;
	
	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;
   

	/** The provider. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legal_gaurdian_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "legalGaurdian", fieldValue = "NOT_NULL")
	private Users legalGaurdian;
	
	@Enumerated
	@Column(name = "created_by_enum")
	private CreatedByEnum createdByEnum;
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
		CompanyLearnerUsers other = (CompanyLearnerUsers) obj;
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
	 * @param id the id to set
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getUser() {
		return user;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public Company getCompany() {
		return company;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public CreatedByEnum getCreatedByEnum() {
		return createdByEnum;
	}

	public void setCreatedByEnum(CreatedByEnum createdByEnum) {
		this.createdByEnum = createdByEnum;
	}

	public Users getLegalGaurdian() {
		return legalGaurdian;
	}

	public void setLegalGaurdian(Users legalGaurdian) {
		this.legalGaurdian = legalGaurdian;
	}
}
