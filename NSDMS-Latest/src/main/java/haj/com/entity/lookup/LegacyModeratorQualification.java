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
 * Excel name: Moderator.xlsx Tab Name: Qualification
 */
@Entity
@Table(name = "legacy_moderator_qualification")
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacyModeratorQualification o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select count(o) from LegacyModeratorQualification o where o.qualification is null", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total ID Number on Legacy Accreditation", query = "select count(o) from LegacyModeratorQualification o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Number not on Legacy Accreditation", query = "select count(o) from LegacyModeratorQualification o where o.appearsAccreditationData = false", key = "count")
// id info
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyModeratorQualification o where o.appearsOnHomeAffairsData = true and o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyModeratorQualification o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "count")
// tables
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select o from LegacyModeratorQualification o where o.qualification is null", key = "Non Linked Qualifications", returnType = LegacyModeratorQualification.class, singleResult = false)
@LegacyReportingAnnotation(name = "ID Number not on Legacy Accreditation", query = "select o from LegacyModeratorQualification o where o.appearsAccreditationData = false", key = "ID Number not on Legacy Accreditation", returnType = LegacyModeratorQualification.class, singleResult = false)
@LegacyReportingAnnotation(name = "ID Numbers not on Home Affairs and Legacy Accreditation", query = "select o from LegacyModeratorQualification o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "ID Numbers not on Home Affairs and Legacy Accreditation", returnType = LegacyModeratorQualification.class, singleResult = false)
public class LegacyModeratorQualification extends AbstractLookup {

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
	 * 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 ModeratorId
	 */
	@CSVAnnotation(name = "ModeratorId", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "moderator_id")
	private String moderatorId;

	@CSVAnnotation(name = "ModeratorId", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String moderatorIdTransient;

	@CSVAnnotation(name = "ModeratorId", className = String.class, lookupField = "appearsAccreditationData")
	@Transient
	private String moderatorIdTransient2;

	// /*
	// * PN: 2
	// * AlternateIDNo
	// */
	// @CSVAnnotation(name = "AlternateIDNo", className = String.class)
	// @Column(name = "alternate_id_no")
	// private String alternateIdNo;

	/*
	 * PN: 2 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 3 MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names")
	private String middleNames;

	/*
	 * PN: 4 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 5 QualCode
	 */
	@CSVAnnotation(name = "QualCode", className = String.class, lookupField = "qualification")
	@Column(name = "qual_code")
	private String qualCode;

	// /*
	// * PN: 7
	// * SAQA Unit Standard ID
	// */
	// @CSVAnnotation(name = "SAQA_Unit_Standard_ID", className = String.class)
	// @Column(name = "saqa_unit_standard_id")
	// private String saqaUnitStandardId;

	/*
	 * PN: 6 ModeratorRegStartDate
	 */
	@CSVAnnotation(name = "ModeratorRegStartDate", className = String.class)
	@Column(name = "moderator_reg_start_date")
	private String moderatorRegStartDate;

	/*
	 * PN: 7 ModeratorFirstRegDate
	 */
	@CSVAnnotation(name = "ModeratorFirstRegDate", className = String.class)
	@Column(name = "moderator_first_reg_date")
	private String moderatorFirstRegDate;

	/*
	 * PN: 8 ModeratorRegEndDate
	 */
	@CSVAnnotation(name = "ModeratorRegEndDate", className = String.class)
	@Column(name = "moderator_reg_end_date")
	private String moderatorRegEndDate;

	/*
	 * PN: 9 ModeratorRegNo
	 */
	@CSVAnnotation(name = "ModeratorRegNo", className = String.class)
	@Column(name = "moderator_reg_no")
	private String moderatorRegNo;

	/*
	 * PN: 10 ModeratorStatusDesc
	 */
	@CSVAnnotation(name = "ModeratorStatusDesc", className = String.class)
	@Column(name = "moderator_status_desc")
	private String moderatorStatusDesc;

	/*
	 * PN: 11 Comments
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
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyModeratorAccreditation", paramClass = String.class)
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

	public LegacyModeratorQualification() {
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
		LegacyModeratorQualification other = (LegacyModeratorQualification) obj;
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

	public String getQualCode() {
		return qualCode;
	}

	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}

	public String getModeratorRegStartDate() {
		return moderatorRegStartDate;
	}

	public void setModeratorRegStartDate(String moderatorRegStartDate) {
		this.moderatorRegStartDate = moderatorRegStartDate;
	}

	public String getModeratorFirstRegDate() {
		return moderatorFirstRegDate;
	}

	public void setModeratorFirstRegDate(String moderatorFirstRegDate) {
		this.moderatorFirstRegDate = moderatorFirstRegDate;
	}

	public String getModeratorRegEndDate() {
		return moderatorRegEndDate;
	}

	public void setModeratorRegEndDate(String moderatorRegEndDate) {
		this.moderatorRegEndDate = moderatorRegEndDate;
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

	public String getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getModeratorStatusDesc() {
		return moderatorStatusDesc;
	}

	public void setModeratorStatusDesc(String moderatorStatusDesc) {
		this.moderatorStatusDesc = moderatorStatusDesc;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public String getModeratorRegNo() {
		return moderatorRegNo;
	}

	public void setModeratorRegNo(String moderatorRegNo) {
		this.moderatorRegNo = moderatorRegNo;
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

	public String getModeratorIdTransient() {
		return moderatorIdTransient;
	}

	public void setModeratorIdTransient(String moderatorIdTransient) {
		this.moderatorIdTransient = moderatorIdTransient;
	}

	public String getModeratorIdTransient2() {
		return moderatorIdTransient2;
	}

	public void setModeratorIdTransient2(String moderatorIdTransient2) {
		this.moderatorIdTransient2 = moderatorIdTransient2;
	}

	public Boolean getAppearsAccreditationData() {
		return appearsAccreditationData;
	}

	public void setAppearsAccreditationData(Boolean appearsAccreditationData) {
		this.appearsAccreditationData = appearsAccreditationData;
	}

}