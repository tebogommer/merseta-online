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
 * SetmisFile506.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_506_extracted")
public class SetmisFile506Extracted implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile506. */
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

	/** * National_Id * Required: Y */
	@CSVAnnotation(length = 15)
	@Column(name = "nationalId")
	private String nationalId;
	
	/** * Person_Alternative_Id * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "personAlternateId")
	private String personAlternateId;
	
	/** * Aternative_Id_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "alternativeIdType")
	private String alternativeIdType;
	
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "qualificationid")
	private String qualificationId;
	
	/** * Qualification_Achievement_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "qualificationachievementdatestring")
	private String qualificationAchievementDateString;
	//private Date qualificationAchievementDate;
	
	/** * Internship_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "internshipstatusid")
	private String internshipStatusId;
	
	/** * Start_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "startdatestring")
	private String startDateString;
	//private Date startDate;
	
	/** * End_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "enddate")
	private String endDateString;
	//private Date endDate;
	
	/** * SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "sdlno")
	private String sDLNo;
	
	/** * Site_No * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "siteno")
	private String siteNo;
	
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "fundingid")
	private String fundingId;
	
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "cumulativespend")
	private String cumulativeSpend;
	
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	@Column(name = "ofocode")
	private String oFOCode;
	
	/** * Urban_Rural_ID * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "urbanruralid")
	private String urbanRuralId;
	
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
	//private Date dateStamp;
	
	
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
		SetmisFile506Extracted other = (SetmisFile506Extracted) obj;
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

	public Date getCreateDate() {
		return createDate;
	}

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

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getQualificationAchievementDateString() {
		return qualificationAchievementDateString;
	}

	public void setQualificationAchievementDateString(String qualificationAchievementDateString) {
		this.qualificationAchievementDateString = qualificationAchievementDateString;
	}

	public String getInternshipStatusId() {
		return internshipStatusId;
	}

	public void setInternshipStatusId(String internshipStatusId) {
		this.internshipStatusId = internshipStatusId;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getsDLNo() {
		return sDLNo;
	}

	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
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

	public String getFundingId() {
		return fundingId;
	}

	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}

	public String getCumulativeSpend() {
		return cumulativeSpend;
	}

	public void setCumulativeSpend(String cumulativeSpend) {
		this.cumulativeSpend = cumulativeSpend;
	}

	public String getoFOCode() {
		return oFOCode;
	}

	public void setoFOCode(String oFOCode) {
		this.oFOCode = oFOCode;
	}

	public String getUrbanRuralId() {
		return urbanRuralId;
	}

	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

	

	
}
