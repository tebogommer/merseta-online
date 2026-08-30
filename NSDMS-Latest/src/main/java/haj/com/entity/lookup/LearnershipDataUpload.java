package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "learnership_data_upload")
public class LearnershipDataUpload extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1 Lship ID (SAQA)
	 */
	@CSVAnnotation(name = "Lship_ID_(SAQA)", className = String.class)
	@Column(name = "lship_id_saqa")
	private String lShipIdSaqa;

	/*
	 * PN: 2 Learnership Code
	 */
	@CSVAnnotation(name = "Learnership_Code", className = String.class)
	@Column(name = "learnership_code")
	private String learnershipCode;

	/*
	 * PN: 3 Learnership Title
	 */
	@CSVAnnotation(name = "Learnership_Title", className = String.class)
	@Column(name = "learnership_title")
	private String learnershipTitle;

	/*
	 * PN: 4 Lship Regis End Date
	 */
	@CSVAnnotation(name = "Lship_Regis_End_Date", className = String.class)
	@Column(name = "lship_regis_end_date")
	private String lShipRegisEndDate;

	/*
	 * PN: 5 ETQA registering Lship
	 */
	@CSVAnnotation(name = "ETQA_registering_Lship", className = String.class)
	@Column(name = "etqa_registering_lship")
	private String etqaRegisteringLship;

	/*
	 * PN: 6 Lship ID (SAQA)
	 */
	@CSVAnnotation(name = "Lship_ID_(SAQA)_2", className = String.class)
	@Column(name = "lship_id_saqa_two")
	private String lShipIdSaqaTwo;

	/*
	 * PN: 7 Qual ID
	 */
	@CSVAnnotation(name = "Qual_ID", className = String.class)
	@Column(name = "qual_Id")
	private String qualId;

	/*
	 * PN: 8 Qualification Title
	 */
	@CSVAnnotation(name = "Qualification_Title", className = String.class)
	@Column(name = "qualification_title")
	private String qualificationTitle;

	/*
	 * PN: 9 Pre-2009 NQF Level
	 */
	@CSVAnnotation(name = "Pre-2009_NQF_Level", className = String.class)
	@Column(name = "pre_2009_nqf_level")
	private String pre2009NqfLevel;

	/*
	 * PN: 10 NQF Level
	 */
	@CSVAnnotation(name = "NQF_Level", className = String.class)
	@Column(name = "nqf_level")
	private String nqfLevel;

	/*
	 * PN: 11 Min Credits
	 */
	@CSVAnnotation(name = "Min_Credits", className = String.class)
	@Column(name = "min_credits")
	private String minCredits;

	/*
	 * PN: 12 Qual Status
	 */
	@CSVAnnotation(name = "Qual_Status", className = String.class)
	@Column(name = "qual_status")
	private String qualStatus;

	/*
	 * PN: 13 Qual Regis End
	 */
	@CSVAnnotation(name = "Qual_Regis_End", className = String.class)
	@Column(name = "qual_regis_end")
	private String qualRegisEnd;
	
	public LearnershipDataUpload() {
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
		LearnershipDataUpload other = (LearnershipDataUpload) obj;
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

	public String getlShipIdSaqa() {
		return lShipIdSaqa;
	}

	public void setlShipIdSaqa(String lShipIdSaqa) {
		this.lShipIdSaqa = lShipIdSaqa;
	}

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getLearnershipTitle() {
		return learnershipTitle;
	}

	public void setLearnershipTitle(String learnershipTitle) {
		this.learnershipTitle = learnershipTitle;
	}

	public String getlShipRegisEndDate() {
		return lShipRegisEndDate;
	}

	public void setlShipRegisEndDate(String lShipRegisEndDate) {
		this.lShipRegisEndDate = lShipRegisEndDate;
	}

	public String getEtqaRegisteringLship() {
		return etqaRegisteringLship;
	}

	public void setEtqaRegisteringLship(String etqaRegisteringLship) {
		this.etqaRegisteringLship = etqaRegisteringLship;
	}

	public String getlShipIdSaqaTwo() {
		return lShipIdSaqaTwo;
	}

	public void setlShipIdSaqaTwo(String lShipIdSaqaTwo) {
		this.lShipIdSaqaTwo = lShipIdSaqaTwo;
	}

	public String getQualId() {
		return qualId;
	}

	public void setQualId(String qualId) {
		this.qualId = qualId;
	}

	public String getQualificationTitle() {
		return qualificationTitle;
	}

	public void setQualificationTitle(String qualificationTitle) {
		this.qualificationTitle = qualificationTitle;
	}

	public String getPre2009NqfLevel() {
		return pre2009NqfLevel;
	}

	public void setPre2009NqfLevel(String pre2009NqfLevel) {
		this.pre2009NqfLevel = pre2009NqfLevel;
	}

	public String getNqfLevel() {
		return nqfLevel;
	}

	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	public String getMinCredits() {
		return minCredits;
	}

	public void setMinCredits(String minCredits) {
		this.minCredits = minCredits;
	}

	public String getQualStatus() {
		return qualStatus;
	}

	public void setQualStatus(String qualStatus) {
		this.qualStatus = qualStatus;
	}

	public String getQualRegisEnd() {
		return qualRegisEnd;
	}

	public void setQualRegisEnd(String qualRegisEnd) {
		this.qualRegisEnd = qualRegisEnd;
	}

}
