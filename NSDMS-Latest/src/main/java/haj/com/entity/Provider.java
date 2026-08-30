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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import haj.com.framework.IDataEntity;

@Entity
@Table(name = "provider")
public class Provider implements IDataEntity {

	private static final long serialVersionUID = 1L;
	/** 
	 * Unique Id of Provider. 
	 * Id can't be null
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** 
	 * Create Date of Provider. 
	 * Create Date length can't be more than 19 characters.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** 
	 * The Provider Code. 
	 * This field is required
	 * Must be unique
	 * Field maximum length = 20
	 * May not start with space 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._-
	 */
	// Field Name	Type	Size	Position	Require	Source
	// Provider_Code TEXT 20 1 Y
	@Column(length = 20, nullable = false)
	@Size(min = 1, max = 20, message = "Provider Code can't be less than 1 character or more than 20 characters")
	@NotEmpty(message ="Provider Code is required")
	private String providerCode;


	/** 
	 * Etqa Id
	 * This field is required
	 * Must be unique
	 * Field max length = 10
	 * May not start with space
	 * May only contain whole numbers
	 * Must contain a valid Provider_ETQE_Id
	 */
	// Etqa_Id NUMBER 10 21 Y T
	//private Etqa etqa;
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 10, message = "Etqa Id can't be less than 1 character or more than 10 characters")
	@NotEmpty(message ="Etqa Id is required")
	private Long etqaId;

	/** 
	 * The std Industry Class Code. 
	 * This field is not required
	 * Field maximum length = 10
	 * May not start with space
	 * May only contain whole numbers
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'-
	 * Field must contain a valid SIC_Code
	 */
	// Std_Industry_Class_Code TEXT 10 31 N T
	//private StdIndustryClass;
	@Column(length = 10, nullable = true)
	@Size(min = 1, max = 10, message = "The Std Industry Class Code can't be less than 1 character or more than 10 characters")
	private String stdIndustryClassCode;

	/** 
	 * The Provider name. 
	 * This field is required
	 * Field maximum length = 70
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or  or 
	 * May not contain duplicated values once characters like PTY,LTD,CC , CC,.,&,+, AND ,T/A,BK,LIMITED, CK,_, ,),( have been stripped from the value
	 */
	// Provider_Name TEXT 128 41 Y
	@Column(length = 70, nullable = false)
	@Size(min = 1, max = 70, message = "Provider name can't be less than 1 character or more than 70 characters")
	@NotEmpty(message ="Provider name is required")
	private String providerName;

	// Look up table
	// Provider_Type_Id NUMBER 10 169 Y L
	// private ProviderType providerType;

	// Provider_Address_1 TEXT 50 179 Y
	// Provider_Address_2 TEXT 50 229 Y
	// Provider_Address_3 TEXT 50 279 N
	// Provider_Postal_Code TEXT 4 329 Y
	private Address providerAddress;

	// Provider_Phone_Number TEXT 20 333 N
	/** 
	 * The Provider phone number. 
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890 ()/-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Phone number can't be less than 1 character or more than 10 characters")
	private String providerPhoneNumber;

	// Provider_Fax_Number TEXT 20 353 N
	/** 
	 * The Provider phone number. 
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890 ()/-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Fax number can't be less than 1 character or more than 10 characters")
	private String providerFaxNumber;

	// Provider_Sars_Number TEXT 20 373 N
	/** 
	 * The Provider SARS number. 
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._-
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "SARS number can't be less than 1 character or more than 10 characters")
	private String providerSarsNumber;

	// Provider_Contact_Name TEXT 50 393 N
	/** 
	 * The Provider Contact Name
	 * This filed is not required
	 * Field maximum length = 50
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or  or NA or U or NONE or GREEN
	 */
	@Column(length = 50, nullable = true)
	@Size(min = 1, max = 50, message = "The Provider Contact Name can't be less than 1 character or more than 50 characters")
	private String providerContactName;

	// Provider_Contact_Email_Address TEXT 50 443 N
	/** 
	 * The Provider Contact Email Address
	 * This filed is not required
	 * Field maximum length = 50
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must contain the @ character
	 */
	@Column(length = 50, nullable = true)
	@Size(min = 1, max = 50, message = "Provider Contact Email Address can't be less than 1 character or more than 50 characters")
	private String providerContactEmailAddress;

	// Provider_Contact_Phone_Number TEXT 20 493 N
	/** 
	 * The Provider Contact phone number	 
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890 ()/-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Provider Contact phone number can't be less than 1 character or more than 20 characters")
	private String providerContactPhoneNumber;

	// Provider_Contact_Cell_Number TEXT 20 513 N
	/** 
	 * The Provider Contact cell number	 
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890 ()/-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Provider Contact cell number can't be less than 1 character or more than 20 characters")
	private String providerContactCellNumber;

	// Provider_Accreditation_Num TEXT 20 533 N
	/** 
	 * The Provider Accreditation Number	
	 * This filed is not required
	 * Field maximum length = 17
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._-
	 */
	@Column(length = 17, nullable = true)
	@Size(min = 1, max = 17, message = "Provider Accreditation Number can't be less than 1 character or more than 17 characters")
	private String providerAccreditationNum;

	// Provider_Accredit_Start_Date DATE 8 553 C
	/** 
	 * The Provider Accredit Start Date	
	 * This filed is required
	 * Field maximum length = 8
	 * May not start with space
	 * Field may only contain a valid date in the format YYYYMMDD
	 * Provider_Start_Date may not be greater than Provider_End_Date
	 * Field may not have a value greater than today's date
	 * The system will display the maximum number of records with the same value for this field 
	 */
	@Column(length = 8, nullable = true)
	@Size(min = 1, max = 8, message = "Provider Accredit Start Date can't be less than 1 character or more than 8 characters")
	@NotEmpty(message ="Start Date is required")
	private Date providerAccreditStartDate;

	// Provider_Accredit_End_Date DATE 8 561 C
	/** 
	 * The Provider Accredit End Date	
	 * This filed is required
	 * Field maximum length = 8
	 * May not start with space
	 * Field may only contain a valid date in the format YYYYMMDD
	 * Provider_Start_Date may not be greater than Provider_End_Date
	 * Field may not have a value greater than today's date
	 * The system will display the maximum number of records with the same value for this field 
	 */
	@Column(length = 8, nullable = true)
	@Size(min = 1, max = 8, message = "Provider Accredit End Date can't be less than 1 character or more than 8 characters")
	@NotEmpty(message ="End Date is required")
	private Date providerAccreditEndDate;

	// Etqa_Decision_Number TEXT 20 569 N
	/** 
	 * The etqa Decision Number	
	 * This filed is not required
	 * Field maximum length = 20
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._- 
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "etqa Decision Number can't be less than 1 character or more than 20 characters")
	private String etqaDecisionNumber;

	// Look up table
	// Provider_Class_Id NUMBER 10 589 Y L
	// private ProviderClass providerClass;

	// Look up table
	// Structure_Status_Id NUMBER 10 599 Y L
	// private StructureStatus structureStatus;

	// Look up table
	// Province_Code TEXT 2 609 Y L
	/** 
	 * The etqa Decision Number	
	 * This filed is not required
	 * Field maximum length = 20
	 * Field must contain a valid Province code
	 */
	@Column(length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Provice can't be less than 1 character or more than 20 characters")
	private Province province;

	// Look up table
	// Country_Code TEXT 4 611 Y L
	// private Country country;

	/*
	 * These probably can be gotten from providerAddress
	 */
	// Latitude_Degree NUMBER 3 615 C
	/**
	 * This filed is not required
	 * Field maximum length = 3
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890-
	 * If a value is provided for Latitude_Degree or Latitude_Minutes or Latitude_Seconds then a value must be for Latitude_Degree and Latitude_Minutes and Latitude_Seconds
	 * If a value is provided for Latitude_Degree and related fields then a value must be provided for Longitude_Degree and related fields
	 * Field must be a negative value, may not be greater than -22 and may not have a value less than -35
	 * Value must be provided if Date_Stamp has a value great than 20160701
	 */
	@Column(length = 3, nullable = true)
	@Size(min = 1, max = 3, message = "Longitude Seconds can't be less than 1 character or more than 3 characters")
	private Double latitudeDegree;

	// Latitude_Minutes NUMBER 2 618 C
	/**
	 * This filed is not required
	 * Field maximum length = 2
	 * May not start with space
	 * Field may only contain whole numbers
	 * Value must have a length of exactly 2 (leading zeros) and may not be greater than 59
	 */
	@Column(length = 2, nullable = true)
	@Size(min = 1, max = 2, message = "Longitude Seconds can't be less than 1 character or more than 2 characters")
	private Double latituedeMinutes;

	// Latitude_Seconds NUMBER 6 620 C
	/**
	 * This filed is not required
	 * Field maximum length = 6
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890.
	 * Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999
	 */
	@Column(length = 6, nullable = true)
	@Size(min = 1, max = 6, message = "Longitude Seconds can't be less than 1 character or more than 6 characters")
	private Double latitudeSeconds;

	// Longitude_Degree NUMBER 2 626 C
	/**
	 * Longitude degree
	 * This filed is not required
	 * Field maximum length = 2
	 * May not start with space
	 * Field may only contain whole numbers
	 * If a value is provided for Longitude_Degree and related fields then a value must be provided for Latitude_Degree and related fields
	 * If a value if provided for Longitude_Degree or Longitude_Minutes or Longitude_Seconds then a value must be for Longitude_Degree, Longitude_Minutes and Longitude_Seconds
	 * Value may not be greater than 33 and may not have a value less than 16
	 * Value must be provided if Date_Stamp has a value great than 20160701
	 */
	@Column(length = 2, nullable = true)
	@Size(min = 1, max = 2, message = "Longitude degree can't be less than 1 character or more than 2 characters")
	private Double longitudeDegree;

	// Longitude_Minutes NUMBER 2 628 C
	/**
	 * Longitude Minutes
	 * This filed is not required
	 * Field maximum length = 2
	 * May not start with space
	 * Field may only contain whole numbers
	 * Value must have a length of exactly 2 (leading zeros) and may not be greater than 59
	 */
	@Column(length = 6, nullable = true)
	@Size(min = 1, max = 6, message = "Longitude Minutes can't be less than 1 character or more than 6 characters")
	private Double longitudeMinutes;

	// Longitude_Seconds NUMBER 6 630 C
	/**
	 * Longitude Seconds
	 * This filed is not required
	 * Field maximum length = 6
	 * May not start with space
	 * Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999
	 */
	@Column(length = 6, nullable = true)
	@Size(min = 1, max = 6, message = "Longitude Seconds can't be less than 1 character or more than 6 characters")
	private Double longitudeSeconds;

	/*
	 * All Present in Address object
	 */
	// Provider_Physical_Address_1 TEXT 50 636 N
	// Provider_Physical_Address_2 TEXT 50 686 N
	// Provider_Physical_Address_Town TEXT 50 736 N
	// Provider_Phys_Address_Postcode TEXT 4 786 N
	/**
	 * Provider Physical Address
	 * This filed is required
	 * Field maximum length = 4
	 * May not start with space
	 * Uppercase value in field may only contain characters 1234567890
	 * Length of field must be exactly 4 characters
	 */
	@Column(length = 4, nullable = true)
	@Size(min = 1, max = 4, message = "Physical Address can't be less than 1 character or more than 4 characters")
	private Address providerPhysicalAddress;

	// Provider_Web_Address TEXT 50 790 N
	/**
	 * Provider Web Address
	 * This filed is not required
	 * Field maximum length = 50
	 * May not start with space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\:._`', 
	 */
	@Column(length = 50, nullable = true)
	@Size(min = 1, max = 50, message = "Provider Web Address can't be less than 1 character or more than 50 characters")
	private String providerWebAddress;

	// Date_Stamp DATE 8 840 Y
	/**
	 * This filed is required
	 * Field maximum length = 8
	 * May not start with space
	 * Field may only contain a valid date in the format YYYYMMDD
	 * Field may not have a value greater than today's date
	 * Year component of field value may not be less than 1900 
	 */
	@Column(length = 8, nullable = true)
	@Size(min = 1, max = 8, message = "Date stamp can't be less than 1 character or more than 8 characters")
	private Date dateStamp;

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
		Provider other = (Provider) obj;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
