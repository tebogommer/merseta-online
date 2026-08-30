
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantInterviewTypeKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantInterviewTypeKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantKey" minOccurs="0"/&gt;
 *         &lt;element name="InterviewTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InterviewTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ApplyDateKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplyDateKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantInterviewTypeKey", propOrder = {
    "applicantKey",
    "interviewTypeKey",
    "applyDateKey"
})
public class ApplicantInterviewTypeKey
    extends ReferenceKey
{

    @XmlElement(name = "ApplicantKey")
    protected ApplicantKey applicantKey;
    @XmlElement(name = "InterviewTypeKey")
    protected InterviewTypeKey interviewTypeKey;
    @XmlElement(name = "ApplyDateKey")
    protected ApplyDateKey applyDateKey;

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
     * Gets the value of the interviewTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link InterviewTypeKey }
     *     
     */
    public InterviewTypeKey getInterviewTypeKey() {
        return interviewTypeKey;
    }

    /**
     * Sets the value of the interviewTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterviewTypeKey }
     *     
     */
    public void setInterviewTypeKey(InterviewTypeKey value) {
        this.interviewTypeKey = value;
    }

    /**
     * Gets the value of the applyDateKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplyDateKey }
     *     
     */
    public ApplyDateKey getApplyDateKey() {
        return applyDateKey;
    }

    /**
     * Sets the value of the applyDateKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplyDateKey }
     *     
     */
    public void setApplyDateKey(ApplyDateKey value) {
        this.applyDateKey = value;
    }

}
