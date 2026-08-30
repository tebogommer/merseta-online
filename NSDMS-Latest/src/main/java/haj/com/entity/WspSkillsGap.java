package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp Skills Gap.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "wsp_skills_gap")
public class WspSkillsGap implements IDataEntity, Cloneable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;
	
	/** row description of Wsp Skills Gap. */
	@Column(name = "row_description", length = 500)
	private String rowDescription;
	
	/** The manager selection */
	@Column(name = "manager_selection")
	private Boolean managerSelection;
	
	/** The professionals selection */
	@Column(name = "professionals_selection")
	private Boolean professionalsSelection;
	
	/** The technicians and associate professionals selection */
	@Column(name = "technicians_associate_professionals_selection")
	private Boolean techniciansAssociateProfessionalsSelection;
	
	/** The clerical support workers selection */
	@Column(name = "clerical_support_workers_selection")
	private Boolean clericalSupportWorkersSelection;
	
	/** The service and sales workers selection */
	@Column(name = "service_sales_workers_selection")
	private Boolean serviceSalesWorkersSelection;
	
	/** The skilled trades workers selection */
	@Column(name = "skilled_trades_workers_selection")
	private Boolean skilledTradesWorkersSelection;
	
	/** The plant and machine operators and assemblers selection */
	@Column(name = "plant_machine_operators_assemblers_selection")
	private Boolean plantMachineOperatorsAssemblersSelection;
	
	/** The elementary workers selection */
	@Column(name = "elementary_workers_selection")
	private Boolean elementaryWorkersSelection;
	
	/** The skills gap section */
	@Column(name = "skills_gap_section")
	private Integer skillsGapSection;
	
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
		WspSkillsGap other = (WspSkillsGap) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			// should never happen
		}
		return clone;

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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public String getRowDescription() {
		return rowDescription;
	}

	public void setRowDescription(String rowDescription) {
		this.rowDescription = rowDescription;
	}

	public Boolean getManagerSelection() {
		return managerSelection;
	}

	public void setManagerSelection(Boolean managerSelection) {
		this.managerSelection = managerSelection;
	}

	public Boolean getProfessionalsSelection() {
		return professionalsSelection;
	}

	public void setProfessionalsSelection(Boolean professionalsSelection) {
		this.professionalsSelection = professionalsSelection;
	}

	public Boolean getTechniciansAssociateProfessionalsSelection() {
		return techniciansAssociateProfessionalsSelection;
	}

	public void setTechniciansAssociateProfessionalsSelection(Boolean techniciansAssociateProfessionalsSelection) {
		this.techniciansAssociateProfessionalsSelection = techniciansAssociateProfessionalsSelection;
	}

	public Boolean getClericalSupportWorkersSelection() {
		return clericalSupportWorkersSelection;
	}

	public void setClericalSupportWorkersSelection(Boolean clericalSupportWorkersSelection) {
		this.clericalSupportWorkersSelection = clericalSupportWorkersSelection;
	}

	public Boolean getServiceSalesWorkersSelection() {
		return serviceSalesWorkersSelection;
	}

	public void setServiceSalesWorkersSelection(Boolean serviceSalesWorkersSelection) {
		this.serviceSalesWorkersSelection = serviceSalesWorkersSelection;
	}

	public Boolean getSkilledTradesWorkersSelection() {
		return skilledTradesWorkersSelection;
	}

	public void setSkilledTradesWorkersSelection(Boolean skilledTradesWorkersSelection) {
		this.skilledTradesWorkersSelection = skilledTradesWorkersSelection;
	}

	public Boolean getPlantMachineOperatorsAssemblersSelection() {
		return plantMachineOperatorsAssemblersSelection;
	}

	public void setPlantMachineOperatorsAssemblersSelection(Boolean plantMachineOperatorsAssemblersSelection) {
		this.plantMachineOperatorsAssemblersSelection = plantMachineOperatorsAssemblersSelection;
	}

	public Boolean getElementaryWorkersSelection() {
		return elementaryWorkersSelection;
	}

	public void setElementaryWorkersSelection(Boolean elementaryWorkersSelection) {
		this.elementaryWorkersSelection = elementaryWorkersSelection;
	}

	public Integer getSkillsGapSection() {
		return skillsGapSection;
	}

	public void setSkillsGapSection(Integer skillsGapSection) {
		this.skillsGapSection = skillsGapSection;
	}

}
