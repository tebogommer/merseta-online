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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.Wsp;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "mandatory_grant_evaluation")
public class MandatoryGrantEvaluation extends AbstractLookup implements Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of bank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "size_of_company_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SizeOfCompany sizeOfCompany;

	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;

	@Column(name = "yes_score")
	private Integer yesScore;

	@Column(name = "no_score")
	private Integer noScore;

	@Column(name = "weighted_score")
	private Double weightedScore;

	@Column(name = "total_score")
	private Double totalScore;

	@Column(name = "validation_form")
	private Boolean validationForm;

	@Enumerated
	@Column(name = "yes_no_enum")
	private YesNoEnum yesNoEnum;

	@Enumerated
	@Column(name = "wsp_type_enum")
	private WspTypeEnum wspTypeEnum;

	public MandatoryGrantEvaluation() {
		super();
	}

	@Override
	public MandatoryGrantEvaluation clone() {
		MandatoryGrantEvaluation clone = null;
		try {
			clone = (MandatoryGrantEvaluation) super.clone();
			clone.setId(null);
			clone.setCreateDate(null);
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
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
		MandatoryGrantEvaluation other = (MandatoryGrantEvaluation) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the id.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * @param id
	 * the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * @param description
	 * the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getYesScore() {
		return yesScore;
	}

	public void setYesScore(Integer yesScore) {
		this.yesScore = yesScore;
	}

	public Integer getNoScore() {
		return noScore;
	}

	public void setNoScore(Integer noScore) {
		this.noScore = noScore;
	}

	public Double getWeightedScore() {
		return weightedScore;
	}

	public void setWeightedScore(Double weightedScore) {
		this.weightedScore = weightedScore;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public SizeOfCompany getSizeOfCompany() {
		return sizeOfCompany;
	}

	public void setSizeOfCompany(SizeOfCompany sizeOfCompany) {
		this.sizeOfCompany = sizeOfCompany;
	}

	public Boolean getValidationForm() {
		return validationForm;
	}

	public void setValidationForm(Boolean validationForm) {
		this.validationForm = validationForm;
	}

	@Transient
	public Double getCalcScore() {
		if (weightedScore == null) return 0.0;
		double score = getNoScore();
		if (yesNoEnum == YesNoEnum.Yes) {
			score = getYesScore();
		}
		return score / 5 * weightedScore;
	}

	@Transient
	public Double getCalcScoreDG() {
		if (yesNoEnum == null) return 0.0;
		double score = getNoScore();
		if (yesNoEnum == YesNoEnum.Yes) score = getYesScore();
		return score;
	}

	@Transient
	public Double getCalcWeighting() {
		if (weightedScore == null) return 0.0;
		return weightedScore / 5;
	}

	public YesNoEnum getYesNoEnum() {
		return yesNoEnum;
	}

	public void setYesNoEnum(YesNoEnum yesNoEnum) {
		this.yesNoEnum = yesNoEnum;
	}

	public WspTypeEnum getWspTypeEnum() {
		return wspTypeEnum;
	}

	public void setWspTypeEnum(WspTypeEnum wspTypeEnum) {
		this.wspTypeEnum = wspTypeEnum;
	}
}
