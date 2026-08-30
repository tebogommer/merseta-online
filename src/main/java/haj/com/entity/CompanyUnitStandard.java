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
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity links a company that is a training provider type to a unit
 * standard
 * 
 * CompanyUnitStandard.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_unit_standard")
@AuditTable(value = "company_unit_standard_hist")
@Audited
public class CompanyUnitStandard implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUnitStandard. */
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

	/** The Unit Standard linked to the Company / Training Provider. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id")
	private UnitStandards unitStandard;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_qualification_id")
	private Qualification forQualification;
	
	/** The Company / Training Provider linked to qualification. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_company_id")
	private NonSetaCompany nonSetaCompany;

	/** Note. */
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
	
	@Transient
	private List<Doc> docList;
	
	@Column(name = "manually_added", columnDefinition = "BIT default false")
	private Boolean manuallyAdded;
	
	@Transient
	private Boolean cannotRemove;

	/**
	 * Default Constructor.
	 */
	public CompanyUnitStandard() {
		super();
	}

	/**
	 * Constructor using company and unit standard fields.
	 *
	 * @param company
	 *            the company
	 * @param unitStandard
	 *            the unit standard
	 */
	public CompanyUnitStandard(Company company, UnitStandards unitStandard) {
		super();
		this.company = company;
		this.unitStandard = unitStandard;
	}

	public CompanyUnitStandard(Company company, UnitStandards unitStandard, String targetClass, Long targetKey) {
		super();
		this.company = company;
		this.unitStandard = unitStandard;
		this.targetClass=targetClass;
		this.targetKey=targetKey;
	}
	
	public CompanyUnitStandard(NonSetaCompany nonSetaCompany, UnitStandards unitStandard, String targetClass, Long targetKey) {
		super();
		this.nonSetaCompany = nonSetaCompany;
		this.unitStandard = unitStandard;
		this.targetClass=targetClass;
		this.targetKey=targetKey;
	}
	
	public CompanyUnitStandard( UnitStandards unitStandard, String targetClass, Long targetKey) {
		super();
		this.unitStandard = unitStandard;
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
		CompanyUnitStandard other = (CompanyUnitStandard) obj;
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
	 * @return company
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
	 * Gets the unit standard.
	 *
	 * @return unitStandard
	 */
	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	/**
	 * Sets the unit standard.
	 *
	 * @param unitStandard
	 *            the new unit standard
	 */
	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	/**
	 * @return the accept
	 */
	public Boolean getAccept() {
		return accept;
	}

	/**
	 * @param accept the accept to set
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

	public Qualification getForQualification() {
		return forQualification;
	}

	public void setForQualification(Qualification forQualification) {
		this.forQualification = forQualification;
	}

	public Boolean getManuallyAdded() {
		return manuallyAdded;
	}

	public void setManuallyAdded(Boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}

	public Boolean getCannotRemove() {
		return cannotRemove;
	}

	public void setCannotRemove(Boolean cannotRemove) {
		this.cannotRemove = cannotRemove;
	}

}
