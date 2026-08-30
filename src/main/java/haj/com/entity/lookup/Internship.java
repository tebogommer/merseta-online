package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * DUPLICATE ENTITY 
 * Excel name: Internship.xlsx
 * Tab Name: Internship
 *
 * @author Techfinium
 */
@Entity
@Table(name = "internship")
public class Internship extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	
	/*
	 * PN: 1
	 * PersonId 
	 */
	@CSVAnnotation(name = "id", className = String.class)
	@Column(name = "id_2"  )
	private String id2;
	
	/* 
	 * PN: 2
	 * IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class)
	@Column(name = "id_no"  )
	private String IDNo;
	
	
	/*
	 * PN: 3
	 * Firstname 
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name"  )
	private String firstName;
	
	/* 
	 * PN: 4
	 * MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names"  )
	private String middleNames;
	
	
	/*
	 * PN: 5
	 * Surname 
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname"  )
	private String surname;
	
	/* 
	 * PN: 6
	 * StartDate
	 */
	@CSVAnnotation(name = "StartDate", className = String.class)
	@Column(name = "start_date"  )
	private String startDate;
	
	
	/*
	 * PN: 7
	 * EndDate 
	 */
	@CSVAnnotation(name = "EndDate", className = String.class)
	@Column(name = "end_date"  )
	private String endDate;
	
	/* 
	 * PN: 8
	 * ApplicationDate
	 */
	@CSVAnnotation(name = "ApplicationDate", className = String.class)
	@Column(name = "application_date"  )
	private String applicationDate;
	
	
	/*
	 * PN: 9
	 * RegistrationDate 
	 */
	@CSVAnnotation(name = "RegistrationDate", className = String.class)
	@Column(name = "registration_date"  )
	private String RegistrationDate;
	
	/* 
	 * PN: 10
	 * CompletionDate
	 */
	@CSVAnnotation(name = "CompletionDate", className = String.class)
	@Column(name = "completion_date"  )
	private String completionDate;
	
	
	/*
	 * PN: 11
	 * EmployerSDL 
	 */
	@CSVAnnotation(name = "Status", className = String.class)
	@Column(name = "status"  )
	private String status;
	
	/* 
	 * PN: 12
	 * EmployerSDL
	 */
	@CSVAnnotation(name = "EmployerSDL", className = String.class)
	@Column(name = "EmployerSDL"  )
	private String EmployerSDL;
	
	
	/*
	 * PN: 13
	 * OrganisationNameLegal 
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal"  )
	private String organisationNameLegal;
	
	/* 
	 * PN: 14
	 * OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade"  )
	private String organisationNameTrade;
	
	/* 
	 * PN: 15
	 * LangDesc
	 */
	@CSVAnnotation(name = "ProgrammeType", className = String.class)
	@Column(name = "programme_type"  )
	private String ProgrammeType;
	
	/* 
	 * PN: 16
	 * LearningArea
	 */
	@CSVAnnotation(name = "LearningArea", className = String.class)
	@Column(name = "learning_area"  )
	private String learningArea;
	
	/* 
	 * PN: 17
	 * LearningAreaOther
	 */
	@CSVAnnotation(name = "LearningAreaOther", className = String.class)
	@Column(name = "learning_area_other"  )
	private String learningAreaOther;
	
	/* 
	 * PN: 18
	 * Provider
	 */
	@CSVAnnotation(name = "Provider", className = String.class)
	@Column(name = "provider"  )
	private String provider;
	
	/* 
	 * PN: 19
	 * OtherProvider
	 */
	@CSVAnnotation(name = "OtherProvider", className = String.class)
	@Column(name = "other_provider"  )
	private String otherProvider;
	
	/* 
	 * PN: 20
	 * ProviderType
	 */
	@CSVAnnotation(name = "ProviderType", className = String.class)
	@Column(name = "provider_type"  )
	private String ProviderType;
	
	
	/* 
	 * PN: 21
	 * OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class)
	@Column(name = "ofo_Code"  )
	private String ofoCode;
	
	/* 
	 * PN: 22
	 * SkillPriority
	 */
	@CSVAnnotation(name = "SkillPriority", className = String.class)
	@Column(name = "skill_priority"  )
	private String skillPriority;
	
	/* 
	 * PN: 23
	 * NQFLevel
	 */
	@CSVAnnotation(name = "NQFLevel", className = String.class)
	@Column(name = "nqf_Level"  )
	private String nqfLevel;
	
	/* 
	 * PN: 24
	 * FundingStatus
	 */
	@CSVAnnotation(name = "FundingStatus", className = String.class)
	@Column(name = "funding_status"  )
	private String fundingStatus;
	
	/* 
	 * PN: 25
	 * PartnershipSDL
	 */
	@CSVAnnotation(name = "PartnershipSDL", className = String.class)
	@Column(name = "partnership_SDL"  )
	private String partnershipSDL;
	
	/* 
	 * PN: 26
	 * PartnerLegalName
	 */
	@CSVAnnotation(name = "PartnerLegalName", className = String.class)
	@Column(name = "partner_legal_name"  )
	private String partnerLegalName;
	
	/* 
	 * PN: 27
	 * DGTag
	 */
	@CSVAnnotation(name = "DGTag", className = String.class)
	@Column(name = "dg_tag"  )
	private String dgTag;
	
	/* 
	 * PN: 28
	 * Amount
	 */
	@CSVAnnotation(name = "Amount", className = String.class)
	@Column(name = "amount"  )
	private String amount;
	
	/* 
	 * PN: 29
	 * SSPNeedsID
	 */
	@CSVAnnotation(name = "SSPNeedsID", className = String.class)
	@Column(name = "ssp_needs_id"  )
	private String sspNeedsid;
	
	/* 
	 * PN: 30
	 * DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created"  )
	private String dateCreated;
	
	/* 
	 * PN: 31
	 * DateUpdated
	 */
	@CSVAnnotation(name = "DateUpdated", className = String.class)
	@Column(name = "date_updated"  )
	private String dateUpdated;
	
	
	/**
	 * Instantiates a new Accreditation.
	 */
	public Internship() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Internship other = (Internship) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getIDNo() {
		return IDNo;
	}

	public void setIDNo(String iDNo) {
		IDNo = iDNo;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getRegistrationDate() {
		return RegistrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		RegistrationDate = registrationDate;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployerSDL() {
		return EmployerSDL;
	}

	public void setEmployerSDL(String employerSDL) {
		EmployerSDL = employerSDL;
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

	public String getProgrammeType() {
		return ProgrammeType;
	}

	public void setProgrammeType(String programmeType) {
		ProgrammeType = programmeType;
	}

	public String getLearningArea() {
		return learningArea;
	}

	public void setLearningArea(String learningArea) {
		this.learningArea = learningArea;
	}

	public String getLearningAreaOther() {
		return learningAreaOther;
	}

	public void setLearningAreaOther(String learningAreaOther) {
		this.learningAreaOther = learningAreaOther;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOtherProvider() {
		return otherProvider;
	}

	public void setOtherProvider(String otherProvider) {
		this.otherProvider = otherProvider;
	}

	public String getProviderType() {
		return ProviderType;
	}

	public void setProviderType(String providerType) {
		ProviderType = providerType;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getSkillPriority() {
		return skillPriority;
	}

	public void setSkillPriority(String skillPriority) {
		this.skillPriority = skillPriority;
	}

	public String getNqfLevel() {
		return nqfLevel;
	}

	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	public String getFundingStatus() {
		return fundingStatus;
	}

	public void setFundingStatus(String fundingStatus) {
		this.fundingStatus = fundingStatus;
	}

	public String getPartnershipSDL() {
		return partnershipSDL;
	}

	public void setPartnershipSDL(String partnershipSDL) {
		this.partnershipSDL = partnershipSDL;
	}

	public String getPartnerLegalName() {
		return partnerLegalName;
	}

	public void setPartnerLegalName(String partnerLegalName) {
		this.partnerLegalName = partnerLegalName;
	}

	public String getDgTag() {
		return dgTag;
	}

	public void setDgTag(String dgTag) {
		this.dgTag = dgTag;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSspNeedsid() {
		return sspNeedsid;
	}

	public void setSspNeedsid(String sspNeedsid) {
		this.sspNeedsid = sspNeedsid;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	

}
