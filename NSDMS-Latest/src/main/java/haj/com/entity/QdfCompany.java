package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * QdfCompany.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qdf_company")
public class QdfCompany implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUsers. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualifications_curriculum_development_id", nullable = false)
	private QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The approval enum. */
	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "review_date", length = 19)
	private Date reviewDate;
	
	/** The form user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_user_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Users reviewUser;
	
	@Transient
	private boolean show;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<Doc> additionalDocs ;
	
	@Transient
	private Qualification qualification;
	
	@Transient
	private List<QdfCompanyUsers> qdfCompanyUsers;

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QdfCompany other = (QdfCompany) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new company users.
	 */
	public QdfCompany() {
		super();
	}

	/**
	 * Instantiates a new Qdf Company user.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 */
	public QdfCompany(Company company) {
		super();
		this.company = company;
		this.createDate = new Date();
	}

	/**
	 * Instantiates a new company users.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 */
	public QdfCompany(Users user, Company company) {
		super();
		this.user = user;
		this.company = company;
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

	public QualificationsCurriculumDevelopment getQualificationsCurriculumDevelopment() {
		return qualificationsCurriculumDevelopment;
	}

	public void setQualificationsCurriculumDevelopment(
			QualificationsCurriculumDevelopment qualificationsCurriculumDevelopment) {
		this.qualificationsCurriculumDevelopment = qualificationsCurriculumDevelopment;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Users getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(Users reviewUser) {
		this.reviewUser = reviewUser;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public List<QdfCompanyUsers> getQdfCompanyUsers() {
		return qdfCompanyUsers;
	}

	public void setQdfCompanyUsers(List<QdfCompanyUsers> qdfCompanyUsers) {
		this.qdfCompanyUsers = qdfCompanyUsers;
	}

	public List<Doc> getAdditionalDocs() {
		return additionalDocs;
	}

	public void setAdditionalDocs(List<Doc> additionalDocs) {
		this.additionalDocs = additionalDocs;
	}
}
