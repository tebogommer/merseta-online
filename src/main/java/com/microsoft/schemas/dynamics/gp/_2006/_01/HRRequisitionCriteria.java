
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRRequisitionCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HRRequisitionCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RequisitionNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfHRRequisitionStatus" minOccurs="0"/&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="OpeningDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="InternalPostDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="InternalCloseDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HRRequisitionCriteria", propOrder = {
    "requisitionNumber",
    "status",
    "companyKey",
    "openingDate",
    "internalPostDate",
    "internalCloseDate",
    "managerKey",
    "lastModifiedDate"
})
public class HRRequisitionCriteria
    extends Criteria
{

    @XmlElement(name = "RequisitionNumber")
    protected BetweenRestrictionOfNullableOfInt32 requisitionNumber;
    @XmlElement(name = "Status")
    protected ListRestrictionOfNullableOfHRRequisitionStatus status;
    @XmlElement(name = "CompanyKey")
    protected LikeRestrictionOfString companyKey;
    @XmlElement(name = "OpeningDate")
    protected BetweenRestrictionOfNullableOfDateTime openingDate;
    @XmlElement(name = "InternalPostDate")
    protected BetweenRestrictionOfNullableOfDateTime internalPostDate;
    @XmlElement(name = "InternalCloseDate")
    protected BetweenRestrictionOfNullableOfDateTime internalCloseDate;
    @XmlElement(name = "ManagerKey")
    protected LikeRestrictionOfString managerKey;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;

    /**
     * Gets the value of the requisitionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public BetweenRestrictionOfNullableOfInt32 getRequisitionNumber() {
        return requisitionNumber;
    }

    /**
     * Sets the value of the requisitionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public void setRequisitionNumber(BetweenRestrictionOfNullableOfInt32 value) {
        this.requisitionNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfHRRequisitionStatus }
     *     
     */
    public ListRestrictionOfNullableOfHRRequisitionStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfHRRequisitionStatus }
     *     
     */
    public void setStatus(ListRestrictionOfNullableOfHRRequisitionStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCompanyKey(LikeRestrictionOfString value) {
        this.companyKey = value;
    }

    /**
     * Gets the value of the openingDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getOpeningDate() {
        return openingDate;
    }

    /**
     * Sets the value of the openingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setOpeningDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.openingDate = value;
    }

    /**
     * Gets the value of the internalPostDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getInternalPostDate() {
        return internalPostDate;
    }

    /**
     * Sets the value of the internalPostDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setInternalPostDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.internalPostDate = value;
    }

    /**
     * Gets the value of the internalCloseDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getInternalCloseDate() {
        return internalCloseDate;
    }

    /**
     * Sets the value of the internalCloseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setInternalCloseDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.internalCloseDate = value;
    }

    /**
     * Gets the value of the managerKey property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getManagerKey() {
        return managerKey;
    }

    /**
     * Sets the value of the managerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setManagerKey(LikeRestrictionOfString value) {
        this.managerKey = value;
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

}
