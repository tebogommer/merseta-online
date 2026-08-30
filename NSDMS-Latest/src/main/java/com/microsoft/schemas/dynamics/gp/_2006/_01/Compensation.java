
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Compensation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Compensation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompensationPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompensationPeriod"/&gt;
 *         &lt;element name="CompensationAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Compensation", propOrder = {
    "compensationPeriod",
    "compensationAmount"
})
public class Compensation
    extends BusinessObject
{

    @XmlElement(name = "CompensationPeriod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CompensationPeriod compensationPeriod;
    @XmlElement(name = "CompensationAmount", required = true, nillable = true)
    protected BigDecimal compensationAmount;

    /**
     * Gets the value of the compensationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link CompensationPeriod }
     *     
     */
    public CompensationPeriod getCompensationPeriod() {
        return compensationPeriod;
    }

    /**
     * Sets the value of the compensationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompensationPeriod }
     *     
     */
    public void setCompensationPeriod(CompensationPeriod value) {
        this.compensationPeriod = value;
    }

    /**
     * Gets the value of the compensationAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    /**
     * Sets the value of the compensationAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCompensationAmount(BigDecimal value) {
        this.compensationAmount = value;
    }

}
