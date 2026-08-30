package haj.com.entity;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "work_place_approval_data")
public class WorkPlaceApprovalData implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@CSVAnnotation(className = String.class, name = "Linked_SDL_Number")
	@Column(name = "sdl_number")
	private String sdlNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@CSVAnnotation(className = String.class, name = "OFO_Code")
	@Column(name = "ofo_code")
	private String ofoCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	@CSVAnnotation(className = String.class, name = "Qualification_Code")
	@Column(name = "qualification_code")
	private String qualificationCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@CSVAnnotation(className = String.class, datePattern = "yyyy/mm/dd", name = "AccreditationStartDate")
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	@CSVAnnotation(className = String.class, name = "LearnershipCode")
	@Column(name = "learnership_code")
	private String learnershipCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "learnership_id", nullable = true)
	private Learnership learnership;

	@CSVAnnotation(className = String.class, name = "sProgrammeCode")
	@Column(name = "s_programme_code")
	private String sProgrammeCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "skills_program_id", nullable = true)
	private SkillsProgram skillsProgram;

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

	public String getSdlNumber() {
		return sdlNumber;
	}

	public void setSdlNumber(String sdlNumber) {
		this.sdlNumber = sdlNumber;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getAccreditationStartDate() {
		return accreditationStartDate;
	}

	public void setAccreditationStartDate(String accreditationStartDate) {
		this.accreditationStartDate = accreditationStartDate;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getsProgrammeCode() {
		return sProgrammeCode;
	}

	public void setsProgrammeCode(String sProgrammeCode) {
		this.sProgrammeCode = sProgrammeCode;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}
}
