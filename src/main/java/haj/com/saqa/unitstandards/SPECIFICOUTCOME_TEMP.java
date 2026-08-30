package haj.com.saqa.unitstandards;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class SPECIFICOUTCOME.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "unitstandardid",
    "outcomeid",
    "outcometitle",
    "outcomenotes",
    "outcomerange"
})
@Entity
@Table(name = "saqa_specific_outcome_temp")
public  class SPECIFICOUTCOME_TEMP implements IDataEntity {
	
	/** The id. */
	@XmlTransient
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    /** The unitstandardid. */
    @XmlElement(name = "UNIT_STANDARD_ID")
    protected Integer unitstandardid;
    
    /** The outcomeid. */
    @XmlElement(name = "OUTCOME_ID")
    protected Integer outcomeid;
	
	/** The outcometitle. */
	@Column(name = "outcome_title", columnDefinition = "LONGTEXT")
    @XmlElement(name = "OUTCOME_TITLE")
    protected String outcometitle;
	
    /** The outcomenotes. */
    @XmlElement(name = "OUTCOME_NOTES")
	@Column(name = "outcome_notes", columnDefinition = "LONGTEXT")
    protected String outcomenotes;
    
    /** The outcomerange. */
    @XmlElement(name = "OUTCOME_RANGE")
    @Column(name = "outcomerange", columnDefinition = "LONGTEXT")
    protected String outcomerange;

    /** The unitstandard. */
    @XmlTransient
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unitstandard_id", nullable=true)
    private UNITSTANDARD unitstandard;
    /**
     * Gets the value of the unitstandardid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUNITSTANDARDID() {
        return unitstandardid;
    }

    /**
     * Sets the value of the unitstandardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUNITSTANDARDID(Integer value) {
        this.unitstandardid = value;
    }

    /**
     * Gets the value of the outcomeid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOUTCOMEID() {
        return outcomeid;
    }

    /**
     * Sets the value of the outcomeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOUTCOMEID(Integer value) {
        this.outcomeid = value;
    }

    /**
     * Gets the value of the outcometitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTCOMETITLE() {
        return outcometitle;
    }

    /**
     * Sets the value of the outcometitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTCOMETITLE(String value) {
        this.outcometitle = value;
    }

    /**
     * Gets the value of the outcomenotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTCOMENOTES() {
        return outcomenotes;
    }

    /**
     * Sets the value of the outcomenotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTCOMENOTES(String value) {
        this.outcomenotes = value;
    }

    /**
     * Gets the value of the outcomerange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTCOMERANGE() {
        return outcomerange;
    }

    /**
     * Sets the value of the outcomerange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTCOMERANGE(String value) {
        this.outcomerange = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SPECIFICOUTCOME_TEMP [unitstandardid=" + unitstandardid + ", outcomeid=" + outcomeid + ", outcometitle="
				+ outcometitle + ", outcomenotes=" + outcomenotes + ", outcomerange=" + outcomerange + "]";
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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the unitstandard.
	 *
	 * @return the unitstandard
	 */
	public UNITSTANDARD getUnitstandard() {
		return unitstandard;
	}

	/**
	 * Sets the unitstandard.
	 *
	 * @param unitstandard the new unitstandard
	 */
	public void setUnitstandard(UNITSTANDARD unitstandard) {
		this.unitstandard = unitstandard;
	}
   
}