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

import haj.com.entity.lookup.temp.QualificationTemp;
import haj.com.entity.lookup.temp.SaqaQualificationTemp;
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
@Table(name = "saqa_us_qualification_temp")
public  class USQUALIFICATIONLINK_TEMP implements IDataEntity {

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
    private QualificationTemp qualificationTemp;

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
		return "USQUALIFICATIONLINK_TEMP [qualificationid=" + qualificationid + ", usqualtypedescription="
				+ usqualtypedescription + ", unitstdtitle=" + unitstdtitle + ", unitstandardid=" + unitstandardid
				+ ", nqfleveldescription=" + nqfleveldescription + ", nqflevelg2DESCRIPTION="
				+ nqflevelg2DESCRIPTION + ", unitstdnumberofcredits=" + unitstdnumberofcredits + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQualificationid() {
		return qualificationid;
	}

	public void setQualificationid(Integer qualificationid) {
		this.qualificationid = qualificationid;
	}

	public String getUsqualtypedescription() {
		return usqualtypedescription;
	}

	public void setUsqualtypedescription(String usqualtypedescription) {
		this.usqualtypedescription = usqualtypedescription;
	}

	public String getUnitstdtitle() {
		return unitstdtitle;
	}

	public void setUnitstdtitle(String unitstdtitle) {
		this.unitstdtitle = unitstdtitle;
	}

	public Integer getUnitstandardid() {
		return unitstandardid;
	}

	public void setUnitstandardid(Integer unitstandardid) {
		this.unitstandardid = unitstandardid;
	}

	public String getNqfleveldescription() {
		return nqfleveldescription;
	}

	public void setNqfleveldescription(String nqfleveldescription) {
		this.nqfleveldescription = nqfleveldescription;
	}

	public String getNqflevelg2DESCRIPTION() {
		return nqflevelg2DESCRIPTION;
	}

	public void setNqflevelg2DESCRIPTION(String nqflevelg2description) {
		nqflevelg2DESCRIPTION = nqflevelg2description;
	}

	public Integer getUnitstdnumberofcredits() {
		return unitstdnumberofcredits;
	}

	public void setUnitstdnumberofcredits(Integer unitstdnumberofcredits) {
		this.unitstdnumberofcredits = unitstdnumberofcredits;
	}

	public QualificationTemp getQualificationTemp() {
		return qualificationTemp;
	}

	public void setQualificationTemp(QualificationTemp qualificationTemp) {
		this.qualificationTemp = qualificationTemp;
	}

	

}
