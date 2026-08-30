package haj.com.sars;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;

/**
 * The Class SarsEmployerDetailAllData.
 */
@Entity
@Table(name = "sars_employer_detail_all_data_for_testing")
public class SarsEmployerDetailAllDataFT implements IDataEntity {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** The sars files. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_employer_detail_id", nullable = true)
	private SarsEmployerDetail sarsEmployerDetail;
	
	/** The sars files. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sars_filel_id", nullable = true)
	private SarsFiles sarsFiles;
	
	/** The ref no. */
	@CSVAnnotation(name = "REF_NO", className = String.class)
	@Column(name = "ref_no")
	private String refNo;

	/** The sars data 1. */
	@CSVAnnotation(name = "SARS_DATA_1", className = String.class)
	@Column(name = "SARS_DATA_1")
	private String sarsData1;

	/** The sars data 2. */
	@CSVAnnotation(name = "SARS_DATA_2", className = String.class)
	@Column(name = "SARS_DATA_2")
	private String sarsData2;

	/** The sars data 3. */
	@CSVAnnotation(name = "SARS_DATA_3", className = String.class)
	@Column(name = "SARS_DATA_3")
	private String sarsData3;

	/** The registered name of entity. */
	@CSVAnnotation(name = "REGISTERED_NAME_OF_ENTITY", className = String.class)
	@Column(name = "registered_name_of_entity", length=500)
	private String registeredNameOfEntity;

	/** The sars data 4. */
	@CSVAnnotation(name = "SARS_DATA_4", className = String.class)
	@Column(name = "SARS_DATA_4")
	private String sarsData4;

	/** The sars data 5. */
	@CSVAnnotation(name = "SARS_DATA_5", className = String.class)
	@Column(name = "SARS_DATA_5")
	private String sarsData5;

	/** The sars data 6. */
	@CSVAnnotation(name = "SARS_DATA_6", className = String.class)
	@Column(name = "SARS_DATA_6")
	private String sarsData6;

	/** The sars data 7. */
	@CSVAnnotation(name = "SARS_DATA_7", className = String.class)
	@Column(name = "SARS_DATA_7")
	private String sarsData7;

	/** The company registration number. */
	@CSVAnnotation(name = "COMPANY_REGISTRATION_NUMBER", className = String.class)
	@Column(name = "company_registration_number")
	private String companyRegistrationNumber;

	/** The sars code 1. */
	@CSVAnnotation(name = "SARS_CODE_1", className = String.class)
	@Column(name = "SARS_CODE_1")
	private String sarsCode1;

	/** The sars code 2. */
	@CSVAnnotation(name = "SARS_CODE_2", className = String.class)
	@Column(name = "SARS_CODE_2")
	private String sarsCode2;

	/** The sars code 3. */
	@CSVAnnotation(name = "SARS_CODE_3", className = String.class)
	@Column(name = "SARS_CODE_3")
	private String sarsCode3;

	/** The sars code 4. */
	@CSVAnnotation(name = "SARS_CODE_4", className = String.class)
	@Column(name = "SARS_CODE_4")
	private String sarsCode4;

	/** The sars code 5. */
	@CSVAnnotation(name = "SARS_CODE_5", className = String.class)
	@Column(name = "SARS_CODE_5")
	private String sarsCode5;

	/** The sars code 6. */
	@CSVAnnotation(name = "SARS_CODE_6", className = String.class)
	@Column(name = "SARS_CODE_6")
	private String sarsCode6;

	/** The sars code 7. */
	@CSVAnnotation(name = "SARS_CODE_7", className = String.class)
	@Column(name = "SARS_CODE_7")
	private String sarsCode7;

	/** The sars code 8. */
	@CSVAnnotation(name = "SARS_CODE_8", className = String.class)
	@Column(name = "SARS_CODE_8")
	private String sarsCode8;

	/** The sars code 9. */
	@CSVAnnotation(name = "SARS_CODE_9", className = String.class)
	@Column(name = "SARS_CODE_9")
	private String sarsCode9;

	/** The sars code 10. */
	@CSVAnnotation(name = "SARS_CODE_10", className = String.class)
	@Column(name = "SARS_CODE_10")
	private String sarsCode10;

	/** The sars code 11. */
	@CSVAnnotation(name = "SARS_CODE_11", className = String.class)
	@Column(name = "SARS_CODE_11")
	private String sarsCode11;

	/** The sars code 12. */
	@CSVAnnotation(name = "SARS_CODE_12", className = String.class)
	@Column(name = "SARS_CODE_12")
	private String sarsCode12;

