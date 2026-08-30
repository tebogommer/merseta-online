package haj.com.entity.lookup.temp;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.lookup.NqfLevels;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Qualification.
 */
@Entity
@Table(name = "saqa_qualification_temp")
public class QualificationTemp implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
	private Long id;

	/** The description. */
	@Column(name = "qualificationtitle", insertable = false, updatable = false)
	private String description;

	/** The code. */
	@Column(name = "qualificationid", insertable = false, updatable = false)
	private Integer code;
	/** The code. */
	@Column(name = "qualificationid_string", insertable = false, updatable = false)
	private String codeString;

	/** The nqflevelg 2 description. */
	@Column(name = "nqflevelg2description", insertable = false, updatable = false)
	private String nqflevelg2description;

	/** The nqflevel. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqflevel;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_doc_parent_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private TrainingProviderDocParent trainingProviderDocParent;

	/** The etqaid. */
	@Column(name = "etqaid", insertable = false, updatable = false)
	protected String etqaid;
	
	/** The qualificationminimumcredits. */
	@Column(name = "qualificationminimumcredits",  nullable = true )
	protected Integer credits;
	
	/**The last date for enrolment*/
	@Column(name = "lastDateForEnrolment",  nullable = true )
	private Date lastDateForEnrolment2;
	
	/**The last date for enrolment*/
	@Column(name = "last_date_for_enrolment",  nullable = true )
	private Date lastDateForEnrolment;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastdateforachievement", nullable = true)
	private Date lastdateforachievement;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qualregistrationstartdate", nullable = true)
	private Date qualregistrationstartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "qualregistrationenddate", nullable = true)
    private Date qualregistrationendDate;
	
	@Column(name = "workplace_approval_required", columnDefinition = "BIT default false")
    private Boolean workplaceApprovalRequired; 

	 /** The etqaacronym. */
    private String etqaacronym;
    
    /** The etqaname. */
    private String etqaname;  
    
    /** The qualificationtypedesc. */
    private String qualificationtypedesc;
    
    /** The qualificationtypeid. */
    private String qualificationtypeid;  

    /** The registrationstatusdesc. */
    private String registrationstatusdesc;
    
    /** The registrationstatuscode. */
    private String registrationstatuscode;
    
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
		QualificationTemp other = (QualificationTemp) obj;
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the nqflevelg 2 description.
	 *
	 * @return the nqflevelg 2 description
	 */
	public String getNqflevelg2description() {
		return nqflevelg2description;
	}

	/**
	 * Sets the nqflevelg 2 description.
	 *
	 * @param nqflevelg2description
	 *            the new nqflevelg 2 description
	 */
	public void setNqflevelg2description(String nqflevelg2description) {
		this.nqflevelg2description = nqflevelg2description;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the new code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * Gets the nqflevel.
	 *
	 * @return the nqflevel
	 */
	public NqfLevels getNqflevel() {
		return nqflevel;
	}

	/**
	 * Sets the nqflevel.
	 *
	 * @param nqflevel
	 *            the new nqflevel
	 */
	public void setNqflevel(NqfLevels nqflevel) {
		this.nqflevel = nqflevel;
	}

	/**
	 * Gets the etqaid.
	 *
	 * @return the etqaid
	 */
	public String getEtqaid() {
		return etqaid;
	}

	/**
	 * Sets the etqaid.
	 *
	 * @param etqaid
	 *            the new etqaid
	 */
	public void setEtqaid(String etqaid) {
		this.etqaid = etqaid;
	}

	public String getCodeString() {
		return codeString;
	}

	public String getSaqaQualification() {
		return "(" + code + ") " + description;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	
	public Date getLastDateForEnrolment() {
		return lastDateForEnrolment;
	}

	public void setLastDateForEnrolment(Date lastDateForEnrolment) {
		this.lastDateForEnrolment = lastDateForEnrolment;
	}

	public String getEtqaacronym() {
		return etqaacronym;
	}

	public void setEtqaacronym(String etqaacronym) {
		this.etqaacronym = etqaacronym;
	}

	public String getQualificationtypedesc() {
		return qualificationtypedesc;
	}

	public void setQualificationtypedesc(String qualificationtypedesc) {
		this.qualificationtypedesc = qualificationtypedesc;
	}
	
	public String getQualificationtypeid() {
		return qualificationtypeid;
	}

	public void setQualificationtypeid(String qualificationtypeid) {
		this.qualificationtypeid = qualificationtypeid;
	}

	public Boolean getWorkplaceApprovalRequired() {
		return workplaceApprovalRequired;
	}

	public void setWorkplaceApprovalRequired(Boolean workplaceApprovalRequired) {
		this.workplaceApprovalRequired = workplaceApprovalRequired;
	}

	public String getEtqaname() {
		return etqaname;
	}

	public void setEtqaname(String etqaname) {
		this.etqaname = etqaname;
	}

	public TrainingProviderDocParent getTrainingProviderDocParent() {
		return trainingProviderDocParent;
	}

	public void setTrainingProviderDocParent(TrainingProviderDocParent trainingProviderDocParent) {
		this.trainingProviderDocParent = trainingProviderDocParent;
	}

	public Date getLastdateforachievement() {
		return lastdateforachievement;
	}

	public void setLastdateforachievement(Date lastdateforachievement) {
		this.lastdateforachievement = lastdateforachievement;
	}

	public Date getQualregistrationstartDate() {
		return qualregistrationstartDate;
	}

	public void setQualregistrationstartDate(Date qualregistrationstartDate) {
		this.qualregistrationstartDate = qualregistrationstartDate;
	}

	public Date getQualregistrationendDate() {
		return qualregistrationendDate;
	}

	public void setQualregistrationendDate(Date qualregistrationendDate) {
		this.qualregistrationendDate = qualregistrationendDate;
	}

	public Date getLastDateForEnrolment2() {
		return lastDateForEnrolment2;
	}

	public void setLastDateForEnrolment2(Date lastDateForEnrolment2) {
		this.lastDateForEnrolment2 = lastDateForEnrolment2;
	}

	public String getRegistrationstatusdesc() {
		return registrationstatusdesc;
	}

	public void setRegistrationstatusdesc(String registrationstatusdesc) {
		this.registrationstatusdesc = registrationstatusdesc;
	}

	public String getRegistrationstatuscode() {
		return registrationstatuscode;
	}

	public void setRegistrationstatuscode(String registrationstatuscode) {
		this.registrationstatuscode = registrationstatuscode;
	}
}
