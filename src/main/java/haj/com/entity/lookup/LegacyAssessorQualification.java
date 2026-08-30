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
import haj.com.annotations.LegacyReportingParamsAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;

/**
 * TO BE DELETED
 * 
 * Excel name: Assessor.xlsx Tab Name: Qualification
 */
@Entity
@Table(name = "legacy_assessor_qualification")
//qualification info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacyAssessorQualification o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select count(o) from LegacyAssessorQualification o where o.qualification is null", key = "count")
//accreditation info
@LegacyReportingAnnotation(name = "Total ID Number on Legacy Accreditation", query = "select count(o) from LegacyAssessorQualification o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Number not on Legacy Accreditation", query = "select count(o) from LegacyAssessorQualification o where o.appearsAccreditationData = false", key = "count")
//id info
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyAssessorQualification o where o.appearsOnHomeAffairsData = true and o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyAssessorQualification o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "count")
//tables
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select o from LegacyAssessorQualification o where o.qualification is null", key = "Non Linked Qualifications", returnType = LegacyAssessorQualification.class, singleResult = false)
@LegacyReportingAnnotation(name = "ID Number not on Legacy Accreditation", query = "select o from LegacyAssessorQualification o where o.appearsAccreditationData = false", key = "ID Number not on Legacy Accreditation", returnType = LegacyAssessorQualification.class, singleResult = false)
@LegacyReportingAnnotation(name = "ID Numbers not on Home Affairs and Legacy Accreditation", query = "select o from LegacyAssessorQualification o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "ID Numbers not on Home Affairs and Legacy Accreditation", returnType = LegacyAssessorQualification.class, singleResult = false)
public class LegacyAssessorQualification extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1 AssessorId
	 */
	@CSVAnnotation(name = "AssessorID", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "assessor_id")
	private String assessorId;

	@CSVAnnotation(name = "AssessorID", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String assessorIdTransient;

	@CSVAnnotation(name = "AssessorID", className = String.class, lookupField = "appearsAccreditationData")
	@Transient
	private String assessorIdTransient2;

	/*
	 * PN: 2 AlternateIDNo
	 */
	@CSVAnnotation(name = "AlternateIDNo", className = String.class)
	@Column(name = "alternate_id_no")
	private String alternateIDNo;

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
	 * PN: 6 QualCode
	 */
	@CSVAnnotation(name = "QualCode", className = String.class, lookupField = "qualification")
	@Column(name = "qual_code")
	private String qualCode;

	/*
	 * PN: 7 AssessorRegStartDate
	 */
	@CSVAnnotation(name = "AssessorRegStartDate", className = String.class)
	@Column(name = "assessor_reg_start_date")
	private String assessorRegStartDate;

	/*
	 * PN: 8 AssessorFirstRegDate
	 */
	@CSVAnnotation(name = "AssessorFirstRegDate", className = String.class)
	@Column(name = "assessor_first_reg_date")
	private String assessorFirstRegDate;

	/*
	 * PN: 9 AssessorRegEndDate
	 */
	@CSVAnnotation(name = "AssessorRegEndDate", className = String.class)
	@Column(name = "assessor_reg_end_date")
	private String assessorRegEndDate;

	/*
	 * PN: 10 AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no")
	private String assessorRegNo;

	/*
	 * PN: 11 AssessorStatusDesc Version 1 name AssessorStatusDesc Version 2 name
	 * Assessor_Status_2
	 */
	@CSVAnnotation(name = "Assessor_Status_2", className = String.class)
	@Column(name = "assessor_status_two")
	private String assessorStatusTwo;

	/*
	 * PN: 12 Comments
	 */
	@CSVAnnotation(name = "Comments", className = String.class)
	@Column(name = "comments")
	private String comments;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

	@Column(name = "appears_accreditation_data")
	@CSVLookupAnnotation(className = LegacyAssessorAccreditationService.class, method = "checkExistingIdNo", paramClass = String.class)
	private Boolean appearsAccreditationData;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	@Column(name = "processed", columnDefinition = "BIT default false")
	private Boolean processed;

	@Column(name = "application_submited", columnDefinition = "BIT default false")
	private Boolean applicationSubmited;

	@Transient
	private Boolean qualificationExpired;

	public LegacyAssessorQualification() {
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
		LegacyAssessorQualification other = (LegacyAssessorQualification) obj;
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

	public String getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}

	public String getAlternateIDNo() {
		return alternateIDNo;
	}

	public void setAlternateIDNo(String alternateIDNo) {
		this.alternateIDNo = alternateIDNo;
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

	public String getQualCode() {
		return qualCode;
	}

	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}

	public String getAssessorRegStartDate() {
		return assessorRegStartDate;
	}

	public void setAssessorRegStartDate(String assessorRegStartDate) {
		this.assessorRegStartDate = assessorRegStartDate;
	}

	public String getAssessorFirstRegDate() {
		return assessorFirstRegDate;
	}

	public void setAssessorFirstRegDate(String assessorFirstRegDate) {
		this.assessorFirstRegDate = assessorFirstRegDate;
	}

	public String getAssessorRegEndDate() {
		return assessorRegEndDate;
	}

	public void setAssessorRegEndDate(String assessorRegEndDate) {
		this.assessorRegEndDate = assessorRegEndDate;
	}

	public String getAssessorRegNo() {
		return assessorRegNo;
	}

	public void setAssessorRegNo(String assessorRegNo) {
		this.assessorRegNo = assessorRegNo;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public String getAssessorStatusTwo() {
		return assessorStatusTwo;
	}

	public void setAssessorStatusTwo(String assessorStatusTwo) {
		this.assessorStatusTwo = assessorStatusTwo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getApplicationSubmited() {
		return applicationSubmited;
	}

	public void setApplicationSubmited(Boolean applicationSubmited) {
		this.applicationSubmited = applicationSubmited;
	}

	public Boolean getQualificationExpired() {
		return qualificationExpired;
	}

	public void setQualificationExpired(Boolean qualificationExpired) {
		this.qualificationExpired = qualificationExpired;
	}

	public String getAssessorIdTransient() {
		return assessorIdTransient;
	}

	public void setAssessorIdTransient(String assessorIdTransient) {
		this.assessorIdTransient = assessorIdTransient;
	}

	public String getAssessorIdTransient2() {
		return assessorIdTransient2;
	}

	public void setAssessorIdTransient2(String assessorIdTransient2) {
		this.assessorIdTransient2 = assessorIdTransient2;
	}

	public Boolean getAppearsAccreditationData() {
		return appearsAccreditationData;
	}

	public void setAppearsAccreditationData(Boolean appearsAccreditationData) {
		this.appearsAccreditationData = appearsAccreditationData;
	}

}