	/** The sars code 13. */
	@CSVAnnotation(name = "SARS_CODE_13", className = String.class)
	@Column(name = "SARS_CODE_13")
	private String sarsCode13;

	/** The sars code 14. */
	@CSVAnnotation(name = "SARS_CODE_14", className = String.class)
	@Column(name = "SARS_CODE_14")
	private String sarsCode14;

	/** The sars code 15. */
	@CSVAnnotation(name = "SARS_CODE_15", className = String.class)
	@Column(name = "SARS_CODE_15")
	private String sarsCode15;

	/** The sars code 16. */
	@CSVAnnotation(name = "SARS_CODE_16", className = String.class)
	@Column(name = "SARS_CODE_16")
	private String sarsCode16;

	/** The trading name. */
	@CSVAnnotation(name = "TRADING_NAME", className = String.class)
	@Column(name = "trading_name")
	private String tradingName;

	/** The sars data 8. */
	@CSVAnnotation(name = "SARS_DATA_8", className = String.class)
	@Column(name = "SARS_DATA_8")
	private String sarsData8;

	/** The sars data 9. */
	@CSVAnnotation(name = "SARS_DATA_9", className = String.class)
	@Column(name = "SARS_DATA_9")
	private String sarsData9;

	/** The sars data 10. */
	@CSVAnnotation(name = "SARS_DATA_10", className = String.class)
	@Column(name = "SARS_DATA_10")
	private String sarsData10;

	/** The sars data 11. */
	@CSVAnnotation(name = "SARS_DATA_11", className = String.class)
	@Column(name = "SARS_DATA_11")
	private String sarsData11;

	/** The sars data 12. */
	@CSVAnnotation(name = "SARS_DATA_12", className = String.class)
	@Column(name = "SARS_DATA_12")
	private String sarsData12;

	/** The sars data 13. */
	@CSVAnnotation(name = "SARS_DATA_13", className = String.class)
	@Column(name = "SARS_DATA_13")
	private String sarsData13;

	/** The sars data 14. */
	@CSVAnnotation(name = "SARS_DATA_14", className = String.class)
	@Column(name = "SARS_DATA_14")
	private String sarsData14;

	/** The sars data 15. */
	@CSVAnnotation(name = "SARS_DATA_15", className = String.class)
	@Column(name = "SARS_DATA_15")
	private String sarsData15;

	/** The sars data 16. */
	@CSVAnnotation(name = "SARS_DATA_16", className = String.class)
	@Column(name = "SARS_DATA_16")
	private String sarsData16;

	/** The sars data 17. */
	@CSVAnnotation(name = "SARS_DATA_17", className = String.class)
	@Column(name = "SARS_DATA_17")
	private String sarsData17;

	/** The sars data 18. */
	@CSVAnnotation(name = "SARS_DATA_18", className = String.class)
	@Column(name = "SARS_DATA_18")
	private String sarsData18;

	/** The sars data 19. */
	@CSVAnnotation(name = "SARS_DATA_19", className = String.class)
	@Column(name = "SARS_DATA_19")
	private String sarsData19;

	/** The sars data 20. */
	@CSVAnnotation(name = "SARS_DATA_20", className = String.class)
	@Column(name = "SARS_DATA_20")
	private String sarsData20;

	/** The sars data 21. */
	@CSVAnnotation(name = "SARS_DATA_21", className = String.class)
	@Column(name = "SARS_DATA_21")
	private String sarsData21;

	/** The sars data 22. */
	@CSVAnnotation(name = "SARS_DATA_22", className = String.class)
	@Column(name = "SARS_DATA_22")
	private String sarsData22;

	/** The sars data 23. */
	@CSVAnnotation(name = "SARS_DATA_23", className = String.class)
	@Column(name = "SARS_DATA_23")
	private String sarsData23;

	/** The sars data 24. */
	@CSVAnnotation(name = "SARS_DATA_24", className = String.class)
	@Column(name = "SARS_DATA_24")
	private String sarsData24;

	/** The sars data 25. */
	@CSVAnnotation(name = "SARS_DATA_25", className = String.class)
	@Column(name = "SARS_DATA_25")
	private String sarsData25;

	/** The sic code 2. */
	@CSVAnnotation(name = "SIC_CODE_2", className = String.class)
	@Column(name = "sic_code_2")
	private String sicCode2;

	/** The sars data 26. */
	@CSVAnnotation(name = "SARS_DATA_26", className = String.class)
	@Column(name = "SARS_DATA_26")
	private String sarsData26;

