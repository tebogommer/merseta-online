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
import haj.com.framework.AbstractLookup;

/**
 * Excel name: Moderator.xlsx
 * Tab Name: Learnership
 */
@Entity
@Table(name = "legacy_moderator_learnership")
public class LegacyModeratorLearnership extends AbstractLookup {
	
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
	 * Filed Sizes:
	 * Book: 				@Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so chataers 	@Column(name = "dttc_id" , length = 500)
	 */
	
	/*
	 * PN: 1
	 * AssessorId 
	 */
	@CSVAnnotation(name = "AssessorId", className = String.class)
	@Column(name = "assessor_id")
	private String assessorId;
	
	/* 
	 * PN: 2
	 * AlternateId
	 */
	@CSVAnnotation(name = "AlternateIDNo", className = String.class)
	@Column(name = "alternate_id")
	private String alternateId;
	
	/* 
	 * PN: 3
	 * Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;	
	
	/* 
	 * PN: 4
	 * MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names")
	private String middleNames;
	
	/* 
	 * PN: 5
	 * Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;
	
	/* 
	 * PN: 6
	 * LearnershipCode
	 */
	@CSVAnnotation(name = "LearnershipCode", className = String.class)
	@Column(name = "learnership_code")
	private String learnershipCode;
	
	/* 
	 * PN: 7
	 * LshipCode
	 */
	@CSVAnnotation(name = "LshipCode", className = String.class)
	@Column(name = "l_ship_code")
	private String lshipCode;
	
	/* 
	 * PN: 8
	 * SAQA Unit Standard ID
	 */
	@CSVAnnotation(name = "SAQA_Unit_Standard_ID", className = String.class)
	@Column(name = "saqa_unit_standard_id")
	private String saqaUnitStandardId;
	
	/* 
	 * PN: 9
	 * ModeratorRegStartDate
	 */
	@CSVAnnotation(name = "ModeratorRegStartDate", className = String.class)
	@Column(name = "moderator_reg_start_date")
	private String moderatorRegStartDate;
	
	/* 
	 * PN: 10
	 * ModeratorFirstRegDate
	 */
	@CSVAnnotation(name = "ModeratorFirstRegDate", className = String.class)
	@Column(name = "moderator_first_reg_date")
	private String moderatorFirstRegDate;
	
	/* 
	 * PN: 11
	 * ModeratorRegEndDate
	 */
	@CSVAnnotation(name = "ModeratorRegEndDate", className = String.class)
	@Column(name = "moderator_reg_end_date")
	private String moderatorRegEndDate;
	
	/* 
	 * PN: 12
	 * ModeratorRegNo
	 */
	@CSVAnnotation(name = "ModeratorRegNo", className = String.class)
	@Column(name = "moderator_reg_no")
	private String moderatorRegNo;
	
	/* 
	 * PN: 13
	 * ModeratorStatusDesc
	 */                    
	@CSVAnnotation(name = "ModeratorStatusDesc", className = String.class)
	@Column(name = "moderator_status_desc")
	private String moderatorStatusDesc;	
	
	@Column(name = "valid_rsa_id_number")
	private Boolean validRsaIdNumber;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Learnership_id")
	private Learnership learnership;
	
	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "application_submited", columnDefinition = "BIT default false")
	private Boolean applicationSubmited;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;
	@Transient
	private Boolean qualificationExpired;
	
	public LegacyModeratorLearnership() {
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
		LegacyModeratorLearnership other = (LegacyModeratorLearnership) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/* Getters and setters  */

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

	public String getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}

	public String getAlternateId() {
		return alternateId;
	}

	public void setAlternateId(String alternateId) {
		this.alternateId = alternateId;
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

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getLshipCode() {
		return lshipCode;
	}

	public void setLshipCode(String lshipCode) {
		this.lshipCode = lshipCode;
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

	public String getSaqaUnitStandardId() {
		return saqaUnitStandardId;
	}

	public void setSaqaUnitStandardId(String saqaUnitStandardId) {
		this.saqaUnitStandardId = saqaUnitStandardId;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getApplicationSubmited() {
		return applicationSubmited;
	}

	public void setApplicationSubmited(Boolean applicationSubmited) {
		this.applicationSubmited = applicationSubmited;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Boolean getQualificationExpired() {
		return qualificationExpired;
	}

	public void setQualificationExpired(Boolean qualificationExpired) {
		this.qualificationExpired = qualificationExpired;
	}

}
