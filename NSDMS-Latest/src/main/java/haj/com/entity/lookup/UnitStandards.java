package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.Doc;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity is the unit standards 
 * 
 * UnitStandards.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "saqa_unitstandard")
@AuditTable(value = "saqa_unitstandard_hist")
@Audited
public class UnitStandards implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of UnitStandards. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** title or description of the unit standard. */
	@Column(name = "unitstdtitle")
	private String title;

	/** Code for the unit standard. */
	@Column(name = "unitstandardid")
	private Integer code;

	@Column(name = "nqflevelg2DESCRIPTION")
	private String nqf;
	
	@Column(name = "unitstdnumberofcredits")
	private String unitstdnumberofcredits;
	
	@Column(name = "unitstdtypedesc")
	private String unitstdtypedesc;
	
	/** The nqflevelg 2 description. */
    //private String nqflevelg2description;
		
	@Column(name = "field")
	private String field;
	
	@Column(name = "saqadecisionnumber")
	private String saqadecisionnumber;
	
	@Transient
	private Doc doc;
	
	/**The last date for enrolment*/
	 @Column(name = "lastDateForEnrolment",  nullable = true )
	 private Date lastDateForEnrolment;
	
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "usregistrationstartdate", nullable = true)
	 private Date usregistrationstartDate;
	
	 @Temporal(TemporalType.TIMESTAMP)
     @Column(name = "usregistrationenddate", nullable = true)
     private Date usregistrationendDate;
	 
	 @Transient
	 private Boolean cannotRemove;
	 
	 @Transient
	 private Qualification qualification;
	 
	 /** The code in String. */
		@Column(name = "unit_standard_id_string", insertable = false, updatable = false)
		private String codeString;
	 
	 
		
	/**
	 * Constructor using title, code and NQF Level fields
	 * 
	 * @param title
	 * @param code
	 * @param nqfLevel
	 */

	public UnitStandards() {
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
		UnitStandards other = (UnitStandards) obj;
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
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * Gets the title.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	public String getNqf() {
		return nqf;
	}

	public void setNqf(String nqf) {
		this.nqf = nqf;
	}

	public String getUnitstdnumberofcredits() {
		return unitstdnumberofcredits;
	}

	public void setUnitstdnumberofcredits(String unitstdnumberofcredits) {
		this.unitstdnumberofcredits = unitstdnumberofcredits;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSaqadecisionnumber() {
		return saqadecisionnumber;
	}

	public void setSaqadecisionnumber(String saqadecisionnumber) {
		this.saqadecisionnumber = saqadecisionnumber;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Date getLastDateForEnrolment() {
		return lastDateForEnrolment;
	}

	public void setLastDateForEnrolment(Date lastDateForEnrolment) {
		this.lastDateForEnrolment = lastDateForEnrolment;
	}

	public Date getUsregistrationstartDate() {
		return usregistrationstartDate;
	}

	public void setUsregistrationstartDate(Date usregistrationstartDate) {
		this.usregistrationstartDate = usregistrationstartDate;
	}

	public Date getUsregistrationendDate() {
		return usregistrationendDate;
	}

	public void setUsregistrationendDate(Date usregistrationendDate) {
		this.usregistrationendDate = usregistrationendDate;
	}

	public String getNqflevelg2description() {
		return nqf;
	}

	public void setNqflevelg2description(String nqflevelg2description) {
		this.nqf = nqflevelg2description;
	}

	public String getUnitstdtypedesc() {
		return unitstdtypedesc;
	}

	public void setUnitstdtypedesc(String unitstdtypedesc) {
		this.unitstdtypedesc = unitstdtypedesc;
	}

	public Boolean getCannotRemove() {
		return cannotRemove;
	}

	public void setCannotRemove(Boolean cannotRemove) {
		this.cannotRemove = cannotRemove;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public String getCodeString() {
		return codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}
}
