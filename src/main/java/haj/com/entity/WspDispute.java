package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_dispute")
public class WspDispute implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The create user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;

	/** The reson for dispute. */
	@Column(name = "reson_for_dispute", columnDefinition = "LONGTEXT")
	private String resonForDispute;

	/** The agreement of grants. */
	@Column(name = "agreement_of_grants")
	private Boolean agreementOfGrants;

	/** The agreement of grants. */
	@Column(name = "no_agreement")
	private Boolean noAgreement;

	/** The mediation date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mediation_date", length = 19)
	private Date mediationDate;

	@Column(name = "resolved")
	private Boolean resolved;

	/** The reson for dispute. */
	@Column(name = "venue", columnDefinition = "LONGTEXT")
	private String venue;

	@Column(name = "summary_no_agreement", columnDefinition = "LONGTEXT")
	private String summaryNoAgreement;

	@Column(name = "labour_rep", columnDefinition = "LONGTEXT")
	private String labourRep;

	@Column(name = "employer_rep", columnDefinition = "LONGTEXT")
	private String employerRep;

	@Transient
	private List<Signoff> signOffs;

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
		WspDispute other = (WspDispute) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new wsp dispute.
	 *
	 * @param wsp
	 *            the wsp
	 * @param user
	 *            the user
	 * @param disputeReason
	 *            the dispute reason
	 */
	public WspDispute(Wsp wsp, Users user, String disputeReason) {
		super();
		this.wsp = wsp;
		this.createUser = user;
		this.resonForDispute = disputeReason;
	}

	/**
	 * Instantiates a new wsp dispute.
	 */
	public WspDispute() {
		super();
		// TODO Auto-generated constructor stub
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the creates the user.
	 *
	 * @return the creates the user
	 */
	public Users getCreateUser() {
		return createUser;
	}

	/**
	 * Sets the creates the user.
	 *
	 * @param createUser
	 *            the new creates the user
	 */
	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	/**
	 * Gets the reson for dispute.
	 *
	 * @return the reson for dispute
	 */
	public String getResonForDispute() {
		return resonForDispute;
	}

	/**
	 * Sets the reson for dispute.
	 *
	 * @param resonForDispute
	 *            the new reson for dispute
	 */
	public void setResonForDispute(String resonForDispute) {
		this.resonForDispute = resonForDispute;
	}

	/**
	 * Gets the agreement of grants.
	 *
	 * @return the agreement of grants
	 */
	public Boolean getAgreementOfGrants() {
		return agreementOfGrants;
	}

	/**
	 * Sets the agreement of grants.
	 *
	 * @param agreementOfGrants
	 *            the new agreement of grants
	 */
	public void setAgreementOfGrants(Boolean agreementOfGrants) {
		this.agreementOfGrants = agreementOfGrants;
	}

	/**
	 * Gets the mediation date.
	 *
	 * @return the mediation date
	 */
	public Date getMediationDate() {
		return mediationDate;
	}

	/**
	 * Sets the mediation date.
	 *
	 * @param mediationDate
	 *            the new mediation date
	 */
	public void setMediationDate(Date mediationDate) {
		this.mediationDate = mediationDate;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Boolean getNoAgreement() {
		return noAgreement;
	}

	public void setNoAgreement(Boolean noAgreement) {
		this.noAgreement = noAgreement;
	}

	public String getSummaryNoAgreement() {
		return summaryNoAgreement;
	}

	public void setSummaryNoAgreement(String summaryNoAgreement) {
		this.summaryNoAgreement = summaryNoAgreement;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}

	public String getLabourRep() {
		return labourRep;
	}

	public void setLabourRep(String labourRep) {
		this.labourRep = labourRep;
	}

	public String getEmployerRep() {
		return employerRep;
	}

	public void setEmployerRep(String employerRep) {
		this.employerRep = employerRep;
	}
}
