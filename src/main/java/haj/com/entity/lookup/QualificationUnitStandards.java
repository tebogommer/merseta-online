package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionType.
 */
@Entity
@Table(name = "qualification_unit_standards")
public class QualificationUnitStandards extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standards_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private UnitStandards unitStandards;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	@Enumerated
	@Column(name = "unit_standard_type_enum")
	private UnitStandardTypeEnum unitStandardTypeEnum;
	
	@CSVAnnotation(name = "qualification_id_entry", className = String.class)
	@Column(name = "qualification_id_entry")
	private String qualificationIdEntry;
	
	@CSVAnnotation(name = "unit_standard_id_entry", className = String.class)
	@Column(name = "unit_standard_id_entry")
	private String unitStandardIdEntry;
	
	@CSVAnnotation(name = "type_entry", className = String.class)
	@Column(name = "type_entry")
	private String typeEntry;
	
	@CSVAnnotation(name = "credits_entry", className = String.class)
	@Column(name = "credits_entry")
	private String creditsEntry;
	
	@Column(name = "import_entry", columnDefinition = "BIT default false")
	private Boolean importEntry;
	
	@Column(name = "import_successful", columnDefinition = "BIT default false")
	private Boolean importSuccessful;
	

	public QualificationUnitStandards() {
		super();
	}

	public QualificationUnitStandards(UnitStandards unitStandards) {
		super();
		this.unitStandards = unitStandards;
	}
	
	public QualificationUnitStandards( UnitStandards unitStandards, Qualification qualification) {
		super();
		this.unitStandards = unitStandards;
		this.qualification = qualification;
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
		QualificationUnitStandards other = (QualificationUnitStandards) obj;
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

	public UnitStandards getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(UnitStandards unitStandards) {
		this.unitStandards = unitStandards;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public UnitStandardTypeEnum getUnitStandardTypeEnum() {
		return unitStandardTypeEnum;
	}

	public void setUnitStandardTypeEnum(UnitStandardTypeEnum unitStandardTypeEnum) {
		this.unitStandardTypeEnum = unitStandardTypeEnum;
	}

	public String getQualificationIdEntry() {
		return qualificationIdEntry;
	}

	public void setQualificationIdEntry(String qualificationIdEntry) {
		this.qualificationIdEntry = qualificationIdEntry;
	}

	public String getUnitStandardIdEntry() {
		return unitStandardIdEntry;
	}

	public void setUnitStandardIdEntry(String unitStandardIdEntry) {
		this.unitStandardIdEntry = unitStandardIdEntry;
	}

	public String getTypeEntry() {
		return typeEntry;
	}

	public void setTypeEntry(String typeEntry) {
		this.typeEntry = typeEntry;
	}

	public String getCreditsEntry() {
		return creditsEntry;
	}

	public void setCreditsEntry(String creditsEntry) {
		this.creditsEntry = creditsEntry;
	}

	public Boolean getImportEntry() {
		return importEntry;
	}

	public void setImportEntry(Boolean importEntry) {
		this.importEntry = importEntry;
	}

	public Boolean getImportSuccessful() {
		return importSuccessful;
	}

	public void setImportSuccessful(Boolean importSuccessful) {
		this.importSuccessful = importSuccessful;
	}
}
