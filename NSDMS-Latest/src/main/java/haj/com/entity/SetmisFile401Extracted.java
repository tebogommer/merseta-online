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

@Entity
@Table(name = "setmis_file_401_extracted")
public class SetmisFile401Extracted implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "line_number")
	private String lineNumber;
	
	@CSVAnnotation(length = 15)
	@Column(name = "nationalid")
	private String nationalId;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personalternateid")
	private String personAlternateId;
	
	@CSVAnnotation(length = 3)
	@Column(name = "alternativeidtype")
	private String alternativeIdType;
	
	@CSVAnnotation(length = 5)
	@Column(name = "designationid")
	private String designationId;
	
	@CSVAnnotation(length = 20)
	@Column(name = "designationregistrationnumber")
	private String designationRegistrationNumber;
	
	@CSVAnnotation(length = 10)
	@Column(name = "designationetqeid")
	private String designationETQEId;
	
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	@Column(name = "designationstartdatestring")
	private String designationStartDateString;
//	private Date designationStartDate;
	
	@CSVAnnotation(length = 8)
	@Column(name = "designationenddatestring")
	private String designationEndDateString;
//	private Date designationEndDate;
	
	@CSVAnnotation(length = 10)
	@Column(name = "designationstructurestatusid")
	private String designationStructureStatusId;
	
	@CSVAnnotation(length = 20)
	@Column(name = "etqedecisionnumber")
	private String eTQEDecisionNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	@CSVAnnotation(length = 10)
	@Column(name = "filler01")
	private String filler01;
	
	@CSVAnnotation(length = 10)
	@Column(name = "filler02")
	private String filler02;
	
	@CSVAnnotation(length = 10)
	@Column(name = "filler03")
	private String filler03;
	
	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
//	private Date dateStamp;
	
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetmisFile401Extracted other = (SetmisFile401Extracted) obj;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
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

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getDesignationRegistrationNumber() {
		return designationRegistrationNumber;
	}

	public void setDesignationRegistrationNumber(String designationRegistrationNumber) {
		this.designationRegistrationNumber = designationRegistrationNumber;
	}

	public String getDesignationETQEId() {
		return designationETQEId;
	}

	public void setDesignationETQEId(String designationETQEId) {
		this.designationETQEId = designationETQEId;
	}

	public String getDesignationStartDateString() {
		return designationStartDateString;
	}

	public void setDesignationStartDateString(String designationStartDateString) {
		this.designationStartDateString = designationStartDateString;
	}

	public String getDesignationEndDateString() {
		return designationEndDateString;
	}

	public void setDesignationEndDateString(String designationEndDateString) {
		this.designationEndDateString = designationEndDateString;
	}

	public String getDesignationStructureStatusId() {
		return designationStructureStatusId;
	}

	public void setDesignationStructureStatusId(String designationStructureStatusId) {
		this.designationStructureStatusId = designationStructureStatusId;
	}

	public String geteTQEDecisionNumber() {
		return eTQEDecisionNumber;
	}

	public void seteTQEDecisionNumber(String eTQEDecisionNumber) {
		this.eTQEDecisionNumber = eTQEDecisionNumber;
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

	public String getFiller03() {
		return filler03;
	}

	public void setFiller03(String filler03) {
		this.filler03 = filler03;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

}