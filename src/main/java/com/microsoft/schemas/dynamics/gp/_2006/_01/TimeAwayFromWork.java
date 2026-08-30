
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeAwayFromWork complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeAwayFromWork"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DoesAutomaticallyAccrue" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AccrualAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="AccrualMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TimeAwayFromWorkAccrualMethod"/&gt;
 *         &lt;element name="HoursPerYear" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="HoursAvailable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="WarnWhenHoursAvailableFallsBelowZero" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeAwayFromWork", propOrder = {
    "doesAutomaticallyAccrue",
    "accrualAmount",
    "accrualMethod",
    "hoursPerYear",
    "hoursAvailable",
    "warnWhenHoursAvailableFallsBelowZero"
})
@XmlSeeAlso({
    Vacation.class,
    SickTime.class
})
public abstract class TimeAwayFromWork {

    @XmlElement(name = "DoesAutomaticallyAccrue", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAutomaticallyAccrue;
    @XmlElement(name = "AccrualAmount")
    protected Quantity accrualAmount;
    @XmlElement(name = "AccrualMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TimeAwayFromWorkAccrualMethod accrualMethod;
    @XmlElement(name = "HoursPerYear", required = true, type = Integer.class, nillable = true)
    protected Integer hoursPerYear;
    @XmlElement(name = "HoursAvailable")
    protected Quantity hoursAvailable;
    @XmlElement(name = "WarnWhenHoursAvailableFallsBelowZero", required = true, type = Boolean.class, nillable = true)
    protected Boolean warnWhenHoursAvailableFallsBelowZero;

    /**
     * Gets the value of the doesAutomaticallyAccrue property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAutomaticallyAccrue() {
        return doesAutomaticallyAccrue;
    }

    /**
     * Sets the value of the doesAutomaticallyAccrue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAutomaticallyAccrue(Boolean value) {
        this.doesAutomaticallyAccrue = value;
    }

    /**
     * Gets the value of the accrualAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getAccrualAmount() {
        return accrualAmount;
    }

    /**
     * Sets the value of the accrualAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setAccrualAmount(Quantity value) {
        this.accrualAmount = value;
    }

    /**
     * Gets the value of the accrualMethod property.
     * 
     * @return
     *     possible object is
     *     {@link TimeAwayFromWorkAccrualMethod }
     *     
     */
    public TimeAwayFromWorkAccrualMethod getAccrualMethod() {
        return accrualMethod;
    }

    /**
     * Sets the value of the accrualMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeAwayFromWorkAccrualMethod }
     *     
     */
    public void setAccrualMethod(TimeAwayFromWorkAccrualMethod value) {
        this.accrualMethod = value;
    }

    /**
     * Gets the value of the hoursPerYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHoursPerYear() {
        return hoursPerYear;
    }

    /**
     * Sets the value of the hoursPerYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHoursPerYear(Integer value) {
        this.hoursPerYear = value;
    }

    /**
     * Gets the value of the hoursAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getHoursAvailable() {
        return hoursAvailable;
    }

    /**
     * Sets the value of the hoursAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setHoursAvailable(Quantity value) {
        this.hoursAvailable = value;
    }

    /**
     * Gets the value of the warnWhenHoursAvailableFallsBelowZero property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWarnWhenHoursAvailableFallsBelowZero() {
        return warnWhenHoursAvailableFallsBelowZero;
    }

    /**
     * Sets the value of the warnWhenHoursAvailableFallsBelowZero property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWarnWhenHoursAvailableFallsBelowZero(Boolean value) {
        this.warnWhenHoursAvailableFallsBelowZero = value;
    }

}
