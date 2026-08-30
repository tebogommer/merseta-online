package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.entity.Users;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * Excel name: Assessor.xlsx Tab Name: Accreditation
 */
@Entity
@Table(name = "legacy_assessor_accreditation")
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyAssessorAccreditation o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyAssessorAccreditation o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyAssessorAccreditation o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyAssessorAccreditation o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyAssessorAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyAssessorAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyAssessorAccreditation.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total Assessors with Accreditation Num", query = "select count(o) from LegacyAssessorAccreditation o where o.assessorRegNo is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors without Accreditation Num", query = "select count(o) from LegacyAssessorAccreditation o where o.assessorRegNo is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where o.assessorRegNo is null", key = "Assessors without Accreditation Num", returnType = LegacyAssessorAccreditation.class, singleResult = false)
// date info
@LegacyReportingAnnotation(name = "Total Assessors with Valid End Date", query = "select count(o) from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) > date(o.assessorRegStartDate)", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors with Invalid End Date", query = "select count(o) from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) < date(o.assessorRegStartDate) or date(o.assessorRegEndDate) = date(o.assessorRegStartDate)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) < date(o.assessorRegStartDate)", key = "Assessors with Invalid End Date", returnType = LegacyAssessorAccreditation.class, singleResult = false)
// expired info
@LegacyReportingAnnotation(name = "Total Assessors with Valid Status", query = "select count(o) from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) < date(NOW()) and o.assessorStatusDesc = 'Expired'", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors with Invalid Status", query = "select count(o) from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) < date(NOW()) and o.assessorStatusDesc <> 'Expired'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where date(o.assessorRegEndDate) < date(NOW()) and o.assessorStatusDesc <> 'Expired'", key = "Assessors with Invalid Status", returnType = LegacyAssessorAccreditation.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total Assessors with Start and End Date", query = "select count(o) from LegacyAssessorAccreditation o where o.assessorRegNo is not null and (o.assessorRegEndDate is not null and o.assessorRegStartDate is not null)", key = "count")
@LegacyReportingAnnotation(name = "Total Assessors without Start and End Date", query = "select count(o) from LegacyAssessorAccreditation o where o.assessorRegNo is not null and (o.assessorRegEndDate is null or o.assessorRegStartDate is null)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyAssessorAccreditation o where o.assessorRegNo is not null and (o.assessorRegEndDate is null or o.assessorRegStartDate is null)", key = "Assessors with Start and End Date", returnType = LegacyAssessorAccreditation.class, singleResult = false) //
public class LegacyAssessorAccreditation extends AbstractLookup {

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
	 * PN: 1 TitleDesc
	 */
	@CSVAnnotation(name = "TitleDesc", className = String.class)
	@Column(name = "title_desc")
	private String titleDesc;

	/*
	 * PN: 2 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 3 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 4 IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "id_no")
	private String idNo;

	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String idNoTransient;

	/*
	 * PN: 5 AssessorType
	 */
	@CSVAnnotation(name = "AssessorType", className = String.class)
	@Column(name = "assessor_type")
	private String assessorType;

	/*
	 * PN: 6 AssessorRegStartDate
	 */
	@CSVAnnotation(name = "AssessorRegStartDate", className = String.class)
	@Column(name = "assessor_reg_start_date")
	private String assessorRegStartDate;

	/*
	 * PN: 7 AssessorRegEndDate
	 */
	@CSVAnnotation(name = "AssessorRegEndDate", className = String.class)
	@Column(name = "assessor_reg_end_date")
	private String assessorRegEndDate;

	/*
	 * PN: 8 AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no")
	private String assessorRegNo;

	/*
	 * PN: 9 AssessorStatusDesc
	 */
	@CSVAnnotation(name = "AssessorStatusDesc", className = String.class)
	@Column(name = "assessor_status_desc")
	private String assessorStatusDesc;
	
	@Column(name = "assessor_status_before_alteration")
	private String assessorStatusBeforeAlteration;

