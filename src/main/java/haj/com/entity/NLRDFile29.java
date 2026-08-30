package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * NLRDFile29.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nlrd_file_29")
public class NLRDFile29 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of NLRDFile29. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "person_alternate_id")
	private String personAlternateId;
	
	@Column(name = "alternative_id_type")
	private BigInteger alternativeIdType;
	
	@Column(name = "qualification_id")
	private Integer qualificationId;
	
	@Column(name = "learner_achievement_status_id")
	private BigInteger learnerAchievementStatusId;
	
	@Column(name = "assessor_registration_number")
	private String assessorRegistrationNumber;
	
	@Column(name = "learner_achievement_type_id")
	private String learnerAchievementTypeId;
	
	@Column(name = "learner_achievement_date")
	private Date learnerAchievementDate;
	
	@Column(name = "learner_enrolled_date")
	private Date learnerEnrolledDate;
	
	@Column(name = "honours_classification")
	private String honoursClassification;
	
	@Column(name = "part_of")
	private String partof;
	
	@Column(name = "learnership_id")
	private String learnershipId;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqa_id")
	private String providerEtqaId;
	
	@Column(name = "assessor_etqa_id")
	private String assessorEtqaId;
	
	@Column(name = "certification_date")
	private Date certificationDate;
	
	@Column(name = "date_stamp")
	private Date dateStamp;

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
		NLRDFile29 other = (NLRDFile29) obj;
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

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPersonAlternateId() {
		return personAlternateId;
	}

	public void setPersonAlternateId(String personAlternateId) {
		this.personAlternateId = personAlternateId;
	}

	public BigInteger getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(BigInteger alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public BigInteger getLearnerAchievementStatusId() {
		return learnerAchievementStatusId;
	}

	public void setLearnerAchievementStatusId(BigInteger learnerAchievementStatusId) {
		this.learnerAchievementStatusId = learnerAchievementStatusId;
	}

	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}

	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}

	public String getLearnerAchievementTypeId() {
		return learnerAchievementTypeId;
	}

	public void setLearnerAchievementTypeId(String learnerAchievementTypeId) {
		this.learnerAchievementTypeId = learnerAchievementTypeId;
	}

	public Date getLearnerAchievementDate() {
		return learnerAchievementDate;
	}

	public void setLearnerAchievementDate(Date learnerAchievementDate) {
		this.learnerAchievementDate = learnerAchievementDate;
	}

	public Date getLearnerEnrolledDate() {
		return learnerEnrolledDate;
	}

	public void setLearnerEnrolledDate(Date learnerEnrolledDate) {
		this.learnerEnrolledDate = learnerEnrolledDate;
	}

	public String getHonoursClassification() {
		return honoursClassification;
	}

	public void setHonoursClassification(String honoursClassification) {
		this.honoursClassification = honoursClassification;
	}

	public String getPartof() {
		return partof;
	}

	public void setPartof(String partof) {
		this.partof = partof;
	}

	public String getLearnershipId() {
		return learnershipId;
	}

	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderEtqaId() {
		return providerEtqaId;
	}

	public void setProviderEtqaId(String providerEtqaId) {
		this.providerEtqaId = providerEtqaId;
	}

	public String getAssessorEtqaId() {
		return assessorEtqaId;
	}

	public void setAssessorEtqaId(String assessorEtqaId) {
		this.assessorEtqaId = assessorEtqaId;
	}

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
