package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigInteger;
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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.QmrEnteredCompletedEnum;
import haj.com.entity.enums.QmrEquityEnum;
import haj.com.entity.enums.QmrGenderEnum;
import haj.com.framework.IDataEntity;

/**
 * QmrLearnershipData.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qmr_artisan_data")
@AuditTable(value = "qmr_artisan_data_hist")
@Audited
public class QmrArtisanData implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of QmrLearnershipData. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	// link to the quarter
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qmr_fin_years_id", nullable = true)
	private QmrFinYears qmrFinYears;

	// Entered / Completed indicator
	@Enumerated
	@Column(name = "qmr_entered_completed", length = 19)
	private QmrEnteredCompletedEnum qmrEnteredCompleted;

	// Gender Indicator
	@Enumerated
	@Column(name = "qmr_gender", length = 19)
	private QmrGenderEnum qmrGender;

	// Equity Indicator
	@Enumerated
	@Column(name = "qmr_equity", length = 19)
	private QmrEquityEnum qmrEquity;

	// Employed / unemployed Indicator
	@Enumerated
	@Column(name = "employed_un_employed", length = 19)
	private EmploymentStatusEnum employedUnEmployed;

	// Age Indicator
	@Column(name = "age_indicator", length = 10)
	private Integer ageIndicator;

	// Citizen indicator
	@Column(name = "rsa_citizen")
	private Boolean rsaCitizen;

	// Disabled indicator
	@Column(name = "disabilityIdentified")
	private Boolean disabilityIdentified;

	// Company Learner Flat Link
	@Column(name = "company_learner_flat_link")
	private Long companyLearnerFlatLink;

	/* QMR Data Start */
	@Column(name = "reporting_period")
	private String reportingPeriod;

	@Column(name = "ofo_code")
	private String ofoCode;

	@Column(name = "artisan_trade_discription")
	private String artisanTradeDiscription;

	@Column(name = "does_the_programme_address_ssp_needs_sip_skills")
	private String doesTheProgrammeAdressSspNeedsSipSkills;

	@Column(name = "learner_name")
	private String learnerName;

	@Column(name = "learner_other_names")
	private String learnerOtherNames;

	@Column(name = "learner_surname")
	private String learnerSurname;

	@Column(name = "learner_permanently_employed")
	private String learnerPermenantlyEmployed;

	@Column(name = "learner_id_number")
	private String learnerIdNumber;

	@Column(name = "learner_alternate_id_passport_number")
	private String learnerAlternateIdPassportNumber;

	@Column(name = "date_of_birth_non_sa_citizens")
	private Date dateOfBirthNonSaCitizens;

	@Column(name = "sa_non_sa_citizens")
	private String saNonSaCitizens;

	@Column(name = "race")
	private String race;

	@Column(name = "gender")
	private String gender;

	@Column(name = "pwd")
	private String pwd;

	@Column(name = "learner_municipality")
	private String learnerMinicipality;

	@Column(name = "seta_idlela_or_public_fet_acronym")
	private String setaIdlelaOrPublicFetAcronym;

	@Column(name = "lead_skills_development_provider_knowladge_components")
	private String leadSkillsDevelopmentProviderKnowladgeComponents;

	@Column(name = "accreditation_number_knowladge_component")
	private String accreditationNumberKnowlageComponent;

	@Column(name = "lead_skills_development_provider_practical_components")
	private String leadSkillsDevelopmentProviderPracticalComponents;

	@Column(name = "accreditation_number_practical_component")
	private String accreditationNumberPracticalComponent;

	@Column(name = "lead_employer")
	private String leadEmployer;

	@Column(name = "lead_employer_approval_number")
	private String leadEmployerApprovalNumber;

	@Column(name = "date_learner_agreement_registration")
	private Date dateLearnerAgreementRegistration;

	@Column(name = "date_learner_agreement_cancelation")
	private Date dateLearnerAgreementCancelation;

	@Column(name = "reason_for_cancelation")
	private String reasonForCancelation;

	@Column(name = "date_learner_agreement_completion")
	private Date dateLearnerAgreementCompletion;

	@Column(name = "trade_test_1_2_and_3")
	private Integer tradeTest12And3;

	@Column(name = "date_trade_test_1_2_and_3")
	private Date dateTradeTest12And3;

	@Column(name = "reason_for_not_yet_competent")
	private String reasonForNotYetCompetent;

	@Column(name = "trade_test_center")
	private String tradeTestCenter;

	@Column(name = "trade_test_center_accreditation_number")
	private String tradeTestCenterAccreditationNumber;

	@Column(name = "trade_test_assessor_name")
	private String tradeTestAssessorName;

	@Column(name = "trade_test_assessor_surname")
	private String tradeTestAssessorSurname;

	@Column(name = "trade_test_assessor_id_number")
	private String tradeTestAssessorIdNumber;

	@Column(name = "trade_test_moderator_name")
	private String tradeTestModeratorName;

	@Column(name = "trade_test_moderator_surname")
	private String tradeTestModeratorSurname;

	@Column(name = "trade_test_moderator_id_number")
	private String tradeTestModeratorIdNumber;

	@Column(name = "date_learner_declared_competent")
	private Date dateLearnerDelacredCompetent;

	@Column(name = "date_learner_certification")
	private Date dateLearnerCertification;

	@Column(name = "learner_certification_number")
	private String learnerCertificationNumber;
	
	// age on bean
	@Column(name = "age_of_learner")
	private Integer ageOfLearner;
	
	@Column(name = "disability")
	private String disability;
	
	@Column(name = "non_rsa_citizen")
	private String nonRsaCitizen;
	
	/* QMR Data End */

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
		QmrArtisanData other = (QmrArtisanData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/*
	 * Index:
	 * CREATE INDEX IDX_DR_qmr_artisan_data ON merseta.qmr_artisan_data (qmr_fin_years_id, qmr_entered_completed);
	 */
	
	/*
	 * Getters and setters
	 */

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
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public QmrFinYears getQmrFinYears() {
		return qmrFinYears;
	}

	public void setQmrFinYears(QmrFinYears qmrFinYears) {
		this.qmrFinYears = qmrFinYears;
	}

	public QmrGenderEnum getQmrGender() {
		return qmrGender;
	}

	public void setQmrGender(QmrGenderEnum qmrGender) {
		this.qmrGender = qmrGender;
	}

	public QmrEquityEnum getQmrEquity() {
		return qmrEquity;
	}

	public void setQmrEquity(QmrEquityEnum qmrEquity) {
		this.qmrEquity = qmrEquity;
	}

	public Integer getAgeIndicator() {
		return ageIndicator;
	}

	public void setAgeIndicator(Integer ageIndicator) {
		this.ageIndicator = ageIndicator;
	}

	public Boolean getRsaCitizen() {
		return rsaCitizen;
	}

	public void setRsaCitizen(Boolean rsaCitizen) {
		this.rsaCitizen = rsaCitizen;
	}

	public Boolean getDisabilityIdentified() {
		return disabilityIdentified;
	}

	public void setDisabilityIdentified(Boolean disabilityIdentified) {
		this.disabilityIdentified = disabilityIdentified;
	}

	public Long getCompanyLearnerFlatLink() {
		return companyLearnerFlatLink;
	}

	public void setCompanyLearnerFlatLink(Long companyLearnerFlatLink) {
		this.companyLearnerFlatLink = companyLearnerFlatLink;
	}

	public String getReportingPeriod() {
		return reportingPeriod;
	}

	public void setReportingPeriod(String reportingPeriod) {
		this.reportingPeriod = reportingPeriod;
	}

	public QmrEnteredCompletedEnum getQmrEnteredCompleted() {
		return qmrEnteredCompleted;
	}

	public void setQmrEnteredCompleted(QmrEnteredCompletedEnum qmrEnteredCompleted) {
		this.qmrEnteredCompleted = qmrEnteredCompleted;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getDoesTheProgrammeAdressSspNeedsSipSkills() {
		return doesTheProgrammeAdressSspNeedsSipSkills;
	}

	public void setDoesTheProgrammeAdressSspNeedsSipSkills(String doesTheProgrammeAdressSspNeedsSipSkills) {
		this.doesTheProgrammeAdressSspNeedsSipSkills = doesTheProgrammeAdressSspNeedsSipSkills;
	}

	public String getLearnerName() {
		return learnerName;
	}

	public void setLearnerName(String learnerName) {
		this.learnerName = learnerName;
	}

	public String getLearnerOtherNames() {
		return learnerOtherNames;
	}

	public void setLearnerOtherNames(String learnerOtherNames) {
		this.learnerOtherNames = learnerOtherNames;
	}

	public String getLearnerSurname() {
		return learnerSurname;
	}

	public void setLearnerSurname(String learnerSurname) {
		this.learnerSurname = learnerSurname;
	}

	public String getLearnerIdNumber() {
		return learnerIdNumber;
	}

	public void setLearnerIdNumber(String learnerIdNumber) {
		this.learnerIdNumber = learnerIdNumber;
	}

	public String getLearnerAlternateIdPassportNumber() {
		return learnerAlternateIdPassportNumber;
	}

	public void setLearnerAlternateIdPassportNumber(String learnerAlternateIdPassportNumber) {
		this.learnerAlternateIdPassportNumber = learnerAlternateIdPassportNumber;
	}

	public Date getDateOfBirthNonSaCitizens() {
		return dateOfBirthNonSaCitizens;
	}

	public void setDateOfBirthNonSaCitizens(Date dateOfBirthNonSaCitizens) {
		this.dateOfBirthNonSaCitizens = dateOfBirthNonSaCitizens;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLearnerMinicipality() {
		return learnerMinicipality;
	}

	public void setLearnerMinicipality(String learnerMinicipality) {
		this.learnerMinicipality = learnerMinicipality;
	}

	public String getSetaIdlelaOrPublicFetAcronym() {
		return setaIdlelaOrPublicFetAcronym;
	}

	public void setSetaIdlelaOrPublicFetAcronym(String setaIdlelaOrPublicFetAcronym) {
		this.setaIdlelaOrPublicFetAcronym = setaIdlelaOrPublicFetAcronym;
	}

	public String getLeadSkillsDevelopmentProviderKnowladgeComponents() {
		return leadSkillsDevelopmentProviderKnowladgeComponents;
	}

	public void setLeadSkillsDevelopmentProviderKnowladgeComponents(
			String leadSkillsDevelopmentProviderKnowladgeComponents) {
		this.leadSkillsDevelopmentProviderKnowladgeComponents = leadSkillsDevelopmentProviderKnowladgeComponents;
	}

	public String getLeadEmployer() {
		return leadEmployer;
	}

	public void setLeadEmployer(String leadEmployer) {
		this.leadEmployer = leadEmployer;
	}

	public String getLeadEmployerApprovalNumber() {
		return leadEmployerApprovalNumber;
	}

	public void setLeadEmployerApprovalNumber(String leadEmployerApprovalNumber) {
		this.leadEmployerApprovalNumber = leadEmployerApprovalNumber;
	}

	public Date getDateLearnerAgreementRegistration() {
		return dateLearnerAgreementRegistration;
	}

	public void setDateLearnerAgreementRegistration(Date dateLearnerAgreementRegistration) {
		this.dateLearnerAgreementRegistration = dateLearnerAgreementRegistration;
	}

	public Date getDateLearnerAgreementCancelation() {
		return dateLearnerAgreementCancelation;
	}

	public void setDateLearnerAgreementCancelation(Date dateLearnerAgreementCancelation) {
		this.dateLearnerAgreementCancelation = dateLearnerAgreementCancelation;
	}

	public String getReasonForCancelation() {
		return reasonForCancelation;
	}

	public void setReasonForCancelation(String reasonForCancelation) {
		this.reasonForCancelation = reasonForCancelation;
	}

	public Date getDateLearnerAgreementCompletion() {
		return dateLearnerAgreementCompletion;
	}

	public void setDateLearnerAgreementCompletion(Date dateLearnerAgreementCompletion) {
		this.dateLearnerAgreementCompletion = dateLearnerAgreementCompletion;
	}

	public Integer getTradeTest12And3() {
		return tradeTest12And3;
	}

	public void setTradeTest12And3(Integer tradeTest12And3) {
		this.tradeTest12And3 = tradeTest12And3;
	}

	public Date getDateTradeTest12And3() {
		return dateTradeTest12And3;
	}

	public void setDateTradeTest12And3(Date dateTradeTest12And3) {
		this.dateTradeTest12And3 = dateTradeTest12And3;
	}

	public String getTradeTestCenter() {
		return tradeTestCenter;
	}

	public void setTradeTestCenter(String tradeTestCenter) {
		this.tradeTestCenter = tradeTestCenter;
	}

	public String getTradeTestCenterAccreditationNumber() {
		return tradeTestCenterAccreditationNumber;
	}

	public void setTradeTestCenterAccreditationNumber(String tradeTestCenterAccreditationNumber) {
		this.tradeTestCenterAccreditationNumber = tradeTestCenterAccreditationNumber;
	}

	public String getTradeTestAssessorName() {
		return tradeTestAssessorName;
	}

	public void setTradeTestAssessorName(String tradeTestAssessorName) {
		this.tradeTestAssessorName = tradeTestAssessorName;
	}

	public String getTradeTestAssessorSurname() {
		return tradeTestAssessorSurname;
	}

	public void setTradeTestAssessorSurname(String tradeTestAssessorSurname) {
		this.tradeTestAssessorSurname = tradeTestAssessorSurname;
	}

	public String getTradeTestAssessorIdNumber() {
		return tradeTestAssessorIdNumber;
	}

	public void setTradeTestAssessorIdNumber(String tradeTestAssessorIdNumber) {
		this.tradeTestAssessorIdNumber = tradeTestAssessorIdNumber;
	}

	public String getTradeTestModeratorName() {
		return tradeTestModeratorName;
	}

	public void setTradeTestModeratorName(String tradeTestModeratorName) {
		this.tradeTestModeratorName = tradeTestModeratorName;
	}

	public String getTradeTestModeratorSurname() {
		return tradeTestModeratorSurname;
	}

	public void setTradeTestModeratorSurname(String tradeTestModeratorSurname) {
		this.tradeTestModeratorSurname = tradeTestModeratorSurname;
	}

	public String getTradeTestModeratorIdNumber() {
		return tradeTestModeratorIdNumber;
	}

	public void setTradeTestModeratorIdNumber(String tradeTestModeratorIdNumber) {
		this.tradeTestModeratorIdNumber = tradeTestModeratorIdNumber;
	}

	public Date getDateLearnerDelacredCompetent() {
		return dateLearnerDelacredCompetent;
	}

	public void setDateLearnerDelacredCompetent(Date dateLearnerDelacredCompetent) {
		this.dateLearnerDelacredCompetent = dateLearnerDelacredCompetent;
	}

	public Date getDateLearnerCertification() {
		return dateLearnerCertification;
	}

	public void setDateLearnerCertification(Date dateLearnerCertification) {
		this.dateLearnerCertification = dateLearnerCertification;
	}

	public String getLearnerCertificationNumber() {
		return learnerCertificationNumber;
	}

	public void setLearnerCertificationNumber(String learnerCertificationNumber) {
		this.learnerCertificationNumber = learnerCertificationNumber;
	}

	public String getArtisanTradeDiscription() {
		return artisanTradeDiscription;
	}

	public void setArtisanTradeDiscription(String artisanTradeDiscription) {
		this.artisanTradeDiscription = artisanTradeDiscription;
	}

	public String getLearnerPermenantlyEmployed() {
		return learnerPermenantlyEmployed;
	}

	public void setLearnerPermenantlyEmployed(String learnerPermenantlyEmployed) {
		this.learnerPermenantlyEmployed = learnerPermenantlyEmployed;
	}

	public String getSaNonSaCitizens() {
		return saNonSaCitizens;
	}

	public void setSaNonSaCitizens(String saNonSaCitizens) {
		this.saNonSaCitizens = saNonSaCitizens;
	}

	public String getAccreditationNumberKnowlageComponent() {
		return accreditationNumberKnowlageComponent;
	}

	public void setAccreditationNumberKnowlageComponent(String accreditationNumberKnowlageComponent) {
		this.accreditationNumberKnowlageComponent = accreditationNumberKnowlageComponent;
	}

	public String getAccreditationNumberPracticalComponent() {
		return accreditationNumberPracticalComponent;
	}

	public void setAccreditationNumberPracticalComponent(String accreditationNumberPracticalComponent) {
		this.accreditationNumberPracticalComponent = accreditationNumberPracticalComponent;
	}

	public String getReasonForNotYetCompetent() {
		return reasonForNotYetCompetent;
	}

	public void setReasonForNotYetCompetent(String reasonForNotYetCompetent) {
		this.reasonForNotYetCompetent = reasonForNotYetCompetent;
	}

	public String getLeadSkillsDevelopmentProviderPracticalComponents() {
		return leadSkillsDevelopmentProviderPracticalComponents;
	}

	public void setLeadSkillsDevelopmentProviderPracticalComponents(
			String leadSkillsDevelopmentProviderPracticalComponents) {
		this.leadSkillsDevelopmentProviderPracticalComponents = leadSkillsDevelopmentProviderPracticalComponents;
	}

	public EmploymentStatusEnum getEmployedUnEmployed() {
		return employedUnEmployed;
	}

	public void setEmployedUnEmployed(EmploymentStatusEnum employedUnEmployed) {
		this.employedUnEmployed = employedUnEmployed;
	}

	public Integer getAgeOfLearner() {
		return ageOfLearner;
	}

	public void setAgeOfLearner(Integer ageOfLearner) {
		this.ageOfLearner = ageOfLearner;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getNonRsaCitizen() {
		return nonRsaCitizen;
	}

	public void setNonRsaCitizen(String nonRsaCitizen) {
		this.nonRsaCitizen = nonRsaCitizen;
	}

}