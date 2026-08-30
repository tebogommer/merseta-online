package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

@Entity
@Table(
		  name = "home_affairs"
		, indexes = {
				  @Index(name = "dha_id_number", columnList = "dha_id_number")
				, @Index(name = "dha_id_number_status", columnList = "dha_id_number, death_status")
		}
)
public class HomeAffairs extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
//	@CSVAnnotation(name = "Name of row on excel", className = String.class)
//	@Column(name = "keep to standard")
//	private String keep to standard;
	
	/*
	 * Filed Sizes:
	 * Book: 				@Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so characters 	@Column(name = "dttc_id" , length = 500)
	 */
	
	/*
	 * PN: 1
	 * MERSETA_ID_NO 
	 */
	@CSVAnnotation(name = "MERSETA_ID_NO", className = String.class)
	@Column(name = "merseta_id_no")
	private String mersetaIDNO;
	
	/* 
	 * PN: 2
	 * DHA_ID_NUMBER
	 */
	@CSVAnnotation(name = "DHA_ID_NUMBER", className = String.class)
	@Column(name = "dha_id_number")
	private String dhaIDNumber;
	
	/* 
	 * PN: 3
	 * SURNAME
	 */
	@CSVAnnotation(name = "SURNAME", className = String.class)
	@Column(name = "surname")
	private String surname;
	
	/* 
	 * PN: 4
	 * FULL_NAMES
	 */
	@CSVAnnotation(name = "FULL_NAMES", className = String.class)
	@Column(name = "full_names")
	private String fullNames;
	
	/* 
	 * PN: 5
	 * GENDER
	 */
	@CSVAnnotation(name = "GENDER", className = String.class)
	@Column(name = "gender")
	private String gender;
	
	/* 
	 * PN: 6
	 * ID_STATUS
	 */
	@CSVAnnotation(name = "ID_STATUS", className = String.class)
	@Column(name = "id_status")
	private String idStatus;
	
	/* 
	 * PN: 7
	 * DEATH_STATUS	
	 */
	@CSVAnnotation(name = "DEATH_STATUS", className = String.class)
	@Column(name = "death_status")
	private String deathStatus;
	
	/* 
	 * PN: 8
	 * DEATH_DATE	
	 */
	@CSVAnnotation(name = "DEATH_DATE", className = String.class)
	@Column(name = "death_date")
	private String deathDate;
	
	/* 
	 * PN: 9
	 * ID_CARD_DATE_ISSUED	
	 */
	@CSVAnnotation(name = "ID_CARD_DATE_ISSUED", className = String.class)
	@Column(name = "id_card_date_issued")
	private String idCardDateIssued;
	
	/* 
	 * PN: 10
	 * ID_BOOK_DATE_ISSUED	
	 */
	@CSVAnnotation(name = "ID_BOOK_DATE_ISSUED", className = String.class)
	@Column(name = "id_book_date_issued")
	private String idBookDateIssued;
	
	/* 
	 * PN: 11
	 * MAIDEN_NAME	
	 */
	@CSVAnnotation(name = "MAIDEN_NAME", className = String.class)
	@Column(name = "maiden_name")
	private String maidenName;
	
	

	public HomeAffairs() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HomeAffairs other = (HomeAffairs) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/* Getters and setters  */

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

	public String getMersetaIDNO() {
		return mersetaIDNO;
	}

	public void setMersetaIDNO(String mersetaIDNO) {
		this.mersetaIDNO = mersetaIDNO;
	}

	public String getDhaIDNumber() {
		return dhaIDNumber;
	}

	public void setDhaIDNumber(String dhaIDNumber) {
		this.dhaIDNumber = dhaIDNumber;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getDeathStatus() {
		return deathStatus;
	}

	public void setDeathStatus(String deathStatus) {
		this.deathStatus = deathStatus;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getIdCardDateIssued() {
		return idCardDateIssued;
	}

	public void setIdCardDateIssued(String idCardDateIssued) {
		this.idCardDateIssued = idCardDateIssued;
	}

	public String getIdBookDateIssued() {
		return idBookDateIssued;
	}

	public void setIdBookDateIssued(String idBookDateIssued) {
		this.idBookDateIssued = idBookDateIssued;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

}
