package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

// TODO: Auto-generated Javadoc
/**
 * QCTOFile03.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qcto_file_03")
public class QCTOFile03 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of QCTOFile03. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "assessment_partner_code")
	private String  assessmentPartnerCode;
	
	@Column(name = "assessment_centre_code")
	private String assessmentCentreCode;
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "learner_alternate_id")
	private String learnerAlternateID;
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
	@Column(name = "eisa_component_number")
	private String eISAComponentNumber;
	
	@Column(name = "eisa_component_achievement_value")
	private String eISAComponentAchievementValue;
	
	@Column(name = "date_assessed")
	private Date dateAssessed;
	
	@Column(name = "eisa_percentage_obtained")
	private String eISAPercentageObtained;
	
	@Column(name = "date_stamp")
	private Date dateStamp;
	
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
		QCTOFile03 other = (QCTOFile03) obj;
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

	public String getAssessmentPartnerCode() {
		return assessmentPartnerCode;
	}

	public void setAssessmentPartnerCode(String assessmentPartnerCode) {
		this.assessmentPartnerCode = assessmentPartnerCode;
	}

	public String getAssessmentCentreCode() {
		return assessmentCentreCode;
	}

	public void setAssessmentCentreCode(String assessmentCentreCode) {
		this.assessmentCentreCode = assessmentCentreCode;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getLearnerAlternateID() {
		return learnerAlternateID;
	}

	public void setLearnerAlternateID(String learnerAlternateID) {
		this.learnerAlternateID = learnerAlternateID;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String geteISAComponentNumber() {
		return eISAComponentNumber;
	}

	public void seteISAComponentNumber(String eISAComponentNumber) {
		this.eISAComponentNumber = eISAComponentNumber;
	}

	public String geteISAComponentAchievementValue() {
		return eISAComponentAchievementValue;
	}

	public void seteISAComponentAchievementValue(String eISAComponentAchievementValue) {
		this.eISAComponentAchievementValue = eISAComponentAchievementValue;
	}

	public Date getDateAssessed() {
		return dateAssessed;
	}

	public void setDateAssessed(Date dateAssessed) {
		this.dateAssessed = dateAssessed;
	}

	public String geteISAPercentageObtained() {
		return eISAPercentageObtained;
	}

	public void seteISAPercentageObtained(String eISAPercentageObtained) {
		this.eISAPercentageObtained = eISAPercentageObtained;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
}
