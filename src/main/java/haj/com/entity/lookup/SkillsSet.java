package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.Doc;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionType.
 */
@Entity
@Table(name = "skills_set")
@AuditTable(value = "skills_set_hist")
@Audited
public class SkillsSet extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The description. */
	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "programme_id", length = 100)
	private String programmeID;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "credits")
	private Integer credits;

	@Column(name = "registration_number")
	private String registrationNumber;


	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "netqa_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Etqa etqa;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;
	
	@Transient
	private Doc doc;
	
	@Transient
	private List<SkillsSetUnitStandards> skillsSetUnitStandards;
	
	@Transient
	private Boolean cannotRemove;

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
		SkillsSet other = (SkillsSet) obj;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProgrammeID() {
		return programmeID;
	}

	public void setProgrammeID(String programmeID) {
		this.programmeID = programmeID;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Etqa getEtqa() {
		return etqa;
	}

	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<SkillsSetUnitStandards> getSkillsSetUnitStandards() {
		return skillsSetUnitStandards;
	}

	public void setSkillsSetUnitStandards(List<SkillsSetUnitStandards> skillsSetUnitStandards) {
		this.skillsSetUnitStandards = skillsSetUnitStandards;
	}

	public Boolean getCannotRemove() {
		return cannotRemove;
	}

	public void setCannotRemove(Boolean cannotRemove) {
		this.cannotRemove = cannotRemove;
	}
	
}
