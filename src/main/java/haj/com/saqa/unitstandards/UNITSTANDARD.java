package haj.com.saqa.unitstandards;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class UNITSTANDARD.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "downloaddate",
    "lastupdatedate",
    "unitstandardid",
    "unitstdtitle",
    "sgbname",
    "field",
    "providercode",
    "providername",
    "provideretqaid",
    "etqaacronym",
    "etqaname",
    "fielddescription",
    "subfielddescription",
    "abetbanddescription",
    "unitstdtypedesc",
    "nqfleveldescription",
    "nqflevelg2DESCRIPTION",
    "unitstdnumberofcredits",
    "registrationstatusdesc",
    "usregistrationstartdate",
    "usregistrationenddate",
    "transitionperiod",
    "lastdateforenrolment",
    "trainoutperiod",
    "lastdateforachievement",
    "saqadecisionnumber",
    "unitstdpurpose",
    "unitstdlearningassumptions",
    "unitstdrange",
    "unitstdoutcomeheader",
    "unitstdaccreditationoptions",
    "unitstdembeddedknowledge",
    "unitstddevelopmentaloutcome",
    "unitstdlinkages",
    "unitstdccfoidentifying",
    "unitstdccfoworking",
    "unitstdccfoorganizing",
    "unitstdccfocollecting",
    "unitstdccfocommunicating",
    "unitstdccfoscience",
    "unitstdccfodemonstrating",
    "unitstdccfocontributing",
    "unitstdassessorcriteria",
    "unitstdnotes",
    "specificoutcome",
    "assessmentcriteria"
})
@Entity
@Table(name = "saqa_unitstandard")
public class UNITSTANDARD implements IDataEntity{

