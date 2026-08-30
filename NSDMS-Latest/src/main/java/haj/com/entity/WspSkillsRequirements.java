package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.enums.PastFutureEnum;
import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.ScarcityReason;
import haj.com.entity.lookup.VacancyReasons;
import haj.com.framework.IDataEntity;

@NamedNativeQueries({

		@NamedNativeQuery(name = "NQ_NATIVE_tradesAllocation",

				query = "select CONCAT('(', coalesce(ofo.ofo_code, ofo2.ofo_code), ') ',ofo.description) AS ofo, " 
						+ "sum(wspr.total_vacancies) AS totalVacancies, sum(wspr.total_vacancies_filled) AS totalVacanciesFilled, " 
						+ "it.description AS interventionType,  " 
						+ "qual.qualificationtitle AS qualification, " 
						+ "man_grant.total as total, "
						+ "man_grant_2.total_pwd as totalPwd "
						+ "from wsp_skills_requirements wspr " 
						+ "inner join ofo_codes ofo on wspr.`ofo_codes_id` = ofo.id "
						+ "left join ofo_codes ofo2 on ofo2.id = ofo.ofo_codes_id " 
						+ "left join wsp on wspr.wsp_id = wsp.id " 
						+ "left join (" 
						+ "select mg.ofo_code_id, mg.intervention_type_id, mg.qualification_id, count(mg.id) AS total "
						+ "from mandatory_grant_detail mg " 
						+ "left join wsp on mg.wsp_id = wsp.id "
						+ "left join intervention_type on mg.intervention_type_id = intervention_type.id " 
						+ "where wsp.fin_year = :finYear "
						+ "and mg.imported = true "
					    + "and mg.funding_id = :dgFund "
					    + "and mg.wsp_report = :wspReport " 
					    
						+ "and intervention_type.intervention_type_enum = :interventionTypeEnum " 
						+ "group by mg.ofo_code_id, mg.intervention_type_id, mg.qualification_id" 
						+ ") AS man_grant "			
						+ "on wspr.ofo_codes_id = man_grant.ofo_code_id "
						+ "left join (select count(mg.id) AS total_pwd, mg.ofo_code_id , mg.qualification_id "
						+ "from mandatory_grant_detail mg " 
					    + "left join wsp on mg.wsp_id = wsp.id " 
					    + "left join intervention_type on mg.intervention_type_id = intervention_type.id " 
					    + "where wsp.fin_year = :finYear "
					    + "and mg.disability_id <> :noDisability "
					    + "and intervention_type.intervention_type_enum = :interventionTypeEnum "
					    + "and mg.imported = true "
					    + "and mg.funding_id = :dgFund "
					    + "and mg.wsp_report = :wspReport " 
					    + "group by mg.ofo_code_id, mg.intervention_type_id, mg.qualification_id"
					    + ") AS man_grant_2 on wspr.ofo_codes_id = man_grant_2.ofo_code_id " 
					    + "and man_grant_2.qualification_id = man_grant.qualification_id " 	
						+ "left join intervention_type it on man_grant.intervention_type_id = it.id " 
						+ "left join saqa_qualification qual on man_grant.qualification_id  = qual.id " 
						+ "where wsp.fin_year = :finYear "
						+ "group by wspr.`ofo_codes_id`, it.description, qual.qualificationtitle, man_grant.total, man_grant_2.total_pwd",
						
				resultSetMapping = "WspSkills"),
		@NamedNativeQuery(name = "NQ_NATIVE_all",

		query = "select CONCAT('(', coalesce(ofo.ofo_code, ofo2.ofo_code), ') ', ofo.description) AS ofo, sum(wspr.total_vacancies) AS totalVacancies,  " + 
				"sum(wspr.total_vacancies_filled) AS totalVacanciesFilled, it.description AS interventionType, " + 
				"qual.qualificationtitle AS qualification, man_grant.total as total, man_grant_2.total_pwd as totalPwd  " + 
				"from wsp_skills_requirements wspr  " +
				"left join ofo_codes ofo on wspr.`ofo_codes_id` = ofo.id  " + 
				"left join ofo_codes ofo2 on ofo2.id = ofo.ofo_codes_id  " + 
				"left join wsp on wspr.wsp_id = wsp.id  " + 
				"left join " + 
				"( " + 
				"select mg.ofo_code_id, mg.intervention_type_id, mg.qualification_id, count(mg.id) AS total  " + 
				"from  mandatory_grant_detail mg  " + 
				"left join wsp  on mg.wsp_id = wsp.id  " + 
				"left join intervention_type  on mg.intervention_type_id = intervention_type.id  " + 
				"where wsp.fin_year = :finYear and mg.imported = true and mg.funding_id = :dgFund and mg.wsp_report = :wspReport " +
			    "group by mg.ofo_code_id, mg.intervention_type_id, mg.qualification_id)  " + 
				"AS man_grant on wspr.ofo_codes_id = man_grant.ofo_code_id   " + 
				"left join  " + 
				"( " + 
				"select mg.ofo_code_id, count(mg.id) AS total_pwd, mg.qualification_id  " + 
				"from mandatory_grant_detail mg  " + 
				"left join wsp  on mg.wsp_id = wsp.id  " + 
				"where wsp.fin_year = :finYear and mg.disability_id <> :noDisability and mg.imported = true and mg.funding_id = :dgFund and mg.wsp_report = :wspReport " + 
				"group by mg.ofo_code_id, mg.qualification_id)  " + 
				"AS man_grant_2 on wspr.ofo_codes_id = man_grant_2.ofo_code_id " + 
				"and man_grant_2.qualification_id = man_grant.qualification_id " +
				"left join intervention_type it  on man_grant.intervention_type_id = it.id  " + 
				"left join saqa_qualification qual on man_grant.qualification_id  = qual.id  " + 
				"where wsp.fin_year = :finYear  " + 
				"group by wspr.`ofo_codes_id`, it.description, qual.qualificationtitle, man_grant.total, man_grant_2.total_pwd ",
				
		resultSetMapping = "WspSkills")
		})

