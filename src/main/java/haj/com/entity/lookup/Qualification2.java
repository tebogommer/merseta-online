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
 * Tab Name: Qualification
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qualification2")
public class Qualification2 extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Qualification. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	
	/*
	 * PN: 1
	 * Assessor ID 
	 */
	@CSVAnnotation(name = "Assessor ID", className = String.class)
	@Column(name = "assessor_id"  )
	private String assessorId;
	
	/* 
	 * PN: 2
	 * AlternateIDNo
	 */
	@CSVAnnotation(name = "AlternateIDNo", className = String.class)
	@Column(name = "alternate_id_no"  )
	private String alternateIDNo;
	
	
	/*
	 * PN: 3
	 * Firstname 
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name"  )
	private String firstName;
	
	/* 
	 * PN: 4
	 * MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names"  )
	private String middleNames;
	
	
	/*
	 * PN: 5
	 * Surname 
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname"  )
	private String surname;
	
	/* 
	 * PN: 6
	 * QualCode
	 */
	@CSVAnnotation(name = "QualCode", className = String.class)
	@Column(name = "qual_code"  )
	private String qualCode;
	
	
	/*
	 * PN: 7
	 * AssessorRegStartDate 
	 */
	@CSVAnnotation(name = "AssessorRegStartDate", className = String.class)
	@Column(name = "assessor_reg_start_date"  )
	private String assessorRegStartDate;
	
	/* 
	 * PN: 8
	 * AssessorFirstRegDate
	 */
	@CSVAnnotation(name = "AssessorFirstRegDate", className = String.class)
	@Column(name = "assessor_first_reg_date"  )
	private String assessorFirstRegDate;
	
	
	/*
	 * PN: 9
	 * AssessorRegEndDate 
	 */
	@CSVAnnotation(name = "AssessorRegEndDate", className = String.class)
	@Column(name = "assessor_reg_end_date"  )
	private String assessorRegEndDate;
	
	/* 
	 * PN: 10
	 * AssessorRegNo
	 */
	@CSVAnnotation(name = "AssessorRegNo", className = String.class)
	@Column(name = "assessor_reg_no"  )
	private String assessorRegNo;
	
	
	/*
	 * PN: 11
	 * AssessorStatusDesc 
	 */
	@CSVAnnotation(name = "AssessorStatusDesc", className = String.class)
	@Column(name = "assessor_status_desc"  )
	private String assessorStatusDesc;
	
	
	/**
	 * Instantiates a new Leadership.
	 */
	public Qualification2() {
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
		Qualification2 other = (Qualification2) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	public String getAssessorId() {
		return assessorId;
	}


	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}


	public String getAlternateIDNo() {
		return alternateIDNo;
	}


	public void setAlternateIDNo(String alternateIDNo) {
		this.alternateIDNo = alternateIDNo;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleNames() {
		return middleNames;
	}


	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getQualCode() {
		return qualCode;
	}


	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}


	public String getAssessorRegStartDate() {
		return assessorRegStartDate;
	}


	public void setAssessorRegStartDate(String assessorRegStartDate) {
		this.assessorRegStartDate = assessorRegStartDate;
	}


	public String getAssessorFirstRegDate() {
		return assessorFirstRegDate;
	}


	public void setAssessorFirstRegDate(String assessorFirstRegDate) {
		this.assessorFirstRegDate = assessorFirstRegDate;
	}


	public String getAssessorRegEndDate() {
		return assessorRegEndDate;
	}


	public void setAssessorRegEndDate(String assessorRegEndDate) {
		this.assessorRegEndDate = assessorRegEndDate;
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




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}

	


	

}
