package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * RejectReasons.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "reject_reasons")
public class RejectReasons extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of RejectReasons. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of RejectReasons. */
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "how_to", length = 500)
	private String howTo;

	/** Description of RejectReasons. */
	@Column(name = "for_process", length = 500)
	private ConfigDocProcessEnum forProcess;

	@Transient
	private String rejectReasonS;
	
	@Column(name = "for_crm")
	private Boolean forCrm;
	
	@Column(name = "for_manager")
	private Boolean forManager;
	
	@Column(name = "for_executive ")
	private Boolean forExecutive ;
	
	@Column(name = "for_senior_manager")
	private Boolean forSeniorManager;
	
	@Column(name = "for_clo")
	private Boolean forclo;
	
	@Column(name = "soft_deleted", columnDefinition = "BIT default false")
	private Boolean softDeleted;
	
	@Transient
	private String additionalInformation;
	
	@Transient
	private Date rejectDate;

	/**
	 * Instantiates a new reject reasons.
	 */
	public RejectReasons() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		RejectReasons other = (RejectReasons) obj;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the for process.
	 *
	 * @return the for process
	 */
	public ConfigDocProcessEnum getForProcess() {
		return forProcess;
	}

	/**
	 * Sets the for process.
	 *
	 * @param forProcess
	 *            the new for process
	 */
	public void setForProcess(ConfigDocProcessEnum forProcess) {
		this.forProcess = forProcess;
	}

	public String getHowTo() {
		return howTo;
	}

	public void setHowTo(String howTo) {
		this.howTo = howTo;
	}

	public String getRejectReasonS() {
		return rejectReasonS;
	}

	public void setRejectReasonS(String rejectReasonS) {
		this.rejectReasonS = rejectReasonS;
	}

	public Boolean getForCrm() {
		return forCrm;
	}

	public void setForCrm(Boolean forCrm) {
		this.forCrm = forCrm;
	}

	public Boolean getForSeniorManager() {
		return forSeniorManager;
	}

	public void setForSeniorManager(Boolean forSeniorManager) {
		this.forSeniorManager = forSeniorManager;
	}

	public Boolean getForManager() {
		return forManager;
	}

	public void setForManager(Boolean forManager) {
		this.forManager = forManager;
	}

	public Boolean getForExecutive() {
		return forExecutive;
	}

	public void setForExecutive(Boolean forExecutive) {
		this.forExecutive = forExecutive;
	}

	public Boolean getForclo() {
		return forclo;
	}

	public void setForclo(Boolean forclo) {
		this.forclo = forclo;
	}

	public Boolean getSoftDeleted() {
		return softDeleted;
	}

	public void setSoftDeleted(Boolean softDeleted) {
		this.softDeleted = softDeleted;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Date getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(Date rejectDate) {
		this.rejectDate = rejectDate;
	}

}
