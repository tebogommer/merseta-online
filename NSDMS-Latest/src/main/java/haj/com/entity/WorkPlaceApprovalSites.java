package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "work_place_approval_sites")
@AuditTable(value = "work_place_approval_sites_hist")
@Audited
public class WorkPlaceApprovalSites implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workPlace_approval_id", nullable = true)
	private WorkPlaceApproval workPlaceApproval;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workPlace_approval_mentor_id", nullable = true)
	private WorkPlaceApprovalMentors workPlaceApprovalMentor;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sites_id", nullable = true)
	private Sites sites;
	
	/** Link to Smes linked to a site */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sites_sme_id", nullable = true)
	private SitesSme sitesSme;

	@Column(name = "learnership_registration_number", columnDefinition = "LONGTEXT")
	private String learnershipRegistrationNumber;
	
	@Column(name = "use_company_address")
	private Boolean useCompanyAddress;

	/** The first name. */
	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;

	/** The last name. */
	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	@Column(name = "identity_number", length = 100, nullable = true)
	private String identityNumber;
	
	@Column(name = "passport_number", length = 100, nullable = true)
	private String passportNumber;

	@Column(name = "number_of_artisans")
	private Integer numberOfArtisans;

	@Column(name = "number_of_learners")
	private Integer numberOfLearners;
	
	/** The qualification. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	/** The residential address if no site assigned. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "residential_address_id", nullable = true)
	private Address residentialAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delete_user_id", nullable = true)
	private Users deleteUser;
	
	// approval enum
	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;

	
	@Transient
	private List<Doc> docs;

	public WorkPlaceApprovalSites() {
		super();
	}

	public WorkPlaceApprovalSites(WorkPlaceApproval workPlaceApproval) {
		super();
		this.workPlaceApproval = workPlaceApproval;
	}

	public WorkPlaceApprovalSites(WorkPlaceApproval workPlaceApproval, Sites sites) {
		super();
		this.workPlaceApproval = workPlaceApproval;
		this.sites = sites;
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
		WorkPlaceApprovalSites other = (WorkPlaceApprovalSites) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public WorkPlaceApproval getWorkPlaceApproval() {
		return workPlaceApproval;
	}

	public void setWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) {
		this.workPlaceApproval = workPlaceApproval;
	}

	public Sites getSites() {
		return sites;
	}

	public void setSites(Sites sites) {
		this.sites = sites;
	}

	public String getLearnershipRegistrationNumber() {
		return learnershipRegistrationNumber;
	}

	public void setLearnershipRegistrationNumber(String learnershipRegistrationNumber) {
		this.learnershipRegistrationNumber = learnershipRegistrationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public Integer getNumberOfArtisans() {
		return numberOfArtisans;
	}

	public void setNumberOfArtisans(Integer numberOfArtisans) {
		this.numberOfArtisans = numberOfArtisans;
	}

	public Integer getNumberOfLearners() {
		return numberOfLearners;
	}

	public void setNumberOfLearners(Integer numberOfLearners) {
		this.numberOfLearners = numberOfLearners;
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

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Users getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Users deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Boolean getUseCompanyAddress() {
		return useCompanyAddress;
	}

	public void setUseCompanyAddress(Boolean useCompanyAddress) {
		this.useCompanyAddress = useCompanyAddress;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public WorkPlaceApprovalMentors getWorkPlaceApprovalMentor() {
		return workPlaceApprovalMentor;
	}

	public void setWorkPlaceApprovalMentor(WorkPlaceApprovalMentors workPlaceApprovalMentor) {
		this.workPlaceApprovalMentor = workPlaceApprovalMentor;
	}
	
}
