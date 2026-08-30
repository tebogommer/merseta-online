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

@Entity
@Table(name = "setmis_file_304_extracted")
public class SetmisFile304Extracted implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "line_number")
	private String lineNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "nonnqfintervcode")
	private String nonNQFIntervCode;
	
	@CSVAnnotation(length = 200)
	@Column(name = "nonnqfintervname")
	private String nonNQFIntervName;
	
	@CSVAnnotation(length = 8)
	@Column(name = "filler01")
	private String filler01;
	
	@CSVAnnotation(length = 8)
	@Column(name = "subfieldid")
	private String subfieldId;
	
	@CSVAnnotation(length = 8)
	@Column(name = "filler02")
	private String filler02;
	
	@CSVAnnotation(length = 8)
	@Column(name = "nonnqfintervregstartdatestring")
	private String nonNQFIntervRegStartDateString;
//	private Date nonNQFIntervRegStartDate;
	
	@CSVAnnotation(length = 8)
	@Column(name = "nonnqfintervregenddatestring")
	private String nonNQFIntervRegEndDateString;
//	private Date nonNQFIntervRegEndDate;
	
	@CSVAnnotation(length = 20)
	@Column(name = "filler03")
	private String filler03;
	
	@CSVAnnotation(length = 10)
	@Column(name = "nonnqfintervetqeid")
	private String nonNQFIntervETQEId;
	
	@CSVAnnotation(length = 10)
	@Column(name = "nonnqfintervstatusid")
	private String nonNQFIntervStatusId;

	@CSVAnnotation(length = 10)
	@Column(name = "nonnqfintervcredit")
	private String nonNQFIntervCredit;
	
	@CSVAnnotation(length = 10)
	@Column(name = "learningprogrammetypeid")
	private String learningProgrammeTypeId;
	
	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
//	private Date dateStamp;

	

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
		SetmisFile304Extracted other = (SetmisFile304Extracted) obj;
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

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getNonNQFIntervCode() {
		return nonNQFIntervCode;
	}

	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		this.nonNQFIntervCode = nonNQFIntervCode;
	}

	public String getNonNQFIntervName() {
		return nonNQFIntervName;
	}

	public void setNonNQFIntervName(String nonNQFIntervName) {
		this.nonNQFIntervName = nonNQFIntervName;
	}

	public String getFiller01() {
		return filler01;
	}

	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}

	public String getSubfieldId() {
		return subfieldId;
	}

	public void setSubfieldId(String subfieldId) {
		this.subfieldId = subfieldId;
	}

	public String getFiller02() {
		return filler02;
	}

	public void setFiller02(String filler02) {
		this.filler02 = filler02;
	}

	public String getNonNQFIntervRegStartDateString() {
		return nonNQFIntervRegStartDateString;
	}

	public void setNonNQFIntervRegStartDateString(String nonNQFIntervRegStartDateString) {
		this.nonNQFIntervRegStartDateString = nonNQFIntervRegStartDateString;
	}

	public String getNonNQFIntervRegEndDateString() {
		return nonNQFIntervRegEndDateString;
	}

	public void setNonNQFIntervRegEndDateString(String nonNQFIntervRegEndDateString) {
		this.nonNQFIntervRegEndDateString = nonNQFIntervRegEndDateString;
	}

	public String getFiller03() {
		return filler03;
	}

	public void setFiller03(String filler03) {
		this.filler03 = filler03;
	}

	public String getNonNQFIntervETQEId() {
		return nonNQFIntervETQEId;
	}

	public void setNonNQFIntervETQEId(String nonNQFIntervETQEId) {
		this.nonNQFIntervETQEId = nonNQFIntervETQEId;
	}

	public String getNonNQFIntervStatusId() {
		return nonNQFIntervStatusId;
	}

	public void setNonNQFIntervStatusId(String nonNQFIntervStatusId) {
		this.nonNQFIntervStatusId = nonNQFIntervStatusId;
	}

	public String getNonNQFIntervCredit() {
		return nonNQFIntervCredit;
	}

	public void setNonNQFIntervCredit(String nonNQFIntervCredit) {
		this.nonNQFIntervCredit = nonNQFIntervCredit;
	}

	public String getLearningProgrammeTypeId() {
		return learningProgrammeTypeId;
	}

	public void setLearningProgrammeTypeId(String learningProgrammeTypeId) {
		this.learningProgrammeTypeId = learningProgrammeTypeId;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}
}