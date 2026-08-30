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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity links a company that is a training provider type to a
 * qualification
 * 
 * CompanyQualifications.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_qualifications")
@AuditTable(value = "company_qualifications_hist")
@Audited
public class CompanyQualifications implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyQualifications. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The Company / Training Provider linked to qualification. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	
	/** The Company / Training Provider linked to qualification. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_company_id")
	private NonSetaCompany nonSetaCompany;

	/** The Qualification linked to the Company / Training Provider. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;

	/** Additional Note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;

	/**
	 * Soft Deleted Defaulted to false If true should not be displayed and / or
	 * referenced.
	 */
	@Column(name = "soft_delete")
	private Boolean softDelete;

	@Column(name = "accept")
	private Boolean accept;
	
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_doc_parent_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private TrainingProviderDocParent trainingProviderDocParent;
	
	@Column(name = "manually_added", columnDefinition = "BIT default false")
	private Boolean manuallyAdded;
	
	@Transient
	private List<Doc> docList;

	/**
	 * Default Constructor.
	 */
	public CompanyQualifications() {
		super();
	}

	/**
	 * Constructor using company and qualification fields.
	 *
	 * @param company
	 *            the company
	 * @param qualification
	 *            the qualification
	 */
	public CompanyQualifications(NonSetaCompany nonSetaCompany, Qualification qualification) {
		super();
		this.nonSetaCompany = nonSetaCompany;
		this.qualification = qualification;
	}
	
	public CompanyQualifications(Company company, Qualification qualification) {
		super();
		this.company = company;
		this.qualification = qualification;
	}

	public CompanyQualifications(Company company, Qualification qualification, String targetClass, Long targetKey) {
		super();
		this.company = company;
		this.qualification = qualification;
		this.targetClass=targetClass;
		this.targetKey=targetKey;
	}
	
	public CompanyQualifications(Qualification qualification, String targetClass, Long targetKey) {
		super();
		this.qualification = qualification;
		this.targetClass=targetClass;
		this.targetKey=targetKey;
	}
	
	
	public CompanyQualifications(NonSetaCompany nonSetaCompany, Qualification qualification, String targetClass, Long targetKey) {
		super();
		this.nonSetaCompany = nonSetaCompany;
		this.qualification = qualification;
		this.targetClass=targetClass;
		this.targetKey=targetKey;
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
		CompanyQualifications other = (CompanyQualifications) obj;
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
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification
	 *            the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the soft delete.
	 *
	 * @return softDelete
	 */
	public Boolean getSoftDelete() {
		return softDelete;
	}

	/**
	 * Sets the soft delete.
	 *
	 * @param softDelete
	 *            the new soft delete
	 */
	public void setSoftDelete(Boolean softDelete) {
		this.softDelete = softDelete;
	}

	/**
	 * @return the accept
	 */
	public Boolean getAccept() {
		return accept;
	}

	/**
	 * @param accept
	 *            the accept to set
	 */
	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public TrainingProviderDocParent getTrainingProviderDocParent() {
		return trainingProviderDocParent;
	}

	public void setTrainingProviderDocParent(TrainingProviderDocParent trainingProviderDocParent) {
		this.trainingProviderDocParent = trainingProviderDocParent;
	}

	public List<Doc> getDocList() {
		return docList;
	}

	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}

	public Boolean getManuallyAdded() {
		return manuallyAdded;
	}

	public void setManuallyAdded(Boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}

}
