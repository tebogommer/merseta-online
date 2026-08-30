package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.enums.LearnerCompletenceEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * EisaLearnerRegistration.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "eisa_learner_registration")
public class EisaLearnerRegistration implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The Learner. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_id", nullable = false)
	private Users learner;
	
	/** The Special Assessment needs. */
	@Column(name = "special_assesment_needs",length=250,  nullable = true)
	private String specialAssesmentNeeds;
	
	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	/** The SkillsDevelopment Provider. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdp_id", nullable = false)
	private Company sdp;
	
	/** The Statement of Results attached. */
	@Enumerated
	@Column(name = "results_attached")
	private YesNoEnum resultsAttached;
	
	/**Competence has been achieved in. */
	@Enumerated
	@Column(name = "completence")
	private LearnerCompletenceEnum completence;
	
	/**The qualification */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	/** The Date of EISA. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eisa_date", length = 19)
	private Date eisaDate;
	
	/** The Time of EISA. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eisa_time", length = 19)
	private Date eisaTime;
	
	/** The  Assessment Centre. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_centre", nullable = false)
	private Company assessmentCentre;
	
	/** The  flcRequired. */
	@Enumerated
	@Column(name = "flc_required")
	private YesNoEnum flcRequired;

    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		EisaLearnerRegistration other = (EisaLearnerRegistration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
	 * @param id the id to set
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getLearner() {
		return learner;
	}

	public void setLearner(Users learner) {
		this.learner = learner;
	}

	public String getSpecialAssesmentNeeds() {
		return specialAssesmentNeeds;
	}

	public void setSpecialAssesmentNeeds(String specialAssesmentNeeds) {
		this.specialAssesmentNeeds = specialAssesmentNeeds;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getSdp() {
		return sdp;
	}

	public void setSdp(Company sdp) {
		this.sdp = sdp;
	}

	public YesNoEnum getResultsAttached() {
		return resultsAttached;
	}

	public void setResultsAttached(YesNoEnum resultsAttached) {
		this.resultsAttached = resultsAttached;
	}

	public LearnerCompletenceEnum getCompletence() {
		return completence;
	}

	public void setCompletence(LearnerCompletenceEnum completence) {
		this.completence = completence;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Date getEisaDate() {
		return eisaDate;
	}

	public void setEisaDate(Date eisaDate) {
		this.eisaDate = eisaDate;
	}

	public Date getEisaTime() {
		return eisaTime;
	}

	public void setEisaTime(Date eisaTime) {
		this.eisaTime = eisaTime;
	}

	public Company getAssessmentCentre() {
		return assessmentCentre;
	}

	public void setAssessmentCentre(Company assessmentCentre) {
		this.assessmentCentre = assessmentCentre;
	}

	public YesNoEnum getFlcRequired() {
		return flcRequired;
	}

	public void setFlcRequired(YesNoEnum flcRequired) {
		this.flcRequired = flcRequired;
	}

	


}
