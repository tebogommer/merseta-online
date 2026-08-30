
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EmployeePayStep complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeePayStep"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PayStepKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayStepKey" minOccurs="0"/&gt;
 *         &lt;element name="Step" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StepFTE" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="BasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeBaseStepIncreasedOn"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeePayStep", propOrder = {
    "payStepKey",
    "step",
    "effectiveDate",
    "stepFTE",
    "basedOn"
})
public class EmployeePayStep {

    @XmlElement(name = "PayStepKey")
    protected PayStepKey payStepKey;
    @XmlElement(name = "Step", required = true, type = Integer.class, nillable = true)
    protected Integer step;
    @XmlElement(name = "EffectiveDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlElement(name = "StepFTE", required = true, nillable = true)
    protected BigDecimal stepFTE;
    @XmlElement(name = "BasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected EmployeeBaseStepIncreasedOn basedOn;

    /**
     * Gets the value of the payStepKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayStepKey }
     *     
     */
    public PayStepKey getPayStepKey() {
        return payStepKey;
    }

    /**
     * Sets the value of the payStepKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayStepKey }
     *     
     */
    public void setPayStepKey(PayStepKey value) {
        this.payStepKey = value;
    }

    /**
     * Gets the value of the step property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStep() {
        return step;
    }

    /**
     * Sets the value of the step property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStep(Integer value) {
        this.step = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the stepFTE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStepFTE() {
        return stepFTE;
    }

    /**
     * Sets the value of the stepFTE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStepFTE(BigDecimal value) {
        this.stepFTE = value;
    }

    /**
     * Gets the value of the basedOn property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeBaseStepIncreasedOn }
     *     
     */
    public EmployeeBaseStepIncreasedOn getBasedOn() {
        return basedOn;
    }

    /**
     * Sets the value of the basedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeBaseStepIncreasedOn }
     *     
     */
    public void setBasedOn(EmployeeBaseStepIncreasedOn value) {
        this.basedOn = value;
    }

}
