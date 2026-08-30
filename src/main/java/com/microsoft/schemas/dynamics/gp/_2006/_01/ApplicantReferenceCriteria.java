
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantReferenceCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantReferenceCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="ReferenceName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Relationship" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="CompanyName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantReferenceCriteria", propOrder = {
    "applicantKeyId",
    "referenceName",
    "relationship",
    "lastModifiedDate",
    "companyName"
})
public class ApplicantReferenceCriteria
    extends Criteria
{

    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;
    @XmlElement(name = "ReferenceName")
    protected LikeRestrictionOfString referenceName;
    @XmlElement(name = "Relationship")
    protected LikeRestrictionOfString relationship;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "CompanyName")
    protected LikeRestrictionOfString companyName;

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
     * Gets the value of the referenceName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getReferenceName() {
        return referenceName;
    }

    /**
     * Sets the value of the referenceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setReferenceName(LikeRestrictionOfString value) {
        this.referenceName = value;
    }

    /**
     * Gets the value of the relationship property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setRelationship(LikeRestrictionOfString value) {
        this.relationship = value;
    }

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
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCompanyName(LikeRestrictionOfString value) {
        this.companyName = value;
    }

}