	/** The id. */
	@XmlTransient
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    /** The downloaddate. */
    @XmlElement(name = "DOWNLOAD_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar downloaddate;
    
    /** The lastupdatedate. */
    @XmlElement(name = "LAST_UPDATE_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastupdatedate;
    
    /** The unitstandardid. */
    @XmlElement(name = "UNIT_STANDARD_ID")
    protected Integer unitstandardid;
    
    /** The unitstdtitle. */
    @XmlElement(name = "UNIT_STD_TITLE")
    protected String unitstdtitle;
    
    /** The sgbname. */
    @XmlElement(name = "SGB_NAME")
    protected String sgbname;
    
    /** The field. */
    @XmlElement(name = "FIELD")
    protected String field;
    
    /** The providercode. */
    @XmlElement(name = "PROVIDER_CODE")
    protected String providercode;
    
    /** The providername. */
    @XmlElement(name = "PROVIDER_NAME")
    protected String providername;
    
    /** The provideretqaid. */
    @XmlElement(name = "PROVIDER_ETQA_ID")
    protected Integer provideretqaid;
    
    /** The etqaacronym. */
    @XmlElement(name = "ETQA_ACRONYM")
    protected String etqaacronym;
    
    /** The etqaname. */
    @XmlElement(name = "ETQA_NAME")
    protected String etqaname;
    
    /** The fielddescription. */
    @XmlElement(name = "FIELD_DESCRIPTION")
	@Column(name = "fielddescription", columnDefinition = "LONGTEXT")
    protected String fielddescription;
    
    /** The subfielddescription. */
    @XmlElement(name = "SUBFIELD_DESCRIPTION")
    protected String subfielddescription;
    
    /** The abetbanddescription. */
    @XmlElement(name = "ABET_BAND_DESCRIPTION")
    protected String abetbanddescription;
    
    /** The unitstdtypedesc. */
    @XmlElement(name = "UNIT_STD_TYPE_DESC")
    protected String unitstdtypedesc;
    
    /** The nqfleveldescription. */
    @XmlElement(name = "NQF_LEVEL_DESCRIPTION")
    protected String nqfleveldescription;
    
    /** The nqflevelg 2 DESCRIPTION. */
    @XmlElement(name = "NQF_LEVEL_G2_DESCRIPTION")
    protected String nqflevelg2DESCRIPTION;
    
    /** The unitstdnumberofcredits. */
    @XmlElement(name = "UNIT_STD_NUMBER_OF_CREDITS")
    protected Integer unitstdnumberofcredits;
    
    /** The registrationstatusdesc. */
    @XmlElement(name = "REGISTRATION_STATUS_DESC")
    protected String registrationstatusdesc;
    
    /** The usregistrationstartdate. */
    @XmlElement(name = "US_REGISTRATION_START_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar usregistrationstartdate;
    
    /** The usregistrationenddate. */
    @XmlElement(name = "US_REGISTRATION_END_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar usregistrationenddate;
    
    /** The transitionperiod. */
    @XmlElement(name = "TRANSITION_PERIOD")
    protected Integer transitionperiod;
    
    /** The lastdateforenrolment. */
    @XmlElement(name = "LAST_DATE_FOR_ENROLMENT")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastdateforenrolment;
    
    /** The trainoutperiod. */
    @XmlElement(name = "TRAIN_OUT_PERIOD")
    protected Integer trainoutperiod;
    
    /** The lastdateforachievement. */
    @XmlElement(name = "LAST_DATE_FOR_ACHIEVEMENT")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastdateforachievement;
    
    /** The saqadecisionnumber. */
    @XmlElement(name = "SAQA_DECISION_NUMBER")
    protected String saqadecisionnumber;
    
    /** The unitstdpurpose. */
    @XmlElement(name = "UNIT_STD_PURPOSE")
    @Column(name = "unitstdpurpose", columnDefinition = "LONGTEXT")
    protected String unitstdpurpose;
    
    /** The unitstdlearningassumptions. */
    @XmlElement(name = "UNIT_STD_LEARNING_ASSUMPTIONS")
    @Column(name = "unitstdlearningassumptions", columnDefinition = "LONGTEXT")
    protected String unitstdlearningassumptions;
    
    /** The unitstdrange. */
    @Column(name = "unitstdrange", columnDefinition = "LONGTEXT")
    @XmlElement(name = "UNIT_STD_RANGE")
    protected String unitstdrange;
    
    /** The unitstdoutcomeheader. */
    @XmlElement(name = "UNIT_STD_OUTCOME_HEADER")
    @Column(name = "unitstdoutcomeheader", columnDefinition = "LONGTEXT")
    protected String unitstdoutcomeheader;
    
    /** The unitstdaccreditationoptions. */
    @XmlElement(name = "UNIT_STD_ACCREDITATION_OPTIONS")
    @Column(name = "unitstdaccreditationoptions", columnDefinition = "LONGTEXT")
    protected String unitstdaccreditationoptions;
    
    /** The unitstdembeddedknowledge. */
    @XmlElement(name = "UNIT_STD_EMBEDDED_KNOWLEDGE")
    @Column(name = "unitstdembeddedknowledge", columnDefinition = "LONGTEXT")
    protected String unitstdembeddedknowledge;
    
    /** The unitstddevelopmentaloutcome. */
    @XmlElement(name = "UNIT_STD_DEVELOPMENTAL_OUTCOME")
    @Column(name = "unitstddevelopmentaloutcome", columnDefinition = "LONGTEXT")
    protected String unitstddevelopmentaloutcome;
    
    /** The unitstdlinkages. */
    @XmlElement(name = "UNIT_STD_LINKAGES")
    @Column(name = "unitstdlinkages", columnDefinition = "LONGTEXT")
    protected String unitstdlinkages;
    
    /** The unitstdccfoidentifying. */
    @XmlElement(name = "UNIT_STD_CCFO_IDENTIFYING")
    @Column(name = "unitstdccfoidentifying", columnDefinition = "LONGTEXT")
    protected String unitstdccfoidentifying;
    
    /** The unitstdccfoworking. */
    @XmlElement(name = "UNIT_STD_CCFO_WORKING")
    @Column(name = "unitstdccfoworking", columnDefinition = "LONGTEXT")
    protected String unitstdccfoworking;
    
    /** The unitstdccfoorganizing. */
    @XmlElement(name = "UNIT_STD_CCFO_ORGANIZING")
    @Column(name = "unitstdccfoorganizing", columnDefinition = "LONGTEXT")
    protected String unitstdccfoorganizing;
    
    /** The unitstdccfocollecting. */
    @XmlElement(name = "UNIT_STD_CCFO_COLLECTING")
    @Column(name = "unitstdccfocollecting", columnDefinition = "LONGTEXT")
    protected String unitstdccfocollecting;
    
    /** The unitstdccfocommunicating. */
    @XmlElement(name = "UNIT_STD_CCFO_COMMUNICATING")
    @Column(name = "unitstdccfocommunicating", columnDefinition = "LONGTEXT")
    protected String unitstdccfocommunicating;
    
    /** The unitstdccfoscience. */
    @XmlElement(name = "UNIT_STD_CCFO_SCIENCE")
    @Column(name = "unitstdccfoscience", columnDefinition = "LONGTEXT")
    protected String unitstdccfoscience;
    
    /** The unitstdccfodemonstrating. */
    @XmlElement(name = "UNIT_STD_CCFO_DEMONSTRATING")
    @Column(name = "unitstdccfodemonstrating", columnDefinition = "LONGTEXT")
    protected String unitstdccfodemonstrating;
    
    /** The unitstdccfocontributing. */
    @XmlElement(name = "UNIT_STD_CCFO_CONTRIBUTING")
    @Column(name = "unitstdccfocontributing", columnDefinition = "LONGTEXT")
    protected String unitstdccfocontributing;
    
    /** The unitstdassessorcriteria. */
    @XmlElement(name = "UNIT_STD_ASSESSOR_CRITERIA")
    @Column(name = "unitstdassessorcriteria", columnDefinition = "LONGTEXT")
    protected String unitstdassessorcriteria;
    
    /** The unitstdnotes. */
    @XmlElement(name = "UNIT_STD_NOTES")
    @Column(name = "unitstdnotes", columnDefinition = "LONGTEXT")
    protected String unitstdnotes;
    
    /** The specificoutcome. */
    @Transient
    @XmlElement(name = "SPECIFICOUTCOME", required = true)
    protected List<SPECIFICOUTCOME> specificoutcome;
    
    /** The assessmentcriteria. */
    @Transient
    @XmlElement(name = "ASSESSMENTCRITERIA", required = true)
    protected List<SAQAUNITSTANDARDS.ASSESSMENTCRITERIA> assessmentcriteria;

    /**
     * Gets the value of the downloaddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDOWNLOADDATE() {
        return downloaddate;
    }

    /**
     * Sets the value of the downloaddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDOWNLOADDATE(XMLGregorianCalendar value) {
        this.downloaddate = value;
    }

    /**
     * Gets the value of the lastupdatedate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * Sets the value of the lastupdatedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
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
     * Gets the value of the sgbname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSGBNAME() {
        return sgbname;
    }

    /**
     * Sets the value of the sgbname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSGBNAME(String value) {
        this.sgbname = value;
    }

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the providercode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVIDERCODE() {
        return providercode;
    }

    /**
     * Sets the value of the providercode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVIDERCODE(String value) {
        this.providercode = value;
    }

    /**
     * Gets the value of the providername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVIDERNAME() {
        return providername;
    }

    /**
     * Sets the value of the providername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVIDERNAME(String value) {
        this.providername = value;
    }

    /**
     * Gets the value of the provideretqaid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPROVIDERETQAID() {
        return provideretqaid;
    }

    /**
     * Sets the value of the provideretqaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPROVIDERETQAID(Integer value) {
        this.provideretqaid = value;
    }

    /**
     * Gets the value of the etqaacronym property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getETQAACRONYM() {
        return etqaacronym;
    }

    /**
     * Sets the value of the etqaacronym property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setETQAACRONYM(String value) {
        this.etqaacronym = value;
    }

    /**
     * Gets the value of the etqaname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getETQANAME() {
        return etqaname;
    }

    /**
     * Sets the value of the etqaname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setETQANAME(String value) {
        this.etqaname = value;
    }

    /**
     * Gets the value of the fielddescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDDESCRIPTION() {
        return fielddescription;
    }

    /**
     * Sets the value of the fielddescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDDESCRIPTION(String value) {
        this.fielddescription = value;
    }

    /**
     * Gets the value of the subfielddescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUBFIELDDESCRIPTION() {
        return subfielddescription;
    }

    /**
     * Sets the value of the subfielddescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUBFIELDDESCRIPTION(String value) {
        this.subfielddescription = value;
    }

    /**
     * Gets the value of the abetbanddescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getABETBANDDESCRIPTION() {
        return abetbanddescription;
    }

    /**
     * Sets the value of the abetbanddescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setABETBANDDESCRIPTION(String value) {
        this.abetbanddescription = value;
    }

    /**
     * Gets the value of the unitstdtypedesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDTYPEDESC() {
        return unitstdtypedesc;
    }

    /**
     * Sets the value of the unitstdtypedesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDTYPEDESC(String value) {
        this.unitstdtypedesc = value;
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

    /**
     * Gets the value of the registrationstatusdesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGISTRATIONSTATUSDESC() {
        return registrationstatusdesc;
    }

    /**
     * Sets the value of the registrationstatusdesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGISTRATIONSTATUSDESC(String value) {
        this.registrationstatusdesc = value;
    }

    /**
     * Gets the value of the usregistrationstartdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUSREGISTRATIONSTARTDATE() {
        return usregistrationstartdate;
    }

    /**
     * Sets the value of the usregistrationstartdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUSREGISTRATIONSTARTDATE(XMLGregorianCalendar value) {
        this.usregistrationstartdate = value;
    }

    /**
     * Gets the value of the usregistrationenddate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUSREGISTRATIONENDDATE() {
        return usregistrationenddate;
    }

    /**
     * Sets the value of the usregistrationenddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUSREGISTRATIONENDDATE(XMLGregorianCalendar value) {
        this.usregistrationenddate = value;
    }

    /**
     * Gets the value of the transitionperiod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRANSITIONPERIOD() {
        return transitionperiod;
    }

    /**
     * Sets the value of the transitionperiod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRANSITIONPERIOD(Integer value) {
        this.transitionperiod = value;
    }

    /**
     * Gets the value of the lastdateforenrolment property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTDATEFORENROLMENT() {
        return lastdateforenrolment;
    }

    /**
     * Sets the value of the lastdateforenrolment property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTDATEFORENROLMENT(XMLGregorianCalendar value) {
        this.lastdateforenrolment = value;
    }

    /**
     * Gets the value of the trainoutperiod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRAINOUTPERIOD() {
        return trainoutperiod;
    }

    /**
     * Sets the value of the trainoutperiod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRAINOUTPERIOD(Integer value) {
        this.trainoutperiod = value;
    }

    /**
     * Gets the value of the lastdateforachievement property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTDATEFORACHIEVEMENT() {
        return lastdateforachievement;
    }

    /**
     * Sets the value of the lastdateforachievement property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTDATEFORACHIEVEMENT(XMLGregorianCalendar value) {
        this.lastdateforachievement = value;
    }

    /**
     * Gets the value of the saqadecisionnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSAQADECISIONNUMBER() {
        return saqadecisionnumber;
    }

    /**
     * Sets the value of the saqadecisionnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSAQADECISIONNUMBER(String value) {
        this.saqadecisionnumber = value;
    }

    /**
     * Gets the value of the unitstdpurpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDPURPOSE() {
        return unitstdpurpose;
    }

    /**
     * Sets the value of the unitstdpurpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDPURPOSE(String value) {
        this.unitstdpurpose = value;
    }

    /**
     * Gets the value of the unitstdlearningassumptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDLEARNINGASSUMPTIONS() {
        return unitstdlearningassumptions;
    }

    /**
     * Sets the value of the unitstdlearningassumptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDLEARNINGASSUMPTIONS(String value) {
        this.unitstdlearningassumptions = value;
    }

    /**
     * Gets the value of the unitstdrange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDRANGE() {
        return unitstdrange;
    }

    /**
     * Sets the value of the unitstdrange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDRANGE(String value) {
        this.unitstdrange = value;
    }

    /**
     * Gets the value of the unitstdoutcomeheader property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDOUTCOMEHEADER() {
        return unitstdoutcomeheader;
    }

    /**
     * Sets the value of the unitstdoutcomeheader property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDOUTCOMEHEADER(String value) {
        this.unitstdoutcomeheader = value;
    }

    /**
     * Gets the value of the unitstdaccreditationoptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDACCREDITATIONOPTIONS() {
        return unitstdaccreditationoptions;
    }

    /**
     * Sets the value of the unitstdaccreditationoptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDACCREDITATIONOPTIONS(String value) {
        this.unitstdaccreditationoptions = value;
    }

    /**
     * Gets the value of the unitstdembeddedknowledge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDEMBEDDEDKNOWLEDGE() {
        return unitstdembeddedknowledge;
    }

    /**
     * Sets the value of the unitstdembeddedknowledge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDEMBEDDEDKNOWLEDGE(String value) {
        this.unitstdembeddedknowledge = value;
    }

    /**
     * Gets the value of the unitstddevelopmentaloutcome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDDEVELOPMENTALOUTCOME() {
        return unitstddevelopmentaloutcome;
    }

    /**
     * Sets the value of the unitstddevelopmentaloutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDDEVELOPMENTALOUTCOME(String value) {
        this.unitstddevelopmentaloutcome = value;
    }

    /**
     * Gets the value of the unitstdlinkages property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDLINKAGES() {
        return unitstdlinkages;
    }

    /**
     * Sets the value of the unitstdlinkages property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDLINKAGES(String value) {
        this.unitstdlinkages = value;
    }

    /**
     * Gets the value of the unitstdccfoidentifying property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOIDENTIFYING() {
        return unitstdccfoidentifying;
    }

    /**
     * Sets the value of the unitstdccfoidentifying property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOIDENTIFYING(String value) {
        this.unitstdccfoidentifying = value;
    }

    /**
     * Gets the value of the unitstdccfoworking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOWORKING() {
        return unitstdccfoworking;
    }

    /**
     * Sets the value of the unitstdccfoworking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOWORKING(String value) {
        this.unitstdccfoworking = value;
    }

    /**
     * Gets the value of the unitstdccfoorganizing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOORGANIZING() {
        return unitstdccfoorganizing;
    }

    /**
     * Sets the value of the unitstdccfoorganizing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOORGANIZING(String value) {
        this.unitstdccfoorganizing = value;
    }

    /**
     * Gets the value of the unitstdccfocollecting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOCOLLECTING() {
        return unitstdccfocollecting;
    }

    /**
     * Sets the value of the unitstdccfocollecting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOCOLLECTING(String value) {
        this.unitstdccfocollecting = value;
    }

    /**
     * Gets the value of the unitstdccfocommunicating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOCOMMUNICATING() {
        return unitstdccfocommunicating;
    }

    /**
     * Sets the value of the unitstdccfocommunicating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOCOMMUNICATING(String value) {
        this.unitstdccfocommunicating = value;
    }

    /**
     * Gets the value of the unitstdccfoscience property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOSCIENCE() {
        return unitstdccfoscience;
    }

    /**
     * Sets the value of the unitstdccfoscience property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOSCIENCE(String value) {
        this.unitstdccfoscience = value;
    }

    /**
     * Gets the value of the unitstdccfodemonstrating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFODEMONSTRATING() {
        return unitstdccfodemonstrating;
    }

    /**
     * Sets the value of the unitstdccfodemonstrating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFODEMONSTRATING(String value) {
        this.unitstdccfodemonstrating = value;
    }

    /**
     * Gets the value of the unitstdccfocontributing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDCCFOCONTRIBUTING() {
        return unitstdccfocontributing;
    }

    /**
     * Sets the value of the unitstdccfocontributing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDCCFOCONTRIBUTING(String value) {
        this.unitstdccfocontributing = value;
    }

    /**
     * Gets the value of the unitstdassessorcriteria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDASSESSORCRITERIA() {
        return unitstdassessorcriteria;
    }

    /**
     * Sets the value of the unitstdassessorcriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDASSESSORCRITERIA(String value) {
        this.unitstdassessorcriteria = value;
    }

    /**
     * Gets the value of the unitstdnotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNITSTDNOTES() {
        return unitstdnotes;
    }

    /**
     * Sets the value of the unitstdnotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNITSTDNOTES(String value) {
        this.unitstdnotes = value;
    }
    
    /**
     * Gets the specificoutcome.
     *
     * @return the specificoutcome
     */
    public List<SPECIFICOUTCOME> getSPECIFICOUTCOME() {
        if (specificoutcome == null) {
            specificoutcome = new ArrayList<SPECIFICOUTCOME>();
        }
        return this.specificoutcome;
    }

    /**
     * Gets the value of the assessmentcriteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assessmentcriteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getASSESSMENTCRITERIA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SAQAUNITSTANDARDS.ASSESSMENTCRITERIA }
     *
     * @return the assessmentcriteria
     */
    public List<SAQAUNITSTANDARDS.ASSESSMENTCRITERIA> getASSESSMENTCRITERIA() {
        if (assessmentcriteria == null) {
            assessmentcriteria = new ArrayList<SAQAUNITSTANDARDS.ASSESSMENTCRITERIA>();
        }
        return this.assessmentcriteria;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UNITSTANDARD [downloaddate=" + downloaddate + ", lastupdatedate=" + lastupdatedate + ", unitstandardid="
				+ unitstandardid + ", unitstdtitle=" + unitstdtitle + ", sgbname=" + sgbname + ", field=" + field
				+ ", providercode=" + providercode + ", providername=" + providername + ", provideretqaid="
				+ provideretqaid + ", etqaacronym=" + etqaacronym + ", etqaname=" + etqaname + ", fielddescription="
				+ fielddescription + ", subfielddescription=" + subfielddescription + ", abetbanddescription="
				+ abetbanddescription + ", unitstdtypedesc=" + unitstdtypedesc + ", nqfleveldescription="
				+ nqfleveldescription + ", nqflevelg2DESCRIPTION=" + nqflevelg2DESCRIPTION + ", unitstdnumberofcredits="
				+ unitstdnumberofcredits + ", registrationstatusdesc=" + registrationstatusdesc
				+ ", usregistrationstartdate=" + usregistrationstartdate + ", usregistrationenddate="
				+ usregistrationenddate + ", transitionperiod=" + transitionperiod + ", lastdateforenrolment="
				+ lastdateforenrolment + ", trainoutperiod=" + trainoutperiod + ", lastdateforachievement="
				+ lastdateforachievement + ", saqadecisionnumber=" + saqadecisionnumber + ", unitstdpurpose="
				+ unitstdpurpose + ", unitstdlearningassumptions=" + unitstdlearningassumptions + ", unitstdrange="
				+ unitstdrange + ", unitstdoutcomeheader=" + unitstdoutcomeheader + ", unitstdaccreditationoptions="
				+ unitstdaccreditationoptions + ", unitstdembeddedknowledge=" + unitstdembeddedknowledge
				+ ", unitstddevelopmentaloutcome=" + unitstddevelopmentaloutcome + ", unitstdlinkages="
				+ unitstdlinkages + ", unitstdccfoidentifying=" + unitstdccfoidentifying + ", unitstdccfoworking="
				+ unitstdccfoworking + ", unitstdccfoorganizing=" + unitstdccfoorganizing + ", unitstdccfocollecting="
				+ unitstdccfocollecting + ", unitstdccfocommunicating=" + unitstdccfocommunicating
				+ ", unitstdccfoscience=" + unitstdccfoscience + ", unitstdccfodemonstrating="
				+ unitstdccfodemonstrating + ", unitstdccfocontributing=" + unitstdccfocontributing
				+ ", unitstdassessorcriteria=" + unitstdassessorcriteria + ", unitstdnotes=" + unitstdnotes
				+ ", specificoutcome=" + specificoutcome + ", assessmentcriteria=" + assessmentcriteria + "]";
	}
    
    


}