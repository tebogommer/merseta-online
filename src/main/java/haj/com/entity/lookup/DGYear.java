package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.Doc;
import haj.com.entity.enums.DGWindowTypeEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * DGYear.
 * @author Techfinium
 */
@Entity
@Table(name = "dg_year")
public class DGYear extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of AbetBand. */
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "allocation_amount")
	private BigDecimal allocationAmount;

	@Column(name = "fin_year")
	private Integer finYear;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", length = 19)
	private Date endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "doc_id", nullable = true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Doc doc;

	@Enumerated
	@Column(name = "dg_window_type")
	private DGWindowTypeEnum dgWindowType;

	@Transient
	private List<Doc> docs;

	/**
	 * Instantiates a new abet band.
	 */
	public DGYear() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
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
		DGYear other = (DGYear) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the id.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * @param id
	 * the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * @param description
	 * the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the allocationAmount
	 */
	public BigDecimal getAllocationAmount() {
		return allocationAmount;
	}

	/**
	 * @param allocationAmount
	 * the allocationAmount to set
	 */
	public void setAllocationAmount(BigDecimal allocationAmount) {
		this.allocationAmount = allocationAmount;
	}

	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public DGWindowTypeEnum getDgWindowType() {
		return dgWindowType;
	}

	public void setDgWindowType(DGWindowTypeEnum dgWindowType) {
		this.dgWindowType = dgWindowType;
	}

}
