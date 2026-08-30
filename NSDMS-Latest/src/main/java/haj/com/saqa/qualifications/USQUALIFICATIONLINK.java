package haj.com.saqa.qualifications;
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

import haj.com.entity.lookup.SaqaQualification;
import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * The Class USQUALIFICATIONLINK.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "qualificationid",
    "usqualtypedescription",
    "unitstdtitle",
    "unitstandardid",
    "nqfleveldescription",
    "nqflevelg2DESCRIPTION",
    "unitstdnumberofcredits"
})
@Entity
@Table(name = "saqa_us_qualification")
public  class USQUALIFICATIONLINK implements IDataEntity {

	/** The id. */
	@XmlTransient
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

    /** The qualificationid. */
    @XmlElement(name = "QUALIFICATION_ID")
    protected Integer qualificationid;

    /** The usqualtypedescription. */
    @XmlElement(name = "US_QUAL_TYPE_DESCRIPTION")
    protected String usqualtypedescription;

    /** The unitstdtitle. */
    @XmlElement(name = "UNIT_STD_TITLE")
    protected String unitstdtitle;

    /** The unitstandardid. */
    @XmlElement(name = "UNIT_STANDARD_ID")
    protected Integer unitstandardid;

    /** The nqfleveldescription. */
    @XmlElement(name = "NQF_LEVEL_DESCRIPTION")
    protected String nqfleveldescription;

    /** The nqflevelg 2 DESCRIPTION. */
    @XmlElement(name = "NQF_LEVEL_G2_DESCRIPTION")
    protected String nqflevelg2DESCRIPTION;

    /** The unitstdnumberofcredits. */
    @XmlElement(name = "UNIT_STD_NUMBER_OF_CREDITS")
    protected Integer unitstdnumberofcredits;

    /** The qualification. */
    @XmlTransient
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="qualification_id", nullable=true)
    private SaqaQualification qualification;

    /**
     * Gets the value of the qualificationid property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getQUALIFICATIONID() {
        return qualificationid;
    }

    /**
     * Sets the value of the qualificationid property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setQUALIFICATIONID(Integer value) {
        this.qualificationid = value;
    }

    /**
     * Gets the value of the usqualtypedescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUSQUALTYPEDESCRIPTION() {
        return usqualtypedescription;
    }

    /**
     * Sets the value of the usqualtypedescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUSQUALTYPEDESCRIPTION(String value) {
        this.usqualtypedescription = value;
    }

    /**
     * Gets the value of the unitstdtitle property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUNITSTDTITLE() {
        return unitstdtitle;
    }

    /**
     * Sets the value of the unitstdtitle property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUNITSTDTITLE(String value) {
        this.unitstdtitle = value;
    }

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
     * Gets the value of the nqfleveldescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNQFLEVELDESCRIPTION() {
        return nqfleveldescription;
    }

    /**
     * Sets the value of the nqfleveldescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNQFLEVELDESCRIPTION(String value) {
        this.nqfleveldescription = value;
    }

    /**
     * Gets the value of the nqflevelg2DESCRIPTION property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNQFLEVELG2DESCRIPTION() {
        return nqflevelg2DESCRIPTION;
    }

    /**
     * Sets the value of the nqflevelg2DESCRIPTION property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNQFLEVELG2DESCRIPTION(String value) {
        this.nqflevelg2DESCRIPTION = value;
    }

    /**
     * Gets the value of the unitstdnumberofcredits property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getUNITSTDNUMBEROFCREDITS() {
        return unitstdnumberofcredits;
    }

    /**
     * Sets the value of the unitstdnumberofcredits property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setUNITSTDNUMBEROFCREDITS(Integer value) {
        this.unitstdnumberofcredits = value;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "USQUALIFICATIONLINK [qualificationid=" + qualificationid + ", usqualtypedescription="
				+ usqualtypedescription + ", unitstdtitle=" + unitstdtitle + ", unitstandardid=" + unitstandardid
				+ ", nqfleveldescription=" + nqfleveldescription + ", nqflevelg2DESCRIPTION="
				+ nqflevelg2DESCRIPTION + ", unitstdnumberofcredits=" + unitstdnumberofcredits + "]";
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public SaqaQualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(SaqaQualification qualification) {
		this.qualification = qualification;
	}

}