	/** The sars data 27. */
	@CSVAnnotation(name = "SARS_DATA_27", className = String.class)
	@Column(name = "SARS_DATA_27")
	private String sarsData27;

	/** The sars data 28. */
	@CSVAnnotation(name = "SARS_DATA_28", className = String.class)
	@Column(name = "SARS_DATA_28")
	private String sarsData28;

	/** The sars data 29. */
	@CSVAnnotation(name = "SARS_DATA_29", className = String.class)
	@Column(name = "SARS_DATA_29")
	private String sarsData29;

	/** The sars data 30. */
	@CSVAnnotation(name = "SARS_DATA_30", className = String.class)
	@Column(name = "SARS_DATA_30")
	private String sarsData30;

	/** The sars data 31. */
	@CSVAnnotation(name = "SARS_DATA_31", className = String.class)
	@Column(name = "SARS_DATA_31")
	private String sarsData31;

	/** The sars data 32. */
	@CSVAnnotation(name = "SARS_DATA_32", className = String.class)
	@Column(name = "SARS_DATA_32")
	private String sarsData32;

	/** The sars data 33. */
	@CSVAnnotation(name = "SARS_DATA_33", className = String.class)
	@Column(name = "SARS_DATA_33")
	private String sarsData33;

	/** The sars data 34. */
	@CSVAnnotation(name = "SARS_DATA_34", className = String.class)
	@Column(name = "SARS_DATA_34")
	private String sarsData34;

	/** The sars data 35. */
	@CSVAnnotation(name = "SARS_DATA_35", className = String.class)
	@Column(name = "SARS_DATA_35")
	private String sarsData35;

	/** The sars data 36. */
	@CSVAnnotation(name = "SARS_DATA_36", className = String.class)
	@Column(name = "SARS_DATA_36")
	private String sarsData36;

	/** The sars data 37. */
	@CSVAnnotation(name = "SARS_DATA_37", className = String.class)
	@Column(name = "SARS_DATA_37")
	private String sarsData37;

	/** The trading status. */
	@CSVAnnotation(name = "TRADING_STATUS", className = String.class)
	@Column(name = "trading_status")
	private String tradingStatus;

	/** The sars data 38. */
	@CSVAnnotation(name = "SARS_DATA_38", className = String.class)
	@Column(name = "SARS_DATA_38")
	private String sarsData38;

	/** The sars data 39. */
	@CSVAnnotation(name = "SARS_DATA_39", className = String.class)
	@Column(name = "SARS_DATA_39")
	private String sarsData39;

	/** The sars data 40. */
	@CSVAnnotation(name = "SARS_DATA_40", className = String.class)
	@Column(name = "SARS_DATA_40")
	private String sarsData40;
	
	@Column(name = "noEmployes")
	private Long noEmployes;
	
	@Column(name= "no_employes_according_to_sars")
	private Integer noEmployesAccordingToSARS;
	
	@Column(name = "employer_post_code")
	private String employerPostCode;
	 
	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	
	public SarsEmployerDetailAllDataFT() {
		super();		
	}
	
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
		SarsEmployerDetailAllDataFT other = (SarsEmployerDetailAllDataFT) obj;
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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the ref no.
	 *
	 * @return the ref no
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * Sets the ref no.
	 *
	 * @param refNo the new ref no
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	/**
	 * Gets the sars data 1.
	 *
	 * @return the sars data 1
	 */
	public String getSarsData1() {
		return sarsData1;
	}

	/**
	 * Sets the sars data 1.
	 *
	 * @param sarsData1 the new sars data 1
	 */
	public void setSarsData1(String sarsData1) {
		this.sarsData1 = sarsData1;
	}

	/**
	 * Gets the sars data 2.
	 *
	 * @return the sars data 2
	 */
	public String getSarsData2() {
		return sarsData2;
	}

	/**
	 * Sets the sars data 2.
	 *
	 * @param sarsData2 the new sars data 2
	 */
	public void setSarsData2(String sarsData2) {
		this.sarsData2 = sarsData2;
	}

	/**
	 * Gets the sars data 3.
	 *
	 * @return the sars data 3
	 */
	public String getSarsData3() {
		return sarsData3;
	}

	/**
	 * Sets the sars data 3.
	 *
	 * @param sarsData3 the new sars data 3
	 */
	public void setSarsData3(String sarsData3) {
		this.sarsData3 = sarsData3;
	}

