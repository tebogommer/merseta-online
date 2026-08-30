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

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

// TODO: Auto-generated Javadoc
/**
 * QCTOFile01.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qcto_file_01")
public class QCTOFile01 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of QCTOFile01. */
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
	
	@Column(name = "sdp_code")
	private String  sdpCode;
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "learner_alternate_id")
	private String learnerAlternateID;
	
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
	
	@Column(name = "socioeconomic_status_code")
	private String socioeconomicStatusCode;
	
	@Column(name = "disability_status_code")
	private String disabilityStatusCode;
	
	@Column(name = "disability_rating")
	private String disabilityRating;
	
	@Column(name = "immigrant_status")
	private String immigrantStatus;
	
	@Column(name = "learner_last_name")
	private String learnerLastName;
	
	@Column(name = "learner_first_name")
	private String learnerFirstName;
	
	@Column(name = "learner_middle_name")
	private String learnerMiddleName;
	
	@Column(name = "learner_title")
	private String learnerTitle;
	
	@Column(name = "learner_birth_date")
	private Date learnerBirthDate;
	
	@Column(name = "learner_home_address_1")
	private String learnerHomeAddress1;
	
	@Column(name = "learner_home_address_2")
	private String learnerHomeAddress2;
	
	@Column(name = "learner_home_address_3")
	private String learnerHomeAddress3;
	
	@Column(name = "learner_postal_address_1")
	private String learnerPostalAddress1;
	
	@Column(name = "learner_postal_address_2")
	private String learnerPostalAddress2;
	
	@Column(name = "learner_postal_address_3")
	private String learnerPostalAddress3;
	
	@Column(name = "learner_home_address_post_code")
	private String learnerHomeAddressPostCode;
	
	@Column(name = "learner_postal_address_post_code")
	private String learnerPostalAddressPostCode;
	
	@Column(name = "learner_phone_number")
	private String learnerPhoneNumber;
	
	@Column(name = "learner_cell_phone_number")
	private String learnerCellPhoneNumber;
	
	@Column(name = "learner_fax_number")
	private String learnerFaxNumber;
	
	@Column(name = "learner_email_address")
	private String learnerEmailAddress;
	
	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "statssa_area_code")
	private String statssaAreaCode;
	
	@Column(name = "popi_act_agree")
	private String popiActAgree;
	
	@Column(name = "popi_act_date")
	private Date popiActDate;
	
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
		QCTOFile01 other = (QCTOFile01) obj;
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

	public String getSdpCode() {
		return sdpCode;
	}

	public void setSdpCode(String sdpCode) {
		this.sdpCode = sdpCode;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getLearnerAlternateID() {
		return learnerAlternateID;
	}

	public void setLearnerAlternateID(String learnerAlternateID) {
		this.learnerAlternateID = learnerAlternateID;
	}

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
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

	public String getSocioeconomicStatusCode() {
		return socioeconomicStatusCode;
	}

	public void setSocioeconomicStatusCode(String socioeconomicStatusCode) {
		this.socioeconomicStatusCode = socioeconomicStatusCode;
	}

	public String getDisabilityStatusCode() {
		return disabilityStatusCode;
	}

	public void setDisabilityStatusCode(String disabilityStatusCode) {
		this.disabilityStatusCode = disabilityStatusCode;
	}

	public String getDisabilityRating() {
		return disabilityRating;
	}

	public void setDisabilityRating(String disabilityRating) {
		this.disabilityRating = disabilityRating;
	}

	public String getImmigrantStatus() {
		return immigrantStatus;
	}

	public void setImmigrantStatus(String immigrantStatus) {
		this.immigrantStatus = immigrantStatus;
	}

	public String getLearnerLastName() {
		return learnerLastName;
	}

	public void setLearnerLastName(String learnerLastName) {
		this.learnerLastName = learnerLastName;
	}

	public String getLearnerFirstName() {
		return learnerFirstName;
	}

	public void setLearnerFirstName(String learnerFirstName) {
		this.learnerFirstName = learnerFirstName;
	}

	public String getLearnerMiddleName() {
		return learnerMiddleName;
	}

	public void setLearnerMiddleName(String learnerMiddleName) {
		this.learnerMiddleName = learnerMiddleName;
	}

	public String getLearnerTitle() {
		return learnerTitle;
	}

	public void setLearnerTitle(String learnerTitle) {
		this.learnerTitle = learnerTitle;
	}

	public Date getLearnerBirthDate() {
		return learnerBirthDate;
	}

	public void setLearnerBirthDate(Date learnerBirthDate) {
		this.learnerBirthDate = learnerBirthDate;
	}

	public String getLearnerHomeAddress1() {
		return learnerHomeAddress1;
	}

	public void setLearnerHomeAddress1(String learnerHomeAddress1) {
		this.learnerHomeAddress1 = learnerHomeAddress1;
	}

	public String getLearnerHomeAddress2() {
		return learnerHomeAddress2;
	}

	public void setLearnerHomeAddress2(String learnerHomeAddress2) {
		this.learnerHomeAddress2 = learnerHomeAddress2;
	}

	public String getLearnerHomeAddress3() {
		return learnerHomeAddress3;
	}

	public void setLearnerHomeAddress3(String learnerHomeAddress3) {
		this.learnerHomeAddress3 = learnerHomeAddress3;
	}

	public String getLearnerPostalAddress1() {
		return learnerPostalAddress1;
	}

	public void setLearnerPostalAddress1(String learnerPostalAddress1) {
		this.learnerPostalAddress1 = learnerPostalAddress1;
	}

	public String getLearnerPostalAddress2() {
		return learnerPostalAddress2;
	}

	public void setLearnerPostalAddress2(String learnerPostalAddress2) {
		this.learnerPostalAddress2 = learnerPostalAddress2;
	}

	public String getLearnerPostalAddress3() {
		return learnerPostalAddress3;
	}

	public void setLearnerPostalAddress3(String learnerPostalAddress3) {
		this.learnerPostalAddress3 = learnerPostalAddress3;
	}

	public String getLearnerHomeAddressPostCode() {
		return learnerHomeAddressPostCode;
	}

	public void setLearnerHomeAddressPostCode(String learnerHomeAddressPostCode) {
		this.learnerHomeAddressPostCode = learnerHomeAddressPostCode;
	}

	public String getLearnerPostalAddressPostCode() {
		return learnerPostalAddressPostCode;
	}

	public void setLearnerPostalAddressPostCode(String learnerPostalAddressPostCode) {
		this.learnerPostalAddressPostCode = learnerPostalAddressPostCode;
	}

	public String getLearnerPhoneNumber() {
		return learnerPhoneNumber;
	}

	public void setLearnerPhoneNumber(String learnerPhoneNumber) {
		this.learnerPhoneNumber = learnerPhoneNumber;
	}

	public String getLearnerCellPhoneNumber() {
		return learnerCellPhoneNumber;
	}

	public void setLearnerCellPhoneNumber(String learnerCellPhoneNumber) {
		this.learnerCellPhoneNumber = learnerCellPhoneNumber;
	}

	public String getLearnerFaxNumber() {
		return learnerFaxNumber;
	}

	public void setLearnerFaxNumber(String learnerFaxNumber) {
		this.learnerFaxNumber = learnerFaxNumber;
	}

	public String getLearnerEmailAddress() {
		return learnerEmailAddress;
	}

	public void setLearnerEmailAddress(String learnerEmailAddress) {
		this.learnerEmailAddress = learnerEmailAddress;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getStatssaAreaCode() {
		return statssaAreaCode;
	}

	public void setStatssaAreaCode(String statssaAreaCode) {
		this.statssaAreaCode = statssaAreaCode;
	}

	public String getPopiActAgree() {
		return popiActAgree;
	}

	public void setPopiActAgree(String popiActAgree) {
		this.popiActAgree = popiActAgree;
	}

	public Date getPopiActDate() {
		return popiActDate;
	}

	public void setPopiActDate(Date popiActDate) {
		this.popiActDate = popiActDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getEquityCode() {
		return equityCode;
	}

	public void setEquityCode(String equityCode) {
		this.equityCode = equityCode;
	}
}
