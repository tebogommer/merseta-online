package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.framework.AbstractLookup;

/**
 * FinYearQuartersLookUp.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "fin_year_quarters_look_up")
@AuditTable(value = "fin_year_quarters_look_up_hist")
@Audited
public class FinYearQuartersLookUp extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of FinYearQuartersLookUp. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of AbetBand. */
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "order_number", length = 10)
	private Integer orderNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date", length = 19)
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date", length = 19)
	private Date toDate;

	@Enumerated
	@Column(name = "fin_year_quarters", length = 19)
	private FinYearQuartersEnum finYearQuarters;
	
	@Column(name = "use_next_year", columnDefinition = "BIT default false")
	private Boolean useNextYear;

	/**
	 * Instantiates a new FinYear Quarters Look Up.
	 */
	public FinYearQuartersLookUp() {
		super();
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinYearQuartersLookUp other = (FinYearQuartersLookUp) obj;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public FinYearQuartersEnum getFinYearQuarters() {
		return finYearQuarters;
	}

	public void setFinYearQuarters(FinYearQuartersEnum finYearQuarters) {
		this.finYearQuarters = finYearQuarters;
	}

	public Boolean getUseNextYear() {
		return useNextYear;
	}

	public void setUseNextYear(Boolean useNextYear) {
		this.useNextYear = useNextYear;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