	/**
	 * Gets the registered name of entity.
	 *
	 * @return the registered name of entity
	 */
	public String getRegisteredNameOfEntity() {
		return registeredNameOfEntity;
	}

	/**
	 * Sets the registered name of entity.
	 *
	 * @param registeredNameOfEntity the new registered name of entity
	 */
	public void setRegisteredNameOfEntity(String registeredNameOfEntity) {
		this.registeredNameOfEntity = registeredNameOfEntity;
	}

	/**
	 * Gets the sars data 4.
	 *
	 * @return the sars data 4
	 */
	public String getSarsData4() {
		return sarsData4;
	}

	/**
	 * Sets the sars data 4.
	 *
	 * @param sarsData4 the new sars data 4
	 */
	public void setSarsData4(String sarsData4) {
		this.sarsData4 = sarsData4;
	}

	/**
	 * Gets the sars data 5.
	 *
	 * @return the sars data 5
	 */
	public String getSarsData5() {
		return sarsData5;
	}

	/**
	 * Sets the sars data 5.
	 *
	 * @param sarsData5 the new sars data 5
	 */
	public void setSarsData5(String sarsData5) {
		this.sarsData5 = sarsData5;
	}

	/**
	 * Gets the sars data 6.
	 *
	 * @return the sars data 6
	 */
	public String getSarsData6() {
		return sarsData6;
	}

	/**
	 * Sets the sars data 6.
	 *
	 * @param sarsData6 the new sars data 6
	 */
	public void setSarsData6(String sarsData6) {
		this.sarsData6 = sarsData6;
	}

	/**
	 * Gets the sars data 7.
	 *
	 * @return the sars data 7
	 */
	public String getSarsData7() {
		return sarsData7;
	}

	/**
	 * Sets the sars data 7.
	 *
	 * @param sarsData7 the new sars data 7
	 */
	public void setSarsData7(String sarsData7) {
		this.sarsData7 = sarsData7;
	}

