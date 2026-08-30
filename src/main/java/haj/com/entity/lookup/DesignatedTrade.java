package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.DesignatedTradeType;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * bank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "designated_trade")
@AuditTable(value = "designated_trade_hist")
@Audited
public class DesignatedTrade extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;
	
	@Enumerated
	@Column(name = "designated_trade_type")
	private DesignatedTradeType designatedTradeType;
	
	@Column(name = "duration", nullable = false)
	private Long duration;
	
	@Column(name = "extention", nullable = false)
	private Long extention;
	
	@Column(name = "auto_extend", nullable = true)
	private boolean autoExtend;
	
	@Column(name = "qualification_id", length = 50)
	private String qualificationId;

	/**
	 * Instantiates a new bank.
	 */
	public DesignatedTrade() {
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
		DesignatedTrade other = (DesignatedTrade) obj;
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
	 * @param id            the id to set
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
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public DesignatedTradeType getDesignatedTradeType() {
		return designatedTradeType;
	}

	public void setDesignatedTradeType(DesignatedTradeType designatedTradeType) {
		this.designatedTradeType = designatedTradeType;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getExtention() {
		return extention;
	}

	public void setExtention(Long extention) {
		this.extention = extention;
	}

	public boolean isAutoExtend() {
		return autoExtend;
	}

	public void setAutoExtend(boolean autoExtend) {
		this.autoExtend = autoExtend;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

}
