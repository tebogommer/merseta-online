
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaximumWriteoff complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaximumWriteoff"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *           &lt;element name="SpecialAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MaximumWriteoffSpecialAmount"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaximumWriteoff", propOrder = {
    "amount",
    "specialAmount"
})
public class MaximumWriteoff {

    @XmlElement(name = "Amount")
    protected MoneyAmount amount;
    @XmlElement(name = "SpecialAmount")
    @XmlSchemaType(name = "string")
    protected MaximumWriteoffSpecialAmount specialAmount;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmount(MoneyAmount value) {
        this.amount = value;
    }

    /**
     * Gets the value of the specialAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MaximumWriteoffSpecialAmount }
     *     
     */
    public MaximumWriteoffSpecialAmount getSpecialAmount() {
        return specialAmount;
    }

    /**
     * Sets the value of the specialAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaximumWriteoffSpecialAmount }
     *     
     */
    public void setSpecialAmount(MaximumWriteoffSpecialAmount value) {
        this.specialAmount = value;
    }

}
