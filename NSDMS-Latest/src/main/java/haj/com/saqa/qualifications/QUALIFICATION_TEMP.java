package haj.com.saqa.qualifications;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * The Class QUALIFICATION.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "downloaddate",
    "lastupdatedate",
    "qualificationid",
    "qualificationtitle",
    "sgbname",
    "field",
    "providercode",
    "providername",
    "provideretqaid",
    "etqaid",
    "etqaacronym",
    "etqaname",
    "qualificationtypedesc",
    "qualificationtypeid",
    "fielddescription",
    "subfielddescription",
    "abetbanddescription",
    "qualificationminimumcredits",
    "nqflevelg2Id",
    "nqfleveldescription",
    "nqflevelg2DESCRIPTION",
    "qualificationclassdesc",
    "qualificationpurpose",
    "quallearningassumedinplace",
    "recognizeprevlearningflag",
    "qualrulesofcombination",
    "eloacqualificationoutcome",
    "eloacqualassessmentcriteria",
    "qualintlbenchmarkingmemo",
    "qualarticulationoptions",
    "qualmoderationoptions",
    "qualassessorcriteria",
    "qualificationnotes",
    "registrationstatusdesc",
    "registrationstatuscode",
    "saqadecisionnumber",
    "qualregistrationstartdate",
    "qualregistrationenddate",
    "transitionperiod",
    "lastdateforenrolment",
    "trainoutperiod",
    "lastdateforachievement",
    "islearningprogramme",
    "learningprogrammequal",
    "usqualificationlink",
    "lpqualificationlink"
})
@Entity
@Table(name = "saqa_qualification_temp")
public class QUALIFICATION_TEMP implements IDataEntity{

	/** The id. */
	@XmlTransient
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	// ****************************************************************** //
	
