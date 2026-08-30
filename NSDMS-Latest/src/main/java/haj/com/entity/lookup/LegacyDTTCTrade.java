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
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: DTTC.xlsx
 * Tab Name: Trade
 */
@Entity
@Table(name = "legacy_dttc_trade")
// id info
@LegacyReportingAnnotation(name = "Total DTTC Trade Linked to Site", query = "select count(o) from LegacyDTTCTrade o where o.linkedAccreditationNumberOnProviderAccreditation = true", key = "count")
@LegacyReportingAnnotation(name = "Total DTTC Trade not Linked to Site", query = "select count(o) from LegacyDTTCTrade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked DTTC Trade", query = "select count(o) from LegacyDTTCTrade o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total not Linked DTTC Trade", query = "select count(o) from LegacyDTTCTrade o where o.qualification is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyDTTCTrade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "DTTC Trade not Linked to Site", returnType = LegacyDTTCTrade.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyDTTCTrade o where o.qualification is null", key = "Not Linked DTTC Trade", returnType = LegacyDTTCTrade.class, singleResult = false)
public class LegacyDTTCTrade extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
	 * SDLNo 
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "linkedAccreditationNumberOnProviderAccreditation")
	@Column(name = "sdl_no")
	private String sdlNo;
	
	/* 
	 * PN: 2
	 * Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class)
	@Column(name = "linked_sdl")
	private String linkedSdl;
	
	/* 
	 * PN: 3
	 * OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;
	
	/* 
	 * PN: 4
	 * OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisatioNameTrade;
	
	/* 
	 * PN: 5
	 * DTTCStatus
	 */
	@CSVAnnotation(name = "DTTCStatus", className = String.class)
	@Column(name = "dttc_status")
	private String dttcStatus;
	
	/* 
	 * PN: 6
	 * AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;
	
	/* 
	 * PN: 7
	 * AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;
	
	/* 
	 * PN: 8
	 * QualificationCode
	 */
	@CSVAnnotation(name = "QualificationCode", className = String.class, lookupField = "qualification")
	@Column(name = "qualification_code")
	private String qualificationCode;
	
	/* 
	 * PN: 9
	 * TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;
	
	/* 
	 * PN: 10
	 * SAQA_ID
	 */
	@CSVAnnotation(name = "SAQA_ID", className = String.class)
	@Column(name = "saqa_id")
	private String saqaID;
	
	/* 
	 * PN: 11
	 * Merseta_Skills_Programme_Registration_Number
	 */
	@CSVAnnotation(name = "Merseta_Skills_Programme_Registration_Number", className = String.class)
	@Column(name = "merseta_skills_programme_registration_number")
	private String mersetaSkillsProgrammeRegistrationNumber;
	
	/* 
	 * PN: 12
	 * CompanyRegistrationNo
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo", className = String.class)
	@Column(name = "company_registration_no")
	private String companyRegistrationNo;
	
	/* 
	 * PN: 13
	 * SicCode
	 */
	@CSVAnnotation(name = "SicCode", className = String.class)
	@Column(name = "sic_code")
	private String sicCode;
	
	/* 
	 * PN: 14
	 * SicCodeDescription
	 */
	@CSVAnnotation(name = "SicCodeDescription", className = String.class)
	@Column(name = "sic_code_description")
	private String sicCodeDescription;
	


	@Column(name = "linked_accreditation_number_on_provider_accreditation")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyDTTCApproval", paramClass = String.class)
	private Boolean linkedAccreditationNumberOnProviderAccreditation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;
	
	
	@Column(name = "processed")
	private Boolean processed;
	
	public LegacyDTTCTrade() {
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
		LegacyDTTCTrade other = (LegacyDTTCTrade) obj;
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

	public String getOrganisatioNameTrade() {
		return organisatioNameTrade;
	}

	public void setOrganisatioNameTrade(String organisatioNameTrade) {
		this.organisatioNameTrade = organisatioNameTrade;
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

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public String getSaqaID() {
		return saqaID;
	}

	public void setSaqaID(String saqaID) {
		this.saqaID = saqaID;
	}

	public String getMersetaSkillsProgrammeRegistrationNumber() {
		return mersetaSkillsProgrammeRegistrationNumber;
	}

	public void setMersetaSkillsProgrammeRegistrationNumber(String mersetaSkillsProgrammeRegistrationNumber) {
		this.mersetaSkillsProgrammeRegistrationNumber = mersetaSkillsProgrammeRegistrationNumber;
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
	
	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}

	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getSicCodeDescription() {
		return sicCodeDescription;
	}

	public void setSicCodeDescription(String sicCodeDescription) {
		this.sicCodeDescription = sicCodeDescription;
	}

}