	/**
	 * Gets the company registration number.
	 *
	 * @return the company registration number
	 */
	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}

	/**
	 * Sets the company registration number.
	 *
	 * @param companyRegistrationNumber the new company registration number
	 */
	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}

	/**
	 * Gets the sars code 1.
	 *
	 * @return the sars code 1
	 */
	public String getSarsCode1() {
		return sarsCode1;
	}

	/**
	 * Sets the sars code 1.
	 *
	 * @param sarsCode1 the new sars code 1
	 */
	public void setSarsCode1(String sarsCode1) {
		this.sarsCode1 = sarsCode1;
	}

	/**
	 * Gets the sars code 2.
	 *
	 * @return the sars code 2
	 */
	public String getSarsCode2() {
		return sarsCode2;
	}

	/**
	 * Sets the sars code 2.
	 *
	 * @param sarsCode2 the new sars code 2
	 */
	public void setSarsCode2(String sarsCode2) {
		this.sarsCode2 = sarsCode2;
	}

	/**
	 * Gets the sars code 3.
	 *
	 * @return the sars code 3
	 */
	public String getSarsCode3() {
		return sarsCode3;
	}

	/**
	 * Sets the sars code 3.
	 *
	 * @param sarsCode3 the new sars code 3
	 */
	public void setSarsCode3(String sarsCode3) {
		this.sarsCode3 = sarsCode3;
	}

	/**
	 * Gets the sars code 4.
	 *
	 * @return the sars code 4
	 */
	public String getSarsCode4() {
		return sarsCode4;
	}

	/**
	 * Sets the sars code 4.
	 *
	 * @param sarsCode4 the new sars code 4
	 */
	public void setSarsCode4(String sarsCode4) {
		this.sarsCode4 = sarsCode4;
	}

	/**
	 * Gets the sars code 5.
	 *
	 * @return the sars code 5
	 */
	public String getSarsCode5() {
		return sarsCode5;
	}

	/**
	 * Sets the sars code 5.
	 *
	 * @param sarsCode5 the new sars code 5
	 */
	public void setSarsCode5(String sarsCode5) {
		this.sarsCode5 = sarsCode5;
	}

	/**
	 * Gets the sars code 6.
	 *
	 * @return the sars code 6
	 */
	public String getSarsCode6() {
		return sarsCode6;
	}

	/**
	 * Sets the sars code 6.
	 *
	 * @param sarsCode6 the new sars code 6
	 */
	public void setSarsCode6(String sarsCode6) {
		this.sarsCode6 = sarsCode6;
	}

	/**
	 * Gets the sars code 7.
	 *
	 * @return the sars code 7
	 */
	public String getSarsCode7() {
		return sarsCode7;
	}

	/**
	 * Sets the sars code 7.
	 *
	 * @param sarsCode7 the new sars code 7
	 */
	public void setSarsCode7(String sarsCode7) {
		this.sarsCode7 = sarsCode7;
	}

	/**
	 * Gets the sars code 8.
	 *
	 * @return the sars code 8
	 */
	public String getSarsCode8() {
		return sarsCode8;
	}

	/**
	 * Sets the sars code 8.
	 *
	 * @param sarsCode8 the new sars code 8
	 */
	public void setSarsCode8(String sarsCode8) {
		this.sarsCode8 = sarsCode8;
	}

	/**
	 * Gets the sars code 9.
	 *
	 * @return the sars code 9
	 */
	public String getSarsCode9() {
		return sarsCode9;
	}

	/**
	 * Sets the sars code 9.
	 *
	 * @param sarsCode9 the new sars code 9
	 */
	public void setSarsCode9(String sarsCode9) {
		this.sarsCode9 = sarsCode9;
	}

	/**
	 * Gets the sars code 10.
	 *
	 * @return the sars code 10
	 */
	public String getSarsCode10() {
		return sarsCode10;
	}

	/**
	 * Sets the sars code 10.
	 *
	 * @param sarsCode10 the new sars code 10
	 */
	public void setSarsCode10(String sarsCode10) {
		this.sarsCode10 = sarsCode10;
	}

	/**
	 * Gets the sars code 11.
	 *
	 * @return the sars code 11
	 */
	public String getSarsCode11() {
		return sarsCode11;
	}

	/**
	 * Sets the sars code 11.
	 *
	 * @param sarsCode11 the new sars code 11
	 */
	public void setSarsCode11(String sarsCode11) {
		this.sarsCode11 = sarsCode11;
	}

	/**
	 * Gets the sars code 12.
	 *
	 * @return the sars code 12
	 */
	public String getSarsCode12() {
		return sarsCode12;
	}

	/**
	 * Sets the sars code 12.
	 *
	 * @param sarsCode12 the new sars code 12
	 */
	public void setSarsCode12(String sarsCode12) {
		this.sarsCode12 = sarsCode12;
	}

	/**
	 * Gets the sars code 13.
	 *
	 * @return the sars code 13
	 */
	public String getSarsCode13() {
		return sarsCode13;
	}

	/**
	 * Sets the sars code 13.
	 *
	 * @param sarsCode13 the new sars code 13
	 */
	public void setSarsCode13(String sarsCode13) {
		this.sarsCode13 = sarsCode13;
	}

	/**
	 * Gets the sars code 14.
	 *
	 * @return the sars code 14
	 */
	public String getSarsCode14() {
		return sarsCode14;
	}

	/**
	 * Sets the sars code 14.
	 *
	 * @param sarsCode14 the new sars code 14
	 */
	public void setSarsCode14(String sarsCode14) {
		this.sarsCode14 = sarsCode14;
	}

	/**
	 * Gets the sars code 15.
	 *
	 * @return the sars code 15
	 */
	public String getSarsCode15() {
		return sarsCode15;
	}

	/**
	 * Sets the sars code 15.
	 *
	 * @param sarsCode15 the new sars code 15
	 */
	public void setSarsCode15(String sarsCode15) {
		this.sarsCode15 = sarsCode15;
	}

	/**
	 * Gets the sars code 16.
	 *
	 * @return the sars code 16
	 */
	public String getSarsCode16() {
		return sarsCode16;
	}

	/**
	 * Sets the sars code 16.
	 *
	 * @param sarsCode16 the new sars code 16
	 */
	public void setSarsCode16(String sarsCode16) {
		this.sarsCode16 = sarsCode16;
	}

	/**
	 * Gets the trading name.
	 *
	 * @return the trading name
	 */
	public String getTradingName() {
		return tradingName;
	}

	/**
	 * Sets the trading name.
	 *
	 * @param tradingName the new trading name
	 */
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	/**
	 * Gets the sars data 8.
	 *
	 * @return the sars data 8
	 */
	public String getSarsData8() {
		return sarsData8;
	}

	/**
	 * Sets the sars data 8.
	 *
	 * @param sarsData8 the new sars data 8
	 */
	public void setSarsData8(String sarsData8) {
		this.sarsData8 = sarsData8;
	}

	/**
	 * Gets the sars data 9.
	 *
	 * @return the sars data 9
	 */
	public String getSarsData9() {
		return sarsData9;
	}

	/**
	 * Sets the sars data 9.
	 *
	 * @param sarsData9 the new sars data 9
	 */
	public void setSarsData9(String sarsData9) {
		this.sarsData9 = sarsData9;
	}

	/**
	 * Gets the sars data 10.
	 *
	 * @return the sars data 10
	 */
	public String getSarsData10() {
		return sarsData10;
	}

	/**
	 * Sets the sars data 10.
	 *
	 * @param sarsData10 the new sars data 10
	 */
	public void setSarsData10(String sarsData10) {
		this.sarsData10 = sarsData10;
	}

	/**
	 * Gets the sars data 11.
	 *
	 * @return the sars data 11
	 */
	public String getSarsData11() {
		return sarsData11;
	}

	/**
	 * Sets the sars data 11.
	 *
	 * @param sarsData11 the new sars data 11
	 */
	public void setSarsData11(String sarsData11) {
		this.sarsData11 = sarsData11;
	}

	/**
	 * Gets the sars data 12.
	 *
	 * @return the sars data 12
	 */
	public String getSarsData12() {
		return sarsData12;
	}

	/**
	 * Sets the sars data 12.
	 *
	 * @param sarsData12 the new sars data 12
	 */
	public void setSarsData12(String sarsData12) {
		this.sarsData12 = sarsData12;
	}

	/**
	 * Gets the sars data 13.
	 *
	 * @return the sars data 13
	 */
	public String getSarsData13() {
		return sarsData13;
	}

	/**
	 * Sets the sars data 13.
	 *
	 * @param sarsData13 the new sars data 13
	 */
	public void setSarsData13(String sarsData13) {
		this.sarsData13 = sarsData13;
	}

	/**
	 * Gets the sars data 14.
	 *
	 * @return the sars data 14
	 */
	public String getSarsData14() {
		return sarsData14;
	}

	/**
	 * Sets the sars data 14.
	 *
	 * @param sarsData14 the new sars data 14
	 */
	public void setSarsData14(String sarsData14) {
		this.sarsData14 = sarsData14;
	}

	/**
	 * Gets the sars data 15.
	 *
	 * @return the sars data 15
	 */
	public String getSarsData15() {
		return sarsData15;
	}

	/**
	 * Sets the sars data 15.
	 *
	 * @param sarsData15 the new sars data 15
	 */
	public void setSarsData15(String sarsData15) {
		this.sarsData15 = sarsData15;
	}

	/**
	 * Gets the sars data 16.
	 *
	 * @return the sars data 16
	 */
	public String getSarsData16() {
		return sarsData16;
	}

	/**
	 * Sets the sars data 16.
	 *
	 * @param sarsData16 the new sars data 16
	 */
	public void setSarsData16(String sarsData16) {
		this.sarsData16 = sarsData16;
	}

	/**
	 * Gets the sars data 17.
	 *
	 * @return the sars data 17
	 */
	public String getSarsData17() {
		return sarsData17;
	}

	/**
	 * Sets the sars data 17.
	 *
	 * @param sarsData17 the new sars data 17
	 */
	public void setSarsData17(String sarsData17) {
		this.sarsData17 = sarsData17;
	}

	/**
	 * Gets the sars data 18.
	 *
	 * @return the sars data 18
	 */
	public String getSarsData18() {
		return sarsData18;
	}

	/**
	 * Sets the sars data 18.
	 *
	 * @param sarsData18 the new sars data 18
	 */
	public void setSarsData18(String sarsData18) {
		this.sarsData18 = sarsData18;
	}

	/**
	 * Gets the sars data 19.
	 *
	 * @return the sars data 19
	 */
	public String getSarsData19() {
		return sarsData19;
	}

	/**
	 * Sets the sars data 19.
	 *
	 * @param sarsData19 the new sars data 19
	 */
	public void setSarsData19(String sarsData19) {
		this.sarsData19 = sarsData19;
	}

	/**
	 * Gets the sars data 20.
	 *
	 * @return the sars data 20
	 */
	public String getSarsData20() {
		return sarsData20;
	}

	/**
	 * Sets the sars data 20.
	 *
	 * @param sarsData20 the new sars data 20
	 */
	public void setSarsData20(String sarsData20) {
		this.sarsData20 = sarsData20;
	}

	/**
	 * Gets the sars data 21.
	 *
	 * @return the sars data 21
	 */
	public String getSarsData21() {
		return sarsData21;
	}

	/**
	 * Sets the sars data 21.
	 *
	 * @param sarsData21 the new sars data 21
	 */
	public void setSarsData21(String sarsData21) {
		this.sarsData21 = sarsData21;
	}

	/**
	 * Gets the sars data 22.
	 *
	 * @return the sars data 22
	 */
	public String getSarsData22() {
		return sarsData22;
	}

	/**
	 * Sets the sars data 22.
	 *
	 * @param sarsData22 the new sars data 22
	 */
	public void setSarsData22(String sarsData22) {
		this.sarsData22 = sarsData22;
	}

	/**
	 * Gets the sars data 23.
	 *
	 * @return the sars data 23
	 */
	public String getSarsData23() {
		return sarsData23;
	}

	/**
	 * Sets the sars data 23.
	 *
	 * @param sarsData23 the new sars data 23
	 */
	public void setSarsData23(String sarsData23) {
		this.sarsData23 = sarsData23;
	}

	/**
	 * Gets the sars data 24.
	 *
	 * @return the sars data 24
	 */
	public String getSarsData24() {
		return sarsData24;
	}

	/**
	 * Sets the sars data 24.
	 *
	 * @param sarsData24 the new sars data 24
	 */
	public void setSarsData24(String sarsData24) {
		this.sarsData24 = sarsData24;
	}

	/**
	 * Gets the sars data 25.
	 *
	 * @return the sars data 25
	 */
	public String getSarsData25() {
		return sarsData25;
	}

	/**
	 * Sets the sars data 25.
	 *
	 * @param sarsData25 the new sars data 25
	 */
	public void setSarsData25(String sarsData25) {
		this.sarsData25 = sarsData25;
	}

	/**
	 * Gets the sic code 2.
	 *
	 * @return the sic code 2
	 */
	public String getSicCode2() {
		return sicCode2;
	}

	/**
	 * Sets the sic code 2.
	 *
	 * @param sicCode2 the new sic code 2
	 */
	public void setSicCode2(String sicCode2) {
		this.sicCode2 = sicCode2;
	}

	/**
	 * Gets the sars data 26.
	 *
	 * @return the sars data 26
	 */
	public String getSarsData26() {
		return sarsData26;
	}

	/**
	 * Sets the sars data 26.
	 *
	 * @param sarsData26 the new sars data 26
	 */
	public void setSarsData26(String sarsData26) {
		this.sarsData26 = sarsData26;
	}

	/**
	 * Gets the sars data 27.
	 *
	 * @return the sars data 27
	 */
	public String getSarsData27() {
		return sarsData27;
	}

	/**
	 * Sets the sars data 27.
	 *
	 * @param sarsData27 the new sars data 27
	 */
	public void setSarsData27(String sarsData27) {
		this.sarsData27 = sarsData27;
	}

	/**
	 * Gets the sars data 28.
	 *
	 * @return the sars data 28
	 */
	public String getSarsData28() {
		return sarsData28;
	}

	/**
	 * Sets the sars data 28.
	 *
	 * @param sarsData28 the new sars data 28
	 */
	public void setSarsData28(String sarsData28) {
		this.sarsData28 = sarsData28;
	}

	/**
	 * Gets the sars data 29.
	 *
	 * @return the sars data 29
	 */
	public String getSarsData29() {
		return sarsData29;
	}

	/**
	 * Sets the sars data 29.
	 *
	 * @param sarsData29 the new sars data 29
	 */
	public void setSarsData29(String sarsData29) {
		this.sarsData29 = sarsData29;
	}

	/**
	 * Gets the sars data 30.
	 *
	 * @return the sars data 30
	 */
	public String getSarsData30() {
		return sarsData30;
	}

	/**
	 * Sets the sars data 30.
	 *
	 * @param sarsData30 the new sars data 30
	 */
	public void setSarsData30(String sarsData30) {
		this.sarsData30 = sarsData30;
	}

	/**
	 * Gets the sars data 31.
	 *
	 * @return the sars data 31
	 */
	public String getSarsData31() {
		return sarsData31;
	}

	/**
	 * Sets the sars data 31.
	 *
	 * @param sarsData31 the new sars data 31
	 */
	public void setSarsData31(String sarsData31) {
		this.sarsData31 = sarsData31;
	}

	/**
	 * Gets the sars data 32.
	 *
	 * @return the sars data 32
	 */
	public String getSarsData32() {
		return sarsData32;
	}

	/**
	 * Sets the sars data 32.
	 *
	 * @param sarsData32 the new sars data 32
	 */
	public void setSarsData32(String sarsData32) {
		this.sarsData32 = sarsData32;
	}

	/**
	 * Gets the sars data 33.
	 *
	 * @return the sars data 33
	 */
	public String getSarsData33() {
		return sarsData33;
	}

	/**
	 * Sets the sars data 33.
	 *
	 * @param sarsData33 the new sars data 33
	 */
	public void setSarsData33(String sarsData33) {
		this.sarsData33 = sarsData33;
	}

	/**
	 * Gets the sars data 34.
	 *
	 * @return the sars data 34
	 */
	public String getSarsData34() {
		return sarsData34;
	}

	/**
	 * Sets the sars data 34.
	 *
	 * @param sarsData34 the new sars data 34
	 */
	public void setSarsData34(String sarsData34) {
		this.sarsData34 = sarsData34;
	}

	/**
	 * Gets the sars data 35.
	 *
	 * @return the sars data 35
	 */
	public String getSarsData35() {
		return sarsData35;
	}

	/**
	 * Sets the sars data 35.
	 *
	 * @param sarsData35 the new sars data 35
	 */
	public void setSarsData35(String sarsData35) {
		this.sarsData35 = sarsData35;
	}

	/**
	 * Gets the sars data 36.
	 *
	 * @return the sars data 36
	 */
	public String getSarsData36() {
		return sarsData36;
	}

	/**
	 * Sets the sars data 36.
	 *
	 * @param sarsData36 the new sars data 36
	 */
	public void setSarsData36(String sarsData36) {
		this.sarsData36 = sarsData36;
	}

	/**
	 * Gets the sars data 37.
	 *
	 * @return the sars data 37
	 */
	public String getSarsData37() {
		return sarsData37;
	}

	/**
	 * Sets the sars data 37.
	 *
	 * @param sarsData37 the new sars data 37
	 */
	public void setSarsData37(String sarsData37) {
		this.sarsData37 = sarsData37;
	}

	/**
	 * Gets the trading status.
	 *
	 * @return the trading status
	 */
	public String getTradingStatus() {
		return tradingStatus;
	}

	/**
	 * Sets the trading status.
	 *
	 * @param tradingStatus the new trading status
	 */
	public void setTradingStatus(String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}

	/**
	 * Gets the sars data 38.
	 *
	 * @return the sars data 38
	 */
	public String getSarsData38() {
		return sarsData38;
	}

	/**
	 * Sets the sars data 38.
	 *
	 * @param sarsData38 the new sars data 38
	 */
	public void setSarsData38(String sarsData38) {
		this.sarsData38 = sarsData38;
	}

	/**
	 * Gets the sars data 39.
	 *
	 * @return the sars data 39
	 */
	public String getSarsData39() {
		return sarsData39;
	}

	/**
	 * Sets the sars data 39.
	 *
	 * @param sarsData39 the new sars data 39
	 */
	public void setSarsData39(String sarsData39) {
		this.sarsData39 = sarsData39;
	}

	/**
	 * Gets the sars data 40.
	 *
	 * @return the sars data 40
	 */
	public String getSarsData40() {
		return sarsData40;
	}

	/**
	 * Sets the sars data 40.
	 *
	 * @param sarsData40 the new sars data 40
	 */
	public void setSarsData40(String sarsData40) {
		this.sarsData40 = sarsData40;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SarsEmployerDetail [id=" + id + ", refNo=" + refNo + ", registeredNameOfEntity="
				+ registeredNameOfEntity + ", companyRegistrationNumber=" + companyRegistrationNumber + ", tradingName="
				+ tradingName + ", sicCode2=" + sicCode2 + ", tradingStatus=" + tradingStatus + "]";
	}

	/**
	 * Gets the sars files.
	 *
	 * @return the sars files
	 */
	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	/**
	 * Sets the sars files.
	 *
	 * @param sarsFiles the new sars files
	 */
	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getNoEmployes() {
		return noEmployes;
	}

	public void setNoEmployes(Long noEmployes) {
		this.noEmployes = noEmployes;
	}


	public Integer getNoEmployesAccordingToSARS() {
		return noEmployesAccordingToSARS;
	}


	public void setNoEmployesAccordingToSARS(Integer noEmployesAccordingToSARS) {
		this.noEmployesAccordingToSARS = noEmployesAccordingToSARS;
	}


	public String getEmployerPostCode() {
		return employerPostCode;
	}


	public void setEmployerPostCode(String employerPostCode) {
		this.employerPostCode = employerPostCode;
	}

	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}

	
}