@SqlResultSetMappings({ @SqlResultSetMapping(name = "WspSkills", classes = {
		@ConstructorResult(targetClass = WspSkillsRequirements.class, columns = {
				@ColumnResult(name = "ofo"), 
				@ColumnResult(name = "totalVacancies"), 
				@ColumnResult(name = "totalVacanciesFilled"), 
				@ColumnResult(name = "interventionType"), 
				@ColumnResult(name = "qualification"), 
				@ColumnResult(name = "total"), 
				@ColumnResult(name = "totalPwd") 
		}) 
	}) 
})

@Entity
@Table(name = "wsp_skills_requirements")
public class WspSkillsRequirements implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The past future enum. */
	@Enumerated
	@Column(name = "past_future_enum")
	private PastFutureEnum pastFutureEnum;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The yes no. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "yes_no_id", nullable = true)
	private YesNoLookup yesNo;

	/** The ofo codes. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	/** The occupation category. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "occupation_category_id", nullable = true)
	private OccupationCategory occupationCategory;

	/** The scarcity reason. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scarcity_reason_id", nullable = true)
	private ScarcityReason scarcityReason;

	/** The intervention type. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	/** The job title. */
	@Column(name = "job_title", length = 100)
	private String jobTitle;

	/** The total vacancies. */
	// Vacancies as of 30 April 20xx
	@Column(name = "total_vacancies")
	private Long totalVacancies;

	/** The total vacancies. */
	// Vacancies as of 30 April 20xx
	@Column(name = "total_vacancies_filled")
	private Long totalVacanciesFilled;

	/** The total needed staff. */
	@Column(name = "total_needed_staff")
	private Long totalNeededStaff;

	/** The total outsourced skills. */
	@Column(name = "total_outsourced_skills")
	private Long totalOutsourcedSkills;

	/** The noHardToFillVacancies */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "no_hard_to_fill_vacancies_id", nullable = true)
	private NoHardToFillVacancies noHardToFillVacancies;

	/** The noHardToFillVacancies */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vacancy_reasons_id", nullable = true)
	private VacancyReasons vacancyReasons;

	/** The HighestQualificationRequired */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "highest_qualification_required_id", nullable = true)
	private HighestQualificationRequired highestQualificationRequired;

	/** The Qualification NOT REQUIRED ANYMORE */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Transient
	private String intercentionDescription;

	@Transient
	private String qualificationDescription;

	@Transient
	private long sspDemand;

	@Transient
	private String ofoDescription;

	@Transient
	private long total;
	@Transient
	private long totalPwd;

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WspSkillsRequirements other = (WspSkillsRequirements) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new wsp skills requirements.
	 */
	public WspSkillsRequirements() {
		super();
	}

	public WspSkillsRequirements(String ofo, BigDecimal totalVacancies, BigDecimal totalVacanciesFilled, String interventionType, String qualification, BigInteger total, BigInteger totalPwd) {
		super();
		this.intercentionDescription = interventionType;
		this.qualificationDescription = qualification;
		if (totalVacancies != null) this.totalVacancies = totalVacancies.longValue();
		if (totalVacanciesFilled != null) this.totalVacanciesFilled = totalVacanciesFilled.longValue();
		this.ofoDescription = ofo;
		if (total != null) this.total = total.longValue();
		if (totalPwd != null) this.totalPwd = totalPwd.longValue();
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
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the past future enum.
	 *
	 * @return the past future enum
	 */
	public PastFutureEnum getPastFutureEnum() {
		return pastFutureEnum;
	}

	/**
	 * Sets the past future enum.
	 *
	 * @param pastFutureEnum
	 *            the new past future enum
	 */
	public void setPastFutureEnum(PastFutureEnum pastFutureEnum) {
		this.pastFutureEnum = pastFutureEnum;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the yes no.
	 *
	 * @return the yes no
	 */
	public YesNoLookup getYesNo() {
		return yesNo;
	}

	/**
	 * Sets the yes no.
	 *
	 * @param yesNo
	 *            the new yes no
	 */
	public void setYesNo(YesNoLookup yesNo) {
		this.yesNo = yesNo;
	}

	/**
	 * Gets the ofo codes.
	 *
	 * @return the ofo codes
	 */
	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	/**
	 * Sets the ofo codes.
	 *
	 * @param ofoCodes
	 *            the new ofo codes
	 */
	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	/**
	 * Gets the occupation category.
	 *
	 * @return the occupation category
	 */
	public OccupationCategory getOccupationCategory() {
		return occupationCategory;
	}

	/**
	 * Sets the occupation category.
	 *
	 * @param occupationCategory
	 *            the new occupation category
	 */
	public void setOccupationCategory(OccupationCategory occupationCategory) {
		this.occupationCategory = occupationCategory;
	}

	/**
	 * Gets the scarcity reason.
	 *
	 * @return the scarcity reason
	 */
	public ScarcityReason getScarcityReason() {
		return scarcityReason;
	}

	/**
	 * Sets the scarcity reason.
	 *
	 * @param scarcityReason
	 *            the new scarcity reason
	 */
	public void setScarcityReason(ScarcityReason scarcityReason) {
		this.scarcityReason = scarcityReason;
	}

	/**
	 * Gets the intervention type.
	 *
	 * @return the intervention type
	 */
	public InterventionType getInterventionType() {
		return interventionType;
	}

	/**
	 * Sets the intervention type.
	 *
	 * @param interventionType
	 *            the new intervention type
	 */
	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle
	 *            the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the total vacancies.
	 *
	 * @return the total vacancies
	 */
	public Long getTotalVacancies() {
		return totalVacancies;
	}

	/**
	 * Sets the total vacancies.
	 *
	 * @param totalVacancies
	 *            the new total vacancies
	 */
	public void setTotalVacancies(Long totalVacancies) {
		this.totalVacancies = totalVacancies;
	}

	/**
	 * Gets the total needed staff.
	 *
	 * @return the total needed staff
	 */
	public Long getTotalNeededStaff() {
		return totalNeededStaff;
	}

	/**
	 * Sets the total needed staff.
	 *
	 * @param totalNeededStaff
	 *            the new total needed staff
	 */
	public void setTotalNeededStaff(Long totalNeededStaff) {
		this.totalNeededStaff = totalNeededStaff;
	}

	/**
	 * Gets the total outsourced skills.
	 *
	 * @return the total outsourced skills
	 */
	public Long getTotalOutsourcedSkills() {
		return totalOutsourcedSkills;
	}

	/**
	 * Sets the total outsourced skills.
	 *
	 * @param totalOutsourcedSkills
	 *            the new total outsourced skills
	 */
	public void setTotalOutsourcedSkills(Long totalOutsourcedSkills) {
		this.totalOutsourcedSkills = totalOutsourcedSkills;
	}

	public NoHardToFillVacancies getNoHardToFillVacancies() {
		return noHardToFillVacancies;
	}

	public void setNoHardToFillVacancies(NoHardToFillVacancies noHardToFillVacancies) {
		this.noHardToFillVacancies = noHardToFillVacancies;
	}

	public VacancyReasons getVacancyReasons() {
		return vacancyReasons;
	}

	public void setVacancyReasons(VacancyReasons vacancyReasons) {
		this.vacancyReasons = vacancyReasons;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Long getTotalVacanciesFilled() {
		return totalVacanciesFilled;
	}

	public void setTotalVacanciesFilled(Long totalVacanciesFilled) {
		this.totalVacanciesFilled = totalVacanciesFilled;
	}

	public HighestQualificationRequired getHighestQualificationRequired() {
		return highestQualificationRequired;
	}

	public void setHighestQualificationRequired(HighestQualificationRequired highestQualificationRequired) {
		this.highestQualificationRequired = highestQualificationRequired;
	}

	public String getIntercentionDescription() {
		return intercentionDescription;
	}

	public void setIntercentionDescription(String intercentionDescription) {
		this.intercentionDescription = intercentionDescription;
	}

	public String getQualificationDescription() {
		return qualificationDescription;
	}

	public void setQualificationDescription(String qualificationDescription) {
		this.qualificationDescription = qualificationDescription;
	}

	public long getSspDemand() {
		return sspDemand;
	}

	public void setSspDemand(long sspDemand) {
		this.sspDemand = sspDemand;
	}

	public String getOfoDescription() {
		return ofoDescription;
	}

	public void setOfoDescription(String ofoDescription) {
		this.ofoDescription = ofoDescription;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getTotalPwd() {
		return totalPwd;
	}

	public void setTotalPwd(long totalPwd) {
		this.totalPwd = totalPwd;
	}

}
