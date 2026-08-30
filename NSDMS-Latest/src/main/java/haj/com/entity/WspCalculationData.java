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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.lookup.Funding;
import haj.com.framework.IDataEntity;
import haj.com.service.CompanyService;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_calculation_data")
public class WspCalculationData implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@CSVLookupAnnotation(className=CompanyService.class, method="searchBySDLNoJoins", paramClass=String.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** Note. */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField="company")
	@Column(name = "levy_number", length = 255)
	private String levyNumber;

	@CSVLookupAnnotation(className=ResolveByCodeLookupDAO.class, method="findOfoCode", paramClass=String.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;
	
	@CSVAnnotation(name = "OfoCode", className = String.class, lookupField="ofoCodes")
	@Column(name = "ofo_code", length = 255)
	private String ofoCode;
	
	@CSVAnnotation(name = "Description", className = String.class)
	@Column(name = "description")
	private String description;
	
	@CSVAnnotation(name = "Training_Programme_Description", className = String.class)
	@Column(name = "training_description")
	private String trainingDescription;
	
	@CSVAnnotation(name = "NQFLevelBandDesc", className = String.class)
	@Column(name = "nqf_level_band_desc")
	private String nqfLevelBandDesc;


	@CSVLookupAnnotation(className=ResolveByCodeLookupDAO.class, method="findFunding", paramClass=String.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "funding_id", nullable = true)
	private Funding funding;
	
	@CSVAnnotation(name = "Funding_Method", className = String.class, lookupField="funding")
	@Column(name = "funding_method")
	private String fundingMethod;
	
	@CSVAnnotation(name = "StartDate", className = Date.class, datePattern = "d/MM/yy")
	@Column(name = "start_date")
	private Date startDate;

	@CSVAnnotation(name = "EndDate", className = Date.class, datePattern = "d/MM/yy")
	@Column(name = "end_date")
	private Date endDate;
	
	@CSVAnnotation(name = "Male", className = Integer.class)
	@Column(name = "male")
	private Integer male;

	@CSVAnnotation(name = "Female", className = Integer.class)
	@Column(name = "female")
	private Integer female;
	
	@Column(name = "total_learners")
	private Integer totalLearners;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		WspCalculationData other = (WspCalculationData) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrainingDescription() {
		return trainingDescription;
	}

	public void setTrainingDescription(String trainingDescription) {
		this.trainingDescription = trainingDescription;
	}

	public String getNqfLevelBandDesc() {
		return nqfLevelBandDesc;
	}

	public void setNqfLevelBandDesc(String nqfLevelBandDesc) {
		this.nqfLevelBandDesc = nqfLevelBandDesc;
	}

	public String getFundingMethod() {
		return fundingMethod;
	}

	public void setFundingMethod(String fundingMethod) {
		this.fundingMethod = fundingMethod;
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

	public Integer getMale() {
		return male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getTotalLearners() {
		return totalLearners;
	}

	public void setTotalLearners(Integer totalLearners) {
		this.totalLearners = totalLearners;
	}

	public Funding getFunding() {
		return funding;
	}

	public void setFunding(Funding funding) {
		this.funding = funding;
	}

}
