package haj.com.report.bean;

import java.util.List;

import haj.com.entity.OfoCodes;
import haj.com.entity.Wsp;
import haj.com.entity.YesNoLookup;
import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.NoHardToFillVacancies;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.ScarcityReason;
import haj.com.entity.lookup.VacancyReasons;
import haj.com.framework.IDataEntity;

public class WspSkillsRequirementsBean implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The ofo codes. */
	private OfoCodes ofoCodes;
	private long number;

	/** The wsp. */
	private Wsp wsp;

	/** The yes no. */
	private YesNoLookup yesNo;

	/** The occupation category. */
	private OccupationCategory occupationCategory;

	/** The scarcity reason. */
	private ScarcityReason scarcityReason;

	/** The intervention type. */
	private InterventionType interventionType;

	/** The job title. */
	private String jobTitle;

	/** The total vacancies. */
	private Long totalVacancies;

	/** The total vacancies. */
	private Long totalVacanciesFilled;

	/** The total needed staff. */
	private Long totalNeededStaff;

	/** The total outsourced skills. */
	private Long totalOutsourcedSkills;

	/** The noHardToFillVacancies */
	private NoHardToFillVacancies noHardToFillVacancies;

	/** The noHardToFillVacancies */
	private VacancyReasons vacancyReasons;

	/** The HighestQualificationRequired */
	private HighestQualificationRequired highestQualificationRequired;

	/** The Qualification NOT REQUIRED ANYMORE */
	private List<Qualification> qualification;
	private List<InterventionType> interventionTypes;

	/**
	 * Instantiates a new wsp skills requirements.
	 */
	public WspSkillsRequirementsBean() {
		super();
	}

	public WspSkillsRequirementsBean(Wsp wsp, OfoCodes ofoCodes) {
		super();
		this.wsp = wsp;
		this.ofoCodes = ofoCodes;
	}

	public WspSkillsRequirementsBean(OfoCodes ofoCodes, long sumTotal, long sumFilled) {
		super();
		this.ofoCodes = ofoCodes;
		this.number = sumTotal - sumFilled;
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
		result = prime * result + ((ofoCodes.getId() == null) ? 0 : ofoCodes.getId().hashCode());
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
		WspSkillsRequirementsBean other = (WspSkillsRequirementsBean) obj;
		if (ofoCodes.getId() == null) {
			if (other.ofoCodes.getId() != null)
				return false;
		} else if (!ofoCodes.getId().equals(other.ofoCodes.getId()))
			return false;
		return true;
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

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public List<Qualification> getQualification() {
		return qualification;
	}

	public void setQualification(List<Qualification> qualification) {
		this.qualification = qualification;
	}

	public List<InterventionType> getInterventionTypes() {
		return interventionTypes;
	}

	public void setInterventionTypes(List<InterventionType> interventionTypes) {
		this.interventionTypes = interventionTypes;
	}

}
