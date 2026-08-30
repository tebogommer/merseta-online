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
 * NLRDFile21.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nlrd_file_21")
public class NLRDFile21 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of NLRDFile21. */
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
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "etqa_id")
	private String etqaId;
	
	@Column(name = "std_industry_class_code")
	private String stdIndustryClassCode;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "provider_type_id")
	private String providerTypeId;
	
	@Column(name = "provider_address_1")
	private String providerAddress1;
	
	@Column(name = "provider_address_2")
	private String providerAddress2;
	
	@Column(name = "provider_address_3")
	private String providerAddress3;
	
	@Column(name = "provider_postal_code")
	private String providerPostalCode;
	
	@Column(name = "provider_phone_number")
	private String providerPhoneNumber;
	
	@Column(name = "provider_fax_number")
	private String providerFaxNumber;
	
	@Column(name = "provider_sars_number")
	private String providerSarsNumber;
	
	@Column(name = "provider_contact_name")
	private String providerContactName;
	
	@Column(name = "provider_contact_email_address")
	private String providerContactEmailAddress;
	
	@Column(name = "provider_contact_phone_number")
	private String providerContactPhoneNumber;
	
	@Column(name = "provider_contact_cell_number")
	private String providerContactCellNumber;
	
	@Column(name = "provider_accreditation_num")
	private String providerAccreditationNum;
	
	@Column(name = "provider_accredit_start_date")
	private Date providerAccreditStartDate;
	
	@Column(name = "provider_accredit_end_date")
	private Date providerAccreditEndDate;
	
	@Column(name = "etqa_decision_number")
	private String etqaDecisionNumber;
	
	@Column(name = "provider_etqa_id")
	private String provider_class_id;
	
	@Column(name = "structure_status_id")
	private String structureStatusId;
	
	@Column(name = "province_code")
	private String provinceCode;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "latitude_degree")
	private String latitudeDegree;
	
	@Column(name = "latitude_minutes")
	private String latitudeMinutes;
	
	@Column(name = "latitude_seconds")
	private String latitudeSeconds;
	
	@Column(name = "longitude_degree")
	private String longitudeDegree;
	
	@Column(name = "longitude_minutes")
	private String longitudeMinutes;
	
	@Column(name = "longitude_seconds")
	private String longitudeSeconds;
	
	@Column(name = "provider_physical_address_1")
	private String providerPhysicalAddress1;
	
	@Column(name = "provider_physical_address_2")
	private String providerPhysicalAddress2;
	
	@Column(name = "provider_physical_address_town")
	private String providerPhysicalAddressTown;
	
	@Column(name = "provider_phys_address_post_code")
	private String providerPhysAddressPostcode;
	
	@Column(name = "provider_web_address")
	private String providerWebAddress;
	
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
		NLRDFile21 other = (NLRDFile21) obj;
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

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getEtqaId() {
		return etqaId;
	}

	public void setEtqaId(String etqaId) {
		this.etqaId = etqaId;
	}

	public String getStdIndustryClassCode() {
		return stdIndustryClassCode;
	}

	public void setStdIndustryClassCode(String stdIndustryClassCode) {
		this.stdIndustryClassCode = stdIndustryClassCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderTypeId() {
		return providerTypeId;
	}

	public void setProviderTypeId(String providerTypeId) {
		this.providerTypeId = providerTypeId;
	}

	public String getProviderAddress1() {
		return providerAddress1;
	}

	public void setProviderAddress1(String providerAddress1) {
		this.providerAddress1 = providerAddress1;
	}

	public String getProviderAddress2() {
		return providerAddress2;
	}

	public void setProviderAddress2(String providerAddress2) {
		this.providerAddress2 = providerAddress2;
	}

	public String getProviderAddress3() {
		return providerAddress3;
	}

	public void setProviderAddress3(String providerAddress3) {
		this.providerAddress3 = providerAddress3;
	}

	public String getProviderPostalCode() {
		return providerPostalCode;
	}

	public void setProviderPostalCode(String providerPostalCode) {
		this.providerPostalCode = providerPostalCode;
	}

	public String getProviderPhoneNumber() {
		return providerPhoneNumber;
	}

	public void setProviderPhoneNumber(String providerPhoneNumber) {
		this.providerPhoneNumber = providerPhoneNumber;
	}

	public String getProviderFaxNumber() {
		return providerFaxNumber;
	}

	public void setProviderFaxNumber(String providerFaxNumber) {
		this.providerFaxNumber = providerFaxNumber;
	}

	public String getProviderSarsNumber() {
		return providerSarsNumber;
	}

	public void setProviderSarsNumber(String providerSarsNumber) {
		this.providerSarsNumber = providerSarsNumber;
	}

	public String getProviderContactName() {
		return providerContactName;
	}

	public void setProviderContactName(String providerContactName) {
		this.providerContactName = providerContactName;
	}

	public String getProviderContactEmailAddress() {
		return providerContactEmailAddress;
	}

	public void setProviderContactEmailAddress(String providerContactEmailAddress) {
		this.providerContactEmailAddress = providerContactEmailAddress;
	}

	public String getProviderContactPhoneNumber() {
		return providerContactPhoneNumber;
	}

	public void setProviderContactPhoneNumber(String providerContactPhoneNumber) {
		this.providerContactPhoneNumber = providerContactPhoneNumber;
	}

	public String getProviderContactCellNumber() {
		return providerContactCellNumber;
	}

	public void setProviderContactCellNumber(String providerContactCellNumber) {
		this.providerContactCellNumber = providerContactCellNumber;
	}

	public String getProviderAccreditationNum() {
		return providerAccreditationNum;
	}

	public void setProviderAccreditationNum(String providerAccreditationNum) {
		this.providerAccreditationNum = providerAccreditationNum;
	}

	public Date getProviderAccreditStartDate() {
		return providerAccreditStartDate;
	}

	public void setProviderAccreditStartDate(Date providerAccreditStartDate) {
		this.providerAccreditStartDate = providerAccreditStartDate;
	}

	public Date getProviderAccreditEndDate() {
		return providerAccreditEndDate;
	}

	public void setProviderAccreditEndDate(Date providerAccreditEndDate) {
		this.providerAccreditEndDate = providerAccreditEndDate;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getProvider_class_id() {
		return provider_class_id;
	}

	public void setProvider_class_id(String provider_class_id) {
		this.provider_class_id = provider_class_id;
	}

	public String getStructureStatusId() {
		return structureStatusId;
	}

	public void setStructureStatusId(String structureStatusId) {
		this.structureStatusId = structureStatusId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLatitudeDegree() {
		return latitudeDegree;
	}

	public void setLatitudeDegree(String latitudeDegree) {
		this.latitudeDegree = latitudeDegree;
	}

	public String getLatitudeMinutes() {
		return latitudeMinutes;
	}

	public void setLatitudeMinutes(String latitudeMinutes) {
		this.latitudeMinutes = latitudeMinutes;
	}

	public String getLatitudeSeconds() {
		return latitudeSeconds;
	}

	public void setLatitudeSeconds(String latitudeSeconds) {
		this.latitudeSeconds = latitudeSeconds;
	}

	public String getLongitudeDegree() {
		return longitudeDegree;
	}

	public void setLongitudeDegree(String longitudeDegree) {
		this.longitudeDegree = longitudeDegree;
	}

	public String getLongitudeMinutes() {
		return longitudeMinutes;
	}

	public void setLongitudeMinutes(String longitudeMinutes) {
		this.longitudeMinutes = longitudeMinutes;
	}

	public String getLongitudeSeconds() {
		return longitudeSeconds;
	}

	public void setLongitudeSeconds(String longitudeSeconds) {
		this.longitudeSeconds = longitudeSeconds;
	}

	public String getProviderPhysicalAddress1() {
		return providerPhysicalAddress1;
	}

	public void setProviderPhysicalAddress1(String providerPhysicalAddress1) {
		this.providerPhysicalAddress1 = providerPhysicalAddress1;
	}

	public String getProviderPhysicalAddress2() {
		return providerPhysicalAddress2;
	}

	public void setProviderPhysicalAddress2(String providerPhysicalAddress2) {
		this.providerPhysicalAddress2 = providerPhysicalAddress2;
	}

	public String getProviderPhysicalAddressTown() {
		return providerPhysicalAddressTown;
	}

	public void setProviderPhysicalAddressTown(String providerPhysicalAddressTown) {
		this.providerPhysicalAddressTown = providerPhysicalAddressTown;
	}

	public String getProviderPhysAddressPostcode() {
		return providerPhysAddressPostcode;
	}

	public void setProviderPhysAddressPostcode(String providerPhysAddressPostcode) {
		this.providerPhysAddressPostcode = providerPhysAddressPostcode;
	}

	public String getProviderWebAddress() {
		return providerWebAddress;
	}

	public void setProviderWebAddress(String providerWebAddress) {
		this.providerWebAddress = providerWebAddress;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
