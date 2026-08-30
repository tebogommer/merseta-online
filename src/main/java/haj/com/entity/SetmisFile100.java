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

import haj.com.annotations.ExtractFilename;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * SetmisFile100.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_100")
@ExtractFilename(fileName = "Setmis File 100")
public class SetmisFile100 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile100. */
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
	
	@Column(name = "provider_etqa_id")
	private String providerETQAId;

	@Column(name = "sic_code")
	private String sICCode;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "provider_type_id")
	private String providerTypeId;
	
	@Column(name = "provider_postal_address_1")
	private String providerPostalAddress1;
	
	@Column(name = "provider_postal_address_2")
	private String providerPostalAddress2;
	
	@Column(name = "provider_postal_address_3")
	private String providerPostalAddress3;
	
	@Column(name = "provider_postal_address_code")
	private String providerPostalAddressCode;
	
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
	
	@Column(name = "provider_start_date")
	private Date providerStartDate;
	
	@Column(name = "provider_end_date")
	private Date providerEndDate;
	
	@Column(name = "etqe_decision_number")
	private String etqeDecisionNumber;
	
	@Column(name = "provider_class_id")
	private String providerClassId;
	
	@Column(name = "provider_status_id")
	private String providerStatusId;
	
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
	
	@Column(name = "provider_physical_address_3")
	private String providerPhysicalAddress3;
	
	@Column(name = "provider_physical_address_code")
	private String providerPhysicalAddressCode;
	
	@Column(name = "provider_web_address")
	private String providerWebAddress;
	
	@Column(name = "sdl_no")
	private String sDLNo;
	
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
		SetmisFile100 other = (SetmisFile100) obj;
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

	public String getProviderETQAId() {
		return providerETQAId;
	}

	public void setProviderETQAId(String providerETQAId) {
		this.providerETQAId = providerETQAId;
	}

	public String getsICCode() {
		return sICCode;
	}

	public void setsICCode(String sICCode) {
		this.sICCode = sICCode;
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

	public String getProviderPostalAddress1() {
		return providerPostalAddress1;
	}

	public void setProviderPostalAddress1(String providerPostalAddress1) {
		this.providerPostalAddress1 = providerPostalAddress1;
	}

	public String getProviderPostalAddress2() {
		return providerPostalAddress2;
	}

	public void setProviderPostalAddress2(String providerPostalAddress2) {
		this.providerPostalAddress2 = providerPostalAddress2;
	}

	public String getProviderPostalAddress3() {
		return providerPostalAddress3;
	}

	public void setProviderPostalAddress3(String providerPostalAddress3) {
		this.providerPostalAddress3 = providerPostalAddress3;
	}

	public String getProviderPostalAddressCode() {
		return providerPostalAddressCode;
	}

	public void setProviderPostalAddressCode(String providerPostalAddressCode) {
		this.providerPostalAddressCode = providerPostalAddressCode;
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

	public Date getProviderStartDate() {
		return providerStartDate;
	}

	public void setProviderStartDate(Date providerStartDate) {
		this.providerStartDate = providerStartDate;
	}

	public Date getProviderEndDate() {
		return providerEndDate;
	}

	public void setProviderEndDate(Date providerEndDate) {
		this.providerEndDate = providerEndDate;
	}

	public String getEtqeDecisionNumber() {
		return etqeDecisionNumber;
	}

	public void setEtqeDecisionNumber(String etqeDecisionNumber) {
		this.etqeDecisionNumber = etqeDecisionNumber;
	}

	public String getProviderClassId() {
		return providerClassId;
	}

	public void setProviderClassId(String providerClassId) {
		this.providerClassId = providerClassId;
	}

	public String getProviderStatusId() {
		return providerStatusId;
	}

	public void setProviderStatusId(String providerStatusId) {
		this.providerStatusId = providerStatusId;
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

	public String getProviderPhysicalAddress3() {
		return providerPhysicalAddress3;
	}

	public void setProviderPhysicalAddress3(String providerPhysicalAddress3) {
		this.providerPhysicalAddress3 = providerPhysicalAddress3;
	}

	public String getProviderPhysicalAddressCode() {
		return providerPhysicalAddressCode;
	}

	public void setProviderPhysicalAddressCode(String providerPhysicalAddressCode) {
		this.providerPhysicalAddressCode = providerPhysicalAddressCode;
	}

	public String getProviderWebAddress() {
		return providerWebAddress;
	}

	public void setProviderWebAddress(String providerWebAddress) {
		this.providerWebAddress = providerWebAddress;
	}

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
