package haj.com.entity;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * View on DB.
 *
 * @author Techfinium
 */
@Entity
@Immutable
@Table(name = "wsp_approved_check")
public class WspApprovalView implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "xkey", unique = true, nullable = false)
	private Long id;

	@Column(name = "levy_number")
	private String levyNumber;
	
	@Column(name = "fin_year")
	private Integer finYear;
	
	@Column(name = "dhet_fin_year")
	private Integer dhetFinYear;
	
	@Enumerated
	@Column(name = "wsp_status_enum")
	private WspStatusEnum StatusEnum;

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
		WspApprovalView other = (WspApprovalView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/*
	 * View SQL:

	 	create view wsp_approved_check (xkey,levy_number,fin_year, dhet_fin_year, wsp_status_enum) as (
			select * from (
				select distinct uuid() as xkey, c.levy_number, w.fin_year, w.fin_year - 1 as dhet_fin_year,  w.wsp_status_enum 
				from  wsp w
				left join company c on c.id = w.company_id
	
				union	
		
				select distinct uuid() as xkey, w.reference_no as levy_number, w.levy_year, w.levy_year - 1 as dhet_fin_year, w.wsp_status_enum
				from wsp_historic_data w	
			)	as yyy
		)	
	 */

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

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public WspStatusEnum getStatusEnum() {
		return StatusEnum;
	}

	public void setStatusEnum(WspStatusEnum statusEnum) {
		StatusEnum = statusEnum;
	}

	public Integer getDhetFinYear() {
		return dhetFinYear;
	}

	public void setDhetFinYear(Integer dhetFinYear) {
		this.dhetFinYear = dhetFinYear;
	}

}
