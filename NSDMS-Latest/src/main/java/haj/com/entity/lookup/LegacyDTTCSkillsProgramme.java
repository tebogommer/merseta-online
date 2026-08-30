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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: DTTC.xlsx 
 * Tab Name: Skills Programme
 */
@Entity
@Table(name = "legacy_dttc_skills_programme")
public class LegacyDTTCSkillsProgramme extends AbstractLookup {

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
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition =
	 * "LONGTEXT") 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class)
	@Column(name = "Linked_SDL")
	private String linkedSdl;

	/*
	 * PN: 2 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sdl_no")
	private String sdlNo;

	/*
	 * PN: 3 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 4 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 5 DTTCStatus
	 */
	@CSVAnnotation(name = "DTTCStatus", className = String.class)
	@Column(name = "dttc_status")
	private String dttcStatus;

	/*
	 * PN: 6 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 7 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 8 sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no")
	private String sRefNo;

	/*
	 * PN: 9 sProgrammeDesc
	 */
	@CSVAnnotation(name = "sProgrammeDesc", className = String.class)
	@Column(name = "s_programme_desc")
	private String sProgrammeDesc;

	/*
	 * PN: 10 Saqa Us Id
	 */
	@CSVAnnotation(name = "Saqa_Us_Id", className = String.class)
	@Column(name = "saqa_us_id")
	private String saqaUsId;

	/*
	 * PN: 11 Skills Programme Registration Number
	 */
	@CSVAnnotation(name = "Skills_Programme_Registration_Number", className = String.class)
	@Column(name = "skills_programme_registration_number")
	private String skillsProgrammeRegistrationNumber;
	
	@Column(name = "valid_rsa_id_number")
	private Boolean validRsaIdNumber;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id")
	private SkillsProgram skillsProgram;
	
	@Column(name = "processed")
	private Boolean processed;
	
	

	public LegacyDTTCSkillsProgramme() {
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
		LegacyDTTCSkillsProgramme other = (LegacyDTTCSkillsProgramme) obj;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getSdlNo() {
		return sdlNo;
	}

	public void setSdlNo(String sdlNo) {
		this.sdlNo = sdlNo;
	}

	public String getOrganisationNameLegal() {
		return organisationNameLegal;
	}

	public void setOrganisationNameLegal(String organisationNameLegal) {
		this.organisationNameLegal = organisationNameLegal;
	}

	public String getOrganisationNameTrade() {
		return organisationNameTrade;
	}

	public void setOrganisationNameTrade(String organisationNameTrade) {
		this.organisationNameTrade = organisationNameTrade;
	}

	public String getDttcStatus() {
		return dttcStatus;
	}

	public void setDttcStatus(String dttcStatus) {
		this.dttcStatus = dttcStatus;
	}

	public String getAccreditationStartDate() {
		return accreditationStartDate;
	}

	public void setAccreditationStartDate(String accreditationStartDate) {
		this.accreditationStartDate = accreditationStartDate;
	}

	public String getAccreditationEndDate() {
		return accreditationEndDate;
	}

	public void setAccreditationEndDate(String accreditationEndDate) {
		this.accreditationEndDate = accreditationEndDate;
	}

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getSaqaUsId() {
		return saqaUsId;
	}

	public void setSaqaUsId(String saqaUsId) {
		this.saqaUsId = saqaUsId;
	}

	public String getsProgrammeDesc() {
		return sProgrammeDesc;
	}

	public void setsProgrammeDesc(String sProgrammeDesc) {
		this.sProgrammeDesc = sProgrammeDesc;
	}

	public String getSkillsProgrammeRegistrationNumber() {
		return skillsProgrammeRegistrationNumber;
	}

	public void setSkillsProgrammeRegistrationNumber(String skillsProgrammeRegistrationNumber) {
		this.skillsProgrammeRegistrationNumber = skillsProgrammeRegistrationNumber;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

}
