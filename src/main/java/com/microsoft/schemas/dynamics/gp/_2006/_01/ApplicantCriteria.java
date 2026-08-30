
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RequisitionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="FirstName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PositionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfApplicationStatus" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantCriteria", propOrder = {
    "requisitionKeyId",
    "firstName",
    "lastName",
    "positionKeyId",
    "divisionKeyId",
    "departmentKeyId",
    "applicationStatus",
    "lastModifiedDate",
    "applicantKeyId"
})
public class ApplicantCriteria
    extends Criteria
{

    @XmlElement(name = "RequisitionKeyId")
    protected LikeRestrictionOfNullableOfInt32 requisitionKeyId;
    @XmlElement(name = "FirstName")
    protected LikeRestrictionOfString firstName;
    @XmlElement(name = "LastName")
    protected LikeRestrictionOfString lastName;
    @XmlElement(name = "PositionKeyId")
    protected LikeRestrictionOfString positionKeyId;
    @XmlElement(name = "DivisionKeyId")
    protected LikeRestrictionOfString divisionKeyId;
    @XmlElement(name = "DepartmentKeyId")
    protected LikeRestrictionOfString departmentKeyId;
    @XmlElement(name = "ApplicationStatus")
    protected ListRestrictionOfNullableOfApplicationStatus applicationStatus;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;

    /**
     * Gets the value of the requisitionKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public LikeRestrictionOfNullableOfInt32 getRequisitionKeyId() {
        return requisitionKeyId;
    }

    /**
     * Sets the value of the requisitionKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public void setRequisitionKeyId(LikeRestrictionOfNullableOfInt32 value) {
        this.requisitionKeyId = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setFirstName(LikeRestrictionOfString value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setLastName(LikeRestrictionOfString value) {
        this.lastName = value;
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
     * Gets the value of the applicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfApplicationStatus }
     *     
     */
    public ListRestrictionOfNullableOfApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Sets the value of the applicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfApplicationStatus }
     *     
     */
    public void setApplicationStatus(ListRestrictionOfNullableOfApplicationStatus value) {
        this.applicationStatus = value;
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

}
