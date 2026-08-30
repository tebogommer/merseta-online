package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.Doc;
import haj.com.entity.TrainingProviderMonitoring;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "auditor_monitor_review")
public class AuditorMonitorReview extends AbstractLookup implements Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "accreditation_criteria", length = 500)
	private String accreditationCriteria;

	@Column(name = "evidence_requirements", columnDefinition = "LONGTEXT")
	private String evidenceRequirements;

	@Column(name = "section")
	private Integer section;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_monitoring", nullable = true)
	private TrainingProviderMonitoring trainingProviderMonitoring;

	@Enumerated
	@Column(name = "evidence_required")
	private YesNoEnum evidenceRequired;
	
	@Enumerated
	@Column(name = "evidence_required_evaluator_outcome")
	private YesNoEnum evidenceRequiredEvaluatorOutcome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "for_process")
	private ConfigDocProcessEnum forProcess;

	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	@Column(name = "target_class", nullable = true)
	private String targetClass;

	@Column(name = "comment", columnDefinition = "LONGTEXT")
	private String comment;
	
	/**TrainingProviderApplication AccreditationApplicationType*/
	@Enumerated
	@Column(name = "tp_accreditation_application_type")
	private AccreditationApplicationTypeEnum tpAccreditationApplicationType;
	
	@Column(name = "is_not_relevant", columnDefinition = "BIT default false")
	private Boolean isNotRelevant;

	@Transient
	private List<Doc> docs;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areas_for_improvement_id", nullable = true)
	private AreaForImprovement areaForImprovement;

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
		AuditorMonitorReview other = (AuditorMonitorReview) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccreditationCriteria() {
		return accreditationCriteria;
	}

	public void setAccreditationCriteria(String accreditationCriteria) {
		this.accreditationCriteria = accreditationCriteria;
	}

	public String getEvidenceRequirements() {
		return evidenceRequirements;
	}

	public void setEvidenceRequirements(String evidenceRequirements) {
		this.evidenceRequirements = evidenceRequirements;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public TrainingProviderMonitoring getTrainingProviderMonitoring() {
		return trainingProviderMonitoring;
	}

	public void setTrainingProviderMonitoring(TrainingProviderMonitoring trainingProviderMonitoring) {
		this.trainingProviderMonitoring = trainingProviderMonitoring;
	}

	public YesNoEnum getEvidenceRequired() {
		return evidenceRequired;
	}

	public void setEvidenceRequired(YesNoEnum evidenceRequired) {
		this.evidenceRequired = evidenceRequired;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
	
	/**
	 * @return the forProcess
	 */
	public ConfigDocProcessEnum getForProcess() {
		return forProcess;
	}

	/**
	 * @param forProcess
	 *            the forProcess to set
	 */
	public void setForProcess(ConfigDocProcessEnum forProcess) {
		this.forProcess = forProcess;
	}

	/**
	 * @return the targetKey
	 */
	public Long getTargetKey() {
		return targetKey;
	}

	/**
	 * @param targetKey
	 *            the targetKey to set
	 */
	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	/**
	 * @return the targetClass
	 */
	public String getTargetClass() {
		return targetClass;
	}

	/**
	 * @param targetClass
	 *            the targetClass to set
	 */
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the tpAccreditationApplicationType
	 */
	public AccreditationApplicationTypeEnum getTpAccreditationApplicationType() {
		return tpAccreditationApplicationType;
	}

	/**
	 * @param tpAccreditationApplicationType the tpAccreditationApplicationType to set
	 */
	public void setTpAccreditationApplicationType(AccreditationApplicationTypeEnum tpAccreditationApplicationType) {
		this.tpAccreditationApplicationType = tpAccreditationApplicationType;
	}

	public Boolean getIsNotRelevant() {
		return isNotRelevant;
	}

	public void setIsNotRelevant(Boolean isNotRelevant) {
		this.isNotRelevant = isNotRelevant;
	}

	public AreaForImprovement getAreaForImprovement() {
		return areaForImprovement;
	}

	public void setAreaForImprovement(AreaForImprovement areaForImprovement) {
		this.areaForImprovement = areaForImprovement;
	}

	public YesNoEnum getEvidenceRequiredEvaluatorOutcome() {
		return evidenceRequiredEvaluatorOutcome;
	}

	public void setEvidenceRequiredEvaluatorOutcome(YesNoEnum evidenceRequiredEvaluatorOutcome) {
		this.evidenceRequiredEvaluatorOutcome = evidenceRequiredEvaluatorOutcome;
	}

	

}
