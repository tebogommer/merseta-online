
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeePayCodeCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeePayCodeCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeeKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PayCodeKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="IsInactive" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="PayPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfCompensationPeriod" minOccurs="0"/&gt;
 *         &lt;element name="IsTaxable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="PayRate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDecimal" minOccurs="0"/&gt;
 *         &lt;element name="PayType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfEmployeePayTypes" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeePayCodeCriteria", propOrder = {
    "employeeKeyId",
    "payCodeKeyId",
    "isInactive",
    "payPeriod",
    "isTaxable",
    "payRate",
    "payType"
})
public class EmployeePayCodeCriteria
    extends Criteria
{

    @XmlElement(name = "EmployeeKeyId")
    protected LikeRestrictionOfString employeeKeyId;
    @XmlElement(name = "PayCodeKeyId")
    protected LikeRestrictionOfString payCodeKeyId;
    @XmlElement(name = "IsInactive")
    protected RestrictionOfNullableOfBoolean isInactive;
    @XmlElement(name = "PayPeriod")
    protected ListRestrictionOfNullableOfCompensationPeriod payPeriod;
    @XmlElement(name = "IsTaxable")
    protected RestrictionOfNullableOfBoolean isTaxable;
    @XmlElement(name = "PayRate")
    protected BetweenRestrictionOfNullableOfDecimal payRate;
    @XmlElement(name = "PayType")
    protected ListRestrictionOfNullableOfEmployeePayTypes payType;

    /**
     * Gets the value of the employeeKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployeeKeyId() {
        return employeeKeyId;
    }

    /**
     * Sets the value of the employeeKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployeeKeyId(LikeRestrictionOfString value) {
        this.employeeKeyId = value;
    }

    /**
     * Gets the value of the payCodeKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPayCodeKeyId() {
        return payCodeKeyId;
    }

    /**
     * Sets the value of the payCodeKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPayCodeKeyId(LikeRestrictionOfString value) {
        this.payCodeKeyId = value;
    }

    /**
     * Gets the value of the isInactive property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsInactive() {
        return isInactive;
    }

    /**
     * Sets the value of the isInactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsInactive(RestrictionOfNullableOfBoolean value) {
        this.isInactive = value;
    }

    /**
     * Gets the value of the payPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfCompensationPeriod }
     *     
     */
    public ListRestrictionOfNullableOfCompensationPeriod getPayPeriod() {
        return payPeriod;
    }

    /**
     * Sets the value of the payPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfCompensationPeriod }
     *     
     */
    public void setPayPeriod(ListRestrictionOfNullableOfCompensationPeriod value) {
        this.payPeriod = value;
    }

    /**
     * Gets the value of the isTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsTaxable() {
        return isTaxable;
    }

    /**
     * Sets the value of the isTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsTaxable(RestrictionOfNullableOfBoolean value) {
        this.isTaxable = value;
    }

    /**
     * Gets the value of the payRate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDecimal }
     *     
     */
    public BetweenRestrictionOfNullableOfDecimal getPayRate() {
        return payRate;
    }

    /**
     * Sets the value of the payRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDecimal }
     *     
     */
    public void setPayRate(BetweenRestrictionOfNullableOfDecimal value) {
        this.payRate = value;
    }

    /**
     * Gets the value of the payType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfEmployeePayTypes }
     *     
     */
    public ListRestrictionOfNullableOfEmployeePayTypes getPayType() {
        return payType;
    }

    /**
     * Sets the value of the payType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfEmployeePayTypes }
     *     
     */
    public void setPayType(ListRestrictionOfNullableOfEmployeePayTypes value) {
        this.payType = value;
    }

}
