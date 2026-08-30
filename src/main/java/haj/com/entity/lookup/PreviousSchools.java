package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "previous_schools")
@AuditTable(value = "previous_schools_hist")
@Audited
public class PreviousSchools extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1
	 * NatEmis 
	 */
	@CSVAnnotation(name = "NatEmis", className = String.class)
	@Column(name = "nat_emis")
	private String natEmis;
	
	/* 
	 * PN: 2
	 * DataYear
	 */
	@CSVAnnotation(name = "Datayear", className = String.class)
	@Column(name = "data_year")
	private String dataYear;
	
	/* 
	 * PN: 3
	 * ProvinceCD
	 */
	@CSVAnnotation(name = "ProvinceCD", className = String.class)
	@Column(name = "province_cd")
	private String provinceCD;
	
	/*
	 * PN: 4
	 * Province
	 */
	@CSVAnnotation(name = "Province", className = String.class)
	@Column(name = "province")
	private String province;
	
	/*
	 * PN: 5
	 * Official_Institution_Name
	 */
	@CSVAnnotation(name = "Official_Institution_Name", className = String.class)
	@Column(name = "official_institution_name")
	private String officialInstitutionName;
	
	/*
	 * PN: 6
	 * Status
	 */
	@CSVAnnotation(name = "Status", className = String.class)
	@Column(name = "status")
	private String status;
	
	/*
	 * PN: 7
	 * Sector
	 */
	@CSVAnnotation(name = "Sector", className = String.class)
	@Column(name = "sector")
	private String sector;
	
	/*
	 * PN: 8
	 * Type_DoE
	 */
	@CSVAnnotation(name = "Type_DoE", className = String.class)
	@Column(name = "type_doe")
	private String typeDoE;
	
	/*
	 * PN: 9
	 * Phase_PED
	 */
	@CSVAnnotation(name = "Phase_PED", className = String.class)
	@Column(name = "phase_ped")
	private String phasePED;
	
	/*
	 * PN: 10
	 * Specialization
	 */
	@CSVAnnotation(name = "Specialization", className = String.class)
	@Column(name = "specialization")
	private String specialization;
	
	/*
	 * PN: 11
	 * OwnerLand
	 */
	@CSVAnnotation(name = "OwnerLand", className = String.class)
	@Column(name = "ownerLand")
	private String ownerLand;
	
	/*
	 * PN: 12
	 * OwnerBuildings
	 */
	@CSVAnnotation(name = "OwnerBuildings", className = String.class)
	@Column(name = "owner_buildings")
	private String ownerBuildings;
	
	/*
	 * PN: 13
	 * ExDept
	 */
	@CSVAnnotation(name = "ExDept", className = String.class)
	@Column(name = "ex_dept")
	private String exDept;
	
	/*
	 * PN: 14
	 * PaypointNo
	 */
	@CSVAnnotation(name = "PaypointNo", className = String.class)
	@Column(name = "pay_point_no")
	private String paypointNo;
	
	/*
	 * PN: 15
	 * ComponentNo
	 */
	@CSVAnnotation(name = "ComponentNo", className = String.class)
	@Column(name = "component_no")
	private String componentNo;
	
	/*
	 * PN: 16
	 * ExamNo
	 */
	@CSVAnnotation(name = "ExamNo", className = String.class)
	@Column(name = "exam_no")
	private String examNo;
	
	/*
	 * PN: 17
	 * ExamCentre
	 */
	@CSVAnnotation(name = "ExamCentre", className = String.class)
	@Column(name = "exam_centre")
	private String examCentre;
	
	/*
	 * PN: 18
	 * GIS_Longitude
	 */
	@CSVAnnotation(name = "GIS_Longitude", className = String.class)
	@Column(name = "gis_longitude")
	private String gisLongitude;
	
	/*
	 * PN: 19
	 * GIS_Latitude
	 */
	@CSVAnnotation(name = "GIS_Latitude", className = String.class)
	@Column(name = "gis_latitude")
	private String gisLatitude;
	
	/*
	 * PN: 20
	 * GISSource
	 */
	@CSVAnnotation(name = "GISSource", className = String.class)
	@Column(name = "gis_source")
	private String gisSource;
	
	/*
	 * PN: 21
	 * DMunName
	 */
	@CSVAnnotation(name = "DMunName", className = String.class)
	@Column(name = "dm_un_name")
	private String dMunName;
	
	/*
	 * PN: 22
	 * LMunName
	 */
	@CSVAnnotation(name = "LMunName", className = String.class)
	@Column(name = "lm_un_name")
	private String lMunName;
	
	/*
	 * PN: 23
	 * Ward_ID
	 */
	@CSVAnnotation(name = "Ward_ID", className = String.class)
	@Column(name = "ward_id")
	private String wardID;
	
	/*
	 * PN: 24
	 * SP_Code
	 */
	@CSVAnnotation(name = "SP_Code", className = String.class)
	@Column(name = "sp_code")
	private String spCode;
	
	/*
	 * PN: 25
	 * SP_Name
	 */
	@CSVAnnotation(name = "SP_Name", className = String.class)
	@Column(name = "sp_name")
	private String spName;
	
	/*
	 * PN: 26
	 * EIRegion
	 */
	@CSVAnnotation(name = "EIRegion", className = String.class)
	@Column(name = "ei_region")
	private String eiRegion;
	
	/*
	 * PN: 27
	 * EIDistrict
	 */
	@CSVAnnotation(name = "EIDistrict", className = String.class)
	@Column(name = "ei_district")
	private String eiDistrict;
	
	/*
	 * PN: 28
	 * EICircuit
	 */
	@CSVAnnotation(name = "EICircuit", className = String.class)
	@Column(name = "ei_circuit")
	private String eiCircuit;
	
	/*
	 * PN: 29
	 * AddrInit
	 */
	@CSVAnnotation(name = "AddrInit", className = String.class)
	@Column(name = "addr_init")
	private String addrInit;
	
	/*
	 * PN: 30
	 * Addressee
	 */
	@CSVAnnotation(name = "Addressee", className = String.class)
	@Column(name = "addressee")
	private String addressee;
	
	/*
	 * PN: 31
	 * Township_Village
	 */
	@CSVAnnotation(name = "Township_Village", className = String.class)
	@Column(name = "township_village")
	private String townshipVillage;
	
	/*
	 * PN: 32
	 * Suburb
	 */
	@CSVAnnotation(name = "Suburb", className = String.class)
	@Column(name = "suburb")
	private String suburb;
	
	/*
	 * PN: 33
	 * TownCity
	 */
	@CSVAnnotation(name = "TownCity", className = String.class)
	@Column(name = "town_city")
	private String townCity;
	
	/*
	 * PN: 34
	 * StreetAddress
	 */
	@CSVAnnotation(name = "StreetAddress", className = String.class)
	@Column(name = "street_address")
	private String streetAddress;
	
	/*
	 * PN: 35
	 * PostalAddress
	 */
	@CSVAnnotation(name = "PostalAddress", className = String.class)
	@Column(name = "postal_address")
	private String postalAddress;
	
	/*
	 * PN: 36
	 * Telephone
	 */
	@CSVAnnotation(name = "Telephone", className = String.class)
	@Column(name = "telephone")
	private String telephone;
	
	/*
	 * PN: 37
	 * Facsimile
	 */
	@CSVAnnotation(name = "Facsimile", className = String.class)
	@Column(name = "facsimile")
	private String facsimile;
	
	/*
	 * PN: 38
	 * Section21
	 */
	@CSVAnnotation(name = "Section21", className = String.class)
	@Column(name = "section_twenty_one")
	private String sectionTwentyOne;
	
	/*
	 * PN: 39
	 * Section21_Funct
	 */
	@CSVAnnotation(name = "Section21_Funct", className = String.class)
	@Column(name = "section_twenty_one_funct")
	private String sectionTwentyOneFunct;
	
	/*
	 * PN: 40
	 * Quintile
	 */
	@CSVAnnotation(name = "Quintile", className = String.class)
	@Column(name = "quintile")
	private String quintile;
	
	/*
	 * PN: 41
	 * NAS
	 */
	@CSVAnnotation(name = "NAS", className = String.class)
	@Column(name = "nas")
	private String nas;
	
	/*
	 * PN: 42
	 * NodalArea
	 */
	@CSVAnnotation(name = "NodalArea", className = String.class)
	@Column(name = "nodal_area")
	private String nodalArea;
	
	/*
	 * PN: 43
	 * RegistrationDate
	 */
	@CSVAnnotation(name = "RegistrationDate", className = String.class)
	@Column(name = "registration_date")
	private String registrationDate;
	
	/*
	 * PN: 44
	 * NoFeeSchool
	 */
	@CSVAnnotation(name = "NoFeeSchool", className = String.class)
	@Column(name = "no_fee_school")
	private String noFeeSchool;
	
	/*
	 * PN: 45
	 * Allocation
	 */
	@CSVAnnotation(name = "Allocation", className = String.class)
	@Column(name = "allocation")
	private String allocation;
	
	/*
	 * PN: 46
	 * DemarcationFrom
	 */
	@CSVAnnotation(name = "DemarcationFrom", className = String.class)
	@Column(name = "demarcation_from")
	private String demarcationFrom;
	
	/*
	 * PN: 47
	 * DemarcationTo
	 */
	@CSVAnnotation(name = "DemarcationTo", className = String.class)
	@Column(name = "demarcation_to")
	private String demarcationTo;
	
	/*
	 * PN: 48
	 * OldNATEMIS
	 */
	@CSVAnnotation(name = "OldNATEMIS", className = String.class)
	@Column(name = "old_natemis")
	private String oldNATEMIS;
	
	/*
	 * PN: 49
	 * NewNATEMIS
	 */
	@CSVAnnotation(name = "NewNATEMIS", className = String.class)
	@Column(name = "new_natemis")
	private String newNATEMIS;
	
	/*
	 * PN: 50
	 * Urban_Rural
	 */
	@CSVAnnotation(name = "Urban_Rural", className = String.class)
	@Column(name = "urban_rural")
	private String urbanRural;
	
	/*
	 * PN: 51
	 * Learners_2018
	 */
	@CSVAnnotation(name = "Learners_2018", className = String.class)
	@Column(name = "learners_twenty_eighteen")
	private String learners2018;
	
	/*
	 * PN: 52
	 * Educator_2018
	 */
	@CSVAnnotation(name = "Educator_2018", className = String.class)
	@Column(name = "educator_twenty_eighteen")
	private String educator2018;
	
	public PreviousSchools() {
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
		PreviousSchools other = (PreviousSchools) obj;
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

	public String getNatEmis() {
		return natEmis;
	}

	public void setNatEmis(String natEmis) {
		this.natEmis = natEmis;
	}

	public String getDataYear() {
		return dataYear;
	}

	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}

	public String getProvinceCD() {
		return provinceCD;
	}

	public void setProvinceCD(String provinceCD) {
		this.provinceCD = provinceCD;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOfficialInstitutionName() {
		return officialInstitutionName;
	}

	public void setOfficialInstitutionName(String officialInstitutionName) {
		this.officialInstitutionName = officialInstitutionName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getTypeDoE() {
		return typeDoE;
	}

	public void setTypeDoE(String typeDoE) {
		this.typeDoE = typeDoE;
	}

	public String getPhasePED() {
		return phasePED;
	}

	public void setPhasePED(String phasePED) {
		this.phasePED = phasePED;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getOwnerLand() {
		return ownerLand;
	}

	public void setOwnerLand(String ownerLand) {
		this.ownerLand = ownerLand;
	}

	public String getOwnerBuildings() {
		return ownerBuildings;
	}

	public void setOwnerBuildings(String ownerBuildings) {
		this.ownerBuildings = ownerBuildings;
	}

	public String getExDept() {
		return exDept;
	}

	public void setExDept(String exDept) {
		this.exDept = exDept;
	}

	public String getPaypointNo() {
		return paypointNo;
	}

	public void setPaypointNo(String paypointNo) {
		this.paypointNo = paypointNo;
	}

	public String getComponentNo() {
		return componentNo;
	}

	public void setComponentNo(String componentNo) {
		this.componentNo = componentNo;
	}

	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}

	public String getExamCentre() {
		return examCentre;
	}

	public void setExamCentre(String examCentre) {
		this.examCentre = examCentre;
	}

	public String getGisLongitude() {
		return gisLongitude;
	}

	public void setGisLongitude(String gisLongitude) {
		this.gisLongitude = gisLongitude;
	}

	public String getGisLatitude() {
		return gisLatitude;
	}

	public void setGisLatitude(String gisLatitude) {
		this.gisLatitude = gisLatitude;
	}

	public String getGisSource() {
		return gisSource;
	}

	public void setGisSource(String gisSource) {
		this.gisSource = gisSource;
	}

	public String getdMunName() {
		return dMunName;
	}

	public void setdMunName(String dMunName) {
		this.dMunName = dMunName;
	}

	public String getlMunName() {
		return lMunName;
	}

	public void setlMunName(String lMunName) {
		this.lMunName = lMunName;
	}

	public String getWardID() {
		return wardID;
	}

	public void setWardID(String wardID) {
		this.wardID = wardID;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getEiRegion() {
		return eiRegion;
	}

	public void setEiRegion(String eiRegion) {
		this.eiRegion = eiRegion;
	}

	public String getEiDistrict() {
		return eiDistrict;
	}

	public void setEiDistrict(String eiDistrict) {
		this.eiDistrict = eiDistrict;
	}

	public String getEiCircuit() {
		return eiCircuit;
	}

	public void setEiCircuit(String eiCircuit) {
		this.eiCircuit = eiCircuit;
	}

	public String getAddrInit() {
		return addrInit;
	}

	public void setAddrInit(String addrInit) {
		this.addrInit = addrInit;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getTownshipVillage() {
		return townshipVillage;
	}

	public void setTownshipVillage(String townshipVillage) {
		this.townshipVillage = townshipVillage;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getTownCity() {
		return townCity;
	}

	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFacsimile() {
		return facsimile;
	}

	public void setFacsimile(String facsimile) {
		this.facsimile = facsimile;
	}

	public String getSectionTwentyOne() {
		return sectionTwentyOne;
	}

	public void setSectionTwentyOne(String sectionTwentyOne) {
		this.sectionTwentyOne = sectionTwentyOne;
	}

	public String getSectionTwentyOneFunct() {
		return sectionTwentyOneFunct;
	}

	public void setSectionTwentyOneFunct(String sectionTwentyOneFunct) {
		this.sectionTwentyOneFunct = sectionTwentyOneFunct;
	}

	public String getQuintile() {
		return quintile;
	}

	public void setQuintile(String quintile) {
		this.quintile = quintile;
	}

	public String getNas() {
		return nas;
	}

	public void setNas(String nas) {
		this.nas = nas;
	}

	public String getNodalArea() {
		return nodalArea;
	}

	public void setNodalArea(String nodalArea) {
		this.nodalArea = nodalArea;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getNoFeeSchool() {
		return noFeeSchool;
	}

	public void setNoFeeSchool(String noFeeSchool) {
		this.noFeeSchool = noFeeSchool;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getDemarcationFrom() {
		return demarcationFrom;
	}

	public void setDemarcationFrom(String demarcationFrom) {
		this.demarcationFrom = demarcationFrom;
	}

	public String getDemarcationTo() {
		return demarcationTo;
	}

	public void setDemarcationTo(String demarcationTo) {
		this.demarcationTo = demarcationTo;
	}

	public String getOldNATEMIS() {
		return oldNATEMIS;
	}

	public void setOldNATEMIS(String oldNATEMIS) {
		this.oldNATEMIS = oldNATEMIS;
	}

	public String getNewNATEMIS() {
		return newNATEMIS;
	}

	public void setNewNATEMIS(String newNATEMIS) {
		this.newNATEMIS = newNATEMIS;
	}

	public String getUrbanRural() {
		return urbanRural;
	}

	public void setUrbanRural(String urbanRural) {
		this.urbanRural = urbanRural;
	}

	public String getLearners2018() {
		return learners2018;
	}

	public void setLearners2018(String learners2018) {
		this.learners2018 = learners2018;
	}

	public String getEducator2018() {
		return educator2018;
	}

	public void setEducator2018(String educator2018) {
		this.educator2018 = educator2018;
	}

}
