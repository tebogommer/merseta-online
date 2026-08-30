
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantInterviewCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantInterviewCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PositionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DateApplied" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="InterviewDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="InterviewTypeKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantInterviewCriteria", propOrder = {
    "applicantKeyId",
    "departmentKeyId",
    "divisionKeyId",
    "positionKeyId",
    "dateApplied",
    "interviewDate",
    "interviewTypeKeyId"
})
public class ApplicantInterviewCriteria
    extends Criteria
{

    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;
    @XmlElement(name = "DepartmentKeyId")
    protected LikeRestrictionOfString departmentKeyId;
    @XmlElement(name = "DivisionKeyId")
    protected LikeRestrictionOfString divisionKeyId;
    @XmlElement(name = "PositionKeyId")
    protected LikeRestrictionOfString positionKeyId;
    @XmlElement(name = "DateApplied")
    protected BetweenRestrictionOfNullableOfDateTime dateApplied;
    @XmlElement(name = "InterviewDate")
    protected BetweenRestrictionOfNullableOfDateTime interviewDate;
    @XmlElement(name = "InterviewTypeKeyId")
    protected LikeRestrictionOfString interviewTypeKeyId;

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
     * Gets the value of the departmentKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDepartmentKeyId() {
        return departmentKeyId;
    }

    /**
     * Sets the value of the departmentKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDepartmentKeyId(LikeRestrictionOfString value) {
        this.departmentKeyId = value;
    }

    /**
     * Gets the value of the divisionKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDivisionKeyId() {
        return divisionKeyId;
    }

    /**
     * Sets the value of the divisionKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDivisionKeyId(LikeRestrictionOfString value) {
        this.divisionKeyId = value;
    }

    /**
     * Gets the value of the positionKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPositionKeyId() {
        return positionKeyId;
    }

    /**
     * Sets the value of the positionKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPositionKeyId(LikeRestrictionOfString value) {
        this.positionKeyId = value;
    }

    /**
     * Gets the value of the dateApplied property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getDateApplied() {
        return dateApplied;
    }

    /**
     * Sets the value of the dateApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setDateApplied(BetweenRestrictionOfNullableOfDateTime value) {
        this.dateApplied = value;
    }

    /**
     * Gets the value of the interviewDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getInterviewDate() {
        return interviewDate;
    }

    /**
     * Sets the value of the interviewDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setInterviewDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.interviewDate = value;
    }

    /**
     * Gets the value of the interviewTypeKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getInterviewTypeKeyId() {
        return interviewTypeKeyId;
    }

    /**
     * Sets the value of the interviewTypeKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setInterviewTypeKeyId(LikeRestrictionOfString value) {
        this.interviewTypeKeyId = value;
    }

}
