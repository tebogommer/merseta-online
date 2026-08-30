
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantSequenceKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantSequenceKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantKey" minOccurs="0"/&gt;
 *         &lt;element name="SequenceKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SequenceKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantSequenceKey", propOrder = {
    "applicantKey",
    "sequenceKey"
})
public class ApplicantSequenceKey
    extends ReferenceKey
{

    @XmlElement(name = "ApplicantKey")
    protected ApplicantKey applicantKey;
    @XmlElement(name = "SequenceKey")
    protected SequenceKey sequenceKey;

    /**
     * Gets the value of the applicantKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantKey }
     *     
     */
    public ApplicantKey getApplicantKey() {
        return applicantKey;
    }

    /**
     * Sets the value of the applicantKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantKey }
     *     
     */
    public void setApplicantKey(ApplicantKey value) {
        this.applicantKey = value;
    }

    /**
     * Gets the value of the sequenceKey property.
     * 
     * @return
     *     possible object is
     *     {@link SequenceKey }
     *     
     */
    public SequenceKey getSequenceKey() {
        return sequenceKey;
    }

    /**
     * Sets the value of the sequenceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceKey }
     *     
     */
    public void setSequenceKey(SequenceKey value) {
        this.sequenceKey = value;
    }

}
