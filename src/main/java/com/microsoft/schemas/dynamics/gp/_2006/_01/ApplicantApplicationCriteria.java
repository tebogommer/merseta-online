
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantApplicationCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantApplicationCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SequenceKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="RequisitionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PositionKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfApplicationStatus" minOccurs="0"/&gt;
 *         &lt;element name="DateApplied" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantApplicationCriteria", propOrder = {
    "sequenceKeyId",
    "applicantKeyId",
    "requisitionKeyId",
    "divisionKeyId",
    "departmentKeyId",
    "positionKeyId",
    "status",
    "dateApplied"
})
public class ApplicantApplicationCriteria
    extends Criteria
{

    @XmlElement(name = "SequenceKeyId")
    protected LikeRestrictionOfNullableOfInt32 sequenceKeyId;
    @XmlElement(name = "ApplicantKeyId")
    protected LikeRestrictionOfNullableOfInt32 applicantKeyId;
    @XmlElement(name = "RequisitionKeyId")
    protected LikeRestrictionOfString requisitionKeyId;
    @XmlElement(name = "DivisionKeyId")
    protected LikeRestrictionOfString divisionKeyId;
    @XmlElement(name = "DepartmentKeyId")
    protected LikeRestrictionOfString departmentKeyId;
    @XmlElement(name = "PositionKeyId")
    protected LikeRestrictionOfString positionKeyId;
    @XmlElement(name = "Status")
    protected ListRestrictionOfNullableOfApplicationStatus status;
    @XmlElement(name = "DateApplied")
    protected BetweenRestrictionOfNullableOfDateTime dateApplied;

    /**
     * Gets the value of the sequenceKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public LikeRestrictionOfNullableOfInt32 getSequenceKeyId() {
        return sequenceKeyId;
    }

    /**
     * Sets the value of the sequenceKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfNullableOfInt32 }
     *     
     */
    public void setSequenceKeyId(LikeRestrictionOfNullableOfInt32 value) {
        this.sequenceKeyId = value;
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
     * Gets the value of the requisitionKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getRequisitionKeyId() {
        return requisitionKeyId;
    }

    /**
     * Sets the value of the requisitionKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setRequisitionKeyId(LikeRestrictionOfString value) {
        this.requisitionKeyId = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfApplicationStatus }
     *     
     */
    public ListRestrictionOfNullableOfApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfApplicationStatus }
     *     
     */
    public void setStatus(ListRestrictionOfNullableOfApplicationStatus value) {
        this.status = value;
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

}
