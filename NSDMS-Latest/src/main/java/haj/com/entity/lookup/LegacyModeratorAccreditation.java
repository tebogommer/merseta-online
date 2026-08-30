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
 * Excel name: Moderator.xlsx Tab Name: Accreditation
 */
@Entity
@Table(name = "legacy_moderator_accreditation")
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyModeratorAccreditation o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyModeratorAccreditation o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyModeratorAccreditation o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyModeratorAccreditation o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyModeratorAccreditation o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyModeratorAccreditation o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyModeratorAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyModeratorAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyModeratorAccreditation.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total Moderator with Accreditation Num", query = "select count(o) from LegacyModeratorAccreditation o where o.moderatorRegNo is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Moderator without Accreditation Num", query = "select count(o) from LegacyModeratorAccreditation o where o.moderatorRegNo is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where o.moderatorRegNo is null", key = "Moderator without Accreditation Num", returnType = LegacyModeratorAccreditation.class, singleResult = false)
// date info
@LegacyReportingAnnotation(name = "Total Moderator with Valid End Date", query = "select count(o) from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) > date(o.moderatorRegStartDate)", key = "count")
@LegacyReportingAnnotation(name = "Total Moderator with Invalid End Date", query = "select count(o) from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) < date(o.moderatorRegStartDate)or date(o.moderatorRegEndDate) = date(o.moderatorRegStartDate)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) < date(o.moderatorRegStartDate)", key = "Moderator with Invalid End Date", returnType = LegacyModeratorAccreditation.class, singleResult = false)
// expired info
@LegacyReportingAnnotation(name = "Total Moderator with Valid Status", query = "select count(o) from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) < date(NOW()) and o.moderatorStatusDesc = 'Expired'", key = "count")
@LegacyReportingAnnotation(name = "Total Moderator with Invalid Status", query = "select count(o) from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) < date(NOW()) and o.moderatorStatusDesc <> 'Expired'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where date(o.moderatorRegEndDate) < date(NOW()) and o.moderatorStatusDesc <> 'Expired'", key = "Moderator with Invalid Status", returnType = LegacyModeratorAccreditation.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total Moderator with Start and End Date", query = "select count(o) from LegacyModeratorAccreditation o where o.moderatorRegNo is not null and (o.moderatorRegEndDate is not null and o.moderatorRegStartDate is not null)", key = "count")
@LegacyReportingAnnotation(name = "Total Moderator without Start or End Date", query = "select count(o) from LegacyModeratorAccreditation o where o.moderatorRegNo is not null and (o.moderatorRegEndDate is null or o.moderatorRegStartDate is null)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyModeratorAccreditation o where o.moderatorRegNo is not null and (o.moderatorRegEndDate is null or o.moderatorRegStartDate is null)", key = "Moderator with Start or End Date", returnType = LegacyModeratorAccreditation.class, singleResult = false) //
public class LegacyModeratorAccreditation extends AbstractLookup {

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
	 * PN: 6 ModeratorRegStartDate
	 */
	@CSVAnnotation(name = "ModeratorRegStartDate", className = String.class)
	@Column(name = "moderator_reg_start_date")
	private String moderatorRegStartDate;

	/*
	 * PN: 7 ModeratorRegEndDate
	 */
	@CSVAnnotation(name = "ModeratorRegEndDate", className = String.class)
	@Column(name = "moderator_reg_end_date")
	private String moderatorRegEndDate;

	/*
	 * PN: 8 ModeratorRegNo
	 */
	@CSVAnnotation(name = "ModeratorRegNo", className = String.class)
	@Column(name = "moderator_reg_no")
	private String moderatorRegNo;

	/*
	 * PN: 9 ModeratorStatusDesc
	 */
	@CSVAnnotation(name = "ModeratorStatusDesc", className = String.class)
	@Column(name = "moderator_status_desc")
	private String moderatorStatusDesc;

	/*
	 * PN: 10 ModeratorStatusEffectiveDate
	 */
	@CSVAnnotation(name = "ModeratorStatusEffectiveDate", className = String.class)
	@Column(name = "moderator_status_effective_date")
	private String moderatorStatusEffectiveDate;

	/*
	 * PN: 11 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	// /*
	// * PN: 12
	// * sRefNo
	// */
	// @CSVAnnotation(name = "sRefNo", className = String.class)
	// @Column(name = "s_ref_no")
	// private String sRefNo;

	/*
	 * PN: 12 DecisionNumber
	 */
	@CSVAnnotation(name = "DecisionNumber", className = String.class)
	@Column(name = "decision_number")
	private String decisionNumber;

	/*
	 * PN: 13 ReviewCommitteeDate
	 */
	@CSVAnnotation(name = "ReviewCommitteeDate", className = String.class)
	@Column(name = "review_committee_date")
	private String reviewCommitteeDate;

	/*
	 * PN: 14 Scope YN
	 */
	@CSVAnnotation(name = "Scope_YN", className = String.class)
	@Column(name = "scope_yn")
	private String scopeYn;

	@Column(name = "processed")
	private Boolean processed;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;
	
	@Column(name = "on_persons_file_alternative_id")
	private Boolean onPersonsFileAlternativeId;
	
	@Column(name = "alive_on_home_affairs_data")
	private Boolean aliveOnHomeAffairsData;
	
	@Column(name = "on_persons_file")
	private Boolean onPersonsFile;
	
	@Column(name = "valid_status")
	private Boolean validStatus;

	public LegacyModeratorAccreditation() {
		super();
	}

	@Transient
	private Users user;

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
		LegacyModeratorAccreditation other = (LegacyModeratorAccreditation) obj;
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

	public String getModeratorRegStartDate() {
		return moderatorRegStartDate;
	}

	public void setModeratorRegStartDate(String moderatorRegStartDate) {
		this.moderatorRegStartDate = moderatorRegStartDate;
	}

	public String getModeratorRegEndDate() {
		return moderatorRegEndDate;
	}

	public void setModeratorRegEndDate(String moderatorRegEndDate) {
		this.moderatorRegEndDate = moderatorRegEndDate;
	}

	public String getModeratorStatusDesc() {
		return moderatorStatusDesc;
	}

	public void setModeratorStatusDesc(String moderatorStatusDesc) {
		this.moderatorStatusDesc = moderatorStatusDesc;
	}

	public String getModeratorStatusEffectiveDate() {
		return moderatorStatusEffectiveDate;
	}

	public void setModeratorStatusEffectiveDate(String moderatorStatusEffectiveDate) {
		this.moderatorStatusEffectiveDate = moderatorStatusEffectiveDate;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
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

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public String getScopeYn() {
		return scopeYn;
	}

	public void setScopeYn(String scopeYn) {
		this.scopeYn = scopeYn;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getModeratorRegNo() {
		return moderatorRegNo;
	}

	public void setModeratorRegNo(String moderatorRegNo) {
		this.moderatorRegNo = moderatorRegNo;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

}
