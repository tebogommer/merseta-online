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
@Table(name = "setmis_file_506")
public class SetmisFile506 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SetmisFile506. */
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
	
	@Column(name = "person_alternative_id")
	private String personAlternativeId;
	
	@Column(name = "aternative_id_type_id")
	private String aternativeIdTypeId;
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
	@Column(name = "qualification_achievement_date")
	private Date qualificationAchievementDate;
	
	@Column(name = "internship_status_id")
	private String internshipStatusId;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "sdl_no")
	private String sDLNo;
	
	@Column(name = "site_no")
	private String siteNo;
	
	@Column(name = "provider_code")
	private String providerCode;
	
	@Column(name = "provider_etqe_id")
	private String providerETQEId;
	
	@Column(name = "funding_id")
	private String fundingId;
	
	@Column(name = "cumulative_spend")
	private String cumulativeSpend;
	
	@Column(name = "ofo_code")
	private String oFOCode;
	
	@Column(name = "urban_rural_id")
	private String urbanRuralID;
	
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
		SetmisFile506 other = (SetmisFile506) obj;
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

	public Date getQualificationAchievementDate() {
		return qualificationAchievementDate;
	}

	public void setQualificationAchievementDate(Date qualificationAchievementDate) {
		this.qualificationAchievementDate = qualificationAchievementDate;
	}

	public String getInternshipStatusId() {
		return internshipStatusId;
	}

	public void setInternshipStatusId(String internshipStatusId) {
		this.internshipStatusId = internshipStatusId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getUrbanRuralID() {
		return urbanRuralID;
	}

	public void setUrbanRuralID(String urbanRuralID) {
		this.urbanRuralID = urbanRuralID;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
