
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantWorkHistoryCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantWorkHistoryCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="Employer" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Wage" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDecimal" minOccurs="0"/&gt;
 *         &lt;element name="YearsOfExperience" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantWorkHistoryCriteria", propOrder = {
    "lastModifiedDate",
    "applicantKeyId",
    "employer",
    "wage",
    "yearsOfExperience"
})
public class ApplicantWorkHistoryCriteria
    extends Criteria
{

    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;
    @XmlElement(name = "Employer")
    protected LikeRestrictionOfString employer;
    @XmlElement(name = "Wage")
    protected BetweenRestrictionOfNullableOfDecimal wage;
    @XmlElement(name = "YearsOfExperience")
    protected BetweenRestrictionOfString yearsOfExperience;

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setLastModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.lastModifiedDate = value;
    }

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
     * Gets the value of the employer property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployer() {
        return employer;
    }

    /**
     * Sets the value of the employer property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployer(LikeRestrictionOfString value) {
        this.employer = value;
    }

    /**
     * Gets the value of the wage property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDecimal }
     *     
     */
    public BetweenRestrictionOfNullableOfDecimal getWage() {
        return wage;
    }

    /**
     * Sets the value of the wage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDecimal }
     *     
     */
    public void setWage(BetweenRestrictionOfNullableOfDecimal value) {
        this.wage = value;
    }

    /**
     * Gets the value of the yearsOfExperience property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Sets the value of the yearsOfExperience property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setYearsOfExperience(BetweenRestrictionOfString value) {
        this.yearsOfExperience = value;
    }

}
