package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.Title;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckID;

// TODO: Auto-generated Javadoc
/**
 * The Class Users.
 */
@Entity
@Table(name = "learners")

@DynamicInsert(value = true)
@DynamicUpdate(value = true)
// @AuditTable(value = "users_hist")
// @Audited

public class Learners implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Learner id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** 
	 * The first name. 
	 * Field length = 26
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "first_name", length = 26, nullable = false)
	@Size(min = 1, max = 26, message = "First name can't be less than 1 character or more than 26 characters")
	@NotEmpty(message ="First name is required")
	private String firstName;

	/** 
	 * The last name.
	 * Field length = 45
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'  
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Value must be provided if POPI_Act_Status_ID does not have a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN 
	 * Person_Last_Name should not have the same value as Person_First_Name 
	 */
	@Column(name = "last_name", length = 45, nullable = false)
	@Size(min = 1, max = 45, message = "Last name can't be less than 1 character or more than 45 characters")
	@NotEmpty(message ="Last name is required")
	private String lastName;

	/** 
	 * The Full names and Surname. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "full_name_surname", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Full names and Surname can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Full names and Surname is required")
	private String fullNameSurname;

	
	/**
	 * The nationality.
	 * This field must contain data 
	 * Only valid code indicators will be allowed. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality_id", nullable = false)
	private Nationality nationality;

	/** 
	 * The date of birth. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "date_of_birth", nullable = false, length = 8)
	@Past(message ="Date of birth must be of past")
	@Size(min = 1, max = 8, message = "Date of birth can't be less than 1 character or more than 8 characters")
	private Date dateOfBirth;

	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "rsa_id_number", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String rsaIDNumber;

	/** The Alternative ID number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "Alternative ID number not valid")
	@Column(name = "alternative_id_number", length = 20, nullable = true)
	@Size(min = 13, max = 20, message = "Alternative ID number can't be less than 1 character or more than 20 characters")
	private String alternativeIDNumber;
	
	/** 
	 * The passport number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * If Person_Alternate_Id has a value then Alternate_Id_Type_Id may not = 533 
	 * If Person_Alternate_Id IS blank then Alternate_Id_Type_Id must = 533 
	 */
	@Column(name = "passport_number", length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Passport number length can't be less than 1 character or more than 20 characters")
	private String passportNumber;

	/** 
	 * The gender. 
	 * Field length = 1
	 * This field may not be left blank 
	 * Only valid code indicators will be allowed.
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'- 
	 * Field must contain a valid Gender_Code 
	 * If National_Id is not blank then Gender_Code must match gender character of National_Id. The 7th character of a SA National ID indicates a gender of female when less than 5 and male when greater than 4. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	private Gender gender;

	/** 
	 * The disabled.
	 * This field must contain data 
	 * Only valid code indicators will be allowed. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabled_id", nullable = true)
	private YesNoLookup disabled;

	/** The disability status. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disabilityStatus")
	private DisabilityStatus disabilityStatus;

	/** The residential address. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "residential_address_id", nullable = true)
	private Address residentialAddress;

	/** The postal address. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postal_address_id", nullable = true)
	private Address postalAddress;

	/** 
	 * The municipality. 
	 * This field must contain data 
	 * Only valid code indicators will be allowed. 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipality_id", nullable = true)
	private Municipality municipality;

	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "tel_number", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String telNumber;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", nullable = true)
	private Title title;

	/** 
	 * The cell number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 ()- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "cell_number", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid cell number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Cell number length can't be less than 1 character or more than 20 characters")
	private String cellNumber;

	/** 
	 * The fax number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * */
	@Column(name = "fax_number", length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Fax number length can't be less than 1 character or more than 20 characters")
	private String faxNumber;

	/** 
	 * The work contact number. 
	 * Field length = 20
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "work_contact_number", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid work contact number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Work contact number length can't be less than 1 character or more than 20 characters")
	private String workContactNumber;

	
	/** The equity. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	private Equity equity;

	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String email;
	
	
	/** 
	 * Name of learnership or designated trade:. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "learnership_designated_trade", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Name of learnership or designated trade can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Name of learnership or designated trade is required")
	private String learnershipDesignatedTrade;
	
	
	/** 
	 * Department of Higher Education and Training registration number of learnership. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "department_higher_education", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Department of Higher Education and Training registration number of learnership can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Department of Higher Education and Training registration number of learnership is required")
	private String departmentHigherEducation;
	
	
	/** 
	 * Commencement date of learnership agreement. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "commencement_date_learnership_agreement", nullable = false, length = 8)
	@Past(message ="Commencement date of learnership agreement")
	@Size(min = 1, max = 8, message = "Commencement date of learnership agreement can't be less than 1 character or more than 8 characters")
	private Date commencementDateLearnershipAgreement;
	
	
	/** 
	 * Termination date of learnership agreement. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "termination_date_learnership_agreement", nullable = false, length = 8)
	@Past(message ="Termination date of learnership agreement")
	@Size(min = 1, max = 8, message = "Termination date of learnership agreement can't be less than 1 character or more than 8 characters")
	private Date terminationDateLearnershipAgreement;
	
	/** 
	 * Learner number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "learner_number_be_supplied", length = 35, nullable = true)
	@Size(min = 1, max = 35, message = "Learner number can't be less than 1 character or more than 35 characters")
	private String learnerNumberBeSupplied;

	
	/** 
	 * Date of birth. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "completed_learner_date_birth", nullable = false, length = 8)
	@Past(message ="Date of birtht")
	@Size(min = 1, max = 8, message = "Date of birth can't be less than 1 character or more than 8 characters")
	private Date completedLearnerDateBirth;
	
	/** 
	 
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_if_specify", nullable = false)
	private String completedLearnerIfSpecify;
	
	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "completed_learner_email_address", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String completedLearnerEmailAddress;
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "completed_learner_home", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String completedLearnerHome;
	
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "completed_learner_work", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String completedLearnerWork;
	
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "completed_learner_cell", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String completedLearnerCell;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_african_citizen", nullable = false)
	private String completedLearnerAfricanCitizen;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_standard_gradeLevels", nullable = false)
	private String completedLearnerStandardGradeLevels;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_your_highest", nullable = false)
	private String completedLearnerYourHighest;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_yes_specify_title", nullable = false)
	private String completedLearnerYesSpecifyTitle;
	
	

	/** 
	 * If yes, specify registration number. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_specify_registration", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "specify registration number can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="specify registration number is required")
	private String completedLearnerSpecifyRegistration;
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_learner_how_many_credits", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "If NQF aligned, specify how many credits you have achieved can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="If NQF aligned, specify how many credits you have achieved is required")
	private String completedLearnerHowManyCredits;
	
	/** 
	 * If you are employed, when did you start work with your employer. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "completed_learner_start_work", nullable = false, length = 8)
	@Past(message ="If you are employed, when did you start work with your employer")
	@Size(min = 1, max = 8, message = "If you are employed, when did you start work with your employer can't be less than 1 character or more than 8 characters")
	private Date completedLearnerStartWork;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "completed_Learner_Name_The_Union", nullable = false)
	private String completedLearnerNameTheUnion;
	
	/** 
	 * If yes, specify registration number. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "parent_guardian_full_names", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "specify Full names can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="specify Full names is required")
	private String parentGuardianFullNames;
	
	
	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "parent_guardian_identity_number", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String parentGuardianIdentityNumber;
	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "parent_guardian_email_address", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String parentGuardianEmailAddress;
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "parent_guardian_home", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Home number can't be less than 1 character or more than 20 characters")
	private String parentGuardianHome;
	
	   
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "parent_guardian_work", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Work number can't be less than 1 character or more than 20 characters")
	private String parentGuardianWork;
	
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "parent_guardian_cell", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Cell number can't be less than 1 character or more than 20 characters")
	private String parentGuardianCell;
	
	
	/** 
	 * Registered name of employer. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_registered_employer", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Registered name of employer can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Registered name of employer is required")
	private String employerDetailsRegisteredEmployer;
	
	
	/** 
	 * If yes, specify registration number. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_trading_name", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Trading name can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Trading name is required")
	private String employerDetailsTradingName;
	
	/** 
	 * If yes, specify registration number. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_contact_person", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Full names of contact person can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Full names of contact person is required")
	private String employerDetailsContactPerson;
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "employer_details_tel", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel No. can't be less than 1 character or more than 20 characters")
	private String employerDetailsTel;
	
	   
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "employer_details_fax", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Fax No. can't be less than 1 character or more than 20 characters")
	private String employerDetailsFax;
	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "employer_details_email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String employerDetailsEmail;
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_sic", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "SIC can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="SIC is required")
	private String employerDetailsSIC;
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_sdl_no", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "SDL No can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="SDL No is required")
	private String employerDetailsSDLNo;
	

	/** 
	 * Name of SETA with which you are registered. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_details_are_registered", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Name of SETA with which you are registered can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Name of SETA with which you are registered is required")
	private String employerDetailsAreRegistered;
	
	/** 
	 * Registered name of Skills Development Provider. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_provider", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Registered name of Skills Development Provider can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Registered name of Skills Development Provider is required")
	private String providerDetailsProvider;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_trading_name", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Trading name can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Trading name is required")
	private String providerDetailsTradingName;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "name_skills_programme_registered_for", length = 150, nullable = false)
	@NotEmpty(message ="name of skills programme registered for is required")
	private String nameOfSkillsProgrammeRegisteredFor;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_code", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard code is required")
	private String saqaUnitStandardCode;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_code_1", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard code 1 is required")
	private String saqaUnitStandardCode1;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_code_2", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard code 2 is required")
	private String saqaUnitStandardCode2;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_code_3", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard code 3 is required")
	private String saqaUnitStandardCode3;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_code_4", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard code 4 is required")
	private String saqaUnitStandardCode4;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_title", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard title is required")
	private String saqaUnitStandardTitle;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_title_1", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard title 1 is required")
	private String saqaUnitStandardTitle1;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_title_2", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard title 2 is required")
	private String saqaUnitStandardTitle2;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_title_3", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard title 3 is required")
	private String saqaUnitStandardTitle3;
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "saqa_unit_standard_title_4", length = 150, nullable = false)
	@NotEmpty(message ="Saqa unit standard title 4 is required")
	private String saqaUnitStandardTitle4;
	
	
	
	
	
	/** 
	 * Trading name. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "registration_code_of_skills_programme_registered", length = 150, nullable = false)
	@NotEmpty(message ="registration code of skills programme registered for is required")
	private String registrationCodeOfSkillsProgrammeRegisteredFor;
	
	/** 
	 * Full names of contact person
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_contact_person", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Full names of contact person can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Full names of contact person is required")
	private String providerDetailsContactPerson;
	
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "provider_details_tel", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel No. can't be less than 1 character or more than 20 characters")
	private String providerDetailsTel;
	
	   
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "provider_details_fax", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Fax No. can't be less than 1 character or more than 20 characters")
	private String providerDetailsFax;
	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "provider_details_email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String providerDetailsEmail;
	
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_sic", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "SIC can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="SIC is required")
	private String providerDetailsSIC;
	
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_sdl_no", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "SDL No can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="SDL No is required")
	private String providerDetailsSDLNo;
	

	/** 
	 * Name of SETA with which you are registered. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "provider_details_seta", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Name of SETA with which you are registered can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Name of SETA with which you are registered is required")
	private String providerDetailsSETA;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "project_and_code_linked_to", nullable = false)
	@NotEmpty(message ="Name of project snd code linked to is required")
	private String projectAndCodeLinkedTo;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "conditions_of_employment_yes", nullable = false)
	
	private String conditionsOfEmploymentYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_full_names_applicant", nullable = false)
	@NotEmpty(message ="Employer full names applicant is required")
	private String employerFullNamesApplicant;
	
	/** 
	 * The passport number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * If Person_Alternate_Id has a value then Alternate_Id_Type_Id may not = 533 
	 * If Person_Alternate_Id IS blank then Alternate_Id_Type_Id must = 533 
	 */
	@Column(name = "employer_id_passport", length = 20, nullable = true)
	@Size(min = 1, max = 20, message = "Passport number length can't be less than 1 character or more than 20 characters")
	private String employerIdPassport;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_original_agreements", nullable = false)
	
	private String employerOriginalAgreements;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_designated_clearly", nullable = false)
	
	private String employerDesignatedClearly;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_initialled_learnership", nullable = false)
	
	private String employerInitialledLearnership;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_applicant_citizen", nullable = false)
	
	private String employerApplicantCitizen;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_full_names", nullable = false)
	
	private String employerFullNames;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_citizen_certified", nullable = false)
	
	private String employerCitizenCertified;
	
	/** 
	 * If you are employed, when did you start work with your employer. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "employer_commencement_date", nullable = false, length = 8)
	@Past(message ="Commencement date and termination date")
	@Size(min = 1, max = 8, message = "Commencement date and termination date can't be less than 1 character or more than 8 characters")
	private Date employerCommencementDate;
	
	/** 
	 * If you are employed, when did you start work with your employer. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "commencement_date", nullable = false, length = 8)
	@Size(min = 1, max = 8, message = "Commencement date can't be less than 1 character or more than 8 characters")
	private Date commencementDate;
	
	/** 
	 * If you are employed, when did you start work with your employer. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "completion_date", nullable = false, length = 8)
	@Size(min = 1, max = 8, message = "Completion date can't be less than 1 character or more than 8 characters")
	private Date completionDate;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_physical_address", nullable = false)
	
	private String employerPhysicalAddress;
	
	
	/** 
	 * Highest qualification. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_highest_qualification", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "Highest qualification can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Highest qualification is required")
	private String employerHighestQualification;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_corrections_initialled", nullable = false)
	
	private String employerCorrectionsInitialled;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_the_training_learner", nullable = false)
	
	private String employerTheTrainingLearner;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "employer_the_training_learner_yes", nullable = false)
	
	private String employerTheTrainingLearnerYes;
	
	/** 
	 * employer date. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "employer_date",  length = 8)
	@Size(min = 1, max = 8, message = "employer date can't be less than 1 character or more than 8 characters")
	private Date employerDate;
	
	/** 
	 * learnership date. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "learnership_date",  length = 8)

	@Size(min = 1, max = 8, message = "learnership date can't be less than 1 character or more than 8 characters")
	private Date learnershipDate;
	
	/** 
	 * The tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "contract_number_date", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "CONTRACT NO. can't be less than 1 character or more than 20 characters")
	private Date contractNumberDate;
	
	/** 
	 * legal guardian. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "legal_guardian_date",  length = 8)
	
	@Size(min = 1, max = 8, message = "legal guardian date can't be less than 1 character or more than 8 characters")
	private Date legalGuardianDate;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "full_name_of_apprentice", nullable = false)
	
	private String fullNameOfApprentice;
	
	/** 
	 * legal guardian. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "last_working_day_of_apprentice",  length = 8)
	
	@Size(min = 1, max = 8, message = "legal guardian date can't be less than 1 character or more than 8 characters")
	private Date lastWorkingDayOfApprentice;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "learnership_name_of_employer", nullable = false)
	
	private String learnershipNameOfEmployer;
	
	
	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "identity_number_date", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String identityNumberDate;
	
	/** 
	 * last working day of learner. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "last_working_day_of_learner",  length = 8)
	@Size(min = 1, max = 8, message = "last working day of learner can't be less than 1 character or more than 8 characters")
	private Date lastWorkingDayOfLearner;
	
	/** 
	 * manager date. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "manager_date",  length = 8)
	@Size(min = 1, max = 8, message = "manager date can't be less than 1 character or more than 8 characters")
	private Date managerDate;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "name_of_registered_company", nullable = false)
	private String nameOfRegisteredCompany;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "insert_institution", nullable = true)
	private String insertInstitution;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "here_in_after_referred_to_as_the", nullable = true)
	private String hereinafterReferredToAsThe;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "give_full_details_relationship_staff_member", nullable = true)
	private String giveFullDetailsRelationshipStaffMember;
	
	/**
	 * Douglas moover
	 * 
	 */
	
	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "identityNumberApprentice", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String identityNumberApprentice;
	
	
	/** 
	 * The contract number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "contractNumberApprentice", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "CONTRACT NUMBER can't be less than 1 character or more than 20 characters")
	private String contractNumberApprentice;
	
	
	/** 
	 * TRADE. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "trade_apprentice", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "TRADE can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="TRADE is required")
	private String tradeApprentice;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "have_applied_for_the_transfer_apprentice", nullable = false)
	private String haveAppliedForTheTransferApprentice;
	
	/** 
	 * APPRENTICE. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "apprentice_apprentice", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "APPRENTICE can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="APPRENTICE is required")
	private String apprenticeApprentice;
	
	
	/** 
	 * Apprentice date. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "date_apprentice",  length = 8)
	@Size(min = 1, max = 8, message = "Apprentice date can't be less than 1 character or more than 8 characters")
	private Date dateApprentice;
	
	/** 
	 * GUARDIAN. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "agreement_guardian", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "TRADE can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="TRADE is required")
	private String agreementGuardian;
	
	/** 
	 * Agreement. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "agreement_date", nullable = false, length = 8)
	@Past(message ="Agreement date and termination date")
	@Size(min = 1, max = 8, message = "Agreement date and termination date can't be less than 1 character or more than 8 characters")
	private Date agreementDate;
	
	
	/** 
	 * Objection. 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "objection_date", nullable = false, length = 8)
	@Past(message ="Objection date and termination date")
	@Size(min = 1, max = 8, message = "Objection date and termination date can't be less than 1 character or more than 8 characters")
	private Date objectionDate;
	
	/** 
	 * names of contact. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "name_of_contact_person_employer", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Full names of contact person can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Full names of contact person is required")
	private String nameOfContactPersonEmployer;
	
	/** 
	 * DESIGNATION. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "designation_employer", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "DESIGNATION can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Full names of contact person is required")
	private String designationEmployer;
	
	/** 
	 * The email. 
	 * Field length = 50
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must contain the @ character 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "email_address_employer", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	@Size(min = 1, max = 50, message = "Email Address can't be less than 1 character or more than 50 characters")
	private String emailAddressEmployer;
	
	
	
	
	
	//Ashton
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "proposed_course_of_study", nullable = true)
	@NotEmpty(message ="Proposed course of study is required")
	private String proposedCourseOfStudy;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_university_which_will_taken", nullable = true)
	@NotEmpty(message ="Name of university where studies will be taken is required")
	private String nameUniversityWhichWillTaken;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "why_choose_this_course", nullable = true)
	@NotEmpty(message ="why choose this course is required")
	private String whyChooseThisCourse;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "do_live_the_institution_residence", nullable = true)
	@NotEmpty(message ="Do you live in the institution residence is required")
	private String doLiveTheInstitutionResidence;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_company_where_employed", nullable = true)
	@NotEmpty(message ="Name of company where employed is required")
	private String nameCompanyWhereEmployed;
	
	
	/** 
	 * The gardian tel number. 
	 * Field length = 20
	 * This field may be left blank 
	 * Uppercase value in field may only contain characters 1234567890 ()/- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or - or  
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@Column(name = "tel_gardian_number", length = 20, nullable = true)
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message = "Invalid tel number")
	//@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="msg")
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String telGardianNumber;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "number_sisters_completing_financially", nullable = true)
	@NotEmpty(message ="Number of brothers and sisters who are still completing their education and are financially supported by father/mother/guardian is required")
	private String numberSistersCompletingFinancially;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_school_1", nullable = true)
	@NotEmpty(message ="Number of school is required")
	private String nameOfSchool1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date yearsFrom1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private String yearsTo1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_school_2", nullable = true)
	@NotEmpty(message ="Name of school is required")
	private String nameOfSchool2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private String yearsFrom2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private String yearsTo2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_school_3", nullable = true)
	@NotEmpty(message ="Name of school is required")
	private String nameOfSchool3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private String yearsFrom3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private String yearsTo3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_certificate", nullable = true)
	@NotEmpty(message ="Name of certificare is required")
	private String nameOfCertificate;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "month_and_year_obtained", nullable = true)
	@Size(min = 1, max = 8, message = "Month and year obtained can't be less than 1 character or more than 8 characters")
	private Date monthAndYearObtained;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_mathematics", nullable = true)
	@NotEmpty(message ="Subject mathematics is required")
	private String subjectMathematics;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_physical_science", nullable = true)
	@NotEmpty(message ="Subject physical science is required")
	private String subjectPhysicalScience;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_english", nullable = true)
	@NotEmpty(message ="Subject english is required")
	private String subjectEnglish;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_afrikaans", nullable = true)
	@NotEmpty(message ="Subject afrikaans is required")
	private String subjectAfrikaans;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_own_1", nullable = true)
	@NotEmpty(message ="Own subject 1 is required")
	private String subjectOwn1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_result_1", nullable = true)
	@NotEmpty(message ="Own subject results 1 is required")
	private String subjectResult1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_Own_2", nullable = true)
	@NotEmpty(message ="Own subject 2 is required")
	private String subjectOwn2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_result_2", nullable = true)
	@NotEmpty(message ="Own subject results 2 is required")
	private String subjectResult2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_own_3", nullable = true)
	@NotEmpty(message ="Own subject 3 is required")
	private String subjectOwn3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_result_3", nullable = true)
	@NotEmpty(message ="Own subject results 3 is required")
	private String subjectResult3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_own_4", nullable = true)
	@NotEmpty(message ="Own subject 4 is required")
	private String subjectOwn4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subject_result_4", nullable = true)
	@NotEmpty(message ="Own subject results 4 is required")
	private String subjectResult4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "school_activities_1", nullable = true)
	@NotEmpty(message ="School activity 1 is required")
	private String schoolActivities1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "school_activities_2", nullable = true)
	@NotEmpty(message ="School activity 2 is required")
	private String schoolActivities2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "school_activities_3", nullable = true)
	@NotEmpty(message ="School activity 3 is required")
	private String schoolActivities3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_university", nullable = true)
	@NotEmpty(message ="name of university is required")
	private String nameOfUniversity;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "faculty_and_course", nullable = true)
	@NotEmpty(message ="name of faculty and course is required")
	private String facultyAndCourse;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_of_current_studies", nullable = true)
	@NotEmpty(message ="Year of course of current studies is required")
	private String yearOfCourseOfCurrentStudies;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "date_1", nullable = true)
	@Size(min = 1, max = 8, message = "Date can't be less than 1 character or more than 8 characters")
	private Date date1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_1", nullable = true)
	@Size(message = "Year of course is required")
	private Date yearOfCourse1;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subjects_taken_1", nullable = true)
	@NotEmpty(message ="subjects taken is required")
	private String subjectsTaken1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "results_1", nullable = true)
	@NotEmpty(message ="subjects result is required")
	private String results1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "date_2", nullable = true)
	@Size(min = 1, max = 8, message = "Date can't be less than 1 character or more than 8 characters")
	private Date date2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_2", nullable = true)
	@Size(message = "Year of course is required")
	private Date yearOfCourse2;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subjects_taken_2", nullable = true)
	@NotEmpty(message ="subjects taken is required")
	private String subjectsTaken2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "results_2", nullable = true)
	@NotEmpty(message ="subjects result is required")
	private String results2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "date_3", nullable = true)
	@Size(min = 1, max = 8, message = "Date can't be less than 1 character or more than 8 characters")
	private Date date3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_3", nullable = true)
	@Size(message = "Year of course is required")
	private Date yearOfCourse3;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subjects_taken_3", nullable = true)
	@NotEmpty(message ="subjects taken is required")
	private String subjectsTaken3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "results_3", nullable = true)
	@NotEmpty(message ="subjects result is required")
	private String results3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	
	@Column(name = "date_4", nullable = true)
	@Size(min = 1, max = 8, message = "Date can't be less than 1 character or more than 8 characters")
	private Date date4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_4", nullable = true)
	@Size(message = "Year of course is required")
	private Date yearOfCourse4;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subjects_taken_4", nullable = true)
	@NotEmpty(message ="subjects taken is required")
	private String subjectsTaken4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "results_4", nullable = true)
	@NotEmpty(message ="subjects result is required")
	private String results4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "date_5", nullable = true)
	@Size(min = 1, max = 8, message = "Date can't be less than 1 character or more than 8 characters")
	private Date date5;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "year_of_course_5", nullable = true)
	@Size(message = "Year of course is required")
	private Date yearOfCourse5;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "subjects_taken_5", nullable = true)
	@NotEmpty(message ="subjects taken is required")
	private String subjectsTaken5;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "results_5", nullable = true)
	@NotEmpty(message ="subjects result is required")
	private String results5;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "appropriate_yes", nullable = true)
	@NotEmpty(message ="tick the appropriate is required")
	private String appropriateYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "appropriate_no", nullable = true)
	@NotEmpty(message ="tick the appropriate is required")
	private String appropriateNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_a_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date yearsFroma1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_a_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date yearsToa1;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_company_a_1", nullable = true)
	@NotEmpty(message ="Name of company is required")
	private String nameOfCompanya1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "type_of_work_a_1", nullable = true)
	@NotEmpty(message ="Type of work is required")
	private String typeOfWorka1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "position_held_a_1", nullable = true)
	@NotEmpty(message ="position held in company is required")
	private String positionHelda1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_a_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date yearsFroma2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_a_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date yearsToa2;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_company_a_2", nullable = true)
	@NotEmpty(message ="Name of company is required")
	private String nameOfCompanya2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "type_of_work_a_2", nullable = true)
	@NotEmpty(message ="Type of work is required")
	private String typeOfWorka2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "position_held_a_2", nullable = true)
	@NotEmpty(message ="Position held in company is required")
	private String positionHelda2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_from_a_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date yearsFroma3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "years_to_a_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date yearsToa3;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_company_a_3", nullable = true)
	@NotEmpty(message ="Name of company is required")
	private String nameOfCompanya3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "type_of_work_a_3", nullable = true)
	@NotEmpty(message ="Type of work is required")
	private String typeOfWorka3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "position_held_a_3", nullable = true)
	@NotEmpty(message ="Position held in company is required")
	private String positionHelda3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employed_yes", nullable = true)
	@NotEmpty(message ="tick the appropriate box is required")
	private String employedYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employed_no", nullable = true)
	@NotEmpty(message ="tick the appropriate box is required")
	private String employedNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "by_which_company", nullable = true)
	@NotEmpty(message ="Provide by which company is required")
	private String byWhichCompany;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "institution_previously_yes", nullable = true)
	@NotEmpty(message ="tick the appropriate box is required")
	private String institutionPreviouslyYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "institution_previously_no", nullable = true)
	@NotEmpty(message ="tick the appropriate box is required")
	private String institutionPreviouslyNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_university_1", nullable = true)
	@NotEmpty(message ="Name of university is required")
	private String higherNameOfUniversity1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_years_from_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date higherYearsFrom1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_Years_to_1", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date higherYearsTo1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_course_1", nullable = true)
	@NotEmpty(message ="Name of course is required")
	private String higherNameOfCourse1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higherResults1", nullable = true)
	@NotEmpty(message ="higher results is required")
	private String higher_results_1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_university_2", nullable = true)
	@NotEmpty(message ="Name of university is required")
	private String higherNameOfUniversity2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_years_from_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date higherYearsFrom2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_Years_to_2", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date higherYearsTo2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_course_2", nullable = true)
	@NotEmpty(message ="Name of course is required")
	private String higherNameOfCourse2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higherResults2", nullable = true)
	@NotEmpty(message ="results is required")
	private String higher_results_2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_university_3", nullable = true)
	@NotEmpty(message ="Name of university is required")
	private String higherNameOfUniversity3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_years_from_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date higherYearsFrom3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_Years_to_3", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date higherYearsTo3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_course_3", nullable = true)
	@NotEmpty(message ="Name of course is required")
	private String higherNameOfCourse3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higherResults3", nullable = true)
	@NotEmpty(message ="Results is required")
	private String higher_results_3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_university_4", nullable = true)
	@NotEmpty(message ="Name of university is required")
	private String higherNameOfUniversity4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_years_from_4", nullable = true)
	@Size(min = 1, max = 8, message = "Year from can't be less than 1 character or more than 8 characters")
	private Date higherYearsFrom4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_Years_to_4", nullable = true)
	@Size(min = 1, max = 8, message = "Year to can't be less than 1 character or more than 8 characters")
	private Date higherYearsTo4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higher_name_of_course_4", nullable = true)
	@NotEmpty(message ="Name of course is required")
	private String higherNameOfCourse4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "higherResults4", nullable = true)
	@NotEmpty(message ="Results is required")
	private String higher_results_4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "loans_grants_bursaries_yes", nullable = true)
	@NotEmpty(message ="Loans grants bursaries is required")
	private String loansGrantsBursariesYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "loans_grants_bursaries_no", nullable = true)
	@NotEmpty(message ="Loans grants bursaries is required")
	private String loansGrantsBursariesNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_award_1", nullable = true)
	@NotEmpty(message ="Name of award is required")
	private String nameOfAward1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "award_amount_1", nullable = true)
	@NotEmpty(message ="Award amount is required")
	private String awardAmount1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_award_2", nullable = true)
	@NotEmpty(message ="Name of award is required")
	private String nameOfAward2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "award_amount_2", nullable = true)
	@NotEmpty(message ="Award amount is required")
	private String awardAmount2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "any_other_loan_grant_yes", nullable = true)
	@NotEmpty(message ="Any other loan grant is required")
	private String anyOtherLoanGrantYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "any_other_loan_grant_no", nullable = true)
	@NotEmpty(message ="Any other loan grant is required")
	private String anyOtherLoanGrantNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "any_other_loan_grant_details", nullable = true)
	@NotEmpty(message ="Any other loan grant details is required")
	private String anyOtherLoanGrantDetails;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "gender_tick_whichever", nullable = true)
	@NotEmpty(message ="Select which gender is required")
	private String genderTickWhichever;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "race_tick_whichever", nullable = true)
	@NotEmpty(message ="Select which race is required")
	private String raceTickWhichever;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "student_number", nullable = true)
	@Size(min = 1, max = 20, message ="Any other loan grant is required")
	private String studentNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "the_previous_semester", nullable = true)
	@NotEmpty(message ="Any other loan grant is required")
	private String thePreviousSemester;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_course", nullable = true)
	@NotEmpty(message ="name of course is required")
	private String nameOfCourse;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "expected_date_commencement_study", nullable = true)
	@Size(min = 1, max = 8, message ="Expected date of commencement of study is required")
	private Date expectedDateCommencementStudy;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "the_engineering_diploma", nullable = true)
	@NotEmpty(message ="Provide which engineering diploma course are you applying for")
	private String theEngineeringDiploma;
		
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "dhet_learnership_registration_number", nullable = true)
	@Size(min = 1, max = 20, message = "Registration number length can't be less than 1 character or more than 20 characters")
	private String dhetLearnershipRegistrationNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "title_of_qualification", nullable = true)
	@NotEmpty(message ="Provide title of qualification")
	private String titleOfQualification;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "home_language", nullable = true)
	@NotEmpty(message ="Home language is required")
	private String homeLanguage;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "other_language_1", nullable = true)
	@NotEmpty(message ="Other Language 1 is required")
	private String otherLanguage1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "other_language_2", nullable = true)
	@NotEmpty(message ="Other Language 2 is required")
	private String otherLanguage2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "qualification_saqa_id", nullable = true)
	@Size(min = 1, max = 20, message = "Qualification saqa Id number length can't be less than 1 character or more than 20 characters")
	private String qualificationSaqaId;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "setas_scope_of_coverage", nullable = true)
	@NotEmpty(message ="Setas scope of coverage is required")
	private String setasScopeOfCoverage;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "skills_programme_title", nullable = true)
	@NotEmpty(message ="Skills programme title is required")
	private String skillsProgrammeTitle;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "skills_programme_number", nullable = true)
	@Size(min = 1, max = 20, message = "Skills programme number length can't be less than 1 character or more than 20 characters")
	private String skillsProgrammeNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "setas_scope_of_coverage2", nullable = true)
	@NotEmpty(message ="Setas scope of coverage is required")
	private String setasScopeOfCoverage2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_1", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_1", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_2", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_2", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_3", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_3", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_4", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_4", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber4;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_5", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle5;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_5", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber5;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_6", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle6;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_6", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber6;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_title_7", nullable = true)
	@NotEmpty(message ="Unit standard title is required")
	private String unitStandardTitle7;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "unit_standard_id_number_7", nullable = true)
	@Size(min = 13, max = 13, message = "Unit standard id number can't be less than 1 character or more than 13 characters")
	private String unitStandardIdNumber7;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "training_provider_name", nullable = true)
	@NotEmpty(message ="Training provider name is required")
	private String trainingProviderName;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "training_provider_date", nullable = true)
	@Size(min = 1, max = 8, message = "Training provider date can't be less than 1 character or more than 8 characters")
	private Date trainingProviderDate;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "provider_date", nullable = true)
	@Size(min = 1, max = 8, message = "Provider date can't be less than 1 character or more than 8 characters")
	private Date providerDate;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "training_provider_signature", nullable = true)
	@NotEmpty(message ="Training provider signature is required")
	private String trainingProviderSignature;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learnership_signature", nullable = true)
	@NotEmpty(message ="Learnership signaturee is required")
	private String learnershipSignature;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "provider_signature", nullable = true)
	@NotEmpty(message ="Provider signature is required")
	private String providerSignature;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_contact_person_trainee", nullable = true) 
	@NotEmpty(message ="Name of contact person is required")
	private String nameOfContactPersonTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "designationTrainee", nullable = true)
	@NotEmpty(message ="Designation trainee is required")
	private String designationTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "cell_no_trainee", nullable = true)
	@Size(min = 1, max = 20, message = "Cell number length can't be less than 1 character or more than 20 characters")
	private String cellNoTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "seta_number_to_which_affiliated", nullable = true)
	@Size(min = 1, max = 20, message = "Seta number length can't be less than 1 character or more than 20 characters")
	private String setaNumberToWhichAffiliated;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "sars_levy_registration_number", nullable = true)
	@Size(min = 1, max = 20, message = "Sars levy number length can't be less than 1 character or more than 20 characters")
	private String sarsLevyRegistrationNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "tell_no_trainee", nullable = true)
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String tellNoTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_telephone", nullable = true)
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String learnerTelephone;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_cel_no", nullable = true)
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String learnerCelNo;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "fax_no_trainee", nullable = true)
	@Size(min = 1, max = 20, message = "Fax number length can't be less than 1 character or more than 20 characters")
	private String faxNoTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "surname_trainee", nullable = true)
	@NotEmpty(message ="Trainee surname is required")
	private String surnameTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "first_names_trainee", nullable = true)
	@NotEmpty(message ="Trainee name is required")
	private String firstNamesTrainee;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "trade_occupation", nullable = true)
	@NotEmpty(message ="Trade occupation is required")
	private String tradeOccupation;
	
	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "trainee_rsa_id_number", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String traineeRsaIDNumber;

	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "trainee_contract_number", nullable = true)
	@Size(min = 1, max = 20, message = "Contact number length can't be less than 1 character or more than 20 characters")
	private String traineeContractNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "starting_date_with_company", nullable = true)
	@Size(min = 1, max = 8, message = "Starting date with company can't be less than 1 character or more than 8 characters")
	private Date startingDateWithCompany;
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_from_1", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date from can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionFrom1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_to_1", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date to can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionTo1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_position_1", nullable = true)
	@NotEmpty(message ="Immediate recognition position is required")
	private String immediateRecognitionPosition1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_tasks_performed_1", nullable = true)
	@NotEmpty(message ="Immediate recognition task performed is required")
	private String immediateRecognitionTasksPerformed1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_from_2", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date from can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionFrom2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_to_2", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date to can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionTo2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_position_2", nullable = true)
	@NotEmpty(message ="Immediate recognition position is required")
	private String immediateRecognitionPosition2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_tasks_performed_2", nullable = true)
	@NotEmpty(message ="Immediate recognition task performed is required")
	private String immediateRecognitionTasksPerformed2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_from_3", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date from can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionFrom3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_to_3", nullable = true)
	@Size(min = 1, max = 8, message = "Immediate recognition date to can't be less than 1 character or more than 8 characters")
	private Date immediateRecognitionTo3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_position_3", nullable = true)
	@NotEmpty(message ="Immediate recognition position is required")
	private String immediateRecognitionPosition3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "immediate_recognition_tasks_performed_3", nullable = true)
	@NotEmpty(message ="Immediate recognition task performed is required")
	private String immediateRecognitionTasksPerformed3;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "full_name_learner", nullable = true)
	@NotEmpty(message ="Full learner name is required")
	private String fullNameLearner;
	
	/** The rsa ID number. 
	 * Field length = 15
	 * This field may be left blank 
	 * Field may not start with a space. 
	 * Uppercase value in field may only contain characters 1234567890 
	 * Both National_Id and Person_Alternate_Id may not be blank
	 * Value must have a length of exactly 13 
	 * Field value may not have characters 0000 from characters 7 to 10 
	 * Field may not have characters 0000 from characters 1 to 4 
	 * Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "learner_rsa_id_number", length = 15, nullable = true)
	@Size(min = 13, max = 13, message = "RSA ID number can't be less than 1 character or more than 13 characters")
	private String learnerRsaIDNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "contract_number_learner", nullable = true)
	@Size(min = 1, max = 20, message = "Cell number length can't be less than 1 character or more than 20 characters")
	private String contractNumberLearner;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "union_membership_yes", nullable = true)
	@Size(min = 1, max = 20, message = "Cell number length can't be less than 1 character or more than 20 characters")
	private String unionMembershipYes;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "trade_learner", nullable = true)
	@NotEmpty(message ="Trade learner is required")
	private String tradeLearner;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "transfer_learner", nullable = true)
	@NotEmpty(message ="Transfer learner is required")
	private String transferLearner;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner", nullable = true)
	@NotEmpty(message ="Learner is required")
	private String learner;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_date", nullable = true)
	@Size(min = 1, max = 8, message = "Learner date can't be less than 1 character or more than 8 characters")
	private Date learnerDate;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "objection_present_employer", nullable = true)
	@NotEmpty(message ="Objection present employer is required")
	private String objectionPresentEmployer;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "cell_no_employer", nullable = true)
	@Size(min = 1, max = 20, message = "Cell number length can't be less than 1 character or more than 20 characters")
	private String cellNoEmployer;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "telephone_number_employer", nullable = true)
	@Size(min = 1, max = 20, message = "Tel number can't be less than 1 character or more than 20 characters")
	private String telephoneNumberEmployer;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "fax_number_employer", nullable = true)
	@Size(min = 1, max = 20, message = "Fax number can't be less than 1 character or more than 20 characters")
	private String faxNumberEmployer;
	
	
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "transferred_this_day_of", nullable = true)
	@Size(min = 1, max = 8, message = "Transfer this day of can't be less than 1 character or more than 8 characters")
	private Date transferredThisDayOf;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employer_witnesses_1", nullable = true)
	@NotEmpty(message ="Employer witness 1 is required")
	private String employerWitnesses1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employer_witnesses_2", nullable = true)
	@NotEmpty(message ="Employer witness 2 is required")
	private String employerWitnesses2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employer_signiture", nullable = true)
	@NotEmpty(message ="Employer signiture is required")
	private String employerSigniture;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "new_employer_witnesses_1", nullable = true)
	@NotEmpty(message ="Employer witness 1 is required")
	private String newEmployerWitnesses1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "new_employer_witnesses_2", nullable = true)
	@NotEmpty(message ="Employer witness 2 is required")
	private String newEmployerWitnesses2;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "new_employer_signiture", nullable = true)
	@NotEmpty(message ="Employer signiture is required")
	private String newEmployerSigniture;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "guardian_witnesses_1", nullable = true)
	@NotEmpty(message ="Guardian witness 1 is required")
	private String guardianWitnesses1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "guardian_witnesses_2", nullable = true)
	@NotEmpty(message ="Guardian witness 2 is required")
	private String guardianWitnesses2;
	
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "guardian_signiture", nullable = true)
	@NotEmpty(message ="Guardian signiture is required")
	private String guardianSigniture;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_witnesses_1", nullable = true)
	@NotEmpty(message ="Learner witness 1 is required")
	private String learnerWitnesses1;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_witnesses_2", nullable = true)
	@NotEmpty(message ="Learner witness 2 is required")
	private String learnerWitnesses2;
	
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "learner_signiture", nullable = true)
	@NotEmpty(message ="Learner signiture is required")
	private String learnerSigniture;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "employer_signature", nullable = true)
	@NotEmpty(message ="Employer signiture is required")
	private String employerSignature;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "id_no_registered_company", nullable = true)
	@Size(min = 20, max = 20, message = "Registered compant ID number can't be less than 1 character or more than 20 characters")
	private String idNoRegisteredCompany;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "date_registered_company", nullable = true)
	@Size(min = 1, max = 8, message = "Registered company date can't be less than 1 character or more than 8 characters")
	private Date dateRegisteredCompany;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "registered_company_signature", nullable = true)
	@NotEmpty(message ="Registered company employer signiture is required")
	private String registeredCompanySignature;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_applicant", nullable = true)
	@NotEmpty(message ="Name of applicant is required")
	private String nameOfApplicant;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "sdl_number", nullable = true)
	@NotEmpty(message ="sdl number is required")
	private String sdlNumber;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "name_of_seta_registered_at", nullable = true)
	@NotEmpty(message ="Name of seta registered at is required")
	private String nameOfSetaRegisteredAt;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 */
	@Column(name = "sic_code_that_applies", nullable = true)
	@NotEmpty(message ="Sic code that applies at is required")
	private String sicCodeThatApplies;
	
	
	
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "south_african_citizen", nullable = false)
	@NotEmpty(message ="South African citizen is required")
	private String southAfricanCitizen;
	
	/**
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "citizenship", nullable = false)
	@NotEmpty(message ="citizenship is required")
	private String citizenship;
	
	/** 
	 * The first name. 
	 * Field length = 26
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "legal_name_of_employer", length = 26, nullable = false)
	@Size(min = 1, max = 26, message = "Legal name of employer can't be less than 1 character or more than 26 characters")
	@NotEmpty(message ="Legal name of employer is required")
	private String legalNameOfEmployer;
	
	/** 
	 * The first name. 
	 * Field length = 26
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "trading_name_of_employer", length = 26, nullable = false)
	@Size(min = 1, max = 26, message = "Trading name of employer can't be less than 1 character or more than 26 characters")
	@NotEmpty(message ="Trading name of employer is required")
	private String tradingNameOfEmployer;
	
	/** 
	 * The first name. 
	 * Field length = 26
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "EmployereWorkplace", length = 26, nullable = false)
	@Size(min = 1, max = 26, message = "Trading name of employer can't be less than 1 character or more than 26 characters")
	@NotEmpty(message ="Trading name of employer is required")
	private String employereWorkplace;
	
	
	
	/**
	 * Douglas moover
	 * 
	 */
	
	/** 
	 * If NQF aligned, specify how many credits you have achieved. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "sars_levy_no_employer", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "SDL No can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="SDL No is required")
	private String sarsLevyNoEmployer;
	
	/** 
	 * Name of SETA with which you are registered. 
	 * Field length = 150
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "seta_employer", length = 150, nullable = false)
	@Size(min = 1, max = 150, message = "Name of SETA with which you are registered can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Name of SETA with which you are registered is required")
	private String setaEmployer;
	
	/** 
	 *  Hereby Transferred Date 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "hereby_transferred_this_day_of", nullable = false, length = 8)
	@Past(message ="Date of birtht")
	@Size(min = 1, max = 8, message = "Hereby Transferred Date can't be less than 1 character or more than 8 characters")
	private Date herebyTransferredThisDayOf;
	
	/** 
	 * Office Date 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "office_this_day_of", nullable = false, length = 8)
	@Past(message ="Date of birtht")
	@Size(min = 1, max = 8, message = "Office Date can't be less than 1 character or more than 8 characters")
	private Date officeThisDayOf;
	
	/** 
	 
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "name_of_employer", nullable = false)
	@NotEmpty(message ="Name of employer is required")
	private String nameOfEmployer;
	
	/** 
	 
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "name_of_training_provider", nullable = false)
	@NotEmpty(message ="Name of training provider is required")
	private String nameOfTrainingProvider;
	
	
	
	
	/** 
	 
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "client_services_administrator", nullable = false)
	@NotEmpty(message ="Client services administrator is required")
	private String clientServicesAdministrator;
	
	/** 
	 * Highest qualification. 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "highest_qualification", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "Highest qualification can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="Highest qualification is required")
	private String highestQualification;
	
	/**
	 * TRADE TEST INFORMATION
	 * This field not may be left blank 
	 * Field may not start with a space
	 */
	@Column(name = "prefer_to_be_tested", nullable = false)
	private String preferToBeTested;
	
	@Column(name = "Proof_of_payment", nullable = false)
	private String ProofOfPayment;
	
	@Column(name = "contractual_learner", nullable = false)
	private String contractualLearner;
	
	@Column(name = "as_indicated", nullable = false)
	private String asIndicated;
	
	/** 
	 * 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "application_received", nullable = false, length = 8)
	@Past(message ="If you are employed, when did you start work with your employer")
	@Size(min = 1, max = 8, message = "application received can't be less than 1 character or more than 8 characters")
	private Date applicationReceived;
	
	/** 
	 * 
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "serial_number", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "serial number can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="serial number is required")
	private String serialNumber;
	
	/** 
	 * 
	 * Field length = 8
	 * This field may not be left blank 
	 * Field may not start with a space. 
	 * Field may only contain a valid date in the format YYYYMMDD 
	 * Year component must be greater than 1850 
	 * The system will display the maximum number of records with the same value for this field 
	 * If National_Id is provided then the first 6 characters of National_Id must be the same as characters 3 to 8 of Person_Birth_Date 
	 * Age may not be less than 16 years 
	 */
	@Column(name = "submitted_company", nullable = false, length = 8)
	
	@Size(min = 1, max = 8, message = "submitted company can't be less than 1 character or more than 8 characters")
	private Date submittedCompany;
	
	/** 
	 *
	 * Field length = 50
	 * This field not may be left blank 
	 * Field may not start with a space
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`'- 
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL or - or 
	 * Field must be blank if POPI_Act_Status_ID has a value of 2 
	 * Uppercase value in field should not contain characters NA or U or NONE or GEEN
	 * Person_First_Name should not have the same value as Person_Middle_Name 
	 */
	@Column(name = "client_services", length = 50, nullable = false)
	@Size(min = 1, max = 50, message = "client services can't be less than 1 character or more than 150 characters")
	@NotEmpty(message ="client services, specify how many credits you have achieved is required")
	private String clientServices;
	
	
	
	
	/**
	 * Douglas moover
	 * 
	 */
	
	
	
	
	
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
		Learners other = (Learners) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new users.
	 */
	public Learners() {
	}

	/**
	 * Instantiates a new users.
	 *
	 * @param id the id
	 */
	public Learners(Long id) {
		this.id = id;
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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public Nationality getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality the new nationality
	 */
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the rsa ID number.
	 *
	 * @return the rsa ID number
	 */
	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	/**
	 * Sets the rsa ID number.
	 *
	 * @param rsaIDNumber the new rsa ID number
	 */
	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public YesNoLookup getDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(YesNoLookup disabled) {
		this.disabled = disabled;
	}

	/**
	 * Gets the residential address.
	 *
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 *
	 * @param residentialAddress the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 *
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 *
	 * @param postalAddress the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the municipality.
	 *
	 * @return the municipality
	 */
	public Municipality getMunicipality() {
		return municipality;
	}

	/**
	 * Sets the municipality.
	 *
	 * @param municipality the new municipality
	 */
	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	/**
	 * Gets the tel number.
	 *
	 * @return the tel number
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * Sets the tel number.
	 *
	 * @param telNumber the new tel number
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * Gets the cell number.
	 *
	 * @return the cell number
	 */
	public String getCellNumber() {
		return cellNumber;
	}

	/**
	 * Sets the cell number.
	 *
	 * @param cellNumber the new cell number
	 */
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	/**
	 * Gets the fax number.
	 *
	 * @return the fax number
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * Sets the fax number.
	 *
	 * @param faxNumber the new fax number
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * Gets the work contact number.
	 *
	 * @return the work contact number
	 */
	public String getWorkContactNumber() {
		return workContactNumber;
	}

	/**
	 * Sets the work contact number.
	 *
	 * @param workContactNumber the new work contact number
	 */
	public void setWorkContactNumber(String workContactNumber) {
		this.workContactNumber = workContactNumber;
	}

	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	public void setDisabilityStatus(DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public Equity getEquity() {
		return equity;
	}

	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fullNameSurname
	 */
	public String getFullNameSurname() {
		return fullNameSurname;
	}

	/**
	 * @param fullNameSurname the fullNameSurname to set
	 */
	public void setFullNameSurname(String fullNameSurname) {
		this.fullNameSurname = fullNameSurname;
	}

	/**
	 * @return the alternativeIDNumber
	 */
	public String getAlternativeIDNumber() {
		return alternativeIDNumber;
	}

	/**
	 * @param alternativeIDNumber the alternativeIDNumber to set
	 */
	public void setAlternativeIDNumber(String alternativeIDNumber) {
		this.alternativeIDNumber = alternativeIDNumber;
	}

	/**
	 * @return the learnershipDesignatedTrade
	 */
	public String getLearnershipDesignatedTrade() {
		return learnershipDesignatedTrade;
	}

	/**
	 * @param learnershipDesignatedTrade the learnershipDesignatedTrade to set
	 */
	public void setLearnershipDesignatedTrade(String learnershipDesignatedTrade) {
		this.learnershipDesignatedTrade = learnershipDesignatedTrade;
	}

	/**
	 * @return the departmentHigherEducation
	 */
	public String getDepartmentHigherEducation() {
		return departmentHigherEducation;
	}

	/**
	 * @param departmentHigherEducation the departmentHigherEducation to set
	 */
	public void setDepartmentHigherEducation(String departmentHigherEducation) {
		this.departmentHigherEducation = departmentHigherEducation;
	}

	/**
	 * @return the commencementDateLearnershipAgreement
	 */
	public Date getCommencementDateLearnershipAgreement() {
		return commencementDateLearnershipAgreement;
	}

	/**
	 * @param commencementDateLearnershipAgreement the commencementDateLearnershipAgreement to set
	 */
	public void setCommencementDateLearnershipAgreement(Date commencementDateLearnershipAgreement) {
		this.commencementDateLearnershipAgreement = commencementDateLearnershipAgreement;
	}

	/**
	 * @return the terminationDateLearnershipAgreement
	 */
	public Date getTerminationDateLearnershipAgreement() {
		return terminationDateLearnershipAgreement;
	}

	/**
	 * @param terminationDateLearnershipAgreement the terminationDateLearnershipAgreement to set
	 */
	public void setTerminationDateLearnershipAgreement(Date terminationDateLearnershipAgreement) {
		this.terminationDateLearnershipAgreement = terminationDateLearnershipAgreement;
	}

	/**
	 * @return the learnerNumberBeSupplied
	 */
	public String getLearnerNumberBeSupplied() {
		return learnerNumberBeSupplied;
	}

	/**
	 * @param learnerNumberBeSupplied the learnerNumberBeSupplied to set
	 */
	public void setLearnerNumberBeSupplied(String learnerNumberBeSupplied) {
		this.learnerNumberBeSupplied = learnerNumberBeSupplied;
	}

	/**
	 * @return the completedLearnerDateBirth
	 */
	public Date getCompletedLearnerDateBirth() {
		return completedLearnerDateBirth;
	}

	/**
	 * @param completedLearnerDateBirth the completedLearnerDateBirth to set
	 */
	public void setCompletedLearnerDateBirth(Date completedLearnerDateBirth) {
		this.completedLearnerDateBirth = completedLearnerDateBirth;
	}

	/**
	 * @return the completedLearnerIfSpecify
	 */
	public String getCompletedLearnerIfSpecify() {
		return completedLearnerIfSpecify;
	}

	/**
	 * @param completedLearnerIfSpecify the completedLearnerIfSpecify to set
	 */
	public void setCompletedLearnerIfSpecify(String completedLearnerIfSpecify) {
		this.completedLearnerIfSpecify = completedLearnerIfSpecify;
	}

	/**
	 * @return the completedLearnerEmailAddress
	 */
	public String getCompletedLearnerEmailAddress() {
		return completedLearnerEmailAddress;
	}

	/**
	 * @param completedLearnerEmailAddress the completedLearnerEmailAddress to set
	 */
	public void setCompletedLearnerEmailAddress(String completedLearnerEmailAddress) {
		this.completedLearnerEmailAddress = completedLearnerEmailAddress;
	}

	/**
	 * @return the completedLearnerHome
	 */
	public String getCompletedLearnerHome() {
		return completedLearnerHome;
	}

	/**
	 * @param completedLearnerHome the completedLearnerHome to set
	 */
	public void setCompletedLearnerHome(String completedLearnerHome) {
		this.completedLearnerHome = completedLearnerHome;
	}

	/**
	 * @return the completedLearnerWork
	 */
	public String getCompletedLearnerWork() {
		return completedLearnerWork;
	}

	/**
	 * @param completedLearnerWork the completedLearnerWork to set
	 */
	public void setCompletedLearnerWork(String completedLearnerWork) {
		this.completedLearnerWork = completedLearnerWork;
	}

	/**
	 * @return the completedLearnerCell
	 */
	public String getCompletedLearnerCell() {
		return completedLearnerCell;
	}

	/**
	 * @param completedLearnerCell the completedLearnerCell to set
	 */
	public void setCompletedLearnerCell(String completedLearnerCell) {
		this.completedLearnerCell = completedLearnerCell;
	}

	/**
	 * @return the completedLearnerAfricanCitizen
	 */
	public String getCompletedLearnerAfricanCitizen() {
		return completedLearnerAfricanCitizen;
	}

	/**
	 * @param completedLearnerAfricanCitizen the completedLearnerAfricanCitizen to set
	 */
	public void setCompletedLearnerAfricanCitizen(String completedLearnerAfricanCitizen) {
		this.completedLearnerAfricanCitizen = completedLearnerAfricanCitizen;
	}

	/**
	 * @return the completedLearnerStandardGradeLevels
	 */
	public String getCompletedLearnerStandardGradeLevels() {
		return completedLearnerStandardGradeLevels;
	}

	/**
	 * @param completedLearnerStandardGradeLevels the completedLearnerStandardGradeLevels to set
	 */
	public void setCompletedLearnerStandardGradeLevels(String completedLearnerStandardGradeLevels) {
		this.completedLearnerStandardGradeLevels = completedLearnerStandardGradeLevels;
	}

	/**
	 * @return the completedLearnerYourHighest
	 */
	public String getCompletedLearnerYourHighest() {
		return completedLearnerYourHighest;
	}

	/**
	 * @param completedLearnerYourHighest the completedLearnerYourHighest to set
	 */
	public void setCompletedLearnerYourHighest(String completedLearnerYourHighest) {
		this.completedLearnerYourHighest = completedLearnerYourHighest;
	}

	/**
	 * @return the completedLearnerYesSpecifyTitle
	 */
	public String getCompletedLearnerYesSpecifyTitle() {
		return completedLearnerYesSpecifyTitle;
	}

	/**
	 * @param completedLearnerYesSpecifyTitle the completedLearnerYesSpecifyTitle to set
	 */
	public void setCompletedLearnerYesSpecifyTitle(String completedLearnerYesSpecifyTitle) {
		this.completedLearnerYesSpecifyTitle = completedLearnerYesSpecifyTitle;
	}

	/**
	 * @return the completedLearnerSpecifyRegistration
	 */
	public String getCompletedLearnerSpecifyRegistration() {
		return completedLearnerSpecifyRegistration;
	}

	/**
	 * @param completedLearnerSpecifyRegistration the completedLearnerSpecifyRegistration to set
	 */
	public void setCompletedLearnerSpecifyRegistration(String completedLearnerSpecifyRegistration) {
		this.completedLearnerSpecifyRegistration = completedLearnerSpecifyRegistration;
	}

	/**
	 * @return the completedLearnerHowManyCredits
	 */
	public String getCompletedLearnerHowManyCredits() {
		return completedLearnerHowManyCredits;
	}

	/**
	 * @param completedLearnerHowManyCredits the completedLearnerHowManyCredits to set
	 */
	public void setCompletedLearnerHowManyCredits(String completedLearnerHowManyCredits) {
		this.completedLearnerHowManyCredits = completedLearnerHowManyCredits;
	}

	/**
	 * @return the completedLearnerStartWork
	 */
	public Date getCompletedLearnerStartWork() {
		return completedLearnerStartWork;
	}

	/**
	 * @param completedLearnerStartWork the completedLearnerStartWork to set
	 */
	public void setCompletedLearnerStartWork(Date completedLearnerStartWork) {
		this.completedLearnerStartWork = completedLearnerStartWork;
	}

	/**
	 * @return the completedLearnerNameTheUnion
	 */
	public String getCompletedLearnerNameTheUnion() {
		return completedLearnerNameTheUnion;
	}

	/**
	 * @param completedLearnerNameTheUnion the completedLearnerNameTheUnion to set
	 */
	public void setCompletedLearnerNameTheUnion(String completedLearnerNameTheUnion) {
		this.completedLearnerNameTheUnion = completedLearnerNameTheUnion;
	}

	/**
	 * @return the parentGuardianFullNames
	 */
	public String getParentGuardianFullNames() {
		return parentGuardianFullNames;
	}

	/**
	 * @param parentGuardianFullNames the parentGuardianFullNames to set
	 */
	public void setParentGuardianFullNames(String parentGuardianFullNames) {
		this.parentGuardianFullNames = parentGuardianFullNames;
	}

	/**
	 * @return the parentGuardianIdentityNumber
	 */
	public String getParentGuardianIdentityNumber() {
		return parentGuardianIdentityNumber;
	}

	/**
	 * @param parentGuardianIdentityNumber the parentGuardianIdentityNumber to set
	 */
	public void setParentGuardianIdentityNumber(String parentGuardianIdentityNumber) {
		this.parentGuardianIdentityNumber = parentGuardianIdentityNumber;
	}

	/**
	 * @return the parentGuardianEmailAddress
	 */
	public String getParentGuardianEmailAddress() {
		return parentGuardianEmailAddress;
	}

	/**
	 * @param parentGuardianEmailAddress the parentGuardianEmailAddress to set
	 */
	public void setParentGuardianEmailAddress(String parentGuardianEmailAddress) {
		this.parentGuardianEmailAddress = parentGuardianEmailAddress;
	}

	/**
	 * @return the parentGuardianHome
	 */
	public String getParentGuardianHome() {
		return parentGuardianHome;
	}

	/**
	 * @param parentGuardianHome the parentGuardianHome to set
	 */
	public void setParentGuardianHome(String parentGuardianHome) {
		this.parentGuardianHome = parentGuardianHome;
	}

	/**
	 * @return the parentGuardianWork
	 */
	public String getParentGuardianWork() {
		return parentGuardianWork;
	}

	/**
	 * @param parentGuardianWork the parentGuardianWork to set
	 */
	public void setParentGuardianWork(String parentGuardianWork) {
		this.parentGuardianWork = parentGuardianWork;
	}

	/**
	 * @return the parentGuardianCell
	 */
	public String getParentGuardianCell() {
		return parentGuardianCell;
	}

	/**
	 * @param parentGuardianCell the parentGuardianCell to set
	 */
	public void setParentGuardianCell(String parentGuardianCell) {
		this.parentGuardianCell = parentGuardianCell;
	}

	/**
	 * @return the employerDetailsRegisteredEmployer
	 */
	public String getEmployerDetailsRegisteredEmployer() {
		return employerDetailsRegisteredEmployer;
	}

	/**
	 * @param employerDetailsRegisteredEmployer the employerDetailsRegisteredEmployer to set
	 */
	public void setEmployerDetailsRegisteredEmployer(String employerDetailsRegisteredEmployer) {
		this.employerDetailsRegisteredEmployer = employerDetailsRegisteredEmployer;
	}

	/**
	 * @return the employerDetailsTradingName
	 */
	public String getEmployerDetailsTradingName() {
		return employerDetailsTradingName;
	}

	/**
	 * @param employerDetailsTradingName the employerDetailsTradingName to set
	 */
	public void setEmployerDetailsTradingName(String employerDetailsTradingName) {
		this.employerDetailsTradingName = employerDetailsTradingName;
	}

	/**
	 * @return the employerDetailsContactPerson
	 */
	public String getEmployerDetailsContactPerson() {
		return employerDetailsContactPerson;
	}

	/**
	 * @param employerDetailsContactPerson the employerDetailsContactPerson to set
	 */
	public void setEmployerDetailsContactPerson(String employerDetailsContactPerson) {
		this.employerDetailsContactPerson = employerDetailsContactPerson;
	}

	/**
	 * @return the employerDetailsTel
	 */
	public String getEmployerDetailsTel() {
		return employerDetailsTel;
	}

	/**
	 * @param employerDetailsTel the employerDetailsTel to set
	 */
	public void setEmployerDetailsTel(String employerDetailsTel) {
		this.employerDetailsTel = employerDetailsTel;
	}

	/**
	 * @return the employerDetailsFax
	 */
	public String getEmployerDetailsFax() {
		return employerDetailsFax;
	}

	/**
	 * @param employerDetailsFax the employerDetailsFax to set
	 */
	public void setEmployerDetailsFax(String employerDetailsFax) {
		this.employerDetailsFax = employerDetailsFax;
	}

	/**
	 * @return the employerDetailsEmail
	 */
	public String getEmployerDetailsEmail() {
		return employerDetailsEmail;
	}

	/**
	 * @param employerDetailsEmail the employerDetailsEmail to set
	 */
	public void setEmployerDetailsEmail(String employerDetailsEmail) {
		this.employerDetailsEmail = employerDetailsEmail;
	}

	/**
	 * @return the employerDetailsSIC
	 */
	public String getEmployerDetailsSIC() {
		return employerDetailsSIC;
	}

	/**
	 * @param employerDetailsSIC the employerDetailsSIC to set
	 */
	public void setEmployerDetailsSIC(String employerDetailsSIC) {
		this.employerDetailsSIC = employerDetailsSIC;
	}

	/**
	 * @return the employerDetailsSDLNo
	 */
	public String getEmployerDetailsSDLNo() {
		return employerDetailsSDLNo;
	}

	/**
	 * @param employerDetailsSDLNo the employerDetailsSDLNo to set
	 */
	public void setEmployerDetailsSDLNo(String employerDetailsSDLNo) {
		this.employerDetailsSDLNo = employerDetailsSDLNo;
	}

	/**
	 * @return the employerDetailsAreRegistered
	 */
	public String getEmployerDetailsAreRegistered() {
		return employerDetailsAreRegistered;
	}

	/**
	 * @param employerDetailsAreRegistered the employerDetailsAreRegistered to set
	 */
	public void setEmployerDetailsAreRegistered(String employerDetailsAreRegistered) {
		this.employerDetailsAreRegistered = employerDetailsAreRegistered;
	}

	/**
	 * @return the providerDetailsProvider
	 */
	public String getProviderDetailsProvider() {
		return providerDetailsProvider;
	}

	/**
	 * @param providerDetailsProvider the providerDetailsProvider to set
	 */
	public void setProviderDetailsProvider(String providerDetailsProvider) {
		this.providerDetailsProvider = providerDetailsProvider;
	}

	/**
	 * @return the providerDetailsTradingName
	 */
	public String getProviderDetailsTradingName() {
		return providerDetailsTradingName;
	}

	/**
	 * @param providerDetailsTradingName the providerDetailsTradingName to set
	 */
	public void setProviderDetailsTradingName(String providerDetailsTradingName) {
		this.providerDetailsTradingName = providerDetailsTradingName;
	}

	/**
	 * @return the providerDetailsContactPerson
	 */
	public String getProviderDetailsContactPerson() {
		return providerDetailsContactPerson;
	}

	/**
	 * @param providerDetailsContactPerson the providerDetailsContactPerson to set
	 */
	public void setProviderDetailsContactPerson(String providerDetailsContactPerson) {
		this.providerDetailsContactPerson = providerDetailsContactPerson;
	}

	/**
	 * @return the providerDetailsTel
	 */
	public String getProviderDetailsTel() {
		return providerDetailsTel;
	}

	/**
	 * @param providerDetailsTel the providerDetailsTel to set
	 */
	public void setProviderDetailsTel(String providerDetailsTel) {
		this.providerDetailsTel = providerDetailsTel;
	}

	/**
	 * @return the providerDetailsFax
	 */
	public String getProviderDetailsFax() {
		return providerDetailsFax;
	}

	/**
	 * @param providerDetailsFax the providerDetailsFax to set
	 */
	public void setProviderDetailsFax(String providerDetailsFax) {
		this.providerDetailsFax = providerDetailsFax;
	}

	/**
	 * @return the providerDetailsEmail
	 */
	public String getProviderDetailsEmail() {
		return providerDetailsEmail;
	}

	/**
	 * @param providerDetailsEmail the providerDetailsEmail to set
	 */
	public void setProviderDetailsEmail(String providerDetailsEmail) {
		this.providerDetailsEmail = providerDetailsEmail;
	}

	/**
	 * @return the providerDetailsSIC
	 */
	public String getProviderDetailsSIC() {
		return providerDetailsSIC;
	}

	/**
	 * @param providerDetailsSIC the providerDetailsSIC to set
	 */
	public void setProviderDetailsSIC(String providerDetailsSIC) {
		this.providerDetailsSIC = providerDetailsSIC;
	}

	/**
	 * @return the providerDetailsSDLNo
	 */
	public String getProviderDetailsSDLNo() {
		return providerDetailsSDLNo;
	}

	/**
	 * @param providerDetailsSDLNo the providerDetailsSDLNo to set
	 */
	public void setProviderDetailsSDLNo(String providerDetailsSDLNo) {
		this.providerDetailsSDLNo = providerDetailsSDLNo;
	}

	/**
	 * @return the providerDetailsSETA
	 */
	public String getProviderDetailsSETA() {
		return providerDetailsSETA;
	}

	/**
	 * @param providerDetailsSETA the providerDetailsSETA to set
	 */
	public void setProviderDetailsSETA(String providerDetailsSETA) {
		this.providerDetailsSETA = providerDetailsSETA;
	}

	/**
	 * @return the conditionsOfEmploymentYes
	 */
	public String getConditionsOfEmploymentYes() {
		return conditionsOfEmploymentYes;
	}

	/**
	 * @param conditionsOfEmploymentYes the conditionsOfEmploymentYes to set
	 */
	public void setConditionsOfEmploymentYes(String conditionsOfEmploymentYes) {
		this.conditionsOfEmploymentYes = conditionsOfEmploymentYes;
	}

	/**
	 * @return the employerFullNamesApplicant
	 */
	public String getEmployerFullNamesApplicant() {
		return employerFullNamesApplicant;
	}

	/**
	 * @param employerFullNamesApplicant the employerFullNamesApplicant to set
	 */
	public void setEmployerFullNamesApplicant(String employerFullNamesApplicant) {
		this.employerFullNamesApplicant = employerFullNamesApplicant;
	}

	/**
	 * @return the employerIdPassport
	 */
	public String getEmployerIdPassport() {
		return employerIdPassport;
	}

	/**
	 * @param employerIdPassport the employerIdPassport to set
	 */
	public void setEmployerIdPassport(String employerIdPassport) {
		this.employerIdPassport = employerIdPassport;
	}

	/**
	 * @return the employerOriginalAgreements
	 */
	public String getEmployerOriginalAgreements() {
		return employerOriginalAgreements;
	}

	/**
	 * @param employerOriginalAgreements the employerOriginalAgreements to set
	 */
	public void setEmployerOriginalAgreements(String employerOriginalAgreements) {
		this.employerOriginalAgreements = employerOriginalAgreements;
	}

	/**
	 * @return the employerDesignatedClearly
	 */
	public String getEmployerDesignatedClearly() {
		return employerDesignatedClearly;
	}

	/**
	 * @param employerDesignatedClearly the employerDesignatedClearly to set
	 */
	public void setEmployerDesignatedClearly(String employerDesignatedClearly) {
		this.employerDesignatedClearly = employerDesignatedClearly;
	}

	/**
	 * @return the employerInitialledLearnership
	 */
	public String getEmployerInitialledLearnership() {
		return employerInitialledLearnership;
	}

	/**
	 * @param employerInitialledLearnership the employerInitialledLearnership to set
	 */
	public void setEmployerInitialledLearnership(String employerInitialledLearnership) {
		this.employerInitialledLearnership = employerInitialledLearnership;
	}

	/**
	 * @return the employerApplicantCitizen
	 */
	public String getEmployerApplicantCitizen() {
		return employerApplicantCitizen;
	}

	/**
	 * @param employerApplicantCitizen the employerApplicantCitizen to set
	 */
	public void setEmployerApplicantCitizen(String employerApplicantCitizen) {
		this.employerApplicantCitizen = employerApplicantCitizen;
	}

	/**
	 * @return the employerFullNames
	 */
	public String getEmployerFullNames() {
		return employerFullNames;
	}

	/**
	 * @param employerFullNames the employerFullNames to set
	 */
	public void setEmployerFullNames(String employerFullNames) {
		this.employerFullNames = employerFullNames;
	}

	/**
	 * @return the employerCitizenCertified
	 */
	public String getEmployerCitizenCertified() {
		return employerCitizenCertified;
	}

	/**
	 * @param employerCitizenCertified the employerCitizenCertified to set
	 */
	public void setEmployerCitizenCertified(String employerCitizenCertified) {
		this.employerCitizenCertified = employerCitizenCertified;
	}

	/**
	 * @return the employerCommencementDate
	 */
	public Date getEmployerCommencementDate() {
		return employerCommencementDate;
	}

	/**
	 * @param employerCommencementDate the employerCommencementDate to set
	 */
	public void setEmployerCommencementDate(Date employerCommencementDate) {
		this.employerCommencementDate = employerCommencementDate;
	}

	/**
	 * @return the employerPhysicalAddress
	 */
	public String getEmployerPhysicalAddress() {
		return employerPhysicalAddress;
	}

	/**
	 * @param employerPhysicalAddress the employerPhysicalAddress to set
	 */
	public void setEmployerPhysicalAddress(String employerPhysicalAddress) {
		this.employerPhysicalAddress = employerPhysicalAddress;
	}

	/**
	 * @return the employerHighestQualification
	 */
	public String getEmployerHighestQualification() {
		return employerHighestQualification;
	}

	/**
	 * @param employerHighestQualification the employerHighestQualification to set
	 */
	public void setEmployerHighestQualification(String employerHighestQualification) {
		this.employerHighestQualification = employerHighestQualification;
	}

	/**
	 * @return the employerCorrectionsInitialled
	 */
	public String getEmployerCorrectionsInitialled() {
		return employerCorrectionsInitialled;
	}

	/**
	 * @param employerCorrectionsInitialled the employerCorrectionsInitialled to set
	 */
	public void setEmployerCorrectionsInitialled(String employerCorrectionsInitialled) {
		this.employerCorrectionsInitialled = employerCorrectionsInitialled;
	}

	/**
	 * @return the employerTheTrainingLearner
	 */
	public String getEmployerTheTrainingLearner() {
		return employerTheTrainingLearner;
	}

	/**
	 * @param employerTheTrainingLearner the employerTheTrainingLearner to set
	 */
	public void setEmployerTheTrainingLearner(String employerTheTrainingLearner) {
		this.employerTheTrainingLearner = employerTheTrainingLearner;
	}

	/**
	 * @return the employerTheTrainingLearnerYes
	 */
	public String getEmployerTheTrainingLearnerYes() {
		return employerTheTrainingLearnerYes;
	}

	/**
	 * @param employerTheTrainingLearnerYes the employerTheTrainingLearnerYes to set
	 */
	public void setEmployerTheTrainingLearnerYes(String employerTheTrainingLearnerYes) {
		this.employerTheTrainingLearnerYes = employerTheTrainingLearnerYes;
	}

	/**
	 * @return the employerDate
	 */
	public Date getEmployerDate() {
		return employerDate;
	}

	/**
	 * @param employerDate the employerDate to set
	 */
	public void setEmployerDate(Date employerDate) {
		this.employerDate = employerDate;
	}

	/**
	 * @return the learnershipDate
	 */
	public Date getLearnershipDate() {
		return learnershipDate;
	}

	/**
	 * @param learnershipDate the learnershipDate to set
	 */
	public void setLearnershipDate(Date learnershipDate) {
		this.learnershipDate = learnershipDate;
	}

	/**
	 * @return the legalGuardianDate
	 */
	public Date getLegalGuardianDate() {
		return legalGuardianDate;
	}

	/**
	 * @param legalGuardianDate the legalGuardianDate to set
	 */
	public void setLegalGuardianDate(Date legalGuardianDate) {
		this.legalGuardianDate = legalGuardianDate;
	}

	/**
	 * @return the fullNameOfApprentice
	 */
	public String getFullNameOfApprentice() {
		return fullNameOfApprentice;
	}

	/**
	 * @param fullNameOfApprentice the fullNameOfApprentice to set
	 */
	public void setFullNameOfApprentice(String fullNameOfApprentice) {
		this.fullNameOfApprentice = fullNameOfApprentice;
	}

	/**
	 * @return the learnershipNameOfEmployer
	 */
	public String getLearnershipNameOfEmployer() {
		return learnershipNameOfEmployer;
	}

	/**
	 * @param learnershipNameOfEmployer the learnershipNameOfEmployer to set
	 */
	public void setLearnershipNameOfEmployer(String learnershipNameOfEmployer) {
		this.learnershipNameOfEmployer = learnershipNameOfEmployer;
	}

	/**
	 * @return the identityNumberDate
	 */
	public String getIdentityNumberDate() {
		return identityNumberDate;
	}

	/**
	 * @param identityNumberDate the identityNumberDate to set
	 */
	public void setIdentityNumberDate(String identityNumberDate) {
		this.identityNumberDate = identityNumberDate;
	}

	/**
	 * @return the lastWorkingDayOfLearner
	 */
	public Date getLastWorkingDayOfLearner() {
		return lastWorkingDayOfLearner;
	}

	/**
	 * @param lastWorkingDayOfLearner the lastWorkingDayOfLearner to set
	 */
	public void setLastWorkingDayOfLearner(Date lastWorkingDayOfLearner) {
		this.lastWorkingDayOfLearner = lastWorkingDayOfLearner;
	}

	/**
	 * @return the managerDate
	 */
	public Date getManagerDate() {
		return managerDate;
	}

	/**
	 * @param managerDate the managerDate to set
	 */
	public void setManagerDate(Date managerDate) {
		this.managerDate = managerDate;
	}

	/**
	 * @return the contractNumberDate
	 */
	public Date getContractNumberDate() {
		return contractNumberDate;
	}

	/**
	 * @param contractNumberDate the contractNumberDate to set
	 */
	public void setContractNumberDate(Date contractNumberDate) {
		this.contractNumberDate = contractNumberDate;
	}

	/**
	 * @return the lastWorkingDayOfApprentice
	 */
	public Date getLastWorkingDayOfApprentice() {
		return lastWorkingDayOfApprentice;
	}

	/**
	 * @param lastWorkingDayOfApprentice the lastWorkingDayOfApprentice to set
	 */
	public void setLastWorkingDayOfApprentice(Date lastWorkingDayOfApprentice) {
		this.lastWorkingDayOfApprentice = lastWorkingDayOfApprentice;
	}

	/**
	 * @return the nameOfRegisteredCompany
	 */
	public String getNameOfRegisteredCompany() {
		return nameOfRegisteredCompany;
	}

	/**
	 * @param nameOfRegisteredCompany the nameOfRegisteredCompany to set
	 */
	public void setNameOfRegisteredCompany(String nameOfRegisteredCompany) {
		this.nameOfRegisteredCompany = nameOfRegisteredCompany;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

}
