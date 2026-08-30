package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import haj.com.annotations.CSVAnnotation;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersProgress;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

// TODO: Auto-generated Javadoc
/**
 * Excel name: Provider.xlsx Tab Name: Accreditation
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_provider_accreditation") // qualification info
@LegacyReportingAnnotation(name = "Total Sites Linked to SDL Number", query = "select count(o) from LegacyProviderAccreditation o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Sites not Linked to SDL Number", query = "select count(o) from LegacyProviderAccreditation o where o.legacyOrganisationSites = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked SDL on SARS File", query = "select count(o) from LegacyProviderAccreditation o where o.checkCountByRefNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Linked SDL not on SARS File", query = "select count(o) from LegacyProviderAccreditation o where o.checkCountByRefNumber = false", key = "count")
// id info
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs Linked to SDL Number", query = "select count(o) from LegacyProviderAccreditation o where o.legacyOrganisationNonLevyPaying = true", key = "count")
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs not Linked to SDL Number", query = "select count(o) from LegacyProviderAccreditation o where o.legacyOrganisationNonLevyPaying = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Providers with Accreditation Num", query = "select count(o) from LegacyProviderAccreditation o where o.accreditationNo is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Providers without Accreditation Num", query = "select count(o) from LegacyProviderAccreditation o where o.accreditationNo is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where o.legacyOrganisationSites = false", key = "Sites not Linked to SDL Number", returnType = LegacyProviderAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where o.checkCountByRefNumber = false", key = "Linked SDL not on SARS File", returnType = LegacyProviderAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where o.legacyOrganisationNonLevyPaying = false", key = "Non Levy Paying Orgs not Linked to SDL Number", returnType = LegacyProviderAccreditation.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where o.accreditationNo is null", key = "Providers without Accreditation Num", returnType = LegacyProviderAccreditation.class, singleResult = false)
// accreditationStartDate accreditationEndDate
// date info
@LegacyReportingAnnotation(name = "Total Providers with Valid End Date", query = "select count(o) from LegacyProviderAccreditation o where date(o.accreditationEndDate) > date(o.accreditationStartDate)", key = "count")
@LegacyReportingAnnotation(name = "Total Providers with Invalid End Date", query = "select count(o) from LegacyProviderAccreditation o where date(o.accreditationEndDate) < date(o.accreditationStartDate) or date(o.accreditationEndDate) = date(o.accreditationStartDate) or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where date(o.accreditationEndDate) < date(o.accreditationStartDate)", key = "Providers with Invalid End Date", returnType = LegacyProviderAccreditation.class, singleResult = false)
// expired info
@LegacyReportingAnnotation(name = "Total Providers with Valid Status", query = "select count(o) from LegacyProviderAccreditation o where date(o.accreditationEndDate) < date(NOW()) and o.providerStatus = 'Expired'", key = "count")
@LegacyReportingAnnotation(name = "Total Providers with Invalid Status", query = "select count(o) from LegacyProviderAccreditation o where date(o.accreditationEndDate) < date(NOW()) and o.providerStatus <> 'Expired'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where date(o.accreditationEndDate) < date(NOW()) and o.providerStatus <> 'Expired'", key = "Providers with Invalid Status", returnType = LegacyProviderAccreditation.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total Providers with Start and End Date", query = "select count(o) from LegacyProviderAccreditation o where (o.accreditationEndDate is not null and o.accreditationStartDate is not null) and o.accreditationEndDate <> 'NULL' and o.accreditationStartDate <> 'NULL'", key = "count")
@LegacyReportingAnnotation(name = "Total Providers without Start or End Date", query = "select count(o) from LegacyProviderAccreditation o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyProviderAccreditation o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "Providers with Start or End Date", returnType = LegacyProviderAccreditation.class, singleResult = false) //
public class LegacyProviderAccreditation extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1 ProviderId
	 */
	@CSVAnnotation(name = "ProviderId", className = String.class)
	@Column(name = "provider_id")
	private String providerId;

	/*
	 * PN: 2 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "sdl_number")
	private String sdlNumber;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationSites", paramClass = String.class)
	private Boolean legacyOrganisationSites;
	
	/*
	 * PN: 3 Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class, lookupField = "checkCountByRefNumber")
	@Column(name = "linked_SDL")
	private String linkedSdl;

	@CSVAnnotation(name = "Linked_SDL", className = String.class, lookupField = "legacyOrganisationNonLevyPaying")
	@Transient
	private String linkedSdlTransient;

	@Column(name = "checkCount_by_ref_number")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkCountByRefNumber", paramClass = String.class)
	private Boolean checkCountByRefNumber;

	@Column(name = "legacy_organisation_non_levy_paying")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationNonLevyPaying", paramClass = String.class)
	private Boolean legacyOrganisationNonLevyPaying;

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
	 * PN: 6 ProviderIntExt
	 */
	@CSVAnnotation(name = "ProviderIntExt", className = String.class)
	@Column(name = "provider_int_ext")
	private String providerIntExt;

	/*
	 * PN: 7 ProviderType
	 */
	@CSVAnnotation(name = "ProviderType", className = String.class)
	@Column(name = "provider_type")
	private String providerType;

	/*
	 * PN: 8 ProviderClass
	 */
	@CSVAnnotation(name = "ProviderClass", className = String.class)
	@Column(name = "provider_class")
	private String providerClass;

	/*
	 * PN: 9 ProviderStatus
	 */
	@CSVAnnotation(name = "ProviderStatus", className = String.class)
	@Column(name = "provider_status")
	private String providerStatus;

	/*
	 * PN: 10 ProviderStatusEffectiveDate
	 */
	@CSVAnnotation(name = "ProviderStatusEffectiveDate", className = String.class)
	@Column(name = "provider_status_effective_date")
	private String providerStatusEffectiveDate;

	/*
	 * PN: 11 ApplicationRecDate
	 */
	@CSVAnnotation(name = "ApplicationRecDate", className = String.class)
	@Column(name = "application_rec_date")
	private String applicationRecDate;

	/*
	 * PN: 12 AccreditationNo
	 */
	@CSVAnnotation(name = "AccreditationNo", className = String.class)
	@Column(name = "accreditation_no")
	private String accreditationNo;

	/*
	 * PN: 13 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 14 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 15 ETQADecisionNo
	 */
	@CSVAnnotation(name = "ETQADecisionNo", className = String.class)
	@Column(name = "etqa_decision_no")
	private String etqaDecisionNo;

	/*
	 * PN: 16 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	/*
	 * PN: 17 iThisETQA
	 */
	@CSVAnnotation(name = "iThisETQA", className = String.class)
	@Column(name = "iThis_ETQA")
	private String iThisETQA;

	/*
	 * PN: 18 sETQA
	 */
	@CSVAnnotation(name = "sETQA", className = String.class)
	@Column(name = "sETQA")
	private String sETQA;

	/*
	 * PN: 19 sSaqaRefNo
	 */
	@CSVAnnotation(name = "sSaqaRefNo", className = String.class)
	@Column(name = "sSaqaRefNo")
	private String sSaqaRefNo;
	
	@Column(name = "valid_status")
	private Boolean validStatus;
	
	@Column(name = "duplicate_acc_number")
	private Boolean duplicateAccNumber;
	
	@Column(name = "legacy_organisation_sites_main_sdl")
	private String legacyOrganisationSitesMainSdl;
	
	@Column(name = "legacy_organisation_sites_linked_sdl")
	private String legacyOrganisationSitesLinkedSdl;
	
	@Transient
	private Company company;
	
	@Transient
	private List<LegacyProviderQualification> legacyProviderQualificationList;
	
	@Transient
	private List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList;
	
	@Transient
	private List<LegacyProviderUnitStandard> legacyProviderUnitStandardList;

	/**
	 * Instantiates a new Accreditation.
	 */
	public LegacyProviderAccreditation() {
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
		LegacyProviderAccreditation other = (LegacyProviderAccreditation) obj;
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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getSdlNumber() {
		return sdlNumber;
	}

	public void setSdlNumber(String sdlNumber) {
		this.sdlNumber = sdlNumber;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
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

	public String getProviderIntExt() {
		return providerIntExt;
	}

	public void setProviderIntExt(String providerIntExt) {
		this.providerIntExt = providerIntExt;
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

	public String getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
	}

	public String getProviderStatusEffectiveDate() {
		return providerStatusEffectiveDate;
	}

	public void setProviderStatusEffectiveDate(String providerStatusEffectiveDate) {
		this.providerStatusEffectiveDate = providerStatusEffectiveDate;
	}

	public String getApplicationRecDate() {
		return applicationRecDate;
	}

	public void setApplicationRecDate(String applicationRecDate) {
		this.applicationRecDate = applicationRecDate;
	}

	public String getAccreditationNo() {
		return accreditationNo;
	}

	public void setAccreditationNo(String accreditationNo) {
		this.accreditationNo = accreditationNo;
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

	public String getEtqaDecisionNo() {
		return etqaDecisionNo;
	}

	public void setEtqaDecisionNo(String etqaDecisionNo) {
		this.etqaDecisionNo = etqaDecisionNo;
	}

	public String getiThisETQA() {
		return iThisETQA;
	}

	public void setiThisETQA(String iThisETQA) {
		this.iThisETQA = iThisETQA;
	}

	public String getsETQA() {
		return sETQA;
	}

	public void setsETQA(String sETQA) {
		this.sETQA = sETQA;
	}

	public String getsSaqaRefNo() {
		return sSaqaRefNo;
	}

	public void setsSaqaRefNo(String sSaqaRefNo) {
		this.sSaqaRefNo = sSaqaRefNo;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(Boolean legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}


	public String getLinkedSdlTransient() {
		return linkedSdlTransient;
	}

	public void setLinkedSdlTransient(String linkedSdlTransient) {
		this.linkedSdlTransient = linkedSdlTransient;
	}

	public Boolean getCheckCountByRefNumber() {
		return checkCountByRefNumber;
	}

	public void setCheckCountByRefNumber(Boolean checkCountByRefNumber) {
		this.checkCountByRefNumber = checkCountByRefNumber;
	}

	public Boolean getLegacyOrganisationNonLevyPaying() {
		return legacyOrganisationNonLevyPaying;
	}

	public void setLegacyOrganisationNonLevyPaying(Boolean legacyOrganisationNonLevyPaying) {
		this.legacyOrganisationNonLevyPaying = legacyOrganisationNonLevyPaying;
	}

	public Boolean getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Boolean validStatus) {
		this.validStatus = validStatus;
	}

	public Boolean getDuplicateAccNumber() {
		return duplicateAccNumber;
	}

	public void setDuplicateAccNumber(Boolean duplicateAccNumber) {
		this.duplicateAccNumber = duplicateAccNumber;
	}

	public List<LegacyProviderQualification> getLegacyProviderQualificationList() {
		return legacyProviderQualificationList;
	}

	public void setLegacyProviderQualificationList(List<LegacyProviderQualification> legacyProviderQualificationList) {
		this.legacyProviderQualificationList = legacyProviderQualificationList;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeList() {
		return legacyProviderSkillsProgrammeList;
	}

	public void setLegacyProviderSkillsProgrammeList(
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList) {
		this.legacyProviderSkillsProgrammeList = legacyProviderSkillsProgrammeList;
	}

	public List<LegacyProviderUnitStandard> getLegacyProviderUnitStandardList() {
		return legacyProviderUnitStandardList;
	}

	public void setLegacyProviderUnitStandardList(List<LegacyProviderUnitStandard> legacyProviderUnitStandardList) {
		this.legacyProviderUnitStandardList = legacyProviderUnitStandardList;
	}

	public String getLegacyOrganisationSitesMainSdl() {
		return legacyOrganisationSitesMainSdl;
	}

	public void setLegacyOrganisationSitesMainSdl(String legacyOrganisationSitesMainSdl) {
		this.legacyOrganisationSitesMainSdl = legacyOrganisationSitesMainSdl;
	}

	public String getLegacyOrganisationSitesLinkedSdl() {
		return legacyOrganisationSitesLinkedSdl;
	}

	public void setLegacyOrganisationSitesLinkedSdl(String legacyOrganisationSitesLinkedSdl) {
		this.legacyOrganisationSitesLinkedSdl = legacyOrganisationSitesLinkedSdl;
	}
}