	/*
	 * PN: 10 AssessorStatusEffectiveDate
	 */
	@CSVAnnotation(name = "AssessorStatusEffectiveDate", className = String.class)
	@Column(name = "assessor_status_effective_date")
	private String assessorStatusEffectiveDate;

	/*
	 * PN: 11 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	/*
	 * PN: 12 sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no")
	private String sRefNo;

	/*
	 * PN: 13 DecisionNumber
	 */
	@CSVAnnotation(name = "DecisionNumber", className = String.class)
	@Column(name = "decision_number")
	private String decisionNumber;

	/*
	 * PN: 14 ReviewCommitteeDate
	 */
	@CSVAnnotation(name = "ReviewCommitteeDate", className = String.class)
	@Column(name = "review_committee_date")
	private String reviewCommitteeDate;

	/*
	 * PN: 15 ETQASetaDesc
	 */
	@CSVAnnotation(name = "ETQASetaDesc", className = String.class)
	@Column(name = "etqa_seta_desc")
	private String etqaSetaDesc;

	/*
	 * PN: 16 Comments
	 */
	@CSVAnnotation(name = "Comments", className = String.class)
	@Column(name = "comments")
	private String comments;

	@Transient
	private Users user;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;
	
	@Column(name = "alive_on_home_affairs_data")
	private Boolean aliveOnHomeAffairsData;
	
	@Column(name = "on_persons_file")
	private Boolean onPersonsFile;
	
	@Column(name = "on_persons_file_alternative_id")
	private Boolean onPersonsFileAlternativeId;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "valid_status")
	private Boolean validStatus;

	public LegacyAssessorAccreditation() {
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
		LegacyAssessorAccreditation other = (LegacyAssessorAccreditation) obj;
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

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAssessorType() {
		return assessorType;
	}

	public void setAssessorType(String assessorType) {
		this.assessorType = assessorType;
	}

	public String getAssessorRegStartDate() {
		return assessorRegStartDate;
	}

	public void setAssessorRegStartDate(String assessorRegStartDate) {
		this.assessorRegStartDate = assessorRegStartDate;
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

	public String getAssessorStatusEffectiveDate() {
		return assessorStatusEffectiveDate;
	}

	public void setAssessorStatusEffectiveDate(String assessorStatusEffectiveDate) {
		this.assessorStatusEffectiveDate = assessorStatusEffectiveDate;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public String getReviewCommitteeDate() {
		return reviewCommitteeDate;
	}

	public void setReviewCommitteeDate(String reviewCommitteeDate) {
		this.reviewCommitteeDate = reviewCommitteeDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getEtqaSetaDesc() {
		return etqaSetaDesc;
	}

	public void setEtqaSetaDesc(String etqaSetaDesc) {
		this.etqaSetaDesc = etqaSetaDesc;
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

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
	}

	public Boolean getAliveOnHomeAffairsData() {
		return aliveOnHomeAffairsData;
	}

	public void setAliveOnHomeAffairsData(Boolean aliveOnHomeAffairsData) {
		this.aliveOnHomeAffairsData = aliveOnHomeAffairsData;
	}

	public Boolean getOnPersonsFile() {
		return onPersonsFile;
	}

	public void setOnPersonsFile(Boolean onPersonsFile) {
		this.onPersonsFile = onPersonsFile;
	}

	public Boolean getOnPersonsFileAlternativeId() {
		return onPersonsFileAlternativeId;
	}

	public void setOnPersonsFileAlternativeId(Boolean onPersonsFileAlternativeId) {
		this.onPersonsFileAlternativeId = onPersonsFileAlternativeId;
	}

	public Boolean getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Boolean validStatus) {
		this.validStatus = validStatus;
	}

	public String getAssessorStatusBeforeAlteration() {
		return assessorStatusBeforeAlteration;
	}

	public void setAssessorStatusBeforeAlteration(String assessorStatusBeforeAlteration) {
		this.assessorStatusBeforeAlteration = assessorStatusBeforeAlteration;
	}

}
