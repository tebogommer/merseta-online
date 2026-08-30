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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * Excel name: LearnershipAssessment.xlsx Tab Name: Assessment1
 */
@Entity
@Table(name = "legacy_learnership_assessment1")
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyLearnershipAssessment o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyLearnershipAssessment o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.learnerId in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyLearnershipAssessment o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyLearnershipAssessment.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyLearnershipAssessment o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyLearnershipAssessment.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyLearnershipAssessment o where o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// assessor info
@LegacyReportingAnnotation(name = "Total Assessors on Legacy Accreditation", query = "select count(o) from LegacyLearnershipAssessment o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors not on Legacy Accreditation", query = "select count(o) from LegacyLearnershipAssessment o where o.appearsAccreditationData = false", key = "count")
@LegacyReportingAnnotation(key = "Assessors not on Legacy Accreditation", query = "select o from LegacyLearnershipAssessment o where o.appearsAccreditationData = false", returnType = LegacyModeratorQualification.class, singleResult = false)
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Learnerships", query = "select count(o) from LegacyLearnershipAssessment o where o.learnership is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Learnerships", query = "select count(o) from LegacyLearnershipAssessment o where o.learnership is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyLearnershipAssessment o where o.learnership is null", key = "Non Linked Learnerships", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Application' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Externally Loaded' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File and Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Qualification Obtained' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyLearnershipAssessment o where o.agreementStatusDesc = 'Registered' and o.learnerId not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnershipAssessment.class, singleResult = false)
public class LegacyLearnershipAssessment extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	// @CSVAnnotation(name = "Name of row on excel", className = String.class)
	// @Column(name = "keep to standard")
	// private String keep to standard;

	/*
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 pkiLearnerAssessmentId
	 */
	@CSVAnnotation(name = "pkiLearnerAssessmentId", className = String.class)
	@Column(name = "pki_learner_assessment_id")
	private String pkiLearnerAssessmentId;

	/*
	 * PN: 2 LearnerId
	 */
	@CSVAnnotation(name = "LearnerId", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "learner_id")
	private String learnerId;

	@CSVAnnotation(name = "LearnerId", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String idNoTransient;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

	/*
	 * PN: 3 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 4 MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names")
	private String middleNames;

	/*
	 * PN: 5 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 6 LearnershipCode
	 */
	@CSVAnnotation(name = "LearnershipCode", className = String.class, lookupField = "learnership")
	@Column(name = "learnership_code")
	private String learnershipCode;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learnership_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLearnership", paramClass = String.class)
	private Learnership learnership;

	/*
	 * PN: 7 LearnershipTitle
	 */
	@CSVAnnotation(name = "LearnershipTitle", className = String.class)
	@Column(name = "learnership_title", length = 500)
	private String learnershipTitle;

	/*
	 * PN: 8 LshipCode
	 */
	@CSVAnnotation(name = "LshipCode", className = String.class)
	@Column(name = "l_ship_code")
	private String lshipCode;

	/*
	 * PN: 9 AgreementRefNo
	 */
	@CSVAnnotation(name = "AgreementRefNo", className = String.class)
	@Column(name = "agreement_ref_no")
	private String agreementRefNo;

	/*
	 * PN: 10 AgreementStatusDesc
	 */
	@CSVAnnotation(name = "AgreementStatusDesc", className = String.class)
	@Column(name = "agreement_status_desc")
	private String agreementStatusDesc;

	/*
	 * PN: 11 AgreementUnitStdStatusDesc
	 */
	@CSVAnnotation(name = "AgreementUnitStdStatusDesc", className = String.class)
	@Column(name = "agreement_unit_std_status_desc")
	private String agreementUnitStdStatusDesc;

	/*
	 * PN: 12 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class)
	@Column(name = "unit_std_code")
	private String unitStdCode;

	/*
	 * PN: 13 UnitStdDesc
	 */
	@CSVAnnotation(name = "UnitStdDesc", className = String.class)
	@Column(name = "unit_std_desc", length = 500)
	private String unitStdDesc;

	/*
	 * PN: 14 dtAssessment
	 */
	@CSVAnnotation(name = "dtAssessment", className = String.class)
	@Column(name = "dt_assessment")
	private String dtAssessment;

	/*
	 * PN: 15 AssessorID
	 */
	@CSVAnnotation(name = "AssessorID", className = String.class, lookupField = "appearsAccreditationData")
	@Column(name = "assessor_id")
	private String assessorId;

	@Column(name = "appears_accreditation_data")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyAssessorAccreditation", paramClass = String.class)
	private Boolean appearsAccreditationData;

	/*
	 * PN: 16 AssessorFirstName
	 */
	@CSVAnnotation(name = "AssessorFirstName", className = String.class)
	@Column(name = "assessor_first_name")
	private String assessorFirstName;

	/*
	 * PN: 17 AssessorSurname
	 */
	@CSVAnnotation(name = "AssessorSurname", className = String.class)
	@Column(name = "assessor_surname")
	private String assessorSurname;

	/*
	 * PN: 18 AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no")
	private String assessorRegNo;

	/*
	 * PN: 19 HologramNumber
	 */
	@CSVAnnotation(name = "HologramNumber", className = String.class)
	@Column(name = "hologram_number")
	private String hologramNumber;

	public LegacyLearnershipAssessment() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LegacyLearnershipAssessment other = (LegacyLearnershipAssessment) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/* Getters and setters */

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

	public String getPkiLearnerAssessmentId() {
		return pkiLearnerAssessmentId;
	}

	public void setPkiLearnerAssessmentId(String pkiLearnerAssessmentId) {
		this.pkiLearnerAssessmentId = pkiLearnerAssessmentId;
	}

	public String getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getLearnershipTitle() {
		return learnershipTitle;
	}

	public void setLearnershipTitle(String learnershipTitle) {
		this.learnershipTitle = learnershipTitle;
	}

	public String getLshipCode() {
		return lshipCode;
	}

	public void setLshipCode(String lshipCode) {
		this.lshipCode = lshipCode;
	}

	public String getAgreementRefNo() {
		return agreementRefNo;
	}

	public void setAgreementRefNo(String agreementRefNo) {
		this.agreementRefNo = agreementRefNo;
	}

	public String getAgreementStatusDesc() {
		return agreementStatusDesc;
	}

	public void setAgreementStatusDesc(String agreementStatusDesc) {
		this.agreementStatusDesc = agreementStatusDesc;
	}

	public String getAgreementUnitStdStatusDesc() {
		return agreementUnitStdStatusDesc;
	}

	public void setAgreementUnitStdStatusDesc(String agreementUnitStdStatusDesc) {
		this.agreementUnitStdStatusDesc = agreementUnitStdStatusDesc;
	}

	public String getUnitStdCode() {
		return unitStdCode;
	}

	public void setUnitStdCode(String unitStdCode) {
		this.unitStdCode = unitStdCode;
	}

	public String getUnitStdDesc() {
		return unitStdDesc;
	}

	public void setUnitStdDesc(String unitStdDesc) {
		this.unitStdDesc = unitStdDesc;
	}

	public String getDtAssessment() {
		return dtAssessment;
	}

	public void setDtAssessment(String dtAssessment) {
		this.dtAssessment = dtAssessment;
	}

	public String getAssessorFirstName() {
		return assessorFirstName;
	}

	public void setAssessorFirstName(String assessorFirstName) {
		this.assessorFirstName = assessorFirstName;
	}

	public String getAssessorSurname() {
		return assessorSurname;
	}

	public void setAssessorSurname(String assessorSurname) {
		this.assessorSurname = assessorSurname;
	}

	public String getAssessorRegNo() {
		return assessorRegNo;
	}

	public void setAssessorRegNo(String assessorRegNo) {
		this.assessorRegNo = assessorRegNo;
	}

	public String getHologramNumber() {
		return hologramNumber;
	}

	public void setHologramNumber(String hologramNumber) {
		this.hologramNumber = hologramNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public Boolean getAppearsAccreditationData() {
		return appearsAccreditationData;
	}

	public void setAppearsAccreditationData(Boolean appearsAccreditationData) {
		this.appearsAccreditationData = appearsAccreditationData;
	}

}
