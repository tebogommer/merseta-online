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
import haj.com.entity.Company;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * Excel name: Provider.xlsx Tab Name: Qualification
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_provider_qualification")
//id info
@LegacyReportingAnnotation(name = "Total Qualification Linked to Provider", query = "select count(o) from LegacyProviderQualification o where o.linkedAccreditationNumberOnProviderAccreditation = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification not Linked to Provider", query = "select count(o) from LegacyProviderQualification o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "count")
//accreditation info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacyProviderQualification o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total not Linked Qualification", query = "select count(o) from LegacyProviderQualification o where o.qualification is null", key = "count")
//tables
@LegacyReportingAnnotation(query = "select o from LegacyProviderQualification o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "Learnership not Linked to Provider", returnType = LegacyProviderQualification.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyProviderQualification o where o.qualification is null", key = "Not Linked Qualification", returnType = LegacyProviderQualification.class, singleResult = false)
public class LegacyProviderQualification extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sld_No")
	private String sldNo;

	/*
	 * PN: 2 Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class)
	@Column(name = "linked_SDL")
	private String linkedSDL;

	/*
	 * PN: 3 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo", className = String.class)
	@Column(name = "company_registration_no")
	private String companyRegistrationNo;

	/*
	 * PN: 4 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 5 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 6 SicCode
	 */
	@CSVAnnotation(name = "SicCode", className = String.class)
	@Column(name = "sic_code")
	private String sicCode;

	/*
	 * PN: 7 SicCodeDescription
	 */
	@CSVAnnotation(name = "SicCodeDescription", className = String.class)
	@Column(name = "sic_code_description")
	private String sicCodeDescription;

	/*
	 * PN: 8 ETQASetaDesc
	 */
	@CSVAnnotation(name = "ETQASetaDesc", className = String.class)
	@Column(name = "etqa_seta_desc")
	private String etqaSetaDesc;

	/*
	 * PN: 9 ProviderStatus
	 */
	@CSVAnnotation(name = "ProviderStatus", className = String.class)
	@Column(name = "provider_status")
	private String providerStatus;

	/*
	 * PN: 10 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 11 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 12 AccreditationNo
	 */
	@CSVAnnotation(name = "AccreditationNo", className = String.class, lookupField = "linkedAccreditationNumberOnProviderAccreditation")
	@Column(name = "accreditation_no")
	private String accreditationNo;

	/*
	 * PN: 13 QualCode
	 */
	@CSVAnnotation(name = "QualCode", className = String.class, lookupField = "qualification")
	@Column(name = "qual_code")
	private String qualCode;

	/*
	 * PN: 14 Saqa Us Id
	 */
	@CSVAnnotation(name = "Saqa_Us_Id", className = String.class)
	@Column(name = "saqa_us_id")
	private String saqaUsId;

	/*
	 * PN: 15 Merseta Skills Programme Registration Number
	 */
	@CSVAnnotation(name = "Merseta_Skills_Programme_Registration_Number", className = String.class)
	@Column(name = "merseta_skills_programme_reg_no")
	private String mersetaSkillsProgrammeRegNo;

	/*
	 * PN: 16 ProviderType
	 */
	@CSVAnnotation(name = "ProviderType", className = String.class)
	@Column(name = "provider_type")
	private String providerType;

	/*
	 * PN: 17 ProviderClass
	 */
	@CSVAnnotation(name = "ProviderClass", className = String.class)
	@Column(name = "provider_class")
	private String providerClass;

	/*
	 * PN: 18 ProviderIntExt
	 */
	@CSVAnnotation(name = "ProviderIntExt", className = String.class)
	@Column(name = "provider_int_ext")
	private String providerIntExt;

	/* Validiations Start */
	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "linked_sdl_number_on_sars_employer_file")
	private Boolean linkedSdlNumberOnSarsEmployerFile;
	
	@Fetch(FetchMode.JOIN)
	@Column(name = "linked_accreditation_number_on_provider_accreditation")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyProviderAccreditation", paramClass = String.class)
	private Boolean linkedAccreditationNumberOnProviderAccreditation;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "application_submited", columnDefinition = "BIT default false")
	private Boolean applicationSubmited;
	
	@Transient
	private Boolean qualificationExpired;

	/* Validiations end */

	/**
	 * Instantiates a new Accreditation.
	 */
	public LegacyProviderQualification() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LegacyProviderQualification other = (LegacyProviderQualification) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSldNo() {
		return sldNo;
	}

	public void setSldNo(String sldNo) {
		this.sldNo = sldNo;
	}

	public String getLinkedSDL() {
		return linkedSDL;
	}

	public void setLinkedSDL(String linkedSDL) {
		this.linkedSDL = linkedSDL;
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

	public String getEtqaSetaDesc() {
		return etqaSetaDesc;
	}

	public void setEtqaSetaDesc(String etqaSetaDesc) {
		this.etqaSetaDesc = etqaSetaDesc;
	}

	public String getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
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

	public String getAccreditationNo() {
		return accreditationNo;
	}

	public void setAccreditationNo(String accreditationNo) {
		this.accreditationNo = accreditationNo;
	}

	public String getQualCode() {
		return qualCode;
	}

	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}

	public String getSaqaUsId() {
		return saqaUsId;
	}

	public void setSaqaUsId(String saqaUsId) {
		this.saqaUsId = saqaUsId;
	}

	public String getMersetaSkillsProgrammeRegNo() {
		return mersetaSkillsProgrammeRegNo;
	}

	public void setMersetaSkillsProgrammeRegNo(String mersetaSkillsProgrammeRegNo) {
		this.mersetaSkillsProgrammeRegNo = mersetaSkillsProgrammeRegNo;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getProviderClass() {
		return providerClass;
	}

	public void setProviderClass(String providerClass) {
		this.providerClass = providerClass;
	}

	public String getProviderIntExt() {
		return providerIntExt;
	}

	public void setProviderIntExt(String providerIntExt) {
		this.providerIntExt = providerIntExt;
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

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Boolean getLinkedAccreditationNumberOnProviderAccreditation() {
		return linkedAccreditationNumberOnProviderAccreditation;
	}

	public void setLinkedAccreditationNumberOnProviderAccreditation(Boolean linkedAccreditationNumberOnProviderAccreditation) {
		this.linkedAccreditationNumberOnProviderAccreditation = linkedAccreditationNumberOnProviderAccreditation;
	}

}
