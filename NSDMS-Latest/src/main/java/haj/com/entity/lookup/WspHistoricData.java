package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * WspHistoricData.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_historic_data")
public class WspHistoricData extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/* Referenceno */
	@Column(name = "reference_no")
	private String referenceNo;

	/* Organistaion Name Trade */
	@Column(name = "organistaion_name_trade")
	private String organistaionNameTrade;

	/* Organisation Name Legal */
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/* Grant Name revised */
	@Column(name = "grant_name_revised")
	private String grantNameRevised;

	/* Levy Year */
	@Column(name = "levy_year")
	private Integer levyYear;

	/* FINAL STATUS */
	@Column(name = "final_status")
	private String finalStatus;

	@Enumerated
	@Column(name = "wsp_status_enum")
	private WspStatusEnum wspStatusEnum;
	
	/**
	 * Instantiates a new abet band.
	 */
	public WspHistoricData() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		WspHistoricData other = (WspHistoricData) obj;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getOrganistaionNameTrade() {
		return organistaionNameTrade;
	}

	public void setOrganistaionNameTrade(String organistaionNameTrade) {
		this.organistaionNameTrade = organistaionNameTrade;
	}

	public String getOrganisationNameLegal() {
		return organisationNameLegal;
	}

	public void setOrganisationNameLegal(String organisationNameLegal) {
		this.organisationNameLegal = organisationNameLegal;
	}

	public String getGrantNameRevised() {
		return grantNameRevised;
	}

	public void setGrantNameRevised(String grantNameRevised) {
		this.grantNameRevised = grantNameRevised;
	}

	public Integer getLevyYear() {
		return levyYear;
	}

	public void setLevyYear(Integer levyYear) {
		this.levyYear = levyYear;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public WspStatusEnum getWspStatusEnum() {
		return wspStatusEnum;
	}

	public void setWspStatusEnum(WspStatusEnum wspStatusEnum) {
		this.wspStatusEnum = wspStatusEnum;
	}

}
