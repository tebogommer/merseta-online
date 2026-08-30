
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantSkillKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantSkillKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantKey" minOccurs="0"/&gt;
 *         &lt;element name="SkillKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SkillKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantSkillKey", propOrder = {
    "applicantKey",
    "skillKey"
})
public class ApplicantSkillKey
    extends ReferenceKey
{

    @XmlElement(name = "ApplicantKey")
    protected ApplicantKey applicantKey;
    @XmlElement(name = "SkillKey")
    protected SkillKey skillKey;

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
     * Gets the value of the skillKey property.
     * 
     * @return
     *     possible object is
     *     {@link SkillKey }
     *     
     */
    public SkillKey getSkillKey() {
        return skillKey;
    }

    /**
     * Sets the value of the skillKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillKey }
     *     
     */
    public void setSkillKey(SkillKey value) {
        this.skillKey = value;
    }

}
