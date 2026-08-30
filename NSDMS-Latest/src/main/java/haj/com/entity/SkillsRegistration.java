package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsIdentification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "skills_registration")
public class SkillsRegistration implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submission_date", length = 19)
	private Date submissionDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_identification_id", nullable = true)
	private SkillsIdentification skillsIdentification;

	@Column(name = "research_summary", columnDefinition = "LONGTEXT")
	private String researchSummary;

	@Column(name = "fit_career", columnDefinition = "LONGTEXT")
	private String fitCareer;

	@Column(name = "proposed_title", length = 200)
	private String proposedTitle;
	
	@Column(name = "amended_title", length = 200)
	private String amendedTitle;
	
	@Column(name = "how_will_assist", length = 200)
	private String howWillAssist;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;

	@Enumerated
	@Column(name = "skills_type")
	private SkillsTypeEnum skillsType;

	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "us_part_of_nqf")
	private YesNoEnum usPartOFNQF;

	@Enumerated
	@Column(name = "learning_assumed_to_be_in_place")
	private YesNoEnum learningAssumedToBeInPlace;

	@Enumerated
	@Column(name = "sequence_of_us_addressed")
	private YesNoEnum sequenceOfUSAddressed;

	@Enumerated
	@Column(name = "intergrated_summative_assesment")
	private YesNoEnum intergratedSummativeAssesment;

	@Enumerated
	@Column(name = "sectors_identified")
	private YesNoEnum sectorsIdentified;

	@Enumerated
	@Column(name = "learner_on_completion_have_employable_skill")
	private YesNoEnum learnerOnCompletionhaveEmployableSkill;

	@Enumerated
	@Column(name = "title_of_the_skills_programme_skills_set_addressing")
	private YesNoEnum titleOfTheSkillsProgrammeSkillsSetAddressing;

	@Enumerated
	@Column(name = "skills_programmes_articulate")
	private YesNoEnum skillsProgrammesArticulate;

	@Enumerated
	@Column(name = "aligned_to_an_existing_organizing_framework")
	private YesNoEnum alignedToAnExistingOrganizingFramework;

	@Enumerated
	@Column(name = "has_nqf_been_determined")
	private YesNoEnum hasNQFBeenDetermined;

	@Enumerated
	@Column(name = "above_nqf_level_1")
	private YesNoEnum aboveNQFlevel1;
	
	/**The last date for enrolment (Linked to the Qualification)*/
	@Column(name = "last_date_for_enrolment",  nullable = true )
	private Date lastDateForEnrolment;
	
	@Column(name = "registration_number",  nullable = true )
	private String registrationNumber;
	
	//The ETQA Review Committee Schedule.
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	@Transient
	private List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards;
	@Transient
	private List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandards;
	
	@Transient
	private boolean show;
	@Transient
	private String meetingMessage;
	@Transient
	private List<Doc> docs;

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
		SkillsRegistration other = (SkillsRegistration) obj;
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
	 *            the id to set
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
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SkillsIdentification getSkillsIdentification() {
		return skillsIdentification;
	}

	public void setSkillsIdentification(SkillsIdentification skillsIdentification) {
		this.skillsIdentification = skillsIdentification;
	}

	public String getResearchSummary() {
		return researchSummary;
	}

	public void setResearchSummary(String researchSummary) {
		this.researchSummary = researchSummary;
	}

	public String getFitCareer() {
		return fitCareer;
	}

	public void setFitCareer(String fitCareer) {
		this.fitCareer = fitCareer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getProposedTitle() {
		return proposedTitle;
	}

	public void setProposedTitle(String proposedTitle) {
		this.proposedTitle = proposedTitle;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public SkillsTypeEnum getSkillsType() {
		return skillsType;
	}

	public void setSkillsType(SkillsTypeEnum skillsType) {
		this.skillsType = skillsType;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getHowWillAssist() {
		return howWillAssist;
	}

	public void setHowWillAssist(String howWillAssist) {
		this.howWillAssist = howWillAssist;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public List<SkillsRegistrationUnitStandards> getSkillsRegistrationUnitStandards() {
		return skillsRegistrationUnitStandards;
	}

	public void setSkillsRegistrationUnitStandards(List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards) {
		this.skillsRegistrationUnitStandards = skillsRegistrationUnitStandards;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public YesNoEnum getUsPartOFNQF() {
		return usPartOFNQF;
	}

	public void setUsPartOFNQF(YesNoEnum usPartOFNQF) {
		this.usPartOFNQF = usPartOFNQF;
	}

	public YesNoEnum getLearningAssumedToBeInPlace() {
		return learningAssumedToBeInPlace;
	}

	public void setLearningAssumedToBeInPlace(YesNoEnum learningAssumedToBeInPlace) {
		this.learningAssumedToBeInPlace = learningAssumedToBeInPlace;
	}

	public YesNoEnum getSequenceOfUSAddressed() {
		return sequenceOfUSAddressed;
	}

	public void setSequenceOfUSAddressed(YesNoEnum sequenceOfUSAddressed) {
		this.sequenceOfUSAddressed = sequenceOfUSAddressed;
	}

	public YesNoEnum getIntergratedSummativeAssesment() {
		return intergratedSummativeAssesment;
	}

	public void setIntergratedSummativeAssesment(YesNoEnum intergratedSummativeAssesment) {
		this.intergratedSummativeAssesment = intergratedSummativeAssesment;
	}

	public YesNoEnum getSectorsIdentified() {
		return sectorsIdentified;
	}

	public void setSectorsIdentified(YesNoEnum sectorsIdentified) {
		this.sectorsIdentified = sectorsIdentified;
	}

	public YesNoEnum getLearnerOnCompletionhaveEmployableSkill() {
		return learnerOnCompletionhaveEmployableSkill;
	}

	public void setLearnerOnCompletionhaveEmployableSkill(YesNoEnum learnerOnCompletionhaveEmployableSkill) {
		this.learnerOnCompletionhaveEmployableSkill = learnerOnCompletionhaveEmployableSkill;
	}

	public YesNoEnum getTitleOfTheSkillsProgrammeSkillsSetAddressing() {
		return titleOfTheSkillsProgrammeSkillsSetAddressing;
	}

	public void setTitleOfTheSkillsProgrammeSkillsSetAddressing(YesNoEnum titleOfTheSkillsProgrammeSkillsSetAddressing) {
		this.titleOfTheSkillsProgrammeSkillsSetAddressing = titleOfTheSkillsProgrammeSkillsSetAddressing;
	}

	public YesNoEnum getSkillsProgrammesArticulate() {
		return skillsProgrammesArticulate;
	}

	public void setSkillsProgrammesArticulate(YesNoEnum skillsProgrammesArticulate) {
		this.skillsProgrammesArticulate = skillsProgrammesArticulate;
	}

	public YesNoEnum getAlignedToAnExistingOrganizingFramework() {
		return alignedToAnExistingOrganizingFramework;
	}

	public void setAlignedToAnExistingOrganizingFramework(YesNoEnum alignedToAnExistingOrganizingFramework) {
		this.alignedToAnExistingOrganizingFramework = alignedToAnExistingOrganizingFramework;
	}

	public YesNoEnum getHasNQFBeenDetermined() {
		return hasNQFBeenDetermined;
	}

	public void setHasNQFBeenDetermined(YesNoEnum hasNQFBeenDetermined) {
		this.hasNQFBeenDetermined = hasNQFBeenDetermined;
	}

	public YesNoEnum getAboveNQFlevel1() {
		return aboveNQFlevel1;
	}

	public void setAboveNQFlevel1(YesNoEnum aboveNQFlevel1) {
		this.aboveNQFlevel1 = aboveNQFlevel1;
	}

	public String getAmendedTitle() {
		return amendedTitle;
	}

	public void setAmendedTitle(String amendedTitle) {
		this.amendedTitle = amendedTitle;
	}

	@Transient
	public boolean checkforNo() {
		return Stream.of(
					 usPartOFNQF 
					 ,learningAssumedToBeInPlace 
					 ,sequenceOfUSAddressed 
					 ,intergratedSummativeAssesment 
					 ,sectorsIdentified 
					 ,learnerOnCompletionhaveEmployableSkill 
					 ,titleOfTheSkillsProgrammeSkillsSetAddressing 
					 ,skillsProgrammesArticulate 
					 ,alignedToAnExistingOrganizingFramework 
					 ,hasNQFBeenDetermined 
					 ,aboveNQFlevel1
					 )
				.allMatch(x -> x == YesNoEnum.Yes);


	}
	
	public Date getLastDateForEnrolment() {
		return lastDateForEnrolment;
	}

	public void setLastDateForEnrolment(Date lastDateForEnrolment) {
		this.lastDateForEnrolment = lastDateForEnrolment;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public List<SkillsRegistrationQualificationUnitStandards> getSkillsRegistrationQualificationUnitStandards() {
		return skillsRegistrationQualificationUnitStandards;
	}

	public void setSkillsRegistrationQualificationUnitStandards(
			List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandards) {
		this.skillsRegistrationQualificationUnitStandards = skillsRegistrationQualificationUnitStandards;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getMeetingMessage() {
		return meetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		this.meetingMessage = meetingMessage;
	}
}
