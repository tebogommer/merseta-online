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

import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.IDataEntity;
// TODO: Auto-generated Javadoc

/**
 * RejectReasonsChild.
 *
 * @author jonathanbowett
 */
@Entity
@Table(name = "reject_reasons_child")
public class RejectReasonsChild implements IDataEntity {

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

	/** The reject reasons. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reject_reasons_id", nullable = true)
	private RejectReasons rejectReasons;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banking_details_id", nullable = true)
	private BankingDetails bankingDetails;

	/** The user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tasks_id", nullable = true)
	private Tasks tasks;

	/** The additional information. */
	@Column(name = "additional_information", columnDefinition = "LONGTEXT")
	private String additionalInformation;

	/** AssessorModeratorApplication. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_moderator_application_id", nullable = true)
	private AssessorModeratorApplication assessorModeratorApplication;

	@Column(name = "target_class")
	private String targetClass;

	@Column(name = "target_key")
	private Long targetKey;

	/**
	 * Instantiates a new reject reasons child.
	 */
	public RejectReasonsChild() {
		super();
	}

	/**
	 * Instantiates a new reject reasons child.
	 *
	 * @param rejectReasons
	 *            the reject reasons
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @param additionalInformation
	 *            the additional information
	 */
	public RejectReasonsChild(RejectReasons rejectReasons, Company company, Users user, Tasks tasks, String additionalInformation) {
		super();
		this.rejectReasons = rejectReasons;
		this.company = company;
		this.user = user;
		this.tasks = tasks;
		this.additionalInformation = additionalInformation;
	}

	public RejectReasonsChild(RejectReasons rejectReasons, Tasks tasks, String targetClass, Long targetKey) {
		super();
		this.rejectReasons = rejectReasons;
		this.tasks = tasks;
		this.targetClass = targetClass;
		this.targetKey = targetKey;
	}

	public RejectReasonsChild(RejectReasons rejectReasons, Company company, BankingDetails bankingDetails, Tasks tasks, String additionalInformation) {
		super();
		this.rejectReasons = rejectReasons;
		this.company = company;
		this.bankingDetails = bankingDetails;
		this.tasks = tasks;
		this.additionalInformation = additionalInformation;
	}

	public RejectReasonsChild(RejectReasons rejectReasons, Company company, Wsp wsp, Tasks tasks, String additionalInformation) {
		super();
		this.rejectReasons = rejectReasons;
		this.company = company;
		this.wsp = wsp;
		this.tasks = tasks;
		this.additionalInformation = additionalInformation;
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
		RejectReasonsChild other = (RejectReasonsChild) obj;
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

	/**
	 * Gets the additional information.
	 *
	 * @return the additional information
	 */
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	/**
	 * Sets the additional information.
	 *
	 * @param additionalInformation
	 *            the new additional information
	 */
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Gets the reject reasons.
	 *
	 * @return the reject reasons
	 */
	public RejectReasons getRejectReasons() {
		return rejectReasons;
	}

	/**
	 * Sets the reject reasons.
	 *
	 * @param rejectReasons
	 *            the new reject reasons
	 */
	public void setRejectReasons(RejectReasons rejectReasons) {
		this.rejectReasons = rejectReasons;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

}
