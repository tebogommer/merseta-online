
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoundingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoundingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Option" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RoundHow"/&gt;
 *         &lt;element name="Policy" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RoundTo"/&gt;
 *         &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoundingType", propOrder = {
    "option",
    "policy",
    "amount"
})
public class RoundingType {

    @XmlElement(name = "Option", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RoundHow option;
    @XmlElement(name = "Policy", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RoundTo policy;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;

    /**
     * Gets the value of the option property.
     * 
     * @return
     *     possible object is
     *     {@link RoundHow }
     *     
     */
    public RoundHow getOption() {
        return option;
    }

    /**
     * Sets the value of the option property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoundHow }
     *     
     */
    public void setOption(RoundHow value) {
        this.option = value;
    }

    /**
     * Gets the value of the policy property.
     * 
     * @return
     *     possible object is
     *     {@link RoundTo }
     *     
     */
    public RoundTo getPolicy() {
        return policy;
    }

    /**
     * Sets the value of the policy property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoundTo }
     *     
     */
    public void setPolicy(RoundTo value) {
        this.policy = value;
    }

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

}
