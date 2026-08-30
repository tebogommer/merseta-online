package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * SetmisFile400.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_400")
public class SetmisFile400 implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile400. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "person_alternate_id")
	private String personAlternateId;

	@Column(name = "alternative_id_type")
	private String alternativeIdType;
	
	@Column(name = "equity_code")
	private String equityCode;
	
	@Column(name = "nationality_code")
	private String nationalityCode;
	
	@Column(name = "home_language_code")
	private String homeLanguageCode;
	
	@Column(name = "gender_code")
	private String genderCode;
	
	@Column(name = "citizen_resident_status_code")
	private String citizenResidentStatusCode;
	
	@Column(name = "filler_01")
	private String filler01;

	@Column(name = "filler_02")
	private String filler02;
	
	@Column(name = "person_last_name")
	private String personLastName;
	
	@Column(name = "person_first_name")
	private String personFirstName;
	
	@Column(name = "person_middle_name")
	private String personMiddleName;
	
	@Column(name = "person_title")
	private String personTitle;
	
	@Column(name = "person_birth_date")
	private Date personBirthDate;
	
	@Column(name = "person_home_address_1")
	private String personHomeAddress1;
	
	@Column(name = "person_home_address_2")
	private String personHomeAddress2;
	
	@Column(name = "person_home_address_3")
	private String personHomeAddress3;
	
	@Column(name = "person_postal_address_1")
	private String personPostalAddress1;
	
	@Column(name = "person_postal_address_2")
	private String personPostalAddress2;
	
	@Column(name = "person_postal_address_3")
	private String personPostalAddress3;
	
	@Column(name = "person_home_addr_postal_code")
	private String personHomeAddrPostalCode;
	
	@Column(name = "person_postal_addr_postal_code")
	private String personPostalAddrPostalCode;
	
	@Column(name = "person_phone_number")
	private String personPhoneNumber;
	
	@Column(name = "person_cell_phone_number")
	private String personCellPhoneNumber;
	
	@Column(name = "person_fax_number")
	private String personFaxNumber;
	
	@Column(name = "person_email_address")
	private String personEmailAddress;
	
	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "person_previous_last_name")
	private String personPreviousLastName;
	
	@Column(name = "person_previous_alternate_id")
	private String personPreviousAlternateId;
	
	@Column(name = "person_previous_alternate_id_type")
	private String personPreviousAlternativeIdType;
	
	@Column(name = "person_previous_provider_code")
	private String personPreviousProviderCode;
	
	@Column(name = "person_previous_provider_etqe_id")
	private String personPreviousProviderETQEId;
	
	@Column(name = "seeing_rating_id")
	private String seeingRatingId;
	
	@Column(name = "hearing_rating_id")
	private String hearingRatingId;
	
	@Column(name = "walking_rating_id")
	private String walkingRatingId;
	
	@Column(name = "remembering_rating_id")
	private String rememberingRatingId;
	
	@Column(name = "communicating_rating_id")
	private String communicatingRatingId;
	
	@Column(name = "selfcare_rating_id")
	private String selfcareRatingId;
	
	@Column(name = "last_school_emis_no")
	private String lastSchoolEMISNo;
	
	@Column(name = "last_school_year")
	private String lastSchoolYear;
	
	@Column(name = "statssa_area_code")
	private String sTATSSAAreaCode;
	
	@Column(name = "popi_act_status_id")
	private String pOPIActStatusID;
	
	@Column(name = "popi_act_status_date")
	private Date pOPIActStatusDate;
	
	@Column(name = "date_stamp")
	private Date dateStamp;
    
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
		SetmisFile400 other = (SetmisFile400) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

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

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPersonAlternateId() {
		return personAlternateId;
	}

	public void setPersonAlternateId(String personAlternateId) {
		this.personAlternateId = personAlternateId;
	}

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}

	public String getEquityCode() {
		return equityCode;
	}

	public void setEquityCode(String equityCode) {
		this.equityCode = equityCode;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public String getHomeLanguageCode() {
		return homeLanguageCode;
	}

	public void setHomeLanguageCode(String homeLanguageCode) {
		this.homeLanguageCode = homeLanguageCode;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getCitizenResidentStatusCode() {
		return citizenResidentStatusCode;
	}

	public void setCitizenResidentStatusCode(String citizenResidentStatusCode) {
		this.citizenResidentStatusCode = citizenResidentStatusCode;
	}

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
	}

	public String getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}

	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	public String getPersonMiddleName() {
		return personMiddleName;
	}

	public void setPersonMiddleName(String personMiddleName) {
		this.personMiddleName = personMiddleName;
	}

	public String getPersonTitle() {
		return personTitle;
	}

	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}

	public Date getPersonBirthDate() {
		return personBirthDate;
	}

	public void setPersonBirthDate(Date personBirthDate) {
		this.personBirthDate = personBirthDate;
	}

	public String getPersonHomeAddress1() {
		return personHomeAddress1;
	}

	public void setPersonHomeAddress1(String personHomeAddress1) {
		this.personHomeAddress1 = personHomeAddress1;
	}

	public String getPersonHomeAddress2() {
		return personHomeAddress2;
	}

	public void setPersonHomeAddress2(String personHomeAddress2) {
		this.personHomeAddress2 = personHomeAddress2;
	}

	public String getPersonHomeAddress3() {
		return personHomeAddress3;
	}

	public void setPersonHomeAddress3(String personHomeAddress3) {
		this.personHomeAddress3 = personHomeAddress3;
	}

	public String getPersonPostalAddress1() {
		return personPostalAddress1;
	}

	public void setPersonPostalAddress1(String personPostalAddress1) {
		this.personPostalAddress1 = personPostalAddress1;
	}

	public String getPersonPostalAddress2() {
		return personPostalAddress2;
	}

	public void setPersonPostalAddress2(String personPostalAddress2) {
		this.personPostalAddress2 = personPostalAddress2;
	}

	public String getPersonPostalAddress3() {
		return personPostalAddress3;
	}

	public void setPersonPostalAddress3(String personPostalAddress3) {
		this.personPostalAddress3 = personPostalAddress3;
	}

	public String getPersonHomeAddrPostalCode() {
		return personHomeAddrPostalCode;
	}

	public void setPersonHomeAddrPostalCode(String personHomeAddrPostalCode) {
		this.personHomeAddrPostalCode = personHomeAddrPostalCode;
	}

	public String getPersonPostalAddrPostalCode() {
		return personPostalAddrPostalCode;
	}

	public void setPersonPostalAddrPostalCode(String personPostalAddrPostalCode) {
		this.personPostalAddrPostalCode = personPostalAddrPostalCode;
	}

	public String getPersonPhoneNumber() {
		return personPhoneNumber;
	}

	public void setPersonPhoneNumber(String personPhoneNumber) {
		this.personPhoneNumber = personPhoneNumber;
	}

	public String getPersonCellPhoneNumber() {
		return personCellPhoneNumber;
	}

	public void setPersonCellPhoneNumber(String personCellPhoneNumber) {
		this.personCellPhoneNumber = personCellPhoneNumber;
	}

	public String getPersonFaxNumber() {
		return personFaxNumber;
	}

	public void setPersonFaxNumber(String personFaxNumber) {
		this.personFaxNumber = personFaxNumber;
	}

	public String getPersonEmailAddress() {
		return personEmailAddress;
	}

	public void setPersonEmailAddress(String personEmailAddress) {
		this.personEmailAddress = personEmailAddress;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderETQEId() {
		return providerETQEId;
	}

	public void setProviderETQEId(String providerETQEId) {
		this.providerETQEId = providerETQEId;
	}

	public String getPersonPreviousLastName() {
		return personPreviousLastName;
	}

	public void setPersonPreviousLastName(String personPreviousLastName) {
		this.personPreviousLastName = personPreviousLastName;
	}

	public String getPersonPreviousAlternateId() {
		return personPreviousAlternateId;
	}

	public void setPersonPreviousAlternateId(String personPreviousAlternateId) {
		this.personPreviousAlternateId = personPreviousAlternateId;
	}

	public String getPersonPreviousAlternativeIdType() {
		return personPreviousAlternativeIdType;
	}

	public void setPersonPreviousAlternativeIdType(String personPreviousAlternativeIdType) {
		this.personPreviousAlternativeIdType = personPreviousAlternativeIdType;
	}

	public String getPersonPreviousProviderCode() {
		return personPreviousProviderCode;
	}

	public void setPersonPreviousProviderCode(String personPreviousProviderCode) {
		this.personPreviousProviderCode = personPreviousProviderCode;
	}

	public String getPersonPreviousProviderETQEId() {
		return personPreviousProviderETQEId;
	}

	public void setPersonPreviousProviderETQEId(String personPreviousProviderETQEId) {
		this.personPreviousProviderETQEId = personPreviousProviderETQEId;
	}

	public String getSeeingRatingId() {
		return seeingRatingId;
	}

	public void setSeeingRatingId(String seeingRatingId) {
		this.seeingRatingId = seeingRatingId;
	}

	public String getHearingRatingId() {
		return hearingRatingId;
	}

	public void setHearingRatingId(String hearingRatingId) {
		this.hearingRatingId = hearingRatingId;
	}

	public String getWalkingRatingId() {
		return walkingRatingId;
	}

	public void setWalkingRatingId(String walkingRatingId) {
		this.walkingRatingId = walkingRatingId;
	}

	public String getRememberingRatingId() {
		return rememberingRatingId;
	}

	public void setRememberingRatingId(String rememberingRatingId) {
		this.rememberingRatingId = rememberingRatingId;
	}

	public String getCommunicatingRatingId() {
		return communicatingRatingId;
	}

	public void setCommunicatingRatingId(String communicatingRatingId) {
		this.communicatingRatingId = communicatingRatingId;
	}

	public String getSelfcareRatingId() {
		return selfcareRatingId;
	}

	public void setSelfcareRatingId(String selfcareRatingId) {
		this.selfcareRatingId = selfcareRatingId;
	}

	public String getLastSchoolEMISNo() {
		return lastSchoolEMISNo;
	}

	public void setLastSchoolEMISNo(String lastSchoolEMISNo) {
		this.lastSchoolEMISNo = lastSchoolEMISNo;
	}

	public String getLastSchoolYear() {
		return lastSchoolYear;
	}

	public void setLastSchoolYear(String lastSchoolYear) {
		this.lastSchoolYear = lastSchoolYear;
	}

	public String getsTATSSAAreaCode() {
		return sTATSSAAreaCode;
	}

	public void setsTATSSAAreaCode(String sTATSSAAreaCode) {
		this.sTATSSAAreaCode = sTATSSAAreaCode;
	}

	public String getpOPIActStatusID() {
		return pOPIActStatusID;
	}

	public void setpOPIActStatusID(String pOPIActStatusID) {
		this.pOPIActStatusID = pOPIActStatusID;
	}

	public Date getpOPIActStatusDate() {
		return pOPIActStatusDate;
	}

	public void setpOPIActStatusDate(Date pOPIActStatusDate) {
		this.pOPIActStatusDate = pOPIActStatusDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
