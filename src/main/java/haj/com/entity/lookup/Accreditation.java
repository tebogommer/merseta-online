package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * Excel name: Assessor
 * Tab Name: Accreditation
 *
 * @author Techfinium
 */
@Entity
@Table(name = "accreditation")
public class Accreditation extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	
	/*
	 * PN: 1
	 * TitleDesc 
	 */
	@CSVAnnotation(name = "TitleDesc", className = String.class)
	@Column(name = "title_desc"  )
	private String titleDesc;
	
	/* 
	 * PN: 2
	 * Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "sdl_no"  )
	private String sdlNo;
	
	
	/*
	 * PN: 3
	 * Surname 
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname"  )
	private String surname;
	
	/* 
	 * PN: 4
	 * IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class)
	@Column(name = "id_num"  )
	private String idNo;
	
	
	/*
	 * PN: 5
	 * AssessorType 
	 */
	@CSVAnnotation(name = "AssessorType", className = String.class)
	@Column(name = "assessor_type"  )
	private String assessorType;
	
	/* 
	 * PN: 6
	 * AssessorRegStartDate
	 */
	@CSVAnnotation(name = "AssessorRegStartDate", className = String.class)
	@Column(name = "assessor_reg_start_date"  )
	private String assessorRegStartDate;
	
	
	/*
	 * PN: 7
	 * AssessorRegEndDate 
	 */
	@CSVAnnotation(name = "AssessorRegEndDate", className = String.class)
	@Column(name = "assessor_reg_end_date"  )
	private String AssessorRegEndDate;
	
	/* 
	 * PN: 8
	 * AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no"  )
	private String assessorRegNo;
	
	
	/*
	 * PN: 9
	 * AssessorStatusDesc 
	 */
	@CSVAnnotation(name = "AssessorStatusDesc", className = String.class)
	@Column(name = "assessor_status_desc"  )
	private String assessorStatusDesc;
	
	/* 
	 * PN: 10
	 * AssessorStatusEffectiveDate
	 */
	@CSVAnnotation(name = "AssessorStatusEffectiveDate", className = String.class)
	@Column(name = "assessor_status_effective_date"  )
	private String assessorStatusEffectiveDate;
	
	
	/*
	 * PN: 11
	 * LastDateChanged 
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed"  )
	private String lastDateChanged;
	
	/* 
	 * PN: 12
	 * sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no"  )
	private String sRefNo;
	
	
	/*
	 * PN: 13
	 * DecisionNumber 
	 */
	@CSVAnnotation(name = "DecisionNumber", className = String.class)
	@Column(name = "decision_number"  )
	private String decisionNumber;
	
	/* 
	 * PN: 14
	 * ReviewCommitteeDate
	 */
	@CSVAnnotation(name = "ReviewCommitteeDate", className = String.class)
	@Column(name = "review_committee_date"  )
	private String reviewCommitteeDate;
		

	
	/**
	 * Instantiates a new Accreditation.
	 */
	public Accreditation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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
		Accreditation other = (Accreditation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	public String getSdlNo() {
		return sdlNo;
	}

	public void setSdlNo(String sdlNo) {
		this.sdlNo = sdlNo;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getAssessorType() {
		return assessorType;
	}

	public void setAssessorType(String assessorType) {
		this.assessorType = assessorType;
	}

	public String getAssessorRegStartDate() {
		return assessorRegStartDate;
	}

	public void setAssessorRegStartDate(String assessorRegStartDate) {
		this.assessorRegStartDate = assessorRegStartDate;
	}

	public String getAssessorRegEndDate() {
		return AssessorRegEndDate;
	}

	public void setAssessorRegEndDate(String assessorRegEndDate) {
		AssessorRegEndDate = assessorRegEndDate;
	}

	public String getAssessorRegNo() {
		return assessorRegNo;
	}

	public void setAssessorRegNo(String assessorRegNo) {
		this.assessorRegNo = assessorRegNo;
	}

	public String getAssessorStatusDesc() {
		return assessorStatusDesc;
	}

	public void setAssessorStatusDesc(String assessorStatusDesc) {
		this.assessorStatusDesc = assessorStatusDesc;
	}

	public String getAssessorStatusEffectiveDate() {
		return assessorStatusEffectiveDate;
	}

	public void setAssessorStatusEffectiveDate(String assessorStatusEffectiveDate) {
		this.assessorStatusEffectiveDate = assessorStatusEffectiveDate;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public String getReviewCommitteeDate() {
		return reviewCommitteeDate;
	}

	public void setReviewCommitteeDate(String reviewCommitteeDate) {
		this.reviewCommitteeDate = reviewCommitteeDate;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	

}