    /** The downloaddate. */
    @Transient
    @XmlElement(name = "DOWNLOAD_DATE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar downloaddate;

    /** The lastupdatedate. */
    @XmlElement(name = "LAST_UPDATE_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastupdatedate;

	// ****************************************************************** //
    
    /** The qualificationid. */
    @XmlElement(name = "QUALIFICATION_ID")
    protected Integer qualificationid;

    /** The qualificationtitle. */
    @XmlElement(name = "QUALIFICATION_TITLE")
    protected String qualificationtitle;

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

    /** The etqaid. */
    @XmlElement(name = "ETQA_ID")
    protected String etqaid;

    /** The etqaacronym. */
    @XmlElement(name = "ETQA_ACRONYM")
    protected String etqaacronym;

    /** The etqaname. */
    @XmlElement(name = "ETQA_NAME")
    protected String etqaname;

    /** The qualificationtypedesc. */
    @XmlElement(name = "QUALIFICATION_TYPE_DESC")
    protected String qualificationtypedesc;
    
    /** The qualificationtypedesc. */
    @XmlElement(name = "QUALIFICATION_TYPE_ID")
    protected String qualificationtypeid;

    /** The fielddescription. */
    @XmlElement(name = "FIELD_DESCRIPTION")
    protected String fielddescription;

    /** The subfielddescription. */
    @XmlElement(name = "SUBFIELD_DESCRIPTION")
    protected String subfielddescription;

    /** The abetbanddescription. */
    @XmlElement(name = "ABET_BAND_DESCRIPTION")
    protected String abetbanddescription;

    /** The qualificationminimumcredits. */
    @XmlElement(name = "QUALIFICATION_MINIMUM_CREDITS")
    protected Integer qualificationminimumcredits;

    /** The nqflevelg 2 id. */
    @XmlElement(name = "NQF_LEVEL_G2_ID")
    protected String nqflevelg2Id;

    /** The nqfleveldescription. */
    @XmlElement(name = "NQF_LEVEL_DESCRIPTION")
    protected String nqfleveldescription;

    /** The nqflevelg 2 DESCRIPTION. */
    @XmlElement(name = "NQF_LEVEL_G2_DESCRIPTION")
    protected String nqflevelg2DESCRIPTION;

    /** The qualificationclassdesc. */
    @XmlElement(name = "QUALIFICATION_CLASS_DESC")
    protected String qualificationclassdesc;

    /** The qualificationpurpose. */
    @XmlElement(name = "QUALIFICATION_PURPOSE")
    protected String qualificationpurpose;

    /** The quallearningassumedinplace. */
    @XmlElement(name = "QUAL_LEARNING_ASSUMED_IN_PLACE")
    protected String quallearningassumedinplace;

    /** The recognizeprevlearningflag. */
    @XmlElement(name = "RECOGNIZE_PREV_LEARNING_FLAG")
    protected String recognizeprevlearningflag;

    /** The qualrulesofcombination. */
    @XmlElement(name = "QUAL_RULES_OF_COMBINATION")
    protected String qualrulesofcombination;

    /** The eloacqualificationoutcome. */
    @XmlElement(name = "ELOAC_QUALIFICATION_OUTCOME")
    protected String eloacqualificationoutcome;

    /** The eloacqualassessmentcriteria. */
    @XmlElement(name = "ELOAC_QUAL_ASSESSMENT_CRITERIA")
    protected String eloacqualassessmentcriteria;

    /** The qualintlbenchmarkingmemo. */
    @XmlElement(name = "QUAL_INTL_BENCHMARKING_MEMO")
    protected String qualintlbenchmarkingmemo;

    /** The qualarticulationoptions. */
    @XmlElement(name = "QUAL_ARTICULATION_OPTIONS")
    protected String qualarticulationoptions;

    /** The qualmoderationoptions. */
    @XmlElement(name = "QUAL_MODERATION_OPTIONS")
    protected String qualmoderationoptions;

    /** The qualassessorcriteria. */
    @XmlElement(name = "QUAL_ASSESSOR_CRITERIA")
    protected String qualassessorcriteria;

    /** The qualificationnotes. */
    @XmlElement(name = "QUALIFICATION_NOTES")
    protected String qualificationnotes;

    /** The registrationstatusdesc. */
    @XmlElement(name = "REGISTRATION_STATUS_DESC")
    protected String registrationstatusdesc;
    
    /** The registrationstatuscode. */
    @XmlElement(name = "REGISTRATION_STATUS_CODE")
    protected String registrationstatuscode;

    /** The saqadecisionnumber. */
    @XmlElement(name = "SAQA_DECISION_NUMBER")
    protected String saqadecisionnumber;

    // **************************************************************** //
    
    /** The qualregistrationstartdate. */
    @XmlElement(name = "QUAL_REGISTRATION_START_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar qualregistrationstartdate;
    
    @XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "qualregistrationstartdate")
    protected Date qualregistrationstartDate;

    /** The qualregistrationenddate. */
    @XmlElement(name = "QUAL_REGISTRATION_END_DATE")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar qualregistrationenddate;

    @XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "qualregistrationenddate")
    protected Date qualregistrationendDate;
    
    // **************************************************************** //
    
    /** The transitionperiod. */
    @XmlElement(name = "TRANSITION_PERIOD")
    protected Integer transitionperiod;

    /** The trainoutperiod. */
    @XmlElement(name = "TRAIN_OUT_PERIOD")
    protected Integer trainoutperiod;

    /** The lastdateforachievement. */
    @XmlElement(name = "LAST_DATE_FOR_ACHIEVEMENT")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastdateforachievement;
    
    @XmlTransient
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastdateforachievement")
    protected Date lastdateforachievement2;

    /** The islearningprogramme. */
    @XmlElement(name = "IS_LEARNING_PROGRAMME")
    protected String islearningprogramme;

    /** The learningprogrammequal. */
    @XmlElement(name = "LEARNING_PROGRAMME_QUAL")
    protected Integer learningprogrammequal;

    /** The usqualificationlink. */
    @Transient
    @XmlElement(name = "US_QUALIFICATION_LINK")
    protected List<USQUALIFICATIONLINK_TEMP> usqualificationlink;

    /** The lpqualificationlink. */
    @Transient
    @XmlElement(name = "LP_QUALIFICATION_LINK")
    protected List<SAQAQUALIFICATIONS_TEMP.LPQUALIFICATIONLINK_TEMP> lpqualificationlink;
    
    // **************************************************************** //
    
    /** The lastdateforenrolment. */
    @XmlElement(name = "LAST_DATE_FOR_ENROLMENT")
    @XmlSchemaType(name = "dateTime")
    @Transient
    protected XMLGregorianCalendar lastdateforenrolment;
    
    @Column(name="lastdateforenrolment")
    @XmlTransient
    protected Date lastdateforenrolment2;
    
    // **************************************************************** //
    
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
	 * Gets the downloaddate.
	 *
	 * @return the downloaddate
	 */
	public XMLGregorianCalendar getDownloaddate() {
		return downloaddate;
	}

	/**
	 * Sets the downloaddate.
	 *
	 * @param downloaddate the new downloaddate
	 */
	public void setDownloaddate(XMLGregorianCalendar downloaddate) {
		this.downloaddate = downloaddate;
	}

	/**
	 * Gets the lastupdatedate.
	 *
	 * @return the lastupdatedate
	 */
	public XMLGregorianCalendar getLastupdatedate() {
		return lastupdatedate;
	}

	/**
	 * Sets the lastupdatedate.
	 *
	 * @param lastupdatedate the new lastupdatedate
	 */
	public void setLastupdatedate(XMLGregorianCalendar lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}

	/**
	 * Gets the qualificationid.
	 *
	 * @return the qualificationid
	 */
	public Integer getQualificationid() {
		return qualificationid;
	}

	/**
	 * Sets the qualificationid.
	 *
	 * @param qualificationid the new qualificationid
	 */
	public void setQualificationid(Integer qualificationid) {
		this.qualificationid = qualificationid;
	}

	/**
	 * Gets the qualificationtitle.
	 *
	 * @return the qualificationtitle
	 */
	public String getQualificationtitle() {
		return qualificationtitle;
	}

	/**
	 * Sets the qualificationtitle.
	 *
	 * @param qualificationtitle the new qualificationtitle
	 */
	public void setQualificationtitle(String qualificationtitle) {
		this.qualificationtitle = qualificationtitle;
	}

	/**
	 * Gets the sgbname.
	 *
	 * @return the sgbname
	 */
	public String getSgbname() {
		return sgbname;
	}

	/**
	 * Sets the sgbname.
	 *
	 * @param sgbname the new sgbname
	 */
	public void setSgbname(String sgbname) {
		this.sgbname = sgbname;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 *
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Gets the providercode.
	 *
	 * @return the providercode
	 */
	public String getProvidercode() {
		return providercode;
	}

	/**
	 * Sets the providercode.
	 *
	 * @param providercode the new providercode
	 */
	public void setProvidercode(String providercode) {
		this.providercode = providercode;
	}

	/**
	 * Gets the providername.
	 *
	 * @return the providername
	 */
	public String getProvidername() {
		return providername;
	}

	/**
	 * Sets the providername.
	 *
	 * @param providername the new providername
	 */
	public void setProvidername(String providername) {
		this.providername = providername;
	}

	/**
	 * Gets the provideretqaid.
	 *
	 * @return the provideretqaid
	 */
	public Integer getProvideretqaid() {
		return provideretqaid;
	}

	/**
	 * Sets the provideretqaid.
	 *
	 * @param provideretqaid the new provideretqaid
	 */
	public void setProvideretqaid(Integer provideretqaid) {
		this.provideretqaid = provideretqaid;
	}

	/**
	 * Gets the etqaacronym.
	 *
	 * @return the etqaacronym
	 */
	public String getEtqaacronym() {
		return etqaacronym;
	}

	/**
	 * Sets the etqaacronym.
	 *
	 * @param etqaacronym the new etqaacronym
	 */
	public void setEtqaacronym(String etqaacronym) {
		this.etqaacronym = etqaacronym;
	}

	/**
	 * Gets the etqaname.
	 *
	 * @return the etqaname
	 */
	public String getEtqaname() {
		return etqaname;
	}

	/**
	 * Sets the etqaname.
	 *
	 * @param etqaname the new etqaname
	 */
	public void setEtqaname(String etqaname) {
		this.etqaname = etqaname;
	}

	/**
	 * Gets the qualificationtypedesc.
	 *
	 * @return the qualificationtypedesc
	 */
	public String getQualificationtypedesc() {
		return qualificationtypedesc;
	}

	/**
	 * Sets the qualificationtypedesc.
	 *
	 * @param qualificationtypedesc the new qualificationtypedesc
	 */
	public void setQualificationtypedesc(String qualificationtypedesc) {
		this.qualificationtypedesc = qualificationtypedesc;
	}
	
	

	/**
	 * Gets the fielddescription.
	 *
	 * @return the fielddescription
	 */
	public String getFielddescription() {
		return fielddescription;
	}

	/**
	 * Sets the fielddescription.
	 *
	 * @param fielddescription the new fielddescription
	 */
	public void setFielddescription(String fielddescription) {
		this.fielddescription = fielddescription;
	}

	/**
	 * Gets the subfielddescription.
	 *
	 * @return the subfielddescription
	 */
	public String getSubfielddescription() {
		return subfielddescription;
	}

	/**
	 * Sets the subfielddescription.
	 *
	 * @param subfielddescription the new subfielddescription
	 */
	public void setSubfielddescription(String subfielddescription) {
		this.subfielddescription = subfielddescription;
	}

	/**
	 * Gets the abetbanddescription.
	 *
	 * @return the abetbanddescription
	 */
	public String getAbetbanddescription() {
		return abetbanddescription;
	}

	/**
	 * Sets the abetbanddescription.
	 *
	 * @param abetbanddescription the new abetbanddescription
	 */
	public void setAbetbanddescription(String abetbanddescription) {
		this.abetbanddescription = abetbanddescription;
	}

	/**
	 * Gets the qualificationminimumcredits.
	 *
	 * @return the qualificationminimumcredits
	 */
	public Integer getQualificationminimumcredits() {
		return qualificationminimumcredits;
	}

	/**
	 * Sets the qualificationminimumcredits.
	 *
	 * @param qualificationminimumcredits the new qualificationminimumcredits
	 */
	public void setQualificationminimumcredits(Integer qualificationminimumcredits) {
		this.qualificationminimumcredits = qualificationminimumcredits;
	}

	/**
	 * Gets the nqfleveldescription.
	 *
	 * @return the nqfleveldescription
	 */
	public String getNqfleveldescription() {
		return nqfleveldescription;
	}

	/**
	 * Sets the nqfleveldescription.
	 *
	 * @param nqfleveldescription the new nqfleveldescription
	 */
	public void setNqfleveldescription(String nqfleveldescription) {
		this.nqfleveldescription = nqfleveldescription;
	}

	/**
	 * Gets the nqflevelg 2 DESCRIPTION.
	 *
	 * @return the nqflevelg 2 DESCRIPTION
	 */
	public String getNqflevelg2DESCRIPTION() {
		return nqflevelg2DESCRIPTION;
	}

	/**
	 * Sets the nqflevelg 2 DESCRIPTION.
	 *
	 * @param nqflevelg2description the new nqflevelg 2 DESCRIPTION
	 */
	public void setNqflevelg2DESCRIPTION(String nqflevelg2description) {
		nqflevelg2DESCRIPTION = nqflevelg2description;
	}

	/**
	 * Gets the qualificationclassdesc.
	 *
	 * @return the qualificationclassdesc
	 */
	public String getQualificationclassdesc() {
		return qualificationclassdesc;
	}

	/**
	 * Sets the qualificationclassdesc.
	 *
	 * @param qualificationclassdesc the new qualificationclassdesc
	 */
	public void setQualificationclassdesc(String qualificationclassdesc) {
		this.qualificationclassdesc = qualificationclassdesc;
	}

	/**
	 * Gets the qualificationpurpose.
	 *
	 * @return the qualificationpurpose
	 */
	public String getQualificationpurpose() {
		return qualificationpurpose;
	}

	/**
	 * Sets the qualificationpurpose.
	 *
	 * @param qualificationpurpose the new qualificationpurpose
	 */
	public void setQualificationpurpose(String qualificationpurpose) {
		this.qualificationpurpose = qualificationpurpose;
	}

	/**
	 * Gets the quallearningassumedinplace.
	 *
	 * @return the quallearningassumedinplace
	 */
	public String getQuallearningassumedinplace() {
		return quallearningassumedinplace;
	}

	/**
	 * Sets the quallearningassumedinplace.
	 *
	 * @param quallearningassumedinplace the new quallearningassumedinplace
	 */
	public void setQuallearningassumedinplace(String quallearningassumedinplace) {
		this.quallearningassumedinplace = quallearningassumedinplace;
	}

	/**
	 * Gets the recognizeprevlearningflag.
	 *
	 * @return the recognizeprevlearningflag
	 */
	public String getRecognizeprevlearningflag() {
		return recognizeprevlearningflag;
	}

	/**
	 * Sets the recognizeprevlearningflag.
	 *
	 * @param recognizeprevlearningflag the new recognizeprevlearningflag
	 */
	public void setRecognizeprevlearningflag(String recognizeprevlearningflag) {
		this.recognizeprevlearningflag = recognizeprevlearningflag;
	}

	/**
	 * Gets the qualrulesofcombination.
	 *
	 * @return the qualrulesofcombination
	 */
	public String getQualrulesofcombination() {
		return qualrulesofcombination;
	}

	/**
	 * Sets the qualrulesofcombination.
	 *
	 * @param qualrulesofcombination the new qualrulesofcombination
	 */
	public void setQualrulesofcombination(String qualrulesofcombination) {
		this.qualrulesofcombination = qualrulesofcombination;
	}

	/**
	 * Gets the eloacqualificationoutcome.
	 *
	 * @return the eloacqualificationoutcome
	 */
	public String getEloacqualificationoutcome() {
		return eloacqualificationoutcome;
	}

	/**
	 * Sets the eloacqualificationoutcome.
	 *
	 * @param eloacqualificationoutcome the new eloacqualificationoutcome
	 */
	public void setEloacqualificationoutcome(String eloacqualificationoutcome) {
		this.eloacqualificationoutcome = eloacqualificationoutcome;
	}

	/**
	 * Gets the eloacqualassessmentcriteria.
	 *
	 * @return the eloacqualassessmentcriteria
	 */
	public String getEloacqualassessmentcriteria() {
		return eloacqualassessmentcriteria;
	}

	/**
	 * Sets the eloacqualassessmentcriteria.
	 *
	 * @param eloacqualassessmentcriteria the new eloacqualassessmentcriteria
	 */
	public void setEloacqualassessmentcriteria(String eloacqualassessmentcriteria) {
		this.eloacqualassessmentcriteria = eloacqualassessmentcriteria;
	}

	/**
	 * Gets the qualintlbenchmarkingmemo.
	 *
	 * @return the qualintlbenchmarkingmemo
	 */
	public String getQualintlbenchmarkingmemo() {
		return qualintlbenchmarkingmemo;
	}

	/**
	 * Sets the qualintlbenchmarkingmemo.
	 *
	 * @param qualintlbenchmarkingmemo the new qualintlbenchmarkingmemo
	 */
	public void setQualintlbenchmarkingmemo(String qualintlbenchmarkingmemo) {
		this.qualintlbenchmarkingmemo = qualintlbenchmarkingmemo;
	}

	/**
	 * Gets the qualarticulationoptions.
	 *
	 * @return the qualarticulationoptions
	 */
	public String getQualarticulationoptions() {
		return qualarticulationoptions;
	}

	/**
	 * Sets the qualarticulationoptions.
	 *
	 * @param qualarticulationoptions the new qualarticulationoptions
	 */
	public void setQualarticulationoptions(String qualarticulationoptions) {
		this.qualarticulationoptions = qualarticulationoptions;
	}

	/**
	 * Gets the qualmoderationoptions.
	 *
	 * @return the qualmoderationoptions
	 */
	public String getQualmoderationoptions() {
		return qualmoderationoptions;
	}

	/**
	 * Sets the qualmoderationoptions.
	 *
	 * @param qualmoderationoptions the new qualmoderationoptions
	 */
	public void setQualmoderationoptions(String qualmoderationoptions) {
		this.qualmoderationoptions = qualmoderationoptions;
	}

	/**
	 * Gets the qualassessorcriteria.
	 *
	 * @return the qualassessorcriteria
	 */
	public String getQualassessorcriteria() {
		return qualassessorcriteria;
	}

	/**
	 * Sets the qualassessorcriteria.
	 *
	 * @param qualassessorcriteria the new qualassessorcriteria
	 */
	public void setQualassessorcriteria(String qualassessorcriteria) {
		this.qualassessorcriteria = qualassessorcriteria;
	}

	/**
	 * Gets the qualificationnotes.
	 *
	 * @return the qualificationnotes
	 */
	public String getQualificationnotes() {
		return qualificationnotes;
	}

	/**
	 * Sets the qualificationnotes.
	 *
	 * @param qualificationnotes the new qualificationnotes
	 */
	public void setQualificationnotes(String qualificationnotes) {
		this.qualificationnotes = qualificationnotes;
	}

	/**
	 * Gets the registrationstatusdesc.
	 *
	 * @return the registrationstatusdesc
	 */
	public String getRegistrationstatusdesc() {
		return registrationstatusdesc;
	}

	/**
	 * Sets the registrationstatusdesc.
	 *
	 * @param registrationstatusdesc the new registrationstatusdesc
	 */
	public void setRegistrationstatusdesc(String registrationstatusdesc) {
		this.registrationstatusdesc = registrationstatusdesc;
	}

	/**
	 * Gets the saqadecisionnumber.
	 *
	 * @return the saqadecisionnumber
	 */
	public String getSaqadecisionnumber() {
		return saqadecisionnumber;
	}

	/**
	 * Sets the saqadecisionnumber.
	 *
	 * @param saqadecisionnumber the new saqadecisionnumber
	 */
	public void setSaqadecisionnumber(String saqadecisionnumber) {
		this.saqadecisionnumber = saqadecisionnumber;
	}

	/**
	 * Gets the qualregistrationstartdate.
	 *
	 * @return the qualregistrationstartdate
	 */
	public XMLGregorianCalendar getQualregistrationstartdate() {
		return qualregistrationstartdate;
	}

	/**
	 * Sets the qualregistrationstartdate.
	 *
	 * @param qualregistrationstartdate the new qualregistrationstartdate
	 */
	public void setQualregistrationstartdate(XMLGregorianCalendar qualregistrationstartdate) {
		this.qualregistrationstartdate = qualregistrationstartdate;
	}

	/**
	 * Gets the qualregistrationenddate.
	 *
	 * @return the qualregistrationenddate
	 */
	public XMLGregorianCalendar getQualregistrationenddate() {
		return qualregistrationenddate;
	}

	/**
	 * Sets the qualregistrationenddate.
	 *
	 * @param qualregistrationenddate the new qualregistrationenddate
	 */
	public void setQualregistrationenddate(XMLGregorianCalendar qualregistrationenddate) {
		this.qualregistrationenddate = qualregistrationenddate;
	}

	/**
	 * Gets the transitionperiod.
	 *
	 * @return the transitionperiod
	 */
	public Integer getTransitionperiod() {
		return transitionperiod;
	}

	/**
	 * Sets the transitionperiod.
	 *
	 * @param transitionperiod the new transitionperiod
	 */
	public void setTransitionperiod(Integer transitionperiod) {
		this.transitionperiod = transitionperiod;
	}

	/**
	 * Gets the lastdateforenrolment.
	 *
	 * @return the lastdateforenrolment
	 */
	public XMLGregorianCalendar getLastdateforenrolment() {
		return lastdateforenrolment;
	}

	/**
	 * Sets the lastdateforenrolment.
	 *
	 * @param lastdateforenrolment the new lastdateforenrolment
	 */
	public void setLastdateforenrolment(XMLGregorianCalendar lastdateforenrolment) {
		this.lastdateforenrolment = lastdateforenrolment;
	}

	/**
	 * Gets the trainoutperiod.
	 *
	 * @return the trainoutperiod
	 */
	public Integer getTrainoutperiod() {
		return trainoutperiod;
	}

	/**
	 * Sets the trainoutperiod.
	 *
	 * @param trainoutperiod the new trainoutperiod
	 */
	public void setTrainoutperiod(Integer trainoutperiod) {
		this.trainoutperiod = trainoutperiod;
	}

	/**
	 * Gets the lastdateforachievement.
	 *
	 * @return the lastdateforachievement
	 */
	public XMLGregorianCalendar getLastdateforachievement() {
		return lastdateforachievement;
	}

	/**
	 * Sets the lastdateforachievement.
	 *
	 * @param lastdateforachievement the new lastdateforachievement
	 */
	public void setLastdateforachievement(XMLGregorianCalendar lastdateforachievement) {
		this.lastdateforachievement = lastdateforachievement;
	}

	/**
	 * Gets the islearningprogramme.
	 *
	 * @return the islearningprogramme
	 */
	public String getIslearningprogramme() {
		return islearningprogramme;
	}

	/**
	 * Sets the islearningprogramme.
	 *
	 * @param islearningprogramme the new islearningprogramme
	 */
	public void setIslearningprogramme(String islearningprogramme) {
		this.islearningprogramme = islearningprogramme;
	}

	/**
	 * Gets the learningprogrammequal.
	 *
	 * @return the learningprogrammequal
	 */
	public Integer getLearningprogrammequal() {
		return learningprogrammequal;
	}

	/**
	 * Sets the learningprogrammequal.
	 *
	 * @param learningprogrammequal the new learningprogrammequal
	 */
	public void setLearningprogrammequal(Integer learningprogrammequal) {
		this.learningprogrammequal = learningprogrammequal;
	}

    /**
     * Gets the usqualificationlink.
     *
     * @return the usqualificationlink
     */
    public List<USQUALIFICATIONLINK_TEMP> getUSQUALIFICATIONLINK() {
        if (usqualificationlink == null) {
            usqualificationlink = new ArrayList<USQUALIFICATIONLINK_TEMP>();
        }
        return this.usqualificationlink;
    }

    /**
     * Gets the value of the lpqualificationlink property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lpqualificationlink property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLPQUALIFICATIONLINK().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SAQAQUALIFICATIONS.LPQUALIFICATIONLINK }
     *
     * @return the lpqualificationlink
     */
    public List<SAQAQUALIFICATIONS_TEMP.LPQUALIFICATIONLINK_TEMP> getLPQUALIFICATIONLINK() {
        if (lpqualificationlink == null) {
            lpqualificationlink = new ArrayList<SAQAQUALIFICATIONS_TEMP.LPQUALIFICATIONLINK_TEMP>();
        }
        return this.lpqualificationlink;
    }

	/**
	 * Gets the etqaid.
	 *
	 * @return the etqaid
	 */
	public String getEtqaid() {
		return etqaid;
	}

	/**
	 * Sets the etqaid.
	 *
	 * @param etqaid the new etqaid
	 */
	public void setEtqaid(String etqaid) {
		this.etqaid = etqaid;
	}

	/**
	 * Gets the nqflevelg 2 id.
	 *
	 * @return the nqflevelg 2 id
	 */
	public String getNqflevelg2Id() {
		return nqflevelg2Id;
	}

	/**
	 * Sets the nqflevelg 2 id.
	 *
	 * @param nqflevelg2Id the new nqflevelg 2 id
	 */
	public void setNqflevelg2Id(String nqflevelg2Id) {
		this.nqflevelg2Id = nqflevelg2Id;
	}

	public Date getLastdateforenrolment2() {
		return lastdateforenrolment2;
	}

	public void setLastdateforenrolment2(Date lastdateforenrolment2) {
		this.lastdateforenrolment2 = lastdateforenrolment2;
	}

	public Date getQualregistrationstartDate() {
		return qualregistrationstartDate;
	}

	public void setQualregistrationstartDate(Date qualregistrationstartDate) {
		this.qualregistrationstartDate = qualregistrationstartDate;
	}

	public Date getQualregistrationendDate() {
		return qualregistrationendDate;
	}

	public void setQualregistrationendDate(Date qualregistrationendDate) {
		this.qualregistrationendDate = qualregistrationendDate;
	}

	public Date getLastdateforachievement2() {
		return lastdateforachievement2;
	}

	public void setLastdateforachievement2(Date lastdateforachievement2) {
		this.lastdateforachievement2 = lastdateforachievement2;
	}

	public String getQualificationtypeid() {
		return qualificationtypeid;
	}

	public void setQualificationtypeid(String qualificationtypeid) {
		this.qualificationtypeid = qualificationtypeid;
	}

	public String getRegistrationstatuscode() {
		return registrationstatuscode;
	}

	public void setRegistrationstatuscode(String registrationstatuscode) {
		this.registrationstatuscode = registrationstatuscode;
	}

}
