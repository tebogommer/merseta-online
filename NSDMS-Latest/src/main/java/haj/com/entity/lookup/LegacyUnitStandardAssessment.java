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
 * Excel name: Unit Standard Assessment.xlsx Tab Name: Assessment
 */
@Entity
@Table(name = "legacy_unit_standard_assessment")
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyUnitStandardAssessment o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyUnitStandardAssessment o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandardAssessment o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandardAssessment o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandardAssessment o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// assessor info
@LegacyReportingAnnotation(name = "Total Assessors on Legacy Accreditation", query = "select count(o) from LegacyUnitStandardAssessment o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors not on Legacy Accreditation", query = "select count(o) from LegacyUnitStandardAssessment o where o.appearsAccreditationData = false", key = "count")
@LegacyReportingAnnotation(key = "Assessors not on Legacy Accreditation", query = "select o from LegacyUnitStandardAssessment o where o.appearsAccreditationData = false", returnType = LegacyModeratorQualification.class, singleResult = false)
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Unit Standards", query = "select count(o) from LegacyUnitStandardAssessment o where o.unitStandard is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Unit Standards", query = "select count(o) from LegacyUnitStandardAssessment o where o.unitStandard is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandardAssessment o where o.unitStandard is null", key = "Non Linked Unit Standards", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Person File", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandardAssessment o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandardAssessment.class, singleResult = false)
public class LegacyUnitStandardAssessment extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	// @CSVAnnotation(name = "Name of row on excel", className = String.class)
	// @Column(name = "keep to standard")
	// private String keep to standard;

	/*
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so characters @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 pkiLearnerAssessmentId
	 */
	@CSVAnnotation(name = "pkiLearnerAssessmentId", className = String.class)
	@Column(name = "pki_learner_assessment_id")
	private String pkiLearnerAssessmentId;

	/*
	 * PN: 2 IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "id_no")
	private String idNo;

	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String idNoTransient;

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
	 * PN: 6 UnitStdDesc
	 */
	@CSVAnnotation(name = "UnitStdDesc", className = String.class)
	@Column(name = "unit_std_desc")
	private String unitStdDesc;

	/*
	 * PN: 7 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class, lookupField = "unitStandard")
	@Column(name = "unit_std_code")
	private String unitStdCode;

	/*
	 * PN: 8 sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no")
	private String sRefNo;

	/*
	 * PN: 9 LearnerUSStatusDesc
	 */
	@CSVAnnotation(name = "LearnerUSStatusDesc", className = String.class)
	@Column(name = "learner_us_statusDesc")
	private String learnerUSStatusDesc;

	/*
	 * PN: 10 AgreementUnitStdStatusDesc
	 */
	@CSVAnnotation(name = "AgreementUnitStdStatusDesc", className = String.class)
	@Column(name = "agreement_unit_std_status_desc")
	private String agreementUnitStdStatusDesc;

	/*
	 * PN: 11 dtAssessment
	 */
	@CSVAnnotation(name = "dtAssessment", className = String.class)
	@Column(name = "dt_assessment")
	private String dtAssessment;

	/*
	 * PN: 12 dtAssessment
	 */
	@CSVAnnotation(name = "AssessorID", className = String.class, lookupField = "appearsAccreditationData")
	@Column(name = "assessor_id")
	private String assessorID;

	/*
	 * PN: 13 AssessorFirstName
	 */
	@CSVAnnotation(name = "AssessorFirstName", className = String.class)
	@Column(name = "assessor_first_name")
	private String assessorFirstName;

	/*
	 * PN: 14 AssessorSurname
	 */
	@CSVAnnotation(name = "AssessorSurname", className = String.class)
	@Column(name = "assessor_surname")
	private String assessorSurname;

	/*
	 * PN: 15 AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no")
	private String assessorRegNo;

	/*
	 * PN: 16 HologramNumber
	 */
	@CSVAnnotation(name = "HologramNumber", className = String.class)
	@Column(name = "hologram_number")
	private String hologramNumber;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findUnitStandards", paramClass = String.class)
	private UnitStandards unitStandard;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

	@Column(name = "appears_accreditation_data")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyAssessorAccreditation", paramClass = String.class)
	private Boolean appearsAccreditationData;
	
	@Column(name = "legacy_unit_standard_flat_id", nullable = true)
	private Long legacyUnitStandardFlatId;

	public LegacyUnitStandardAssessment() {
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
		LegacyUnitStandardAssessment other = (LegacyUnitStandardAssessment) obj;
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getUnitStdDesc() {
		return unitStdDesc;
	}

	public void setUnitStdDesc(String unitStdDesc) {
		this.unitStdDesc = unitStdDesc;
	}

	public String getUnitStdCode() {
		return unitStdCode;
	}

	public void setUnitStdCode(String unitStdCode) {
		this.unitStdCode = unitStdCode;
	}

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getLearnerUSStatusDesc() {
		return learnerUSStatusDesc;
	}

	public void setLearnerUSStatusDesc(String learnerUSStatusDesc) {
		this.learnerUSStatusDesc = learnerUSStatusDesc;
	}

	public String getAgreementUnitStdStatusDesc() {
		return agreementUnitStdStatusDesc;
	}

	public void setAgreementUnitStdStatusDesc(String agreementUnitStdStatusDesc) {
		this.agreementUnitStdStatusDesc = agreementUnitStdStatusDesc;
	}

	public String getDtAssessment() {
		return dtAssessment;
	}

	public void setDtAssessment(String dtAssessment) {
		this.dtAssessment = dtAssessment;
	}

	public String getAssessorID() {
		return assessorID;
	}

	public void setAssessorID(String assessorID) {
		this.assessorID = assessorID;
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

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
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

	public Boolean getAppearsAccreditationData() {
		return appearsAccreditationData;
	}

	public void setAppearsAccreditationData(Boolean appearsAccreditationData) {
		this.appearsAccreditationData = appearsAccreditationData;
	}

	public Long getLegacyUnitStandardFlatId() {
		return legacyUnitStandardFlatId;
	}

	public void setLegacyUnitStandardFlatId(Long legacyUnitStandardFlatId) {
		this.legacyUnitStandardFlatId = legacyUnitStandardFlatId;
	}

}
