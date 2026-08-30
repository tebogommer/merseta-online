
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeePayCodeSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeePayCodeSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeePayCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeePayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="IsInactive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PayType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeePayTypes"/&gt;
 *         &lt;element name="PayPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompensationPeriod"/&gt;
 *         &lt;element name="IsTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PayRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PayUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeePayCodeSummary", propOrder = {
    "employeePayCodeKey",
    "isInactive",
    "payType",
    "payPeriod",
    "isTaxable",
    "payRate",
    "payUnit"
})
public class EmployeePayCodeSummary {

    @XmlElement(name = "EmployeePayCodeKey")
    protected EmployeePayCodeKey employeePayCodeKey;
    @XmlElement(name = "IsInactive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInactive;
    @XmlElement(name = "PayType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected EmployeePayTypes payType;
    @XmlElement(name = "PayPeriod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CompensationPeriod payPeriod;
    @XmlElement(name = "IsTaxable", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxable;
    @XmlElement(name = "PayRate", required = true, nillable = true)
    protected BigDecimal payRate;
    @XmlElement(name = "PayUnit")
    protected String payUnit;

    /**
     * Gets the value of the employeePayCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeePayCodeKey }
     *     
     */
    public EmployeePayCodeKey getEmployeePayCodeKey() {
        return employeePayCodeKey;
    }

    /**
     * Sets the value of the employeePayCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeePayCodeKey }
     *     
     */
    public void setEmployeePayCodeKey(EmployeePayCodeKey value) {
        this.employeePayCodeKey = value;
    }

    /**
     * Gets the value of the isInactive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInactive() {
        return isInactive;
    }

    /**
     * Sets the value of the isInactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInactive(Boolean value) {
        this.isInactive = value;
    }

    /**
     * Gets the value of the payType property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeePayTypes }
     *     
     */
    public EmployeePayTypes getPayType() {
        return payType;
    }

    /**
     * Sets the value of the payType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeePayTypes }
     *     
     */
    public void setPayType(EmployeePayTypes value) {
        this.payType = value;
    }

    /**
     * Gets the value of the payPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link CompensationPeriod }
     *     
     */
    public CompensationPeriod getPayPeriod() {
        return payPeriod;
    }

    /**
     * Sets the value of the payPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompensationPeriod }
     *     
     */
    public void setPayPeriod(CompensationPeriod value) {
        this.payPeriod = value;
    }

    /**
     * Gets the value of the isTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxable() {
        return isTaxable;
    }

    /**
     * Sets the value of the isTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxable(Boolean value) {
        this.isTaxable = value;
    }

    /**
     * Gets the value of the payRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayRate() {
        return payRate;
    }

    /**
     * Sets the value of the payRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayRate(BigDecimal value) {
        this.payRate = value;
    }

    /**
     * Gets the value of the payUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayUnit() {
        return payUnit;
    }

    /**
     * Sets the value of the payUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayUnit(String value) {
        this.payUnit = value;
    }

}
