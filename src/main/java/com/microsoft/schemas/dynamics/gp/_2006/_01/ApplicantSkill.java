
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantSkill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantSkill"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantSkillKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantSkillKey" minOccurs="0"/&gt;
 *         &lt;element name="SkillNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Proficiency" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantSkill", propOrder = {
    "applicantSkillKey",
    "skillNumber",
    "proficiency",
    "comments",
    "deleteOnUpdate"
})
public class ApplicantSkill
    extends BusinessObject
{

    @XmlElement(name = "ApplicantSkillKey")
    protected ApplicantSkillKey applicantSkillKey;
    @XmlElement(name = "SkillNumber", required = true, type = Integer.class, nillable = true)
    protected Integer skillNumber;
    @XmlElement(name = "Proficiency", required = true, type = Integer.class, nillable = true)
    protected Integer proficiency;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

    /**
     * Gets the value of the applicantSkillKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantSkillKey }
     *     
     */
    public ApplicantSkillKey getApplicantSkillKey() {
        return applicantSkillKey;
    }

    /**
     * Sets the value of the applicantSkillKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantSkillKey }
     *     
     */
    public void setApplicantSkillKey(ApplicantSkillKey value) {
        this.applicantSkillKey = value;
    }

    /**
     * Gets the value of the skillNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSkillNumber() {
        return skillNumber;
    }

    /**
     * Sets the value of the skillNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSkillNumber(Integer value) {
        this.skillNumber = value;
    }

    /**
     * Gets the value of the proficiency property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProficiency() {
        return proficiency;
    }

    /**
     * Sets the value of the proficiency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProficiency(Integer value) {
        this.proficiency = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
    }

}
