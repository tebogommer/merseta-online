package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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

import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "summative_assessment_report_unit_standards")
public class SummativeAssessmentReportUnitStandards implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "summative_assessment_report_id", nullable = true)
	private SummativeAssessmentReport summativeAssessmentReport;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "unit_standards_id", nullable = true)
	private UnitStandards unitStandards;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assesment_date", length = 19)
	private Date assesmentDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "moderation_date", length = 19)
	private Date moderationDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_user_id", nullable = true)
	private Users assessorUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moderator_user_id", nullable = true)
	private Users moderatorUser;

	@Enumerated
	@Column(name = "competence_enum")
	private CompetenceEnum competenceEnum;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_application_id", nullable = true)
	private AssessorModeratorApplication assessorApplication;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_assessor_accredidation_id", nullable = true)
	private LegacyAssessorAccreditation legacyAssessorAccreditation;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_moderator_accredidation_id", nullable = true)
	private LegacyModeratorAccreditation legacyModeratorAccreditation;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moderator_application_id", nullable = true)
	private AssessorModeratorApplication moderatorApplication;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;
	
	@Enumerated
	@Column(name = "unit_standard_type_enum")
	private UnitStandardTypeEnum unitStandardTypeEnum;	
	
	/** The doc. */
	@Transient
	private List<Doc> docs;
	
	@Transient
	private Doc doc;
	
	@Transient
	private Doc documents;

	public SummativeAssessmentReportUnitStandards() {
		super();
	}

	public SummativeAssessmentReportUnitStandards(SummativeAssessmentReport summativeAssessmentReport, UnitStandards unitStandards) {
		this();
		this.summativeAssessmentReport = summativeAssessmentReport;
		this.unitStandards = unitStandards;
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
		SummativeAssessmentReportUnitStandards other = (SummativeAssessmentReportUnitStandards) obj;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SummativeAssessmentReport getSummativeAssessmentReport() {
		return summativeAssessmentReport;
	}

	public void setSummativeAssessmentReport(SummativeAssessmentReport summativeAssessmentReport) {
		this.summativeAssessmentReport = summativeAssessmentReport;
	}

	public UnitStandards getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(UnitStandards unitStandards) {
		this.unitStandards = unitStandards;
	}

	public Date getAssesmentDate() {
		return assesmentDate;
	}

	public void setAssesmentDate(Date assesmentDate) {
		this.assesmentDate = assesmentDate;
	}

	public CompetenceEnum getCompetenceEnum() {
		return competenceEnum;
	}

	public void setCompetenceEnum(CompetenceEnum competenceEnum) {
		this.competenceEnum = competenceEnum;
	}

	public Date getModerationDate() {
		return moderationDate;
	}

	public void setModerationDate(Date moderationDate) {
		this.moderationDate = moderationDate;
	}

	public Users getAssessorUser() {
		return assessorUser;
	}

	public void setAssessorUser(Users assessorUser) {
		this.assessorUser = assessorUser;
	}

	public Users getModeratorUser() {
		return moderatorUser;
	}

	public void setModeratorUser(Users moderatorUser) {
		this.moderatorUser = moderatorUser;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public AssessorModeratorApplication getAssessorApplication() {
		return assessorApplication;
	}

	public void setAssessorApplication(AssessorModeratorApplication assessorApplication) {
		this.assessorApplication = assessorApplication;
	}

	public AssessorModeratorApplication getModeratorApplication() {
		return moderatorApplication;
	}

	public void setModeratorApplication(AssessorModeratorApplication moderatorApplication) {
		this.moderatorApplication = moderatorApplication;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public UnitStandardTypeEnum getUnitStandardTypeEnum() {
		return unitStandardTypeEnum;
	}

	public void setUnitStandardTypeEnum(UnitStandardTypeEnum unitStandardTypeEnum) {
		this.unitStandardTypeEnum = unitStandardTypeEnum;
	}

	public LegacyAssessorAccreditation getLegacyAssessorAccreditation() {
		return legacyAssessorAccreditation;
	}

	public void setLegacyAssessorAccreditation(LegacyAssessorAccreditation legacyAssessorAccreditation) {
		this.legacyAssessorAccreditation = legacyAssessorAccreditation;
	}

	public LegacyModeratorAccreditation getLegacyModeratorAccreditation() {
		return legacyModeratorAccreditation;
	}

	public void setLegacyModeratorAccreditation(LegacyModeratorAccreditation legacyModeratorAccreditation) {
		this.legacyModeratorAccreditation = legacyModeratorAccreditation;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Doc getDocuments() {
		return documents;
	}

	public void setDocuments(Doc documents) {
		this.documents = documents;
	}
}
