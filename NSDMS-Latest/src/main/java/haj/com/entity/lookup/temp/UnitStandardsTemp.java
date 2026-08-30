package haj.com.entity.lookup.temp;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * This entity is the unit standards 
 * 
 * UnitStandards.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "saqa_unitstandard_temp")
public class UnitStandardsTemp implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of UnitStandards. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**The last date for enrolment*/
	 @Column(name = "lastDateForEnrolment",  nullable = true )
	 private Date lastDateForEnrolment;
	
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "usregistrationstartdate", nullable = true)
	 private Date usregistrationstartDate;
	
	 @Temporal(TemporalType.TIMESTAMP)
     @Column(name = "usregistrationenddate", nullable = true)
     private Date usregistrationendDate;
	
	/** title or description of the unit standard. */
	@Column(name = "unitstdtitle")
	private String title;

	/** Code for the unit standard. */
	@Column(name = "unitstandardid")
	private Integer code;

	/** Code for the unit standard. */
	@Column(name = "nqflevelg2DESCRIPTION")
	private String nqf;
	
	@Column(name = "unitstdnumberofcredits")
	private String unitstdnumberofcredits;
	
	/** The nqflevelg 2 description. */
	
    //private String nqflevelg2description;
	/**
	 * Default Constructor.
	 */
	
	@Column(name = "field")
	private String field;
	
	@Column(name = "saqadecisionnumber")
	private String saqadecisionnumber;
	/**
	 * Constructor using title, code and NQF Level fields
	 * 
	 * @param title
	 * @param code
	 * @param nqfLevel
	 */
	
	/** The abetbanddescription. */
    private String abetbanddescription;
    
    /** The etqaacronym. */
    private String etqaacronym;
    
    /** The etqaname. */
    private String etqaname;   
    
    /** The fielddescription. */
    private String fielddescription;
    
    /** The nqfleveldescription. */
    private String nqfleveldescription;
    
    /** The providercode. */
    private String providercode;
    
    /** The provideretqaid. */
    private Integer provideretqaid;
    
    /** The providername. */
    private String providername;
    
    /** The registrationstatusdesc. */
    private String registrationstatusdesc;
    
    /** The sgbname. */
    private String sgbname;
    
    /** The subfielddescription. */
    private String subfielddescription;
    
    /** The trainoutperiod. */
    private Integer trainoutperiod;
    
    /** The transitionperiod. */
    private Integer transitionperiod;
         
    /** The unitstdaccreditationoptions. */
    private String unitstdaccreditationoptions;
    
    /** The unitstdassessorcriteria. */
    private String unitstdassessorcriteria;
    
    /** The unitstdccfocollecting. */
    private String unitstdccfocollecting;
    
    /** The unitstdccfocommunicating. */
    private String unitstdccfocommunicating;
    
    /** The unitstdccfocontributing. */
    private String unitstdccfocontributing;
    
    /** The unitstdccfodemonstrating. */
    private String unitstdccfodemonstrating;
    
    /** The unitstdccfoidentifying. */
    private String unitstdccfoidentifying;
    
    /** The unitstdccfoorganizing. */
    private String unitstdccfoorganizing;
    
    /** The unitstdccfoscience. */
    private String unitstdccfoscience;
    
    /** The unitstdccfoworking. */
    private String unitstdccfoworking;
    
    /** The unitstddevelopmentaloutcome. */
    private String unitstddevelopmentaloutcome;
    
    /** The unitstdembeddedknowledge. */
    private String unitstdembeddedknowledge;
    
    /** The unitstdlearningassumptions. */
    private String unitstdlearningassumptions;
    
    /** The unitstdlinkages. */
    private String unitstdlinkages;
    
    /** The unitstdnotes. */
    private String unitstdnotes;
         
    /** The unitstdoutcomeheader. */
    private String unitstdoutcomeheader;
    
    /** The unitstdpurpose. */
    private String unitstdpurpose;
    
    /** The unitstdrange. */
    private String unitstdrange;
    
    /** The unitstdtypedesc. */
    private String unitstdtypedesc;

	public UnitStandardsTemp() {
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
		UnitStandardsTemp other = (UnitStandardsTemp) obj;
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
	 * @return id
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
     * Gets the abetbanddescription.
     *
     * @return the abetbanddescription
     */
    @Column(name="abetbanddescription")
    public String getAbetbanddescription() {
        return this.abetbanddescription;
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
     * Gets the etqaacronym.
     *
     * @return the etqaacronym
     */
    @Column(name="etqaacronym")
    public String getEtqaacronym() {
        return this.etqaacronym;
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
    @Column(name="etqaname")
    public String getEtqaname() {
        return this.etqaname;
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
     * Gets the fielddescription.
     *
     * @return the fielddescription
     */
    @Column(name="fielddescription")
    public String getFielddescription() {
        return this.fielddescription;
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
     * Gets the nqfleveldescription.
     *
     * @return the nqfleveldescription
     */
    @Column(name="nqfleveldescription")
    public String getNqfleveldescription() {
        return this.nqfleveldescription;
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
     * Gets the providercode.
     *
     * @return the providercode
     */
    @Column(name="providercode")
    public String getProvidercode() {
        return this.providercode;
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
     * Gets the provideretqaid.
     *
     * @return the provideretqaid
     */
    @Column(name="provideretqaid")
    public Integer getProvideretqaid() {
        return this.provideretqaid;
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
     * Gets the providername.
     *
     * @return the providername
     */
    @Column(name="providername")
    public String getProvidername() {
        return this.providername;
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
     * Gets the registrationstatusdesc.
     *
     * @return the registrationstatusdesc
     */
    @Column(name="registrationstatusdesc")
    public String getRegistrationstatusdesc() {
        return this.registrationstatusdesc;
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
     * Gets the sgbname.
     *
     * @return the sgbname
     */
    @Column(name="sgbname")
    public String getSgbname() {
        return this.sgbname;
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
     * Gets the subfielddescription.
     *
     * @return the subfielddescription
     */
    @Column(name="subfielddescription")
    public String getSubfielddescription() {
        return this.subfielddescription;
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
     * Gets the trainoutperiod.
     *
     * @return the trainoutperiod
     */
    @Column(name="trainoutperiod")
    public Integer getTrainoutperiod() {
        return this.trainoutperiod;
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
     * Gets the transitionperiod.
     *
     * @return the transitionperiod
     */
    @Column(name="transitionperiod")
    public Integer getTransitionperiod() {
        return this.transitionperiod;
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
     * Gets the unitstdaccreditationoptions.
     *
     * @return the unitstdaccreditationoptions
     */
    @Column(name="unitstdaccreditationoptions")
    public String getUnitstdaccreditationoptions() {
        return this.unitstdaccreditationoptions;
    }
    
    /**
     * Sets the unitstdaccreditationoptions.
     *
     * @param unitstdaccreditationoptions the new unitstdaccreditationoptions
     */
    public void setUnitstdaccreditationoptions(String unitstdaccreditationoptions) {
        this.unitstdaccreditationoptions = unitstdaccreditationoptions;
    }

    
    /**
     * Gets the unitstdassessorcriteria.
     *
     * @return the unitstdassessorcriteria
     */
    @Column(name="unitstdassessorcriteria")
    public String getUnitstdassessorcriteria() {
        return this.unitstdassessorcriteria;
    }
    
    /**
     * Sets the unitstdassessorcriteria.
     *
     * @param unitstdassessorcriteria the new unitstdassessorcriteria
     */
    public void setUnitstdassessorcriteria(String unitstdassessorcriteria) {
        this.unitstdassessorcriteria = unitstdassessorcriteria;
    }

    
    /**
     * Gets the unitstdccfocollecting.
     *
     * @return the unitstdccfocollecting
     */
    @Column(name="unitstdccfocollecting")
    public String getUnitstdccfocollecting() {
        return this.unitstdccfocollecting;
    }
    
    /**
     * Sets the unitstdccfocollecting.
     *
     * @param unitstdccfocollecting the new unitstdccfocollecting
     */
    public void setUnitstdccfocollecting(String unitstdccfocollecting) {
        this.unitstdccfocollecting = unitstdccfocollecting;
    }

    
    /**
     * Gets the unitstdccfocommunicating.
     *
     * @return the unitstdccfocommunicating
     */
    @Column(name="unitstdccfocommunicating")
    public String getUnitstdccfocommunicating() {
        return this.unitstdccfocommunicating;
    }
    
    /**
     * Sets the unitstdccfocommunicating.
     *
     * @param unitstdccfocommunicating the new unitstdccfocommunicating
     */
    public void setUnitstdccfocommunicating(String unitstdccfocommunicating) {
        this.unitstdccfocommunicating = unitstdccfocommunicating;
    }

    
    /**
     * Gets the unitstdccfocontributing.
     *
     * @return the unitstdccfocontributing
     */
    @Column(name="unitstdccfocontributing")
    public String getUnitstdccfocontributing() {
        return this.unitstdccfocontributing;
    }
    
    /**
     * Sets the unitstdccfocontributing.
     *
     * @param unitstdccfocontributing the new unitstdccfocontributing
     */
    public void setUnitstdccfocontributing(String unitstdccfocontributing) {
        this.unitstdccfocontributing = unitstdccfocontributing;
    }

    
    /**
     * Gets the unitstdccfodemonstrating.
     *
     * @return the unitstdccfodemonstrating
     */
    @Column(name="unitstdccfodemonstrating")
    public String getUnitstdccfodemonstrating() {
        return this.unitstdccfodemonstrating;
    }
    
    /**
     * Sets the unitstdccfodemonstrating.
     *
     * @param unitstdccfodemonstrating the new unitstdccfodemonstrating
     */
    public void setUnitstdccfodemonstrating(String unitstdccfodemonstrating) {
        this.unitstdccfodemonstrating = unitstdccfodemonstrating;
    }

    
    /**
     * Gets the unitstdccfoidentifying.
     *
     * @return the unitstdccfoidentifying
     */
    @Column(name="unitstdccfoidentifying")
    public String getUnitstdccfoidentifying() {
        return this.unitstdccfoidentifying;
    }
    
    /**
     * Sets the unitstdccfoidentifying.
     *
     * @param unitstdccfoidentifying the new unitstdccfoidentifying
     */
    public void setUnitstdccfoidentifying(String unitstdccfoidentifying) {
        this.unitstdccfoidentifying = unitstdccfoidentifying;
    }

    
    /**
     * Gets the unitstdccfoorganizing.
     *
     * @return the unitstdccfoorganizing
     */
    @Column(name="unitstdccfoorganizing")
    public String getUnitstdccfoorganizing() {
        return this.unitstdccfoorganizing;
    }
    
    /**
     * Sets the unitstdccfoorganizing.
     *
     * @param unitstdccfoorganizing the new unitstdccfoorganizing
     */
    public void setUnitstdccfoorganizing(String unitstdccfoorganizing) {
        this.unitstdccfoorganizing = unitstdccfoorganizing;
    }

    
    /**
     * Gets the unitstdccfoscience.
     *
     * @return the unitstdccfoscience
     */
    @Column(name="unitstdccfoscience")
    public String getUnitstdccfoscience() {
        return this.unitstdccfoscience;
    }
    
    /**
     * Sets the unitstdccfoscience.
     *
     * @param unitstdccfoscience the new unitstdccfoscience
     */
    public void setUnitstdccfoscience(String unitstdccfoscience) {
        this.unitstdccfoscience = unitstdccfoscience;
    }

    
    /**
     * Gets the unitstdccfoworking.
     *
     * @return the unitstdccfoworking
     */
    @Column(name="unitstdccfoworking")
    public String getUnitstdccfoworking() {
        return this.unitstdccfoworking;
    }
    
    /**
     * Sets the unitstdccfoworking.
     *
     * @param unitstdccfoworking the new unitstdccfoworking
     */
    public void setUnitstdccfoworking(String unitstdccfoworking) {
        this.unitstdccfoworking = unitstdccfoworking;
    }

    
    /**
     * Gets the unitstddevelopmentaloutcome.
     *
     * @return the unitstddevelopmentaloutcome
     */
    @Column(name="unitstddevelopmentaloutcome")
    public String getUnitstddevelopmentaloutcome() {
        return this.unitstddevelopmentaloutcome;
    }
    
    /**
     * Sets the unitstddevelopmentaloutcome.
     *
     * @param unitstddevelopmentaloutcome the new unitstddevelopmentaloutcome
     */
    public void setUnitstddevelopmentaloutcome(String unitstddevelopmentaloutcome) {
        this.unitstddevelopmentaloutcome = unitstddevelopmentaloutcome;
    }

    
    /**
     * Gets the unitstdembeddedknowledge.
     *
     * @return the unitstdembeddedknowledge
     */
    @Column(name="unitstdembeddedknowledge")
    public String getUnitstdembeddedknowledge() {
        return this.unitstdembeddedknowledge;
    }
    
    /**
     * Sets the unitstdembeddedknowledge.
     *
     * @param unitstdembeddedknowledge the new unitstdembeddedknowledge
     */
    public void setUnitstdembeddedknowledge(String unitstdembeddedknowledge) {
        this.unitstdembeddedknowledge = unitstdembeddedknowledge;
    }

    
    /**
     * Gets the unitstdlearningassumptions.
     *
     * @return the unitstdlearningassumptions
     */
    @Column(name="unitstdlearningassumptions")
    public String getUnitstdlearningassumptions() {
        return this.unitstdlearningassumptions;
    }
    
    /**
     * Sets the unitstdlearningassumptions.
     *
     * @param unitstdlearningassumptions the new unitstdlearningassumptions
     */
    public void setUnitstdlearningassumptions(String unitstdlearningassumptions) {
        this.unitstdlearningassumptions = unitstdlearningassumptions;
    }

    
    /**
     * Gets the unitstdlinkages.
     *
     * @return the unitstdlinkages
     */
    @Column(name="unitstdlinkages")
    public String getUnitstdlinkages() {
        return this.unitstdlinkages;
    }
    
    /**
     * Sets the unitstdlinkages.
     *
     * @param unitstdlinkages the new unitstdlinkages
     */
    public void setUnitstdlinkages(String unitstdlinkages) {
        this.unitstdlinkages = unitstdlinkages;
    }

    
    /**
     * Gets the unitstdnotes.
     *
     * @return the unitstdnotes
     */
    @Column(name="unitstdnotes")
    public String getUnitstdnotes() {
        return this.unitstdnotes;
    }
    
    /**
     * Sets the unitstdnotes.
     *
     * @param unitstdnotes the new unitstdnotes
     */
    public void setUnitstdnotes(String unitstdnotes) {
        this.unitstdnotes = unitstdnotes;
    }

    

    
    /**
     * Gets the unitstdoutcomeheader.
     *
     * @return the unitstdoutcomeheader
     */
    @Column(name="unitstdoutcomeheader")
    public String getUnitstdoutcomeheader() {
        return this.unitstdoutcomeheader;
    }
    
    /**
     * Sets the unitstdoutcomeheader.
     *
     * @param unitstdoutcomeheader the new unitstdoutcomeheader
     */
    public void setUnitstdoutcomeheader(String unitstdoutcomeheader) {
        this.unitstdoutcomeheader = unitstdoutcomeheader;
    }

    
    /**
     * Gets the unitstdpurpose.
     *
     * @return the unitstdpurpose
     */
    @Column(name="unitstdpurpose")
    public String getUnitstdpurpose() {
        return this.unitstdpurpose;
    }
    
    /**
     * Sets the unitstdpurpose.
     *
     * @param unitstdpurpose the new unitstdpurpose
     */
    public void setUnitstdpurpose(String unitstdpurpose) {
        this.unitstdpurpose = unitstdpurpose;
    }

    
    /**
     * Gets the unitstdrange.
     *
     * @return the unitstdrange
     */
    @Column(name="unitstdrange")
    public String getUnitstdrange() {
        return this.unitstdrange;
    }
    
    /**
     * Sets the unitstdrange.
     *
     * @param unitstdrange the new unitstdrange
     */
    public void setUnitstdrange(String unitstdrange) {
        this.unitstdrange = unitstdrange;
    }

    

    
    /**
     * Gets the unitstdtypedesc.
     *
     * @return the unitstdtypedesc
     */
    @Column(name="unitstdtypedesc")
    public String getUnitstdtypedesc() {
        return this.unitstdtypedesc;
    }
    
    /**
     * Sets the unitstdtypedesc.
     *
     * @param unitstdtypedesc the new unitstdtypedesc
     */
    public void setUnitstdtypedesc(String unitstdtypedesc) {
        this.unitstdtypedesc = unitstdtypedesc;
    }



	/**
	 * Gets the title.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	public String getNqf() {
		return nqf;
	}

	public void setNqf(String nqf) {
		this.nqf = nqf;
	}

	public String getUnitstdnumberofcredits() {
		return unitstdnumberofcredits;
	}

	public void setUnitstdnumberofcredits(String unitstdnumberofcredits) {
		this.unitstdnumberofcredits = unitstdnumberofcredits;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSaqadecisionnumber() {
		return saqadecisionnumber;
	}

	public void setSaqadecisionnumber(String saqadecisionnumber) {
		this.saqadecisionnumber = saqadecisionnumber;
	}

	public String getNqflevelg2description() {
		return nqf;
	}

	public void setNqflevelg2description(String nqflevelg2description) {
		this.nqf = nqflevelg2description;
	}

	public Date getLastDateForEnrolment() {
		return lastDateForEnrolment;
	}

	public void setLastDateForEnrolment(Date lastDateForEnrolment) {
		this.lastDateForEnrolment = lastDateForEnrolment;
	}

	public Date getUsregistrationstartDate() {
		return usregistrationstartDate;
	}

	public void setUsregistrationstartDate(Date usregistrationstartDate) {
		this.usregistrationstartDate = usregistrationstartDate;
	}

	public Date getUsregistrationendDate() {
		return usregistrationendDate;
	}

	public void setUsregistrationendDate(Date usregistrationendDate) {
		this.usregistrationendDate = usregistrationendDate;
	}


}
