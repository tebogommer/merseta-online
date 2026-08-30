
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditLimit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditLimit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="SpecialAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CreditLimitSpecialAmount"/&gt;
 *           &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
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
@XmlType(name = "CreditLimit", propOrder = {
    "specialAmount",
    "amount"
})
@XmlSeeAlso({
    VendorCreditLimit.class,
    CustomerCreditLimit.class
})
public abstract class CreditLimit {

    @XmlElement(name = "SpecialAmount")
    @XmlSchemaType(name = "string")
    protected CreditLimitSpecialAmount specialAmount;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;

    /**
     * Gets the value of the specialAmount property.
     * 
     * @return
     *     possible object is
     *     {@link CreditLimitSpecialAmount }
     *     
     */
    public CreditLimitSpecialAmount getSpecialAmount() {
        return specialAmount;
    }

    /**
     * Sets the value of the specialAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditLimitSpecialAmount }
     *     
     */
    public void setSpecialAmount(CreditLimitSpecialAmount value) {
        this.specialAmount = value;
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
