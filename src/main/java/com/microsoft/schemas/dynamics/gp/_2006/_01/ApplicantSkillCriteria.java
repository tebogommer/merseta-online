
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantSkillCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantSkillCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="SkillKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Proficiency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantSkillCriteria", propOrder = {
    "applicantKeyId",
    "skillKeyId",
    "proficiency"
})
public class ApplicantSkillCriteria
    extends Criteria
{

    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;
    @XmlElement(name = "SkillKeyId")
    protected LikeRestrictionOfString skillKeyId;
    @XmlElement(name = "Proficiency")
    protected LikeRestrictionOfNullableOfInt32 proficiency;

    /**
     * Gets the value of the applicantKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public LikeRestrictionOfNullableOfInt32 getApplicantKeyId() {
        return applicantKeyId;
    }

    /**
     * Sets the value of the applicantKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public void setApplicantKeyId(LikeRestrictionOfNullableOfInt32 value) {
        this.applicantKeyId = value;
    }

    /**
     * Gets the value of the skillKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSkillKeyId() {
        return skillKeyId;
    }

    /**
     * Sets the value of the skillKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSkillKeyId(LikeRestrictionOfString value) {
        this.skillKeyId = value;
    }

    /**
     * Gets the value of the proficiency property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public LikeRestrictionOfNullableOfInt32 getProficiency() {
        return proficiency;
    }

    /**
     * Sets the value of the proficiency property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public void setProficiency(LikeRestrictionOfNullableOfInt32 value) {
        this.proficiency = value;
    }

}
