package haj.com.entity;

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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Record;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_achievement_status_eisa")
@AuditTable(value = "company_learners_achievement_status_eisa_hist")
@Audited
@Record
public class CompanyLearnersAchievementStatusEISA implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "administration_of_aqp_learners_id", nullable = true)
	private AdministrationOfAQPLearners administrationOfAQPLearners;

	@Column(name = "assessment_partner_code", length = 20)
	private String assessmentPartnerCode;

	// Assessment Centre Code TEXT 20 41
	@Column(name = "assessment_centre_code", length = 20)
	private String assessmentCentreCode;

	// National ID TEXT 15 61
	@Column(name = "national_id", length = 15)
	private String nationalID;

	// Learner Alternate ID TEXT 20 76
	@Column(name = "learner_alternate_id", length = 20)
	private String learnerAlternateId;

	// Qualification ID TEXT 20 96
	@Column(name = "qualification_id", length = 20)
	private String qualificationId;

	// EISA Component Number NUMBER 3 116
	@Column(name = "eisa_component_number", length = 20)
	private String eisaComponentNumber;

	// EISA Component Achievement Value TEXT 3 119
	@Column(name = "eisa_component_achievement_value", length = 20)
	private String eisaComponentAchievementValue;

	// Date Assessed TEXT 8 122
	@Column(name = "date_assessed", length = 8)
	private String dateAssessed;

	// EISA Percentage Obtained TEXT 3 130
	@Column(name = "eisa_percentage_obtained", length = 20)
	private String eisaPercentageObtained;

	// Date Stamp DATE 8 133
	@Column(name = "date_stamp", length = 8)
	private String dateStamp;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the assessmentPartnerCode
	 */
	// Assessment Partner Code TEXT 20 1
	@Field(offset = 1, length = 20)
	public String getAssessmentPartnerCode() {
		return assessmentPartnerCode;
	}

	/**
	 * @param assessmentPartnerCode
	 *            the assessmentPartnerCode to set
	 */
	public void setAssessmentPartnerCode(String assessmentPartnerCode) {
		this.assessmentPartnerCode = assessmentPartnerCode;
	}

	/**
	 * @return the assessmentCentreCode
	 */
	@Field(offset = 41, length = 20)
	public String getAssessmentCentreCode() {
		return assessmentCentreCode;
	}

	/**
	 * @param assessmentCentreCode
	 *            the assessmentCentreCode to set
	 */
	public void setAssessmentCentreCode(String assessmentCentreCode) {
		this.assessmentCentreCode = assessmentCentreCode;
	}

	/**
	 * @return the nationalID
	 */
	@Field(offset = 61, length = 15)
	public String getNationalID() {
		return nationalID;
	}

	/**
	 * @param nationalID
	 *            the nationalID to set
	 */
	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	/**
	 * @return the learnerAlternateId
	 */
	@Field(offset = 76, length = 20)
	public String getLearnerAlternateId() {
		return learnerAlternateId;
	}

	/**
	 * @param learnerAlternateId
	 *            the learnerAlternateId to set
	 */
	public void setLearnerAlternateId(String learnerAlternateId) {
		this.learnerAlternateId = learnerAlternateId;
	}

	/**
	 * @return the qualificationId
	 */
	@Field(offset = 96, length = 20)
	public String getQualificationId() {
		return qualificationId;
	}

	/**
	 * @param qualificationId
	 *            the qualificationId to set
	 */
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	/**
	 * @return the eisaComponentNumber
	 */
	@Field(offset = 116, length = 3)
	public String getEisaComponentNumber() {
		return eisaComponentNumber;
	}

	/**
	 * @param eisaComponentNumber
	 *            the eisaComponentNumber to set
	 */
	public void setEisaComponentNumber(String eisaComponentNumber) {
		this.eisaComponentNumber = eisaComponentNumber;
	}

	/**
	 * @return the eisaComponentAchievementValue
	 */
	@Field(offset = 119, length = 3)
	public String getEisaComponentAchievementValue() {
		return eisaComponentAchievementValue;
	}

	/**
	 * @param eisaComponentAchievementValue
	 *            the eisaComponentAchievementValue to set
	 */
	public void setEisaComponentAchievementValue(String eisaComponentAchievementValue) {
		this.eisaComponentAchievementValue = eisaComponentAchievementValue;
	}

	/**
	 * @return the dateAssessed
	 */
	@Field(offset = 122, length = 8)
	public String getDateAssessed() {
		return dateAssessed;
	}

	/**
	 * @param dateAssessed
	 *            the dateAssessed to set
	 */
	public void setDateAssessed(String dateAssessed) {
		this.dateAssessed = dateAssessed;
	}

	/**
	 * @return the eisaPercentageObtained
	 */
	@Field(offset = 120, length = 3)
	public String getEisaPercentageObtained() {
		return eisaPercentageObtained;
	}

	/**
	 * @param eisaPercentageObtained
	 *            the eisaPercentageObtained to set
	 */
	public void setEisaPercentageObtained(String eisaPercentageObtained) {
		this.eisaPercentageObtained = eisaPercentageObtained;
	}

	/**
	 * @return the dateStamp
	 */
	@Field(offset = 133, length = 8)
	public String getDateStamp() {
		return dateStamp;
	}

	/**
	 * @param dateStamp
	 *            the dateStamp to set
	 */
	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}

	/**
	 * @return the administrationOfAQPLearners
	 */
	public AdministrationOfAQPLearners getAdministrationOfAQPLearners() {
		return administrationOfAQPLearners;
	}

	/**
	 * @param administrationOfAQPLearners
	 *            the administrationOfAQPLearners to set
	 */
	public void setAdministrationOfAQPLearners(AdministrationOfAQPLearners administrationOfAQPLearners) {
		this.administrationOfAQPLearners = administrationOfAQPLearners;
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
		CompanyLearnersAchievementStatusEISA other = (CompanyLearnersAchievementStatusEISA) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}
