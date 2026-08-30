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
@Table(name = "setmis_file_505")
public class SetmisFile505 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile505. */
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
	
	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "person_alternate_id")
	private String personAlternateId;
	
	@Column(name = "alternative_id_type")
	private String alternativeIdType;
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
	@Column(name = "trade_test_number")
	private String tradeTestNumber;

	@Column(name = "trade_test_result_id")
	private String tradeTestResultId;
	
	@Column(name = "trade_test_provider_code")
	private String tradeTestProviderCode;
	
	@Column(name = "assessor_registration_number")
	private String assessorRegistrationNumber;
	
	@Column(name = "moderator_registration_number")
	private String moderatorRegistrationNumber;
	
	@Column(name = "trade_test_date")
	private String tradeTestDate;
	
	@Column(name = "trade_test_result_reason_id")
	private String tradeTestResultReasonId;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "trade_test_provider_etqe_id")
	private String tradeTestProviderETQEId;
	
	@Column(name = "assessor_etqe_id")
	private String assessorETQEId;
	
	@Column(name = "moderator_etqe_id")
	private String moderatorETQEId;

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
		SetmisFile505 other = (SetmisFile505) obj;
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

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
