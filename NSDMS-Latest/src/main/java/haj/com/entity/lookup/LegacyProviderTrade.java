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

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * Excel name: Provider.xlsx Tab Name: Trade
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_provider_trade")
// id info
@LegacyReportingAnnotation(name = "Total Trade Linked to Provider", query = "select count(o) from LegacyProviderTrade o where o.linkedAccreditationNumberOnProviderAccreditation = true", key = "count")
@LegacyReportingAnnotation(name = "Total Trade not Linked to Provider", query = "select count(o) from LegacyProviderTrade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked Trade", query = "select count(o) from LegacyProviderTrade o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total not Linked Trade", query = "select count(o) from LegacyProviderTrade o where o.qualification is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyProviderTrade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "Trade not Linked to Provider", returnType = LegacyProviderTrade.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyProviderTrade o where o.qualification is null", key = "Not Linked Trade", returnType = LegacyProviderTrade.class, singleResult = false)
public class LegacyProviderTrade extends AbstractLookup {

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
	 * PN: 5 ETQASetaDesc
	 */
	@CSVAnnotation(name = "ETQASetaDesc", className = String.class)
	@Column(name = "etqa_seta_desc")
	private String etqaSetaDesc;

	/*
	 * PN: 6 ProviderStatus
	 */
	@CSVAnnotation(name = "ProviderStatus", className = String.class)
	@Column(name = "provider_status")
	private String providerStatus;

	/*
	 * PN: 7 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 8 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 9 AccreditationNo
	 */
	@CSVAnnotation(name = "AccreditationNo", className = String.class, lookupField = "linkedAccreditationNumberOnProviderAccreditation")
	@Column(name = "accreditation_no")
	private String accreditationNo;

	/*
	 * PN: 10 QualificationCode
	 */
	@CSVAnnotation(name = "QualificationCode", className = String.class, lookupField = "qualification")
	@Column(name = "qualification_code")
	private String qualificationCode;

	/*
	 * PN: 11 TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;

	/*
	 * PN: 12 Saqa_Us_Id
	 */
	@CSVAnnotation(name = "Saqa_Us_Id", className = String.class)
	@Column(name = "saqa_us_id")
	private String saqaUsId;

	/*
	 * PN: 13 Merseta_Skills_Programme_Registration_Number
	 */
	@CSVAnnotation(name = "Merseta_Skills_Programme_Registration_Number", className = String.class)
	@Column(name = "merseta_skills_programme_reg_no")
	private String mersetaskillsProgrammeNo;

	/*
	 * PN: 14 ProviderType
	 */
	@CSVAnnotation(name = "ProviderType", className = String.class)
	@Column(name = "provider_type")
	private String providerType;

	/*
	 * PN: 15 ProviderClass
	 */
	@CSVAnnotation(name = "ProviderClass", className = String.class)
	@Column(name = "provider_class")
	private String providerClass;

	/*
	 * PN: 16 ProviderIntExt
	 */
	@CSVAnnotation(name = "ProviderIntExt", className = String.class)
	@Column(name = "provider_int_ext")
	private String providerIntExt;

	@Column(name = "linked_accreditation_number_on_provider_accreditation")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyProviderAccreditation", paramClass = String.class)
	private Boolean linkedAccreditationNumberOnProviderAccreditation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	/**
	 * Instantiates a new Accreditation.
	 */
	public LegacyProviderTrade() {
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
		LegacyProviderTrade other = (LegacyProviderTrade) obj;
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

	public String getSaqaUsId() {
		return saqaUsId;
	}

	public void setSaqaUsId(String saqaUsId) {
		this.saqaUsId = saqaUsId;
	}

	public String getMersetaskillsProgrammeNo() {
		return mersetaskillsProgrammeNo;
	}

	public void setMersetaskillsProgrammeNo(String mersetaskillsProgrammeNo) {
		this.mersetaskillsProgrammeNo = mersetaskillsProgrammeNo;
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

	public Boolean getLinkedAccreditationNumberOnProviderAccreditation() {
		return linkedAccreditationNumberOnProviderAccreditation;
	}

	public void setLinkedAccreditationNumberOnProviderAccreditation(Boolean linkedAccreditationNumberOnProviderAccreditation) {
		this.linkedAccreditationNumberOnProviderAccreditation = linkedAccreditationNumberOnProviderAccreditation;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

}
