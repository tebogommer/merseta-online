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
import haj.com.service.lookup.LegacyAssessorAccreditationService;

/**
 * TO BE DELETED
 * 
 * Excel name: Assessor.xlsx Tab Name: Skills Unit Standard
 */
@Entity
@Table(name = "legacy_assessor_unit_standard")
//qualification info
@LegacyReportingAnnotation(name = "Total Linked Unit Standard", query = "select count(o) from LegacyAssessorUnitStandard o where o.unitStandard is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Unit Standard", query = "select count(o) from LegacyAssessorUnitStandard o where o.unitStandard is null", key = "count")
//accreditation info
@LegacyReportingAnnotation(name = "Total ID Number on Legacy Accreditation", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Number not on Legacy Accreditation", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsAccreditationData = false", key = "count")
//id info
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = true and o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "count")
//tables
@LegacyReportingAnnotation(query = "select o from LegacyAssessorUnitStandard o where o.unitStandard is null", key = "Total Non Linked Unit Standard", returnType = LegacyAssessorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyAssessorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyAssessorUnitStandard o where o.appearsAccreditationData = false", key = "ID Number not on Legacy Accreditation", returnType = LegacyAssessorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyAssessorUnitStandard o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "ID Numbers not on Home Affairs and Legacy Accreditation", returnType = LegacyAssessorUnitStandard.class, singleResult = false)
public class LegacyAssessorUnitStandard extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

//	@CSVAnnotation(name = "Name of row on excel", className = String.class)
//	@Column(name = "keep to standard")
//	private String keep to standard;

	/*
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 AssessorId
	 */
	@CSVAnnotation(name = "Assessorid", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "assessor_id")
	private String assessorid;

	@CSVAnnotation(name = "Assessorid", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String assessorIdTransient;

	@CSVAnnotation(name = "Assessorid", className = String.class, lookupField = "appearsAccreditationData")
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
	@CSVAnnotation(name = "QualCode", className = String.class)
	@Column(name = "qual_code")
	private String qualCode;

	/*
	 * PN: 7 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class, lookupField = "unitStandard")
	@Column(name = "unit_std_code")
	private String unitStdCode;

	/*
	 * PN: 8 AssessorRegStartDate
	 */
	@CSVAnnotation(name = "AssessorRegStartDate", className = String.class)
	@Column(name = "assessor_reg_start_date")
	private String assessorRegStartDate;

	/*
	 * PN: 9 AssessorFirstRegDate
	 */
	@CSVAnnotation(name = "AssessorFirstRegDate", className = String.class)
	@Column(name = "assessor_first_reg_date")
	private String assessorFirstRegDate;

	/*
	 * PN: 10 AssessorRegEndDate
	 */
	@CSVAnnotation(name = "AssessorRegEndDate", className = String.class)
	@Column(name = "assessor_reg_end_date")
	private String assessorRegEndDate;

	/*
	 * PN: 11 AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no")
	private String assessorRegNo;

	/*
	 * PN: 12 AssessorStatusDesc
	 */
	@CSVAnnotation(name = "AssessorStatusDesc", className = String.class)
	@Column(name = "assessor_status_desc")
	private String assessorStatusDesc;
	
	/*
	 * PN: 13 Comments
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
	@JoinColumn(name = "unit_standard_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findUnitStandards", paramClass = String.class)
	private UnitStandards unitStandard;
	
	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "application_submited", columnDefinition = "BIT default false")
	private Boolean applicationSubmited;
	
	@Transient
	private Boolean unitStandardExpired;

	public LegacyAssessorUnitStandard() {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LegacyAssessorUnitStandard other = (LegacyAssessorUnitStandard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getAssessorid() {
		return assessorid;
	}

	public void setAssessorid(String assessorid) {
		this.assessorid = assessorid;
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

	public String getUnitStdCode() {
		return unitStdCode;
	}

	public void setUnitStdCode(String unitStdCode) {
		this.unitStdCode = unitStdCode;
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

	public String getAssessorStatusDesc() {
		return assessorStatusDesc;
	}

	public void setAssessorStatusDesc(String assessorStatusDesc) {
		this.assessorStatusDesc = assessorStatusDesc;
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

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public Boolean getApplicationSubmited() {
		return applicationSubmited;
	}

	public void setApplicationSubmited(Boolean applicationSubmited) {
		this.applicationSubmited = applicationSubmited;
	}

	public Boolean getUnitStandardExpired() {
		return unitStandardExpired;
	}

	public void setUnitStandardExpired(Boolean unitStandardExpired) {
		this.unitStandardExpired = unitStandardExpired;
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
