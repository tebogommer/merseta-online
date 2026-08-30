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
 * Excel name: Moderator.xlsx Tab Name: UnitStandard
 */
@Entity
@Table(name = "legacy_moderator_unit_standard")
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Unit Standard", query = "select count(o) from LegacyModeratorUnitStandard o where o.unitStandard is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Unit Standard", query = "select count(o) from LegacyModeratorUnitStandard o where o.unitStandard is null", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total ID Number on Legacy Accreditation", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Number not on Legacy Accreditation", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsAccreditationData = false", key = "count")
// id info
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = true and o.appearsAccreditationData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs and Legacy Accreditation", query = "select count(o) from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyModeratorUnitStandard o where o.unitStandard is null", key = "Total Non Linked Unit Standard", returnType = LegacyModeratorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyModeratorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyModeratorUnitStandard o where o.appearsAccreditationData = false", key = "ID Number not on Legacy Accreditation", returnType = LegacyModeratorUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyModeratorUnitStandard o where o.appearsOnHomeAffairsData = false and o.appearsAccreditationData = false", key = "ID Numbers not on Home Affairs and Legacy Accreditation", returnType = LegacyModeratorUnitStandard.class, singleResult = false)
public class LegacyModeratorUnitStandard extends AbstractLookup {

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
	// * AlternateIdNo
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

	// /*
	// * PN: 5
	// * QualCode
	// */
	// @CSVAnnotation(name = "QualCode", className = String.class)
	// @Column(name = "qual_code")
	// private String qualCode;

	/*
	 * PN: 5 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class, lookupField = "unitStandard")
	@Column(name = "unit_std_code")
	private String unitStdCode;

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
	@JoinColumn(name = "unit_standard_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findUnitStandards", paramClass = String.class)
	private UnitStandards unitStandard;
	
	@Column(name = "processed")
	private Boolean processed;

	@Column(name = "application_submited", columnDefinition = "BIT default false")
	private Boolean applicationSubmited;

	@Transient
	private Boolean unitStandardExpired;

	public LegacyModeratorUnitStandard() {
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
		LegacyModeratorUnitStandard other = (LegacyModeratorUnitStandard) obj;
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

	public String getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
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

	public String getUnitStdCode() {
		return unitStdCode;
	}

	public void setUnitStdCode(String unitStdCode) {
		this.unitStdCode = unitStdCode;
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

	public String getModeratorRegNo() {
		return moderatorRegNo;
	}

	public void setModeratorRegNo(String moderatorRegNo) {
		this.moderatorRegNo = moderatorRegNo;
	}

	public String getModeratorStatusDesc() {
		return moderatorStatusDesc;
	}

	public void setModeratorStatusDesc(String moderatorStatusDesc) {
		this.moderatorStatusDesc = moderatorStatusDesc;
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
