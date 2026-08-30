
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeApplicableTaxes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeApplicableTaxes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsFederalTaxApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsSocialSecurityApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsMedicareApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsStateTaxApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsLocalTaxApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsFutaApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsSutaApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SutaState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeApplicableTaxes", propOrder = {
    "isFederalTaxApplied",
    "isSocialSecurityApplied",
    "isMedicareApplied",
    "isStateTaxApplied",
    "isLocalTaxApplied",
    "isFutaApplied",
    "isSutaApplied",
    "sutaState"
})
public class EmployeeApplicableTaxes {

    @XmlElement(name = "IsFederalTaxApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFederalTaxApplied;
    @XmlElement(name = "IsSocialSecurityApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSocialSecurityApplied;
    @XmlElement(name = "IsMedicareApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isMedicareApplied;
    @XmlElement(name = "IsStateTaxApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isStateTaxApplied;
    @XmlElement(name = "IsLocalTaxApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLocalTaxApplied;
    @XmlElement(name = "IsFutaApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFutaApplied;
    @XmlElement(name = "IsSutaApplied", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSutaApplied;
    @XmlElement(name = "SutaState")
    protected String sutaState;

    /**
     * Gets the value of the isFederalTaxApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFederalTaxApplied() {
        return isFederalTaxApplied;
    }

    /**
     * Sets the value of the isFederalTaxApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFederalTaxApplied(Boolean value) {
        this.isFederalTaxApplied = value;
    }

    /**
     * Gets the value of the isSocialSecurityApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSocialSecurityApplied() {
        return isSocialSecurityApplied;
    }

    /**
     * Sets the value of the isSocialSecurityApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSocialSecurityApplied(Boolean value) {
        this.isSocialSecurityApplied = value;
    }

    /**
     * Gets the value of the isMedicareApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMedicareApplied() {
        return isMedicareApplied;
    }

    /**
     * Sets the value of the isMedicareApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMedicareApplied(Boolean value) {
        this.isMedicareApplied = value;
    }

    /**
     * Gets the value of the isStateTaxApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsStateTaxApplied() {
        return isStateTaxApplied;
    }

    /**
     * Sets the value of the isStateTaxApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsStateTaxApplied(Boolean value) {
        this.isStateTaxApplied = value;
    }

    /**
     * Gets the value of the isLocalTaxApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLocalTaxApplied() {
        return isLocalTaxApplied;
    }

    /**
     * Sets the value of the isLocalTaxApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLocalTaxApplied(Boolean value) {
        this.isLocalTaxApplied = value;
    }

    /**
     * Gets the value of the isFutaApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFutaApplied() {
        return isFutaApplied;
    }

    /**
     * Sets the value of the isFutaApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFutaApplied(Boolean value) {
        this.isFutaApplied = value;
    }

    /**
     * Gets the value of the isSutaApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSutaApplied() {
        return isSutaApplied;
    }

    /**
     * Sets the value of the isSutaApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSutaApplied(Boolean value) {
        this.isSutaApplied = value;
    }

    /**
     * Gets the value of the sutaState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSutaState() {
        return sutaState;
    }

    /**
     * Sets the value of the sutaState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSutaState(String value) {
        this.sutaState = value;
    }

}
