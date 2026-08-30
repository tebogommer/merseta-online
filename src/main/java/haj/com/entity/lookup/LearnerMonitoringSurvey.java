package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.WorkplaceSurveyType;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "Learner_Monitoring_survey")
public class LearnerMonitoringSurvey extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "section")
	private Integer section;

	@Column(name = "question", columnDefinition = "LONGTEXT")
	private String question;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workplace_monitoring", nullable = true)
	private WorkplaceMonitoring workplaceMonitoring;

	@Enumerated(EnumType.STRING)
	@Column(name = "yes_no")
	private YesNoEnum yesNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "workplace_survey_type")
	private WorkplaceSurveyType workplaceSurveyType;

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
		LearnerMonitoringSurvey other = (LearnerMonitoringSurvey) obj;
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
		}
		return clone;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public WorkplaceMonitoring getWorkplaceMonitoring() {
		return workplaceMonitoring;
	}

	public void setWorkplaceMonitoring(WorkplaceMonitoring workplaceMonitoring) {
		this.workplaceMonitoring = workplaceMonitoring;
	}

	public YesNoEnum getYesNo() {
		return yesNo;
	}

	public void setYesNo(YesNoEnum yesNo) {
		this.yesNo = yesNo;
	}

	public WorkplaceSurveyType getWorkplaceSurveyType() {
		return workplaceSurveyType;
	}

	public void setWorkplaceSurveyType(WorkplaceSurveyType workplaceSurveyType) {
		this.workplaceSurveyType = workplaceSurveyType;
	}
}
