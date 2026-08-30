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
 * SetmisFile505.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "setmis_file_505_extracted")
public class SetmisFile505Extracted implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile505. */
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
	@Column(name = "nationalid")
	private String nationalId;
	
	/** * Person_Alternative_Id * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "personalternativeid")
	private String personAlternativeId;
	
	/** * Aternative_Id_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	@Column(name = "aternativeidtypeid")
	private String aternativeIdTypeId;
	
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "qualificationid")
	private String qualificationId;
	
	/** * Trade_Test_Number * Required: Y */
	@CSVAnnotation(length = 2)
	@Column(name = "tradetestnumber")
	private String tradeTestNumber;
	
	/** * Trade_Test_Result_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "tradetestresultid")
	private String tradeTestResultId;
	
	/** * Trade_Test_Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	@Column(name = "tradetestprovidercode")
	private String tradeTestProviderCode;
	
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "assessorregistrationnumber")
	private String assessorRegistrationNumber;
	
	/** * Moderator_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "moderatorregistrationnumber")
	private String moderatorRegistrationNumber;
	
	/** * Trade_Test_Date * Required: Y */
	@CSVAnnotation(length = 8)
	@Column(name = "tradetestdate")
	private String tradeTestDate;
	
	/** * Trade_Test_Result_Reason_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "tradetestresultreasonid")
	private String tradeTestResultReasonId;
	
	/** * Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	/** * Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	/** * Trade_Test_Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	@Column(name = "tradetestprovideretqeid")
	private String tradeTestProviderETQEId;
	
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "assessoretqeid")
	private String assessorETQEId;
	
	/** * Moderator_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	@Column(name = "moderatoretqeid")
	private String moderatorETQEId;
	
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
		SetmisFile505Extracted other = (SetmisFile505Extracted) obj;
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

	public String getPersonAlternativeId() {
		return personAlternativeId;
	}

	public void setPersonAlternativeId(String personAlternativeId) {
		this.personAlternativeId = personAlternativeId;
	}

	public String getAternativeIdTypeId() {
		return aternativeIdTypeId;
	}

	public void setAternativeIdTypeId(String aternativeIdTypeId) {
		this.aternativeIdTypeId = aternativeIdTypeId;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getTradeTestNumber() {
		return tradeTestNumber;
	}

	public void setTradeTestNumber(String tradeTestNumber) {
		this.tradeTestNumber = tradeTestNumber;
	}

	public String getTradeTestResultId() {
		return tradeTestResultId;
	}

	public void setTradeTestResultId(String tradeTestResultId) {
		this.tradeTestResultId = tradeTestResultId;
	}

	public String getTradeTestProviderCode() {
		return tradeTestProviderCode;
	}

	public void setTradeTestProviderCode(String tradeTestProviderCode) {
		this.tradeTestProviderCode = tradeTestProviderCode;
	}

	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}

	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}

	public String getModeratorRegistrationNumber() {
		return moderatorRegistrationNumber;
	}

	public void setModeratorRegistrationNumber(String moderatorRegistrationNumber) {
		this.moderatorRegistrationNumber = moderatorRegistrationNumber;
	}

	public String getTradeTestDate() {
		return tradeTestDate;
	}

	public void setTradeTestDate(String tradeTestDate) {
		this.tradeTestDate = tradeTestDate;
	}

	public String getTradeTestResultReasonId() {
		return tradeTestResultReasonId;
	}

	public void setTradeTestResultReasonId(String tradeTestResultReasonId) {
		this.tradeTestResultReasonId = tradeTestResultReasonId;
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

	public String getTradeTestProviderETQEId() {
		return tradeTestProviderETQEId;
	}

	public void setTradeTestProviderETQEId(String tradeTestProviderETQEId) {
		this.tradeTestProviderETQEId = tradeTestProviderETQEId;
	}

	public String getAssessorETQEId() {
		return assessorETQEId;
	}

	public void setAssessorETQEId(String assessorETQEId) {
		this.assessorETQEId = assessorETQEId;
	}

	public String getModeratorETQEId() {
		return moderatorETQEId;
	}

	public void setModeratorETQEId(String moderatorETQEId) {
		this.moderatorETQEId = moderatorETQEId;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}

	
}
